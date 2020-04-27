package quests._275_BlackWingedSpies;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _275_BlackWingedSpies extends Quest
{
	// NPCs
	private static int Tantus = 30567;
	// Mobs
	private static int Darkwing_Bat = 20316;
	private static int Varangkas_Tracker = 27043;
	// Items
	private static int ADENA = 57;
	// Quest Items
	private static short Darkwing_Bat_Fang = 1478;
	private static short Varangkas_Parasite = 1479;
	// Chances
	private static int Varangkas_Parasite_Chance = 10;

	public _275_BlackWingedSpies()
	{
		super(275, "_275_BlackWingedSpies", "Black Winged Spies");
		addStartNpc(Tantus);
		addKillId(Darkwing_Bat);
		addKillId(Varangkas_Tracker);
		addQuestItem(Darkwing_Bat_Fang);
		addQuestItem(Varangkas_Parasite);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30567-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() != Tantus)
			return "noquest";

		if(st.isCreated())
		{
			if(st.getPlayer().getRace().ordinal() != 3)
			{
				st.exitCurrentQuest(true);
				return "30567-00.htm";
			}
			if(st.getPlayer().getLevel() < 11)
			{
				st.exitCurrentQuest(true);
				return "30567-01.htm";
			}
			st.set("cond", "0");
			return "30567-02.htm";
		}

		if(!st.isStarted())
			return "noquest";
		int cond = st.getInt("cond");

		if(st.getQuestItemsCount(Darkwing_Bat_Fang) < 70)
		{
			if(cond != 1)
				st.set("cond", "1");
			return "30567-04.htm";
		}
		if(cond == 2)
		{
			st.rollAndGive(ADENA, 4550, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			return "30567-05.htm";
		}
		return "noquest";
	}

	private static void spawn_Varangkas_Tracker(QuestState st)
	{
		if(st.getQuestItemsCount(Varangkas_Parasite) > 0)
		{
			st.takeItems(Varangkas_Parasite, -1);
			st.getPcSpawn().removeAllSpawn();
		}
		st.giveItems(Varangkas_Parasite, 1);
		st.getPcSpawn().addSpawn(Varangkas_Tracker);
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() != 1)
			return;

		int npcId = npc.getNpcId();

		if(npcId == Darkwing_Bat && st.getQuestItemsCount(Darkwing_Bat_Fang) < 70)
		{
			if(st.getQuestItemsCount(Darkwing_Bat_Fang) > 10 && st.getQuestItemsCount(Darkwing_Bat_Fang) < 65 && Rnd.chance(Varangkas_Parasite_Chance))
			{
				spawn_Varangkas_Tracker(st);
				return;
			}
			if(st.rollAndGiveLimited(Darkwing_Bat_Fang, 1, 100, 70))
			{
				if(st.getQuestItemsCount(Darkwing_Bat_Fang) == 70)
				{
					st.playSound(SOUND_MIDDLE);
					st.setCond(2);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == Varangkas_Tracker && st.getQuestItemsCount(Darkwing_Bat_Fang) < 70 && st.getQuestItemsCount(Varangkas_Parasite) > 0)
		{
			st.takeItems(Varangkas_Parasite, -1);
			if(st.rollAndGiveLimited(Darkwing_Bat_Fang, 5, 100, 70))
			{
				if(st.getQuestItemsCount(Darkwing_Bat_Fang) == 70)
				{
					st.playSound(SOUND_MIDDLE);
					st.setCond(2);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}