package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 13.12.11 10:09
 */
public class GatheredNpcForASeed extends GatheredNpc
{
	public int FieldCycleID = 0;

	public GatheredNpcForASeed(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == COLLECTED_EVENT)
		{
			int i0 = Rnd.get(100);
			if(i0 > 50)
			{
				if(FieldCycleID == 4)
				{
					int i1 = Rnd.get(4);
					switch(i1)
					{
						case 0:
							_thisActor.createOnePrivate(22747, "Brakian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 1:
							_thisActor.createOnePrivate(22748, "Groykhan", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 2:
							_thisActor.createOnePrivate(22749, "Traikhan", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 3:
							_thisActor.createOnePrivate(22746, "Bgurent", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
					}
				}
				else if(FieldCycleID == 5)
				{
					int i1 = Rnd.get(3);
					switch(i1)
					{
						case 0:
							_thisActor.createOnePrivate(22754, "Turtlian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 1:
							_thisActor.createOnePrivate(22756, "Tardion", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 2:
							_thisActor.createOnePrivate(22755, "Krakian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
					}
				}
				else if(FieldCycleID == 6)
				{
					int i1 = Rnd.get(3);
					switch(i1)
					{
						case 0:
							_thisActor.createOnePrivate(22760, "Karnibi", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 1:
							_thisActor.createOnePrivate(22761, "Kiriona", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
						case 2:
							_thisActor.createOnePrivate(22762, "Caiona", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
							break;
					}
				}
			}
			super.onEvtScriptEvent(eventId, arg1, arg2);
		}
	}
}