package quests._644_GraveRobberAnnihilation;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.L2Player;

public class _644_GraveRobberAnnihilation extends Quest
{
	//NPC
	private static final int KARUDA = 32017;
	//QuestItem
	private static int ORC_GOODS = 8088;

	public _644_GraveRobberAnnihilation()
	{
		super(644, "_644_GraveRobberAnnihilation", "Grave Robber Annihilation"); // Party true
		addStartNpc(KARUDA);

		addKillId(22003);
		addKillId(22004);
		addKillId(22005);
		addKillId(22006);
		addKillId(22008);

		addQuestItem(ORC_GOODS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32017-03.htm"))
		{
			st.takeItems(ORC_GOODS, -1);
			if(st.getPlayer().getLevel() < 20)
			{
				htmltext = "32017-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		if(st.getInt("cond") == 2 && st.getQuestItemsCount(ORC_GOODS) >= 120)
		{
			if(event.equalsIgnoreCase("varn"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1865, 30, 100);
				htmltext = null;
			}
			else if(event.equalsIgnoreCase("an_s"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1867, 40, 100);
				htmltext = null;
			}
			else if(event.equalsIgnoreCase("an_b"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1872, 40, 100);
				htmltext = null;
			}
			else if(event.equalsIgnoreCase("char"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1871, 30, 100);
				htmltext = null;
			}
			else if(event.equalsIgnoreCase("coal"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1870, 30, 100);
				htmltext = null;
			}
			else if(event.equalsIgnoreCase("i_o"))
			{
				st.takeItems(ORC_GOODS, -1);
				st.rollAndGive(1869, 30, 100);
				htmltext = null;
			}
			if(htmltext == null)
			{
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
			htmltext = "32017-01.htm";
		else if(cond == 1)
			htmltext = "32017-04.htm";
		else if(cond == 2)
			if(st.getQuestItemsCount(ORC_GOODS) >= 120)
				htmltext = "32017-05.htm";
			else
			{
				st.set("cond", "1");
				htmltext = "32017-04.htm";
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGiveLimited(ORC_GOODS, 1, 75, 120))
		{
			if(st.getQuestItemsCount(ORC_GOODS) == 120)
			{
				st.playSound(SOUND_MIDDLE);
				st.setCond(2);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}
