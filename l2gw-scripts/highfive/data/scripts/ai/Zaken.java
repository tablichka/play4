package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * Индивидуальное АИ эпик боса Zaken.
 *
 * @author rage
 */
public class Zaken extends Fighter
{
	private long _retargetTime = 0;
	private int _currentPos;
	// Zaken skills
	private static L2Skill s_zaken_tel_pc = SkillTable.getInstance().getInfo(4216, 1);
	private static L2Skill s_zaken_range_tel_pc = SkillTable.getInstance().getInfo(4217, 1);
	private static L2Skill s_zaken_drain = SkillTable.getInstance().getInfo(4218, 1);
	private static L2Skill s_zaken_hold = SkillTable.getInstance().getInfo(4219, 1);
	private static L2Skill s_zaken_dual_attack = SkillTable.getInstance().getInfo(4220, 1);
	private static L2Skill s_zaken_range_dual_attack = SkillTable.getInstance().getInfo(4221, 1);
	private static L2Skill s_zaken_self_tel = SkillTable.getInstance().getInfo(4222, 1);
	private static L2Skill s_anti_strider_slow = SkillTable.getInstance().getInfo(4258, 1);

	private static Location[] _zakenRooms = {
			new Location(53950, 219860, -3488),
			new Location(55980, 219820, -3488),
			new Location(54950, 218790, -3488),
			new Location(55970, 217770, -3488),
			new Location(53930, 217760, -3488),
			new Location(55970, 217770, -3216),
			new Location(55980, 219920, -3216),
			new Location(54960, 218790, -3216),
			new Location(53950, 219860, -3216),
			new Location(53930, 217760, -3216),
			new Location(55970, 217770, -2944),
			new Location(55980, 219920, -2944),
			new Location(54960, 218790, -2944),
			new Location(53950, 219860, -2944),
			new Location(53930, 217760, -2944)
	};

	public Zaken(L2Character actor)
	{
		super(actor);
		_retargetTime = System.currentTimeMillis() + 60000;
	}

	@Override
	protected void onEvtSpawn()
	{
		PlaySound ps = new PlaySound(0, "BS01_A", 0, 0, _thisActor.getLoc());
		for(L2Player player : _thisActor.getAroundPlayers(10000))
			if(player != null)
				player.sendPacket(ps);
		_currentPos = 6;
		addTimer(1003, 1700);
		addTimer(1001, 1000);
		addTimer(1051, 1000);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == s_zaken_self_tel)
			_thisActor.teleToLocation(_zakenRooms[_currentPos]);
		else if(skill == s_zaken_tel_pc)
		{
			L2Character target = getAttackTarget();
			if(target != null)
			{
				_thisActor.stopHate(target);
				Location loc = _zakenRooms[Rnd.get(_zakenRooms.length)];
				target.teleToLocation(loc.getX() + Rnd.get(650), loc.getY() + Rnd.get(650), loc.getZ());
			}
		}
		else if(skill == s_zaken_range_tel_pc)
		{
			L2Character target = getAttackTarget();
			if(target != null)
			{
				GArray<L2Player> players = target.getAroundPlayers(250);
				while(players.size() > 5)
					players.remove(Rnd.get(players.size()));
				Location loc = _zakenRooms[Rnd.get(_zakenRooms.length)];
				_thisActor.stopHate(target);
				target.teleToLocation(loc.getX() + Rnd.get(650), loc.getY() + Rnd.get(650), loc.getZ());
				for(L2Player player : players)
				{
					_thisActor.stopHate(player);
					loc = _zakenRooms[Rnd.get(_zakenRooms.length)];
					player.teleToLocation(loc.getX() + Rnd.get(650), loc.getY() + Rnd.get(650), loc.getZ());
				}
			}
		}
	}

	@Override
	protected boolean createNewTask()
	{
		// Удаляем все задания
		clearTasks();

		L2Character _temp_attack_target = getAttackTarget();

		// Новая цель исходя из агрессивности
		L2Character hated = _thisActor.isConfused() ? _temp_attack_target : _thisActor.getMostHated();

		if(hated != null && hated != _thisActor)
		{
			if(_retargetTime < System.currentTimeMillis())
			{
				_retargetTime = System.currentTimeMillis() + Rnd.get(25000, 60000);
				L2Player target = getRandomTarget();
				if(target != null && target != hated)
				{
					if(!_thisActor.getAggroList().containsKey(target.getObjectId()))
						_thisActor.addDamageHate(target, 0, _thisActor.getAggroList().get(hated.getObjectId()).hate);
					else
						_thisActor.getAggroList().get(target.getObjectId()).hate = _thisActor.getAggroList().get(hated.getObjectId()).hate;

					_thisActor.getAggroList().get(hated.getObjectId()).hate = 0;
				}

				return false;
			}
			_temp_attack_target = hated;
		}
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			_temp_attack_target = null;
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		L2Skill r_skill = null;

		if(r_skill == null && Rnd.get(15) < 1)
		{
			int chance = Rnd.get(15 * 5);
			if(chance < 1)
				r_skill = s_zaken_tel_pc;
			else if(chance < 2)
				r_skill = s_zaken_range_tel_pc;
			else if(chance < 4)
				r_skill = s_zaken_hold;
			else if(chance < 8)
				r_skill = s_zaken_drain;
			else if(chance < 15)
			{
				if(_thisActor.isInRange(_temp_attack_target, 100))
					r_skill = s_zaken_range_dual_attack;
			}
			if(r_skill == null && Rnd.chance(50))
				r_skill = s_zaken_dual_attack;
		}
		else if(_temp_attack_target.isPlayer() && _temp_attack_target.getPlayer().getMountEngine().isMounted())
			r_skill = s_anti_strider_slow;

		// Использовать скилл если можно, иначе атаковать
		if(r_skill != null && !r_skill.isMuted(_thisActor) && _thisActor.getCurrentMp() >= r_skill.getMpConsume() && !_thisActor.isSkillDisabled(r_skill.getId()))
		{
			// Проверка таргета
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;

			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		// Добавить новое задание
		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		PlaySound ps = new PlaySound(0, "BS02_D", 0, 0, _thisActor.getLoc());
		for(L2Player player : _thisActor.getAroundPlayers(10000))
			if(player != null)
				player.sendPacket(ps);
		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
			inst.successEnd();
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		switch(timerId)
		{
			case 1001:
				if(Rnd.get(40) < 1)
				{
					_currentPos = Rnd.get(_zakenRooms.length);
					addUseSkillDesire(_thisActor, s_zaken_self_tel, 1, 1, DEFAULT_DESIRE * 1000);
				}
				//addTimer(1001, 30000);
				break;
			case 1003:
				Instance inst = _thisActor.getSpawn().getInstance();
				if(inst != null)
					inst.spawnEvent("zaken");
				break;
			case 1051:
				if(!_thisActor.isInRange(_zakenRooms[_currentPos], 1500))
				{
					_thisActor.stopHate();
					_thisActor.teleToLocation(_zakenRooms[_currentPos]);
				}
				addTimer(1051, 10000);
				break;
		}
	}
}