package quests._10270_BirthoftheSeed;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 23.11.2010 15:59:56
 */
public class _10270_BirthoftheSeed extends Quest
{
	// NPCs
	private static final int PLENOS = 32563;
	private static final int ARTIUS = 32559;
	private static final int RELRIKIA = 32567;
	private static final int JINBI = 32566;
	// Items
	private static final int KLODEKUS_BADGE = 13868;
	private static final int KLANIKUS_BADGE = 13869;
	private static final int LICH_CRYSTAL = 13870;
	// MOBs
	private static final int COHEMENES1 = 25634;
	private static final int KLODEKUS = 25665;
	private static final int KLANIKUS = 25666;

	public _10270_BirthoftheSeed()
	{
		super(10270, "_10270_BirthoftheSeed", "Birth of the Seed");

		addStartNpc(PLENOS);
		addTalkId(PLENOS, ARTIUS, RELRIKIA, JINBI);
		addKillId(COHEMENES1, KLODEKUS, KLANIKUS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();

		if(event.equals("wharf_soldier_plenos_q10270_04.htm"))
		{
			if(st.isCreated() && player.getLevel() >= 75)
				return event;
		}
		else if(event.equals("wharf_soldier_plenos_q10270_05.htm"))
		{
			if(st.isCreated() && player.getLevel() >= 75)
			{
				st.setCond(1);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return event;
			}
		}
		else if(event.equals("warmage_artius_q10270_03.htm"))
		{
			if(st.getCond() == 1)
			{
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("warmage_artius_q10270_09.htm"))
		{
			QuestState qs = player.getQuestState("_10272_LightFragment");
			if(st.getCond() == 3 && (qs == null || qs.getInt("ex_cond") < 10))
			{
				st.setCond(4);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("warmage_artius_q10270_10.htm"))
		{
			QuestState qs = player.getQuestState("_10272_LightFragment");
			if(st.getCond() == 3 && (qs != null && (qs.getInt("ex_cond") >= 10 || qs.isCompleted())))
			{
				st.setCond(4);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("warmage_artius_q10270_13.htm"))
		{
			if(st.getInt("ex_cond") == 20)
			{
				st.rollAndGive(57, 133590, 100);
				st.addExpAndSp(625343, 48222);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("silen_priest_relrikia_q10270_02.htm") || event.equals("silen_priest_relrikia_q10270_03.htm"))
		{
			if(st.getInt("ex_cond") == 10)
				return "npchtm:" + event;
		}
		else if(event.equals("silen_priest_relrikia_q10270_05.htm"))
		{
			if(st.getInt("ex_cond") == 10)
			{
				st.set("ex_cond", 11);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("silen_priest_relrikia_q10270_07.htm"))
		{
			if(st.getInt("ex_cond") == 11)
			{
				st.set("ex_cond", 20);
				player.teleToClosestTown();
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_jinbi_q10270_03.htm"))
		{
			if(st.getCond() == 4)
			{
				QuestState qs = player.getQuestState("_10272_LightFragment");
				if(qs == null || qs.getInt("ex_cond") < 10)
					return "npchtm:soldier_jinbi_q10270_03.htm";
				if(qs.getInt("ex_cond") >= 10 || qs.isCompleted())
					return "npchtm:soldier_jinbi_q10270_04.htm";
			}
		}
		else if(event.equals("soldier_jinbi_q10270_05.htm"))
		{
			if(st.getCond() == 4)
			{
				if(player.getAdena() < 10000)
					return "npchtm:soldier_jinbi_q10270_04a.htm";

				st.takeItems(57, 10000);
				st.setCond(5);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_jinbi_q10270_06.htm"))
		{
			if(st.getCond() == 5)
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_jinbi_q10270_08.htm"))
		{
			if(st.getCond() >= 5 && st.getInt("ex_cond") < 20)
			{
				if(InstanceManager.enterInstance(117, player, player.getLastNpc(), 0))
				{
					st.set("ex_cond", 10);
					return "npchtm:" + event;
				}
				return "npchtm:soldier_jinbi_q10270_09.htm";
			}
		}

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == PLENOS)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 75)
					return "wharf_soldier_plenos_q10270_01.htm";

				st.exitCurrentQuest(true);
				return "wharf_soldier_plenos_q10270_02.htm";
			}
			else if(st.isCompleted())
				return "npchtm:wharf_soldier_plenos_q10270_03.htm";
			else if(cond == 1)
				return "npchtm:wharf_soldier_plenos_q10270_06.htm";
		}
		else if(npcId == ARTIUS)
		{
			if(st.isCompleted())
				return "npchtm:warmage_artius_q10270_02.htm";
			if(cond == 1)
				return "npchtm:warmage_artius_q10270_01.htm";
			if(cond == 2)
			{
				if(!st.haveQuestItems(KLODEKUS_BADGE) && !st.haveQuestItems(KLANIKUS_BADGE) && !st.haveQuestItems(LICH_CRYSTAL))
					return "npchtm:warmage_artius_q10270_04.htm";
				if(!st.haveQuestItems(KLODEKUS_BADGE) || !st.haveQuestItems(KLANIKUS_BADGE) || !st.haveQuestItems(LICH_CRYSTAL))
					return "npchtm:warmage_artius_q10270_05.htm";
				if(st.getQuestItemsCount(KLODEKUS_BADGE) == 1 && st.getQuestItemsCount(KLANIKUS_BADGE) == 1 && st.getQuestItemsCount(LICH_CRYSTAL) == 1)
				{
					st.setCond(3);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
					st.takeItems(KLODEKUS_BADGE, -1);
					st.takeItems(KLANIKUS_BADGE, -1);
					st.takeItems(LICH_CRYSTAL, -1);
					return "npchtm:warmage_artius_q10270_06.htm";
				}
			}
			else if(cond == 3)
			{
				QuestState qs = player.getQuestState("_10272_LightFragment");
				if(qs == null || qs.getInt("ex_cond") < 10)
					return "npchtm:warmage_artius_q10270_07.htm";
				if(qs.getInt("ex_cond") >= 10 || qs.isCompleted())
					return "npchtm:warmage_artius_q10270_08.htm";
			}
			else if(cond == 4)
				return "npchtm:warmage_artius_q10270_11.htm";
			else if(st.getInt("ex_cond") == 20)
				return "npchtm:warmage_artius_q10270_12.htm";
		}
		else if(npcId == RELRIKIA)
		{
			if(st.getInt("ex_cond") == 10)
				return "npchtm:silen_priest_relrikia_q10270_01.htm";
			if(st.getInt("ex_cond") == 11)
				return "npchtm:silen_priest_relrikia_q10270_06.htm";
		}
		else if(npcId == JINBI)
		{
			if(cond == 4)
				return "npchtm:soldier_jinbi_q10270_01.htm";
			if(cond < 4)
				return "npchtm:soldier_jinbi_q10270_02.htm";
			if(cond == 5)
				return "npchtm:soldier_jinbi_q10270_07.htm";
			if(st.getInt("ex_cond") >= 10 && st.getInt("ex_cond") < 20)
				return "npchtm:soldier_jinbi_q10270_10.htm";
			if(st.getInt("ex_cond") == 20)
				return "npchtm:soldier_jinbi_q10270_12.htm";
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == COHEMENES1)
		{
			GArray<QuestState> list = getPartyMembersWithQuest(killer, 2);
			if(list.size() > 0)
				for(QuestState qs : list)
					if(!qs.haveQuestItems(LICH_CRYSTAL))
						qs.giveItems(LICH_CRYSTAL, 1);
		}
		else if(npc.getNpcId() == KLODEKUS || npc.getNpcId() == KLANIKUS)
		{
			GArray<QuestState> list = getPartyMembersWithQuest(killer, 2);
			if(list.size() > 0)
			{
				int badge = npc.getNpcId() == KLODEKUS ? KLODEKUS_BADGE : KLANIKUS_BADGE;
				for(QuestState qs : list)
					if(!qs.haveQuestItems(badge))
					{
						qs.giveItems(badge, 1);
						qs.playSound(SOUND_ITEMGET);
					}
			}
		}
	}
}
