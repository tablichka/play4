package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 28.09.11 0:12
 */
public class IcequeenHelpPc extends DefaultNpc
{
	public L2Skill Skill01_ID = null;
	public int Skill01_Prob = 2500;
	public L2Skill Buff = null;
	public L2Skill Dash = null;
	public int TIMER_buff = 2314017;
	public int TIMER_dash = 2314019;
	public int TIMER_call_npc = 2314020;
	public int TIMER_heal = 2314022;
	public int PosX = -1;
	public int PosY = -1;
	public int PosZ = -1;
	public int position = -1;
	public int debug_mode = 0;

	public IcequeenHelpPc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(position != 2)
		{
			if(Dash != null)
			{
				addTimer(TIMER_dash, 1000);
			}
			addTimer(TIMER_buff, 1000);
			addTimer(TIMER_heal, 2000);
			addTimer(TIMER_call_npc, 2000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			_thisActor.onDecay();
		}
		else if(eventId == 23140052)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 1000);
				if((Integer) arg2 == 2314)
				{
					addAttackDesire(c0, 1, 500);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_buff)
		{
			addUseSkillDesire(_thisActor, Buff, 1, 0, 10000000);
			addTimer(TIMER_buff, 25000);
		}
		else if(timerId == TIMER_dash)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(_thisActor.getLoc().distance3D(_thisActor.getMostHated().getLoc()) > 200)
				{
					addUseSkillDesire(_thisActor.getMostHated(), Dash, 0, 1, 1000000000);
				}
			}
			addTimer(TIMER_dash, 10000);
		}
		else if(timerId == TIMER_call_npc)
		{
			broadcastScriptEvent(23140051, getStoredIdFromCreature(_thisActor), null, 1500);
			addTimer(TIMER_call_npc, (10 * 1000));
		}
		else if(timerId == TIMER_heal)
		{
			int i0 = (int) (_thisActor.getMaxHp() * 0.250000 + _thisActor.getCurrentHp());
			if(i0 > _thisActor.getMaxHp())
			{
				_thisActor.setCurrentHp(_thisActor.getMaxHp());
			}
			else
			{
				_thisActor.setCurrentHp(i0);
			}
			addTimer(TIMER_heal, 5000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!attacker.isPlayer() && !CategoryManager.isInCategory(12, attacker.getNpcId()) && CategoryManager.isInCategory(123, attacker.getNpcId()))
		{
			addAttackDesire(attacker, 1, damage);
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
	}
}
