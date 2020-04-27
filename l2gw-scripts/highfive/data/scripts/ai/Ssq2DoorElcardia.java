package ai;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 29.09.11 18:50
 */
public class Ssq2DoorElcardia extends Citizen
{

	public Ssq2DoorElcardia(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 10292 && reply == 1)
		{
			if((talker.isQuestStarted(10292) && talker.getQuestState(10292).getMemoState() > 1 && talker.getQuestState(10292).getMemoState() < 9) ||
					(talker.isQuestComplete(10292) && !talker.isQuestStarted(10293)) ||
					(talker.isQuestStarted(10293) && talker.getQuestState(10293).getMemoState() > 8) ||
					(talker.isQuestStarted(10294) && talker.getQuestState(10294).getMemoState() < 9) ||
					(talker.isQuestStarted(10296) && talker.getQuestState(10296).getMemoState() > 2 && talker.getQuestState(10296).getMemoState() < 4))
			{
				InstanceManager.enterInstance(158, talker, _thisActor, 0);
			}
			else
			{
				_thisActor.showPage(talker, "ssq2_door_elcardia003.htm");
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}
}
