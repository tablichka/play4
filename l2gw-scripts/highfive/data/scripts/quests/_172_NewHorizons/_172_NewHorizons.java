package quests._172_NewHorizons;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _172_NewHorizons extends Quest
{
	//NPC
	private static final int Zenya = 32140;
	private static final int Ragara = 32163;
	//Items
	private static final int ScrollOfEscapeGiran = 7126;
	private static final int MarkOfTraveler = 7570;

	public _172_NewHorizons()
	{
		super(172, "_172_NewHorizons", "New Horizons");

		addStartNpc(Zenya);

		addTalkId(Zenya);
		addTalkId(Ragara);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("32140-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32163-02.htm"))
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
		if(npcId == Zenya)
		{
			if(st.isCreated())
				if(st.getPlayer().getRace() == Race.kamael && st.getPlayer().getLevel() >= 3)
					htmltext = "32140-01.htm";
				else
				{
					htmltext = "32140-00.htm";
					st.exitCurrentQuest(true);
				}
		}
		else if(npcId == Ragara)
			if(cond == 1)
				htmltext = "32163-01.htm";
		return htmltext;
	}
}