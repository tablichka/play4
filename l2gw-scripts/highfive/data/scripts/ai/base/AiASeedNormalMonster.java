package ai.base;

import ai.CombatMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 12.12.11 16:34
 */
public class AiASeedNormalMonster extends CombatMonster
{
	public int FieldCycle_ID = 0;
	public int FieldCycle_point = 0;
	public int max_desire = 10000000;

	public AiASeedNormalMonster(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtNoDesire()
	{
		if(_thisActor.isMyBossAlive())
		{
			addFollowDesire(_thisActor.getLeader(), 5);
		}
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
		}
		if(attacker.isPlayer())
		{
			_thisActor.addDamageHate(attacker, 0, 1);
		}
		else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(!attacker.getPlayer().isDead())
			{
				_thisActor.addDamageHate(attacker, 0, 2);
				_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);
			}
			else
			{
				addAttackDesire(attacker, 1, 100);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai1 = 0;
		addTimer(1044, 10000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1044)
		{
			if(!_thisActor.isMyBossAlive() && _intention != CtrlIntention.AI_INTENTION_ATTACK)
			{
				clearTasks();
				_thisActor.onDecay();
			}
			else
			{
				addTimer(1044, 10000);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(FieldCycle_ID == 4)
		{
			int i0 = ServerVariables.getInt("GM_" + 34);
			if(i0 == 2 || i0 == 3)
			{
				if(attacker.isPlayer())
				{
					if(skill != null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 30);
					}
					else if(skill == null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 10);
					}
				}
			}
		}
		else if(FieldCycle_ID == 5)
		{
			int i0 = ServerVariables.getInt("GM_" + 36);
			if(i0 == 2 || i0 == 3)
			{
				if(attacker.isPlayer())
				{
					if(skill != null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 30);
					}
					else if(skill == null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 10);
					}
				}
			}
		}
		else if(FieldCycle_ID == 6)
		{
			int i0 = ServerVariables.getInt("GM_" + 35);
			if(i0 == 2 || i0 == 3)
			{
				if(attacker.isPlayer())
				{
					if(skill != null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 30);
					}
					else if(skill == null && CategoryManager.isInCategory(5, attacker.getActiveClass()))
					{
						_thisActor.addDamageHate(attacker, 0, damage * 10);
					}
				}
			}
		}
		if(_thisActor.c_ai0 == 0)
		{
			if(CategoryManager.isInCategory(2, attacker.getActiveClass()))
			{
				_thisActor.c_ai0 = attacker.getStoredId();
			}
		}
		else if(_thisActor.c_ai1 == 0)
		{
			if(CategoryManager.isInCategory(2, attacker.getActiveClass()))
			{
				_thisActor.c_ai1 = attacker.getStoredId();
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = FieldCycleManager.getStep(FieldCycle_ID);
		int i1 = FieldCycle_point;
		if(i0 == 1)
		{
			FieldCycleManager.addPoint("npc_" + 1, FieldCycle_ID, FieldCycle_point, _thisActor);
		}
		L2Player c0 = null;
		if(killer != null)
			c0 = killer.getPlayer();

		if(c0 == null)
			return;

		if(FieldCycle_ID == 4)
		{
			i0 = ServerVariables.getInt("GM_" + 34);
			if(i0 == 3)
			{
				if(_thisActor.c_ai0 != 0 && _thisActor.c_ai1 != 0)
				{
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) > 70)
					{
						_thisActor.dropItem(c0, 8604, 1);
					}
				}
			}
		}
		else if(FieldCycle_ID == 5)
		{
			i0 = ServerVariables.getInt("GM_" + 36);
			if(i0 == 3)
			{
				if(_thisActor.c_ai0 != 0 && _thisActor.c_ai1 != 0)
				{
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) > 70)
					{
						_thisActor.dropItem(c0, 8604, 1);
					}
				}
			}
		}
		else if(FieldCycle_ID == 6)
		{
			i0 = ServerVariables.getInt("GM_" + 35);
			if(i0 == 3)
			{
				if(_thisActor.c_ai0 != 0 && _thisActor.c_ai1 != 0)
				{
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) < 70)
					{
						_thisActor.dropItem(c0, 8603, 1);
					}
					if(Rnd.get(100) > 70)
					{
						_thisActor.dropItem(c0, 8604, 1);
					}
				}
			}
		}
	}
}