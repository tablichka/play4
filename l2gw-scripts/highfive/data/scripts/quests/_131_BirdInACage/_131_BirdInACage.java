package quests._131_BirdInACage;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _131_BirdInACage extends Quest
{
	//Quest items
	private static final int EchoCrystalOfFreeThought = 9783;
	private static final int ParmesLetter = 9784;
	//notice: item_ID=9690	"Contaminated Crystal" - нужен для прохода в "Crystal Caverns", берется в "Dark Cloud Mansion" 

	//NPCs
	private static final int Kanis = 32264;
	private static final int Parme = 32271;

	public _131_BirdInACage()
	{
		super(131, "_131_BirdInACage", "Bird in a Cage");
		addStartNpc(Kanis);
		addTalkId(Parme);
		addQuestItem(EchoCrystalOfFreeThought);
		addQuestItem(ParmesLetter);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("32264-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32264-08.htm"))
		{
			if(st.getQuestItemsCount(EchoCrystalOfFreeThought) < 1)
				st.giveItems(EchoCrystalOfFreeThought, 1);
		}
		else if(event.equalsIgnoreCase("32264-09.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("32264-12.htm"))
		{
			st.setState(COMPLETED);
			st.addExpAndSp(250677, 25019);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("32271-03.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.giveItems(ParmesLetter, 1);
			int rndX = 30 + Rnd.get(100) * (Rnd.chance(50) ? 1 : -1);
			int rndY = 30 + Rnd.get(100) * (Rnd.chance(50) ? 1 : -1);
			st.getPlayer().teleToLocation(149413 + rndX, 173398 + rndY, -5016, 0, false);
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
		if(st.isCreated())
		{
			if(npcId == Kanis)
			{
				if(st.getPlayer().getLevel() < 78)
				{
					st.exitCurrentQuest(true);
					htmltext = "32264-00.htm";
				}
				else
					htmltext = "32264-01.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Kanis)
			{
				if(0 < cond && cond < 3)
				{
					if(st.getQuestItemsCount(EchoCrystalOfFreeThought) < 1)
						htmltext = "32264-07.htm";
					else
						htmltext = "32264-08.htm";
				}
				else if(cond > 2)
				{
					htmltext = "32264-10.htm";
				}
			}
			else if(npcId == Parme)
			{
				if(cond == 2 && st.getQuestItemsCount(EchoCrystalOfFreeThought) > 0)
					htmltext = "32271-01.htm";
				else
					htmltext = "32271-00.htm";
			}

		}
		return htmltext;
	}
}