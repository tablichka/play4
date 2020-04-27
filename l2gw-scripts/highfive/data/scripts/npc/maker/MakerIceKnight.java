package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 27.09.11 1:07
 */
public class MakerIceKnight extends InzoneMaker
{
	public int debug_mode = 0;
	public String MAKER_knight_leader = "schuttgart29_2314_102m2";
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public int FLAG_SPAWN = 231400;
	public int FLAG_NO_SPAWN = 231401;
	private long c_ai0;

	public MakerIceKnight(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		i_ai0 = 0;
		i_ai1 = 0;
		i_ai2 = 0;
		c_ai0 = 0;
	}

	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		super.onInstanceZoneEvent(inst, eventId);
		if(eventId == 1)
		{
			enabled = 1;
			for(SpawnDefine sd : spawn_defines)
			{
				if(debug > 0)
					_log.info(this + " onInstanceZoneEvent: " + sd);
				sd.setReflection(inst.getReflection());
				if(maximum_npc >= npc_count + sd.total && atomicIncrease(sd, 1))
					sd.spawn(1, 0, 0);
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1000)
		{
		}
		else if(eventId == 1001)
		{
			for(SpawnDefine sd : spawn_defines)
			{
				if(debug > 0)
					_log.info(this + " onScriptEvent: " + sd);
				sd.setReflection(reflectionId);
				if(maximum_npc >= npc_count + sd.total && atomicIncrease(sd, 1))
					sd.spawn(1, 0, 0);
			}
		}
		else if(eventId == 23140013)
		{
			c_ai0 = (Long) arg1;
		}
		else if(eventId == 23140015)
		{
			if((Integer) arg1 == 1)
			{
				if(i_ai0 == 1 || i_ai0 == 4)
				{
					int i0 = Rnd.get(8);
					SpawnDefine def0 = spawn_defines.get(i0);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}
				}
				else if(i_ai0 == 2 || i_ai0 == 3)
				{
					int i0 = Rnd.get(16);
					SpawnDefine def0 = spawn_defines.get(i0);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}
				}
			}
			else if((Integer) arg1 == 2)
			{
				if(i_ai0 == 1 || i_ai0 == 4)
				{
					int i0 = Rnd.get(4);
					int i1 = Rnd.get(4) + 4;
					SpawnDefine def0 = spawn_defines.get(i0);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}

					def0 = spawn_defines.get(i1);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}
				}
				else if(i_ai0 == 2 || i_ai0 == 3)
				{
					int i0 = Rnd.get(8);
					int i1 = Rnd.get(8) + 8;
					SpawnDefine def0 = spawn_defines.get(i0);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}
					def0 = spawn_defines.get(i1);
					if(def0 != null)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
						if(c0 != null && c0.isDead())
						{
							def0.sendScriptEvent(23140045, c_ai0, 0);
						}
						else
						{
							def0.sendScriptEvent(23140045, 0L, 0);
						}
					}
				}
			}
			else if((Integer) arg1 == 4)
			{
				int i0 = Rnd.get(4);
				int i1 = Rnd.get(4) + 4;
				int i2 = Rnd.get(4) + 8;
				int i3 = Rnd.get(4) + 12;
				SpawnDefine def0 = spawn_defines.get(i0);
				if(def0 != null)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
					if(c0 != null && c0.isDead())
					{
						def0.sendScriptEvent(23140045, c_ai0, 0);
					}
					else
					{
						def0.sendScriptEvent(23140045, 0L, 0);
					}
				}
				def0 = spawn_defines.get(i1);
				if(def0 != null)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
					if(c0 != null && c0.isDead())
					{
						def0.sendScriptEvent(23140045, c_ai0, 0);
					}
					else
					{
						def0.sendScriptEvent(23140045, 0L, 0);
					}
				}
				def0 = spawn_defines.get(i2);
				if(def0 != null)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
					if(c0 != null && c0.isDead())
					{
						def0.sendScriptEvent(23140045, c_ai0, 0);
					}
					else
					{
						def0.sendScriptEvent(23140045, 0L, 0);
					}
				}
				def0 = spawn_defines.get(i3);
				if(def0 != null)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(c_ai0);
					if(c0 != null && c0.isDead())
					{
						def0.sendScriptEvent(23140045, c_ai0, 0);
					}
					else
					{
						def0.sendScriptEvent(23140045, 0L, 0);
					}
				}
			}
		}
		if(eventId == 23140001)
		{
			i_ai0 = 1;
		}
		else if(eventId == 23140002)
		{
			i_ai0 = 2;
		}
		else if(eventId == 23140003)
		{
			i_ai0 = 3;
		}
		else if(eventId == 23140005)
		{
			i_ai0 = 4;
		}
		else if(eventId == 23140006)
		{
			i_ai0 = 5;
		}
		else if(eventId == FLAG_SPAWN)
		{
			i_ai2 = eventId;
		}
		else if(eventId == FLAG_NO_SPAWN)
		{
			i_ai2 = (Integer) arg1;
		}
		else if(eventId == 23140054)
		{
			Instance inst = InstanceManager.getInstance().getInstanceByReflection(reflectionId);
			if(inst != null)
			{
				DefaultMaker maker0 = inst.getMaker(MAKER_knight_leader);
				maker0.onScriptEvent(1001, 0, 0);
			}
		}
		if(eventId == 23140056)
		{
			int i0 = 0;
			switch((Integer) arg1)
			{
				case 11:
					i0 = 0;
					break;
				case 12:
					i0 = 1;
					break;
				case 13:
					i0 = 2;
					break;
				case 14:
					i0 = 3;
					break;
				case 15:
					i0 = 4;
					break;
				case 16:
					i0 = 5;
					break;
				case 17:
					i0 = 6;
					break;
				case 18:
					i0 = 7;
					break;
				case 21:
					i0 = 8;
					break;
				case 22:
					i0 = 9;
					break;
				case 23:
					i0 = 10;
					break;
				case 24:
					i0 = 11;
					break;
				case 25:
					i0 = 12;
					break;
				case 26:
					i0 = 13;
					break;
				case 27:
					i0 = 14;
					break;
				case 28:
					i0 = 15;
					break;
			}

			SpawnDefine def0 = spawn_defines.get(i0);
			if(atomicIncrease(def0, 1))
			{
				def0.spawn(1, (Integer) arg2, 0);
			}
		}
		else if(eventId == 23140063)
		{
			//DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(myself.name);
			//if( maker0 != null )
			//{
			//	maker0.onScriptEvent(1000, 0, 0);
			//}
			i_ai0 = 0;
			i_ai1 = 0;
			i_ai2 = 0;
		}
		else if(eventId == 23140069)
		{
			if(i_ai0 != 5)
			{
				if(i_ai0 == 2 && i_ai2 == FLAG_SPAWN)
				{
					i_ai1++;
					if(i_ai1 == 10)
					{
						Instance inst = InstanceManager.getInstance().getInstanceByReflection(reflectionId);
						if(inst != null)
						{
							DefaultMaker maker0 = inst.getMaker(MAKER_controller);
							maker0.onScriptEvent(23140053, 0, 0);
						}
					}
				}
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(i_ai0 != 5)
		{
			if(i_ai0 == 2 && i_ai2 == FLAG_SPAWN)
			{
				i_ai1++;
				if(i_ai1 == 10)
				{
					Instance inst = InstanceManager.getInstance().getInstanceByReflection(reflectionId);
					if(inst != null)
					{
						DefaultMaker maker0 = inst.getMaker(MAKER_controller);
						maker0.onScriptEvent(23140053, 0, 0);
					}
				}
			}
			if(i_ai2 == FLAG_SPAWN)
			{
				if(npc.getSpawnDefine().npc_count == 0)
				{
					if(atomicIncrease(npc.getSpawnDefine(), 1))
					{
						npc.getSpawnDefine().respawn(npc, 3, 0);
					}
				}
			}
		}
	}
}
