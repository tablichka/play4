package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 14.10.11 22:25
 */
public class AiSolinaLearner extends WizardUseSkill
{
	public int dist_check = 5000;

	public AiSolinaLearner(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(413335553);
		Skill01_Check_Dist = 1;
		Skill01_Dist_Min = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(413401089);
		Skill02_Check_Dist = 0;
		Skill02_Dist_Min = 500;
		Skill02_Dist_Max = 2000;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && _thisActor.getMostHated() == null)
		{
			if(creature.isPlayer() && creature.getPlayer().getActiveWeaponInstance() != null)
			{
				if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
				{
					addUseSkillDesire(creature, Skill02_ID, 0, 1, 1000000);
				}
				_thisActor.c_ai0 = creature.getStoredId();
			}
			super.onEvtSeeCreature(creature);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(Rnd.get(100) < 30)
		{
			if(_thisActor.i_ai2 == 0 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 400)
			{
				addTimer(100002, 2000);
				_thisActor.i_ai2 = 1;
				_thisActor.c_ai1 = attacker.getStoredId();
			}
		}
		else if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(_thisActor.getMostHated() == attacker)
				{
					if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.i_ai0 = 1;
						addAttackDesire(attacker, 1, 1000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 100002)
		{
			if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
			{
				clearTasks();
				L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1);
				addFleeDesire(c1, 10000000000L);
				addTimer(dist_check, 1000);
			}
			else
			{
				_thisActor.i_ai2 = 0;
			}
		}
		else if(timerId == dist_check)
		{
			L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1);
			if(c1 != null && _thisActor.getLoc().distance3D(c1.getLoc()) > 200)
			{
				clearTasks();
				if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(Skill02_ID.getId()))
					{
						addUseSkillDesire(c1, Skill02_ID, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(c1, Skill02_ID, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(c1, 1, 1000);
				}
				_thisActor.i_ai3 = 1;
				_thisActor.i_ai2 = 0;
			}
			else
			{
				addTimer(dist_check, 1000);
				_thisActor.i_ai2 = 0;
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor))
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || !CategoryManager.isInCategory(12, attacker.getNpcId())))
			{
				if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
				{
					if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill02_ID, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(attacker, Skill02_ID, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.i_ai0 = 1;
						addAttackDesire(attacker, 1, 1000);
					}
				}
				else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(attacker, 1, 1000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
		L2Character c0 = h0 != null ? h0.getAttacker() : null;

		if(c0 != null)
		{
			if(_thisActor.i_ai3 == 1 && Rnd.get(100) < 33)
			{
				if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(c0, Skill01_ID, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(c0, Skill01_ID, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(c0, 1, 1000);
				}
				_thisActor.i_ai3 = 0;
			}
			else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp())
			{
				if(!_thisActor.isSkillDisabled(Skill02_ID.getId()))
				{
					addUseSkillDesire(c0, Skill02_ID, 0, 1, 1000000);
				}
				else
				{
					addUseSkillDesire(c0, Skill02_ID, 0, 1, 1000000);
				}
			}
			else
			{
				_thisActor.i_ai0 = 1;
				addAttackDesire(c0, 1, 1000);
			}
		}
		else if(skill == Skill02_ID)
		{
			if(_thisActor.i_ai4 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1121006);
				_thisActor.i_ai4 = 1;
			}

			addAttackDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), 1, 10000);
		}
		super.onEvtFinishCasting(skill);
	}
}