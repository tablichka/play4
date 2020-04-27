package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 23.09.11 19:46
 */
public class IceKnightDefeatdun extends DefaultAI
{
	public L2Skill Skill01_ID = null;
	public int Skill01_Prob = 2500;
	public L2Skill Skill02_ID = null;
	public int Skill02_Prob = 3333;

	public IceKnightDefeatdun(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		addAttackDesire(attacker, 1, (damage * 2));
		if(_thisActor.getLoc().distance3D(attacker.getLoc()) >= 500)
		{
			if(Skill02_ID != null)
			{
				if(Rnd.get(10000) < Skill02_Prob)
				{
					if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
					{
						addUseSkillDesire(attacker, Skill02_ID, 0, 1, 1000000);
					}
				}
			}
		}
		if(attacker.isPlayer())
		{
			broadcastScriptEvent(23140043, getStoredIdFromCreature(_thisActor), null, 1500);
		}
		if(Skill01_ID != null)
		{
			if(Rnd.get(10000) < Skill01_Prob)
			{
				if(Rnd.get(2) == 1)
				{
					if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
				}
				else if(_thisActor.getMostHated() != null)
				{
					if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(_thisActor.getMostHated(), Skill01_ID, 0, 1, 1000000);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		addAttackDesire(attacker, 1, 100);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140045)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.addDamageHate(c0, 0, 1);
				broadcastScriptEvent(23140043, _thisActor.getStoredId(), null, 1500);
				addTimer(4321, 3000);
			}
		}
		else if(eventId == 23140020)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 4321)
		{
			try
			{
				L2NpcInstance.AggroInfo ai = _thisActor.getAggroList().get(Rnd.get(_thisActor.getAggroListSize()));
				if(ai != null)
				{
					L2Character c0 = ai.getAttacker();
					if(c0 != null)
					{
						addAttackDesire(c0, 1, 1000);
					}
				}
			}
			catch(Exception e)
			{
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			addAttackDesire(creature, 1, 1000);
		}
	}
}
