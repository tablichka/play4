package ai;

import ai.base.UndeadSeedTwinBoss;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 22:01
 */
public class Is1Clodecus extends UndeadSeedTwinBoss
{
	public Is1Clodecus(L2Character actor)
	{
		super(actor);
		pan_skill = SkillTable.getInstance().getInfo(388759553);
		donut_skill = SkillTable.getInstance().getInfo(388956161);
		pc_buff_skill = SkillTable.getInstance().getInfo(388890625);
		toggle_shield = SkillTable.getInstance().getInfo(388694017);
		BadgeName = 13868;
		BadgeNumber = 1;
		my_weapon = 13982;
		boss_type = 0;
	}
}