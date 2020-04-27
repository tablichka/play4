package quests._10289_FadetoBlack;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 09.10.11 19:41
 */
public class _10289_FadetoBlack extends Quest
{
	// NPC
	public static final int new_falsepriest_gremory = 32757;

	// Mobs
	private static final int n_divine_anais = 25701;

	// Items
	private static final int q_voucher_of_brilliance = 15527;
	private static final int q_voucher_of_darkness = 15528;

	public _10289_FadetoBlack()
	{
		super(10289, "_10289_FadetoBlack", "Fade to Black");
		addStartNpc(new_falsepriest_gremory);
		addTalkId(new_falsepriest_gremory);
		addKillId(n_divine_anais);
		addQuestItem(q_voucher_of_brilliance, q_voucher_of_darkness);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == new_falsepriest_gremory)
		{
			if(st.isCreated() && talker.isQuestComplete(10288) && talker.getLevel() >= 82)
				return "new_falsepriest_gremory_q10289_01.htm";
			if(st.isCompleted())
				return "npchtm:new_falsepriest_gremory_q10289_02a.htm";
			if(st.isCreated() && (!talker.isQuestComplete(10288) || talker.getLevel() < 82))
				return "new_falsepriest_gremory_q10289_03.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:new_falsepriest_gremory_q10289_04a.htm";
				if(st.getMemoState() == 2)
					return "npchtm:new_falsepriest_gremory_q10289_05.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == new_falsepriest_gremory)
		{
			if(reply == 10289)
			{
				if(st.isCreated() && talker.isQuestComplete(10288) && talker.getLevel() >= 82)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("new_falsepriest_gremory_q10289_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10288))
				{
					showQuestPage("new_falsepriest_gremory_q10289_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(q_voucher_of_darkness) < 1 && st.getQuestItemsCount(q_voucher_of_brilliance) < 1)
				{
					showPage("new_falsepriest_gremory_q10289_06.htm", talker);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(q_voucher_of_darkness) >= 1 && st.getQuestItemsCount(q_voucher_of_brilliance) < 1)
				{
					showPage("new_falsepriest_gremory_q10289_07.htm", talker);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(q_voucher_of_brilliance) >= 1)
				{
					showPage("new_falsepriest_gremory_q10289_08.htm", talker);
				}
			}
			else if(reply >= 11 && reply <= 30)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					if(st.getQuestItemsCount(q_voucher_of_darkness) >= 1)
					{
						long i0 = st.getQuestItemsCount(q_voucher_of_darkness);
						st.addExpAndSp(55983 * i0, 136500 * i0);
						st.takeItems(q_voucher_of_darkness, -1);
					}
					if(st.getQuestItemsCount(q_voucher_of_brilliance) >= 1)
					{
						st.takeItems(q_voucher_of_brilliance, st.getQuestItemsCount(q_voucher_of_brilliance));
						if(reply == 11)
						{
							st.giveItems(15775, 1);
							st.rollAndGive(57, 420920, 100);
						}
						else if(reply == 12)
						{
							st.giveItems(15776, 1);
							st.rollAndGive(57, 420920, 100);
						}
						else if(reply == 13)
						{
							st.giveItems(15777, 1);
							st.rollAndGive(57, 420920, 100);
						}
						else if(reply == 14)
						{
							st.giveItems(15778, 1);
						}
						else if(reply == 15)
						{
							st.giveItems(15779, 1);
							st.rollAndGive(57, 168360, 100);
						}
						else if(reply == 16)
						{
							st.giveItems(15780, 1);
							st.rollAndGive(57, 168360, 100);
						}
						else if(reply == 17)
						{
							st.giveItems(15781, 1);
							st.rollAndGive(57, 252540, 100);
						}
						else if(reply == 18)
						{
							st.giveItems(15782, 1);
							st.rollAndGive(57, 357780, 100);
						}
						else if(reply == 19)
						{
							st.giveItems(15783, 1);
							st.rollAndGive(57, 357780, 100);
						}
						else if(reply == 20)
						{
							st.giveItems(15784, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 21)
						{
							st.giveItems(15785, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 22)
						{
							st.giveItems(15786, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 23)
						{
							st.giveItems(15787, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 24)
						{
							st.giveItems(15788, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 25)
						{
							st.giveItems(15789, 1);
							st.rollAndGive(57, 505100, 100);
						}
						else if(reply == 26)
						{
							st.giveItems(15790, 1);
							st.rollAndGive(57, 496680, 100);
						}
						else if(reply == 27)
						{
							st.giveItems(15791, 1);
							st.rollAndGive(57, 496680, 100);
						}
						else if(reply == 28)
						{
							st.giveItems(15812, 1);
							st.rollAndGive(57, 563860, 100);
						}
						else if(reply == 29)
						{
							st.giveItems(15813, 1);
							st.rollAndGive(57, 509040, 100);
						}
						else if(reply == 30)
						{
							st.giveItems(15814, 1);
							st.rollAndGive(57, 454240, 100);
						}

						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("new_falsepriest_gremory_q10289_09.htm", talker);
					}
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);

		if(!party.isEmpty())
		{
			QuestState st = party.remove(Rnd.get(party.size()));
			st.giveItems(q_voucher_of_brilliance, 1);
			st.setMemoState(2);
			st.setCond(3);
			showQuestMark(st.getPlayer());
			st.playSound(SOUND_MIDDLE);
			if(party.size() > 0)
			{
				for(QuestState qs : party)
				{
					qs.giveItems(q_voucher_of_darkness, 1);
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.playSound(SOUND_MIDDLE);
				}
			}
		}
	}
}