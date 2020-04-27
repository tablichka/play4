package ai;

import ru.l2gw.gameserver.ai.Ranger;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;


public class FestivalRanger extends Ranger
{
	public FestivalRanger(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(!_def_think)
		{
			boolean targetsFound = checkTargetsAround();
			if(targetsFound)
				return createNewTask();
		}
		if(_def_think)
		{
			doTask();
			return true;
		}

		return super.thinkActive();
	}

	private boolean checkTargetsAround()
	{
		boolean aggro = false;
		GArray<L2Player> players = _thisActor.getAroundPlayers(_thisActor.getAggroRange());
		for(L2Player player : players)
			if(GeoEngine.canSeeTarget(_thisActor, player))
			{
				_thisActor.addDamageHate(player, 0, Rnd.get(10));
				aggro = true;
			}

		if(aggro)
			checkAggression(players.get(Rnd.get(players.size())));
		return aggro;
	}
}