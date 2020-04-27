package ai.gk;

import ai.base.TeleporterMultiList;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 07.11.11 23:56
 */
public class Verona extends TeleporterMultiList
{
	public Verona(L2Character actor)
	{
		super(actor);
		Position1 = new int[][]{{1010013, 82971, 53207, -1470, 3700, 4}, {1010020, 117088, 76931, -2670, 6800, 0}, {1010023, 146783, 25808, -2000, 6200, 5}};
		Position2 = new int[][]{{1010016, 84852, 15863, -4270, 0, 0}, {1010017, 85289, 16225, -2780, 0, 0}, {1010018, 85289, 16225, -2270, 0, 0}, {1010019, 85289, 16225, -1750, 0, 0}};
	}
}