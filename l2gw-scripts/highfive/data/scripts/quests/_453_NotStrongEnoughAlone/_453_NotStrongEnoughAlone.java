package quests._453_NotStrongEnoughAlone;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.crontab.Crontab;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author rage
 * @date 06.02.11 15:16
 */
public class _453_NotStrongEnoughAlone extends Quest
{
	// NPCs
	private static final int clemis = 32734;

	// Mobs
	private static final HashMap<Integer, Integer> _mobs1 = new HashMap<Integer, Integer>();
	private static final HashMap<Integer, Integer> _mobs2 = new HashMap<Integer, Integer>();
	private static final HashMap<Integer, Integer> _mobs3 = new HashMap<Integer, Integer>();

	static
	{
		_mobs1.put(22746, 22746); // 15 1
		_mobs1.put(22747, 22747);
		_mobs1.put(22748, 22748);
		_mobs1.put(22749, 22749);
		_mobs1.put(22750, 22746);
		_mobs1.put(22751, 22747);
		_mobs1.put(22752, 22748);
		_mobs1.put(22753, 22749);

		_mobs2.put(22754, 22754); // 20 2
		_mobs2.put(22755, 22755);
		_mobs2.put(22756, 22756);
		_mobs2.put(22757, 22754);
		_mobs2.put(22758, 22755);
		_mobs2.put(22759, 22756);

		_mobs3.put(22760, 22760); // 20 3
		_mobs3.put(22761, 22761);
		_mobs3.put(22762, 22762);
		_mobs3.put(22763, 22760);
		_mobs3.put(22764, 22761);
		_mobs3.put(22765, 22762);
	}

	private static final Crontab resetTime = new Crontab("30 6 * * *");

	public _453_NotStrongEnoughAlone()
	{
		super(453, "_453_NotStrongEnoughAlone", "Not Strong Enough Alone");

		addStartNpc(clemis);
		addTalkId(clemis);
		for(int npcId : _mobs1.keySet())
			addKillId(npcId);
		for(int npcId : _mobs2.keySet())
			addKillId(npcId);
		for(int npcId : _mobs3.keySet())
			addKillId(npcId);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == clemis)
		{
			if(st.isCreated())
			{
				if(reply == 453 && player.getLevel() >= 84 && !player.getVarB("q453") && player.isQuestComplete(10282))
				{
					st.setMemoState(1);
					st.setCond(1);
					st.set("ex", 0);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("clemis_q0453_06.htm", player);
				}
				else if(reply == 1)
				{
					if(!player.getVarB("q453") && (player.getLevel() < 84 || !player.isQuestComplete(10282)))
						showPage("clemis_q0453_03.htm", player);
					else if(!player.getVarB("q453") && player.getLevel() >= 84 && player.isQuestComplete(10282))
						showPage("clemis_q0453_04.htm", player);
				}
				else if(reply == 2)
				{
					if(!player.getVarB("q453") && player.getLevel() >= 84 && player.isQuestComplete(10282))
						showQuestPage("clemis_q0453_05.htm", player);
				}
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 3)
				{
					st.set("ex", 1);
					showPage("clemis_q0453_07.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 4)
				{
					st.set("ex", 2);
					showPage("clemis_q0453_08.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 5)
				{
					st.set("ex", 3);
					showPage("clemis_q0453_09.htm", player);
					st.setCond(4);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == clemis)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 84 && !st.getPlayer().getVarB("q453") && st.getPlayer().isQuestComplete(10282))
					return "clemis_q0453_01.htm";

				st.exitCurrentQuest(true);

				if(st.getPlayer().getVarB("q453"))
					return "clemis_q0453_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getInt("ex") == 0)
						return "npchtm:clemis_q0453_10.htm";
					if(st.getInt("ex") == 1)
						return "npchtm:clemis_q0453_11.htm";
					if(st.getInt("ex") == 2)
						return "npchtm:clemis_q0453_12.htm";
					if(st.getInt("ex") == 3)
						return "npchtm:clemis_q0453_13.htm";
				}
				else if(cond == 2)
				{
					if(Rnd.chance(50))
					{
						int i0 = Rnd.get(100);
						if(i0 < 9)
							st.giveItems(15815, 1);
						else if(i0 <= 9 && i0 < 18)
							st.giveItems(15816, 1);
						else if(i0 <= 18 && i0 < 27)
							st.giveItems(15817, 1);
						else if(i0 <= 27 && i0 < 36)
							st.giveItems(15818, 1);
						else if(i0 <= 38 && i0 < 47)
							st.giveItems(15819, 1);
						else if(i0 <= 47 && i0 < 56)
							st.giveItems(15820, 1);
						else if(i0 <= 56 && i0 < 65)
							st.giveItems(15821, 1);
						else if(i0 <= 65 && i0 < 74)
							st.giveItems(15822, 1);
						else if(i0 <= 74 && i0 < 83)
							st.giveItems(15823, 1);
						else if(i0 <= 83 && i0 < 92)
							st.giveItems(15824, 1);
						else
							st.giveItems(15825, 1);
					}
					else
					{
						int i0 = Rnd.get(100);
						if(i0 < 9)
							st.giveItems(15634, 1);
						else if(i0 <= 9 && i0 < 18)
							st.giveItems(15635, 1);
						else if(i0 <= 18 && i0 < 27)
							st.giveItems(15636, 1);
						else if(i0 <= 27 && i0 < 36)
							st.giveItems(15637, 1);
						else if(i0 <= 38 && i0 < 47)
							st.giveItems(15638, 1);
						else if(i0 <= 47 && i0 < 56)
							st.giveItems(15639, 1);
						else if(i0 <= 56 && i0 < 65)
							st.giveItems(15640, 1);
						else if(i0 <= 65 && i0 < 74)
							st.giveItems(15641, 1);
						else if(i0 <= 74 && i0 < 83)
							st.giveItems(15642, 1);
						else if(i0 <= 83 && i0 < 92)
							st.giveItems(15643, 1);
						else
							st.giveItems(15644, 1);
					}
					st.getPlayer().setVar("q453", "1", (int) (resetTime.timeNextUsage(System.currentTimeMillis()) / 1000));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:clemis_q0453_14.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs1.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
					if(party.get(i).getInt("ex") != 1)
						party.remove(i);

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					qs.set("m" + _mobs1.get(npc.getNpcId()), qs.getInt("m" + _mobs1.get(npc.getNpcId())) + 1);
					if(qs.getInt("m22746") >= 15 && qs.getInt("m22747") >= 15 && qs.getInt("m22748") >= 15 && qs.getInt("m22749") >= 15)
					{
						qs.setMemoState(2);
						qs.setCond(5);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
		}
		else if(_mobs2.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
					if(party.get(i).getInt("ex") != 2)
						party.remove(i);

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					qs.set("m" + _mobs2.get(npc.getNpcId()), qs.getInt("m" + _mobs2.get(npc.getNpcId())) + 1);
					if(qs.getInt("m22754") >= 20 && qs.getInt("m22755") >= 20 && qs.getInt("m22756") >= 20)
					{
						qs.setMemoState(2);
						qs.setCond(5);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
		}
		else if(_mobs3.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
					if(party.get(i).getInt("ex") != 3)
						party.remove(i);

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					qs.set("m" + _mobs3.get(npc.getNpcId()), qs.getInt("m" + _mobs3.get(npc.getNpcId())) + 1);
					if(qs.getInt("m22760") >= 20 && qs.getInt("m22761") >= 20 && qs.getInt("m22762") >= 20)
					{
						qs.setMemoState(2);
						qs.setCond(5);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}
