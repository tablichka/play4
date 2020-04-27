package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.tables.ClanTable;
import ru.l2gw.util.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

/**
 * @author rage
 * @date 25.02.2010 17:20:40
 */
public class CommunityBoard implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");
	
	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: service loaded.");
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
		return new String[]{"_bbshome", "_bbslink", "_bbsmultisell_", "_langselect_", "_bbsscripts"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		String html = "";
		if("langselect".equals(cmd))
		{
			String[] b = bypass.split(";");
			if(b.length != 2)
				return;

			StringTokenizer p = new StringTokenizer(b[0], "_");
			p.nextToken();
			String val = p.nextToken();
			String page = b[1];
			player.setVar("lang@", val);
			player.setVar("selected_language@", "true");
			ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler(page);
			if(handler != null)
				handler.onBypassCommand(player, page);
			return;
		}
		else if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);
		}
		else if("bbshome".equals(cmd))
		{
			html = Files.read("data/scripts/services/community/html/bbs_top.htm", player, false);

			int favCount = 0;
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			StringBuilder fl = new StringBuilder("");
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT count(*) as cnt FROM `bbs_favorites` WHERE `object_id` = ?");
				statement.setInt(1, player.getObjectId());
				rset = statement.executeQuery();
				if(rset.next())
					favCount = rset.getInt("cnt");
			}
			catch(Exception e)
			{
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			html = html.replace("<?fav_count?>", String.valueOf(favCount));
			html = html.replace("<?clan_count?>", String.valueOf(ClanTable.getInstance().getClans().length));
			html = html.replace("<?market_count?>", String.valueOf(CommunityBoardManager.getInstance().getIntProperty("col_count")));
		}
		else if("bbslink".equals(cmd))
		{
			StringTokenizer p = new StringTokenizer(bypass, " ");
			p.nextToken();
			if(p.hasMoreTokens())
			{
				String path = p.nextToken().replace("../", "");
				path = path.replace("..\\", "");
				path = "data/scripts/services/community/html/" + path;
				html = Files.read(path, player, false);
				if(html == null)
				{
					_log.info("CommunityBoard: _bbslink_ file not found: " + path);
					return;
				}
			}
			else
			{
				onBypassCommand(player, "_bbslink_ bbs_link.htm");
				return;
			}
		}
		else if("bbsmultisell".equals(cmd))
		{
			StringTokenizer p = new StringTokenizer(bypass, " ");
			p.nextToken();
			int listId = Integer.parseInt(p.nextToken());
			player.setLastMultisellNpc(null);
			if(p.hasMoreTokens())
			{
				String bp = p.nextToken();
				if(bp.startsWith("&"))
				{
					bp = bp.replace("&", "_");
					ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler(bp);
					if(handler != null)
						handler.onBypassCommand(player, bp);
				}
				else
					onBypassCommand(player, "_bbslink_ " + bp);
			}
			L2Multisell.getInstance().SeparateAndSend(listId, player, 0);
			return;
		}
		else if("bbsscripts".equals(cmd))
		{
			String command = bypass.substring(12).trim();
			String[] word = command.split("\\s+");
			String[] args = command.substring(word[0].length()).trim().split("\\s+");
			String[] path = word[0].split(":");
			if(path.length != 2)
			{
				_log.warn("CommunityBoard: Bad Script bypass! " + bypass);
				return;
			}

			if(word.length == 1)
				player.callScripts(path[0], path[1], null, null);
			else
				player.callScripts(path[0], path[1], new Object[] { args }, null);

			return;
		}

		ShowBoard.separateAndSend(html, player);
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
