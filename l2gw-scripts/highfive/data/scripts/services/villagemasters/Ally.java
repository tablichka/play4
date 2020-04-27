package services.villagemasters;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2VillageMasterInstance;

public class Ally extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Villagemasters [Alliance Operations]");
	}

	public void CheckCreateAlly()
	{
		if(npc == null || self == null)
			return;
		L2Player pl = (L2Player) self;
		String htmltext = "ally-01.htm";
		if(pl.isClanLeader())
			htmltext = "ally-02.htm";
		((L2VillageMasterInstance) npc).showChatWindow(pl, "data/html/villagemaster/" + htmltext);
	}

	public void CheckDissolveAlly()
	{
		if(npc == null || self == null)
			return;
		L2Player pl = (L2Player) self;
		String htmltext = "ally-01.htm";
		if(pl.isAllyLeader())
			htmltext = "ally-03.htm";
		((L2VillageMasterInstance) npc).showChatWindow(pl, "data/html/villagemaster/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}