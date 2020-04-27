package events.coldrop;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.util.Files;
import ru.l2gw.commons.math.Rnd;

/**
 * User: Incomig
 * Date: 23.11.08
* Time: 5:00:18
 *INSERT INTO `server_variables` (`name`,`value`) VALUES ('col_drop','off') - создать!!!
 */
public class coldrop extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;

	// COL
	private static int COL_CHANCE = 100;
	private static int COL2_CHANCE = 100;

	// Для временного статуса который выдается в игре рандомно либо 0 либо 1
	private int isTalker;

	// Кол
	private static int COL = 4037;

	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: L2 COL DROP [state: activated]");
			if(COL_CHANCE > 80 || COL2_CHANCE > 50)
				_log.info("Event L2 Col drop: << W A R N I N G >> RATES IS TO HIGH!!!");
		}
		else
			_log.info("Loaded Event: L2 col drop Event [state: deactivated]");
	}


	public void onReload()
	{
		onLoad();
	}



	public void onShutdown()
	{
		if(isActive())
			_log.info("Loaded Event: L2 col drop Event [state: deactivated]");
		
	}


	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("col_drop", "off").equalsIgnoreCase("on");
	}

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
			ServerVariables.set("col_drop", "on");
			_log.info("Event 'L2 col_drop Event' started(Danila noob!!!).");
			Announcements.getInstance().announceByCustomMessage("scripts.events.coldrop.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'L2 col_drop Event' already started.");

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
			ServerVariables.unset("col_drop");
			_log.info("Event 'L2 col_drop' stopped(Danila Noob!!!).");
			Announcements.getInstance().announceByCustomMessage("scripts.events.coldrop.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'col_drop' not started(Danila noob).");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.coldrop.AnnounceEventStarted", null);
	}

	/**
	 * Обработчик смерти мобов, управляющий эвентовым дропом
	 */
	@Override
	public void onDie(L2Character cha, L2Character killer)
	{

        if(_active && cha.isMonster() && !cha.isRaid() && killer != null && killer.getPlayer() != null && Math.abs(cha.getLevel() - killer.getLevel()) < 10)
        {
            if(Rnd.chance(0.8))
            {
                L2ItemInstance item = ItemTable.getInstance().createItem("coldrop", COL, 1, killer.getPlayer());
                
                ((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
                _log.info("Игроку " + ((L2Player) killer).getName() + " упало 1 CoL");
                Announcements.getInstance().announceToAll("Игроку " + ((L2Player) killer).getName() + " повезло, он получил 1 COL за убийство моба!!!");
                Announcements.getInstance().announceToAll("Во время евента Col Drop у любого игрока есть шанс получить Coin of Luck с любого моба!!!");
                Announcements.getInstance().announceToAll("Получил кол проголосуй в l2top.ru !!!");
            }
        }
        
        }

}   

