package services;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.SiegeManager;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SetupGauge;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

public class RideHire extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public static String DialogAppend_30827(Integer val)
	{
		if(Config.SERVECES_RIDEHIRE && val == 0)
		{
			L2Player player = (L2Player) self;
			String lang = player.getVar("lang@");
			if(lang == null)
				lang = "en";
			if(lang.equalsIgnoreCase("en"))
				return "<br>[scripts_services.RideHire:ride_prices|Ride hire mountable pet.]";
			return "<br>[scripts_services.RideHire:ride_prices|Взять на прокат ездовое животное.]";
		}
		return "";
	}

	public static void ride_prices()
	{
		if(Config.SERVECES_RIDEHIRE)
			show("data/scripts/services/ride-prices.htm", (L2Player) self);
		else
			show("Сервис проката животных отключен", (L2Player) self);
	}

	public static void ride(String[] args)
	{
		L2Player player = (L2Player) self;
		String lang = player.getVar("lang@");
		if(lang == null)
			lang = "en";
		if(!Config.SERVECES_RIDEHIRE)
		{
			show("Сервис проката животных отключен", player);
			return;
		}
		if(args.length != 3)
		{
			if(lang.equalsIgnoreCase("en"))
				show("Incorrect input", player);
			else
				show("Некорректные данные", player);
			return;
		}

		if(player.isActionsDisabled() || player.getLastNpc().getDistance(player) > 250)
			return;

		if(!SiegeManager.getCanRide())
		{
			if(lang.equalsIgnoreCase("en"))
				show("Can't ride while Siege in progress.", player);
			else
				show("Прокат не работает во время осады.", player);
			return;
		}

		if(player.getTransformation() != 0)
		{
			if(lang.equalsIgnoreCase("en"))
				show("Can't ride while in transformation mode.", player);
			else
				show("Вы не можете взять пета в прокат, пока находитесь в режиме трансформации.", player);
			return;
		}

		if(player.isPetSummoned() || player.getMountEngine().isMounted())
		{
			player.sendPacket(new SystemMessage(SystemMessage.YOU_ALREADY_HAVE_A_PET));
			return;
		}

		Integer npc_id = Integer.parseInt(args[0]);
		Integer time = Integer.parseInt(args[1]);
		Integer price = Integer.parseInt(args[2]);

		if(npc_id != 12621 && npc_id != 12526 && npc_id != 16030)
		{
			if(lang.equalsIgnoreCase("en"))
				show("Unknown pet.", player);
			else
				show("У меня нет таких питомцев!", player);
			return;
		}

		if(time > 1800)
		{
			if(lang.equalsIgnoreCase("en"))
				show("Too long time to ride.", player);
			else
				show("Слишком большое время.", player);
			return;
		}

		if(player.reduceAdena("RideHire", price, player.getLastNpc(), true))
			doLimitedRide(player, npc_id, time);
	}

	public static void doLimitedRide(L2Player player, Integer npc_id, Integer time)
	{
		if(!ride(player, npc_id))
			return;
		player.sendPacket(new SetupGauge(3, time * 1000));
		executeTask(player, "services.RideHire", "rideOver", new Object[0], time * 1000);
	}

	public static void rideOver()
	{
		if(self == null)
			return;
		L2Player player = (L2Player) self;
		unRide(player);
		String lang = player.getVar("lang@");
		if(lang.equalsIgnoreCase("en"))
			show("Ride time is over.<br><br>Welcome back again!", player);
		else
			show("Время проката закончилось. Приходите еще!", player);
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Ride Hire");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}