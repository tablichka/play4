package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:39
 */
public class Angelina extends Teleporter
{
	public Angelina(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010007, 83551, 147945, -3400, 5200, 3}, {1010049, 111455, 219400, -3546, 7100, 6}};
		PositionNoblessNeedItemField = new int[][]{{1010506, -87328, 142266, -3640, 1, 0}, {1010507, 73579, 142709, -3768, 1, 0}, {1010053, 146440, 46723, -3400, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010506, -87328, 142266, -3640, 1000, 0}, {1010507, 73579, 142709, -3768, 1000, 0}, {1010053, 146440, 46723, -3400, 1000, 0}};
	}
}
