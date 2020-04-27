package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 17.09.11 22:41
 */
public class KegorNpc extends Citizen
{
	public KegorNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 1)
		{
			_thisActor.showPage(talker, "kegor_npc_q10285_01.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 2)
		{
			_thisActor.showPage(talker, "kegor_npc_q10285_03.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 3)
		{
			_thisActor.showPage(talker, "kegor_npc_q10285_04.htm", 10285);
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_1") == 1 && talker.getQuestState(10287).getInt("ex_2") == 0)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				_thisActor.showPage(talker, "kegor_q10287_01.htm", 10287);
			}
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_1") == 0 && talker.getQuestState(10287).getInt("ex_2") == 0)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				_thisActor.showPage(talker, "kegor_q10287_02.htm", 10287);
			}
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_2") == 1)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				_thisActor.showPage(talker, "kegor_q10287_05.htm", 10287);
			}
		}
		else
			return super.onTalk(talker);

		return true;
	}
}