package events.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.AbstractAINotifyEventListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.ai.AbstractAI;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.Wyvern;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.ValidateLocation;
import ru.l2gw.gameserver.tables.PetDataTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;


public class Transport extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	private static HashMap<String, Wyvern> wyverns;
	private static ConcurrentHashMap<Integer, Rider> _riders = new ConcurrentHashMap<Integer, Rider>();

	private static NotifyEventListener _notifyEventListener = new NotifyEventListener();

	private static boolean _active = false;

	protected static Log _log = LogFactory.getLog(Transport.class.getName());

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("transport", "off").equalsIgnoreCase("on");
	}

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			loadWyvernPath();
			_log.info("Loaded Event: Transport [state: activated Wyvern]");
		}
		else
			wyverns = null;
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{}

	/**
	* Запускает эвент
	*/
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("transport", "on");
			loadWyvernPath();
			_log.info("Event 'Transport' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.transport.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'Transport' already started.");

		_active = true;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	* Останавливает эвент
	*/
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("transport");
			wyverns = null;
			_log.info("Event 'Transport' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.transport.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'Transport' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	private void loadWyvernPath()
	{
		LineNumberReader lnr = null;
		wyverns = new HashMap<String, Wyvern>();
		try
		{
			File wyvernData = new File(Config.DATAPACK_ROOT, "data/wyvernpath.csv");
			lnr = new LineNumberReader(new BufferedReader(new FileReader(wyvernData)));
			String line = null;
			while((line = lnr.readLine()) != null)
			{
				if(line.trim().length() == 0 || line.startsWith("#"))
					continue;
				Wyvern W = new Wyvern();
				W.parseLine(line);
				wyverns.put(W.name, W);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(lnr != null)
					lnr.close();
			}
			catch(Exception e1)
			{ /* ignore problems */}
		}
	}

	public class Rider
	{
		public Wyvern W;
		public L2Player P;
		public Stack<Integer[]> way;
	}

	public void HireWyvern(String[] param)
	{
		if(param.length < 2)
			throw new IllegalArgumentException();

		if(!_active)
			return;

		if(wyverns == null)
			return;

		L2Player player = (L2Player) self;

		int price = Integer.parseInt(param[1]);

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if(day != 1 && day != 7 && (hour <= 12 || hour >= 22))
			price /= 2;

		if(player.isActionsDisabled() || player.isSitting() || player.getMountEngine().isMounted() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(player.isPetSummoned())
		{
			player.sendMessage("You can't ride wyvern while controlling pet or summon.");
			return;
		}

		if(price > 0 && !player.reduceAdena("TransportEvent", price, npc, true))
			return;

		player.setStablePoint(new Location(player.getX(), player.getY(), player.getZ()));
		player.setVar("wyvern_moneyback", String.valueOf(price));

		Wyvern W = wyverns.get(param[0]);

		player.getMountEngine().setMount(PetDataTable.getInstance().getInfo(PetDataTable.WYVERN_ID, player.getLevel()), 0);
		player.block();
		player.setIsInvul(true);

		Rider r = new Rider();
		r.P = player;
		r.W = W;
		r.way = new Stack<Integer[]>();
		r.way.addAll(W.path);
		_riders.put(player.getObjectId(), r);

		player.getAI().getListenerEngine().addMethodInvokedListener(_notifyEventListener);
		flyNext(r);
		player.broadcastUserInfo(true);
	}

	public static class NotifyEventListener extends AbstractAINotifyEventListener
	{
		@Override
		public void NotifyEvent(AbstractAI ai, CtrlEvent evt, Object[] args)
		{
			if(evt == CtrlEvent.EVT_ARRIVED)
			{
				if(ai == null || ai.getActor() == null)
					return;
				Rider r = _riders.get(ai.getActor().getObjectId());
				if(r == null)
					return;
				flyNext(r);
			}
		}
	}

	private static void flyNext(Rider r)
	{
		if(!r.way.empty())
		{
			// летим в следующую точку
			Integer[] next = r.way.firstElement();
			r.way.remove(0);
			if(next.length == 3)
				r.P.moveToLocation(next[0], next[1], next[2], 0, false);
			else
			{
				r.P.setXYZ(next[0], next[1], next[2], false);
				r.P.sendPacket(new ValidateLocation(r.P));
				r.P.moveToLocation(next[0], next[1], next[2], 0, false);
			}
		}
		else
		{
			r.P.getMountEngine().dismount();
			r.P.setStablePoint(null);
			r.P.unsetVar("wyvern_moneyback");
			r.P.setIsInvul(false);
			r.P.unblock();
			r.P.getAI().getListenerEngine().removeMethodInvokedListener(_notifyEventListener);
			_riders.remove(r.P.getObjectId());
			r.P.broadcastUserInfo(true);
		}
	}

	public static String DialogAppend_31212(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31212.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31213(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31213.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31214(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31214.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31215(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31215.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31216(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31216.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31217(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31217.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31218(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31218.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31219(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31219.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31220(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31220.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31221(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31221.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31222(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31222.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31223(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31223.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31224(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31224.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31767(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31767.htm", player);
		}
		return "";
	}

	public static String DialogAppend_31768(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/31768.htm", player);
		}
		return "";
	}

	public static String DialogAppend_32048(Integer val)
	{
		if(_active && val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/transport/32048.htm", player);
		}
		return "";
	}
}
