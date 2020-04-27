package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.ai.Ranger;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.ArrayList;

/**
 * @author rage
 * @date 30.09.2009 14:19:53
 */
public class DungeonRanger extends Ranger
{
	public DungeonRanger(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker == null)
			return;

		L2Player _player = attacker.getPlayer();

		if(_player != null)
		{
			if(!_thisActor.canAttackCharacter(attacker))
				return;

			if((_thisActor.isRaid() || _thisActor.isRaidMinion()) && !attacker.isDead() && attacker.getAllEffects().size() > 0 && attacker.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF && attacker.getEffectBySkillId(5456) == null)
			{
				L2Skill revengeSkill =  SkillTable.getInstance().getInfo(5456, 1); // Cancel buffs
				if(!_thisActor.isSkillDisabled(revengeSkill.getId()))
					_thisActor.doCast(revengeSkill, attacker, true);
			}

			if(checkAttacker(_player))
				return;
		}

		_isMovingBack = false;
		_thisActor.setAttackTimeout(MAX_ATTACK_TIMEOUT + GameTimeController.getGameTicks());
		setGlobalAggro(0);

		// 1 хейт добавляется хозяину суммона, чтобы после смерти суммона моб накинулся на хозяина.
		if(_player != null && aggro > 0 && attacker.getPlayer() != null && (attacker.isSummon() || attacker.isPet()))
			_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);

		_thisActor.addDamageHate(attacker, 0, aggro);

		if(!_actor.isRunning())
			startRunningTask(1000);

		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
		{
			// Показываем анимацию зарядки шотов, если есть таковые.
			switch(_thisActor.getTemplate().shots)
			{
				case SOUL:
					_thisActor.unChargeShots(false);
					break;
				case SPIRIT:
				case BSPIRIT:
					_thisActor.unChargeShots(true);
					break;
				case SOUL_SPIRIT:
				case SOUL_BSPIRIT:
					_thisActor.unChargeShots(false);
					_thisActor.unChargeShots(true);
					break;
			}

			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null && caster.getAllEffects().size() > 0 && skill != null && !skill.isToggle() && !skill.isHandler() && !skill.isTriggered() && (_thisActor.isRaid() || _thisActor.isRaidMinion()) && caster.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF && caster.getEffectBySkillId(5456) == null)
		{
			L2Skill revengeSkill =  SkillTable.getInstance().getInfo(5456, 1); // Cancel buffs
			_thisActor.doCast(revengeSkill, caster, true);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.canAttackCharacter(attacker))
			return;

		L2Player _player = attacker.getPlayer();
		if(_player != null)
		{
			ArrayList<QuestState> quests = _player.getQuestsForAttacks(_thisActor);
			if(quests != null)
				for(QuestState qs : quests)
					qs.getQuest().notifyAttack(_thisActor, qs, skill);

			// 1 хейт добавляется хозяину суммона, чтобы после смерти суммона моб накинулся на хозяина.
			if(damage > 0 && attacker.getPlayer() != null && (attacker.isSummon() || attacker.isPet()))
			{
				if(!((_thisActor.isRaid() || _thisActor.isRaidMinion()) && attacker.getPlayer().getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF))
					_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);
			}
		}

		_isMovingBack = false;
		_thisActor.callFriends(attacker, damage);

		if((_thisActor.isRaid() || _thisActor.isRaidMinion()) && !attacker.isDead() && attacker.getAllEffects().size() > 0 && attacker.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF && attacker.getEffectBySkillId(5456) == null)
		{
			L2Skill revengeSkill =  SkillTable.getInstance().getInfo(5456, 1); // Cancel buffs
			if(!_thisActor.isSkillDisabled(revengeSkill.getId()))
				_thisActor.doCast(revengeSkill, attacker, true);
		}

		if(checkAttacker(_player))
			return;

		_thisActor.setAttackTimeout(MAX_ATTACK_TIMEOUT + GameTimeController.getGameTicks());
		setGlobalAggro(0);

		_thisActor.addDamage(attacker, damage);

		if(!_actor.isRunning())
			startRunningTask(1000);

		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
	}
}
