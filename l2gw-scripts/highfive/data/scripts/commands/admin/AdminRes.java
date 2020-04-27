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
import ru.l2gw.gameserver.taskmanager.DecayTaskManager;

public class AdminRes extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_res", "usage: //res <name> or <range>") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Character target = null;
		int range = 0;
		
		if(args.length > 0)
		{
			target = L2ObjectsStorage.getPlayer(args[0]);
			if(target == null)
			{
				try
				{
					range = Integer.parseInt(args[0]);
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
				Functions.sendSysMessage(activeChar, "Access denied");
				return false;
			}

			if(!target.isDead())
				return false;

			target.setCurrentHpMp(target.getMaxHp(), target.getMaxMp());
			target.setCurrentCp(target.getMaxCp());
			// GM Resurrection will restore any lost exp
			if(target.isPlayer())
			{
				((L2Player) target).restoreExp();
			}

			target.broadcastPacket(new SocialAction(target.getObjectId(), 15));
			target.broadcastPacket(new Revive(target));
			target.doRevive();
			logGM.info(activeChar.toFullString() + " resurrected character " + target.getObjectId() + " " + target.getName());
			return true;
		}

		if(range > 0)
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, target, range, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied");
				return false;
			}

			for(L2Character character : activeChar.getKnownCharacters(range))
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, character, null, null, null))
					continue;

				character.setCurrentHpMp(character.getMaxHp(), character.getMaxMp());
				character.setCurrentCp(character.getMaxCp());
				if(character.isPlayer())
					((L2Player) character).restoreExp();
					// If the target is an NPC, then abort it's auto decay and respawn.
				else
					DecayTaskManager.getInstance().cancelDecayTask(character);

				character.broadcastPacket(new SocialAction(character.getObjectId(), 15));
				character.broadcastPacket(new Revive(character));
				character.doRevive();
				logGM.info(activeChar.toFullString() + " resurrected character " + character.getObjectId() + " " + character.getName());
			}

			Functions.sendSysMessage(activeChar, "Resurrected in range: " + range);
			return true;
		}

		Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
		return false;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}