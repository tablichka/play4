package services.villagemasters;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2VillageMasterInstance;

public class Clan extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Villagemasters [Clan Operations]");
	}

	public void CheckCreateClan()
	{
		if(npc == null || self == null)
			return;
		L2Player pl = (L2Player) self;
		String htmltext = "clan-02.htm";
		// Player less 10 levels, and can not create clan
		if(pl.getLevel() <= 9)
			htmltext = "clan-06.htm";
		// Player already is a clan by leader and can not newly create clan
		else if(pl.isClanLeader())
			htmltext = "clan-07.htm";
		// Player already consists in clan and can not create clan
		else if(pl.getClanId() != 0)
			htmltext = "clan-09.htm";
		((L2VillageMasterInstance) npc).showChatWindow(pl, "data/html/villagemaster/" + htmltext);
	}

	public void CheckDissolveClan()
	{
		if(npc == null || self == null)
			return;
		L2Player pl = (L2Player) self;
		String htmltext = "clan-01.htm";
		if(pl.isClanLeader())
			htmltext = "clan-04.htm";
		else
		// Player already consists in clan and can not create clan
		if(pl.getClanId() != 0)
			htmltext = "9000-08.htm";
		// Player not in clan and can not dismiss clan
		else
			htmltext = "9000-11.htm";
		((L2VillageMasterInstance) npc).showChatWindow(pl, "data/html/villagemaster/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}