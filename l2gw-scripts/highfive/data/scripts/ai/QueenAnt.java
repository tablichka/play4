package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

public class QueenAnt extends DefaultNpc
{
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);
	public L2Skill skill01 = SkillTable.getInstance().getInfo(279052289);
	public L2Skill skill02 = SkillTable.getInstance().getInfo(263323649);
	public L2Skill skill03 = SkillTable.getInstance().getInfo(263389185);
	public L2Skill skill04 = SkillTable.getInstance().getInfo(263258113);

	public QueenAnt(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(Rnd.get(100) < 33)
		{
			Util.teleportInMyTerritory(_thisActor, -19480, 187344, -5600, 200);
		}
		else if(Rnd.get(100) < 50)
		{
			Util.teleportInMyTerritory(_thisActor, -17928, 180912, -5520, 200);
		}
		else
		{
			Util.teleportInMyTerritory(_thisActor, -23808, 182368, -5600, 200);
		}
		_thisActor.broadcastPacket(new PlaySound("BS01_A"));
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.weight_point = 10;
		_thisActor.createOnePrivate(29002, "QueenAntLarva", 0, 0, -21600, 179482, -5846, Rnd.get(360), 0, 0, 0);
		_thisActor.spawnMinions();
		addTimer(1001, 10000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(Rnd.get(100) < 30 && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				if(Rnd.get(100) < 50)
				{
					addEffectActionDesire(3, 50 * 1000 / 30, 30);
				}
				else
				{
					addEffectActionDesire(4, 50 * 1000 / 30, 30);
				}
			}
			addTimer(1001, 10000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance victim)
	{
		if(victim != _thisActor)
		{
			if(victim.getNpcId() == 29003)
			{
				_thisActor.respawnPrivate(victim, victim.weight_point, 10);
			}
			else
			{
				_thisActor.respawnPrivate(victim, victim.weight_point, 280 + Rnd.get(40));
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.getZ() - _thisActor.getZ() > 5 || (attacker.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(attacker.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(SkillTable.getAbnormalLevel(attacker, different_level_9_attacked) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
					_thisActor.addDamageHate(attacker, damage, 0);
					removeAttackDesire(attacker);
					return;
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
				}
			}
		}

		if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && SkillTable.getAbnormalLevel(attacker, 295895041) == -1)
		{
			if(attacker.getPlayer().getMountEngine().isMounted() && SkillTable.getAbnormalLevel(attacker, 279052289) <= 0)
			{
				if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
				{
					addUseSkillDesire(attacker, skill01, 0, 1, 1000000);
				}
			}
			if(skill != null && skill.getElement() == L2Skill.Element.FIRE && (Rnd.get(100) < 70 && _thisActor.inMyTerritory(attacker)))
			{
				if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
				{
					addUseSkillDesire(attacker, skill02, 0, 1, 1000000);
				}
			}
			else if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 500 && Rnd.get(100) < 10)
			{
				if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, skill03, 0, 0, 1000000);
				}
			}
			else if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 150 && Rnd.get(100) < 10)
			{
				if(Rnd.get(100) < 80 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 500)
				{
					if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
					{
						addUseSkillDesire(attacker, skill02, 0, 0, 1000000);
					}
				}
				else if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, skill03, 0, 0, 1000000);
				}
			}
			else if(Rnd.get(100) < 5 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 250)
			{
				if(skill04.getMpConsume() < _thisActor.getCurrentMp() && skill04.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill04.getId()))
				{
					addUseSkillDesire(_thisActor, skill04, 0, 1, 1000000);
				}
			}
			else if(Rnd.get(100) < 1)
			{
				addEffectActionDesire(1, 60 * 1000 / 30, 3000000);
			}
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				_thisActor.addDamageHate(attacker, damage, (long) (damage / _thisActor.getMaxHp() / 0.050000 * damage * 100));
				_thisActor.callFriends(attacker, damage);
				addAttackDesire(attacker, 0, DEFAULT_DESIRE);
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && victim != _thisActor)
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 500 && Rnd.get(100) < 5)
			{
				if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, skill03, 0, 0, 1000000);
				}
			}
			else if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 150 && Rnd.get(100) < 5)
			{
				if(Rnd.get(100) < 80)
				{
					if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
					{
						addUseSkillDesire(attacker, skill02, 0, 0, 1000000);
					}
				}
				else if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, skill03, 0, 0, 1000000);
				}
			}
			else if(Rnd.get(100) < 2 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 250)
			{
				if(skill04.getMpConsume() < _thisActor.getCurrentMp() && skill04.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill04.getId()))
				{
					addUseSkillDesire(_thisActor, skill04, 0, 1, 1000000);
				}
			}
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				_thisActor.addDamageHate(attacker, 0, (long) (damage / _thisActor.getMaxHp() / 0.050000 * ((L2NpcInstance) victim).weight_point * damage * 100));
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 500 && Rnd.get(100) < 3)
			{
				if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, 263389185, 0, 0, 1000000);
				}
			}
			else if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 150 && Rnd.get(100) < 3)
			{
				if(Rnd.get(100) < 80)
				{
					if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
					{
						addUseSkillDesire(attacker, 263323649, 0, 0, 1000000);
					}
				}
				else if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
				{
					addUseSkillDesire(attacker, 263389185, 0, 0, 1000000);
				}
			}
			else if(Rnd.get(100) < 2 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 250)
			{
				if(skill04.getMpConsume() < _thisActor.getCurrentMp() && skill04.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill04.getId()))
				{
					addUseSkillDesire(_thisActor, 263258113, 0, 1, 1000000);
				}
			}
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				_thisActor.addDamageHate(attacker, 0, (long) (damage / _thisActor.getMaxHp() / 0.050000 * 500));
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character speller)
	{
		if(speller.getZ() - _thisActor.getZ() > 5 || (speller.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(speller.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(SkillTable.getAbnormalLevel(speller, different_level_9_see_spelled) == -1)
			{
				if(different_level_9_see_spelled.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, speller);
					removeAttackDesire(speller);
					return;
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, speller);
				}
			}
		}
		if(skill != null && skill.getEffectPoint() > 0 && Rnd.get(100) < 15)
		{
			if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
			{
				addUseSkillDesire(speller, skill02, 0, 0, 1000000);
			}
		}
	}

	@Override
	protected void onEvtOutOfMyTerritory()
	{
		removeAllAttackDesire();
		teleportHome();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		_thisActor.broadcastPacket(new PlaySound("BS02_D"));
	}
}