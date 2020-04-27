package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class DorianNight extends Fighter
{
	private GArray<Integer> _players;

	public DorianNight(L2Character actor)
	{
		super(actor);
		_players = new GArray<Integer>();

		addTimer(2001, 10000);
		lookNeighbors(400);

	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2001)
		{
			lookNeighbors(400);
			addTimer(2001, 10000);

		}
		else
			super.onEvtTimer(timerId, arg1, arg2);

	}

	private void seeCreature(L2Player player)
	{
		if(player.isQuestStarted(24) && player.getItemCountByItemId(7153) > 0)
		{
			QuestState st = player.getQuestState(24);
			if(st != null)
			{
				st.takeItems(7153, -1);
				st.giveItems(7154, 1);
				Functions.npcSay(_thisActor, Say2C.ALL, 2450);
				st.setCond(4);
			}
		}
	}

	private void lookNeighbors(int range)
	{
		for(L2Player player : _thisActor.getAroundPlayers(range))
			if(!_players.contains(player.getObjectId()))
			{
				_players.add(player.getObjectId());
				seeCreature(player);
			}

		for(int i = 0; i < _players.size(); i++)
		{
			L2Player player = L2ObjectsStorage.getPlayer(_players.get(i));
			if(player == null || !_thisActor.isInRange(player, 400))
				_players.remove(i);
		}
	}


}
