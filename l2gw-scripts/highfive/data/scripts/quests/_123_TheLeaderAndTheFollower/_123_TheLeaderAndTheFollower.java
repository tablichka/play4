package quests._123_TheLeaderAndTheFollower;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _123_TheLeaderAndTheFollower extends Quest
{
	int NEWYEAR = 31961;
	int BRUIN_LIZARDMEN = 27321;
	int BRUIN_BLOOD = 8549;
	int PICOT_ARANEID = 27322;
	int PICOT_LEG = 8550;
	int D_CRY = 1458;
	int D_CRY_COUNT_HEAVY = 922;
	int D_CRY_COUNT_LIGHT_MAGIC = 771;

	int CLAN_OATH_HELM = 7850;

	int CLAN_OATH_ARMOR = 7851;
	int CLAN_OATH_GAUNTLETS = 7852;
	int CLAN_OATH_SABATON = 7853;

	int CLAN_OATH_BRIGANDINE = 7854;
	int CLAN_OATH_LEATHER_GLOVES = 7855;
	int CLAN_OATH_BOOTS = 7856;

	int CLAN_OATH_AKETON = 7857;
	int CLAN_OATH_PADDED_GLOVES = 7858;
	int CLAN_OATH_SANDALS = 7859;

	public _123_TheLeaderAndTheFollower()
	{
		super(123, "_123_TheLeaderAndTheFollower", "The Leader && The Follower");

		addStartNpc(NEWYEAR);
		addTalkId(NEWYEAR);

		addKillId(BRUIN_LIZARDMEN);
		addKillId(PICOT_ARANEID);

		addQuestItem(BRUIN_BLOOD, PICOT_LEG);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31961-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31961-05.htm"))
		{
			st.set("choose", "1");
			st.set("cond", "3");
		}
		else if(event.equals("31961-06.htm"))
		{
			st.set("choose", "2");
			st.set("cond", "4");
		}
		else if(event.equals("31961-07.htm"))
		{
			st.set("choose", "3");
			st.set("cond", "5");
		}
		else if(event.equals("31961-08.htm"))
		{
			int choose = st.getInt("choose");
			int D_CRY_COUNT = D_CRY_COUNT_LIGHT_MAGIC;
			if(choose == 1)
				D_CRY_COUNT = D_CRY_COUNT_HEAVY;
			if(st.getQuestItemsCount(D_CRY) >= D_CRY_COUNT)
			{
				st.set("cond", "7");
				st.takeItems(D_CRY, D_CRY_COUNT);
			}
			else
				htmltext = "<html><body>771 D Cry!</body></html>";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 19)
			{
				htmltext = "<html><body>Your level is too low</body></html>";
				return htmltext;
			}
			else if(st.getPlayer().getClanId() == 0)
			{
				htmltext = "<html><body>You are not in clan</body></html>";
				return htmltext;
			}
			else if(st.getPlayer().getSponsor() == 0)
			{
				htmltext = "<html><body>You have no sponsor</body></html>";
				return htmltext;
			}
			else
				htmltext = "31961-00.htm";
		}
		else if(cond == 1)
			htmltext = "<html><body>Bring me 10 Bruin Lizardmen blood.</body></html>";
		else if(cond == 2)
		{
			st.takeItems(BRUIN_BLOOD, 10);
			htmltext = "31961-04.htm";
		}
		else if(cond == 3)
			htmltext = "31961-05.htm";
		else if(cond == 4)
			htmltext = "31961-06.htm";
		else if(cond == 5)
			htmltext = "31961-07.htm";
		else if(cond == 7)
			htmltext = "<html><body>Bring me 8 Picot Legs.</body></html>";
		else if(cond == 8)
		{
			st.takeItems(PICOT_LEG, 8);
			int choose = st.getInt("choose");
			st.giveItems(CLAN_OATH_HELM, 1);
			if(choose == 1)
			{
				st.giveItems(CLAN_OATH_ARMOR, 1);
				st.giveItems(CLAN_OATH_GAUNTLETS, 1);
				st.giveItems(CLAN_OATH_SABATON, 1);
			}
			else if(choose == 2)
			{
				st.giveItems(CLAN_OATH_BRIGANDINE, 1);
				st.giveItems(CLAN_OATH_LEATHER_GLOVES, 1);
				st.giveItems(CLAN_OATH_BOOTS, 1);
			}
			else if(choose == 3)
			{
				st.giveItems(CLAN_OATH_AKETON, 1);
				st.giveItems(CLAN_OATH_PADDED_GLOVES, 1);
				st.giveItems(CLAN_OATH_SANDALS, 1);
			}
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			htmltext = "<html><body>OK!</body></html>";
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == BRUIN_LIZARDMEN && cond == 1 && st.rollAndGiveLimited(BRUIN_BLOOD, 1, 50, 10))
		{
			if(st.getQuestItemsCount(BRUIN_BLOOD) == 10)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == PICOT_ARANEID && cond == 7 && st.rollAndGiveLimited(PICOT_LEG, 1, 50, 8))
		{
			if(st.getQuestItemsCount(PICOT_LEG) == 8)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}