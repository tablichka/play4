package quests._626_ADarkTwilight;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _626_ADarkTwilight extends Quest
{
	//NPC
	private static final int Hierarch = 31517;
	//QuestItem
	private static int BloodOfSaint = 7169;

	public _626_ADarkTwilight()
	{
		super(626, "_626_ADarkTwilight", "A Dark Twilight"); // party true
		addStartNpc(Hierarch);
		for(int npcId = 21520; npcId <= 21542; npcId++)
			addKillId(npcId);
		addQuestItem(BloodOfSaint);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("31517-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31517-03-choose.htm"))
		{
			if(st.getQuestItemsCount(BloodOfSaint) < 300)
				htmltext = "31517-bug.htm";
		}
		else if(event.equalsIgnoreCase("rew_exp"))
		{
			st.takeItems(BloodOfSaint, -1);
			st.addExpAndSp(162773, 12500);
			htmltext = "31517-reward.htm";
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("rew_adena"))
		{
			st.takeItems(BloodOfSaint, -1);
			st.rollAndGive(57, 100000, 100);
			htmltext = "31517-reward.htm";
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(npcId == Hierarch)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 60)
				{
					htmltext = "31517-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31517-01.htm";
			}
			else if(cond == 1)
				htmltext = "31517-02-rep.htm";
			else if(cond == 2)
				htmltext = "31517-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pm = new GArray<QuestState>();

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
			if(st.getQuestItemsCount(BloodOfSaint) < 300)
				pm.add(st);

		if(pm.isEmpty())
			return;


		QuestState st = pm.get(Rnd.get(pm.size()));

		if(st.rollAndGiveLimited(BloodOfSaint, 1, 70, 300))
			st.playSound(st.getQuestItemsCount(BloodOfSaint) == 300 ? SOUND_MIDDLE : SOUND_ITEMGET);

		if(st.getQuestItemsCount(BloodOfSaint) == 300)
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}
}