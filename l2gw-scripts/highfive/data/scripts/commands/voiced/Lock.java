package commands.voiced;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.LockAccountIP;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Files;

/**
 * @Author: SYS
 * @Date: 10/4/2008
 */
public class Lock extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private String[] _commandList = new String[] { "lock" };

	private static String defaultPage = "data/scripts/commands/voiced/lock.html";

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	private void showDefaultPage(L2Player activeChar)
	{
		String html = Files.read(defaultPage, activeChar);
		html = html.replaceFirst("%IP%", activeChar.getIP());
		show(html, activeChar);
	}

	public static void lock_on()
	{
		L2Player activeChar = (L2Player) self;
		LSConnection.getInstance().sendPacket(new LockAccountIP(activeChar.getAccountName(), activeChar.getIP()));
		activeChar.sendMessage("Account locked.");
	}

	public static void lock_off()
	{
		L2Player activeChar = (L2Player) self;
		LSConnection.getInstance().sendPacket(new LockAccountIP(activeChar.getAccountName(), "*"));
		activeChar.sendMessage("Account unlocked.");
	}

	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if(!Config.SERVICES_LOCK_ACCOUNT_IP)
		{
			activeChar.sendMessage("Service turned off.");
			return true;
		}

		if(command.equals("lock") && (target == null || target.equals("")))
		{
			showDefaultPage(activeChar);
			return true;
		}

		if(target.equalsIgnoreCase("on"))
		{
			LSConnection.getInstance().sendPacket(new LockAccountIP(activeChar.getAccountName(), activeChar.getIP()));
			activeChar.sendMessage("Account locked.");
			return true;
		}

		if(target.equalsIgnoreCase("off"))
		{
			LSConnection.getInstance().sendPacket(new LockAccountIP(activeChar.getAccountName(), "*"));
			activeChar.sendMessage("Account unlocked.");
			return true;
		}

		showDefaultPage(activeChar);
		return true;
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}