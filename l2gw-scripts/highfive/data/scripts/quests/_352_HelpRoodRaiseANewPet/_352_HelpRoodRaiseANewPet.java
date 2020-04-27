package quests._352_HelpRoodRaiseANewPet;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _352_HelpRoodRaiseANewPet extends Quest
{
	//NPCs
	private static int Rood = 31067;
	//Mobs
	private static int Lienrik = 20786;
	private static int Lienrik_Lad = 20787;
	//Items
	private static int ADENA = 57;
	//Quest Items
	private static int LIENRIK_EGG1 = 5860;
	private static int LIENRIK_EGG2 = 5861;
	//Chances
	private static int LIENRIK_EGG1_Chance = 30;
	private static int LIENRIK_EGG2_Chance = 7;

	public _352_HelpRoodRaiseANewPet()
	{
		super(352, "_352_HelpRoodRaiseANewPet", "Help Rood Raise A New Pet");
		addStartNpc(Rood);
		addKillId(Lienrik);
		addKillId(Lienrik_Lad);
		addQuestItem(LIENRIK_EGG1, LIENRIK_EGG2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("31067-04.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31067-09.htm") && st.isStarted())
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
		if(npc.getNpcId() != Rood)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 39)
			{
				htmltext = "31067-00.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "31067-01.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
		{
			long reward = st.getQuestItemsCount(LIENRIK_EGG1) * 209 + st.getQuestItemsCount(LIENRIK_EGG2) * 2050;
			if(reward > 0)
			{
				htmltext = "31067-08.htm";
				st.takeItems(LIENRIK_EGG1, -1);
				st.takeItems(LIENRIK_EGG2, -1);
				st.rollAndGive(ADENA, reward, 100);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31067-05.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted() || st.getCond() != 1)
			return;

		if(Rnd.chance(LIENRIK_EGG1_Chance))
		{
			st.rollAndGive(LIENRIK_EGG1, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
		else if(Rnd.chance(LIENRIK_EGG2_Chance))
		{
			st.rollAndGive(LIENRIK_EGG2, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
	}
}