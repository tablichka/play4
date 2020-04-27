package quests._006_StepIntoTheFuture;

// version = Unknown

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _006_StepIntoTheFuture extends Quest
{
	//NPC
	private static final int Roxxy = 30006;
	private static final int Baulro = 30033;
	private static final int Windawood = 30311;
	//Quest Item
	private static final int BaulrosLetter = 7571;
	//Items
	private static final int ScrollOfEscapeGiran = 7126;
	private static final int MarkOfTraveler = 7570;

	public _006_StepIntoTheFuture()
	{
		super(6, "_006_StepIntoTheFuture", "Step Into The Future");

		addStartNpc(Roxxy);

		addTalkId(Baulro);
		addTalkId(Windawood);

		addQuestItem(BaulrosLetter);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("rapunzel_q0006_0104.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("baul_q0006_0201.htm"))
		{
			st.giveItems(BaulrosLetter, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("sir_collin_windawood_q0006_0301.htm"))
		{
			st.takeItems(BaulrosLetter, -1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("rapunzel_q0006_0401.htm"))
		{
			st.giveItems(ScrollOfEscapeGiran, 1);
			st.giveItems(MarkOfTraveler, 1);
			st.unset("cond");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Roxxy)
		{
			if(st.isCreated())
				if(st.getPlayer().getRace() == Race.human && st.getPlayer().getLevel() >= 3)
					htmltext = "rapunzel_q0006_0101.htm";
				else
				{
					htmltext = "rapunzel_q0006_0102.htm";
					st.exitCurrentQuest(true);
				}
			else if(cond == 1)
				htmltext = "rapunzel_q0006_0105.htm";
			else if(cond == 3)
				htmltext = "rapunzel_q0006_0301.htm";
		}
		else if(npcId == Baulro)
		{
			if(cond == 1)
				htmltext = "baul_q0006_0101.htm";
			else if(cond == 2 && st.getQuestItemsCount(BaulrosLetter) > 0)
				htmltext = "baul_q0006_0202.htm";
		}
		else if(npcId == Windawood)
			if(cond == 2 && st.getQuestItemsCount(BaulrosLetter) > 0)
				htmltext = "sir_collin_windawood_q0006_0201.htm";
			else if(cond == 2 && st.getQuestItemsCount(BaulrosLetter) == 0)
				htmltext = "sir_collin_windawood_q0006_0302.htm";
			else if(cond == 3)
				htmltext = "sir_collin_windawood_q0006_0303.htm";
		return htmltext;
	}
}
