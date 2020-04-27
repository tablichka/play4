package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;

/**
 * @author Diamond
 */
public class WailingOfSplendor extends RndTeleportFighter
{
	private boolean _spawned;

	public WailingOfSplendor(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		try
		{
			if(!_spawned && Rnd.chance(25))
			{
				_spawned = true;
				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(21540));
				spawn.setLoc(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 100, 150, _thisActor.getReflection()));
				L2NpcInstance npc = spawn.doSpawn(true);
				npc.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, attacker, 100, skill);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_spawned = false;
		super.onEvtDead(killer);
	}
}