package quests._10288_SecretMission;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 10.09.11 9:36
 */
public class _10288_SecretMission extends Quest
{
	// NPCs
	private static final int falsepriest_dominic = 31350;
	private static final int falsepriest_aquilani = 32780;
	private static final int new_falsepriest_gremory = 32757;

	// Items
	private static final int q_letter_of_falsepriest_dominic = 15529;

	public _10288_SecretMission()
	{
		super(10288, "_10288_SecretMission", "Secret Mission");

		addStartNpc(falsepriest_dominic);
		addTalkId(falsepriest_dominic, falsepriest_aquilani, new_falsepriest_gremory);
		addQuestItem(q_letter_of_falsepriest_dominic);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("falsepriest_dominic_q10288_02.htm", st.getPlayer());
			return;
		}

		L2Player talker = st.getPlayer();
		int npcId = talker.getLastNpc().getNpcId();

		if(npcId == falsepriest_dominic)
		{
			if(st.isCreated())
			{
				if(reply == 10288 && talker.getLevel() >= 82)
				{
					st.giveItems(q_letter_of_falsepriest_dominic, 1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("falsepriest_dominic_q10288_06.htm", talker);
					st.setCond(1);
					showQuestMark(st.getPlayer());
				}
				else if(reply == 1)
				{
					if(talker.getLevel() < 82)
					{
						showPage("falsepriest_dominic_q10288_02a.htm", talker);
					}
					else
					{
						showPage("falsepriest_dominic_q10288_03.htm", talker);
					}
				}
				else if(reply == 2)
				{
					if(talker.getLevel() >= 82)
					{
						showPage("falsepriest_dominic_q10288_04.htm", talker);
					}
				}
				else if(reply == 3)
				{
					if(talker.getLevel() >= 82)
					{
						showQuestPage("falsepriest_dominic_q10288_05.htm", talker);
					}
				}
			}
		}
		else if(npcId == falsepriest_aquilani && st.isStarted())
		{
			if(reply == 1)
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_letter_of_falsepriest_dominic) >= 1)
				{
					st.setMemoState(2);
					showPage("falsepriest_aquilani_q10288_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.getMemoState() == 2)
				{
					showPage("falsepriest_aquilani_q10288_03.htm", talker);
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == new_falsepriest_gremory && st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(q_letter_of_falsepriest_dominic) >= 1)
		{
			if(reply == 1)
			{
				showPage("new_falsepriest_gremory_q10288_02.htm", talker);
			}
			else if(reply == 2)
			{
				st.giveItems(57, 106583);
				st.addExpAndSp(417788, 46320);
				st.takeItems(q_letter_of_falsepriest_dominic, st.getQuestItemsCount(q_letter_of_falsepriest_dominic));
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("new_falsepriest_gremory_q10288_03.htm", talker);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:falsepriest_dominic_q10288_02.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == falsepriest_dominic)
		{
			if(st.isCreated())
				return "falsepriest_dominic_q10288_01.htm";
			if(st.isStarted() && cond == 1)
				return "npchtm:falsepriest_dominic_q10288_07.htm";
		}
		else if(npcId == falsepriest_aquilani && st.isStarted())
		{
			if(cond == 1 && st.getQuestItemsCount(q_letter_of_falsepriest_dominic) >= 1)
			{
				return "npchtm:falsepriest_aquilani_q10288_01.htm";
			}
			if(cond == 2)
			{
				return "npchtm:falsepriest_aquilani_q10288_04.htm";
			}
		}
		else if(npcId == new_falsepriest_gremory && st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(q_letter_of_falsepriest_dominic) >= 1)
			return "npchtm:new_falsepriest_gremory_q10288_01.htm";

		return "noquest";
	}
}
