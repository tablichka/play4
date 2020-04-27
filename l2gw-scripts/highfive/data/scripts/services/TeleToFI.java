package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;

public class TeleToFI extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Loaded Service: Teleport to Fantasy Island");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public void toFI()
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player))
			return;

		player.setVar("backCoords", player.getX() + " " + player.getY() + " " + player.getZ());
		player.teleToLocation(-60695, -56894, -2032);
	}

	public void fromFI()
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player))
			return;

		String var = player.getVar("backCoords");
		if(var == null || var.equals(""))
		{
			teleOut(player);
			return;
		}
		String[] coords = var.split(" ");
		if(coords.length != 3)
		{
			teleOut(player);
			return;
		}
		player.teleToLocation(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
	}

	public void teleOut(L2Player player)
	{
		player.teleToLocation(12902, 181011, -3563);
		if(player.getVar("lang@").equalsIgnoreCase("en"))
			show("I don't know from where you came here, but I can teleport you to village.", player);
		else
			show("Я не знаю, как Вы попали сюда, но я могу Вас отправить в город.", player);
	}

	public boolean checkCondition(L2Player player)
	{
		return !(player.isActionsDisabled() || player.isSitting());
	}
}