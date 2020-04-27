package quests._633_InTheForgottenVillage;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

public class _633_InTheForgottenVillage extends Quest
{
	// NPC
	private static int MINA = 31388;
	// ITEMS
	private static int RIB_BONE = 7544;
	private static int Z_LIVER = 7545;

	// Mobid : DROP CHANCES
	private static HashMap<Integer, Integer> DAMOBS = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> UNDEADS = new HashMap<Integer, Integer>();

	public _633_InTheForgottenVillage()
	{
		super(633, "_633_InTheForgottenVillage", "In The Forgotten Village"); // Party true

		DAMOBS.put(21557, 328); // Bone Snatcher
		DAMOBS.put(21558, 328); // Bone Snatcher
		DAMOBS.put(21559, 337); // Bone Maker
		DAMOBS.put(21560, 337); // Bone Shaper
		DAMOBS.put(21563, 342); // Bone Collector
		DAMOBS.put(21564, 348); // Skull Collector
		DAMOBS.put(21565, 351); // Bone Animator
		DAMOBS.put(21566, 359); // Skull Animator
		DAMOBS.put(21567, 359); // Bone Slayer
		DAMOBS.put(21572, 365); // Bone Sweeper
		DAMOBS.put(21574, 383); // Bone Grinder
		DAMOBS.put(21575, 383); // Bone Grinder
		DAMOBS.put(21580, 385); // Bone Caster
		DAMOBS.put(21581, 395); // Bone Puppeteer
		DAMOBS.put(21583, 397); // Bone Scavenger
		DAMOBS.put(21584, 401); // Bone Scavenger

		UNDEADS.put(21553, 347); // Trampled Man
		UNDEADS.put(21554, 347); // Trampled Man
		UNDEADS.put(21561, 450); // Sacrificed Man
		UNDEADS.put(21578, 501); // Behemoth Zombie
		UNDEADS.put(21596, 359); // Requiem Lord
		UNDEADS.put(21597, 370); // Requiem Behemoth
		UNDEADS.put(21598, 441); // Requiem Behemoth
		UNDEADS.put(21599, 395); // Requiem Priest
		UNDEADS.put(21600, 408); // Requiem Behemoth
		UNDEADS.put(21601, 411); // Requiem Behemoth

		addStartNpc(MINA);
		addQuestItem(RIB_BONE);

		for(int i : UNDEADS.keySet())
			addKillId(i);

		for(int i : DAMOBS.keySet())
			addKillId(i);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("accept"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "31388-04.htm";
		}
		if(event.equalsIgnoreCase("quit"))
		{
			st.takeItems(RIB_BONE, -1);
			st.playSound(SOUND_FINISH);
			htmltext = "31388-10.htm";
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("stay"))
			htmltext = "31388-07.htm";
		else if(event.equalsIgnoreCase("reward"))
			if(st.getInt("cond") == 2)
				if(st.getQuestItemsCount(RIB_BONE) >= 200)
				{
					st.takeItems(RIB_BONE, -1);
					st.rollAndGive(57, 25000, 100);
					st.addExpAndSp(305235, 0);
					st.playSound(SOUND_FINISH);
					st.set("cond", "1");
					htmltext = "31388-08.htm";
				}
				else
					htmltext = "31388-09.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == MINA)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() > 64)
					htmltext = "31388-01.htm";
				else
				{
					htmltext = "31388-03.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "31388-06.htm";
			else if(cond == 2)
				htmltext = "31388-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();

		QuestState st = getRandomPartyMemberWithQuest(killer, 2);

		if(st != null && UNDEADS.get(npcId) != null && st.rollAndGive(Z_LIVER, 1, UNDEADS.get(npcId)))
			st.playSound(SOUND_ITEMGET);

		st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			if(DAMOBS.get(npcId) != null && st.rollAndGiveLimited(RIB_BONE, 1, DAMOBS.get(npcId), 200))
			{
				if(st.getQuestItemsCount(RIB_BONE) == 200)
				{
					st.set("cond", "2");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}

			if(st != null && UNDEADS.get(npcId) != null && st.rollAndGive(Z_LIVER, 1, UNDEADS.get(npcId)))
				st.playSound(SOUND_ITEMGET);
		}

	}
}