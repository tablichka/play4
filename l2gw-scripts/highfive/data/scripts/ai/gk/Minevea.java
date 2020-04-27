package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 08.11.11 0:09
 */
public class Minevea extends Teleporter
{
	public Minevea(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010007, 83551, 147945, -3400, 4400, 3}, {1010013, 82971, 53207, -1470, 6100, 4}, {1010020, 117088, 76931, -2670, 3400, 0}};
		PositionNoblessNeedItemField = new int[][]{{1010506, -87328, 142266, -3640, 1, 0}, {1010507, 73579, 142709, -3768, 1, 0}, {1010119, 113553, 134813, -3540, 1, 0}, {1010053, 146440, 46723, -3400, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010506, -87328, 142266, -3640, 1000, 0}, {1010507, 73579, 142709, -3768, 1000, 0}, {1010119, 113553, 134813, -3540, 1000, 0}, {1010053, 146440, 46723, -3400, 1000, 0}};
	}
}
