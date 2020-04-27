package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 18.09.11 0:56
 */
public class FreyaDeaconKeeperMaker extends DefaultMaker
{
	public FreyaDeaconKeeperMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 10025 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(10026, 0, 0);
		}
		if( eventId == 10005 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(10005, 0, 0);
		}
		if( eventId == 11037 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(11037, 0, 0);
		}
	}
}
