package commands.user;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

import static ru.l2gw.gameserver.model.zone.L2Zone.ZoneType.no_escape;

/**
 * Support for /unstuck command
 */
public class Escape implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = { 52 };

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(id != COMMAND_IDS[0])
			return false;

		if(activeChar.isMovementDisabled() || activeChar.isInOlympiadMode())
			return false;

		if(activeChar.getTeleMode() != 0 || activeChar.getUnstuck() != 0 || activeChar.isInZone(no_escape))
		{
			activeChar.sendMessage(new CustomMessage("common.TryLater", activeChar));
			return false;
		}

		if(activeChar.isInDuel())
		{
			activeChar.sendMessage(new CustomMessage("common.RecallInDuel", activeChar));
			return false;
		}

		activeChar.abortCast();
		activeChar.abortAttack();
		activeChar.stopMove();

		L2Skill skill = SkillTable.getInstance().getInfo(2099, 1);

		if(skill != null && skill.checkCondition(activeChar, activeChar, null, false, true))
			activeChar.getAI().Cast(skill, activeChar, null, false, true);

		return true;
	}

	public final int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}

	public void onLoad()
	{
		UserCommandHandler.getInstance().registerUserCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
