package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * AI мобов Prison Guard на Isle of Prayer. Не используют функцию Random Walk. Ругаются на атаковавших чаров без эффекта Event Timer. Ставят
 * в петрификацию атаковавших чаров без эффекта Event Timer. TODO: Не могут быть убиты чарами без эффекта Event Timer. Не проявляют агресии к чарам
 * без эффекта Event Timer
 * ID: 18367, 18368
 *
 * @author SYS
 */
public class PrisonGuard extends Fighter
{
	private static final int EVENT_TIMER_ID = 5239;

	public PrisonGuard(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return target.getEffectBySkillId(EVENT_TIMER_ID) != null && super.checkAggression(target);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		L2Character eventParticipant = attacker;
		if(attacker.isSummon() || attacker.isPet())
			eventParticipant = attacker.getPlayer();

		if(eventParticipant.getEffectBySkillId(EVENT_TIMER_ID) != null)
		{
			super.onEvtAttacked(attacker, damage, skill);
		}
		else
		{
			if(_thisActor.getNpcId() == 18368)
				Functions.npcSay(_thisActor, Say2C.ALL, 1800108);
			else if(_thisActor.getNpcId() == 18367)
				Functions.npcSay(_thisActor, Say2C.ALL, 1800107);

			L2Skill petrification = SkillTable.getInstance().getInfo(4578, 1); // Petrification
			_actor.doCast(petrification, attacker, true);
		}
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	protected void onIntentionAttack(L2Character target)
	{
		if(_thisActor.getNpcId() == 18368)
			super.onIntentionAttack(target);
	}
	
}