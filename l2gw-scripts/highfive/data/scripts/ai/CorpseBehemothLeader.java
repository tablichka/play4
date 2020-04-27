package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 24.09.11 19:11
 */
public class CorpseBehemothLeader extends Citizen
{
	public String AreaName = "24_21_skill_behemoth_leader";
	public int DESPAWN_TIME = 5000;
	private GCSArray<Long> attackers;

	public CorpseBehemothLeader(L2Character actor)
	{
		super(actor);
		attackers = new GCSArray<>();
	}

	@Override
	protected void onEvtSpawn()
	{
		attackers.clear();
		_thisActor.lookNeighbor(1500);
		ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
		broadcastScriptEvent(15008, 0, null, 5000);
		broadcastScriptEvent(15007, 0, null, 5000);
		addTimer(DESPAWN_TIME, 30 * 60 * 1000);
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
		if(c0 != null)
		{
			_thisActor.changeFStrMasterName(1120301, c0.getName());
		}
		c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 15005, _thisActor.getStoredId(), null);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && !attackers.contains(creature.getStoredId()))
		{
			attackers.add(creature.getStoredId());
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15006)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null && !attackers.contains((Long) arg1))
			{
				attackers.add((Long) arg1);
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		int i0 = Util.getMPCCId(talker);
		if(talker.isQuestStarted(456) && talker.getQuestState(456).getMemoState() == 1)
		{
			if(attackers.contains(talker.getStoredId()) && i0 == _thisActor.param3 && i0 > 0)
			{
				if(talker.getItemCountByItemId(17252) >= 1)
				{
					_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_03.htm", 456);
				}
				else
				{
					_thisActor.showPage(talker, "corpse_behemoth_leader001.htm");
				}
			}
			else if(!attackers.contains(talker.getStoredId()))
			{
				_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_02.htm", 456);
			}
			else if(i0 == 0)
			{
				_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_02.htm", 456);
			}
			else if(i0 != _thisActor.param3)
			{
				_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_02.htm", 456);
			}
			else
			{
				_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_02.htm", 456);
			}
		}
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(talker.isQuestStarted(456) && talker.getQuestState(456).getMemoState() == 1)
		{
			int i0 = Util.getMPCCId(talker);
			if(attackers.contains(talker.getStoredId()) && i0 == _thisActor.param3 && i0 > 0)
			{
				if(ask == 456)
				{
					if(reply == 1)
					{
						if(talker.getItemCountByItemId(17251) == 0 || talker.getItemCountByItemId(17253) == 0)
						{
							talker.addItem("Quest", 17252, 1, _thisActor, true);
							_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_01.htm", 456);
							attackers.remove(talker.getStoredId());
						}
						else if(talker.getItemCountByItemId(17251) == 1 && talker.getItemCountByItemId(17253) == 1)
						{
							talker.addItem("Quest", 17252, 1, _thisActor, true);
							_thisActor.showPage(talker, "corpse_behemoth_leader_q0456_01.htm", 456);
							QuestState st = talker.getQuestState(456);
							st.setCond(2);
							st.getQuest().showQuestMark(talker);
							st.playSound(Quest.SOUND_MIDDLE);
							attackers.remove(talker.getStoredId());
						}
					}
				}
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DESPAWN_TIME)
		{
			_thisActor.onDecay();
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}