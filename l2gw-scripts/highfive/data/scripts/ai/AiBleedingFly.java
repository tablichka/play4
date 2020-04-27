package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.09.11 2:05
 */
public class AiBleedingFly extends DetectPartyWizard
{
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(447741953);
	public L2Skill atkup_buff = SkillTable.getInstance().getInfo(453181443);
	public int isChasePC = 2500;

	public AiBleedingFly(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai5 = 5;
		_thisActor.i_ai6 = 10;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 20100503)
		{
			if(_thisActor.i_ai5 > 0)
			{
				_thisActor.i_ai5 = (_thisActor.i_ai5 - 1);
				addUseSkillDesire(_thisActor, summonSkill, 1, 0, 9999999999900000L);
				_thisActor.createOnePrivate(25734, "AiBigBloodyLeech", 0, 0, (_thisActor.getX()) + Rnd.get(150), (_thisActor.getY()) + Rnd.get(150), _thisActor.getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(25734, "AiBigBloodyLeech", 0, 0, (_thisActor.getX()) + Rnd.get(150), (_thisActor.getY()) + Rnd.get(150), _thisActor.getZ(), 0, 0, 0, 0);
				if(_thisActor.i_ai2 == 1)
				{
					addTimer(20100503, 140000);
				}
			}
		}
		if(timerId == 20100504)
		{
			if(_thisActor.i_ai6 > 0)
			{
				_thisActor.i_ai6 = (_thisActor.i_ai6 - 1);
				addUseSkillDesire(_thisActor, summonSkill, 1, 0, 9999999999900000L);
				addUseSkillDesire(_thisActor, atkup_buff, 1, 0, 9999999999900000L);
				_thisActor.createOnePrivate(25734, "AiBigBloodyLeech", 0, 0, (_thisActor.getX()) + Rnd.get(150), (_thisActor.getY()) + Rnd.get(150), _thisActor.getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(25734, "AiBigBloodyLeech", 0, 0, (_thisActor.getX()) + Rnd.get(150), (_thisActor.getY()) + Rnd.get(150), _thisActor.getZ(), 0, 0, 0, 0);
				if(_thisActor.i_ai3 == 1)
				{
					addTimer(20100504, 80000);
				}
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		int i0 = _thisActor.getSpawnedLoc().getX();
		int i1 = _thisActor.getSpawnedLoc().getY();
		int i2 = _thisActor.getX();
		int i3 = _thisActor.getY();
		int i4 = i0 - i2;
		int i5 = i1 - i3;
		if(i4 * i4 + i5 * i5 > isChasePC * isChasePC)
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
		}
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.500000) && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addTimer(20100503, 1000);
		}
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.250000) && _thisActor.i_ai3 == 0)
		{
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai3 = 1;
			addTimer(20100504, 1000);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
