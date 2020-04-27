package quests._226_TestOfHealer;

import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

public class _226_TestOfHealer extends Quest
{
	private static final int Bandellos = 30473;
	private static final int Perrin = 30428;
	private static final int OrphanGirl = 30659;
	private static final int Allana = 30424;
	private static final int FatherGupu = 30658;
	private static final int Windy = 30660;
	private static final int Sorius = 30327;
	private static final int Daurin = 30674;
	private static final int Piper = 30662;
	private static final int Slein = 30663;
	private static final int Kein = 30664;
	private static final int MysteryDarkElf = 30661;
	private static final int Kristina = 30665;

	private static final int TATOMA = 27134;

	private static final int REPORT_OF_PERRIN_ID = 2810;
	private static final int CRISTINAS_LETTER_ID = 2811;
	private static final int PICTURE_OF_WINDY_ID = 2812;
	private static final int GOLDEN_STATUE_ID = 2813;
	private static final int WINDYS_PEBBLES_ID = 2814;
	private static final int ORDER_OF_SORIUS_ID = 2815;
	private static final int SECRET_LETTER1_ID = 2816;
	private static final int SECRET_LETTER2_ID = 2817;
	private static final int SECRET_LETTER3_ID = 2818;
	private static final int SECRET_LETTER4_ID = 2819;
	private static final int MARK_OF_HEALER_ID = 2820;

	private static HashMap<Integer, Integer[]> DROPLIST = new HashMap<Integer, Integer[]>();

	static
	{
		DROPLIST.put(TATOMA, new Integer[]{2, 3, 0});
		DROPLIST.put(27123, new Integer[]{11, 12, SECRET_LETTER1_ID});
		DROPLIST.put(27124, new Integer[]{14, 15, SECRET_LETTER2_ID});
		DROPLIST.put(27125, new Integer[]{16, 17, SECRET_LETTER3_ID});
		DROPLIST.put(27127, new Integer[]{18, 19, SECRET_LETTER4_ID});
	}

	public _226_TestOfHealer()
	{
		super(226, "_226_TestOfHealer", "Test Of Healer");

		addStartNpc(Bandellos);

		addTalkId(Sorius, Allana, Perrin, FatherGupu, OrphanGirl, Windy, MysteryDarkElf, Piper, Slein, Kein, Kristina, Daurin);

		addKillId(20150, 27123, 27124, 27125, 27127, 27134);

		addQuestItem(REPORT_OF_PERRIN_ID, CRISTINAS_LETTER_ID, PICTURE_OF_WINDY_ID, GOLDEN_STATUE_ID, //
				WINDYS_PEBBLES_ID, ORDER_OF_SORIUS_ID, SECRET_LETTER1_ID, SECRET_LETTER2_ID, SECRET_LETTER3_ID, SECRET_LETTER4_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			htmltext = "30473-04.htm";
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(7562, 45);
				st.getPlayer().setVar("dd3", "1");
			}
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(REPORT_OF_PERRIN_ID, 1);
		}
		else if(event.equalsIgnoreCase("30473_1"))
			htmltext = "30473-08.htm";
		else if(event.equalsIgnoreCase("30473_2"))
		{
			htmltext = "30473-09.htm";
			st.takeItems(GOLDEN_STATUE_ID, -1);
			st.giveItems(MARK_OF_HEALER_ID, 1);
			if(!st.getPlayer().getVarB("q226"))
			{
				st.addExpAndSp(738283, 50662);
				st.rollAndGive(57, 233490, 100);
				st.getPlayer().setVar("q226", "1");
			}
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("30428_1"))
		{
			htmltext = "30428-02.htm";
			st.setCond(2);
			st.setState(STARTED);
			st.getPcSpawn().addSpawn(TATOMA, -93254, 147559, -2679);
		}
		else if(event.equalsIgnoreCase("30658_1"))
			if(st.getQuestItemsCount(57) >= 100000)
			{
				htmltext = "30658-02.htm";
				st.takeItems(57, 100000);
				st.giveItems(PICTURE_OF_WINDY_ID, 1);
				st.setCond(7);
				st.setState(STARTED);
			}
			else
				htmltext = "30658-05.htm";
		else if(event.equalsIgnoreCase("30658_2"))
		{
			st.setCond(6);
			st.setState(STARTED);
			htmltext = "30658-03.htm";
		}
		else if(event.equalsIgnoreCase("30660-03.htm"))
		{
			st.takeItems(PICTURE_OF_WINDY_ID, 1);
			st.giveItems(WINDYS_PEBBLES_ID, 1);
			st.setCond(8);
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30674_1"))
		{
			htmltext = "30674-02.htm";
			st.setCond(11);
			st.setState(STARTED);

			st.takeItems(ORDER_OF_SORIUS_ID, 1);
			st.getPcSpawn().addSpawn(27122);
			st.getPcSpawn().addSpawn(27122);
			st.getPcSpawn().addSpawn(27123);
			st.playSound(SOUND_BEFORE_BATTLE);
		}
		else if(event.equalsIgnoreCase("30665_1"))
		{
			htmltext = "30665-02.htm";
			st.takeItems(SECRET_LETTER1_ID, 1);
			st.takeItems(SECRET_LETTER2_ID, 1);
			st.takeItems(SECRET_LETTER3_ID, 1);
			st.takeItems(SECRET_LETTER4_ID, 1);
			st.giveItems(CRISTINAS_LETTER_ID, 1);
			st.setCond(22);
			st.setState(STARTED);

		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted() || st.getQuestItemsCount(MARK_OF_HEALER_ID) > 0)
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Bandellos)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getClassId() == ClassId.knight || st.getPlayer().getClassId() == ClassId.cleric || st.getPlayer().getClassId() == ClassId.oracle || st.getPlayer().getClassId() == ClassId.elvenKnight)
					if(st.getPlayer().getLevel() >= 39)
						htmltext = "30473-03.htm";
					else
						htmltext = "30473-01.htm";
				else
				{
					htmltext = "30473-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 23)
			{
				if(st.getQuestItemsCount(GOLDEN_STATUE_ID) == 0)
				{
					htmltext = "30473-06.htm";
					st.giveItems(MARK_OF_HEALER_ID, 1);
					htmltext = "30690-08.htm";
					if(!st.getPlayer().getVarB("q226"))
					{
						st.addExpAndSp(738283, 50662);
						st.rollAndGive(57, 133490, 100);
						st.getPlayer().setVar("q226", "1");
					}
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
				}
				else
					htmltext = "30473-07.htm";
			}
			else
				htmltext = "30473-05.htm";
		}
		else if(npcId == Perrin)
		{
			if(cond == 1)
				htmltext = "30428-01.htm";
			else if(cond == 3)
			{
				htmltext = "30428-03.htm";
				st.takeItems(REPORT_OF_PERRIN_ID, 1);
				st.setCond(4);
				st.setState(STARTED);
			}
			else if(cond != 2)
				htmltext = "30428-04.htm";
		}
		else if(npcId == OrphanGirl)
		{
			int n = Rnd.get(5);
			if(n == 0)
				htmltext = "30659-01.htm";
			else if(n == 1)
				htmltext = "30659-02.htm";
			else if(n == 2)
				htmltext = "30659-03.htm";
			else if(n == 3)
				htmltext = "30659-04.htm";
			else if(n == 4)
				htmltext = "30659-05.htm";
		}
		else if(npcId == Allana)
		{
			if(cond == 4)
			{
				htmltext = "30424-01.htm";
				st.setCond(5);
				st.setState(STARTED);
			}
			else
				htmltext = "30424-02.htm";
		}
		else if(npcId == FatherGupu)
		{
			if(cond == 5)
				htmltext = "30658-01.htm";
			else if(cond == 7)
				htmltext = "30658-04.htm";
			else if(cond == 8)
			{
				htmltext = "30658-06.htm";
				st.giveItems(GOLDEN_STATUE_ID, 1);
				st.takeItems(WINDYS_PEBBLES_ID, 1);
				st.setCond(9);
				st.setState(STARTED);
			}
			else if(cond == 6)
			{
				st.setCond(9);
				st.setState(STARTED);
				htmltext = "30658-07.htm";
			}
			else if(cond == 9)
				htmltext = "30658-07.htm";
		}
		else if(npcId == Windy)
		{
			if(cond == 7)
				htmltext = "30660-01.htm";
			else if(cond == 8)
				htmltext = "30660-04.htm";
		}
		else if(npcId == Sorius)
		{
			if(cond == 9)
			{
				htmltext = "30327-01.htm";
				st.giveItems(ORDER_OF_SORIUS_ID, 1);
				st.setCond(10);
				st.setState(STARTED);
			}
			else if(cond > 9 && cond < 22)
				htmltext = "30327-02.htm";
			else if(cond == 22)
			{
				htmltext = "30327-03.htm";
				st.takeItems(CRISTINAS_LETTER_ID, 1);
				st.setCond(23);
				st.setState(STARTED);
			}
		}
		else if(npcId == Daurin)
		{
			if(cond == 10 && st.getQuestItemsCount(ORDER_OF_SORIUS_ID) > 0)
				htmltext = "30674-01.htm";
			else if(cond == 12 && st.getQuestItemsCount(SECRET_LETTER1_ID) > 0)
			{
				htmltext = "30674-03.htm";
				st.setCond(13);
				st.setState(STARTED);
			}
		}
		else if(npcId == Piper || npcId == Slein || npcId == Kein)
		{
			if(cond == 13)
				htmltext = npcId + "-01.htm";
			else if(cond == 15)
				htmltext = npcId + "-02.htm";
			else if(cond == 20)
			{
				st.setCond(21);
				st.setState(STARTED);
				htmltext = npcId + "-03.htm";
			}
			else if(cond == 21)
				htmltext = npcId + "-04.htm";
		}
		else if(npcId == MysteryDarkElf)
		{
			if(cond == 13)
			{
				htmltext = "30661-01.htm";
				st.getPcSpawn().addSpawn(27124);
				st.getPcSpawn().addSpawn(27124);
				st.getPcSpawn().addSpawn(27124);
				st.playSound(SOUND_BEFORE_BATTLE);
				st.setCond(14);
				st.set("13spawn", String.valueOf(System.currentTimeMillis() / 1000));
				st.setState(STARTED);
			}
			else if(cond == 14 && st.getInt("13spawn") * 1000 > System.currentTimeMillis() + 300000)
			{
				st.setCond(13);
				st.setState(STARTED);
				return null;
			}
			else if(cond == 15)
			{
				htmltext = "30661-02.htm";
				st.getPcSpawn().addSpawn(27125);
				st.getPcSpawn().addSpawn(27125);
				st.getPcSpawn().addSpawn(27125);
				st.playSound(SOUND_BEFORE_BATTLE);
				st.setCond(16);
				st.set("15spawn", String.valueOf(System.currentTimeMillis() / 1000));
				st.setState(STARTED);
			}
			else if(cond == 16 && st.getInt("15spawn") * 1000 > System.currentTimeMillis() + 300000)
			{
				st.setCond(15);
				st.setState(STARTED);
				return null;
			}
			else if(cond == 17)
			{
				htmltext = "30661-03.htm";
				st.getPcSpawn().addSpawn(27126);
				st.getPcSpawn().addSpawn(27126);
				st.getPcSpawn().addSpawn(27127);
				st.playSound(SOUND_BEFORE_BATTLE);
				st.setCond(18);
				st.set("17spawn", String.valueOf(System.currentTimeMillis() / 1000));
				st.setState(STARTED);
			}
			else if(cond == 18 && st.getInt("17spawn") * 1000 > System.currentTimeMillis() + 300000)
			{
				st.setCond(17);
				st.setState(STARTED);
				return null;
			}
			else if(cond == 19)
			{
				htmltext = "30661-04.htm";
				st.setCond(20);
				st.setState(STARTED);
			}
		}
		else if(npcId == Kristina)
			if(cond == 20 || cond == 21)
				htmltext = "30665-01.htm";
			else
				htmltext = "30665-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		Integer[] d = DROPLIST.get(npc.getNpcId());
		if(st.getCond() == d[0] && (d[2] == 0 || st.getQuestItemsCount(d[2]) == 0))
		{
			if(d[2] != 0)
				st.giveItems(d[2], 1);
			st.setCond(d[1]);
			st.setState(STARTED);
		}
	}
}
