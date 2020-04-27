package quests._026_TiredofWaiting;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 25.09.11 21:07
 */
public class _026_TiredofWaiting extends Quest
{
	// NPC
	private static final int isael_silvershadow = 30655;
	private static final int kitzka = 31045;

	// Items
	private static final int q_delivery_box = 17281;

	public _026_TiredofWaiting()
	{
		super(26, "_026_TiredofWaiting", "Tired of Waiting");
		addStartNpc(isael_silvershadow);
		addTalkId(isael_silvershadow, kitzka);
		addQuestItem(q_delivery_box);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == isael_silvershadow)
		{
			if(st.isCompleted())
				return "npchtm:isael_silvershadow_q0026_02.htm";

			if(st.isCreated())
				return "isael_silvershadow_q0026_01.htm";

			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:isael_silvershadow_q0026_09.htm";
		}
		else if(npc.getNpcId() == kitzka)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:kitzka_q0026_01.htm";
			if(st.isStarted() && st.getMemoState() == 2)
				return "npchtm:kitzka_q0026_06.htm";
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == isael_silvershadow)
		{
			if(reply == 26)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					st.giveItems(q_delivery_box, 1);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("isael_silvershadow_q0026_08.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() < 80)
				{
					showQuestPage("isael_silvershadow_q0026_03.htm", talker);
				}
				else if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("isael_silvershadow_q0026_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("isael_silvershadow_q0026_05.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("isael_silvershadow_q0026_06.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("isael_silvershadow_q0026_07.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == kitzka)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("kitzka_q0026_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("kitzka_q0026_03.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.takeItems(q_delivery_box, -1);
					st.setMemoState(2);
					showPage("kitzka_q0026_04.htm", talker);
				}
			}
			else if(reply == 10)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kitzka_q0026_05.htm", talker);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kitzka_q0026_07.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kitzka_q0026_08.htm", talker);
				}
			}
			else if(reply == 13)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kitzka_q0026_09.htm", talker);
				}
			}
			else if(reply > 20)
			{
				if(reply == 21 && st.isStarted() && st.getMemoState() == 2)
				{
					st.rollAndGive(17248, 1, 100);
					showPage("kitzka_q0026_10.htm", talker);
				}
				else if(reply == 22 && st.isStarted() && st.getMemoState() == 2)
				{
					st.rollAndGive(17266, 1, 100);
					showPage("kitzka_q0026_11.htm", talker);
				}
				else if(reply == 23 && st.isStarted() && st.getMemoState() == 2)
				{
					st.rollAndGive(17267, 1, 100);
					showPage("kitzka_q0026_12.htm", talker);
				}

				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		return "npchtm:" + event;
	}
}
