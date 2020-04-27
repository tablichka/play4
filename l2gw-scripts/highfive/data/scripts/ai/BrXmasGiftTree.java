package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.base.ItemData;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;

/**
 * @author: rage
 * @date: 30.12.11 12:49
 */
public class BrXmasGiftTree extends Citizen
{
	public BrXmasGiftTree(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1001, Config.EVENT_XMAS_DROP_TIME + Rnd.get(-Config.EVENT_XMAS_DROP_TIME_RAND, Config.EVENT_XMAS_DROP_TIME_RAND));
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(Config.EVENT_XMAS_GIFT_ITEMS != null && Config.EVENT_XMAS_GIFT_ITEMS.length > 0)
			{
				int chance = Rnd.get(100000000);
				for(ItemData id : Config.EVENT_XMAS_GIFT_ITEMS)
				{
					if(chance < id.chance)
					{
						_thisActor.broadcastPacket(new MagicSkillUse(_thisActor, _thisActor, 21006, 1, 2000, 0, false));
						for(ItemData item : id.items)
						{
							_thisActor.dropItem(item.item_id, 1 + Rnd.get((int) item.count));
						}
						break;
					}
					chance -= id.chance;
				}
			}
			addTimer(1001, Config.EVENT_XMAS_DROP_TIME + Rnd.get(-Config.EVENT_XMAS_DROP_TIME_RAND, Config.EVENT_XMAS_DROP_TIME_RAND));
		}
	}
}