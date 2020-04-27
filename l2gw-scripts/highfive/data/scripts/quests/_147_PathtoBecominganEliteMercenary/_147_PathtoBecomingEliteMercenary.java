package quests._147_PathtoBecominganEliteMercenary;

import quests.TerritoryWar.TerritoryWarQuest;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 22.10.2010 0:07:27
 */
public class _147_PathtoBecomingEliteMercenary extends Quest
{
	// NPCs
	private static final int[] _merc = {36481, 36482, 36483, 36484, 36485, 36486, 36487, 36488, 36489};
	private static final int[] _catapults = {36499, 36500, 36501, 36502, 36503, 36504, 36505, 36506, 36507};
	// Items
	private static final int _cert_ordinary = 13766;
	private static final int _cert_elite = 13767;

	private static final int COND_ENEMY_KILLED = 2;
	private static final int COND_CATAPULT_KILLED = 4;
	private static final int COND_DONE = 8;

	public _147_PathtoBecomingEliteMercenary()
	{
		super(147, "_147_PathtoBecominganEliteMercenary", "Path to Becoming an Elite Mercenary");

		addStartNpc(_merc);
		addTalkId(_merc);
		addKillId(_catapults);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2NpcInstance npc = st.getPlayer().getLastNpc();

		if(contains(_merc, npc.getNpcId()))
		{
			if(event.equalsIgnoreCase("elite-02.htm"))
			{
				if(st.haveQuestItems(_cert_ordinary))
					return "npchtm:elite-02a.htm";

				st.giveItems(_cert_ordinary, 1);
			}
			else if(event.equalsIgnoreCase("elite-04.htm"))
			{
				st.setCond(0x80000001);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		L2Player player = st.getPlayer();
		if(contains(_merc, npc.getNpcId()))
		{
			if(st.isCreated())
			{
				if(player.getClan() != null && player.getClan().getHasCastle() > 0)
					return "npchtm:castle.htm";

				return "npchtm:elite-01.htm";
			}
			if(st.isStarted())
			{
				if((st.getCond() & COND_DONE) == COND_DONE)
				{
					st.takeItems(_cert_ordinary, -1);
					st.giveItems(_cert_elite, 1);
					st.exitCurrentQuest(false);
					return "npchtm:elite-06.htm";
				}
				return "npchtm:elite-05.htm";
			}
			if(st.isCompleted())
				return "completed";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> list = getPartyMembersWithQuest(killer, -1);
		int catapultTerritoryId = npc.getNpcId() - 36418;
		if(list.size() > 0)
			for(QuestState qs : list)
				if(qs.getPlayer().getTerritoryId() > 0 && qs.getPlayer().getTerritoryId() != catapultTerritoryId && (qs.getCond() & COND_CATAPULT_KILLED) != COND_CATAPULT_KILLED)
				{
					int cond = qs.getCond() | COND_CATAPULT_KILLED;
					if((cond & COND_ENEMY_KILLED) == COND_ENEMY_KILLED)
						cond |= COND_DONE;

					qs.setCond(cond);
					qs.playSound(SOUND_MIDDLE);
					qs.setState(STARTED);
				}
	}

	@Override
	public void onPlayerKill(L2Player killer, L2Player killed)
	{
		if(!TerritoryWarManager.getWar().isInProgress() || !TerritoryWarQuest.checkCondition(killer, killed))
			return;

		QuestState st = killer.getQuestState(getName());

		if(st != null && (st.getCond() & COND_ENEMY_KILLED) != COND_ENEMY_KILLED)
		{
			int kills = st.getInt("kills") + 1;
			st.set("kills", String.valueOf(kills));

			if(kills >= 10)
			{
				int cond = st.getCond() | COND_ENEMY_KILLED;
				if((cond & COND_CATAPULT_KILLED) == COND_CATAPULT_KILLED)
					cond |= COND_DONE;

				st.setCond(cond);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
	}

	@Override
	public void onPlayerKillParty(L2Player killer, L2Player killed, QuestState qs)
	{
		onPlayerKill(qs.getPlayer(), killed);
	}
}
