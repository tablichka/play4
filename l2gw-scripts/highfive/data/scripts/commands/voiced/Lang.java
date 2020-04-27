package commands.voiced;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Files;

public class Lang extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private String[] _commandList = new String[] { "lang" };

	public boolean useVoicedCommand(String command, L2Player activeChar, String args)
	{
		if(command.equals("lang"))
		{
			if(args != null)
				if(args.equalsIgnoreCase("en"))
					activeChar.setVar("lang@", "en");
				else if(args.equalsIgnoreCase("ru"))
					activeChar.setVar("lang@", "ru");
		}

		String dialog = Files.read("data/scripts/commands/voiced/lang.htm", activeChar);

		if(activeChar.getVar("lang@").equalsIgnoreCase("en"))
			dialog = dialog.replaceFirst("%lang%", "EN");
		else
			dialog = dialog.replaceFirst("%lang%", "RU");

		show(dialog, activeChar);

		return true;
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
