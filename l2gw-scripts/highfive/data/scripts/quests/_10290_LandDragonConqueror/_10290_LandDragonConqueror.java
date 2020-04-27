package quests._10290_LandDragonConqueror;

import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 09.10.11 20:17
 */
public class _10290_LandDragonConqueror extends Quest
{
	// NPC
	private static final int watcher_antaras_theodric = 30755;

	// Mobs
	private static final int antaras = 29019;
	private static final int antaras_min = 29066;
	private static final int antaras_normal = 29067;
	private static final int antaras_max = 29068;

	// Items
	private static final int q_shabby_necklace = 15522;
	private static final int q_voucher_of_miracle = 15523;
	private static final int q_portal_stone_1 = 3865;
	private static final int circlet_of_antaras_slayer = 8568;

	public _10290_LandDragonConqueror()
	{
		super(10290, "_10290_LandDragonConqueror", "Land Dragon Conqueror");
		addStartNpc(watcher_antaras_theodric);
		addTalkId(watcher_antaras_theodric);
		addKillId(antaras, antaras_min, antaras_normal, antaras_max);
		addQuestItem(q_shabby_necklace, q_voucher_of_miracle);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == watcher_antaras_theodric)
		{
			if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) > 0 && talker.getLevel() >= 83)
				return "watcher_antaras_theodric_q10290_01.htm";
			if(st.isCreated() && talker.getLevel() < 83)
				return "watcher_antaras_theodric_q10290_02.htm";
			if(st.isCompleted())
				return "watcher_antaras_theodric_q10290_03.htm";
			if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) < 1 && talker.getLevel() >= 83)
				return "watcher_antaras_theodric_q10290_04.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_shabby_necklace) >= 1)
					return "npchtm:watcher_antaras_theodric_q10290_08.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_shabby_necklace) == 0 && st.getQuestItemsCount(q_voucher_of_miracle) == 0)
				{
					st.giveItems(q_shabby_necklace, 1);
					return "npchtm:watcher_antaras_theodric_q10290_09.htm";
				}
				if(st.getMemoState() == 2)
				{
					st.giveItems(circlet_of_antaras_slayer, 1);
					st.rollAndGive(57, 131236, 100);
					st.addExpAndSp(702557, 76334);
					st.takeItems(q_voucher_of_miracle, -1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:watcher_antaras_theodric_q10290_10.htm";
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

		if(npc.getNpcId() == watcher_antaras_theodric)
		{
			if(reply == 10290)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) > 0 && talker.getLevel() >= 83)
				{
					st.giveItems(q_shabby_necklace, 1);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("watcher_antaras_theodric_q10290_07.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) > 0 && talker.getLevel() >= 83)
				{
					showPage("watcher_antaras_theodric_q10290_05.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) > 0 && talker.getLevel() >= 83)
				{
					showPage("watcher_antaras_theodric_q10290_06.htm", talker);
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
							QuestState qs = member.getQuestState(10290);
							if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(q_shabby_necklace) > 0)
								{
									qs.takeItems(q_shabby_necklace, -1);
									qs.giveItems(q_voucher_of_miracle, 1);
									qs.setMemoState(2);
									qs.setCond(2);
									showQuestMark(member);
									qs.playSound(SOUND_MIDDLE);
								}
						}
		}
	}
}