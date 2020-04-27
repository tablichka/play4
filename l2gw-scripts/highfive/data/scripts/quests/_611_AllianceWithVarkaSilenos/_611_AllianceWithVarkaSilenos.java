package quests._611_AllianceWithVarkaSilenos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

public class _611_AllianceWithVarkaSilenos extends Quest
{
	protected static Log _log = LogFactory.getLog(_611_AllianceWithVarkaSilenos.class.getName());

	//Varka mobs
	private final int[] VARKA_NPC_LIST = {
			25309,
			25312,
			21350,
			21351,
			21353,
			21354,
			21355,
			21357,
			21358,
			21360,
			21361,
			21362,
			21364,
			21365,
			21366,
			21368,
			21369,
			21370,
			21371,
			21372,
			21373,
			21374};

	// Items
	private static final int KB_SOLDIER = 7226;
	private static final int KB_CAPTAIN = 7227;
	private static final int KB_GENERAL = 7228;
	private static final int[] KB_LIST = {KB_SOLDIER, KB_CAPTAIN, KB_GENERAL};

	private static final int FEATHER_OF_VALOR = 7229;
	private static final int FEATHER_OF_WISDOM = 7230;
	private static final int MARK_OF_VARKA_ALLIANCE1 = 7221;
	private static final int MARK_OF_VARKA_ALLIANCE2 = 7222;
	private static final int MARK_OF_VARKA_ALLIANCE3 = 7223;
	private static final int MARK_OF_VARKA_ALLIANCE4 = 7224;
	private static final int MARK_OF_VARKA_ALLIANCE5 = 7225;
	private static final int[] MARK_OF_VARKA_ALLIANCE_LIST = {
			MARK_OF_VARKA_ALLIANCE5,
			MARK_OF_VARKA_ALLIANCE4,
			MARK_OF_VARKA_ALLIANCE3,
			MARK_OF_VARKA_ALLIANCE2,
			MARK_OF_VARKA_ALLIANCE1};

	// hunt for soldier
	private static final int RAIDER = 21327;
	private static final int FOOTMAN = 21324;
	private static final int SCOUT = 21328;
	private static final int WAR_HOUND = 21325;
	private static final int SHAMAN = 21329;
	private static final int[] SOLDIERS_LIST = {RAIDER, FOOTMAN, SCOUT, WAR_HOUND, SHAMAN};

	// hunt for captain
	private static final int SEER = 21338;
	private static final int WARRIOR = 21331;
	private static final int LIEUTENANT = 21332;
	private static final int ELITE_SOLDIER = 21335;
	private static final int MEDIUM = 21334;
	private static final int COMMAND = 21343;
	private static final int ELITE_GUARD = 21344;
	private static final int WHITE_CAPTAIN = 21336;
	private static final int[] CAPTAINS_LIST = {SEER, WARRIOR, LIEUTENANT, ELITE_SOLDIER, MEDIUM, COMMAND, ELITE_GUARD, WHITE_CAPTAIN};

	// hunt for general
	private static final int BATTALION_COMMANDER_SOLDIER = 21340;
	private static final int GENERAL = 21339;
	private static final int GREAT_SEER = 21342;
	private static final int KETRA_PROPHET = 21347;
	private static final int DISCIPLE_OF_PROPHET = 21375;
	private static final int PROPHET_GUARDS = 21348;
	private static final int PROPHET_AIDE = 21349;
	private static final int HEAD_SHAMAN = 21345;
	private static final int HEAD_GUARDS = 21346;
	private static final int[] COMMANDERS_LIST = {
			BATTALION_COMMANDER_SOLDIER,
			GENERAL,
			GREAT_SEER,
			KETRA_PROPHET,
			DISCIPLE_OF_PROPHET,
			PROPHET_GUARDS,
			PROPHET_AIDE,
			HEAD_SHAMAN,
			HEAD_GUARDS};
	//npc
	private static final int NARAN_ASHANUK = 31378;

	public _611_AllianceWithVarkaSilenos()
	{
		super(611, "_611_AllianceWithVarkaSilenos", "Alliance With Varka Silenos"); // Party true

		addStartNpc(NARAN_ASHANUK);

		for(int npcId : VARKA_NPC_LIST)
		{
			addAttackId(npcId);
			addKillId(npcId);
		}
		//hunt for soldier
		for(int i : SOLDIERS_LIST)
			addKillId(i);
		//hunt for captain
		for(int i : CAPTAINS_LIST)
			addKillId(i);
		//hunt for general
		for(int i : COMMANDERS_LIST)
			addKillId(i);

		for(int i : KB_LIST)
			addQuestItem(i);
		for(int i : MARK_OF_VARKA_ALLIANCE_LIST)
			addQuestItem(i);
	}

	public boolean isVarkaNpc(int npc)
	{
		for(int i : VARKA_NPC_LIST)
			if(npc == i)
				return true;
		return false;
	}

	private static void checkMarks(QuestState st)
	{
		if(st.getInt("cond") == 0)
			return;
		if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE5) > 0)
			st.set("cond", "6");
		else if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE4) > 0)
			st.set("cond", "5");
		else if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE3) > 0)
			st.set("cond", "4");
		else if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE2) > 0)
			st.set("cond", "3");
		else if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE1) > 0)
			st.set("cond", "2");
		else
			st.set("cond", "1");
	}

	private String increaseAllyLevel(QuestState st, int lvl)
	{
		for(int i : KB_LIST)
			st.takeItems(i, -1);
		for(int i : MARK_OF_VARKA_ALLIANCE_LIST)
			st.takeItems(i, -1);
		st.giveItems(7220 + lvl, 1);
		st.set("cond", String.valueOf(lvl + 1));
		st.setState(STARTED);
		st.getPlayer().setVarka(lvl);
		st.playSound(SOUND_MIDDLE);
		return null;
	}

	private boolean checkBadges(QuestState st, int nextLvl)
	{
		switch(nextLvl)
		{
			case 1:
			{
				if(st.getQuestItemsCount(KB_SOLDIER) > 99)
					return true;
				break;
			}
			case 2:
			{
				if(st.getQuestItemsCount(KB_SOLDIER) > 199 && st.getQuestItemsCount(KB_CAPTAIN) > 99)
					return true;
				break;
			}
			case 3:
			{
				if(st.getQuestItemsCount(KB_SOLDIER) > 299 && st.getQuestItemsCount(KB_CAPTAIN) > 199 && st.getQuestItemsCount(KB_GENERAL) > 99)
					return true;
				break;
			}
			case 4:
			{
				if(st.getQuestItemsCount(KB_SOLDIER) > 299 && st.getQuestItemsCount(KB_CAPTAIN) > 299 && st.getQuestItemsCount(KB_GENERAL) > 199 && st.getQuestItemsCount(FEATHER_OF_VALOR) > 0)
					return true;
				break;
			}
			case 5:
			{
				if(st.getQuestItemsCount(KB_SOLDIER) > 399 && st.getQuestItemsCount(KB_CAPTAIN) > 399 && st.getQuestItemsCount(KB_GENERAL) > 199 && st.getQuestItemsCount(FEATHER_OF_WISDOM) > 0)
					return true;
				break;
			}
		}
		return false;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("first-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("first-have-2.htm") && checkBadges(st, 1))
			increaseAllyLevel(st, 1);
		else if(event.equalsIgnoreCase("second-have-2.htm") && checkBadges(st, 2))
			increaseAllyLevel(st, 2);
		else if(event.equalsIgnoreCase("third-have-2.htm") && checkBadges(st, 3))
			increaseAllyLevel(st, 3);
		else if(event.equalsIgnoreCase("fourth-have-2.htm") && checkBadges(st, 4))
		{
			st.takeItems(FEATHER_OF_VALOR, 1);
			increaseAllyLevel(st, 4);
		}
		else if(event.equalsIgnoreCase("fifth-have-2.htm") && checkBadges(st, 5))
		{
			st.takeItems(FEATHER_OF_WISDOM, 1);
			increaseAllyLevel(st, 5);
		}
		else if(event.equalsIgnoreCase("quit-2.htm"))
		{
			st.getPlayer().setKetra(0);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == NARAN_ASHANUK)
		{
			QuestState ketraQS = st.getPlayer().getQuestState("_605_AllianceWithKetraOrcs");
			if(st.getPlayer().getKetra() > 0 || (ketraQS != null && ketraQS.isStarted()))
			{
				htmltext = "ketra.htm";
				st.exitCurrentQuest(true);
				return htmltext;
			}
			checkMarks(st);
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 74)
				{
					htmltext = "no-level.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "first.htm";
			}
			else if(st.isStarted())
			{
				if(cond == 1)
				{
					if(checkBadges(st, 1))
						htmltext = "first-have.htm";
					else
						htmltext = "first-havenot.htm";
				}
				else if(cond == 2)
				{
					if(checkBadges(st, 2))
						htmltext = "second-have.htm";
					else
						htmltext = "second.htm";
				}
				else if(cond == 3)
				{
					if(checkBadges(st, 3))
						htmltext = "third-have.htm";
					else
						htmltext = "third.htm";
				}
				else if(cond == 4)
				{
					if(checkBadges(st, 4))
						htmltext = "fourth-have.htm";
					else
						htmltext = "fourth.htm";
				}
				else if(cond == 5)
				{
					if(checkBadges(st, 5))
						htmltext = "fifth-have.htm";
					else
						htmltext = "fifth.htm";
				}
				else if(cond == 6)
					htmltext = "high.htm";
			}
		}
		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		for(int i : VARKA_NPC_LIST)
		{
			if(npc.getNpcId() == i && npc.getNpcId() != 25309 && npc.getNpcId() != 25312 && st.getInt("cond") > 1)
				npc.doCast(SkillTable.getInstance().getInfo(4578, 1), st.getPlayer(), true);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		for(QuestState st : getPartyMembersWithQuest(killer, -1))
		{
			if(isVarkaNpc(npcId))
			{
				for(int i : MARK_OF_VARKA_ALLIANCE_LIST)
				{
					if(st.getQuestItemsCount(i) > 0)
					{
						st.getPlayer().setVarka(i - 7221);
						st.set("cond", String.valueOf(i - 7220));
						st.setState(STARTED);
						st.takeItems(i, -1);
						if(st.getPlayer().getVarka() < 1)
						{
							st.exitCurrentQuest(true);
							break;
						}
						st.giveItems(i - 1, 1);
						break;
					}
				}
				return;
			}
		}

		GArray<QuestState> pm = new GArray<QuestState>();
		int item = -1;
		for(int i : SOLDIERS_LIST)
			if(npcId == i)
				item = KB_SOLDIER;

		for(int i : CAPTAINS_LIST)
			if(npcId == i)
				item = KB_CAPTAIN;

		for(int i : COMMANDERS_LIST)
			if(npcId == i)
				item = KB_GENERAL;

		if(item == -1)
			return;

		for(QuestState qs : getPartyMembersWithQuest(killer, -1))
		{
			int cond = qs.getCond();
			if(cond > 5 || qs.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE5) > 0 || cond < 1)
				continue;

			if(cond == 1)
			{
				if(item == KB_SOLDIER && qs.getQuestItemsCount(KB_SOLDIER) == 100)
					continue;
			}
			else if(cond == 2)
			{
				if(item == KB_SOLDIER && qs.getQuestItemsCount(KB_SOLDIER) == 200)
					continue;
				else if(item == KB_CAPTAIN && qs.getQuestItemsCount(KB_CAPTAIN) == 100)
					continue;
			}
			else if(cond == 3)
			{
				if(item == KB_SOLDIER && qs.getQuestItemsCount(KB_SOLDIER) == 300)
					continue;
				else if(item == KB_CAPTAIN && qs.getQuestItemsCount(KB_CAPTAIN) == 200)
					continue;
				else if(item == KB_GENERAL && qs.getQuestItemsCount(KB_GENERAL) == 100)
					continue;
			}
			else if(cond == 4)
			{
				if(item == KB_SOLDIER && qs.getQuestItemsCount(KB_SOLDIER) == 300)
					continue;
				else if(item == KB_CAPTAIN && qs.getQuestItemsCount(KB_CAPTAIN) == 300)
					continue;
				else if(item == KB_GENERAL && qs.getQuestItemsCount(KB_GENERAL) == 200)
					continue;
			}
			else
			{
				if(item == KB_SOLDIER && qs.getQuestItemsCount(KB_SOLDIER) == 400)
					continue;
				else if(item == KB_CAPTAIN && qs.getQuestItemsCount(KB_CAPTAIN) == 400)
					continue;
				else if(item == KB_GENERAL && qs.getQuestItemsCount(KB_GENERAL) == 200)
					continue;
			}
			pm.add(qs);
		}

		if(pm.isEmpty())
			return;


		QuestState st = pm.get(Rnd.get(pm.size()));
		int cond = st.getCond();
		int LIMIT = -1;
		if(cond == 1)
		{
			if(item == KB_SOLDIER)
				LIMIT = 100;
		}
		else if(cond == 2)
		{
			if(item == KB_SOLDIER)
				LIMIT = 200;
			else if(item == KB_CAPTAIN)
				LIMIT = 100;
		}
		else if(cond == 3)
		{
			if(item == KB_SOLDIER)
				LIMIT = 300;
			else if(item == KB_CAPTAIN)
				LIMIT = 200;
			else if(item == KB_GENERAL)
				LIMIT = 100;
		}
		else if(cond == 4)
		{
			if(item == KB_SOLDIER)
				LIMIT = 300;
			else if(item == KB_CAPTAIN)
				LIMIT = 300;
			else if(item == KB_GENERAL)
				LIMIT = 200;
		}
		else
		{
			if(item == KB_SOLDIER)
				LIMIT = 400;
			else if(item == KB_CAPTAIN)
				LIMIT = 400;
			else if(item == KB_GENERAL)
				LIMIT = 200;
		}
		if(LIMIT > 0 && st.rollAndGiveLimited(item, 1, 100, LIMIT))
			st.playSound(st.getQuestItemsCount(item) == LIMIT ? SOUND_MIDDLE : SOUND_ITEMGET);
	}
}