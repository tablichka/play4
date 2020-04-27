package quests._607_ProveYourCourage;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _607_ProveYourCourage extends Quest
{
	private final static int KADUN_ZU_KETRA = 31370;
	private final static int VARKAS_HERO_SHADITH = 25309;

	// Quest items
	private final static short HEAD_OF_SHADITH = 7235;
	private final static short TOTEM_OF_VALOR = 7219;

	// etc
	@SuppressWarnings("unused")
	private final static short MARK_OF_KETRA_ALLIANCE1 = 7211;
	@SuppressWarnings("unused")
	private final static short MARK_OF_KETRA_ALLIANCE2 = 7212;
	private final static short MARK_OF_KETRA_ALLIANCE3 = 7213;
	private final static short MARK_OF_KETRA_ALLIANCE4 = 7214;
	private final static short MARK_OF_KETRA_ALLIANCE5 = 7215;

	public _607_ProveYourCourage()
	{
		super(607, "_607_ProveYourCourage", "Prove Your Courage"); // Party true

		addStartNpc(KADUN_ZU_KETRA);
		addTalkId(KADUN_ZU_KETRA);
		addKillId(VARKAS_HERO_SHADITH);

		addQuestItem(HEAD_OF_SHADITH);
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
			if(st.getQuestItemsCount(HEAD_OF_SHADITH) > 0)
			{
				st.takeItems(HEAD_OF_SHADITH, -1);
				if(st.getQuestItemsCount(TOTEM_OF_VALOR) < 1)
					st.giveItems(TOTEM_OF_VALOR, 1);
				st.addExpAndSp(0, 10000);
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
			if(st.getPlayer().getLevel() < 75)
			{
				htmltext = "31370-0.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				if(st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE3) > 0 || st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE4) > 0 || st.getQuestItemsCount(MARK_OF_KETRA_ALLIANCE5) > 0)
					htmltext = "31370-1.htm";
				else
				{
					htmltext = "31370-00.htm";
					st.exitCurrentQuest(true);
				}
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(HEAD_OF_SHADITH) < 1)
			htmltext = "31370-2r.htm";
		else if(cond == 2 && st.getQuestItemsCount(HEAD_OF_SHADITH) > 0)
			htmltext = "31370-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId == VARKAS_HERO_SHADITH)
			for(QuestState st : getPartyMembersWithQuest(killer, 1))
			{
				st.giveItems(HEAD_OF_SHADITH, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);

			}
	}
}