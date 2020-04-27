package quests._691_MatrasSuspiciousRequest;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _691_MatrasSuspiciousRequest extends Quest
{
	// NPC
	private final static int MATRAS = 32245;
	private final static int LABYRINTH_CAPTAIN = 22368;

	// Items
	private final static int RED_STONE = 10372;
	private final static int RED_STONES_COUNT = 744;
	private final static int DYNASTIC_ESSENCE_II = 10413;

	public _691_MatrasSuspiciousRequest()
	{
		super(691, "_691_MatrasSuspiciousRequest", "Matras Suspicious Request");

		addStartNpc(MATRAS);
		addQuestItem(RED_STONE);
		addKillId(22363, 22364, 22365, 22366, 22367, LABYRINTH_CAPTAIN, 22369, 22370, 22371, 22372);
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if(event.equalsIgnoreCase("32245-03.htm"))
		{
			qs.setCond(1);
			qs.setState(STARTED);
			qs.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32245-05.htm"))
		{
			qs.takeItems(RED_STONE, RED_STONES_COUNT);
			qs.giveItems(DYNASTIC_ESSENCE_II, 1);
			qs.playSound(SOUND_FINISH);
			qs.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getCond();
		if(cond == 0)
		{
			if(st.getPlayer().getLevel() >= 76)
				htmltext = "32245-01.htm";
			else
				htmltext = "32245-00.htm";
			st.exitCurrentQuest(true);
		}
		else
		{
			if(st.getQuestItemsCount(RED_STONE) < RED_STONES_COUNT)
				htmltext = "32245-03.htm";
			else
				htmltext = "32245-04.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGiveLimited(RED_STONE, 1, npc.getNpcId() == LABYRINTH_CAPTAIN ? 50 : 30, RED_STONES_COUNT))
			st.playSound(st.getQuestItemsCount(RED_STONE) == RED_STONES_COUNT ? SOUND_MIDDLE : SOUND_ITEMGET);
	}
}