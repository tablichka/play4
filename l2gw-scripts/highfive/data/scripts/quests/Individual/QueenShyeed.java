package quests.Individual;

import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.Date;

/**
 * @author кфпу
 * @date 18.08.2010 11:03:20
 */
public class QueenShyeed extends Quest
{
	private L2Spawn _queenSpawn;
	private String _state;
	private final int QUEEN_ID = 25671;
	private L2Zone _zone;
	private L2Skill _buff, _mobBuff, _debuff;

	public void onLoad()
	{
		_state = loadGlobalQuestVar("state");

		if(_state == null || _state.isEmpty())
			setSate("alive");

		long time = 0;
		try
		{
			String str = loadGlobalQuestVar("time");
			time = Long.parseLong(str);
		}
		catch(Exception e)
		{
		}

		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(QUEEN_ID);
			_queenSpawn = new L2Spawn(template);
			_queenSpawn.setAmount(1);
			_queenSpawn.setLoc(new Location(79634, -55428, -6104, -1));
			_queenSpawn.stopRespawn();
		}
		catch(Exception e)
		{
			_log.info(this + " can't create spawn: " + e);
			e.printStackTrace();
		}

		if(_state.equals("alive"))
		{
			spawnQueen();
			if(time > System.currentTimeMillis())
			{
				_log.info(this + " set despawn timer: " + new Date(time));
				startQuestTimer("despawn_queen", time - System.currentTimeMillis(), null, null, true);
			}
			else
			{
				long t = 4 * 60 * 60000 + Rnd.get(2 * 60 * 60000);
				_log.info(this + " set despawn timer: " + new Date(t + System.currentTimeMillis()));
				saveGlobalQuestVar("time", String.valueOf(System.currentTimeMillis() + t));
				startQuestTimer("despawn_queen", t, null, null, true);
			}
		}
		else if(_state.equals("hide") || _state.equals("dead"))
		{
			if(time < System.currentTimeMillis())
			{
				spawnQueen();
				long t = 4 * 60 * 60000 + Rnd.get(2 * 60 * 60000);
				_log.info(this + " set despawn timer: " + new Date(t + System.currentTimeMillis()));
				saveGlobalQuestVar("time", String.valueOf(System.currentTimeMillis() + t));
				startQuestTimer("despawn_queen", t, null, null, true);
			}
			else
			{
				_log.info("QueenShyeed: set spawn timer: " + new Date(time));
				startQuestTimer("spawn_queen", time - System.currentTimeMillis(), null, null, true);
				SpawnTable.getInstance().startEventSpawn("shyeed_room_mobs");
			}
		}

		_zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.altered, 5600);
		if(_zone == null)
			_log.warn(this + " no debuff zone found!");

		_mobBuff = SkillTable.getInstance().getInfo(6170, 1);
		_buff = SkillTable.getInstance().getInfo(6171, 1);
		_debuff = SkillTable.getInstance().getInfo(6169, 1);

		startQuestTimer("buff_timer", 60000, null, null, true);
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public QueenShyeed()
	{
		super(21006, "QueenShyeed", "Queen Shyeed Individual", true);
		addKillId(QUEEN_ID);
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.equals("despawn_queen"))
		{
			_log.info(this + " despawn Queen");
			setSate("hide");
			_queenSpawn.stopRespawn();
			_queenSpawn.despawnAll();
			SpawnTable.getInstance().startEventSpawn("shyeed_room_mobs");
			long t = 4 * 60 * 60000 + Rnd.get(4 * 60 * 60000);
			saveGlobalQuestVar("time", String.valueOf(System.currentTimeMillis() + t));
			_log.info(this + " set spawn timer: " + new Date(System.currentTimeMillis() + t));
			cancelQuestTimer("despawn_queen", null, null);
			cancelQuestTimer("spawn_queen", null, null);
			startQuestTimer("spawn_queen", t, null, null, true);
		}
		else if(event.equals("spawn_queen"))
		{
			_log.info(this + " spawn Queen Shyeed");
			spawnQueen();
			long t = 4 * 60 * 60000 + Rnd.get(2 * 60 * 60000);
			_log.info(this + " set despawn timer: " + new Date(t + System.currentTimeMillis()));
			saveGlobalQuestVar("time", String.valueOf(System.currentTimeMillis() + t));
			cancelQuestTimer("spawn_queen", null, null);
			cancelQuestTimer("despawn_queen", null, null);
			startQuestTimer("despawn_queen", t, null, null, true);
		}
		else if(event.equals("buff_timer"))
		{
			if(_zone != null)
			{
				if(_state.equals("alive"))
					for(L2Character cha : _zone.getCharacters())
					{
						if(cha.isMonster())
							_mobBuff.applyEffects(cha, cha, false);
						else if(cha.isPlayable())
							_debuff.applyEffects(cha, cha, false);
					}
				else if(_state.equals("dead"))
					for(L2Player pl : _zone.getPlayers())
						_buff.applyEffects(pl, pl, false);
			}
			cancelQuestTimer("buff_timer", null, null);
			startQuestTimer("buff_timer", 60500, null, null, true);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		cancelQuestTimer("despawn_queen", null, null);
		_queenSpawn.stopRespawn();
		setSate("dead");
		SpawnTable.getInstance().startEventSpawn("shyeed_room_mobs");
		long t = 43200000 + Rnd.get(86400000);
		saveGlobalQuestVar("time", String.valueOf(System.currentTimeMillis() + t));
		_log.info(this + " killed, set spawn timer: " + new Date(System.currentTimeMillis() + t));
		cancelQuestTimer("spawn_queen", null, null);
		cancelQuestTimer("despawn_queen", null, null);
		startQuestTimer("spawn_queen", t, null, null, true);
	}

	private void spawnQueen()
	{
		setSate("alive");
		SpawnTable.getInstance().stopEventSpawn("shyeed_room_mobs", true);
		_queenSpawn.spawnOne();
		_queenSpawn.stopRespawn();
	}

	private void setSate(String state)
	{
		_state = state;
		saveGlobalQuestVar("state", _state);
	}
}