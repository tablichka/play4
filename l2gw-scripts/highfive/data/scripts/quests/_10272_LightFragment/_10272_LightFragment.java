package quests._10272_LightFragment;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author rage
 * @date 24.11.2010 16:08:23
 */
public class _10272_LightFragment extends Quest
{
	// NPCs
	private static final int ORBIU = 32560;
	private static final int ARTIUS = 32559;
	private static final int JINBI = 32566;
	private static final int RELRIKIA = 32567;
	private static final int LEKON = 32557;
	// Items
	private static final int MEDIBALS_DOCUMENT = 13852;
	private static final int DARKNESS_FRAGMENT = 13853;
	private static final int LIGHT_FRAGMENT = 13854;
	private static final int SACRED_LIGHT = 13855;
	// Drop
	private static final HashMap<Integer, int[]> dropData = new HashMap<Integer, int[]>(51);

	static
	{
		//           npcid, chance, min, rnd, min, rnd
		dropData.put(22537, new int[]{133, 1, 3, 1, 0});
		dropData.put(22571, new int[]{970, 1, 0, 0, 0});
		dropData.put(22546, new int[]{402, 1, 3, 1, 0});
		dropData.put(22579, new int[]{585, 2, 3, 1, 3});
		dropData.put(22588, new int[]{409, 4, 3, 3, 3});
		dropData.put(22540, new int[]{250, 1, 3, 1, 0});
		dropData.put(22596, new int[]{423, 1, 3, 1, 0});
		dropData.put(22574, new int[]{409, 4, 3, 3, 3});
		dropData.put(22536, new int[]{348, 4, 5, 4, 3});
		dropData.put(22570, new int[]{545, 5, 5, 2, 3});
		dropData.put(22547, new int[]{7, 1, 3, 1, 0});
		dropData.put(22580, new int[]{686, 1, 3, 1, 0});
		dropData.put(22589, new int[]{563, 4, 3, 3, 3});
		dropData.put(22538, new int[]{548, 1, 3, 1, 0});
		dropData.put(22594, new int[]{880, 4, 5, 4, 3});
		dropData.put(22586, new int[]{439, 3, 3, 1, 0});
		dropData.put(22572, new int[]{428, 3, 3, 1, 0});
		dropData.put(22541, new int[]{293, 1, 3, 1, 0});
		dropData.put(22597, new int[]{423, 1, 3, 1, 0});
		dropData.put(22575, new int[]{563, 4, 3, 3, 3});
		dropData.put(22544, new int[]{476, 1, 3, 1, 0});
		dropData.put(22593, new int[]{476, 1, 3, 1, 0});
		dropData.put(22578, new int[]{467, 2, 3, 1, 3});
		dropData.put(22585, new int[]{463, 1, 3, 1, 0});
		dropData.put(22583, new int[]{987, 5, 3, 4, 3});
		dropData.put(22584, new int[]{274, 2, 3, 1, 3});
		dropData.put(22543, new int[]{355, 1, 3, 1, 0});
		dropData.put(22592, new int[]{355, 1, 3, 1, 0});
		dropData.put(22591, new int[]{323, 3, 3, 2, 3});
		dropData.put(22576, new int[]{446, 5, 3, 4, 3});
		dropData.put(22542, new int[]{323, 3, 3, 2, 3});
		dropData.put(22577, new int[]{164, 2, 3, 1, 3});
		dropData.put(22548, new int[]{610, 1, 3, 1, 0});
		dropData.put(22549, new int[]{610, 1, 3, 1, 0});
		dropData.put(22582, new int[]{804, 2, 3, 1, 3});
		dropData.put(22581, new int[]{804, 2, 3, 1, 3});
		dropData.put(22595, new int[]{987, 5, 5, 4, 5});
		dropData.put(22587, new int[]{873, 4, 5, 4, 3});
		dropData.put(22573, new int[]{987, 4, 5, 4, 3});
		dropData.put(22539, new int[]{600, 1, 3, 1, 0});
		dropData.put(22569, new int[]{504, 4, 3, 1, 0});
		dropData.put(18785, new int[]{428, 3, 3, 2, 3});
		dropData.put(18790, new int[]{996, 1, 0, 0, 0});
		dropData.put(18783, new int[]{546, 4, 3, 2, 3});
		dropData.put(18786, new int[]{589, 4, 3, 3, 3});
		dropData.put(18789, new int[]{401, 1, 3, 1, 0});
		dropData.put(18788, new int[]{173, 2, 3, 1, 3});
		dropData.put(18787, new int[]{474, 4, 5, 4, 3});
		dropData.put(18791, new int[]{804, 2, 3, 1, 3});
		dropData.put(18792, new int[]{987, 1, 3, 1, 3});
		dropData.put(18784, new int[]{689, 4, 5, 4, 3});
	}

	public _10272_LightFragment()
	{
		super(10272, "_10272_LightFragment", "Light Fragment");

		addStartNpc(ORBIU);
		addTalkId(ORBIU, ARTIUS, JINBI, RELRIKIA, LEKON);
		for(int npcId : dropData.keySet())
			addKillId(npcId);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();

		if(event.equals("wharf_soldier_orbiu_q10272_05.htm"))
		{
			if(player.getLevel() >= 75 && player.isQuestComplete(10271) && st.isCreated())
				return event;
		}
		else if(event.equals("wharf_soldier_orbiu_q10272_06.htm"))
		{
			if(player.getLevel() >= 75 && player.isQuestComplete(10271) && st.isCreated())
			{
				st.setCond(1);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				if(!st.haveQuestItems(MEDIBALS_DOCUMENT))
					st.giveItems(MEDIBALS_DOCUMENT, 1);
				return event;
			}
		}
		else if(event.equals("warmage_artius_q10272_02.htm"))
		{
			if(st.haveQuestItems(MEDIBALS_DOCUMENT))
				return "npchtm:" + event;
		}
		else if(event.equals("warmage_artius_q10272_03.htm"))
		{
			if(st.haveQuestItems(MEDIBALS_DOCUMENT))
			{
				st.takeItems(MEDIBALS_DOCUMENT, -1);
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("warmage_artius_q10272_05.htm"))
		{
			if(st.getCond() == 2)
			{
				QuestState qs = player.getQuestState("_10270_BirthoftheSeed");
				if(qs != null && (qs.isCompleted() || qs.getInt("ex_cond") >= 10))
					return "npchtm:" + event;
				if(qs == null || qs.getInt("ex_cond") < 10)
					return "npchtm:warmage_artius_q10272_06.htm";
			}
		}
		else if(event.equals("warmage_artius_q10272_07.htm"))
		{
			if(st.getCond() == 2)
			{
				QuestState qs = player.getQuestState("_10270_BirthoftheSeed");
				if(qs != null && (qs.isCompleted() || qs.getInt("ex_cond") >= 10))
				{
					st.set("ex_cond", 5);
					st.setCond(3);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:" + event;
				}
			}
		}
		else if(event.equals("warmage_artius_q10272_08.htm"))
		{
			if(st.getCond() == 2)
			{
				QuestState qs = player.getQuestState("_10270_BirthoftheSeed");
				if(qs == null || qs.getInt("ex_cond") < 10)
				{
					st.set("ex_cond", 5);
					st.setCond(3);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:" + event;
				}
			}
		}
		else if(event.equals("warmage_artius_q10272_11.htm"))
		{
			if(st.getInt("ex_cond") == 20)
				return "npchtm:" + event;
		}
		else if(event.equals("warmage_artius_q10272_12.htm"))
		{
			if(st.getInt("ex_cond") == 20)
			{
				st.set("ex_cond", 21);
				st.setCond(5);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_jinbi_q10272_03.htm"))
		{
			if(st.getInt("ex_cond") == 5)
			{
				QuestState qs = player.getQuestState("_10270_BirthoftheSeed");
				if(qs != null && (qs.isCompleted() || qs.getInt("ex_cond") >= 10))
					return "npchtm:" + event;
				if(qs == null || qs.getInt("ex_cond") < 10)
					return "npchtm:soldier_jinbi_q10272_04.htm";
			}
		}
		else if(event.equals("soldier_jinbi_q10272_04a.htm"))
		{
			if(st.getInt("ex_cond") == 5)
			{
				if(player.getAdena() < 10000)
					return "npchtm:" + event;

				st.takeItems(57, 10000);
				st.set("ex_cond", 6);
				return "npchtm:soldier_jinbi_q10272_05.htm";
			}
		}
		else if(event.equals("soldier_jinbi_q10272_06.htm"))
		{
			if(st.getInt("ex_cond") == 6)
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_jinbi_q10272_08.htm"))
		{
			if(st.getInt("ex_cond") >= 6 && st.getInt("ex_cond") < 20)
			{
				if(InstanceManager.enterInstance(118, player, player.getLastNpc(), 10272))
				{
					if(st.getInt("ex_cond") == 6)
						st.set("ex_cond", 10);
					return "npchtm:soldier_jinbi_q10272_07.htm";
				}
				return "npchtm:" + event;
			}
		}
		else if(event.equals("silen_priest_relrikia_q10272_02.htm") || event.equals("silen_priest_relrikia_q10272_03.htm"))
		{
			if(st.getInt("ex_cond") == 10)
				return "npchtm:" + event;
		}
		else if(event.equals("silen_priest_relrikia_q10272_04.htm"))
		{
			if(st.getInt("ex_cond") == 10)
			{
				st.set("ex_cond", 11);
				st.setCond(4);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("silen_priest_relrikia_q10272_06.htm"))
		{
			if(st.getInt("ex_cond") == 11)
			{
				st.set("ex_cond", 20);
				player.teleToClosestTown();
				return "npchtm:" + event;
			}
		}
		else if(event.equals("engineer_recon_q10272_02.htm"))
		{
			if(st.getInt("ex_cond") == 24)
				return "npchtm:" + event;
		}
		else if(event.equals("engineer_recon_q10272_03.htm"))
		{
			if(st.getInt("ex_cond") == 24)
			{
				if(st.getQuestItemsCount(LIGHT_FRAGMENT) >= 100)
				{
					st.takeItems(LIGHT_FRAGMENT, 100);
					st.set("ex_cond", 25);
					return "npchtm:" + event;
				}
				return "npchtm:engineer_recon_q10272_04.htm";
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

		if(npcId == ORBIU)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 75)
				{
					if(player.isQuestComplete(10271))
						return "wharf_soldier_orbiu_q10272_01.htm";

					st.exitCurrentQuest(true);
					return "wharf_soldier_orbiu_q10272_02.htm";
				}

				st.exitCurrentQuest(true);
				return "wharf_soldier_orbiu_q10272_03.htm";
			}
			if(st.isCompleted())
				return "npchtm:wharf_soldier_orbiu_q10272_04.htm";

			if(cond == 1)
				return "npchtm:wharf_soldier_orbiu_q10272_07.htm";
		}
		else if(npcId == ARTIUS)
		{
			if(st.isCompleted())
				return "npchtm:warmage_artius_q10272_19.htm";
			if(cond == 1 && st.haveQuestItems(MEDIBALS_DOCUMENT))
				return "npchtm:warmage_artius_q10272_01.htm";
			if(cond == 2)
				return "npchtm:warmage_artius_q10272_04.htm";
			if(st.getInt("ex_cond") == 5)
				return "npchtm:warmage_artius_q10272_09.htm";
			if(st.getInt("ex_cond") == 20)
				return "npchtm:warmage_artius_q10272_10.htm";
			if(st.getInt("ex_cond") == 21)
			{
				if(!st.haveQuestItems(DARKNESS_FRAGMENT))
					return "npchtm:warmage_artius_q10272_13.htm";
				if(st.getQuestItemsCount(DARKNESS_FRAGMENT) < 100)
					return "npchtm:warmage_artius_q10272_14.htm";

				st.set("ex_cond", 22);
				st.setCond(6);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:warmage_artius_q10272_15.htm";
			}
			if(st.getInt("ex_cond") == 22)
			{
				if(st.getQuestItemsCount(LIGHT_FRAGMENT) < 100)
					return "npchtm:warmage_artius_q10272_16.htm";

				st.set("ex_cond", 24);
				st.setCond(7);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:warmage_artius_q10272_17.htm";
			}
			if(st.getInt("ex_cond") == 26)
			{
				st.giveItems(57, 556980);
				st.addExpAndSp(1009016, 91363);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				return "npchtm:warmage_artius_q10272_18.htm";
			}
		}
		else if(npcId == JINBI)
		{
			if(st.getInt("ex_cond") == 5)
				return "npchtm:soldier_jinbi_q10272_01.htm";
			if(st.getInt("ex_cond") < 5)
				return "npchtm:soldier_jinbi_q10272_02.htm";
			if(st.getInt("ex_cond") == 6)
				return "npchtm:soldier_jinbi_q10272_06a.htm";
			if(st.getInt("ex_cond") >= 10 && st.getInt("ex_cond") < 20)
				return "npchtm:soldier_jinbi_q10272_09.htm";
			if(st.getInt("ex_cond") == 20)
				return "npchtm:soldier_jinbi_q10272_11.htm";
		}
		else if(npcId == RELRIKIA)
		{
			if(st.getInt("ex_cond") == 10)
				return "npchtm:silen_priest_relrikia_q10272_01.htm";
			if(st.getInt("ex_cond") == 11)
				return "npchtm:silen_priest_relrikia_q10272_05.htm";
		}
		else if(npcId == LEKON)
		{
			if(st.getInt("ex_cond") == 24)
				return "npchtm:engineer_recon_q10272_01.htm";
			if(st.getInt("ex_cond") == 25)
			{
				st.giveItems(SACRED_LIGHT, 1);
				st.set("ex_cond", 26);
				st.setCond(8);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:engineer_recon_q10272_05.htm";
			}
			if(st.getInt("ex_cond") == 26)
				return "npchtm:engineer_recon_q10272_06.htm";
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> list = getPartyMembersWithQuest(killer, -1);
		if(list.size() > 0)
		{
			GArray<QuestState> random = new GArray<QuestState>(list.size());
			for(QuestState qs : list)
				if(qs.getInt("ex_cond") == 21)
					random.add(qs);

			if(random.size() > 0)
			{
				QuestState qs = random.get(Rnd.get(random.size()));
				int[] drop = dropData.get(npc.getNpcId());
				if(drop != null)
				{
					int count = Rnd.get(1000) < drop[0] ? drop[1] + Rnd.get(drop[2]) : drop[3] + Rnd.get(drop[4]);
					if(count > 0)
					{
						qs.rollAndGive(DARKNESS_FRAGMENT, count, 100);
						qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}
