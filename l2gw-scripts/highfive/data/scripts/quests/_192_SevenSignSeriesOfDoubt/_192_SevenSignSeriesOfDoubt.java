package quests._192_SevenSignSeriesOfDoubt;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;

/**
 * @author rage
 * @date 13.08.2010 17:53:15
 */
public class _192_SevenSignSeriesOfDoubt extends Quest
{
	// NPCs
	private static final int CROOP = 30676;
	private static final int HECTOR = 30197;
	private static final int STAN = 30200;
	private static final int CORPSE = 32568;
	private static final int HOLLINT = 30191;

	// ITEMS
	private static final int CROOP_INTRO = 13813;
	private static final int JACOB_NECK = 13814;
	private static final int CROOP_LETTER = 13815;

	public _192_SevenSignSeriesOfDoubt()
	{
		super(192, "_192_SevenSignSeriesOfDoubt", "Seven Sign Series Of Doubt");

		addStartNpc(CROOP);

		addTalkId(CROOP);
		addTalkId(HECTOR);
		addTalkId(STAN);
		addTalkId(CORPSE);
		addTalkId(HOLLINT);

		addQuestItem(CROOP_INTRO, JACOB_NECK, CROOP_LETTER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30676-03.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("8"))
		{
			st.set("cond", 2);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
			st.getPlayer().showQuestMovie(ExStartScenePlayer.SCENE_SSQ_SUSPICIOUS_DEATH);
			return null;
		}
		else if(event.equals("30197-03.htm"))
		{
			st.set("cond", 4);
			st.takeItems(CROOP_INTRO, 1);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("30200-04.htm"))
		{
			st.set("cond", 5);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("32568-02.htm"))
		{
			st.set("cond", 6);
			st.giveItems(JACOB_NECK, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30676-12.htm"))
		{
			st.set("cond", 7);
			st.takeItems(JACOB_NECK, 1);
			st.giveItems(CROOP_LETTER, 1);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("30191-03.htm"))
		{
			if(st.getPlayer().getLevel() < 79)
				htmltext = "<html><body>Only characters who are <font color=\"LEVEL\">level 79</font> or higher may complete this quest.</body></html>";
			else
			{
				st.takeItems(CROOP_LETTER, 1);
				st.addExpAndSp(52518015, 5817677);
				st.unset("cond");
				st.setState(COMPLETED);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		L2Player player = st.getPlayer();
		if(npcId == CROOP)
		{
			if(st.isCreated() && player.getLevel() >= 79)
				htmltext = "30676-01.htm";
			else if(cond == 1)
				htmltext = "30676-04.htm";
			else if(cond == 2)
			{
				htmltext = "30676-05.htm";
				st.set("cond", "3");
				showQuestMark(st.getPlayer());
				st.playSound("ItemSound.quest_middle");
				st.giveItems(CROOP_INTRO, 1);
			}
			else if(cond >= 3 && cond <= 5)
				htmltext = "30676-06.htm";
			else if(cond == 6)
				htmltext = "30676-07.htm";
			else if(st.isCompleted())
				htmltext = "30676-13.htm";
			else if(player.getLevel() < 79)
			{
				htmltext = "30676-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == HECTOR)
		{
			if(cond == 3)
				htmltext = "30197-01.htm";
			if(cond >= 4 && cond <= 7)
				htmltext = "30197-04.htm";
		}
		else if(npcId == STAN)
		{
			if(cond == 4)
				htmltext = "30200-01.htm";
			if(cond >= 5 && cond <= 7)
				htmltext = "30200-05.htm";
		}
		else if(npcId == CORPSE)
		{
			if(cond == 5)
				htmltext = "32568-01.htm";
		}
		else if(npcId == HOLLINT)
			if(cond == 7)
				htmltext = "30191-01.htm";
		return htmltext;
	}
}
