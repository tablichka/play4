package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 09.09.11 5:53
 */
public class AiBeastfarmInvaderEvent extends WarriorUseSkill
{
	public int Skill01_Prob = 3333;
	public L2Skill Skill_Display = SkillTable.getInstance().getInfo(408551425);
	public int TIMER_despawn = 2115003;
	public int TIMER_callme = 2115007;
	public int TIMER_suicide = 2115009;
	public int reward_adena_83lv = 9031;
	public int reward_adena_84lv = 10068;

	public AiBeastfarmInvaderEvent(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		addTimer(TIMER_callme, 1000);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		int i0 = 0;
		if(eventId == 21150001)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(c0 == _thisActor)
				{
					_thisActor.i_ai0++;
					_thisActor.i_ai1 = (_thisActor.i_ai1 - 1);
					if((Integer) arg2 == 83)
					{
						_thisActor.i_ai2++;
					}
					else if((Integer) arg2 == 84)
					{
						_thisActor.i_ai3++;
					}
					switch(Rnd.get(11))
					{
						case 0:
							i0 = 421462017;
							break;
						case 1:
							i0 = 421527553;
							break;
						case 2:
							i0 = 421593089;
							break;
						case 3:
							i0 = 421658625;
							break;
						case 4:
							i0 = 437190657;
							break;
						case 5:
							i0 = 436862977;
							break;
						case 6:
							i0 = 436928513;
							break;
						case 7:
							i0 = 436994049;
							break;
						case 8:
							i0 = 437256193;
							break;
						case 9:
							i0 = 437059585;
							break;
						case 10:
							i0 = 437125121;
							break;
					}
					addUseSkillDesire(_thisActor, i0, 1, 0, 1000000);
				}
			}
			if(_thisActor.i_ai0 == 10)
			{
				addTimer(TIMER_despawn, 5 * 60 * 1000);
			}
		}
		else if(eventId == 21150004)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.i_ai1++;
				if(_thisActor.i_ai1 <= 50)
				{
					addAttackDesire(c0, 1, 1000);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_callme)
		{
			broadcastScriptEvent(21150003, 0, null, 1200);
			addTimer(TIMER_callme, (60 * 1000));
		}
		else if(timerId == TIMER_despawn)
		{
			_thisActor.onDecay();
		}
		else if(timerId == TIMER_suicide)
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.050000) && _thisActor.i_ai4 == 0 && attacker.isPlayer())
		{
			_thisActor.i_ai4 = 1;
			clearTasks();
			clientStopMoving();
			_thisActor.c_ai0 = attacker.getStoredId();
			addTimer(TIMER_suicide, 2000);
			if(_thisActor.i_ai0 != 0)
			{
				addUseSkillDesire(_thisActor, Skill_Display, 1, 0, 100000000);
			}
		}
		else if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.050000) && _thisActor.i_ai4 == 0 && !attacker.isPlayer())
		{
			_thisActor.i_ai4 = 1;
			_thisActor.setAbilityItemDrop(false);
			addTimer(TIMER_suicide, 1500);
		}
		else if(_thisActor.i_ai4 != 1)
		{
			if(!CategoryManager.isInCategory(122, attacker.getNpcId()))
			{
				addAttackDesire(attacker, 1, damage);
				if(Skill01_ID != null)
				{
					if(Rnd.get(10000) < Skill01_Prob)
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
				}
			}
			super.onEvtAttacked(attacker, damage, skill);

		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(target != null && target.getPlayer() != null && skill == Skill_Display)
		{
			if(_thisActor.i_ai0 != 0)
			{
				if(_thisActor.i_ai2 != 0)
				{
					for(int i0 = 0; i0 < _thisActor.i_ai2; i0++)
					{
						int i1 = (int) (reward_adena_83lv - reward_adena_83lv * 0.050000 + Rnd.get(reward_adena_83lv) * 0.100000);
						_thisActor.dropItem(target.getPlayer(), 57, i1);
					}
				}
				if(_thisActor.i_ai3 != 0)
				{
					for(int i0 = 0; i0 < _thisActor.i_ai3; i0++)
					{
						int i1 = (int) (reward_adena_84lv - reward_adena_84lv * 0.050000 + Rnd.get(reward_adena_84lv) * 0.100000);
						_thisActor.dropItem(target.getPlayer(), 57, i1);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && CategoryManager.isInCategory(121, creature.getNpcId()))
		{
			addAttackDesire(creature, 1, 10000);
		}
		super.onEvtSeeCreature(creature);
	}
}
