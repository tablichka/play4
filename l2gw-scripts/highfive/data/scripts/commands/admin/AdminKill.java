package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminKill extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_kill", "usage: //kill <name or range>") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Character target = null;
		int range = 0;
		
		if(args.length > 0)
		{
			target = L2ObjectsStorage.getPlayer(args[0]);
			if(target == null)
			{
				try
				{
					range = Integer.parseInt(args[0]);
				}
				catch(Exception e)
				{
					// quite
				}
			}
		}
		
		if(target == null && activeChar.getTarget() instanceof L2Character)
			target = (L2Character) activeChar.getTarget();
		
		if(target != null)
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}
			
			target.reduceHp(target.getMaxHp() + target.getMaxCp() + 1, activeChar, false, false);
			logGM.info(activeChar.toFullString() + " " + "kill character " + target.getObjectId() + " " + target.getName());
			return true;
		}
		if(range > 0)
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, range, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			for(L2Character character : activeChar.getKnownCharacters(range))
			{
				if(AdminTemplateManager.checkCommand(command, activeChar, character, null, null, null))
					character.reduceHp(character.getMaxHp() + character.getMaxCp() + 1, character, false, false);
			}

			Functions.sendSysMessage(activeChar, "Kill all characters in range: " + range);
			return true;
		}

		Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
		return false;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}