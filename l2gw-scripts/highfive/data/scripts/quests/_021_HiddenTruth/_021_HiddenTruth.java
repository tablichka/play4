package quests._021_HiddenTruth;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

public class _021_HiddenTruth extends Quest
{
	// Npc
	public final int shadow_hardin = 31522;
	public final int q_forest_stone1 = 31523;
	public final int rune_ghost1 = 31524;
	public final int rune_ghost1b = 31525;
	public final int broken_desk1 = 31526;
	public final int falsepriest_agripel = 31348;
	public final int falsepriest_dominic = 31350;
	public final int falsepriest_benedict = 31349;
	public final int highpriest_innocentin = 31328;

	// Items
	public final int CrossofEinhasad = 7140;
	public final int CrossofEinhasadNextQuest = 7141;

	public _021_HiddenTruth()
	{
		super(21, "_021_HiddenTruth", "Hidden Truth");

		addStartNpc(shadow_hardin);
		addTalkId(shadow_hardin, q_forest_stone1, rune_ghost1, rune_ghost1b, broken_desk1, falsepriest_agripel, falsepriest_dominic, falsepriest_benedict, highpriest_innocentin);
		addQuestItem(CrossofEinhasad);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		return "npchtm:" + event;
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

		if(npcId == shadow_hardin)
		{
			if(reply == 21)
			{
				if(st.isCreated() && player.getLevel() >= 63)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					st.setState(STARTED);
					showQuestPage("shadow_hardin_q0021_02.htm", player);
				}
				else
				{
					st.exitCurrentQuest(true);
					showQuestPage("shadow_hardin_q0021_03.htm", player);
				}
			}
			else if(reply == 1001)
				showQuestPage("shadow_hardin_q0021_01.htm", player);
		}
		else if(npcId == q_forest_stone1)
		{
			if(reply == 1)
				showPage("q_forest_stone1_q0021_02.htm", player);
			else if(reply == 2 && st.isStarted() && (st.getMemoState() == 1 || st.getMemoState() == 3))
			{
				L2NpcInstance npc = player.getLastNpc();
				if(npc.i_quest0 == 0)
				{
					npc.i_quest0 = 1;
					L2NpcInstance ghost = addSpawn(rune_ghost1, new Location(51432, -54570, -3136), false, 300000);
					ghost.c_ai0 = npc.getStoredId();
					Functions.npcSay(ghost, Say2C.ALL, 2150);
					st.playSound(SOUND_HORROR2);
					showPage("q_forest_stone1_q0021_03.htm", player);
					st.setCond(2);
					showQuestMark(player);
				}
				else
				{
					st.playSound(SOUND_HORROR2);
					showPage("q_forest_stone1_q0021_04.htm", player);
				}
			}
		}
		else if(npcId == rune_ghost1)
		{
			L2NpcInstance npc = player.getLastNpc();
			if(reply == 3)
			{
				if(npc.i_quest0 <= 5)
				{
					L2NpcInstance mob = addSpawn(rune_ghost1b, new Location(51446, -54514, -3136), false);
					mob.i_quest0 = st.getPlayer().getObjectId();
					mob.i_quest1 = npc.getObjectId();
					npc.i_quest0++;
					st.set("ex", st.getInt("ex") + 1);
					st.setMemoState(3);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					showPage("rune_ghost1_q0021_06.htm", player);
				}
				else
					showPage("rune_ghost1_q0021_06a.htm", player);
			}
		}
		else if(npcId == broken_desk1)
		{
			if(reply == 4)
			{
				st.playSound("ItemSound.item_drop_equip_armor_cloth");
				showPage("broken_desk1_q0021_03.htm", player);
			}
			if(reply == 5)
				showPage("broken_desk1_q0021_04.htm", player);
			if(reply == 6)
				showPage("broken_desk1_q0021_05.htm", player);
			if(reply == 7)
			{
				st.setMemoState(5);
				showPage("broken_desk1_q0021_07.htm", player);
			}
			if(reply == 8)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					st.setMemoState(6);
					showPage("broken_desk1_q0021_08.htm", player);
					st.playSound("AmdSound.ed_chimes_05");
					st.setCond(5);
					showQuestMark(player);
				}
				else if(st.isStarted() && st.getMemoState() == 6)
					showPage("broken_desk1_q0021_09.htm", player);
			}
			if(reply == 9)
				showPage("broken_desk1_q0021_12.htm", player);
			if(reply == 11)
				showPage("broken_desk1_q0021_13.htm", player);
			if(reply == 10)
			{
				st.giveItems(CrossofEinhasad, 1);
				st.setMemoState(7);
				st.set("ex", 0);
				showPage("broken_desk1_q0021_14.htm", player);
				st.setCond(6);
				showQuestMark(player);
			}
		}
		else if(npcId == highpriest_innocentin)
		{
			if(reply == 11)
				showPage("highpriest_innocentin_q0021_02.htm", player);
			if(reply == 12)
				showPage("highpriest_innocentin_q0021_03.htm", player);
			if(reply == 13)
				showPage("highpriest_innocentin_q0021_04.htm", player);
			if(reply == 14)
			{
				if(st.getMemoState() == 10 && st.haveQuestItems(CrossofEinhasad))
				{
					st.giveItems(CrossofEinhasadNextQuest, 1);
					st.addExpAndSp(131228, 11978);
					st.takeItems(CrossofEinhasad, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("highpriest_innocentin_q0021_05.htm", player);
				}
			}
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(npc.getNpcId() == rune_ghost1)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();

		if(st.isCompleted() && npcId != highpriest_innocentin)
			return "completed";

		int cond = st.getMemoState();

		if(npcId == shadow_hardin)
		{
			if(st.isCreated())
				return "shadow_hardin_q0021_01.htm";
			else
				return "npchtm:shadow_hardin_q0021_05.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == q_forest_stone1)
			{
				if(cond == 1 || cond == 3)
					return "npchtm:q_forest_stone1_q0021_01.htm";
			}
			else if(npcId == rune_ghost1)
			{
				if(cond == 1)
					return "npchtm:rune_ghost1_q0021_01.htm";
				if(cond == 3)
				{
					if(st.getInt("ex") <= 20)
					{
						if(npc.i_quest0 <= 5)
						{
							L2NpcInstance mob = addSpawn(rune_ghost1b, new Location(51446, -54514, -3136), false);
							mob.i_quest0 = st.getPlayer().getObjectId();
							mob.i_quest1 = npc.getObjectId();
							npc.i_quest0++;
							st.set("ex", st.getInt("ex") + 1);
							st.setMemoState(3);
							st.setCond(3);
							showQuestMark(st.getPlayer());
							st.playSound(SOUND_MIDDLE);
							return "npchtm:rune_ghost1_q0021_07.htm";
						}
						return "npchtm:rune_ghost1_q0021_07a.htm";
					}
					else
					{
						st.setCond(4);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
						return "npchtm:rune_ghost1_q0021_07b.htm";
					}
				}
				if(cond >= 4)
					return "npchtm:rune_ghost1_q0021_07c.htm";
			}
			else if(npcId == rune_ghost1b)
			{
				if(cond == 3)
				{
					if(npc.i_quest2 == 0)
						return "npchtm:rune_ghost1b_q0021_01.htm";

					npc.getAI().addTimer(2105, 3000);
					return "npchtm:rune_ghost1b_q0021_02.htm";
				}
			}
			else if(npcId == broken_desk1)
			{
				if(cond == 3)
					return "npchtm:broken_desk1_q0021_01.htm";
				if(cond == 5)
				{
					st.setMemoState(6);
					st.playSound(SOUND_ED_CHIMES05);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					return "npchtm:broken_desk1_q0021_10.htm";
				}
				if(cond == 6)
					return "npchtm:broken_desk1_q0021_11.htm";
				if(cond == 7)
					return "npchtm:broken_desk1_q0021_15.htm";
			}
			else if(npcId == falsepriest_agripel)
			{
				if(cond == 7 && st.haveQuestItems(CrossofEinhasad))
				{
					st.setMemoState(8);
					st.set("ex", st.getInt("ex") + 1);
					return "npchtm:falsepriest_agripel_q0021_01.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && (st.getInt("ex") == 2 || st.getInt("ex") == 4))
				{
					st.setMemoState(9);
					st.set("ex", st.getInt("ex") + 1);
					return "npchtm:falsepriest_agripel_q0021_02.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 1)
					return "npchtm:falsepriest_agripel_q0021_01.htm";
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 6)
				{
					st.setMemoState(10);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:falsepriest_agripel_q0021_03.htm";
				}
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") != 6)
					return "npchtm:falsepriest_agripel_q0021_02.htm";
				if(cond == 10 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:falsepriest_agripel_q0021_03.htm";
			}
			else if(npcId == falsepriest_dominic)
			{
				if(cond == 7 && st.haveQuestItems(CrossofEinhasad))
				{
					st.setMemoState(8);
					st.set("ex", st.getInt("ex") + 4);
					return "npchtm:falsepriest_dominic_q0021_01.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && (st.getInt("ex") == 1 || st.getInt("ex") == 2))
				{
					st.setMemoState(9);
					st.set("ex", st.getInt("ex") + 4);
					return "npchtm:falsepriest_dominic_q0021_02.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 4)
					return "npchtm:falsepriest_dominic_q0021_01.htm";
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 3)
				{
					st.setMemoState(10);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:falsepriest_dominic_q0021_03.htm";
				}
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") != 3)
					return "npchtm:falsepriest_dominic_q0021_02.htm";
				if(cond == 10 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:falsepriest_dominic_q0021_03.htm";
			}
			else if(npcId == falsepriest_benedict)
			{
				if(cond == 7 && st.haveQuestItems(CrossofEinhasad))
				{
					st.setMemoState(8);
					st.set("ex", st.getInt("ex") + 2);
					return "npchtm:falsepriest_benedict_q0021_01.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && (st.getInt("ex") == 1 || st.getInt("ex") == 4))
				{
					st.setMemoState(9);
					st.set("ex", st.getInt("ex") + 2);
					return "npchtm:falsepriest_benedict_q0021_02.htm";
				}
				if(cond == 8 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 2)
					return "npchtm:falsepriest_benedict_q0021_01.htm";
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") == 5)
				{
					st.setMemoState(10);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:falsepriest_benedict_q0021_03.htm";
				}
				if(cond == 9 && st.haveQuestItems(CrossofEinhasad) && st.getInt("ex") != 5)
					return "npchtm:falsepriest_benedict_q0021_02.htm";
				if(cond == 10 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:falsepriest_benedict_q0021_03.htm";
			}
			else if(npcId == highpriest_innocentin)
			{
				if(cond == 10 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:highpriest_innocentin_q0021_01.htm";
			}
		}
		else if(st.isCompleted())
		{
			if(npcId == highpriest_innocentin && !st.getPlayer().isQuestStarted(22) && !st.getPlayer().isQuestComplete(22))
				return "highpriest_innocentin_q0021_06.htm";

			return "complete";
		}
		return "noquest";
	}
}