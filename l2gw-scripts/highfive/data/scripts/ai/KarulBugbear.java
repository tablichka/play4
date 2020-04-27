package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Ranger;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * AI для Karul Bugbear ID: 20600
 *
 * @author Diamond
 */
public class KarulBugbear extends Ranger
{
	private boolean _firstTimeAttacked = true;

	public KarulBugbear(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_firstTimeAttacked)
		{
			_firstTimeAttacked = false;
			if(Rnd.chance(10))
				Functions.npcSay(_thisActor, Say2C.ALL, "Вы слишком открыты, так вас любой может поразить!");//TODO: Найти fString и заменить.
		}
		else if(Rnd.chance(5))
			Functions.npcSay(_thisActor, Say2C.ALL, "Смотрите за вашей спиной!");//TODO: Найти fString и заменить.
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}