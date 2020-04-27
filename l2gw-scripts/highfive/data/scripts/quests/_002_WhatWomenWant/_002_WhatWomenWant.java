package quests._002_WhatWomenWant;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _002_WhatWomenWant extends Quest
{
	int ARUJIEN = 30223;
	int MIRABEL = 30146;
	int HERBIEL = 30150;
	int GREENIS = 30157;

	int ARUJIENS_LETTER1 = 1092;
	int ARUJIENS_LETTER2 = 1093;
	int ARUJIENS_LETTER3 = 1094;
	int POETRY_BOOK = 689;
	int GREENIS_LETTER = 693;

	int MYSTICS_EARRING = 113;
	private final static int ADENA_ID = 57;

	public _002_WhatWomenWant()
	{
		super(2, "_002_WhatWomenWant", "What Women Want");
		addStartNpc(ARUJIEN);

		addTalkId(MIRABEL);
		addTalkId(HERBIEL);
		addTalkId(GREENIS);

		addQuestItem(GREENIS_LETTER, ARUJIENS_LETTER3, ARUJIENS_LETTER1, ARUJIENS_LETTER2, POETRY_BOOK);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("quest_accept"))
		{
			htmltext = "arujien_q0002_04.htm";
			st.giveItems(ARUJIENS_LETTER1, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("2_1"))
		{
			htmltext = "arujien_q0002_08.htm";
			st.takeItems(ARUJIENS_LETTER3, -1);
			st.giveItems(POETRY_BOOK, 1);
			st.set("cond", "4");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("2_2"))
		{
			htmltext = "arujien_q0002_09.htm";
			st.takeItems(ARUJIENS_LETTER3, -1);
			st.rollAndGive(ADENA_ID, 2300, 100); //st.giveItems(ADENA_ID, 2300, true);
			st.getPlayer().addExpAndSp(4254, 335);
			if(st.getPlayer().getClassId().getLevel() == 1 && !st.getPlayer().getVarB("ng1"))
				st.getPlayer().sendPacket(new ExShowScreenMessage("  Delivery duty complete.\nGo find the Newbie Guide.", 5000, ScreenMessageAlign.TOP_CENTER, true));
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
		if(npcId == ARUJIEN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.elf && st.getPlayer().getRace() != Race.human)
					htmltext = "arujien_q0002_00.htm";
				else if(st.getPlayer().getLevel() >= 2)
					htmltext = "arujien_q0002_02.htm";
				else
				{
					htmltext = "arujien_q0002_01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(ARUJIENS_LETTER1) > 0)
				htmltext = "arujien_q0002_05.htm";
			else if(cond == 2 && st.getQuestItemsCount(ARUJIENS_LETTER2) > 0)
				htmltext = "arujien_q0002_06.htm";
			else if(cond == 3 && st.getQuestItemsCount(ARUJIENS_LETTER3) > 0)
				htmltext = "arujien_q0002_07.htm";
			else if(cond == 4 && st.getQuestItemsCount(POETRY_BOOK) > 0)
				htmltext = "arujien_q0002_11.htm";
			else if(cond == 5 && st.getQuestItemsCount(GREENIS_LETTER) > 0)
			{
				htmltext = "arujien_q0002_09.htm";
				st.giveItems(MYSTICS_EARRING, 1);
				st.takeItems(GREENIS_LETTER, -1);
				if(st.getPlayer().getVarInt("NR41") % 10 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4151", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}
				st.getPlayer().addExpAndSp(4254, 335);
				st.rollAndGive(ADENA_ID, 1850, 100);//st.giveItems(ADENA_ID, (int) ((Config.RATE_QUESTS_REWARD - 1) * 620 + 1850 * Config.RATE_QUESTS_REWARD), false); // T2
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == MIRABEL)
		{
			if(cond == 1 && st.getQuestItemsCount(ARUJIENS_LETTER1) > 0)
			{
				htmltext = "mint_q0002_01.htm";
				st.takeItems(ARUJIENS_LETTER1, -1);
				st.giveItems(ARUJIENS_LETTER2, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else if(cond == 2)
				htmltext = "mint_q0002_02.htm";
		}
		else if(npcId == HERBIEL)
		{
			if(cond == 2 && st.getQuestItemsCount(ARUJIENS_LETTER2) > 0)
			{
				htmltext = "green_q0002_01.htm";
				st.takeItems(ARUJIENS_LETTER2, -1);
				st.giveItems(ARUJIENS_LETTER3, 1);
				st.set("cond", "3");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else if(cond == 3)
				htmltext = "green_q0002_02.htm";
		}
		else if(npcId == GREENIS)
			if(cond == 4 && st.getQuestItemsCount(POETRY_BOOK) > 0)
			{
				htmltext = "grain_q0002_02.htm";
				st.takeItems(POETRY_BOOK, -1);
				st.giveItems(GREENIS_LETTER, 1);
				st.set("cond", "5");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else if(cond == 5 && st.getQuestItemsCount(GREENIS_LETTER) > 0)
				htmltext = "grain_q0002_03.htm";
			else
				htmltext = "grain_q0002_01.htm";
		return htmltext;
	}
}
