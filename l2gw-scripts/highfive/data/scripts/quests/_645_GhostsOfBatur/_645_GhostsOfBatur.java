package quests._645_GhostsOfBatur;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _645_GhostsOfBatur extends Quest
{
	//Npc
	private static final int Karuda = 32017;
	//Items
	private static final int CursedGraveGoods = 14861;
	//Rewards
	private static final int[][] REWARDS = {{9628, 1, 8}, {9629, 1, 15}, {9630, 1, 12}, {9967, 1, 500}, {9968, 1, 500}, {9969, 1, 500}, {9970, 1, 500},
			{9971, 1, 500}, {9972, 1, 500}, {9973, 1, 500}, {9974, 1, 500}, {9975, 1, 500}};
	//Mobs
	private static final int[] MOBS = {22703, 22704, 22705};

	public _645_GhostsOfBatur()
	{
		super(645, "_645_GhostsOfBatur", "Ghosts Of Batur");

		addStartNpc(Karuda);
		addTalkId(Karuda);
		addKillId(MOBS);
		addQuestItem(CursedGraveGoods);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32017-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}

		for(int i = 0; i < REWARDS.length; i++)
		{
			if(event.equalsIgnoreCase(String.valueOf(REWARDS[i][0])))
			{
				if(st.getQuestItemsCount(CursedGraveGoods) >= REWARDS[i][2])
				{
					st.takeItems(CursedGraveGoods, REWARDS[i][2]);
					st.giveItems(REWARDS[i][0], REWARDS[i][1]);
					htmltext = "32017-07.htm";
				}
				else
				{
					htmltext = "32017-04.htm";
				}
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 80)
			{
				htmltext = "32017-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "32017-01.htm";
		}
		else if(cond == 1 && st.getQuestItemsCount(CursedGraveGoods) < 8)
			htmltext = "32017-04.htm";
		else if(cond == 1)
			htmltext = "32017-05a.htm";
		else if(cond == 2)
			htmltext = "32017-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") >= 1 && st.rollAndGive(CursedGraveGoods, 1, 70))
			if(st.getCond() == 1 && st.getQuestItemsCount(CursedGraveGoods) >= 500)
			{
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else
				st.playSound(SOUND_ITEMGET);
	}
}