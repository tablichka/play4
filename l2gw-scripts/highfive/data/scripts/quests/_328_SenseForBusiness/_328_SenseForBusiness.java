package quests._328_SenseForBusiness;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _328_SenseForBusiness extends Quest
{
	//NPC
	private int SARIEN = 30436;
	//items
	private int MONSTER_EYE_CARCASS = 1347;
	private int MONSTER_EYE_LENS = 1366;
	private int BASILISK_GIZZARD = 1348;
	private int ADENA = 57;

	public _328_SenseForBusiness()
	{
		super(328, "_328_SenseForBusiness", "Sense For Business");

		addStartNpc(SARIEN);
		addKillId(20055);
		addKillId(20059);
		addKillId(20067);
		addKillId(20068);
		addKillId(20070);
		addKillId(20072);
		addQuestItem(MONSTER_EYE_CARCASS, MONSTER_EYE_LENS, BASILISK_GIZZARD);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30436-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30436-06.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 21)
			{
				htmltext = "30436-02.htm";
				return htmltext;
			}
			htmltext = "30436-01.htm";
			st.exitCurrentQuest(true);
		}
		else
		{
			long carcass = st.getQuestItemsCount(MONSTER_EYE_CARCASS);
			long lenses = st.getQuestItemsCount(MONSTER_EYE_LENS);
			long gizzard = st.getQuestItemsCount(BASILISK_GIZZARD);
			if(carcass + lenses + gizzard > 0)
			{
				st.rollAndGive(ADENA, 30 * carcass + 2000 * lenses + 75 * gizzard, 100);
				st.takeItems(MONSTER_EYE_CARCASS, -1);
				st.takeItems(MONSTER_EYE_LENS, -1);
				st.takeItems(BASILISK_GIZZARD, -1);
				htmltext = "30436-05.htm";
			}
			else
				htmltext = "30436-04.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() != 1)
			return;
		int npcId = npc.getNpcId();
		int n = Rnd.get(1, 100);
		if(npcId == 20055)
		{
			if(n < 47)
			{
				st.rollAndGive(MONSTER_EYE_CARCASS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 49)
			{
				st.rollAndGive(MONSTER_EYE_LENS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20059)
		{
			if(n < 51)
			{
				st.rollAndGive(MONSTER_EYE_CARCASS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 53)
			{
				st.rollAndGive(MONSTER_EYE_LENS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20067)
		{
			if(n < 67)
			{
				st.rollAndGive(MONSTER_EYE_CARCASS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 69)
			{
				st.rollAndGive(MONSTER_EYE_LENS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20068)
		{
			if(n < 75)
			{
				st.rollAndGive(MONSTER_EYE_CARCASS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 77)
			{
				st.rollAndGive(MONSTER_EYE_LENS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20070)
		{
			if(st.rollAndGive(BASILISK_GIZZARD, 1, 50))
			{
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20072)
			if(st.rollAndGive(BASILISK_GIZZARD, 1, 51))
			{
				st.playSound(SOUND_ITEMGET);
			}
	}
}