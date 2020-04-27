package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 03.09.11 1:04
 */
public class AiEventDcMonster extends WarriorUseSkill
{
	public int delayTime = 1;
	public int SuperPointMethod = 0;
	public int SuperPointDesire = 200;
	public String SuperPointName = "";
	public int isPartyLeader = 0;
	public int eventNode = 0;
	public int spawnTimer = 20100504;
	public int privateNPC = 18970;
	public String privateAI = "AiMagmaDrakeHatchlingBaby";

	public AiEventDcMonster(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.decayMe();
		addTimer(spawnTimer, delayTime * 100);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == spawnTimer )
		{
			_thisActor.spawnMe();
			_thisActor.setRunning();
			addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			if(isPartyLeader == 1 )
			{
				_thisActor.createOnePrivate(privateNPC, privateAI, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(privateNPC, privateAI, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
			}
		}
	}

	@Override
	protected void onEvtNodeArrived(SuperpointNode node)
	{
		if(eventNode == node.getNodeId())
		{
			if(isPartyLeader == 1)
				broadcastScriptEvent(10023, 0, 0, 2000);

			_thisActor.onDecay();
		}
	}
}
