package quests._278_HomeSecurity;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 05.02.11 16:55
 */
public class _278_HomeSecurity extends Quest
{
	// NPCs
	private static final int beast_herder_tunatun = 31537;

	// Items
	private static final int q_mane_of_farm_marauder = 15531;

	// Mobs
	private static final HashMap<Integer, Double> _mobs = new HashMap<Integer, Double>();

	static
	{
		_mobs.put(18907, 8.5);
		_mobs.put(18906, 8.5);
		_mobs.put(18905, 100.0);
	}

	public _278_HomeSecurity()
	{
		super(278, "_278_HomeSecurity", "Home Security");

		addStartNpc(beast_herder_tunatun);
		addTalkId(beast_herder_tunatun);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_mane_of_farm_marauder);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("complete", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == beast_herder_tunatun)
		{
			if(st.isCreated())
			{
				if(reply == 278 && player.getLevel() >= 82)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("beast_herder_tunatun_q0278_04.htm", player);
				}
				else if(reply == 1)
				{
					if(player.getLevel() >= 82)
						showQuestPage("beast_herder_tunatun_q0278_02.htm", player);
					else
						showPage("beast_herder_tunatun_q0278_03.htm", player);
				}
			}
			else if(st.isStarted() && reply == 2 && st.getMemoState() == 2 && st.getQuestItemsCount(q_mane_of_farm_marauder) >= 300)
			{
				int i0 = Rnd.get(100);
				if(i0 < 10)
					st.giveItems(960, 1);
				else if(i0 < 19)
					st.giveItems(960, 2);
				else if(i0 < 27)
					st.giveItems(960, 3);
				else if(i0 < 34)
					st.giveItems(960, 4);
				else if(i0 < 40)
					st.giveItems(960, 5);
				else if(i0 < 45)
					st.giveItems(960, 6);
				else if(i0 < 49)
					st.giveItems(960, 7);
				else if(i0 < 52)
					st.giveItems(960, 8);
				else if(i0 < 54)
					st.giveItems(960, 9);
				else if(i0 < 55)
					st.giveItems(960, 10);
				else if(i0 < 75)
					st.giveItems(9553, 1);
				else if(i0 < 90)
					st.giveItems(9553, 2);
				else
					st.giveItems(959, 1);

				st.takeItems(q_mane_of_farm_marauder, -1);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("beast_herder_tunatun_q0278_07.htm", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "complete";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == beast_herder_tunatun)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "beast_herder_tunatun_q0278_01.htm";

				st.exitCurrentQuest(true);
				return "noquest";
			}
			if(st.isStarted())
			{
				if(cond == 2 && st.getQuestItemsCount(q_mane_of_farm_marauder) >= 300)
					return "npchtm:beast_herder_tunatun_q0278_05.htm";
				if(cond == 1 || st.getQuestItemsCount(q_mane_of_farm_marauder) < 300)
					return "npchtm:beast_herder_tunatun_q0278_06.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 1);
			long c = npc.getNpcId() == 18905 ? Rnd.chance(48.6) ? Rnd.get(6) + 1 : Rnd.get(5) + 1 : 1;
			if(qs != null && qs.rollAndGiveLimited(q_mane_of_farm_marauder, c, _mobs.get(npc.getNpcId()), 300))
				if(qs.getQuestItemsCount(q_mane_of_farm_marauder) >= 300)
				{
					qs.setMemoState(2);
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.playSound(SOUND_MIDDLE);
				}
				else
					qs.playSound(SOUND_ITEMGET);
		}
	}
}
