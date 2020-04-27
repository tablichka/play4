package quests._605_AllianceWithKetraOrcs;

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

public class _605_AllianceWithKetraOrcs extends Quest
{
	protected static Log _log = LogFactory.getLog(_605_AllianceWithKetraOrcs.class.getName());

	// ketra mobs
	private static final int[] KETRA_NPC_LIST = {
			25299,
			25302,
			21324,
			21325,
			21327,
			21328,
			21329,
			21331,
			21332,
			21334,
			21335,
			21336,
			21338,
			21339,
			21340,
			21342,
			21343,
			21344,
			21345,
			21346,
			21347};

	// items
	private static final int VB_SOLDIER = 7216;
	private static final int VB_CAPTAIN = 7217;
	private static final int VB_GENERAL = 7218;
	private static final int[] VB_LIST = {VB_SOLDIER, VB_CAPTAIN, VB_GENERAL};

	private static final int TOTEM_OF_VALOR = 7219;
	private static final int TOTEM_OF_WISDOM = 7220;
	private static final int MARK_OF_KETRA_ALLIANCE1 = 7211;
	private static final int MARK_OF_KETRA_ALLIANCE2 = 7212;
	private static final int MARK_OF_KETRA_ALLIANCE3 = 7213;
	private static final int MARK_OF_KETRA_ALLIANCE4 = 7214;
	private static final int MARK_OF_KETRA_ALLIANCE5 = 7215;
	private static final int[] MARK_OF_KETRA_ALLIANCE_LIST = {
			MARK_OF_KETRA_ALLIANCE5,
			MARK_OF_KETRA_ALLIANCE4,
			MARK_OF_KETRA_ALLIANCE3,
			MARK_OF_KETRA_ALLIANCE2,
			MARK_OF_KETRA_ALLIANCE1};

	// hunt for soldier
	private static final int RECRUIT = 21350;
	private static final int FOOTMAN = 21351;
	private static final int SCOUT = 21353;
	private static final int HUNTER = 21354;
	private static final int SHAMAN = 21355;
	private static final int[] SOLDIERS_LIST = {RECRUIT, FOOTMAN, SCOUT, HUNTER, SHAMAN};

	// hunt for captain
	private static final int PRIEST = 21357;
	private static final int WARRIOR = 21358;
	private static final int MEDIUM = 21360;
	private static final int MAGUS = 21361;
	private static final int OFFICIER = 21362;
	private static final int COMMANDER = 21369;
	private static final int ELITE_GUARD = 21370;
	private static final int[] CAPTAINS_LIST = {PRIEST, WARRIOR, MEDIUM, MAGUS, OFFICIER, COMMANDER, ELITE_GUARD};

	// hunt for general
	private static final int GREAT_MAGUS = 21365;
	private static final int GENERAL = 21366;
	private static final int GREAT_SEER = 21368;
	private static final int VARKA_PROPHET = 21373;
	private static final int DISCIPLE_OF_PROPHET = 21375;
	private static final int PROPHET_GUARDS = 21374;
	private static final int HEAD_MAGUS = 21371;
	private static final int HEAD_GUARDS = 21372;
	private static final int[] COMMANDERS_LIST = {
			GREAT_MAGUS,
			GENERAL,
			GREAT_SEER,
			VARKA_PROPHET,
			DISCIPLE_OF_PROPHET,
			PROPHET_GUARDS,
			HEAD_MAGUS,
			HEAD_GUARDS};
	// npc
	private static final int WAHKAN = 31371;

	public _605_AllianceWithKetraOrcs()
	{
		super(605, "_605_AllianceWithKetraOrcs", "Alliance With Ketra Orcs"); // Party true

		addStartNpc(WAHKAN);

		for(int npcId : KETRA_NPC_LIST)
		{
			addAttackId(npcId);
			addKillId(npcId);
		}
		// hunt for soldier
		for(int i : SOLDIERS_LIST)
			addKillId(i);
		// hunt for captain
		for(int i : CAPTAINS_LIST)
			addKillId(i);
		// hunt for general
		for(int i : COMMANDERS_LIST)
			addKillId(i);

		for(int i : VB_LIST)
			addQuestItem(i);
		for(int i : MARK_OF_KETRA_ALLIANCE_LIST)
			addQuestItem(i);
	}

	public boolean isKetraNpc(int npc)
	{
		for(int i : KETRA_NPC_LIST)
			if(npc == i)
				return true;
		return false;
	}

	private void checkMarks(QuestState st)
	{
		if(st.getInt("cond") == 0)
			return;
		if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE5) > 0)
			st.set("cond", "6");
		else if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE4) > 0)
			st.set("cond", "5");
		else if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE3) > 0)
			st.set("cond", "4");
		else if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE2) > 0)
			st.set("cond", "3");
		else if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE1) > 0)
			st.set("cond", "2");
		else
			st.set("cond", "1");
	}

	private String increaseAllyLevel(QuestState st, int lvl)
	{
		for(int i : VB_LIST)
			st.takeItems(i, -1);
		for(int i : MARK_OF_KETRA_ALLIANCE_LIST)
			st.takeItems(i, -1);
		st.giveItems(7210 + lvl, 1);
		st.set("cond", String.valueOf(lvl + 1));
		st.setState(STARTED);
		st.getPlayer().setKetra(lvl);
		st.playSound(SOUND_MIDDLE);
		return null;
	}

	private boolean checkBadges(QuestState st, int nextLvl)
	{
		switch(nextLvl)
		{
			case 1:
			{
				if(st.getQuestItemsCount(VB_SOLDIER) > 99)
					return true;
				break;
			}
			case 2:
			{
				if(st.getQuestItemsCount(VB_SOLDIER) > 199 && st.getQuestItemsCount(VB_CAPTAIN) > 99)
					return true;
				break;
			}
			case 3:
			{
				if(st.getQuestItemsCount(VB_SOLDIER) > 299 && st.getQuestItemsCount(VB_CAPTAIN) > 199 && st.getQuestItemsCount(VB_GENERAL) > 99)
					return true;
				break;
			}
			case 4:
			{
				if(st.getQuestItemsCount(VB_SOLDIER) > 299 && st.getQuestItemsCount(VB_CAPTAIN) > 299 && st.getQuestItemsCount(VB_GENERAL) > 199 && st.getQuestItemsCount(TOTEM_OF_VALOR) > 0)
					return true;
				break;
			}
			case 5:
			{
				if(st.getQuestItemsCount(VB_SOLDIER) > 399 && st.getQuestItemsCount(VB_CAPTAIN) > 399 && st.getQuestItemsCount(VB_GENERAL) > 199 && st.getQuestItemsCount(TOTEM_OF_WISDOM) > 0)
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
			st.takeItems(TOTEM_OF_VALOR, 1);
			increaseAllyLevel(st, 4);
		}
		else if(event.equalsIgnoreCase("fifth-have-2.htm") && checkBadges(st, 5))
		{
			st.takeItems(TOTEM_OF_WISDOM, 1);
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
		if(npcId == WAHKAN)
		{
			QuestState varkaQS = st.getPlayer().getQuestState("_611_AllianceWithVarkaSilenos");

			if(st.getPlayer().getVarka() > 0 || (varkaQS != null && varkaQS.isStarted()))
			{
				htmltext = "isvarka.htm";
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
		for(int i : KETRA_NPC_LIST)
		{
			if(npc.getNpcId() == i && npc.getNpcId() != 25299 && npc.getNpcId() != 25302 && st.getInt("cond") > 1)
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
			if(isKetraNpc(npcId))
			{
				for(int i : MARK_OF_KETRA_ALLIANCE_LIST)
				{
					if(st.getQuestItemsCount(i) > 0)
					{
						st.getPlayer().setKetra(i - 7211);
						st.set("cond", String.valueOf(i - 7210));
						st.setState(STARTED);
						st.takeItems(i, -1);
						if(st.getPlayer().getKetra() < 1)
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
				item = VB_SOLDIER;

		for(int i : CAPTAINS_LIST)
			if(npcId == i)
				item = VB_CAPTAIN;

		for(int i : COMMANDERS_LIST)
			if(npcId == i)
				item = VB_GENERAL;

		if(item == -1)
			return;

		for(QuestState qs : getPartyMembersWithQuest(killer, -1))
		{
			int cond = qs.getCond();
			if(cond > 5 || qs.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE5) > 0 || cond < 1)
				continue;

			if(cond == 1)
			{
				if(item == VB_SOLDIER && qs.getQuestItemsCount(VB_SOLDIER) == 100)
					continue;
			}
			else if(cond == 2)
			{
				if(item == VB_SOLDIER && qs.getQuestItemsCount(VB_SOLDIER) == 200)
					continue;
				else if(item == VB_CAPTAIN && qs.getQuestItemsCount(VB_CAPTAIN) == 100)
					continue;
			}
			else if(cond == 3)
			{
				if(item == VB_SOLDIER && qs.getQuestItemsCount(VB_SOLDIER) == 300)
					continue;
				else if(item == VB_CAPTAIN && qs.getQuestItemsCount(VB_CAPTAIN) == 200)
					continue;
				else if(item == VB_GENERAL && qs.getQuestItemsCount(VB_GENERAL) == 100)
					continue;
			}
			else if(cond == 4)
			{
				if(item == VB_SOLDIER && qs.getQuestItemsCount(VB_SOLDIER) == 300)
					continue;
				else if(item == VB_CAPTAIN && qs.getQuestItemsCount(VB_CAPTAIN) == 300)
					continue;
				else if(item == VB_GENERAL && qs.getQuestItemsCount(VB_GENERAL) == 200)
					continue;
			}
			else
			{
				if(item == VB_SOLDIER && qs.getQuestItemsCount(VB_SOLDIER) == 400)
					continue;
				else if(item == VB_CAPTAIN && qs.getQuestItemsCount(VB_CAPTAIN) == 400)
					continue;
				else if(item == VB_GENERAL && qs.getQuestItemsCount(VB_GENERAL) == 200)
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
			if(item == VB_SOLDIER)
				LIMIT = 100;
		}
		else if(cond == 2)
		{
			if(item == VB_SOLDIER)
				LIMIT = 200;
			else if(item == VB_CAPTAIN)
				LIMIT = 100;
		}
		else if(cond == 3)
		{
			if(item == VB_SOLDIER)
				LIMIT = 300;
			else if(item == VB_CAPTAIN)
				LIMIT = 200;
			else if(item == VB_GENERAL)
				LIMIT = 100;
		}
		else if(cond == 4)
		{
			if(item == VB_SOLDIER)
				LIMIT = 300;
			else if(item == VB_CAPTAIN)
				LIMIT = 300;
			else if(item == VB_GENERAL)
				LIMIT = 200;
		}
		else
		{
			if(item == VB_SOLDIER)
				LIMIT = 400;
			else if(item == VB_CAPTAIN)
				LIMIT = 400;
			else if(item == VB_GENERAL)
				LIMIT = 200;
		}
		if(LIMIT > 0 && st.rollAndGiveLimited(item, 1, 100, LIMIT))
			st.playSound(st.getQuestItemsCount(item) == LIMIT ? SOUND_MIDDLE : SOUND_ITEMGET);
	}
}