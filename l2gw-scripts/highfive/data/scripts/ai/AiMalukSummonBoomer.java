package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 16:09
 */
public class AiMalukSummonBoomer extends WarriorUseSkill
{
	public L2Skill kamikazeSkill = SkillTable.getInstance().getInfo(448921601);

	public AiMalukSummonBoomer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(cha != null)
		{
			addAttackDesire(cha, 1, 10000);
			addUseSkillDesire(cha, kamikazeSkill, 0, 1, 999999999);
		}

		super.onEvtSpawn();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == kamikazeSkill)
			_thisActor.doDie(null);
	}
}
