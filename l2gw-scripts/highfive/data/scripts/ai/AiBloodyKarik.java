package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 10:32
 */
public class AiBloodyKarik extends WarriorUseSkill
{
	public AiBloodyKarik(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(443744257);
		Skill01_Probablity = 1000;
		Skill02_ID = SkillTable.getInstance().getInfo(443809793);
		Skill02_Probablity = 1000;
		Skill03_ID = SkillTable.getInstance().getInfo(443875329);
		Skill03_Probablity = 1000;
		Skill03_Target = 3;
		Skill03_Type = 1;
		HATE_SKILL_Weight_Point = 10000.000000f;
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
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, (int) (aggro * HATE_SKILL_Weight_Point), 0);
		super.onEvtManipulation(target, aggro, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.param1 == 0)
		{
			if(Rnd.get(15) < 1)
			{
				_thisActor.createOnePrivate(22854, "AiBloodyKarik", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() + 10), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22854, "AiBloodyKarik", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 10), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22854, "AiBloodyKarik", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() + 30), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22854, "AiBloodyKarik", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 30), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(22854, "AiBloodyKarik", 0, 0, (_thisActor.getX() + 30), (_thisActor.getY() - 50), _thisActor.getZ(), 0, 1, 0, 0);
			}
		}
		super.onEvtDead(killer);
	}
}
