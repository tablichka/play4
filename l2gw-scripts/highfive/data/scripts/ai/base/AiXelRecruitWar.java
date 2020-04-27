package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 06.09.11 15:17
 */
public class AiXelRecruitWar extends WarriorUseSkill
{
	public int trainer_id = 0;
	public int direction = 0;

	public AiXelRecruitWar(L2Character actor)
	{
		super(actor);
		MoveArounding = 0;
	}

	@Override
	protected boolean thinkActive()
	{
		if(!_thisActor.isDead())
			if(_thisActor.getX() == _thisActor.getSpawnedLoc().getX() && _thisActor.getSpawnedLoc().getY() == _thisActor.getY())
				_thisActor.changeHeading(direction);
			else if(_thisActor.i_ai6 == 0 && !_thisActor.isInCombat())
			{
				clearTasks();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			}

		return super.thinkActive();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if( _thisActor.i_ai6 == 1 )
		{
			return;
		}
		broadcastScriptEvent(10016 + trainer_id, getStoredIdFromCreature(attacker), null, 1000);
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if( _thisActor.i_ai6 == 1 )
		{
			return;
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}
