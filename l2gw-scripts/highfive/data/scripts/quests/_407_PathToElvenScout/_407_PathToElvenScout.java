package quests._407_PathToElvenScout;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _407_PathToElvenScout extends Quest
{

	public final int REISA = 30328;
	public final int MORETTI = 30337;
	public final int PIPPEN = 30426;

	public final int OL_MAHUM_SENTRY = 27031;
	public final int OL_MAHUM_PATROL = 20053;

	public final short REORIA_LETTER2_ID = 1207;
	public final short PRIGUNS_TEAR_LETTER1_ID = 1208;
	public final short PRIGUNS_TEAR_LETTER2_ID = 1209;
	public final short PRIGUNS_TEAR_LETTER3_ID = 1210;
	public final short PRIGUNS_TEAR_LETTER4_ID = 1211;
	public final short MORETTIS_HERB_ID = 1212;
	public final short MORETTIS_LETTER_ID = 1214;
	public final short PRIGUNS_LETTER_ID = 1215;
	public final short MONORARY_GUARD_ID = 1216;
	public final short REORIA_RECOMMENDATION_ID = 1217;
	public final short RUSTED_KEY_ID = 1293;
	public final short HONORARY_GUARD_ID = 1216;

	public _407_PathToElvenScout()
	{
		super(407, "_407_PathToElvenScout", "Path To Elven Scout");

		addStartNpc(REISA);
		addTalkId(REISA);
		addTalkId(REISA);
		addTalkId(MORETTI);
		addTalkId(PIPPEN);
		addTalkId(REISA);

		addKillId(OL_MAHUM_SENTRY);
		addKillId(OL_MAHUM_PATROL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x12)
			{
				if(st.getPlayer().getClassId().getId() == 0x16)
					htmltext = "30328-02a.htm";
				else
					htmltext = "30328-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(REORIA_RECOMMENDATION_ID) > 0)
			{
				htmltext = "30328-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30328-03.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30328-05.htm";
				st.giveItems(REORIA_LETTER2_ID, 1);
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("30337_1"))
		{
			st.takeItems(REORIA_LETTER2_ID, 1);
			st.set("cond", "2");
			htmltext = "30337-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == REISA)
		{
			if(st.isCreated())
				htmltext = "30328-01.htm";
			else if(cond == 1)
				htmltext = "30328-06.htm";
			else if(cond > 1 && st.getQuestItemsCount(HONORARY_GUARD_ID) == 0)
				htmltext = "30328-08.htm";
			else if(cond == 8 && st.getQuestItemsCount(HONORARY_GUARD_ID) == 1)
			{
				htmltext = "30328-07.htm";
				st.takeItems(HONORARY_GUARD_ID, 1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(REORIA_RECOMMENDATION_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 19932);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 26630);
						else
							st.addExpAndSp(591724, 33328);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.playSound(SOUND_FINISH);
				st.showSocial(3);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == MORETTI)
		{
			if(cond == 1)
				htmltext = "30337-01.htm";
			else if(cond == 2)
				htmltext = "30337-04.htm";
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID) == 1 && st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID) == 1 && st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID) == 1 && st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 1)
				{
					htmltext = "30337-06.htm";
					st.takeItems(PRIGUNS_TEAR_LETTER1_ID, 1);
					st.takeItems(PRIGUNS_TEAR_LETTER2_ID, 1);
					st.takeItems(PRIGUNS_TEAR_LETTER3_ID, 1);
					st.takeItems(PRIGUNS_TEAR_LETTER4_ID, 1);
					st.giveItems(MORETTIS_HERB_ID, 1);
					st.giveItems(MORETTIS_LETTER_ID, 1);
					st.set("cond", "4");
				}
				else
					htmltext = "30337-05.htm";
			}
			else if(cond == 7 && st.getQuestItemsCount(PRIGUNS_LETTER_ID) == 1)
			{
				htmltext = "30337-07.htm";
				st.takeItems(PRIGUNS_LETTER_ID, 1);
				st.giveItems(HONORARY_GUARD_ID, 1);
				st.set("cond", "8");
			}
			else if(cond > 8)
				htmltext = "30337-08.htm";
		}
		else if(npcId == PIPPEN)
			if(cond == 4)
			{
				htmltext = "30426-01.htm";
				st.set("cond", "5");
			}
			else if(cond == 5)
				htmltext = "30426-01.htm";
			else if(cond == 6 && st.getQuestItemsCount(RUSTED_KEY_ID) == 1 && st.getQuestItemsCount(MORETTIS_HERB_ID) == 1 && st.getQuestItemsCount(MORETTIS_LETTER_ID) == 1)
			{
				htmltext = "30426-02.htm";
				st.takeItems(RUSTED_KEY_ID, 1);
				st.takeItems(MORETTIS_HERB_ID, 1);
				st.takeItems(MORETTIS_LETTER_ID, 1);
				st.giveItems(PRIGUNS_LETTER_ID, 1);
				st.set("cond", "7");
			}
			else if(cond == 7)
				htmltext = "30426-04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == OL_MAHUM_PATROL && cond == 2)
		{
			if(st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID) == 0)
			{
				st.giveItems(PRIGUNS_TEAR_LETTER1_ID, 1);
				st.playSound(SOUND_ITEMGET);
				return;
			}
			if(st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID) == 0)
			{
				st.giveItems(PRIGUNS_TEAR_LETTER2_ID, 1);
				st.playSound(SOUND_ITEMGET);
				return;
			}
			if(st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID) == 0)
			{
				st.giveItems(PRIGUNS_TEAR_LETTER3_ID, 1);
				st.playSound(SOUND_ITEMGET);
				return;
			}
			if(st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 0)
			{
				st.giveItems(PRIGUNS_TEAR_LETTER4_ID, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "3");
				st.setState(STARTED);
			}
		}
		else if(npcId == OL_MAHUM_SENTRY && cond == 5 && st.rollAndGiveLimited(RUSTED_KEY_ID, 1, 60, 1))
		{
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "6");
			st.setState(STARTED);
		}
	}
}
