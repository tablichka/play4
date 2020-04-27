package quests._294_CovertBusiness;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _294_CovertBusiness extends Quest
{
	public static int BatFang = 1491;
	public static int RingOfRaccoon = 1508;
	public static int Adena = 57;

	public static int BarbedBat = 20370;
	public static int BladeBat = 20480;

	public static int Keef = 30534;

	public _294_CovertBusiness()
	{
		super(294, "_294_CovertBusiness", "Covert Business");

		addStartNpc(Keef);
		addTalkId(Keef);

		addKillId(BarbedBat);
		addKillId(BladeBat);

		addQuestItem(BatFang);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30534-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getRace().ordinal() != 4)
			{
				htmltext = "30534-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() >= 10)
			{
				htmltext = "30534-02.htm";
				return htmltext;
			}
			else
			{
				htmltext = "30534-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.getQuestItemsCount(BatFang) < 100)
			htmltext = "30534-04.htm";
		else if(st.getQuestItemsCount(RingOfRaccoon) < 1)
		{
			htmltext = "30534-05.htm";
			st.addExpAndSp(0, 60);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			st.giveItems(RingOfRaccoon, 1);
		}
		else
		{
			htmltext = "30534-06.htm";
			st.giveItems(Adena, 2400);
			st.addExpAndSp(0, 60);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") != 1)
			return;
		int npcId = npc.getNpcId();
		if(npcId == BarbedBat)
		{
			if(st.rollAndGiveLimited(BatFang, 2, 50, 100))
			{
				if(st.getQuestItemsCount(BatFang) == 100)
					st.playSound(SOUND_MIDDLE);
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == BladeBat)
		{
			if(st.rollAndGiveLimited(BatFang, 2, 60, 100))
			{
				if(st.getQuestItemsCount(BatFang) == 100)
					st.playSound(SOUND_MIDDLE);
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}