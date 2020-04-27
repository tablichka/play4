package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.RecipeController;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.instancemanager.TownManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.Town;
import ru.l2gw.gameserver.model.entity.recipe.RecipeList;
import ru.l2gw.gameserver.serverpackets.MyTargetSelected;
import ru.l2gw.gameserver.serverpackets.RadarControl;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.util.Files;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author rage
 * @date 24.05.2010 11:14:40
 */
public class RegionCommunity implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");
	private static final GArray<Integer> _towns = new GArray<Integer>(6);
	private static final String[] _regionTypes = {"Private Store Sell", "Private Store Buy", "Private Store Manufacture"};
	private static final String[] _elements = {"&$1622;", "&$1623;", "&$1624;", "&$1625;", "&$1626;", "&$1627;"};
	private static final String[] _grade = {"D Grade", "C Grade", "B Grade", "A Grade", "S Grade", "S80 Grade", "S84 Grade"};
	private static final int SELLER_PER_PAGE = 12;
	private static final ItemPriceComparator itemPriceComparator = new ItemPriceComparator();

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_towns.add(6);
			_towns.add(8);
			_towns.add(9);
			_towns.add(10);
			_towns.add(11);
			_towns.add(12);
			_towns.add(14);
			_towns.add(15);
			_log.info("CommunityBoard: Region service loaded.");
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
		return new String[]{"_bbsloc", "_bbsregion_", "_bbsreglist_", "_bbsregsearch", "_bbsregview_", "_bbsregtarget_", "_bbsregsres_"};
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
		else if("bbsloc".equals(cmd))
		{
			String tpl = Files.read("data/scripts/services/community/html/bbs_regiontpl.htm", player, false);
			StringBuilder rl = new StringBuilder("");
			for(Town town : TownManager.getInstance().getTowns())
			{
				if(!_towns.contains(town.getTownId()))
					continue;

				String reg = tpl.replace("%region_bypass%", "_bbsregion_" + String.valueOf(town.getTownId()));
				reg = reg.replace("%region_name%", town.getName());
				reg = reg.replace("%region_desc%", "Private store: Sell, Buy, Manufacture.");
				reg = reg.replace("%region_type%", "l2ui.bbs_folder");
				int sellers = 0;
				if(town.getZone() != null)
					for(L2Player seller : town.getZone().getPlayers())
						if(seller.getPrivateStoreType() > 0 && seller.getPrivateStoreType() != L2Player.STORE_OBSERVING_GAMES)
							sellers++;

				reg = reg.replace("%sellers_count%", String.valueOf(sellers));
				rl.append(reg);
			}
			String html = Files.read("data/scripts/services/community/html/bbs_region_list.htm", player, false);
			html = html.replace("%REGION_LIST%", rl.toString());
			html = html.replace("%TREE%", "<table border=0 cellspacing=0 cellpadding=0><tr><td width=50></td><td align=center width=695 height=30 align=left><img src=\"l2ui.bbs_lineage2\" width=128 height=16 ></td></tr></table>");

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsregion".equals(cmd))
		{
			String tpl = Files.read("data/scripts/services/community/html/bbs_regiontpl.htm", player, false);
			int townId = Integer.parseInt(st.nextToken());
			StringBuilder rl = new StringBuilder("");
			Town town = TownManager.getInstance().getBuildingById(townId);
			player.setSessionVar("add_fav", bypass + "&Region " + town.getName());

			for(int type = 0; type < _regionTypes.length; type++)
			{
				String reg = tpl.replace("%region_bypass%", "_bbsreglist_" + townId + "_" + type + "_1_0_");
				reg = reg.replace("%region_name%", _regionTypes[type]);
				reg = reg.replace("%region_desc%", _regionTypes[type] + " Players.");
				reg = reg.replace("%region_type%", "l2ui.bbs_board");
				int sellers = 0;
				if(town.getZone() != null)
					for(L2Player seller : town.getZone().getPlayers())
						if(seller.getPrivateStoreType() > 0)
						{
							if(type == 0 && (seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL || seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL_PACKAGE))
								sellers++;
							else if(type == 1 && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_BUY)
								sellers++;
							else if(type == 2 && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
								sellers++;
						}

				reg = reg.replace("%sellers_count%", String.valueOf(sellers));
				rl.append(reg);
			}

			String html = Files.read("data/scripts/services/community/html/bbs_region_list.htm", player, false);
			html = html.replace("%REGION_LIST%", rl.toString());
			html = html.replace("%TREE%", "<br1><table border=0 cellspacing=0 cellpadding=0><tr><td FIXWIDTH=15>&nbsp;</td><td width=745 height=30 align=left><a action=\"bypass -h _bbshome\"> HOME </a>&nbsp;&gt; <a action=\"bypass -h _bbsloc\"> Region </a>&nbsp;&gt; <a action=\"bypass -h _bbsregion_" + townId + "\"> " + town.getName() + " </a></td></tr></table>");

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsreglist".equals(cmd))
		{
			int townId = Integer.parseInt(st.nextToken());
			int type = Integer.parseInt(st.nextToken());
			int page = Integer.parseInt(st.nextToken());
			int byItem = Integer.parseInt(st.nextToken());
			String search = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";
			Town town = TownManager.getInstance().getBuildingById(townId);
			player.setSessionVar("add_fav", bypass + "&Region " + town.getName() + " " + _regionTypes[type]);

			GArray<L2Player> sellers = getSellersList(townId, type, search, byItem == 1);

			int start = (page - 1) * SELLER_PER_PAGE;
			int end = Math.min(page * SELLER_PER_PAGE, sellers.size());

			String html = Files.read("data/scripts/services/community/html/bbs_region_sellers.htm", player, false);

			if(page == 1)
			{
				html = html.replace("%ACTION_GO_LEFT%", "");
				html = html.replace("%GO_LIST%", "");
				html = html.replace("%NPAGE%", "1");
			}
			else
			{
				html = html.replace("%ACTION_GO_LEFT%", "bypass -h _bbsreglist_" + townId + "_" + type + "_" + (page - 1) + "_" + byItem + "_" + search);
				html = html.replace("%NPAGE%", String.valueOf(page));
				StringBuilder goList = new StringBuilder("");
				for(int i = page > 10 ? page - 10 : 1 ; i < page; i++)
					goList.append("<td><a action=\"bypass -h _bbsreglist_").append(townId).append("_").append(type).append("_").append(i).append("_").append(byItem).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST%", goList.toString());
			}

			int pages = Math.max(sellers.size() / SELLER_PER_PAGE, 1);
			if(sellers.size() > pages * SELLER_PER_PAGE)
				pages++;

			if(pages > page)
			{
				html = html.replace("%ACTION_GO_RIGHT%", "bypass -h _bbsreglist_" + townId + "_" + type + "_" + (page + 1) + "_" + byItem + "_" + search);
				int ep = Math.min(page + 10, pages);
				StringBuilder goList = new StringBuilder("");
				for(int i = page + 1; i <= ep; i++)
					goList.append("<td><a action=\"bypass -h _bbsreglist_").append(townId).append("_").append(type).append("_").append(i).append("_").append(byItem).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST2%", goList.toString());
			}
			else
			{
				html = html.replace("%ACTION_GO_RIGHT%", "");
				html = html.replace("%GO_LIST2%", "");
			}

			StringBuilder seller_list = new StringBuilder("");
			String tpl = Files.read("data/scripts/services/community/html/bbs_region_stpl.htm", player, false);

			for(int i = start; i < end; i++)
			{
				L2Player seller = sellers.get(i);
				L2TradeList tl = seller.getTradeList();
				L2ManufactureList cl = seller.getCreateList();

				if(tl == null && cl == null)
					continue;

				String stpl = tpl;
				stpl = stpl.replace("%view_bypass%", "bypass -h _bbsregview_" + townId + "_" + type + "_" + page + "_" + seller.getObjectId() + "_" + byItem + "_" + search);
				stpl = stpl.replace("%seller_name%", seller.getName());
				String title = "";
				if(type == 0)
					title = tl != null && tl.getSellStoreName() != null && !tl.getSellStoreName().isEmpty() ? tl.getSellStoreName() : "no title";
				else if(type == 1)
					title =  tl != null && tl.getBuyStoreName() != null && !tl.getBuyStoreName().isEmpty() ? tl.getBuyStoreName() : "no title";
				else if(type == 2 && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
					title = cl != null && cl.getStoreName() != null && !cl.getStoreName().isEmpty() ? cl.getStoreName() : "no title";

				title = title.replace("<", "");
				title = title.replace(">", "");
				title = title.replace("&", "");
				title = title.replace("$", "");

				if(title.isEmpty())
					title = "no title";

				stpl = stpl.replace("%seller_title%", title);

				seller_list.append(stpl);
			}

			html = html.replace("%SELLER_LIST%", seller_list.toString());
			html = html.replace("%TREE%", "<br1><table border=0 cellspacing=0 cellpadding=0><tr><td FIXWIDTH=15>&nbsp;</td><td width=745 height=30 align=left><a action=\"bypass -h _bbshome\"> HOME </a>&nbsp;&gt; <a action=\"bypass -h _bbsloc\"> Region </a>&nbsp;&gt; <a action=\"bypass -h _bbsregion_" + townId + "\"> " + town.getName() + " </a></td></tr></table>");
			html = html.replace("%search_bypass%", "_bbsregsearch_" + townId + "_" + type);

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsregsres".equals(cmd))
		{
			int townId = Integer.parseInt(st.nextToken());
			int type = Integer.parseInt(st.nextToken());
			int page = Integer.parseInt(st.nextToken());
			String search = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";
			Town town = TownManager.getInstance().getBuildingById(townId);
			player.setSessionVar("add_fav", bypass + "&Region " + town.getName() + " " + _regionTypes[type]);

			List<SearchItem> searchItems = searchItems(townId, type, search);

			int start = (page - 1) * SELLER_PER_PAGE;
			int end = Math.min(page * SELLER_PER_PAGE, searchItems.size());

			String html = Files.read("data/scripts/services/community/html/bbs_region_search.htm", player, false);

			if(page == 1)
			{
				html = html.replace("%ACTION_GO_LEFT%", "");
				html = html.replace("%GO_LIST%", "");
				html = html.replace("%NPAGE%", "1");
			}
			else
			{
				html = html.replace("%ACTION_GO_LEFT%", "bypass -h _bbsregsres_" + townId + "_" + type + "_" + (page - 1) + "_" + search);
				html = html.replace("%NPAGE%", String.valueOf(page));
				StringBuilder goList = new StringBuilder("");
				for(int i = page > 10 ? page - 10 : 1 ; i < page; i++)
					goList.append("<td><a action=\"bypass -h _bbsregsres_").append(townId).append("_").append(type).append("_").append(i).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST%", goList.toString());
			}

			int pages = Math.max(searchItems.size() / SELLER_PER_PAGE, 1);
			if(searchItems.size() > pages * SELLER_PER_PAGE)
				pages++;

			if(pages > page)
			{
				html = html.replace("%ACTION_GO_RIGHT%", "bypass -h bbsregsres_" + townId + "_" + type + "_" + (page + 1) + "_" + search);
				int ep = Math.min(page + 10, pages);
				StringBuilder goList = new StringBuilder("");
				for(int i = page + 1; i <= ep; i++)
					goList.append("<td><a action=\"bypass -h _bbsregsres_").append(townId).append("_").append(type).append("_").append(i).append("_").append(search).append("\"> ").append(i).append(" </a> </td>\n\n");

				html = html.replace("%GO_LIST2%", goList.toString());
			}
			else
			{
				html = html.replace("%ACTION_GO_RIGHT%", "");
				html = html.replace("%GO_LIST2%", "");
			}

			StringBuilder items_list = new StringBuilder("");
			String tpl = Files.read("data/scripts/services/community/html/bbs_region_sres.htm", player, false);

			for(int i = start; i < end; i++)
			{
				SearchItem searchItem = searchItems.get(i);
				TradeItem ti = searchItem.tradeItem;
				L2ManufactureItem mi = searchItem.manufactureItem;

				if(ti == null && mi == null)
					continue;

				String stpl = tpl;
				stpl = stpl.replace("%object_id%", String.valueOf(searchItem.seller.getObjectId()));
				stpl = stpl.replace("%seller_name%", searchItem.seller.getName());

				if(ti != null)
				{
					L2Item item = ItemTable.getInstance().getTemplate(ti.getItemId());
					if(item != null)
					{
						stpl = stpl.replace("%item_img%", item.getIcon());
						stpl = stpl.replace("%item_name%", item.getName() + (item.isEquipment() && ti.getEnchantLevel() > 0 ? " +" + ti.getEnchantLevel() : ""));
						stpl = stpl.replace("%item_count%", String.valueOf(ti.getCount()));
						stpl = stpl.replace("%item_price%", String.format("%,3d", ti.getOwnersPrice()).replace(" ",","));

						String desc = "";
						if(item.getCrystalType() != L2Item.Grade.NONE)
							desc = _grade[item.getCrystalType().ordinal() - 1] + (item.getCrystalCount() > 0 ? " Crystals: " + item.getCrystalCount() + ";" : ";");

						if(item.isEquipment())
						{
							if(ti.getAttackElement() >= 0 && ti.getAttackValue() > 0)
								desc = "&$1620;: " + _elements[ti.getAttackElement()] + " +" + ti.getAttackValue();
							else{
                                if(ti.getDefenceFire() > 0)
                                    desc += "&$1622; +" + ti.getDefenceFire() + "; ";
                                if(ti.getDefenceWater()  > 0)
                                    desc += "&$1623; +" + ti.getDefenceWater() + "; ";
                                if(ti.getDefenceWind()  > 0)
                                    desc += "&$1624; +" + ti.getDefenceWind() + "; ";
                                if(ti.getDefenceEarth()  > 0)
                                    desc += "&$1625; +" + ti.getDefenceEarth() + "; ";
                                if(ti.getDefenceHoly() > 0)
                                    desc += "&$1626; +" + ti.getDefenceHoly() + "; ";
                                if(ti.getDefenceDark() > 0)
                                    desc += "&$1627; +" + ti.getDefenceDark() +";";
                            }
						}
						if(item.isStackable())
							desc = "Stackable;";
						if(item.isSealed())
							desc += "Sealed;";
						if(item.isShadowItem())
							desc += "Shadow item;";
						if(item.isTemporal())
							desc += "Temporal;";

						stpl = stpl.replace("%item_desc%", desc.isEmpty() ? "no desctiption;" : desc);
					}
				}
				else
				{
					RecipeList rec = RecipeController.getRecipeList(mi.getRecipeId() - 1);
					if(rec == null)
						continue;

					L2Item item = ItemTable.getInstance().getTemplate(rec.getProductItemId());

					if(item == null)
						continue;

					stpl = stpl.replace("%item_name%", item.getName());
					stpl = stpl.replace("%item_img%", item.getIcon());
					stpl = stpl.replace("%item_count%", "N/A");
					stpl = stpl.replace("%item_price%", String.format("%,3d", mi.getCost()).replace(" ",","));

					String desc = "";
					if(item.getCrystalType() != L2Item.Grade.NONE)
						desc = _grade[item.getCrystalType().ordinal() - 1] + (item.getCrystalCount() > 0 ? " Crystals: " + item.getCrystalCount() + ";" : ";");

					if(item.isStackable())
						desc = "Stackable;";
					if(item.isSealed())
						desc += "Sealed;";

					stpl = stpl.replace("%item_desc%", desc);
				}

				items_list.append(stpl);
			}

			html = html.replace("%ITEMS_LIST%", items_list.toString());
			html = html.replace("%TREE%", "<br1><table border=0 cellspacing=0 cellpadding=0><tr><td FIXWIDTH=15>&nbsp;</td><td width=745 height=30 align=left><a action=\"bypass -h _bbshome\"> HOME </a>&nbsp;&gt; <a action=\"bypass -h _bbsloc\"> Region </a>&nbsp;&gt; <a action=\"bypass -h _bbsregion_" + townId + "\"> " + town.getName() + " </a></td></tr></table>");
			html = html.replace("%search_bypass%", "_bbsregsearch_" + townId + "_" + type);

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsregview".equals(cmd))
		{
			int townId = Integer.parseInt(st.nextToken());
			int type = Integer.parseInt(st.nextToken());
			int page = Integer.parseInt(st.nextToken());
			int objectId = Integer.parseInt(st.nextToken());
			int byItem = Integer.parseInt(st.nextToken());
			String search = st.hasMoreTokens() ? st.nextToken().toLowerCase() : "";
			Town town = TownManager.getInstance().getBuildingById(townId);

			L2Player seller = L2ObjectsStorage.getPlayer(objectId);
			if(seller == null || seller.getPrivateStoreType() == 0)
			{
				onBypassCommand(player, "_bbsreglist_" + townId + "_" + type + "_" + page + "_" + byItem + "_" + search);
				return;
			}

			String title = "no title";
			String tpl = Files.read("data/scripts/services/community/html/bbs_region_storetpl.htm", player, false);
			StringBuilder sb = new StringBuilder("");

			if(type < 2)
			{
				ConcurrentLinkedQueue<TradeItem> sl = type == 0 ? seller.getSellList() : seller.getBuyList();
				L2TradeList tl = seller.getTradeList();

				if(sl == null || sl.isEmpty() || tl == null)
				{
					onBypassCommand(player, "_bbsreglist_" + townId + "_" + type + "_" + page + "_" + byItem + "_" + search);
					return;
				}

				if(type == 0 && tl.getSellStoreName() != null && !tl.getSellStoreName().isEmpty())
					title = tl.getSellStoreName();
				else if(type == 1 && tl.getBuyStoreName() != null && !tl.getBuyStoreName().isEmpty())
					title = tl.getBuyStoreName();

				for(TradeItem ti : sl)
				{
					L2Item item = ItemTable.getInstance().getTemplate(ti.getItemId());
					if(item != null)
					{
						String stpl = tpl.replace("%item_name%", item.getName() + (item.isEquipment() && ti.getEnchantLevel() > 0 ? " +" + ti.getEnchantLevel() : ""));
						stpl = stpl.replace("%item_img%", item.getIcon());
						stpl = stpl.replace("%item_count%", String.valueOf(ti.getCount()));
						stpl = stpl.replace("%item_price%", String.format("%,3d", ti.getOwnersPrice()).replace(" ",","));

						String desc = "";
						if(item.getCrystalType() != L2Item.Grade.NONE)
							desc = _grade[item.getCrystalType().ordinal() - 1] + (item.getCrystalCount() > 0 ? " Crystals: " + item.getCrystalCount() + ";" : ";");

						if(item.isEquipment())
						{
							if(ti.getAttackElement() >= 0 && ti.getAttackValue() > 0)
								desc = "&$1620;: " + _elements[ti.getAttackElement()] + " +" + ti.getAttackValue();
                            else{
                                if(ti.getDefenceFire() > 0)
                                    desc += "&$1622; +" + ti.getDefenceFire() + "; ";
                                if(ti.getDefenceWater()  > 0)
                                    desc += "&$1623; +" + ti.getDefenceWater() + "; ";
                                if(ti.getDefenceWind()  > 0)
                                    desc += "&$1624; +" + ti.getDefenceWind() + "; ";
                                if(ti.getDefenceEarth()  > 0)
                                    desc += "&$1625; +" + ti.getDefenceEarth() + "; ";
                                if(ti.getDefenceHoly() > 0)
                                    desc += "&$1626; +" + ti.getDefenceHoly() + "; ";
                                if(ti.getDefenceDark() > 0)
                                    desc += "&$1627; +" + ti.getDefenceDark() +";";
                            }
						}
						if(item.isStackable())
							desc = "Stackable;";
						if(item.isSealed())
							desc += "Sealed;";
						if(item.isShadowItem())
							desc += "Shadow item;";
						if(item.isTemporal())
							desc += "Temporal;";

						stpl = stpl.replace("%item_desc%", desc.isEmpty() ? "no desctiption;" : desc);
						sb.append(stpl);
					}
				}
			}
			else
			{
				L2ManufactureList cl = seller.getCreateList();
				if(cl == null)
				{
					onBypassCommand(player, "_bbsreglist_" + townId + "_" + type + "_" + page + "_" + byItem + "_" + search);
					return;
				}

				if((title = cl.getStoreName()) == null)
					title = "no title";

				for(L2ManufactureItem mi : cl.getList())
				{
					RecipeList rec = RecipeController.getRecipeList(mi.getRecipeId() - 1);
					if(rec == null)
						continue;

					L2Item item = ItemTable.getInstance().getTemplate(rec.getProductItemId());

					if(item == null)
						continue;

					String stpl = tpl.replace("%item_name%", item.getName());
					stpl = stpl.replace("%item_img%", item.getIcon());
					stpl = stpl.replace("%item_count%", "N/A");
					stpl = stpl.replace("%item_price%", String.format("%,3d", mi.getCost()).replace(" ",","));

					String desc = "";
					if(item.getCrystalType() != L2Item.Grade.NONE)
						desc = _grade[item.getCrystalType().ordinal() - 1] + (item.getCrystalCount() > 0 ? " Crystals: " + item.getCrystalCount() + ";" : ";");

					if(item.isStackable())
						desc = "Stackable;";
					if(item.isSealed())
						desc += "Sealed;";

					stpl = stpl.replace("%item_desc%", desc);
					sb.append(stpl);
				}
			}


			String html = Files.read("data/scripts/services/community/html/bbs_region_view.htm", player, false);

			html = html.replace("%TREE%", "<br1><table border=0 cellspacing=0 cellpadding=0><tr><td FIXWIDTH=15>&nbsp;</td><td width=745 height=30 align=left><a action=\"bypass -h _bbshome\"> HOME </a>&nbsp;&gt; " +
					"<a action=\"bypass -h _bbsloc\"> Region </a>&nbsp;&gt; " +
					"<a action=\"bypass -h _bbsregion_" + townId + "\"> " + town.getName() + " </a>&nbsp;&gt; " +
					"<a action=\"bypass -h _bbsreglist_" + townId + "_" + type + "_" + page + "_" + byItem + "_\"> " + town.getName() + " " + _regionTypes[type] + " </a></td></tr></table>");
			html = html.replace("%sell_type%", _regionTypes[type]);

			title = title.replace("<", "");
			title = title.replace(">", "");
			title = title.replace("&", "");
			title = title.replace("$", "");
			if(title.isEmpty())
				title = "no title";
			html = html.replace("%title%", title);
			html = html.replace("%char_name%", seller.getName());
			html = html.replace("%object_id%", String.valueOf(seller.getObjectId()));
			html = html.replace("%STORE_LIST%", sb.toString());
			html = html.replace("%list_bypass%", "_bbsreglist_" + townId + "_" + type + "_" + page + "_" + byItem + "_" + search);

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsregtarget".equals(cmd))
		{
			int objectId = Integer.parseInt(st.nextToken());
			L2Player seller = L2ObjectsStorage.getPlayer(objectId);
			if(seller != null)
			{
				player.sendPacket(new RadarControl(0, 2, seller.getLoc()));
				if(player.knowsObject(seller) && player.setTarget(seller))
				{
					player.sendPacket(new MyTargetSelected(objectId, 0));
					seller.sendRelation(player);
				}
			}
			else
				player.sendActionFailed();
		}

	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		if("bbsregsearch".equals(cmd))
		{
			int townId = Integer.parseInt(st.nextToken());
			int type = Integer.parseInt(st.nextToken());
			String byItem = "Item".equals(arg4) ? "1" : "0";
			if(arg3 == null)
				arg3 = "";

			arg3 = arg3.replace("<", "");
			arg3 = arg3.replace(">", "");
			arg3 = arg3.replace("&", "");
			arg3 = arg3.replace("$", "");

			if(arg3.length() > 30)
				arg3 = arg3.substring(0, 30);

			if("1".equals(byItem))
				onBypassCommand(player, "_bbsregsres_" + townId + "_" + type + "_1_" + arg3);
			else
				onBypassCommand(player, "_bbsreglist_" + townId + "_" + type + "_1_" + byItem + "_" + arg3);
		}
	}

	private static List<SearchItem> searchItems(int townId, int type, String search)
	{
		List<L2Player> list = new LinkedList<>();
		Town town = TownManager.getInstance().getBuildingById(townId);
		if(town == null || town.getZone() == null)
			return Collections.emptyList();

		for(L2Player seller : town.getZone().getPlayers())
		{
			L2TradeList tl = seller.getTradeList();
			L2ManufactureList cl = seller.getCreateList();
			if(seller.getPrivateStoreType() > 0)
			{
				if(type == 0 && tl != null && (seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL || seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL_PACKAGE))
					list.add(seller);
				else if(type == 1 && tl != null && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_BUY)
					list.add(seller);
				else if(type == 2 && cl != null && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
					list.add(seller);
			}
		}

		if(!search.isEmpty() && !list.isEmpty())
		{
			List<SearchItem> s_list = new LinkedList<>();

			for(L2Player seller : list)
			{
				L2TradeList tl = seller.getTradeList();
				L2ManufactureList cl = seller.getCreateList();
				if((type == 0 || type == 1) && tl != null)
				{
					ConcurrentLinkedQueue<TradeItem> sl = type == 0 ? seller.getSellList() : seller.getBuyList();
					if(sl != null)
						for(TradeItem ti : sl)
						{
							L2Item item = ItemTable.getInstance().getTemplate(ti.getItemId());
							if(item != null && item.getName() != null && item.getName().toLowerCase().contains(search))
							{
								s_list.add(new SearchItem(seller, ti, null));
							}
						}
				}
				else if(type == 2 && cl != null)
				{
					for(L2ManufactureItem mi : cl.getList())
					{
						RecipeList recipe = RecipeController.getRecipeList(mi.getRecipeId() - 1);
						if(recipe != null)
						{
							L2Item item = ItemTable.getInstance().getTemplate(recipe.getProductItemId());
							if(item != null && item.getName() != null && item.getName().toLowerCase().contains(search))
							{
								s_list.add(new SearchItem(seller, null, mi));
							}
						}
					}
				}
			}

			Collections.sort(s_list, itemPriceComparator);
			return s_list;
		}

		return Collections.emptyList();
	}

	private static GArray<L2Player> getSellersList(int townId, int type, String search, boolean byItem)
	{
		GArray<L2Player> list = new GArray<L2Player>();
		Town town = TownManager.getInstance().getBuildingById(townId);
		if(town == null || town.getZone() == null)
			return list;

		for(L2Player seller : town.getZone().getPlayers())
		{
			L2TradeList tl = seller.getTradeList();
			L2ManufactureList cl = seller.getCreateList();
			if(seller.getPrivateStoreType() > 0)
			{
				if(type == 0 && tl != null && (seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL || seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL_PACKAGE))
					list.add(seller);
				else if(type == 1 && tl != null && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_BUY)
					list.add(seller);
				else if(type == 2 && cl != null && seller.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
					list.add(seller);
			}
		}

		if(!search.isEmpty() && !list.isEmpty())
		{
			GArray<L2Player> s_list = new GArray<L2Player>();
			for(L2Player seller : list)
			{
				L2TradeList tl = seller.getTradeList();
				L2ManufactureList cl = seller.getCreateList();
				if(byItem)
				{
					if((type == 0 || type == 1) && tl != null)
					{
						ConcurrentLinkedQueue<TradeItem> sl = type == 0 ? seller.getSellList() : seller.getBuyList();
						if(sl != null)
							for(TradeItem ti : sl)
							{
								L2Item item = ItemTable.getInstance().getTemplate(ti.getItemId());
								if(item != null && item.getName() != null && item.getName().toLowerCase().contains(search))
								{
									s_list.add(seller);
									break;
								}
							}
					}
					else if(type == 2 && cl != null)
						for(L2ManufactureItem mi : cl.getList())
						{
							RecipeList recipe = RecipeController.getRecipeList(mi.getRecipeId() - 1);
							if(recipe != null)
							{
								L2Item item = ItemTable.getInstance().getTemplate(recipe.getProductItemId());
								if(item != null && item.getName() != null && item.getName().toLowerCase().contains(search))
								{
									s_list.add(seller);
									break;
								}
							}
						}
				}
				else if(type == 0 && tl != null && tl.getSellStoreName() != null && tl.getSellStoreName().toLowerCase().contains(search))
					s_list.add(seller);
				else if(type == 1 && tl != null && tl.getBuyStoreName() != null && tl.getBuyStoreName().toLowerCase().contains(search))
					s_list.add(seller);
				else if(type == 2 && cl != null && seller.getCreateList() != null && seller.getCreateList().getStoreName() != null && seller.getCreateList().getStoreName().toLowerCase().contains(search))
					s_list.add(seller);
			}
			list = s_list;
		}

		if(!list.isEmpty())
		{
			L2Player[] players = new L2Player[list.size()];
			list.toArray(players);
			Arrays.sort(players, new PlayersComparator<L2Player>());
			list.clear();
			list.addAll(Arrays.asList(players));
		}

		return list;
	}

	private static class SearchItem
	{
		public L2Player seller;
		public TradeItem tradeItem;
		public L2ManufactureItem manufactureItem;

		private SearchItem(L2Player seller, TradeItem tradeItem, L2ManufactureItem manufactureItem)
		{
			this.seller = seller;
			this.tradeItem = tradeItem;
			this.manufactureItem = manufactureItem;
		}
	}

	private static class PlayersComparator<T> implements Comparator<T>
	{
		@Override
		public int compare(Object o1, Object o2)
		{
			if(o1 instanceof L2Player && o2 instanceof L2Player)
			{
				L2Player p1 = (L2Player) o1;
				L2Player p2 = (L2Player) o2;
				return p1.getName().compareTo(p2.getName());
			}
			return 0;
		}
	}

	private static class ItemPriceComparator implements Comparator<SearchItem>
	{
		@Override
		public int compare(SearchItem o1, SearchItem o2)
		{
			if(o1 == null || o2 == null)
				return 0;

			if(o1.tradeItem != null && o2.tradeItem != null)
			{
				long r = o1.tradeItem.getOwnersPrice() - o2.tradeItem.getOwnersPrice();
				if(r > Integer.MAX_VALUE)
					r -= Integer.MAX_VALUE;
				else if(r < Integer.MIN_VALUE)
					r += Integer.MAX_VALUE;
				return (int) (r);
			}

			if(o1.manufactureItem != null && o2.manufactureItem != null)
			{
				long r = o1.manufactureItem.getCost() - o2.manufactureItem.getCost();
				if(r > Integer.MAX_VALUE)
					r -= Integer.MAX_VALUE;
				else if(r < Integer.MIN_VALUE)
					r += Integer.MAX_VALUE;
				return (int) (r);
			}

			return 0;
		}
	}
}
