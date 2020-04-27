package services;

import javolution.util.FastMap;
import ru.l2gw.extensions.listeners.events.MethodEvent;
import ru.l2gw.extensions.listeners.reduceHp.ReduceCurrentHpListener;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;
import ru.l2gw.gameserver.tables.ItemTable;

import java.util.concurrent.ScheduledFuture;

public class AutoHeal extends Functions implements IVoicedCommandHandler, ScriptFile
{
	private static final String[] _commandList = new String[]{"acpon", "acpoff"};

	//Heal Potions
	private static final int GREAT_HEAL_POTION = 1539;
	private static final int[] QUICK_HEAL_POTION = { 1540, 435 };

	//Mana Potions
	private static final int MANA_POTION = 1539;
	private static final int[] MANA_DRUG_POTION = { 1540, 435 };

	//CP Potions
	private static final int[] CP_POTION = { 5591, 50 };
	private static final int[] GREAT_CP_POTION = { 5592, 200 };

	private static final String ollyMsg = "scripts.services.AutoHeal.ollyMsg";
	private static final String notPotionsMsg = "scripts.services.AutoHeal.notPotionsMsg";
	private static final String healOn = "scripts.services.AutoHeal.healOn";
	private static final String healOff = "scripts.services.AutoHeal.healOff";

	private static HPListener _currentHpListener;

	private static FastMap<Integer, AHPlayer> _players = new FastMap<Integer, AHPlayer>();

	public void onLoad()
	{
		_log.info("Loaded Service: Auto Heal loaded.");
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
		if(Config.SERVICES_AUTO_HEAL_ACTIVE)
			_currentHpListener = new HPListener();
	}

	public void onReload()
	{
		if(Config.SERVICES_AUTO_HEAL_ACTIVE)
		{
			for(AHPlayer pl : _players.values())
				pl.player.removeMethodInvokeListener(_currentHpListener);

			_currentHpListener = new HPListener();

			for(AHPlayer pl : _players.values())
				pl.player.addMethodInvokeListener(_currentHpListener);
		}
		else
			_players.clear();
	}

	public void onShutdown()
	{}

	public static void OnPlayerEnter(L2Player player)
	{
		if(player.getVarB("AutoHealActive") && Config.SERVICES_AUTO_HEAL_ACTIVE)
		{
			AHPlayer pl = _players.put(player.getObjectId(), new AHPlayer(player));
			if(pl == null)
				return;

			checkRecovery(pl, 0, false);
			checkRecovery(pl, 0, true);
		}
	}

	public static void changeAutoHeal(L2Player player, boolean active)
	{
		if(!Config.SERVICES_AUTO_HEAL_ACTIVE)
			return;

		if(active)
		{
			CustomMessage cm = new CustomMessage(healOn, player);
			player.sendPacket(new ExShowScreenMessage(cm.toString(), 10000, ScreenMessageAlign.TOP_CENTER, true, true));
			player.setVar("AutoHealActive", "1");
			AHPlayer pl = _players.put(player.getObjectId(), new AHPlayer(player));
			if(pl == null)
				return;

			checkRecovery(pl, 0, false);
			checkRecovery(pl, 0, true);
		}
		else
		{
			CustomMessage cm = new CustomMessage(healOff, player);
			player.sendPacket(new ExShowScreenMessage(cm.toString(), 10000, ScreenMessageAlign.TOP_CENTER, true, true));
			player.unsetVar("AutoHealActive");
			_players.remove(player.getObjectId());
		}
	}

	private class HPListener extends ReduceCurrentHpListener
	{
		@Override
		public void onReduceCurrentHp(L2Character actor, double damage, L2Character attacker, boolean directHp, MethodEvent event)
		{
			L2Player player = actor.getPlayer();
			if(player == null)
				return;

			AHPlayer pl = _players.get(player.getObjectId());
			if(pl == null)
				return;

			if(pl.hpRecoveryTask == null)
				checkRecovery(pl, damage, false);
			if(pl.cpRecoveryTask == null)
				checkRecovery(pl, damage, true);
		}
	}

	private static class RecoverTask implements Runnable
	{
		private final AHPlayer _player;
		private final boolean _isCpRecover;

		public RecoverTask(AHPlayer player, boolean isCpRecover)
		{
			_player = player;
			_isCpRecover = isCpRecover;
		}

		@Override
		public void run()
		{
			if(_isCpRecover)
				checkRecovery(_player, 0, _isCpRecover);
			else
				checkRecovery(_player, 0, _isCpRecover);
		}
	}

	private static void checkRecovery(AHPlayer pl, double damage, boolean isCpRecover)
	{
		if(pl == null)
			return;

		L2Player player = pl.player;
		if(player == null || player.isDead())
		{
			pl.stopAllTasks();
			return;
		}

		if(!Config.SERVICES_AUTO_HEAL_ACTIVE)
		{
			_players.remove(player.getObjectId());
			player.unsetVar("AutoHealActive");
			pl.stopAllTasks();
			return;
		}

		if(!player.getVarB("AutoHealActive") || !player.isOnline())
		{
			_players.remove(player.getObjectId());
			pl.stopAllTasks();
			return;
		}

		if(player.isInOlympiadMode())
		{
			CustomMessage cm = new CustomMessage(healOff, player);
			player.sendPacket(new ExShowScreenMessage(cm.toString(), 3000, ScreenMessageAlign.TOP_CENTER, true, true));
			_players.remove(player.getObjectId());
			player.unsetVar("AutoHealActive");
			sendMessage(new CustomMessage(ollyMsg, player), player);
			pl.stopAllTasks();
			return;
		}

		if(!isCpRecover)
		{
			int maxHp = player.getMaxHp();
			double newHp = player.getCurrentHp() - damage;
			if(!player.isPotionsDisabled() && newHp < maxHp * 0.95)
			{
				L2ItemInstance item = null;
				L2ItemInstance quickHpItem = player.getInventory().getItemByItemId(QUICK_HEAL_POTION[0]);
				L2ItemInstance greatHpItem = player.getInventory().getItemByItemId(GREAT_HEAL_POTION);
				if(quickHpItem != null && (newHp <= maxHp * 0.3 || greatHpItem == null))
				{
					int power = QUICK_HEAL_POTION[1];
					if((power <= (maxHp - newHp) * 1.1) || power >= maxHp && (newHp <= maxHp * 0.3))
						item = quickHpItem;
				}
				else if(greatHpItem != null)
					item = greatHpItem;

				pl.haveHpPotions = quickHpItem != null || greatHpItem != null;

				if(item != null)
				{
					ItemTable.useHandler(player, item);
					pl.hpRecoveryTask = ThreadPoolManager.getInstance().scheduleGeneral(new RecoverTask(pl, false), item.getItem().getReuseDelay() + 1000);
				}
				else
					pl.stopHpRecoveryTask();
			}
			else
				pl.stopHpRecoveryTask();
		}
		else
		{
			int maxCp = player.getMaxCp();
			double newCp = player.getCurrentCp() - damage;
			if(!player.isPotionsDisabled() && newCp < maxCp * 0.95)
			{
				int power = GREAT_CP_POTION[1];
				L2ItemInstance item = player.getInventory().getItemByItemId(GREAT_CP_POTION[0]);
				if(item == null)
				{
					power = CP_POTION[1];
					item = player.getInventory().getItemByItemId(CP_POTION[0]);
				}

				pl.haveCpPotions = item != null;

				if(item != null && (power > (maxCp - newCp) * 1.1) || power >= maxCp && (newCp <= maxCp * 0.3))
					item = null;

				if(item != null)
				{
					ItemTable.useHandler(player, item);
					pl.cpRecoveryTask = ThreadPoolManager.getInstance().scheduleGeneral(new RecoverTask(pl, true), item.getItem().getReuseDelay() + 1000);
				}
				else
					pl.stopCpRecoveryTask();
			}
			else
				pl.stopCpRecoveryTask();
		}

		if(!pl.haveHpPotions && !pl.haveCpPotions)
		{
			CustomMessage cm = new CustomMessage(healOff, player);
			player.sendPacket(new ExShowScreenMessage(cm.toString(), 3000, ScreenMessageAlign.TOP_CENTER, true, true));
			_players.remove(player.getObjectId());
			player.unsetVar("AutoHealActive");
			sendMessage(new CustomMessage(notPotionsMsg, player), player);
			pl.stopAllTasks();
		}
	}

	private static class AHPlayer
	{
		public L2Player player;
		public ScheduledFuture<?> hpRecoveryTask;
		public ScheduledFuture<?> cpRecoveryTask;
		public boolean haveHpPotions = false;
		public boolean haveCpPotions = false;
		
		public AHPlayer(L2Player pl)
		{
			player = pl;
			pl.addMethodInvokeListener(_currentHpListener);
		}

		public void stopHpRecoveryTask()
		{
			if(hpRecoveryTask != null)
			{
				hpRecoveryTask.cancel(false);
				hpRecoveryTask = null;
			}
		}

		public void stopCpRecoveryTask()
		{
			if(cpRecoveryTask != null)
			{
				cpRecoveryTask.cancel(false);
				cpRecoveryTask = null;
			}
		}

		public void stopAllTasks()
		{
			stopHpRecoveryTask();
			stopCpRecoveryTask();
		}
	}

	@Override
	public boolean useVoicedCommand(String command, L2Player player, String args)
	{
		if(command.equals("acpon"))
			changeAutoHeal(player, true);
		if(command.equals("acpoff"))
			changeAutoHeal(player, false);

		return true;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}