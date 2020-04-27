package quests._368_TrespassingIntoTheSacredArea;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _368_TrespassingIntoTheSacredArea extends Quest
{
	//NPCs
	private static int RESTINA = 30926;
	//Items
	private static int BLADE_STAKATO_FANG = 5881;
	private static int ADENA = 57;
	//Chances
	private static int BLADE_STAKATO_FANG_BASECHANCE = 10;

	public _368_TrespassingIntoTheSacredArea()
	{
		super(368, "_368_TrespassingIntoTheSacredArea", "Trespassing Into The Sacred Area");
		addStartNpc(RESTINA);
		for(int Blade_Stakato_id = 20794; Blade_Stakato_id <= 20797; Blade_Stakato_id++)
			addKillId(Blade_Stakato_id);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != RESTINA)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 36)
			{
				htmltext = "30926-00.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30926-01.htm";
				st.set("cond", "0");
			}
		}
		else
		{
			long _count = st.getQuestItemsCount(BLADE_STAKATO_FANG);
			if(_count > 0)
			{
				htmltext = "30926-04.htm";
				st.takeItems(BLADE_STAKATO_FANG, -1);
				st.rollAndGive(ADENA, _count * 2250, 100);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "30926-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30926-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30926-05.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getCond() == 1 && st.rollAndGive(BLADE_STAKATO_FANG, 1, Rnd.get(npc.getNpcId() - 20794 + BLADE_STAKATO_FANG_BASECHANCE)))
			st.playSound(SOUND_ITEMGET);
	}
}
