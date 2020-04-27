package quests._166_DarkMass;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _166_DarkMass extends Quest
{
	int UNDRES_LETTER_ID = 1088;
	int CEREMONIAL_DAGGER_ID = 1089;
	int DREVIANT_WINE_ID = 1090;
	int GARMIELS_SCRIPTURE_ID = 1091;

	public _166_DarkMass()
	{
		super(166, "_166_DarkMass", "Dark Mass");

		addStartNpc(30130);
		addTalkId(30135, 30139, 30143);
		addQuestItem(CEREMONIAL_DAGGER_ID, DREVIANT_WINE_ID, GARMIELS_SCRIPTURE_ID, UNDRES_LETTER_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "30130-04.htm";
			st.giveItems(UNDRES_LETTER_ID, 1);
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
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
		int cond = st.getCond();

		if(npcId == 30130)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.darkelf && st.getPlayer().getRace() != Race.human)
					htmltext = "30130-00.htm";
				else if(st.getPlayer().getLevel() >= 2)
				{
					htmltext = "30130-03.htm";
					return htmltext;
				}
				else
				{
					htmltext = "30130-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "30130-05.htm";
			else if(cond == 2)
			{
				htmltext = "30130-06.htm";
				st.takeItems(CEREMONIAL_DAGGER_ID, -1);
				st.takeItems(DREVIANT_WINE_ID, -1);
				st.takeItems(GARMIELS_SCRIPTURE_ID, -1);
				st.takeItems(UNDRES_LETTER_ID, -1);
				st.addExpAndSp(5672, 446);
				st.rollAndGive(57, 2966, 100);
				if(st.getPlayer().getVarInt("NR41") % 10 == 0)
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4151", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 30135)
		{
			if(cond == 1 && st.getQuestItemsCount(CEREMONIAL_DAGGER_ID) == 0)
			{
				giveItem(st, CEREMONIAL_DAGGER_ID);
				htmltext = "30135-01.htm";
			}
			else
				htmltext = "30135-02.htm";
		}
		else if(npcId == 30139)
		{
			if(cond == 1 && st.getQuestItemsCount(DREVIANT_WINE_ID) == 0)
			{
				giveItem(st, DREVIANT_WINE_ID);
				htmltext = "30139-01.htm";
			}
			else
				htmltext = "30139-02.htm";
		}
		else if(npcId == 30143)
			if(cond == 1 && st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID) == 0)
			{
				giveItem(st, GARMIELS_SCRIPTURE_ID);
				htmltext = "30143-01.htm";
			}
			else
				htmltext = "30143-02.htm";
		return htmltext;
	}

	private void giveItem(QuestState st, int item)
	{
		st.giveItems(item, 1);
		if(st.getQuestItemsCount(CEREMONIAL_DAGGER_ID) >= 1 && st.getQuestItemsCount(DREVIANT_WINE_ID) >= 1 && st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID) >= 1)
		{
			st.setCond(2);
			st.setState(STARTED);
		}
	}
}