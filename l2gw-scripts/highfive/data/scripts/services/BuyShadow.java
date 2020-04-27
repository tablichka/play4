package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author Bonux
 */

public class BuyShadow extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Buy Shadow");
	}

	public void buyShadow() // Buy from Trader with adena
	{
		L2Player pl = (L2Player) self;

		String htmltext;
		int Level = pl.getLevel();
		Race r = pl.getRace();

		if(Level < 40)
		{
			htmltext = "lowlevel.htm";
		}
		else if(Level < 46)
		{
			if(r == Race.kamael)
				htmltext = "c-kamael.htm";
			else
				htmltext = "c.htm";
		}
		else if(Level < 52)
		{
			if(r == Race.kamael)
				htmltext = "c-hi-kamael.htm";
			else
				htmltext = "c-hi.htm";
		}
		else
		{
			if(r == Race.kamael)
				htmltext = "b-kamael.htm";
			else
				htmltext = "b.htm";
		}
		((L2NpcInstance) npc).showChatWindow(pl, "data/html/merchant/shadow/" + htmltext);
	}

	public void getShadow() // Exchange with coupons from Village Masters
	{
		L2Player pl = (L2Player) self;

		String htmltext;
		int Level = pl.getLevel();
		Race r = pl.getRace();
		if(r == Race.kamael)
			htmltext = "master-kamael.htm";
		else
			htmltext = "master.htm";


		((L2NpcInstance) npc).showChatWindow(pl, "data/html/merchant/shadow/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
