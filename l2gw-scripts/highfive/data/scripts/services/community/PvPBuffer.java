package services.community;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author rage
 * @date 03.06.2010 15:30:39
 */
public class PvPBuffer implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");
	private static final GArray<BuffInfo> _buffList = new GArray<BuffInfo>();
	private static final FastMap<Integer, FastMap<String, BuffSet>> _buffSets = new FastMap<Integer, FastMap<String, BuffSet>>().shared();
	private static final HashMap<Integer, BuffSet> autoBuffSets = new HashMap<Integer, BuffSet>();
	private static final int MAX_BUFF_SLOTS = 36; 
	private static final int MAX_BUFF_SETS = 3;
	private static final int MAX_BUFF_PREMIUM_SETS = 10;
	private static final int BUFF_TIME = 3600000;
	private static final int BUFF_PER_PAGE = 12;
	private static final int MIN_LEVEL_FOR_PAY = 70;
	private static AutoBufferThread _autoBuffer = null;
	private static final FastMap<String, BuffSet> _stdBuffSets = new FastMap<String, BuffSet>();

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED && Config.SERVICES_COMMUNITY_BUFFER)
		{
			_log.info("CommunityBoard: PvP Buffer service loaded.");
			CommunityBoardManager.getInstance().registerHandler(this);
			loadBuffList();
			loadStdBuffList();
			if(Config.SERVICES_COMMUNITY_BUFFER_AUTOBUFF && _autoBuffer == null)
				_autoBuffer = new AutoBufferThread();
		}
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			CommunityBoardManager.getInstance().unregisterHandler(this);
			if(_autoBuffer != null)
				_autoBuffer.interrupt();
		}
	}

	public void onShutdown()
	{
		if(_autoBuffer != null)
			_autoBuffer.interrupt();
	}

	public String[] getBypassCommands()
	{
		return new String[]{"_pvpbufflist", "_pvpcreateset", "_pvpdelset_", "_pvppetbuff_", "_pvppetbuff1_", "_pvpselfbuff_", "_pvpselfbuff1_", "_pvpeditset", "_pvpviewset_", "_pvpsaveset", "_pvpaddbuff_", "_pvpdelbuff_", "_pvpabself", "_pvpabpet", "_pvpabhelp"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		player.setSessionVar("add_fav", null);
		if("pvpbufflist".equals(cmd))
		{
			HashMap<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/pvp_bufflist.htm", player, false));
			String html = tpls.get(0);
			String tpl = tpls.get(2);
			StringBuilder sb = new StringBuilder("");

			if(_stdBuffSets.size() > 0)
				for(BuffSet bs : _stdBuffSets.values())
				{
					String set = tpl.replace("<?set_name?>", bs.name);
					sb.append(set.replace("<?set_price?>", String.format("%,3d", bs.getTotalCost(player.getLevel())).replace(" ",",")));
				}

			FastMap<String, BuffSet> buffSet = _buffSets.get(player.getObjectId());

			if(buffSet == null)
			{
				buffSet = getBuffSet(player);
				_buffSets.put(player.getObjectId(), buffSet);
			}

			tpl = tpls.get(1);

			for(String setName : buffSet.keySet())
			{
				String set = tpl.replace("<?set_name?>", setName);
				sb.append(set.replace("<?set_price?>", String.format("%,3d", buffSet.get(setName).getTotalCost(player.getLevel())).replace(" ",",")));
			}

			html = html.replace("<?SET_LIST?>", sb.toString());

			ShowBoard.separateAndSend(html, player);
		}
		else if("pvpviewset".equals(cmd))
		{
			String setName = st.nextToken();
			if(setName != null && !setName.isEmpty())
			{
				player.setSessionVar("editSet", setName);
				onBypassCommand(player, "_pvpeditset_1");
				return;
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpdelset".equals(cmd))
		{
			String setName = st.nextToken();
			if(setName != null && !setName.isEmpty())
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.remove(setName);
					if(bs != null)
						BuffSet.delete(bs, player.getObjectId());
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpeditset".equals(cmd))
		{
			String setName = player.getSessionVar("editSet");
			int page = Integer.parseInt(st.nextToken());

			if(setName != null && !setName.isEmpty())
			{
				player.setSessionVar("add_fav", bypass + "&Редактирование " + setName);

				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets == null)
				{
					playerSets = getBuffSet(player);
					_buffSets.put(player.getObjectId(), playerSets);
				}

				BuffSet bs = playerSets.get(setName);
				if(bs == null)
				{
					int maxSets = player.isPremiumEnabled() ? MAX_BUFF_PREMIUM_SETS : MAX_BUFF_SETS;
					if(playerSets.size() >= maxSets)
					{
						String html = Files.read("data/scripts/services/community/html/pvp_maxsets.htm", player, false);
						html = html.replace("<?max_sets?>", String.valueOf(maxSets));
						ShowBoard.separateAndSend(html, player);
						return;
					}
					bs = new BuffSet(setName);
					playerSets.put(setName, bs);
				}

				HashMap<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/pvp_editset.htm", player, false));
				String html = tpls.get(0);
				String tpl = tpls.get(1);
				StringBuilder sb = new StringBuilder("");

				int col = 0;
				for(BuffInfo bi : bs.buffList)
				{
					if(col == 0)
						sb.append("<tr>");
					
					String set = tpl.replace("<?icon?>", bi.icon);
					set = set.replace("<?buff_name?>", bi.name + " lv. " + bi.skillLvl);
					set = set.replace("<?buff_id?>", String.valueOf(bi.skillId));
					set = set.replace("<?buff_premium?>", bi.isPremium ? " <font color=LEVEL>*</font>" : "");
					sb.append("<td>").append(set.replace("<?buff_price?>", String.format("%,3d", bi.cost).replace(" ",","))).append("</td>");
					col++;
					if(col == 3)
					{
						col = 0;
						sb.append("</tr>");
					}
				}

				if(col != 0)
				{
					for(int i = col; col <= 3; col++)
						sb.append("<td></td>");
					sb.append("</tr>");
				}

				html = html.replace("<?SET_LIST?>", sb.toString());
				html = html.replace("<?set_cost?>", String.format("%,3d", bs.getTotalCost(player.getLevel())).replace(" ",","));
				html = html.replace("<?set_name?>", setName);

				sb = new StringBuilder("");
				col = 0;
				tpl = tpls.get(2);

				int start = (page - 1) * BUFF_PER_PAGE;
				int end = Math.min(page * BUFF_PER_PAGE, _buffList.size());

				if(page == 1)
				{
					html = html.replace("<?ACTION_GO_LEFT?>", "");
					html = html.replace("<?GO_LIST?>", "");
					html = html.replace("<?NPAGE?>", "1");
				}
				else
				{
					html = html.replace("<?ACTION_GO_LEFT?>", "bypass _pvpeditset_" + (page - 1));
					html = html.replace("<?NPAGE?>", String.valueOf(page));
					StringBuilder goList = new StringBuilder("");
					for(int i = page > 10 ? page - 10 : 1 ; i < page; i++)
						goList.append("<td><a action=\"bypass _pvpeditset_").append(i).append("\"> ").append(i).append(" </a> </td>\n\n");

					html = html.replace("<?GO_LIST?>", goList.toString());
				}

				int pages = Math.max(_buffList.size() / BUFF_PER_PAGE, 1);
				if(_buffList.size() > pages * BUFF_PER_PAGE)
					pages++;

				if(pages > page)
				{
					html = html.replace("<?ACTION_GO_RIGHT?>", "bypass _pvpeditset_" + (page + 1));
					int ep = Math.min(page + 10, pages);
					StringBuilder goList = new StringBuilder("");
					for(int i = page + 1; i <= ep; i++)
						goList.append("<td><a action=\"bypass _pvpeditset_").append(i).append("\"> ").append(i).append(" </a> </td>\n\n");

					html = html.replace("<?GO_LIST2?>", goList.toString());
				}
				else
				{
					html = html.replace("<?ACTION_GO_RIGHT?>", "");
					html = html.replace("<?GO_LIST2?>", "");
				}

				for(;start < end; start++)
				{
					BuffInfo bi = _buffList.get(start);
					if(col == 0)
						sb.append("<tr>");

					String set = tpl.replace("<?icon?>", bi.icon);
					set = set.replace("<?buff_name?>", bi.name + " lv. " + bi.skillLvl);
					set = set.replace("<?buff_id?>", String.valueOf(bi.skillId));
					set = set.replace("<?buff_premium?>", bi.isPremium ? " <font color=LEVEL>*</font>" : "");
					sb.append("<td>").append(set.replace("<?buff_price?>", String.format("%,3d", bi.cost).replace(" ",","))).append("</td>");
					col++;
					if(col == 3)
					{
						col = 0;
						sb.append("</tr>");
					}
				}

				if(col != 0)
				{
					for(; col <= 3; col++)
						sb.append("<td></td>");
					sb.append("</tr>");
				}

				html = html.replace("<?BUFF_LIST?>", sb.toString());
				html = html.replace("<?page?>", String.valueOf(page));
				if(Config.SERVICES_COMMUNITY_BUFFER_AUTOBUFF)
				{
					html = html.replace("<?AUTO_BUFF?>", tpls.get(3));
					html = html.replace("<?autobuff_on?>", autoBuffSets.containsKey(player.getObjectId()) && autoBuffSets.get(player.getObjectId()).name.equals(setName) ? "Выключить" : "Включить");
					html = html.replace("<?autobuffpet_on?>", player.getPet() != null && autoBuffSets.containsKey(player.getPet().getObjectId()) && autoBuffSets.get(player.getPet().getObjectId()).name.equals(setName) ? "Выключить на пета" : "Включить на пета");
				}
				else
					html = html.replace("<?AUTO_BUFF?>", "");

				ShowBoard.separateAndSend(html, player);
			}
			else
				onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpaddbuff".equals(cmd))
		{
			int skillId = Integer.parseInt(st.nextToken());
			BuffInfo bi = getBuffInfo(skillId);
			String setName = player.getSessionVar("editSet");
			if(bi != null && setName != null && !setName.isEmpty())
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.get(setName);
					if(bs != null)
					{
						bs.addBuff(bi);
						onBypassCommand(player, "_pvpeditset_" + st.nextToken());
						return;
					}
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpdelbuff".equals(cmd))
		{
			int skillId = Integer.parseInt(st.nextToken());
			BuffInfo bi = getBuffInfo(skillId);
			String setName = player.getSessionVar("editSet");
			if(bi != null && setName != null && !setName.isEmpty())
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.get(setName);
					if(bs != null)
					{
						bs.deleteBuff(skillId);
						onBypassCommand(player, "_pvpeditset_1");
						return;
					}
				}

			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpsaveset".equals(cmd))
		{
			String setName = player.getSessionVar("editSet");
			if(setName != null && !setName.isEmpty())
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.get(setName);
					if(bs != null)
						BuffSet.store(bs, player.getObjectId());
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpselfbuff".equals(cmd))
		{
			String setName = st.nextToken();
			if(setName != null && !setName.isEmpty())
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.get(setName);
					boolean premium = player.isPremiumEnabled();
					if(bs != null && checkBuffCondition(player) && bs.buffList.size() > 0 && (player.getLevel() < MIN_LEVEL_FOR_PAY || player.reduceAdena("PvPBuff", bs.getActualCost(premium, player.getLevel()), null, true)))
					{
						player.setMassUpdating(true);
						for(BuffInfo bi : bs.buffList)
							if(!bi.isPremium)
								bi.skill.applyEffects(player, player, false, BUFF_TIME);
							else if(bi.isPremium && premium)
								bi.skill.applyEffects(player, player, false, BUFF_TIME);
						player.setMassUpdating(false);
						player.updateEffectIcons();
						player.sendChanges();
					}
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpselfbuff1".equals(cmd))
		{
			String setName = st.nextToken();
			if(setName != null && !setName.isEmpty())
			{
				BuffSet bs = _stdBuffSets.get(setName);
				boolean premium = player.isPremiumEnabled();
				if(bs != null && checkBuffCondition(player) && bs.buffList.size() > 0 && (player.getLevel() < MIN_LEVEL_FOR_PAY || player.reduceAdena("PvPBuff", bs.getActualCost(premium, player.getLevel()), null, true)))
				{
					player.setMassUpdating(true);
					for(BuffInfo bi : bs.buffList)
						if(!bi.isPremium)
							bi.skill.applyEffects(player, player, false, BUFF_TIME);
						else if(bi.isPremium && premium)
							bi.skill.applyEffects(player, player, false, BUFF_TIME);
					player.setMassUpdating(false);
					player.updateEffectIcons();
					player.sendChanges();
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvppetbuff".equals(cmd))
		{
			String setName = st.nextToken();
			L2Summon pet = player.getPet();
			if(setName != null && !setName.isEmpty() && pet != null && player.isInRange(pet, 500))
			{
				FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
				if(playerSets != null)
				{
					BuffSet bs = playerSets.get(setName);
					boolean premium = player.isPremiumEnabled();
					if(bs != null && checkBuffCondition(player) && bs.buffList.size() > 0 && (player.getLevel() < MIN_LEVEL_FOR_PAY || player.reduceAdena("PvPBuffPet", bs.getActualCost(premium, player.getLevel()), null, true)))
					{
						pet.setMassUpdating(true);
						for(BuffInfo bi : bs.buffList)
							if(!bi.isPremium)
								bi.skill.applyEffects(pet, pet, false, BUFF_TIME);
							else if(bi.isPremium && premium)
								bi.skill.applyEffects(pet, pet, false, BUFF_TIME);
						pet.setMassUpdating(false);
						pet.updateEffectIcons();
						pet.sendChanges();
					}
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvppetbuff1".equals(cmd))
		{
			String setName = st.nextToken();
			L2Summon pet = player.getPet();
			if(setName != null && !setName.isEmpty() && pet != null && player.isInRange(pet, 500))
			{
				BuffSet bs = _stdBuffSets.get(setName);
				boolean premium = player.isPremiumEnabled();
				if(bs != null && checkBuffCondition(player) && bs.buffList.size() > 0 && (player.getLevel() < MIN_LEVEL_FOR_PAY || player.reduceAdena("PvPBuffPet", bs.getActualCost(premium, player.getLevel()), null, true)))
				{
					pet.setMassUpdating(true);
					for(BuffInfo bi : bs.buffList)
						if(!bi.isPremium)
							bi.skill.applyEffects(pet, pet, false, BUFF_TIME);
						else if(bi.isPremium && premium)
							bi.skill.applyEffects(pet, pet, false, BUFF_TIME);
					pet.setMassUpdating(false);
					pet.updateEffectIcons();
					pet.sendChanges();
				}
			}
			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpabself".equals(cmd) && Config.SERVICES_COMMUNITY_BUFFER_AUTOBUFF)
		{
			String setName = player.getSessionVar("editSet");
			if(setName != null && !setName.isEmpty())
			{
				boolean isOn = autoBuffSets.containsKey(player.getObjectId()) && autoBuffSets.get(player.getObjectId()).name.equals(setName);
				if(isOn)
				{
					BuffSet bs;
					synchronized(autoBuffSets)
					{
						bs = autoBuffSets.remove(player.getObjectId());
					}
					if(bs != null)
						player.sendMessage("Авто-бафф: выключен набор " + bs.name);
				}
				else
				{
					FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
					if(playerSets != null)
					{
						BuffSet bs = playerSets.get(setName);
						if(bs != null)
						{
							BuffSet b = autoBuffSets.remove(player.getObjectId());
							if(b != null)
								player.sendMessage("Авто-бафф: выключен набор " + b.name);

							synchronized(autoBuffSets)
							{
								autoBuffSets.put(player.getObjectId(), bs);
								player.sendMessage("Авто-бафф: включен набор " + bs.name);
							}
						}
					}
				}
				onBypassCommand(player, "_pvpeditset_1");
				return;
			}

			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpabpet".equals(cmd) && Config.SERVICES_COMMUNITY_BUFFER_AUTOBUFF)
		{
			String setName = player.getSessionVar("editSet");
			if(setName != null && !setName.isEmpty())
			{
				L2Summon pet = player.getPet();
				boolean isOn =  pet != null && autoBuffSets.containsKey(pet.getObjectId()) && autoBuffSets.get(pet.getObjectId()).name.equals(setName);

				if(isOn)
				{
					BuffSet bs;
					synchronized(autoBuffSets)
					{
						bs = autoBuffSets.remove(pet.getObjectId());
					}
					if(bs != null)
						player.sendMessage("Авто-бафф: выключен набор " + bs.name);
				}
				else if(pet != null)
				{
					FastMap<String, BuffSet> playerSets = _buffSets.get(player.getObjectId());
					if(playerSets != null)
					{
						BuffSet bs = playerSets.get(setName);
						if(bs != null)
						{
							BuffSet b = autoBuffSets.remove(pet.getObjectId());
							if(b != null)
								player.sendMessage("Авто-бафф: выключен набор " + b.name);

							synchronized(autoBuffSets)
							{
								autoBuffSets.put(pet.getObjectId(), bs);
							}
							player.sendMessage("Авто-бафф: включен набор " + bs.name);
						}
					}
				}
				onBypassCommand(player, "_pvpeditset_1");
				return;
			}

			onBypassCommand(player, "_pvpbufflist");
		}
		else if("pvpabhelp".equals(cmd))
		{
			String html = Files.read("data/scripts/services/community/html/pvp_abhelp.htm", player, false);
			html = html.replace("<?max_buffs?>", String.valueOf(MAX_BUFF_SLOTS));
			html = html.replace("<?max_sets?>", String.valueOf(MAX_BUFF_SETS));
			html = html.replace("<?max_premium_sets?>", String.valueOf(MAX_BUFF_PREMIUM_SETS));
			ShowBoard.separateAndSend(html, player);
		}
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		if("pvpcreateset".equals(cmd))
		{
			if(arg3 != null && !arg3.isEmpty())
			{
				arg3 = arg3.replace("<", "");
				arg3 = arg3.replace(">", "");
				arg3 = arg3.replace("&", "");
				arg3 = arg3.replace("$", "");
				arg3 = arg3.replace("\"", "");
				arg3 = arg3.replace("\'", "");
				arg3 = arg3.replace("_", "");
				arg3 = arg3.replace("#", "");
				arg3 = arg3.replace("@", "");
				arg3 = arg3.replace("%", "");
				arg3 = arg3.replace("^", "");
				arg3 = arg3.replace("!", "");
				arg3 = arg3.replace("*", "");
				arg3 = arg3.replace("(", "");
				arg3 = arg3.replace(")", "");
				arg3 = arg3.replace("=", "");
				arg3 = arg3.replace("+", "");
				arg3 = arg3.replace("\\", "");
				arg3 = arg3.replace("/", "");

				if(arg3.length() > 16)
					arg3 = arg3.substring(0, 15);

				if(!arg3.isEmpty())
				{
					player.setSessionVar("editSet", arg3);
					onBypassCommand(player, "_pvpeditset_1");
					return;
				}

			}
			onBypassCommand(player, "_pvpbufflist");
		}
	}

	private static FastMap<String, BuffSet> getBuffSet(L2Player player)
	{
		FastMap<String, BuffSet> buffSets = new FastMap<String, BuffSet>().shared();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT * FROM `pvp_buffsets` WHERE `obj_id` = ? ORDER BY set_name");
			statement.setInt(1, player.getObjectId());
			rset = statement.executeQuery();
			while(rset.next())
			{
				String setName = rset.getString("set_name");
				BuffSet buffSet = buffSets.get(setName);
				if(buffSet == null)
				{
					buffSet = new BuffSet(setName);
					buffSets.put(setName, buffSet);
				}

				String skills = rset.getString("skills");
				if(skills != null && !skills.isEmpty())
					for(String sklId : skills.split(";"))
						if(!sklId.isEmpty())
							buffSet.addBuff(getBuffInfo(Integer.parseInt(sklId)));
			}
		}
		catch(Exception e)
		{
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}
		return buffSets;
	}

	private static BuffInfo getBuffInfo(int skillId)
	{
		for(BuffInfo bi : _buffList)
			if(bi.skillId == skillId)
				return bi;

		return null;
	}

	private static void loadBuffList()
	{
		_buffList.clear();

		LineNumberReader lnr = null;
		try
		{
			File bufflist = new File("data/scripts/services/community/bufflist.csv");
			lnr = new LineNumberReader(new BufferedReader(new FileReader(bufflist)));

			String line;
			while((line = lnr.readLine()) != null)
			{
				if(line.trim().length() == 0 || line.startsWith("#"))
					continue;
				String[] params = line.split(";");
				try
				{
					_buffList.add(new BuffInfo(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Long.parseLong(params[2]), params[3], Boolean.parseBoolean(params[4])));
				}
				catch(Exception e)
				{
					_log.warn("PvpBuffer: parse error in line: " + lnr.getLineNumber() + " '" + line + "' " + e);
				}
			}

			_log.info("PvpBuffer: Loaded " + _buffList.size() + " buff skills.");
		}
		catch(FileNotFoundException e)
		{
			_log.info("PvpBuffer: bufflist.csv is missing.");
		}
		catch(Exception e)
		{
			_log.info("PvpBuffer: error while loading bufflist.csv: " + e.getMessage());
		}
		finally
		{
			try
			{
				if(lnr != null)
					lnr.close();
			}
			catch(Exception e1)
			{
			}
		}
	}

	private static void loadStdBuffList()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);

			File file = new File("data/scripts/services/community/pvpbuffer.xml");
			if(!file.exists())
			{
				_log.info("PvPBuffer: file pvpbuffer.xml not found, standard buff sets not loaded.");
				return;
			}

			Document doc = factory.newDocumentBuilder().parse(file);
			for(Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
				if("list".equalsIgnoreCase(n.getNodeName()))
					for(Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						if("buffset".equalsIgnoreCase(d.getNodeName()))
						{
							NamedNodeMap attrs = d.getAttributes();
							String name = attrs.getNamedItem("name").getNodeValue();
							BuffSet bs = new BuffSet(name);
							bs.price = attrs.getNamedItem("price") != null ? Long.parseLong(attrs.getNamedItem("price").getNodeValue()) : 0;
							for(Node s = d.getFirstChild(); s != null; s = s.getNextSibling())
								if("skill".equalsIgnoreCase(s.getNodeName()))
								{
									int id = Integer.parseInt(s.getAttributes().getNamedItem("id").getNodeValue());
									int level = Integer.parseInt(s.getAttributes().getNamedItem("level").getNodeValue());
									long bp = s.getAttributes().getNamedItem("price") != null ? Long.parseLong(s.getAttributes().getNamedItem("price").getNodeValue()) : 0;
									boolean premium = s.getAttributes().getNamedItem("premium") != null && Boolean.parseBoolean(s.getAttributes().getNamedItem("premium").getNodeValue());
									bs.addBuff(new BuffInfo(id, level, bp, "", premium));
								}
							_stdBuffSets.put(name, bs);
						}
			_log.info("PvPBuffer: loaded: " + _stdBuffSets.size() + " standard buff sets.");
		}
		catch(Exception e)
		{
			_log.warn("PvPBuffer: Error parsing pvpbuffer.xml. " + e);
			e.printStackTrace();
		}
	}

	private static boolean checkBuffCondition(L2Player player)
	{
		if(player.isInOlympiadMode())
		{
			player.sendMessage("Нельзя использовать бафф во время олимпиады.");
			return false;
		}
		if(player.isInCombat())
		{
			player.sendMessage("Нельзя использовать бафф во время боя.");
			return false;
		}

		return !player.inObserverMode() && !player.isAlikeDead();
	}

	private static class BuffSet
	{
		public final String name;
		public final ArrayList<BuffInfo> buffList;
		public long price = 0;

		public BuffSet(String _name)
		{
			name = _name;
			buffList = new ArrayList<BuffInfo>();
		}

		public void addBuff(BuffInfo bi)
		{
			if(bi != null && !buffList.contains(bi))
			{
				if(buffList.size() >= MAX_BUFF_SLOTS)
					buffList.remove(0);

				for(int i = 0; i < buffList.size(); i++)
					if(buffList.get(i).skill.getAbnormalTypes().size() > 0 && bi.skill.getAbnormalTypes().size() > 0)
						for(String abnormal : bi.skill.getAbnormalTypes())
							if(buffList.get(i).skill.getAbnormalTypes().contains(abnormal))
							{
								buffList.remove(i);
								break;
							}	

				buffList.add(bi);
			}
		}

		public long getTotalCost(int level)
		{
			if(level < MIN_LEVEL_FOR_PAY)
				return 0;
				
			if(price > 0)
				return price;

			long cost = 0;
			for(BuffInfo bi : buffList)
				cost += bi.cost;

			return cost;
		}

		public long getActualCost(boolean premium, int level)
		{
			if(level < MIN_LEVEL_FOR_PAY)
			    return 0;
			if(price > 0)
				return price;
			long cost = 0;
			for(BuffInfo bi : buffList)
				if(!bi.isPremium)
					cost += bi.cost;
				else if(bi.isPremium && premium)
					cost += bi.cost;

			return cost;
		}

		public void deleteBuff(int skillId)
		{
			int i = 0;
			for(;i < buffList.size();i++)
				if(buffList.get(i).skillId == skillId)
					break;
			if(i < buffList.size())
				buffList.remove(i);
		}

		public static void store(BuffSet bs, int objectId)
		{
			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("REPLACE INTO pvp_buffsets VALUES(?, ?, ?)");
				statement.setInt(1, objectId);
				statement.setString(2, bs.name);
				String skills = "";
				for(BuffInfo bi : bs.buffList)
					skills += bi.skillId + ";";
				statement.setString(3, skills);

				statement.execute();
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}
		}

		public static void delete(BuffSet bs, int objectId)
		{
			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("DELETE FROM pvp_buffsets WHERE obj_id = ? AND set_name = ?");
				statement.setInt(1, objectId);
				statement.setString(2, bs.name);
				statement.execute();
			}
			catch(Exception e)
			{}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}
		}
	}

	private static class BuffInfo
	{
		public final int skillId;
		public final int skillLvl;
		public final long cost;
		public final String icon;
		public final boolean isPremium;
		public final String name;
		public final L2Skill skill;

		public BuffInfo(int _skillId, int _skillLvl, long _cost, String _icon, boolean _premium)
		{
			skillId = _skillId;
			skillLvl = _skillLvl;
			cost = _cost;
			icon = _icon.isEmpty() ? String.format("icon.skill%04d", skillId) : _icon;
			isPremium = _premium;
			skill = SkillTable.getInstance().getInfo(skillId, skillLvl);
			name = skill.getName();
		}
	}

	public static class AutoBufferThread extends Thread
	{
		private static final int sleepTime = 1500;
		public AutoBufferThread()
		{
			super("AutoBuffer");
			setDaemon(true);
			setPriority(Thread.MIN_PRIORITY);
			start();
		}

		/**
		 * Возвращает true если эффект для скилла уже есть и заново накладывать не надо
		 */
		private boolean checkEffect(L2Effect ef, L2Skill skill)
		{
			if(ef == null || ef.getSkill() == null || ef.getSkill().getId() != skill.getId())
				return false;
			if(ef.getTimeLeft() > sleepTime) // старый еще не кончается - ждем
				return true;
			if(ef.getNext() != null) // старый уже кончается - проверить рекурсией что там зашедулено
				return checkEffect(ef.getNext(), skill);
			return false;
		}

		@Override
		public void run()
		{
			L2Character buffTarget = null;
			L2Player buffRequestor = null;
			BuffSet autoSet = null;
			L2Effect[] targetEffects = null;
			FastList<BuffInfo> buffs_to_buff = new FastList<BuffInfo>();

			for(;;)
			{
				try
				{
					sleep(sleepTime);
				}
				catch(InterruptedException e)
				{
					return;
				}
				if(autoBuffSets.size() == 0)
					continue;

				Integer[] charObjectIds;
				synchronized (autoBuffSets)
				{
					charObjectIds = autoBuffSets.keySet().toArray(new Integer[autoBuffSets.keySet().size()]);
				}
				for(int objectId : charObjectIds)
				{
					L2Object object = L2ObjectsStorage.findObject(objectId);
					buffTarget = object instanceof L2Character ? (L2Character) object : null;

					if(buffTarget != null)
					{
						if(buffTarget.isPlayer())
							buffRequestor = (L2Player) buffTarget;
						else if(buffTarget.isPet() || buffTarget.isSummon())
							buffRequestor = buffTarget.getPlayer();
						else
							buffRequestor = null;
					}
					if(buffTarget == null || buffRequestor == null)
					{
						synchronized (autoBuffSets)
						{
							autoBuffSets.remove(objectId);
						}
						continue;
					}

					if(buffRequestor.isInOlympiadMode())
					{
						synchronized (autoBuffSets)
						{
							autoBuffSets.remove(objectId);
						}
						buffRequestor.sendMessage("Авто-бафф: выключен, причина: Олимпиада");
						continue;
					}

					if(buffTarget.isInCombat() || buffTarget.isDead())
						continue;

					autoSet = autoBuffSets.get(objectId);

					if(autoSet == null)
						continue;

					if(autoSet.buffList.size() == 0)
					{
						synchronized (autoBuffSets)
						{
							autoBuffSets.remove(objectId);
						}
						buffRequestor.sendMessage("Авто-бафф: выключен, причина: Набор пуст");
						continue;
					}

					targetEffects = buffTarget.getAllEffects().toArray(new L2Effect[buffTarget.getAllEffects().size()]);
					buffs_to_buff.clear();
					boolean premium = buffRequestor.isPremiumEnabled();

					for(BuffInfo autoBuff : autoSet.buffList)
					{
						if(autoBuff.isPremium && !premium)
							continue;

						boolean needed = true;
						for(L2Effect effect : targetEffects)
							if(checkEffect(effect, autoBuff.skill))
							{
								needed = false;
								break;
							}
						if(needed)
							buffs_to_buff.add(autoBuff);
					}

					if(buffs_to_buff.size() > 0)
					{
						if(!takeAdenaAndBuff(buffRequestor, buffs_to_buff, !buffTarget.isPlayer()))
						{
							synchronized (autoBuffSets)
							{
								autoBuffSets.remove(objectId);
							}
							buffRequestor.sendMessage("Авто-бафф: выключен, причина: Недостаток адены");
						}
					}
				}
			}
		}
	}

	private static boolean takeAdenaAndBuff(L2Player player, FastList<BuffInfo> buffs, boolean toPet)
	{
		long need_adena = 0;
		for(BuffInfo bi : buffs)
			need_adena += bi.cost;

		if(player.getLevel() >= MIN_LEVEL_FOR_PAY && !player.reduceAdena("AutoBuff", need_adena, null, false))
			return false;

		L2Playable target = toPet ? player.getPet() : player;
		if(target != null)
		{
			target.setMassUpdating(true);
			for(BuffInfo nextbuff : buffs)
				nextbuff.skill.applyEffects(target, target, false, BUFF_TIME);

			player.setMassUpdating(false);
			player.updateEffectIcons();
		}
		return true;
	}
}
