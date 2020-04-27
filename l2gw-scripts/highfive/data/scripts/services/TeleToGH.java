package services;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Util;

import java.util.ArrayList;

public class TeleToGH extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	public void onLoad()
	{
		if(Config.SERVICES_GIRAN_HARBOR_ENABLED)
		{
			try
			{
				// init reflection
				//ReflectionTable.getInstance().getById(-2, true).setCore(new Location(47416, 186568, -3480));

				// spawn wh keeper
				L2Spawn sp1 = new L2Spawn(NpcTable.getTemplate(30086));
				sp1.setLocx(48059);
				sp1.setLocy(186791);
				sp1.setLocz(-3512);
				sp1.setAmount(1);
				sp1.setHeading(42000);
				sp1.setRespawnDelay(5);
				sp1.init();
				sp1.getAllSpawned().iterator().next().setReflection(-2);
				_spawns.add(sp1);

				// spawn grocery trader (Helvetia)
				L2Spawn sp2 = new L2Spawn(NpcTable.getTemplate(30081));
				sp2.setLocx(48146);
				sp2.setLocy(186753);
				sp2.setLocz(-3512);
				sp2.setAmount(1);
				sp2.setHeading(42000);
				sp2.setRespawnDelay(5);
				sp2.init();
				sp2.getAllSpawned().iterator().next().setReflection(-2);
				_spawns.add(sp2);

				// spawn gk
				L2NpcTemplate t = NpcTable.getTemplate(36394);
				t.displayId = 36394;
				t.title = "Gatekeeper";
				t.type = "L2Merchant";
				t.ai_type = "npc";
				L2Spawn sp3 = new L2Spawn(t);
				sp3.setLocx(47984);
				sp3.setLocy(186832);
				sp3.setLocz(-3445);
				sp3.setAmount(1);
				sp3.setHeading(42000);
				sp3.setRespawnDelay(5);
				sp3.init();
				sp3.getAllSpawned().iterator().next().setReflection(-2);
				_spawns.add(sp3);

				/*
				//Respawn Old GK
				L2Spawn sp4 = SpawnTable.getInstance().getSpawnsByNpcId(30878).get(0);
				sp4.despawnAll();
				sp4.setLocx(46447);
				sp4.setLocy(185935);
				sp4.setLocz(-3583);
				sp4.setHeading(42000);
				sp4.doSpawn(true);
				_spawns.add(sp4);*/

				// spawn Orion the Cat
				L2Spawn sp5 = new L2Spawn(NpcTable.getTemplate(31860));
				sp5.setLocx(48129);
				sp5.setLocy(186828);
				sp5.setLocz(-3512);
				sp5.setAmount(1);
				sp5.setHeading(45452);
				sp5.setRespawnDelay(5);
				sp5.init();
				sp5.getAllSpawned().iterator().next().setReflection(-2);
				_spawns.add(sp5);

				// spawn blacksmith (Pushkin)
				L2Spawn sp6 = new L2Spawn(NpcTable.getTemplate(30300));
				sp6.setLocx(48102);
				sp6.setLocy(186772);
				sp6.setLocz(-3512);
				sp6.setAmount(1);
				sp6.setHeading(42000);
				sp6.setRespawnDelay(5);
				sp6.init();
				sp6.getAllSpawned().iterator().next().setReflection(-2);
				_spawns.add(sp6);
			}
			catch(SecurityException e)
			{
				e.printStackTrace();
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}

			_zoneListener = new ZoneListener();
			_zone = ZoneManager.getInstance().getZoneById(ZoneType.offshore, 500014);
			_zone.getListenerEngine().addMethodInvokedListener(_zoneListener);

			ZoneManager.getInstance().getZoneById(ZoneType.offshore, 500014).setActive(true);
			ZoneManager.getInstance().getZoneById(ZoneType.peace, 500023).setActive(true);
			ZoneManager.getInstance().getZoneById(ZoneType.dummy, 500024).setActive(true);

			_log.info("Loaded Service: Teleport to Giran Harbor");
		}
	}

	public void onReload()
	{
		if(Config.SERVICES_GIRAN_HARBOR_ENABLED) 
		{ 
			_zone.getListenerEngine().removeMethodInvokedListener(_zoneListener);
			for(L2Spawn spawn : _spawns)
				spawn.despawnAll();
			_spawns.clear();
		}
	}

	public void onShutdown()
	{}

	public void toGH()
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player))
			return;

		player.setReflection(-2);
		player.setStablePoint(player.getLoc());
		player.teleToLocation(47416, 186568, -3480);
	}

	public void fromGH()
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player))
			return;

		if(player.getStablePoint() != null)
		{
			player.teleToLocation(player.getStablePoint(), 0);
			player.setStablePoint(null);
			return;
		}

		teleOut(player);
	}

	public void teleOut(L2Player player)
	{
		player.setReflection(0);
		player.teleToLocation(46776, 185784, -3528);
		if(player.getVar("lang@").equalsIgnoreCase("en"))
			show("I don't know from where you came here, but I can teleport you the another border side.", player);
		else
			show("Я не знаю, как Вы попали сюда, но я могу Вас отправить за ограждение.", player);
	}

	public boolean checkCondition(L2Player player)
	{
		return !(player.isActionsDisabled() || player.isSitting());
	}

	public static String DialogAppend_30059(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30080(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30177(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30233(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30256(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30320(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30848(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30878(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30899(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31210(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31275(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31320(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31964(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30006(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30134(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30146(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_32163(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30576(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30540(Integer val)
	{
		return getHtmlAppends(val);
	}

	private static final String en = "<br1>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"I want free admision to the Giran Harbor.\"]<br1>";
	private static final String ru = "<br1>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"Я хочу бесплатно попасть в Giran Harbor.\"]<br1>";

	public static String getHtmlAppends(Integer val)
	{
		if(val != 0 || !Config.SERVICES_GIRAN_HARBOR_ENABLED || self == null)
			return "";
		if(((L2Player) self).getVar("lang@").equalsIgnoreCase("ru"))
			return ru;
		return en;
	}

	private L2Zone _zone;
	private ZoneListener _zoneListener;

	public class ZoneListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
			L2Player player = object.getPlayer();
			if(player != null && Config.SERVICES_GIRAN_HARBOR_ENABLED && player.getReflection() == -2 && player.isMoving)
			{
				L2Playable playable = (L2Playable) object;
				double angle = Util.convertHeadingToDegree(playable.getHeading()); // угол в градусах
				double radian = Math.toRadians(angle - 90); // угол в радианах
				playable.teleToLocation((int) (playable.getX() + 50 * Math.sin(radian)), (int) (playable.getY() - 50 * Math.cos(radian)), playable.getZ());
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}
}