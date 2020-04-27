package quests._699_GuardianOfTheSkies;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.08.2010 16:38:16
 */
public class _699_GuardianOfTheSkies extends Quest
{
	// NPCs
	private static final int LEKON = 32557;

	// ITEMS
	private static final int GOLDEN_FEATHER = 13871;

	// MOBS
	private static final int[] MOBS = {22614, 22615, 25623, 25633};

	// SETTINGS
	private static final int DROP_CHANCE = 80;

	public _699_GuardianOfTheSkies()
	{
		super(699, "_699_GuardianOfTheSkies", "Guardian Of The Skies");

		addStartNpc(LEKON);
		addTalkId(LEKON);

		addKillId(MOBS);
		addQuestItem(GOLDEN_FEATHER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("32557-03.htm"))
		{
			st.setState(STARTED);
			st.set("cond", 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32557-quit.htm"))
		{
			st.unset("cond");
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == LEKON)
		{
			QuestState first = st.getPlayer().getQuestState("_10273_GoodDayToFly");
			if(first != null && first.isCompleted() && st.isCreated() && st.getPlayer().getLevel() >= 75)
				htmltext = "32557-01.htm";
			else if(cond == 1)
			{
				long itemcount = st.getQuestItemsCount(GOLDEN_FEATHER);
				if(itemcount > 0)
				{
					st.takeItems(GOLDEN_FEATHER, -1);
					st.rollAndGive(57, itemcount * 2300, 100);
					st.playSound(SOUND_ITEMGET);
					htmltext = "32557-06.htm";
				}
				else
					htmltext = "32557-04.htm";
			}
			else if(st.isCreated())
				htmltext = "32557-00.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1)
			for(int mobId : MOBS)
				if(mobId == npcId && st.rollAndGive(GOLDEN_FEATHER, 1, DROP_CHANCE))
				{
					st.playSound(SOUND_ITEMGET);
					break;
				}

	}
}
