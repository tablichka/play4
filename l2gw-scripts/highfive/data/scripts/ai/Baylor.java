package ai;

import javolution.util.FastList;
import javolution.util.FastMap;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.List;
import java.util.Map;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author rage
 * @date 21.12.2009 14:50:57
 */
public class Baylor extends DefaultAI
{
	private L2Skill _udSkill, _bersSkill, _damSkill1, _damSkill2, _damSkill3;
	private boolean _useUD = false;
	private boolean _udCanceled = false;
	private long _firstDragonClawUse = 0;
	private int _dragonClawUsed = 0;
	private int _dragonClawPlayers = 9;
	private int _dragonClawSkill = 2360;
	private long _lastUdUsed = 0;
	private int _minions = 0;
	private L2Spawn _alarmSpawn;
	private long _lastAlarmSpawned = 0;
	private int _alarmCount = 0;
	private static Location[] _alarmLocs = {
			new Location(154351, 142070, -12700),
			new Location(153573, 141274, -12700),
			new Location(152779, 142073, -12700),
			new Location(153568, 142862, -12700) };

	public Baylor(L2Character actor)
	{
		super(actor);
		_bersSkill = SkillTable.getInstance().getInfo(5224, 1);
		_udSkill = SkillTable.getInstance().getInfo(5225, 1);
		_damSkill1 = SkillTable.getInstance().getInfo(5227, 1);
		_damSkill2 = SkillTable.getInstance().getInfo(5228, 1);
		_damSkill3 = SkillTable.getInstance().getInfo(5229, 1);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_lastAlarmSpawned == 0)
			_lastAlarmSpawned = System.currentTimeMillis();

		if(!_useUD && !_udCanceled && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.3)
			_useUD = true;

		if(_minions == 1 && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.10)
			spawnMinions();
		else if(_minions == 0 && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.23)
			spawnMinions();

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		super.onEvtSeeSpell(skill, caster);

		if(skill.getId() == _dragonClawSkill && caster.getTarget() == _thisActor)
			if(System.currentTimeMillis() - _firstDragonClawUse < 3000)
			{
				_dragonClawUsed++;
				if(_dragonClawPlayers == _dragonClawUsed)
				{
					_thisActor.abortCast();
					_thisActor.stopEffect(_udSkill.getId());
					_thisActor.doCast(SkillTable.getInstance().getInfo(5404, 1), _thisActor, false);
					_udCanceled = true;
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1800068);
				}
			}
			else
			{
				_firstDragonClawUse = System.currentTimeMillis();
				_dragonClawUsed = 1;
				_dragonClawPlayers = _thisActor.getSpawn().getInstance().getMembers().size();
			}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill != null)
		{
			if(skill.getId() == 5224)
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800058 + Rnd.get(0, 2));
			else if(skill.getId() == 5225)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800067);
				_lastUdUsed = System.currentTimeMillis();
			}
		}

	}

	@Override
	protected boolean thinkActive()
	{
		checkAlarm();
		return super.thinkActive();
	}

	@Override
	protected void thinkAttack()
	{
		checkAlarm();
		super.thinkAttack();
	}

	@Override
	protected boolean createNewTask()
	{
		if(debug)
			_log.info("createNewTask: attackTarget: " + getAttackTarget());
		// Удаляем все задания
		clearTasks();

		L2Character _temp_attack_target = getAttackTarget();

		// Новая цель исходя из агрессивности
		L2Character hated = _thisActor.isConfused() ? _temp_attack_target : _thisActor.getMostHated();

		if(debug)
			_log.info("createNewTask: hated: " + hated);

		if(hated != null && hated != _thisActor)
			_temp_attack_target = hated;
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			_temp_attack_target = null;
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		double currHp = _thisActor.getCurrentHp() / _thisActor.getMaxHp();

		if(_useUD && !_udCanceled && _lastUdUsed + 25000 < System.currentTimeMillis())
		{
			addUseSkillDesire(_thisActor, _udSkill, 1, 1, DEFAULT_DESIRE * 1000);
			_lastUdUsed = System.currentTimeMillis() - 21000;
			return true;
		}

		int bersChance = currHp < 0.5 ? 80 : currHp < 0.9 ? 20 : 0;

		if(!_useUD && _thisActor.getEffectBySkill(_bersSkill) == null && Rnd.chance(bersChance))
		{
			addUseSkillDesire(_thisActor, _bersSkill, 1, 1, DEFAULT_DESIRE * 1000);
			return true;
		}

		int distance = (int) _thisActor.getDistance(_temp_attack_target);
		double _def_mp = _thisActor.getCurrentMp();

		if(!Rnd.chance(currHp < 0.5 ? 20 : 30))
		{
			List<L2Skill> d_skill = new FastList<L2Skill>();
			L2Skill r_skill = null;

			Map<L2Skill, Integer> skill_chances = new FastMap<L2Skill, Integer>();
			skill_chances.put(_damSkill1, currHp < 0.6 ? 50 : 25);
			skill_chances.put(_damSkill2, currHp < 0.6 ? 70 : 40);
			skill_chances.put(_damSkill3, currHp < 0.6 ? 40 : 10);

			for(L2Skill skill : skill_chances.keySet())
				if(!_thisActor.isSkillDisabled(skill.getId()) && Rnd.chance(skill_chances.get(skill)))
					d_skill.add(skill);

			if(d_skill.size() > 0)
				r_skill = getSkillByRange(d_skill.toArray(new L2Skill[d_skill.size()]), distance);

			// Использовать скилл если можно
			if(r_skill != null && !r_skill.isMuted(_thisActor) && _def_mp >= r_skill.getMpConsume())
			{
				// Проверка таргета
				if(r_skill.getAimingTarget(_thisActor) == _thisActor)
					_temp_attack_target = _thisActor;

				// Добавить новое задание
				addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
				return true;
			}
		}

		// Добавить новое задание
		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}

	private void spawnMinions()
	{
		_minions++;
		L2GroupSpawn minions = SpawnTable.getInstance().getEventGroupSpawn("baylor_minions", _thisActor.getSpawn().getInstance());
		minions.doSpawn();
	}

	private void checkAlarm()
	{
		if(_alarmCount < 20 && _lastAlarmSpawned > 0 && _lastAlarmSpawned + 30000 < System.currentTimeMillis())
		{
			if(_alarmSpawn == null || _alarmSpawn.getLastSpawn().isDead())
			{
				try
				{
					_alarmSpawn = new L2Spawn(NpcTable.getTemplate(18474));
					_alarmSpawn.setAmount(1);
					_alarmSpawn.setReflection(_thisActor.getReflection());
					_alarmSpawn.setInstance(_thisActor.getSpawn().getInstance());
					_alarmSpawn.setLoc(_alarmLocs[Rnd.get(_alarmLocs.length)]);
					_alarmSpawn.spawnOne();
					_lastAlarmSpawned = System.currentTimeMillis();
					_alarmCount++;
				}
				catch(Exception e)
				{
					_log.info(_thisActor + " can't spawn alarm device!" + e);
					e.printStackTrace();
				}
			}
			else
				_lastAlarmSpawned = System.currentTimeMillis();
		}
	}
}