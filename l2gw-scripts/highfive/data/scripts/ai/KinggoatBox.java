package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 22.11.11 13:09
 */
public class KinggoatBox extends DefaultNpc
{
	public KinggoatBox(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(killer != null)
		{
			L2Player player = killer.getPlayer();
			if(player != null && player.getEffectBySkillId(1073) != null)
			{
				if(Rnd.chance(50))
					_thisActor.dropItem(player, 9693, 1);
				if(Rnd.chance(33))
					_thisActor.dropItem(player, 9692, 1);
			}
		}
	}
}
