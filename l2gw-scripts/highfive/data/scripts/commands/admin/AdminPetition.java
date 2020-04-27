package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.PetitionManager;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

public class AdminPetition extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{
		new AdminCommandDescription("admin_view_petitions", null),
		new AdminCommandDescription("admin_view_petition", null),
		new AdminCommandDescription("admin_accept_petition", null),
		new AdminCommandDescription("admin_reject_petition", null),
		new AdminCommandDescription("admin_reset_petitions", null),
		new AdminCommandDescription("admin_force_petition", null) 
	};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		int petitionId = -1;
		if(args.length > 0)
			petitionId = Integer.parseInt(args[0]);

		if(command.equals("admin_view_petitions"))
			PetitionManager.getInstance().sendPendingPetitionList(activeChar);
		else if(command.equals("admin_view_petition"))
			PetitionManager.getInstance().viewPetition(activeChar, petitionId);
		else if(command.equals("admin_accept_petition"))
		{
			if(petitionId < 0)
			{
				activeChar.sendMessage("Usage: //accept_petition id");
				return false;
			}
			if(PetitionManager.getInstance().isPlayerInConsultation(activeChar))
			{
				activeChar.sendPacket(new SystemMessage(SystemMessage.ALREADY_APPLIED_FOR_PETITION));
				return true;
			}
			if(PetitionManager.getInstance().isPetitionInProcess(petitionId))
			{
				activeChar.sendPacket(new SystemMessage(SystemMessage.PETITION_UNDER_PROCESS));
				return true;
			}
			if(!PetitionManager.getInstance().acceptPetition(activeChar, petitionId))
				activeChar.sendPacket(new SystemMessage(SystemMessage.NOT_UNDER_PETITION_CONSULTATION));
		}
		else if(command.equals("admin_reject_petition"))
		{
			if(petitionId < 0)
			{
				activeChar.sendMessage("Usage: //accept_petition id");
				return false;
			}
			if(!PetitionManager.getInstance().rejectPetition(activeChar, petitionId))
				activeChar.sendPacket(new SystemMessage(SystemMessage.FAILED_TO_CANCEL_PETITION_PLEASE_TRY_AGAIN_LATER));
			PetitionManager.getInstance().sendPendingPetitionList(activeChar);
		}
		else if(command.equals("admin_reset_petitions"))
		{
			if(PetitionManager.getInstance().isPetitionInProcess())
			{
				activeChar.sendPacket(new SystemMessage(SystemMessage.PETITION_UNDER_PROCESS));
				return false;
			}
			PetitionManager.getInstance().clearPendingPetitions();
			PetitionManager.getInstance().sendPendingPetitionList(activeChar);
		}
		else if(command.equals("admin_force_petition"))
		{
			if(args.length < 1)
			{
				activeChar.sendMessage("Usage: //force_petition text");
				return false;
			}
			try
			{
				L2Object targetChar = activeChar.getTarget();
				if(targetChar == null || !targetChar.isPlayer())
				{
					activeChar.sendPacket(new SystemMessage(SystemMessage.INVALID_TARGET));
					return false;
				}
				L2Player targetPlayer = targetChar.getPlayer();
				petitionId = PetitionManager.getInstance().submitPetition(targetPlayer, fullCommand.substring(14), 9);
				PetitionManager.getInstance().acceptPetition(activeChar, petitionId);
			}
			catch(StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //force_peti text");
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