package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 27.09.11 20:43
 */
public class MakerIcequeenElemental extends InzoneMaker
{
	public MakerIcequeenElemental(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onScriptEvent(eventId, arg1, arg2);
		if( eventId == 23140012 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if( def0 != null )
			{
				def0.sendScriptEvent(23140012, arg1, arg2);
			}
		}
	}
}
