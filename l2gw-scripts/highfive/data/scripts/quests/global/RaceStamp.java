package quests.global;

import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * User: ic
 * Date: 30.10.2009
 */
public class RaceStamp extends Quest
{
	private static final int RIGNOS = 32349;
	private static final int RACE_STAMP = 10013;
	private static final int SECRET_KEY = 9694;
	private static final int EVENT_TIMER = 5239;
	private static final int PRISON_GUARD = 18367;

	public RaceStamp()
	{
		super(25100, "RaceStamp", "Race Stamp Quest", true);
		addAttackId(PRISON_GUARD);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st == null)
			return null;

		long lastRaceStarted = ServerVariables.getLong("RaceStampLastTime", 0);
		if(event.equalsIgnoreCase("start"))
		{
			if(System.currentTimeMillis() >= lastRaceStarted && System.currentTimeMillis() <= lastRaceStarted + 30 * 60000)
				return "RaceStamp-2.htm";
			else if(System.currentTimeMillis() >= lastRaceStarted + 30 * 60000)
			{
				L2NpcInstance npc = st.getPlayer().getLastNpc();
				if(npc.getNpcId() == RIGNOS)
				{
					st.takeItems(RACE_STAMP, -1);
					L2Skill skill = SkillTable.getInstance().getInfo(EVENT_TIMER, 5);
					npc.setTarget(st.getPlayer());
					npc.doCast(skill, skill.getAimingTarget(npc), true);
					ServerVariables.set("RaceStampLastTime", System.currentTimeMillis());
					ServerVariables.set("RaceStampLastChar", st.getPlayer().getObjectId());
					st.getPlayer().setVar("PrisonGuard1", "0");
					st.getPlayer().setVar("PrisonGuard2", "0");
					st.getPlayer().setVar("PrisonGuard3", "0");
					st.getPlayer().setVar("PrisonGuard4", "0");
					return null;
				}
				else
					return null;
			}
		}
		else if(event.equalsIgnoreCase("exchange"))
		{
			if(System.currentTimeMillis() >= lastRaceStarted
					&& System.currentTimeMillis() <= lastRaceStarted + 30 * 60000
					&& st.getQuestItemsCount(RACE_STAMP) == 4
					&& ServerVariables.getInt("RaceStampLastChar") == st.getPlayer().getObjectId())
			{
				st.takeItems(RACE_STAMP, 4);
				st.giveItems(SECRET_KEY, 3);
				st.getPlayer().setVar("PrisonGuard1", "0");
				st.getPlayer().setVar("PrisonGuard2", "0");
				st.getPlayer().setVar("PrisonGuard3", "0");
				st.getPlayer().setVar("PrisonGuard4", "0");
				if(st.getPlayer().getEffectBySkillId(EVENT_TIMER) != null)
					st.getPlayer().stopEffect(EVENT_TIMER);
				st.exitCurrentQuest(true);
			}
		}

		return null;
	}

	@Override
	public String onAttack(L2NpcInstance npc, L2Player killer, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		if(npcId == PRISON_GUARD && killer.getEffectBySkillId(EVENT_TIMER) != null)
		{
			String pgNumber = npc.getAIParams().getString("pg", null);
			if(pgNumber != null && !pgNumber.equalsIgnoreCase("0"))
			{
				if(killer.getVar("PrisonGuard" + pgNumber).equalsIgnoreCase("0"))
				{
					killer.setVar("PrisonGuard" + pgNumber, "1");
					Quest q = QuestManager.getQuest("RaceStamp");
					QuestState qs = killer.getQuestState("RaceStamp");
					if(qs == null)
						qs = q.newQuestState(killer);
					qs.giveItems(RACE_STAMP, 1);
				}
			}
		}
		return null;
	}
}
