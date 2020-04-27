package quests._10283_RequestofIceMerchant;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.09.11 23:44
 */
public class _10283_RequestofIceMerchant extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int keier = 32022;
	private static final int jinia_npc = 32760;

	public _10283_RequestofIceMerchant()
	{
		super(10283, "_10283_RequestofIceMerchant", "Request of Ice Merchant");
		addStartNpc(repre);
		addTalkId(repre, keier, jinia_npc);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == repre)
		{
			if(st.isCompleted())
				return "npchtm:repre_q10283_02.htm";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 82 && talker.isQuestComplete(115))
					return "repre_q10283_01.htm";

				return "repre_q10283_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:repre_q10283_06.htm";
				if(st.getMemoState() == 2)
					return "npchtm:repre_q10283_10.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == repre)
		{
			if(reply == 10283)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(115))
				{
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					showQuestPage("repre_q10283_05.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(115))
				{
					showQuestPage("repre_q10283_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("repre_q10283_07.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("repre_q10283_08.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(talker);
					showPage("repre_q10283_09.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == keier)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("keier_q10283_01.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						npc.i_quest1 = talker.getObjectId();
						st.setCond(3);
						showQuestMark(talker);
						npc.createOnePrivate(32760, "JiniaNpc", 0, 0, 104476, -107535, -3688, 44954, talker.getStoredId(), talker.getObjectId(), npc.getStoredId());
					}
					else
					{
						if(npc.i_quest1 == talker.getObjectId())
						{
							showPage("keier_q10283_03.htm", talker);
						}
						else
						{
							showPage("keier_q10283_02.htm", talker);
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == jinia_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("jinia_npc_q10283_01.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("jinia_npc_q10283_02.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.rollAndGive(57, 190000, 100);
					st.addExpAndSp(627000, 50300);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("jinia_npc_q10283_03.htm", talker);
					npc.getAI().addTimer(528351, 2000);
					((DefaultAI) npc.getAI()).addFleeDesire(talker, 1000000);
				}
			}
		}
	}
}
