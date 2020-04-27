package events.Christmas;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.Calendar;

/**
 * User: Death
 * Date: 20/12/2007
 * Time: 23:25:41
 */
public class NewYearTimer implements ScriptFile
{
	private static NewYearTimer instance;

	public static NewYearTimer getInstance()
	{
		if(instance == null)
			new NewYearTimer();
		return instance;
	}

	public NewYearTimer()
	{
		if(instance != null)
			return;

		instance = this;

		if(!isActive())
			return;

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("С Новым, " + c.get(Calendar.YEAR) + ", Годом!!!"), getDelay(c));
		c.add(Calendar.SECOND, -1);
		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("1"), getDelay(c));
		c.add(Calendar.SECOND, -1);
		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("2"), getDelay(c));
		c.add(Calendar.SECOND, -1);
		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("3"), getDelay(c));
		c.add(Calendar.SECOND, -1);
		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("4"), getDelay(c));
		c.add(Calendar.SECOND, -1);
		ThreadPoolManager.getInstance().scheduleGeneral(new NewYearAnnouncer("5"), getDelay(c));
	}

	private long getDelay(Calendar c)
	{
		return c.getTime().getTime() - System.currentTimeMillis();
	}

	/**
	 * Вызывается при загрузке классов скриптов
	 */
	public void onLoad()
	{}

	/**
	 * Вызывается при перезагрузке
	 * После перезагрузки onLoad() вызывается автоматически
	 */
	public void onReload()
	{}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("Christmas", "off").equalsIgnoreCase("on");
	}

	/**
	 * Вызывается при выключении сервера
	 */
	public void onShutdown()
	{}

	private class NewYearAnnouncer implements Runnable
	{
		private final String message;

		private NewYearAnnouncer(String message)
		{
			this.message = message;
		}

		public void run()
		{
			Announcements.getInstance().announceToAll(message);

			// Через жопу сделано, но не суть важно :)
			if(message.length() == 1)
				return;

			for(L2Player player : L2ObjectsStorage.getAllPlayers())
			{
				L2Skill skill = SkillTable.getInstance().getInfo(3266, 1);
				MagicSkillUse msu = new MagicSkillUse(player, player, 3266, 1, skill.getHitTime(), 0);
				player.broadcastPacket(msu);
			}

			instance = null;
			new NewYearTimer();
		}
	}
}