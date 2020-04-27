package quests._351_BlackSwan;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

public class _351_BlackSwan extends Quest
{
	//NPC
	private static final int captain_gosta = 30916;
	private static final int iason_haine = 30969;
	private static final int head_blacksmith_roman = 30897;

	public _351_BlackSwan()
	{
		super(351, "_351_BlackSwan", "BlackSwan");
		addStartNpc(captain_gosta);
		addTalkId(captain_gosta, head_blacksmith_roman, iason_haine);

		addKillId(21639, 21640, 20785, 20784);
		addQuestItem(4296, 4297, 4298, 4310);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == captain_gosta)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() < 32)
					return "npchtm:captain_gosta_q0351_01.htm";

				return "npchtm:captain_gosta_q0351_02.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() >= 0)
					return "npchtm:captain_gosta_q0351_05.htm";
			}

		}
		else if(npc.getNpcId() == head_blacksmith_roman)
		{
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(4407) >= 1)
					return "npchtm:head_blacksmith_roman_q0351_01.htm";

				return "npchtm:head_blacksmith_roman_q0351_02.htm";
			}
		}
		else if(npc.getNpcId() == iason_haine)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:iason_haine_q0351_01.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == captain_gosta)
		{
			if(reply == 351)
			{
				st.giveItems(4269, 1);
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				showQuestPage("captain_gosta_q0351_04.htm", talker);
				st.playSound(SOUND_ACCEPT);
			}
			else if(reply == 1)
			{
				showQuestPage("captain_gosta_q0351_03.htm", talker);
			}
		}
		else if(npc.getNpcId() == head_blacksmith_roman)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(4407) > 0)
				{
					st.rateAndGive(57, 700);
					st.takeItems(4407, 1);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.getQuestItemsCount(4407) >= 3)
				{
					st.rateAndGive(1867, 20);
					st.takeItems(4407, 3);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.getQuestItemsCount(4407) >= 3)
				{
					st.rateAndGive(1872, 20);
					st.takeItems(4407, 3);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.getQuestItemsCount(4407) >= 2)
				{
					st.rateAndGive(1870, 10);
					st.takeItems(4407, 2);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.getQuestItemsCount(4407) >= 2)
				{
					st.rateAndGive(1871, 10);
					st.takeItems(4407, 2);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.getQuestItemsCount(4407) >= 9)
				{
					st.rateAndGive(1882, 10);
					st.takeItems(4407, 9);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.getQuestItemsCount(4407) >= 5)
				{
					st.rateAndGive(1879, 6);
					st.takeItems(4407, 5);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 8)
			{
				if(st.getQuestItemsCount(4407) >= 3)
				{
					st.rateAndGive(1881, 2);
					st.takeItems(4407, 3);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 9)
			{
				if(st.getQuestItemsCount(4407) >= 3)
				{
					st.rateAndGive(1874, 1);
					st.takeItems(4407, 3);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 10)
			{
				if(st.getQuestItemsCount(4407) >= 3)
				{
					st.rateAndGive(1875, 1);
					st.takeItems(4407, 3);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 11)
			{
				if(st.getQuestItemsCount(4407) >= 6)
				{
					st.rateAndGive(1894, 1);
					st.rateAndGive(57, 210);
					st.takeItems(4407, 6);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.getQuestItemsCount(4407) >= 7)
				{
					st.rateAndGive(1888, 1);
					st.rateAndGive(57, 280);
					st.takeItems(4407, 7);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 13)
			{
				if(st.getQuestItemsCount(4407) >= 9)
				{
					st.rateAndGive(1887, 1);
					st.rateAndGive(57, 630);
					st.takeItems(4407, 9);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 15)
			{
				if(st.getQuestItemsCount(4407) >= 5)
				{
					st.rateAndGive(5220, 1);
					st.takeItems(4407, 5);
					showPage("head_blacksmith_roman_q0351_03.htm", talker);
				}
				else
				{
					showPage("head_blacksmith_roman_q0351_04.htm", talker);
				}
			}
			else if(reply == 14)
			{
				showPage("head_blacksmith_roman_q0351_05.htm", talker);
			}
		}
		else if(npc.getNpcId() == iason_haine)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(4297) == 0)
				{
					showPage("iason_haine_q0351_02.htm", talker);
				}
				else
				{
					if(st.getQuestItemsCount(4297) >= 10)
					{
						st.rateAndGive(57, 3880 + 20 * st.getQuestItemsCount(4297));
					}
					else
					{
						st.rateAndGive(57, 20 * st.getQuestItemsCount(4297));
					}

					st.takeItems(4297, -1);
					showPage("iason_haine_q0351_03.htm", talker);

					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(711);
						if(qs != null && qs.isStarted() && qs.getMemoState() % 100 >= 5 && qs.getMemoState() % 100 < 15)
						{
							qs.setMemoState(qs.getMemoState() + 1);
						}
					}
				}
			}
			else if(reply == 2)
			{
				if(st.getQuestItemsCount(4298) == 0)
				{
					showPage("iason_haine_q0351_04.htm", talker);
				}
				else
				{
					st.giveItems(4407, st.getQuestItemsCount(4298));
					st.rateAndGive(57, 3880);
					st.takeItems(4298, -1);
					showPage("iason_haine_q0351_05.htm", talker);
					st.setCond(2);
					showQuestMark(talker);

					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(711);
						if(qs != null && qs.isStarted() && qs.getMemoState() % 100 >= 5 && qs.getMemoState() % 100 < 15)
						{
							qs.setMemoState(qs.getMemoState() + 1);
						}
					}
				}
			}
			else if(reply == 3)
			{
				showPage("iason_haine_q0351_06.htm", talker);
			}
			else if(reply == 4)
			{
				if(st.getQuestItemsCount(4298) == 0 && st.getQuestItemsCount(4297) == 0)
				{
					showPage("iason_haine_q0351_07.htm", talker);
				}
				else
				{
					showPage("iason_haine_q0351_08.htm", talker);
				}
			}
			else if(reply == 5)
			{
				showPage("iason_haine_q0351_09.htm", talker);
				if(st.getQuestItemsCount(4296) > 0)
				{
					st.takeItems(4296, 1);
				}
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int i0 = Rnd.get(20);
		if(i0 < 10)
		{
			st.rollAndGive(4297, 1, 100);
			st.rollAndGive(4298, 1, 5);
			st.playSound(SOUND_ITEMGET);
		}
		else if(i0 < 15)
		{
			st.rollAndGive(4297, 2, 100);
			st.rollAndGive(4298, 1, 5);
			st.playSound(SOUND_ITEMGET);
		}
		else if(Rnd.chance(3))
		{
			st.rollAndGive(4298, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
	}
}