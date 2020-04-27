package instances;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SpawnTable;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 02.12.2010 18:40:46
 */
public class CastlePailaka extends Instance
{
	private final String _groupName;
	private L2GroupSpawn _npcGroup;
	private L2GroupSpawn[] _currentGroup;
	private int _currentStage = 0;
	private ScheduledFuture<?> _spawnTask;
	private ScheduledFuture<?> _timeoutTask;
	protected static final int FINAL_BOSS_KILLED = 2117009;
	private int _bosses = 0;

	public CastlePailaka(InstanceTemplate template, int rId)
	{
		super(template, rId);
		if(_template.getId() >= 80 && _template.getId() <= 88)
			_groupName = "c_pailaka_";
		else
			_groupName = "f_pailaka_";

		_currentGroup = new L2GroupSpawn[3];
	}

	@Override
	public void notifyEvent(String event, L2Character cha, L2Player player)
	{
		if("boss_killed".equals(event))
		{
			_bosses++;
			if(_bosses >= 2)
			{
				cha.getAI().broadcastScriptEvent(FINAL_BOSS_KILLED, null, null, 10000);
				successEnd();
			}
		}
		else if("npc_killed".equals(event) && !_terminate)
		{
			_terminate = true;
			rescheduleEndTask(300);
		}
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnGroup(), 2 * 60000);
		_timeoutTask = ThreadPoolManager.getInstance().scheduleGeneral(new TimeOut(), 26 * 60000); 
	}

	@Override
	public void successEnd()
	{
		super.successEnd();

		for(L2GroupSpawn inv : _currentGroup)
			if(inv != null)
				inv.despawnAll();

		if(_spawnTask != null)
		{
			_spawnTask.cancel(true);
			_spawnTask = null;
		}
		if(_timeoutTask != null)
		{
			_timeoutTask.cancel(true);
			_timeoutTask = null;
		}
	}

	@Override
	public void stopInstance()
	{
		super.stopInstance();

		if(_npcGroup != null)
			_npcGroup.despawnAll();

		for(L2GroupSpawn inv : _currentGroup)
			if(inv != null)
				inv.despawnAll();

		if(_spawnTask != null)
		{
			_spawnTask.cancel(true);
			_spawnTask = null;
		}
		if(_timeoutTask != null)
		{
			_timeoutTask.cancel(true);
			_timeoutTask = null;
		}
	}

	private class TimeOut implements Runnable
	{
		public void run()
		{
			if(!_terminate)
			{
				if(_npcGroup != null)
					for(L2NpcInstance npc : _npcGroup.getAllSpawned())
						npc.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, FINAL_BOSS_KILLED);

				for(L2GroupSpawn inv : _currentGroup)
					if(inv != null)
						inv.despawnAll();
			}
		}
	}

	private class SpawnGroup implements Runnable
	{
		public void run()
		{
			if(!_terminate)
			{
				_currentStage++;
				if(_currentStage == 1)
				{
					_npcGroup = SpawnTable.getInstance().getEventGroupSpawn(_groupName + "npc", CastlePailaka.this);
					_npcGroup.setRespawnDelay(0);
					_npcGroup.doSpawn();
				}

				_currentGroup[_currentStage - 1] = SpawnTable.getInstance().getEventGroupSpawn(_groupName + "inv" + _currentStage, CastlePailaka.this);
				_currentGroup[_currentStage - 1].setRespawnDelay(0);
				_currentGroup[_currentStage - 1].doSpawn();
				if(_currentStage < 3)
					_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(this, 8 * 60000);
			}
		}
	}
}
