package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.ChangePremiumDate;
import ru.l2gw.gameserver.loginservercon.gspackets.LockAccountIP;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.serverpackets.*;
import ru.l2gw.gameserver.tables.CharTemplateTable;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;
import services.AutoHeal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author: rage
 * @date: 26.05.2010 21:21:49
 */
public class ManageAccount implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");
	private static final String UPDATE_CHAR_SEX = "UPDATE characters SET sex = ?, hairStyle = 0, hairColor = 0, face = 0 WHERE obj_id = ?";

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Manage Account service loaded.");
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
		return new String[]{"_bbsaccount", "_bbspremium", "_bbsgetpremium_", "_bbsswgender", "_bbsswgenderhtm", "_bbsaddip", "_bbsdelip_", "_bbscfg_"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		boolean isPremium = player.isPremiumEnabled();
		HashMap<Integer, String> tpls;
		String html, tpl;

		if(!player.getVarB("selected_language@") && Config.SHOW_LANG_SELECT_MENU)
		{
			html = Files.read("data/scripts/services/community/html/langue_select.htm", player, false);
			html = html.replace("<?page?>", bypass);
		}
		else if("bbspremium".equals(cmd))
		{
			tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/bbs_premiumbuy.htm", player, false));
			html = tpls.get(0);
			tpl = tpls.get(1);
			StringBuilder sb = new StringBuilder("");
			for(int i = 0; i < Config.PREMIUM_PRICES.size(); i++)
			{
				int[] price = Config.PREMIUM_PRICES.get(i);
				String t = tpl.replace("<?premium_duration?>", String.valueOf(price[2]));
				t = t.replace("<?premium_price?>", String.valueOf(price[1]));
				t = t.replace("<?premium_item?>", ItemTable.getInstance().getTemplate(price[0]).getName());
				t = t.replace("<?premium_buy?>", "_bbsgetpremium_" + i);
				sb.append(t);
			}

			html = html.replace("<?premium_option?>", sb.toString());
			ShowBoard.separateAndSend(html, player);
			return;
		}
		else if("bbsgetpremium".equals(cmd))
		{
		 	int priceId = Integer.parseInt(st.nextToken());
			if(priceId < Config.PREMIUM_PRICES.size())
			{
				int[] price = Config.PREMIUM_PRICES.get(priceId);
				if(player.destroyItemByItemId("premiumBuy", price[0], price[1], null, true))
				{
					long endTime = System.currentTimeMillis();
					if(player.isPremiumEnabled())
						endTime = player.getNetConnection().getPremiumExpire();

					endTime += price[2] * 24L * 60 * 60000L;
					player.getNetConnection().setPremiumExpire(endTime);
					LSConnection.getInstance().sendPacket(new ChangePremiumDate(player.getAccountName(), (int)(endTime / 1000)));
					player.startPremiumTask(endTime);
				}
			}
			onBypassCommand(player, "_bbsaccount");
			return;
		}
		else if("bbsswgenderhtm".equals(cmd))
		{
			tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/bbs_switchgender.htm", player, false));
			html = tpls.get(0);
			tpl = tpls.get(1);
			StringBuilder sb = new StringBuilder("");
			for(int i = 0; i < Config.PREMIUM_SEX_CHANGE_PRICE.length; i+=2)
			{
				String t = tpl.replace("<?curr_gender?>", player.getSex() == 0 ? "&$177;" : "&$178;");
				t = t.replace("<?dst_gender?>", player.getSex() == 0 ? "&$178;" : "&$177;");
				t = t.replace("<?sw_gender_price?>", String.valueOf(Config.PREMIUM_SEX_CHANGE_PRICE[i + 1]));
				t = t.replace("<?sw_gender_item?>", ItemTable.getInstance().getTemplate(Config.PREMIUM_SEX_CHANGE_PRICE[i]).getName());
				t = t.replace("<?sw_gender?>", "_bbsswgender_" + i);
				sb.append(t);
			}

			html = html.replace("<?sw_gender_option?>", sb.toString());
			ShowBoard.separateAndSend(html, player);
			return;
		}
		else if("bbsswgender".equals(cmd))
		{
			if(player.getRace() == Race.kamael)
			{
				player.sendPacket(Msg.ActionFail);
				// TODO show error htm
				return;
			}

			int priceId = Integer.parseInt(st.nextToken());
			if(player.destroyItemByItemId("ChangeSex", Config.PREMIUM_SEX_CHANGE_PRICE[priceId], Config.PREMIUM_SEX_CHANGE_PRICE[priceId + 1], null, true))
			{
				Connection con = null;
				try
				{
					con = DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement(UPDATE_CHAR_SEX);
					statement.setInt(1, player.getSex() == 0 ? 1 : 0);
					statement.setInt(2, player.getObjectId());
					statement.execute();
					statement.close();
				}
				catch(Exception e)
				{
					_log.warn("ChangeNameColor: " + e);
				}
				finally
				{
					try
					{
						con.close();
						player.logout(false, false, true);
					}
					catch(Exception e)
					{
					}
				}
			}
			return;
		}
		else if("bbsaddip".equals(cmd))
		{
			String currIps = player.getNetConnection().getAllowdIps();

			if(currIps.equals("*"))
				currIps = "";

			String[] ips = currIps.split(",");
			if(ips.length > 3)
			{
				player.sendMessage(new CustomMessage("common.aclMaxExceeded", player));
				onBypassCommand(player, "_bbsaccount");
				return;
			}

			boolean add = true;
			String currIp = player.getIP();
			for(String ip : ips)
				if(ip.equals(currIp))
				{
					add = false;
					break;
				}

			if(add)
			{
				currIps += currIp + ",";
				player.getNetConnection().setAllowdIps(currIps);
				LSConnection.getInstance().sendPacket(new LockAccountIP(player.getAccountName(), currIps));
			}

			onBypassCommand(player, "_bbsaccount");
			return;
		}
		else if("bbsdelip".equals(cmd))
		{
			String ip = st.nextToken();
			String currIps = player.getNetConnection().getAllowdIps().replace(ip + ",", "");
			if(currIps.isEmpty())
				currIps = "*";
			player.getNetConnection().setAllowdIps(currIps);
			LSConnection.getInstance().sendPacket(new LockAccountIP(player.getAccountName(), currIps));
			onBypassCommand(player, "_bbsaccount");
			return;
		}
		else if("bbscfg".equals(cmd))
		{
			String subCmd = st.nextToken();
			String val = st.nextToken();
			if("ah".equals(subCmd))
			{
				boolean on = val.equals("on");
				AutoHeal.changeAutoHeal(player, on);
			}
			else if("al".equals(subCmd))
			{
				boolean on = val.equals("on");
				if(on && !isPremium && Config.PREMIUM_AUTOLOOT_ONLY)
					player.sendMessage(new CustomMessage("common.onlyPremium", player));
				else if(!on)
					player.unsetVar("autoloot");
				else
					player.setVar("autoloot", "true");
			}
			else if("lg".equals(subCmd))
				player.setVar("lang@", val);
			else if("ne".equals(subCmd))
			{
				if(val.equalsIgnoreCase("on"))
					player.setVar("NoExp", "1");
				else if(val.equalsIgnoreCase("of"))
					player.unsetVar("NoExp");
			}
			else if("tr".equals(subCmd))
			{
				if(val.equalsIgnoreCase("on"))
					player.setVar("trace", "1");
				else if(val.equalsIgnoreCase("of"))
					player.unsetVar("trace");
			}
			else if("nt".equals(subCmd))
			{
				if(val.equalsIgnoreCase("on"))
				{
					player.setVar("notraders", "1");
					for(L2Player pl : L2World.getAroundPlayers(player))
						if(pl.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
							player.sendPacket(new DeleteObject(pl));
				}
				else if(val.equalsIgnoreCase("of"))
				{
					player.unsetVar("notraders");
					for(L2Player pl : L2World.getAroundPlayers(player))
						if(pl.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
						{
							player.sendPacket(new CharInfo(pl));
							if(pl.getPrivateStoreType() == L2Player.STORE_PRIVATE_BUY)
								player.sendPacket(new PrivateStoreMsgBuy(pl));
							else if(pl.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL || pl.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL_PACKAGE)
								player.sendPacket(new PrivateStoreMsgSell(pl));
							else if(pl.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
								player.sendPacket(new RecipeShopMsg(pl));
						}
				}
			}
			else if("nb".equals(subCmd))
			{
				if(val.equalsIgnoreCase("on"))
				{
					player.setNotShowBuffAnim(true);
					player.setVar("notShowBuffAnim", "1");
				}
				else if(val.equalsIgnoreCase("of"))
				{
					player.setNotShowBuffAnim(false);
					player.unsetVar("notShowBuffAnim");
				}
			}
			else if("sd".equals(subCmd))
			{
				if(val.equalsIgnoreCase("on"))
					player.setVar("showdrop", "1");
				else if(val.equalsIgnoreCase("of"))
					player.unsetVar("showdrop");
			}
			else if("pa".equals(subCmd) && Config.ALT_ANNOUNCE_PVP)
			{
				if(val.equalsIgnoreCase("on"))
					player.setVar("pvpan", "1");
				else if(val.equalsIgnoreCase("of"))
					player.unsetVar("pvpan");
			}
			else if("ah".equals(subCmd) && Config.SERVICES_AUTO_HEAL_ACTIVE)
			{
				if(val.equalsIgnoreCase("on"))
					player.setVar("AutoHealActive", "1");
				else if(val.equalsIgnoreCase("of"))
					player.unsetVar("AutoHealActive");
			}
		}

		tpls = Util.parseTemplate(Files.read("data/scripts/services/community/html/bbs_myaccount.htm", player, false));

		html = tpls.get(0);
		tpl = isPremium ? tpls.get(2).replace("<?premium_end?>", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date(player.getNetConnection().getPremiumExpire()))) : tpls.get(1);
		html = html.replace("<?acc_status?>", tpl);
		html = html.replace("<?acc_icon?>", isPremium ? tpls.get(5) : "");
		html = html.replace("%recomms%", String.valueOf(player.getRecSystem().getRecommendsHave()));
		html = html.replace("%rates%", isPremium ? String.valueOf(Config.PREMIUM_RATE_EXPSP) : "0");
		html = html.replace("%inv_slots%", isPremium ? String.valueOf(Config.PREMIUM_INVENTORY_LIMIT) : "0");
		html = html.replace("%wh_slots%", isPremium ? String.valueOf(Config.PREMIUM_WAREHOUSE_LIMIT) : "0");
		html = html.replace("%tr_slots%", isPremium ? String.valueOf(Config.PREMIUM_PRIVATE_STORE_LIMIT) : "0");
		html = html.replace("%weight_limit%", isPremium ? String.valueOf(Config.PREMIUM_WEIGHT_LIMIT) : "0");
		html = html.replace("%nick_color%", String.format("<font color=%s>%s</font>", Util.int2rgb(player.getNameColor()), player.getName()));
		html = html.replace("%title_color%", String.format("<font color=%s>%s</font>", Util.int2rgb(player.getTitleColor()), player.getTitle() == null || player.getTitle().isEmpty() ? "no title" : player.getTitle().replace("&", "").replace("$", "").replace("<", "&lt;").replace(">", "&gt;")));
		html = html.replace("%class%", CharTemplateTable.getClassNameById(player.getActiveClass()));
		html = html.replace("%gender%", player.getSex() == 0 ? "male" : "female");
		html = html.replace("<?auto_loot?>", player.isAutoLoot() ? "&$227;" : "&$228;");
		html = html.replace("<?lang?>", player.getVar("lang@").equals("ru") ? "Russian" : "English");
		html = html.replace("<?noe?>", player.getVarB("NoExp") ? "&$227;" : "&$228;");
		html = html.replace("<?trace?>", player.getVarB("trace") ? "&$227;" : "&$228;");
		html = html.replace("<?notraders?>", player.getVarB("notraders") ? "&$227;" : "&$228;");
		html = html.replace("<?nobuff?>", player.getVarB("notShowBuffAnim") ? "&$227;" : "&$228;");
		html = html.replace("<?showdrop?>", player.getVarB("showdrop") ? "&$227;" : "&$228;");
		if(Config.SERVICES_AUTO_HEAL_ACTIVE)
			html = html.replace("<?autoheal_tpl?>", tpls.get(7).replace("<?autoheal?>", player.getVarB("AutoHealActive") ? "&$227;" : "&$228;"));
		else
			html = html.replace("<?autoheal_tpl?>", "");
		if(Config.ALT_ANNOUNCE_PVP)
			html = html.replace("<?pvp_an_tpl?>", tpls.get(6).replace("<?pvpan?>", player.getVarB("pvpan") ? "&$227;" : "&$228;"));
		else
			html = html.replace("<?pvp_an_tpl?>", "");

		html = html.replace("<?curr_ip?>", player.getNetConnection().getIpAddr());
		StringBuilder sb = new StringBuilder("");
		String ips = player.getNetConnection().getAllowdIps();
		if(!ips.equals("*"))
		{
			tpl = tpls.get(3);
			for(String ip : ips.split(","))
				if(!ip.isEmpty())
				{
					String p = tpl.replace("<?acl_ip?>", ip);
					sb.append(p);
				}
		}
		html = html.replace("<?acl_list?>", sb.toString());

		ShowBoard.separateAndSend(html, player);
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
