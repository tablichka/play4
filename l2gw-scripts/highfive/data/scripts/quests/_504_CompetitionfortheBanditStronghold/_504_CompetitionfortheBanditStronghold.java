package quests._504_CompetitionfortheBanditStronghold;

import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.siege.Siege;
import ru.l2gw.gameserver.model.entity.siege.SiegeUnit;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * Квест на захват Клан Холла Bandit Stronghold Reserve (35)
 *
 * @author PaInKiLlEr
 */
public class _504_CompetitionfortheBanditStronghold extends Quest
{
	//NPC
	private static final int MESSENGER = 35437;
	//MOBS
	private static int TARLK_BUGBEAR = 20570;
	private static int TARLK_BASILISK = 20573;
	private static int ELDER_TARLK_BASILISK = 20574;

	//ITEMS
	private static int AMULET = 4332;

	//SHANCE
	private static int AMULET_CHANCE = 10;

	public _504_CompetitionfortheBanditStronghold()
	{
		super(504, "_504_CompetitionfortheBanditStronghold", "Competition for the Bandit Stronghold;"); // Party true

		addStartNpc(MESSENGER);
		addTalkId(MESSENGER);
		addKillId(TARLK_BUGBEAR);
		addKillId(TARLK_BASILISK);
		addKillId(ELDER_TARLK_BASILISK);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		SiegeUnit ch = ResidenceManager.getInstance().getBuildingById(35);
		Siege chSiege = ch.getSiege();

		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("35437-02.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			if(st.getQuestItemsCount(AMULET) > 0)
				st.takeItems(AMULET, -1);
		}
		if(event.equalsIgnoreCase("35437-03.htm"))
		{
			st.takeItems(AMULET, -1);
			st.set("cond", "3");
		}
		if(event.equalsIgnoreCase("35437-11.htm"))
		{
			st.takeItems(AMULET, -1);
			st.exitCurrentQuest(true);
		}
		if(event.equalsIgnoreCase("35437-12.htm"))
		{
			st.setState(STARTED);
			if(st.getQuestItemsCount(AMULET) > 0)
				st.takeItems(AMULET, -1);
			if(st.isCreated())
			{
				st.takeItems(57, 200000);
				st.set("cond", "1");
				st.set("SiegePeriod", String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000));
			}
			if(cond == 1)
			{
				if(st.get("SiegePeriod") != String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000))
				{
					st.takeItems(57, 200000);
					st.set("SiegePeriod", String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000));
				}
			}
			if(cond == 3)
				st.set("SiegePeriod", String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000));
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(npcId == MESSENGER)
		{
			SiegeUnit unit = ResidenceManager.getInstance().getResidenceByOwner(st.getPlayer().getClanId(), true);
			if(unit != null)
			{
				htmltext = "nohaveCh.htm";
				st.exitCurrentQuest(true);
			}

			SiegeUnit ch = ResidenceManager.getInstance().getBuildingById(35);
			Siege chSiege = ch.getSiege();
			if(chSiege != null)
			{
				if(st.isCreated())
				{
					if(st.get("SiegePeriod") == null)
						st.set("SiegePeriod", String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000));

					if(!st.getPlayer().isClanLeader())
					{
						if(st.getPlayer().getClanId() != 0)
						{
							L2Clan clan = st.getPlayer().getClan();
							if(clan.getLeader().getPlayer() != null)
								if(clan.getLeader().getPlayer().getQuestState(getName()) != null)
									st.set("cond", String.valueOf(clan.getLeader().getPlayer().getQuestState(getName()).getInt("cond")));
						}
					}
					else
						htmltext = "35437-05.htm";
				}
				if(cond > 0)
				{
					if(st.get("SiegePeriod") != String.valueOf(chSiege.getSiegeDate().getTimeInMillis() / 1000))
						htmltext = "35437-11.htm";
					else if(cond == 1)
						htmltext = "35437-12.htm";
					else if(cond == 2)
					{
						if(st.getQuestItemsCount(AMULET) < 30)
							htmltext = "noitem.htm";
						else
							htmltext = "35437-03.htm";
					}
					if(cond == 3)
						htmltext = "35437-12.htm";
				}
				else if(!st.getPlayer().isClanLeader() && st.isCreated())
				{
					htmltext = "35437-05.htm";
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pm = new GArray<QuestState>();

		for(QuestState st : getPartyMembersWithQuest(killer, 2))
			if(st.getQuestItemsCount(AMULET) < 30)
				pm.add(st);

		if(!pm.isEmpty())
		{
			QuestState qs = pm.get(Rnd.get(pm.size()));
			if(qs.rollAndGiveLimited(AMULET, 1, AMULET_CHANCE, 30))
				qs.playSound(qs.getQuestItemsCount(AMULET) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
	}
}