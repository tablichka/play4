package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 15.12.11 19:10
 */
public class ImmoVein extends DefaultNpc
{
	public String type = "";
	public int reward_siege = 13797;
	public int reward_rate = 1;
	public int reward_rate_wall = 50;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 100;
	public L2Skill Skill_Branding = SkillTable.getInstance().getInfo(542375937);
	public L2Skill Skill_dying_display = SkillTable.getInstance().getInfo(395640833);
	public int TM_VEIN_SIGNAL = 78001;
	public int TIME_VEIN_SIGNAL = 15;

	public ImmoVein(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(!type.equals("boss_vein") && !type.equals("wall"))
		{
			_thisActor.i_ai0 = 1;
			broadcastScriptEvent(78010058, 0L, _thisActor.getStoredId(), 500);
			addTimer(TM_VEIN_SIGNAL, TIME_VEIN_SIGNAL * 1000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!type.equals("boss_vein") && !type.equals("wall"))
		{
			int i1 = 0;
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.800000)
			{
				i1 = 0;
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.600000)
			{
				i1 = 1;
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.400000)
			{
				i1 = 2;
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.200000)
			{
				i1 = 3;
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.010000)
			{
				i1 = 4;
			}
			if(damage / _thisActor.getMaxHp() >= 0.010000)
			{
				i1 += 4;
			}
			else if(damage / _thisActor.getMaxHp() >= 0.006000)
			{
				i1 += 3;
			}
			else if(damage / _thisActor.getMaxHp() >= 0.003000)
			{
				i1 += 2;
			}
			else if(damage / _thisActor.getMaxHp() >= 0.001000)
			{
				i1 += 1;
			}
			broadcastScriptEvent(78010058, attacker.getStoredId(), (long) i1, (300 + Rnd.get(100)));
		}
		if(skill == Skill_Branding)
		{
			if(attacker.isPlayer())
			{
				Functions.showSystemMessageFStr(attacker, 1800293, String.valueOf((int) (_thisActor.getCurrentHp() * 0.200000)));
				_thisActor.setCurrentHp(_thisActor.getCurrentHp() - _thisActor.getCurrentHp() * 0.200000);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(!type.equals("boss_vein") && !type.equals("wall"))
		{
			if(eventId == 989812)
			{
				_thisActor.i_ai0 = 1;
			}
			else if(eventId == 998915)
			{
				_thisActor.i_ai0++;
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_VEIN_SIGNAL)
		{
			broadcastScriptEvent(78010058, 0L, _thisActor.getStoredId(), 500);
			addTimer(TM_VEIN_SIGNAL, TIME_VEIN_SIGNAL * 1000);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(!type.equals("boss_vein"))
		{
			int i0 = Rnd.get(100);
			if(type.equals("wall"))
			{
				if(i0 <= reward_rate_wall)
				{
					_thisActor.dropItem(killer, reward_siege, 1);
				}
			}

			broadcastScriptEvent(78010058, 1L, (long) _thisActor.i_ai0, 500);
			if(i0 <= reward_rate)
			{
				_thisActor.dropItem(killer, reward_siege, 1);
			}
		}
	}
}