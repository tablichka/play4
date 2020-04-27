package ai;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SocialAction;

/**
 * @author: rage
 * @date: 23.09.11 18:31
 */
public class JiniaNpc2 extends Citizen
{
	public int inzone_id1 = 139;
	public int inzone_id2 = 144;
	public int enter_type = 2;
	public int TIMER_delay = 2314901;
	public int TIMER_delay2 = 2314902;
	public int position = -1;
	public int debug_mode = 0;

	public JiniaNpc2(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 2)
		{
			_thisActor.showPage(talker, "jinia_npc2_q10285_01.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 3)
		{
			_thisActor.showPage(talker, "jinia_npc2_q10285_08.htm", 10285);
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 2)
		{
			_thisActor.showPage(talker, "jinia_npc2_q10286_01.htm", 10286);
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 10)
		{
			QuestState st = talker.getQuestState(10286);
			st.addExpAndSp(2152200, 181070);
			st.exitCurrentQuest(false);
			st.playSound(Quest.SOUND_FINISH);
			_thisActor.showPage(talker, "jinia_npc2_q10286_08.htm", 10286);
		}
		else if(talker.getLevel() < 82)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else
		{
			_thisActor.showPage(talker, "jinia_npc2002.htm");
		}
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -2314 )
		{
			switch(reply)
			{
				case 1:
					Instance inst = InstanceManager.getInstance().getInstanceByPlayer(talker);
					if(inst != null && inst.getTemplate().getId() == inzone_id1 && InstanceManager.enterInstance(inzone_id1, talker, _thisActor, 0))
					{
						return;
					}
					if(talker.getParty() == null || !talker.getParty().isLeader(talker))
					{
						_thisActor.showPage(talker, "jinia_npc2007.htm");
					}
					else
					{
						InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(inzone_id1);
						if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(inzone_id1) >= it.getMaxCount())
						{
							_thisActor.showPage(talker, "jinia_npc2_q10286_10.htm", 10286);
						}
						else if(InstanceManager.enterInstance(inzone_id1, talker, _thisActor, 0))
						{
							setQuestOnEnter(talker);
						}
					}
					//myself.InstantZone_Enter(talker, inzone_id1, enter_type);
					break;
				case 2:
					_thisActor.l_ai5 = talker.getStoredId();
					addTimer(TIMER_delay, 2000);
					break;
				case 3:
					if( talker.getItemCountByItemId(15469) > 0 || talker.getItemCountByItemId(15470) > 0 )
					{
						_thisActor.showPage(talker, "jinia_npc2009.htm");
					}
					else if( talker.isQuestComplete(10286) )
					{
						_thisActor.showPage(talker, "jinia_npc2008.htm");
						talker.addItem("Quest", 15469, 1, _thisActor, true);
					}
					else if( !talker.isQuestComplete(10286) )
					{
						_thisActor.showPage(talker, "jinia_npc2008.htm");
						talker.addItem("Quest", 15470, 1, _thisActor, true);
					}
					break;
			}
		}
		else if( ask == 10286 )
		{
			if( reply == 2 )
			{
				if(talker.getParty() == null || !talker.getParty().isLeader(talker))
				{
					_thisActor.showPage(talker, "jinia_npc2007.htm");
				}
				else
				{
					InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(inzone_id1);
					if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(inzone_id1) >= it.getMaxCount())
					{
						_thisActor.showPage(talker, "jinia_npc2_q10286_10.htm", 10286);
					}
					else if(InstanceManager.enterInstance(inzone_id1, talker, _thisActor, 0))
					{
						setQuestOnEnter(talker);
					}
				}
			}
		}
	}

	private void setQuestOnEnter(L2Player talker)
	{
		L2Party party = talker.getParty();
		if(party != null)
		{
			L2CommandChannel cc = party.getCommandChannel();
			if(cc != null)
			{
				for(L2Player member : cc.getMembers())
				{
					if(member != null && member.isQuestStarted(10286) && member.getQuestState(10286).getMemoState() == 2)
					{
						QuestState st = member.getQuestState(10286);
						st.setCond(6);
						st.getQuest().showQuestMark(st.getPlayer());
						st.playSound(Quest.SOUND_MIDDLE);
					}
				}
			}
		}
	}

	/*
	EventHandler INSTANT_ZONE_ENTER_RETURNED(talker,reply,state,party0,i0,i1,i2,i3,i4,i5,i6,c0,c1)
	{
		if( state == 0 )
		{
			if( reply == 0 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 2 )
				{
					_thisActor.showPage(talker, "jinia_npc2_q10285_10.htm");
				}
				else if( talker.isQuestStarted(10286) && st.getMemoState() == 2 )
				{
					_thisActor.showPage(talker, "jinia_npc2_q10286_10.htm");
				}
			}
			else if( reply == 1 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 2 )
				{
					st.setCond(9);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else if( talker.isPlayer() && talker != null )
				{
					if( talker != myself.GetLeaderOfParty(gg.GetParty(talker)) )
					{
						_thisActor.showPage(talker, "jinia_npc2007.htm");
					}
					else
					{
						int i0 = myself.MPCC_GetMPCCId(talker);
						int i1 = myself.MPCC_GetPartyCount(i0);
						if( i0 > 0 )
						{
							for(int i2 = 0; i2 < i1; i2 = ( i2 + 1 ))
							{
								int i6 = myself.MPCC_GetPartyID(i0, i2);
								party0 = gg.GetPartyFromID(i6);
								int i4 = party0.member_count;
								for(int i5 = 0; i5 < i4; i5 = ( i5 + 1 ))
								{
									L2Character c1 = myself.GetMemberOfParty(party0, i5);
									if( c1 != null )
									{
										if( c1.isQuestStarted(10286) && st.getMemoState() == 2 )
										{
											st.setCond(6);
											showQuestMark(st.getPlayer());
											st.playSound(SOUND_MIDDLE);
										}
										if( c1.transformID == 260 || c1.transformID == 8 || c1.transformID == 9 )
										{
											myself.ShowSystemMessage(talker, 2924);
											return;
										}
									}
								}
							}
						}
						else if( talker.transformID == 260 || talker.transformID == 8 || talker.transformID == 9 )
						{
							myself.ShowSystemMessage(talker, 2924);
							return;
						}
					}
				}
			}
		}
		else if( state == 1 )
		{
		}
	}
	*/

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_delay)
		{
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 3));
			addTimer(TIMER_delay2, 2000);
		}
		else if(timerId == TIMER_delay2)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai5);
			if(c0 != null)
			{
				if(InstanceManager.enterInstance(inzone_id2, c0, _thisActor, 0))
				{
					setQuestOnEnter(c0);
				}
				//myself.InstantZone_Enter(c0, inzone_id2, enter_type);
			}
		}
	}
}
