package quests._401_PathToWarrior;

import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _401_PathToWarrior extends Quest
{
	int AURON = 30010;
	int SIMPLON = 30253;

	int TRACKER_SKELETON = 20035;
	int POISON_SPIDER = 20038;
	int TRACKER_SKELETON_LD = 20042;
	int ARACHNID_TRACKER = 20043;

	int EINS_LETTER_ID = 1138;
	int WARRIOR_GUILD_MARK_ID = 1139;
	int RUSTED_BRONZE_SWORD1_ID = 1140;
	int RUSTED_BRONZE_SWORD2_ID = 1141;
	int SIMPLONS_LETTER_ID = 1143;
	int POISON_SPIDER_LEG2_ID = 1144;
	int MEDALLION_OF_WARRIOR_ID = 1145;
	int RUSTED_BRONZE_SWORD3_ID = 1142;

	public _401_PathToWarrior()
	{
		super(401, "_401_PathToWarrior", "Path to Warrior");

		addStartNpc(AURON);

		addTalkId(AURON, SIMPLON);

		addKillId(TRACKER_SKELETON);
		addKillId(POISON_SPIDER);
		addKillId(TRACKER_SKELETON_LD);
		addKillId(ARACHNID_TRACKER);

		addQuestItem(SIMPLONS_LETTER_ID,
				RUSTED_BRONZE_SWORD2_ID,
				EINS_LETTER_ID,
				WARRIOR_GUILD_MARK_ID,
				RUSTED_BRONZE_SWORD1_ID,
				POISON_SPIDER_LEG2_ID,
				RUSTED_BRONZE_SWORD3_ID);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("401_1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x00)
			{
				if(st.getPlayer().getClassId().getId() == 0x01)
					htmltext = "30010-02a.htm";
				else
					htmltext = "30010-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID) > 0)
			{
				htmltext = "30010-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30010-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30010-05.htm";
		}
		else if(event.equalsIgnoreCase("401_2"))
			htmltext = "30010-10.htm";
		else if(event.equalsIgnoreCase("401_3"))
		{
			htmltext = "30010-11.htm";
			st.takeItems(SIMPLONS_LETTER_ID, 1);
			st.takeItems(RUSTED_BRONZE_SWORD2_ID, 1);
			st.giveItems(RUSTED_BRONZE_SWORD3_ID, 1);
			st.set("cond", "5");
		}
		else if(event.equalsIgnoreCase("1"))
		{
			if(st.getQuestItemsCount(EINS_LETTER_ID) == 0)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.giveItems(EINS_LETTER_ID, 1);
				htmltext = "30010-06.htm";
			}
		}
		else if(event.equalsIgnoreCase("30253_1"))
		{
			htmltext = "30253-02.htm";
			st.takeItems(EINS_LETTER_ID, 1);
			st.giveItems(WARRIOR_GUILD_MARK_ID, 1);
			st.set("cond", "2");
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(npcId == AURON && st.isCreated())
			htmltext = "30010-01.htm";
		else if(npcId == AURON && st.getCond() == 1)
			htmltext = "30010-07.htm";
		else if(npcId == AURON && st.getCond() == 2)
			htmltext = "30010-08.htm";
		else if(npcId == SIMPLON && st.getCond() == 1)
			htmltext = "30253-01.htm";
		else if(npcId == SIMPLON && st.getCond() == 2)
		{
			if(st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID) < 1)
				htmltext = "30253-03.htm";
			else if(st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID) < 10)
				htmltext = "30253-04.htm";
		}
		else if(npcId == SIMPLON && st.getCond() == 3)
		{
			st.takeItems(WARRIOR_GUILD_MARK_ID, -1);
			st.takeItems(RUSTED_BRONZE_SWORD1_ID, -1);
			st.giveItems(RUSTED_BRONZE_SWORD2_ID, 1);
			st.giveItems(SIMPLONS_LETTER_ID, 1);
			st.set("cond", "4");
			htmltext = "30253-05.htm";
		}
		else if(npcId == SIMPLON && st.getCond() == 4)
			htmltext = "30253-06.htm";
		else if(npcId == AURON && st.getCond() == 4 && st.getQuestItemsCount(RUSTED_BRONZE_SWORD2_ID) > 0 && st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID) == 0 && st.getQuestItemsCount(EINS_LETTER_ID) == 0)
			htmltext = "30010-09.htm";
		else if(npcId == AURON && st.getQuestItemsCount(RUSTED_BRONZE_SWORD3_ID) > 0 && st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID) == 0 && st.getQuestItemsCount(EINS_LETTER_ID) == 0)
			if(st.getQuestItemsCount(POISON_SPIDER_LEG2_ID) < 20)
				htmltext = "30010-12.htm";
			else if(st.getQuestItemsCount(POISON_SPIDER_LEG2_ID) > 19)
			{
				st.takeItems(POISON_SPIDER_LEG2_ID, -1);
				st.takeItems(RUSTED_BRONZE_SWORD3_ID, -1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(MEDALLION_OF_WARRIOR_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 21012);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 27710);
						else
							st.addExpAndSp(160267, 34408);
						st.rollAndGive(57, 163800, 100);
					}
				}
				htmltext = "30010-13.htm";
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == TRACKER_SKELETON || npcId == TRACKER_SKELETON_LD)
		{
			if(cond == 2 && st.rollAndGiveLimited(RUSTED_BRONZE_SWORD1_ID, 1, 40, 10))
			{
				if(st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID) == 10)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "3");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == ARACHNID_TRACKER || npcId == POISON_SPIDER)
			if(cond == 5 && st.getQuestItemsCount(RUSTED_BRONZE_SWORD3_ID) == 1 && st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == RUSTED_BRONZE_SWORD3_ID && st.rollAndGiveLimited(POISON_SPIDER_LEG2_ID, 1, 100, 20))
			{
				if(st.getQuestItemsCount(POISON_SPIDER_LEG2_ID) == 20)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "6");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}