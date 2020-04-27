package ai;

import ai.base.AiTantaarLizardWizard;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 16:27
 */
public class AiTantaarLizardSummoner extends AiTantaarLizardWizard
{
	public L2Skill Self_Debuff = SkillTable.getInstance().getInfo(421068801);

	public AiTantaarLizardSummoner(L2Character actor)
	{
		super(actor);
		Max_Desire = 1000000000000000000L;
		Skill01_ID = SkillTable.getInstance().getInfo(437714945);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		super.onEvtAttacked(attacker, damage, skill);

		if(_thisActor.getCurrentHp() <= (_thisActor.getMaxHp() * 0.600000) && _thisActor.i_ai3 == 0 && attacker != null)
		{
			_thisActor.i_ai3 = 1;
			addUseSkillDesire(_thisActor, Self_Debuff, 0, 1, Max_Desire);
			_thisActor.createOnePrivate(22768, "AiTantaarLizardWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 0, 0);
			_thisActor.createOnePrivate(22768, "AiTantaarLizardWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 0, 0);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.c_ai0 != 0)
		{
			_thisActor.createOnePrivate(18919, "AiAuragrafter", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
		if(Rnd.get(1000) == 0 && _thisActor.getNpcId() != 18862)
		{
			_thisActor.createOnePrivate(18862, "TantaarLizardProtecter", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
		super.onEvtDead(killer);
	}
}
