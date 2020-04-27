package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 24.10.2010 18:33:44
 */
public class HBUrbanAreaTorturedNative extends Fighter
{
	public HBUrbanAreaTorturedNative(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(Rnd.chance(1))
			if(Rnd.chance(10))
				Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBUrbanAreaTorturedNative1", null);//TODO: Найти fString и заменить.
			else
				Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBUrbanAreaTorturedNative2", null);//TODO: Найти fString и заменить.

		return super.thinkActive();
	}

}