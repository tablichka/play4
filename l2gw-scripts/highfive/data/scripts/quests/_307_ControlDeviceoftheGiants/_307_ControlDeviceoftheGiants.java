package quests._307_ControlDeviceoftheGiants;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

public class _307_ControlDeviceoftheGiants extends Quest
{
	// NPCs
	private static final int giant_q_dwarf = 32711;

	// Mobs
	private static final int giant_marpanak_re = 25680;
	private static final int gorgolos_re = 25681;
	private static final int last_lesser_utenus_re = 25684;
	private static final int hekaton_prime_re = 25687;

	public _307_ControlDeviceoftheGiants()
	{
		super(307, "_307_ControlDeviceoftheGiants", "Control Device of the Giants");
		addStartNpc(giant_q_dwarf);
		addTalkId(giant_q_dwarf);

		addKillId(giant_marpanak_re, gorgolos_re, last_lesser_utenus_re, hekaton_prime_re);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == giant_q_dwarf)
		{
			if(st.isCreated() && talker.getLevel() >= 79)
				return "giant_q_dwarf_q0307_01.htm";
			if(st.isCreated() && talker.getLevel() < 79)
				return "giant_q_dwarf_q0307_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && (st.getQuestItemsCount(14851) < 1 || st.getQuestItemsCount(14852) < 1 || st.getQuestItemsCount(14853) < 1))
					return "npchtm:giant_q_dwarf_q0307_07.htm";

				if(st.getMemoState() == 1 && st.getQuestItemsCount(14851) >= 1 && st.getQuestItemsCount(14852) >= 1 && st.getQuestItemsCount(14853) >= 1)
					return "npchtm:giant_q_dwarf_q0307_08.htm";

				if(st.getMemoState() == 2)
				{
					st.giveItems(14850, 1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:giant_q_dwarf_q0307_10.htm";
				}

			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == giant_q_dwarf)
		{
			if(reply == 307)
			{
				if(st.isCreated() && talker.getLevel() >= 79)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					if(st.getQuestItemsCount(14851) < 1 || st.getQuestItemsCount(14852) < 1 || st.getQuestItemsCount(14853) < 1)
					{
						showPage("giant_q_dwarf_q0307_04.htm", talker);
					}
					else if(st.getQuestItemsCount(14851) >= 1 && st.getQuestItemsCount(14852) >= 1 && st.getQuestItemsCount(14853) >= 1)
				   	{
						showPage("giant_q_dwarf_q0307_04a.htm", talker);
					}
				}
			}
			else if( reply == 1 )
			{
				if( st.isCreated() && talker.getLevel() >= 79 )
				{
					showQuestPage("giant_q_dwarf_q0307_03.htm", talker);
				}
			}
			else if( reply == 11 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("giant_q_dwarf_q0307_05.htm", talker);
				}
			}
			else if( reply == 21 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("giant_q_dwarf_q0307_05a.htm", talker);
					talker.radar.deleteAll(2);
					st.showRadar(186214, 61591, -4152, 2);
				}
			}
			else if( reply == 22 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("giant_q_dwarf_q0307_05b.htm", talker);
					talker.radar.deleteAll(2);
					st.showRadar(187554, 60800, -4984, 2);
				}
			}
			else if( reply == 23 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("giant_q_dwarf_q0307_05c.htm", talker);
					talker.radar.deleteAll(2);
					st.showRadar(193432, 53922, -4368, 2);
				}
			}
			else if( reply == 12 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("giant_q_dwarf_q0307_06.htm", talker);
				}
			}
			else if( reply == 40 )
			{
				if( st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(14851) >= 1 && st.getQuestItemsCount(14852) >= 1 && st.getQuestItemsCount(14853) >= 1)
				{
					if(ServerVariables.getInt("GM_39", -1) == 1 )
					{
						showPage("giant_q_dwarf_q0307_09.htm", talker);
						st.takeItems(14851, 1);
						st.takeItems(14852, 1);
						st.takeItems(14853, 1);
						npc.getAI().broadcastScriptEvent(2519001, 0, null, 5000);
						((DefaultAI) npc.getAI()).addMoveToDesire(192062, 57357, -7650, 1000);
						npc.getAI().addTimer(2519007, 5000);
					}
					else
					{
						showPage("giant_q_dwarf_q0307_09a.htm", talker);
					}
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == hekaton_prime_re)
		{
			L2Party party = Util.getParty(killer);
			if(party != null)
			{
				L2CommandChannel cc = party.getCommandChannel();
				if(cc != null)
				{
					int c = 0;
					for(L2Player member : cc.getMembers())
					{
						if(c >= 18)
							break;

						QuestState qs = member.getQuestState(307);
						if(qs != null && qs.isStarted() && qs.getMemoState() == 1)
						{
							qs.setMemoState(2);
							qs.setCond(2);
							showQuestMark(member);
							qs.playSound(SOUND_MIDDLE);
							c++;
						}
					}
				}
				else
				{
					for(L2Player member : party.getPartyMembers())
					{
						QuestState qs = member.getQuestState(307);
						if(qs != null && qs.isStarted() && qs.getMemoState() == 1)
						{
							qs.setMemoState(2);
							qs.setCond(2);
							showQuestMark(member);
							qs.playSound(SOUND_MIDDLE);
						}
					}
				}
			}
		}
		else
		{
			QuestState st = getRandomPartyMemberWithMemoState(killer, 1);
			if(st != null)
			{
				if(npc.getNpcId() == giant_marpanak_re)
				{
					st.giveItems(14853, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(npc.getNpcId() == gorgolos_re)
				{
					st.giveItems(14851, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(npc.getNpcId() == last_lesser_utenus_re)
				{
					st.giveItems(14852, 1);
					st.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}