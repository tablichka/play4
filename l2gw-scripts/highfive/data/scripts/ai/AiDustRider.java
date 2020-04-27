package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 14:40
 */
public class AiDustRider extends DetectPartyWarrior
{
	public L2Skill atkup_buff = SkillTable.getInstance().getInfo(453115907);
	public int isChasePC = 2500;

	public AiDustRider(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai2 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!_thisActor.isInRange(_thisActor.getSpawnedLoc(), isChasePC))
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc());
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.3 && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addUseSkillDesire(_thisActor, atkup_buff, 1, 0, 99999999999000000L);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
