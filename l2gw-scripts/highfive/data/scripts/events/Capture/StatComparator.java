package events.Capture;

import ru.l2gw.gameserver.templates.StatsSet;

import java.util.Comparator;

/**
 * @author: rage
 * @date: 26.06.12 12:24
 */
public class StatComparator implements Comparator<StatsSet>
{
	private final String key;

	public StatComparator(String key)
	{
		this.key = key;
	}

	public int compare(StatsSet s1, StatsSet s2)
	{
		if("kd".equals(key))
		{
			double d2 = s2.getInteger("kill_count", 0) > 0 && s2.getInteger("killed_count", 0) > 0 ? s2.getInteger("kill_count", 0) / s2.getInteger("killed_count", 1) : 0;
			double d1 = s1.getInteger("kill_count", 0) > 0 && s1.getInteger("killed_count", 0) > 0 ? s1.getInteger("kill_count", 0) / s1.getInteger("killed_count", 1) : 0;
			return (int) (d2 - d1);
		}
		if("wl".equals(key))
		{
			double d2 = s2.getInteger("wins_count", 0) > 0 && s2.getInteger("loos_count", 0) > 0 ? s2.getInteger("wins_count", 0) / s2.getInteger("loos_count", 1) : 0;
			double d1 = s1.getInteger("wins_count", 0) > 0 && s1.getInteger("loos_count", 0) > 0 ? s1.getInteger("wins_count", 0) / s1.getInteger("loos_count", 1) : 0;
			return (int) (d2 - d1);
		}

		return s2.getInteger(key, 0) - s1.getInteger(key, 0);
	}
}