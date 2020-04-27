package quests._126_TheNameOfEvil2;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _126_TheNameOfEvil2 extends Quest
{
	//NPC
	public final int MUSHIKA = 32114;
	public final int ASAMAH = 32115;
	public final int ULU_KAIMU = 32119;
	public final int BALU_KAIMU = 32120;
	public final int CHUTA_KAIMU = 32121;
	public final int WARRIORS_GRAVE = 32122;
	public final int SHILLIEN_STONE_STATUE = 32109;

	//item
	public final int BONE_POWDER = 8783;
	public final int ADENA = 57;
	public final int SCROLL_ENCHANT_WEAPON_A = 729;

	public _126_TheNameOfEvil2()
	{
		super(126, "_126_TheNameOfEvil2", "The Name of Evil - 2");

		addStartNpc(ASAMAH);
		addTalkId(MUSHIKA);
		addTalkId(ASAMAH);
		addTalkId(ULU_KAIMU);
		addTalkId(BALU_KAIMU);
		addTalkId(CHUTA_KAIMU);
		addTalkId(WARRIORS_GRAVE);
		addTalkId(SHILLIEN_STONE_STATUE);
		addQuestItem(BONE_POWDER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");

		// Asamah
		if(event.equalsIgnoreCase("asama_q0126_03.htm"))
		{
			String prefix = "asama_q0126_";
			QuestState part1Quest = st.getPlayer().getQuestState("_125_TheNameOfEvil1");
			if(st.isCreated() && st.getPlayer().getLevel() < 77)
			{
				htmltext = prefix + "02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && (part1Quest == null || !part1Quest.isCompleted()))
			{
				htmltext = prefix + "04.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = prefix + "05.htm";
		}
		else if(event.equalsIgnoreCase("accept"))
		{
			String prefix = "asama_q0126_";
			QuestState part1Quest = st.getPlayer().getQuestState("_125_TheNameOfEvil1");
			if(st.isCreated() && st.getPlayer().getLevel() < 77)
			{
				htmltext = prefix + "02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && (part1Quest == null || !part1Quest.isCompleted()))
			{
				htmltext = prefix + "04.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = prefix + "07.htm";
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}

		}
		else if(event.equalsIgnoreCase("asama_q0126_10.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("asama_q0126_18.htm"))
		{
			st.set("cond", "21");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("asama_q0126_26.htm"))
		{
			st.set("cond", "22");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("asama_q0126_28.htm"))
		{
			st.set("flag28", "1");
		}

		// Ulu Kaimu
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0126_03.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0126_09.htm"))
		{
			if(st.getInt("cond") < 4)
			{
				st.set("cond", "4");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(event.equalsIgnoreCase("ulu_kaimu_stone_q0126_11.htm"))
		{
			st.set("cond", "5");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		// Balu Kaimu
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0126_04.htm"))
		{
			st.set("cond", "6");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0126_09.htm"))
		{
			if(st.getInt("cond") < 7)
			{
				st.set("cond", "7");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(event.equalsIgnoreCase("balu_kaimu_stone_q0126_11.htm"))
		{
			st.set("cond", "8");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		// Chuta Kaimu
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0126_03.htm"))
		{
			st.set("cond", "9");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0126_11.htm"))
		{
			st.set("cond", "10");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("jiuta_kaimu_stone_q0126_15.htm"))
		{
			st.set("cond", "11");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		// Warrior's Grave
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_06.htm"))
		{
			st.set("cond", "12");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_15.htm"))
		{
			st.set("cond", "13");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_18.htm"))
		{
			st.set("verse1", "0");
			st.set("verse2", "0");
			st.set("verse3", "0");
			st.set("cond", "14");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_26c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse1") + 1;
			st.set("verse1", Integer.toString(letters));
			htmltext = prefix + "26.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_30c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse1") + 1;
			st.set("verse1", Integer.toString(letters));
			htmltext = prefix + "30.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_34c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse1") + 1;
			st.set("verse1", Integer.toString(letters));
			htmltext = prefix + "34.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_38c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse1") + 1;
			st.set("verse1", Integer.toString(letters));
			htmltext = prefix + "38.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_42c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse1") + 1;
			st.set("verse1", Integer.toString(letters));
			if(letters < 5)
			{
				htmltext = prefix + "43.htm";
				st.set("verse1", "0");
			}
			else
			{
				htmltext = prefix + "42.htm";
				st.set("cond", "15");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_43.htm"))
		{
			st.set("verse1", "0");
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_47c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse2") + 1;
			st.set("verse2", Integer.toString(letters));
			htmltext = prefix + "47.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_51c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse2") + 1;
			st.set("verse2", Integer.toString(letters));
			htmltext = prefix + "51.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_55c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse2") + 1;
			st.set("verse2", Integer.toString(letters));
			htmltext = prefix + "55.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_59c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse2") + 1;
			st.set("verse2", Integer.toString(letters));
			htmltext = prefix + "59.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_63c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse2") + 1;
			st.set("verse2", Integer.toString(letters));
			if(letters < 5)
			{
				htmltext = prefix + "64.htm";
				st.set("verse2", "0");
			}
			else
			{
				htmltext = prefix + "63.htm";
				st.set("cond", "16");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_64.htm"))
		{
			st.set("verse2", "0");
		}

		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_68c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse3") + 1;
			st.set("verse3", Integer.toString(letters));
			htmltext = prefix + "68.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_72c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse3") + 1;
			st.set("verse3", Integer.toString(letters));
			htmltext = prefix + "72.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_76c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse3") + 1;
			st.set("verse3", Integer.toString(letters));
			htmltext = prefix + "76.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_80c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse3") + 1;
			st.set("verse3", Integer.toString(letters));
			htmltext = prefix + "80.htm";
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_84c.htm"))
		{
			String prefix = "grave_of_brave_man_q0126_";
			int letters = st.getInt("verse3") + 1;
			st.set("verse3", Integer.toString(letters));
			if(letters < 5)
			{
				htmltext = prefix + "85.htm";
				st.set("verse3", "0");
			}
			else
			{
				htmltext = prefix + "84.htm";
				st.set("cond", "17");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_85.htm"))
		{
			st.set("verse3", "0");
		}

		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_87.htm"))
		{
			if(st.getQuestItemsCount(BONE_POWDER) < 1)
				st.giveItems(BONE_POWDER, 1);
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_88.htm"))
		{
			st.set("cond", "18");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("grave_of_brave_man_q0126_90.htm"))
		{
			st.set("flag90", "1");
		}

		// Statue of Shillien
		else if(event.equalsIgnoreCase("statue_of_shilen_q0126_05.htm"))
		{
			st.set("cond", "19");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("statue_of_shilen_q0126_13.htm"))
		{
			st.set("cond", "20");
			st.takeItems(BONE_POWDER, -1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("statue_of_shilen_q0126_19.htm"))
		{
			st.set("flag19", "1");
		}

		// Mushika
		else if(event.equalsIgnoreCase("mushika_q0126_03.htm"))
		{
			st.set("cond", "23");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("mushika_q0126_09.htm"))
		{
			st.rollAndGive(ADENA, 460483, 100);
			st.addExpAndSp(1015973, 102802);
			st.rollAndGive(SCROLL_ENCHANT_WEAPON_A, 1, 100);
			st.setState(COMPLETED);
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

		if(npcId == ASAMAH)
		{
			String prefix = "asama_q0126_";
			if(st.isCreated())
				htmltext = prefix + "01.htm";
			else if(cond == 1)
				htmltext = prefix + "07.htm";
			else if(cond == 2)
				htmltext = prefix + "11.htm";
			else if(cond > 2 && cond < 11)
				htmltext = prefix + "12.htm";
			else if(cond == 20)
				htmltext = prefix + "13.htm";
			else if(cond == 21)
				htmltext = prefix + "17.htm";
			else if(cond == 22 && st.getInt("flag28") == 0)
				htmltext = prefix + "27.htm";
			else if(cond == 22 && st.getInt("flag28") == 1)
				htmltext = prefix + "29.htm";
			else if(cond > 22)
				htmltext = prefix + "14.htm";
		}
		else if(npcId == ULU_KAIMU)
		{
			String prefix = "ulu_kaimu_stone_q0126_";
			if(cond < 2)
				htmltext = prefix + "01a.htm";
			else if(cond == 2)
				htmltext = prefix + "01.htm";
			else if(cond == 3)
				htmltext = prefix + "04.htm";
			else if(cond == 4)
				htmltext = prefix + "10.htm";
			else if(cond > 4)
				htmltext = prefix + "12.htm";
		}
		else if(npcId == BALU_KAIMU)
		{
			String prefix = "balu_kaimu_stone_q0126_";
			if(cond < 5)
				htmltext = prefix + "02.htm";
			else if(cond == 5)
				htmltext = prefix + "01.htm";
			else if(cond == 6)
				htmltext = prefix + "05.htm";
			else if(cond == 7)
				htmltext = prefix + "10.htm";
			else if(cond > 7)
				htmltext = prefix + "12.htm";
		}
		else if(npcId == CHUTA_KAIMU)
		{
			String prefix = "jiuta_kaimu_stone_q0126_";
			if(cond < 8)
				htmltext = prefix + "02.htm";
			else if(cond == 8)
				htmltext = prefix + "01.htm";
			else if(cond == 9)
				htmltext = prefix + "04.htm";
			else if(cond == 10)
				htmltext = prefix + "12.htm";
			else if(cond > 10)
				htmltext = prefix + "16.htm";
		}
		else if(npcId == WARRIORS_GRAVE)
		{
			String prefix = "grave_of_brave_man_q0126_";
			st.set("verse1", "0");
			st.set("verse2", "0");
			st.set("verse3", "0");
			if(cond < 11)
				htmltext = prefix + "02.htm";
			else if(cond == 11)
				htmltext = prefix + "01.htm";
			else if(cond == 12)
				htmltext = prefix + "07.htm";
			else if(cond == 13)
				htmltext = prefix + "16.htm";
			else if(cond == 14)
				htmltext = prefix + "19.htm";
			else if(cond == 15)
				htmltext = prefix + "45.htm";
			else if(cond == 16)
				htmltext = prefix + "66.htm";
			else if(cond == 17 && st.getQuestItemsCount(BONE_POWDER) < 1)
			{
				htmltext = prefix + "87.htm";
				st.giveItems(BONE_POWDER, 1);
			}
			else if(cond == 17 && st.getQuestItemsCount(BONE_POWDER) > 0)
				htmltext = prefix + "87a.htm";
			else if(cond == 18 && st.getInt("flag90") == 0)
				htmltext = prefix + "89.htm";
			else if(cond == 18 && st.getInt("flag90") == 1)
				htmltext = prefix + "91.htm";

		}
		else if(npcId == SHILLIEN_STONE_STATUE)
		{
			String prefix = "statue_of_shilen_q0126_";
			if(cond < 18)
				htmltext = prefix + "03.htm";
			else if(cond >= 18 && cond <= 19 && st.getQuestItemsCount(BONE_POWDER) < 1)
				htmltext = prefix + "04.htm";
			else if(cond == 18)
				htmltext = prefix + "02.htm";
			else if(cond == 19)
				htmltext = prefix + "06.htm";
			else if(cond > 19 && st.getInt("flag19") == 1)
				htmltext = prefix + "20.htm";
			else if(cond == 20)
				htmltext = prefix + "14.htm";
		}
		else if(npcId == MUSHIKA)
		{
			String prefix = "mushika_q0126_";
			if(cond < 22)
				htmltext = prefix + "02.htm";
			else if(cond == 22)
				htmltext = prefix + "01.htm";
			else if(cond == 23)
				htmltext = prefix + "03a.htm";
		}

		return htmltext;
	}

}