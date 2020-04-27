package quests._700_CursedLife;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 21.10.2010 20:59:05
 */
public class _700_CursedLife extends Quest
{
	// NPCs
	private static final int ORBYU = 32560;

	// ITEMS
	private static final int SWALLOWED_SKULL = 13872;
	private static final int SWALLOWED_STERNUM = 13873;
	private static final int SWALLOWED_BONES = 13874;

	// MOBS
	private static final int[] MOBS = {22602, 22603, 22604, 22605};

	public _700_CursedLife()
	{
		super(700, "_700_CursedLife", "Cursed Life");

		addStartNpc(ORBYU);
		addTalkId(ORBYU);
		addQuestItem(SWALLOWED_SKULL, SWALLOWED_STERNUM, SWALLOWED_BONES);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("32560-03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32560-quit.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return "npchtm:" + htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(npc.getNpcId() == ORBYU)
		{
			QuestState first = st.getPlayer().getQuestState("_10273_GoodDayToFly");
			if(first != null && first.isCompleted() && st.isCreated() && st.getPlayer().getLevel() >= 75)
				htmltext = "32560-01.htm";
			else if(cond == 1)
			{
				long count1 = st.getQuestItemsCount(SWALLOWED_BONES);
				long count2 = st.getQuestItemsCount(SWALLOWED_STERNUM);
				long count3 = st.getQuestItemsCount(SWALLOWED_SKULL);
				if(count1 > 0 || count2 > 0 || count3 > 0)
				{
					long reward = count1 * 500 + count2 * 5000 + count3 * 50000;
					st.takeItems(SWALLOWED_BONES, -1);
					st.takeItems(SWALLOWED_STERNUM, -1);
					st.takeItems(SWALLOWED_SKULL, -1);
					st.giveItems(57, reward);
					st.playSound(SOUND_ITEMGET);
					htmltext = "npchtm:32560-06.htm";
				}
				else
					htmltext = "npchtm:32560-04.htm";
			}
			else if(cond == 0)
				htmltext = "npchtm:32560-00.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && contains(MOBS, npc.getNpcId()))
		{
			if(Rnd.chance(20))
			{
				if(st.rollAndGive(SWALLOWED_SKULL, 1, 5))
					st.playSound(SOUND_ITEMGET);
				else if(st.rollAndGive(SWALLOWED_STERNUM, 1, 20))
					st.playSound(SOUND_ITEMGET);
				else if(st.rollAndGive(SWALLOWED_BONES, 1, 20))
					st.playSound(SOUND_ITEMGET);
			}
			else if(st.rollAndGive(SWALLOWED_BONES, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
	}
}
