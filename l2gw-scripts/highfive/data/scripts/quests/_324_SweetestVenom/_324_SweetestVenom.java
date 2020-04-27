package quests._324_SweetestVenom;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _324_SweetestVenom extends Quest
{
	//NPCs
	private static int ASTARON = 30351;
	//Mobs
	private static int Prowler = 20034;
	private static int Venomous_Spider = 20038;
	private static int Arachnid_Tracker = 20043;
	//Items
	private static int VENOM_SAC = 1077;
	private static int ADENA = 57;
	//Chances
	private static int VENOM_SAC_BASECHANCE = 60;

	public _324_SweetestVenom()
	{
		super(324, "_324_SweetestVenom", "Sweetest Venom");
		addStartNpc(ASTARON);
		addKillId(Prowler);
		addKillId(Venomous_Spider);
		addKillId(Arachnid_Tracker);
		addQuestItem(VENOM_SAC);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != ASTARON)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 18)
			{
				htmltext = "30351-03.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "30351-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			long _count = st.getQuestItemsCount(VENOM_SAC);
			if(_count >= 10)
			{
				htmltext = "30351-06.htm";
				st.takeItems(VENOM_SAC, -1);
				st.rollAndGive(ADENA, 5810, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30351-05.htm";
		}
		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30351-04.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		int _chance = VENOM_SAC_BASECHANCE + (npc.getNpcId() - Prowler) / 4 * 12;

		if(st.rollAndGiveLimited(VENOM_SAC, 1, _chance, 10))
		{
			if(st.getQuestItemsCount(VENOM_SAC) == 10)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}