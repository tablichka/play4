package quests._316_DestroyPlaguebringers;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _316_DestroyPlaguebringers extends Quest
{
	//NPCs
	private static int Ellenia = 30155;
	//Mobs
	private static int Sukar_Wererat = 20040;
	private static int Sukar_Wererat_Leader = 20047;
	private static int Varool_Foulclaw = 27020;
	//Items
	private static int ADENA = 57;
	//Quest Items
	private static int Wererats_Fang = 1042;
	private static int Varool_Foulclaws_Fang = 1043;
	//Chances
	private static int Wererats_Fang_Chance = 50;
	private static int Varool_Foulclaws_Fang_Chance = 30;

	public _316_DestroyPlaguebringers()
	{
		super(316, "_316_DestroyPlaguebringers", "Destroy Plaguebringers");
		addStartNpc(Ellenia);
		addKillId(Sukar_Wererat);
		addKillId(Sukar_Wererat_Leader);
		addKillId(Varool_Foulclaw);
		addQuestItem(Wererats_Fang);
		addQuestItem(Varool_Foulclaws_Fang);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30155-04.htm") && st.isCreated() && st.getPlayer().getRace().ordinal() == 1 && st.getPlayer().getLevel() >= 18)
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30155-08.htm") && st.isStarted())
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
		if(npc.getNpcId() != Ellenia)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getRace().ordinal() != 1)
			{
				htmltext = "30155-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30155-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30155-03.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
		{
			long Reward = st.getQuestItemsCount(Wererats_Fang) * 60 + st.getQuestItemsCount(Varool_Foulclaws_Fang) * 10000;
			if(Reward > 0)
			{
				htmltext = "30155-07.htm";
				st.takeItems(Wererats_Fang, -1);
				st.takeItems(Varool_Foulclaws_Fang, -1);
				st.rollAndGive(ADENA, Reward, 100);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "30155-05.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(npc.getNpcId() == Varool_Foulclaw && st.rollAndGiveLimited(Varool_Foulclaws_Fang, 1, Varool_Foulclaws_Fang_Chance, 1))
			st.playSound(SOUND_ITEMGET);
		else if(st.rollAndGive(Wererats_Fang, 1, Wererats_Fang_Chance))
			st.playSound(SOUND_ITEMGET);
	}
}