package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;

/**
 * @author rage
 * @date 27.11.2010 18:51:01
 */
public class SSQController extends DefaultAI
{
	public SSQController(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 100)
		{
			int idNumber = (Integer) arg1;
			if(_thisActor.i_ai0 == 0)
				_thisActor.i_ai0 = (Integer) arg2;

			_thisActor.i_ai1 += idNumber;
			if(_thisActor.i_ai1 == 1111)
			{
				Instance inst = _thisActor.getSpawn().getInstance();
				if(inst != null)
					inst.notifyEvent("despawn_iz112_1724_f01", null, null);

				L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_ai0);
				if(player != null && player.getReflection() == _thisActor.getReflection())
				{
					player.showQuestMovie(ExStartScenePlayer.SCENE_SSQ_SEALING_EMPEROR_2ND);
					addTimer(7802, 27000);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 7802)
		{
			L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_ai0);
			if(player != null && player.getReflection() == _thisActor.getReflection())
				player.teleToLocation(-89556, 216119, -7488);
		}
	}
}
