package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

public class Quest421FairyTree extends Fighter
{
	public Quest421FairyTree(L2Character actor)
	{
		super(actor);
		_actor.setImobilised(true);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skll)
	{
		if(attacker.isPlayer())
		{
			L2Skill skill = SkillTable.getInstance().getInfo(4167, 1);
			skill.applyEffects(_actor, attacker, false);
			return;
		}
		if(attacker.isPet())
			super.onEvtAttacked(attacker, damage, skll);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}