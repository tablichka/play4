package events.TvT;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2PetInstance;
import ru.l2gw.util.Location;

import java.lang.ref.WeakReference;

/**
 * @author rage
 * @date 23.06.11 12:13
 */
public class DelayedTeleport implements Runnable
{
	private final WeakReference<L2Player> _player;
	private final Location _loc;

	public DelayedTeleport(L2Player player, Location loc)
	{
		_player = new WeakReference<L2Player>(player);
		_loc = loc;
	}

	public void run()
	{
		L2Player player = _player.get();
		if(player != null && player.isDead())
		{
			player.teleToLocation(_loc);
			player.doRevive();
			if(player.getPet() instanceof L2PetInstance)
				player.getPet().doRevive();
		}
	}
}
