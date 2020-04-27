package quests._290_ThreatRemoval;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

/**
 * @author rage
 * @date 06.02.11 13:44
 */
public class _290_ThreatRemoval extends Quest
{
	// NPCs
	private static final int pinaps = 30201;

	// Items
	private static final int q_id_tag_of_xel = 15714;

	// Mobs
	private static final HashMap<Integer, Double> _mobs = new HashMap<Integer, Double>();

	static
	{
		_mobs.put(22781, 48.3);
		_mobs.put(22785, 16.9);
		_mobs.put(22783, 35.2);
		_mobs.put(22780, 36.3);
		_mobs.put(22784, 36.3);
		_mobs.put(22782, 36.3);
		_mobs.put(22776, 39.7);
		_mobs.put(22775, 93.2);
		_mobs.put(22778, 93.2);
		_mobs.put(22777, 93.2);
	}

	public _290_ThreatRemoval()
	{
		super(290, "_290_ThreatRemoval", "Threat Removal");

		addStartNpc(pinaps);
		addTalkId(pinaps);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_id_tag_of_xel);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == pinaps)
		{
			if(st.isCreated())
			{
				if(reply == 290 && player.getLevel() >= 82 && player.isQuestComplete(251))
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("pinaps_q0290_03.htm", player);
				}
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 1)
				{
					if(st.getQuestItemsCount(q_id_tag_of_xel) >= 400)
					{
						st.takeItems(q_id_tag_of_xel, 400);
						int i0 = Rnd.get(10);
						if(i0 == 0)
							st.giveItems(959, 1);
						else if(i0 >= 1 && i0 < 4)
							st.giveItems(960, 1);
						else if(i0 >= 4 && i0 < 6)
							st.giveItems(960, 2);
						else if(i0 >= 6 && i0 < 7)
							st.giveItems(960, 3);
						else if(i0 >= 7 && i0 < 9)
							st.giveItems(9552, 1);
						else
							st.giveItems(9552, 2);

						st.playSound(SOUND_FINISH);
						showPage("pinaps_q0290_06.htm", player);
					}
				}
				else if(reply == 2)
					showPage("pinaps_q0290_07.htm", player);
				else if(reply == 3)
				{
					if(st.getQuestItemsCount(q_id_tag_of_xel) > 1)
						showPage("pinaps_q0290_08.htm", player);
					else if(st.getQuestItemsCount(q_id_tag_of_xel) == 0)
					{
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("pinaps_q0290_09.htm", player);
					}
				}
				else if(reply == 4)
				{
					st.takeItems(q_id_tag_of_xel, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("pinaps_q0290_10.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == pinaps)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82 && st.getPlayer().isQuestComplete(251))
					return "pinaps_q0290_02.htm";

				st.exitCurrentQuest(true);
				return "pinaps_q0290_01.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(q_id_tag_of_xel) < 400)
						return "npchtm:pinaps_q0290_04.htm";

					return "npchtm:pinaps_q0290_05.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(killer, 1);
		if(qs != null && qs.rollAndGive(q_id_tag_of_xel, 1, _mobs.get(npc.getNpcId())))
			qs.playSound(SOUND_ITEMGET);
	}
}
