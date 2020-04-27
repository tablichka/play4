package ai;

import ai.base.UndeadSeedTwinBoss;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 22:02
 */
public class Is1Clanicus extends UndeadSeedTwinBoss
{
	public Is1Clanicus(L2Character actor)
	{
		super(actor);
		pan_skill = SkillTable.getInstance().getInfo(388759553);
		donut_skill = SkillTable.getInstance().getInfo(388956161);
		pc_buff_skill = SkillTable.getInstance().getInfo(388890625);
		toggle_shield = SkillTable.getInstance().getInfo(388694017);
		BadgeName = 13869;
		BadgeNumber = 1;
		my_weapon = 13983;
		boss_type = 1;
	}

}