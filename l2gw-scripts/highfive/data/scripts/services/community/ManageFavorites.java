package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.util.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author: rage
 * @date: 23.05.2010 23:20:20
 */
public class ManageFavorites implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Manage Favorites service loaded.");
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
		return new String[]{"_bbsgetfav", "_bbsaddfav_List", "_bbsdelfav_"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();

		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			String html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsgetfav".equals(cmd))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			StringBuilder fl = new StringBuilder("");
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_favorites` WHERE `object_id` = ? ORDER BY `add_date` DESC");
				statement.setInt(1, player.getObjectId());
				rset = statement.executeQuery();
				String tpl = Files.read("data/scripts/services/community/html/bbs_favoritetpl.htm", player, false);
				while(rset.next())
				{
					String fav = tpl.replace("%fav_title%", rset.getString("fav_title"));
					fav = fav.replace("%fav_bypass%", rset.getString("fav_bypass"));
					fav = fav.replace("%add_date%", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date(rset.getInt("add_date")  * 1000L)));
					fav = fav.replace("%fav_id%", String.valueOf(rset.getInt("fav_id")));
					fl.append(fav);
				}
			}
			catch(Exception e)
			{
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			String html = Files.read("data/scripts/services/community/html/bbs_getfavorite.htm", player, false);
			html = html.replace("%FAV_LIST%", fl.toString());

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsaddfav".equals(cmd))
		{
			String fav = player.getSessionVar("add_fav");
			player.setSessionVar("add_fav", null);
			if(fav != null)
			{
				String favs[] = fav.split("&");
				if(favs.length > 1)
				{
					Connection con = null;
					PreparedStatement statement = null;
					try
					{
						con = DatabaseFactory.getInstance().getConnection();
						statement = con.prepareStatement("REPLACE INTO `bbs_favorites`(`object_id`, `fav_bypass`, `fav_title`, `add_date`) VALUES(?, ?, ?, ?)");
						statement.setInt(1, player.getObjectId());
						statement.setString(2, favs[0]);
						statement.setString(3, favs[1]);
						statement.setInt(4, (int)(System.currentTimeMillis() / 1000));
						statement.execute();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						DbUtils.closeQuietly(con, statement);
					}
				}
			}
			onBypassCommand(player, "_bbsgetfav");
		}
		else if("bbsdelfav".equals(cmd))
		{
			int fav_id = Integer.parseInt(st.nextToken());

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("DELETE FROM `bbs_favorites` WHERE `fav_id` = ? and `object_id` = ?");
				statement.setInt(1, fav_id);
				statement.setInt(2, player.getObjectId());
				statement.execute();
			}
			catch(Exception e)
			{
			}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}
			onBypassCommand(player, "_bbsgetfav");
		}
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
