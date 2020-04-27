package quests._032_AnObviousLie;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _032_AnObviousLie extends Quest
{
	//NPC
	private final static int MAXIMILIAN = 30120;
	private final static int GENTLER = 30094;
	private final static int MIKI_THE_CAT = 31706;
	//MOBS
	private final static int ALLIGATOR = 20135;
	//CHANCE FOR DROP
	private final static int CHANCE_FOR_DROP = 30;
	//ITEMS
	private final static int MAP = 7165;
	private final static int MEDICINAL_HERB = 7166;
	private final static int SPIRIT_ORES = 3031;
	private final static int THREAD = 1868;
	private final static int SUEDE = 1866;
	//REWARDS
	private final static int RACCOON_EAR = 7680;
	private final static int CAT_EAR = 6843;
	private final static int RABBIT_EAR = 7683;

	public _032_AnObviousLie()
	{
		super(32, "_032_AnObviousLie", "An Obvious Lie");

		addStartNpc(MAXIMILIAN);
		addTalkId(MAXIMILIAN);
		addTalkId(GENTLER);
		addTalkId(MIKI_THE_CAT);

		addKillId(ALLIGATOR);

		addQuestItem(MEDICINAL_HERB);
		addQuestItem(MAP);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30120-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30094-1.htm"))
		{
			st.giveItems(MAP, 1);
			st.set("cond", "2");
		}
		else if(event.equals("31706-1.htm"))
		{
			st.takeItems(MAP, 1);
			st.set("cond", "3");
		}
		else if(event.equals("30094-4.htm"))
		{
			if(st.getQuestItemsCount(MEDICINAL_HERB) > 19)
			{
				st.takeItems(MEDICINAL_HERB, 20);
				st.set("cond", "5");
			}
			else
			{
				htmltext = "You don't have enough materials";
				st.set("cond", "3");
			}
		}
		else if(event.equals("30094-7.htm"))
		{
			if(st.getQuestItemsCount(SPIRIT_ORES) >= 500)
			{
				st.takeItems(SPIRIT_ORES, 500);
				st.set("cond", "6");
			}
			else
				htmltext = "You don't have enough materials";
		}
		else if(event.equals("31706-4.htm"))
			st.set("cond", "7");
		else if(event.equals("30094-10.htm"))
			st.set("cond", "8");
		else if(event.equals("30094-13.htm"))
		{
			if(st.getQuestItemsCount(THREAD) < 1000 || st.getQuestItemsCount(SUEDE) < 500)
				htmltext = "You don't have enough materials";
		}
		else if(event.equalsIgnoreCase("cat") || event.equalsIgnoreCase("racoon") || event.equalsIgnoreCase("rabbit"))
			if(st.getInt("cond") == 8 && st.getQuestItemsCount(THREAD) >= 1000 && st.getQuestItemsCount(SUEDE) >= 500)
			{
				st.takeItems(THREAD, 1000);
				st.takeItems(SUEDE, 500);
				if(event.equalsIgnoreCase("cat"))
					st.giveItems(CAT_EAR, 1);
				else if(event.equalsIgnoreCase("racoon"))
					st.giveItems(RACCOON_EAR, 1);
				else if(event.equalsIgnoreCase("rabbit"))
					st.giveItems(RABBIT_EAR, 1);
				st.unset("cond");
				st.playSound(SOUND_FINISH);
				htmltext = "30094-14.htm";
				st.exitCurrentQuest(false);
			}
			else
				htmltext = "You don't have enough materials";
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
		if(npcId == MAXIMILIAN)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 45)
					htmltext = "30120-0.htm";
				else
				{
					htmltext = "30120-0a.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "30120-2.htm";
		if(npcId == GENTLER)
			if(cond == 1)
				htmltext = "30094-0.htm";
			else if(cond == 2)
				htmltext = "30094-2.htm";
			else if(cond == 3)
				htmltext = "30094-forgot.htm";
			else if(cond == 4)
				htmltext = "30094-3.htm";
			else if(cond == 5 && st.getQuestItemsCount(SPIRIT_ORES) < 500)
				htmltext = "30094-5.htm";
			else if(cond == 5 && st.getQuestItemsCount(SPIRIT_ORES) >= 500)
				htmltext = "30094-6.htm";
			else if(cond == 6)
				htmltext = "30094-8.htm";
			else if(cond == 7)
				htmltext = "30094-9.htm";
			else if(cond == 8 && (st.getQuestItemsCount(THREAD) < 1000 || st.getQuestItemsCount(SUEDE) < 500))
				htmltext = "30094-11.htm";
			else if(cond == 8 && st.getQuestItemsCount(THREAD) >= 1000 && st.getQuestItemsCount(SUEDE) >= 500)
				htmltext = "30094-12.htm";
		if(npcId == MIKI_THE_CAT)
			if(cond == 2)
				htmltext = "31706-0.htm";
			else if(cond == 3)
				htmltext = "31706-2.htm";
			else if(cond == 6)
				htmltext = "31706-3.htm";
			else if(cond == 7)
				htmltext = "31706-5.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 3 && st.rollAndGiveLimited(MEDICINAL_HERB, 1, CHANCE_FOR_DROP, 20))
		{
			if(st.getQuestItemsCount(MEDICINAL_HERB) >= 20)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else
			{
				st.playSound(SOUND_ITEMGET);
			}
		}
	}
}