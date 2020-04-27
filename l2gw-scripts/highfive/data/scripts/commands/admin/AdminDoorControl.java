package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.tables.DoorTable;

public class AdminDoorControl extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_open", "usage: //open <doorId>"), 
					new AdminCommandDescription("admin_close", "usage: //close <doorId>"), 
					new AdminCommandDescription("admin_openall", null), 
					new AdminCommandDescription("admin_closeall", null), 
					new AdminCommandDescription("admin_doorget", "usage: //doorget <doorId>") 
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		try
		{
			if(command.equals("admin_open"))
			{
				if(args.length == 0)
				{
					L2Object target = activeChar.getTarget();
					if(target instanceof L2DoorInstance)
					{
						if(!AdminTemplateManager.checkCommand(command, activeChar, null, ((L2DoorInstance) target).getDoorId(), null, null))
						{
							Functions.sendSysMessage(activeChar, "Access denied.");
							return false;
						}

						((L2DoorInstance) target).openMe();
						return true;
					}

					activeChar.sendPacket(Msg.INVALID_TARGET);
					return false;
				}

				int doorId = Integer.parseInt(args[0]);
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, doorId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				L2DoorInstance door = null;
				if(activeChar.getReflection() > 0)
				{
					if(activeChar.getTarget() instanceof L2DoorInstance && ((L2DoorInstance) activeChar.getTarget()).getDoorId() == doorId)
						door = (L2DoorInstance) activeChar.getTarget();
				}
				else
					door = DoorTable.getInstance().getDoor(doorId);

				if(door == null)
					activeChar.sendMessage("Door " + doorId + " not found");
				else
					DoorTable.getInstance().getDoor(doorId).openMe();

				return true;
			}
			else if(command.equals("admin_close"))
			{
				if(args.length == 0)
				{
					L2Object target = activeChar.getTarget();
					if(target instanceof L2DoorInstance)
					{
						if(!AdminTemplateManager.checkCommand(command, activeChar, null, ((L2DoorInstance) target).getDoorId(), null, null))
						{
							Functions.sendSysMessage(activeChar, "Access denied.");
							return false;
						}

						((L2DoorInstance) target).closeMe();
						return true;
					}

					activeChar.sendPacket(Msg.INVALID_TARGET);
					return false;
				}

				int doorId = Integer.parseInt(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, doorId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				L2DoorInstance door = null;
				if(activeChar.getReflection() > 0)
				{
					if(activeChar.getTarget() instanceof L2DoorInstance && ((L2DoorInstance) activeChar.getTarget()).getDoorId() == doorId)
						door = (L2DoorInstance) activeChar.getTarget();
				}
				else
					door = DoorTable.getInstance().getDoor(doorId);

				if(door == null)
					activeChar.sendMessage("Door " + doorId + " not found");
				else
					DoorTable.getInstance().getDoor(doorId).closeMe();

				return true;
			}
			else if(command.equals("admin_doorget"))
			{
				int doorId = Integer.parseInt(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, doorId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(DoorTable.getInstance().getDoor(doorId) == null)
					activeChar.sendMessage("Door " + doorId + " not found");
				else
				{
					int objId = DoorTable.getInstance().getDoor(doorId).getObjectId();
					L2Object o = L2ObjectsStorage.findObject(objId);
					activeChar.sendMessage("DoorId = " + doorId + ", x=" + o.getX() + ", y=" + o.getY() + ", z=" + o.getZ());
				}

				return true;
			}
			if(command.equals("admin_closeall"))
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				for(L2DoorInstance door : DoorTable.getInstance().getDoors())
					door.closeMe();
			}
			if(command.equals("admin_openall"))
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				for(L2DoorInstance door : DoorTable.getInstance().getDoors())
					door.openMe();
			}
		}
		catch(Exception e)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}
		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}