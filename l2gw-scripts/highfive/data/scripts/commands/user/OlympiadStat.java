package commands.user;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.templates.StatsSet;

/**
 * Support for /olympiadstat command
 */
public class OlympiadStat implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = { 109 };

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(id != COMMAND_IDS[0])
			return false;

		L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

		if(!target.isNoble())
			activeChar.sendPacket(new SystemMessage(SystemMessage.THIS_COMMAND_CAN_ONLY_BE_USED_BY_A_NOBLESSE));
		else
		{
			StatsSet nobles = Olympiad.getNoblesData(target);
			SystemMessage sm = new SystemMessage(SystemMessage.THE_CURRENT_FOR_THIS_OLYMPIAD_IS_S1_WINS_S2_DEFEATS_S3_YOU_HAVE_EARNED_S4_OLYMPIAD_POINTS);
			SystemMessage sm2 = new SystemMessage(SystemMessage.YOU_HAVE_S1_MATCHES_REMAINING_THAT_YOU_CAN_PARTICIPATE_IN_THIS_WEEK_S2_1_VS_1_CLASS_MATCHES_S3_1_VS_1_MATCHES_S4_3_VS_3_TEAM_MATCHES);
			if(nobles != null)
			{
				sm.addNumber(nobles.getInteger("wins") + nobles.getInteger("loos"));
				sm.addNumber(nobles.getInteger("wins"));
				sm.addNumber(nobles.getInteger("loos"));
				sm.addNumber(nobles.getInteger("points"));
				sm2.addNumber(Math.max(0, Config.ALT_OLY_MATCH_LIMIT - nobles.getInteger("cb_matches") - nobles.getInteger("ncb_matches") - nobles.getInteger("team_matches")));
				sm2.addNumber(Math.max(0, Config.ALT_OLY_CB_LIMIT - nobles.getInteger("cb_matches")));
				sm2.addNumber(Math.max(0, Config.ALT_OLY_NCB_LIMIT - nobles.getInteger("ncb_matches")));
				sm2.addNumber(Math.max(0, Config.ALT_OLY_TEAM_LIMIT - nobles.getInteger("team_matches")));
			}
			else
			{
				sm.addNumber(0);
				sm.addNumber(0);
				sm.addNumber(0);
				sm.addNumber(0);
				sm2.addNumber(Config.ALT_OLY_MATCH_LIMIT);
				sm2.addNumber(Config.ALT_OLY_CB_LIMIT);
				sm2.addNumber(Config.ALT_OLY_NCB_LIMIT);
				sm2.addNumber(Config.ALT_OLY_TEAM_LIMIT);
			}
			activeChar.sendPacket(sm);
			activeChar.sendPacket(sm2);
		}
		return true;
	}

	public int[] getUserCommandList()
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
