package quests._133_ThatsBloodyHot;

import quests.global.Hellbound;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Thats Bloody Hot
 *
 * @author PainKiller
 * @LastFixes by HellSinger
 */

public class _133_ThatsBloodyHot extends Quest
{
	//NPC
	private final static int KANIS = 32264;
	private final static int GALATE = 32292;

	//ITEMS
	private final static int CRYSTAL_SAMPLE = 9785;

	public _133_ThatsBloodyHot()
	{
		super(133, "_133_ThatsBloodyHot", "Thats Bloody Hot"); // Party true?

		addStartNpc(KANIS);
		addTalkId(KANIS);
		addTalkId(GALATE);

		addQuestItem(CRYSTAL_SAMPLE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("32264-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32264-07.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.giveItems(CRYSTAL_SAMPLE, 1);
			st.set("cry_is_given", "0");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("32292-03.htm"))
		{
			if(st.getQuestItemsCount(CRYSTAL_SAMPLE) > 0)
			{
				st.takeItems(CRYSTAL_SAMPLE, -1);
				st.set("cry_is_given", "1");
			}
		}
		else if(event.equalsIgnoreCase("32292-04.htm"))
		{
			int HBStage = ServerVariables.getInt("hb_stage", 0);

			st.rollAndGive(57, 254247, 100);
			st.addExpAndSp(331457, 32524);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
			ServerVariables.set("hb_stage0_accept", 1);
			int HBStage0Progress = ServerVariables.getInt("hb_stage0_progress", 0);

			if(HBStage0Progress < 100000)
			{
				HBStage0Progress += 5000; // full party would generate +45.000 points
				ServerVariables.set("hb_stage0_progress", HBStage0Progress);
			}

			if(HBStage < 1 && HBStage0Progress >= 100000)
			{
				Hellbound.setStage(1);
				Hellbound.spawnStage(1);
			}
			else
				htmltext = "32292-05.htm"; // warp gate isn't operational yet

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
		if(npcId == KANIS)
		{
			if(st.isCreated())
			{
				QuestState BirdInACage = st.getPlayer().getQuestState("_131_BirdInACage");
				if(BirdInACage != null && BirdInACage.isCompleted())
					htmltext = "32264-01.htm";
				else
				{
					htmltext = "32264-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "32264-02.htm";
			else if(cond == 2)
				htmltext = "32264-07a.htm";
		}
		else if(npcId == GALATE && cond == 2)
		{
			if(st.getQuestItemsCount(CRYSTAL_SAMPLE) > 0)
				htmltext = "32292-01.htm";
			else if(st.getInt("cry_is_given") == 1)
				htmltext = "32292-03.htm";
			else
				htmltext = "32292-01a.htm";
		}
		return htmltext;
	}
}