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

public class AskForAdvice extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Ask For Advice");
	}

	public void askHuman()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.human)
		{
			htmltext = "30598-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "30598-noadvice.htm";
		}
		else
			htmltext = "30598-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void askElven()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.elf)
		{
			htmltext = "30599-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "30599-noadvice.htm";
		}
		else
			htmltext = "30599-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void askDark()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.darkelf)
		{
			htmltext = "30600-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "30600-noadvice.htm";

		}
		else
			htmltext = "30600-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void askOrc()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.orc)
		{
			htmltext = "30602-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "30602-noadvice.htm";

		}
		else
			htmltext = "30602-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void askDwarf()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.dwarf)
		{
			htmltext = "30601-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "30601-noadvice.htm";

		}
		else
			htmltext = "30601-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void askKamael()
	{
		L2Player pl = (L2Player) self;

		String htmltext = "Ask For Advice.";

		if(pl.getRace() == Race.kamael)
		{
			htmltext = "32135-7.htm";
			if(pl.getLevel() >= 20)
				htmltext = "32135-noadvice.htm";

		}
		else
			htmltext = "32135-8.htm";

		((L2NpcInstance) npc).showChatWindow(pl, "data/html/default/" + htmltext);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
