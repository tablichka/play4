package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Ranger;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.commons.math.Rnd;

/**
 * AI для Karul Bugbear ID: 20438
 *
 * @author Diamond
 */
public class OlMahumGeneral extends Ranger
{
	private boolean _firstTimeAttacked = true;

	public OlMahumGeneral(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_firstTimeAttacked)
		{
			_firstTimeAttacked = false;
			if(Rnd.chance(25))
				Functions.npcSay(_thisActor, Say2C.ALL, "Мы займемся сейчас твоим вопросом!");//TODO: Найти fString и заменить.
		}
		else if(Rnd.chance(10))
			Functions.npcSay(_thisActor, Say2C.ALL, "Не думай что я буду спокойно сносить твои оскорбления!");//TODO: Найти fString и заменить.
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}