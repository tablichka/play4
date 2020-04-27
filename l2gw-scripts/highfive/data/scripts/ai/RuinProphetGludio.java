package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 01.09.11 20:58
 */
public class RuinProphetGludio extends DefaultAI
{
	public String SuperPointName = "";

	public RuinProphetGludio(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		if(!SuperPointName.isEmpty())
			addMoveSuperPointDesire(SuperPointName, 1, 2000);

		addTimer(1111, 5000);
	}

	@Override
	protected void onEvtNodeArrived(SuperpointNode node)
	{
		switch(node.getNodeId())
		{
			case 1:
				Functions.npcSay(_thisActor, Say2C.ALL, 1010221);
				break;
			case 3:
				Functions.npcSay(_thisActor, Say2C.ALL, 1010222);
				break;
			case 6:
				Functions.npcSay(_thisActor, Say2C.ALL, 1010223);
				break;
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1111)
		{
			if(!_thisActor.isMoving)
				addMoveSuperPointDesire(SuperPointName, 1, 2000);

			addTimer(1111, 5000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{}
}