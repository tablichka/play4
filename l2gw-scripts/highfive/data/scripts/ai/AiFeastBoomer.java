package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 15:23
 */
public class AiFeastBoomer extends DetectPartyWarrior
{
	public L2Skill selfBlasting = SkillTable.getInstance().getInfo(448135169);

	public AiFeastBoomer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(cha != null)
		{
			_thisActor.addDamageHate(cha, 0, 10000);
			addAttackDesire(cha, 1, 10000);
			addUseSkillDesire(cha, selfBlasting, 0, 1, 999999999L);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == selfBlasting)
		{
			_thisActor.doDie(null);
		}
	}
}
