package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.RaidBossSpawnManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SpawnTable;

public class AdminDelete extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_delete", null)
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;
		if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_delete"))
		{
			if(target instanceof L2NpcInstance)
			{
				target.deleteMe();

				L2Spawn spawn = ((L2NpcInstance) target).getSpawn();
				if(spawn != null)
				{
					spawn.stopRespawn();
					if(RaidBossSpawnManager.getInstance().isDefined(spawn.getNpcId()))
						RaidBossSpawnManager.getInstance().deleteSpawn(spawn, true);
					else
						SpawnTable.getInstance().deleteSpawn(spawn, true);
				}

				logGM.info(activeChar.toFullString() + " deleted " + target + " at " + target.getLoc());
				return true;
			}
			else
				activeChar.sendPacket(Msg.INVALID_TARGET);
		}

		return false;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}