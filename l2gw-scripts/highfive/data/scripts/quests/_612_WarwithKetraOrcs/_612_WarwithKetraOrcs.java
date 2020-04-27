package quests._612_WarwithKetraOrcs;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _612_WarwithKetraOrcs extends Quest
{
	// NPC
	private final int DURAI = 31377;

	// Quest items
	private final int MOLAR_OF_KETRA_ORC = 7234;
	private final int NEPENTHES_SEED = 7187;

	private final int[] KETRA_NPC_LIST = new int[19];

	public _612_WarwithKetraOrcs()
	{
		super(612, "_612_WarwithKetraOrcs", "War with Ketra Orcs"); // Party true

		addStartNpc(DURAI);
		addTalkId(DURAI);

		KETRA_NPC_LIST[0] = 21324;
		KETRA_NPC_LIST[1] = 21325;
		KETRA_NPC_LIST[2] = 21327;
		KETRA_NPC_LIST[3] = 21328;
		KETRA_NPC_LIST[4] = 21329;
		KETRA_NPC_LIST[5] = 21331;
		KETRA_NPC_LIST[6] = 21332;
		KETRA_NPC_LIST[7] = 21334;
		KETRA_NPC_LIST[8] = 21335;
		KETRA_NPC_LIST[9] = 21336;
		KETRA_NPC_LIST[10] = 21338;
		KETRA_NPC_LIST[11] = 21339;
		KETRA_NPC_LIST[12] = 21340;
		KETRA_NPC_LIST[13] = 21342;
		KETRA_NPC_LIST[14] = 21343;
		KETRA_NPC_LIST[15] = 21344;
		KETRA_NPC_LIST[16] = 21345;
		KETRA_NPC_LIST[17] = 21346;
		KETRA_NPC_LIST[18] = 21347;
		addKillId(KETRA_NPC_LIST);

		addQuestItem(MOLAR_OF_KETRA_ORC);
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
		{
			if(st.getQuestItemsCount(MOLAR_OF_KETRA_ORC) >= 100)
			{
				st.takeItems(MOLAR_OF_KETRA_ORC, 100);
				st.giveItems(NEPENTHES_SEED, 20);
			}
			else
				htmltext = "31377-havenot.htm";
		}
		else if(event.equalsIgnoreCase("31377-quit.htm"))
		{
			st.takeItems(MOLAR_OF_KETRA_ORC, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 74)
				htmltext = "31377-1.htm";
			else
			{
				htmltext = "31377-0.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(MOLAR_OF_KETRA_ORC) == 0)
			htmltext = "31377-2r.htm";
		else if(cond == 1 && st.getQuestItemsCount(MOLAR_OF_KETRA_ORC) > 0)
			htmltext = "31377-3.htm";
		return htmltext;
	}

	public boolean isKetraNpc(int npc)
	{
		for(int i : KETRA_NPC_LIST)
			if(npc == i)
				return true;
		return false;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null && isKetraNpc(npcId))
			if(st.rollAndGive(MOLAR_OF_KETRA_ORC, 1, 100))
				st.playSound(SOUND_ITEMGET);
	}
}