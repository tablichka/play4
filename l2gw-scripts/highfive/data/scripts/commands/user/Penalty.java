package commands.user;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Player;

import java.text.SimpleDateFormat;
import ru.l2gw.util.Files;

/**
 * Support for /clanpenalty command
 */
public class Penalty extends Functions implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = { 100 };

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(COMMAND_IDS[0] != id)
			return false;

		long _leaveclan = 0;
		if(activeChar.getLeaveClanTime() != 0)
			_leaveclan = activeChar.getLeaveClanTime() + 1 * 24 * 60 * 60 * 1000;
		long _deleteclan = 0;
		if(activeChar.getDeleteClanTime() != 0)
			_deleteclan = activeChar.getDeleteClanTime() + 10 * 24 * 60 * 60 * 1000;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String html = Files.read("data/scripts/commands/user/penalty.htm");

		if(activeChar.getClanId() == 0)
		{
			if(_leaveclan == 0 && _deleteclan == 0)
			{
				html = html.replaceFirst("%reason%", "No penalty is imposed.");
				html = html.replaceFirst("%expiration%", " ");
			}
			else if(_leaveclan > 0 && _deleteclan == 0)
			{
				html = html.replaceFirst("%reason%", "Penalty for leaving clan.");
				html = html.replaceFirst("%expiration%", format.format(_leaveclan));
			}
			else if(_deleteclan > 0)
			{
				html = html.replaceFirst("%reason%", "Penalty for dissolving clan.");
				html = html.replaceFirst("%expiration%", format.format(_deleteclan));
			}
		}
		else if(activeChar.getClan().canInvite())
		{
			html = html.replaceFirst("%reason%", "No penalty is imposed.");
			html = html.replaceFirst("%expiration%", " ");
		}
		else
		{
			html = html.replaceFirst("%reason%", "Penalty for expelling clan member.");
			html = html.replaceFirst("%expiration%", format.format(activeChar.getClan().getExpelledMemberTime()));
		}
		show(html, activeChar);
		return false;
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
