package quests._142_FallenAngelRequestOfDawn;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

import java.util.HashMap;


/**
 * @author rage
 * @date 23.12.10 18:54
 */
public class _142_FallenAngelRequestOfDawn extends Quest
{
	//NPC
	private final static int NATOOLS = 30894;
	private final static int RAYMOND = 30289;
	private final static int CASIAN = 30612;
	private final static int ROCK = 32368;

	//ITEM
	private final static int CRYPT = 10351;
	private final static int FRAGMENT = 10352;
	private final static int BLOOD = 10353;

	//MONSTER
	private final static int Fallen_angel = 27338;
	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(4);
	static
	{
		dropChances.put(20079, 33);
		dropChances.put(20080, 36);
		dropChances.put(20081, 61);
		dropChances.put(20082, 37);
		dropChances.put(20084, 42);
		dropChances.put(20086, 37);
		dropChances.put(20087, 90);
		dropChances.put(20088, 100);
		dropChances.put(20089, 41);
		dropChances.put(20090, 91);
	}

	public _142_FallenAngelRequestOfDawn()
	{
		super(142, "_142_FallenAngelRequestOfDawn", "Fallen Angel Request Of Dawn");

		addStartNpc(NATOOLS);
		addTalkId(NATOOLS, RAYMOND, CASIAN, ROCK);
		addQuestItem(CRYPT, FRAGMENT, BLOOD);

		for(int npcId : dropChances.keySet())
			addKillId(npcId);
		addKillId(Fallen_angel);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();

		if(npcId == NATOOLS)
		{
			if(st.isCreated())
			{
				if(player.isQuestComplete(141) && !player.isQuestStarted(143) && !player.isQuestComplete(143) && player.getLevel() >= 38)
				{
					if(reply == 142)
					{
						st.setMemoState(1);
						st.playSound(SOUND_ACCEPT);
						showPage("warehouse_chief_natools_q0142_07.htm", player);
						st.setCond(1);
						st.setState(STARTED);
					}
					else if(reply == 1)
						showPage("warehouse_chief_natools_q0142_02.htm", player);
					else if(reply == 2)
						showPage("warehouse_chief_natools_q0142_03.htm", player);
					else if(reply == 3)
						showPage("warehouse_chief_natools_q0142_04.htm", player);
				}
			}
			else if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					if(reply == 4)
						showPage("warehouse_chief_natools_q0142_09.htm", player);
					else if(reply == 5)
					{
						st.giveItems(CRYPT, 1);
						st.setMemoState(2);
						showPage("warehouse_chief_natools_q0142_10.htm", player);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npcId == RAYMOND)
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				if(reply == 3)
					showPage("bishop_raimund_q0142_03.htm", player);
				else if(reply == 4)
					showPage("bishop_raimund_q0142_04.htm", player);
				else if(reply == 5)
				{
					st.setMemoState(4);
					showPage("bishop_raimund_q0142_05.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == CASIAN)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(memoState == 5)
				{
					if(reply == 5)
						showPage("sage_kasian_q0142_04.htm", player);
					else if(reply == 6)
						showPage("sage_kasian_q0142_05.htm", player);
					else if(reply == 7)
					{
						st.setMemoState(6);
						showPage("sage_kasian_q0142_06.htm", player);
					}
				}
				else if(memoState == 6)
				{
					if(reply == 8)
						showPage("sage_kasian_q0142_08.htm", player);
					else if(reply == 9)
						showPage("sage_kasian_q0142_09.htm", player);
					else if(reply == 10)
					{
						st.setMemoState(7);
						showPage("sage_kasian_q0142_10.htm", player);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npcId == ROCK)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 8)
			{
				L2NpcInstance npc = player.getLastNpc();
				if(npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					npc.i_ai1 = player.getObjectId();
					showPage("stained_rock_q0142_05.htm", player);
					L2NpcInstance mob = addSpawn(Fallen_angel, new Location(npc.getX() + 100, npc.getY() + 100, npc.getZ()), false, 120000);
					mob.i_ai0 = npc.getObjectId();
					mob.i_ai1 = player.getObjectId();
				}
				else if(npc.i_ai0 == player.getObjectId())
					showPage("stained_rock_q0142_04.htm", player);
				else
					showPage("stained_rock_q0142_03.htm", player);
			}
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(!npc.isDead())
		{
			L2NpcInstance creator = L2ObjectsStorage.getNpc(npc.i_ai0);
			if(creator != null)
				creator.i_ai0 = 0;
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int cond = st.getMemoState();
		int npcId = npc.getNpcId();
		L2Player player = st.getPlayer();

		if(npcId == NATOOLS)
		{
			if(st.isCreated())
			{
				if(player.isQuestComplete(141) && !player.isQuestStarted(143) && !player.isQuestComplete(143))
				{
					if(player.getLevel() >= 38)
						return "npchtm:warehouse_chief_natools_q0142_01.htm";

					return "npchtm:warehouse_chief_natools_q0142_05.htm";
				}
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:warehouse_chief_natools_q0142_08.htm";
				if(cond >= 2)
					return "npchtm:warehouse_chief_natools_q0142_11.htm";

			}
		}
		else if(npcId == RAYMOND)
		{
			if(st.isStarted())
			{
				if(cond < 2)
					return "npchtm:bishop_raimund_q0142_01.htm";
				if(cond == 2)
				{
					st.takeItems(CRYPT, -1);
					st.setMemoState(3);
					return "npchtm:bishop_raimund_q0142_02.htm";
				}
				if(cond == 3)
					return "npchtm:bishop_raimund_q0142_02a.htm";
				if(cond >= 4 && cond < 9)
					return "npchtm:bishop_raimund_q0142_06.htm";
				if(cond >= 9)
				{
					st.takeItems(BLOOD, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 92676, 100);
					if(player.getLevel() < 44)
						st.addExpAndSp(223036, 13901);
					return "npchtm:bishop_raimund_q0142_07.htm";
				}
			}
		}
		else if(npcId == CASIAN)
		{
			if(st.isStarted())
			{
				if(cond < 4)
					return "npchtm:sage_kasian_q0142_01.htm";
				if(cond == 4)
				{
					st.setMemoState(5);
					return "npchtm:sage_kasian_q0142_02.htm";
				}
				if(cond == 5)
					return "npchtm:sage_kasian_q0142_03.htm";
				if(cond == 6)
					return "npchtm:sage_kasian_q0142_07.htm";
				if(cond >= 7)
					return "npchtm:sage_kasian_q0142_11.htm";
			}
		}
		else if(npcId == ROCK)
		{
			if(st.isStarted())
			{
				if(cond < 8)
					return "npchtm:stained_rock_q0142_01.htm";
				if(cond == 8)
					return "npchtm:stained_rock_q0142_02.htm";
				if(cond > 8)
					return "npchtm:stained_rock_q0142_06.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == Fallen_angel)
		{
			L2Player player = L2ObjectsStorage.getPlayer(npc.i_ai1);
			L2Object creator = L2ObjectsStorage.findObject(npc.i_ai0);
			if(creator != null && creator instanceof L2NpcInstance)
				((L2NpcInstance) creator).i_ai0 = 0;
			if(player != null && npc.isInRange(player, Config.ALT_PARTY_DISTRIBUTION_RANGE) && (player == killer || killer.isPartyMember(player)))
			{
				QuestState qs = player.getQuestState(getName());
				if(qs != null && qs.getMemoState() == 8)
				{
					qs.setMemoState(9);
					qs.setCond(6);
					showQuestMark(player);
					qs.playSound(SOUND_MIDDLE);
					qs.giveItems(BLOOD, 1);
				}
			}
		}
		else if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 7);
			if(qs != null && qs.rollAndGiveLimited(FRAGMENT, 1, dropChances.get(npc.getNpcId()), 30))
			{
				if(qs.getQuestItemsCount(FRAGMENT) >= 30)
				{
					qs.takeItems(FRAGMENT, -1);
					qs.playSound(SOUND_MIDDLE);
					qs.setCond(5);
					showQuestMark(qs.getPlayer());
					qs.setMemoState(8);
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
		}
	}
}