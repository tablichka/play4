package quests._167_DwarvenKinship;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _167_DwarvenKinship extends Quest
{
	//NPC
	private static final int Carlon = 30350;
	private static final int Haprock = 30255;
	private static final int Norman = 30210;
	//Quest Items
	private static final int CarlonsLetter = 1076;
	private static final int NormansLetter = 1106;
	//Items
	private static final int Adena = 57;

	public _167_DwarvenKinship()
	{
		super(167, "_167_DwarvenKinship", "Dwarven Kinship");

		addStartNpc(Carlon);

		addTalkId(Carlon);
		addTalkId(Haprock);
		addTalkId(Norman);

		addQuestItem(CarlonsLetter, NormansLetter);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30350-04.htm"))
		{
			st.giveItems(CarlonsLetter, 1);
			st.playSound(SOUND_ACCEPT);
			st.set("cond", "1");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30255-03.htm"))
		{
			st.takeItems(CarlonsLetter, -1);
			st.giveItems(NormansLetter, 1);
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30255-04.htm"))
		{
			st.takeItems(CarlonsLetter, -1);
			st.rollAndGive(Adena, 2000, 100);
			st.unset("cond");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("30210-02.htm"))
		{
			st.takeItems(NormansLetter, -1);
			st.rollAndGive(Adena, 20000, 100);
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
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Carlon)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 15)
					htmltext = "30350-03.htm";
				else
				{
					htmltext = "30350-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond > 0)
				htmltext = "30350-05.htm";
		}
		else if(npcId == Haprock)
		{
			if(cond == 1)
				htmltext = "30255-01.htm";
			else if(cond > 1)
				htmltext = "30255-05.htm";
		}
		else if(npcId == Norman && cond == 2)
			htmltext = "30210-01.htm";
		return htmltext;
	}
}