package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 13.10.11 20:03
 */
public class LabyrinthA extends WarriorUseSkill
{
	public int is_victim = 1;
	public int silhouette = 20130;
	public String ai_type = "warrior";

	public LabyrinthA(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(8255, 3000);
		if(is_victim == 0)
		{
			int i0 = Rnd.get(9);
			if(i0 == 0)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 1)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() - 85, _thisActor.getSpawnedLoc().getY() - 60, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 2)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY() - 120, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10, 15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 3)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() + 85, _thisActor.getSpawnedLoc().getY() - 60, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 4)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() - 120, _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 5)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() + 120, _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 6)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() - 85, _thisActor.getSpawnedLoc().getY() + 60, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else if(i0 == 7)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY() + 120, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
			else
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX() + 85, _thisActor.getSpawnedLoc().getY() + 60, _thisActor.getSpawnedLoc().getZ());
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() - 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 85), (_thisActor.getSpawnedLoc().getY() - 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() + 120), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), (_thisActor.getSpawnedLoc().getX() - 85), (_thisActor.getSpawnedLoc().getY() + 60), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
				_thisActor.createOnePrivate(silhouette, ai_type, 0, Rnd.get(10,15), _thisActor.getSpawnedLoc().getX(), (_thisActor.getSpawnedLoc().getY() + 120), _thisActor.getSpawnedLoc().getZ(), 0, 0, 0, 0);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(is_victim == 0)
		{
			if(partyPrivate != _thisActor)
			{
				_thisActor.respawnPrivate(partyPrivate, partyPrivate.weight_point, partyPrivate.getMinionData().minionRespawn);
			}
		}
		super.onEvtPartyDead(partyPrivate);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 8255)
		{
			if(_thisActor.getSpawnedLoc().getZ() - _thisActor.getZ() > 15 || _thisActor.getSpawnedLoc().getZ() - _thisActor.getZ() < -500)
			{
				removeAllAttackDesire();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			}
			addTimer(8255, 3000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	public boolean checkBossPosition()
	{
		return false;
	}
}
