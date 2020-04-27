package ai;

import ai.base.AiASeedNormalMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

public class Kiriona extends AiASeedNormalMonster
{

	public Kiriona(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(419168257);
		Skill01_Probability = 10;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(419233793);
		Skill02_Probability = 10;
		Skill02_Target_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(419299329);
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
			if(CategoryManager.isInCategory(112, target) || CategoryManager.isInCategory(3, target))
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
}
