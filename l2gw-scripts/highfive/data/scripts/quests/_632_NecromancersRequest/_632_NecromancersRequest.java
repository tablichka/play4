package quests._632_NecromancersRequest;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _632_NecromancersRequest extends Quest
{
	//NPC
	private static final int WIZARD = 31522;
	//ITEMS
	private static final int V_HEART = 7542;
	private static final int Z_BRAIN = 7543;
	//REWARDS
	private static final int ADENA = 57;
	private static final int ADENA_AMOUNT = 120000;
	//MOBS
	private static final int[] VAMPIRES = {21568, 21573, 21582, 21585, 21586, 21587, 21588, 21589, 21590, 21591, 21592, 21593, 21594, 21595};
	private static final int[] UNDEADS = {21547, 21548, 21549, 21551, 21552, 21555, 21556, 21562, 21571, 21576, 21577, 21579};

	public _632_NecromancersRequest()
	{
		super(632, "_632_NecromancersRequest", "Necromancers Request"); // Party true
		addStartNpc(WIZARD);
		addKillId(VAMPIRES);
		addKillId(UNDEADS);
		addQuestItem(V_HEART, Z_BRAIN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("0"))
		{
			st.playSound(SOUND_FINISH);
			htmltext = "31522-3.htm";
			st.exitCurrentQuest(true);
		}
		else if(event.equals("1"))
			htmltext = "31522-0.htm";
		else if(event.equals("2"))
		{
			if(st.getInt("cond") == 2)
				if(st.getQuestItemsCount(V_HEART) > 199)
				{
					st.takeItems(V_HEART, 200);
					st.rollAndGive(ADENA, ADENA_AMOUNT, 100);
					st.playSound(SOUND_FINISH);
					st.set("cond", "1");
					htmltext = "31522-1.htm";
				}
		}
		else if(event.equals("start"))
			if(st.getPlayer().getLevel() > 62)
			{
				htmltext = "31522-0.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "<html><body>Mysterious Wizard:<br>This quest can only be taken by characters that have a minimum level of <font color=\"LEVEL\">63</font>. Return when you are more experienced.";
				st.exitCurrentQuest(true);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated())
			if(npcId == WIZARD)
				htmltext = "31522.htm";
		if(cond == 1)
			htmltext = "31522-1.htm";
		if(cond == 2)
			if(st.getQuestItemsCount(V_HEART) > 199)
				htmltext = "31522-2.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{

		QuestState st = getRandomPartyMemberWithQuest(killer, 2);

		if(st != null && st.rollAndGive(Z_BRAIN, 1, 33))
			st.playSound(SOUND_ITEMGET);

		st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			if(st.rollAndGiveLimited(V_HEART, 1, 50, 200))
			{
				if(st.getQuestItemsCount(V_HEART) == 200)
				{
					st.set("cond", "2");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}

			if(st.rollAndGive(Z_BRAIN, 1, 33))
				st.playSound(SOUND_ITEMGET);
		}

	}
}