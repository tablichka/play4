package quests._382_KailsMagicCoin;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

public class _382_KailsMagicCoin extends Quest
{
	//Quest items
	private static int ROYAL_MEMBERSHIP = 5898;
	//NPCs
	private static int VERGARA = 30687;
	//MOBs and CHANCES
	private static final HashMap<Integer, int[]> MOBS = new HashMap<Integer, int[]>();

	static
	{
		MOBS.put(21017, new int[]{5961});
		MOBS.put(21019, new int[]{5962});
		MOBS.put(21020, new int[]{5963});
		MOBS.put(21022, new int[]{5961, 5962, 5963});
	}

	public _382_KailsMagicCoin()
	{
		super(382, "_382_KailsMagicCoin", "Kail's Magic Coin");

		addStartNpc(VERGARA);

		for(int mobId : MOBS.keySet())
			addKillId(mobId);

		addQuestItem(5961);
		addQuestItem(5962);
		addQuestItem(5963);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("30687-03.htm"))
			if(st.getPlayer().getLevel() >= 55 && st.getQuestItemsCount(ROYAL_MEMBERSHIP) > 0)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "30687-01.htm";
				st.exitCurrentQuest(true);
			}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");

		if(st.getQuestItemsCount(ROYAL_MEMBERSHIP) == 0 || st.getPlayer().getLevel() < 55)
		{
			htmltext = "30687-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(st.isCreated())
			htmltext = "30687-02.htm";
		else
			htmltext = "30687-04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		if(st.rollAndGive(MOBS.get(npcId)[Rnd.get(MOBS.get(npcId).length)], 1, 10) && st.getQuestItemsCount(ROYAL_MEMBERSHIP) > 0)
			st.playSound(SOUND_ITEMGET);
	}
}