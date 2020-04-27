package quests._648_AnIceMerchantsDream;

import javolution.util.FastList;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _648_AnIceMerchantsDream extends Quest
{
	// NPCs
	private static int Rafforty = 32020;
	private static int Ice_Shelf = 32023;
	// Items
	private static int ADENA = 57;
	private static int Silver_Hemocyte = 8057;
	private static int Silver_Ice_Crystal = 8077;
	private static int Black_Ice_Crystal = 8078;
	// Chances
	private static int Silver_Hemocyte_Chance = 10;
	private static int Silver2Black_Chance = 30;

	private static FastList<Integer> silver2black = new FastList<Integer>();

	public _648_AnIceMerchantsDream()
	{
		super(648, "_648_AnIceMerchantsDream", "An Ice Merchant's Dream"); // party true
		addStartNpc(Rafforty);
		addStartNpc(Ice_Shelf);
		for(int i = 22080; i <= 22098; i++)
			if(i != 22095)
				addKillId(i);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32020-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32020-09.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		if(!st.isStarted())
			return event;

		long Silver_Ice_Crystal_Count = st.getQuestItemsCount(Silver_Ice_Crystal);
		long Black_Ice_Crystal_Count = st.getQuestItemsCount(Black_Ice_Crystal);

		if(event.equalsIgnoreCase("32020-07.htm"))
		{
			long reward = Silver_Ice_Crystal_Count * 300 + Black_Ice_Crystal_Count * 1200;
			if(reward > 0)
			{
				st.takeItems(Silver_Ice_Crystal, -1);
				st.takeItems(Black_Ice_Crystal, -1);
				st.rollAndGive(ADENA, reward, 100);
			}
			else
				return "32020-07a.htm";
		}
		else if(event.equalsIgnoreCase("32023-04.htm"))
		{
			int char_obj_id = st.getPlayer().getObjectId();
			synchronized(silver2black)
			{
				if(silver2black.contains(char_obj_id))
					return event;
				else if(Silver_Ice_Crystal_Count > 0)
					silver2black.add(char_obj_id);
				else
					return "cheat.htm";
			}

			st.takeItems(Silver_Ice_Crystal, 1);
			st.playSound(SOUND_BROKEN_KEY);
		}
		else if(event.equalsIgnoreCase("32023-05.htm"))
		{
			Integer char_obj_id = st.getPlayer().getObjectId();
			synchronized(silver2black)
			{
				if(silver2black.contains(char_obj_id))
					while(silver2black.contains(char_obj_id))
						silver2black.remove(char_obj_id);
				else
					return "cheat.htm";
			}

			if(Rnd.chance(Silver2Black_Chance))
			{
				st.giveItems(Black_Ice_Crystal, 1);
				st.playSound(SOUND_ENCHANT_SUCESS);
			}
			else
			{
				st.playSound(SOUND_ENCHANT_FAILED);
				return "32023-06.htm";
			}
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(npcId == Rafforty)
			{
				if(st.getPlayer().getLevel() >= 53)
				{
					st.set("cond", "0");
					return "32020-01.htm";
				}
				st.exitCurrentQuest(true);
				return "32020-00.htm";
			}
			if(npcId == Ice_Shelf)
				return "32023-00.htm";
		}

		if(!st.isStarted())
			return "noquest";

		long Silver_Ice_Crystal_Count = st.getQuestItemsCount(Silver_Ice_Crystal);
		if(npcId == Ice_Shelf)
			return Silver_Ice_Crystal_Count > 0 ? "32023-02.htm" : "32023-01.htm";

		long Black_Ice_Crystal_Count = st.getQuestItemsCount(Black_Ice_Crystal);
		if(npcId == Rafforty)
		{
			if(cond == 1)
			{
				if(Silver_Ice_Crystal_Count > 0 || Black_Ice_Crystal_Count > 0)
				{
					QuestState TheOtherSideOfTruth = st.getPlayer().getQuestState("_115_TheOtherSideOfTruth");
					if(TheOtherSideOfTruth != null && TheOtherSideOfTruth.isCompleted())
					{
						st.set("cond", "2");
						st.playSound(SOUND_MIDDLE);
						return "32020-10.htm";
					}
					return "32020-05.htm";
				}
				return "32020-04.htm";
			}
			if(cond == 2)
				return Silver_Ice_Crystal_Count > 0 || Black_Ice_Crystal_Count > 0 ? "32020-10.htm" : "32020-04a.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 2);
		if(st != null && st.rollAndGive(Silver_Hemocyte, 1, Silver_Hemocyte_Chance))
			st.playSound(SOUND_ITEMGET);

		st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGive(Silver_Ice_Crystal, 1, npc.getNpcId() - 22050))
			st.playSound(SOUND_ITEMGET);
	}
}