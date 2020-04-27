package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author rage
 * @date 19.10.2010 14:00:04
 */
public class SoDObelisk extends DefaultAI
{
	private boolean _spawned = false;
	private static final int[] DOORS = { 12240003, 12240004, 12240011, 12240012, 12240019, 12240020 };

	public SoDObelisk(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!_spawned)
		{
			_spawned = true;
			L2GroupSpawn gs = SpawnTable.getInstance().getEventGroupSpawn("sod_obelisk_room", _thisActor.getSpawn().getInstance());
			gs.stopRespawn();
			gs.doSpawn();
			for(L2NpcInstance npc : gs.getAllSpawned())
				npc.addDamageHate(attacker, 0, 10000);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		for(L2Object object : L2World.getAroundObjects(_thisActor))
			if(object instanceof L2DoorInstance && Quest.contains(DOORS, ((L2DoorInstance) object).getDoorId()))
				((L2DoorInstance) object).openMe();

		_thisActor.broadcastPacket(new ExShowScreenMessage(3000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, false, 1800295));
	}
}
