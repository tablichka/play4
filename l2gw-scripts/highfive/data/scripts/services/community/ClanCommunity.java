package services.community;

import javolution.util.FastList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2ClanMember;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.ClanTable;
import ru.l2gw.util.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author: rage
 * @date: 23.05.2010 12:12:52
 */
public class ClanCommunity extends Functions implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");
	private static final int CLANS_PER_PAGE = 10;

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Clan Community service loaded.");
			CommunityBoardManager.getInstance().registerHandler(this);
		}
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
			CommunityBoardManager.getInstance().unregisterHandler(this);
	}

	public void onShutdown()
	{}

	public String[] getBypassCommands()
	{
		return new String[]{"_bbsclan", "_clbbsclan_", "_clbbslist_", "_clsearch", "_clbbsadmi", "_mailwritepledgeform", "_announcepledgewriteform", "_announcepledgeswitchshowflag", "_announcepledgewrite", "_clwriteintro", "_clwritemail"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		player.setSessionVar("add_fav", null);
		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			String html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsclan".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan != null && clan.getLevel() > 1)
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			onBypassCommand(player, "_clbbslist_1_0_");
		}
		else if("clbbslist".equals(cmd))
		{
			int page = Integer.parseInt(st.nextToken());
			int byCL = Integer.parseInt(st.nextToken());

			String search = st.hasMoreTokens() ? st.nextToken() : "";

			String html = Files.read("data/scripts/services/community/html/bbs_clanlist.htm", player, false);

			L2Clan playerClan = player.getClan();
			if(playerClan != null)
			{
				html = html.replace("%PLEDGE_ID%", String.valueOf(playerClan.getClanId()));
				html = html.replace("%MY_PLEDGE_NAME%", playerClan.getLevel() > 1 ? playerClan.getName() : "");
			}
			else
			{
				html = html.replace("%PLEDGE_ID%", "0");
				html = html.replace("%MY_PLEDGE_NAME%", "");
			}
			
			List<L2Clan> clanList = getClanList(search, byCL == 1);

			int start = (page - 1) * CLANS_PER_PAGE;
			int end = Math.min(page * CLANS_PER_PAGE, clanList.size());

			if(page == 1)
			{
				html = html.replace("%ACTION_GO_LEFT%", "");
				html = html.replace("%GO_LIST%", "");
				html = html.replace("%NPAGE%", "1");
			}
			else
			{
				html = html.replace("%ACTION_GO_LEFT%", "bypass -h _clbbslist_" + (page - 1) + "_" + byCL + "_" + search);
				html = html.replace("%NPAGE%", String.valueOf(page));
				StringBuilder goList = new StringBuilder("");
				for(int i = page > 10 ? page - 10 : 1 ; i < page; i++)
					goList.append("<td><a action=\"bypass -h _clbbslist_").append(i).append("_").append(byCL).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST%", goList.toString());
			}

			int pages = Math.max(clanList.size() / CLANS_PER_PAGE, 1);
			if(clanList.size() > pages * CLANS_PER_PAGE)
				pages++;

			if(pages > page)
			{
				html = html.replace("%ACTION_GO_RIGHT%", "bypass -h _clbbslist_" + (page + 1) + "_" + byCL + "_" + search);
				int ep = Math.min(page + 10, pages);
				StringBuilder goList = new StringBuilder("");
				for(int i = page + 1; i <= ep; i++)
					goList.append("<td><a action=\"bypass -h _clbbslist_").append(i).append("_").append(byCL).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST2%", goList.toString());
			}
			else
			{
				html = html.replace("%ACTION_GO_RIGHT%", "");
				html = html.replace("%GO_LIST2%", "");
			}

			StringBuilder cl = new StringBuilder("");
			String tpl = Files.read("data/scripts/services/community/html/bbs_clantpl.htm", player, false);
			for(int i = start; i < end; i++)
			{
				L2Clan clan = clanList.get(i);
				String clantpl = tpl;
				clantpl = clantpl.replace("%action_clanhome%", "bypass -h _clbbsclan_" + clan.getClanId());
				clantpl = clantpl.replace("%clan_name%", clan.getName());
				clantpl = clantpl.replace("%clan_owner%", clan.getLeaderName());
				clantpl = clantpl.replace("%skill_level%", String.valueOf(clan.getLevel()));
				clantpl = clantpl.replace("%member_count%", String.valueOf(clan.getMembersCount()));
				cl.append(clantpl);
			}

			html = html.replace("%CLAN_LIST%", cl.toString());

			ShowBoard.separateAndSend(html, player);
		}
		else if("clbbsclan".equals(cmd))
		{
			int clanId = Integer.parseInt(st.nextToken());
			if(clanId == 0)
			{
				player.sendPacket(new SystemMessage(SystemMessage.NOT_JOINED_IN_ANY_CLAN));
				onBypassCommand(player, "_clbbslist_1_0");
				return;
			}

			L2Clan clan = ClanTable.getInstance().getClan(clanId);
			if(clan == null)
			{
				onBypassCommand(player, "_clbbslist_1_0");
				return;
			}

			if(clan.getLevel() < 2)
			{
				player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_NO_COMMUNITIES_IN_MY_CLAN_CLAN_COMMUNITIES_ARE_ALLOWED_FOR_CLANS_WITH_SKILL_LEVELS_OF_2_AND_HIGHER));
				onBypassCommand(player, "_clbbslist_1_0");
				return;
			}

			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			String intro = "";
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type = 2");
				statement.setInt(1, clanId);
				rset = statement.executeQuery();
				if(rset.next())
					intro = rset.getString("notice");
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			String html = Files.read("data/scripts/services/community/html/bbs_clan.htm", player, false);

			html = html.replace("%PLEDGE_ID%", String.valueOf(clanId));
			html = html.replace("%ACTION_ANN%", "");
			html = html.replace("%ACTION_FREE%", "");

			if(player.getClanId() == clanId && player.isClanLeader())
			{
				html = html.replace("%CLAN_ADMINLINK%", "<a action=\"bypass -h _clbbsadmi\">[Clan Management]</a>&nbsp;&nbsp; ");
				html = html.replace("%CLAN_MAILLINK%", "<a action=\"bypass -h _mailwritepledgeform\">[Clan Mail]</a>&nbsp;&nbsp; ");
				html = html.replace("%CLAN_ANNOUNCELINK%", "<a action=\"bypass -h _announcepledgewriteform\">[Clan Notice]</a>&nbsp;&nbsp; ");
			}
			else
			{
				html = html.replace("%CLAN_ADMINLINK%", "");
				html = html.replace("%CLAN_MAILLINK%", "");
				html = html.replace("%CLAN_ANNOUNCELINK%", "");
			}

			html = html.replace("%CLAN_INTRO%", intro.replace("\n", "<br1>"));
			html = html.replace("%CLAN_NAME%", clan.getName());
			html = html.replace("%SKILL_LEVEL%", String.valueOf(clan.getLevel()));
			html = html.replace("%CLAN_MEMBERS%", String.valueOf(clan.getMembersCount()));
			html = html.replace("%OWNER_NAME%", clan.getLeaderName());
			html = html.replace("%ALLIANCE_NAME%", clan.getAlliance() != null ? clan.getAlliance().getAllyName() : "");

			html = html.replace("%ANN_LIST%", "");
			html = html.replace("%THREAD_LIST%", "");

			ShowBoard.separateAndSend(html, player);
		}
		else if("clbbsadmi".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			String html = Files.read("data/scripts/services/community/html/bbs_clanadmin.htm", player, false);

			html = html.replace("%PLEDGE_ID%", String.valueOf(clan.getClanId()));
			html = html.replace("%ACTION_ANN%", "");
			html = html.replace("%ACTION_FREE%", "");
			html = html.replace("%CLAN_NAME%", clan.getName());
			html = html.replace("%per_list%", "");

			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			String intro = "";
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type = 2");
				statement.setInt(1, clan.getClanId());
				rset = statement.executeQuery();
				if(rset.next())
					intro = rset.getString("notice");
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			List<String> args = new FastList<String>();
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0"); // account data ?
			args.add("");
			args.add("0"); // account data ?
			args.add("");
			args.add("0"); // account data ?
			args.add("");
			args.add("");
			args.add(intro);
			args.add("");
			args.add("");
			args.add("0");
			args.add("0");
			args.add("");

			player.sendPacket(new ShowBoard(html, "1001", player));
			player.sendPacket(new ShowBoard(args));
		}
		else if("mailwritepledgeform".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			String html = Files.read("data/scripts/services/community/html/bbs_pledge_mail_write.htm", player, false);

			html = html.replace("%pledge_id%", String.valueOf(clan.getClanId()));
			html = html.replace("%pledge_name%", clan.getName());

			ShowBoard.separateAndSend(html, player);
		}
		else if("announcepledgewriteform".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			String html = Files.read("data/scripts/services/community/html/bbs_clanannounce.htm", player, false);

			html = html.replace("%PLEDGE_ID%", String.valueOf(clan.getClanId()));
			html = html.replace("%ACTION_ANN%", "");
			html = html.replace("%ACTION_FREE%", "");

			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			String notice = "";
			int type = 0;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type != 2");
				statement.setInt(1, clan.getClanId());
				rset = statement.executeQuery();
				if(rset.next())
				{
					notice = rset.getString("notice");
					type = rset.getInt("type");
				}
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			if(type == 0)
			{
				html = html.replace("%usage%", "off");
				html = html.replace("%switch_flag%", "1");
				html = html.replace("%usage_flag%", "on");
			}
			else
			{
				html = html.replace("%usage%", "on");
				html = html.replace("%switch_flag%", "0");
				html = html.replace("%usage_flag%", "off");
			}
			
			html = html.replace("%flag%", String.valueOf(type));

			List<String> args = new FastList<String>();
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0");
			args.add("0"); // account data ?
			args.add("");
			args.add("0"); // account data ?
			args.add("");
			args.add("0"); // account data ?
			args.add("");
			args.add("");
			args.add(notice);
			args.add("");
			args.add("");
			args.add("0");
			args.add("0");
			args.add("");

			player.sendPacket(new ShowBoard(html, "1001", player));
			player.sendPacket(new ShowBoard(args));
		}
		else if("announcepledgeswitchshowflag".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			int type = Integer.parseInt(st.nextToken());

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("UPDATE `bbs_clannotice` SET type = ? WHERE `clan_id` = ? and type = ?");
				statement.setInt(1, type);
				statement.setInt(2, clan.getClanId());
				statement.setInt(3, type == 1 ? 0 : 1);
				statement.execute();
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}

			clan.setNotice(type == 0 ? "" : null);
			onBypassCommand(player, "_announcepledgewriteform");
		}
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		if("clsearch".equals(cmd))
		{
			if(arg3 == null)
				arg3 = "";

			onBypassCommand(player, "_clbbslist_1_" + ("Ruler".equals(arg4) ? "1" : "0") + "_" + arg3);
		}
		else if("clwriteintro".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader() || arg3 == null || arg3.isEmpty())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			arg3 = arg3.replace("<", "");
			arg3 = arg3.replace(">", "");
			arg3 = arg3.replace("&", "");
			arg3 = arg3.replace("$", "");

			if(arg3.length() > 3000)
				arg3 = arg3.substring(0, 3000);

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("REPLACE INTO `bbs_clannotice`(clan_id, type, notice) VALUES(?, ?, ?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, 2);
				statement.setString(3, arg3);
				statement.execute();
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}

			onBypassCommand(player, "_clbbsclan_" + player.getClanId());
		}
		else if("clwritemail".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			if(arg3 == null || arg4 == null)
			{
				player.sendPacket(Msg.THE_MESSAGE_WAS_NOT_SENT);
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			arg3 = arg3.replace("<", "");
			arg3 = arg3.replace(">", "");
			arg3 = arg3.replace("&", "");
			arg3 = arg3.replace("$", "");

			arg5 = arg5.replace("<", "");
			arg5 = arg5.replace(">", "");
			arg5 = arg5.replace("&", "");
			arg5 = arg5.replace("$", "");

			if(arg3.isEmpty() || arg4.isEmpty())
			{
				player.sendPacket(Msg.THE_MESSAGE_WAS_NOT_SENT);
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			if(arg3.length() > 128)
				arg3 = arg3.substring(0, 128);

			if(arg4.length() > 3000)
				arg5 = arg5.substring(0, 3000);

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("INSERT INTO `bbs_mail`(to_name, to_object_id, from_name, from_object_id, title, message, post_date, box_type) VALUES(?, ?, ?, ?, ?, ?, ?, 0)");
				for(L2ClanMember clm : clan.getMembers())
				{
					statement.setString(1, clan.getName());
					statement.setInt(2, clm.getObjectId());
					statement.setString(3, player.getName());
					statement.setInt(4, player.getObjectId());
					statement.setString(5, arg3);
					statement.setString(6, arg5);
					statement.setInt(7, (int) (System.currentTimeMillis() / 1000));
					statement.execute();
				}
				statement.close();

				statement = con.prepareStatement("INSERT INTO `bbs_mail`(to_name, to_object_id, from_name, from_object_id, title, message, post_date, box_type) VALUES(?, ?, ?, ?, ?, ?, ?, 1)");
				statement.setString(1, clan.getName());
				statement.setInt(2, player.getObjectId());
				statement.setString(3, player.getName());
				statement.setInt(4, player.getObjectId());
				statement.setString(5, arg3);
				statement.setString(6, arg5);
				statement.setInt(7, (int) (System.currentTimeMillis() / 1000));
				statement.execute();
			}
			catch(Exception e)
			{
				player.sendPacket(Msg.THE_MESSAGE_WAS_NOT_SENT);
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}

			player.sendPacket(Msg.YOUVE_SENT_MAIL);

			for(L2Player member : clan.getOnlineMembers(""))
			{
				member.sendPacket(Msg.YOUVE_GOT_MAIL);
				member.sendPacket(Msg.ExMailArrived);
			}

			onBypassCommand(player, "_clbbsclan_" + player.getClanId());
		}
		else if("announcepledgewrite".equals(cmd))
		{
			L2Clan clan = player.getClan();
			if(clan == null || clan.getLevel() < 2 || !player.isClanLeader())
			{
				onBypassCommand(player, "_clbbsclan_" + player.getClanId());
				return;
			}

			if(arg3 == null || arg3.isEmpty())
			{
				onBypassCommand(player, "_announcepledgewriteform");
				return;
			}

			arg3 = arg3.replace("<", "");
			arg3 = arg3.replace(">", "");
			arg3 = arg3.replace("&", "");
			arg3 = arg3.replace("$", "");

			if(arg3.isEmpty())
			{
				onBypassCommand(player, "_announcepledgewriteform");
				return;
			}

			if(arg3.length() > 3000)
				arg3 = arg3.substring(0, 3000);

			int type = Integer.parseInt(st.nextToken());

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("REPLACE INTO `bbs_clannotice`(clan_id, type, notice) VALUES(?, ?, ?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, type);
				statement.setString(3, arg3);
				statement.execute();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				onBypassCommand(player, "_announcepledgewriteform");
				return;
			}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}

			if(type == 1)
				clan.setNotice(arg3.replace("\n", "<br1>"));
			else
				clan.setNotice("");

			player.sendPacket(Msg.NOTICE_HAS_BEEN_SAVED);
			onBypassCommand(player, "_announcepledgewriteform");
		}
	}

	public static void OnPlayerEnter(L2Player player)
	{
		L2Clan clan = player.getClan();
		if(clan == null || clan.getLevel() < 2)
			return;

		if(clan.getNotice() == null)
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			String notice = "";
			int type = 0;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type != 2");
				statement.setInt(1, clan.getClanId());
				rset = statement.executeQuery();
				if(rset.next())
				{
					notice = rset.getString("notice");
					type = rset.getInt("type");
				}
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			clan.setNotice(type == 1 ? notice.replace("\n", "<br1>\n") : "");
		}

		if(!clan.getNotice().isEmpty())
		{
			String html = Files.read("data/scripts/services/community/html/clan_popup.htm", player, false);
			html = html.replace("%pledge_name%", clan.getName());
			html = html.replace("%content%", clan.getNotice());

			player.sendPacket(new NpcHtmlMessage(0).setHtml(html));
		}
	}

	private static List<L2Clan> getClanList(String search, boolean byCL)
	{
		ArrayList<L2Clan> clanList = new ArrayList<L2Clan>();

		L2Clan[] clans = ClanTable.getInstance().getClans();
		Arrays.sort(clans);
		for(L2Clan clan : clans)
			if(clan.getLevel() > 1)
				clanList.add(clan);

		if(!search.isEmpty())
		{
			ArrayList<L2Clan> searchList = new ArrayList<L2Clan>();
			for(L2Clan clan : clanList)
				if(byCL && clan.getLeaderName().toLowerCase().contains(search.toLowerCase()))
					searchList.add(clan);
				else if(!byCL && clan.getName().toLowerCase().contains(search.toLowerCase()))
					searchList.add(clan);

			clanList = searchList;
		}

		return clanList;
	}
}
