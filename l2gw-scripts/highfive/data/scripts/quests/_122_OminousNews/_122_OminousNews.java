package quests._122_OminousNews;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _122_OminousNews extends Quest
{
	int MOIRA = 31979;
	int KARUDA = 32017;

	public _122_OminousNews()
	{
		super(122, "_122_OminousNews", "Ominous News");

		addStartNpc(MOIRA);
		addTalkId(KARUDA);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		if(npcId == MOIRA)
		{
			if(reply == 122 && player.getLevel() >= 20)
			{
				st.setState(STARTED);
				st.setMemoState(11);
				st.setCond(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("seer_moirase_q0122_0104.htm", player);
				showQuestMark(player);
				return;
			}
		}
		else if(npcId == KARUDA)
		{
			if(st.getInt("cookie") != 0)
			{
				if(reply == 3 && st.isStarted() && st.getMemoState() >= 11)
				{
					st.setState(COMPLETED);
					st.rollAndGive(57, 8923, 100);
					st.addExpAndSp(45151, 2310);
					st.playSound(SOUND_FINISH);
					showPage("karuda_q0122_0201.htm", player);
					st.exitCurrentQuest(false);
					return;
				}
			}
		}

		showPage("noquest", player);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";

		if(st.isCompleted())
			return "npchtm:completed";

		if(npcId == MOIRA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 20)
					htmltext = "seer_moirase_q0122_0101.htm";
				else
					htmltext = "seer_moirase_q0122_0103.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 11)
			{
				htmltext = "npchtm:seer_moirase_q0122_0105.htm";
			}
		}
		else if(npcId == KARUDA)
		{
			if(st.isStarted() && st.getMemoState() == 11)
			{
				st.set("cookie", 1);
				htmltext = "npchtm:karuda_q0122_0101.htm";
			}
		}

		return htmltext;
	}
}