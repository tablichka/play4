package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.fieldcycle.FieldCycle;
import ru.l2gw.gameserver.model.entity.fieldcycle.FieldStep;

import java.text.SimpleDateFormat;

/**
 * @author: rage
 * @date: 11.12.11 20:41
 */
public class AdminFieldCycle extends AdminBase
{
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_field_cycle", "usage: //field_cycle <add_point|set_step|show_point|show_step|info> <fieldId> <value>")
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if("admin_field_cycle".equals(command))
		{
			try
			{
				String cmd = args[0];
				int fieldId = Integer.parseInt(args[1]);

				switch(cmd)
				{
					case "add_point":
						int value = Integer.parseInt(args[2]);
						FieldCycleManager.addPoint("ADMIN", fieldId, value, activeChar);
						Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " point: " + FieldCycleManager.getPoint(fieldId));
						break;
					case "set_step":
						value = Integer.parseInt(args[2]);
						FieldCycleManager.setStep("ADMIN", fieldId, value, activeChar);
						Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " step: " + FieldCycleManager.getStep(fieldId));
						break;
					case "show_point":
						Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " point: " + FieldCycleManager.getPoint(fieldId));
						break;
					case "show_step":
						Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " step: " + FieldCycleManager.getStep(fieldId));
						break;
					case "info":
						Functions.sendSysMessage(activeChar, "=========================");
						Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " step: " + FieldCycleManager.getStep(fieldId) + " point: " + FieldCycleManager.getPoint(fieldId));
						FieldCycle fc = FieldCycleManager.getFieldCycle(fieldId);
						if(fc != null)
						{
							Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " point change: " + format.format(fc.getLastPointChange()));
							Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " step change: " + format.format(fc.getLastStepChange()));
							FieldStep fs = fc.getCurrentStep();
							if(fs != null)
							{
								if(fs.getExpireTime() > 0)
									Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " expire: " + format.format(fs.getExpireTime()));

								Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " interval time: " + (fs.getIntervalTime() / 1000) + " sec.");
								Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " interval point: " + fs.getIntervalPoint());
								if(fs.getMapString() > 0)
									Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " map string: " + fs.getMapString());
								if(fs.getMapLoc() != null)
									Functions.sendSysMessage(activeChar, "FieldCycle: " + fieldId + " map loc: " + fs.getMapLoc().getX() + ","+ fs.getMapLoc().getY() + ","+ fs.getMapLoc().getZ());
							}
						}
						break;
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "//field_cycle (add_point | set_step) fieldId value");
				Functions.sendSysMessage(activeChar, "//field_cycle (show_point | show_step) fieldId");
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