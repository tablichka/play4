package quests._022_TragedyInVonHellmannForest;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

public class _022_TragedyInVonHellmannForest extends Quest
{
	// Npc
	public final int umul = 31527;
	public final int grandmagister_tifaren = 31334;
	public final int highpriest_innocentin = 31328;
	public final int ghost_of_umul = 27217;
	public final int rune_ghost2 = 31528;
	public final int rune_ghost3 = 31529;

	// Items
	public final int ReportBox = 7147;
	public final int LostSkullofElf = 7142;
	public final int CrossofEinhasad = 7141;
	public final int SealedReportBox = 7146;
	public final int LetterofInnocentin = 7143;
	public final int JewelofAdventurerRed = 7145;
	public final int JewelofAdventurerGreen = 7144;

	// Mobs
	private int[] mobs = { 21553, 21554, 21555, 21556, 21561 };

	public _022_TragedyInVonHellmannForest()
	{
		super(22, "_022_TragedyInVonHellmannForest", "Tragedy In Von Hellmann Forest");

		addStartNpc(grandmagister_tifaren);

		addTalkId(grandmagister_tifaren, rune_ghost2, highpriest_innocentin, rune_ghost3, umul);

		addAttackId(ghost_of_umul);
		addKillId(ghost_of_umul);
		addKillId(mobs);

		addQuestItem(ReportBox, LostSkullofElf, SealedReportBox, LetterofInnocentin, JewelofAdventurerRed, JewelofAdventurerGreen);
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

		if(npcId == grandmagister_tifaren)
		{
			if(st.isCreated())
			{
				if(reply == 1)
				{
					if(player.isQuestComplete(21) && player.getLevel() >= 63)
						showQuestPage("grandmagister_tifaren_q0022_02.htm", player);
					else
						showPage("grandmagister_tifaren_q0022_03.htm", player);
				}
				else if(reply == 22 && player.isQuestComplete(21) && player.getLevel() >= 63)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					st.setState(STARTED);
					showQuestPage("grandmagister_tifaren_q0022_04.htm", player);
				}
			}
			else if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 2)
				{
					if(memoState == 1 && st.haveQuestItems(CrossofEinhasad))
						showPage("grandmagister_tifaren_q0022_06.htm", player);
					else if(memoState == 1 && !st.haveQuestItems(CrossofEinhasad))
					{
						showPage("grandmagister_tifaren_q0022_07.htm", player);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(reply == 3 && memoState == 1 && st.haveQuestItems(CrossofEinhasad))
				{
					st.setMemoState(2);
					showPage("grandmagister_tifaren_q0022_08.htm", player);
					st.setCond(4);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 4)
					showPage("grandmagister_tifaren_q0022_12.htm", player);
				else if(reply == 5)
				{
					L2NpcInstance npc = player.getLastNpc();
					if(memoState == 2 && st.haveQuestItems(CrossofEinhasad) && st.haveQuestItems(LostSkullofElf))
					{
						if(npc.i_quest0 == 0)
						{
							npc.i_quest0 = 1;
							npc.i_quest1 = player.getObjectId();
							L2NpcInstance ghost = addSpawn(rune_ghost2, new Location(38354, -49777, -1128), false);
							ghost.i_ai0 = player.getObjectId();
							ghost.c_ai0 = npc.getStoredId();
							Functions.npcSay(ghost, Say2C.ALL, 2250, player.getName());
							st.takeItems(LostSkullofElf, -1);
							st.setMemoState(4);
							showPage("grandmagister_tifaren_q0022_13.htm", player);
							st.setCond(7);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("grandmagister_tifaren_q0022_14.htm", player);
							st.setCond(6);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
						}
					}
					else if(memoState == 4 && st.haveQuestItems(CrossofEinhasad))
					{
						if(npc.i_quest0 == 0)
						{
							npc.i_quest0 = 1;
							npc.i_quest1 = player.getObjectId();
							L2NpcInstance ghost = addSpawn(rune_ghost2, new Location(38354, -49777, -1128), false);
							ghost.i_ai0 = player.getObjectId();
							ghost.c_ai0 = npc.getStoredId();
							showPage("grandmagister_tifaren_q0022_13.htm", player);
						}
						else
						{
							showPage("grandmagister_tifaren_q0022_14.htm", player);
							st.setCond(6);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
						}
					}
				}
			}
		}
		else if(npcId == rune_ghost2)
		{
			if(reply == 6)
			{
				showPage("rune_ghost2_q0022_04.htm", player);
				st.playSound("AmbSound.d_horror_03");
			}
			else if(reply == 7)
			{
				st.setMemoState(5);
				showPage("rune_ghost2_q0022_08.htm", player);
				player.getLastNpc().getAI().addTimer(2202, 3000);
				st.setCond(8);
				showQuestMark(player);
			}
		}
		else if(npcId == highpriest_innocentin)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 8 && memoState == 5)
				{
					st.takeItems(CrossofEinhasad, -1);
					st.setMemoState(6);
					showPage("highpriest_innocentin_q0022_03.htm", player);
				}
				else if(reply == 10 && memoState == 6)
				{
					st.giveItems(LetterofInnocentin, 1);
					st.setMemoState(7);
					showPage("highpriest_innocentin_q0022_09.htm", player);
					st.setCond(9);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 17 && memoState == 12 && st.haveQuestItems(ReportBox))
				{
					st.takeItems(ReportBox, -1);
					st.setMemoState(13);
					showPage("highpriest_innocentin_q0022_11.htm", player);
					st.setCond(15);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 18)
					showPage("highpriest_innocentin_q0022_13.htm", player);
				else if(reply == 19 && memoState == 13)
				{
					st.setMemoState(14);
					showPage("highpriest_innocentin_q0022_19.htm", player);
					st.setCond(16);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == rune_ghost3)
		{
			if(reply == 11)
				showPage("rune_ghost3_q0022_02.htm", player);
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 7 && st.haveQuestItems(LetterofInnocentin))
				{
					st.takeItems(LetterofInnocentin, -1);
					st.setMemoState(8);
					showPage("rune_ghost3_q0022_03.htm", player);
				}
			}
			else if(reply == 13)
				showPage("rune_ghost3_q0022_04.htm", player);
			else if(reply == 14)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					st.setMemoState(9);
					showPage("rune_ghost3_q0022_08.htm", player);
				}
			}
			else if(reply == 16)
				showPage("rune_ghost3_q0022_09.htm", player);
			else if(reply == 15)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					st.giveItems(JewelofAdventurerGreen, 1);
					st.setMemoState(10);
					showPage("rune_ghost3_q0022_11.htm", player);
					st.setCond(10);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == umul)
		{
			if ( reply == 16 )
			{
				L2NpcInstance npc = player.getLastNpc();
				if ( npc.i_quest0 == 0 )
				{
					npc.i_quest0 = 1;
					L2NpcInstance ghost = addSpawn(ghost_of_umul, new Location(34706, -54590, -2054), false, 120000);
					ghost.i_ai0 = player.getObjectId();
					ghost.i_ai1 = (int)(System.currentTimeMillis() / 1000);
					ghost.c_ai0 = npc.getStoredId();
					ghost.addDamageHate(player, 0, 1000);
					ghost.setRunning();
					ghost.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
					showPage("umul_q0022_02.htm", player);
					st.playSound("SkillSound3.antaras_fear");
				}
				else
					showPage("umul_q0022_03.htm", player);
			}
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(npc.getNpcId() == ghost_of_umul)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(!npc.isDecayed() && c0 != null)
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
		if(st.isCompleted())
			return "completed";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == grandmagister_tifaren)
		{
			if(st.isCreated())
				return "grandmagister_tifaren_q0022_01.htm";
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:grandmagister_tifaren_q0022_05.htm";
				if(cond == 2)
				{
					if(st.haveQuestItems(CrossofEinhasad) && !st.haveQuestItems(LostSkullofElf))
						return "npchtm:grandmagister_tifaren_q0022_09.htm";
					if(st.haveQuestItems(CrossofEinhasad) && st.haveQuestItems(LostSkullofElf))
						return "npchtm:grandmagister_tifaren_q0022_10.htm";
				}
				if(cond == 4 && st.haveQuestItems(CrossofEinhasad))
				{
					if(npc.i_quest0 == 1)
					{
						if(npc.i_quest1 == st.getPlayer().getObjectId())
							return "npchtm:grandmagister_tifaren_q0022_15.htm";

						st.setCond(6);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
						return "npchtm:grandmagister_tifaren_q0022_16.htm";
					}

					return "npchtm:grandmagister_tifaren_q0022_17.htm";
				}
				if(cond == 5 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:grandmagister_tifaren_q0022_19.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == rune_ghost2)
			{
				if(npc.i_ai0 != st.getPlayer().getObjectId())
				{
					st.playSound("AmbSound.d_horror_15");
					return "npchtm:rune_ghost2_q0022_01a.htm";
				}
				else
				{
					st.playSound("AmbSound.d_horror_15");
					return "npchtm:rune_ghost2_q0022_01.htm";
				}
			}
			else if(npcId == highpriest_innocentin)
			{
				if(cond < 5 && !st.haveQuestItems(CrossofEinhasad))
				{
					st.giveItems(CrossofEinhasad, 1);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:highpriest_innocentin_q0022_01.htm";
				}
				if(cond < 5 && st.haveQuestItems(CrossofEinhasad))
					return "npchtm:highpriest_innocentin_q0022_01b.htm";
				if(cond == 5)
					return "npchtm:highpriest_innocentin_q0022_02.htm";
				if(cond == 6)
					return "npchtm:highpriest_innocentin_q0022_04.htm";
				if(cond == 7)
					return "npchtm:highpriest_innocentin_q0022_09a.htm";
				if(cond == 12 && st.haveQuestItems(ReportBox))
					return "npchtm:highpriest_innocentin_q0022_10.htm";
				if(cond == 13)
					return "npchtm:highpriest_innocentin_q0022_12.htm";
				if(cond == 14 && st.getPlayer().getLevel() >= 64)
				{
					st.addExpAndSp(345966, 31578);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:highpriest_innocentin_q0022_20.htm";
				}
				if(cond == 14 && st.getPlayer().getLevel() < 64)
				{
					st.addExpAndSp(345966, 31578);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:highpriest_innocentin_q0022_21.htm";
				}
			}
			else if(npcId == rune_ghost3)
			{
				if(cond == 7 && st.haveQuestItems(LetterofInnocentin))
					return "npchtm:rune_ghost3_q0022_01.htm";
				if(cond == 8)
					return "npchtm:rune_ghost3_q0022_03a.htm";
				if(cond == 9)
					return "npchtm:rune_ghost3_q0022_10.htm";
				if(cond == 10 && st.haveQuestItems(JewelofAdventurerGreen))
					return "npchtm:rune_ghost3_q0022_12.htm";
				if(cond == 11 && st.haveQuestItems(JewelofAdventurerGreen))
					return "npchtm:rune_ghost3_q0022_14.htm";
				if(cond == 11 && st.haveQuestItems(JewelofAdventurerRed) && !st.haveQuestItems(SealedReportBox))
				{
					st.setCond(12);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:rune_ghost3_q0022_15.htm";
				}
				if(cond == 11 && st.haveQuestItems(JewelofAdventurerRed) && st.haveQuestItems(SealedReportBox))
				{
					st.giveItems(ReportBox, 1);
					st.takeItems(SealedReportBox, -1);
					st.takeItems(JewelofAdventurerRed, -1);
					st.setMemoState(12);
					st.setCond(14);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:rune_ghost3_q0022_16.htm";
				}
				if(cond == 12 && st.haveQuestItems(ReportBox))
					return "npchtm:rune_ghost3_q0022_17.htm";
			}
			else if(npcId == umul)
			{
				if ((cond == 10 || cond == 11) && st.haveQuestItems(JewelofAdventurerGreen))
				{
					st.playSound("AmbSound.dd_horror_01");
					return "npchtm:umul_q0022_01.htm";
				}
				if (cond == 11 && st.haveQuestItems(JewelofAdventurerRed) && !st.haveQuestItems(SealedReportBox))
				{
					st.giveItems(SealedReportBox, 1);
					st.setCond(13);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:umul_q0022_04.htm";
				}
				if (cond == 11 && st.haveQuestItems(JewelofAdventurerRed) && st.haveQuestItems(SealedReportBox))
					return "npchtm:umul_q0022_05.htm";
				if (cond > 11)
					return "npchtm:umul_q0022_05.htm";
			}
		}

		return "noquest";
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		if(npcId == ghost_of_umul)
		{
			if(st.getMemoState() == 10 && st.haveQuestItems(JewelofAdventurerGreen))
				st.setMemoState(11);
			else if(st.getMemoState() == 11 && st.haveQuestItems(JewelofAdventurerGreen))
			{
				if(npc.i_ai1 + 90 < (int)(System.currentTimeMillis() / 1000))
				{
					st.takeItems(JewelofAdventurerGreen, -1);
					st.giveItems(JewelofAdventurerRed, 1);
					st.playSound(SOUND_ITEMGET);
					st.setCond(11);
					showQuestMark(st.getPlayer());
				}
			}
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		QuestState st = killer.getQuestState(getName());

		if(npcId == ghost_of_umul)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;
		}
		else if(st != null && contains(mobs, npcId))
		{
			if(st.getMemoState() == 2 && st.haveQuestItems(CrossofEinhasad) && !st.haveQuestItems(LostSkullofElf) && st.rollAndGiveLimited(LostSkullofElf, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				st.setCond(5);
				showQuestMark(st.getPlayer());
			}
		}
	}
}