package quests._015_SweetWhispers;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _015_SweetWhispers extends Quest
{
	private static final int Vladimir = 31302;
	private static final int Hierarch = 31517;
	private static final int MysteriousNecromancer = 31518;

	public _015_SweetWhispers()
	{
		super(15, "_015_SweetWhispers", "Sweet Whispers");

		addStartNpc(Vladimir);

		addTalkId(Hierarch);
		addTalkId(MysteriousNecromancer);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31302-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31518-02.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31517-02.htm"))
		{
			st.addExpAndSp(350531, 28204);
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
		if(st.isCreated() && npcId == Vladimir)
		{
			if(st.getPlayer().getLevel() < 60)
			{
				htmltext = "31302-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31302-01.htm";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case Vladimir:
				{
					htmltext = "31302-02r.htm";
					break;
				}
				case MysteriousNecromancer:
				{
					if(cond == 1)
						htmltext = "31518-01.htm";
					else if(cond == 2)
						htmltext = "31518-02r.htm";
					break;
				}
				case Hierarch:
				{
					if(cond == 2)
						htmltext = "31517-01.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}