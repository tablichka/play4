package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author: rage
 * @date: 29.05.2010 14:23:17
 */
public class ManageColors implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Manage Colors service loaded.");
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
		return new String[]{"_bbscolor_", "_bbsch_"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		int type = Integer.parseInt(st.nextToken());

		HashMap<Integer, String> tpls;
		String html, tpl;

		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);
		}
		else if("bbscolor".equals(cmd))
		{
			int page = 1;
			if(st.hasMoreTokens())
				try
				{
					page = Integer.parseInt(st.nextToken());
				}
				catch(Exception e)
				{}

			tpls = ru.l2gw.util.Util.parseTemplate(Files.read("data/scripts/services/community/html/bbs_ncolor.htm", player, false));
			html = tpls.get(0);
			tpl = tpls.get(1);
			StringBuilder sb = new StringBuilder("");
			int nCol = 0;
			String[] list = type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_LIST : Config.SERVICES_CHANGE_TITLE_COLOR_LIST;

			int start = (page - 1) * Config.SERVICES_CHANGE_NICK_COLOR_PER_PAGE;
			int end = Math.min(page * Config.SERVICES_CHANGE_NICK_COLOR_PER_PAGE, list.length);

			int pages = Math.max(list.length / Config.SERVICES_CHANGE_NICK_COLOR_PER_PAGE, 1);
			if(list.length > pages * Config.SERVICES_CHANGE_NICK_COLOR_PER_PAGE)
				pages++;

			StringBuilder links = new StringBuilder();
			if(pages > 0)
			{
				for(int i = 1; i <= pages; i++)
					links.append(tpls.get(i == page ? 3 : 2).replace("<?pn?>", String.valueOf(i)).replace("<?type?>", String.valueOf(type)));
			}

			for(int i = start; i < end; i++)
			{
				if(nCol == 0)
					sb.append("<tr>");

				String t = tpl.replace("<?ch_bypass?>", "_bbsch_" + type + "_" + i);
				t = t.replace("<?rgb?>", list[i]);
				t = t.replace("<?name?>", type == 0 ? player.getName() : player.getTitle().isEmpty() ? "no title" : player.getTitle().replace("&", "").replace("$", "").replace("<", "&lt;").replace(">", "&gt;"));
				sb.append(t);
				nCol++;
				if(nCol == 5)
				{
					sb.append("</tr>");
					nCol = 0;
				}
			}

			if(nCol != 0)
			{
				for(nCol += 1; nCol <= 5; nCol++)
					sb.append("<td></td>\n");
				sb.append("</tr>\n");
			}

			html = html.replace("<?color_list?>", sb.toString());
			html = html.replace("<?type?>", String.valueOf(type));
			html = html.replace("<?change_cost?>", String.valueOf(type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_PRICE : Config.SERVICES_CHANGE_TITLE_COLOR_PRICE));
			html = html.replace("<?change_item?>", ItemTable.getInstance().getTemplate(type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_ITEM : Config.SERVICES_CHANGE_TITLE_COLOR_ITEM).getName());
			html = html.replace("<?curr_color?>", Util.int2rgb(type == 0 ? player.getNameColor() : player.getTitleColor()));
			html = html.replace("<?page_links?>", pages > 1 ? links : "");
			html = html.replace("<?name?>", type == 0 ? player.getName() : player.getTitle().isEmpty() ? "no title" : player.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
			ShowBoard.separateAndSend(html, player);
			return;
		}
		else if("bbsch".equals(cmd))
		{
			int colorId = Integer.parseInt(st.nextToken());
			if(colorId < 0)
			{
				if(type == 0)
					player.setNameColor(Util.rgb2int("FFFFFF"));
				else
				{
					player.setTitleColor(Util.rgb2int("77FFFF"));
					player.unsetVar("titlecolor");
				}
				player.broadcastUserInfo(true);
				CommunityBoardManager.getInstance().getCommunityHandler("_bbsaccount").onBypassCommand(player, "_bbsaccount");
				return;
			}

			String[] list = type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_LIST : Config.SERVICES_CHANGE_TITLE_COLOR_LIST;

			if(colorId < list.length)
			{
				int price = type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_PRICE : Config.SERVICES_CHANGE_TITLE_COLOR_PRICE;
				int itemId = type == 0 ? Config.SERVICES_CHANGE_NICK_COLOR_ITEM : Config.SERVICES_CHANGE_TITLE_COLOR_ITEM;
				int currColor = type == 0 ? player.getNameColor() : player.getTitleColor();

				if(Config.PREMIUM_FREE_COLOR_CHANGE && player.isPremiumEnabled() && currColor != (type == 0 ? Util.rgb2int("FFFFFF") : Util.rgb2int("77FFFF")) ||
					player.destroyItemByItemId(type == 0 ? "ChangeNickColor" : "ChangeTitleColor", itemId, price, null, true))
				{
					if(type == 0)
						player.setNameColor(Util.rgb2int(list[colorId]));
					else
					{
						player.setTitleColor(Util.rgb2int(list[colorId]));
						player.setVar("titlecolor", Integer.toHexString(player.getTitleColor()));
					}
				}
			}

			player.broadcastUserInfo(true);
		}
		CommunityBoardManager.getInstance().getCommunityHandler("_bbsaccount").onBypassCommand(player, "_bbsaccount");
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
