package quests._246_PossessorOfaPreciousSoul3;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _246_PossessorOfaPreciousSoul3 extends Quest
{
	// NPC
	private static final int caradine = 31740;
	private static final int magister_ladd = 30721;
	private static final int ossian = 31741;

	// Mobs
	private static final int blinding_fire_barakiel = 25325;
	private static final int brilliant_mark = 21535;
	private static final int brilliant_crown = 21536;
	private static final int brilliant_fang = 21537;
	private static final int brilliant_anguish = 21539;
	private static final int brilliant_prophet = 21541;
	private static final int brilliant_justice = 21544;

	public _246_PossessorOfaPreciousSoul3()
	{
		super(246, "_246_PossessorOfaPreciousSoul3", "Possessor Of a Precious Soul 3");

		addStartNpc(caradine);
		addTalkId(caradine, magister_ladd, ossian);

		addKillId(blinding_fire_barakiel);
		addKillId(brilliant_mark, brilliant_crown, brilliant_fang, brilliant_anguish, brilliant_prophet, brilliant_justice);
		addQuestItem(7678, 7591, 7592, 7593, 21725);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == caradine)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 65)
				{
					if(talker.isQuestComplete(242) && talker.isSubClassActive())
						return "caradine_q0246_0101.htm";

					return "caradine_q0246_0102.htm";
				}
				if(!talker.isSubClassActive())
					return "caradine_q0246_0103.htm";
			}
			if(st.isCompleted() && talker.isSubClassActive())
				return "completed";
			if(st.isStarted() && talker.isSubClassActive())
			{
				if(st.getMemoState() == 11)
					return "npchtm:caradine_q0246_0105.htm";
			}
		}
		else if(npc.getNpcId() == magister_ladd)
		{
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(7594) >= 1 && st.getMemoState() == 41 && talker.isSubClassActive())
				{
					talker.setSessionVar("cookie", "4");
					return "npchtm:magister_ladd_q0246_0401.htm";
				}
				if(!talker.isSubClassActive() && st.getMemoState() == 41)
					return "npchtm:quest_not_subclass001.htm";
			}
		}
		else if(npc.getNpcId() == ossian)
		{
			if(st.isStarted())
			{
				if(!talker.isSubClassActive())
					return "npchtm:quest_not_subclass001.htm";

				if(st.getMemoState() == 11)
				{
					talker.setSessionVar("cookie", "1");
					return "npchtm:ossian_q0246_0101.htm";
				}
				if(st.getMemoState() <= 22 && st.getMemoState() >= 21)
				{
					if(st.getMemoState() == 22 && st.getQuestItemsCount(7591) >= 1 && st.getQuestItemsCount(7592) >= 1)
					{
						talker.setSessionVar("cookie", "2");
						return "npchtm:ossian_q0246_0202.htm";
					}

					return "npchtm:ossian_q0246_0203.htm";
				}
				if(st.getMemoState() <= 32 && st.getMemoState() >= 31)
				{
					if(st.getMemoState() == 32 && st.getQuestItemsCount(7593) >= 1 || st.getQuestItemsCount(21725) >= 100)
					{
						talker.setSessionVar("cookie", "3");
						return "npchtm:ossian_q0246_0303.htm";
					}

					return "npchtm:ossian_q0246_0304.htm";
				}
				if(st.getMemoState() == 41)
					return "npchtm:ossian_q0246_0403.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == caradine)
		{
			if(reply == 246)
			{
				if(talker.getLevel() >= 65 && st.isCreated() && talker.isSubClassActive() && talker.isQuestComplete(242))
				{
					st.setMemoState(11);
					st.takeItems(7678, -1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("caradine_q0246_0104.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == magister_ladd)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null)
			{
				if(reply == 3 && st.isStarted())
				{
					if(st.getQuestItemsCount(7594) >= 1)
					{
						st.takeItems(7594, -1);
						st.giveItems(7679, 1);
						st.addExpAndSp(719843, 0);
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("magister_ladd_q0246_0501.htm", talker);
						st.showSocial(4);
					}
					else
					{
						showPage("magister_ladd_q0246_0502.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ossian)
		{
			String cookie = talker.getSessionVar("cookie");
			if(cookie != null)
			{
				if("1".equals(cookie))
				{
					if(reply == 1 && st.isStarted() && talker.isSubClassActive())
					{
						showPage("ossian_q0246_0201.htm", talker);
						st.setMemoState(21);
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if("2".equals(cookie))
				{
					if(reply == 1 && st.isStarted())
					{
						if(st.getQuestItemsCount(7591) >= 1 && st.getQuestItemsCount(7592) >= 1)
						{
							st.takeItems(7591, 1);
							st.takeItems(7592, 1);
							showPage("ossian_q0246_0301.htm", talker);
							st.setMemoState(31);
							st.setCond(4);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("ossian_q0246_0302.htm", talker);
						}
					}
				}
				else if("3".equals(cookie))
				{
					if(reply == 1 && st.isStarted() && talker.isSubClassActive())
					{
						if(st.getQuestItemsCount(7593) >= 1)
						{
							st.takeItems(7593, 1);
							st.takeItems(21725, -1);
							st.giveItems(7594, 1);
							showPage("ossian_q0246_0401.htm", talker);
							st.setMemoState(41);
							st.setCond(6);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else if(st.getQuestItemsCount(21725) >= 100)
						{
							st.takeItems(21725, -1);
							st.giveItems(7594, 1);
							showPage("ossian_q0246_0401.htm", talker);
							st.setMemoState(41);
							st.setCond(6);
							showQuestMark(talker);
							st.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("ossian_q0246_0402.htm", talker);
						}
					}
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == blinding_fire_barakiel)
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 31);
			if(!party.isEmpty())
				for(QuestState st : party)
					if(st.getPlayer().isSubClassActive())
					{
						st.giveItems(7593, 1);
						st.playSound(SOUND_MIDDLE);
						st.setCond(5);
						showQuestMark(st.getPlayer());
						st.setMemoState(32);
					}
		}
		else if(npc.getNpcId() >= brilliant_mark && npc.getNpcId() <= brilliant_anguish)
		{
			GArray<QuestState> party = new GArray<>(9);
			for(QuestState st : getPartyMembersWithMemoState(killer, 31))
				if(st.getPlayer().isSubClassActive())
					party.add(st);

			if(!party.isEmpty())
			{
				QuestState st = party.get(Rnd.get(party.size()));
				if(st.rollAndGiveLimited(21725, 1, 20, 100))
				{
					if(st.getQuestItemsCount(21725) >= 100)
					{
						st.playSound(SOUND_MIDDLE);
						st.setCond(5);
						showQuestMark(st.getPlayer());
						st.setMemoState(32);
					}
					else
					{
						st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
		else if(npc.getNpcId() == brilliant_prophet || npc.getNpcId() == brilliant_justice)
		{
			GArray<QuestState> party = new GArray<>(9);
			for(QuestState st : getPartyMembersWithMemoState(killer, 21))
				if(st.getPlayer().isSubClassActive())
					party.add(st);

			if(!party.isEmpty())
			{
				QuestState st = party.get(Rnd.get(party.size()));
				if(st.rollAndGiveLimited(npc.getNpcId() == brilliant_prophet ? 7591 : 7592, 1, 40, 1))
				{
					if(st.getQuestItemsCount(npc.getNpcId() == brilliant_prophet ? 7592 : 7591) >=1)
					{
						st.playSound(SOUND_MIDDLE);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.setMemoState(22);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}