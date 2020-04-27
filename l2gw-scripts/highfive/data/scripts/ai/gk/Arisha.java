package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 08.11.11 0:12
 */
public class Arisha extends Teleporter
{
	public Arisha(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010493, 38316, -48216, -1152, 150, 0}};
		PositionNoblessNeedItemField = new int[][]{{1010493, 38303, -48040, 896, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010493, 38303, -48040, 896, 1000, 0}};
	}
}
