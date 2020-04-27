package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 17.09.11 23:16
 */
public class FreyaDeacon extends Citizen
{
	public String szName = "freya_deacon_q0656";
	public String DoorName1 = "ice_barrier_001";
	public String DoorName2 = "ice_barrier_002";
	public String fnHi2 = "freya_deacon002.htm";

	public FreyaDeacon(L2Character actor)
	{
		super(actor);
		fnHi = "freya_deacon001.htm";
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.c_ai0 = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 2)
		{
			QuestState st = talker.getQuestState(10285);
			_thisActor.showPage(talker, "freya_deacon_q10285_01.htm", 10285);
			st.setCond(8);
			st.getQuest().showQuestMark(st.getPlayer());
			st.playSound(Quest.SOUND_MIDDLE);
		}
		else
		{
			L2Party party0 = talker.getParty();
			if(party0 != null)
			{
				if(_thisActor.i_ai2 == 1 && _thisActor.i_ai1 == party0.getPartyId())
				{
					_thisActor.showPage(talker, fnHi2);
				}
				else if(_thisActor.i_ai2 == 1 && _thisActor.i_ai1 != party0.getPartyId())
				{
					_thisActor.showPage(talker, szName + "_01.htm");
				}
				else if(_thisActor.i_ai2 == 0)
				{
					_thisActor.showPage(talker, fnHi);
				}
			}
			else if(_thisActor.i_ai2 == 1)
			{
				_thisActor.showPage(talker, szName + "_01.htm");
			}
			else if(_thisActor.i_ai2 == 0)
			{
				_thisActor.showPage(talker, fnHi);
			}
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 656)
		{
			if(reply == 1)
			{
				if(talker.getLevel() >= 82)
				{
					talker.teleToLocation(103045, -124361, -2768);
				}
				else
				{
					L2Party party0 = talker.getParty();
					if(_thisActor.i_ai2 == 0)
					{
						if(party0 == null)
						{
							_thisActor.showPage(talker, szName + "_06.htm");
						}
						else if(_thisActor.i_ai1 == 0 || party0.getPartyId() == _thisActor.i_ai1)
						{
							if(party0.getPartyLeader() == talker)
							{
								for(L2Player c0 : party0.getPartyMembers())
								{
									if(c0 != null && c0.getItemCountByItemId(8057) < 10)
									{
										String fhtml0 = _thisActor.getHtmlFile(talker, szName + "_03.htm");
										fhtml0 = fhtml0.replace("<?name?>", c0.getName());
										_thisActor.showHtml(talker, fhtml0);
										return;
									}
								}

								for(L2Player c0 : party0.getPartyMembers())
								{
									if(c0 != null)
									{
										c0.destroyItemByItemId("Quest", 8057, 10, _thisActor, true);
									}
								}

								talker.addItem("Quest", 8379, 3, _thisActor, true);
								_thisActor.showPage(talker, szName + "_05.htm");

								_thisActor.i_ai4 = party0.getMemberCount();
								for(L2Player c0 : party0.getPartyMembers())
								{
									Functions.showOnScreentMsg(c0, 2, 0, 0, 0, 1, 0, 100000, 0, 1121000);
								}

								Functions.npcSay(_thisActor, Say2C.SHOUT, 1121005);
								addTimer(1005, 120000);
								_thisActor.i_ai2 = 1;
								_thisActor.i_ai1 = party0.getPartyId();
								_thisActor.c_ai0 = talker.getStoredId();
							}
							else
							{
								_thisActor.showPage(talker, szName + "_02.htm");
							}
						}
					}
					else
					{
						_thisActor.showPage(talker, szName + "_01.htm");
					}
				}
			}
		}
		else if(ask == -2317)
		{
			if(reply == 1)
			{
				if(talker.getLevel() >= 82)
				{
					talker.teleToLocation(103045, -124361, -2768);
				}
				else
				{
					_thisActor.showPage(talker, "freya_deacon_q0656_01a.htm");
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10026)
		{
			L2Player talker = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(talker != null)
			{
				L2Party party0 = talker.getParty();
				if(party0 != null)
				{
					for(L2Player c0 : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(c0, 2, 0, 0, 0, 1, 0, 10000, 0, 1121004);
					}
				}
			}

			broadcastScriptEvent(11039, 0, null, 8000);
			DoorTable.getInstance().doorOpenClose(DoorName1, 0);
			DoorTable.getInstance().doorOpenClose(DoorName2, 0);
			_thisActor.i_ai3 = 1;
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai1 = 0;
			_thisActor.c_ai0 = 0;
		}
		else if(eventId == 10005)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_3m1");
			if(maker0 != null)
			{
				maker0.onScriptEvent(10025, 0, 0);
			}
			L2Player talker = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(talker != null)
			{
				L2Party party0 = talker.getParty();
				if(party0 != null)
				{
					for(L2Player c0 : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(c0, 2, 0, 0, 0, 1, 0, 10000, 0, 1121003);
					}
				}
			}
			broadcastScriptEvent(11039, 0, null, 8000);
			DoorTable.getInstance().doorOpenClose(DoorName1, 0);
			DoorTable.getInstance().doorOpenClose(DoorName2, 0);
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai1 = 0;
			_thisActor.c_ai0 = 0;
		}
		else if(eventId == 11037)
		{
			_thisActor.i_ai3 = 0;
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2002)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);

			if(c0 == null)
			{
				return;
			}

			L2Party party0 = c0.getParty();
			if(party0 != null)
			{
				int i1 = party0.getMemberCount();
				if(_thisActor.i_ai4 >= i1)
				{
					for(L2Player p0 : party0.getPartyMembers())
					{
						// myself.TeleportParty(_thisActor.i_ai1, 113533, -126159, -3488, 1000, 0); ??
						p0.teleToLocation(113533, -126159, -3488);
					}

					DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_mb2314_05m1");
					if(maker0 != null)
					{
						maker0.onScriptEvent(11040, _thisActor.c_ai0, 0);
					}
				}
				else
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1121007);
					_thisActor.i_ai2 = 0;
					_thisActor.i_ai1 = 0;
					_thisActor.c_ai0 = 0;
					DoorTable.getInstance().doorOpenClose(DoorName1, 0);
					DoorTable.getInstance().doorOpenClose(DoorName2, 0);
				}
			}
		}
		else if(timerId == 2003)
		{
			if(_thisActor.i_ai3 == 0)
			{
				L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);

				if(c0 != null)
				{
					L2Party party0 = c0.getParty();
					if(party0 != null)
					{
						for(L2Player c1 : party0.getPartyMembers())
						{
							Functions.showOnScreentMsg(c1, 2, 0, 0, 0, 1, 0, 10000, 0, 1010643, "30");
						}
					}
				}
				addTimer(2004, 600000);
			}
		}
		else if(timerId == 2004)
		{
			if(_thisActor.i_ai3 == 0)
			{
				L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
				if(c0 != null)
				{
					L2Party party0 = c0.getParty();
					if(party0 != null)
					{
						for(L2Player c1 : party0.getPartyMembers())
						{
							Functions.showOnScreentMsg(c1, 2, 0, 0, 0, 1, 0, 10000, 0, 1010643, "20");
						}
					}
				}
			}
		}
		else if(timerId == 1005)
		{
			long i0 = ServerVariables.getLong("GM_" + 7, -1);
			L2Character c0 = null;
			if(i0 != -1)
			{
				c0 = L2ObjectsStorage.getAsCharacter(i0);
			}

			if(c0 != null)
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 10027, 0, 0);
			}

			DoorTable.getInstance().doorOpenClose(DoorName1, 1);
			DoorTable.getInstance().doorOpenClose(DoorName2, 1);
			addTimer(1006, 5000);
			addTimer(2002, 5000);
			_thisActor.i_ai3 = 0;
		}
		else if(timerId == 1006)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				L2Party party0 = c0.getParty();
				if(party0 != null)
				{
					for(L2Player c1 : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(c1, 2, 0, 0, 0, 1, 0, 10000, 0, 1121001);
					}
				}
			}
		}
	}
}