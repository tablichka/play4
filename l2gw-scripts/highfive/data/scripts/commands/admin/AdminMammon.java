package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

import java.util.ArrayList;

/**
 * Admin Command Handler for Mammon NPCs
 */
public class AdminMammon extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_find_mammon", null), 
					new AdminCommandDescription("admin_show_mammon", null), 
					new AdminCommandDescription("admin_hide_mammon", null),
					new AdminCommandDescription("admin_list_spawns", null) 
			};


	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		ArrayList<Integer> npcIds = new ArrayList<>();

		if(command.equals("admin_find_mammon"))
		{
			npcIds.add(31113);
			npcIds.add(31126);
			npcIds.add(31092); // Add the Marketeer of Mammon also
			int teleportIndex = -1;

			try
			{
				if(args.length > 0)
					teleportIndex = Integer.parseInt(args[0]);
			}
			catch(Exception NumberFormatException)
			{
				// activeChar.sendPacket(SystemMessage.sendString("Command format is
				// //find_mammon <teleportIndex>"));
			}

			findAdminNPCs(activeChar, npcIds, teleportIndex, -1);
		}

		else if(command.equals("admin_show_mammon"))
		{
			npcIds.add(31113);
			npcIds.add(31126);

			findAdminNPCs(activeChar, npcIds, -1, 1);
		}

		else if(command.equals("admin_hide_mammon"))
		{
			npcIds.add(31113);
			npcIds.add(31126);

			findAdminNPCs(activeChar, npcIds, -1, 0);
		}

		else if(command.equals("admin_list_spawns"))
		{
			int npcId = 0;

			try
			{
				npcId = Integer.parseInt(args[0]);
			}
			catch(Exception NumberFormatException)
			{
				activeChar.sendMessage("Command format is //list_spawns <NPC_ID>");
			}

			npcIds.add(npcId);
			findAdminNPCs(activeChar, npcIds, -1, -1);
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	public void findAdminNPCs(L2Player activeChar, ArrayList<Integer> npcIdList, int teleportIndex, int makeVisible)
	{
		int index = 0;

		for(L2NpcInstance npcInst : L2ObjectsStorage.getAllNpcs())
		{
			int npcId = npcInst.getNpcId();

			if(npcIdList.contains(npcId))
			{
				if(makeVisible == 1)
					npcInst.spawnMe();
				else if(makeVisible == 0)
					npcInst.decayMe();

				if(npcInst.isVisible())
				{
					index++;

					if(teleportIndex > -1)
					{
						if(teleportIndex == index)
							activeChar.teleToLocation(npcInst.getLoc());
					}
					else
						activeChar.sendMessage(index + " - " + npcInst.getName() + " (" + npcInst.getObjectId() + "): " + npcInst.getX() + " " + npcInst.getY() + " " + npcInst.getZ());
				}
			}
		}
	}
}