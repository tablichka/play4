package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 10:17
 */
public class AiDrakeMage extends WarriorUseSkill
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(443351041);

	public AiDrakeMage(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(443416577);
		Skill01_Dist_Min = 0;
		Skill01_Probablity = 5000;
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1001, 10000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(!_thisActor.isMyBossAlive())
			{
				if(!_thisActor.isInCombat())
				{
					_thisActor.onDecay();
				}
			}
			else
				addTimer(1001, 10000);
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.isMyBossAlive() && attacked_member == _thisActor.getLeader())
		{
			if(attacker != null)
			{
				addAttackDesire(attacker, damage, 0);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 14002)
		{
			addUseSkillDesire(_thisActor.getLeader(), SpecialSkill01_ID, 1, 1, 100000000);
		}
		if(eventId == 14006)
		{
			if(_thisActor.getLeader() != null && _thisActor.getLoc().distance3D(_thisActor.getLeader().getLoc()) > 500 && _thisActor.isMyBossAlive() && !_thisActor.isMoving)
			{
				removeAllAttackDesire();
				_thisActor.teleToLocation(_thisActor.getLeader().getX(), _thisActor.getLeader().getY(), (_thisActor.getLeader().getZ()));
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		L2Player c0 = killer != null ? killer.getPlayer() : null;
		if(CategoryManager.isInCategory(2, c0))
		{
			if(Rnd.get(100) < 70)
			{
				_thisActor.dropItem(c0, 8603, 1);
			}
			else
			{
				_thisActor.dropItem(c0, 8604, 1);
			}
		}
	}
}
