package quests._643_RiseAndFallOfTheElrokiTribe;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

public class _643_RiseAndFallOfTheElrokiTribe extends Quest
{
	private static int BONES_OF_A_PLAINS_DINOSAUR = 8776;

	private static int[] REWARDS = {8712, 8713, 8714, 8715, 8716, 8717, 8718, 8719, 8720, 8721, 8722};
	private static final HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>();
	static
	{
		dropChances.put(22204, 11);
		dropChances.put(22203, 55);
		dropChances.put(22225, 11);
		dropChances.put(22220, 11);
		dropChances.put(22745, 36);
		dropChances.put(22743, 36);
		dropChances.put(22205, 11);
		dropChances.put(22201, 11);
		dropChances.put(22200, 11);
		dropChances.put(22224, 11);
		dropChances.put(22219, 11);
		dropChances.put(22744, 36);
		dropChances.put(22742, 36);
		dropChances.put(22202, 11);
		dropChances.put(22209, 11);
		dropChances.put(22208, 11);
		dropChances.put(22221, 11);
		dropChances.put(22210, 11);
		dropChances.put(22212, 11);
		dropChances.put(22211, 11);
		dropChances.put(22227, 11);
		dropChances.put(22222, 11);
		dropChances.put(22213, 11);
	}

	public _643_RiseAndFallOfTheElrokiTribe()
	{
		super(643, "_643_RiseAndFallOfTheElrokiTribe", "Rise And Fall Of The Elroki Tribe"); // Party true

		addStartNpc(32106);
		addTalkId(32117);

		for(int npc : dropChances.keySet())
			addKillId(npc);

		addQuestItem(BONES_OF_A_PLAINS_DINOSAUR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		long count = st.getQuestItemsCount(BONES_OF_A_PLAINS_DINOSAUR);
		if(event.equalsIgnoreCase("singsing_q0643_05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("shaman_caracawe_q0643_06.htm"))
		{
			if(count >= 300)
			{
				st.takeItems(BONES_OF_A_PLAINS_DINOSAUR, 300);
				st.giveItems(REWARDS[Rnd.get(REWARDS.length)], 5);
			}
			else
				htmltext = "shaman_caracawe_q0643_05.htm";
		}
		else if(event.equalsIgnoreCase("None"))
			htmltext = null;
		else if(event.equalsIgnoreCase("Quit"))
		{
			htmltext = null;
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 75)
				htmltext = "singsing_q0643_01.htm";
			else
			{
				htmltext = "singsing_q0643_04.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			if(npcId == 32106)
			{
				long count = st.getQuestItemsCount(BONES_OF_A_PLAINS_DINOSAUR);
				if(count == 0)
					htmltext = "singsing_q0643_08.htm";
				else
				{
					htmltext = "singsing_q0643_08.htm";
					st.takeItems(BONES_OF_A_PLAINS_DINOSAUR, -1);
					st.rollAndGive(57, count * 1374, 100);
				}
			}
			else if(npcId == 32117)
				htmltext = "shaman_caracawe_q0643_02.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, 1);
			if(st != null)
				if(st.rollAndGive(BONES_OF_A_PLAINS_DINOSAUR, 1, dropChances.get(npc.getNpcId())))
					st.playSound(SOUND_ITEMGET);
		}
	}
}