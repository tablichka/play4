package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.commons.math.Rnd;

public class EdwinFollower extends DefaultAI
{
	private static int EDWIN_ID = 32072;
	private static int DRIFT_DISTANCE = 350;
	private L2NpcInstance _edwin;
	private long wait_timeout = 15000;

	public EdwinFollower(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean randomAnimation()
	{
		return false;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_edwin == null)
		{
			// Ищем преследуемого не чаще, чем раз в 15 секунд, если по каким-то причинам его нету
			if(System.currentTimeMillis() > wait_timeout)
				for(L2NpcInstance npc : L2World.getAroundNpc(_thisActor))
					if(npc.getNpcId() == EDWIN_ID)
					{
						_edwin = npc;
						return true;
					}
		}
		else if(!_thisActor.isMoving)
		{
			int x = _edwin.getX() + Rnd.get(2 * DRIFT_DISTANCE) - DRIFT_DISTANCE;
			int y = _edwin.getY() + Rnd.get(2 * DRIFT_DISTANCE) - DRIFT_DISTANCE;
			int z = _edwin.getZ();

			_thisActor.setRunning(); // всегда бегают
			_thisActor.moveToLocation(x, y, z, 0, true);
			return true;
		}
		return false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}