package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.clientpackets.RequestFriendDel;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.util.Files;
import ru.l2gw.commons.arrays.GArray;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author rage
 * @date 21.05.2010 10:12:05
 */
public class ManageFriends implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Manage Friends service loaded.");
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
		return new String[]{"_friendlist_", "_friendblocklist_", "_frienddelete_", "_frienddeleteallconfirm_", "_frienddeleteall_", "_friendblockdelete_", "_friendblockadd_", "_friendblockdeleteallconfirm_", "_friendblockdeleteall_"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		String html = Files.read(cmd.startsWith("friendbloc") ? "data/scripts/services/community/html/bbs_block_list.htm" : "data/scripts/services/community/html/bbs_friend_list.htm", player, false);
		player.setSessionVar("add_fav", null);
		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);
		}
		else if(cmd.equals("friendlist"))
		{
			String act = st.nextToken();

			html = html.replace("%friend_list%", getFriendList(player));

			if(act.equals("0"))
			{
				if(player.getSessionVar("selFriends") != null)
					player.setSessionVar("selFriends", null);

				html = html.replace("%selected_friend_list%", "");
				html = html.replace("%delete_all_msg%", "");
			}
			else if(act.equals("1"))
			{
				String objId = st.nextToken();
				String selected;
				if((selected = player.getSessionVar("selFriends")) == null)
					selected = objId + ";";
				else if(!selected.contains(objId))
					selected += objId + ";";

				player.setSessionVar("selFriends", selected);

				html = html.replace("%selected_friend_list%", getSelectedList(player));
				html = html.replace("%delete_all_msg%", "");
			}
			else if(act.equals("2"))
			{
				String objId = st.nextToken();
				String selected = player.getSessionVar("selFriends");
				if(selected != null)
				{
					selected = selected.replace(objId + ";", "");
					player.setSessionVar("selFriends", selected);
				}
				html = html.replace("%selected_friend_list%", getSelectedList(player));
				html = html.replace("%delete_all_msg%", "");
			}
		}
		else if(cmd.equals("frienddeleteallconfirm"))
		{
			html = html.replace("%friend_list%", getFriendList(player));
			html = html.replace("%selected_friend_list%", getSelectedList(player));
			html = html.replace("%delete_all_msg%", "<br>\nAre you sure you want to delete all friends from the friends list? <button value = \"OK\" action=\"bypass -h _frienddeleteall_\" back=\"l2ui_ct1.button.button_df_small_down\" width=70 height=25 fore=\"l2ui_ct1.button.button_df_small\">");
		}
		else if(cmd.equals("frienddelete"))
		{
			String selected = player.getSessionVar("selFriends");
			if(selected != null)
			{
				for(String objId : selected.split(";"))
					if(!objId.isEmpty())
						RequestFriendDel.TryFriendDelete(player, player.getFriendList().getList().get(Integer.parseInt(objId)));
			}
			player.setSessionVar("selFriends", null);

			html = html.replace("%friend_list%", getFriendList(player));
			html = html.replace("%selected_friend_list%", "");
			html = html.replace("%delete_all_msg%", "");
		}
		else if(cmd.equals("frienddeleteall"))
		{
			GArray<String> friends = new GArray<String>(1);
			friends.addAll(player.getFriendList().getList().values());
			for(String name : friends)
				RequestFriendDel.TryFriendDelete(player, name);

			player.setSessionVar("selFriends", null);

			html = html.replace("%friend_list%", "");
			html = html.replace("%selected_friend_list%", "");
			html = html.replace("%delete_all_msg%", "");
		}
		else if(cmd.equals("friendblocklist"))
		{
			html = html.replace("%block_list%", getBlockList(player));
			html = html.replace("%delete_all_msg%", "");
		}
		else if(cmd.equals("friendblockdeleteallconfirm"))
		{
			html = html.replace("%block_list%", getBlockList(player));
			html = html.replace("%delete_all_msg%", "<br>\nDo you want to delete all characters from the block list? <button value = \"OK\" action=\"bypass -h _friendblockdeleteall_\" back=\"l2ui_ct1.button.button_df_small_down\" width=70 height=25 fore=\"l2ui_ct1.button.button_df_small\" >");
		}
		else if(cmd.equals("friendblockdelete"))
		{
			String objId = st.nextToken();
			if(objId != null && !objId.isEmpty())
			{
				int objectId = Integer.parseInt(objId);
				String name = player.getBlockListMap().get(objectId);
				if(name != null)
					player.removeFromBlockList(name);
			}
			html = html.replace("%block_list%", getBlockList(player));
			html = html.replace("%delete_all_msg%", "");
		}
		else if(cmd.equals("friendblockdeleteall"))
		{
			GArray<String> bl = new GArray<String>(1);
			bl.addAll(player.getBlockList());
			for(String name : bl)
				player.removeFromBlockList(name);

			html = html.replace("%block_list%", "");
			html = html.replace("%delete_all_msg%", "");
		}

		ShowBoard.separateAndSend(html, player);
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		String html = Files.read("data/scripts/services/community/html/bbs_block_list.htm", player, false);

		if("_friendblockadd_".equals(bypass) && arg3 != null && !arg3.isEmpty())
			player.addToBlockList(arg3);

		html = html.replace("%block_list%", getBlockList(player));
		html = html.replace("%delete_all_msg%", "");
		ShowBoard.separateAndSend(html, player);
	}

	private static String getFriendList(L2Player player)
	{
		StringBuilder friendList = new StringBuilder("");
		Map<Integer, String> fl = player.getFriendList().getList();
		for(int objectId : fl.keySet())
			friendList.append("<a action=\"bypass -h _friendlist_1_").append(objectId).append("\">").append(fl.get(objectId)).append("</a> (").append((L2ObjectsStorage.getPlayer(objectId) == null ? "off" : "on")).append(") &nbsp;");

		return friendList.toString();
	}

	private static String getSelectedList(L2Player player)
	{
		String selected = player.getSessionVar("selFriends");

		if(selected == null)
			return "";

		String[] sels = selected.split(";");
		StringBuilder selectedList = new StringBuilder("");
		for(String objectId : sels)
			if(!objectId.isEmpty())
				selectedList.append("<a action=\"bypass -h _friendlist_2_").append(objectId).append("\">").append(player.getFriendList().getList().get(Integer.parseInt(objectId))).append("</a>;");

		return selectedList.toString();
	}

	private static String getBlockList(L2Player player)
	{
		StringBuilder blockList = new StringBuilder("");
		Map<Integer, String> bl = player.getBlockListMap();
		for(Integer objectId : bl.keySet())
			blockList.append(bl.get(objectId)).append("&nbsp; <a action=\"bypass -h _friendblockdelete_").append(objectId).append("\">Delete</a>&nbsp;&nbsp;");

		return blockList.toString();
	}
}
