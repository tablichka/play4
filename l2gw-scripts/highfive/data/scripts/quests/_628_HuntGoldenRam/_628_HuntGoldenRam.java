package quests._628_HuntGoldenRam;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _628_HuntGoldenRam extends Quest
{
	//Npcs
	private static int KAHMAN = 31554;

	//Items
	private static int CHITIN = 7248; //Splinter Stakato Chitin
	private static int CHITIN2 = 7249; //Needle Stakato Chitin
	private static int RECRUIT = 7246; //Golden Ram Badge - Recruit
	private static int SOLDIER = 7247; //Golden Ram Badge - Soldier

	private static int CHANCE = 49; //Base dropchance of the Badges

	public _628_HuntGoldenRam()
	{
		super(628, "_628_HuntGoldenRam", "Hunt of the Golden Ram Mercenary Force");

		addStartNpc(KAHMAN);

		for(int npcId = 21508; npcId <= 21518; npcId++)
			addKillId(npcId);

		addQuestItem(CHITIN, CHITIN2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("31554-03a.htm"))
		{
			if(st.getQuestItemsCount(CHITIN) >= 100 && st.getInt("cond") == 1)
			{
				st.set("cond", "2");
				st.takeItems(CHITIN, 100);
				st.giveItems(RECRUIT, 1);
				htmltext = "31554-04.htm";
			}
		}
		else if(event.equalsIgnoreCase("31554-07.htm"))
		{
			st.playSound(SOUND_GIVEUP);
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");
		long chitin1 = st.getQuestItemsCount(CHITIN);
		long chitin2 = st.getQuestItemsCount(CHITIN2);

		if(st.isCompleted())
			htmltext = "31554-05a.htm";
		else if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 66)
			{
				htmltext = "31554-02.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "31554-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
		{
			if(chitin1 >= 100)
				htmltext = "31554-03.htm";
			else
				htmltext = "31554-03a.htm";
		}
		else if(cond == 2)
		{
			if(chitin1 >= 100 && chitin2 >= 100)
			{
				htmltext = "31554-05.htm";
				st.takeItems(CHITIN, -1);
				st.takeItems(CHITIN2, -1);
				st.takeItems(RECRUIT, -1);
				st.giveItems(SOLDIER, 1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else if(chitin1 == 0 && chitin2 == 0)
				htmltext = "31554-04b.htm";
			else
				htmltext = "31554-04a.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(cond >= 1 && 21507 < npcId && npcId < 21513)
		{
			if(st.rollAndGiveLimited(CHITIN, 1, CHANCE + (npcId - 21506) * 2, 100))
			{
				if(st.getQuestItemsCount(CHITIN) < 100)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(cond == 2 && 21513 <= npcId && npcId <= 21518 && st.rollAndGiveLimited(CHITIN2, 1, CHANCE + (npcId - 21512) * 3, 100))
		{
			if(st.getQuestItemsCount(CHITIN2) < 100)
				st.playSound(SOUND_ITEMGET);
			else
				st.playSound(SOUND_MIDDLE);
		}
	}
}