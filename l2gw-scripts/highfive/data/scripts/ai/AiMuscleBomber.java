package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 03.09.11 14:49
 */
public class AiMuscleBomber extends DetectPartyWarrior
{
	public int summonTimer = 23210001;
	public int limitTimer = 23210002;
	public L2Skill speedUp_lv1 = SkillTable.getInstance().getInfo(448397313);
	public L2Skill speedUp_lv2 = SkillTable.getInstance().getInfo(448462849);
	public L2Skill speedUp_lv3 = SkillTable.getInstance().getInfo(448397314);
	public int isChasePC = 2500;

	public AiMuscleBomber(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai5 = 0;
		_thisActor.c_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.isInRange(_thisActor.getSpawnedLoc(), isChasePC))
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc());

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.8 && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addUseSkillDesire(_thisActor, speedUp_lv1, 1, 0, 99999999999000000L);
		}
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5 && _thisActor.i_ai3 == 0)
		{
			_thisActor.i_ai3 = 1;
			addUseSkillDesire(_thisActor, speedUp_lv2, 1, 0, 99999999999000000L);
			_thisActor.c_ai0 = attacker.getStoredId();
			addTimer(summonTimer, 60000);
			addTimer(limitTimer, 5 * 60000);
		}
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.1 && _thisActor.i_ai4 == 0)
		{
			_thisActor.i_ai4 = 1;
			addUseSkillDesire(_thisActor, speedUp_lv3, 1, 0, 99999999999000000L);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == limitTimer)
			_thisActor.i_ai5 = 1;
		else if(timerId == summonTimer && _thisActor.i_ai5 == 0)
		{
			L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(cha != null)
			{
				Location pos = Location.coordsRandomize(_thisActor, 100);
				_thisActor.createOnePrivate(22823, "AiDrakosAssasin", 0, 0, pos.getX(), pos.getX(), pos.getZ(), 0, cha.getStoredId(), 0, 0);
				pos = Location.coordsRandomize(_thisActor, 100);
				_thisActor.createOnePrivate(22823, "AiDrakosAssasin", 0, 0, pos.getX(), pos.getX(), pos.getZ(), 0, cha.getStoredId(), 0, 0);
			}
			addTimer(summonTimer, 60000);
		}
	}
}
