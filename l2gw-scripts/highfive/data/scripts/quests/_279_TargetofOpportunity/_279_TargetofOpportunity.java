package quests._279_TargetofOpportunity;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 05.02.11 17:21
 */
public class _279_TargetofOpportunity extends Quest
{
	// NPCs
	private static final int zerian = 32302;
	private static final int teleporter_a01 = 32745;
	private static final int teleporter_a02 = 32746;
	private static final int teleporter_a03 = 32747;
	private static final int teleporter_a04 = 32748;
	private static final int teleporter_a06 = 32749;
	private static final int teleporter_a07 = 32750;
	private static final int teleporter_a08 = 32751;
	private static final int teleporter_a09 = 32752;

	// Mobs
	private static final HashMap<Integer, Integer> _mobs = new HashMap<Integer, Integer>();

	static
	{
		_mobs.put(22373, 15517);
		_mobs.put(22374, 15518);
		_mobs.put(22375, 15519);
		_mobs.put(22376, 15520);
	}

	public _279_TargetofOpportunity()
	{
		super(279, "_279_TargetofOpportunity", "Target of Opportunity");

		addStartNpc(zerian);
		addTalkId(zerian, teleporter_a01, teleporter_a02, teleporter_a03, teleporter_a04, teleporter_a06, teleporter_a07, teleporter_a08, teleporter_a09);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(15517, 15518, 15519, 15520);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == zerian)
		{
			if(st.isCreated())
			{
				if(reply == 279 && player.getLevel() >= 82)
				{
					st.setMemoState(1);
					st.set("ex", 1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("zerian_q0279_05.htm", player);
				}
				else if(reply == 1 && player.getLevel() >= 82)
					showPage("zerian_q0279_03.htm", player);
				else if(reply == 2 && player.getLevel() >= 82)
					showPage("zerian_q0279_04.htm", player);
			}
			else if(st.isStarted() && reply == 3)
			{
				if(st.getMemoState() < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1)
				{
					st.giveItems(15515, 1);
					st.giveItems(15516, 1);
					st.takeItems(15517, -1);
					st.takeItems(15518, -1);
					st.takeItems(15519, -1);
					st.takeItems(15520, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("zerian_q0279_08.htm", player);
				}
			}
		}
		else if(st.isStarted())
		{
			if(npcId == teleporter_a01 || npcId == teleporter_a02 || npcId == teleporter_a03 || npcId == teleporter_a06 || npcId == teleporter_a07 || npcId == teleporter_a08)
			{
				if(reply == 4 && st.getCond() < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1 && st.getInt("ex") == 2)
					showPage("zerian_voice_q0279_10.htm", player);
				else if(reply == 5 && st.getCond() < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1 && st.getInt("ex") == 1)
				{
					st.set("ex", 2);
					showPage("zerian_voice_q0279_11.htm", player);
				}

			}
			else if(npcId == teleporter_a04 || npcId == teleporter_a09)
			{
				if(reply == 6 && st.getCond() < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1 && st.getInt("ex") == 1)
				{
					st.set("ex", 2);
					showPage("zerian_voice_q0279_12.htm", player);
				}
				else if(reply == 7)
					showPage("zerian_voice_q0279_14.htm", player);
				else if(reply == 8 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1)
				{
					st.giveItems(15515, 1);
					st.giveItems(15516, 1);
					st.takeItems(15517, -1);
					st.takeItems(15518, -1);
					st.takeItems(15519, -1);
					st.takeItems(15520, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("zerian_voice_q0279_15.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == zerian)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "zerian_q0279_01.htm";

				st.exitCurrentQuest(true);
				return "zerian_q0279_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1 && (st.getQuestItemsCount(15517) == 0 || st.getQuestItemsCount(15518) == 0 || st.getQuestItemsCount(15519) == 0 || st.getQuestItemsCount(15520) == 0))
					return "npchtm:zerian_q0279_06.htm";
				if(cond < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1)
					return "npchtm:zerian_q0279_07.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId >= teleporter_a01 && npcId <= teleporter_a09)
			{
				if(cond < 2 && st.getQuestItemsCount(15517) >= 1 && st.getQuestItemsCount(15518) >= 1 & st.getQuestItemsCount(15519) >= 1 && st.getQuestItemsCount(15520) >= 1)
				{
					if(st.getInt("ex") == 1)
						return "npchtm:teleporter_a0" + (teleporter_a01 - 32744) + "_q0279_01.htm";
					if(st.getInt("ex") == 2)
						return "npchtm:zerian_voice_q0279_13.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithQuest(killer, 1);
			if(qs != null && qs.rollAndGiveLimited(_mobs.get(npc.getNpcId()), 1, 33.1, 1))
				if(qs.getQuestItemsCount(15517) >= 1 && qs.getQuestItemsCount(15518) >= 1 & qs.getQuestItemsCount(15519) >= 1 && qs.getQuestItemsCount(15520) >= 1)
				{
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.playSound(SOUND_MIDDLE);
				}
				else
					qs.playSound(SOUND_ITEMGET);
		}
	}
}
