package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 06.09.11 16:44
 */
public class WarriorParameter extends MonsterParameter
{
	public WarriorParameter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(IsVs == 1)
		{
			_thisActor.c_ai0 = _thisActor.getStoredId();
		}

		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(c0 != null)
			{
				addUseSkillDesire(c0, 305594369, 0, 1, 10000);
				addAttackDesire(c0, 1, 500);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1)
		{
			if(AttackLowLevel == 1)
			{
				_thisActor.lookNeighbor(300);
			}
		}
		if(timerId == 2)
		{
			if(IsVs == 1)
			{
				_thisActor.c_ai0 = _thisActor.getStoredId();
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(CreviceOfDiminsion != 0)
		{
			if(!_thisActor.inMyTerritory(attacker))
			{
				removeAttackDesire(attacker);
				return;
			}
		}
		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= 20)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(SetCurse != null && Rnd.get(100) < 3 && _thisActor.getMostHated() == attacker)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1000452);
					if(SetCurse.getMpConsume() < _thisActor.getCurrentMp() && SetCurse.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SetCurse.getId()))
					{
						addUseSkillDesire(attacker, SetCurse, 0, 1, 1000000);
					}
				}
			}
		}
		int i11 = SkillTable.getAbnormalLevel(_thisActor, 330563587);
		if(skill != null && (LongRangeGuardRate == -1 || skill.getId() == 28 || skill.getId() == 680 || skill.getId() == 51 || skill.getId() == 511 || skill.getId() == 15 || skill.getId() == 254 || skill.getId() == 1069 || skill.getId() == 1097 || skill.getId() == 1042 || skill.getId() == 1072 || skill.getId() == 1170 || skill.getId() == 352 || skill.getId() == 358 || skill.getId() == 1394 || skill.getId() == 695 || skill.getId() == 115 || skill.getId() == 1083 || skill.getId() == 1160 || skill.getId() == 1164 || skill.getId() == 1201 || skill.getId() == 1206 || skill.getId() == 1222 || skill.getId() == 1223 || skill.getId() == 1224 || skill.getId() == 1092 || skill.getId() == 65 || skill.getId() == 106 || skill.getId() == 122 || skill.getId() == 127 || skill.getId() == 1049 || skill.getId() == 1064 || skill.getId() == 1071 || skill.getId() == 1074 || skill.getId() == 1169 || skill.getId() == 1263 || skill.getId() == 1269 || skill.getId() == 352 || skill.getId() == 353 || skill.getId() == 1336 || skill.getId() == 1337 || skill.getId() == 1338 || skill.getId() == 1358 || skill.getId() == 1359 || skill.getId() == 402 || skill.getId() == 403 || skill.getId() == 412 || skill.getId() == 1386 || skill.getId() == 1394 || skill.getId() == 1396 || skill.getId() == 485 || skill.getId() == 501 || skill.getId() == 1445 || skill.getId() == 1446 || skill.getId() == 1447 || skill.getId() == 522 || skill.getId() == 531 || skill.getId() == 1481 || skill.getId() == 1482 || skill.getId() == 1483 || skill.getId() == 1484 || skill.getId() == 1485 || skill.getId() == 1486 || skill.getId() == 695 || skill.getId() == 696 || skill.getId() == 716 || skill.getId() == 775 || skill.getId() == 1511 || skill.getId() == 792 || skill.getId() == 1524 || skill.getId() == 1529))
		{
		}
		else if(LongRangeGuardRate > 0)
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 150)
			{
				if(i11 <= 0 && Rnd.get(100) < LongRangeGuardRate)
				{
					addUseSkillDesire(_thisActor, 330563587, 1, 1, 10000000000L);
				}
			}
			else if(i11 <= 0)
			{
			}
			else
			{
				_thisActor.stopEffect(330563587 >> 16);
			}
		}
		if(AttackLowLevel == 1 && !_thisActor.isMoving)
		{
			addTimer(1, 7000);
		}
		if(AttackLowHP == 1 && attacker.getCurrentHp() / attacker.getMaxHp() * 100 < 30 && Rnd.get(100) < 10)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(_thisActor.getMostHated() != attacker)
				{
					removeAllAttackDesire();
					if(attacker.isPlayable())
					{
						addAttackDesire(attacker, 1, 100);
					}
					switch(Rnd.get(3))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000307);
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000427);
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000428);
							break;
					}
				}
			}
		}
		if(IsVs == 1)
		{
			if(_thisActor.getMostHated() != null && _thisActor.c_ai0 == _thisActor.getStoredId())
			{
				if(attacker != _thisActor.getMostHated() && attacker.isPlayer())
				{
					switch(Rnd.get(5))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000288, attacker.getName());
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000388, attacker.getName());
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000389);
							break;
						case 3:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000390);
							break;
						case 4:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000391);
							break;
					}
					_thisActor.c_ai0 = attacker.getStoredId();
					addTimer(2, 20000);
					broadcastScriptEvent(10001, getStoredIdFromCreature(attacker), null, 600);
				}
			}
		}
		if(SpecialSkill != null)
		{
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 30 && Rnd.get(100) < 10)
			{
				if(_thisActor.getAbnormalLevelBySkill(SpecialSkill) <= 0)
				{
					switch(Rnd.get(4))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000290);
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000395);
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000396);
							break;
						case 3:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000397);
							break;
					}
					if(SpecialSkill.getMpConsume() < _thisActor.getCurrentMp() && SpecialSkill.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SpecialSkill.getId()))
					{
						addUseSkillDesire(_thisActor, SpecialSkill, 1, 1, 1000000);
					}
				}
			}
		}
		if(HelpHeroSilhouette != 0)
		{
			if(attacker.getCurrentHp() / attacker.getMaxHp() * 100 < 20 && Rnd.get(100) < 3 && attacker.isPlayer())
			{
				_thisActor.createOnePrivate(HelpHeroSilhouette, HelpHeroAI, 0, 0, _thisActor.getX() + 80, _thisActor.getY() + 80, _thisActor.getZ(), 0, 0, 0, getStoredIdFromCreature(_thisActor));
			}
		}
		if(DungeonType != 0)
		{
			for(int i1 = 0; i1 < DungeonType; i1 = (i1 + 1))
			{
				_thisActor.createOnePrivate(DungeonTypeAI, DungeonTypePrivate, 0, 0, _thisActor.getX() + i1 * 20, _thisActor.getY() + i1 * 20, _thisActor.getZ(), 0, 1000, getStoredIdFromCreature(attacker), 0);
			}
			_thisActor.onDecay();
		}
		if(Rnd.get(100) < 5 && _thisActor.getMostHated() != null && ShoutTarget != 0)
		{
			if(_thisActor.getMostHated() == attacker)
			{
				broadcastScriptEvent(10016, getStoredIdFromCreature(attacker), null, 300);
			}
		}
		if(SelfExplosion != null && Rnd.get(100) < 5)
		{
			int i0 = (int) (_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100);
			if(i0 < 50)
			{
				i0 = 10 - i0 / 10;
				if(i0 > Rnd.get(100))
				{
					if(SelfExplosion.getMpConsume() < _thisActor.getCurrentMp() && SelfExplosion.getHpConsume() < _thisActor.getCurrentHp() && _thisActor.isSkillDisabled(SelfExplosion.getId()))
					{
						addUseSkillDesire(_thisActor, SelfExplosion, 0, 1, 1000000);
					}
				}
			}
		}
		if(IsTransform > 0)
		{
			switch((int) _thisActor.param3)
			{
				case 0:
					if(_thisActor.param3 < IsTransform && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 70 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 50 && Rnd.get(100) < 30)
					{
						_thisActor.c_ai0 = attacker.getStoredId();
						switch(Rnd.get(3))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000406);
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000407);
								break;
							case 2:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000408);
								break;
						}
						int i0 = _thisActor.getHeading();
						_thisActor.createOnePrivate(step1, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), i0 * 182, 1000, _thisActor.c_ai0, 1);
						_thisActor.onDecay();
					}
					break;
				case 1:
					if(_thisActor.param3 < IsTransform && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 50 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 30 && Rnd.get(100) < 20)
					{
						_thisActor.c_ai0 = attacker.getStoredId();
						switch(Rnd.get(3))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000409);
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000410);
								break;
							case 2:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000411);
								break;
						}
						int i0 = _thisActor.getHeading();
						_thisActor.createOnePrivate(step2, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), i0 * 182, 1000, _thisActor.c_ai0, 2);
						_thisActor.onDecay();
					}
					break;
				case 2:
					if(_thisActor.param3 < IsTransform && (((_thisActor.getCurrentHp() / _thisActor.getMaxHp()) * 100)) < 30 && (((_thisActor.getCurrentHp() / _thisActor.getMaxHp()) * 100)) > 5 && Rnd.get(100) < 10)
					{
						_thisActor.c_ai0 = attacker.getStoredId();
						switch(Rnd.get(3))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000412);
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000413);
								break;
							case 2:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000414);
								break;
						}
						int i0 = _thisActor.getHeading();
						_thisActor.createOnePrivate(step3, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), i0 * 182, 1000, _thisActor.c_ai0, 3);
						_thisActor.onDecay();
					}
					break;
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(AttackLowHP == 1 && attacker.getCurrentHp() / attacker.getMaxHp() * 100 < 30 && Rnd.get(100) < 3)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(_thisActor.getMostHated() != attacker)
				{
					removeAllAttackDesire();
					if(attacker.isPlayable())
					{
						addAttackDesire(attacker, 1, 100);
					}
				}
			}
		}
		if(AttackLowLevel == 1 && !_thisActor.isMoving)
		{
			addTimer(1, 7000);
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			return;
		}
		if(_thisActor.isDead())
		{
			return;
		}
		if(HalfAggressive == 1)
		{
			if(Util.getTimeHour() >= 5)
			{
				if(!_thisActor.isMoving)
				{
					if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
					{
						return;
					}

					if(SeeCreatureAttackerTime == -1)
					{
						if(SetAggressiveTime == -1)
						{
							if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(SetAggressiveTime == 0)
						{
							if(_thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
						{
							addAttackDesire(creature, 1, 200);
						}
					}
					else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
					{
						addAttackDesire(creature, 1, 200);
					}
				}
			}
			return;
		}
		else if(HalfAggressive == 2)
		{
			if(Util.getTimeHour() < 5)
			{
				if(!_thisActor.isMoving)
				{
					if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
					{
						return;
					}
					if(SeeCreatureAttackerTime == -1)
					{
						if(SetAggressiveTime == -1)
						{
							if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(SetAggressiveTime == 0)
						{
							if(_thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
						{
							addAttackDesire(creature, 1, 200);
						}
					}
					else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
					{
						addAttackDesire(creature, 1, 200);
					}
				}
			}
			return;
		}
		else if(RandomAggressive > 0)
		{
			if(Rnd.get(100) < RandomAggressive && creature.isPlayer())
			{
				if(!_thisActor.isMoving)
				{
					if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
					{
						return;
					}
					if(SeeCreatureAttackerTime == -1)
					{
						if(SetAggressiveTime == -1)
						{
							if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(SetAggressiveTime == 0)
						{
							if(_thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
						{
							addAttackDesire(creature, 1, 200);
						}
					}
					else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
					{
						addAttackDesire(creature, 1, 200);
					}
				}
				return;
			}
			else if(!_thisActor.isMoving)
			{
				removeAllAttackDesire();
			}
		}
		if(AttackLowLevel == 1)
		{
			if((!_thisActor.isMoving) && _thisActor.getLoc().distance3D(creature.getLoc()) < 300)
			{
				if((creature.getLevel() + 15) < _thisActor.getLevel())
				{
					removeAllAttackDesire();
					if(creature.isPlayable())
					{
						addAttackDesire(creature, 1, (7 * 100));
					}
				}
				addTimer(1, 7000);
			}
		}
		if(IsVs == 1)
		{
			if(creature.isPlayer())
			{
				if(creature.getLevel() > (_thisActor.getLevel() - 2) && creature.getLevel() < (_thisActor.getLevel() + 2) && !_thisActor.isMoving)
				{
					switch(Rnd.get(5))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000287, creature.getName());
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000384, creature.getName());
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000385, creature.getName());
							break;
						case 3:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000386, creature.getName());
							break;
						case 4:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000387, creature.getName());
							break;
					}
					if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
					{
						return;
					}
					if(SeeCreatureAttackerTime == -1)
					{
						if(SetAggressiveTime == -1)
						{
							if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(SetAggressiveTime == 0)
						{
							if(_thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
						{
							addAttackDesire(creature, 1, 200);
						}
					}
					else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
					{
						addAttackDesire(creature, 1, 200);
					}
				}
			}
		}
		if(DaggerBackAttack == 1)
		{
			if(creature.isPlayer() && Rnd.get(100) < 50 && !_thisActor.isMoving)
			{
				if(_thisActor.getLoc().distance3D(creature.getLoc()) < 100 && _thisActor.isBehindTarget(creature))
				{
					switch(Rnd.get(4))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000286, creature.getName());
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000381, creature.getName());
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000382);
							break;
						case 3:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000383);
							break;
					}

					if(SeeCreatureAttackerTime == -1)
					{
						if(SetAggressiveTime == -1)
						{
							if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(SetAggressiveTime == 0)
						{
							if(_thisActor.inMyTerritory(_thisActor))
							{
								addAttackDesire(creature, 1, 200);
							}
						}
						else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
						{
							addAttackDesire(creature, 1, 200);
						}
					}
					else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
					{
						addAttackDesire(creature, 1, 200);
					}
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		L2Character target = caster.getCastingTarget();
		if((caster.getLevel() + 15) < _thisActor.getLevel())
		{
			removeAllAttackDesire();
			if(skill.getEffectPoint() > 0)
			{
				if(!_thisActor.isMoving && _thisActor.getMostHated() == target)
				{
					int i0 = skill.getEffectPoint();
					float f0 = 0;
					if(SetHateGroup >= 0)
					{
						if(CategoryManager.isInCategory(SetHateGroup, caster))
						{
							f0 += SetHateGroupRatio;
						}
					}
					if(caster.getActiveClass() == SetHateOccupation)
					{
						f0 += SetHateOccupationRatio;
					}

					if(caster.isPlayer() && SetHateRace == ((L2Player) caster).getRace().ordinal())
					{
						f0 += SetHateRaceRatio;
					}

					f0 = i0 / (_thisActor.getLevel() + 7) + f0 / 100 * i0 / (_thisActor.getLevel() + 7);
					addAttackDesire(caster, 1, (long) (f0 * 150));
				}
			}
			if(_pathfind_fails > 10 && caster == _thisActor.getMostHated() && (int) _thisActor.getCurrentHp() != _thisActor.getMaxHp())
			{
				ThreadPoolManager.getInstance().executeAi(new Teleport(GeoEngine.moveCheckForAI(caster.getLoc(), _thisActor.getLoc(), caster.getReflection())), false);
			}
		}
		if(SwapPosition != 0)
		{
			if(!_thisActor.isMoving && Rnd.get(100) < SwapPosition)
			{
				L2Character top_desire_target = _thisActor.getMostHated();
				if(top_desire_target != null && caster != top_desire_target && (CategoryManager.isInCategory(0, top_desire_target.getActiveClass()) || CategoryManager.isInCategory(3, top_desire_target.getActiveClass())))
				{
					if(_thisActor.getLoc().distance3D(top_desire_target.getLoc()) < _thisActor.getLoc().distance3D(caster.getLoc()))
					{
						if(_thisActor.getLoc().distance3D(caster.getLoc()) < 900)
						{
							Location loc = caster.getLoc();
							caster.teleToLocation(top_desire_target.getLoc());
							top_desire_target.teleToLocation(loc);
							addAttackDesire(caster, 1, 1000);
						}
					}
				}
			}
		}

		super.onEvtSeeSpell(skill, caster);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10001 && !_thisActor.isMoving)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(_thisActor.isDead())
				{
					return;
				}
				if(_thisActor.c_ai0 != c0.getStoredId())
				{
					switch(Rnd.get(3))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000392);
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000393);
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1000394);
							break;
					}
					addAttackDesire(c0, 1, 1000000);
				}
			}
		}
		if(eventId == 10016 && Rnd.get(100) < 50)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(_thisActor.isDead())
				{
					return;
				}
				removeAllAttackDesire();
				if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					addAttackDesire(c0, 1, 100);
				}
			}
		}
		if(eventId == 10020)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(_thisActor.isDead())
				{
					return;
				}
				if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					addAttackDesire(c0, 1, 100);
				}
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(SelfExplosion != null)
		{
			if(skill == SelfExplosion)
			{
				_thisActor.doDie(null);
			}
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}
}
