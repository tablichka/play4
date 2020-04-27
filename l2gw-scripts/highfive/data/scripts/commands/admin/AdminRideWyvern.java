package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.tables.PetDataTable;

public class AdminRideWyvern extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS = {
			new AdminCommandDescription("admin_ride_wyvern", null),
			new AdminCommandDescription("admin_ride_strider", null),
			new AdminCommandDescription("admin_unride_wyvern", null),
			new AdminCommandDescription("admin_unride_strider", null),
			new AdminCommandDescription("admin_unride", null),
			new AdminCommandDescription("admin_wr", null),
			new AdminCommandDescription("admin_sr", null),
			new AdminCommandDescription("admin_ur", null) };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_ride_wyvern") || command.equals("admin_wr"))
		{
			if(activeChar.getMountEngine().isMounted() || activeChar.getPet() != null)
			{
				activeChar.sendMessage("Already Have a Pet or Mounted.");
				return false;
			}
			activeChar.getMountEngine().setMount(PetDataTable.getInstance().getInfo(PetDataTable.WYVERN_ID, activeChar.getLevel()), 0);
		}
		else if(command.equals("admin_ride_strider") || command.equals("admin_sr"))
		{
			if(activeChar.getMountEngine().isMounted() || activeChar.getPet() != null)
			{
				activeChar.sendMessage("Already Have a Pet or Mounted.");
				return false;
			}
			activeChar.getMountEngine().setMount(PetDataTable.getInstance().getInfo(PetDataTable.WYVERN_ID, activeChar.getLevel()), 0);
		}
		else if(command.equals("admin_unride") || command.equals("admin_ur"))
			activeChar.getMountEngine().dismount();

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}