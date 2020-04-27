package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 15.12.11 17:02
 */
public class ImmoMobMaker extends ImmoBasicMaker
{
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 100;
	public int supply_lv0_2nd_def = 5;
	public int supply_lv1_2nd_def = 10;
	public int supply_lv0 = 10;
	public int supply_lv1 = 5;
	public int TM_mob_increase = 78002;
	public int TIME_mob_increase = 240;
	public int supply_increase = 3;
	public int supply_decrease = 1;
	public String z2_a_sb01_mob_maker = "rumwarsha14_1424_a_sb1m2";
	public String z2_a_sb02_mob_maker = "rumwarsha14_1424_a_sb2m2";
	public String z2_a_sb03_mob_maker = "rumwarsha14_1424_a_sb3m2";
	public String z2_a_sb04_mob_maker = "rumwarsha14_1424_a_sb4m2";
	public String z2_a_tm01_mob_maker = "rumwarsha14_1424_a_t1m2";
	public String z2_a_tm02_mob_maker = "rumwarsha14_1424_a_t2m2";
	public String z2_a_tm03_mob_maker = "rumwarsha14_1424_a_t3m2";
	public String z2_a_tm04_mob_maker = "rumwarsha14_1424_a_t4m2";
	public String z2_d_sb01_mob_maker = "rumwarsha14_1424_d_sb1m2";
	public String z2_d_sb02_mob_maker = "rumwarsha14_1424_d_sb2m2";
	public String z2_d_sb03_mob_maker = "rumwarsha14_1424_d_sb3m2";
	public String z2_d_sb04_mob_maker = "rumwarsha14_1424_d_sb4m2";
	public String z2_d_tm01_mob_maker = "rumwarsha14_1424_d_t1m2";
	public String z2_d_tm02_mob_maker = "rumwarsha14_1424_d_t2m2";
	public String z2_d_tm03_mob_maker = "rumwarsha14_1424_d_t3m2";
	public String z2_d_tm04_mob_maker = "rumwarsha14_1424_d_t4m2";
	public String z3_a_tm01_mob_maker = "rumwarsha15_1424_a_t1m2";
	public String z3_a_tm02_mob_maker = "rumwarsha15_1424_a_t2m2";
	public String z3_a_tm03_mob_maker = "rumwarsha15_1424_a_t3m2";
	public String z3_a_tm04_mob_maker = "rumwarsha15_1424_a_t4m2";
	public String z3_a_tm05_mob_maker = "rumwarsha15_1424_a_t5m2";
	public String z3_a_tm06_mob_maker = "rumwarsha15_1424_a_t6m2";
	public String z3_d_tm01_mob_maker = "rumwarsha15_1424_d_t1m2";
	public String z3_d_tm02_mob_maker = "rumwarsha15_1424_d_t2m2";
	public String z3_d_tm03_mob_maker = "rumwarsha15_1424_d_t3m2";
	public String z3_d_tm04_mob_maker = "rumwarsha15_1424_d_t4m2";
	public String z3_d_tm05_mob_maker = "rumwarsha15_1424_d_t5m2";
	public String z3_d_tm06_mob_maker = "rumwarsha15_1424_d_t6m2";

	public ImmoMobMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
		if(enabled == 1)
		{
			if(zone == 2 && (room == 212 || room == 222 || room == 232 || room == 242))
			{
				i_ai0 = supply_lv0_2nd_def;
			}
			else
			{
				i_ai0 = supply_lv0;
			}
			i_ai1 = TACT_AGGRESIVE;
			i_ai3 = 0;
			i_ai4 = 0;
			if(debug > 0)
			{
				_log.info("#" + room + "created: " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
			}
		}
		else
		{
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null && def0.npc_count > 0)
				{
					def0.despawn();
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010067 && enabled == 1)
		{
			if(debug > 0)
			{
				_log.info("#" + room + "mob_increase_begin");
			}
			onScriptEvent(78010072, 0, 0);
			addTimer(TM_mob_increase, TIME_mob_increase * 1000);
		}
		else if(eventId == 78010065 && enabled == 1)
		{
			if(zone == 2 && (room == 212 || room == 222 || room == 232 || room == 242))
			{
				switch((Integer) arg1)
				{
					case 0:
						i_ai0 = supply_lv0_2nd_def;
						break;
					case 1:
						i_ai0 = supply_lv1_2nd_def;
						break;
					case 2:
						i_ai0 = supply_lv0_2nd_def;
						break;
				}
			}
			else
			{
				switch((Integer) arg1)
				{
					case 0:
						i_ai0 = supply_lv1;
						break;
					case 1:
						i_ai0 = supply_lv0;
						break;
				}
			}
			i_ai1 = (Integer) arg2;
			if(debug > 0)
			{
				_log.info("#" + room + "renew: " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
			}
			onScriptEvent(78010072, 0, 0);
		}
		else if(eventId == 78010072 && enabled == 1)
		{
			int i0;
			if((Integer) arg1 == 0)
			{
				i0 = ((i_ai0 + i_ai4) - npc_count);
			}
			else
			{
				i0 = (Integer) arg1;
			}
			if(i0 > 0 && maximum_npc >= npc_count + i0)
			{
				for(int i1 = 1; i1 <= i0; i1++)
				{
					SpawnDefine def0 = null;
					if(i_ai1 == TACT_AGGRESIVE)
					{
						def0 = spawn_defines.get(Rnd.get(6));
					}
					else if(i_ai1 == TACT_DEFENSIVE)
					{
						def0 = spawn_defines.get((Rnd.get(6) + 6));
					}
					else if(i_ai1 == TACT_INTERCEPT)
					{
						def0 = spawn_defines.get((Rnd.get(3) + 12));
					}
					if(def0 != null)
					{
						if(atomicIncrease(def0, 1))
						{
							def0.spawn(1, 0, def0.respawn_rand);
						}
					}
				}
				if(debug > 0)
				{
					_log.info("#" + room + "deploy(" + i0 + "): " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
				}
			}
		}
		else if(eventId == 78010060 && enabled == 1)
		{
			if(maximum_npc >= i_ai0 + i_ai4 + npc_count + supply_decrease)
			{
				i_ai4 = i_ai4 + supply_decrease;
				if(debug > 0)
				{
					_log.info("#" + room + "receive_supply: " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
				}
				onScriptEvent(78010072, supply_decrease, 0);
			}
		}
		else if(eventId == 1000)
		{
			enabled = 0;
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null && def0.npc_count > 0)
				{
					def0.despawn();
				}
			}
		}
		else if(eventId == 78010051 && npc_count > (i_ai0 + i_ai4))
		{
			int i1 = (npc_count - (i_ai0 + i_ai4));
			int i2 = 0;
			for(int i0 = 0; i0 <= i1; i0 = (i0 + 1))
			{
				SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));
				if(def0 != null && def0.npc_count > 0)
				{
					i2 = (i2 + def0.npc_count);
					if(i2 <= i1)
					{
						def0.despawn();
					}
				}
			}
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_mob_increase && enabled == 1)
		{
			if(maximum_npc >= (((i_ai0 + i_ai4) + npc_count) + supply_increase))
			{
				i_ai4 = (i_ai4 + supply_increase);
				i_ai3++;
				if(debug > 0)
				{
					_log.info("#" + room + "mob_increase(" + i_ai3 + "): " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
				}
				onScriptEvent(78010072, supply_increase, 0);
			}
			addTimer(TM_mob_increase, TIME_mob_increase * 1000);
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(!npc.isDead() && enabled == 1)
		{
			if(i_ai0 > i_ai4 * -1)
			{
				DefaultMaker maker0 = null;
				i_ai4 = i_ai4 - supply_decrease;
				if(name.equals(z2_a_sb01_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_sb02_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_sb03_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_sb04_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_tm01_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_tm02_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_tm03_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_a_tm04_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm02_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_tm03_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_sb01_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_sb02_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_sb03_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_sb04_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_tm01_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_tm02_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_tm03_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm04_mob_maker);
							break;
					}
				}
				else if(name.equals(z2_d_tm04_mob_maker))
				{
					switch(Rnd.get(7))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_sb04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm01_mob_maker);
							break;
						case 5:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm02_mob_maker);
							break;
						case 6:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_tm03_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm01_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm02_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm02_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm03_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm04_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm05_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_a_tm06_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_tm05_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm01_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm02_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm02_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm03_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm03_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm04_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm04_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm05_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm05_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm06_mob_maker);
							break;
					}
				}
				else if(name.equals(z3_d_tm06_mob_maker))
				{
					switch(Rnd.get(5))
					{
						case 0:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm01_mob_maker);
							break;
						case 1:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm02_mob_maker);
							break;
						case 2:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm03_mob_maker);
							break;
						case 3:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm04_mob_maker);
							break;
						case 4:
							maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_tm05_mob_maker);
							break;
					}
				}
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010060, 0, 0);
				}

				if(debug > 0)
				{
					_log.info("#" + room + "mob_decrease: " + i_ai0 + "/" + i_ai4 + "/" + npc_count + "/" + maximum_npc);
				}
			}
		}
		else if(enabled == 1)
		{
			SpawnDefine def0 = null;
			if(i_ai1 == TACT_AGGRESIVE)
			{
				def0 = spawn_defines.get(Rnd.get(6));
			}
			else if(i_ai1 == TACT_DEFENSIVE)
			{
				def0 = spawn_defines.get((Rnd.get(6) + 6));
			}
			else if(i_ai1 == TACT_INTERCEPT)
			{
				def0 = spawn_defines.get((Rnd.get(3) + 12));
			}

			if(def0 != null && def0.respawn != 0)
			{
				if(atomicIncrease(def0, 1))
				{
					def0.spawn(1, def0.respawn, def0.respawn_rand);
				}
			}
		}
	}
}