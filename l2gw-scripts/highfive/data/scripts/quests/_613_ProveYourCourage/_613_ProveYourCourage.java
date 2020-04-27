package quests._613_ProveYourCourage;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _613_ProveYourCourage extends Quest
{
	private final static int DURAI = 31377;
	private final static int KETRAS_HERO_HEKATON = 25299;

	// Quest items
	private final static short HEAD_OF_HEKATON = 7240;
	private final static short FEATHER_OF_VALOR = 7229;

	// etc
	@SuppressWarnings("unused")
	private final static short MARK_OF_VARKA_ALLIANCE1 = 7221;
	@SuppressWarnings("unused")
	private final static short MARK_OF_VARKA_ALLIANCE2 = 7222;
	private final static short MARK_OF_VARKA_ALLIANCE3 = 7223;
	private final static short MARK_OF_VARKA_ALLIANCE4 = 7224;
	private final static short MARK_OF_VARKA_ALLIANCE5 = 7225;

	public _613_ProveYourCourage()
	{
		super(613, "_613_ProveYourCourage", "Prove Your Courage"); // Party true

		addStartNpc(DURAI);
		addTalkId(DURAI);
		addKillId(KETRAS_HERO_HEKATON);

		addQuestItem(HEAD_OF_HEKATON);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("31377-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31377-4.htm"))
			if(st.getQuestItemsCount(HEAD_OF_HEKATON) > 0)
			{
				st.takeItems(HEAD_OF_HEKATON, -1);
				if(st.getQuestItemsCount(FEATHER_OF_VALOR) < 1)
					st.giveItems(FEATHER_OF_VALOR, 1);
				st.addExpAndSp(0, 10000);
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
			if(st.getPlayer().getLevel() < 75)
			{
				htmltext = "31377-0.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				if(st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE3) > 0 || st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE4) > 0 || st.getQuestItemsCount(MARK_OF_VARKA_ALLIANCE5) > 0)
					htmltext = "31377-1.htm";
				else
				{
					htmltext = "31377-00.htm";
					st.exitCurrentQuest(true);
				}
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(HEAD_OF_HEKATON) < 1)
			htmltext = "31377-2r.htm";
		else if(cond == 2 && st.getQuestItemsCount(HEAD_OF_HEKATON) > 0)
			htmltext = "31377-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId == KETRAS_HERO_HEKATON)
			for(QuestState st : getPartyMembersWithQuest(killer, 1))
			{
				st.giveItems(HEAD_OF_HEKATON, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);

			}
	}
}