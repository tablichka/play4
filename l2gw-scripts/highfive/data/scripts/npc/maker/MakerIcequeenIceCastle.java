package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 27.09.11 21:09
 */
public class MakerIcequeenIceCastle extends InzoneMaker
{

	public MakerIcequeenIceCastle(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onScriptEvent(eventId, arg1, arg2);
		if(eventId == 23140015)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(23140015, 0, 0);
			}
		}
		else if(eventId == 23140040)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(23140040, 0, 0);
			}
		}
	}
}
