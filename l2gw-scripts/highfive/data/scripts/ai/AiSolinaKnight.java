package ai;

import ai.base.WarriorPhysicalspecialBuff;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.10.11 16:27
 */
public class AiSolinaKnight extends WarriorPhysicalspecialBuff
{
	public int TIMER = 555;

	public AiSolinaKnight(L2Character actor)
	{
		super(actor);
		Buff = SkillTable.getInstance().getInfo(413728769);
		PhysicalSpecial = SkillTable.getInstance().getInfo(413663233);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai3 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(Rnd.get(100) < 20 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 50 && _thisActor.i_ai4 == 0)
		{
			_thisActor.i_ai4 = 1;
			_thisActor.createOnePrivate(18909, "AiSolinaWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1000, attacker.getStoredId(), 0);
			Functions.npcSay(_thisActor, Say2C.ALL, 60013);
			if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
			{
				addUseSkillDesire(_thisActor, Buff, 1, 1, 1000000);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140014 && Rnd.get(100) < 10 && _thisActor.i_ai3 == 0)
		{
			switch(Rnd.get(3))
			{
				case 0:
					Functions.npcSay(_thisActor, Say2C.ALL, 60014);
					break;
				case 1:
					Functions.npcSay(_thisActor, Say2C.ALL, 60015);
					break;
				case 2:
					Functions.npcSay(_thisActor, Say2C.ALL, 60016);
					break;
			}
			_thisActor.i_ai3 = 1;
			addTimer(TIMER, 10000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER)
		{
			_thisActor.i_ai3 = 0;
		}
	}
}