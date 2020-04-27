package ai;

import ai.base.IsBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:51
 */
public class Is1SoulWagon extends IsBasic
{
	public L2Skill self_stun = SkillTable.getInstance().getInfo(389087233);

	public Is1SoulWagon(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(385875969);
		Skill01_Probability = 15;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(386596865);
		Skill02_Probability = 20;
		Skill02_Target_Type = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if( CategoryManager.isInCategory(112, attacker.getActiveClass()) )
		{
			if( Rnd.get(100) < 20 )
			{
				addUseSkillDesire(_thisActor, self_stun, 1, 0, 100000000);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}