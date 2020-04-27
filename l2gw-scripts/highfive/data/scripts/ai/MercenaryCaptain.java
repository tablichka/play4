package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.instancemanager.SiegeManager;
import npc.model.L2FortressMercenaryInstance;

public class MercenaryCaptain extends DefaultAI
{
	public MercenaryCaptain(L2Character actor)
	{
		super(actor);
		_thisActor = (L2FortressMercenaryInstance) actor;
		MAX_PATHFIND_FAILS = Integer.MAX_VALUE;
		MAX_ATTACK_TIMEOUT = Integer.MAX_VALUE;
		AI_TASK_DELAY = 1000;

	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		if(!(target instanceof L2Playable))
			return false;
		if(!_thisActor.isAttackable(target, false, false))
			return false;
		return super.checkAggression(target);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null && attacker.getPlayer() != null)
		{
			L2Clan clan = attacker.getPlayer().getClan();
			if(clan != null && SiegeManager.getSiege(_thisActor) == clan.getSiege() && clan.isDefender())
				super.onEvtAttacked(attacker, damage, skill);
		}
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{
		if(target != null && target.getPlayer() != null)
		{
			L2Clan clan = target.getPlayer().getClan();
			if(clan != null && SiegeManager.getSiege(_thisActor) == clan.getSiege() && clan.isDefender())
				super.onEvtAggression(target, aggro, skill);
		}
	}

	@Override
	protected boolean thinkActive()
	{
		if(super.thinkActive())
		{
			if(randomAnimation())
				return false;
			return true;
		}
		return false;
	}

	@Override
	protected void thinkAttack()
	{
		_thisActor.setRunning();
		_thisActor.setAttackTimeout(Integer.MAX_VALUE);

		super.thinkAttack();
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		L2Character _temp_attack_target = getAttackTarget();

		L2Character hated = _thisActor.isConfused() ? _temp_attack_target : _thisActor.getMostHated();

		if(hated != null && hated != _thisActor)
			_temp_attack_target = hated;
		else
		{
			returnHome();
			return false;
		}

		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE * 2);
		return true;
	}

}