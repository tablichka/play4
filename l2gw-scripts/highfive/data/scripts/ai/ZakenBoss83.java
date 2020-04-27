package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.List;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author: rage
 * @date: 15.09.11 17:33
 */
public class ZakenBoss83 extends DefaultAI
{
	private static L2Skill s_zaken_tel_pc = SkillTable.getInstance().getInfo(4216, 1);
	private static L2Skill s_zaken_range_tel_pc = SkillTable.getInstance().getInfo(4217, 1);
	private static L2Skill s_zaken_drain = SkillTable.getInstance().getInfo(438370305);
	private static L2Skill s_zaken_hold = SkillTable.getInstance().getInfo(438435841);
	private static L2Skill s_zaken_dual_attack = SkillTable.getInstance().getInfo(438501377);
	private static L2Skill s_zaken_range_dual_attack = SkillTable.getInstance().getInfo(438566913);
	private static L2Skill s_anti_strider_slow = SkillTable.getInstance().getInfo(4258, 1);
	private long _retargetTime = 0;

	protected static final Location[] startPos =
			{
					new Location(54237, 218135, -3496),
					new Location(56288, 218087, -3496),
					new Location(55273, 219140, -3496),
					new Location(54232, 220184, -3496),
					new Location(56259, 220168, -3496),
					new Location(54250, 218122, -3224),
					new Location(56308, 218125, -3224),
					new Location(55243, 219064, -3224),
					new Location(54255, 220156, -3224),
					new Location(56255, 220161, -3224),
					new Location(54261, 218095, -2952),
					new Location(56258, 218086, -2952),
					new Location(55258, 219080, -2952),
					new Location(54292, 220096, -2952),
					new Location(56258, 220135, -2952)
			};

	protected int currentPos;
	protected long spawnTime;

	public ZakenBoss83(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.broadcastPacket(new PlaySound(0, "BS01_A", 0, 0, _thisActor.getLoc()));
		_thisActor.setHide(true);
		currentPos = Rnd.get(15);
		ServerVariables.set("zaken_pos_" + _thisActor.getReflection(), currentPos);
		_thisActor.teleToLocation(startPos[currentPos]);
		spawnTime = System.currentTimeMillis();
		_thisActor.i_ai0 = Util.getCurrentTime();
		addTimer(1051, 30000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1051)
		{
			if(!_thisActor.isInRange(startPos[currentPos], 1500))
			{
				_thisActor.stopHate();
				_thisActor.teleToLocation(startPos[currentPos]);
			}
			addTimer(1051, 30000);
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

		if(Rnd.get(15) < 1)
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
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2124002)
		{
			_thisActor.i_ai0++;
			if(_thisActor.i_ai0 > 3)
			{
				_thisActor.setHide(false);
				Instance inst = _thisActor.getSpawn().getInstance();
				inst.addSpawn(29184, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 60, 150, _thisActor.getReflection()), 0);
				inst.addSpawn(29184, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 60, 150, _thisActor.getReflection()), 0);
				inst.addSpawn(29184, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 60, 150, _thisActor.getReflection()), 0);
				inst.addSpawn(29184, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 60, 150, _thisActor.getReflection()), 0);
				_thisActor.updateAbnormalEffect();
			}
		}
		else if(eventId == 2124006)
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 0, 10000, 0, 1800868);
		else if(eventId == 2124007)
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 0, 10000, 0, 1800869);
		else if(eventId == 2124008)
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 0, 10000, 0, 1800870);
		else
			super.onEvtScriptEvent(eventId, arg1 ,arg2);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return !_thisActor.isHide() && super.checkAggression(target);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.isHide())
			return;

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.isHide())
			return;

		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(_thisActor.isHide())
			return;

		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		PlaySound ps = new PlaySound(0, "BS02_D", 0, 0, _thisActor.getLoc());
		for(L2Player player : _thisActor.getAroundPlayers(10000))
			if(player != null)
				player.sendPacket(ps);

		ServerVariables.unset("zaken_pos_" + _thisActor.getReflection());

		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
			inst.successEnd();
		broadcastScriptEvent(2124010, null, null, 5000);

		L2Party party = Util.getParty(killer);
		if(party != null)
		{
			List<L2Player> members;
			L2CommandChannel cc = party.getCommandChannel();
			members = cc != null ? cc.getMembers() : party.getPartyMembers();
			int i5 = Util.getCurrentTime() - _thisActor.i_ai0;
			for(L2Player member : members)
			{
				Functions.sendUIEventFStr(member, 1, 0, 0, "1", "1", "1", "60", "0", 1911119);
				if(_thisActor.isInRange(member, 1500))
				{
					if(i5 <= 5 * 60)
					{
						if(Rnd.get(100) < 50)
						{
							member.addItem("Loot", 15763, 1, _thisActor, false);
						}
					}
					else if(i5 <= 10 * 60)
					{
						if(Rnd.get(100) < 30)
						{
							member.addItem("Loot", 15764, 1, _thisActor, false);
						}
					}
					else if(i5 <= 15 * 60)
					{
						if(Rnd.get(100) < 25)
						{
							member.addItem("Loot", 15763, 1, _thisActor, false);
						}
					}
				}
			}
		}
	}
}