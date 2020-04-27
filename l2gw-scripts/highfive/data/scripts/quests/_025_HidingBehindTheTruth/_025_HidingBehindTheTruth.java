package quests._025_HidingBehindTheTruth;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 4.01.2011 17:08
 */
public class _025_HidingBehindTheTruth extends Quest
{
	// NPC
	private static final int falsepriest_agripel = 31348;
	private static final int falsepriest_benedict = 31349;
	private static final int broken_desk2 = 31533;
	private static final int broken_desk3 = 31534;
	private static final int broken_desk4 = 31535;
	private static final int q_forest_box1 = 31536;
	private static final int maid_of_ridia = 31532;
	private static final int shadow_hardin = 31522;
	private static final int q_forest_stone2 = 31531;

	// Items
	private static final int EARRING_OF_BLESSING = 874;
	private static final int NECKLACE_OF_BLESSING = 936;
	private static final int RING_OF_BLESSING = 905;
	private static final int CONTRACT = 7066;
	private static final int GEMSTONE_KEY = 7157;
	private static final int LIDIAS_DRESS = 7155;
	private static final int MAP_FOREST_OF_DEADMAN = 7063;
	private static final int SUSPICIOUS_TOTEM_DOLL = 7156;
	private static final int SUSPICIOUS_TOTEM_DOLL_2 = 7158;

	// Mobs
	private static final int triyol_zzolda = 27218;

	public _025_HidingBehindTheTruth()
	{
		super(25, "_025_HidingBehindTheTruth", "Hiding Behind The Truth");

		addStartNpc(falsepriest_benedict);
		addTalkId(falsepriest_agripel, falsepriest_benedict, broken_desk2, broken_desk3, broken_desk4, q_forest_box1, maid_of_ridia, shadow_hardin, q_forest_stone2);

		addKillId(triyol_zzolda);
		addQuestItem(CONTRACT, GEMSTONE_KEY, LIDIAS_DRESS, MAP_FOREST_OF_DEADMAN, SUSPICIOUS_TOTEM_DOLL, SUSPICIOUS_TOTEM_DOLL_2);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == falsepriest_benedict)
		{
			if(reply == 25 && st.isCreated() && player.getLevel() >= 66 && player.isQuestComplete(24))
			{
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("falsepriest_benedict_q0025_03.htm", player);
			}
			else if(reply == 2)
				showPage("falsepriest_benedict_q0025_06.htm", player);
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 1)
				{
					if(st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL))
						showPage("falsepriest_benedict_q0025_04.htm", player);
					else
					{
						showPage("falsepriest_benedict_q0025_05.htm", player);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(reply == 3)
				{
					if(st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL))
					{
						st.setMemoState(2);
						showPage("falsepriest_benedict_q0025_10.htm", player);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npcId == shadow_hardin)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 7 && memoState == 6 && !st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL))
				{
					st.setMemoState(7);
					st.set("ex", 20);
					showPage("shadow_hardin_q0025_04.htm", player);
					st.setCond(6);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 17 && memoState == 16)
				{
					st.setMemoState(19);
					showPage("shadow_hardin_q0025_10.htm", player);
				}
				else if(reply == 18)
					showPage("shadow_hardin_q0025_11.htm", player);
				else if(reply == 19 && memoState == 19)
				{
					st.setMemoState(20);
					showPage("shadow_hardin_q0025_13.htm", player);
					st.setCond(16);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 23 && memoState == 24)
				{
					st.giveItems(EARRING_OF_BLESSING, 1);
					st.giveItems(NECKLACE_OF_BLESSING, 1);
					st.takeItems(MAP_FOREST_OF_DEADMAN, -1);
					st.addExpAndSp(572277, 53750);
					showPage("shadow_hardin_q0025_16.htm", player);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
				}
			}
		}
		else if(npcId == falsepriest_agripel)
		{
			int memoState = st.getMemoState();
			if(reply == 4)
			{
				if(st.isStarted() && memoState == 2)
				{
					st.takeItems(SUSPICIOUS_TOTEM_DOLL, -1);
					st.setMemoState(3);
					showPage("falsepriest_agripel_q0025_02.htm", player);
				}
			}
			else if(reply == 5)
				showPage("falsepriest_agripel_q0025_07.htm", player);
			else if(reply == 6)
			{
				if(st.isStarted() && memoState == 3)
				{
					st.giveItems(GEMSTONE_KEY, 1);
					st.setMemoState(6);
					showPage("falsepriest_agripel_q0025_08.htm", player);
					st.setCond(5);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 25)
			{
				if(st.isStarted() && memoState == 20 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
				{
					st.takeItems(SUSPICIOUS_TOTEM_DOLL_2, -1);
					st.setMemoState(21);
					showPage("falsepriest_agripel_q0025_10.htm", player);
				}
			}
			else if(reply == 20)
				showPage("falsepriest_agripel_q0025_12.htm", player);
			else if(reply == 21)
			{
				if(st.isStarted() && memoState == 21)
				{
					st.setMemoState(22);
					showPage("falsepriest_agripel_q0025_13.htm", player);
				}
			}
			else if(reply == 22)
				showPage("falsepriest_agripel_q0025_14.htm", player);
			else if(reply == 23)
			{
				if(st.isStarted() && memoState == 22)
				{
					st.setMemoState(23);
					showPage("falsepriest_agripel_q0025_16.htm", player);
					st.setCond(17);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 24)
			{
				if(st.isStarted() && memoState == 22)
				{
					st.setMemoState(24);
					showPage("falsepriest_agripel_q0025_17.htm", player);
					st.setCond(18);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == broken_desk2)
		{
			int memoState = st.getMemoState();
			if(reply == 8)
			{
				int i0 = st.getMemoState() % 1000;
				if(i0 >= 100)
					showPage("broken_desk2_q0025_03.htm", player);
				else if(Rnd.get(60) > st.getInt("ex"))
				{
					st.set("ex", st.getInt("ex") + 20);
					st.setMemoState(st.getMemoState() + 100);
					showPage("broken_desk2_q0025_04.htm", player);
				}
				else
				{
					st.setMemoState(8);
					showPage("broken_desk2_q0025_05.htm", player);
					st.playSound("AmdSound.dd_horror_02");
				}
			}
			else if(reply == 9)
			{
				L2NpcInstance npc = player.getLastNpc();
				if(memoState == 8 && !st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						npc.i_quest1 = player.getObjectId();
						L2NpcInstance triyol = addSpawn(triyol_zzolda, new Location(47142, -35941, -1623), false, 120000);
						Functions.npcSay(triyol, Say2C.ALL, 2550, player.getName());
						triyol.i_quest0 = player.getObjectId();
						triyol.c_ai0 = npc.getStoredId();
						triyol.addDamageHate(player, 0, 1000);
						triyol.setRunning();
						triyol.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
						showPage("broken_desk2_q0025_07.htm", player);
						st.setCond(7);
						showQuestMark(player);
					}
					else if(npc.i_quest1 == player.getObjectId())
						showPage("broken_desk2_q0025_08.htm", player);
					else
						showPage("broken_desk2_q0025_09.htm", player);
				}
				else if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
					showPage("broken_desk2_q0025_10.htm", player);
			}
			else if(reply == 10)
			{
				if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2) && st.haveQuestItems(GEMSTONE_KEY))
				{
					st.giveItems(CONTRACT, 1);
					st.takeItems(GEMSTONE_KEY, -1);
					st.setMemoState(9);
					showPage("broken_desk2_q0025_11.htm", player);
					st.setCond(9);
					showQuestMark(player);
				}
			}
		}
		else if(npcId == broken_desk3)
		{
			int memoState = st.getMemoState();
			if(reply == 8)
			{
				int i0 = st.getMemoState() % 10000;
				if(i0 >= 1000)
					showPage("broken_desk3_q0025_03.htm", player);
				else if(Rnd.get(60) > st.getInt("ex"))
				{
					st.set("ex", st.getInt("ex") + 20);
					st.setMemoState(st.getMemoState() + 1000);
					showPage("broken_desk3_q0025_04.htm", player);
				}
				else
				{
					st.setMemoState(8);
					showPage("broken_desk3_q0025_05.htm", player);
					st.playSound("AmdSound.dd_horror_02");
				}
			}
			else if(reply == 9)
			{
				L2NpcInstance npc = player.getLastNpc();
				if(memoState == 8 && !st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						npc.i_quest1 = player.getObjectId();
						L2NpcInstance triyol = addSpawn(triyol_zzolda, new Location(50055, -47020, -3396), false, 120000);
						Functions.npcSay(triyol, Say2C.ALL, 2550, player.getName());
						triyol.i_quest0 = player.getObjectId();
						triyol.c_ai0 = npc.getStoredId();
						triyol.addDamageHate(player, 0, 1000);
						triyol.setRunning();
						triyol.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
						showPage("broken_desk3_q0025_07.htm", player);
						st.setCond(7);
						showQuestMark(player);
					}
					else if(npc.i_quest1 == player.getObjectId())
						showPage("broken_desk3_q0025_08.htm", player);
					else
						showPage("broken_desk3_q0025_09.htm", player);
				}
				else if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
					showPage("broken_desk3_q0025_10.htm", player);
			}
			else if(reply == 10)
			{
				if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2) && st.haveQuestItems(GEMSTONE_KEY))
				{
					st.giveItems(CONTRACT, 1);
					st.takeItems(GEMSTONE_KEY, -1);
					st.setMemoState(9);
					showPage("broken_desk3_q0025_11.htm", player);
					st.setCond(9);
					showQuestMark(player);
				}
			}
		}
		else if(npcId == broken_desk4)
		{
			int memoState = st.getMemoState();
			if(reply == 8)
			{
				int i0 = st.getMemoState();
				if(i0 >= 10000)
					showPage("broken_desk4_q0025_03.htm", player);
				else if(Rnd.get(60) > st.getInt("ex"))
				{
					st.set("ex", st.getInt("ex") + 20);
					st.setMemoState(st.getMemoState() + 10000);
					showPage("broken_desk4_q0025_04.htm", player);
				}
				else
				{
					st.setMemoState(8);
					showPage("broken_desk4_q0025_05.htm", player);
					st.playSound("AmdSound.dd_horror_02");
				}
			}
			else if(reply == 9)
			{
				L2NpcInstance npc = player.getLastNpc();
				if(memoState == 8 && !st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						npc.i_quest1 = player.getObjectId();
						L2NpcInstance triyol = addSpawn(triyol_zzolda, new Location(59712, -47568, -2720), false, 120000);
						Functions.npcSay(triyol, Say2C.ALL, 2550, player.getName());
						triyol.i_quest0 = player.getObjectId();
						triyol.c_ai0 = npc.getStoredId();
						triyol.addDamageHate(player, 0, 1000);
						triyol.setRunning();
						triyol.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
						showPage("broken_desk4_q0025_07.htm", player);
						st.setCond(7);
						showQuestMark(player);
					}
					else if(npc.i_quest1 == player.getObjectId())
						showPage("broken_desk4_q0025_08.htm", player);
					else
						showPage("broken_desk4_q0025_09.htm", player);
				}
				else if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2))
					showPage("broken_desk4_q0025_10.htm", player);
			}
			else if(reply == 10)
			{
				if(memoState == 8 && st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2) && st.haveQuestItems(GEMSTONE_KEY))
				{
					st.giveItems(CONTRACT, 1);
					st.takeItems(GEMSTONE_KEY, -1);
					st.setMemoState(9);
					showPage("broken_desk4_q0025_11.htm", player);
					st.setCond(9);
					showQuestMark(player);
				}
			}
		}
		else if(npcId == maid_of_ridia)
		{
			int memoState = st.getMemoState();
			if(reply == 11)
			{
				if(st.isStarted() && memoState == 9 && st.haveQuestItems(CONTRACT))
				{
					st.takeItems(CONTRACT, -1);
					st.setMemoState(10);
					showPage("maid_of_ridia_q0025_02.htm", player);
				}
			}
			else if(reply == 12)
				showPage("maid_of_ridia_q0025_04.htm", player);
			else if(reply == 13)
			{
				if(st.isStarted() && memoState == 10)
				{
					st.setMemoState(11);
					showPage("maid_of_ridia_q0025_07.htm", player);
					st.playSound("SkillSound5.horror_01");
					st.setCond(11);
					showQuestMark(player);
				}
			}
			else if(reply == 14)
			{
				if(st.isStarted() && memoState == 13)
				{
					if(st.getInt("ex") <= 3)
					{
						st.set("ex", st.getInt("ex") + 1);
						showPage("maid_of_ridia_q0025_11.htm", player);
						st.playSound("ChrSound.FDElf_Cry");
					}
					else
					{
						st.setMemoState(14);
						showPage("maid_of_ridia_q0025_12.htm", player);
					}
				}
			}
			else if(reply == 15)
			{
				if(st.isStarted() && memoState == 14)
				{
					st.setMemoState(15);
					showPage("maid_of_ridia_q0025_17.htm", player);
				}
			}
			else if(reply == 16)
			{
				if(st.isStarted() && memoState == 15)
				{
					st.setMemoState(16);
					showPage("maid_of_ridia_q0025_21.htm", player);
					st.setCond(15);
					showQuestMark(player);
				}
			}
			else if(reply == 22)
			{
				if(st.isStarted() && memoState == 23)
				{
					st.giveItems(EARRING_OF_BLESSING, 1);
					st.giveItems(RING_OF_BLESSING, 2);
					st.takeItems(MAP_FOREST_OF_DEADMAN, -1);
					st.addExpAndSp(572277, 53750);
					showPage("maid_of_ridia_q0025_25.htm", player);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
				}
			}
		}
		else if(npcId == q_forest_stone2)
		{
			if ( reply == 1 )
			{
				if ( st.isStarted() && st.getMemoState() == 11)
				{
					showPage("q_forest_stone2_q0025_02.htm", player);
					addSpawn(q_forest_box1, new Location(60104, -35820, -681), false, 20000);
					st.setCond(12);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if(qs.isCompleted())
			return "completed";

		return "npchtm:" + event;
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(npc.getNpcId() == triyol_zzolda && !npc.isDecayed())
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == falsepriest_benedict)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 66 && st.getPlayer().isQuestComplete(24))
					return "falsepriest_benedict_q0025_01.htm";

				st.exitCurrentQuest(true);
				return "npchtm:falsepriest_benedict_q0025_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:falsepriest_benedict_q0025_03a.htm";
				if(cond == 2)
					return "npchtm:falsepriest_benedict_q0025_11.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == shadow_hardin)
			{
				if(cond == 1)
				{
					if(!st.haveQuestItems(SUSPICIOUS_TOTEM_DOLL))
					{
						st.giveItems(SUSPICIOUS_TOTEM_DOLL, 1);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
						return "npchtm:shadow_hardin_q0025_01.htm";
					}

					return "npchtm:shadow_hardin_q0025_02.htm";
				}
				if(cond == 6 && st.haveQuestItems(GEMSTONE_KEY))
					return "npchtm:shadow_hardin_q0025_03.htm";
				if(cond % 100 == 7)
					return "npchtm:shadow_hardin_q0025_05.htm";
				if(cond == 9 && st.haveQuestItems(CONTRACT))
				{
					st.setCond(10);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:shadow_hardin_q0025_06.htm";
				}
				if(cond == 16)
					return "npchtm:shadow_hardin_q0025_06a.htm";
				if(cond == 19)
					return "npchtm:shadow_hardin_q0025_12.htm";
				if(cond == 20)
					return "npchtm:shadow_hardin_q0025_14.htm";
				if(cond == 24)
					return "npchtm:shadow_hardin_q0025_15.htm";
				if(cond == 23)
					return "npchtm:shadow_hardin_q0025_15a.htm";
			}
			else if(npcId == falsepriest_agripel)
			{
				if(cond == 2)
					return "npchtm:falsepriest_agripel_q0025_01.htm";
				if(cond == 3)
					return "npchtm:falsepriest_agripel_q0025_03.htm";
				if(cond == 6)
					return "npchtm:falsepriest_agripel_q0025_08a.htm";
				if(cond == 20)
					return "npchtm:falsepriest_agripel_q0025_09.htm";
				if(cond == 21)
					return "npchtm:falsepriest_agripel_q0025_10a.htm";
				if(cond == 22)
					return "npchtm:falsepriest_agripel_q0025_15.htm";
				if(cond == 23)
					return "npchtm:falsepriest_agripel_q0025_18.htm";
				if(cond == 24)
					return "npchtm:falsepriest_agripel_q0025_19.htm";
			}
			else if(npcId == broken_desk2)
			{
				if(cond % 100 == 7)
					return "npchtm:broken_desk2_q0025_01.htm";
				if(cond % 100 >= 9)
					return "npchtm:broken_desk2_q0025_02.htm";
				if(cond == 8)
					return "npchtm:broken_desk2_q0025_06.htm";

			}
			else if(npcId == broken_desk3)
			{
				if(cond % 100 == 7)
					return "npchtm:broken_desk3_q0025_01.htm";
				if(cond % 100 >= 9)
					return "npchtm:broken_desk3_q0025_02.htm";
				if(cond == 8)
					return "npchtm:broken_desk3_q0025_06.htm";
			}
			else if(npcId == broken_desk4)
			{
				if(cond % 100 == 7)
					return "npchtm:broken_desk4_q0025_01.htm";
				if(cond % 100 >= 9)
					return "npchtm:broken_desk4_q0025_02.htm";
				if(cond == 8)
					return "npchtm:broken_desk4_q0025_06.htm";
			}
			else if(npcId == maid_of_ridia)
			{
				if(cond == 11)
				{
					st.playSound("SkillSound5.horror_01");
					return "npchtm:maid_of_ridia_q0025_08.htm";
				}
				if(cond == 9 && st.haveQuestItems(CONTRACT))
					return "npchtm:maid_of_ridia_q0025_01.htm";
				if(cond == 10)
					return "npchtm:maid_of_ridia_q0025_03.htm";
				if(cond == 12 && st.haveQuestItems(LIDIAS_DRESS))
				{
					st.takeItems(LIDIAS_DRESS, -1);
					st.setMemoState(13);
					st.setCond(14);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:maid_of_ridia_q0025_09.htm";
				}
				if(cond == 13)
				{
					st.set("ex", 0);
					st.playSound("ChrSound.FDElf_Cry");
					return "npchtm:maid_of_ridia_q0025_10.htm";
				}
				if(cond == 14)
					return "npchtm:maid_of_ridia_q0025_13.htm";
				if(cond == 15)
					return "npchtm:maid_of_ridia_q0025_18.htm";
				if(cond == 16)
					return "npchtm:maid_of_ridia_q0025_22.htm";
				if(cond == 23)
					return "npchtm:maid_of_ridia_q0025_23.htm";
				if(cond == 24)
					return "npchtm:maid_of_ridia_q0025_24.htm";
			}
			else if(npcId == q_forest_stone2)
			{
				if(cond == 11)
					return "npchtm:q_forest_stone2_q0025_01.htm";
				if(cond == 12)
					return "npchtm:q_forest_stone2_q0025_03.htm";

			}
			else if(npcId == q_forest_box1)
			{
				if(cond == 11)
				{
					st.giveItems(LIDIAS_DRESS, 1);
					st.setMemoState(12);
					st.setCond(13);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					startQuestTimer("despawn_" + npc.getObjectId(), 3000, npc, null, true);
					return "npchtm:q_forest_box1_q0025_01.htm";
				}
			}
		}

		return "noquest";
	}

	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.startsWith("despawn"))
		{
			if(npc != null)
				npc.deleteMe();
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == triyol_zzolda)
		{
			QuestState qs = killer.getQuestState(getName());
			if(qs != null && qs.getMemoState() == 8 && !qs.haveQuestItems(SUSPICIOUS_TOTEM_DOLL_2) && qs.getPlayer().getObjectId() == npc.i_quest0)
			{
				qs.giveItems(SUSPICIOUS_TOTEM_DOLL_2, 1);
				qs.playSound(SOUND_ITEMGET);
				qs.setCond(8);
				showQuestMark(qs.getPlayer());
			}

			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;

			Functions.npcSay(npc, Say2C.ALL, 2551);
			npc.deleteMe();
		}
	}
}