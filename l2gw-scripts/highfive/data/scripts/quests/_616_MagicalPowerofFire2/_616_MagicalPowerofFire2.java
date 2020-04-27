package quests._616_MagicalPowerofFire2;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashSet;

/**
 * @author: rage
 * @date: 29.01.12 12:45
 */
public class _616_MagicalPowerofFire2 extends Quest
{
	// NPC
	private static final int shaman_udan = 31379;
	private static final int totem_of_ketra = 31558;

	// Mobs
	private static final int flame_spirit_nastron = 25306;
	private static final int[] varka_mobs = new int[]{21375, 21374, 25315, 25312, 21370, 25309, 21372, 21371, 21365,
			21351, 21369, 21350, 21354, 21361, 21360, 21366, 21368, 21357, 21353, 21364, 21362, 21355, 21358, 21373,
			25316};

	public _616_MagicalPowerofFire2()
	{
		super();
		addStartNpc(shaman_udan);
		addTalkId(shaman_udan, totem_of_ketra);
		addKillId(varka_mobs);
		addKillId(flame_spirit_nastron);
		addQuestItem(7244);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == shaman_udan)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 75)
				{
					if(st.getQuestItemsCount(7243) >= 1)
						return "shaman_udan_q0616_0101.htm";

					return "shaman_udan_q0616_0102.htm";
				}

				return "shaman_udan_q0616_0103.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:shaman_udan_q0616_0105.htm";
				if(st.getMemoState() == 22)
				{
					if(st.getQuestItemsCount(7244) >= 1)
						return "npchtm:shaman_udan_q0616_0201.htm";

					return "npchtm:shaman_udan_q0616_0202.htm";
				}
			}
		}
		else if(npc.getNpcId() == totem_of_ketra)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:totem_of_ketra_q0616_0101.htm";
				if(st.getMemoState() == 21)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						npc.createOnePrivate(flame_spirit_nastron, "FlameSpiritNastron", 0, 0, 142528, -82528, -6496, 0, npc.getStoredId(), talker.getObjectId(), 0);
						npc.onDecay();
						return "npchtm:totem_of_ketra_q0616_0201.htm";
					}

					return "npchtm:totem_of_ketra_q0616_0202.htm";
				}
				if(st.getMemoState() == 22)
					return "npchtm:totem_of_ketra_q0616_0204.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == shaman_udan)
		{
			if(reply == 616)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(11);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("shaman_udan_q0616_0104.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted())
			{
				if(st.getQuestItemsCount(7244) >= 1)
				{
					st.takeItems(7244, -1);
					st.addExpAndSp(10000, 0);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("shaman_udan_q0616_0301.htm", talker);
				}
				else
				{
					showPage("shaman_udan_q0616_0302.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == totem_of_ketra)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(7243) >= 1)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						st.takeItems(7243, 1);
						showPage("totem_of_ketra_q0616_0201.htm", talker);
						npc.createOnePrivate(flame_spirit_nastron, "FlameSpiritNastron", 0, 0, 142528, -82528, -6496, 0, npc.getStoredId(), talker.getObjectId(), 0);
						npc.onDecay();
						st.setMemoState(21);
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("totem_of_ketra_q0616_0202.htm", talker);
					}
				}
				else
				{
					showPage("totem_of_ketra_q0616_0203.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == flame_spirit_nastron)
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
						st.giveItems(7244, 1);
						st.playSound(SOUND_ITEMGET);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.setMemoState(22);
					}
					else if(st.getQuestItemsCount(7243) >= 1)
					{
						st.takeItems(7243, 1);
						st.giveItems(7244, 1);
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
		else if(contains(varka_mobs, npc.getNpcId()))
		{
			QuestState st = killer.getQuestState(616);
			if(st != null)
			{
				st.takeItems(7244, -1);
				st.exitCurrentQuest(true);
			}
		}
	}
}