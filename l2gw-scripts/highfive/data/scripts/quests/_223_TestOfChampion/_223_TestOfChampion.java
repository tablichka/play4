package quests._223_TestOfChampion;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _223_TestOfChampion extends Quest
{
	//item
	private static final int MARK_OF_CHAMPION_ID = 3276;
	private static final int ASCALONS_LETTER1_ID = 3277;
	private static final int MASONS_LETTER_ID = 3278;
	private static final int IRON_ROSE_RING_ID = 3279;
	private static final int ASCALONS_LETTER2_ID = 3280;
	private static final int WHITE_ROSE_INSIGNIA_ID = 3281;
	private static final int GROOTS_LETTER_ID = 3282;
	private static final int ASCALONS_LETTER3_ID = 3283;
	private static final int MOUENS_ORDER1_ID = 3284;
	private static final int MOUENS_ORDER2_ID = 3285;
	private static final int MOUENS_LETTER_ID = 3286;
	private static final int HARPYS_EGG1_ID = 3287;
	private static final int MEDUSA_VENOM1_ID = 3288;
	private static final int WINDSUS_BILE1_ID = 3289;
	private static final int BLOODY_AXE_HEAD_ID = 3290;
	private static final int ROAD_RATMAN_HEAD_ID = 3291;
	private static final int LETO_LIZARDMAN_FANG1_ID = 3292;
	//NPC
	private static final int Ascalon = 30624;
	private static final int Groot = 30093;
	private static final int Mouen = 30196;
	private static final int Mason = 30625;
	private static final int RewardExp = 635371;
	private static final int RewardSP = 43600;
	private static final int RewardAdena = 114882;

	public _223_TestOfChampion()
	{
		super(223, "_223_TestOfChampion", "Test Of Champion");
		addStartNpc(Ascalon);
		addTalkId(Groot);
		addTalkId(Mouen);
		addTalkId(Mason);

		addKillId(new int[]{20145, 20158, 27088, 27089, 20551, 20553, 20577, 20578, 20579, 20580, 20581, 20582, 20780});

		addQuestItem(MASONS_LETTER_ID,
				MEDUSA_VENOM1_ID,
				WINDSUS_BILE1_ID,
				WHITE_ROSE_INSIGNIA_ID,
				HARPYS_EGG1_ID,
				GROOTS_LETTER_ID,
				MOUENS_LETTER_ID,
				ASCALONS_LETTER1_ID,
				IRON_ROSE_RING_ID,
				BLOODY_AXE_HEAD_ID,
				ASCALONS_LETTER2_ID,
				ASCALONS_LETTER3_ID,
				MOUENS_ORDER1_ID,
				ROAD_RATMAN_HEAD_ID,
				MOUENS_ORDER2_ID,
				LETO_LIZARDMAN_FANG1_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "30624-06.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(7562, 64);
				st.getPlayer().setVar("dd3", "1");
			}
			st.playSound(SOUND_ACCEPT);
			st.giveItems(ASCALONS_LETTER1_ID, 1);
		}
		else if(event.equals("30624_1"))
			htmltext = "30624-05.htm";
		else if(event.equals("30624_2"))
		{
			htmltext = "30624-10.htm";
			st.takeItems(MASONS_LETTER_ID, st.getQuestItemsCount(MASONS_LETTER_ID));
			st.giveItems(ASCALONS_LETTER2_ID, 1);
		}
		else if(event.equals("30624_3"))
		{
			htmltext = "30624-14.htm";
			st.takeItems(GROOTS_LETTER_ID, st.getQuestItemsCount(GROOTS_LETTER_ID));
			st.giveItems(ASCALONS_LETTER3_ID, 1);
		}
		else if(event.equals("30625_1"))
			htmltext = "30625-02.htm";
		else if(event.equals("30625_2"))
		{
			htmltext = "30625-03.htm";
			st.takeItems(ASCALONS_LETTER1_ID, st.getQuestItemsCount(ASCALONS_LETTER1_ID));
			st.giveItems(IRON_ROSE_RING_ID, 1);
		}
		else if(event.equals("30093_1"))
		{
			htmltext = "30093-02.htm";
			st.takeItems(ASCALONS_LETTER2_ID, st.getQuestItemsCount(ASCALONS_LETTER2_ID));
			st.giveItems(WHITE_ROSE_INSIGNIA_ID, 1);
		}
		else if(event.equals("30196_1"))
			htmltext = "30196-02.htm";
		else if(event.equals("30196_2"))
		{
			htmltext = "30196-03.htm";
			st.takeItems(ASCALONS_LETTER3_ID, st.getQuestItemsCount(ASCALONS_LETTER3_ID));
			st.giveItems(MOUENS_ORDER1_ID, 1);
		}
		else if(event.equals("30196_3"))
		{
			htmltext = "30196-06.htm";
			st.takeItems(MOUENS_ORDER1_ID, st.getQuestItemsCount(MOUENS_ORDER1_ID));
			st.takeItems(ROAD_RATMAN_HEAD_ID, st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID));
			st.giveItems(MOUENS_ORDER2_ID, 1);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(st.getQuestItemsCount(MARK_OF_CHAMPION_ID) > 0)
		{
			st.exitCurrentQuest(true);
			return "completed";
		}
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(npcId == Ascalon && st.getInt("cond") == 0)
		{
			int class_id = st.getPlayer().getClassId().getId();
			if(class_id != 0x01 && class_id != 0x2d)
			{
				st.exitCurrentQuest(true);
				return "30624-01.htm";
			}
			if(st.getPlayer().getLevel() < 39)
			{
				st.exitCurrentQuest(true);
				return "30624-02.htm";
			}
			st.set("cond", "0");
			return class_id == 0x01 ? "30624-03.htm" : "30624-04.htm";
		}
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER1_ID) > 0)
			htmltext = "30624-07.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(IRON_ROSE_RING_ID) > 0)
			htmltext = "30624-08.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(MASONS_LETTER_ID) > 0)
			htmltext = "30624-09.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER2_ID) > 0)
			htmltext = "30624-11.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0)
			htmltext = "30624-12.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(GROOTS_LETTER_ID) > 0)
			htmltext = "30624-13.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER3_ID) > 0)
			htmltext = "30624-15.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && (st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0 || st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0))
			htmltext = "30624-16.htm";
		else if(npcId == Ascalon && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_LETTER_ID) > 0)
		{
			htmltext = "30624-17.htm";
			st.takeItems(MOUENS_LETTER_ID, -1);
			st.giveItems(MARK_OF_CHAMPION_ID, 1);
			if(!st.getPlayer().getVarB("q223"))
			{
				st.addExpAndSp(RewardExp, RewardSP);
				st.rollAndGive(57, RewardAdena, 100);
				st.getPlayer().setVar("q223", "1");
			}
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(false);
		}
		else if(npcId == Mason && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER1_ID) > 0)
			htmltext = "30625-01.htm";
		else if(npcId == Mason && st.getInt("cond") > 0 && st.getQuestItemsCount(IRON_ROSE_RING_ID) > 0 && st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) < 100)
			htmltext = "30625-04.htm";
		else if(npcId == Mason && st.getInt("cond") > 0 && st.getQuestItemsCount(IRON_ROSE_RING_ID) > 0 && st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) >= 100)
		{
			htmltext = "30625-05.htm";
			st.takeItems(BLOODY_AXE_HEAD_ID, st.getQuestItemsCount(BLOODY_AXE_HEAD_ID));
			st.takeItems(IRON_ROSE_RING_ID, st.getQuestItemsCount(IRON_ROSE_RING_ID));
			st.giveItems(MASONS_LETTER_ID, 1);
		}
		else if(npcId == Mason && st.getInt("cond") > 0 && st.getQuestItemsCount(MASONS_LETTER_ID) > 0)
			htmltext = "30625-06.htm";
		else if(npcId == Mason && st.getInt("cond") > 0 && (st.getQuestItemsCount(ASCALONS_LETTER2_ID) > 0 || st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0 || st.getQuestItemsCount(ASCALONS_LETTER2_ID) > 0 || st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0 || st.getQuestItemsCount(GROOTS_LETTER_ID) > 0 || st.getQuestItemsCount(ASCALONS_LETTER3_ID) > 0 || st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0 || st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0 || st.getQuestItemsCount(MOUENS_LETTER_ID) > 0 || st.getQuestItemsCount(GROOTS_LETTER_ID) > 0))
			htmltext = "30625-07.htm";
		else if(npcId == Groot && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER2_ID) > 0)
			htmltext = "30093-01.htm";
		else if(npcId == Groot && st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0 && (st.getQuestItemsCount(HARPYS_EGG1_ID) < 30 || st.getQuestItemsCount(MEDUSA_VENOM1_ID) < 30 || st.getQuestItemsCount(WINDSUS_BILE1_ID) < 30))
			htmltext = "30093-03.htm";
		else if(npcId == Groot && st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0 && st.getQuestItemsCount(HARPYS_EGG1_ID) >= 30 && st.getQuestItemsCount(MEDUSA_VENOM1_ID) >= 30 && st.getQuestItemsCount(WINDSUS_BILE1_ID) >= 30)
		{
			htmltext = "30093-04.htm";
			st.takeItems(WHITE_ROSE_INSIGNIA_ID, st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID));
			st.takeItems(HARPYS_EGG1_ID, st.getQuestItemsCount(HARPYS_EGG1_ID));
			st.takeItems(MEDUSA_VENOM1_ID, st.getQuestItemsCount(MEDUSA_VENOM1_ID));
			st.takeItems(WINDSUS_BILE1_ID, st.getQuestItemsCount(WINDSUS_BILE1_ID));
			st.giveItems(GROOTS_LETTER_ID, 1);
		}
		else if(npcId == Groot && st.getInt("cond") > 0 && st.getQuestItemsCount(GROOTS_LETTER_ID) > 0)
			htmltext = "30093-05.htm";
		else if(npcId == Groot && st.getInt("cond") > 0 && (st.getQuestItemsCount(ASCALONS_LETTER3_ID) > 0 || st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0 || st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0 || st.getQuestItemsCount(MOUENS_LETTER_ID) > 0))
			htmltext = "30093-06.htm";
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(ASCALONS_LETTER3_ID) > 0)
			htmltext = "30196-01.htm";
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0 && st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) < 100)
			htmltext = "30196-04.htm";
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0 && st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) >= 100)
			htmltext = "30196-05.htm";
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0 && st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100)
			htmltext = "30196-07.htm";
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0 && st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) >= 100)
		{
			htmltext = "30196-08.htm";
			st.takeItems(MOUENS_ORDER2_ID, st.getQuestItemsCount(MOUENS_ORDER2_ID));
			st.takeItems(LETO_LIZARDMAN_FANG1_ID, st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID));
			st.giveItems(MOUENS_LETTER_ID, 1);
		}
		else if(npcId == Mouen && st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_LETTER_ID) > 0)
			htmltext = "30196-09.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == 20780)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(IRON_ROSE_RING_ID) > 0)
				st.rollAndGiveLimited(BLOODY_AXE_HEAD_ID, 1, 100, 100);
		}
		else if(npcId == 20145 || npcId == 27088)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0)
				st.rollAndGiveLimited(HARPYS_EGG1_ID, 1, 100, 30);
		}
		else if(npcId == 20158)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0)
				st.rollAndGiveLimited(MEDUSA_VENOM1_ID, 1, 50, 30);
		}
		else if(npcId == 20553)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) > 0)
				st.rollAndGiveLimited(WINDSUS_BILE1_ID, 1, 50, 30);
		}
		else if(npcId == 20551)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER1_ID) > 0)
				st.rollAndGiveLimited(ROAD_RATMAN_HEAD_ID, 1, 100, 100);
		}
		else if(npcId == 20577)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 50, 100);
		}
		else if(npcId == 20578)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 60, 100);
		}
		else if(npcId == 20579)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 70, 100);
		}
		else if(npcId == 20580)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 80, 100);
		}
		else if(npcId == 20581)
		{
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 90, 100);
		}
		else if(npcId == 20582)
			if(st.getInt("cond") > 0 && st.getQuestItemsCount(MOUENS_ORDER2_ID) > 0)
				st.rollAndGiveLimited(LETO_LIZARDMAN_FANG1_ID, 1, 100, 100);
	}
}