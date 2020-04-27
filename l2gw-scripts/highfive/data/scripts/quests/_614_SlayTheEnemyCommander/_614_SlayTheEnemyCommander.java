package quests._614_SlayTheEnemyCommander;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _614_SlayTheEnemyCommander extends Quest
{
	// NPC
	private static final int DURAI = 31377;
	private static final int KETRAS_COMMANDER_TAYR = 25302;

	// etc
	@SuppressWarnings("unused")
	private static final int MARK_OF_VARKA_ALLIANCE1 = 7221;
	@SuppressWarnings("unused")
	private static final int MARK_OF_VARKA_ALLIANCE2 = 7222;
	@SuppressWarnings("unused")
	private static final int MARK_OF_VARKA_ALLIANCE3 = 7223;
	private static final int MARK_OF_VARKA_ALLIANCE4 = 7224;
	private static final int MARK_OF_VARKA_ALLIANCE5 = 7225;
	private static final int HEAD_OF_TAYR = 7241;
	private static final int FEATHER_OF_WISDOM = 7230;

	public _614_SlayTheEnemyCommander()
	{
		super(614, "_614_SlayTheEnemyCommander", "Slay The Enemy Commander"); // Party true
		addStartNpc(DURAI);
		addKillId(KETRAS_COMMANDER_TAYR);
		addQuestItem(HEAD_OF_TAYR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31377-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31377-4.htm"))
			if(st.getQuestItemsCount(HEAD_OF_TAYR) >= 1)
			{
				st.takeItems(HEAD_OF_TAYR, -1);
				st.giveItems(FEATHER_OF_WISDOM, 1);
				st.addExpAndSp(0, 10000);
				st.unset("cond");
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31377-2r.htm";
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
				if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE4) == 1 || st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE5) == 1)
					htmltext = "31377-1.htm";
				else
				{
					htmltext = "31377-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "31377-0.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(HEAD_OF_TAYR) == 0)
			htmltext = "31377-2r.htm";
		else if(cond == 2 && st.getQuestItemsCount(HEAD_OF_TAYR) >= 1)
			htmltext = "31377-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId == KETRAS_COMMANDER_TAYR)
			for(QuestState st : getPartyMembersWithQuest(killer, 1))
			{
				st.giveItems(HEAD_OF_TAYR, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);

			}
	}
}