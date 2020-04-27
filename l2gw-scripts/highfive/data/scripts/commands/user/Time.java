package commands.user;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

/**
 * Support for /time command
 */
public class Time implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = { 77 };

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(COMMAND_IDS[0] != id)
			return false;

		int t = GameTimeController.getInstance().getGameTime();
		int h = t / 60 % 24;
		int m = t % 60;

		SystemMessage sm;
		if(h > 12)
		{
			h -= 12;
			sm = new SystemMessage(SystemMessage.THE_CURRENT_TIME_IS_S1S2_PM);
		}
		else
			sm = new SystemMessage(SystemMessage.THE_CURRENT_TIME_IS_S1S2_AM);
		sm.addNumber(h);
		sm.addString(String.format("%02d",m));
		activeChar.sendPacket(sm);
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
