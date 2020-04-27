package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.playerSubOrders.UserVar;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author rage
 * @date 14.04.11 13:07
 */
public class ManageRates extends Functions implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED && Config.ALT_FLOATING_RATE_ENABLE)
		{
			_log.info("CommunityBoard: Manage Floating Rate service loaded.");
			CommunityBoardManager.getInstance().registerHandler(this);
		}
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED && Config.ALT_FLOATING_RATE_ENABLE)
			CommunityBoardManager.getInstance().unregisterHandler(this);
	}

	public void onShutdown()
	{
	}

	public String[] getBypassCommands()
	{
		return new String[]{"_bbsmr", "_bbsmrapl", "_bbsmrup_", "_bbsmrdn_", "_bbsmrbuye"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		HashMap<Integer, String> tpls;
		String html, tpl;

		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);
		}
		else if("bbsmr".equals(cmd))
		{
			tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/bbs_managerate.htm", player, false));
			html = tpls.get(0);

			html = html.replace("<?rate_expsp?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + player.getFloatingRate().pointEXPSP));
			html = html.replace("<?rate_adena?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + player.getFloatingRate().pointADENA));
			html = html.replace("<?rate_drop?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + player.getFloatingRate().pointDROP));
			html = html.replace("<?rate_spoil?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + player.getFloatingRate().pointSPOIL));

			int nexp, nadena, ndrop, nspoil;
			String temp = player.getSessionVar("nrate_expsp");
			if(temp == null)
				nexp = player.getFloatingRate().pointEXPSP;
			else
				nexp = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_adena");
			if(temp == null)
				nadena = player.getFloatingRate().pointADENA;
			else
				nadena = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_drop");
			if(temp == null)
				ndrop = player.getFloatingRate().pointDROP;
			else
				ndrop = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_spoil");
			if(temp == null)
				nspoil = player.getFloatingRate().pointSPOIL;
			else
				nspoil = Integer.parseInt(temp);

			html = html.replace("<?points?>", String.valueOf(Config.ALT_FLOATING_RATE_POINTS + player.getVarInt("fr_extra") - (nexp + nadena + ndrop + nspoil)));
			html = html.replace("<?fr_extra?>", String.valueOf(player.getVarInt("fr_extra")));
			UserVar ext = player.getUserVars().get("fr_extra");
			if(ext != null)
				html = html.replace("<?expire?>", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date(ext.expire)));
			else
				html = html.replace("<?expire?>", "");

			html = html.replace("<?extra_points?>", String.valueOf(Config.ALT_FLOATING_RATE_EXTRA_LIMIT - player.getVarInt("fr_extra")));
			if(Config.ALT_FLOATING_RATE_EXTRA_LIMIT - player.getVarInt("fr_extra") > 0)
			{
				tpl = tpls.get(4);
				tpl = tpl.replace("<?extra_count?>", String.valueOf(Config.ALT_FLOATING_RATE_EXTRA_SELL_COUNT));
				tpl = tpl.replace("<?ex_item_count?>", String.valueOf((long)(Config.ALT_FLOATING_RATE_EXTRA_ITEM_COUNT * (player.getVarInt("fr_extra") + 1) * Config.ALT_FLOATING_RATE_EXTRA_MUL)));
				tpl = tpl.replace("<?ex_item_name?>", ItemTable.getInstance().getTemplate(Config.ALT_FLOATING_RATE_EXTRA_ITEM_ID).getName());
				html = html.replace("<?extra_buy?>", tpl);
			}
			else
				html = html.replace("<?extra_buy?>", "");

			html = html.replace("<?nrate_expsp?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + nexp));
			html = html.replace("<?nrate_adena?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + nadena));
			html = html.replace("<?nrate_drop?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + ndrop));
			html = html.replace("<?nrate_spoil?>", String.valueOf(Config.ALT_FLOATING_RATE_MIN + nspoil));

			if(player.getVarB("fr_time"))
			{
				UserVar uv = player.getUserVars().get("fr_time");
				html = html.replace("<?time?>", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date(uv.expire)));
				tpl = tpls.get(3);
				tpl = tpl.replace("<?item_name?>", ItemTable.getInstance().getTemplate(Config.ALT_FLOATING_RATE_ITEM_ID).getName());
				tpl = tpl.replace("<?item_count?>", String.valueOf(Config.ALT_FLOATING_RATE_ITEM_COUNT));
				html = html.replace("<?change_button?>", tpl);
			}
			else
			{
				html = html.replace("<?time?>", tpls.get(1));
				html = html.replace("<?change_button?>", tpls.get(2));
			}

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsmrup".equals(cmd))
		{
			int type = Integer.parseInt(st.nextToken());

			int nexp, nadena, ndrop, nspoil;
			String temp = player.getSessionVar("nrate_expsp");
			if(temp == null)
				nexp = player.getFloatingRate().pointEXPSP;
			else
				nexp = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_adena");
			if(temp == null)
				nadena = player.getFloatingRate().pointADENA;
			else
				nadena = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_drop");
			if(temp == null)
				ndrop = player.getFloatingRate().pointDROP;
			else
				ndrop = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_spoil");
			if(temp == null)
				nspoil = player.getFloatingRate().pointSPOIL;
			else
				nspoil = Integer.parseInt(temp);

			int total = nexp + nadena + ndrop + nspoil + 1;

			if(type == 1)
			{
				nexp++;
				if(total <= Config.ALT_FLOATING_RATE_POINTS + player.getVarInt("fr_extra") && nexp + Config.ALT_FLOATING_RATE_MIN <= Config.ALT_FLOATING_RATE_MAX)
					player.setSessionVar("nrate_expsp", String.valueOf(nexp));
			}
			else if(type == 2)
			{
				nadena++;
				if(total <= Config.ALT_FLOATING_RATE_POINTS + player.getVarInt("fr_extra") && nadena + Config.ALT_FLOATING_RATE_MIN <= Config.ALT_FLOATING_RATE_MAX)
					player.setSessionVar("nrate_adena", String.valueOf(nadena));
			}
			else if(type == 3)
			{
				ndrop++;
				if(total <= Config.ALT_FLOATING_RATE_POINTS + player.getVarInt("fr_extra") && ndrop + Config.ALT_FLOATING_RATE_MIN <= Config.ALT_FLOATING_RATE_MAX)
					player.setSessionVar("nrate_drop", String.valueOf(ndrop));
			}
			else if(type == 4)
			{
				nspoil++;
				if(total <= Config.ALT_FLOATING_RATE_POINTS + player.getVarInt("fr_extra") && nspoil + Config.ALT_FLOATING_RATE_MIN <= Config.ALT_FLOATING_RATE_MAX)
					player.setSessionVar("nrate_spoil", String.valueOf(nspoil));
			}

			onBypassCommand(player, "_bbsmr");
		}
		else if("bbsmrdn".equals(cmd))
		{
			int type = Integer.parseInt(st.nextToken());
			int nexp, nadena, ndrop, nspoil;
			String temp = player.getSessionVar("nrate_expsp");
			if(temp == null)
				nexp = player.getFloatingRate().pointEXPSP;
			else
				nexp = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_adena");
			if(temp == null)
				nadena = player.getFloatingRate().pointADENA;
			else
				nadena = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_drop");
			if(temp == null)
				ndrop = player.getFloatingRate().pointDROP;
			else
				ndrop = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_spoil");
			if(temp == null)
				nspoil = player.getFloatingRate().pointSPOIL;
			else
				nspoil = Integer.parseInt(temp);

			if(type == 1)
			{
				nexp--;
				if(nexp >= 0)
					player.setSessionVar("nrate_expsp", String.valueOf(nexp));
			}
			else if(type == 2)
			{
				nadena--;
				if(nadena >= 0)
					player.setSessionVar("nrate_adena", String.valueOf(nadena));
			}
			else if(type == 3)
			{
				ndrop--;
				if(ndrop >= 0)
					player.setSessionVar("nrate_drop", String.valueOf(ndrop));
			}
			else if(type == 4)
			{
				nspoil--;
				if(nspoil >= 0)
					player.setSessionVar("nrate_spoil", String.valueOf(nspoil));
			}

			onBypassCommand(player, "_bbsmr");
		}
		else if("bbsmrapl".equals(cmd))
		{
			if(player.getVarB("fr_time"))
			{
				onBypassCommand(player, "_bbsmr");
				return;
			}

			int nexp, nadena, ndrop, nspoil;
			String temp = player.getSessionVar("nrate_expsp");
			if(temp == null)
				nexp = player.getFloatingRate().pointEXPSP;
			else
				nexp = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_adena");
			if(temp == null)
				nadena = player.getFloatingRate().pointADENA;
			else
				nadena = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_drop");
			if(temp == null)
				ndrop = player.getFloatingRate().pointDROP;
			else
				ndrop = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_spoil");
			if(temp == null)
				nspoil = player.getFloatingRate().pointSPOIL;
			else
				nspoil = Integer.parseInt(temp);

			if((nexp != player.getFloatingRate().pointEXPSP || nadena != player.getFloatingRate().pointADENA || ndrop != player.getFloatingRate().pointDROP || nspoil != player.getFloatingRate().pointSPOIL) && nexp + nadena + ndrop + nspoil <= Config.ALT_FLOATING_RATE_POINTS + player.getFloatingRate().extraPoints)
			{
				player.setVar("fr_time", "1", (int) (System.currentTimeMillis() / 1000 + Config.ALT_FLOATING_RATE_NEXT_CHANGE));
				player.getFloatingRate().pointEXPSP = 0;
				player.getFloatingRate().pointADENA = 0;
				player.getFloatingRate().pointDROP = 0;
				player.getFloatingRate().pointSPOIL = 0;
				player.getFloatingRate().setPointExpSp(nexp);
				player.getFloatingRate().setPointAdena(nadena);
				player.getFloatingRate().setPointDrop(ndrop);
				player.getFloatingRate().setPointSpoil(nspoil);
			}

			onBypassCommand(player, "_bbsmr");
		}
		else if("bbsmrapl1".equals(cmd))
		{
			int nexp, nadena, ndrop, nspoil;
			String temp = player.getSessionVar("nrate_expsp");
			if(temp == null)
				nexp = player.getFloatingRate().pointEXPSP;
			else
				nexp = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_adena");
			if(temp == null)
				nadena = player.getFloatingRate().pointADENA;
			else
				nadena = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_drop");
			if(temp == null)
				ndrop = player.getFloatingRate().pointDROP;
			else
				ndrop = Integer.parseInt(temp);

			temp = player.getSessionVar("nrate_spoil");
			if(temp == null)
				nspoil = player.getFloatingRate().pointSPOIL;
			else
				nspoil = Integer.parseInt(temp);

			if((nexp != player.getFloatingRate().pointEXPSP || nadena != player.getFloatingRate().pointADENA || ndrop != player.getFloatingRate().pointDROP || nspoil != player.getFloatingRate().pointSPOIL) && nexp + nadena + ndrop + nspoil <= Config.ALT_FLOATING_RATE_POINTS + player.getFloatingRate().extraPoints && player.destroyItemByItemId("ChangeRate", Config.ALT_FLOATING_RATE_ITEM_ID, Config.ALT_FLOATING_RATE_ITEM_COUNT, null, true))
			{
				player.getFloatingRate().pointEXPSP = 0;
				player.getFloatingRate().pointADENA = 0;
				player.getFloatingRate().pointDROP = 0;
				player.getFloatingRate().pointSPOIL = 0;
				player.getFloatingRate().setPointExpSp(nexp);
				player.getFloatingRate().setPointAdena(nadena);
				player.getFloatingRate().setPointDrop(ndrop);
				player.getFloatingRate().setPointSpoil(nspoil);
			}
			onBypassCommand(player, "_bbsmr");
		}
		else if("bbsmrbuye".equals(cmd))
		{
			if(player.getVarInt("fr_extra") < Config.ALT_FLOATING_RATE_EXTRA_LIMIT && player.destroyItemByItemId("BuyExtraRate", Config.ALT_FLOATING_RATE_EXTRA_ITEM_ID, (long)(Config.ALT_FLOATING_RATE_EXTRA_ITEM_COUNT * (player.getVarInt("fr_extra") + 1) * Config.ALT_FLOATING_RATE_EXTRA_MUL), null, true))
			{
				player.setVar("fr_extra", String.valueOf(player.getVarInt("fr_extra") + Config.ALT_FLOATING_RATE_EXTRA_SELL_COUNT), (int)(System.currentTimeMillis() / 1000 + Config.ALT_FLOATING_RATE_EXTRA_TIME));
				player.getFloatingRate().setExtraPoints(player.getVarInt("fr_extra"), Config.ALT_FLOATING_RATE_EXTRA_TIME * 1000L);
			}

			onBypassCommand(player, "_bbsmr");
		}
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(Config.ALT_FLOATING_RATE_ENABLE && player.getLevel() <= Config.ALT_FLOATING_RATE_SHOW_CONFIG)
		{
			ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler("_bbsmr");
			if(handler != null)
				handler.onBypassCommand(player, "_bbsmr");
		}
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
