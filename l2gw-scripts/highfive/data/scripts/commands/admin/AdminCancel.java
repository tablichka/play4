package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.Revive;
import ru.l2gw.gameserver.serverpackets.SocialAction;

public class AdminCancel extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
			        new AdminCommandDescription("admin_cancel", "usage: //cancel <name or radius>")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_cancel"))
		{
			L2Character target = null;
			int radius = 0;
			if(args.length > 0)
			{
				L2Character c0 = L2ObjectsStorage.getPlayer(args[0]);
				if(c0 == null)
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
				else 
					target = c0;
			}
			
			if(target == null)
				target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;
			
			if(radius > 0)
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, radius, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				for(L2Character cha : activeChar.getKnownCharacters(radius))
				{
					cha.stopAllEffects();
					cha.broadcastPacket(new SocialAction(cha.getObjectId(), 15));
					cha.broadcastPacket(new Revive(cha));
				}
				
				Functions.sendSysMessage(activeChar, "Apply cancel in radius: " + radius);
			}
			else if(target != null)
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				target.stopAllEffects();
				target.broadcastPacket(new SocialAction(target.getObjectId(), 15));
				target.broadcastPacket(new Revive(target));

				Functions.sendSysMessage(activeChar, "Apply cancel to: " + target);
			}
			else
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}