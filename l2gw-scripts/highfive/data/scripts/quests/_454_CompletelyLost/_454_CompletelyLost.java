package quests._454_CompletelyLost;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.crontab.Crontab;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 06.02.11 16:21
 */
public class _454_CompletelyLost extends Quest
{
	// NPCs
	private static final int wunded_gracia_soldier = 32738;
	private static final int ermian = 32736;

	private static final Crontab resetTime = new Crontab("30 6 * * *");

	public _454_CompletelyLost()
	{
		super(454, "_454_CompletelyLost", "Completely Lost");

		addStartNpc(wunded_gracia_soldier);
		addTalkId(wunded_gracia_soldier, ermian);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();
		int npcId = npc.getNpcId();

		if(npcId == wunded_gracia_soldier)
		{
			if(st.isCreated())
			{
				if(reply == 454 && player.getLevel() >= 84 && !player.getVarB("q454"))
				{
					L2Player p1 = L2ObjectsStorage.getAsPlayer(npc.c_ai0);
					boolean partyMember = p1 != null && p1.getParty() != null && p1.isPartyMember(player);
					if(npc.i_quest0 == 0)
					{
						npc.c_ai0 = player.getStoredId();
						npc.i_quest0 = 1;
						st.setMemoState(1);
						st.setCond(1);
						st.setState(STARTED);
						st.playSound(SOUND_ACCEPT);
						showQuestPage("wunded_gracia_soldier_q0454_04.htm", player);
					}
					else if(partyMember)
					{
						showHtmlFile(st.getPlayer(), "wunded_gracia_soldier_q0454_04a.htm", new String[]{"<?reader?>"}, new String[]{p1.getName()}, true);
						st.setMemoState(1);
						st.setCond(1);
						st.setState(STARTED);
						st.playSound(SOUND_ACCEPT);
					}
					else
						showHtmlFile(st.getPlayer(), "wunded_gracia_soldier_q0454_01b.htm", new String[]{"<?reader?>"}, new String[]{p1 != null ? p1.getName() : ""}, true);
				}
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 10)
				{
					L2Player c1 = L2ObjectsStorage.getAsPlayer(npc.c_ai0);
					if(c1 != null)
					{
						if(c1.getParty() != null)
							showPage("wunded_gracia_soldier_q0454_05a.htm", player);
						else
						{
							st.setMemoState(2);
							showPage("wunded_gracia_soldier_q0454_06.htm", player);
							npc.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 20091028);
						}
					}

				}
				else if(reply == 1)
				{
					st.setMemoState(2);
					showPage("wunded_gracia_soldier_q0454_06.htm", player);
					npc.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 20091028);
					L2Player c1 = L2ObjectsStorage.getAsPlayer(npc.c_ai0);
					if(c1 != null && c1.getParty() != null)
						for(L2Player member : c1.getParty().getPartyMembers())
						{
							QuestState qs = member.getQuestState(getName());
							if(qs != null && qs.getMemoState() == 1)
								qs.setMemoState(2);
						}
				}
				else if(reply == 2)
					showPage("wunded_gracia_soldier_q0454_07.htm", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == wunded_gracia_soldier)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 84 && !st.getPlayer().getVarB("q454"))
				{
					L2Player player = L2ObjectsStorage.getAsPlayer(npc.c_ai0);
					boolean partyMember = player != null && player.getParty() != null && player.isPartyMember(st.getPlayer());
					if(npc.i_quest0 == 0)
						return "wunded_gracia_soldier_q0454_01.htm";
					if(npc.i_quest0 == 99)
						return "wunded_gracia_soldier_q0454_01c.htm";
					if(partyMember)
					{
						showHtmlFile(st.getPlayer(), "wunded_gracia_soldier_q0454_01a.htm", new String[]{"<?reader?>", "<?name?>"}, new String[]{player.getName(), st.getPlayer().getName()}, true);
						return null;
					}
					else
					{
						showHtmlFile(st.getPlayer(), "wunded_gracia_soldier_q0454_01b.htm", new String[]{"<?reader?>"}, new String[]{player != null ? player.getName() : ""}, true);
						return null;
					}
				}

				st.exitCurrentQuest(true);

				if(st.getPlayer().getVarB("q454"))
					return "wunded_gracia_soldier_q0454_02.htm";

				return "wunded_gracia_soldier_q0454_03.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:wunded_gracia_soldier_q0454_05.htm";
				if(cond == 2)
					return "npchtm:wunded_gracia_soldier_q0454_08.htm";
			}
		}
		else if(npcId == ermian && st.isStarted())
		{
			if(cond == 3)
			{
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				st.getPlayer().setVar("q454", "1", (int) (resetTime.timeNextUsage(System.currentTimeMillis()) / 1000));
				return "npchtm:ermian_q0454_02.htm";
			}
			if(cond == 4)
			{
				int i1 = Rnd.get(3);
				if(i1 == 0)
				{
					if(Rnd.get(2) == 1)
					{
						int i0 = Rnd.get(100);
						if(i0 < 11)
							st.giveItems(15792, 1);
						else if(i0 <= 11 && i0 < 22)
							st.giveItems(15798, 1);
						else if(i0 <= 22 && i0 < 33)
							st.giveItems(15795, 1);
						else if(i0 <= 33 && i0 < 44)
							st.giveItems(15801, 1);
						else if(i0 <= 44 && i0 < 55)
							st.giveItems(15808, 1);
						else if(i0 <= 55 && i0 < 66)
							st.giveItems(15804, 1);
						else if(i0 <= 66 && i0 < 77)
							st.giveItems(15809, 1);
						else if(i0 <= 77 && i0 < 88)
							st.giveItems(15810, 1);
						else
							st.giveItems(15811, 1);
					}
					else
					{
						int i0 = Rnd.get(100);
						if(i0 < 11)
							st.giveItems(15660, 3);
						else if(i0 <= 11 && i0 < 22)
							st.giveItems(15666, 3);
						else if(i0 <= 22 && i0 < 33)
							st.giveItems(15663, 3);
						else if(i0 <= 33 && i0 < 44)
							st.giveItems(15667, 3);
						else if(i0 <= 44 && i0 < 55)
							st.giveItems(15669, 3);
						else if(i0 <= 55 && i0 < 66)
							st.giveItems(15668, 3);
						else if(i0 <= 66 && i0 < 77)
							st.giveItems(15769, 3);
						else if(i0 <= 77 && i0 < 88)
							st.giveItems(15770, 3);
						else
							st.giveItems(15771, 3);
					}
				}
				else if(i1 == 1)
				{
					if(Rnd.get(2) == 1)
					{
						int i0 = Rnd.get(100);
						if(i0 < 12)
							st.giveItems(15805, 1);
						else if(i0 <= 12 && i0 < 24)
							st.giveItems(15796, 1);
						else if(i0 <= 24 && i0 < 36)
							st.giveItems(15793, 1);
						else if(i0 <= 36 && i0 < 48)
							st.giveItems(15799, 1);
						else if(i0 <= 48 && i0 < 60)
							st.giveItems(15802, 1);
						else if(i0 <= 60 && i0 < 72)
							st.giveItems(15809, 1);
						else if(i0 <= 72 && i0 < 84)
							st.giveItems(15810, 1);
						else
							st.giveItems(15811, 1);
					}
					else
					{
						int i0 = Rnd.get(100);
						if(i0 < 12)
							st.giveItems(15672, 3);
						else if(i0 <= 12 && i0 < 24)
							st.giveItems(15664, 3);
						else if(i0 <= 24 && i0 < 36)
							st.giveItems(15661, 3);
						else if(i0 <= 36 && i0 < 48)
							st.giveItems(15670, 3);
						else if(i0 <= 48 && i0 < 60)
							st.giveItems(15671, 3);
						else if(i0 <= 60 && i0 < 72)
							st.giveItems(15769, 3);
						else if(i0 <= 72 && i0 < 84)
							st.giveItems(15770, 3);
						else
							st.giveItems(15771, 3);
					}
				}
				else if(Rnd.get(2) == 1)
				{
					int i0 = Rnd.get(100);
					if(i0 < 11)
						st.giveItems(15800, 1);
					else if(i0 <= 11 && i0 < 22)
						st.giveItems(15803, 1);
					else if(i0 <= 22 && i0 < 33)
						st.giveItems(15806, 1);
					else if(i0 <= 33 && i0 < 44)
						st.giveItems(15807, 1);
					else if(i0 <= 44 && i0 < 55)
						st.giveItems(15797, 1);
					else if(i0 <= 55 && i0 < 66)
						st.giveItems(15794, 1);
					else if(i0 <= 66 && i0 < 77)
						st.giveItems(15809, 1);
					else if(i0 <= 77 && i0 < 88)
						st.giveItems(15810, 1);
					else
						st.giveItems(15811, 1);
				}
				else
				{
					int i0 = Rnd.get(100);
					if(i0 < 11)
						st.giveItems(15673, 3);
					else if(i0 <= 11 && i0 < 22)
						st.giveItems(15674, 3);
					else if(i0 <= 22 && i0 < 33)
						st.giveItems(15675, 3);
					else if(i0 <= 33 && i0 < 44)
						st.giveItems(15691, 3);
					else if(i0 <= 44 && i0 < 55)
						st.giveItems(15665, 3);
					else if(i0 <= 55 && i0 < 66)
						st.giveItems(15662, 3);
					else if(i0 <= 66 && i0 < 77)
						st.giveItems(15769, 3);
					else if(i0 <= 77 && i0 < 88)
						st.giveItems(15770, 3);
					else
						st.giveItems(15771, 3);
				}

				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				st.getPlayer().setVar("q454", "1", (int) (resetTime.timeNextUsage(System.currentTimeMillis()) / 1000));
				return "npchtm:ermian_q0454_03.htm";
			}
			if(st.getPlayer().getVarB("q454"))
				return "npchtm:ermian_q0454_04.htm";
		}

		return "noquest";
	}
}
