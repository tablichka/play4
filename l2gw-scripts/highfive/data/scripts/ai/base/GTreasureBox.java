package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.12.11 20:47
 */
public class GTreasureBox extends DefaultNpc
{
	public GTreasureBox(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		addTimer(5002, 14400000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.getLevel() < 78)
		{
			_thisActor.i_ai5 = 6;
		}
		else if(attacker.getLevel() >= 78)
		{
			_thisActor.i_ai5 = 5;
		}

		_thisActor.i_ai2 = _thisActor.getLevel();

		int i3 = 0;
		if(_thisActor.i_ai2 >= 21 && _thisActor.i_ai2 <= 85)
		{
			i3 = 22271;
		}

		if(_thisActor.i_ai1 == 0)
		{
			if(skill != null && (skill.getId() == i3 || skill.getId() == 27))
			{
				_thisActor.i_ai1 = 1;
				addTimer(5001, 5000);
				if(_thisActor.getLevel() - _thisActor.i_ai5 > attacker.getLevel())
				{
					int i0 = _thisActor.getLevel() / 10;
					i0 += 271515649;
					if(SkillTable.mpConsume(i0) < _thisActor.getCurrentMp() && SkillTable.hpConsume(i0) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(i0))
					{
						addUseSkillDesire(_thisActor, i0, 0, 1, 1000000);
					}
				}
				else
				{
					int i2 = Rnd.get(100);
					if(i2 < 10)
					{
						_thisActor.doDie(attacker);
					}
					else
					{
						int i0 = _thisActor.getLevel() / 10;
						i0 += 271515649;
						if(SkillTable.mpConsume(i0) < _thisActor.getCurrentMp() && SkillTable.hpConsume(i0) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(i0))
						{
							addUseSkillDesire(_thisActor, i0, 0, 1, 1000000);
						}
					}
				}
			}
			else
			{
				int i2 = Rnd.get(100);
				if(i2 < 30)
				{
					attacker.sendPacket(new SystemMessage(6050));
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 5001)
		{
			_thisActor.onDecay();
		}
		if(timerId == 5002)
		{
			_thisActor.onDecay();
		}
	}
}