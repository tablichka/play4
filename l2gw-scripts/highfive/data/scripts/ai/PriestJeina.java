package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;

/**
 * @author: rage
 * @date: 29.09.11 20:51
 */
public class PriestJeina extends Citizen
{
	public PriestJeina(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 198)
		{
			if(reply == 2)
			{
				if(talker.isQuestStarted(198) && talker.getQuestState(198).getCond() >= 1)
				{
					_thisActor.showPage(talker, "priest_jeina_q0198_02.htm", 198);
					talker.teleToClosestTown();
					Instance inst = _thisActor.getInstanceZone();
					if(inst != null)
						inst.stopInstance();
				}
				else if(talker.isQuestStarted(10292) && talker.getQuestState(10292).getMemoState() >= 1)
				{
					_thisActor.showPage(talker, "priest_jeina_q0198_02.htm", 198);
					talker.teleToClosestTown();
					Instance inst = _thisActor.getInstanceZone();
					if(inst != null)
						inst.stopInstance();
				}
				else
				{
					_thisActor.showPage(talker, "priest_jeina_q0198_02.htm", 198);
					talker.teleToClosestTown();
					Instance inst = _thisActor.getInstanceZone();
					if(inst != null)
						inst.stopInstance();
				}
			}
			else if(reply == 3)
			{
				if(talker.isQuestStarted(198) && talker.getQuestState(198).getCond() >= 1)
				{
					_thisActor.showPage(talker, "priest_jeina_q0198_02a.htm", 198);
				}
				else if(talker.isQuestStarted(10292) && talker.getQuestState(10292).getMemoState() >= 1)
				{
					_thisActor.showPage(talker, "priest_jeina_q0198_02a.htm", 198);
				}
			}
			else if(reply == 1)
			{
				_thisActor.showPage(talker, "priest_jeina_q0198_01.htm", 198);
			}
		}
	}
}