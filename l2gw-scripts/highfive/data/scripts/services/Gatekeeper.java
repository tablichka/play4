package services;

import java.util.Calendar;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class Gatekeeper extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Gatekeeper");
	}

	public void tele()
	{
		L2Player pl = (L2Player) self;
		L2NpcInstance L2Npc = (L2NpcInstance) npc;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

		if(pl != null && L2Npc != null)
		{
			String htmltext;
			if(pl.getKarma() > 0)
				htmltext = L2Npc.getNpcId() + "-pk.htm";
			else if(pl.getLevel() >= 41)
			{

				if(day != 1 && day != 7 && (hour <= 12 || hour >= 22))
					htmltext = L2Npc.getNpcId() + "-night.htm";
				else
					htmltext = L2Npc.getNpcId() + "-1.htm";
			}
			else
				htmltext = L2Npc.getNpcId() + "-1-1.htm";

			L2Npc.showChatWindow(pl, "data/html/teleporter/" + htmltext);
		}
	}

	public void stronghold32163()
	{
		L2Player pl = (L2Player) self;

		String htmltext;
		int Level = pl.getLevel();

		if(Level >= 21)
			htmltext = "32163-4.htm";
		else
			htmltext = "32163-5.htm";

		if((L2NpcInstance) npc != null)
			((L2NpcInstance) npc).showChatWindow(pl, "data/html/teleporter/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
