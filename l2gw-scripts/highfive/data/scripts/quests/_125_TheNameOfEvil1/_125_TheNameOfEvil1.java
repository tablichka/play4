package quests._125_TheNameOfEvil1;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _125_TheNameOfEvil1 extends Quest
{
	//NPC
	public final int MUSHIKA = 32114;
	public final int KARAKAWEI = 32117;
	public final int ULU_KAIMU = 32119;
	public final int BALU_KAIMU = 32120;
	public final int CHUTA_KAIMU = 32121;

	//item
	public final int ORNITHOMIMUS_CLAW = 8779;
	public final int DEINONYCHUS_BONE_FRAGMENT = 8780;
	public final int EPITAPH_OF_WISDOM = 8781;
	// mobs
	public final int[] ORNITHOMIMUS = {22200, 22201, 22202};
	public final int[] DEINONYCHUS = {22203, 22204, 22205};
	public final int CHANCE = 30;

	public _125_TheNameOfEvil1()
	{
		super(125, "_125_TheNameOfEvil1", "The Name of Evil - 1");

		addStartNpc(MUSHIKA);
		addTalkId(MUSHIKA);
		addTalkId(KARAKAWEI);
		addTalkId(ULU_KAIMU);
		addTalkId(BALU_KAIMU);
		addTalkId(CHUTA_KAIMU);

		addKillId(ORNITHOMIMUS);
		addKillId(DEINONYCHUS);

		addQuestItem(ORNITHOMIMUS_CLAW);
		addQuestItem(DEINONYCHUS_BONE_FRAGMENT);
		addQuestItem(EPITAPH_OF_WISDOM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");

		// Mushika
		if(event.equalsIgnoreCase("mushika_q0125_03.htm"))
		{
			String prefix = "mushika_q0125_";
			QuestState elrokiQuest = st.getPlayer().getQuestState("_124_MeetingTheElroki");
			if(st.isCreated() && st.getPlayer().getLevel() < 76)
			{
				htmltext = prefix + "02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && (elrokiQuest == null || !elrokiQuest.isCompleted()))
			{
				htmltext = prefix + "04.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = prefix + "05.htm";
		}
		else if(event.equalsIgnoreCase("accept"))
		{
			String prefix = "mushika_q0125_";
			QuestState elrokiQuest = st.getPlayer().getQuestState("_124_MeetingTheElroki");
			if(st.isCreated() && st.getPlayer().getLevel() < 76)
			{
				htmltext = prefix + "02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && (elrokiQuest == null || !elrokiQuest.isCompleted()))
			{
				htmltext = prefix + "04.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = prefix + "08.htm";
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("mushika_q0125_12.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		// Karakawei
		else if(event.equalsIgnoreCase("shaman_caracawe_q0125_09.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		// Ulu Kaimu
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_00.htm"))
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			st.set("ulu_cl", "0");
			if(cond < 5)
				htmltext = prefix + "02.htm";
			else if(cond > 6)
				htmltext = prefix + "03.htm";
			else if(cond == 5)
				htmltext = prefix + "04.htm";
			else
				htmltext = prefix + "21.htm";
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_01.htm"))
		{
			st.set("ulu_cl", "0");
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_05c.htm"))
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			int letters = st.getInt("ulu_cl") + 1;
			st.set("ulu_cl", Integer.toString(letters));
			htmltext = prefix + "05.htm";
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_06c.htm"))
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			int letters = st.getInt("ulu_cl") + 1;
			st.set("ulu_cl", Integer.toString(letters));
			htmltext = prefix + "06.htm";
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_07c.htm"))
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			int letters = st.getInt("ulu_cl") + 1;
			st.set("ulu_cl", Integer.toString(letters));
			htmltext = prefix + "07.htm";
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_08c.htm"))
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			int letters = st.getInt("ulu_cl") + 1;
			if(letters < 4)
				htmltext = prefix + "08.htm";
			else
				htmltext = prefix + "09.htm";
			st.set("ulu_cl", "0");
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0125_20.htm"))
		{
			st.set("cond", "6");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		// Balu Kaimu
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_00.htm"))
		{
			String prefix = "balu_kaimu_stone_q0125_";
			st.set("balu_cl", "0");
			if(cond < 6)
				htmltext = prefix + "02.htm";
			else if(cond > 7)
				htmltext = prefix + "03.htm";
			else if(cond == 6)
				htmltext = prefix + "04.htm";
			else
				htmltext = prefix + "20.htm";
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_01.htm"))
		{
			st.set("balu_cl", "0");
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_05c.htm"))
		{
			String prefix = "balu_kaimu_stone_q0125_";
			int letters = st.getInt("balu_cl") + 1;
			st.set("balu_cl", Integer.toString(letters));
			htmltext = prefix + "05.htm";
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_06c.htm"))
		{
			String prefix = "balu_kaimu_stone_q0125_";
			int letters = st.getInt("balu_cl") + 1;
			st.set("balu_cl", Integer.toString(letters));
			htmltext = prefix + "06.htm";
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_07c.htm"))
		{
			String prefix = "balu_kaimu_stone_q0125_";
			int letters = st.getInt("balu_cl") + 1;
			st.set("balu_cl", Integer.toString(letters));
			htmltext = prefix + "07.htm";
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_08c.htm"))
		{
			String prefix = "balu_kaimu_stone_q0125_";
			int letters = st.getInt("balu_cl") + 1;
			if(letters < 4)
				htmltext = prefix + "08.htm";
			else
				htmltext = prefix + "09.htm";
			st.set("balu_cl", "0");
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0125_19.htm"))
		{
			st.set("cond", "7");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}


		// Chuta Kaimu
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_00.htm"))
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			st.set("jiuta_cl", "0");
			if(cond < 7)
				htmltext = prefix + "02.htm";
			else if(cond > 8)
				htmltext = prefix + "03.htm";
			else if(cond == 7)
				htmltext = prefix + "04.htm";
			else
				htmltext = prefix + "24.htm";
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_01.htm"))
		{
			st.set("jiuta_cl", "0");
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_05c.htm"))
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			int letters = st.getInt("jiuta_cl") + 1;
			st.set("jiuta_cl", Integer.toString(letters));
			htmltext = prefix + "05.htm";
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_06c.htm"))
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			int letters = st.getInt("jiuta_cl") + 1;
			st.set("jiuta_cl", Integer.toString(letters));
			htmltext = prefix + "06.htm";
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_07c.htm"))
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			int letters = st.getInt("jiuta_cl") + 1;
			st.set("jiuta_cl", Integer.toString(letters));
			htmltext = prefix + "07.htm";
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_08c.htm"))
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			int letters = st.getInt("jiuta_cl") + 1;
			if(letters < 4)
				htmltext = prefix + "08.htm";
			else
				htmltext = prefix + "09.htm";
			st.set("jiuta_cl", "0");
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0125_23.htm"))
		{
			st.giveItems(EPITAPH_OF_WISDOM, 1);
			st.set("cond", "8");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
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

		if(npcId == MUSHIKA)
		{
			String prefix = "mushika_q0125_";
			if(st.isCreated())
				htmltext = prefix + "01.htm";
			else if(cond == 1)
				htmltext = prefix + "09.htm";
			else if(cond == 2)
				htmltext = prefix + "14.htm";
			else if(cond == 8)
			{
				htmltext = prefix + "15.htm";
				st.addExpAndSp(859195, 86603);
				st.setState(COMPLETED);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}

		}
		else if(npcId == KARAKAWEI)
		{
			String prefix = "shaman_caracawe_q0125_";
			if(cond == 1)
				htmltext = prefix + "02.htm";
			else if(cond == 2)
				htmltext = prefix + "01.htm";
			else if(cond == 3)
				htmltext = prefix + "12.htm";
			else if(cond == 4)
			{
				htmltext = prefix + "11.htm";
				st.takeItems(DEINONYCHUS_BONE_FRAGMENT, -1);
				st.takeItems(ORNITHOMIMUS_CLAW, -1);
				st.set("cond", "5");
			}
			else if(cond == 5)
				htmltext = prefix + "18.htm";
			else if(cond > 5 && cond < 8)
				htmltext = prefix + "19.htm";
			else if(cond == 8)
				htmltext = prefix + "20.htm";
		}
		else if(npcId == ULU_KAIMU && st.isStarted())
		{
			String prefix = "ulu_kaimu_stone_q0125_";
			htmltext = prefix + "01.htm";
			st.set("ulu_cl", "0");
		}
		else if(npcId == BALU_KAIMU && st.isStarted())
		{
			String prefix = "balu_kaimu_stone_q0125_";
			htmltext = prefix + "01.htm";
			st.set("balu_cl", "0");
		}
		else if(npcId == CHUTA_KAIMU && st.isStarted())
		{
			String prefix = "jiuta_kaimu_stone_q0125_";
			htmltext = prefix + "01.htm";
			st.set("jiuta_cl", "0");
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getCond() == 3)
		{
			for(int i : DEINONYCHUS)
				if(i == npcId && st.rollAndGiveLimited(DEINONYCHUS_BONE_FRAGMENT, 1, CHANCE, 2))
					st.playSound(SOUND_ITEMGET);

			for(int i : ORNITHOMIMUS)
				if(i == npcId && st.rollAndGiveLimited(ORNITHOMIMUS_CLAW, 1, CHANCE, 2))
					st.playSound(SOUND_ITEMGET);

			if(st.getQuestItemsCount(DEINONYCHUS_BONE_FRAGMENT) == 2 && st.getQuestItemsCount(ORNITHOMIMUS_CLAW) == 2)
			{
				st.set("cond", "4");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
	}
}