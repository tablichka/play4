package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Ranger;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * AI для Delu Lizardman Special Agent ID: 21105
 *
 * @author Diamond
 */
public class DeluLizardmanSpecialAgent extends Ranger
{
	private boolean _firstTimeAttacked = true;

	public DeluLizardmanSpecialAgent(L2Character actor)
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
				Functions.npcSay(_thisActor, Say2C.ALL, "Какого черта ты вмешиваешся не в свое дело? Эй парни, на помощь!");//TODO: Найти fString и заменить.
		}
		else if(Rnd.chance(10))
			Functions.npcSay(_thisActor, Say2C.ALL, "Эй! Мы будем дратся здесь!");//TODO: Найти fString и заменить.
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}