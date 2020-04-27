package quests._510_AClansPrestige;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 22.01.12 15:56
 */
public class _510_AClansPrestige extends Quest
{
	// NPC
	private static final int grandmagister_valdis = 31331;

	// Mobs
	private static final int tyrannosaurus = 22215;
	private static final int tyrannosaurus_soul = 22216;
	private static final int tyrannosaurus_s = 22217;

	public _510_AClansPrestige()
	{
		super(510, "_510_AClansPrestige", "A Clan's Prestige");
		addStartNpc(grandmagister_valdis);
		addTalkId(grandmagister_valdis);

		addKillId(tyrannosaurus, tyrannosaurus_soul, tyrannosaurus_s);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == grandmagister_valdis)
		{
			if(st.isCreated())
			{
				if(talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					if(clan != null)
					{
						if(clan.getLevel() >= 5)
							return "grandmagister_valdis_q0510_01.htm";

						return "grandmagister_valdis_q0510_03.htm";
					}
				}
				else
					return "grandmagister_valdis_q0510_04.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(8767) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0510_06.htm";
				if(!talker.isClanLeader())
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:grandmagister_valdis_q0510_07a.htm";
				}
				if(st.getMemoState() == 1 && st.getQuestItemsCount(8767) >= 1 && talker.isClanLeader())
				{
					int i0;
					if(st.getQuestItemsCount(8767) < 10)
						i0 = 15 * (int) st.getQuestItemsCount(8767) * 2;
					else
						i0 = (59 + 15 * (int) st.getQuestItemsCount(8767)) * 2;

					L2Clan clan = talker.getClan();
					clan.incReputation(i0, false, "Quest");
					st.playSound(SOUND_FANFARE1);
					Functions.showSystemMessageFStr(talker, 50851, String.valueOf(i0));
					st.takeItems(8767, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0510_07.htm";
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
		if(npc.getNpcId() == grandmagister_valdis)
		{
			if(reply == 510)
			{
				L2Clan clan = talker.getClan();
				if(st.isCreated() && clan != null && clan.getLevel() >= 5 && talker.isClanLeader())
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("grandmagister_valdis_q0510_05.htm", talker);
				}
			}
			else if(reply == 1 && st.isCreated() && talker.isClanLeader())
			{
				L2Clan pledge0 = talker.getClan();
				if(pledge0 != null && pledge0.getLevel() >= 5)
				{
					showQuestPage("grandmagister_valdis_q0510_02.htm", talker);
				}
			}
			else if(reply == 2 && st.isStarted() && talker.isClanLeader())
			{
				L2Clan pledge0 = talker.getClan();
				if(pledge0 != null && pledge0.getLevel() >= 5)
				{
					showPage("grandmagister_valdis_q0510_08.htm", talker);
				}
			}
			else if(reply == 3 && st.isStarted() && talker.isClanLeader())
			{
				L2Clan pledge0 = talker.getClan();
				if(pledge0 != null && pledge0.getLevel() >= 5)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("grandmagister_valdis_q0510_09.htm", talker);
					st.takeItems(8767, -1);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		if(c0 == null || !npc.isInRange(c0, 1500))
			return;

		// TODO: check npc.i_quest0 captured state
		QuestState st = c0.getQuestState(510);
		if(st != null && st.isStarted() && st.getMemoState() == 1 && c0.getClan().getLevel() >= 5)
		{
			st.giveItems(8767, 1);
			st.playSound(SOUND_ITEMGET);
		}
	}
}