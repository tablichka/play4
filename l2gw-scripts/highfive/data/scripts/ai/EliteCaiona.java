package ai;

import ai.base.AiASeedEliteMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 16:50
 */
public class EliteCaiona extends AiASeedEliteMonster
{
	public EliteCaiona(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(419364865);
		Skill01_Probability = 10;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(419430401);
		Skill02_Probability = 10;
		Skill02_Target_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(419495937);
		Skill03_Probability = 10;
		Skill03_Target_Type = 0;
		FieldCycle_ID = 6;
		FieldCycle_point = 10;
	}

	@Override
	protected void onEvtSpawn()
	{
		for(int i0 = 0; i0 < 3; i0++)
		{
			int i1 = Rnd.get(3);
			switch(i1)
			{
				case 0:
					_thisActor.createOnePrivate(22760, "Karnibi", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
				case 1:
					_thisActor.createOnePrivate(22761, "Kiriona", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
				case 2:
					_thisActor.createOnePrivate(22762, "Caiona", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
			}
		}
		super.onEvtSpawn();
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