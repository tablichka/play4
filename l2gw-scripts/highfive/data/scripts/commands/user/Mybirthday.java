package commands.user;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

import java.util.Calendar;

/**
 * @author rage
 * @date 24.02.2010 11:34:40
 */
public class Mybirthday implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = {126};

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(COMMAND_IDS[0] != id)
			return false;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(activeChar.getBirthday());
		activeChar.sendPacket(new SystemMessage(SystemMessage.C1S_CHARACTER_BIRTHDAY_IS_S3_S4_S2).addCharName(activeChar).addNumber(cal.get(Calendar.YEAR)).addNumber(cal.get(Calendar.DAY_OF_MONTH)).addNumber(cal.get(Calendar.MONTH) + 1));
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
	{
	}

	public void onShutdown()
	{
	}
}
