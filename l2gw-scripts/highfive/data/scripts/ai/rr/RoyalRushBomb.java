package ai.rr;

import ai.base.Warrior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 17:13
 */
public class RoyalRushBomb extends Warrior
{
	public L2Skill SelfRangeDDMagic = SkillTable.getInstance().getInfo(277348353);
	public int SelfRangeDDMagicRate = 33;
	public int DDMagicUseHpRate = 30;

	public RoyalRushBomb(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(Rnd.get(100) < SelfRangeDDMagicRate && (_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) < DDMagicUseHpRate)
		{
			if(SelfRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeDDMagic.getId()))
			{
				addUseSkillDesire(_thisActor, SelfRangeDDMagic, 0, 1, 1000000);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		_thisActor.onDecay();
	}
}