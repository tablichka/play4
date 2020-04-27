package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 24.09.11 18:57
 */
public class BehemothLeader extends AntarasCaveRaidBasic
{
	public int USE_SKILL03_TIME = 4000;
	public int USE_SKILL04_TIME = 4001;
	public int TURN_AREA_OFF = 4002;
	public int USE_SKILL03A_TIME = 4003;
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(441712641);
	public L2Skill Skill03_ID_P = SkillTable.getInstance().getInfo(441647105);
	public L2Skill Skill03_ID_F = SkillTable.getInstance().getInfo(441778177);
	public L2Skill Skill04_ID = SkillTable.getInstance().getInfo(458752001);
	public int object1a = 18964;
	public int object1b = 18965;
	public int object1c = 18966;
	public String ai_object1 = "ChannelObject";
	public int object2 = 18963;
	public String ai_object2 = "PetrifyObject";

	public BehemothLeader(L2Character actor)
	{
		super(actor);
		SPAWN_HOLD_MON_TIME = 40;
		Skill01_ID = SkillTable.getInstance().getInfo(451739649);
		Skill02_ID = SkillTable.getInstance().getInfo(451805185);
		corpse = 32885;
		ai_corpse = "CorpseBehemothLeader";
		AreaName = "24_21_skill_behemoth_leader";
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && _thisActor.i_ai6 == 0)
		{
			_thisActor.i_ai6 = 1;
			addTimer(USE_SKILL03_TIME, 60 + Rnd.get(30) * 1000);
			addTimer(USE_SKILL04_TIME, 30 + Rnd.get(30) * 1000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == USE_SKILL03_TIME)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai3 == 0)
			{
				_thisActor.i_ai1 = 0;
				_thisActor.i_ai3 = 1;
				switch(Rnd.get(6))
				{
					case 0:
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 1);
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 2);
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 3);
						break;
					case 1:
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 1);
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 3);
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 2);
						break;
					case 2:
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 2);
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 1);
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 3);
						break;
					case 3:
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 2);
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 3);
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 1);
						break;
					case 4:
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 3);
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 1);
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 2);
						break;
					case 5:
						_thisActor.createOnePrivate(object1c, ai_object1, 0, 0, 145280, 120489, -3912, 57917, 1000, getStoredIdFromCreature(_thisActor), 3);
						_thisActor.createOnePrivate(object1b, ai_object1, 0, 0, 145941, 119081, -3912, 16383, 1000, getStoredIdFromCreature(_thisActor), 2);
						_thisActor.createOnePrivate(object1a, ai_object1, 0, 0, 146116, 120316, -3912, 37739, 1000, getStoredIdFromCreature(_thisActor), 1);
						break;
				}
				addTimer(USE_SKILL03A_TIME, (15 * 1000));
			}
		}
		else if(timerId == USE_SKILL04_TIME)
		{
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(USE_SKILL04_TIME, 30 + Rnd.get(30) * 1000);
				for(int i2 = 0; i2 < 15; i2++)
				{
					L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
					if(h0 != null)
					{
						L2Character c0 = h0.getAttacker();
						if(c0 != null && _thisActor.inMyTerritory(c0))
						{
							if(_thisActor.getLoc().distance3D(c0.getLoc()) > 300)
							{
								_thisActor.createOnePrivate(object2, ai_object2, 0, 0, c0.getX() + Rnd.get(40), c0.getY() + Rnd.get(40), c0.getZ(), 0, 0, 0, 0);
							}
						}
					}
				}
			}
		}
		else if(timerId == TURN_AREA_OFF)
		{
			ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
		}
		else if(timerId == USE_SKILL03A_TIME)
		{
			if(_thisActor.i_ai1 == 3 && _thisActor.i_ai3 == 1)
			{
				_thisActor.i_ai3 = 0;
				addUseSkillDesire(_thisActor, Skill03_ID_F, 0, 1, 10000000000L);
				broadcastScriptEvent(15007, 0, null, 4000);
				_thisActor.i_ai1 = 0;
				addTimer(USE_SKILL03_TIME, 60 + Rnd.get(30) * 1000);
			}
			if(_thisActor.i_ai1 < 3 && _thisActor.i_ai3 == 1)
			{
				_thisActor.i_ai3 = 0;
				addUseSkillDesire(_thisActor, Skill03_ID, 0, 1, 10000000000L);
				ZoneManager.getInstance().areaSetOnOff(AreaName, 1);
				broadcastScriptEvent(15007, 0, null, 4000);
				_thisActor.i_ai1 = 0;
				addTimer(USE_SKILL03_TIME, 60 + Rnd.get(30) * 1000);
				addTimer(TURN_AREA_OFF, 45000);
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15001)
		{
			_thisActor.i_ai1++;
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
		}
	}
}