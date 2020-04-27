package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.10.11 14:29
 */
public class PartyPrivatePhysicalspecialHeal extends PartyPrivatePhysicalspecial
{
	public L2Skill MagicHeal = SkillTable.getInstance().getInfo(458752001);

	public PartyPrivatePhysicalspecialHeal(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.getCurrentHp() <= (_thisActor.getMaxHp() / 2) && Rnd.get(100) < 33)
		{
			if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
			{
				addUseSkillDesire(_thisActor, MagicHeal, 0, 1, 1000000);
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(_thisActor.getCurrentHp() <= (_thisActor.getMaxHp() / 2) && victim != _thisActor && Rnd.get(100) < 33)
		{
			if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
			{
				addUseSkillDesire(victim, MagicHeal, 1, 1, 1000000);
			}
		}
		if(_thisActor.getLeader().getCurrentHp() <= (_thisActor.getLeader().getMaxHp() / 2) && Rnd.get(100) < 33)
		{
			if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
			{
				addUseSkillDesire(_thisActor.getLeader(), MagicHeal, 1, 1, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}
}