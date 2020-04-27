package quests._702_ATrapforRevenge;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 23.11.2010 11:17:12
 */
public class _702_ATrapforRevenge extends Quest
{
	// NPCs
	private static final int PLENOS = 32563;
	private static final int TENIUS = 32555;
	private static final int LEKON = 32557;

	// MOBs
	private static final int DRAKE1 = 22610;
	private static final int DRAKE2 = 22611;
	private static final int DRAC1 = 22612;
	private static final int DRAC2 = 22613;
	private static final int DRAKE3 = 25631;
	private static final int DRAC3 = 25632;
	private static final int MUTATED_DRAKE = 25626;

	// Items
	private static final int DRAKES_FLESH = 13877;
	private static final int ROTTEN_BLOOD = 13878;
	private static final int BAIT_FOR_DRAKES = 13879;
	private static final int DRAKE_WING = 13880;
	private static final int RED_STAR_STONE = 14009;

	public _702_ATrapforRevenge()
	{
		super(702, "_702_ATrapforRevenge", "A Trap for Revenge");
		addStartNpc(PLENOS);
		addTalkId(PLENOS, TENIUS, LEKON);
		addKillId(DRAC1, DRAC2, DRAC3, DRAKE1, DRAKE2, DRAKE3, MUTATED_DRAKE);
	}

	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();

		if(event.equals("wharf_soldier_plenos_q0702_03.htm"))
		{
			if(player.getLevel() >= 78 && player.isQuestComplete(10273))
				return event;
		}
		else if(event.equals("wharf_soldier_plenos_q0702_04.htm"))
		{
			if(player.getLevel() >= 78 && player.isQuestComplete(10273))
			{
				st.setCond(1);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return event;
			}
		}
		else if(event.equals("wharf_soldier_plenos_q0702_07.htm"))
		{
			if(st.getCond() == 2)
				return st.haveQuestItems(DRAKES_FLESH) ? "npchtm:wharf_soldier_plenos_q0702_08.htm" : "npchtm:wharf_soldier_plenos_q0702_07.htm";
		}
		else if(event.equals("wharf_soldier_plenos_q0702_09.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(DRAKES_FLESH))
			{
				st.rollAndGive(57, st.getQuestItemsCount(DRAKES_FLESH) * 100, 100);
				st.takeItems(DRAKES_FLESH, -1);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("wharf_soldier_plenos_q0702_10.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(DRAKES_FLESH))
				return "npchtm:" + event;
		}
		else if(event.equals("wharf_soldier_plenos_q0702_11.htm"))
		{
			if(st.getCond() == 2)
			{
				if(!st.haveQuestItems(DRAKE_WING))
					return "npchtm:" + event;

				st.rollAndGive(57, st.getQuestItemsCount(DRAKE_WING) * 200000, 100);
				st.takeItems(DRAKE_WING, -1);
				return "npchtm:wharf_soldier_plenos_q0702_12.htm";
			}
		}
		else if(event.equals("wharf_soldier_plenos_q0702_13.htm"))
		{
			if(st.getCond() == 2)
				return "npchtm:" + event;
		}
		else if(event.equals("wharf_soldier_plenos_q0702_14.htm"))
		{
			if(st.getCond() == 2)
			{
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_tenis_q0702_02.htm"))
		{
			if(st.getCond() == 1)
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_tenis_q0702_03.htm"))
		{
			if(st.getCond() == 1)
			{
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_tenis_q0702_05.htm"))
		{
			if(st.getCond() == 2)
			{
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_tenis_q0702_06.htm"))
		{
			if(st.getCond() == 2)
				return st.getQuestItemsCount(DRAKES_FLESH) < 100 ? "npchtm:" + event : "npchtm:soldier_tenis_q0702_07.htm";
		}
		else if(event.equals("soldier_tenis_q0702_08.htm"))
		{
			if(st.getCond() == 2 && st.getQuestItemsCount(DRAKES_FLESH) >= 100)
			{
				st.giveItems(ROTTEN_BLOOD, 1);
				st.takeItems(DRAKES_FLESH, 100);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_tenis_q0702_09.htm"))
		{
			if(st.getCond() == 2 && st.getQuestItemsCount(DRAKES_FLESH) >= 100)
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_tenis_q0702_10.htm"))
		{
			if(st.getCond() == 2)
				return st.haveQuestItems(DRAKE_WING) ? "npchtm:soldier_tenis_q0702_11.htm" : "npchtm:" + event;
		}
		else if(event.equals("soldier_tenis_q0702_12.htm") || event.equals("soldier_tenis_q0702_13.htm") || event.equals("soldier_tenis_q0702_14.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(DRAKE_WING))
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_tenis_q0702_15.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(DRAKE_WING))
			{
				int i0 = Rnd.get(1000);
				int i1 = Rnd.get(1000);
				String htm = "npchtm:";
				if(i0 >= 500 && i1 >= 600)
				{
					st.rollAndGive(57, Rnd.get(49917) + 125000, 100);
					if(i1 < 720)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 840)
					{
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 960)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 1000)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}
					htm += "soldier_tenis_q0702_15.htm";
				}
				else if(i0 >= 500 && i1 < 600)
				{
					st.rollAndGive(57, Rnd.get(49917) + 125000, 100);
					if(i1 >= 210 && i1 < 340)
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
					else if(i1 < 470)
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
					else if(i1 < 600)
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);

					htm += "soldier_tenis_q0702_16.htm";
				}
				else if(i0 < 500 && i1 >= 600)
				{
					st.rollAndGive(57, Rnd.get(49917) + 25000, 100);
					if(i1 < 720)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 840)
					{
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 960)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}
					else if(i1 < 1000)
					{
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);
					}

					htm += "soldier_tenis_q0702_17.htm";
				}
				else if(((i0 < 500) && (i1 < 600)))
				{
					st.rollAndGive(57, Rnd.get(49917) + 25000, 100);

					if(i1 >= 210 && i1 < 340)
						st.rollAndGive(9628, Rnd.get(3) + 1, 100);
					else if(i1 < 470)
						st.rollAndGive(9629, Rnd.get(3) + 1, 100);
					else if(i1 < 600)
						st.rollAndGive(9630, Rnd.get(3) + 1, 100);

					htm += "soldier_tenis_q0702_18.htm";
				}

				st.takeItems(DRAKE_WING, 1);
				return htm;
			}
		}
		else if(event.equals("engineer_recon_q0702_03.htm"))
		{
			if(st.getCond() == 2)
			{
				if(!st.haveQuestItems(ROTTEN_BLOOD) && st.getQuestItemsCount(RED_STAR_STONE) < 100)
					return "npchtm:" + event;
				if(st.haveQuestItems(ROTTEN_BLOOD) && st.getQuestItemsCount(RED_STAR_STONE) < 100)
					return "npchtm:engineer_recon_q0702_04.htm";
				if(!st.haveQuestItems(ROTTEN_BLOOD) && st.getQuestItemsCount(RED_STAR_STONE) >= 100)
					return "npchtm:engineer_recon_q0702_05.htm";
				if(st.haveQuestItems(ROTTEN_BLOOD) && st.getQuestItemsCount(RED_STAR_STONE) >= 100)
				{
					st.giveItems(BAIT_FOR_DRAKES, 1);
					st.takeItems(ROTTEN_BLOOD, 1);
					st.takeItems(RED_STAR_STONE, 100);
					return "npchtm:engineer_recon_q0702_06.htm";
				}
			}
		}

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == PLENOS)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 78 && player.isQuestComplete(10273))
					return "wharf_soldier_plenos_q0702_01.htm";

				st.exitCurrentQuest(true);
				return "wharf_soldier_plenos_q0702_02.htm";
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:wharf_soldier_plenos_q0702_05.htm";
				if(cond == 2)
					return "npchtm:wharf_soldier_plenos_q0702_06.htm";
			}
		}
		else if(npcId == TENIUS)
		{
			if(cond == 1)
				return "npchtm:soldier_tenis_q0702_01.htm";
			if(cond == 2)
				return "npchtm:soldier_tenis_q0702_04.htm";
		}
		else if(npcId == LEKON)
		{
			if(cond == 1)
				return "npchtm:engineer_recon_q0702_01.htm";
			if(cond == 2)
				return "npchtm:engineer_recon_q0702_02.htm";
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithQuest(killer, 2);
		if(qs != null)
		{
			boolean give = false;
			if(npc.getNpcId() == DRAC1)
				give = qs.rollAndGive(DRAKES_FLESH, Rnd.chance(41.3) ? 2 : 1, 100);
			else if(npc.getNpcId() == DRAC2)
				give = qs.rollAndGive(DRAKES_FLESH, Rnd.chance(44) ? 2 : 1, 100);
			else if(npc.getNpcId() == DRAC3)
				give = qs.rollAndGive(DRAKES_FLESH, 1, 99.6);
			else if(npc.getNpcId() == DRAKE1 || npc.getNpcId() == DRAKE3)
				give = qs.rollAndGive(DRAKES_FLESH, Rnd.chance(48.5) ? 2 : 1, 100);
			else if(npc.getNpcId() == DRAKE2)
				give = qs.rollAndGive(DRAKES_FLESH, Rnd.chance(45.1) ? 2 : 1, 100);
			else if(npc.getNpcId() == MUTATED_DRAKE)
			{
				int i0 = Rnd.get(1000);
				if(i0 < 708)
					give = qs.rollAndGive(DRAKE_WING, 1 + Rnd.get(2), 100);
				else if(i0 < 978)
					give = qs.rollAndGive(DRAKE_WING, 3 + Rnd.get(3), 100);
				else if(i0 < 994)
					give = qs.rollAndGive(DRAKE_WING, 6 + Rnd.get(4), 100);
				else if(i0 < 998)
					give = qs.rollAndGive(DRAKE_WING, 10 + Rnd.get(4), 100);
				else
					give = qs.rollAndGive(DRAKE_WING, 14 + Rnd.get(5), 100);
			}

			if(give)
				qs.playSound(SOUND_ITEMGET);
		}
	}
}
