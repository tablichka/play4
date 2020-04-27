package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 29.09.11 19:59
 */
public class WizardCorpseVampireBasic extends WizardDdmagic2
{
	public L2Skill DeBuff = SkillTable.getInstance().getInfo(272039937);
	public L2Skill DDMagic = SkillTable.getInstance().getInfo(272039937);

	public WizardCorpseVampireBasic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 90 && !_thisActor.isMoving)
				{
					if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
				}
				if(_thisActor.getMostHated() == attacker)
				{
					if(Rnd.get(100) < 33 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 50 && _thisActor.i_ai0 == 0)
					{
						if(DDMagic.getMpConsume() < _thisActor.getCurrentMp() && DDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic.getId()))
						{
							addUseSkillDesire(attacker, DDMagic, 0, 1, 1000000);
						}
						_thisActor.i_ai0 = 1;
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}
}