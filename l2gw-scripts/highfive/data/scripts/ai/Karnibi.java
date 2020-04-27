package ai;

import ai.base.AiASeedNormalMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

public class Karnibi extends AiASeedNormalMonster
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(418906113);
	public int DIST_CHECK_TIMER = 3113;

	public Karnibi(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(418971649);
		Skill01_Probability = 10;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(419037185);
		Skill02_Probability = 10;
		Skill02_Target_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(419102721);
		Skill03_Probability = 10;
		Skill03_Target_Type = 0;
		FieldCycle_ID = 6;
		FieldCycle_point = 1;
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(target != null && target.isPlayer())
		{
			if(CategoryManager.isInCategory(112, target) || CategoryManager.isInCategory(3, target.getActiveClass()))
			{
				if(Rnd.get(100) < 5)
				{
					addUseSkillDesire(target, 418709505, 0, 1, max_desire);
				}
			}
			else if(Rnd.get(100) < 10)
			{
				addUseSkillDesire(target, 418709505, 0, 1, max_desire);
			}
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer())
		{
			if(CategoryManager.isInCategory(112, attacker) || CategoryManager.isInCategory(3, attacker.getActiveClass()))
			{
				if(Rnd.get(100) < 1)
				{
					addFleeDesire(attacker, max_desire);
					addTimer(DIST_CHECK_TIMER, 1000);
					_thisActor.c_ai0 = attacker.getStoredId();
				}
			}
			else if(Rnd.get(100) < 5)
			{
				addFleeDesire(attacker, max_desire);
				addTimer(DIST_CHECK_TIMER, 1000);
				_thisActor.c_ai0 = attacker.getStoredId();
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}


	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DIST_CHECK_TIMER)
		{
			addUseSkillDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), SpecialSkill01_ID, 0, 1, max_desire);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}