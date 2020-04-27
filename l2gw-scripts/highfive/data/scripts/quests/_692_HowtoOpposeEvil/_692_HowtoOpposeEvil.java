package quests._692_HowtoOpposeEvil;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 10.06.2011 15:33:29
 */
public class _692_HowtoOpposeEvil extends Quest
{
	// NPC
	private static final int DILIOS = 32549;
	private static final int KUTRAN = 32550;

	// Items
	private static final int freed_soul_core = 13796;
	private static final int draconian_talisman = 13841;
	private static final int q_certificate_of_recon = 13857;
	private static final int q_defected_soul_core = 13863;
	private static final int q_draconian_totem = 13865;
	private static final int q_soul_facet = 13866;
	private static final int q_tiat_breath = 13867;
	private static final int q_concentrated_life_energy = 15535;
	private static final int q_brkn_piece_pwdr_of_soul_stone = 15536;

	// MOBS
	private static final HashMap<Integer, Double> dropSoA = new HashMap<Integer, Double>(20);
	private static final HashMap<Integer, Double> dropSoI = new HashMap<Integer, Double>(49);
	private static final HashMap<Integer, Double> dropSoD = new HashMap<Integer, Double>(40);

	static
	{
		// SoA mobs
		dropSoA.put(22746, 16.5); // 15536
		dropSoA.put(22747, 16.5); // 15536
		dropSoA.put(22762, 16.5); // 15536
		dropSoA.put(22750, 1.2); // 15536
		dropSoA.put(22751, 1.2); // 15536
		dropSoA.put(22765, 1.2); // 15536
		dropSoA.put(22752, 1.2); // 15536
		dropSoA.put(22763, 1.2); // 15536
		dropSoA.put(22764, 1.2); // 15536
		dropSoA.put(22758, 1.2); // 15536
		dropSoA.put(22759, 1.2); // 15536
		dropSoA.put(22753, 1.2); // 15536
		dropSoA.put(22757, 1.2); // 15536
		dropSoA.put(22748, 16.5); // 15536
		dropSoA.put(22760, 16.5); // 15536
		dropSoA.put(22761, 16.5); // 15536
		dropSoA.put(22755, 16.5); // 15536
		dropSoA.put(22756, 16.5); // 15536
		dropSoA.put(22749, 16.5); // 15536
		dropSoA.put(22754, 16.5); // 15536

		// SoI mobs
		dropSoI.put(22512, 93.5); // 13863
		dropSoI.put(22534, 62.7); // 13863
		dropSoI.put(22535, 36.8); // 13863
		dropSoI.put(22530, 69.6); // 13863
		dropSoI.put(22531, 70.8); // 13863
		dropSoI.put(22521, 65.2); // 13863
		dropSoI.put(22518, 61.8); // 13863
		dropSoI.put(22517, 61.0); // 13863
		dropSoI.put(22524, 67.2); // 13863
		dropSoI.put(22525, 68.4); // 13863
		dropSoI.put(22526, 69.1); // 13863
		dropSoI.put(22527, 70.3); // 13863
		dropSoI.put(22532, 65.8); // 13863
		dropSoI.put(22532, 65.8); // 13863
		dropSoI.put(22533, 66.9); // 13863
		dropSoI.put(25643, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25644, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25645, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25646, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25647, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25648, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25649, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25650, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25651, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25652, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25665, 47.5); // 13863 Rand(50) + 20 ) * 2, Rand(50) + 60 ) * 2
		dropSoI.put(25666, 47.5); // 13863 Rand(50) + 20 ) * 2, Rand(50) + 60 ) * 2
		dropSoI.put(22513, 95.2); // 13863
		dropSoI.put(22510, 87.7); // 13863
		dropSoI.put(22511, 89.5); // 13863
		dropSoI.put(22509, 98.3); // 13863
		dropSoI.put(22514, 83.8); // 13863
		dropSoI.put(22515, 77.3); // 13863
		dropSoI.put(25634, 50.2); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25635, 50.2); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25636, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25637, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25638, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25639, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25640, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25641, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(25642, 46.0); // 13863 Rand(10) + 20 ) * 2, Rand(10) + 25 ) * 2
		dropSoI.put(22522, 60.9); // 13863
		dropSoI.put(22523, 47.7); // 13863 Rand(4) + 1 ) * 2, Rand(6) + 5 ) * 2
		dropSoI.put(22528, 87.2); // 13863
		dropSoI.put(22529, 88.7); // 13863
		dropSoI.put(22520, 66.1); // 13863
		dropSoI.put(22519, 68.2); // 13863
		dropSoI.put(22516, 68.9); // 13863

		// SoD mobs
		dropSoD.put(22537, 15.7); // 13865
		dropSoD.put(22569, 61.5); // 13865 Rand(2) + 1 ) * 2, Rand(4) + 1 ) * 2
		dropSoD.put(22570, 46.4); // 13865 Rand(3) + 1 ) * 2, Rand(4) + 3 ) * 2
		dropSoD.put(22571, 21.7); // 13865 3, 2
		dropSoD.put(22572, 85.6); // 13865 3, 2
		dropSoD.put(22546, 13.9); // 13865
		dropSoD.put(22579, 35.9); // 13865
		dropSoD.put(22540, 18.8); // 13865
		dropSoD.put(22596, 20.2); // 13865
		dropSoD.put(22574, 66.5); // 13865
		dropSoD.put(22588, 61.2); // 13865
		dropSoD.put(22536, 74.2); // 13865
		dropSoD.put(22547, 22.3); // 13865
		dropSoD.put(22580, 23.4); // 13865
		dropSoD.put(22538, 21.5); // 13865
		dropSoD.put(22594, 81.6); // 13865
		dropSoD.put(22586, 89.8); // 13865 3, 2
		dropSoD.put(22541, 19.5); // 13865
		dropSoD.put(22575, 68.6); // 13865
		dropSoD.put(22589, 63.3); // 13865
		dropSoD.put(22544, 19.8); // 13865
		dropSoD.put(22593, 20.5); // 13865
		dropSoD.put(22578, 34.2); // 13865
		dropSoD.put(22585, 20.3); // 13865
		dropSoD.put(22543, 18.1); // 13865
		dropSoD.put(22592, 18.8); // 13865
		dropSoD.put(22542, 44.5); // 13865
		dropSoD.put(22591, 18.5); // 13865
		dropSoD.put(22576, 29.6); // 13865
		dropSoD.put(22577, 30.0); // 13865
		dropSoD.put(22583, 30.5); // 13865
		dropSoD.put(22584, 31.5); // 13865
		dropSoD.put(22548, 22.3); // 13865
		dropSoD.put(22549, 22.3); // 13865
		dropSoD.put(22581, 38.9); // 13865
		dropSoD.put(22582, 38.9); // 13865
		dropSoD.put(22539, 22.2); // 13865
		dropSoD.put(22595, 83.9); // 13865 5
		dropSoD.put(22573, 78.7); // 13865
		dropSoD.put(22587, 81.5); // 13865
	}

	public _692_HowtoOpposeEvil()
	{
		super(692, "_692_HowtoOpposeEvil", "How to Oppose Evil");

		addStartNpc(DILIOS);
		addTalkId(DILIOS);
		addTalkId(KUTRAN);

		for(int mobId : dropSoA.keySet())
			addKillId(mobId);
		for(int mobId : dropSoI.keySet())
			addKillId(mobId);
		for(int mobId : dropSoD.keySet())
			addKillId(mobId);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == DILIOS && st.isCreated() && player.getLevel() >= 75)
		{
			if(reply == 692)
			{
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("dwyllios_q0692_04.htm", player);
			}
			else if(reply == 1)
				showQuestPage("dwyllios_q0692_03.htm", player);
		}
		else if(npcId == KUTRAN && st.isStarted())
		{
			if(reply == 1 && st.getMemoState() == 2)
			{
				st.setMemoState(3);
				showPage("kirklan_q0692_02.htm", player);
				st.setCond(3);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
			}
			else if(st.getMemoState() == 3)
			{
				if(reply == 2)
				{
					if(st.getQuestItemsCount(q_defected_soul_core) >= 1 || st.getQuestItemsCount(q_draconian_totem) >= 1 || st.getQuestItemsCount(q_tiat_breath) >= 1 || st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) >= 1 || st.getQuestItemsCount(q_concentrated_life_energy) >= 1)
					{
						showPage("kirklan_q0692_04a.htm", player);
					}
				}
				else if(reply == 3)
				{
					if(st.getQuestItemsCount(q_defected_soul_core) >= 1 || st.getQuestItemsCount(q_draconian_totem) >= 1 || st.getQuestItemsCount(q_tiat_breath) >= 1 || st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) >= 1 || st.getQuestItemsCount(q_concentrated_life_energy) >= 1)
					{
						showPage("kirklan_q0692_04b.htm", player);
					}
				}
				else if(reply == 4)
				{
					if(st.getQuestItemsCount(q_defected_soul_core) >= 1 || st.getQuestItemsCount(q_draconian_totem) >= 1 || st.getQuestItemsCount(q_tiat_breath) >= 1 || st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) >= 1 || st.getQuestItemsCount(q_concentrated_life_energy) >= 1)
					{
						showPage("kirklan_q0692_04c.htm", player);
					}
				}
				else if(reply == 10)
				{
					if(st.getQuestItemsCount(q_defected_soul_core) >= 5)
					{
						st.giveItems(freed_soul_core, 1);
						st.takeItems(q_defected_soul_core, 5);
						showPage("kirklan_q0692_05.htm", player);
					}
					else if(st.getQuestItemsCount(q_defected_soul_core) < 5)
					{
						showPage("kirklan_q0692_06.htm", player);
					}
				}
				else if(reply == 11)
				{
					if(st.getQuestItemsCount(q_soul_facet) >= 1)
					{
						st.rollAndGive(57, (st.getQuestItemsCount(q_soul_facet) * 600), 100);
						st.takeItems(q_soul_facet, -1);
						showPage("kirklan_q0692_07.htm", player);
					}
					else if(st.getQuestItemsCount(q_soul_facet) < 1)
					{
						showPage("kirklan_q0692_08.htm", player);
					}
				}
				else if(reply == 20)
				{
					if(st.getQuestItemsCount(q_draconian_totem) >= 5)
					{
						st.giveItems(draconian_talisman, 1);
						st.takeItems(q_draconian_totem, 5);
						showPage("kirklan_q0692_09.htm", player);
					}
					else if(st.getQuestItemsCount(q_draconian_totem) < 5)
					{
						showPage("kirklan_q0692_10.htm", player);
					}
				}
				else if(reply == 21)
				{
					if(st.getQuestItemsCount(q_tiat_breath) >= 1)
					{
						st.rollAndGive(57, st.getQuestItemsCount(q_tiat_breath) * 600, 100);
						st.takeItems(q_tiat_breath, -1);
						showPage("kirklan_q0692_13.htm", player);
					}
					else if(st.getQuestItemsCount(q_tiat_breath) < 1)
					{
						showPage("kirklan_q0692_14.htm", player);
					}
				}
				else if(reply == 30)
				{
					if(st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) >= 5)
					{
						st.giveItems(15486, 1);
						st.takeItems(q_brkn_piece_pwdr_of_soul_stone, 5);
						showPage("kirklan_q0692_15.htm", player);
					}
					else if(st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) < 5)
					{
						showPage("kirklan_q0692_16.htm", player);
					}
				}
				else if(reply == 31)
				{
					if(st.getQuestItemsCount(q_concentrated_life_energy) >= 1)
					{
						st.rollAndGive(57, st.getQuestItemsCount(q_concentrated_life_energy) * 600, 100);
						st.takeItems(q_concentrated_life_energy, -1);
						showPage("kirklan_q0692_17.htm", player);
					}
					else if(st.getQuestItemsCount(q_concentrated_life_energy) < 1)
					{
						showPage("kirklan_q0692_18.htm", player);
					}
				}
				else if(reply == 102)
				{
					showPage("kirklan_q0692_19.htm", player);
				}
				else if(reply == 103)
				{
					showPage("kirklan_q0692_20.htm", player);
				}
				else if(reply == 104)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("kirklan_q0692_21.htm", player);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getMemoState();

		if(npc.getNpcId() == DILIOS)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 75)
					return "dwyllios_q0692_01.htm";

				return "dwyllios_q0692_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1 && !st.haveQuestItems(q_certificate_of_recon) && !st.getPlayer().isQuestComplete(10273))
					return "npchtm:dwyllios_q0692_05.htm";
				if(cond == 1 && st.haveQuestItems(q_certificate_of_recon))
				{
					st.takeItems(q_certificate_of_recon, -1);
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:dwyllios_q0692_06.htm";
				}
				if(cond == 1 && !st.haveQuestItems(q_certificate_of_recon) && st.getPlayer().isQuestComplete(10273))
				{
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:dwyllios_q0692_07.htm";
				}
				if(cond == 2)
					return "ncphtm:dwyllios_q0692_08.htm";
				if(cond == 3 && !st.haveQuestItems(q_defected_soul_core) && !st.haveQuestItems(13864) && !st.haveQuestItems(q_draconian_totem) && !st.haveQuestItems(q_tiat_breath))
					return "ncphtm:dwyllios_q0692_09.htm";
				if(cond == 3 && st.haveQuestItems(q_defected_soul_core) && st.haveQuestItems(13864) && st.haveQuestItems(q_draconian_totem) && st.haveQuestItems(q_tiat_breath))
					return "npchtm:dwyllios_q0692_10.htm";

			}
		}
		else if(npc.getNpcId() == KUTRAN && st.isStarted())
		{
			if(cond == 2)
				return "npchtm:kirklan_q0692_01.htm";
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(q_defected_soul_core) < 1 && st.getQuestItemsCount(q_draconian_totem) < 1 &&
						st.getQuestItemsCount(q_tiat_breath) < 1 && st.getQuestItemsCount(q_brkn_piece_pwdr_of_soul_stone) < 1 && st.getQuestItemsCount(q_concentrated_life_energy) < 1)
					return "npchtm:kirklan_q0692_03.htm";
				else
					return "npchtm:kirklan_q0692_04.htm";
			}
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(player, 3);
		int npcId = npc.getNpcId();
		if(qs != null)
		{
			boolean itemGet = false;
			if(dropSoA.containsKey(npcId))
				itemGet = qs.rollAndGive(q_brkn_piece_pwdr_of_soul_stone, 1, dropSoA.get(npcId));
			else if(dropSoI.containsKey(npc.getNpcId()))
			{
				if(npcId >= 25643 && npcId <= 25652 || npcId >= 25634 && npcId <= 25642)
					itemGet = qs.rollAndGive(q_defected_soul_core, (Rnd.chance(dropSoI.get(npcId)) ? Rnd.get(10) + 20 : Rnd.get(10) + 25) * 2, 100);
				else if(npcId >= 25665 && npcId <= 25666)
					itemGet = qs.rollAndGive(q_defected_soul_core, (Rnd.chance(dropSoI.get(npcId)) ? Rnd.get(50) + 20 : Rnd.get(50) + 60) * 2, 100);
				else if(npcId == 22523)
					itemGet = qs.rollAndGive(q_defected_soul_core, (Rnd.chance(dropSoI.get(npcId)) ? Rnd.get(4) + 1 : Rnd.get(6) + 5) * 2, 100);
				else
					itemGet = qs.rollAndGive(q_defected_soul_core, 1, dropSoI.get(npcId));
			}
			else if(dropSoD.containsKey(npcId))
			{
				if(npcId == 22569)
					itemGet = qs.rollAndGive(q_draconian_totem, (Rnd.chance(dropSoD.get(npcId)) ? Rnd.get(2) + 1 : Rnd.get(4) + 1) * 2, 100);
				else if(npcId == 22570)
					itemGet = qs.rollAndGive(q_draconian_totem, (Rnd.chance(dropSoD.get(npcId)) ? Rnd.get(3) + 1 : Rnd.get(4) + 1) * 2, 100);
				else if(npcId == 22571 || npcId == 22572 || npcId == 22586)
					itemGet = qs.rollAndGive(q_draconian_totem, Rnd.chance(dropSoD.get(npcId)) ? 3 : 2, 100);
				else if(npcId == 22595)
					itemGet = qs.rollAndGive(q_draconian_totem, 5, dropSoD.get(npcId));
				else
					itemGet = qs.rollAndGive(q_draconian_totem, 1, dropSoD.get(npcId));
			}
			if(itemGet)
				qs.playSound(SOUND_ITEMGET);
		}
	}
}
