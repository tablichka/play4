package quests._463_IMustBeaGenius;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 14.10.11 15:29
 */
public class _463_IMustBeaGenius extends Quest
{
	// NPC
	private static final int collecter_gutenhagen = 32069;

	// Mobs
	private static final int golem_boom1_p = 22812;
	private static final int golem_cannon1_p = 22801;
	private static final int golem_cannon2_p = 22802;
	private static final int golem_cannon3_p = 22803;
	private static final int golem_carrier_p = 22807;
	private static final int golem_guardian_p = 22809;
	private static final int golem_micro_p = 22810;
	private static final int golem_prop1_p = 22804;
	private static final int golem_prop2_p = 22805;
	private static final int golem_prop3_p = 22806;
	private static final int golem_steel_p = 22811;

	// Items
	private static final int q_log_of_golemgroup = 15510;
	private static final int q_log_roll_of_golemgroup = 15511;

	public _463_IMustBeaGenius()
	{
		super(463, "_463_IMustBeaGenius", "I Must Be a Genius");
		addStartNpc(collecter_gutenhagen);
		addTalkId(collecter_gutenhagen);
		addKillId(golem_boom1_p, golem_cannon1_p, golem_cannon2_p, golem_cannon3_p, golem_carrier_p, golem_guardian_p, golem_micro_p);
		addKillId(golem_prop1_p, golem_prop2_p, golem_prop3_p, golem_steel_p);
		addQuestItem(q_log_of_golemgroup, q_log_roll_of_golemgroup);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == collecter_gutenhagen)
		{
			if(st.isCreated() && talker.getLevel() >= 70)
				return "collecter_gutenhagen_q0463_01.htm";
			if(st.isCreated() && talker.getLevel() < 70)
				return "collecter_gutenhagen_q0463_02.htm";
			if(st.isCompleted())
				return "collecter_gutenhagen_q0463_03.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() != 2 && st.getMemoState() != 3 && st.getQuestItemsCount(q_log_roll_of_golemgroup) != 1)
					return "npchtm:collecter_gutenhagen_q0463_06.htm";
				if(st.getMemoState() == 2 && st.getQuestItemsCount(q_log_roll_of_golemgroup) == 1)
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.takeItems(q_log_roll_of_golemgroup, -1);
					st.setMemoState(3);
					return "npchtm:collecter_gutenhagen_q0463_08.htm";
				}
				if(st.getMemoState() == 3)
					return "npchtm:collecter_gutenhagen_q0463_08a.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == collecter_gutenhagen)
		{
			if(reply == 463)
			{
				if(st.isCreated() && talker.getLevel() >= 70)
				{
					int i0 = 550 + Rnd.get(51);
					int i1 = Rnd.get(4);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.set("ex_1", i0);
					st.set("ex_2", i1);
					showHtmlFile(talker, "collecter_gutenhagen_q0463_05.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(i0)}, true);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 70)
				{
					showQuestPage("collecter_gutenhagen_q0463_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				int i0 = st.getInt("ex_1");
				if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(q_log_of_golemgroup) != i0)
				{
					showHtmlFile(talker, "collecter_gutenhagen_q0463_07.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(i0)}, true);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					int i8 = Rnd.get(100);
					int i9 = Rnd.get(100);
					int i6, i7;

					if(i8 == 0)
					{
						st.addExpAndSp(198725, 0);
						i6 = 1;
					}
					else if(i8 >= 1 && i8 < 5)
					{
						st.addExpAndSp(278216, 0);
						i6 = 1;
					}
					else if(i8 >= 5 && i8 < 10)
					{
						st.addExpAndSp(317961, 0);
						i6 = 1;
					}
					else if(i8 >= 10 && i8 < 25)
					{
						st.addExpAndSp(357706, 0);
						i6 = 2;
					}
					else if(i8 >= 25 && i8 < 40)
					{
						st.addExpAndSp(397451, 0);
						i6 = 2;
					}
					else if(i8 >= 40 && i8 < 60)
					{
						st.addExpAndSp(596176, 0);
						i6 = 2;
					}
					else if(i8 >= 60 && i8 < 72)
					{
						st.addExpAndSp(715411, 0);
						i6 = 3;
					}
					else if(i8 >= 72 && i8 < 81)
					{
						st.addExpAndSp(794901, 0);
						i6 = 3;
					}
					else if(i8 >= 81 && i8 < 89)
					{
						st.addExpAndSp(914137, 0);
						i6 = 3;
					}
					else
					{
						st.addExpAndSp(1192352, 0);
						i6 = 4;
					}

					if(i9 == 0)
					{
						st.addExpAndSp(0, 15892);
						i7 = 1;
					}
					else if(i9 >= 1 && i9 < 5)
					{
						st.addExpAndSp(0, 22249);
						i7 = 1;
					}
					else if(i9 >= 5 && i9 < 10)
					{
						st.addExpAndSp(0, 25427);
						i7 = 1;
					}
					else if(i9 >= 10 && i9 < 25)
					{
						st.addExpAndSp(0, 28606);
						i7 = 2;
					}
					else if(i9 >= 25 && i9 < 40)
					{
						st.addExpAndSp(0, 31784);
						i7 = 2;
					}
					else if(i9 >= 40 && i9 < 60)
					{
						st.addExpAndSp(0, 47677);
						i7 = 2;
					}
					else if(i9 >= 60 && i9 < 72)
					{
						st.addExpAndSp(0, 57212);
						i7 = 3;
					}
					else if(i9 >= 72 && i9 < 81)
					{
						st.addExpAndSp(0, 63569);
						i7 = 3;
					}
					else if(i9 >= 81 && i9 < 89)
					{
						st.addExpAndSp(0, 73104);
						i7 = 3;
					}
					else
					{
						st.addExpAndSp(0, 95353);
						i7 = 4;
					}

					if(i6 == 1 && i7 == 1)
					{
						showPage("collecter_gutenhagen_q0463_09.htm", talker);
					}
					else if(i6 == 1 && i7 == 2 || i6 == 2 && i7 == 1)
					{
						showPage("collecter_gutenhagen_q0463_10.htm", talker);
					}
					else if(i7 == 1 && i6 == 3 || i6 == 3 && i7 == 1 || i6 == 1 && i7 == 4 || i6 == 4 && i7 == 1)
					{
						showPage("collecter_gutenhagen_q0463_11.htm", talker);
					}
					else if(i6 == 2 && i7 == 2 || i6 == 2 && i7 == 3 || i6 == 3 && i7 == 2)
					{
						showPage("collecter_gutenhagen_q0463_12.htm", talker);
					}
					else if(i6 == 2 && i7 == 4 || i6 == 4 && i7 == 2 || i6 == 3 && i7 == 3)
					{
						showPage("collecter_gutenhagen_q0463_13.htm", talker);
					}
					else if(i6 == 3 && i7 == 4 || i6 == 4 && i7 == 3)
					{
						showPage("collecter_gutenhagen_q0463_14.htm", talker);
					}
					else
					{
						showPage("collecter_gutenhagen_q0463_15.htm", talker);
					}

					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithMemoState(killer, 1);
		if(st != null && st.getQuestItemsCount(q_log_roll_of_golemgroup) < 1)
		{
			if(npc.getNpcId() == golem_boom1_p)
			{
				if(st.getQuestItemsCount(q_log_of_golemgroup) + 1 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					st.giveItems(q_log_of_golemgroup, 1);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), "1");
			}
			else if(npc.getNpcId() == golem_cannon1_p || npc.getNpcId() == golem_cannon2_p || npc.getNpcId() == golem_cannon3_p)
			{
				int i0;
				if(st.getInt("ex_2") == 0)
				{
					i0 = 1 + Rnd.get(100);
				}
				else
				{
					i0 = 5;
				}

				if(st.getQuestItemsCount(q_log_of_golemgroup) + i0 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					st.giveItems(q_log_of_golemgroup, i0);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), String.valueOf(i0));
			}
			else if(npc.getNpcId() == golem_carrier_p)
			{
				if(st.getQuestItemsCount(q_log_of_golemgroup) - 1 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else if(st.getQuestItemsCount(q_log_of_golemgroup) > 1)
				{
					st.takeItems(q_log_of_golemgroup, 1);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), "-1");
			}
			else if(npc.getNpcId() == golem_guardian_p)
			{
				int i0;
				if(st.getInt("ex_2") == 2)
				{
					i0 = 1 + Rnd.get(100);
				}
				else
				{
					i0 = 2;
				}

				if(st.getQuestItemsCount(q_log_of_golemgroup) + i0 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					st.giveItems(q_log_of_golemgroup, i0);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), String.valueOf(i0));
			}
			else if(npc.getNpcId() == golem_micro_p)
			{
				int i0;
				if(st.getInt("ex_2") == 3)
				{
					i0 = 1 + Rnd.get(100);
				}
				else
				{
					i0 = -3;
				}

				if(st.getQuestItemsCount(q_log_of_golemgroup) + i0 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else if(i0 > 0)
				{
					st.giveItems(q_log_of_golemgroup, i0);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(q_log_of_golemgroup) > 3)
				{
					st.takeItems(q_log_of_golemgroup, 3);
					st.playSound(SOUND_ITEMGET);
				}
				else
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), String.valueOf(i0));
			}
			else if(npc.getNpcId() == golem_prop1_p)
			{
				int i0;
				if(st.getInt("ex_2") == 2)
				{
					i0 = 1 + Rnd.get(100);
				}
				else
				{
					i0 = -2;
				}

				if(st.getQuestItemsCount(q_log_of_golemgroup) + i0 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else if(i0 > 0)
				{
					st.giveItems(q_log_of_golemgroup, i0);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(q_log_of_golemgroup) > 2)
				{
					st.takeItems(q_log_of_golemgroup, 2);
					st.playSound(SOUND_ITEMGET);
				}
				else
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), String.valueOf(i0));
			}
			else if(npc.getNpcId() == golem_prop2_p || npc.getNpcId() == golem_prop3_p)
			{
				int i0;
				if(st.getInt("ex_2") == 1)
				{
					i0 = 1 + Rnd.get(100);
				}
				else
				{
					i0 = -2;
				}

				if(st.getQuestItemsCount(q_log_of_golemgroup) + i0 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else if(i0 > 0)
				{
					st.giveItems(q_log_of_golemgroup, i0);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(q_log_of_golemgroup) > 2)
				{
					st.takeItems(q_log_of_golemgroup, 2);
					st.playSound(SOUND_ITEMGET);
				}
				else
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), String.valueOf(i0));
			}
			else if(npc.getNpcId() == golem_steel_p)
			{
				if(st.getQuestItemsCount(q_log_of_golemgroup) + 3 == st.getInt("ex_1"))
				{
					st.takeItems(q_log_of_golemgroup, -1);
					st.giveItems(q_log_roll_of_golemgroup, 1);
					st.setMemoState(2);
					st.playSound(SOUND_ITEMGET);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					st.giveItems(q_log_of_golemgroup, 3);
					st.playSound(SOUND_ITEMGET);
				}
				Functions.npcSay(npc, Say2C.ALL, 46350, st.getPlayer().getName(), "3");
			}
		}
	}
}