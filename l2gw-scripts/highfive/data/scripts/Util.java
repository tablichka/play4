import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.instancemanager.TownManager;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.Castle;
import ru.l2gw.gameserver.model.entity.SevenSigns;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.util.Location;

import java.util.Calendar;

public class Util extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void onLoad()
	{
		_log.info("Utilites Loaded");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	/**
	 * Перемещает за плату в аденах
	 *
	 * @param param x,y,z price
	 */
	public void Gatekeeper(String[] param)
	{
		if(param.length < 4)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;

		if(player == null)
			return;

		int price = Integer.parseInt(param[3]);

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if(day != 1 && day != 7 && (hour <= 12 || hour >= 22))
			price /= 2;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(player.isCombatFlagEquipped() && player.getActiveWeaponInstance() != null && player.getActiveWeaponInstance().isTerritoryWard())
		{
			player.sendPacket(Msg.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
			return;
		}

		if(player.getAdena() < price)
		{
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			return;
		}

		int x = Integer.parseInt(param[0]);
		int y = Integer.parseInt(param[1]);
		int z = Integer.parseInt(param[2]);

		// Нельзя телепортироваться в города, где идет осада
		// Узнаем, идет ли осада в ближайшем замке к точке телепортации
		Castle castle = TownManager.getInstance().getBuildingByCoord(x, y).getCastle();
		if(castle != null && castle.getSiege().isInProgress() && MapRegionTable.getInstance().getMapRegion(player.getX(), player.getY()) != MapRegionTable.getInstance().getMapRegion(x, y))
		{
			player.sendPacket(new SystemMessage(SystemMessage.YOU_CANNOT_TELEPORT_TO_A_VILLAGE_THAT_IS_IN_A_SIEGE));
			return;
		}

		Location pos = GeoEngine.findPointToStay(x, y, z, 50, 100, player.getReflection());

		if(price > 0)
			player.reduceAdena("Teleport", price, player.getLastNpc(), true);

		if(param.length > 4)
		{
			int refId = Integer.parseInt(param[4]);
			if(refId == 0)
				player.setStablePoint(null);
			player.teleToLocation(pos, refId);
		}
		else
			player.teleToLocation(pos);
	}

	public void DungeonGatekeeper(String[] param)
	{
		// Special method that provide move into special locations

		if(param.length < 1)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;


		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		String _loc = param[0];

		if(_loc.equals("CRUMA_TOWER"))
		{

			if(player.getLevel() > 56)
			{
				show("data/scripts/cruma-out-of-range.htm", player);
				return;
			}

			int x = 17776;
			int y = 113968;
			int z = -11672;

			Location pos = GeoEngine.findPointToStay(x, y, z, 50, 100, player.getReflection());
			player.teleToLocation(pos);

		}
	}

	public void SSGatekeeper(String[] param)
	{
		if(param.length < 4)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;
		int type = Integer.parseInt(param[3]);

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(type > 0)
		{
//			_log.info("Current SSQ Period: " + SevenSigns.getInstance().getCurrentPeriod());
//			_log.info("Player: " + player + " Cabal: " + SevenSigns.getInstance().getPlayerCabal(player));
			if(SevenSigns.getInstance().getCurrentPeriod() == SevenSigns.PERIOD_COMPETITION && SevenSigns.getInstance().getPlayerCabal(player) == SevenSigns.CABAL_NULL)
			{
				show("data/html/seven_signs/ssq_ziggurat_no1.htm", player);
				return;
			}

			if(SevenSigns.getInstance().getCurrentPeriod() == SevenSigns.PERIOD_SEAL_VALIDATION)
			{
//				_log.info("Current Seal Owner Avarice: " + SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_AVARICE));
//				_log.info("Current Seal Owner Gnosis: " + SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_GNOSIS));

				if(type == 1 && SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_AVARICE) != SevenSigns.getInstance().getPlayerCabal(player))
				{
					show("data/html/seven_signs/ssq_ziggurat_no2.htm", player);
					return;
				}
				if(type == 2 && SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_GNOSIS) != SevenSigns.getInstance().getPlayerCabal(player))
				{
					show("data/html/seven_signs/ssq_ziggurat_no2.htm", player);
					return;
				}

				if((type == 1 || type == 2) && SevenSigns.getInstance().getPlayerCabal(player) == SevenSigns.CABAL_NULL)
				{
					show("data/html/seven_signs/ssq_ziggurat_no1.htm", player);
					return;
				}
			}
		}

		player.teleToLocation(Integer.parseInt(param[0]), Integer.parseInt(param[1]), Integer.parseInt(param[2]));
	}

	/**
	 * Перемещает за определенный предмет
	 *
	 * @param param
	 */
	public void QuestGatekeeper(String[] param)
	{
		if(param.length < 5)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;

		int count = Integer.parseInt(param[3]);
		int item = Integer.parseInt(param[4]);
		L2ItemInstance ii = player.getInventory().getItemByItemId(item);

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(ii == null || ii.getCount() < count)
		{
			if(item == 8542)
				show("data/html/guide/guide_teleport_error001.htm", player);
			else
				player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
			return;
		}

		int x = Integer.parseInt(param[0]);
		int y = Integer.parseInt(param[1]);
		int z = Integer.parseInt(param[2]);

		Location pos = GeoEngine.findPointToStay(x, y, z, 20, 70, player.getReflection());

		player.destroyItem("QuestGK", ii.getObjectId(), count, player.getLastNpc(), true);

		player.teleToLocation(pos);
	}

	public void ReflectionGatekeeper(String[] param)
	{
		if(param.length < 5)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;
		player.setReflection(Integer.parseInt(param[4]));

		Gatekeeper(param);
	}

	/**
	 * Используется для телепортации за Newbie Token, проверяет уровень и передает
	 * параметры в QuestGatekeeper
	 */
	public void TokenJump(String[] param)
	{
		L2Player player = (L2Player) self;
		if(player.getLevel() <= 19)
			QuestGatekeeper(param);
		else
			show("Only for newbies", player);
	}

	public void NoblessTeleport()
	{
		L2Player player = (L2Player) self;
		if(player.isNoble() || Config.ALLOW_NOBLE_TP_TO_ALL)
			show("data/scripts/noble.htm", player);
		else
			show("data/scripts/nobleteleporter-no.htm", player);
	}

	public void PayPage(String[] param)
	{
		if(param.length < 2)
			throw new IllegalArgumentException();

		L2Player player = (L2Player) self;
		String page = param[0];
		int item = Integer.parseInt(param[1]);
		int price = Integer.parseInt(param[2]);

		if(getItemCount(player, item) < price)
		{
			if(item == 57)
				player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			else
				player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
			return;
		}

		removeItem(player, item, price);
		show(page, player);
	}
}