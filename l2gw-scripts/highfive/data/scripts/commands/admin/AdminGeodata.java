package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminGeodata extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_geo_z", null),
					new AdminCommandDescription("admin_geo_type", null),
					new AdminCommandDescription("admin_geo_nswe", null),
					new AdminCommandDescription("admin_geo_los", null),
					new AdminCommandDescription("admin_cansee", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		switch(command)
		{
			case "admin_geo_z":
				activeChar.sendMessage("GeoEngine: Geo_Z = " + GeoEngine.getHeight(activeChar.getLoc(), activeChar.getReflection()) + " Loc_Z = " + activeChar.getZ());
				break;
			case "admin_geo_type":
				int type = GeoEngine.getType(activeChar.getX(), activeChar.getY(), activeChar.getReflection());
				activeChar.sendMessage("GeoEngine: Geo_Type = " + type);
				break;
			case "admin_geo_nswe":
				String result = "";
				int nswe = GeoEngine.getNSWE(activeChar.getX(), activeChar.getY(), activeChar.getZ(), activeChar.getReflection());
				if((nswe & 8) == 0)
					result += " N";
				if((nswe & 4) == 0)
					result += " S";
				if((nswe & 2) == 0)
					result += " W";
				if((nswe & 1) == 0)
					result += " E";
				activeChar.sendMessage("GeoEngine: Geo_NSWE -> " + nswe + "->" + result);
				break;
			case "admin_geo_los":
				if(activeChar.getTarget() != null)
					if(GeoEngine.canSeeTarget(activeChar, activeChar.getTarget()))
						activeChar.sendMessage("GeoEngine: Can See Target");
					else
						activeChar.sendMessage("GeoEngine: Can't See Target");
				else
					activeChar.sendMessage("None Target!");
				break;
			case "admin_cansee":
				if(activeChar.getTarget() == null)
				{
					activeChar.sendMessage("cansee: no target");
					return false;
				}
				L2Object target = activeChar.getTarget();
				activeChar.sendMessage("canSee: " + GeoEngine.canSeeTarget(activeChar, target, activeChar.isFloating(), true));
				break;
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}