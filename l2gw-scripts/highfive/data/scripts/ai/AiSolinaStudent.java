package ai;

import ai.base.Warrior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 14.10.11 22:35
 */
public class AiSolinaStudent extends Warrior
{
	public int MoveAroundSocial = 0;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(413532161);
	public int timer = 5000;

	public AiSolinaStudent(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		_thisActor.i_ai3 = 0;
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && _thisActor.getMostHated() == null)
		{
			if(creature.isPlayer() && creature.getPlayer().getActiveWeaponInstance() != null)
			{
				if(Rnd.get(100) < 3)
				{
					if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(creature, Skill01_ID, 0, 1, 1000000);
					}
					_thisActor.c_ai0 = creature.getStoredId();
					if(_thisActor.i_ai3 == 0)
					{
						addTimer(timer, 20000);
						_thisActor.i_ai3 = 1;
						_thisActor.c_ai1 = creature.getStoredId();
					}
				}
				else
				{
					addAttackDesire(creature, 1, 200);
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai3 == 0)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(_thisActor.getMostHated() != null)
				{
					if(_thisActor.getMostHated() == attacker)
					{
						addTimer(timer, 20000);
						_thisActor.i_ai3 = 1;
						_thisActor.c_ai1 = attacker.getStoredId();
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == timer)
		{
			if(Rnd.get(100) < 3)
			{
				if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
				{
					addUseSkillDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1), Skill01_ID, 0, 1, 1000000);
				}
				_thisActor.i_ai3 = 0;
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == Skill01_ID)
		{
			if(_thisActor.i_ai4 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1121006);
				_thisActor.i_ai4 = 1;
				_thisActor.i_ai3 = 0;
			}
			addAttackDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), 1, 10000);
		}
	}
}