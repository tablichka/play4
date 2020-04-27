package npc.maker;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 15.12.11 12:41
 */
public class ImmoSbossMaker extends ImmoBasicMaker
{
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = -2000;

	public ImmoSbossMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		SpawnDefine created_def = npc.getSpawnDefine();
		if(created_def == spawn_defines.get(0) && zone == 2 && tide == 0)
		{
			i_ai0 = 0;
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(deleted_def == spawn_defines.get(0) && npc.isDead() && zone == 2 && tide == 1)
		{
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_dispatcher_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010054, 0, room);
			}
		}
		else if(deleted_def == spawn_defines.get(0) && npc.isDead() && zone == 2 && tide == 0)
		{
			i_ai0 = 1;
			if(deleted_def.respawn != 0)
			{
				if(atomicIncrease(deleted_def, 1))
				{
					deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010067 && zone == 2 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
		}
		else if(eventId == 78010068 && zone == 2 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
		}
		else if(eventId == 78010070 && zone == 2 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null)
			{
				def0.sendScriptEvent(78010070, arg1, arg2);
			}
		}
		else if(eventId == 78010061 && i_ai0 == 0)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc((Long) arg1);
			if(c0 != null)
			{
				c0.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 78010062, room, null);
			}
		}
		else if(eventId == 1000 && zone == 2)
		{
			enabled = 0;
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null && def0.npc_count > 0)
			{
				def0.despawn();
			}
		}
	}
}