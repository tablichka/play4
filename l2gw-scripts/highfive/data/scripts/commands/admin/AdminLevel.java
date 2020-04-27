package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.Experience;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminLevel extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS =
			{
					new AdminCommandDescription("admin_addlevel", "usage: //addlevel <level> player must be in target"),
					new AdminCommandDescription("admin_setlevel", "usage: //setlevel <level> player must be in target")
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Player target = activeChar.getTargetPlayer();

		if(target == null || args.length < 1)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}

		int level = 0;
		try
		{
			level = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}

		if(!AdminTemplateManager.checkCommand(command, activeChar, target, level, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_addLevel"))
		{
			if(level >= 1 && level <= Experience.getMaxLevel())
			{
				Long exp_add = Experience.LEVEL[level] - target.getExp();
				target.addExpAndSp(exp_add, 0);
			}
			else
			{
				activeChar.sendMessage("You must specify level between 1 and " + Experience.getMaxLevel() + ".");
				return false;
			}
		}
		else if(command.equals("admin_setLevel"))
		{
			if(level >= 1 && level <= Experience.getMaxLevel())
			{
				Long exp_add = Experience.LEVEL[level] - target.getExp();
				target.addExpAndSp(exp_add, 0);
			}
			else
			{
				activeChar.sendMessage("You must specify level between 1 and " + Experience.getMaxLevel() + ".");
				return false;
			}
		}
		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}