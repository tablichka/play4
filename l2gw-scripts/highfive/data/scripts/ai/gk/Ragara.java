package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:31
 */
public class Ragara extends Teleporter
{
	public Ragara(L2Character acotr)
	{
		super(acotr);
		Position = new int[][]{{1010005, -12787, 122779, -3114, 12000, 1}, {1010004, -80684, 149770, -3043, 18000, 0}, {1010023, 146783, 25808, -2000, 26000, 0}, {1010001, -84141, 244623, -3729, 20000, 0}, {1010156, 9709, 15566, -4500, 13000, 0}, {1010155, 46951, 51550, -2976, 16000, 0}, {1010158, 115120, -178224, -917, 32000, 0}, {1010157, -45158, -112583, -236, 17000, 0}, {1010653, -122410, 73205, -2859, 2600, 0}, {1010654, -95540, 52150, -2017, 2200, 0}, {1010655, -85928, 37095, -2040, 3200, 0}, {1010652, -73983, 51956, -3680, 4300, 0}};
		PositionNoblessNeedItemField = new int[][]{{1010506, -87328, 142266, -3640, 1, 0}, {1010507, 73579, 142709, -3768, 1, 0}, {1010053, 146440, 46723, -3400, 1, 0}};
		PositionNoblessNoItemField = new int[][]{{1010506, -87328, 142266, -3640, 1000, 0}, {1010507, 73579, 142709, -3768, 1000, 0}, {1010053, 146440, 46723, -3400, 1000, 0}};
		PositionPoint = new int[][]{{1010653, -122410, 73205, -2859, 0, 0}, {1010654, -95540, 52150, -2017, 0, 0}, {1010655, -85928, 37095, -2040, 0, 0}};
		name = "gatekeeper_ragara";
	}
}