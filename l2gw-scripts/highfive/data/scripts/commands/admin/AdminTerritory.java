package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

/**
 * @author rage
 * @date 07.07.2010 14:36:29
 */
public class AdminTerritory extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_tw_start", null),
			new AdminCommandDescription("admin_tw_end", null),
			new AdminCommandDescription("admin_tw_set_lord", null),
			new AdminCommandDescription("admin_tw_del_lord", null)};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_tw_start"))
		{
			if(TerritoryWarManager.getWar().isInProgress())
			{
				activeChar.sendMessage("TW in progress.");
				return true;
			}

			TerritoryWarManager.getWar().stopStartTask();
			TerritoryWarManager.getWar().startWar();
		}
		else if(command.equals("admin_tw_end"))
		{
			if(!TerritoryWarManager.getWar().isInProgress())
			{
				activeChar.sendMessage("TW not in progress.");
				return true;
			}

			TerritoryWarManager.getWar().stopEndTask();
			TerritoryWarManager.getWar().endWar();
		}
		else if(command.equals("admin_tw_set_lord"))
		{
			L2Player target = activeChar.getTarget() == null || !activeChar.getTarget().isPlayable() ? activeChar : activeChar.getTarget().getPlayer();

			if(target.getClanId() == 0 || target.getClan().getHasCastle() == 0)
			{
				activeChar.sendPacket(Msg.INVALID_TARGET);
				return true;
			}

			target.setVar("territory_lord_" + target.getTerritoryId(), "true");
			TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(target.getTerritoryId()));
		}
		else if(command.equals("admin_tw_del_lord"))
		{
			L2Player target = activeChar.getTarget() == null || !activeChar.getTarget().isPlayable() ? activeChar : activeChar.getTarget().getPlayer();

			if(target.getClanId() == 0 || target.getClan().getHasCastle() == 0)
			{
				activeChar.sendPacket(Msg.INVALID_TARGET);
				return true;
			}

			target.unsetVar("territory_lord_" + target.getTerritoryId());
			TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(target.getTerritoryId()));
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}