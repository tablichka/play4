package quests._10501_ZakenEmbroideredSoulCloak;

import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 15.09.11 18:42
 */
public class _10501_ZakenEmbroideredSoulCloak extends Quest
{
	// NPC
	private static final int weaver_wolf_adams = 32612;

	// Mobs
	private static final int zaken_boss_83 = 29181;

	// Itema
	private static final int g_zaken_cloak_b = 21719;
	private static final int g_q_soulpiece_of_zaken = 21722;

	public _10501_ZakenEmbroideredSoulCloak()
	{
		super(10501, "_10501_ZakenEmbroideredSoulCloak", "Zaken Embroidered Soul Cloak");
		addStartNpc(weaver_wolf_adams);
		addTalkId(weaver_wolf_adams);
		addQuestItem(g_q_soulpiece_of_zaken);
		addKillId(zaken_boss_83);
	}
	
	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == weaver_wolf_adams)
		{
			if(st.isCompleted())
				return "npchtm:weaver_wolf_adams_q10501_03.htm";

			if(st.isCreated())
				if(talker.getLevel() >= 78)
					return "weaver_wolf_adams_q10501_01.htm";
				else
					return "weaver_wolf_adams_q10501_02.htm";

			if(st.isStarted() && st.getMemoState() == 1)
			{
				if(st.getQuestItemsCount(g_q_soulpiece_of_zaken) <= 19)
					return "npchtm:weaver_wolf_adams_q10501_05.htm";
				else
				{
					st.giveItems(g_zaken_cloak_b, 1);
					st.takeItems(g_q_soulpiece_of_zaken, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:weaver_wolf_adams_q10501_06.htm";
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

		if(npc.getNpcId() == weaver_wolf_adams)
		{
			if(reply == 10501)
			{
				if(st.isCreated() && talker.getLevel() >= 78)
				{
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("weaver_wolf_adams_q10501_04.htm", talker);
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
			{
				for(L2Party pp : cc.getParties())
					for(L2Player member : pp.getPartyMembers())
						if(npc.isInRange(member, 1500))
						{
							QuestState qs = member.getQuestState(10501);
							if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(g_q_soulpiece_of_zaken) < 20 && qs.rollAndGiveLimited(g_q_soulpiece_of_zaken, Rnd.chance(50) ? 2 : 1, 100, 20))
								if(qs.getQuestItemsCount(g_q_soulpiece_of_zaken) >= 20)
								{
									qs.setCond(2);
									showQuestMark(member);
									qs.playSound(SOUND_MIDDLE);
								}
								else
									qs.playSound(SOUND_ITEMGET);
						}
			}
			else
				for(L2Player member : party.getPartyMembers())
					if(npc.isInRange(member, 1500))
					{
						QuestState qs = member.getQuestState(10501);
						if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(g_q_soulpiece_of_zaken) < 20 && qs.rollAndGiveLimited(g_q_soulpiece_of_zaken, Rnd.chance(50) ? 2 : 1, 100, 20))
							if(qs.getQuestItemsCount(g_q_soulpiece_of_zaken) >= 20)
							{
								qs.setCond(2);
								showQuestMark(member);
								qs.playSound(SOUND_MIDDLE);
							}
							else
								qs.playSound(SOUND_ITEMGET);
					}
		}
		else
		{
			QuestState qs = killer.getQuestState(10501);
			if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(g_q_soulpiece_of_zaken) < 20 && qs.rollAndGiveLimited(g_q_soulpiece_of_zaken, Rnd.chance(50) ? 2 : 1, 100, 20))
				if(qs.getQuestItemsCount(g_q_soulpiece_of_zaken) >= 20)
				{
					qs.setCond(2);
					showQuestMark(killer);
					qs.playSound(SOUND_MIDDLE);
				}
				else
					qs.playSound(SOUND_ITEMGET);
		}
	}
}
