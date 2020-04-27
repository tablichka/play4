package quests._410_PathToPalusKnight;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _410_PathToPalusKnight extends Quest
{
	//npc
	public final int VIRGIL = 30329;
	public final int KALINTA = 30422;
	//mobs
	public final int POISON_SPIDER = 20038;
	public final int ARACHNID_TRACKER = 20043;
	public final int LYCANTHROPE = 20049;
	//items
	public final int PALLUS_TALISMAN_ID = 1237;
	public final int LYCANTHROPE_SKULL_ID = 1238;
	public final int VIRGILS_LETTER_ID = 1239;
	public final int MORTE_TALISMAN_ID = 1240;
	public final int PREDATOR_CARAPACE_ID = 1241;
	public final int TRIMDEN_SILK_ID = 1242;
	public final int COFFIN_ETERNAL_REST_ID = 1243;
	public final int GAZE_OF_ABYSS_ID = 1244;

	public _410_PathToPalusKnight()
	{
		super(410, "_410_PathToPalusKnight", "Path to Palus Knight");

		addStartNpc(VIRGIL);

		addTalkId(VIRGIL);
		addTalkId(KALINTA);

		addKillId(POISON_SPIDER);
		addKillId(ARACHNID_TRACKER);
		addKillId(LYCANTHROPE);

		addQuestItem(PALLUS_TALISMAN_ID,
				VIRGILS_LETTER_ID,
				COFFIN_ETERNAL_REST_ID,
				MORTE_TALISMAN_ID,
				PREDATOR_CARAPACE_ID,
				TRIMDEN_SILK_ID,
				LYCANTHROPE_SKULL_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30329-06.htm";
			st.giveItems(PALLUS_TALISMAN_ID, 1);
		}
		else if(event.equalsIgnoreCase("410_1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x1f)
			{
				if(st.getPlayer().getClassId().getId() == 0x20)
					htmltext = "30329-02a.htm";
				else
					htmltext = "30329-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(GAZE_OF_ABYSS_ID) > 0)
			{
				htmltext = "30329-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30329-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30329-05.htm";
		}
		else if(event.equalsIgnoreCase("30329_2"))
		{
			htmltext = "30329-10.htm";
			st.takeItems(PALLUS_TALISMAN_ID, -1);
			st.takeItems(LYCANTHROPE_SKULL_ID, -1);
			st.giveItems(VIRGILS_LETTER_ID, 1);
			st.set("cond", "3");
		}
		else if(event.equalsIgnoreCase("30422_1"))
		{
			htmltext = "30422-02.htm";
			st.takeItems(VIRGILS_LETTER_ID, -1);
			st.giveItems(MORTE_TALISMAN_ID, 1);
			st.set("cond", "4");
		}
		else if(event.equalsIgnoreCase("30422_2"))
		{
			htmltext = "30422-06.htm";
			st.takeItems(MORTE_TALISMAN_ID, -1);
			st.takeItems(TRIMDEN_SILK_ID, -1);
			st.takeItems(PREDATOR_CARAPACE_ID, -1);
			st.giveItems(COFFIN_ETERNAL_REST_ID, 1);
			st.set("cond", "6");
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == VIRGIL)
		{
			if(cond < 1)
				htmltext = "30329-01.htm";
			else if(st.getQuestItemsCount(PALLUS_TALISMAN_ID) > 0)
			{
				if(st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) < 1)
					htmltext = "30329-07.htm";
				else if(st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) > 0 && st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) < 13)
					htmltext = "30329-08.htm";
				else if(st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) > 12)
					htmltext = "30329-09.htm";
			}
			else if(st.getQuestItemsCount(COFFIN_ETERNAL_REST_ID) > 0)
			{
				htmltext = "30329-11.htm";
				st.takeItems(COFFIN_ETERNAL_REST_ID, -1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(GAZE_OF_ABYSS_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 26212);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 32910);
						else
							st.addExpAndSp(591724, 39608);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
			else if(st.getQuestItemsCount(MORTE_TALISMAN_ID) > 0 | st.getQuestItemsCount(VIRGILS_LETTER_ID) > 0)
				htmltext = "30329-12.htm";
		}
		else if(npcId == KALINTA && cond > 0)
			if(st.getQuestItemsCount(VIRGILS_LETTER_ID) > 0)
				htmltext = "30422-01.htm";
			else if(st.getQuestItemsCount(MORTE_TALISMAN_ID) > 0)
				if(st.getQuestItemsCount(TRIMDEN_SILK_ID) < 1 && st.getQuestItemsCount(PREDATOR_CARAPACE_ID) < 1)
					htmltext = "30422-03.htm";
				else if(st.getQuestItemsCount(TRIMDEN_SILK_ID) < 1 | st.getQuestItemsCount(PREDATOR_CARAPACE_ID) < 1)
					htmltext = "30422-04.htm";
				else if(st.getQuestItemsCount(TRIMDEN_SILK_ID) > 4 && st.getQuestItemsCount(PREDATOR_CARAPACE_ID) > 0)
					htmltext = "30422-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == LYCANTHROPE)
		{
			if(cond == 1 && st.getQuestItemsCount(PALLUS_TALISMAN_ID) > 0 && st.rollAndGiveLimited(LYCANTHROPE_SKULL_ID, 1, 100, 13))
			{
				if(st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) == 13)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "2");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == POISON_SPIDER)
		{
			if(cond == 4 && st.getQuestItemsCount(MORTE_TALISMAN_ID) > 0 && st.getQuestItemsCount(PREDATOR_CARAPACE_ID) < 1)
			{
				st.giveItems(PREDATOR_CARAPACE_ID, 1);
				st.playSound(SOUND_MIDDLE);
				if(st.getQuestItemsCount(TRIMDEN_SILK_ID) > 4)
				{
					st.set("cond", "5");
					st.setState(STARTED);
				}
			}
		}
		else if(npcId == ARACHNID_TRACKER)
			if(cond == 4 && st.getQuestItemsCount(MORTE_TALISMAN_ID) > 0 && st.rollAndGiveLimited(TRIMDEN_SILK_ID, 1, 100, 5))
			{
				if(st.getQuestItemsCount(TRIMDEN_SILK_ID) == 5)
				{
					st.playSound(SOUND_MIDDLE);
					if(st.getQuestItemsCount(PREDATOR_CARAPACE_ID) > 0)
					{
						st.set("cond", "5");
						st.setState(STARTED);
					}
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}