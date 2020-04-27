package quests._646_SignsOfRevolt;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _646_SignsOfRevolt extends Quest
{
	// NPCs
	private static int TORRANT = 32016;
	// Mobs
	private static int Ragna_Orc = 22029; // First in Range
	private static int Ragna_Orc_Sorcerer = 22044; // Last in Range
	private static int Guardian_of_the_Ghost_Town = 22047;
	private static int Varangkas_Succubus = 22049;
	// Items
	private static int ADENA = 57;
	private static int Steel = 1880;
	private static int Coarse_Bone_Powder = 1881;
	private static int Leather = 1882;
	// Quest Items
	private static int CURSED_DOLL = 8087;
	// Chances
	private static int CURSED_DOLL_Chance = 75;

	public _646_SignsOfRevolt()
	{
		super(646, "_646_SignsOfRevolt", "Signs of Revolt"); // party true
		addStartNpc(TORRANT);
		for(int Ragna_Orc_id = Ragna_Orc; Ragna_Orc_id <= Ragna_Orc_Sorcerer; Ragna_Orc_id++)
			addKillId(Ragna_Orc_id);
		addKillId(Guardian_of_the_Ghost_Town);
		addKillId(Varangkas_Succubus);
		addQuestItem(CURSED_DOLL);
	}

	private static String doReward(QuestState st, int reward_id, int _count)
	{
		if(st.getQuestItemsCount(CURSED_DOLL) < 180)
			return null;
		st.takeItems(CURSED_DOLL, -1);
		st.rollAndGive(reward_id, _count, 100);
		st.playSound(SOUND_FINISH);
		st.exitCurrentQuest(true);
		return "32016-07.htm";
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32016-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("reward_adena") && st.isStarted())
			return doReward(st, ADENA, 21600);
		else if(event.equalsIgnoreCase("reward_cbp") && st.isStarted())
			return doReward(st, Coarse_Bone_Powder, 12);
		else if(event.equalsIgnoreCase("reward_steel") && st.isStarted())
			return doReward(st, Steel, 9);
		else if(event.equalsIgnoreCase("reward_leather") && st.isStarted())
			return doReward(st, Leather, 20);

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != TORRANT)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 40)
			{
				htmltext = "32017-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "32016-01.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
			htmltext = st.getQuestItemsCount(CURSED_DOLL) >= 180 ? "32016-05.htm" : "32016-04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGiveLimited(CURSED_DOLL, 1, CURSED_DOLL_Chance, 180))
		{
			if(st.getQuestItemsCount(CURSED_DOLL) == 180)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}