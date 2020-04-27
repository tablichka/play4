package quests._610_MagicalPowerofWater2;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashSet;

/**
 * @author: rage
 * @date: 26.01.12 19:18
 */
public class _610_MagicalPowerofWater2 extends Quest
{
	// NPC
	private static final int shaman_asefa = 31372;
	private static final int totem_of_barka = 31560;

	// Mobs
	private static final int ketra_mobs[] = new int[]{25306, 25305, 25302, 21344, 25299, 21346, 21345, 21332, 21336, 21324, 21343, 21334,
			21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325, 21349, 21348};
	private static final int water_spirit_ashutar = 25316;

	public _610_MagicalPowerofWater2()
	{
		super(610, "_610_MagicalPowerofWater2", "Magical Power of Water part 2");
		addStartNpc(shaman_asefa);
		addTalkId(shaman_asefa, totem_of_barka);

		addKillId(ketra_mobs);
		addKillId(water_spirit_ashutar);
		addQuestItem(7239);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == shaman_asefa)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 75)
				{
					if(st.getQuestItemsCount(7238) >= 1)
						return "shaman_asefa_q0610_0101.htm";
					return "npchtm:shaman_asefa_q0610_0102.htm";
				}

				return "shaman_asefa_q0610_0103.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:shaman_asefa_q0610_0105.htm";
				if(st.getMemoState() == 22)
				{
					if(st.getQuestItemsCount(7239) >= 1)
						return "npchtm:shaman_asefa_q0610_0201.htm";

					return "npchtm:shaman_asefa_q0610_0202.htm";
				}
			}
		}
		else if(npc.getNpcId() == totem_of_barka)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:totem_of_barka_q0610_0101.htm";
				if(st.getMemoState() == 21)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						npc.createOnePrivate(25316, null, 0, 0, 104825, -36926, -1136, 0, npc.getStoredId(), talker.getObjectId(), 0);
						npc.onDecay();
						return "npchtm:totem_of_barka_q0610_0201.htm";
					}
					return "npchtm:totem_of_barka_q0610_0202.htm";
				}
				if(st.getMemoState() == 22)
					return "npchtm:totem_of_barka_q0610_0204.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == shaman_asefa)
		{
			if(reply == 610)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(11);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("shaman_asefa_q0610_0104.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted())
			{
				if(st.getQuestItemsCount(7239) >= 1)
				{
					st.takeItems(7239, -1);
					st.addExpAndSp(10000, 0);
					st.exitCurrentQuest(true);
					showPage("shaman_asefa_q0610_0301.htm", talker);
				}
				else
				{
					showPage("shaman_asefa_q0610_0302.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == totem_of_barka)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(7238) >= 1)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						st.takeItems(7238, 1);
						showPage("totem_of_barka_q0610_0201.htm", talker);
						npc.createOnePrivate(25316, null, 0, 0, 104825, -36926, -1136, 0, npc.getStoredId(), talker.getObjectId(), 0);
						npc.onDecay();
						st.setMemoState(21);
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("totem_of_barka_q0610_0202.htm", talker);
					}
				}
				else
				{
					showPage("totem_of_barka_q0610_0203.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == water_spirit_ashutar)
		{
			HashSet<QuestState> party = new HashSet<>(9);
			party.addAll(getPartyMembersWithMemoState(killer, 11));
			party.addAll(getPartyMembersWithMemoState(killer, 21));
			if(!party.isEmpty())
			{
				for(QuestState st : party)
				{
					if(st.getPlayer().getObjectId() == npc.param2 || st.getMemoState() == 21)
					{
						st.giveItems(7239, 1);
						st.playSound(SOUND_ITEMGET);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.setMemoState(22);
					}
					else if(st.getQuestItemsCount(7238) >= 1)
					{
						st.takeItems(7238, 1);
						st.giveItems(7239, 1);
						st.playSound(SOUND_ITEMGET);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.setMemoState(22);
					}
				}
			}
			L2NpcInstance totem = L2ObjectsStorage.getAsNpc(npc.param1);
			if(totem != null)
				totem.av_quest0.set(0);
			else
				_log.info(this + " totem is null!!");
		}
		else if(contains(ketra_mobs, npc.getNpcId()))
		{
			QuestState st = killer.getQuestState(610);
			if(st != null)
			{
				st.takeItems(7239, -1);
				st.exitCurrentQuest(true);
			}
		}
	}
}