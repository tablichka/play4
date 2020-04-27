package quests._902_ReclaimOurEra;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 09.10.11 13:43
 */
public class _902_ReclaimOurEra extends Quest
{
	// NPC
	private static final int captain_mathias = 31340;

	// Mobs
	private static final int ketra_hero_hekaton = 25299;
	private static final int ketra_commander_tayr = 25302;
	private static final int ketra_chief_brakki = 25305;
	private static final int varka_hero_shadith = 25309;
	private static final int varka_commnder_mos = 25312;
	private static final int varka_chief_horuth = 25315;
	private static final int n_caanibal_stakato_sr = 25667;
	private static final int n_caanibal_stakato_sp = 25668;
	private static final int n_caanibal_stakato_wr = 25669;
	private static final int n_caanibal_stakato_wp = 25670;
	private static final int n_divine_anais = 25701;

	// Items
	private static final int q_g_broken_bone = 21997;
	private static final int q_g_stakato_craw = 21998;
	private static final int q_g_anais_scroll = 21999;
	private static final int g_seal_of_challenge = 21750;
	
	public _902_ReclaimOurEra()
	{
		super(902, "_902_ReclaimOurEra", "Reclaim Our Era");
		addStartNpc(captain_mathias);
		addTalkId(captain_mathias);

		addKillId(n_caanibal_stakato_sr, n_caanibal_stakato_sp, n_caanibal_stakato_wr, n_caanibal_stakato_wp);
		addKillId(ketra_chief_brakki, ketra_commander_tayr, ketra_hero_hekaton);
		addKillId(varka_hero_shadith, varka_commnder_mos, varka_chief_horuth);
		addKillId(n_divine_anais);

		addQuestItem(q_g_broken_bone, q_g_stakato_craw, q_g_anais_scroll);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == captain_mathias)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 80)
					return "captain_mathias_q0902_01.htm";
				return "captain_mathias_q0902_03.htm";
			}
			if(st.isCompleted())
				return "npchtm:captain_mathias_q0902_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					if(st.getInt("ex_1") == 1)
						return "npchtm:captain_mathias_q0902_11.htm";
					if(st.getInt("ex_2") == 1)
						return "npchtm:captain_mathias_q0902_12.htm";
					if(st.getInt("ex_3") == 1)
						return "npchtm:captain_mathias_q0902_13.htm";

					return "npchtm:captain_mathias_q0902_10.htm";
				}
				if(st.getMemoState() == 2)
				{
					if(st.getInt("ex_1") == 2)
					{
						st.takeItems(q_g_broken_bone, -1);
						st.rollAndGive(57, 134038, 100);
						st.giveItems(g_seal_of_challenge, 1);
					}
					else if(st.getInt("ex_2") == 2)
					{
						st.takeItems(q_g_stakato_craw, -1);
						st.rollAndGive(57, 210119, 100);
						st.giveItems(g_seal_of_challenge, 3);
					}
					else if(st.getInt("ex_3") == 2)
					{
						st.takeItems(q_g_anais_scroll, -1);
						st.rollAndGive(57, 348155, 100);
						st.giveItems(g_seal_of_challenge, 3);
					}
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					return "npchtm:captain_mathias_q0902_14.htm";
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

		if(npc.getNpcId() == captain_mathias)
		{
			if(reply == 902)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("captain_mathias_q0902_09.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("captain_mathias_q0902_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("captain_mathias_q0902_05.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && talker.getLevel() >= 80)
				{
					showQuestPage("captain_mathias_q0902_06.htm", talker);
					st.set("ex_1", 1);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && talker.getLevel() >= 80)
				{
					showQuestPage("captain_mathias_q0902_06.htm", talker);
					st.set("ex_2", 1);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && talker.getLevel() >= 80)
				{
					showQuestPage("captain_mathias_q0902_06.htm", talker);
					st.set("ex_3", 1);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> partyQuest = new GArray<>();

		L2Party party = killer.getParty();
		if(party != null)
		{
			L2CommandChannel cc = party.getCommandChannel();
			if(cc != null)
			{
				for(L2Player member : cc.getMembers())
				{
					QuestState st = member.getQuestState(902);
					if(st != null && st.isStarted() && st.getMemoState() == 1 && npc.isInRange(member, Config.ALT_PARTY_DISTRIBUTION_RANGE))
					{
						partyQuest.add(st);
					}
				}
			}
			else
			{
				for(L2Player member : party.getPartyMembers())
				{
					QuestState st = member.getQuestState(902);
					if(st != null && st.isStarted() && st.getMemoState() == 1 && npc.isInRange(member, Config.ALT_PARTY_DISTRIBUTION_RANGE))
					{
						partyQuest.add(st);
					}
				}
			}
		}
		else
		{
			QuestState st = killer.getQuestState(902);
			if(st != null && st.isStarted() && st.getMemoState() == 1 && npc.isInRange(killer, Config.ALT_PARTY_DISTRIBUTION_RANGE))
			{
				partyQuest.add(st);
			}
		}

		if(npc.getNpcId() == ketra_hero_hekaton || npc.getNpcId() == ketra_commander_tayr || npc.getNpcId() == ketra_chief_brakki ||
				npc.getNpcId() == varka_hero_shadith || npc.getNpcId() == varka_commnder_mos || npc.getNpcId() == varka_chief_horuth)
		{
			for(QuestState st : partyQuest)
			{
				if(st.getInt("ex_1") == 1 && st.getQuestItemsCount(q_g_broken_bone) < 1)
				{
					st.setMemoState(2);
					st.set("ex_1", 2);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.giveItems(q_g_broken_bone, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() >= n_caanibal_stakato_sr && npc.getNpcId() <= n_caanibal_stakato_wp)
		{
			for(QuestState st : partyQuest)
			{
				if(st.getInt("ex_2") == 1 && st.getQuestItemsCount(q_g_stakato_craw) < 1)
				{
					st.setMemoState(2);
					st.set("ex_2", 2);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.giveItems(q_g_stakato_craw, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == n_divine_anais)
		{
			for(QuestState st : partyQuest)
			{
				if(st.getInt("ex_3") == 1 && st.getQuestItemsCount(q_g_anais_scroll) < 1)
				{
					st.setMemoState(2);
					st.set("ex_3", 2);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.giveItems(q_g_anais_scroll, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}
}
