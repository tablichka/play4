package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 08.11.11 0:15
 */
public class Stanislava extends Teleporter
{
	public Stanislava(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010494, 38303, -48040, 896, 150, 0}};
		PositionNoblessNeedItemField = new int[][]{{1010494, 38316, -48216, -1152, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010494, 38316, -48216, -1152, 1000, 0}};
	}
}
