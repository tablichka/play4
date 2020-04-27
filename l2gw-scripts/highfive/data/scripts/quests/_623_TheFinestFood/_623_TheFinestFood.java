package quests._623_TheFinestFood;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _623_TheFinestFood extends Quest
{
	public final int JEREMY = 31521;

	public static final int HOT_SPRINGS_BUFFALO = 21315;
	public static final int HOT_SPRINGS_FLAVA = 21316;
	public static final int HOT_SPRINGS_ANTELOPE = 21318;

	public static final int LEAF_OF_FLAVA = 7199;
	public static final int BUFFALO_MEAT = 7200;
	public static final int ANTELOPE_HORN = 7201;
	public static final int ADENA = 57;

	public _623_TheFinestFood()
	{
		super(623, "_623_TheFinestFood", "The Finest Food"); // party true

		addStartNpc(JEREMY);

		addTalkId(JEREMY);

		addKillId(HOT_SPRINGS_BUFFALO);
		addKillId(HOT_SPRINGS_FLAVA);
		addKillId(HOT_SPRINGS_ANTELOPE);

		addQuestItem(BUFFALO_MEAT, LEAF_OF_FLAVA, ANTELOPE_HORN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("31521-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31521-04.htm"))
		{
			st.takeItems(LEAF_OF_FLAVA, -1);
			st.takeItems(BUFFALO_MEAT, -1);
			st.takeItems(ANTELOPE_HORN, -1);
			st.rollAndGive(ADENA, 73000, 100);
			st.addExpAndSp(230000, 18250);
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCreated())
			st.set("cond", "0");
		// На случай любых ошибок, если предметы есть - квест все равно пройдется.
		if(summ(st) >= 300)
			st.set("cond", "2");
		int cond = st.getInt("cond");
		if(npcId == JEREMY)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 71)
					htmltext = "31521-01.htm";
				else
				{
					htmltext = "31521-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && summ(st) < 300)
				htmltext = "31521-02r.htm";
			else if(cond == 2 && summ(st) >= 300)
				htmltext = "31521-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pm = new GArray<QuestState>();
		int npcId = npc.getNpcId();
		int item = -1;

		if(npcId == HOT_SPRINGS_BUFFALO)
			item = BUFFALO_MEAT;
		else if(npcId == HOT_SPRINGS_FLAVA)
			item = LEAF_OF_FLAVA;
		else if(npcId == HOT_SPRINGS_ANTELOPE)
			item = ANTELOPE_HORN;

		if(item == -1)
			return;

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
			if(st.getQuestItemsCount(item) < 100)
				pm.add(st);

		if(pm.isEmpty())
			return;


		QuestState st = pm.get(Rnd.get(pm.size()));
		if(st.rollAndGiveLimited(item, 1, 100, 100))
			st.playSound(st.getQuestItemsCount(item) == 100 ? SOUND_MIDDLE : SOUND_ITEMGET);

		if(summ(st) == 300)
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}

	private long summ(QuestState st)
	{
		return st.getQuestItemsCount(LEAF_OF_FLAVA) + st.getQuestItemsCount(BUFFALO_MEAT) + st.getQuestItemsCount(ANTELOPE_HORN);
	}
}