package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;

/**<hr>
 * AI моба <strong>Dark Water Dragon</strong> npc_id=22267 для Isle of Prayer.<hr>
 * <li>после первой атаки, спавнит миньон-мобов <strong>Shade</strong> npc_id=22268, 22269 в количестве 5 штук.
 * <li>при потере половины HP, спавнит еще 5 миньон-мобов <strong>Shade</strong>
 * <li>после смерти спавнит моба <strong>Fafurion Kindred</strong> npc_id=18482.
 * <li>не используют функцию Random Walk, если были заспавнены "миньоны"
 * @author SYS
 * @edited HellSinger
 * @since 2008-12-19
 * @version 0.1b
 */
public class DarkWaterDragon extends Fighter
{
	private boolean _firstWaveIsSpawned = false;
	private boolean _secondWaveIsSpawned = false;
	private static final int SHADE1 = 22268;
	private static final int SHADE2 = 22269;
	private static final int FAFURION = 18482;

	private L2Character _attacker;

	public DarkWaterDragon(L2Character actor)
	{
		super(actor);
	}

	protected void spawnMob(L2Character attacker, int mobId)
	{
		try
		{
			Location pos = GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 100, 120, _thisActor.getReflection());
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(mobId));
			spawn.setLoc(pos);
			L2NpcInstance npc = spawn.doSpawn(true);
			npc.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, attacker, 100);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void spawnShade(L2Character attacker)
	{
		if(_firstWaveIsSpawned)
		{
			spawnMob(attacker, SHADE2);
			spawnMob(attacker, SHADE1);
			spawnMob(attacker, SHADE2);
			spawnMob(attacker, SHADE1);
			spawnMob(attacker, SHADE2);
		}
		else
		{
			spawnMob(attacker, SHADE1);
			spawnMob(attacker, SHADE2);
			spawnMob(attacker, SHADE1);
			spawnMob(attacker, SHADE2);
			spawnMob(attacker, SHADE1);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null)
			_attacker = attacker;
		if(!_firstWaveIsSpawned)
		{
			if(_attacker.isSummon() || _attacker.isPet())
				_attacker = _attacker.getPet();
			//Спавнит первую волну из пяти Shade при атаке на Dark Water Dragon
			spawnShade(attacker);
			_firstWaveIsSpawned = true;
		}
		else if(!_secondWaveIsSpawned && _thisActor.getCurrentHp() < (_thisActor.getMaxHp() / 2))
		{
			//Спавнит вторую волну из пяти Shade если у Dark Water Dragon меньше половины HP
			spawnShade(attacker);
			_secondWaveIsSpawned = true;
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_firstWaveIsSpawned = false;
		_secondWaveIsSpawned = false;

		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(FAFURION));
			spawn.setLoc(_thisActor.getSpawnedLoc());
			spawn.doSpawn(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		super.onEvtDead(killer);
	}

	@Override
	protected boolean randomWalk()
	{
		return !_firstWaveIsSpawned;
	}
}