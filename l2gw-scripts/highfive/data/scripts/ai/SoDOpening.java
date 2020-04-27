package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;

/**
 * @author rage
 * @date 20.10.2010 14:37:39
 */
public class SoDOpening extends DefaultAI
{
	private boolean _showMovie = true;
	private static final L2Zone _zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 4007);

	public SoDOpening(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
		_thisActor.setIsInvul(true);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_showMovie && _thisActor.getAroundPlayers(150).size() > 0)
		{
			_showMovie = false;
			for(L2Player player : _zone.getPlayers())
				if(player.getReflection() == _thisActor.getReflection())
				{
					if(Config.DEBUG_INSTANCES)
						Instance._log.info(player + " start scene play: " + ExStartScenePlayer.SCENE_TIAT_OPENING);
					player.showQuestMovie(ExStartScenePlayer.SCENE_TIAT_OPENING);
				}
		}
		return true;
	}
}
