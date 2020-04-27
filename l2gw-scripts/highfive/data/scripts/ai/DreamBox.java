package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 11.01.11 14:49
 */
public class DreamBox extends DefaultAI
{
	private static final int rate = 25;
	private static final L2Skill s_display_jackpot_firework = SkillTable.getInstance().getInfo(5758, 1);
	private static final L2Skill s_npc_party60_m_self_range_dd_kamigaze4 = SkillTable.getInstance().getInfo(5376, 4);

	public DreamBox(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker.getPlayer() == null)
			return;

		if((int)_thisActor.getCurrentHp() <= damage && _thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			if(Rnd.chance(rate))
			{
				if(Rnd.chance(33))
					_thisActor.dropItem(attacker.getPlayer(), 4042, Rnd.get(3, (int) (3 * Config.RATE_DROP_ITEMS)));
				if(Rnd.chance(50))
					_thisActor.dropItem(attacker.getPlayer(), 4044, Rnd.get(4, (int) (4 * Config.RATE_DROP_ITEMS)));
				if(Rnd.chance(50))
					_thisActor.dropItem(attacker.getPlayer(), 4043, Rnd.get(4, (int) (4 * Config.RATE_DROP_ITEMS)));
				if(Rnd.chance(16))
					_thisActor.dropItem(attacker.getPlayer(), 9628, Rnd.get(2, (int) (2 * Config.RATE_DROP_ITEMS)));

				broadcastScriptEvent(9274149, null, null, 2000);
				_thisActor.altUseSkill(s_display_jackpot_firework, _thisActor);
			}
			else
				broadcastScriptEvent(9274150, null, null, 2000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 9274149)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.altUseSkill(s_display_jackpot_firework, _thisActor);
			addTimer(1, 2000);
		}
		else if(eventId == 9274150)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.altUseSkill(s_npc_party60_m_self_range_dd_kamigaze4, _thisActor);
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1)
			_thisActor.doDie(null);
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
