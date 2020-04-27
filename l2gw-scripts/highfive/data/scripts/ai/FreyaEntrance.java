package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 26.09.11 21:36
 */
public class FreyaEntrance extends DefaultNpc
{
	public String AREA_effect_1s_01 = "23_14_icequeen_1s_01";
	public String AREA_effect_1s_02 = "23_14_icequeen_1s_02";
	public String AREA_effect_1s_03 = "23_14_icequeen_1s_03";
	public String AREA_effect_1s_04 = "23_14_icequeen_1s_04";
	public String AREA_effect_1s_05 = "23_14_icequeen_1s_05";
	public String AREA_effect_1s_06 = "23_14_icequeen_1s_06";
	public String AREA_effect_1s_07 = "23_14_icequeen_1s_07";
	public String AREA_effect_1s_08 = "23_14_icequeen_1s_08";
	public String AREA_effect_1s_09 = "23_14_icequeen_1s_09";
	public String AREA_effect_1s_10 = "23_14_icequeen_1s_10";
	public String AREA_effect_1s_11 = "23_14_icequeen_1s_11";
	public String AREA_effect_1s_12 = "23_14_icequeen_1s_12";
	public String AREA_effect_1s_13 = "23_14_icequeen_1s_13";
	public String AREA_effect_1s_14 = "23_14_icequeen_1s_14";
	public String AREA_effect_1s_15 = "23_14_icequeen_1s_15";
	public String AREA_effect_1s_16 = "23_14_icequeen_1s_16";
	public String AREA_effect_1s_17 = "23_14_icequeen_1s_17";
	public String AREA_effect_1s_18 = "23_14_icequeen_1s_18";
	public String AREA_effect_1s_19 = "23_14_icequeen_1s_19";
	public String AREA_effect_1s_20 = "23_14_icequeen_1s_20";
	public String AREA_effect_1s_21 = "23_14_icequeen_1s_21";
	public String AREA_effect_2s_01 = "23_14_icequeen_2s_01";
	public String AREA_effect_2s_02 = "23_14_icequeen_2s_02";
	public String AREA_effect_2s_03 = "23_14_icequeen_2s_03";
	public String AREA_effect_2s_04 = "23_14_icequeen_2s_04";
	public String AREA_effect_2s_05 = "23_14_icequeen_2s_05";
	public String AREA_effect_2s_06 = "23_14_icequeen_2s_06";
	public String AREA_effect_2s_07 = "23_14_icequeen_2s_07";
	public String AREA_effect_2s_08 = "23_14_icequeen_2s_08";
	public String AREA_effect_1s_01_hd = "23_14_icequeen_1s_01_hd";
	public String AREA_effect_1s_02_hd = "23_14_icequeen_1s_02_hd";
	public String AREA_effect_1s_03_hd = "23_14_icequeen_1s_03_hd";
	public String AREA_effect_1s_04_hd = "23_14_icequeen_1s_04_hd";
	public String AREA_effect_1s_05_hd = "23_14_icequeen_1s_05_hd";
	public String AREA_effect_1s_06_hd = "23_14_icequeen_1s_06_hd";
	public String AREA_effect_1s_07_hd = "23_14_icequeen_1s_07_hd";
	public String AREA_effect_1s_08_hd = "23_14_icequeen_1s_08_hd";
	public String AREA_effect_1s_09_hd = "23_14_icequeen_1s_09_hd";
	public String AREA_effect_1s_10_hd = "23_14_icequeen_1s_10_hd";
	public String AREA_effect_1s_11_hd = "23_14_icequeen_1s_11_hd";
	public String AREA_effect_1s_12_hd = "23_14_icequeen_1s_12_hd";
	public String AREA_effect_1s_13_hd = "23_14_icequeen_1s_13_hd";
	public String AREA_effect_1s_14_hd = "23_14_icequeen_1s_14_hd";
	public String AREA_effect_1s_15_hd = "23_14_icequeen_1s_15_hd";
	public String AREA_effect_1s_16_hd = "23_14_icequeen_1s_16_hd";
	public String AREA_effect_1s_17_hd = "23_14_icequeen_1s_17_hd";
	public String AREA_effect_1s_18_hd = "23_14_icequeen_1s_18_hd";
	public String AREA_effect_1s_19_hd = "23_14_icequeen_1s_19_hd";
	public String AREA_effect_1s_20_hd = "23_14_icequeen_1s_20_hd";
	public String AREA_effect_1s_21_hd = "23_14_icequeen_1s_21_hd";
	public String AREA_effect_2s_01_hd = "23_14_icequeen_2s_01_hd";
	public String AREA_effect_2s_02_hd = "23_14_icequeen_2s_02_hd";
	public String AREA_effect_2s_03_hd = "23_14_icequeen_2s_03_hd";
	public String AREA_effect_2s_04_hd = "23_14_icequeen_2s_04_hd";
	public String AREA_effect_2s_05_hd = "23_14_icequeen_2s_05_hd";
	public String AREA_effect_2s_06_hd = "23_14_icequeen_2s_06_hd";
	public String AREA_effect_2s_07_hd = "23_14_icequeen_2s_07_hd";
	public String AREA_effect_2s_08_hd = "23_14_icequeen_2s_08_hd";
	public L2Skill SKILL_death_clock = SkillTable.getInstance().getInfo(411566081);
	public L2Skill SKILL_area_lv1 = SkillTable.getInstance().getInfo(421855233);
	public int TIMER_start = 2314030;
	public int debug_mode = 0;

	public FreyaEntrance(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 1;
		_thisActor.i_ai1 = 0;
		_thisActor.l_ai2 = 0;
		if(_thisActor.param1 == 0)
		{
			_thisActor.i_ai3 = 0;
		}
		else if(_thisActor.param1 == 1)
		{
			_thisActor.i_ai3 = 1;
		}
		int i0 = _thisActor.getReflection();
		if(_thisActor.i_ai3 == 0)
		{
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08, 0, i0);
		}
		else if(_thisActor.i_ai3 == 1)
		{
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21_hd, 1, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07_hd, 0, i0);
			ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08_hd, 0, i0);
		}
		_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 23140022, getStoredIdFromCreature(_thisActor), null);
		_thisActor.createOnePrivate(32762, "SirrNpc", 0, 0, 114766, -113141, -11200, 15956, 0, 0, 0);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			if(_thisActor.i_ai0 == 3)
			{
				Functions.changeZoneInfo(creature, 0, 2);
				creature.teleToLocation(114727, -113387, -11200);
			}
			else
			{
				Functions.changeZoneInfo(creature, 0, 1);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140050)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "SCE_1MIN_TIMER");
			}

			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				Functions.sendUIEventFStr(player, 0, 0, 0, "0", "1", "0", "0", "0", 1801090);
			}
		}
		else if(eventId == 23140021)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "SCE_ZONEINFO_CHG");
			}
			if(_thisActor.i_ai1 == 0)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "NOW P3");
				}
				_thisActor.i_ai0 = 3;
				_thisActor.i_ai1 = 1;
				for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
				{
					Functions.changeZoneInfo(player, 0, 2);
				}

				int i0 = _thisActor.getReflection();
				if(_thisActor.i_ai3 == 0)
				{
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08, 1, i0);
				}
				else if(_thisActor.i_ai3 == 1)
				{
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08_hd, 1, i0);
				}
			}
			else if(_thisActor.i_ai1 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "NOW P4");
				}
				_thisActor.i_ai0 = 4;
				_thisActor.i_ai1 = 0;
				for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
				{
					Functions.changeZoneInfo(player, 0, 1);
				}
				int i0 = _thisActor.getReflection();
				if(_thisActor.i_ai3 == 0)
				{
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08, 0, i0);
				}
				else if(_thisActor.i_ai3 == 1)
				{
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_01_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_02_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_03_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_04_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_05_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_06_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_07_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_08_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_09_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_10_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_11_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_12_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_13_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_14_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_15_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_16_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_17_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_18_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_19_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_20_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_1s_21_hd, 1, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_01_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_02_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_03_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_04_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_06_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_07_hd, 0, i0);
					ZoneManager.getInstance().areaSetOnOff(AREA_effect_2s_08_hd, 0, i0);
				}
			}
		}
		else if(eventId == 23140055)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				if(player.getAbnormalLevelByType(SKILL_death_clock.getId()) > 0)
				{
					player.stopEffect(SKILL_death_clock.getId());
				}
			}
		}
		else if(eventId == 23140057)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				if(player.getAbnormalLevelByType(SKILL_area_lv1.getId()) > 0)
				{
					if(debug_mode > 0)
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, "area lv1 dispel : " + player.getName());
					}
					player.stopEffect(SKILL_area_lv1.getId());
				}
			}
		}
		else if(eventId == 23140058)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				if(player.isQuestStarted(10286))
				{
					QuestState st = player.getQuestState(10286);
					st.setMemoState(10);
					st.setCond(7);
					st.getQuest().showQuestMark(st.getPlayer());
					st.playSound(Quest.SOUND_MIDDLE);
				}
			}
		}
		else if(eventId == 23140059)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				if(player.getAbnormalLevelByType(SKILL_area_lv1.getId()) > 0)
				{
					if(debug_mode > 0)
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, "area lv1 dispel : " + player.getName());
					}
					player.stopEffect(SKILL_area_lv1.getId());
				}
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "leave " + player.getName());
				}
				player.teleToClosestTown();
			}
		}
		else if(eventId == 23140061)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				if((Integer) arg1 == 0 && (Integer) arg2 == 0)
				{
					Functions.sendUIEventFStr(player, 1, 0, 0, "0", String.valueOf(arg1), String.valueOf(arg2), "0", "0", 1801110);
				}
				else
				{
					Functions.sendUIEventFStr(player, 0, 0, 0, "0", String.valueOf(arg1), String.valueOf(arg2), "0", "0", 1801110);
				}
			}
		}
		else if(eventId == 23140022)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.l_ai2 = (Long) arg1;
			}
		}
		else if(eventId == 23140014)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai2);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140014, 0);
			}
		}
		else if(eventId == 23140019)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai2);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140019, 0);
			}
			_thisActor.i_ai0 = 5;
		}
		else if(eventId == 23140063)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.onDecay();
		}
		else if(eventId == 23140066)
		{
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				Functions.voiceNpcEffect(player, "Freya.freya_voice_03", 0);
			}
		}
		else if(eventId == 23140067)
		{
			int i0 = Rnd.get(3);
			String s0 = "";
			switch(i0)
			{
				case 0:
					s0 = "Freya.freya_voice_09";
					break;
				case 1:
					s0 = "Freya.freya_voice_10";
					break;
				case 2:
					s0 = "Freya.freya_voice_11";
					break;
			}
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				Functions.voiceNpcEffect(player, s0, 0);
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "voice blizzard : " + player.getName());
				}
			}
		}
		else if(eventId == 23140068)
		{
			int i0 = 0;
			String s0 = "";
			if(_thisActor.i_ai3 == 0)
			{
				i0 = Rnd.get(5);
			}
			else if(_thisActor.i_ai3 == 1)
			{
				i0 = Rnd.get(8);
			}
			switch(i0)
			{
				case 0:
					s0 = "Freya.freya_voice_04";
					break;
				case 1:
					s0 = "Freya.freya_voice_05";
					break;
				case 2:
					s0 = "Freya.freya_voice_06";
					break;
				case 3:
					s0 = "Freya.freya_voice_07";
					break;
				case 4:
					s0 = "Freya.freya_voice_08";
					break;
				case 5:
					s0 = "Freya.freya_voice_12";
					break;
				case 6:
					s0 = "Freya.freya_voice_13";
					break;
				case 7:
					s0 = "Freya.freya_voice_14";
					break;
			}
			
			for(L2Player player : _thisActor.getInstanceZone().getPlayersInside())
			{
				Functions.voiceNpcEffect(player, s0, 0);
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "voice normal : " + player.getName());
				}
			}
		}
	}
}