package quests._10291_FireDragonDestroyer;

import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 09.10.11 20:38
 */
public class _10291_FireDragonDestroyer extends Quest
{
	// NPC
	private static final int watcher_valakas_klein = 31540;

	// Mobs
	private static final int valakas = 29028;

	// Items
	private static final int q_floating_stone = 7267;
	private static final int q_poor_necklace = 15524;
	private static final int q_voucher_of_valor = 15525;
	private static final int circlet_of_valakas_slayer = 8567;

	public _10291_FireDragonDestroyer()
	{
		super(10291, "_10291_FireDragonDestroyer", "Fire Dragon Destroyer");
		addStartNpc(watcher_valakas_klein);
		addTalkId(watcher_valakas_klein);
		addKillId(valakas);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		
		if(npc.getNpcId() == watcher_valakas_klein)
		{
			if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) > 0 && talker.getLevel() >= 83)
				return "watcher_valakas_klein_q10291_01.htm";
			if(st.isCreated() && talker.getLevel() < 83)
				return "watcher_valakas_klein_q10291_02.htm";
			if(st.isCompleted())
				return "watcher_valakas_klein_q10291_03.htm";
			if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) < 1 && talker.getLevel() >= 83)
				return "watcher_valakas_klein_q10291_04.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_poor_necklace) >= 1)
					return "npchtm:watcher_valakas_klein_q10291_08.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_poor_necklace) == 0 && st.getQuestItemsCount(q_voucher_of_valor) == 0)
				{
					st.giveItems(q_poor_necklace, 1);
					return "npchtm:watcher_valakas_klein_q10291_09.htm";
				}
				if(st.getMemoState() == 2)
				{
					st.takeItems(q_voucher_of_valor, -1);
					st.rollAndGive(57, 126549, 100);
					st.addExpAndSp(717291, 77397);
					st.giveItems(circlet_of_valakas_slayer, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:watcher_valakas_klein_q10291_10.htm";
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
			if(reply == 10291)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) > 0 && talker.getLevel() >= 83)
				{
					st.giveItems(q_poor_necklace, 1);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("watcher_valakas_klein_q10291_07.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) > 0 && talker.getLevel() >= 83)
				{
					showPage("watcher_valakas_klein_q10291_05.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_floating_stone) > 0 && talker.getLevel() >= 83)
				{
					showPage("watcher_valakas_klein_q10291_06.htm", talker);
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
							QuestState qs = member.getQuestState(10291);
							if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(q_poor_necklace) > 0)
								{
									qs.takeItems(q_poor_necklace, -1);
									qs.giveItems(q_voucher_of_valor, 1);
									qs.setMemoState(2);
									qs.setCond(2);
									showQuestMark(member);
									qs.playSound(SOUND_MIDDLE);
								}
						}
		}
	}
}
