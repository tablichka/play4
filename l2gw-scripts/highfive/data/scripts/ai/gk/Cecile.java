package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:53
 */
public class Cecile extends Teleporter
{
	public Cecile(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010016, 84856, 15912, -4270, 0, 0}, {1010015, 85336, 16137, -3640, 0, 0}, {1010018, 85391, 16228, -2270, 0, 0}, {1010019, 85391, 16228, -1750, 0, 0}};
	}
}
