package events.Christmas;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;

public class ctreeAI extends DefaultAI
{
	public ctreeAI(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		int skillId = 2139;

		GArray<L2Player> players = L2World.getAroundPlayers(_actor, 200, 200);
		for(L2Player player : players)
			if(!player.isInZonePeace() && player.getEffectBySkillId(skillId) == null)
				_thisActor.doCast(SkillTable.getInstance().getInfo(skillId, 1), player, true);

		return false;
	}

	@Override
	protected boolean randomAnimation()
	{
		return false;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}