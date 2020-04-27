package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 03.09.11 13:38
 */
public class AiDrakosAssasin extends DetectPartyWarrior
{
	public AiDrakosAssasin(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(cha != null)
		{
			_thisActor.addDamageHate(cha, 0, 10000);
			addAttackDesire(cha, 0, 10000);
		}
	}
}
