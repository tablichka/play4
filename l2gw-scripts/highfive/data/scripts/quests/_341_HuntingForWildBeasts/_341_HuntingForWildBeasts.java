package quests._341_HuntingForWildBeasts;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _341_HuntingForWildBeasts extends Quest
{
	//NPCs
	private static int PANO = 30078;
	//Mobs
	private static int Red_Bear = 20021;
	private static int Dion_Grizzly = 20203;
	private static int Brown_Bear = 20310;
	private static int Grizzly_Bear = 20335;
	//Quest Items
	private static int BEAR_SKIN = 4259;
	//Items
	private static int ADENA = 57;
	//Chances
	private static int BEAR_SKIN_CHANCE = 40;

	public _341_HuntingForWildBeasts()
	{
		super(341, "_341_HuntingForWildBeasts", "Hunting For Wild Beasts");
		addStartNpc(PANO);
		addKillId(Red_Bear);
		addKillId(Dion_Grizzly);
		addKillId(Brown_Bear);
		addKillId(Grizzly_Bear);
		addQuestItem(BEAR_SKIN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30078-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != PANO)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 20)
			{
				htmltext = "30078-01.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "30078-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			if(st.getQuestItemsCount(BEAR_SKIN) >= 20)
			{
				htmltext = "30078-04.htm";
				st.takeItems(BEAR_SKIN, -1);
				st.rollAndGive(ADENA, 3710, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30078-03.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getCond() == 1 && st.rollAndGiveLimited(BEAR_SKIN, 1, BEAR_SKIN_CHANCE, 20))
		{
			if(st.getQuestItemsCount(BEAR_SKIN) == 20)
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