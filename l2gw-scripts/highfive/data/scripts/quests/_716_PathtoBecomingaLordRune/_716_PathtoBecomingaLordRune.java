package quests._716_PathtoBecomingaLordRune;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 22.03.12 21:50
 */
public class _716_PathtoBecomingaLordRune extends Quest
{
	// NPC
	private static final int chamberlain_frederick = 35509;
	private static final int falsepriest_agripel = 31348;
	private static final int highpriest_innocentin = 31328;

	public _716_PathtoBecomingaLordRune()
	{
		super(716, "_716_PathtoBecomingaLordRune", "Path to Becoming a Lord Rune");
		addStartNpc(chamberlain_frederick);
		addStartNpc(highpriest_innocentin);
		addTalkId(chamberlain_frederick, falsepriest_agripel, highpriest_innocentin);

		addKillId(22176, 22146, 22151, 22138, 22141, 22175, 31328, 22155, 22159, 22163, 22167, 22171, 22143, 22137);
		addKillId(22194, 22164, 22156, 22166, 22173, 22170, 22157, 22160, 22165, 22168, 22174, 22158, 22162, 22149);
		addKillId(22147, 22154, 22161, 22169, 22172, 22145, 22152, 22153, 22136, 22150, 22148, 22142, 22144, 22139, 22140);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_frederick)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(88).hasLord())
					return "chamberlain_frederick_q0716_01.htm";

				return "chamberlain_frederick_q0716_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					if(!talker.isQuestComplete(25))
						return "npchtm:chamberlain_frederick_q0716_06.htm";

					st.setMemoState(2);
					st.setMemoStateEx(1, 0);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_frederick_q0716_07.htm";
				}
				if(st.getMemoState() == 2 && npc.isMyLord(talker))
					return "npchtm:chamberlain_frederick_q0716_08.htm";
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
				{
					st.setMemoState(4);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_frederick_q0716_09.htm";
				}
				if(st.getMemoState() == 4 && npc.isMyLord(talker))
					return "npchtm:chamberlain_frederick_q0716_10.htm";
				if(st.getMemoState() == 5 && npc.isMyLord(talker))
					return "npchtm:chamberlain_frederick_q0716_19.htm";
				if(st.getMemoState() == 6 && npc.isMyLord(talker))
				{
					st.setMemoState(st.getMemoState() + 10);
					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_frederick_q0716_20.htm";
				}
				if(st.getMemoState() / 10 == 1)
					return "npchtm:chamberlain_frederick_q0716_21.htm";
				if(st.getMemoState() / 10 == 2 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_frederick_q0716_22a.htm";
					if(ResidenceManager.getInstance().getBuildingById(110).getContractCastleId() != 8)
						return "npchtm:chamberlain_frederick_q0716_22b.htm";

					Functions.npcSay(npc, 71659, talker.getName());
					talker.setVar("territory_lord_88", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(88));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:chamberlain_frederick_q0716_22.htm";
				}
			}
			if(!npc.isMyLord(talker))
			{
				if(npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						if(c0.isQuestStarted(716) && c0.getQuestState(716).getMemoState() == 4)
						{
							if(npc.isInRange(c0, 1500))
								return "npchtm:chamberlain_frederick_q0716_16.htm";

							return "npchtm:chamberlain_frederick_q0716_15.htm";
						}

						return "npchtm:chamberlain_frederick_q0716_13.htm";
					}

					return "npchtm:chamberlain_frederick_q0716_12.htm";
				}

				return "npchtm:chamberlain_frederick_q0716_11.htm";
			}
		}
		else if(npc.getNpcId() == falsepriest_agripel)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2 && npc.isMyLord(talker))
					return "npchtm:falsepriest_agripel_q0716_01.htm";
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
					return "npchtm:falsepriest_agripel_q0716_04.htm";
				if(st.getMemoState() / 10 == 1 && npc.isMyLord(talker))
					return "npchtm:falsepriest_agripel_q0716_05.htm";
				if(st.getMemoState() / 10 == 2 && npc.isMyLord(talker))
					return "npchtm:falsepriest_agripel_q0716_11.htm";
			}
		}
		else if(npc.getNpcId() == highpriest_innocentin)
		{
			if(!npc.isMyLord(talker))
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = null;
					if(c0.isQuestStarted(716) && (qs = c0.getQuestState(716)) != null && qs.getMemoState() == 5)
					{
						if(talker.getObjectId() == qs.getMemoStateEx(1))
							return "npchtm:highpriest_innocentin_q0716_03.htm";

						return "npchtm:highpriest_innocentin_q0716_03a.htm";
					}
					if(qs != null && qs.getMemoState() < 5)
						return "npchtm:highpriest_innocentin_q0716_02.htm";
					if(qs != null && qs.getMemoState() % 10 == 6)
						return "npchtm:highpriest_innocentin_q0716_06.htm";
				}

				return "npchtm:highpriest_innocentin_q0716_01.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_frederick)
		{
			if(reply == 716)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(88).hasLord())
				{
					if(!talker.isQuestComplete(25))
					{
						st.setMemoState(1);
						st.playSound(SOUND_ACCEPT);
						showQuestPage("chamberlain_frederick_q0716_04.htm", talker);
						st.setCond(1);
						st.setState(STARTED);
					}
					else 
					{
						st.setMemoState(2);
						showQuestPage("chamberlain_frederick_q0716_05.htm", talker);
						st.setCond(2);
						st.setState(STARTED);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(88).hasLord())
				{
					showQuestPage("chamberlain_frederick_q0716_02.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(!npc.isMyLord(talker))
				{
					if(npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
					{
						L2Player c0 = Util.getClanLeader(talker);
						if(c0 != null)
						{
							QuestState qs;
							if(c0.isQuestStarted(716) && (qs = c0.getQuestState(716)) != null && qs.getMemoState() == 4)
							{
								if(npc.isInRange(c0, 1500))
								{
									showHtmlFile(talker, "chamberlain_frederick_q0716_17.htm", new String[]{ "<?name?>" }, new String[] { talker.getName() }, false);
									Functions.npcSay(npc, 71652, talker.getName());
									qs.setMemoState(5);
									qs.setCond(5);
									showQuestMark(c0);
									qs.playSound(SOUND_MIDDLE);
									qs.setMemoStateEx(1, talker.getObjectId());
								}
								else
								{
									showPage("chamberlain_frederick_q0716_18.htm", talker);
								}
							}
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == falsepriest_agripel)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("falsepriest_agripel_q0716_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("falsepriest_agripel_q0716_03.htm", talker);
					st.setMemoState(3);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1)
				{
					if(st.getMemoStateEx(1) >= 100)
					{
						showPage("falsepriest_agripel_q0716_07.htm", talker);
					}
					else
					{
						showPage("falsepriest_agripel_q0716_06.htm", talker);
					}
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1 && st.getMemoStateEx(1) < 100)
				{
					showPage("falsepriest_agripel_q0716_08.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1 && st.getMemoStateEx(1) >= 100)
				{
					showPage("falsepriest_agripel_q0716_09.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1 && st.getMemoStateEx(1) >= 100)
				{
					st.setMemoState(st.getMemoState() + 10);
					showPage("falsepriest_agripel_q0716_10.htm", talker);
					st.setCond(8);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == highpriest_innocentin)
		{
			if(reply == 1)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null && c0.isQuestStarted(716) && c0.getQuestState(716).getMemoState() == 5)
				{
					showPage("highpriest_innocentin_q0716_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				L2Player c0 = Util.getClanLeader(talker);
				QuestState qs;
				if(c0 != null && c0.isQuestStarted(716) && (qs = c0.getQuestState(716)).getMemoState() == 5)
				{
					qs.setMemoState(6);
					qs.setMemoStateEx(1, 0);
					qs.setCond(6);
					showQuestMark(c0);
					qs.playSound(SOUND_MIDDLE);
					showPage("highpriest_innocentin_q0716_05.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		QuestState qs;
		if(c0 != null && c0.isQuestStarted(716) && (qs = c0.getQuestState(716)) != null && qs.getMemoState() % 10 == 6 && qs.getMemoState() / 10 == 1 && qs.getMemoStateEx(1) < 100)
		{
			qs.setMemoStateEx(1, qs.getMemoStateEx(1) + 1);
		}
	}
}