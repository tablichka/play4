package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 17:55
 */
public class RoyalSpawnTreasurebox extends RoyalRushMaker
{
	public RoyalSpawnTreasurebox(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1002)
		{
			int i1 = Calendar.getInstance().get(Calendar.MINUTE);
			int i2 = Rnd.get(10);
			if(i1 < 50 && i1 >= 48)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 10 + i2))
					{
						def0.spawn(10 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 48 && i1 >= 46)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 15 + i2))
					{
						def0.spawn(15 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 46 && i1 >= 44)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 20 + i2))
					{
						def0.spawn(20 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 44 && i1 >= 42)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 26 + i2))
					{
						def0.spawn(26 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 42 && i1 >= 40)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 32 + i2))
					{
						def0.spawn(32 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 40 && i1 >= 38)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 39 + i2))
					{
						def0.spawn(39 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 38 && i1 >= 36)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 45 + i2))
					{
						def0.spawn(45 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 36 && i1 >= 34)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 52 + i2))
					{
						def0.spawn(52 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 34 && i1 >= 32)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 60 + i2))
					{
						def0.spawn(60 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 32 && i1 >= 30)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 68 + i2))
					{
						def0.spawn(68 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 30 && i1 >= 28)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 76 + i2))
					{
						def0.spawn(76 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 28 && i1 >= 26)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 85 + i2))
					{
						def0.spawn(85 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 26 && i1 >= 24)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 94 + i2))
					{
						def0.spawn(94 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 24 && i1 >= 22)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 103 + i2))
					{
						def0.spawn(103 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 22 && i1 >= 20)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 113 + i2))
					{
						def0.spawn(113 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 18 && i1 >= 16)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 123 + i2))
					{
						def0.spawn(123 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 16 && i1 >= 14)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 134 + i2))
					{
						def0.spawn(134 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 14 && i1 >= 12)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 145 + i2))
					{
						def0.spawn(145 + i2, 0, 0);
					}
				}
			}
			else if(i1 < 12)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(atomicIncrease(def0, 157 + i2))
					{
						def0.spawn(157 + i2, 0, 0);
					}
				}
			}
		}
	}
}