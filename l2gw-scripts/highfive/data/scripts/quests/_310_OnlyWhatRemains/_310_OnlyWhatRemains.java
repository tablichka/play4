package quests._310_OnlyWhatRemains;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * User: ic
 * Date: 19.08.2010
 */
public class _310_OnlyWhatRemains extends Quest
{
	private static final int KINTAIJIN = 32640; // NPC

	private static final int[] MOBS = {22617, 22618, 22619, 22620, 22621, 22622, 22623, 22624, 22625, 22626, 22627, 22628, 22629, 22630, 22631, 22632, 22633, 25667, 25668, 25669, 25670};

	private static final int DIRTY_BEADS = 14880;
	private static final int MULTI_COLORED_JEWEL = 14835;
	private static final int GROWTH_ACCEL = 14832;
	private static final int COCOON_SMALL = 14833;
	private static final int COCOON_LARGE = 14834;

	public _310_OnlyWhatRemains()
	{
		super(310, "_310_OnlyWhatRemains", "What Is This Item?"); // Party true

		addStartNpc(KINTAIJIN);
		addTalkId(KINTAIJIN);
		addQuestItem(DIRTY_BEADS);
		addKillId(MOBS);
	}


	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("accept"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "kintaijin_q0310_004.htm";
		}
		else if(event.equalsIgnoreCase("quit"))
		{
			st.exitCurrentQuest(true);
			htmltext = "kintaijin_q0310_008.htm";
		}
		else if(event.equalsIgnoreCase("getreward"))
		{
			if(st.getQuestItemsCount(DIRTY_BEADS) < 500)
			{
				htmltext = "kintaijin_q0310_010a.htm";
			}
			else
			{
				st.takeItems(DIRTY_BEADS, 500);
				st.giveItems(GROWTH_ACCEL, 1);
				st.giveItems(MULTI_COLORED_JEWEL, 1);
				htmltext = "kintaijin_q0310_010.htm";
			}
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		QuestState q240 = st.getPlayer().getQuestState("_240_ImTheOnlyOneYouCanTrust");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 81 || q240 == null || !q240.isCompleted())
			{
				htmltext = "kintaijin_q0310_000.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "kintaijin_q0310_001.htm";
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(DIRTY_BEADS) < 500)
		{
			htmltext = "kintaijin_q0310_006.htm";
		}
		else if(cond == 1 && st.getQuestItemsCount(DIRTY_BEADS) >= 500)
		{
			htmltext = "kintaijin_q0310_009.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId >= 25667 && npcId <= 25670)
		{
			if(killer.getParty() != null)
			{
				for(L2Player member : killer.getParty().getPartyMembers())
					member.addItem("CannibalChiefReward", Rnd.get(COCOON_SMALL, COCOON_LARGE), 1, npc, true);
			}
			else
				killer.addItem("CannibalChiefReward", Rnd.get(COCOON_SMALL, COCOON_LARGE), 1, npc, true);
		}
		else if(npc.getNpcId() >= 22617 && npc.getNpcId() <= 22633)
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, 1);
			if(st != null)
			{
				st.rollAndGive(DIRTY_BEADS, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}

	}
}
