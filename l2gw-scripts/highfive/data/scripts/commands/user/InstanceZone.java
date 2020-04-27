package commands.user;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

/**
 * @author admin
 * @date 27.07.2009 19:28:56
 * /instancezone command
 */
public class InstanceZone implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = { 114 };

	public boolean useUserCommand(int id, L2Player player)
	{
		if(id != COMMAND_IDS[0] || player == null)
			return false;

		Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);

		if(inst != null)
			player.sendPacket(new SystemMessage(SystemMessage.INSTANT_ZONE_CURRENTLY_IN_USE_S1).addInstanceName(inst));

		boolean f = true;
		for(Integer type : InstanceManager.getInstance().getInstanceTypes())
			if(player.getVar("instance-" + type) != null)
			{
				int instId = Integer.parseInt(player.getVar("instance-" + type));
				InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);
				if(it != null)
				{
					int secLeft = (int)((player.getVarExpireTime("instance-" + type) - System.currentTimeMillis()) / 1000);
					int h = secLeft / 60 / 60;
					int m = secLeft / 60 % 60;
					player.sendPacket(new SystemMessage(SystemMessage.S1_WILL_BE_AVAILABLE_FOR_RE_USE_AFTER_S2_HOUR_S3_MINUTE).addInstanceName(it.getId()).addNumber(h).addNumber(m));
					f = false;
				}
			}

		if(f)
			player.sendPacket(new SystemMessage(SystemMessage.THERE_IS_NO_INSTANCE_ZONE_UNDER_A_TIME_LIMIT));

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
