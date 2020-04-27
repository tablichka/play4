package quests._107_MercilessPunishment;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _107_MercilessPunishment extends Quest
{
	int HATOSS_ORDER1 = 1553;
	int HATOSS_ORDER2 = 1554;
	int HATOSS_ORDER3 = 1555;
	int LETTER_TO_HUMAN = 1557;
	int LETTER_TO_DARKELF = 1556;
	int LETTER_TO_ELF = 1558;
	int BUTCHER = 1510;

	public _107_MercilessPunishment()
	{
		super(107, "_107_MercilessPunishment", "Merciless Punishment");

		addStartNpc(30568);

		addTalkId(30580);

		addKillId(27041);

		addQuestItem(LETTER_TO_DARKELF, LETTER_TO_HUMAN, LETTER_TO_ELF, HATOSS_ORDER1, HATOSS_ORDER2, HATOSS_ORDER3);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("urutu_chief_hatos_q0107_03.htm"))
		{
			st.giveItems(HATOSS_ORDER1, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("urutu_chief_hatos_q0107_06.htm"))
		{
			st.takeItems(HATOSS_ORDER2, 1);
			st.takeItems(LETTER_TO_DARKELF, 1);
			st.takeItems(LETTER_TO_HUMAN, 1);
			st.takeItems(LETTER_TO_ELF, 1);
			st.takeItems(HATOSS_ORDER1, 1);
			st.takeItems(HATOSS_ORDER2, 1);
			st.takeItems(HATOSS_ORDER3, 1);
			st.rollAndGive(57, 200, 100);
			st.unset("cond");
			st.playSound(SOUND_GIVEUP);
		}
		else if(event.equalsIgnoreCase("urutu_chief_hatos_q0107_07.htm"))
		{
			st.takeItems(HATOSS_ORDER1, 1);
			if(st.getQuestItemsCount(HATOSS_ORDER2) == 0)
				st.giveItems(HATOSS_ORDER2, 1);
		}
		else if(event.equalsIgnoreCase("urutu_chief_hatos_q0107_09.htm"))
		{
			st.takeItems(HATOSS_ORDER2, 1);
			if(st.getQuestItemsCount(HATOSS_ORDER3) == 0)
				st.giveItems(HATOSS_ORDER3, 1);
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
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == 30568)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.orc)
				{
					htmltext = "urutu_chief_hatos_q0107_00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 10)
					htmltext = "urutu_chief_hatos_q0107_02.htm";
				else
				{
					htmltext = "urutu_chief_hatos_q0107_01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(HATOSS_ORDER1) > 0)
				htmltext = "urutu_chief_hatos_q0107_04.htm";
			else if(cond == 2 && st.getQuestItemsCount(HATOSS_ORDER1) > 0 && st.getQuestItemsCount(LETTER_TO_HUMAN) == 0)
				htmltext = "urutu_chief_hatos_q0107_04.htm";
			else if(cond == 3 && st.getQuestItemsCount(HATOSS_ORDER1) > 0 && st.getQuestItemsCount(LETTER_TO_HUMAN) >= 1)
			{
				htmltext = "urutu_chief_hatos_q0107_05.htm";
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else if(cond == 4 && st.getQuestItemsCount(HATOSS_ORDER2) > 0 && st.getQuestItemsCount(LETTER_TO_DARKELF) == 0)
				htmltext = "urutu_chief_hatos_q0107_05.htm";
			else if(cond == 5 && st.getQuestItemsCount(HATOSS_ORDER2) > 0 && st.getQuestItemsCount(LETTER_TO_DARKELF) >= 1)
			{
				htmltext = "urutu_chief_hatos_q0107_08.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6 && st.getQuestItemsCount(HATOSS_ORDER3) > 0 && st.getQuestItemsCount(LETTER_TO_ELF) == 0)
				htmltext = "urutu_chief_hatos_q0107_08.htm";
			else if(cond == 7 && st.getQuestItemsCount(HATOSS_ORDER3) > 0 && st.getQuestItemsCount(LETTER_TO_ELF) + st.getQuestItemsCount(LETTER_TO_HUMAN) + st.getQuestItemsCount(LETTER_TO_DARKELF) == 3)
			{
				if(st.getPlayer().getLevel() < 25)
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 7000);
				}

				if(st.getPlayer().getVarInt("NR41") == 0)
				{
					st.getPlayer().setVar("NR41", 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}
				else if(st.getPlayer().getVarInt("NR41") % 1000000 / 100000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}

				st.addExpAndSp(34565, 2962);
				st.rollAndGive(57, 14666, 100);
				htmltext = "urutu_chief_hatos_q0107_10.htm";
				st.giveItems(1060, 100); // healing potion
				for(int item = 4412; item <= 4416; item++)
					st.giveItems(item, 10); // echo cry
				st.takeItems(LETTER_TO_DARKELF, -1);
				st.takeItems(LETTER_TO_HUMAN, -1);
				st.takeItems(LETTER_TO_ELF, -1);
				st.takeItems(HATOSS_ORDER3, -1);
				st.giveItems(BUTCHER, 1);
				st.showSocial(3);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		}
		else if(npcId == 30580 && cond >= 1 && (st.getQuestItemsCount(HATOSS_ORDER1) > 0 || st.getQuestItemsCount(HATOSS_ORDER2) > 0 || st.getQuestItemsCount(HATOSS_ORDER3) > 0))
		{
			if(cond == 1)
			{
				st.set("cond", "2");
				st.setState(STARTED);
			}
			htmltext = "centurion_parugon_q0107_01.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 27041)
			if(cond == 2 && st.getQuestItemsCount(HATOSS_ORDER1) > 0 && st.getQuestItemsCount(LETTER_TO_HUMAN) == 0)
			{
				st.giveItems(LETTER_TO_HUMAN, 1);
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else if(cond == 4 && st.getQuestItemsCount(HATOSS_ORDER2) > 0 && st.getQuestItemsCount(LETTER_TO_DARKELF) == 0)
			{
				st.giveItems(LETTER_TO_DARKELF, 1);
				st.set("cond", "5");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else if(cond == 6 && st.getQuestItemsCount(HATOSS_ORDER3) > 0 && st.getQuestItemsCount(LETTER_TO_ELF) == 0)
			{
				st.giveItems(LETTER_TO_ELF, 1);
				st.set("cond", "7");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
	}
}