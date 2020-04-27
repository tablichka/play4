package events.TvT;

import ru.l2gw.gameserver.model.L2Player;

import java.util.Comparator;

/**
 * @author rage
 * @date 22.06.11 14:10
 */
public class PlayersComparator implements Comparator<L2Player>
{
	public int compare(L2Player p1, L2Player p2)
	{
		if(p1 == null || p2 == null)
			return 0;

		return p2.getPvpKills() - p1.getPvpKills();
	}
}
