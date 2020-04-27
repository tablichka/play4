package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 03.09.11 16:13
 */
public class AiMalukSummonZombie extends WarriorUseSkill
{
	public AiMalukSummonZombie(L2Character actor)
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
		}

		super.onEvtSpawn();
	}
}
