package quests._237_WindsOfChange;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.08.2010 14:23:18
 */
public class _237_WindsOfChange extends Quest
{
	// NPCs
	private static final int FLAUEN = 30899;
	private static final int IASON = 30969;
	private static final int ROMAN = 30897;
	private static final int MORELYN = 30925;
	private static final int HELVETICA = 32641;
	private static final int ATHENIA = 32643;

	private static final int FLAUENS_LETTER = 14862;
	private static final int DOSKOZER_LETTER = 14863;
	private static final int ATHENIA_LETTER = 14864;
	private static final int VICINITY_OF_FOS = 14865;
	private static final int SUPPORT_CERTIFICATE = 14866;

	public _237_WindsOfChange()
	{
		super(237, "_237_WindsOfChange", "Winds Of Change");
		addStartNpc(FLAUEN);
		addTalkId(FLAUEN);
		addTalkId(IASON);
		addTalkId(ROMAN);
		addTalkId(MORELYN);
		addTalkId(HELVETICA);
		addTalkId(ATHENIA);
		addQuestItem(FLAUENS_LETTER, DOSKOZER_LETTER, ATHENIA_LETTER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("30899-06.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.giveItems(FLAUENS_LETTER, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30969-02.htm"))
			st.takeItems(FLAUENS_LETTER, 1);
		else if(event.equals("30969-05.htm"))
		{
			st.set("cond", 2);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30897-03.htm"))
		{
			st.set("cond", 3);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30925-03.htm"))
		{
			st.set("cond", 4);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30969-09.htm"))
		{
			st.giveItems(DOSKOZER_LETTER, 1);
			st.set("cond", 5);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30969-10.htm"))
		{
			st.giveItems(ATHENIA_LETTER, 1);
			st.set("cond", 6);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32641-02.htm"))
		{
			st.takeItems(DOSKOZER_LETTER, 1);
			st.giveItems(VICINITY_OF_FOS, 1);
			st.rollAndGive(57, 213876, 100);
			st.addExpAndSp(892773, 60012);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equals("32643-02.htm"))
		{
			st.giveItems(57, 213876);
			st.takeItems(ATHENIA_LETTER, 1);
			st.giveItems(SUPPORT_CERTIFICATE, 1);
			st.addExpAndSp(892773, 0);
			st.addExpAndSp(0, 60012);
			st.setState(COMPLETED);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == FLAUEN)
		{
			if(st.isCompleted())
				htmltext = "30899-09.htm";
			else if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					htmltext = "30899-01.htm";
				else
				{
					htmltext = "30899-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond >= 1 && cond <= 4)
				htmltext = "30899-07.htm";
			else if(cond >= 5)
				htmltext = "30899-08.htm";
		}
		else if(npcId == IASON)
		{
			if(st.isCompleted())
				htmltext = "completed";
			else if(cond == 1)
				htmltext = "30969-01.htm";
			else if(cond == 2)
				htmltext = "30969-06.htm";
			else if(cond == 4)
				htmltext = "30969-07.htm";
			else if(cond == 5 || cond == 6)
				htmltext = "30969-11.htm";
		}
		else if(npcId == ROMAN)
		{
			if(cond == 2)
				htmltext = "30897-01.htm";
			else if(cond == 3 || cond == 4)
				htmltext = "30897-04.htm";
		}
		else if(npcId == MORELYN)
		{
			if(cond == 3)
				htmltext = "30925-01.htm";
			else if(cond == 4)
				htmltext = "30925-04.htm";
		}
		else if(npcId == HELVETICA)
		{
			if(cond == 5 && st.isCompleted())
				htmltext = "32641-03.htm";
			else if(cond == 5)
				htmltext = "32641-01.htm";
		}
		else if(npcId == ATHENIA)
		{
			if(cond == 6 && st.isCompleted())
				htmltext = "32643-03.htm";
			else if(cond == 6)
				htmltext = "32643-01.htm";
		}
		return htmltext;
	}
}
