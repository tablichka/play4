package ai.base;

import ai.CombatMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 12.12.11 16:18
 */
public class AiASeedEliteMonster extends CombatMonster
{
	public int FieldCycle_ID = 0;
	public int FieldCycle_point = 0;
	public int max_desire = 10000000;

	public AiASeedEliteMonster(L2Character actor)
	{
		super(actor);
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
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(FieldCycle_ID == 4)
		{
			int i0 = ServerVariables.getInt("GM_" + 34, -1);
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
			int i0 = ServerVariables.getInt("GM_" + 36, -1);
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
			int i0 = ServerVariables.getInt("GM_" + 35, -1);
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

		i0 = 0;
		L2Player c0 = null;

		if(killer != null)
		{
			if(killer.isPlayer())
			{
				c0 = (L2Player) killer;
			}
			else if(CategoryManager.isInCategory(12, killer.getNpcId()))
			{
				c0 = killer.getPlayer();
			}

			if(c0 != null)
			{
				L2Party party0 = c0.getParty();
				L2Player c1 = null;
				if(party0 != null)
				{
					c1 = party0.getRandomMember();
					i0 = 10 + 10 * party0.getMemberCount();

					if(FieldCycleManager.getStep(FieldCycle_ID) == 2)
					{
						i0 *= 2;
					}
				}
				if(c1 != null)
				{
					if(_thisActor.isInRange(c0, 2000) && _thisActor.isInRange(c1, 2000) && Rnd.get(1000) < i0)
					{
						Location pos0 = Location.coordsRandomize(c1, 10, 40);
						_thisActor.createOnePrivate(18839, "AiMarguene", 0, 0, pos0.getX(), pos0.getY(), pos0.getY(), 0, 0, 0, getStoredIdFromCreature(c1));
					}
				}
			}
		}

		if(c0 != null)
		{
			if(FieldCycle_ID == 4)
			{
				i0 = ServerVariables.getInt("GM_" + 34, -1);
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
				i0 = ServerVariables.getInt("GM_" + 36, -1);
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
				i0 = ServerVariables.getInt("GM_" + 35, -1);
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
}