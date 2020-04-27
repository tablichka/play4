package ai;

import events.SavingSanta.SavingSanta;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

import java.util.Calendar;

/**
 * @author rage
 * @date 29.11.2010 18:31:04
 * АИ для новогоднего эвента Saving Santa
 * http://www.lineage2.com/archive/2008/12/saving_santa_ev.html
 */
public class BRSantaManager extends DefaultAI
{
	private static int _interval = 0;
	private static int _gifts = 0;
	private static int _online = 0;
	private static Location _turkeyLoc;
	private static final int _jeckpotMon = 1;
	private static final int _jeckpotDay = 1;
	private static final GArray<Integer> _jeckpotPlayers = new GArray<Integer>();

	public BRSantaManager(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		ServerVariables.set("br_xmas_event", 0);
		ServerVariables.set("br_xmas_event_pc", 0);
		_turkeyLoc = new Location(getInt("turkey_x"), getInt("turkey_y"), getInt("turkey_z"));
		addTimer(1225, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1225)
		{
			addTimer(1225, 60000);
			_interval++;

			if(_interval == 2 || _interval % 240 == 0)
			{
				ServerVariables.set("br_xmas_event", 0);
				addTimer(1226, 60000);
			}
		}
		else if(timerId == 1226)
		{
			ServerVariables.set("br_xmas_event", 1);
			_gifts = 0;
			L2NpcInstance turkey = spawnNpc(100, _turkeyLoc, null);
			turkey.c_ai0 = _thisActor.getStoredId();
			turkey.i_ai0 = 30;
		}
		else if(timerId == 1227)
		{
			if(ServerVariables.getInt("br_xmas_event") == 2)
			{
				findRandomUser();
				addTimer(1227, 20000);
			}
		}
		else if(timerId == 2227)
			spawnNpc(105, new Location(_turkeyLoc.getX() - 100, _turkeyLoc.getY() - 100, _turkeyLoc.getZ() + 100, 47000), "param1=1");
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1)
		{
			ServerVariables.set("br_xmas_event", 2);
			addTimer(2227, 5000);
			spawnNpc(105, new Location(81505, 141709, -2732, Rnd.get(65535)), "param1=0");
			_online = Math.max(1, (int)((L2ObjectsStorage.getAllPlayersCount() - L2ObjectsStorage.getAllOfflineCount()) * 0.80));
			addTimer(1227, 20000);
		}
	}

	private void findRandomUser()
	{
		GArray<L2Player> players = getOnlinePlayers();

		if(players.size() > 0)
		{
			L2Player player = players.get(Rnd.get(players.size()));
			L2NpcInstance santa = spawnNpc(104, GeoEngine.findPointToStay(player.getX(), player.getY(), player.getZ(), 60, 90, player.getReflection()), null);
			santa.i_ai0 = player.getObjectId();
			rewardPlayer(player);
		}
	}

	private void rewardPlayer(L2Player player)
	{
		if(player.getInventory().slotsLeft() >= 5)
		{
			Calendar calend = Calendar.getInstance();
			if(calend.get(Calendar.MONTH) == _jeckpotMon - 1 && calend.get(Calendar.DAY_OF_MONTH) == _jeckpotDay)
			{
				player.addItem("br_xmas_event", Rnd.chance(SavingSanta.JACKPOT_CHANCE) ? 20102 : 20101, 1, _thisActor, true);
				if(!_jeckpotPlayers.contains(player.getObjectId()))
					player.addItem("br_xmas_event", 20092, 1, _thisActor, true);
			}
			else
				player.addItem("br_xmas_event", 20101, 1, _thisActor, true);

			_gifts++;
			if(_gifts >= _online)
				ServerVariables.set("br_xmas_event", 0);
		}
	}

	private GArray<L2Player> getOnlinePlayers()
	{
		GArray<L2Player> players = new GArray<L2Player>();
		for(L2Player player : L2ObjectsStorage.getAllPlayers())
			if(player.isInCombat() && !player.isInZonePeace() && !player.isInOlympiadMode() && player.getReflection() == 0 && !player.isInOfflineMode())
				players.add(player);

		return players;
	}

	private L2NpcInstance spawnNpc(int npcId, Location loc, String ai_params)
	{
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(npcId));
			if(ai_params != null)
				spawn.setAIParameters(ai_params);
			spawn.setRespawnDelay(0);
			spawn.setAmount(1);
			spawn.setLoc(loc);
			return spawn.spawnOne();
		}
		catch(Exception e)
		{
			_log.warn(_thisActor + " can't spawn turkey: " + e);
			e.printStackTrace();
		}

		return null;
	}
}
