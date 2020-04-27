package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 09.09.11 6:18
 */
public class AiBeastfarmTamer extends Citizen
{
	public AiBeastfarmTamer(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		return false;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		int i0 = 0;
		int i1 = 0;
		if(eventId == 21150002)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			switch((Integer) arg2)
			{
				case 11:
					i0 = 18869;
					i1 = 1;
					break;
				case 12:
					i0 = 18869;
					i1 = 2;
					break;
				case 21:
					i0 = 18870;
					i1 = 1;
					break;
				case 22:
					i0 = 18870;
					i1 = 2;
					break;
				case 31:
					i0 = 18871;
					i1 = 1;
					break;
				case 32:
					i0 = 18871;
					i1 = 2;
					break;
				case 41:
					i0 = 18872;
					i1 = 1;
					break;
				case 42:
					i0 = 18872;
					i1 = 2;
					break;
			}
			if(c0 != null)
			{
				_thisActor.createOnePrivate(i0, "AiBeastfarmTamed", 0, 0, c0.getX(), c0.getY(), c0.getZ(), c0.getHeading() * 182, (Long) arg1, i1, 0);
			}
		}
	}
}
