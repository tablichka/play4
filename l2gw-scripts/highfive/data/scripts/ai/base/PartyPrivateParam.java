package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 21.09.11 19:27
 */
public class PartyPrivateParam extends Warrior
{
	public int IsSayPrivate = 1;

	public PartyPrivateParam(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(_thisActor.isDead())
		{
			return;
		}
		if(eventId == 10002)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(c0 == _thisActor.getLeader())
				{
					L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.getLeader().l_ai5);
					if(c1 != null)
					{
						if(_thisActor.getMostHated() != null)
						{
							if(_thisActor.getMostHated() == c1)
							{
								return;
							}
						}
						if(IsSayPrivate == 1)
						{
							switch(Rnd.get(4))
							{
								case 0:
									Functions.npcSay(_thisActor, Say2C.ALL, 1000292);
									break;
								case 1:
									Functions.npcSay(_thisActor, Say2C.ALL, 1000400);
									break;
								case 2:
									Functions.npcSay(_thisActor, Say2C.ALL, 1000401);
									break;
								case 3:
									Functions.npcSay(_thisActor, Say2C.ALL, 1000402);
									break;
							}
						}
						removeAllAttackDesire();
						addAttackDesire(c1, 1, 2000);
					}
				}
			}
		}
		else if(eventId == 11039)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}
