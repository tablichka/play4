package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 21.09.11 19:47
 */
public class PartyPrivateCastingEnchant extends PartyPrivate
{
	public L2Skill Buff = SkillTable.getInstance().getInfo(263979009);

	public PartyPrivateCastingEnchant(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.i_ai1 == 0 && Rnd.get(100) < 33 && (((_thisActor.getCurrentHp() / _thisActor.getMaxHp()) * 100)) > 50)
			{
				if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
				{
					addUseSkillDesire(_thisActor, Buff, 1, 1, 1000000);
				}
				_thisActor.i_ai1 = 1;
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 11039)
		{
			_thisActor.onDecay();
		}
	}
}
