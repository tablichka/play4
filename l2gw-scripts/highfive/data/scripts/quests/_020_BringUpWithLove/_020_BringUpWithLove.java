package quests._020_BringUpWithLove;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _020_BringUpWithLove extends Quest
{
	private static final int beast_herder_tunatun = 31537;

	public _020_BringUpWithLove()
	{
		super(20, "_020_BringUpWithLove", "Bring Up With Love");

		addStartNpc(beast_herder_tunatun);
		addTalkId(beast_herder_tunatun);
		addQuestItem(7185, 15533);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == beast_herder_tunatun)
		{
			if(st.isCreated() && talker.getLevel() >= 82)
				return "beast_herder_tunatun_q0020_01.htm";

			if(st.isCreated() && talker.getLevel() < 82)
				return "beast_herder_tunatun_q0020_02.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(15533) == 0 && st.getQuestItemsCount(7185) == 0)
					return "npchtm:beast_herder_tunatun_q0020_15.htm";
				if(st.getMemoState() == 1 && (st.getQuestItemsCount(15533) >= 1 || st.getQuestItemsCount(7185) >= 1))
					return "npchtm:beast_herder_tunatun_q0020_16.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == beast_herder_tunatun)
		{
			if(reply == 20)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.set("ex_1", 1);
					showQuestPage("beast_herder_tunatun_q0020_14.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_03.htm", talker);
			}
			else if(reply == 12 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_06.htm", talker);
			}
			else if(reply == 13 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_07.htm", talker);
			}
			else if(reply == 14 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_08.htm", talker);
			}
			else if(reply == 11 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_09.htm", talker);
			}
			else if(reply == 4 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_10.htm", talker);
			}
			else if(reply == 3 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_11.htm", talker);
			}
			else if(reply == 7 && st.isCreated() && talker.getLevel() >= 82)
			{
				showPage("beast_herder_tunatun_q0020_12.htm", talker);
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					if(st.getQuestItemsCount(15473) < 1)
					{
						st.giveItems(15473, 1);
						showPage("beast_herder_tunatun_q0020_04.htm", talker);
					}
					else
					{
						showPage("beast_herder_tunatun_q0020_05.htm", talker);
					}
				}
			}
			else if(reply == 5)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showQuestPage("beast_herder_tunatun_q0020_13.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(15533) >= 1 || st.getQuestItemsCount(7185) >= 1))
				{
					st.giveItems(9553, 1);
					st.takeItems(7185, -1);
					st.takeItems(15533, -1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("beast_herder_tunatun_q0020_17.htm", talker);
				}
			}
		}
	}
}