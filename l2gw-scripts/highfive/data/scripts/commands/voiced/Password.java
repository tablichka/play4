package commands.voiced;

import ru.l2gw.commons.utils.StringUtil;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.ChangePassword;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @Author: Death
 * @Date: 16/6/2007
 * @Time: 11:27:35
 */
public class Password extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private String[] _commandList = new String[] { "password" };

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public void check(String[] var)
	{
		if(var.length != 3)
		{
			show(new CustomMessage("scripts.commands.user.password.IncorrectValues", self), (L2Player) self);
			return;
		}
		useVoicedCommand("password", (L2Player) self, var[0] + " " + var[1] + " " + var[2]);
	}

	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if(command.equals("password") && (target == null || target.equals("")))
		{
			if(Config.SERVICES_CHANGE_PASSWORD)
				show("data/scripts/commands/voiced/password.html", activeChar);
			else
				show("data/scripts/commands/voiced/nopassword.html", activeChar);
			return true;
		}

		String[] parts = target.split(" ");

		if(parts.length != 3)
		{
			show(new CustomMessage("scripts.commands.user.password.IncorrectValues", activeChar), activeChar);
			return false;
		}

		if(!parts[1].equals(parts[2]))
		{
			show(new CustomMessage("scripts.commands.user.password.IncorrectConfirmation", activeChar), activeChar);
			return false;
		}

		if(parts[1].equals(parts[0]))
		{
			show(new CustomMessage("scripts.commands.user.password.NewPassIsOldPass", activeChar), activeChar);
			return false;
		}

		if(parts[1].length() < 5 || parts[1].length() > 20)
		{
			show(new CustomMessage("scripts.commands.user.password.IncorrectSize", activeChar), activeChar);
			return false;
		}

		if(!StringUtil.isMatchingRegexp(parts[1], Config.APASSWD_TEMPLATE))
		{
			show(new CustomMessage("scripts.commands.user.password.IncorrectInput", activeChar), activeChar);
			return false;
		}

		LSConnection.getInstance().sendPacket(new ChangePassword(activeChar.getAccountName(), parts[0], parts[1]));
		return true;
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}
