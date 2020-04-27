package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 04.09.11 20:38
 */
public class AiMalrukWarlord extends WarriorUseSkill
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(442499073);

	public AiMalrukWarlord(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() / 10 && _thisActor.i_ai1 == 0)
		{
			addUseSkillDesire(attacker, SpecialSkill01_ID, 1, 1, 50000000);
			_thisActor.i_ai1 = 1;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
