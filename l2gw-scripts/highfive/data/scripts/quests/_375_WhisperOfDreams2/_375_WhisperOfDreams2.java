package quests._375_WhisperOfDreams2;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _375_WhisperOfDreams2 extends Quest
{
	//NPCs
	private int MANAKIA = 30515;
	//Quest items
	private int MSTONE = 5887;
	private int K_HORN = 5888;
	private int CH_SKULL = 5889;
	//Reward
	private int[] REWARDS = {5348, 5352, 5350};
	//Mobs & Drop
	private int[][] DROPLIST = {{20624, CH_SKULL}, {20629, K_HORN}};

	public _375_WhisperOfDreams2()
	{
		super(375, "_375_WhisperOfDreams2", "Whisper Of Dreams - Part 2"); // Party true

		addStartNpc(MANAKIA);
		for(int[] e : DROPLIST)
		{
			addKillId(e[0]);
			addQuestItem(e[1]);
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30515-6.htm"))
		{
			if(st.isCreated())
			{
				st.takeItems(MSTONE, -1);
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("30515-7.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 60)
			{
				htmltext = "30515-2.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(MSTONE) < 1)
			{
				htmltext = "30515-3.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30515-1.htm";
		}
		else if(st.isStarted())
		{
			if(st.getQuestItemsCount(CH_SKULL) < 100 || st.getQuestItemsCount(K_HORN) < 100)
				htmltext = "30515-5.htm";
			else
			{
				st.takeItems(CH_SKULL, -1);
				st.takeItems(K_HORN, -1);
				int item = REWARDS[Rnd.get(REWARDS.length)];
				if(Config.ALT_100_RECIPES)
					item += 1;
				st.giveItems(item, 1);
				htmltext = "30515-4.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int item = -1;
		for(int[] e : DROPLIST)
			if(npc.getNpcId() == e[0])
				item = e[1];

		if(item == -1)
			return;

		GArray<QuestState> pm = new GArray<QuestState>();

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
		{
			if(st.getQuestItemsCount(item) < 100)
				pm.add(st);
		}

		if(!pm.isEmpty())
		{
			QuestState st = pm.get(Rnd.get(pm.size()));
			if(st.rollAndGiveLimited(item, 1, 100, 100))
				st.playSound(st.getQuestItemsCount(item) == 100 ? SOUND_MIDDLE : SOUND_ITEMGET);

		}

	}
}