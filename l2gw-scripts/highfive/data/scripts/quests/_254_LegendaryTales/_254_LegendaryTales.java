package quests._254_LegendaryTales;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 09.09.11 10:55
 */
public class _254_LegendaryTales extends Quest
{
	// NPCs
	private static final int gilmore = 30754;

	// Items
	private static final int great_dragon_bone = 17249;

	// MOBS
	public static final int raid_monster_1 = 25718;
	public static final int raid_monster_2 = 25719;
	public static final int raid_monster_3 = 25720;
	public static final int raid_monster_4 = 25721;
	public static final int raid_monster_5 = 25723;
	public static final int raid_monster_6 = 25722;
	public static final int raid_monster_7 = 25724;

	public _254_LegendaryTales()
	{
		super(254, "_254_LegendaryTales", "Legendary Tales");

		addStartNpc(gilmore);
		addTalkId(gilmore);
		addKillId(raid_monster_1, raid_monster_2, raid_monster_3, raid_monster_4, raid_monster_5, raid_monster_6, raid_monster_7);
		addQuestItem(great_dragon_bone);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("watcher_antaras_gilmore_q0254_02.htm", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == gilmore)
		{
			if(st.isCreated())
			{
				if(reply == 254 && player.getLevel() >= 80)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.set("ex", 1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("watcher_antaras_gilmore_q0254_07.htm", player);
				}
				else if(reply == 1)
				{
					if(player.getLevel() < 80)
					{
						showPage("watcher_antaras_gilmore_q0254_03.htm", player);
					}
					else if(player.getLevel() >= 80)
					{
						showQuestPage("watcher_antaras_gilmore_q0254_04.htm", player);
					}
				}
				else if(reply == 2)
				{
					if(player.getLevel() >= 80)
					{
						showQuestPage("watcher_antaras_gilmore_q0254_05.htm", player);
					}
				}
				else if(reply == 3)
				{
					if(player.getLevel() >= 80)
					{
						showQuestPage("watcher_antaras_gilmore_q0254_06.htm", player);
					}
				}

			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 4)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						showPage("watcher_antaras_gilmore_q0254_11.htm", player);
					}
				}
				else if(reply == 11)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13457, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 12)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13458, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				if(reply == 13)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13459, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 14)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13460, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 15)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13461, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 16)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13462, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 17)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13463, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 18)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13464, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 19)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13465, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 20)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13466, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 21)
				{
					if(st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_12.htm", player);
					}
					else if(st.getQuestItemsCount(great_dragon_bone) >= 7)
					{
						st.giveItems(13467, 1);
						st.takeItems(great_dragon_bone, st.getQuestItemsCount(great_dragon_bone));
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("watcher_antaras_gilmore_q0254_13.htm", player);
					}
				}
				else if(reply == 30)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_14.htm", player);
					}
				}
				else if(reply == 31)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_15.htm", player);
					}
				}
				else if(reply == 32)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(2 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_22.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 33)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(3 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_23.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 34)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(5 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_24.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 35)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(7 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_25.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 36)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(11 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_26.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 37)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(13 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_27.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == i0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 38)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						int i0 = st.getInt("ex");
						if(17 == i0)
						{
							showPage("watcher_antaras_gilmore_q0254_28.htm", player);
						}
						else
						{
							int i1 = Rnd.get(4);
							if(i1 == 0)
							{
								showPage("watcher_antaras_gilmore_q0254_16.htm", player);
							}
							else if(i1 == 1)
							{
								showPage("watcher_antaras_gilmore_q0254_17.htm", player);
							}
							else if(i1 == 2)
							{
								showPage("watcher_antaras_gilmore_q0254_18.htm", player);
							}
							else
							{
								showPage("watcher_antaras_gilmore_q0254_19.htm", player);
							}
						}
					}
				}
				else if(reply == 39)
				{
					showPage("watcher_antaras_gilmore_q0254_20.htm", player);
				}
				else if(reply == 40)
				{
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
					{
						showPage("watcher_antaras_gilmore_q0254_21.htm", player);
					}
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:watcher_antaras_gilmore_q0254_02.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == gilmore)
		{
			if(st.isCreated())
			{
				return "watcher_antaras_gilmore_q0254_01.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(great_dragon_bone) == 0)
						return "npchtm:watcher_antaras_gilmore_q0254_08.htm";
					if(st.getQuestItemsCount(great_dragon_bone) >= 1 && st.getQuestItemsCount(great_dragon_bone) < 7)
						return "npchtm:watcher_antaras_gilmore_q0254_09.htm";

					return "npchtm:watcher_antaras_gilmore_q0254_10.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);

		if(!party.isEmpty())
		{
			int i1 = 0;
			if(npcId == raid_monster_1)
				i1 = 2;
			else if(npcId == raid_monster_2)
				i1 = 3;
			else if(npcId == raid_monster_3)
				i1 = 5;
			else if(npcId == raid_monster_4)
				i1 = 7;
			else if(npcId == raid_monster_5)
				i1 = 13;
			else if(npcId == raid_monster_6)
				i1 = 11;
			else if(npcId == raid_monster_7)
				i1 = 17;

			for(QuestState qs : party)
			{
				int i0 = qs.getInt("ex");
				if(i0 == 0)
				{
					i0 = 1;
					qs.set("ex", i0);
				}


				if(i1 > 0 && i0 != i1 && i0 % i1 != 0)
				{
					if(i0 * i1 == 510510)
					{
						qs.giveItems(great_dragon_bone, 1);
						qs.set("ex", i0 * i1);
						qs.setCond(2);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
					{
						qs.giveItems(great_dragon_bone, 1);
						qs.set("ex", i0 * i1);
						qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}
