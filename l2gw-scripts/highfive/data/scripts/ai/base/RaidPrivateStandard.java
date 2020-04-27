package ai.base;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 23.09.11 16:50
 */
public class RaidPrivateStandard extends DefaultAI
{
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;

	public RaidPrivateStandard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1001, 2000);
		if(!_thisActor.isMyBossAlive())
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(_thisActor.getLeader() != null && _thisActor.getLoc().distance3D(_thisActor.getLeader().getLoc()) > 500 && _thisActor.isMyBossAlive() && !_thisActor.isMoving && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				_thisActor.teleToLocation(Location.coordsRandomize(_thisActor.getLeader(), 40, 100));
			}
			if(Rnd.get(3) < 1)
			{
				randomizeTargets();
			}
			if(!_thisActor.isMyBossAlive())
			{
				_thisActor.onDecay();
			}
			addTimer(1001, 60000);
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(debug)
			_log.info(_thisActor + " onEvtPartyAttacked: " + attacker + " " + victim + " " + damage);
		if(!attacker.isDead() && attacker.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF)
		{
			L2Skill revengeSkill = SkillTable.getInstance().getInfo(L2Skill.SKILL_RAID_CURSE, 1);
			if(!_thisActor.isSkillDisabled(revengeSkill.getId()))
				_thisActor.altUseSkill(revengeSkill, attacker);

			return;
		}
		if(attacker.getAbnormalLevelByType(4515) == -1)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(damage == 0)
				{
					damage = 1;
				}

				if(debug)
					_log.info(_thisActor + " onEvtPartyAttacked: addAttackDesire: " + attacker + " " + (long) (1.000000 * damage / (_thisActor.getLevel() + 7) * 20000));
				_thisActor.addDamageHate(attacker, 0, (long) (1.000000 * damage / (_thisActor.getLevel() + 7) * 20000));
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
		}
		if(_thisActor.isInZonePeace())
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			removeAllAttackDesire();
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null && (!caster.isPlayer() || !caster.getPlayer().isInvisible())
				&& skill != null && !skill.isToggle() && !skill.isHandler() && !skill.isTriggered()
				&& Math.abs(caster.getZ() - _thisActor.getZ()) < _see_spell_z
				&& caster.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF
				&& caster.getCastingTarget() != null && _thisActor.getHate(caster.getCastingTarget()) > 0 && (skill.isMagic() || skill.isPhysic()))
		{
			L2Skill revengeSkill = skill.isMagic() ? SkillTable.getInstance().getInfo(L2Skill.SKILL_RAID_SILENS, 1) : SkillTable.getInstance().getInfo(L2Skill.SKILL_RAID_CURSE, 1);
			_thisActor.doCast(revengeSkill, caster, null, true);
			return;
		}
		if(skill != null && skill.getEffectPoint() > 0)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(!_thisActor.isMoving && _thisActor.getMostHated() != caster)
				{
					addAttackDesire(caster, 1, (long) (skill.getEffectPoint() / _thisActor.getMaxHp() * 2000 * 150));
				}
			}
		}
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate == _thisActor.getLeader())
		{
			_thisActor.onDecay();
		}
	}
}
