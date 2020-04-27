package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:28
 */
public class Mint extends Teleporter
{
	public Mint(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010005, -12787, 122779, -3114, 9200, 1}, {1010004, -80684, 149770, -3043, 18000, 0}, {1010158, 115120, -178224, -917, 23000, 0}, {1010001, -84141, 244623, -3729, 23000, 0}, {1010157, -45158, -112583, -236, 18000, 0}, {1010648, -117251, 46771, 380, 16000, 0}, {1010167, 21362, 51122, -3688, 710, 0}, {1010560, 29294, 74968, -3776, 820, 0}, {1010612, -10612, 75881, -3592, 1700, 0}};
		PositionNoblessNeedItemField = new int[][]{{1010506, -87328, 142266, -3640, 1, 0}, {1010507, 73579, 142709, -3768, 1, 0}, {1010613, -18415, 85624, -3680, 1, 0}, {1010053, 146440, 46723, -3400, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010506, -87328, 142266, -3640, 1000, 0}, {1010507, 73579, 142709, -3768, 1000, 0}, {1010613, -18415, 85624, -3680, 1000, 0}, {1010053, 146440, 46723, -3400, 1000, 0}};
		PositionNewbie = new int[][]{{1010156, 9709, 15566, -4500, 1, 0}, {1010158, 115120, -178224, -917, 1, 0}, {1010001, -84141, 244623, -3729, 1, 0}, {1010157, -45158, -112583, -236, 1, 0}};
	}
}