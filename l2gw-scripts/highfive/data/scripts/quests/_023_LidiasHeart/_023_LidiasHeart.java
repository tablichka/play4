package quests._023_LidiasHeart;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 4.01.2011 13:47
 */
public class _023_LidiasHeart extends Quest
{
	// Npc
	int highpriest_innocentin = 31328;
	int broken_desk1 = 31526;
	int rune_ghost1 = 31524;
	int q_forest_stone1 = 31523;
	int day_violet = 31386;
	int rust_box1 = 31530;

	// Items
	int MapForestofDeadman = 7063;
	int SilverKey = 7149;
	int LidiaHairPin = 7148;
	int LidiaDiary = 7064;
	int SilverSpear = 7150;

	public _023_LidiasHeart()
	{
		super(23, "_023_LidiasHeart", "Lidias Heart");

		addStartNpc(highpriest_innocentin);

		addTalkId(highpriest_innocentin, broken_desk1, rune_ghost1, q_forest_stone1, day_violet, rust_box1);
		addQuestItem(MapForestofDeadman, LidiaDiary, LidiaHairPin, SilverKey, SilverSpear);
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

		if(npcId == highpriest_innocentin)
		{
			if(reply == 23 && st.isCreated())
			{
				if(player.getLevel() >= 64 && player.isQuestComplete(22))
				{
					st.giveItems(MapForestofDeadman, 1);
					st.giveItems(SilverKey, 1);
					st.setMemoState(1);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					st.setState(STARTED);
					showQuestPage("highpriest_innocentin_q0023_03.htm", player);
				}
				else
					showQuestPage("highpriest_innocentin_q0023_02.htm", player);
			}
			else if(reply == 1)
				showPage("highpriest_innocentin_q0023_05.htm", player);
			else if(reply == 2)
				showPage("highpriest_innocentin_q0023_06.htm", player);
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 1)
			{
				st.setMemoState(2);
				showPage("highpriest_innocentin_q0023_07.htm", player);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 13)
				showPage("highpriest_innocentin_q0023_11.htm", player);
			else if(reply == 15 && st.isStarted() && (st.getMemoState() == 5 || st.getMemoState() == 6))
			{
				st.setMemoState(6);
				showPage("highpriest_innocentin_q0023_12.htm", player);
				st.setCond(5);
				showQuestMark(player);
			}
			else if(reply == 14 && st.isStarted() && (st.getMemoState() == 5 || st.getMemoState() == 6))
			{
				st.setMemoState(7);
				showPage("highpriest_innocentin_q0023_13.htm", player);
			}
			else if(reply == 16)
			{
				showPage("highpriest_innocentin_q0023_21.htm", player);
				st.setCond(5);
				showQuestMark(player);
			}
			else if(reply == 17)
				showPage("highpriest_innocentin_q0023_16.htm", player);
			else if(reply == 18)
				showPage("highpriest_innocentin_q0023_17.htm", player);
			else if(reply == 19)
				showPage("highpriest_innocentin_q0023_18.htm", player);
			else if(reply == 20)
			{
				showPage("highpriest_innocentin_q0023_19.htm", player);
				st.playSound("AmbSound.mt_creak01");
			}
			else if(reply == 21 && st.isStarted() && st.getMemoState() == 7)
			{
				st.setMemoState(8);
				showPage("highpriest_innocentin_q0023_20.htm", player);
				st.setCond(6);
				showQuestMark(player);
			}
		}
		else if(npcId == broken_desk1)
		{
			if(reply == 4 && st.isStarted() && st.getMemoState() == 2 && st.haveQuestItems(SilverKey))
			{
				st.takeItems(SilverKey, -1);
				st.setMemoState(3);
				showPage("broken_desk1_q0023_02.htm", player);
			}
			else if(reply == 5)
				showPage("broken_desk1_q0023_04.htm", player);
			else if(reply == 7)
				showPage("broken_desk1_q0023_05.htm", player);
			else if(reply == 8)
			{
				st.giveItems(LidiaHairPin, 1);
				st.setMemoState(st.getMemoState() + 1);
				showPage("broken_desk1_q0023_06.htm", player);
				if(st.haveQuestItems(LidiaDiary))
					st.setCond(4);
				showQuestMark(player);
			}
			else if(reply == 6)
				showPage("broken_desk1_q0023_07a.htm", player);
			else if(reply == 9)
			{
				showPage("broken_desk1_q0023_08.htm", player);
				st.playSound("ItemSound.itemdrop_armor_leather");
			}
			else if(reply == 10)
				showPage("broken_desk1_q0023_09.htm", player);
			else if(reply == 11)
			{
				showPage("broken_desk1_q0023_10.htm", player);
				st.playSound("AmbSound.eg_dron_02");
			}
			else if(reply == 12)
			{
				st.giveItems(LidiaDiary, 1);
				st.setMemoState(st.getMemoState() + 1);
				showPage("broken_desk1_q0023_11.htm", player);
				if(st.haveQuestItems(LidiaHairPin))
					st.setCond(4);
				showQuestMark(player);
			}
		}
		else if(npcId == rune_ghost1)
		{
			if(reply == 23)
			{
				showPage("rune_ghost1_q0023_02.htm", player);
				st.playSound("ChrSound.MHFighter_cry");
			}
			else if(reply == 24)
				showPage("rune_ghost1_q0023_03.htm", player);
			else if(reply == 25 && st.isStarted() && st.getMemoState() == 8)
			{
				st.takeItems(LidiaDiary, -1);
				st.setMemoState(9);
				showPage("rune_ghost1_q0023_04.htm", player);
				st.setCond(7);
				showQuestMark(player);
			}
		}
		else if(npcId == q_forest_stone1)
		{
			if(st.isStarted())
			{
				L2NpcInstance npc = player.getLastNpc();
				if(reply == 22 && (st.getMemoState() == 8 || st.getMemoState() == 9))
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						L2NpcInstance ghost = addSpawn(rune_ghost1, new Location(51432, -54570, -3136), false, 300000);
						ghost.c_ai0 = npc.getStoredId();
						Functions.npcSay(ghost, Say2C.ALL, 2150);
						st.playSound(SOUND_HORROR2);
						showPage("q_forest_stone1_q0023_02.htm", player);
					}
					else
					{
						showPage("q_forest_stone1_q0023_03.htm", player);
						st.playSound(SOUND_HORROR2);
					}
				}
				else if(reply == 26 && st.getMemoState() == 9)
				{
					st.giveItems(SilverKey, 1);
					st.setMemoState(10);
					showPage("q_forest_stone1_q0023_06.htm", player);
					st.setCond(8);
					showQuestMark(player);
				}
			}
		}
		else if(npcId == rust_box1)
		{
			if(reply == 27 && st.isStarted() && st.getMemoState() == 11 && st.haveQuestItems(SilverKey))
			{
				st.giveItems(SilverSpear, 1);
				st.takeItems(SilverKey, -1);
				showPage("rust_box1_q0023_02.htm", player);
				st.playSound("ItemSound.itemdrop_weapon_spear");
				st.setCond(10);
				showQuestMark(player);
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
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.isCompleted())
		{
			if(npcId == day_violet && !st.getPlayer().isQuestComplete(24) && !st.getPlayer().isQuestStarted(24))
				return "npchtm:day_violet_q0023_04.htm";

			return "completed";
		}

		int cond = st.getMemoState();

		if(npcId == highpriest_innocentin)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().isQuestComplete(22))
					return "highpriest_innocentin_q0023_01.htm";

				st.exitCurrentQuest(true);
				return "npchtm:highpriest_innocentin_q0023_01a.htm";
			}

			if(cond == 1)
				return "npchtm:highpriest_innocentin_q0023_04.htm";
			if(cond == 2)
				return "npchtm:highpriest_innocentin_q0023_08.htm";
			if(cond == 5)
				return "npchtm:highpriest_innocentin_q0023_09.htm";
			if(cond == 6)
				return "npchtm:highpriest_innocentin_q0023_14.htm";
			if(cond == 7)
				return "npchtm:highpriest_innocentin_q0023_15.htm";
			if(cond == 8)
			{
				st.setCond(6);
				showQuestMark(st.getPlayer());
				st.playSound(SOUND_MIDDLE);
				return "npchtm:highpriest_innocentin_q0023_22.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == broken_desk1)
			{
				if(cond == 2 && st.haveQuestItems(SilverKey))
				{
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:broken_desk1_q0023_01.htm";
				}
				if(cond == 3)
					return "npchtm:broken_desk1_q0023_03.htm";
				if(cond == 4)
				{
					if(st.haveQuestItems(LidiaHairPin))
						return "npchtm:broken_desk1_q0023_07.htm";
					if(st.haveQuestItems(LidiaDiary))
						return "npchtm:broken_desk1_q0023_12.htm";
				}
				if(cond == 5 && st.haveQuestItems(LidiaHairPin) && st.haveQuestItems(LidiaDiary))
					return "npchtm:broken_desk1_q0023_13.htm";
			}
			else if(npcId == rune_ghost1)
			{
				if(cond == 8)
					return "npchtm:rune_ghost1_q0023_01.htm";
				if(cond == 9 && !st.haveQuestItems(SilverKey))
					return "npchtm:rune_ghost1_q0023_05.htm";
				if((cond == 9 || cond == 10) && st.haveQuestItems(SilverKey))
				{
					st.setMemoState(10);
					return "npchtm:rune_ghost1_q0023_06.htm";
				}
			}
			else if(npcId == q_forest_stone1)
			{
				if(cond == 8)
					return "npchtm:q_forest_stone1_q0023_01.htm";
				if(cond == 9)
					return "npchtm:q_forest_stone1_q0023_04.htm";
				if(cond == 10)
					return "npchtm:q_forest_stone1_q0023_05.htm";
			}
			else if(npcId == day_violet)
			{
				if(cond == 10 && st.haveQuestItems(SilverKey))
				{
					st.setMemoState(11);
					st.setCond(9);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(st.getPlayer());
					return "npchtm:day_violet_q0023_01.htm";
				}
				if(cond == 11 && !st.haveQuestItems(SilverSpear))
					return "npchtm:day_violet_q0023_02.htm";
				if(cond == 11 && st.haveQuestItems(SilverSpear))
				{
					st.rollAndGive(57, 350000, 100);
					st.addExpAndSp(456893, 42112);
					st.takeItems(SilverSpear, -1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:day_violet_q0023_03.htm";
				}
			}
			else if(npcId == rust_box1)
			{
				if(cond == 11)
				{
					if(st.haveQuestItems(SilverKey))
						return "npchtm:rust_box1_q0023_01.htm";
					if(st.haveQuestItems(SilverSpear))
						return "npchtm:rust_box1_q0023_03.htm";
				}
			}
		}
		return "noquest";
	}
}