package quests._606_WarwithVarkaSilenos;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _606_WarwithVarkaSilenos extends Quest
{
	// NPC
	public static int KADUN_ZU_KETRA = 31370;

	// Quest items
	public static int VARKAS_MANE = 7233;
	public static int HORN_OF_BUFFALO = 7186;

	private final int[] VARKA_NPC_LIST = new int[20];

	public _606_WarwithVarkaSilenos()
	{
		super(606, "_606_WarwithVarkaSilenos", "War with Varka Silenos"); // Party true

		addStartNpc(KADUN_ZU_KETRA);
		addTalkId(KADUN_ZU_KETRA);

		VARKA_NPC_LIST[0] = 21350;
		VARKA_NPC_LIST[1] = 21351;
		VARKA_NPC_LIST[2] = 21353;
		VARKA_NPC_LIST[3] = 21354;
		VARKA_NPC_LIST[4] = 21355;
		VARKA_NPC_LIST[5] = 21357;
		VARKA_NPC_LIST[6] = 21358;
		VARKA_NPC_LIST[7] = 21360;
		VARKA_NPC_LIST[8] = 21361;
		VARKA_NPC_LIST[9] = 21362;
		VARKA_NPC_LIST[10] = 21364;
		VARKA_NPC_LIST[11] = 21365;
		VARKA_NPC_LIST[12] = 21366;
		VARKA_NPC_LIST[13] = 21368;
		VARKA_NPC_LIST[14] = 21369;
		VARKA_NPC_LIST[15] = 21370;
		VARKA_NPC_LIST[16] = 21371;
		VARKA_NPC_LIST[17] = 21372;
		VARKA_NPC_LIST[18] = 21373;
		VARKA_NPC_LIST[19] = 21374;
		addKillId(VARKA_NPC_LIST);

		addQuestItem(VARKAS_MANE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("31370-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31370-4.htm"))
		{
			if(st.getQuestItemsCount(VARKAS_MANE) >= 100)
			{
				st.takeItems(VARKAS_MANE, 100);
				st.giveItems(HORN_OF_BUFFALO, 20);
			}
			else
				htmltext = "31370-havenot.htm";
		}
		else if(event.equals("31370-quit.htm"))
		{
			st.takeItems(VARKAS_MANE, -1);
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
				htmltext = "31370-1.htm";
			else
			{
				htmltext = "31370-0.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(VARKAS_MANE) == 0)
			htmltext = "31370-2r.htm";
		else if(cond == 1 && st.getQuestItemsCount(VARKAS_MANE) > 0)
			htmltext = "31370-3.htm";
		return htmltext;
	}

	public boolean isVarkaNpc(int npc)
	{
		for(int i : VARKA_NPC_LIST)
			if(npc == i)
				return true;
		return false;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null && isVarkaNpc(npcId))
			if(st.rollAndGive(VARKAS_MANE, 1, 100))
				st.playSound(SOUND_ITEMGET);
	}
}