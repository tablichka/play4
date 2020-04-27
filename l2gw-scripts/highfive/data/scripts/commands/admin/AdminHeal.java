package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminHeal extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_heal", "usage: //heal <name or range>") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_heal"))
		{
			L2Character target = null;
			int radius = 0;
			if(args.length > 0)
			{
				target = L2ObjectsStorage.getPlayer(args[0]);
				if(target == null)
				{
					try
					{
						radius = Integer.parseInt(args[0]);
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

				target.setCurrentHpMp(target.getMaxHp(), target.getMaxMp());
				if(target.isPlayer())
					target.setCurrentCp(target.getMaxCp());

				logGM.info(activeChar.toFullString() + " heal target: " + target);
				return true;
			}

			if(radius > 0)
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, radius, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				for(L2Character character : activeChar.getKnownCharacters(radius))
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, character, null, null, null))
					{
						continue;
					}

					character.setCurrentHpMp(character.getMaxHp(), character.getMaxMp());
					if(character.isPlayer())
						character.setCurrentCp(character.getMaxCp());

					logGM.info(activeChar.toFullString() + " heal in radius: " + radius + " target: " + character);
				}
				activeChar.sendMessage("Healed within " + radius + " unit radius.");
			}
			else
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}