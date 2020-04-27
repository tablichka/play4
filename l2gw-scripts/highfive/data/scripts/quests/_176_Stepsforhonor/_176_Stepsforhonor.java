package quests._176_Stepsforhonor;

import quests.TerritoryWar.TerritoryWarQuest;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 22.10.2010 1:30:53
 */
public class _176_Stepsforhonor extends Quest
{
	// NPCs
	private static final int RAPIDUS = 36479;

	// ITEMS
	private static final int CLOAK = 14603;

	public _176_Stepsforhonor()
	{
		super(176, "_176_Stepsforhonor", "Steps for honor");
		addStartNpc(RAPIDUS);
		addTalkId(RAPIDUS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equals("36479-02.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		L2Player player = st.getPlayer();

		if(npc.getNpcId() == RAPIDUS)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 80)
					return "npchtm:36479-01.htm";

				st.exitCurrentQuest(true);
				return "36479-00.htm";
			}
			if(st.isStarted())
			{
				if(TerritoryWarManager.getWar().isInProgress())
					return "npchtm:36479-tw.htm";

				if(cond == 1)
					return "npchtm:36479-03.htm";
				else if(cond == 2)
				{
					st.setCond(3);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
					return "npchtm:36479-04.htm";
				}
				else if(cond == 3)
					return "npchtm:36479-05.htm";
				else if(cond == 4)
				{
					st.setCond(5);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
					return "npchtm:36479-06.htm";
				}
				else if(cond == 5)
					return "npchtm:36479-07.htm";
				else if(cond == 6)
				{
					st.setCond(7);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
					return "npchtm:36479-08.htm";
				}
				else if(cond == 7)
					return "npchtm:36479-09.htm";
				else if(cond == 8)
				{
					st.giveItems(CLOAK, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:36479-10.htm";
				}
			}
			else if(st.isCompleted())
				return "npchtm:36479-11.htm";

		}
		return "noquest";
	}

	@Override
	public void onPlayerKill(L2Player killer, L2Player killed)
	{
		if(!TerritoryWarManager.getWar().isInProgress() || !TerritoryWarQuest.checkCondition(killer, killed))
			return;

		QuestState st = killer.getQuestState(getName());

		if(st != null)
		{
			int kills = st.getInt("kills") + 1;
			st.set("kills", kills);

			if(st.getCond() == 1 && kills >= 9)
			{
				st.set("kills", 0);
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else if(st.getCond() == 3 && kills >= 18)
			{
				st.set("kills", 0);
				st.setCond(4);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else if(st.getCond() == 5 && kills >= 27)
			{
				st.set("kills", 0);
				st.setCond(6);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else if(st.getCond() == 7 && kills >= 36)
			{
				st.set("kills", 0);
				st.setCond(8);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
	}

	@Override
	public void onPlayerKillParty(L2Player killer, L2Player killed, QuestState qs)
	{
		onPlayerKill(qs.getPlayer(), killed);
	}
}
