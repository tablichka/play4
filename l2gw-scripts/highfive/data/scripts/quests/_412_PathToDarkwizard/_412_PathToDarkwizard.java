package quests._412_PathToDarkwizard;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _412_PathToDarkwizard extends Quest
{
	//npc
	public final int CHARKEREN = 30415;
	public final int ANNIKA = 30418;
	public final int ARKENIA = 30419;
	public final int VARIKA = 30421;
	//mobs
	public final int MARSH_ZOMBIE = 20015;
	public final int MARSH_ZOMBIE_LORD = 20020;
	public final int MISERY_SKELETON = 20022;
	public final int SKELETON_SCOUT = 20045;
	public final int SKELETON_HUNTER = 20517;
	public final int SKELETON_HUNTER_ARCHER = 20518;
	//items
	public final int SEEDS_OF_DESPAIR_ID = 1254;
	public final int SEEDS_OF_ANGER_ID = 1253;
	public final int SEEDS_OF_HORROR_ID = 1255;
	public final int SEEDS_OF_LUNACY_ID = 1256;
	public final int FAMILYS_ASHES_ID = 1257;
	public final int KNEE_BONE_ID = 1259;
	public final int HEART_OF_LUNACY_ID = 1260;
	public final int JEWEL_OF_DARKNESS_ID = 1261;
	public final int LUCKY_KEY_ID = 1277;
	public final int CANDLE_ID = 1278;
	public final int HUB_SCENT_ID = 1279;
	//DROPLIST [MOB_ID, REQUIRED, ITEM, NEED_COUNT]
	public final int[][] DROPLIST = {
			{20015, LUCKY_KEY_ID, FAMILYS_ASHES_ID, 3},
			{20020, LUCKY_KEY_ID, FAMILYS_ASHES_ID, 3},
			{20517, CANDLE_ID, KNEE_BONE_ID, 2},
			{20518, CANDLE_ID, KNEE_BONE_ID, 2},
			{20022, CANDLE_ID, KNEE_BONE_ID, 2},
			{20045, HUB_SCENT_ID, HEART_OF_LUNACY_ID, 3}};

	public _412_PathToDarkwizard()
	{
		super(412, "_412_PathToDarkwizard", "Path to Dark Wizard");

		addStartNpc(VARIKA);

		addTalkId(CHARKEREN);
		addTalkId(ANNIKA);
		addTalkId(ARKENIA);
		addTalkId(VARIKA);

		addQuestItem(SEEDS_OF_ANGER_ID,
				LUCKY_KEY_ID,
				SEEDS_OF_HORROR_ID,
				CANDLE_ID,
				SEEDS_OF_LUNACY_ID,
				HUB_SCENT_ID,
				SEEDS_OF_DESPAIR_ID,
				FAMILYS_ASHES_ID,
				KNEE_BONE_ID,
				HEART_OF_LUNACY_ID);

		for(int[] element : DROPLIST)
			addKillId(element[0]);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x26)
			{
				if(st.getPlayer().getClassId().getId() == 0x27)
					htmltext = "30421-02a.htm";
				else
					htmltext = "30421-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) > 0)
			{
				htmltext = "30421-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30421-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.giveItems(SEEDS_OF_DESPAIR_ID, 1);
				htmltext = "30421-05.htm";
			}
		}
		else if(event.equalsIgnoreCase("412_1"))
		{
			if(st.getQuestItemsCount(SEEDS_OF_ANGER_ID) > 0)
				htmltext = "30421-06.htm";
			else
				htmltext = "30421-07.htm";
		}
		else if(event.equalsIgnoreCase("412_2"))
		{
			if(st.getQuestItemsCount(SEEDS_OF_HORROR_ID) > 0)
				htmltext = "30421-09.htm";
			else
				htmltext = "30421-10.htm";
		}
		else if(event.equalsIgnoreCase("412_3"))
		{
			if(st.getQuestItemsCount(SEEDS_OF_LUNACY_ID) > 0)
				htmltext = "30421-12.htm";
			else if(st.getQuestItemsCount(SEEDS_OF_LUNACY_ID) < 1 && st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0)
				htmltext = "30421-13.htm";
		}
		else if(event.equalsIgnoreCase("412_4"))
		{
			htmltext = "30415-03.htm";
			st.giveItems(LUCKY_KEY_ID, 1);
		}
		else if(event.equalsIgnoreCase("30418_1"))
		{
			htmltext = "30418-02.htm";
			st.giveItems(CANDLE_ID, 1);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == VARIKA)
		{
			if(cond < 1)
			{
				if(st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) < 1)
					htmltext = "30421-01.htm";
				else
					htmltext = "30421-04.htm";
			}
			else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(SEEDS_OF_HORROR_ID) > 0 && st.getQuestItemsCount(SEEDS_OF_LUNACY_ID) > 0 && st.getQuestItemsCount(SEEDS_OF_ANGER_ID) > 0)
			{
				htmltext = "30421-16.htm";
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(JEWEL_OF_DARKNESS_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 28630);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 28630);
						else
							st.addExpAndSp(591724, 35328);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
			else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0)
				if(st.getQuestItemsCount(FAMILYS_ASHES_ID) < 1 && st.getQuestItemsCount(LUCKY_KEY_ID) < 1 && st.getQuestItemsCount(CANDLE_ID) < 1 && st.getQuestItemsCount(HUB_SCENT_ID) < 1 && st.getQuestItemsCount(KNEE_BONE_ID) < 1 && st.getQuestItemsCount(HEART_OF_LUNACY_ID) < 1)
					htmltext = "30421-17.htm";
				else if(st.getQuestItemsCount(SEEDS_OF_ANGER_ID) < 1)
					htmltext = "30421-08.htm";
				else if(st.getQuestItemsCount(SEEDS_OF_HORROR_ID) > 0)
					htmltext = "30421-19.htm";
				else if(st.getQuestItemsCount(HEART_OF_LUNACY_ID) < 1)
					htmltext = "30421-13.htm";
		}
		else if(npcId == ARKENIA && cond > 0 && st.getQuestItemsCount(SEEDS_OF_LUNACY_ID) < 1)
		{
			if(st.getQuestItemsCount(HUB_SCENT_ID) < 1 && st.getQuestItemsCount(HEART_OF_LUNACY_ID) < 1)
			{
				htmltext = "30419-01.htm";
				st.giveItems(HUB_SCENT_ID, 1);
			}
			else if(st.getQuestItemsCount(HUB_SCENT_ID) > 0 && st.getQuestItemsCount(HEART_OF_LUNACY_ID) < 3)
				htmltext = "30419-02.htm";
			else if(st.getQuestItemsCount(HUB_SCENT_ID) > 0 && st.getQuestItemsCount(HEART_OF_LUNACY_ID) >= 3)
			{
				htmltext = "30419-03.htm";
				st.giveItems(SEEDS_OF_LUNACY_ID, 1);
				st.takeItems(HEART_OF_LUNACY_ID, -1);
				st.takeItems(HUB_SCENT_ID, -1);
			}
		}
		else if(npcId == CHARKEREN && cond > 0)
		{
			if(st.getQuestItemsCount(SEEDS_OF_ANGER_ID) < 1)
			{
				if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(FAMILYS_ASHES_ID) < 1 && st.getQuestItemsCount(LUCKY_KEY_ID) < 1)
					htmltext = "30415-01.htm";
				else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(FAMILYS_ASHES_ID) < 3 && st.getQuestItemsCount(LUCKY_KEY_ID) > 0)
					htmltext = "30415-04.htm";
				else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(FAMILYS_ASHES_ID) >= 3 && st.getQuestItemsCount(LUCKY_KEY_ID) > 0)
				{
					htmltext = "30415-05.htm";
					st.giveItems(SEEDS_OF_ANGER_ID, 1);
					st.takeItems(FAMILYS_ASHES_ID, -1);
					st.takeItems(LUCKY_KEY_ID, -1);
				}
			}
			else
				htmltext = "30415-06.htm";
		}
		else if(npcId == ANNIKA && cond > 0 && st.getQuestItemsCount(SEEDS_OF_HORROR_ID) < 1)
			if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(CANDLE_ID) < 1 && st.getQuestItemsCount(KNEE_BONE_ID) < 1)
				htmltext = "30418-01.htm";
			else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(CANDLE_ID) > 0 && st.getQuestItemsCount(KNEE_BONE_ID) < 2)
				htmltext = "30418-03.htm";
			else if(st.getQuestItemsCount(SEEDS_OF_DESPAIR_ID) > 0 && st.getQuestItemsCount(CANDLE_ID) > 0 && st.getQuestItemsCount(KNEE_BONE_ID) >= 2)
			{
				htmltext = "30418-04.htm";
				st.giveItems(SEEDS_OF_HORROR_ID, 1);
				st.takeItems(CANDLE_ID, -1);
				st.takeItems(KNEE_BONE_ID, -1);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		//DROPLIST [MOB_ID, REQUIRED, ITEM, NEED_COUNT]
		for(int[] element : DROPLIST)
			if(st.getInt("cond") == 1 && npc.getNpcId() == element[0] && st.getQuestItemsCount(element[1]) > 0)
				if(st.rollAndGiveLimited(element[2], 1, 50, element[3]))
				{
					if(st.getQuestItemsCount(element[2]) == element[3])
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
	}
}