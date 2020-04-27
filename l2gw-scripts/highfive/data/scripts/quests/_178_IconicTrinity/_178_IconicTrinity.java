package quests._178_IconicTrinity;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Files;

public class _178_IconicTrinity extends Quest
{
	//NPC
	private static final int Kekropus = 32138;
	private static final int IconOfThePast = 32255;
	private static final int IconOfThePresent = 32256;
	private static final int IconOfTheFuture = 32257;
	//Items
	private static final int EnchantD = 956;

	public _178_IconicTrinity()
	{
		super(178, "_178_IconicTrinity", "Iconic Trinity");

		addStartNpc(Kekropus);
		addTalkId(IconOfThePast);
		addTalkId(IconOfThePresent);
		addTalkId(IconOfTheFuture);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("32138-02.htm") && st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.kamael || st.getPlayer().getLevel() < 17)
			{
				htmltext = "32138-00.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("32255-03.htm") || event.equalsIgnoreCase("32256-03.htm") || event.equalsIgnoreCase("32257-03.htm"))
		{
			st.set("id", "");
			htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/" + event, st.getPlayer().getVar("lang@"));
			htmltext = htmltext.replace("%pass%", "");
		}
		else if(event.equalsIgnoreCase("32255-09.htm"))
		{
			st.set("id", "");
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("32256-09.htm"))
		{
			st.set("id", "");
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("32257-06.htm"))
		{
			st.set("id", "");
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.length() == 1)
		{
			int cond = st.getInt("cond");
			st.set("id", st.get("id") + event);
			int len = st.get("id").toString().length();
			if(len == 4 && (cond == 1 || cond == 2) || len == 5 && cond == 3)
			{
				if(cond == 1 && st.get("id") != null && st.get("id").toString().equalsIgnoreCase("CRTR"))
				{
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32255-04.htm", st.getPlayer().getVar("lang@"));
					htmltext = htmltext.replace("%pass%", "****");
				}
				else if(cond == 2 && st.get("id") != null && st.get("id").toString().equalsIgnoreCase("CNCL"))
				{
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32256-04.htm", st.getPlayer().getVar("lang@"));
					htmltext = htmltext.replace("%pass%", "****");
				}
				else if(cond == 3 && st.get("id") != null && st.get("id").toString().equalsIgnoreCase("CHAOS"))
				{
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32257-04.htm", st.getPlayer().getVar("lang@"));
					htmltext = htmltext.replace("%pass%", "*****");
				}
				else
				{
					htmltext = "<html><body>Quest Failed</body></html>";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				if(cond == 1)
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32255-03.htm", st.getPlayer().getVar("lang@"));
				else if(cond == 2)
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32256-03.htm", st.getPlayer().getVar("lang@"));
				else
					htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/32257-03.htm", st.getPlayer().getVar("lang@"));
				if(len == 1)
					htmltext = htmltext.replace("%pass%", "*");
				else if(len == 2)
					htmltext = htmltext.replace("%pass%", "**");
				else if(len == 3)
					htmltext = htmltext.replace("%pass%", "***");
				else if(len == 4)
					htmltext = htmltext.replace("%pass%", "****");
				else
					htmltext = htmltext.replace("%pass%", "*****");
			}
		}
		else if(event.equalsIgnoreCase("32138-04.htm"))
		{
			st.rollAndGive(EnchantD, 1, 100);
			st.addExpAndSp(20123, 976);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		if(event.equalsIgnoreCase("32255-07.htm") || event.equalsIgnoreCase("32255-09.htm") || event.equalsIgnoreCase("32256-06a.htm") || event.equalsIgnoreCase("32256-07.htm") || event.equalsIgnoreCase("32256-08.htm") || event.equalsIgnoreCase("32256-09.htm") || event.equalsIgnoreCase("32257-06.htm"))
		{
			htmltext = Files.read("data/scripts/quests/_178_IconicTrinity/" + event, st.getPlayer().getVar("lang@"));
			htmltext = htmltext.replace("%player_name%", st.getPlayer().getName());
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
		int cond = st.getInt("cond");
		if(npcId == Kekropus)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.kamael || st.getPlayer().getLevel() < 17)
				{
					htmltext = "32138-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "32138-01.htm";
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					htmltext = "32138-02.htm";
				else if(cond == 4)
					htmltext = "32138-03.htm";
			}
		}
		else if(npcId == IconOfThePast && cond == 1)
			htmltext = "32255-01.htm";
		else if(npcId == IconOfThePresent && cond == 2)
			htmltext = "32256-01.htm";
		else if(npcId == IconOfTheFuture && cond == 3)
			htmltext = "32257-01.htm";
		return htmltext;
	}
}
