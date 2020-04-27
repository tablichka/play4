package ai;

import javolution.util.FastList;
import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.List;
import java.util.Map;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author rage
 * @date 12.11.2009 11:46:48
 */
public class Tears extends Balanced
{
	private L2Skill _udSkill;
	private boolean _udUsed = false;
	private long _firstDragonScaleUse = 0;
	private int _dragonScaleUsed = 0;
	private int _dragonScalePlayers = 9;
	private int _dragonScaleSkill = 2369;
	private boolean _minionsSpawned = false;
	private long _minionsDespawnHp = 0;
	private long _nextMinionSpawnTime;
	private static int _minionId = 25535;
	private List<L2Spawn> _miniosList;
	private boolean _attacked;

	public Tears(L2Character actor)
	{
		super(actor);
		_udSkill = SkillTable.getInstance().getInfo(5420, 1);
		_miniosList = new FastList<L2Spawn>();
		_attacked = false;
	}

	@Override
	protected boolean thinkActive()
	{
		return super.thinkActive();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!_attacked)
		{
			_nextMinionSpawnTime = System.currentTimeMillis() + Rnd.get(60000, 120000);
			_attacked = true;
		}
		if(_minionsSpawned)
		{
			if(_minionsDespawnHp > _thisActor.getCurrentHp())
				despawnMinios();
		}
		else if(!_udUsed && _nextMinionSpawnTime < System.currentTimeMillis() && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.18)
		{
			for(L2Character cha : _thisActor.getKnownCharacters(2000))
				if(cha instanceof L2Playable)
				{
					if(cha.getTarget() == _thisActor)
					{
						cha.setTarget(null);
						cha.abortAttack();
						cha.abortCast();
					}
					cha.getAI().setAttackTarget(null);
				}

			_thisActor.abortAttack();
			_thisActor.abortCast();
			_thisActor.broadcastPacket(new MagicSkillUse(_thisActor, 5441, 1, 2000, 0));
			Location pos = spawnMinios();
			_thisActor.teleToLocation(pos);
			return;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}


	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		L2Character _temp_attack_target = getAttackTarget();

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

		if(!_udUsed && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.13)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800032);
			_thisActor.doCast(_udSkill, _thisActor, false);
			_udUsed = true;
			return true;
		}

		int phys_per = 20;
		int debuff_per = 35;
		int dam_per = 70;
		int heal_per = 20;

		List<L2Skill> d_skill = new FastList<L2Skill>();
		L2Skill r_skill = null;

		int distance = (int) _thisActor.getDistance(_temp_attack_target);
		double _def_mp = _thisActor.getCurrentMp();

		if(!Rnd.chance(phys_per))
		{
			Map<L2Skill, Integer> skill_chances = new FastMap<L2Skill, Integer>();

			// DEBUFF
			if(_debuff_skills.length > 0)
			{
				L2Skill skill = getSkillByRange(_debuff_skills, distance);
				if(skill != null)
					skill_chances.put(skill, debuff_per);
			}

			// Dmage skills
			if(_dam_skills.length > 0)
			{
				L2Skill skill = getSkillByRange(_dam_skills, distance);
				if(skill != null)
					skill_chances.put(skill, dam_per);
			}

			if(_cancel_skills.length > 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() > 0.40)
			{
				List<L2Skill> skills = getEnabledSkills(_cancel_skills);
				if(skills.size() > 0)
					skill_chances.put(skills.get(Rnd.get(skills.size())), 1);
			}

			// Mana burn
			if(_manaburn_skills.length > 0)
			{
				List<L2Skill> skills = getEnabledSkills(_manaburn_skills);
				if(skills.size() > 0)
					skill_chances.put(skills.get(Rnd.get(skills.size())), 10);
			}

			// HEAL
			if(_heal_skills.length > 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() < 0.25)
			{
				List<L2Skill> skills = getEnabledSkills(_heal_skills);
				if(skills != null && skills.size() > 0)
					skill_chances.put(skills.get(Rnd.get(skills.size())), heal_per);
			}

			if(skill_chances.size() > 0)
			{
				for(L2Skill skill : skill_chances.keySet())
					if(Rnd.chance(skill_chances.get(skill)))
						d_skill.add(skill);

				r_skill = getSkillByRange(d_skill.toArray(new L2Skill[d_skill.size()]), distance);
			}

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

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		super.onEvtSeeSpell(skill, caster);
		if(skill.getId() == _dragonScaleSkill && caster.getTarget() == _thisActor)
			if(System.currentTimeMillis() - _firstDragonScaleUse < 3000)
			{
				_dragonScaleUsed++;
				if(_dragonScalePlayers == _dragonScaleUsed)
					_thisActor.stopEffect(_udSkill.getId());
			}
			else
			{
				_firstDragonScaleUse = System.currentTimeMillis();
				_dragonScaleUsed = 1;
				_dragonScalePlayers = _thisActor.getSpawn().getInstance().getMembers().size();
			}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		despawnMinios();
		super.onEvtDead(killer);
	}

	private void despawnMinios()
	{
		for(L2Spawn spawn : _miniosList)
			spawn.despawnAll();

		_miniosList.clear();
		_nextMinionSpawnTime = System.currentTimeMillis() + Rnd.get(30000, 180000);
		_minionsSpawned = false;
	}

	private Location spawnMinios()
	{
		if(_miniosList.size() > 0)
			despawnMinios();

		_minionsSpawned = true;
		_minionsDespawnHp = (long)_thisActor.getCurrentHp() - 50000;
		if(_minionsDespawnHp < 0)
			_minionsDespawnHp = (long)_thisActor.getCurrentHp() / 2;

		int rbPos = Rnd.get(10);
		Location rbLoc = _thisActor.getSpawnedLoc();
		L2Character target = getAttackTarget();

		for(int c = 0; c < 10; c++)
		{
			try
			{
				Location spawnLoc = Util.getPointInRadius(_thisActor.getSpawnedLoc(), 370, 36 * c + 18);

				if(c == rbPos)
				{
					rbLoc = spawnLoc;
					continue;
				}

				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(_minionId));
				spawn.setAmount(1);
				spawn.setReflection(_thisActor.getReflection());
				spawn.setInstance(_thisActor.getSpawn().getInstance());
				spawn.setLoc(spawnLoc);
				spawn.stopRespawn();
				spawn.spawnOne();
				spawn.getLastSpawn().setCurrentHp(_thisActor.getCurrentHp());

				if(target != null)
				{
					spawn.getLastSpawn().addDamageHate(target, 500, 500);
					spawn.getLastSpawn().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
				}

				_miniosList.add(spawn);
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + ": can't spawn minions: " + _minionId + " " + e);
				e.printStackTrace();
			}
		}
		return rbLoc;
	}
}
