package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 04.09.11 20:34
 */
public class AiMalruk extends WarriorUseSkill
{
	public int dropHerb = 0;

	public AiMalruk(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		L2Player player = killer == null ? null : killer.getPlayer();
		if(player != null && dropHerb > 0 && CategoryManager.isInCategory(2, player))
		{
			if(Rnd.chance(70))
				_thisActor.dropItem(player, 8603, 1);
			else
				_thisActor.dropItem(player, 8604, 1);
		}
	}
}
