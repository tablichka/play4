package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.serverpackets.SkillList;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.CharTemplateTable;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.util.Files;

import java.util.ArrayList;

public class Quests extends Functions implements ScriptFile, ICommunityBoardHandler
{
	private static final Log log = LogFactory.getLog("service");

	private static final int COST_ITEM_ID = 6673;
	private static final int COST_GOLD_ITEM_ID = 9143;
	private static final String COST_ITEM_NAME = "Festival Adena";
	private static final String COST_GOLD_ITEM_ID_NAME = "Golden Apiga";
	private static final int[] class_levels = {20, 40, 76};
	private static final int Ocupation1_Adena = 100000;
	private static final int Ocupation2_Adena = 2000000;
	private static final int Ocupation3_Adena = 10000000;
	private static final int Ocupation3_COST_ITEM = 5;
	private static final int Ocupation_CHANGE_MAX_LEVEL = 4;
	private static final int Subclasses_Adena = 2000000000;
	private static final int Subclasses_COST_ITEM = 20;
	private static final int Nobless_Adena = 2000000000;
	private static final int Nobless_COST_ITEM = 1;
	private static final int PK_ITEM_COUNT = 10;
	private static final int SUB_SELL_ENABLE = 0; /// 0 ili 1
	private static final int NOBL_SELL_ENABLE = 0;

	private static final String notEnoughtCOST_ITEM = "У вас недостаточно " + COST_ITEM_NAME;
	private static final String notEnoughtCOST_FOLD_ITEM = "У вас недостаточно " + COST_GOLD_ITEM_ID_NAME;

	@Override
	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Quests service.");
			CommunityBoardManager.getInstance().registerHandler(this);
		}
	}

	@Override
	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
			CommunityBoardManager.getInstance().unregisterHandler(this);
	}

	@Override
	public void onShutdown()
	{
	}

	@Override
	public String[] getBypassCommands()
	{
		return new String[]{"_bbsQuestsOcupation", "_bbsQuestsSubs", "_bbsQuests", "_bbsQuestsNobless", "_bbsQuestsSaveMySoul"};
	}

	@Override
	public void onBypassCommand(L2Player player, String bypass)
	{
		if(bypass.startsWith("_bbsQuests "))
			ShowBoard.separateAndSend(FullPage(player), player);
		else if(bypass.startsWith("_bbsQuestsOcupation"))
		{
			ClassId playerClass = player.getClassId();
			if(playerClass.getLevel() >= Ocupation_CHANGE_MAX_LEVEL)
			{
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			int need_level = class_levels[playerClass.getLevel() - 1];
			if(player.getLevel() < need_level)
			{
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			String[] var = bypass.replaceAll("_bbsQuestsOcupation ", "").split(" ");
			int RequestClass = Integer.parseInt(var[0]);
			ClassId RequestClassId = null;
			ArrayList<ClassId> avail_classes = getAvailClasses(playerClass);
			for(ClassId _class : avail_classes)
				if(_class.getId() == RequestClass)
				{
					RequestClassId = _class;
					break;
				}
			if(RequestClassId == null)
			{
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			int need_item_id = 0;
			int need_item_count = 0;

			switch(playerClass.getLevel())
			{
				case 1:
					need_item_id = 57;
					need_item_count = Ocupation1_Adena;
					break;
				case 2:
					need_item_id = 57;
					need_item_count = Ocupation2_Adena;
					break;
				case 3:
					need_item_id = var[1].equalsIgnoreCase("0") ? 57 : COST_ITEM_ID;
					need_item_count = var[1].equalsIgnoreCase("0") ? Ocupation3_Adena : Ocupation3_COST_ITEM;
					break;
			}

			if(need_item_id == 0 || need_item_count == 0)
			{
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			if(getItemCount(player, need_item_id) < need_item_count)
			{
				if(need_item_id == 57)
					player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
				else
					player.sendMessage(notEnoughtCOST_ITEM);

				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			removeItem(player, need_item_id, need_item_count);
			log.info("QUEST: " + player.toFullString() + " Смена професии " + CharTemplateTable.charClasses[playerClass.getId()] + " -> " + CharTemplateTable.charClasses[RequestClassId.getId()] + " за " + need_item_id + ":" + need_item_count);

			if(player.getClassId().getLevel() == 3)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_COMPLETED_THE_QUEST_FOR_3RD_OCCUPATION_CHANGE_AND_MOVED_TO_ANOTHER_CLASS_CONGRATULATIONS));
				if(need_item_id != 57)
					addItem(player, 6622, 15);
			}
			else
				player.sendPacket(new SystemMessage(SystemMessage.CONGRATULATIONS_YOU_HAVE_TRANSFERRED_TO_A_NEW_CLASS));

    			player.setClassId((short) RequestClass, false);
    			player.sendChanges();
			if(RequestClass == 97)
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_CARDINAL, 1, null, true);
			else if(RequestClass == 105)
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_EVAS_SAINT, 1, null, true);
			else if(RequestClass == 112)
			{
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_SHILIEN_SAINT, 1, null, true);
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_SHILIEN_SAINT, 1, null, true);
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_SHILIEN_SAINT, 1, null, true);
				player.addItem("CommunityClassBuy", L2Item.ITEM_ID_POMANDER_SHILIEN_SAINT, 1, null, true);
			}

			ShowBoard.separateAndSend(FullPage(player), player);
		}
		else if(bypass.startsWith("_bbsQuestsSubs"))
		{
			if(player.getLevel() < 75)
			{
				String tpl = Files.read("data/scripts/services/community/html/quests.htm", player, false);
				tpl = tpl.replace("%content%", str_getSubClass_Level);
				tpl = tpl.replace("%ocupationTab%", tableOcupationCurrent(player));
				tpl = tpl.replace("%karmaTab%", tableKarmaPK(player));
				ShowBoard.separateAndSend(tpl, player);
				return;
			}

			String[] var = bypass.replaceAll("_bbsQuestsSubs ", "").split(" ");
			int need_item_id = var[0].equalsIgnoreCase("0") ? 57 : COST_GOLD_ITEM_ID;
			int need_item_count = var[0].equalsIgnoreCase("0") ? Subclasses_Adena : Subclasses_COST_ITEM;

			if(getItemCount(player, need_item_id) < need_item_count)
			{
				if(need_item_id == 57)
					player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
				else
					player.sendMessage(notEnoughtCOST_FOLD_ITEM);
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			removeItem(player, need_item_id, need_item_count);
			log.info("QUEST: " + player.toFullString() + " Активация сабклассов за " + need_item_id + ":" + need_item_count);

			CompleteQuest("_234_FatesWhisper", player);
			CompleteQuest(player.getRace() == Race.kamael ? "_236_SeedsOfChaos" : "_235_MimirsElixir", player);

			ShowBoard.separateAndSend(FullPage(player), player);
		}
		else if(bypass.startsWith("_bbsQuestsNobless"))
		{
			if(player.isNoble())
			{
				String tpl = Files.read("data/scripts/services/community/html/quests.htm", player, false);
				tpl = tpl.replace("%content%", str_getNobless_Already);
				tpl = tpl.replace("%ocupationTab%", tableOcupationCurrent(player));
				tpl = tpl.replace("%karmaTab%", tableKarmaPK(player));
				ShowBoard.separateAndSend(tpl, player);
				return;
			}

			if(player.getSubLevel() < 75)
			{
				String tpl = Files.read("data/scripts/services/community/html/quests.htm", player, false);
				tpl = tpl.replace("%content%", str_getNobless_Level);
				tpl = tpl.replace("%ocupationTab%", tableOcupationCurrent(player));
				tpl = tpl.replace("%karmaTab%", tableKarmaPK(player));
				ShowBoard.separateAndSend(tpl, player);
				return;
			}

			String[] var = bypass.replaceAll("_bbsQuestsNobless ", "").split(" ");
			int need_item_id = var[0].equalsIgnoreCase("0") ? 57 : COST_ITEM_ID;
			int need_item_count = var[0].equalsIgnoreCase("0") ? Nobless_Adena : Nobless_COST_ITEM;

			if(getItemCount(player, need_item_id) < need_item_count)
			{
				if(need_item_id == 57)
					player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
				else
					player.sendMessage(notEnoughtCOST_ITEM);
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			log.info("QUEST: " + player.toFullString() + " Получение дворянства за " + need_item_id + ":" + need_item_count);
			removeItem(player, need_item_id, need_item_count);

			Olympiad.checkNoble(player);
			player.setNoble(true);
			player.sendPacket(new SkillList(player));
			player.broadcastPacket(new SocialAction(player.getObjectId(), 16));
			player.broadcastUserInfo(true);
			player.broadcastPacket(new SocialAction(player.getObjectId(), SocialAction.SocialType.VICTORY));
			player.sendMessage("Спасибо за покупку дворянства, приятной игры.");

			ShowBoard.separateAndSend(FullPage(player), player);
		}
		else if(bypass.startsWith("_bbsQuestsSaveMySoul"))
		{
			if(getItemCount(player, COST_ITEM_ID) < PK_ITEM_COUNT * player.getPkKills())
			{
				player.sendMessage(notEnoughtCOST_ITEM);
				ShowBoard.separateAndSend(FullPage(player), player);
				return;
			}

			removeItem(player, COST_ITEM_ID, PK_ITEM_COUNT * player.getPkKills());
			log.info("QUEST: " + player.toFullString() + " Искупление грехов за 1 COL: " + player.getPkKills() + " PK и " + player.getKarma() + " кармы");
			player.setKarma(0);
			player.setPkKills(0);
			player.sendUserInfo(true);

			ShowBoard.separateAndSend(FullPage(player), player);
		}
	}

	@Override
	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}

	/**
	 * *****************************************************************************
	 */
	private static void CompleteQuest(String name, L2Player player)
	{
		Quest _quest = QuestManager.getQuest(name);
		QuestState qs = player.getQuestState(_quest.getName());

		if(qs == null)
			qs = _quest.newQuestState(player);
		qs.setState(Quest.COMPLETED);
	}

	private static ArrayList<ClassId> getAvailClasses(ClassId playerClass)
	{
		ArrayList<ClassId> result = new ArrayList<ClassId>();
		for(ClassId _class : ClassId.values())
			if(_class.getLevel() == playerClass.getLevel() + 1 && _class.childOf(playerClass) && _class != ClassId.inspector)
				result.add(_class);
		return result;
	}

	private static String tableOcupation(L2Player player)
	{
		ClassId playerClass = player.getClassId();
		String playerClassName = CharTemplateTable.charClasses[playerClass.getId()];
		String result = "";

		if(playerClass.getLevel() == Ocupation_CHANGE_MAX_LEVEL)
			return result + "Ваша профессия <font color=\"LEVEL\">" + playerClassName + "</font>.";

		int need_level = class_levels[playerClass.getLevel() - 1];
		if(player.getLevel() < need_level)
			return result + "<br1><font color=\"f8c481\">Для получения следующей профессии вы должны достичь " + need_level + "-ого уровня.</font><br>";

		ArrayList<ClassId> avail_classes = getAvailClasses(playerClass);
		if(avail_classes.size() == 0)
			return result + "<br>";

		result += "<table height=25><tr>";
		for(ClassId _class : avail_classes)
		{
			String _className = CharTemplateTable.charClasses[_class.getId()];
			switch(playerClass.getLevel())
			{
				case 1:
					result += "<td><button value=\"" + _className + ":" + Ocupation1_Adena + " аден\" action=\"bypass _bbsQuestsOcupation " + _class.getId() + "\" width=170 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></td>";
					break;
				case 2:
					result += "<td><button value=\"" + _className + ":" + Ocupation2_Adena + " аден\" action=\"bypass _bbsQuestsOcupation " + _class.getId() + "\" width=170 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></td>";
					break;
				case 3:
					result += "<td><button value=\"" + _className + ":" + Ocupation3_Adena + " аден\" action=\"bypass _bbsQuestsOcupation " + _class.getId() + " 0\" width=170 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></td>";
					if(Ocupation3_COST_ITEM > 0)
					    result += "<td><button value=\"" + _className + ":" + Ocupation3_COST_ITEM + " " + COST_ITEM_NAME + "\" action=\"bypass _bbsQuestsOcupation " + _class.getId() + " 1\" width=170 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></td>";
					break;
			}
		}
		result += "</tr></table>";

		return result;
	}

	/**
	 * *****************************************************************************
	 */
	private static final String str_getSubClass = "<table><tr><td width=230><a action=\"bypass _bbsQuestsSubs 1\" <font color =\"f8c481\">Активировать сабы за " + Subclasses_COST_ITEM + " " + COST_GOLD_ITEM_ID_NAME + "</font></a></td></tr></table>";
	private static final String str_getSubClass_Level = "Для активации сабклассов вы должны достичь 75-ого уровня<br>";
	private static final String str_getNobless_Level = "<center>Что бы стать дворянином вы должны прокачать сабкласс до 75 уровня.<br1><button value=\"Назад\" action=\"bypass _bbsQuests 0\" width=128 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></center>";
	private static final String str_getNobless_Already = "<center><font color=\"f8c481\">Вы Дворянин.</font><br1><button value=\"Назад\" action=\"bypass _bbsQuests 0\" width=128 height=25 back=\"l2ui_ct1.button.button_df_small_down\" fore=\"l2ui_ct1.button.button_df_small\"></center>";

	private static String tableSubclasses(L2Player player)
	{
		if(!Config.ALT_GAME_SUBCLASS_WITHOUT_QUESTS && SUB_SELL_ENABLE == 1)
			{
				QuestState qs = player.getQuestState("_234_FatesWhisper");
		
				if(qs == null || !qs.isCompleted())
				return player.getLevel() < 75 ? str_getSubClass_Level : str_getSubClass;

				qs = player.getQuestState(player.getRace() == Race.kamael ? "_236_SeedsOfChaos" : "_235_MimirsElixir");
				if(qs == null || !qs.isCompleted())
				return player.getLevel() < 75 ? str_getSubClass_Level : str_getSubClass;
		
				return "Квест на саб пройден.<br>";
			}
		return " ";	
	}

	private static String tableNobless(L2Player player)
	{
		if(NOBL_SELL_ENABLE == 1)
		{
			if(player.isNoble())
				return str_getNobless_Already;

			return "<table><tr><td width=230><a action=\"bypass _bbsQuestsNobless 1\" <font color =\"LEVEL\">Купить дворянство за " + Nobless_COST_ITEM + " "+ COST_ITEM_NAME + "</font></a></td></tr></table><table><tr><td width=230><a action=\"bypass _bbsQuestsNobless 0\" <font color =\"LEVEL\">Купить дворянство за Адену "+ Nobless_Adena + "</font></a></td></tr></table>";
		}
	return " ";	
	}

	private static String tableKarmaPK(L2Player player)
	{
		String result = "<table width=230><tr><td width=230><font color=FF0000>У вас ";
		if(player.getPkKills() > 0)
		{
			result += player.getPkKills() + " PK";
			if(player.getKarma() > 0)
				result += " и " + player.getKarma() + " кармы";
		}
		else if(player.getKarma() > 0)
			result += player.getKarma() + " кармы.</font>";
		else
			return "<table width=230><tr><td width=230>У вас нет грехов.</td></tr></table>";
		return "" + result + "&nbsp;<br1><a action=\"bypass _bbsQuestsSaveMySoul\"<font color =\"f8c481\">Искупить грехи за " + PK_ITEM_COUNT * player.getPkKills() + " "+ COST_ITEM_NAME + "</font></a></td></tr></table>";
	}

	private static String FullPage(L2Player player)
	{
		String tpl = Files.read("data/scripts/services/community/html/quests.htm", player, false);
		tpl = tpl.replace("%content%", tableOcupation(player));
		tpl = tpl.replace("%ocupationTab%", tableOcupationCurrent(player));
		tpl = tpl.replace("%karmaTab%", tableKarmaPK(player));
		return  tpl;
	}

	private static String tableOcupationCurrent(L2Player player)
	{
		ClassId playerClass = player.getClassId();
		String playerClassName = CharTemplateTable.charClasses[playerClass.getId()];
		String result = "";
		result += "" + tableSubclasses(player) + "<br0>";
		result += "" + tableNobless(player) + "<br1>";
		if(player == null)
			return "";
		return result;
	}
}