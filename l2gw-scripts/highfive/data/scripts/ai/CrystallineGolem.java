package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 12.11.2009 9:54:28
 */
public class CrystallineGolem extends DefaultAI
{
	private static Location point1 = new Location(143021, 151675, -11839);
	private static Location point2 = new Location(139482, 151675, -11839);
	private static int CRYSTAL_FRAGMENT = 9693;
	private boolean onPlace = false;
	private boolean returnToHome = false;
	private int speach = -1;
	private long nextSpeach;

	public CrystallineGolem(L2Character actor)
	{
		super(actor);
		nextSpeach = System.currentTimeMillis() + 2000;
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		return false;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(speach == -1)
			speach = _thisActor.getDistance(point1.getX(), point1.getY(), point1.getZ()) < _thisActor.getDistance(point2.getX(), point2.getY(), point2.getZ()) ? 1 : 0;

		if(nextSpeach > System.currentTimeMillis())
			return true;

		if(speach == 1)
		{
			speach++;
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800035);
			nextSpeach = System.currentTimeMillis() + Rnd.get(2000, 5000);
		}
		else if(speach == 2 && nextSpeach < System.currentTimeMillis())
		{
			speach++;
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800036);
		}
		else if(speach == 3 && _thisActor.getAroundPlayers(500).size() > 0)
		{
			speach++;
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800037);
		}

		if(_def_think)
		{
			doTask();
			return true;
		}
		else
			createNewTask();

		return false;
	}

	@Override
	protected void onEvtArrived()
	{
		L2ItemInstance closestItem = null;
		int minDist = Integer.MAX_VALUE;

		for(L2Object obj : L2World.getAroundObjects(_thisActor, 150, 100))
			if(obj instanceof L2ItemInstance && ((L2ItemInstance) obj).getItemId() == CRYSTAL_FRAGMENT && _thisActor.getDistance3D(obj) < minDist)
			{
				minDist = (int) _thisActor.getDistance3D(obj);
				closestItem = (L2ItemInstance) obj;
			}

		if(closestItem != null && minDist < 20)
		{
			closestItem.deleteMe();
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800038 + Rnd.get(0, 4));
		}
		else
			returnToHome = true;
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		if(returnToHome)
		{
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = _thisActor.getSpawn().getLoc();
			_task_list.add(task);
			_def_think = true;
			returnToHome = false;
			return true;
		}

		L2ItemInstance closestItem = null;
		int minDist = Integer.MAX_VALUE;

		for(L2Object obj : L2World.getAroundObjects(_thisActor, 150, 100))
			if(obj instanceof L2ItemInstance && ((L2ItemInstance) obj).getItemId() == CRYSTAL_FRAGMENT && _thisActor.getDistance3D(obj) < minDist)
			{
				minDist = (int) _thisActor.getDistance3D(obj);
				closestItem = (L2ItemInstance) obj;
			}

		if(closestItem != null)
			if(minDist > 20)
			{
				Task task = new Task();
				task.type = TaskType.MOVE;
				task.usePF = false;
				task.loc = closestItem.getLoc();
				_task_list.add(task);
				_def_think = true;
				return true;
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800038 + Rnd.get(0, 4));
				closestItem.deleteMe();
			}

		if(onPlace)
			return false;

		if(_thisActor.getDistance(point1.getX(), point1.getY(), point1.getZ()) < 40 || _thisActor.getDistance(point2.getX(), point2.getY(), point2.getZ()) < 40)
		{
			onPlace = true;
			addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(5441, 1), 1, 1, DEFAULT_DESIRE * 100);
			_thisActor.getSpawn().getInstance().notifyKill(_thisActor, null);
			return true;
		}

		return true;
	}
}
