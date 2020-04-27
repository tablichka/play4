package quests._241_PossessorOfaPreciousSoul1;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

public class _241_PossessorOfaPreciousSoul1 extends Quest
{
	// NPC
	private static final int talien = 31739;
	private static final int virgil = 31742;
	private static final int caradine = 31740;
	private static final int gabrielle = 30753;
	private static final int highseer_rahorakti = 31336;
	private static final int kasandra = 31743;
	private static final int master_stedmiel = 30692;
	private static final int muzyk = 31042;
	private static final int ogmar = 31744;
	private static final int watcher_antaras_gilmore = 30754;

	// Mobs
	private static final int baraham = 27113;
	private static final int h_malruk_succubus_turen = 20284;
	private static final int malruk_succubus = 20244;
	private static final int malruk_succubus_hold = 20283;
	private static final int malruk_succubus_turen = 20245;
	private static final int taik_orc_supply_leader = 20669;

	public _241_PossessorOfaPreciousSoul1()
	{
		super(241, "_241_PossessorOfaPreciousSoul1", "Possessor Of a Precious Soul 1");

		addStartNpc(talien);
		addTalkId(talien, virgil, gabrielle, highseer_rahorakti, kasandra, muzyk, ogmar, watcher_antaras_gilmore, master_stedmiel, caradine);
		addKillId(baraham, h_malruk_succubus_turen, malruk_succubus, malruk_succubus_hold, malruk_succubus_turen, taik_orc_supply_leader);
		addQuestItem(7587, 7589, 7588, 7597, 7598, 7599);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == talien)
		{
			if(talker.getLevel() >= 50 && st.isCreated())
			{
				if(talker.isSubClassActive())
					return "talien_q0241_0101.htm";

				return "talien_q0241_0102.htm";
			}
			if(talker.getLevel() < 50 && st.isCreated() && talker.isSubClassActive())
				return "talien_q0241_0103.htm";
			if(st.isCompleted() && talker.isSubClassActive())
				return "completed";
			if(st.isStarted())
			{
				if(st.getMemoState() == 11 && talker.isSubClassActive())
					return "npchtm:talien_q0241_0105.htm";
				if(st.getMemoState() == 32 && talker.isSubClassActive())
				{
					if(st.getQuestItemsCount(7587) >= 1)
					{
						talker.setSessionVar("cookie", "3");
						return "npchtm:talien_q0241_0301.htm";
					}
					return "npchtm:talien_q0241_0302.htm";
				}
				if(st.getMemoState() == 32 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
				if(st.getMemoState() == 41 && talker.isSubClassActive())
					return "npchtm:talien_q0241_0403.htm";
				if(st.getMemoState() == 41 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
				if(st.getMemoState() == 61 && st.getQuestItemsCount(7589) >= 1 && talker.isSubClassActive())
				{
					talker.setSessionVar("cookie", "6");
					return "npchtm:talien_q0241_0601.htm";
				}
				if(st.getMemoState() == 61 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
				if(st.getMemoState() == 71 && talker.isSubClassActive())
					return "npchtm:talien_q0241_0703.htm";
				if(st.getMemoState() == 71 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
				if(st.getMemoState() == 81 && st.getQuestItemsCount(7588) >= 1 && talker.isSubClassActive())
				{
					talker.setSessionVar("cookie", "8");
					return "npchtm:talien_q0241_0801.htm";
				}
				if(st.getMemoState() == 81 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
				if(st.getMemoState() == 91 && talker.isSubClassActive())
					return "npchtm:talien_q0241_0903.htm";
				if(st.getMemoState() == 91 && !talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";
			}
		}
		else if(npc.getNpcId() == virgil)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 91)
				{
					talker.setSessionVar("cookie", "9");
					return "npchtm:virgil_q0241_0901.htm";
				}
				if(st.getMemoState() == 101)
					return "npchtm:virgil_q0241_1002.htm";
				if(st.getMemoState() == 141)
				{
					talker.setSessionVar("cookie", "14");
					return "npchtm:virgil_q0241_1401.htm";
				}
				if(st.getMemoState() == 151)
					return "npchtm:virgil_q0241_1502.htm";
			}
		}
		else if(npc.getNpcId() == caradine)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 181 || st.getMemoState() == 171 || st.getMemoState() == 161 || st.getMemoState() == 151)
				{
					talker.setSessionVar("cookie", "18");
					return "npchtm:caradine_q0241_1501.htm";
				}

			}
		}
		else if(npc.getNpcId() == gabrielle)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 11)
				{
					talker.setSessionVar("cookie", "1");
					return "npchtm:gabrielle_q0241_0101.htm";
				}
				if(st.getMemoState() == 21)
					return "npchtm:gabrielle_q0241_0202.htm";
			}
		}
		else if(npc.getNpcId() == highseer_rahorakti)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 111)
				{
					talker.setSessionVar("cookie", "11");
					return "npchtm:highseer_rahorakti_q0241_1101.htm";
				}
				if(st.getMemoState() <= 122 && st.getMemoState() >= 121)
				{
					if(st.getMemoState() == 122 && st.getQuestItemsCount(7598) >= 5)
					{
						talker.setSessionVar("cookie", "12");
						return "npchtm:highseer_rahorakti_q0241_1202.htm";
					}

					return "npchtm:highseer_rahorakti_q0241_1203.htm";
				}
				if(st.getMemoState() == 131)
					return "npchtm:highseer_rahorakti_q0241_1303.htm";
			}
		}
		else if(npc.getNpcId() == kasandra)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getQuestItemsCount(7599) >= 1 && st.getMemoState() == 131)
				{
					talker.setSessionVar("cookie", "13");
					return "npchtm:kasandra_q0241_1301.htm";
				}
				if(st.getMemoState() == 141)
					return "npchtm:kasandra_q0241_1403.htm";
			}
		}
		else if(npc.getNpcId() == master_stedmiel)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 71)
				{
					talker.setSessionVar("cookie", "7");
					return "npchtm:master_stedmiel_q0241_0701.htm";
				}
				if(st.getMemoState() == 81)
					return "npchtm:master_stedmiel_q0241_0802.htm";

			}
		}
		else if(npc.getNpcId() == muzyk)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 41)
				{
					talker.setSessionVar("cookie", "4");
					return "npchtm:muzyk_q0241_0401.htm";
				}
				if(st.getMemoState() <= 52 && st.getMemoState() >= 51)
				{
					if(st.getMemoState() == 52 && st.getQuestItemsCount(7597) >= 10)
					{
						talker.setSessionVar("cookie", "5");
						return "npchtm:muzyk_q0241_0502.htm";
					}

					return "npchtm:muzyk_q0241_0503.htm";
				}
				if(st.getMemoState() == 61)
					return "npchtm:muzyk_q0241_0603.htm";
			}
		}
		else if(npc.getNpcId() == ogmar)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 101)
				{
					talker.setSessionVar("cookie", "10");
					return "npchtm:ogmar_q0241_1001.htm";
				}
				if(st.getMemoState() == 111)
					return "npchtm:ogmar_q0241_1102.htm";
			}
		}
		else if(npc.getNpcId() == watcher_antaras_gilmore)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 21)
				{
					talker.setSessionVar("cookie", "2");
					return "npchtm:watcher_antaras_gilmore_q0241_0201.htm";
				}
				if(st.getMemoState() == 31)
					return "npchtm:watcher_antaras_gilmore_q0241_0302.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == talien)
		{
			if(reply == 241)
			{
				if(talker.getLevel() >= 50 && st.isCreated() && talker.isSubClassActive())
				{
					st.setMemoState(11);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("talien_q0241_0104.htm", talker);
				}
			}
			else if(reply == 1)
			{
				String cookie = talker.getSessionVar("cookie");
				if(cookie != null)
				{
					if("3".equals(cookie) && st.isStarted() && talker.isSubClassActive())
					{
						if(st.getQuestItemsCount(7587) >= 1)
						{
							st.takeItems(7587, 1);
							showPage("talien_q0241_0401.htm", talker);
							st.setMemoState(41);
							st.setCond(5);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("talien_q0241_0402.htm", talker);
						}
					}
					else if("6".equals(cookie) && st.isStarted() && talker.isSubClassActive())
					{
						if(st.getQuestItemsCount(7589) >= 1)
						{
							st.takeItems(7589, 1);
							showPage("talien_q0241_0701.htm", talker);
							st.setMemoState(71);
							st.setCond(9);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("talien_q0241_0702.htm", talker);
						}
					}
					else if("8".equals(cookie) && st.isStarted() && talker.isSubClassActive())
					{
						if(st.getQuestItemsCount(7588) >= 1)
						{
							st.takeItems(7588, 1);
							showPage("talien_q0241_0901.htm", talker);
							st.setMemoState(91);
							st.setCond(11);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("talien_q0241_0902.htm", talker);
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == virgil)
		{
			if(reply == 1)
			{
				String cookie = talker.getSessionVar("cookie");
				if(cookie != null)
				{
					if("9".equals(cookie) && st.isStarted())
					{
						showPage("virgil_q0241_1001.htm", talker);
						st.setMemoState(101);
						st.setCond(12);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else if("14".equals(cookie) && st.isStarted())
					{
						showPage("virgil_q0241_1501.htm", talker);
						st.setMemoState(151);
						st.setCond(18);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npc.getNpcId() == caradine)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "18".equals(cookie) && reply == 3 && st.isStarted())
			{
				st.giveItems(7677, 1);
				st.addExpAndSp(263043, 0);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("caradine_q0241_1901.htm", talker);
				st.showSocial(3);
			}
		}
		else if(npc.getNpcId() == gabrielle)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "1".equals(cookie) && reply == 1 && st.isStarted())
			{
				showPage("gabrielle_q0241_0201.htm", talker);
				st.setMemoState(21);
				st.setCond(2);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == highseer_rahorakti)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null)
			{
				if("11".equals(cookie) && reply == 1 && talker.isSubClassActive())
				{
					showPage("highseer_rahorakti_q0241_1201.htm", talker);
					st.setMemoState(121);
					st.setCond(14);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else if("12".equals(cookie) && reply == 1 && talker.isSubClassActive())
				{
					if(st.getQuestItemsCount(7598) >= 5)
					{
						st.takeItems(7598, 5);
						st.giveItems(7599, 1);
						showPage("highseer_rahorakti_q0241_1301.htm", talker);
						st.setMemoState(131);
						st.setCond(16);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("highseer_rahorakti_q0241_1302.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == kasandra)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "13".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
			{
				if(st.getQuestItemsCount(7599) >= 1)
				{
					st.takeItems(7599, 1);
					showPage("kasandra_q0241_1401.htm", talker);
					st.setMemoState(141);
					st.setCond(17);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					showPage("kasandra_q0241_1402.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == master_stedmiel)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "7".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
			{
				st.giveItems(7588, 1);
				showPage("master_stedmiel_q0241_0801.htm", talker);
				st.setMemoState(81);
				st.setCond(10);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == muzyk)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null)
			{
				if("4".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
				{
					showPage("muzyk_q0241_0501.htm", talker);
					st.setMemoState(51);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else if("5".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
				{
					if(st.getQuestItemsCount(7597) >= 10)
					{
						st.takeItems(7597, 10);
						st.giveItems(7589, 1);
						showPage("muzyk_q0241_0601.htm", talker);
						st.setMemoState(61);
						st.setCond(8);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("muzyk_q0241_0602.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ogmar)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "10".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
			{
				showPage("ogmar_q0241_1101.htm", talker);
				st.setMemoState(111);
				st.setCond(13);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == watcher_antaras_gilmore)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null && "2".equals(cookie) && reply == 1 && st.isStarted() && talker.isSubClassActive())
			{
				showPage("watcher_antaras_gilmore_q0241_0301.htm", talker);
				st.setMemoState(31);
				st.setCond(3);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == baraham)
		{
			GArray<QuestState> p = getPartyMembersWithMemoState(killer, 31);
			if(!p.isEmpty())
			{
				GArray<QuestState> party = new GArray<>(p.size());
				for(QuestState st : p)
					if(st.getPlayer().isSubClassActive())
						party.add(st);

				if(!party.isEmpty())
				{
					QuestState st = party.get(Rnd.get(party.size()));
					st.giveItems(7587, 1);
					st.playSound(SOUND_MIDDLE);
					st.setCond(4);
					showQuestMark(st.getPlayer());
					st.setMemoState(32);
				}
			}
		}
		else if(npc.getNpcId() == h_malruk_succubus_turen || npc.getNpcId() == malruk_succubus || npc.getNpcId() == malruk_succubus_hold || npc.getNpcId() == malruk_succubus_turen)
		{
			GArray<QuestState> p = getPartyMembersWithMemoState(killer, 51);
			if(!p.isEmpty())
			{
				GArray<QuestState> party = new GArray<>(p.size());
				for(QuestState st : p)
					if(st.getPlayer().isSubClassActive())
						party.add(st);

				if(!party.isEmpty())
				{
					QuestState st = party.get(Rnd.get(party.size()));
					if(st.rollAndGiveLimited(7597, 1, 50, 10))
					{
						if(st.getQuestItemsCount(7597) >= 10)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(7);
							showQuestMark(st.getPlayer());
							st.setMemoState(52);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
		else if(npc.getNpcId() == taik_orc_supply_leader)
		{
			GArray<QuestState> p = getPartyMembersWithMemoState(killer, 121);
			if(!p.isEmpty())
			{
				GArray<QuestState> party = new GArray<>(p.size());
				for(QuestState st : p)
					if(st.getPlayer().isSubClassActive())
						party.add(st);

				if(!party.isEmpty())
				{
					QuestState st = party.get(Rnd.get(party.size()));
					if(st.rollAndGiveLimited(7598, 1, 30, 5))
					{
						if(st.getQuestItemsCount(7598) >= 5)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(15);
							showQuestMark(st.getPlayer());
							st.setMemoState(122);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}
