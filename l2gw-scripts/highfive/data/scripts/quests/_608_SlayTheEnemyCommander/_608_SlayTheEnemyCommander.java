package quests._608_SlayTheEnemyCommander;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _608_SlayTheEnemyCommander extends Quest
{
	// npc
	private static final int KADUN_ZU_KETRA = 31370;
	private static final int VARKAS_COMMANDER_MOS = 25312;

	//quest items
	private static final int HEAD_OF_MOS = 7236;
	private static final int TOTEM_OF_WISDOM = 7220;
	@SuppressWarnings("unused")
	private static final int MARK_OF_KETRA_ALLIANCE1 = 7211;
	@SuppressWarnings("unused")
	private static final int MARK_OF_KETRA_ALLIANCE2 = 7212;
	@SuppressWarnings("unused")
	private static final int MARK_OF_KETRA_ALLIANCE3 = 7213;
	private static final int MARK_OF_KETRA_ALLIANCE4 = 7214;
	private static final int MARK_OF_KETRA_ALLIANCE5 = 7215;

	public _608_SlayTheEnemyCommander()
	{
		super(608, "_608_SlayTheEnemyCommander", "Slay The Enemy Commander"); // Party true
		addStartNpc(KADUN_ZU_KETRA);
		addKillId(VARKAS_COMMANDER_MOS);
		addQuestItem(HEAD_OF_MOS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31370-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31370-4.htm"))
			if(st.getQuestItemsCount(HEAD_OF_MOS) >= 1)
			{
				st.takeItems(HEAD_OF_MOS, -1);
				st.giveItems(TOTEM_OF_WISDOM, 1);
				st.addExpAndSp(0, 10000);
				st.unset("cond");
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31370-2r.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 75)
			{
				if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE4) == 1 || st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE5) == 1)
					htmltext = "31370-1.htm";
				else
				{
					htmltext = "31370-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "31370-0.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(HEAD_OF_MOS) == 0)
			htmltext = "31370-2r.htm";
		else if(cond == 2 && st.getQuestItemsCount(HEAD_OF_MOS) >= 1)
			htmltext = "31370-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId == VARKAS_COMMANDER_MOS)
			for(QuestState st : getPartyMembersWithQuest(killer, 1))
			{
				st.giveItems(HEAD_OF_MOS, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);

			}
	}
}