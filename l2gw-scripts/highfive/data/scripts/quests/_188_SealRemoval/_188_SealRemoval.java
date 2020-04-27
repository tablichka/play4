package quests._188_SealRemoval;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 24.12.2010 15:38
 */
public class _188_SealRemoval extends Quest
{
	// NPCs
	private static final int Nikola = 30621;
	private static final int Lorain = 30673;
	private static final int Dorothy = 30970;

	// Items
	private static final int BrokenMetal = 10369;

	public _188_SealRemoval()
	{
		super(188, "_188_SealRemoval", "Seal Removal");
		addStartNpc(Lorain);
		addTalkId(Nikola, Lorain, Dorothy);
		addQuestItem(BrokenMetal);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();

		if(npcId == Lorain)
		{
			if(reply == 188 && st.isCreated() && player.getLevel() >= 41 && !st.haveQuestItems(10362) && (st.getPlayer().isQuestComplete(184) || st.getPlayer().isQuestComplete(185)) &&
					!st.getPlayer().isQuestComplete(186) && !st.getPlayer().isQuestComplete(187) && !st.getPlayer().isQuestStarted(187) && !st.getPlayer().isQuestStarted(186))
			{
				st.giveItems(BrokenMetal, 1);
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("researcher_lorain_q0188_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola)
			{
				if(reply == 1 && st.getMemoState() == 1)
					showPage("maestro_nikola_q0188_02.htm", player);
				else if(reply == 2 && st.getMemoState() == 1)
				{
					st.setMemoState(2);
					showPage("maestro_nikola_q0188_03.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 3 && st.getMemoState() == 2)
					showPage("maestro_nikola_q0188_04.htm", player);
			}
			else if(npcId == Dorothy && st.getMemoState() == 2)
			{
				if(reply == 1)
					showPage("dorothy_the_locksmith_q0188_02.htm", player);
				else if(reply == 2)
				{
					st.takeItems(BrokenMetal, -1);
					st.playSound(SOUND_FINISH);
					showPage("dorothy_the_locksmith_q0188_03.htm", player);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 98583, 100);
					if(player.getLevel() < 47)
						st.addExpAndSp(285935, 18711);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == Lorain)
		{
			if(st.isCreated() && !st.haveQuestItems(10362) && (st.getPlayer().isQuestComplete(184) || st.getPlayer().isQuestComplete(185)) &&
				!st.getPlayer().isQuestComplete(186) && !st.getPlayer().isQuestComplete(187) && !st.getPlayer().isQuestStarted(187) && !st.getPlayer().isQuestStarted(186))
			{
				if(st.getPlayer().getLevel() >= 41)
					return "researcher_lorain_q0188_01.htm";

				st.exitCurrentQuest(true);
				return "researcher_lorain_q0188_02.htm";
			}
			else if(st.isStarted())
				return "npchtm:researcher_lorain_q0188_04.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola)
			{
				if(cond == 1)
					return "npchtm:maestro_nikola_q0188_01.htm";
				if(cond == 2)
					return "npchtm:maestro_nikola_q0188_05.htm";
			}
			else if(npcId == Dorothy)
				if(cond == 2)
					return "npchtm:dorothy_the_locksmith_q0188_01.htm";
		}
		return "noquest";
	}
}

