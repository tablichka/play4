package quests._347_GoGetTheCalculator;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _347_GoGetTheCalculator extends Quest
{
	//npc
	public final int BRUNON = 30526;
	public final int SILVERA = 30527;
	public final int SPIRON = 30532;
	public final int BALANKI = 30533;
	//mob
	public final int GEMSTONE_BEAST = 20540;
	//quest items
	public final int GEMSTONE_BEAST_CRYSTAL = 4286;
	public final int ADENA = 57;
	public final int CALCULATOR_Q = 4285;
	public final int CALCULATOR = 4393;

	public _347_GoGetTheCalculator()
	{
		super(347, "_347_GoGetTheCalculator", "Go Get The Calculator");

		addStartNpc(BRUNON);

		addTalkId(SILVERA);
		addTalkId(SPIRON);
		addTalkId(BALANKI);

		addKillId(GEMSTONE_BEAST);

		addQuestItem(GEMSTONE_BEAST_CRYSTAL);
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
			htmltext = BRUNON + "-02.htm";
		}
		else if(event.equalsIgnoreCase("30533_1"))
			if(st.getQuestItemsCount(ADENA) > 100)
			{
				st.takeItems(ADENA, 100);
				if(st.getInt("cond") == 1)
					st.set("cond", "2");
				else
					st.set("cond", "4");
				st.setState(STARTED);
				htmltext = BALANKI + "-02.htm";
			}
			else
				htmltext = BALANKI + "-03.htm";
		else if(event.equalsIgnoreCase("30532_1"))
		{
			htmltext = SPIRON + "-02a.htm";
			if(st.getInt("cond") == 1)
				st.set("cond", "3");
			else
				st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30532_2"))
			htmltext = SPIRON + "-02b.htm";
		else if(event.equalsIgnoreCase("30532_3"))
			htmltext = SPIRON + "-02c.htm";
		else if(event.equalsIgnoreCase("30526_1"))
		{
			st.takeItems(CALCULATOR_Q, 1);
			st.giveItems(CALCULATOR, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			htmltext = BRUNON + "-05.htm";
		}
		else if(event.equalsIgnoreCase("30526_2"))
		{
			st.takeItems(CALCULATOR_Q, 1);
			st.rollAndGive(ADENA, 1000, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			htmltext = BRUNON + "-06.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		String htmltext = "noquest";
		if(npcId == BRUNON && st.isCreated() && st.getPlayer().getLevel() >= 12)
			htmltext = BRUNON + "-01.htm";
		else if(npcId == BRUNON && cond > 0 && st.getQuestItemsCount(CALCULATOR_Q) == 0)
			htmltext = BRUNON + "-03.htm";
		else if(npcId == BRUNON && cond == 6 && st.getQuestItemsCount(CALCULATOR_Q) >= 1)
			htmltext = BRUNON + "-04.htm";
		else if(npcId == BALANKI && (cond == 1 || cond == 3))
			htmltext = BALANKI + "-01.htm";
		else if(npcId == SPIRON && (cond == 1 || cond == 2))
			htmltext = SPIRON + "-01.htm";
		else if(npcId == SILVERA && cond == 4)
		{
			st.set("cond", "5");
			st.setState(STARTED);
			htmltext = SILVERA + "-01.htm";
		}
		else if(npcId == SILVERA && cond == 5 && st.getQuestItemsCount(GEMSTONE_BEAST_CRYSTAL) < 10)
			htmltext = SILVERA + "-02.htm";
		else if(npcId == SILVERA && cond == 5 && st.getQuestItemsCount(GEMSTONE_BEAST_CRYSTAL) >= 10)
		{
			htmltext = SILVERA + "-03.htm";
			st.takeItems(GEMSTONE_BEAST_CRYSTAL, 10);
			st.giveItems(CALCULATOR_Q, 1);
			st.playSound(SOUND_ITEMGET);
			st.set("cond", "6");
			st.setState(STARTED);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == GEMSTONE_BEAST && st.getInt("cond") == 5 && Rnd.chance(50) && st.getQuestItemsCount(GEMSTONE_BEAST_CRYSTAL) < 10)
		{
			st.giveItems(GEMSTONE_BEAST_CRYSTAL, 1);
			if(st.getQuestItemsCount(GEMSTONE_BEAST_CRYSTAL) >= 10)
				st.playSound(SOUND_MIDDLE);
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}