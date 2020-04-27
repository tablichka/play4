package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 10:33
 */
public class AiBloodyBerserker extends WarriorUseSkill
{
	public AiBloodyBerserker(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(443940865);
		Skill01_Probablity = 1000;
		Skill01_Check_Dist = 1;
		Skill01_Dist_Max = 300;
		Skill02_ID = SkillTable.getInstance().getInfo(444006401);
		Skill02_Probablity = 500;
		Skill02_AttackSplash = 1;
		Skill01_Check_Dist = 1;
		Skill02_Dist_Max = 200;
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			addAttackDesire(creature, 1, 500);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.param1 == 0)
		{
			if(Rnd.get(15) < 1)
			{
				_thisActor.createOnePrivate(22855, "AiBloodyBerserker", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() + 10), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22855, "AiBloodyBerserker", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 10), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22855, "AiBloodyBerserker", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() + 30), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22855, "AiBloodyBerserker", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 30), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22855, "AiBloodyBerserker", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 50), _thisActor.getZ(), 0, 1, 0, 0);
			}
		}
		super.onEvtDead(killer);
	}
}
