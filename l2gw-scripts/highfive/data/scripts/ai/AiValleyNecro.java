package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 13:20
 */
public class AiValleyNecro extends WizardUseSkill
{
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(448790529);

	public AiValleyNecro(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.600000))
		{
			if(Rnd.get(10) < 1)
			{
				_thisActor.c_ai0 = attacker.getStoredId();
				addUseSkillDesire(_thisActor, summonSkill, 1, 0, 9900000000L);
				if(Rnd.get(2) < 1)
				{
					_thisActor.createOnePrivate(22818, "AiMalukSummonBoomer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 0, 0);
				}
				else
				{
					_thisActor.createOnePrivate(22819, "AiMalukSummonZombie", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 0, 0);
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}
}
