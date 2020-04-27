package ai.gk;

import ai.base.Teleporter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:44
 */
public class Transmitter extends Teleporter
{
	public Transmitter(L2Character actor)
	{
		super(actor);
		Position = new int[][]{{1010709, -212843, 209695, 4280, 150000, 0 },{1010710, -248535, 250273, 4336, 150000, 0 },{1010711, -175520, 154505, 2712, 150000, 0 }};
	}
}
