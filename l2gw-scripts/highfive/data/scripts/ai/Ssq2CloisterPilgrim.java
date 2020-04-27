package ai;

import ai.base.PartyPrivatePhysicalspecialHeal;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.10.11 14:32
 */
public class Ssq2CloisterPilgrim extends PartyPrivatePhysicalspecialHeal
{
	public int IsAggressive = 1;

	public Ssq2CloisterPilgrim(L2Character actor)
	{
		super(actor);
	}
}