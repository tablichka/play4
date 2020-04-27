package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 08.11.11 0:03
 */
public class Mariell extends Teleporter
{
	public Mariell(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010016, 84814, 15926, -4270, 0, 0}, {1010015, 85391, 16228, -3640, 0, 0}, {1010017, 85391, 16228, -2780, 0, 0}, {1010019, 85343, 16267, -1750, 0, 0}};
	}
}
