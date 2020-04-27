package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 04.10.11 10:22
 */
public class Ssq2ElcardiaLibrary1 extends DefaultNpc
{
	public int p_TIMER_ID_TALK = 1000;
	public int p_TIMER_GAP_TALK = 10000;

	public Ssq2ElcardiaLibrary1(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(2000, p_TIMER_GAP_TALK);
		addTimer(p_TIMER_ID_TALK, p_TIMER_GAP_TALK + 500);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			_thisActor.c_ai0 = creature.getStoredId();
			addFollowDesire(creature, 9000);
		}
	}

	@Override
	protected void onEvtNoDesire()
	{
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(cha != null)
			addFollowDesire(cha, 9000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2000)
		{
			_thisActor.lookNeighbor(300);
			addTimer(2000, 5000);
		}
		else if(timerId == p_TIMER_ID_TALK)
		{
			L2Player player = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(player != null)
			{
				QuestState st = player.getQuestState(10293);
				if(st != null)
				{
					if(st.getMemoState() >= 1 && st.getMemoState() <= 2)
					{
						switch(Rnd.get(3))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1029350);
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1029351);
								break;
							case 2:
								Functions.npcSay(_thisActor, Say2C.ALL, 1029352);
								break;
						}
					}
					else if(st.getMemoState() >= 5 && st.getMemoState() <= 8)
					{
						switch(Rnd.get(2))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1029353);
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1029354);
								break;
						}
					}
				}
			}
			addTimer(p_TIMER_ID_TALK, p_TIMER_GAP_TALK);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
