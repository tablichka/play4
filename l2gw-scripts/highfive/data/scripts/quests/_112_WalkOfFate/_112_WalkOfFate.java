package quests._112_WalkOfFate;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _112_WalkOfFate extends Quest
{
	//NPC
	private static final int Livina = 30572;
	private static final int Karuda = 32017;
	//Items
	private static final int EnchantD = 956;

	public _112_WalkOfFate()
	{
		super(112, "_112_WalkOfFate", "Walk of Fate");

		addStartNpc(Livina);
		addTalkId(Karuda);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("karuda_q0112_0201.htm"))
		{
			st.addExpAndSp(112876, 5774);
			st.rollAndGive(57, 22308, 100);
			st.giveItems(EnchantD, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("seer_livina_q0112_0104.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(st.isCompleted())
			htmltext = "completed";
		int cond = st.getInt("cond");
		if(npcId == Livina)
		{
			if(cond == 0)
			{
				if(st.getPlayer().getLevel() >= 20)
					htmltext = "seer_livina_q0112_0101.htm";
				else
				{
					htmltext = "seer_livina_q0112_0103.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "seer_livina_q0112_0105.htm";
		}
		else if(npcId == Karuda)
			if(cond == 1)
				htmltext = "karuda_q0112_0101.htm";
		return htmltext;
	}
}
