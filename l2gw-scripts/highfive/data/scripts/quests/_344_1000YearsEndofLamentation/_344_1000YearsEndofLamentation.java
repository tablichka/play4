package quests._344_1000YearsEndofLamentation;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _344_1000YearsEndofLamentation extends Quest
{
	// Rewards
	private static final int ADENA = 57;

	// Quest Items
	private static final int ARTICLES_DEAD_HEROES = 4269;
	private static final int OLD_KEY = 4270;
	private static final int OLD_HILT = 4271;
	private static final int OLD_TOTEM = 4272;
	private static final int CRUCIFIX = 4273;

	// Chances
	private static final int CHANCE = 36;
	private static final int SPECIAL = 1000;

	// NPCs
	private static final int GILMORE = 30754;
	private static final int RODEMAI = 30756;
	private static final int ORVEN = 30857;
	private static final int KAIEN = 30623;
	private static final int GARVARENTZ = 30704;

	public _344_1000YearsEndofLamentation()
	{
		super(344, "_344_1000YearsEndofLamentation", "1000 Years, End of Lamentation"); // Party true
		addStartNpc(GILMORE);

		addTalkId(RODEMAI);
		addTalkId(ORVEN);
		addTalkId(GARVARENTZ);
		addTalkId(KAIEN);

		for(int mob = 20236; mob < 20241; mob++)
			addKillId(mob);

		addQuestItem(ARTICLES_DEAD_HEROES, OLD_KEY, OLD_HILT, OLD_TOTEM, CRUCIFIX);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		long amount = st.getQuestItemsCount(ARTICLES_DEAD_HEROES);
		int cond = st.getInt("cond");
		int level = st.getPlayer().getLevel();
		if(event.equalsIgnoreCase("30754-04.htm"))
		{
			if(level >= 48 && st.isCreated())
			{
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "noquest";
				st.exitCurrentQuest(true);
			}
		}
		else if(event.equalsIgnoreCase("30754-08.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		else if(event.equalsIgnoreCase("30754-06.htm") && cond == 1)
		{
			if(amount == 0)
				htmltext = "30754-06a.htm";
			else
			{
				if(Rnd.get(SPECIAL) >= amount)
					st.giveItems(ADENA, amount * 60);
				else
				{
					htmltext = "30754-10.htm";
					st.set("ok", "1");
					st.set("amount", str(amount));
				}
				st.takeItems(ARTICLES_DEAD_HEROES, -1);
			}
		}
		else if(event.equalsIgnoreCase("30754-11.htm") && cond == 1)
			if(st.getInt("ok") != 1)
				htmltext = "noquest";
			else
			{
				int random = Rnd.get(100);
				st.set("cond", "2");
				st.unset("ok");
				if(random < 25)
				{
					htmltext = "30754-12.htm";
					st.giveItems(OLD_KEY, 1);
				}
				else if(random < 50)
				{
					htmltext = "30754-13.htm";
					st.giveItems(OLD_HILT, 1);
				}
				else if(random < 75)
				{
					htmltext = "30754-14.htm";
					st.giveItems(OLD_TOTEM, 1);
				}
				else
					st.giveItems(CRUCIFIX, 1);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		long amount = st.getQuestItemsCount(ARTICLES_DEAD_HEROES);
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 48)
				htmltext = "30754-02.htm";
			else
			{
				htmltext = "30754-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == GILMORE && cond == 1)
		{
			if(amount > 0)
				htmltext = "30754-05.htm";
			else
				htmltext = "30754-09.htm";
		}
		else if(cond == 2)
		{
			if(npcId == GILMORE)
				htmltext = "30754-15.htm";
			else if(rewards(st, npcId))
			{
				htmltext = str(npcId) + "-01.htm";
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(cond == 3)
			if(npcId == GILMORE)
			{
				int amt = st.getInt("amount");
				int mission = st.getInt("mission");
				int bonus = 0;
				if(mission == 1)
					bonus = 1500;
				else if(mission == 2)
					st.giveItems(4044, 1);
				else if(mission == 3)
					st.giveItems(4043, 1);
				else if(mission == 4)
					st.giveItems(4042, 1);
				if(amt > 0)
				{
					st.unset("amount");
					st.rollAndGive(ADENA, amt * 50 + bonus, 100);
				}
				htmltext = "30754-16.htm";
				st.set("cond", "1");
				st.unset("mission");
			}
			else
				htmltext = str(npcId) + "-02.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null && st.getInt("cond") == 1)
			st.rollAndGive(ARTICLES_DEAD_HEROES, 1, CHANCE + (npc.getNpcId() - 20234) * 2);
	}

	private boolean rewards(QuestState st, int npcId)
	{
		boolean state = false;
		int chance = Rnd.get(100);
		if(npcId == ORVEN && st.getQuestItemsCount(CRUCIFIX) > 0)
		{
			st.set("mission", "1");
			st.takeItems(CRUCIFIX, -1);
			state = true;
			if(chance < 50)
				st.rollAndGive(1875, 19, 100);
			else if(chance < 70)
				st.rollAndGive(952, 5, 100);
			else
				st.giveItems(2437, 1);
		}
		else if(npcId == GARVARENTZ && st.getQuestItemsCount(OLD_TOTEM) > 0)
		{
			st.set("mission", "2");
			st.takeItems(OLD_TOTEM, -1);
			state = true;
			if(chance < 45)
				st.rollAndGive(1882, 70, 100);
			else if(chance < 95)
				st.rollAndGive(1881, 50, 100);
			else
				st.giveItems(191, 1);
		}
		else if(npcId == KAIEN && st.getQuestItemsCount(OLD_HILT) > 0)
		{
			st.set("mission", "3");
			st.takeItems(OLD_HILT, -1);
			state = true;
			if(chance < 50)
				st.rollAndGive(1874, 25, 100);
			else if(chance < 75)
				st.rollAndGive(1887, 10, 100);
			else if(chance < 99)
				st.rollAndGive(951, 1, 100);
			else
				st.giveItems(133, 1);
		}
		else if(npcId == RODEMAI && st.getQuestItemsCount(OLD_KEY) > 0)
		{
			st.set("mission", "4");
			st.takeItems(OLD_KEY, -1);
			state = true;
			if(chance < 40)
				st.rollAndGive(1879, 55, 100);
			else if(chance < 90)
				st.rollAndGive(951, 1, 100);
			else
				st.giveItems(885, 1);
		}
		return state;
	}
}