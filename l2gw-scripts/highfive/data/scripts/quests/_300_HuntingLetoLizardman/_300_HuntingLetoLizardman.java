package quests._300_HuntingLetoLizardman;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _300_HuntingLetoLizardman extends Quest
{
	//NPCs
	private static int RATH = 30126;
	//Items
	private static int BRACELET_OF_LIZARDMAN = 7139;
	private static int ANIMAL_BONE = 1872;
	private static int ANIMAL_SKIN = 1867;
	private static int ADENA = 57;
	//Chances
	private static int BRACELET_OF_LIZARDMAN_CHANCE = 70;
	private static int ADENA_CHANCE = 50;

	public _300_HuntingLetoLizardman()
	{
		super(300, "_300_HuntingLetoLizardman", "Hunting Leto Lizardman");
		addStartNpc(RATH);
		for(int lizardman_id = 20577; lizardman_id <= 20582; lizardman_id++)
			addKillId(lizardman_id);
		addQuestItem(BRACELET_OF_LIZARDMAN);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != RATH)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 34)
			{
				htmltext = "30126-00.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30126-01.htm";
				st.set("cond", "0");
			}
		}
		else if(st.getQuestItemsCount(BRACELET_OF_LIZARDMAN) < 60)
		{
			htmltext = "30126-02r.htm";
			st.set("cond", "1");
		}
		else
			htmltext = "30126-03.htm";
		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30126-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30126-04.htm") && st.isStarted())
			if(st.getQuestItemsCount(BRACELET_OF_LIZARDMAN) < 60)
			{
				htmltext = "bug.htm";
				st.set("cond", "1");
			}
			else
			{
				st.takeItems(BRACELET_OF_LIZARDMAN, -1);
				switch(Rnd.get(3))
				{
					case 0:
						st.rollAndGive(ADENA, 30000, 100);
						break;
					case 1:
						st.rollAndGive(ANIMAL_BONE, 50, 100);
						break;
					case 2:
						st.rollAndGive(ANIMAL_SKIN, 50, 100);
						break;
				}
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getCond() == 1 && st.rollAndGiveLimited(BRACELET_OF_LIZARDMAN, 1, BRACELET_OF_LIZARDMAN_CHANCE, 60))
		{
			if(st.getQuestItemsCount(BRACELET_OF_LIZARDMAN) == 60)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}