package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.SetupGauge;

public class AdminPolymorph extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_polymorph", "usage: //polymorph <id> [type]"),
					new AdminCommandDescription("admin_unpolymorph", null), 
					new AdminCommandDescription("admin_poly", "usage: //poly <id> [type]"),
					new AdminCommandDescription("admin_unpoly", null) 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_polymorph") || command.equals("admin_poly"))
		{
			try
			{
				String id = args[0];
				String type;

				if(args.length > 1)
					type = args[1];
				else
					type = "npc";

				L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, id, type, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target != null)
				{
					target.setPolyInfo(type, id);
					if(target.isCharacter())
					{
						L2Character Char = (L2Character) target;
						Char.broadcastPacket(new MagicSkillUse(Char, Char, 1008, 1, 1000, 0));
						Char.sendPacket(new SetupGauge(0, 1000));
					}
					target.decayMe();
					target.spawnMe(target.getLoc());
				}
				else
					activeChar.sendPacket(Msg.INVALID_TARGET);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_unpolymorph") || command.equals("admin_unpoly"))
		{
			L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;

			if(target != null)
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				target.setPolyInfo(null, "0");
				if(target.isCharacter())
				{
					L2Character Char = (L2Character) target;
					Char.broadcastPacket(new MagicSkillUse(Char, Char, 1008, 1, 1000, 0));
					Char.sendPacket(new SetupGauge(0, 1000));
				}
				target.decayMe();
				target.spawnMe(target.getLoc());
			}
			else
				activeChar.sendPacket(Msg.INVALID_TARGET);
		}
		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}