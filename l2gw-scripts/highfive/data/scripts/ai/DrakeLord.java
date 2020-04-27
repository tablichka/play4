package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.TerritoryTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 24.09.11 21:30
 */
public class DrakeLord extends AntarasCaveRaidBasic
{
	public int USE_SKILL03_TIME = 4000;
	public int USE_SKILL03A_TIME = 4001;
	public int USE_SKILL04_TIME = 4002;
	public int USE_SKILL04A_TIME = 4003;
	public int OBJECT2_TIME = 4004;
	public String TRR_DRAKE_LORD = "giran04_mb2421_03";
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(441188353);
	public L2Skill Skill04_ID = SkillTable.getInstance().getInfo(441253889);
	public L2Skill Skill04A_ID = SkillTable.getInstance().getInfo(452984833);
	public int object2 = 18963;
	public String ai_object2 = "PetrifyObject";

	public DrakeLord(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(451608577);
		Skill02_ID = SkillTable.getInstance().getInfo(451674113);
		corpse = 32884;
		ai_corpse = "CorpseDrakeLord";
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			if(_thisActor.i_ai2 == 1)
			{
				_thisActor.addDamageHate(creature, 0, 1);
			}
			if(_thisActor.i_ai6 == 0)
			{
				_thisActor.i_ai6 = 1;
				addTimer(USE_SKILL04_TIME, (30 + Rnd.get(30)) * 1000);
				addTimer(USE_SKILL03A_TIME, (120 + Rnd.get(60)) * 1000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == USE_SKILL04_TIME)
		{
			addTimer(USE_SKILL04_TIME, (30 + Rnd.get(30)) * 1000);
			if(_thisActor.i_ai0 == 1)
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					L2Character c0 = h0.getAttacker();
					if(c0 != null)
					{
						addUseSkillDesire(c0, Skill04_ID, 0, 1, 10000000000L);
						_thisActor.c_ai0 = c0.getStoredId();
						addTimer(USE_SKILL04A_TIME, 10000);
					}
				}
			}
		}
		else if(timerId == USE_SKILL03A_TIME)
		{
			_thisActor.i_ai2 = 1;
			addTimer(USE_SKILL03_TIME, 5000);
		}
		else if(timerId == USE_SKILL03_TIME)
		{
			_thisActor.i_ai2 = 1;
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(USE_SKILL03A_TIME, (120 + Rnd.get(60)) * 1000);
				addUseSkillDesire(_thisActor, Skill03_ID, 0, 1, 10000000000L);
				L2Territory terr = TerritoryTable.getInstance().getLocations().get(TRR_DRAKE_LORD);
				if(terr != null)
				{
					int[] p = terr.getRandomPoint(false);
					Location pos0 = new Location(p[0], p[1], p[2]);

					_thisActor.removeAllHateInfoIF(1, 0);
					_thisActor.removeAllHateInfoIF(3, 1000);
					if(_thisActor.getAggroListSize() > 0)
					{
						for(L2NpcInstance.AggroInfo ai : _thisActor.getAggroList().values())
						{
							if(ai != null)
							{
								L2Character c0 = ai.getAttacker();
								if(c0 != null && c0.isPlayer() && _thisActor.getLoc().distance3D(c0.getLoc()) < 1000)
								{
									c0.teleToLocation(pos0.getX() + Rnd.get(30) - Rnd.get(30), pos0.getY() + Rnd.get(30) - Rnd.get(30), pos0.getZ());
								}
							}
						}
					}

					p = terr.getRandomPoint(false);
					pos0 = new Location(p[0], p[1], p[2]);
					_thisActor.lookNeighbor(1500);
					_thisActor.createOnePrivate(object2, ai_object2, 0, 0, pos0.getX(), pos0.getY(), pos0.getY(), 0, 0, 1, 0);
				}

				addTimer(OBJECT2_TIME, 5000);
			}
		}
		else if(timerId == USE_SKILL04A_TIME)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				addUseSkillDesire(c0, Skill04A_ID, 0, 1, 10000000000L);
			}
		}
		else if(timerId == OBJECT2_TIME)
		{
			_thisActor.i_ai2 = 0;
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
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
