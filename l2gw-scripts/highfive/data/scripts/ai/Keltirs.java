package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Location;

/**
 * User: darkevil, thx VX, Artful за текст, airman за инициативу. Date: 06.04.2008 Time: 22:02:29 Info: Crazy Keltirs - по желанию можно добавить и другие
 * вариации, примеров благо полно. Info: test ai for noob player's
 */

public class Keltirs extends Fighter implements ScriptFile
{
	// Радиус на который будут отбегать келтиры.
	private static final int range = 600;
	// Время в мс. через которое будет потвторяться Rnd фраза.
	private static final int voicetime = 8000;
	private long _lastAction;
	private static String[] _retreatText = {
			"Не трогай меня, я боюсь!",
			"Ты страшный! Братья, убегаем!",
			"Полундра! Сезон охоты открыт!!!",
			"ррРРРрррРРРрррррррр!",
			"Браконьер, я занесен в красную книгу!",
			"Я от тебя убегу, двуногое существо!",
			"Не догонишь, не догонишь!" };

	private static String[] _fightText = {
			"Всех убью, один останусь!",
			"рррРРРррРРрррРРРРРррррр!",
			"Бей гада!",
			"Хочешь, за жопу укушу",
			"Щас КУСЬ всем сделаю..." };

	public Keltirs(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean createNewTask()
	{
		if(Rnd.chance(60))

		{
			// Удаляем все задания
			// clearTasks();

			L2Character _temp_attack_target = getAttackTarget();

			// Новая цель исходя из агрессивности
			L2Character hated = _thisActor.isConfused() ? _temp_attack_target : _thisActor.getMostHated();

			if(hated != null && hated != _thisActor)
				_temp_attack_target = hated;

			// Добавить новое задание
			addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE * 2);

			if(System.currentTimeMillis() - _lastAction > voicetime)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, _fightText[Rnd.get(_fightText.length)]);
				_lastAction = System.currentTimeMillis();
			}
			return true;
		}

		int spawnX = _thisActor.getSpawnedLoc().getX();
		int spawnY = _thisActor.getSpawnedLoc().getY();
		int spawnZ = _thisActor.getSpawnedLoc().getZ();

		int x = spawnX + Rnd.get(2 * range) - range;
		int y = spawnY + Rnd.get(2 * range) - range;
		int z = GeoEngine.getHeight(x, y, spawnZ, _thisActor.getReflection());

		_thisActor.setRunning();

		_thisActor.moveToLocation(x, y, z, 0, true);

		Task task = new Task();
		task.type = TaskType.MOVE;
		task.usePF = false;
		task.loc = new Location(spawnX, spawnY, spawnZ);
		_task_list.add(task);
		_def_think = true;

		if(System.currentTimeMillis() - _lastAction > voicetime)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, _retreatText[Rnd.get(_retreatText.length)]);
			_lastAction = System.currentTimeMillis();
		}
		return true;
	}

	private static final int[] _list = {
			20481,
			20529,
			20530,
			20531,
			20532,
			20533,
			20534,
			20535,
			20536,
			20537,
			20538,
			20539,
			20544,
			20545,
			22229,
			22230,
			22231,
			18003 };

	@Override
	public void onLoad()
	{
		if(Config.ALT_AI_KELTIRS)
			for(int id : _list)
			{
				for(L2Spawn s : SpawnTable.getInstance().getSpawnsByNpcId(id))
					for(L2NpcInstance i : s.getAllSpawned())
						i.setAI(new Keltirs(i));
				NpcTable.getTemplate(id).ai_type = "Keltirs";
			}
	}

	@Override
	public void onReload()
	{}

	@Override
	public void onShutdown()
	{}
}
