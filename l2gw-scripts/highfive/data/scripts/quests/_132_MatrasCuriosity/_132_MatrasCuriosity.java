package quests._132_MatrasCuriosity;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @see http://www.linedia.ru/wiki/Matras'_Curiosity
 */
public class _132_MatrasCuriosity extends Quest
{
	// npc
	private static final int Matras = 32245;

	// monster
	private static final int Ranku = 25542;
	private static final int Demon_Prince = 25540;

	// quest items
	private static final int Rankus_Blueprint = 9800;
	private static final int Demon_Princes_Blueprint = 9801;

	// items
	private static final int Rough_Ore_of_Fire = 10521;
	private static final int Rough_Ore_of_Water = 10522;
	private static final int Rough_Ore_of_Earth = 10523;
	private static final int Rough_Ore_of_Wind = 10524;
	private static final int Rough_Ore_of_Darkness = 10525;
	private static final int Rough_Ore_of_Divinity = 10526;

	public _132_MatrasCuriosity()
	{
		super(132, "_132_MatrasCuriosity", "Matras Curiosity");

		addStartNpc(Matras);

		addKillId(Ranku);
		addKillId(Demon_Prince);

		addQuestItem(Rankus_Blueprint, Demon_Princes_Blueprint);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:completed";

		if(event.equalsIgnoreCase("32245-03.htm"))
		{
			if(st.isCreated())
			{
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				if(st.getPlayer().getVarInt("q132_Rough_Ore_is_given") == 0)
				{
					st.giveItems(Rough_Ore_of_Fire, 1);
					st.giveItems(Rough_Ore_of_Water, 1);
					st.giveItems(Rough_Ore_of_Earth, 1);
					st.giveItems(Rough_Ore_of_Wind, 1);
					st.giveItems(Rough_Ore_of_Darkness, 1);
					st.giveItems(Rough_Ore_of_Divinity, 1);
					st.getPlayer().setVar("q132_Rough_Ore_is_given", 1);
				}
				else
					return "npchtm:32245-03a.htm";
			}
			else
				return "npchtm:32245-03a.htm";
		}
		else if(event.equalsIgnoreCase("32245-07.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.rollAndGive(57, 31210, 100);
			st.exitCurrentQuest(false);
		}

		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:completed";

		if(npc.getNpcId() == Matras)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 76)
				{
					st.exitCurrentQuest(true);
					return "npchtm:32245-02.htm";
				}
				return "32245-01.htm";
			}
			if(st.getCond() == 1)
				return "npchtm:32245-04.htm";
			if(st.getCond() == 2)
			{
				st.takeItems(Rankus_Blueprint, 1);
				st.takeItems(Demon_Princes_Blueprint, 1);
				st.setCond(3);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:32245-05.htm";
			}
			if(st.getCond() == 3)
				return "npchtm:32245-06.htm";
		}
		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> list = getPartyMembersWithQuest(killer, 1);
		if(list.size() > 0)
			for(QuestState qs : list)
			{
				if(npc.getNpcId() == Ranku && qs.getQuestItemsCount(Rankus_Blueprint) < 1)
				{
					qs.playSound(SOUND_ITEMGET);
					qs.giveItems(Rankus_Blueprint, 1);
				}
				else if(npc.getNpcId() == Demon_Prince && qs.getQuestItemsCount(Demon_Princes_Blueprint) < 1)
				{
					qs.playSound(SOUND_ITEMGET);
					qs.giveItems(Demon_Princes_Blueprint, 1);
				}

				if(qs.getQuestItemsCount(Rankus_Blueprint) > 0 && qs.getQuestItemsCount(Demon_Princes_Blueprint) > 0)
				{
					qs.setCond(2);
					qs.setState(STARTED);
					qs.playSound(SOUND_MIDDLE);
				}
			}
	}
}