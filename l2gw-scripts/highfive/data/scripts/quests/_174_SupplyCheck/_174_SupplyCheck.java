package quests._174_SupplyCheck;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _174_SupplyCheck extends Quest
{
	// NPC
	private static final int zerstorer_morsell = 32173;
	private static final int warehouse_keeper_benis = 32170;
	private static final int trader_neagel = 32167;
	private static final int trader_erinu = 32164;
	private static final int subelder_casca = 32139;

	// Items
	private static final int q_warehouse_inventory_list = 9792;
	private static final int q_grocery_inventory_list = 9793;
	private static final int q_weaponshop_inventory_list = 9794;
	private static final int q_supplyment_report = 9795;

	public _174_SupplyCheck()
	{
		super(174, "_174_SupplyCheck", "Supply Check");

		addStartNpc(zerstorer_morsell);
		addTalkId(zerstorer_morsell, warehouse_keeper_benis, trader_neagel, trader_erinu, subelder_casca);
		addQuestItem(q_warehouse_inventory_list, q_grocery_inventory_list, q_weaponshop_inventory_list, q_supplyment_report);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == zerstorer_morsell)
		{
			if(st.isCreated() && talker.getLevel() >= 2)
				return "zerstorer_morsell_q0174_01.htm";
			if(st.isCreated() && talker.getLevel() < 2)
				return "zerstorer_morsell_q0174_02.htm";
			if(st.isCompleted())
				return "completed";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:zerstorer_morsell_q0174_05.htm";
				if(st.getMemoState() == 2 && st.getQuestItemsCount(q_warehouse_inventory_list) >= 1)
				{
					st.takeItems(q_warehouse_inventory_list, 1);
					st.setMemoState(3);
					st.playSound(SOUND_MIDDLE);
					st.setCond(3);
					showQuestMark(talker);
					return "npchtm:zerstorer_morsell_q0174_06.htm";
				}
				if(st.getMemoState() == 3)
					return "npchtm:zerstorer_morsell_q0174_07.htm";
				if(st.getMemoState() == 4 && st.getQuestItemsCount(q_grocery_inventory_list) >= 1)
				{
					st.takeItems(q_grocery_inventory_list, 1);
					st.setMemoState(5);
					st.playSound(SOUND_MIDDLE);
					st.setCond(5);
					showQuestMark(talker);
					return "npchtm:zerstorer_morsell_q0174_08.htm";
				}
				if(st.getMemoState() == 5)
					return "npchtm:zerstorer_morsell_q0174_09.htm";
				if(st.getMemoState() == 6 && st.getQuestItemsCount(q_weaponshop_inventory_list) >= 1)
				{
					st.giveItems(q_supplyment_report, 1);
					st.takeItems(q_weaponshop_inventory_list, 1);
					st.setMemoState(7);
					st.playSound(SOUND_MIDDLE);
					st.setCond(7);
					showQuestMark(talker);
					return "npchtm:zerstorer_morsell_q0174_10.htm";
				}
				if(st.getMemoState() == 7)
					return "npchtm:zerstorer_morsell_q0174_11.htm";
				if(st.getMemoState() == 8)
				{
					st.takeItems(q_grocery_inventory_list, -1);
					if(talker.getLevel() >= 2)
					{
						st.giveItems(23, 1);
						st.giveItems(43, 1);
						st.giveItems(49, 1);
						st.giveItems(2386, 1);
						st.giveItems(37, 1);
						if(talker.getVarInt("NR41") % 10 == 0)
						{
							talker.setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
							Functions.showOnScreentMsg(talker, 2, 0, 0, 0, 1, 0, 5000, 0, 4151);
						}
						st.exitCurrentQuest(false);
						st.addExpAndSp(5672, 446);
						st.rollAndGive(57, 2466, 100);
						st.playSound(SOUND_FINISH);
						return "npchtm:zerstorer_morsell_q0174_12.htm";
					}
				}
			}
		}
		else if(npc.getNpcId() == warehouse_keeper_benis)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					st.giveItems(q_warehouse_inventory_list, 1);
					st.setMemoState(2);
					st.playSound(SOUND_MIDDLE);
					st.setCond(2);
					showQuestMark(talker);
					return "npchtm:warehouse_keeper_benis_q0174_01.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:warehouse_keeper_benis_q0174_02.htm";
				if(st.getMemoState() > 2)
					return "npchtm:warehouse_keeper_benis_q0174_03.htm";
			}
		}
		else if(npc.getNpcId() == trader_neagel)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() < 3)
					return "npchtm:trader_neagel_q0174_01.htm";
				if(st.getMemoState() == 3)
				{
					st.giveItems(q_grocery_inventory_list, 1);
					st.setMemoState(8);
					st.playSound(SOUND_MIDDLE);
					st.setCond(4);
					showQuestMark(talker);
					return "npchtm:trader_neagel_q0174_02.htm";
				}
				if(st.getMemoState() == 8)
					return "npchtm:trader_neagel_q0174_03.htm";
				if(st.getMemoState() > 8)
					return "npchtm:trader_neagel_q0174_04.htm";
			}
		}
		else if(npc.getNpcId() == trader_erinu)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() < 5)
					return "npchtm:trader_erinu_q0174_01.htm";
				if(st.getMemoState() == 5)
				{
					st.giveItems(q_weaponshop_inventory_list, 1);
					st.setMemoState(6);
					st.playSound(SOUND_MIDDLE);
					st.setCond(6);
					showQuestMark(talker);
					return "npchtm:trader_erinu_q0174_02.htm";
				}
				if(st.getMemoState() == 6)
					return "npchtm:trader_erinu_q0174_03.htm";
				if(st.getMemoState() > 6)
					return "npchtm:trader_erinu_q0174_04.htm";
			}
		}
		else if(npc.getNpcId() == subelder_casca)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() < 7)
					return "npchtm:subelder_casca_q0174_01.htm";
				if(st.getMemoState() == 7)
				{
					st.takeItems(q_supplyment_report, 1);
					st.setMemoState(8);
					st.playSound(SOUND_MIDDLE);
					st.setCond(8);
					showQuestMark(talker);
					return "npchtm:subelder_casca_q0174_02.htm";
				}
				if(st.getMemoState() == 8)
					return "npchtm:subelder_casca_q0174_03.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == zerstorer_morsell)
		{
			if(reply == 174)
			{
				if(st.isCreated() && talker.getLevel() >= 2)
				{
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("zerstorer_morsell_q0174_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
		}
	}
}