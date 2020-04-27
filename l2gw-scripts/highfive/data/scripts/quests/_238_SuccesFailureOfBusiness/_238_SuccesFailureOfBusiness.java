package quests._238_SuccesFailureOfBusiness;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.08.2010 14:35:29
 */
public class _238_SuccesFailureOfBusiness extends Quest
{
	// NPCs
	private static final int HELVETICA = 32641;

	private static final int BRAZIER_OF_PURITY = 18806;
	private static final int EVIL_SPIRITS = 22658;
	private static final int GUARDIAN_SPIRITS = 22659;

	private static final int VICINITY_OF_FOS = 14865;
	private static final int BROKEN_PIECE_OF_MAGIC_FORCE = 14867;
	private static final int GUARDIAN_SPIRIT_FRAGMENT = 14868;

	public _238_SuccesFailureOfBusiness()
	{
		super(238, "_238_SuccesFailureOfBusiness", "Succes/Failure Of Business");
		addStartNpc(HELVETICA);
		addTalkId(HELVETICA);
		addKillId(BRAZIER_OF_PURITY);
		addKillId(EVIL_SPIRITS);
		addKillId(GUARDIAN_SPIRITS);
		addQuestItem(BROKEN_PIECE_OF_MAGIC_FORCE, GUARDIAN_SPIRIT_FRAGMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("32461-03.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32461-06.htm"))
		{
			st.set("cond", 3);
			st.playSound(SOUND_MIDDLE);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		QuestState qs = st.getPlayer().getQuestState("_237_WindsOfChange");
		QuestState qs2 = st.getPlayer().getQuestState("_239_WontYouJoinUs");
		if(npcId == HELVETICA)
		{
			if(st.isCompleted())
				htmltext = "32461-09.htm";
			else if(st.isCreated())
			{
				if(qs2 != null && qs2.isCompleted())
					htmltext = "32461-10.htm";
				else if(qs != null && qs.isCompleted() && st.getPlayer().getLevel() >= 82)
					htmltext = "32461-01.htm";
				else
				{
					htmltext = "32461-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "32461-04.htm";
			else if(cond == 2)
			{
				htmltext = "32461-05.htm";
				st.takeItems(BROKEN_PIECE_OF_MAGIC_FORCE, 10);
			}
			else if(cond == 3)
				htmltext = "32461-07.htm";
			else if(cond == 4 && st.getQuestItemsCount(GUARDIAN_SPIRIT_FRAGMENT) == 20)
			{
				htmltext = "32461-08.htm";
				st.rollAndGive(57, 283346, 100);
				st.takeItems(VICINITY_OF_FOS, 1);
				st.takeItems(GUARDIAN_SPIRIT_FRAGMENT, 20);
				st.addExpAndSp(1319736, 103553);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1 && npcId == BRAZIER_OF_PURITY)
		{
			if(st.rollAndGiveLimited(BROKEN_PIECE_OF_MAGIC_FORCE, 1, 100, 10))
			{
				if(st.getQuestItemsCount(BROKEN_PIECE_OF_MAGIC_FORCE) == 10)
				{
					st.set("cond", 2);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);

			}
		}
		else if(cond == 3 && (npcId == EVIL_SPIRITS || npcId == GUARDIAN_SPIRITS))
		{
			if(st.rollAndGiveLimited(GUARDIAN_SPIRIT_FRAGMENT, 1, 80, 20))
			{
				if(st.getQuestItemsCount(GUARDIAN_SPIRIT_FRAGMENT) == 20)
				{
					st.set("cond", 4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);

			}
		}
	}
}
