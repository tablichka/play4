package quests._404_PathToWizard;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _404_PathToWizard extends Quest
{
	//npc
	public final int PARINA = 30391;
	public final int EARTH_SNAKE = 30409;
	public final int WASTELAND_LIZARDMAN = 30410;
	public final int FLAME_SALAMANDER = 30411;
	public final int WIND_SYLPH = 30412;
	public final int WATER_UNDINE = 30413;
	//mobs
	public final int RED_BEAR = 20021;
	public final int RATMAN_WARRIOR = 20359;
	public final int WATER_SEER = 27030;
	//items
	public final int MAP_OF_LUSTER_ID = 1280;
	public final int KEY_OF_FLAME_ID = 1281;
	public final int FLAME_EARING_ID = 1282;
	public final int BROKEN_BRONZE_MIRROR_ID = 1283;
	public final int WIND_FEATHER_ID = 1284;
	public final int WIND_BANGEL_ID = 1285;
	public final int RAMAS_DIARY_ID = 1286;
	public final int SPARKLE_PEBBLE_ID = 1287;
	public final int WATER_NECKLACE_ID = 1288;
	public final int RUST_GOLD_COIN_ID = 1289;
	public final int RED_SOIL_ID = 1290;
	public final int EARTH_RING_ID = 1291;
	public final int BEAD_OF_SEASON_ID = 1292;

	public _404_PathToWizard()
	{
		super(404, "_404_PathToWizard", "Path to Wizard");

		addStartNpc(PARINA);

		addTalkId(PARINA);
		addTalkId(EARTH_SNAKE);
		addTalkId(WASTELAND_LIZARDMAN);
		addTalkId(FLAME_SALAMANDER);
		addTalkId(WIND_SYLPH);
		addTalkId(WATER_UNDINE);

		addKillId(RED_BEAR);
		addKillId(RATMAN_WARRIOR);
		addKillId(WATER_SEER);

		addQuestItem(KEY_OF_FLAME_ID,
				MAP_OF_LUSTER_ID,
				WIND_FEATHER_ID,
				BROKEN_BRONZE_MIRROR_ID,
				SPARKLE_PEBBLE_ID,
				RAMAS_DIARY_ID,
				RED_SOIL_ID,
				RUST_GOLD_COIN_ID,
				FLAME_EARING_ID,
				WIND_BANGEL_ID,
				WATER_NECKLACE_ID,
				EARTH_RING_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x0a)
			{
				if(st.getPlayer().getClassId().getId() == 0x0b)
					htmltext = "30391-02a.htm";
				else
					htmltext = "30391-01.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(BEAD_OF_SEASON_ID) > 0)
			{
				htmltext = "30391-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30391-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30391-08.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("30410_1"))
			if(st.getQuestItemsCount(WIND_FEATHER_ID) < 1)
			{
				htmltext = "30410-03.htm";
				st.giveItems(WIND_FEATHER_ID, 1);
				st.set("cond", "6");
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == PARINA)
		{
			if(st.isCreated())
				htmltext = "30391-04.htm";
			else if(cond > 0 && st.getQuestItemsCount(FLAME_EARING_ID) < 1 | st.getQuestItemsCount(WIND_BANGEL_ID) < 1 | st.getQuestItemsCount(WATER_NECKLACE_ID) < 1 | st.getQuestItemsCount(EARTH_RING_ID) < 1)
				htmltext = "30391-05.htm";
			else if(cond > 0 && st.getQuestItemsCount(FLAME_EARING_ID) > 0 && st.getQuestItemsCount(WIND_BANGEL_ID) > 0 && st.getQuestItemsCount(WATER_NECKLACE_ID) > 0 && st.getQuestItemsCount(EARTH_RING_ID) > 0)
			{
				htmltext = "30391-06.htm";
				st.takeItems(FLAME_EARING_ID, st.getQuestItemsCount(FLAME_EARING_ID));
				st.takeItems(WIND_BANGEL_ID, st.getQuestItemsCount(WIND_BANGEL_ID));
				st.takeItems(WATER_NECKLACE_ID, st.getQuestItemsCount(WATER_NECKLACE_ID));
				st.takeItems(EARTH_RING_ID, st.getQuestItemsCount(EARTH_RING_ID));
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					if(st.getQuestItemsCount(BEAD_OF_SEASON_ID) < 1)
						st.giveItems(BEAD_OF_SEASON_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 23152);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 29850);
						else
							st.addExpAndSp(160267, 36548);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == FLAME_SALAMANDER)
		{
			if(cond > 0 && st.getQuestItemsCount(MAP_OF_LUSTER_ID) < 1 && st.getQuestItemsCount(FLAME_EARING_ID) < 1)
			{
				st.giveItems(MAP_OF_LUSTER_ID, 1);
				htmltext = "30411-01.htm";
				st.set("cond", "2");
			}
			else if(cond > 0 && st.getQuestItemsCount(MAP_OF_LUSTER_ID) > 0 && st.getQuestItemsCount(KEY_OF_FLAME_ID) < 1)
				htmltext = "30411-02.htm";
			else if(cond == 3 && st.getQuestItemsCount(MAP_OF_LUSTER_ID) > 0 && st.getQuestItemsCount(KEY_OF_FLAME_ID) > 0)
			{
				st.takeItems(KEY_OF_FLAME_ID, -1);
				st.takeItems(MAP_OF_LUSTER_ID, -1);
				if(st.getQuestItemsCount(FLAME_EARING_ID) < 1)
					st.giveItems(FLAME_EARING_ID, 1);
				htmltext = "30411-03.htm";
				st.set("cond", "4");
			}
			else if(cond > 0 && st.getQuestItemsCount(FLAME_EARING_ID) > 0)
				htmltext = "30411-04.htm";
		}
		else if(npcId == WIND_SYLPH)
		{
			if(cond == 4 && st.getQuestItemsCount(FLAME_EARING_ID) > 0 && st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) < 1 && st.getQuestItemsCount(WIND_BANGEL_ID) < 1)
			{
				st.giveItems(BROKEN_BRONZE_MIRROR_ID, 1);
				htmltext = "30412-01.htm";
				st.set("cond", "5");
			}
			else if(cond > 0 && st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) > 0 && st.getQuestItemsCount(WIND_FEATHER_ID) < 1)
				htmltext = "30412-02.htm";
			else if(cond > 0 && st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) > 0 && st.getQuestItemsCount(WIND_FEATHER_ID) > 0)
			{
				st.takeItems(WIND_FEATHER_ID, st.getQuestItemsCount(WIND_FEATHER_ID));
				st.takeItems(BROKEN_BRONZE_MIRROR_ID, st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID));
				if(st.getQuestItemsCount(WIND_BANGEL_ID) < 1)
					st.giveItems(WIND_BANGEL_ID, 1);
				htmltext = "30412-03.htm";
				st.set("cond", "7");
			}
			else if(cond > 0 && st.getQuestItemsCount(WIND_BANGEL_ID) > 0)
				htmltext = "30412-04.htm";
		}
		else if(npcId == WASTELAND_LIZARDMAN)
		{
			if(cond > 0 && st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) > 0 && st.getQuestItemsCount(WIND_FEATHER_ID) < 1)
				htmltext = "30410-01.htm";
			else if(cond > 0 && st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) > 0 && st.getQuestItemsCount(WIND_FEATHER_ID) > 0)
				htmltext = "30410-04.htm";
		}
		else if(npcId == WATER_UNDINE)
		{
			if(cond == 7 && st.getQuestItemsCount(WIND_BANGEL_ID) > 0 && st.getQuestItemsCount(RAMAS_DIARY_ID) < 1 && st.getQuestItemsCount(WATER_NECKLACE_ID) < 1)
			{
				st.giveItems(RAMAS_DIARY_ID, 1);
				htmltext = "30413-01.htm";
				st.set("cond", "8");
			}
			else if(cond > 0 && st.getQuestItemsCount(RAMAS_DIARY_ID) > 0 && st.getQuestItemsCount(SPARKLE_PEBBLE_ID) < 2)
				htmltext = "30413-02.htm";
			else if(cond == 9 && st.getQuestItemsCount(RAMAS_DIARY_ID) > 0 && st.getQuestItemsCount(SPARKLE_PEBBLE_ID) > 1)
			{
				st.takeItems(SPARKLE_PEBBLE_ID, -1);
				st.takeItems(RAMAS_DIARY_ID, -1);
				if(st.getQuestItemsCount(WATER_NECKLACE_ID) < 1)
					st.giveItems(WATER_NECKLACE_ID, 1);
				htmltext = "30413-03.htm";
				st.set("cond", "10");
			}
			else if(cond > 0 && st.getQuestItemsCount(WATER_NECKLACE_ID) > 0)
				htmltext = "30413-04.htm";
		}
		else if(npcId == EARTH_SNAKE)
			if(cond > 0 && st.getQuestItemsCount(WATER_NECKLACE_ID) > 0 && st.getQuestItemsCount(RUST_GOLD_COIN_ID) < 1 && st.getQuestItemsCount(EARTH_RING_ID) < 1)
			{
				st.giveItems(RUST_GOLD_COIN_ID, 1);
				htmltext = "30409-01.htm";
				st.set("cond", "11");
			}
			else if(cond > 0 && st.getQuestItemsCount(RUST_GOLD_COIN_ID) > 0 && st.getQuestItemsCount(RED_SOIL_ID) < 1)
				htmltext = "30409-02.htm";
			else if(cond == 12 && st.getQuestItemsCount(RUST_GOLD_COIN_ID) > 0 && st.getQuestItemsCount(RED_SOIL_ID) > 0)
			{
				st.takeItems(RED_SOIL_ID, st.getQuestItemsCount(RED_SOIL_ID));
				st.takeItems(RUST_GOLD_COIN_ID, st.getQuestItemsCount(RUST_GOLD_COIN_ID));
				if(st.getQuestItemsCount(EARTH_RING_ID) < 1)
					st.giveItems(EARTH_RING_ID, 1);
				htmltext = "30409-03.htm";
				st.set("cond", "13");
			}
			else if(cond > 0 && st.getQuestItemsCount(EARTH_RING_ID) > 0)
				htmltext = "30409-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == RATMAN_WARRIOR)
		{
			if(cond == 2)
			{
				st.giveItems(KEY_OF_FLAME_ID, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "3");
				st.setState(STARTED);
			}
		}
		else if(npcId == WATER_SEER)
		{
			if(cond == 8 && st.rollAndGiveLimited(SPARKLE_PEBBLE_ID, 1, 100, 2))
			{
				if(st.getQuestItemsCount(SPARKLE_PEBBLE_ID) == 2)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "9");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == RED_BEAR)
			if(cond == 11)
			{
				st.giveItems(RED_SOIL_ID, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "12");
				st.setState(STARTED);
			}
	}
}