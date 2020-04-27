package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 04.09.2010 22:58:39
 */
public class DiliosNpc extends L2CharacterAI
{
	private GArray<L2NpcInstance> _soldiers;

	public DiliosNpc(L2Character actor)
	{
		super(actor);
		addTimer(1, 60000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == 1)
		{
			Functions.npcSayInRange((L2NpcInstance) _actor, Say2C.ALL, 1800704, 500);
			if(_soldiers == null || _soldiers.isEmpty())
			{
				_soldiers = new GArray<L2NpcInstance>(18);
				for(L2NpcInstance npc : _actor.getKnownNpc(1000))
					if(npc.getNpcId() == 32619)
						_soldiers.add(npc);
			}

			for(L2NpcInstance npc : _soldiers)
				addTimer(2, npc, Rnd.get(1500, 2500));
			addTimer(1, 60000);
		}
		else if(timerId == 2)
		{
			L2NpcInstance npc = (L2NpcInstance) arg1;
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 4));
			addTimer(3, npc, Rnd.get(2000, 3000));
		}
		else if(timerId == 3)
		{
			L2NpcInstance npc = (L2NpcInstance) arg1;
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 4));
			addTimer(4, npc, Rnd.get(2000, 3000));
		}
		else if(timerId == 4)
		{
			L2NpcInstance npc = (L2NpcInstance) arg1;
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 4));
		}
	}
}
