package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 24.10.2010 18:17:30
 */
public class HBUrbanAreaFighter extends Fighter
{
	private long _lastTalk;

	public HBUrbanAreaFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void thinkAttack()
	{
		if(_lastTalk < System.currentTimeMillis())
		{
			_lastTalk = System.currentTimeMillis() + 300000;
			Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBUrbanAreaFighter", null);//TODO: Найти fString и заменить.
		}

		super.thinkAttack();
	}
}
