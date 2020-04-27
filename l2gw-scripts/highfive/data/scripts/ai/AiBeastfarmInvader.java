package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 09.09.11 5:49
 */
public class AiBeastfarmInvader extends WarriorUseSkill
{
	public int Skill01_Prob = 3333;
	public L2Skill SelfBuff = null;
	public int pet_attack_prob = 1000;
	public int event_spawn_prob = 33;
	public int debug_mode = 0;

	public AiBeastfarmInvader(L2Character actor)
	{
		super(actor);
		Skill01_ID = null;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(Rnd.get(10000) < event_spawn_prob)
		{
			_thisActor.createOnePrivate(18905, "AiBeastfarmInvaderEvent", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
			_thisActor.onDecay();
		}
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

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

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.700000 && _thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			if(SelfBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff.getId()))
			{
				addUseSkillDesire(_thisActor, SelfBuff, 1, 1, 10000000);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && CategoryManager.isInCategory(121, creature.getNpcId()))
		{
			if(Rnd.get(10000) < pet_attack_prob)
			{
				addAttackDesire(creature, 1, 1000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21150004)
		{
			if(Rnd.get(10000) < pet_attack_prob)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					addAttackDesire(c0, 1000, 0);
				}
			}
		}
	}
}
