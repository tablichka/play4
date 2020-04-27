package quests._379_FantasyWine;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _379_FantasyWine extends Quest
{
	//NPC
	public final int HARLAN = 30074;
	//Mobs
	public final int Enku_Orc_Champion = 20291;
	public final int Enku_Orc_Shaman = 20292;
	//Quest Item
	public final int LEAF_OF_EUCALYPTUS = 5893;
	public final int STONE_OF_CHILL = 5894;
	//Item
	public final int[] REWARD = {5956, 5957, 5958};

	public _379_FantasyWine()
	{
		super(379, "_379_FantasyWine", "Fantasy Wine");

		addStartNpc(HARLAN);
		addTalkId(HARLAN);

		addKillId(Enku_Orc_Champion);
		addKillId(Enku_Orc_Shaman);

		addQuestItem(LEAF_OF_EUCALYPTUS, STONE_OF_CHILL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30074-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("reward"))
		{
			st.takeItems(LEAF_OF_EUCALYPTUS, -1);
			st.takeItems(STONE_OF_CHILL, -1);
			int rand = Rnd.get(100);
			if(rand < 25)
			{
				st.giveItems(REWARD[0], 1);
				htmltext = "30074-rew1.htm";
			}
			else if(rand < 50)
			{
				st.giveItems(REWARD[1], 1);
				htmltext = "30074-rew2.htm";
			}
			else
			{
				st.giveItems(REWARD[2], 1);
				htmltext = "30074-rew3.htm";
			}
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("quit.htm"))
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
		if(npcId == HARLAN)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 20)
				{
					htmltext = "30074-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30074-01.htm";
			}
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) < 80 && st.getQuestItemsCount(STONE_OF_CHILL) < 100)
					htmltext = "30074-04r.htm";
				else if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) == 80 && st.getQuestItemsCount(STONE_OF_CHILL) < 100)
					htmltext = "30074-chill.htm";
				else if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) < 80 && st.getQuestItemsCount(STONE_OF_CHILL) == 100)
					htmltext = "30074-leaf.htm";
				else
					htmltext = "30074-01.htm";
			}
			else if(cond == 2)
				if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) >= 80 && st.getQuestItemsCount(STONE_OF_CHILL) >= 100)
					htmltext = "30074-05.htm";
				else
				{
					st.set("cond", "1");
					st.setState(STARTED);
					if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) < 80 && st.getQuestItemsCount(STONE_OF_CHILL) < 100)
						htmltext = "30074-04r.htm";
					else if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) >= 80 && st.getQuestItemsCount(STONE_OF_CHILL) < 100)
						htmltext = "30074-chill.htm";
					else if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) < 80 && st.getQuestItemsCount(STONE_OF_CHILL) >= 100)
						htmltext = "30074-leaf.htm";
				}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getInt("cond") == 1)
		{
			if(npcId == Enku_Orc_Champion)
			{
				if(st.rollAndGiveLimited(LEAF_OF_EUCALYPTUS, 1, 80, 80))
					st.playSound(SOUND_ITEMGET);
			}
			else if(npcId == Enku_Orc_Shaman)
			{
				if(st.rollAndGiveLimited(STONE_OF_CHILL, 1, 80, 100))
					st.playSound(SOUND_ITEMGET);
			}
			if(st.getQuestItemsCount(LEAF_OF_EUCALYPTUS) == 80 && st.getQuestItemsCount(STONE_OF_CHILL) == 100)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
		}
	}
}