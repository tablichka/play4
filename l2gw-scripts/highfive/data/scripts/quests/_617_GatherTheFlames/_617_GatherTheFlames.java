package quests._617_GatherTheFlames;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _617_GatherTheFlames extends Quest
{
	//npc
	private final static int VULCAN = 31539;
	private final static int HILDA = 31271;
	private final static int ROONEY = 32049;
	//items
	private final static int TORCH = 7264;
	//DROPLIST (MOB_ID, CHANCE)
	private final static int[][] DROPLIST = {
			{22634, 63},
			{22635, 61},
			{22636, 64},
			{22637, 63},
			{22638, 63},
			{22639, 64},
			{22640, 55},
			{22641, 58},
			{22642, 53},
			{22643, 61},
			{22644, 63},
			{22645, 55},
			{22646, 59},
			{22647, 68},
			{22648, 63},
			{22649, 68}};

	public _617_GatherTheFlames()
	{
		super(617, "_617_GatherTheFlames", "Gather the Flames"); // Party true

		addStartNpc(VULCAN);
		addStartNpc(HILDA);
		addTalkId(ROONEY);

		for(int[] element : DROPLIST)
			addKillId(element[0]);

		addQuestItem(TORCH);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();
		if(npc == null)
			return;

		int npcId = npc.getNpcId();


		if(npcId == HILDA)
		{
			if(reply == 617) // accept
			{
				st.setState(STARTED);
				showQuestMark(player);
				st.playSound(SOUND_ACCEPT);
				st.setCond(1);
				showQuestPage("blacksmith_hilda_q0617_03.htm", player);
				return;
			}
		}
		else if(npcId == VULCAN)
		{
			if(reply == 617) // accept
			{
				st.setState(STARTED);
				st.setCond(1);
				showQuestMark(player);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("warsmith_vulcan_q0617_03.htm", player);
				return;
			}
			else if(reply == 1)
			{
				showPage("warsmith_vulcan_q0617_06.htm", player);
				return;
			}
			else if(reply == 3)
			{
				if(st.getQuestItemsCount(TORCH) >= 1000)
				{
					int i0 = Rnd.get(10);
					switch(i0)
					{
						case 0:
							st.giveItems(6881, 1);
							break;
						case 1:
							st.giveItems(6883, 1);
							break;
						case 2:
							st.giveItems(6885, 1);
							break;
						case 3:
							st.giveItems(6887, 1);
							break;
						case 4:
							st.giveItems(7580, 1);
							break;
						case 5:
							st.giveItems(6891, 1);
							break;
						case 6:
							st.giveItems(6893, 1);
							break;
						case 7:
							st.giveItems(6895, 1);
							break;
						case 8:
							st.giveItems(6897, 1);
							break;
						case 9:
							st.giveItems(6899, 1);
							break;
						default:
							break;
					}

					st.takeItems(TORCH, 1000);
					showPage("warsmith_vulcan_q0617_07.htm", player);
					return;
				}
			}
			else if(reply == 4)
			{
				st.takeItems(TORCH, -1);
				st.playSound(SOUND_FINISH);
				showPage("warsmith_vulcan_q0617_08.htm", player);
				st.exitCurrentQuest(true);
				return;
			}
		}
		else if(npcId == ROONEY)
		{
			if(reply == 1 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6881, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_03.htm", player);
				return;
			}
			else if(reply == 2 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6883, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_04.htm", player);
				return;
			}
			else if(reply == 3 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6885, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_05.htm", player);
				return;
			}
			else if(reply == 4 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6887, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_06.htm", player);
				return;
			}
			else if(reply == 5 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(7580, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_07.htm", player);
				return;
			}
			else if(reply == 6 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6891, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_08.htm", player);
				return;
			}
			else if(reply == 7 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6893, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_09.htm", player);
				return;
			}
			else if(reply == 8 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6895, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_10.htm", player);
				return;
			}
			else if(reply == 9 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6897, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_11.htm", player);
				return;
			}
			else if(reply == 10 && st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200 && npc.getDistance(player) <= 1500)
			{
				st.giveItems(6899, 1);
				st.takeItems(TORCH, 1200);
				showPage("warsmith_rooney_q0617_12.htm", player);
				return;
			}
		}

		showPage("noquest", player);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == HILDA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 74)
					htmltext = "blacksmith_hilda_q0617_01.htm";
				else
				{
					htmltext = "blacksmith_hilda_q0617_02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.isStarted())
			{
				htmltext = "npchtm:blacksmith_hilda_q0617_04.htm";
			}
		}
		else if(npcId == VULCAN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 74)
					htmltext = "warsmith_vulcan_q0617_01.htm";
				else
				{
					htmltext = "warsmith_vulcan_q0617_02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.isStarted())
			{
				if(st.getQuestItemsCount(TORCH) >= 1000)
					htmltext = "npchtm:warsmith_vulcan_q0617_04.htm";
				else
					htmltext = "npchtm:warsmith_vulcan_q0617_05.htm";
			}
		}
		else if(npcId == ROONEY)
		{
			if(st.isStarted() && st.getQuestItemsCount(TORCH) < 1200)
			{
				htmltext = "npchtm:warsmith_rooney_q0617_01.htm";
			}
			else if(st.isStarted() && st.getQuestItemsCount(TORCH) >= 1200)
			{
				htmltext = "npchtm:warsmith_rooney_q0617_02.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null)
			for(int[] element : DROPLIST)
				if(npc.getNpcId() == element[0])
				{
					st.rollAndGive(TORCH, 1, element[1]);
					return;
				}

	}
}