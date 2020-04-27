package quests._639_GuardiansOfTheHolyGrail;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Guardians Of The Holy Grail
 *
 * @author PainKiller
 * @Last_Fixed by Felixx спиздил виРУС
 */

public class _639_GuardiansOfTheHolyGrail extends Quest
{
	//NPC
	private final static int DOMINIC = 31350;
	private final static int GREMORY = 32008;
	private final static int GRAIL = 32028;

	//MONSTERS
	private final static int MONASTIC_PILGRIM = 22122;
	private final static int Monastic_Crusader = 22123;
	private final static int Solina_Brother = 22124;
	private final static int Solina_Lay_Brother = 22125;
	private final static int Silent_Seeker = 22126;
	private final static int Silent_Brother = 22127;
	private final static int Monastery_Guardian = 22128;
	private final static int Warrior_Monk = 22129;
	private final static int Divine_Advocate = 22130;
	private final static int Pilgrim_of_Light = 22131;
	private final static int Judge_of_Light = 22132;
	private final static int Guardian_of_the_Grail = 22133;
	private final static int Guardian_of_the_Holy_Land = 22134;
	private final static int Beholder_of_Light = 22135;
	private final static int GATEKEEPER_ZOMBIE = 22136;

	//ITEMS
	private final static int WATER_BOTTLE = 8070;
	private final static int HOLY_WATER_BOTTLE = 8071;
	private final static int SCRIPTURES = 8069;
	private final static int SEWS = 959;
	private final static int SEAS = 960;
	private final static int ADENA = 57;

	//MINLEVEL
	private final static int MINLEVEL = 73;

	public _639_GuardiansOfTheHolyGrail()
	{
		super(639, "_639_GuardiansOfTheHolyGrail", "Guardians Of The Holy Grail"); // Party true

		addStartNpc(DOMINIC);
		addTalkId(GREMORY);
		addTalkId(GRAIL);
		addKillId(Guardian_of_the_Grail);
		addKillId(Guardian_of_the_Holy_Land);
		addKillId(Beholder_of_Light);
		addKillId(Monastery_Guardian);
		addKillId(Warrior_Monk);
		addKillId(Divine_Advocate);
		addKillId(Pilgrim_of_Light);
		addKillId(Judge_of_Light);
		addKillId(MONASTIC_PILGRIM);
		addKillId(GATEKEEPER_ZOMBIE);
		addKillId(Monastic_Crusader);
		addKillId(Solina_Brother);
		addKillId(Solina_Lay_Brother);
		addKillId(Silent_Seeker);
		addKillId(Silent_Brother);
		addQuestItem(WATER_BOTTLE, HOLY_WATER_BOTTLE, SCRIPTURES);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31350-03.htm"))
		{
			if(st.getPlayer().getLevel() < MINLEVEL)
			{
				htmltext = "31350-00.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("31350-07.htm"))
		{
			st.unset("cond");
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("31350-08.htm"))
		{
			long QI = st.getQuestItemsCount(SCRIPTURES);
			st.takeItems(SCRIPTURES, -1);
			st.rollAndGive(ADENA, 1625 * QI, 100);
		}
		else if(event.equalsIgnoreCase("32008-05.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.giveItems(WATER_BOTTLE, 1);
		}
		else if(event.equalsIgnoreCase("32028-02.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.takeItems(WATER_BOTTLE, -1);
			st.giveItems(HOLY_WATER_BOTTLE, 1);
		}
		else if(event.equalsIgnoreCase("32008-07.htm"))
		{
			st.set("cond", "4");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.takeItems(HOLY_WATER_BOTTLE, -1);
		}
		else if(event.equalsIgnoreCase("32008-08a.htm"))
		{
			st.takeItems(SCRIPTURES, 4000);
			st.giveItems(SEWS, 1);
		}
		else if(event.equalsIgnoreCase("32008-08b.htm"))
		{
			st.takeItems(SCRIPTURES, 400);
			st.giveItems(SEAS, 1);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == DOMINIC)
		{
			if(cond < 1)
			{
				htmltext = "31350-01.htm";
			}
			else if(cond > 0)
			{
				if(st.getQuestItemsCount(SCRIPTURES) > 0)
					htmltext = "31350-04.htm";
				else
					htmltext = "31350-05.htm";
			}
		}
		else if(npcId == GREMORY)
		{
			if(cond == 1)
				htmltext = "32008-01.htm";
			else if(cond == 2)
				htmltext = "32008-05b.htm";
			else if(cond == 3)
				htmltext = "32008-06.htm";
			else if(cond == 4)
			{
				if(st.getQuestItemsCount(SCRIPTURES) < 400)
					htmltext = "32008-08b.htm";
				else if(st.getQuestItemsCount(SCRIPTURES) >= 4000)
					htmltext = "32008-08c.htm";
				else
					htmltext = "32008-08.htm";
			}
		}
		else if(npcId == GRAIL)
		{
			if(cond == 2)
				htmltext = "32028-01.htm";
			else if(cond >= 3)
				htmltext = "32028-03.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, -1);
		if(st != null && st.isStarted() && st.getCond() >= 1)
			st.rollAndGive(SCRIPTURES, 1, 75);
	}
}