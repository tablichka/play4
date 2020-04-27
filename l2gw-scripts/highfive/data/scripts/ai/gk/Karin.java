package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:50
 */
public class Karin extends Teleporter
{
	public Karin(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010015, 85289, 16225, -3640, 0, 0}, {1010017, 85336, 16137, -2780, 0, 0}, {1010018, 85336, 16137, -2270, 0, 0}, {1010019, 85336, 16137, -1750, 0, 0}};
	}
}
