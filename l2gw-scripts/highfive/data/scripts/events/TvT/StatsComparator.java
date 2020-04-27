package events.TvT;

import ru.l2gw.gameserver.templates.StatsSet;

import java.util.Comparator;

/**
 * @author rage
 * @date 22.06.11 15:30
 */
public class StatsComparator implements Comparator<StatsSet>
{
	public int compare(StatsSet s1, StatsSet s2)
	{
		if(s1 == null || s2 == null)
			return 0;

		return s2.getInteger("kills", 0) - s1.getInteger("kills", 0);
	}
}
