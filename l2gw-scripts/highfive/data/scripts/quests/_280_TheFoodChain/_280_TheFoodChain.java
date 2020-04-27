package quests._280_TheFoodChain;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _280_TheFoodChain extends Quest
{
	// NPCs
	private static int BIXON = 32175;
	// Mobs
	private static int Young_Grey_Keltir = 22229;
	private static int Grey_Keltir = 22230;
	private static int Dominant_Grey_Keltir = 22231;
	private static int Black_Wolf = 22232;
	private static int Dominant_Black_Wolf = 22233;
	// Items
	private static int ADENA = 57;
	private static int[] REWARDS = {28, 35, 116};
	// Quest Items
	private static int Grey_Keltir_Tooth = 9809;
	private static int Black_Wolf_Tooth = 9810;
	// Chances
	private static int Grey_Keltir_Tooth_Chance = 90;
	private static int Black_Wolf_Tooth_Chance = 70;

	public _280_TheFoodChain()
	{
		super(280, "_280_TheFoodChain", "The Food Chain");
		addStartNpc(BIXON);
		addKillId(Young_Grey_Keltir);
		addKillId(Grey_Keltir);
		addKillId(Dominant_Grey_Keltir);
		addKillId(Black_Wolf);
		addKillId(Dominant_Black_Wolf);
		addQuestItem(Grey_Keltir_Tooth);
		addQuestItem(Black_Wolf_Tooth);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32175-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32175-08.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(st.isStarted())
		{
			long Grey_Keltir_Tooth_count = st.getQuestItemsCount(Grey_Keltir_Tooth);
			long Black_Wolf_Tooth_count = st.getQuestItemsCount(Black_Wolf_Tooth);

			if(event.equalsIgnoreCase("ADENA"))
			{
				st.takeItems(Grey_Keltir_Tooth, -1);
				st.takeItems(Black_Wolf_Tooth, -1);
				st.rollAndGive(ADENA, (Grey_Keltir_Tooth_count + Black_Wolf_Tooth_count) * 2, 100);
				st.playSound(SOUND_MIDDLE);
				return "32175-06.htm";
			}
			else if(event.equalsIgnoreCase("ITEM"))
			{
				if(Grey_Keltir_Tooth_count + Black_Wolf_Tooth_count < 25)
					return "32175-09.htm";
				int take_Grey_Keltir_Tooth = 0;
				int take_Black_Wolf_Tooth = 0;
				while(take_Grey_Keltir_Tooth + take_Black_Wolf_Tooth < 25)
				{
					if(Grey_Keltir_Tooth_count > 0)
					{
						take_Grey_Keltir_Tooth++;
						Grey_Keltir_Tooth_count--;
					}
					if(take_Grey_Keltir_Tooth + take_Black_Wolf_Tooth < 25 && Black_Wolf_Tooth_count > 0)
					{
						take_Black_Wolf_Tooth++;
						Black_Wolf_Tooth_count--;
					}
				}

				if(take_Grey_Keltir_Tooth > 0)
					st.takeItems(Grey_Keltir_Tooth, take_Grey_Keltir_Tooth);
				if(take_Black_Wolf_Tooth > 0)
					st.takeItems(Black_Wolf_Tooth, take_Black_Wolf_Tooth);
				int rew_count = 1;
				while(rew_count > 0)
				{
					rew_count--;
					st.giveItems(REWARDS[Rnd.get(REWARDS.length)], 1);
				}
				st.playSound(SOUND_MIDDLE);
				return "32175-06.htm";
			}
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() != BIXON)
			return "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 3)
			{
				st.set("cond", "0");
				return "32175-01.htm";
			}
			st.exitCurrentQuest(true);
			return "32175-02.htm";
		}
		else if(st.isStarted())
			return st.getQuestItemsCount(Grey_Keltir_Tooth) > 0 || st.getQuestItemsCount(Black_Wolf_Tooth) > 0 ? "32175-05.htm" : "32175-04.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		int npcId = npc.getNpcId();

		if((npcId == Young_Grey_Keltir || npcId == Grey_Keltir || npcId == Dominant_Grey_Keltir) && Rnd.chance(Grey_Keltir_Tooth_Chance))
		{
			st.rollAndGive(Grey_Keltir_Tooth, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
		else if((npcId == Black_Wolf || npcId == Dominant_Black_Wolf) && Rnd.chance(Black_Wolf_Tooth_Chance))
		{
			st.rollAndGive(Black_Wolf_Tooth, 3, 100);
			st.playSound(SOUND_ITEMGET);
		}
	}
}