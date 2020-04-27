package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 08.11.11 0:06
 */
public class Merian extends Teleporter
{
	public Merian(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010016, 84872, 15882, -4270, 0, 0}, {1010015, 85343, 16267, -3640, 0, 0}, {1010017, 85343, 16267, -2780, 0, 0}, {1010018, 85343, 16267, -2270, 0, 0}};
	}
}
