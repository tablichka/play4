package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 23.09.11 19:57
 */
public class AiIcequeenQGuard extends DefaultAI
{
	public L2Skill Skill01_ID = null;
	public int Skill01_Prob = 2000;
	public int is_leader = -1;
	public int TIMER_call_knight = 2314888;
	public int TIMER_soulshot = 2314889;
	public String MAKER_freya_defeatdun = "schuttgart29_2314_300m1";

	public AiIcequeenQGuard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.l_ai1 = 0;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return false;

		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
		if( c0 != null )
		{
			_thisActor.setRunning();
			addFollowDesire(c0, 10);
		}

		return super.thinkActive();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addAttackDesire(attacker, 1, damage);
		if( Skill01_ID != null )
		{
			if( Rnd.get(10000) < Skill01_Prob )
			{
				if( Rnd.get(2) == 1 )
				{
					if( Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()) )
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
				}
				else if( _thisActor.getMostHated() != null )
				{
					if( Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()) )
					{
						addUseSkillDesire(_thisActor.getMostHated(), Skill01_ID, 0, 1, 1000000);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if( creature.isPlayer() )
		{
			_thisActor.l_ai1 = getStoredIdFromCreature(creature);
			if( _thisActor.i_ai0 == 0 )
			{
				if( is_leader == 1 )
				{
					Instance inst = _thisActor.getInstanceZone();
					if(inst != null)
					{

						DefaultMaker maker0 = inst.getMaker(MAKER_freya_defeatdun);
						if(maker0 != null)
						{
							maker0.onScriptEvent(23140101, getStoredIdFromCreature(creature), 0);
						}
						Functions.npcSay(_thisActor, Say2C.ALL, 1801096, creature.getName());
						_thisActor.chargeShots(false);
					}
					//myself.UseSoulShot(10);
				}
				addTimer(TIMER_call_knight, ( 5 * 1000 ));
				_thisActor.i_ai0 = 1;
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 23140043 )
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if( c0 != null )
			{
				_thisActor.chargeShots(false);
				//myself.UseSoulShot(10);
				addAttackDesire(c0, 1, 150);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == TIMER_call_knight )
		{
			_thisActor.chargeShots(false);
			//myself.UseSoulShot(10);
			broadcastScriptEvent(23140045, getStoredIdFromCreature(_thisActor), null, 3500);
			addTimer(TIMER_call_knight, 3000);
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}
}