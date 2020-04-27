package quests._065_PathToSoulBreaker;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;

/**
 * Квест на вторую профессию Path To Soul Breaker
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _065_PathToSoulBreaker extends Quest
{
	private static final int Vitus = 32213;
	private static final int Kekropus = 32138;
	private static final int Casca = 32139;
	private static final int Holst = 32199;
	private static final int Harlan = 30074;
	private static final int Jacob = 30073;
	private static final int Lucas = 30071;
	private static final int Xaber = 30075;
	private static final int Liam = 30076; //(listto)
	private static final int Vesa = 30123;
	private static final int Zerom = 30124;
	private static final int Felton = 30879;
	private static final int Meldina = 32214;
	private static final int Katenar = 32235;
	private static final int Box = 32243;
	private static final int Guardian_Angel = 27332;
	private static final int Wyrm = 20176;

	private static final int DD = 7562;
	private static final int Sealed_Doc = 9803;
	private static final int Wyrm_Heart = 9804;
	private static final int Kekropus_Rec = 9805;
	private static final int SB_Certificate = 9806;

	public _065_PathToSoulBreaker()
	{
		super(65, "_065_PathToSoulBreaker", "Path To Soul Breaker");

		addStartNpc(Vitus);

		addTalkId(Vitus);
		addTalkId(Kekropus);
		addTalkId(Casca);
		addTalkId(Holst);
		addTalkId(Harlan);
		addTalkId(Lucas);
		addTalkId(Jacob);
		addTalkId(Xaber);
		addTalkId(Liam);
		addTalkId(Vesa);
		addTalkId(Zerom);
		addTalkId(Felton);
		addTalkId(Meldina);
		addTalkId(Katenar);
		addTalkId(Box);
		addKillId(Guardian_Angel);
		addKillId(Wyrm);
		addQuestItem(new int[]{Sealed_Doc, Wyrm_Heart, Kekropus_Rec});
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32213-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			if(!st.getPlayer().getVarB("dd"))
			{
				st.giveItems(DD, 47);
				st.getPlayer().setVar("dd", "1");
			}
			st.playSound(SOUND_ACCEPT);
		}
		if(event.equalsIgnoreCase("32138-03.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32139-01.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32139-03.htm"))
		{
			st.set("cond", "4");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32199-01.htm"))
		{
			st.set("cond", "5");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("30071-01.htm"))
		{
			st.set("cond", "8");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32214-01.htm"))
		{
			st.set("cond", "11");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("30879-02.htm"))
		{
			st.set("cond", "12");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32235-01.htm"))
		{
			QuestTimer timer = st.getQuestTimer("Katenar_Fail");
			if(timer != null)
				timer.cancel();
			st.giveItems(Sealed_Doc, 1);
			st.set("cond", "13");
			st.unset("id");
			st.setState(STARTED);
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(Katenar);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		if(event.equalsIgnoreCase("32139-06.htm"))
		{
			st.takeItems(Sealed_Doc, -1);
			st.set("cond", "14");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32138-05.htm"))
		{
			st.set("cond", "15");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("32138-09.htm"))
		{
			st.takeItems(Wyrm_Heart, -1);
			st.giveItems(Kekropus_Rec, 1);
			st.set("cond", "17");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("Guardian_Angel_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(Guardian_Angel);
			if(isQuest != null)
				isQuest.deleteMe();
			htmltext = null;
		}
		if(event.equalsIgnoreCase("Katenar_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(Katenar);
			if(isQuest != null)
				isQuest.deleteMe();
			htmltext = null;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Vitus)
		{
			if(st.getQuestItemsCount(SB_Certificate) > 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
				if(st.getPlayer().getClassId().getId() == 0x7e || st.getPlayer().getClassId().getId() == 0x7d)
				{
					if(st.getPlayer().getLevel() >= 39)
						htmltext = "32213.htm";
					else
					{
						htmltext = "nolevel.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "32213-000.htm";
					st.exitCurrentQuest(true);
				}
			else if(cond == 17)
			{
				htmltext = "32213-03.htm";
				st.takeItems(Kekropus_Rec, -1);
				if(!st.getPlayer().getVarB("prof2.1"))
				{
					st.addExpAndSp(196875, 13510);
					st.rollAndGive(57, 35597, 100);
					st.getPlayer().setVar("prof2.1", "1");
				}
				st.giveItems(SB_Certificate, 1);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == Kekropus)
		{
			if(cond == 1)
				htmltext = "32138.htm";
			if(cond == 14)
				htmltext = "32138-04.htm";
			if(cond == 16)
				htmltext = "32138-06.htm";
		}
		else if(npcId == Casca)
		{
			if(cond == 2)
				htmltext = "32139.htm";
			if(cond == 3)
				htmltext = "32139-02.htm";
			if(cond == 13)
				htmltext = "32139-04.htm";
		}
		else if(npcId == Holst)
		{
			if(cond == 4)
				htmltext = "32199.htm";
			if(cond == 5)
			{
				st.set("cond", "6");
				htmltext = "32199-02.htm";
			}
		}
		else if(npcId == Harlan)
		{
			if(cond == 6)
				htmltext = "30074.htm";
		}
		else if(npcId == Jacob)
		{
			if(cond == 6)
			{
				htmltext = "30073.htm";
				st.set("cond", "7");
				st.setState(STARTED);
			}
		}
		else if(npcId == Lucas)
		{
			if(cond == 7)
				htmltext = "30071.htm";
		}
		else if(npcId == Xaber)
		{
			if(cond == 8)
				htmltext = "30075.htm";
		}
		else if(npcId == Liam)
		{
			if(cond == 8)
			{
				htmltext = "30076.htm";
				st.set("cond", "9");
				st.setState(STARTED);
			}
		}
		else if(npcId == Zerom)
		{
			if(cond == 9)
				htmltext = "30124.htm";
		}
		else if(npcId == Vesa)
		{
			if(cond == 9)
			{
				htmltext = "30123.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
		}
		else if(npcId == Meldina)
		{
			if(cond == 10)
				htmltext = "32214.htm";
		}
		else if(npcId == Box)
		{
			if(cond == 12)
			{
				htmltext = "32243-01.htm";
				for(L2Player cha : L2World.getAroundPlayers(st.getPlayer()))
					if(cha.getRace() == Race.kamael)
					{
						htmltext = "32243-02.htm";
						break;
					}
				if(!htmltext.equals("32243-02.htm"))
				{
					L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(Guardian_Angel);
					if(isQuest != null)
						isQuest.deleteMe();

					st.set("id", "0");
					st.getPcSpawn().addSpawn(Guardian_Angel);
					st.startQuestTimer("Guardian_Angel_Fail", 120000);
					// Натравим ангела
					L2NpcInstance angel = L2ObjectsStorage.getByNpcId(Guardian_Angel);
					if(angel != null)
						angel.addDamageHate(st.getPlayer(), 0, 1);
				}
			}
			else
				htmltext = "32243.htm";
		}
		else if(npcId == Felton)
		{
			if(cond == 11)
				htmltext = "30879.htm";
			if(cond == 12)
				htmltext = "30879.htm";
		}
		else if(npcId == Katenar && st.getInt("id") == 1)
			if(cond == 12)
				htmltext = "32235.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Guardian_Angel)
		{
			QuestTimer timer = st.getQuestTimer("Guardian_Angel_Fail");
			if(timer != null)
				timer.cancel();
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(Guardian_Angel);
			if(isQuest != null)
				isQuest.deleteMe();

			if(cond == 12)
			{
				for(L2Player cha : L2World.getAroundPlayers(st.getPlayer()))
					if(cha.getRace() == Race.kamael)
						return;
				isQuest = L2ObjectsStorage.getByNpcId(Katenar);
				if(isQuest != null)
					isQuest.deleteMe();

				st.set("id", "1");
				st.getPcSpawn().addSpawn(Katenar);
				st.startQuestTimer("Katenar_Fail", 120000);
				L2NpcInstance katenar = L2ObjectsStorage.getByNpcId(Katenar);
				if(katenar != null)
					Functions.npcSay(katenar, Say2C.ALL, "I am late!");
			}
		}
		if(cond == 15 && npcId == Wyrm)
		{
			if(st.rollAndGiveLimited(Wyrm_Heart, 1, 10, 10))
			{
				if(st.getQuestItemsCount(Wyrm_Heart) == 10)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "16");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}