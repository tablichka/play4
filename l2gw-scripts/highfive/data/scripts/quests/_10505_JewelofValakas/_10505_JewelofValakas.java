package quests._10505_JewelofValakas;

import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.09.11 23:27
 */
public class _10505_JewelofValakas extends Quest
{
	// NPC
	private static final int watcher_valakas_klein = 31540;

	// Items
	private static final int q_floating_stone = 7267;
	private static final int q_g_empty_orb2 = 21906;
	private static final int q_brimful_orb2 = 21908;
	private static final int g_orb_of_fire_dragon1 = 21896;

	public _10505_JewelofValakas()
	{
		super(10505, "_10505_JewelofValakas", "Jewel of Valakas");
		addStartNpc(watcher_valakas_klein);
		addTalkId(watcher_valakas_klein);
		addKillId(29028);
		addQuestItem(q_g_empty_orb2, q_brimful_orb2);
	}
	
	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == watcher_valakas_klein)
		{
			if(st.isCompleted())
				return "npchtm:watcher_valakas_klein_q10505_03.htm";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 84 && st.getQuestItemsCount(q_floating_stone) >= 1)
					return "watcher_valakas_klein_q10505_01.htm";
				if(talker.getLevel() < 84)
					return "watcher_valakas_klein_q10505_02.htm";
				if(talker.getLevel() >= 84 && st.getQuestItemsCount(q_floating_stone) < 1)
					return "watcher_valakas_klein_q10505_04.htm";
			}

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_g_empty_orb2) >= 1)
					return "npchtm:watcher_valakas_klein_q10505_08.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_g_empty_orb2) == 0)
				{
					st.giveItems(q_g_empty_orb2, 1);
					return "npchtm:watcher_valakas_klein_q10505_09.htm";
				}
				if(st.getMemoState() == 2)
				{
					st.giveItems(g_orb_of_fire_dragon1, 1);
					st.takeItems(q_brimful_orb2, 1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:watcher_valakas_klein_q10505_10.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == watcher_valakas_klein)
		{
			if(st.isCompleted())
			{
				showPage("watcher_valakas_klein_q10505_03.htm", talker);
				return;
			}

			if(reply == 10505)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) >= 1 && talker.getLevel() >= 84)
				{
					st.giveItems(q_g_empty_orb2, 1);
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("watcher_valakas_klein_q10505_07.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) >= 1 && talker.getLevel() >= 84)
				{
					showPage("watcher_valakas_klein_q10505_05.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) >= 1 && talker.getLevel() >= 84)
				{
					showQuestPage("watcher_valakas_klein_q10505_06.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Party party = killer.getParty();
		if(party != null)
		{
			L2CommandChannel cc = party.getCommandChannel();
			if(cc != null)
				for(L2Party pp : cc.getParties())
					for(L2Player member : pp.getPartyMembers())
						if(npc.isInRange(member, 8000))
						{
							QuestState qs = member.getQuestState(10505);
							if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(q_g_empty_orb2) > 0)
								{
									qs.takeItems(q_g_empty_orb2, -1);
									qs.giveItems(q_brimful_orb2, 1);
									qs.setMemoState(2);
									qs.setCond(2);
									showQuestMark(member);
									qs.playSound(SOUND_MIDDLE);
								}
						}
		}
	}
}
