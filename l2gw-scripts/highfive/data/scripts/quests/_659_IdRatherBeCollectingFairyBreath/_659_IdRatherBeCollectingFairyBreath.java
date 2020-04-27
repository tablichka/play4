package quests._659_IdRatherBeCollectingFairyBreath;

// Created by Artful

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _659_IdRatherBeCollectingFairyBreath extends Quest
{
	//NPC
	public final int GALATEA = 30634;
	//Mobs
	public final int[] MOBS = {20078, 21026, 21025, 21024, 21023};
	//Quest Item
	public final int FAIRY_BREATH = 8286;
	//Item
	public final int ADENA = 57;

	public _659_IdRatherBeCollectingFairyBreath()
	{
		super(659, "_659_IdRatherBeCollectingFairyBreath", "I'd Rather Be Collecting Fairy Breath");

		addStartNpc(GALATEA);
		addTalkId(GALATEA);
		addKillId(MOBS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30634-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30634-06.htm"))
		{
			long count = st.getQuestItemsCount(FAIRY_BREATH);
			if(count > 0)
			{
				long reward = 0;
				if(count < 10)
					reward = count * 50;
				else
					reward = count * 50 + 5365;
				st.takeItems(FAIRY_BREATH, -1);
				st.rollAndGive(ADENA, reward, 100);
			}
		}
		else if(event.equalsIgnoreCase("30634-08.htm"))
			st.exitCurrentQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == GALATEA)
			if(st.getPlayer().getLevel() < 26)
			{
				htmltext = "30634-01.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
				htmltext = "30634-02.htm";
			else if(cond == 1)
				if(st.getQuestItemsCount(FAIRY_BREATH) == 0)
					htmltext = "30634-04.htm";
				else
					htmltext = "30634-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1)
			for(int i : MOBS)
				if(npcId == i && st.rollAndGive(FAIRY_BREATH, 1, 30))
				{
					st.giveItems(FAIRY_BREATH, 1);
					st.playSound(SOUND_ITEMGET);
				}
	}
}