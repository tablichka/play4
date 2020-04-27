package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 29.09.11 19:03
 */
public class WizardParameter extends MonsterParameter
{
	public WizardParameter(L2Character actor)
	{
		super(actor);
		SpecialSkill = null;
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(c0 != null)
			{
				_thisActor.addDamageHate(c0, 0, 1);
				addUseSkillDesire(c0, 305594369, 0, 1, 1000000);
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
		else if(timerId == 2)
		{
			if(IsVs == 1)
			{
				_thisActor.c_ai0 = _thisActor.getStoredId();
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

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
			if(SetCurse != null && Rnd.get(100) < 3 && attacker == _thisActor.getMostHated())
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000452);
				if(SetCurse.getMpConsume() < _thisActor.getCurrentMp() && SetCurse.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SetCurse.getId()))
				{
					addUseSkillDesire(attacker, SetCurse, 0, 1, 1000000);
				}
			}
		}

		/*
		if( SoulShot != 0 )
		{
			int i0 = gg.GetAbnormalLevel(_thisActor, myself.Skill_GetAbnormalType(6553601));
			int i1 = gg.GetAbnormalLevel(_thisActor, myself.Skill_GetAbnormalType(269811713));
			if( i0 <= 0 && i1 <= 0 )
			{
				if( Rnd.get(100) < SoulShotRate )
				{
					myself.UseSoulShot(SoulShot);
				}
			}
		}
		if( SpiritShot != 0 )
		{
			int i0 = gg.GetAbnormalLevel(_thisActor, myself.Skill_GetAbnormalType(6553601));
			if( i0 <= 0 )
			{
				i0 = gg.GetAbnormalLevel(_thisActor, myself.Skill_GetAbnormalType(269811713));
				if( Rnd.get(100) < SpiritShotRate && i0 <= 0 )
				{
					myself.UseSpiritShot(SpiritShot, SpeedBonus, HealBonus);
				}
			}
		}
		*/

		int i11 = _thisActor.getAbnormalLevelByType(5044);

		if(LongRangeGuardRate == -1 && skill != null && (skill.getId() == 28 || skill.getId() == 680 || skill.getId() == 51 || skill.getId() == 511 || skill.getId() == 15 || skill.getId() == 254 || skill.getId() == 1069 || skill.getId() == 1097 || skill.getId() == 1042 || skill.getId() == 1072 || skill.getId() == 1170 || skill.getId() == 352 || skill.getId() == 358 || skill.getId() == 1394 || skill.getId() == 695 || skill.getId() == 115 || skill.getId() == 1083 || skill.getId() == 1160 || skill.getId() == 1164 || skill.getId() == 1201 || skill.getId() == 1206 || skill.getId() == 1222 || skill.getId() == 1223 || skill.getId() == 1224 || skill.getId() == 1092 || skill.getId() == 65 || skill.getId() == 106 || skill.getId() == 122 || skill.getId() == 127 || skill.getId() == 1049 || skill.getId() == 1064 || skill.getId() == 1071 || skill.getId() == 1074 || skill.getId() == 1169 || skill.getId() == 1263 || skill.getId() == 1269 || skill.getId() == 352 || skill.getId() == 353 || skill.getId() == 1336 || skill.getId() == 1337 || skill.getId() == 1338 || skill.getId() == 1358 || skill.getId() == 1359 || skill.getId() == 402 || skill.getId() == 403 || skill.getId() == 412 || skill.getId() == 1386 || skill.getId() == 1394 || skill.getId() == 1396 || skill.getId() == 485 || skill.getId() == 501 || skill.getId() == 1445 || skill.getId() == 1446 || skill.getId() == 1447 || skill.getId() == 522 || skill.getId() == 531 || skill.getId() == 1481 || skill.getId() == 1482 || skill.getId() == 1483 || skill.getId() == 1484 || skill.getId() == 1485 || skill.getId() == 1486 || skill.getId() == 695 || skill.getId() == 696 || skill.getId() == 716 || skill.getId() == 775 || skill.getId() == 1511 || skill.getId() == 792 || skill.getId() == 1524 || skill.getId() == 1529))
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
			else if(i11 > 0)
			{
				_thisActor.stopEffect(5044);
			}
		}
		if(AttackLowLevel == 1 && !_thisActor.isMoving)
		{
			addTimer(1, 7000);
		}
		if(AttackLowHP == 1 && attacker.getCurrentHp() / attacker.getMaxHp() * 100 < 30 && Rnd.get(100) < 10)
		{
			L2Character c0 = _thisActor.getMostHated();

			if(c0 != null)
			{
				if(c0 != attacker)
				{
					_thisActor.removeAllHateInfoIF(0, 0);
					if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						if(_thisActor.getAggroListSize() == 0)
						{
							float f0 = 0;
							if(SetHateGroup >= 0)
							{
								if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
								{
									f0 += SetHateGroupRatio;
								}
							}
							if(attacker.getActiveClass() == SetHateOccupation)
							{
								f0 += SetHateOccupationRatio;
							}
							if(SetHateRace == attacker.getPlayer().getRace().ordinal())
							{
								f0 += SetHateRaceRatio;
							}

							f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
							_thisActor.addDamageHate(attacker, damage, (long) (f0 * 100) + 300);
						}
						else
						{
							float f0 = 0;
							if(SetHateGroup >= 0)
							{
								if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
								{
									f0 += SetHateGroupRatio;
								}
							}
							if(attacker.getActiveClass() == SetHateOccupation)
							{
								f0 += SetHateOccupationRatio;
							}
							if(SetHateRace == attacker.getPlayer().getRace().ordinal())
							{
								f0 += SetHateRaceRatio;
							}
							f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
							_thisActor.addDamageHate(attacker, damage, (long) (f0 * 100));
						}
					}
				}
			}
		}
		if(HelpHeroSilhouette != 0)
		{
			if(attacker.getCurrentHp() / attacker.getMaxHp() * 100 < 20 && Rnd.get(100) < 3 && attacker.isPlayer())
			{
				_thisActor.createOnePrivate(HelpHeroSilhouette, HelpHeroAI, 0, 0, (_thisActor.getX()) + 80, (_thisActor.getY()) + 80, _thisActor.getZ(), 0, 0, 0, getStoredIdFromCreature(_thisActor));
			}
		}
		if(SpecialSkill != null)
		{
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 30 && Rnd.get(100) < 10)
			{
				if(_thisActor.getAbnormalLevelByType(SpecialSkill.getId()) <= 0)
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

		L2Character c0 = _thisActor.getMostHated();

		if(Rnd.get(100) < 5 && c0 != null && ShoutTarget != 0)
		{
			if(c0 == attacker)
			{
				broadcastScriptEvent(10016, getStoredIdFromCreature(attacker), null, 300);
			}
		}

		if(Rnd.get(100) < 5 && SelfExplosion != null)
		{
			int i0 = (int) (_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100);
			i0 = 10 - i0 / 10;

			if(i0 > Rnd.get(100))
			{
				if(SelfExplosion.getMpConsume() < _thisActor.getCurrentMp() && SelfExplosion.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfExplosion.getId()))
				{
					addUseSkillDesire(_thisActor, SelfExplosion, 0, 1, 1000000);
				}
				if(SelfExplosion.getMpConsume() < _thisActor.getCurrentMp() && SelfExplosion.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfExplosion.getId()))
				{
					addUseSkillDesire(_thisActor, SelfExplosion, 0, 1, 1000000);
				}
				if(SelfExplosion.getMpConsume() < _thisActor.getCurrentMp() && SelfExplosion.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfExplosion.getId()))
				{
					addUseSkillDesire(_thisActor, SelfExplosion, 0, 1, 1000000);
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

						_thisActor.createOnePrivate(step1, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), 1000, _thisActor.c_ai0, 1);
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
						_thisActor.createOnePrivate(step2, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), 1000, _thisActor.c_ai0, 2);
						_thisActor.onDecay();
					}
					break;
				case 2:
					if(_thisActor.param3 < IsTransform && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 30 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 5 && Rnd.get(100) < 10)
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
						_thisActor.createOnePrivate(step3, _thisActor.getTemplate().ai_type, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), 1000, _thisActor.c_ai0, 3);
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

			L2Character c0 = _thisActor.getMostHated();

			if(c0 != null)
			{
				if(c0 != attacker)
				{
					_thisActor.removeAllHateInfoIF(0, 0);
					if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						if(_thisActor.getAggroListSize() == 0)
						{
							float f0 = 0;
							if(SetHateGroup >= 0)
							{
								if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
								{
									f0 += SetHateGroupRatio;
								}
							}
							if(attacker.getActiveClass() == SetHateOccupation)
							{
								f0 += SetHateOccupationRatio;
							}
							if(SetHateRace == attacker.getPlayer().getRace().ordinal())
							{
								f0 += SetHateRaceRatio;
							}

							f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
							_thisActor.addDamageHate(attacker, 0, (long) (f0 * 100) + 300);
						}
						else
						{
							float f0 = 0;
							if(SetHateGroup >= 0)
							{
								if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
								{
									f0 += SetHateGroupRatio;
								}
							}
							if(attacker.getActiveClass() == SetHateOccupation)
							{
								f0 += SetHateOccupationRatio;
							}
							if(SetHateRace == attacker.getPlayer().getRace().ordinal())
							{
								f0 += SetHateRaceRatio;
							}
							f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
							_thisActor.addDamageHate(attacker, 0, (long) (f0 * 100));
						}
					}
				}
			}
		}
		if(AttackLowLevel == 1 && !_thisActor.isMoving)
		{
			addTimer(1, 7000);
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
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
		if(AttackLowLevel == 1)
		{
			if(!_thisActor.isMoving && _thisActor.getLoc().distance3D(creature.getLoc()) < 300)
			{
				if((creature.getLevel() + 15) < _thisActor.getLevel())
				{
					_thisActor.removeAllHateInfoIF(0, 0);
					if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()))
					{
						_thisActor.addDamageHate(creature, 0, 700);
					}
				}
				addTimer(1, 7000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if((caster.getLevel() + 15) < _thisActor.getLevel())
		{
			_thisActor.removeAllHateInfoIF(0, 0);
			if(skill.getEffectPoint() > 0)
			{
				if(!_thisActor.isMoving && _thisActor.getMostHated() == caster)
				{
					int i0 = skill.getEffectPoint();
					float f0 = 0;
					if(SetHateGroup >= 0)
					{
						if(CategoryManager.isInCategory(SetHateGroup, caster.getActiveClass()))
						{
							f0 += SetHateGroupRatio;
						}
					}
					if(caster.getActiveClass() == SetHateOccupation)
					{
						f0 += SetHateOccupationRatio;
					}
					if(SetHateRace == caster.getPlayer().getRace().ordinal())
					{
						f0 += SetHateRaceRatio;
					}

					f0 = (float) (1.000000 * i0 / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * i0 / (_thisActor.getLevel() + 7));
					_thisActor.addDamageHate(caster, 0, (long) (f0 * 150));
				}
				else
				{
					int i0 = skill.getEffectPoint();
					float f0 = 0;
					if(SetHateGroup >= 0)
					{
						if(CategoryManager.isInCategory(SetHateGroup, caster.getActiveClass()))
						{
							f0 += SetHateGroupRatio;
						}
					}
					if(caster.getActiveClass() == SetHateOccupation)
					{
						f0 += SetHateOccupationRatio;
					}
					if(SetHateRace == caster.getPlayer().getRace().ordinal())
					{
						f0 += SetHateRaceRatio;
					}
					f0 = (float) (1.000000 * i0 / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * i0 / (_thisActor.getLevel() + 7));
					_thisActor.addDamageHate(caster, 0, (long) (f0 * 75));
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10016 && Rnd.get(100) < 50)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.removeAllHateInfoIF(0, 0);
				if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					_thisActor.addDamageHate(c0, 0, 200);
				}
			}
		}
		else if(eventId == 10020)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.lookNeighbor(500);
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
}