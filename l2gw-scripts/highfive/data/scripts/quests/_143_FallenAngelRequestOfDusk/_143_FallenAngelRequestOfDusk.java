package quests._143_FallenAngelRequestOfDusk;

import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 23.12.10 23:17
 */
public class _143_FallenAngelRequestOfDusk extends Quest
{
	//NPC
	private final static int NATOOLS = 30894;
	private final static int TOBIAS = 30297;
	private final static int CASIAN = 30612;
	private final static int ROCK = 32368;
	private final static int ANGEL = 32369;

	//ITEM
	private final static int SEALED_PATH = 10354;
	private final static int PATH = 10355;
	private final static int EMPTY_CRYSTAL = 10356;
	private final static int MEDICINE = 10357;
	private final static int MESSAGE = 10358;

	public _143_FallenAngelRequestOfDusk()
	{
		super(143, "_143_FallenAngelRequestOfDusk", "Fallen Angel Request Of Dusk");

		addTalkId(NATOOLS, TOBIAS, CASIAN, ROCK, ANGEL);
		addQuestItem(SEALED_PATH, PATH, EMPTY_CRYSTAL, MEDICINE, MESSAGE);
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
			if(reply == 143 && st.isCreated() && !player.isQuestStarted(142) && !player.isQuestComplete(142) && player.getLevel() >= 38)
			{
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showPage("warehouse_chief_natools_q0143_01.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 4)
					showPage("warehouse_chief_natools_q0143_03.htm", player);
				if(reply == 5)
				{
					st.giveItems(SEALED_PATH, 1);
					st.setMemoState(2);
					showPage("warehouse_chief_natools_q0143_04.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == TOBIAS)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 3 && memoState == 2)
				{
					st.takeItems(SEALED_PATH, -1);
					st.setMemoState(3);
					showPage("master_tobias_q0143_03.htm", player);
				}
				else if(memoState == 3)
				{
					if(reply == 4)
						showPage("master_tobias_q0143_04.htm", player);
					else if(reply == 5)
					{
						st.giveItems(PATH, 1);
						st.giveItems(EMPTY_CRYSTAL, 1);
						st.setMemoState(4);
						showPage("master_tobias_q0143_05.htm", player);
						st.setCond(3);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npcId == CASIAN)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 5 && memoState == 4)
				{
					st.takeItems(PATH, -1);
					st.setMemoState(5);
					showPage("sage_kasian_q0143_03.htm", player);
				}
				if(memoState == 5)
				{
					if(reply == 6)
						showPage("sage_kasian_q0143_05.htm", player);
					else if(reply == 7)
						showPage("sage_kasian_q0143_06.htm", player);
					else if(reply == 8)
						showPage("sage_kasian_q0143_07.htm", player);
					else if(reply == 9)
						showPage("sage_kasian_q0143_08.htm", player);
					else if(reply == 10)
					{
						st.giveItems(MEDICINE, 1);
						st.setMemoState(8);
						showPage("sage_kasian_q0143_09.htm", player);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npcId == ROCK)
		{
			if(st.isStarted() && reply == 1 && st.getMemoState() >= 8 && st.getMemoState() < 11)
			{
				L2NpcInstance npc = player.getLastNpc();
				if(npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					npc.i_ai1 = player.getObjectId();
					L2NpcInstance mob = addSpawn(ANGEL, new Location(npc.getX() + 100, npc.getY() + 100, npc.getZ()), false, 120000);
					mob.i_ai0 = npc.getObjectId();
					mob.i_ai1 = player.getObjectId();
				}
				else if(npc.i_ai1 == player.getObjectId())
					showPage("stained_rock_q0143_04.htm", player);
				else
					showPage("stained_rock_q0143_03.htm", player);
			}
		}
		else if(npcId == ANGEL)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 1 && memoState == 8)
				{
					st.takeItems(MEDICINE, -1);
					st.setMemoState(9);
					showPage("q_fallen_angel_npc_q0143_04.htm", player);
				}
				else if(memoState == 9)
				{
					if(reply == 3)
						showPage("q_fallen_angel_npc_q0143_06.htm", player);
					else if(reply == 4)
						showPage("q_fallen_angel_npc_q0143_07.htm", player);
					else if(reply == 5)
					{
						st.setMemoState(10);
						showPage("q_fallen_angel_npc_q0143_08.htm", player);
					}
				}
				else if(memoState == 10)
				{
					if(reply == 6)
						showPage("q_fallen_angel_npc_q0143_10.htm", player);
					else if(reply == 7)
						showPage("q_fallen_angel_npc_q0143_11.htm", player);
					else if(reply == 8)
						showPage("q_fallen_angel_npc_q0143_12.htm", player);
					else if(reply == 9)
						showPage("q_fallen_angel_npc_q0143_13.htm", player);
					else if(reply == 10)
					{
						L2Object creator = L2ObjectsStorage.findObject(player.getLastNpc().i_ai0);
						if(creator instanceof L2NpcInstance)
							((L2NpcInstance) creator).i_ai0 = 0;
						player.getLastNpc().deleteMe();
						st.giveItems(MESSAGE, 1);
						st.takeItems(EMPTY_CRYSTAL, -1);
						st.setMemoState(11);
						showPage("q_fallen_angel_npc_q0143_14.htm", player);
						st.setCond(5);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(!npc.isDecayed())
		{
			L2Object creator = L2ObjectsStorage.findObject(npc.i_ai0);
			if(creator instanceof L2NpcInstance)
				((L2NpcInstance) creator).i_ai0 = 0;
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
		if(npcId == NATOOLS)
		{
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:warehouse_chief_natools_q0143_02.htm";
				if(cond >= 2)
					return "npchtm:warehouse_chief_natools_q0143_05.htm";
			}

		}
		else if(npcId == TOBIAS)
		{
			if(st.isStarted())
			{
				if(cond < 2)
					return "npchtm:master_tobias_q0143_01.htm";
				if(cond == 2)
					return "npchtm:master_tobias_q0143_02.htm";
				if(cond == 3)
					return "npchtm:master_tobias_q0143_03a.htm";
				if(cond >= 4 && cond < 11)
					return "npchtm:master_tobias_q0143_06.htm";
				if(cond >= 11)
				{
					st.takeItems(MESSAGE, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 89046, 100);
					if(st.getPlayer().getLevel() < 44)
						st.addExpAndSp(223036, 13901);
					return "npchtm:master_tobias_q0143_07.htm";
				}
			}
		}
		else if(npcId == CASIAN)
		{
			if(st.isStarted())
			{
				if(cond < 4)
					return "npchtm:sage_kasian_q0143_01.htm";
				if(cond == 4)
					return "npchtm:sage_kasian_q0143_02.htm";
				if(cond == 5)
					return "npchtm:sage_kasian_q0143_04.htm";
				if(cond >= 6)
					return "npchtm:sage_kasian_q0143_10.htm";
			}
		}
		else if(npcId == ROCK)
		{
			if(st.isStarted())
			{
				if(cond < 8)
					return "npchtm:stained_rock_q0143_01.htm";
				if(cond >= 8 && cond < 11)
					return "npchtm:stained_rock_q0143_02.htm";
				if(cond >= 11)
					return "npchtm:stained_rock_q0143_06.htm";
			}
		}
		else if(npcId == ANGEL)
		{
			if(st.isStarted())
			{
				if(cond < 8)
					return "npchtm:q_fallen_angel_npc_q0143_01.htm";
				if(cond == 8)
				{
					L2Player player = L2ObjectsStorage.getPlayer(npc.i_ai1);
					if(player == st.getPlayer())
						return "npchtm:q_fallen_angel_npc_q0143_03.htm";
					else
						return "npchtm:q_fallen_angel_npc_q0143_02.htm";
				}
				if(cond == 9)
					return "npchtm:q_fallen_angel_npc_q0143_05.htm";
				if(cond == 10)
					return "npchtm:q_fallen_angel_npc_q0143_09.htm";
			}
		}
		return htmltext;
	}
}