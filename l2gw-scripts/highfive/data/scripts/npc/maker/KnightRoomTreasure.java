package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 14.10.11 21:53
 */
public class KnightRoomTreasure extends DefaultMaker
{
	public KnightRoomTreasure(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}
}
