package quests._186_ContractExecution;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 24.12.10 14:44
 */
public class _186_ContractExecution extends Quest
{
	// NPCs
	private static final int Nikola = 30621;
	private static final int Lorain = 30673;
	private static final int Luka = 31437;

	// Items
	private static final int Certificate = 10362;
	private static final int MetalReport = 10366;
	private static final int Accessory = 10367;

	// Monsters
	private static final HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(6);
	static
	{
		dropChances.put(20577, 40);
		dropChances.put(20578, 44);
		dropChances.put(20579, 46);
		dropChances.put(20580, 88);
		dropChances.put(20581, 50);
		dropChances.put(20582, 100);
	}

	public _186_ContractExecution()
	{
		super(186, "_186_ContractExecution", "Contract Execution");
		addStartNpc(Lorain);
		addTalkId(Nikola, Lorain, Luka);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
		addQuestItem(Certificate, MetalReport, Accessory);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();

		if(npcId == Lorain)
		{
			if(reply == 186 && st.isCreated() && st.haveQuestItems(Certificate) && player.isQuestComplete(184))
			{
				st.giveItems(MetalReport, 1);
				st.takeItems(Certificate, -1);
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("researcher_lorain_q0186_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola && st.getMemoState() == 1)
			{
				if(reply == 1)
					showPage("maestro_nikola_q0186_02.htm", player);
				else if(reply == 2)
				{
					st.setMemoState(2);
					showPage("maestro_nikola_q0186_03.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(npcId == Luka)
			{
				if(reply == 1 && st.getMemoState() == 2 && st.haveQuestItems(Accessory))
					showPage("blueprint_seller_luka_q0186_03.htm", player);
				else if(reply == 2 && st.getMemoState() == 2 && st.haveQuestItems(Accessory))
				{
					st.setMemoState(3);
					showPage("blueprint_seller_luka_q0186_04.htm", player);
				}
				else if(reply == 3 && st.getMemoState() == 3)
				{
					st.takeItems(MetalReport, -1);
					st.takeItems(Accessory, -1);
					showPage("blueprint_seller_luka_q0186_06.htm", player);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 105083, 100);
					if(player.getLevel() < 47)
						st.addExpAndSp(285935, 18711);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == Lorain)
		{
			if(st.isCreated())
			{
				if(st.haveQuestItems(Certificate) && st.getPlayer().isQuestComplete(184))
				{
					if(st.getPlayer().getLevel() >= 41)
						return "researcher_lorain_q0186_01.htm";
					return "researcher_lorain_q0186_02.htm";
				}
			}
			else if(st.isStarted() && cond >= 1 && st.getPlayer().isQuestComplete(184))
				return "npchtm:researcher_lorain_q0186_04.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola)
			{
				if(cond == 1 && st.getPlayer().isQuestComplete(184))
					return "npchtm:maestro_nikola_q0186_01.htm";
				if(cond == 2 && st.getPlayer().isQuestComplete(184))
					return "npchtm:maestro_nikola_q0186_04.htm";
			}
			else if(npcId == Luka)
			{
				if(cond == 2)
				{
					if(st.haveQuestItems(Accessory))
						return "npchtm:blueprint_seller_luka_q0186_02.htm";

					return "npchtm:blueprint_seller_luka_q0186_01.htm";
				}
				if(cond == 3)
					return "npchtm:blueprint_seller_luka_q0186_05.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 2);
			if(qs != null && !qs.haveQuestItems(Accessory) && qs.rollAndGiveLimited(Accessory, 1, dropChances.get(npc.getNpcId()), 1))
				qs.playSound(SOUND_MIDDLE);
		}
	}
}
