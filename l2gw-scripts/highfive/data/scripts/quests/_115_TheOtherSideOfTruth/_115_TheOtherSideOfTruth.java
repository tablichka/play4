package quests._115_TheOtherSideOfTruth;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _115_TheOtherSideOfTruth extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int misa = 32018;
	private static final int ice_sculpture = 32021;
	private static final int ice_sculpture2 = 32077;
	private static final int ice_sculpture3 = 32078;
	private static final int ice_sculpture4 = 32079;
	private static final int keier = 32022;

	// Items
	private static final int q_letter_of_misa = 8079;
	private static final int q_letter_of_repre = 8080;
	private static final int q_part_of_tablet = 8081;
	private static final int q_part_of_report_keier = 8082;

	public _115_TheOtherSideOfTruth()
	{
		super(115, "_115_TheOtherSideOfTruth", "The Other Side Of Truth");
		addStartNpc(repre);
		addTalkId(repre, misa, ice_sculpture, ice_sculpture2, ice_sculpture3, ice_sculpture4, keier);
		addQuestItem(q_letter_of_misa, q_letter_of_repre, q_part_of_tablet, q_part_of_report_keier);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == repre)
		{
			if(st.isCompleted())
				return "completed";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 53)
					return "repre_q0115_01.htm";

				return "repre_q0115_02.htm";
			}

			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:repre_q0115_04.htm";
				if(st.getMemoState() == 2 && st.getQuestItemsCount(q_letter_of_misa) == 0)
				{
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
					return "npchtm:repre_q0115_05.htm";
				}
				if(st.getMemoState() == 2 && st.getQuestItemsCount(q_letter_of_misa) > 0)
					return "npchtm:repre_q0115_06.htm";
				if(st.getMemoState() == 3)
					return "npchtm:repre_q0115_09.htm";
				if(st.getMemoState() == 4)
					return "npchtm:repre_q0115_16.htm";
				if(st.getMemoState() == 5)
				{
					st.giveItems(q_letter_of_repre, 1);
					st.setMemoState(6);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:repre_q0115_18.htm";
				}
				if(st.getMemoState() == 6 && st.getQuestItemsCount(q_letter_of_repre) > 0)
					return "npchtm:repre_q0115_19.htm";
				if(st.getMemoState() == 6 && st.getQuestItemsCount(q_letter_of_repre) == 0)
				{
					st.giveItems(q_letter_of_repre, 1);
					return "npchtm:repre_q0115_20.htm";
				}
				if(st.getMemoState() >= 7 && st.getMemoState() < 9)
					return "npchtm:repre_q0115_21.htm";
				if(st.getMemoState() == 9 && st.getQuestItemsCount(q_part_of_report_keier) > 0)
					return "npchtm:repre_q0115_22.htm";
				if(st.getMemoState() == 10)
					return "npchtm:repre_q0115_24.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) == 0)
					return "npchtm:repre_q0115_29.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) > 0)
				{
					st.rollAndGive(57, 115673, 100);
					st.addExpAndSp(493595, 40442);
					st.takeItems(q_part_of_tablet, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:repre_q0115_30.htm";
				}
			}
		}
		else if(npc.getNpcId() == misa)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() > 2 && st.getMemoState() < 5)
					return "npchtm:misa_q0115_01.htm";
				if(st.getMemoState() == 1)
				{
					st.giveItems(q_letter_of_misa, 1);
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:misa_q0115_02.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:misa_q0115_03.htm";
				if(st.getMemoState() == 6 && st.getQuestItemsCount(q_letter_of_repre) > 0)
					return "npchtm:misa_q0115_04.htm";
				if(st.getMemoState() == 7)
					return "npchtm:misa_q0115_06.htm";
			}
		}
		else if(npc.getNpcId() == ice_sculpture)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 2 < 1)
				{
					int i0 = st.getInt("ex_1");
					if(i0 == 6 || i0 == 10 || i0 == 12)
						return "npchtm:ice_sculpture_q0115_02.htm";
					else if(i0 == 14)
						return "npchtm:ice_sculpture_q0115_05.htm";
					else
					{
						st.set("ex_1", i0 + 1);
						return "npchtm:ice_sculpture_q0115_01.htm";
					}
				}
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 2 >= 1)
					return "npchtm:ice_sculpture_q0115_01a.htm";
				if(st.getMemoState() == 8)
					return "npchtm:ice_sculpture_q0115_07.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) == 0)
				{
					st.giveItems(q_part_of_tablet, 1);
					st.setCond(12);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ice_sculpture_q0115_08.htm";
				}
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) >= 1)
					return "npchtm:ice_sculpture_q0115_09.htm";
			}
		}
		else if(npc.getNpcId() == ice_sculpture2)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 4 <= 1)
				{
					int i0 = st.getInt("ex_1");
					if(i0 == 5 || i0 == 9 || i0 == 12)
						return "npchtm:ice_sculpture_q0115_02.htm";
					else if(i0 == 13)
						return "npchtm:ice_sculpture_q0115_05.htm";
					else
					{
						st.set("ex_1", i0 + 2);
						return "npchtm:ice_sculpture_q0115_01.htm";
					}
				}
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 4 > 1)
					return "npchtm:ice_sculpture_q0115_01a.htm";
				if(st.getMemoState() == 8)
					return "npchtm:ice_sculpture_q0115_07.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) == 0)
				{
					st.giveItems(q_part_of_tablet, 1);
					st.setCond(12);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ice_sculpture_q0115_08.htm";
				}
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) >= 1)
					return "npchtm:ice_sculpture_q0115_09.htm";
			}
		}
		else if(npc.getNpcId() == ice_sculpture3)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 8 <= 3)
				{
					int i0 = st.getInt("ex_1");
					if(i0 == 3 || i0 == 9 || i0 == 10)
						return "npchtm:ice_sculpture_q0115_02.htm";
					else if(i0 == 11)
						return "npchtm:ice_sculpture_q0115_05.htm";
					else
					{
						st.set("ex_1", i0 + 4);
						return "npchtm:ice_sculpture_q0115_01.htm";
					}
				}
				if(st.getMemoState() == 7 && st.getInt("ex_1") % 8 > 3)
					return "npchtm:ice_sculpture_q0115_01a.htm";
				if(st.getMemoState() == 8)
					return "npchtm:ice_sculpture_q0115_07.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) == 0)
				{
					st.giveItems(q_part_of_tablet, 1);
					st.setCond(12);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ice_sculpture_q0115_08.htm";
				}
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) >= 1)
					return "npchtm:ice_sculpture_q0115_09.htm";
			}
		}
		else if(npc.getNpcId() == ice_sculpture4)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 7 && st.getInt("ex_1") <= 7)
				{
					int i0 = st.getInt("ex_1");
					if(i0 == 3 || i0 == 5 || i0 == 6)
						return "npchtm:ice_sculpture_q0115_02.htm";
					else if(i0 == 7)
						return "npchtm:ice_sculpture_q0115_05.htm";
					else
					{
						st.set("ex_1", i0 + 8);
						return "npchtm:ice_sculpture_q0115_01.htm";
					}
				}
				if(st.getMemoState() == 7 && st.getInt("ex_1") > 7)
					return "npchtm:ice_sculpture_q0115_01a.htm";
				if(st.getMemoState() == 8)
					return "npchtm:ice_sculpture_q0115_07.htm";
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) == 0)
				{
					st.giveItems(q_part_of_tablet, 1);
					st.setCond(12);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ice_sculpture_q0115_08.htm";
				}
				if(st.getMemoState() == 11 && st.getQuestItemsCount(q_part_of_tablet) >= 1)
					return "npchtm:ice_sculpture_q0115_09.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == repre)
		{
			if(reply == 115)
			{
				if(st.isCreated() && talker.getLevel() >= 53)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					showQuestPage("repre_q0115_03.htm", talker);
				}
			}
			else if(reply == 1 && st.isStarted() && st.getMemoState() == 2)
			{
				st.takeItems(q_letter_of_misa, -1);
				st.setMemoState(3);
				showPage("repre_q0115_07.htm", talker);
				st.setCond(3);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 2)
			{
				st.takeItems(q_letter_of_misa, -1);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("repre_q0115_08.htm", talker);
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 3)
			{
				st.setMemoState(4);
				showPage("repre_q0115_11.htm", talker);
				st.setCond(4);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 4 && st.isStarted() && st.getMemoState() == 3)
			{
				st.setMemoState(4);
				showPage("repre_q0115_12.htm", talker);
				st.setCond(4);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 5 && st.isStarted() && st.getMemoState() == 3)
			{
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("repre_q0115_13.htm", talker);
			}
			else if(reply == 6 && st.isStarted() && st.getMemoState() == 4)
			{
				st.setMemoState(5);
				showPage("repre_q0115_17.htm", talker);
				st.playSound("AmbSound.t_wingflap_04");
				st.setCond(5);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 8 && st.isStarted() && st.getMemoState() == 9 && st.getQuestItemsCount(q_part_of_report_keier) >= 1)
			{
				st.takeItems(q_part_of_report_keier, -1);
				st.setMemoState(10);
				showPage("repre_q0115_23.htm", talker);
				st.setCond(10);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 9 && st.isStarted() && st.getMemoState() == 10 && talker.getLevel() >= 53)
			{
				if(st.getQuestItemsCount(q_part_of_tablet) >= 1)
				{
					st.takeItems(q_part_of_tablet, -1);
					showPage("repre_q0115_25.htm", talker);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					st.rollAndGive(57, 115673, 100);
					st.addExpAndSp(493595, 40442);
				}
				else
				{
					st.setMemoState(11);
					showPage("repre_q0115_27.htm", talker);
					st.playSound("AmbSound.thunder_02");
					st.setCond(11);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 10 && st.isStarted() && st.getMemoState() == 10 && talker.getLevel() >= 53)
			{
				if(st.getQuestItemsCount(q_part_of_tablet) >= 1)
				{
					st.takeItems(q_part_of_tablet, st.getQuestItemsCount(q_part_of_tablet));
					showPage("repre_q0115_26.htm", talker);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					st.rollAndGive(57, 115673, 100);
					st.addExpAndSp(493595, 40442);
				}
				else
				{
					st.setMemoState(11);
					showPage("repre_q0115_28.htm", talker);
					st.playSound("AmbSound.thunder_02");
					st.setCond(11);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == misa)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 6 && st.getQuestItemsCount(q_letter_of_repre) > 0)
			{
				st.takeItems(q_letter_of_repre, -1);
				st.setMemoState(7);
				showPage("misa_q0115_05.htm", talker);
				st.setCond(7);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ice_sculpture)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 2 < 1)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 6 || i0 == 10 || i0 == 12)
				{
					st.set("ex_1", i0 + 1);
					st.giveItems(q_part_of_tablet, 1);
					showPage("ice_sculpture_q0115_03.htm", talker);
					st.playSound(SOUND_ITEMGET);
				}
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 2 < 1)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 6 || i0 == 10 || i0 == 12)
				{
					st.set("ex_1", i0 + 1);
					showPage("ice_sculpture_q0115_04.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") == 14)
			{
				st.setMemoState(8);
				showPage("ice_sculpture_q0115_06.htm", talker);
				st.setCond(8);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ice_sculpture2)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 4 <= 1)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 5 || i0 == 9 || i0 == 12)
				{
					st.set("ex_1", i0 + 2);
					st.giveItems(q_part_of_tablet, 1);
					showPage("ice_sculpture_q0115_03.htm", talker);
					st.playSound(SOUND_ITEMGET);
				}
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 4 <= 1)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 5 || i0 == 9 || i0 == 12)
				{
					st.set("ex_1", i0 + 2);
					showPage("ice_sculpture_q0115_04.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") == 13)
			{
				st.setMemoState(8);
				showPage("ice_sculpture_q0115_06.htm", talker);
				st.setCond(8);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ice_sculpture3)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 8 <= 3)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 3 || i0 == 9 || i0 == 10)
				{
					st.set("ex_1", i0 + 4);
					st.giveItems(q_part_of_tablet, 1);
					showPage("ice_sculpture_q0115_03.htm", talker);
					st.playSound(SOUND_ITEMGET);
				}
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") % 8 <= 3)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 3 || i0 == 9 || i0 == 10)
				{
					st.set("ex_1", i0 + 4);
					showPage("ice_sculpture_q0115_04.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") == 11)
			{
				st.setMemoState(8);
				showPage("ice_sculpture_q0115_06.htm", talker);
				st.setCond(8);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ice_sculpture4)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") <= 7)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 3 || i0 == 5 || i0 == 6)
				{
					st.set("ex_1", i0 + 8);
					st.giveItems(q_part_of_tablet, 1);
					showPage("ice_sculpture_q0115_03.htm", talker);
					st.playSound(SOUND_ITEMGET);
				}
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") <= 7)
			{
				int i0 = st.getInt("ex_1");
				if(i0 == 3 || i0 == 5 || i0 == 6)
				{
					st.set("ex_1", i0 + 8);
					showPage("ice_sculpture_q0115_04.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 7 && st.getInt("ex_1") == 7)
			{
				st.setMemoState(8);
				showPage("ice_sculpture_q0115_06.htm", talker);
				st.setCond(8);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == keier)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 8)
			{
				st.giveItems(q_part_of_report_keier, 1);
				st.setMemoState(9);
				showPage("keier_q0115_02.htm", talker);
				st.setCond(9);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}