package commands.user;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.util.Files;

/**
 * @author: rage
 * @date: 27.01.2010 16:02:14
 */
public class Siegestatus extends Functions implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = {99};

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(!activeChar.isClanLeader() || !activeChar.isNoble())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessage.ONLY_A_CLAN_LEADER_THAT_IS_A_NOBLESSE_CAN_VIEW_THE_SIEGE_WAR_STATUS_WINDOW_DURING_A_SIEGE_WAR));
			return true;
		}
		if(activeChar.getClan().getSiege() == null || !activeChar.getClan().getSiege().isInProgress() || !activeChar.getClan().getSiege().getSiegeUnit().isCastle)
		{
			activeChar.sendPacket(new SystemMessage(SystemMessage.IT_CAN_BE_USED_ONLY_WHILE_A_SIEGE_WAR_IS_TAKING_PLACE));
			return true;
		}

		L2Clan clan = activeChar.getClan();
		int kills = clan.getSiegeKills();
		int death = clan.getSiegeDeath();

		String members = "";

		for(L2Player member : clan.getOnlineMembers(""))
			if(member != null && member.isInSiege())
				members += "<tr><td align=left>" + member.getName() + "</td><td></td></tr>";

		String html = Files.read("data/html/siegestatus.htm");
		html = html.replace("%kill_count%", String.valueOf(kills));
		html = html.replace("%death_count%", String.valueOf(death));
		html = html.replace("%MEMBER_LIST%", members);

		show(html, activeChar);
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
