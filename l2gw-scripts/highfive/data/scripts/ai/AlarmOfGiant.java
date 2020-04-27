package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.PlaySound;

/**
 * @author admin
 * @date 24.12.10 12:29
 * AI для аларм девайса по квестам 184, 185
 */
public class AlarmOfGiant extends L2CharacterAI
{
	private L2NpcInstance _thisActor;

	public AlarmOfGiant(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(18401, 60000);
		Functions.npcSay(_thisActor, Say2C.ALL, 18451);
		_thisActor.broadcastPacket(new PlaySound(0, "ItemSound3.sys_siren", 0, 0, _thisActor.getLoc()));
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 18401)
		{
			addTimer(18402, 30000);
			Functions.npcSay(_thisActor, Say2C.ALL, 18452);
		}
		else if(timerId == 18402)
		{
			addTimer(18403, 20000);
			Functions.npcSay(_thisActor, Say2C.ALL, 18453);
		}
		else if(timerId == 18403)
		{
			addTimer(18404, 10000);
			Functions.npcSay(_thisActor, Say2C.ALL, 18454);
		}
		else if(timerId == 18404)
		{
			L2Object npc = L2ObjectsStorage.findObject(_thisActor.i_quest0);
			if(npc instanceof L2NpcInstance && ((L2NpcInstance) npc).i_quest0 == 1)
				((L2NpcInstance) npc).i_quest0 = 0;

			L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_quest1);
			if(player != null)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 18455);
				QuestState qs = player.getQuestState(185);
				if(qs != null)
					qs.setMemoState(5);
				else if((qs = player.getQuestState(184)) != null)
					qs.setMemoState(5);
			}
			_thisActor.deleteMe();
		}
	}

	@Override
	public boolean onTalk(L2Player player)
	{
		QuestState qs = player.getQuestState(184);
		player.setLastNpc(_thisActor);
		if(qs != null)
		{
			if(qs.isStarted() && qs.getMemoState() == 3 && _thisActor.i_quest1 == player.getObjectId())
				qs.getQuest().notifyEvent("alarm_of_giant_q0184001.htm", qs);
			else
				qs.getQuest().notifyEvent("alarm_of_giant_q0184002.htm", qs);
			return true;
		}
		else if((qs = player.getQuestState(185)) != null)
		{
			if(qs.isStarted() && qs.getMemoState() == 3 && _thisActor.i_quest1 == player.getObjectId())
				qs.getQuest().notifyEvent("alarm_of_giant_q0184001.htm", qs);
			else
				qs.getQuest().notifyEvent("alarm_of_giant_q0184002.htm", qs);
			return true;
		}
		return false;
	}
}
