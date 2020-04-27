package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author Bonux
 */

public class TravelToken extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Travel Token");
	}

	public void TI()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "30598-11.htm";
		}
		else
			htmltext = "30598-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void EV()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "30599-11.htm";
		}
		else
			htmltext = "30599-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void DV()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "30600-11.htm";
		}
		else
			htmltext = "30600-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void OV()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "30602-11.htm";
		}
		else
			htmltext = "30602-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void DwV()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "30601-11.htm";
		}
		else
			htmltext = "30601-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void KV()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Travel Token.";
		int Level = pl.getLevel();

		if(Level <= 20)
		{
			htmltext = "32135-11.htm";
		}
		else
			htmltext = "32135-12.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
