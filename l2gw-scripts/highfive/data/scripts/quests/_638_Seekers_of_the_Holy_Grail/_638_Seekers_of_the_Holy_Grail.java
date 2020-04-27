package quests._638_Seekers_of_the_Holy_Grail;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _638_Seekers_of_the_Holy_Grail extends Quest
{
	// NPCs
	private static final int Innocentin = 31328;

	// Mobs
	private static final int MOBS_START = 22139;
	private static final int MOBS_END = 22174;

	// Items
	private static short PaganTotem = 8068;

	public _638_Seekers_of_the_Holy_Grail()
	{
		super(638, "_638_Seekers_of_the_Holy_Grail", "Seekers of the Holy Grail"); // Party true
		addStartNpc(Innocentin);
		addQuestItem(PaganTotem);

		for(int i = MOBS_START; i <= MOBS_END; i++)
			addKillId(i);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31328-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("REWARD"))
		{
			htmltext = "31328-07.htm";
			st.takeItems(PaganTotem, 2000);
			if(Rnd.chance(40))
				st.rollAndGive(57, 3650000, 100);
			else
			{
				if(Rnd.chance(80))
					st.giveItems(960, 1);
				else
					st.giveItems(959, 1);
			}
			st.playSound(SOUND_ITEMGET);
		}
		else if(event.equalsIgnoreCase("31328-08.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";

		if(npcId == Innocentin)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 73)
				{
					st.exitCurrentQuest(true);
					htmltext = "31328-02.htm";
				}
				else
					htmltext = "31328-01.htm";
			}
			else if(st.isStarted())
			{
				if(st.getQuestItemsCount(PaganTotem) < 2000)
					htmltext = "31328-04.htm";
				else
					htmltext = "31328-05.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st == null)
			return;

		if(killer.getClanId() > 0 && killer.getClan().getHasCastle() == 8 && killer.getClan().getLeader().getPlayer() != null)
		{
			L2Player leader = killer.getClan().getLeader().getPlayer();
			QuestState qs = leader.getQuestState("_716_PathtoBecomingaLordRune");
			if(qs != null && qs.getInt("cond") == 5 && qs.getInt("q716assigned") == killer.getObjectId())
			{
				qs.getQuest().notifyEvent("cond5_" + killer.getObjectId(), qs);
			}
		}
		st.rollAndGive(PaganTotem, 1, 15 * npc.getTemplate().hp_mod);
	}
}