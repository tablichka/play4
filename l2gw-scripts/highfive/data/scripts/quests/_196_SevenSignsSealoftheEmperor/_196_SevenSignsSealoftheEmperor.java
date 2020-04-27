package quests._196_SevenSignsSealoftheEmperor;

import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.util.Location;

public class _196_SevenSignsSealoftheEmperor extends Quest
{
	// NPCs
	private static final int IASON_HEINE = 30969;
	private static final int SSQ_MAMMON = 32584;
	private static final int EMPEROR = 32586;
	private static final int GUARD_LEON = 32587;
	private static final int PRIEST_WOOD = 32593;
	private static final int SSQ_WIZARD = 32598;
	private static final int DISCIPLE_GATEKEEPER = 32657;

	// ITEMS
	private static final int SEAL_OF_BINDING = 13846;
	private static final int HOLY_WATER = 13808;
	private static final int SACRED_SWORD = 15310;
	private static final int MAGIC_STAFF = 13809;
	private static final Location ssqMammonLoc = new Location(109743, 219975, -3512, 0);

	// MOBs
	private static final int[] MOBS = {27371, 27372, 27373, 27374, 27375, 27376, 27377, 27378, 27379};

	public _196_SevenSignsSealoftheEmperor()
	{
		super(196, "_196_SevenSignsSealoftheEmperor", "Seven Signs, Seal of the Emperor");

		addQuestItem(SEAL_OF_BINDING, HOLY_WATER, SACRED_SWORD, MAGIC_STAFF);
		addStartNpc(IASON_HEINE);
		addTalkId(SSQ_MAMMON, EMPEROR, GUARD_LEON, PRIEST_WOOD, SSQ_WIZARD, DISCIPLE_GATEKEEPER);
		addDecayId(MOBS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();

		if(st.isCompleted())
			return "npchtm:completed";

		QuestState q195 = player.getQuestState("_195_SevenSignsSecretRitualofthePriests");

		if(event.equalsIgnoreCase("accept"))
		{
			if(st.isCreated() && player.getLevel() >= 79 && q195 != null && q195.isCompleted())
			{
				st.setState(STARTED);
				st.setCond(1);
				event = "iason_haine_q0196_07.htm";
				st.playSound(SOUND_ACCEPT);
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_1"))
		{
			if(st.isCreated() && player.getLevel() >= 79 && q195 != null && q195.isCompleted())
			{
				event = "iason_haine_q0196_04.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_2"))
		{
			if(st.isCreated() && player.getLevel() >= 79 && q195 != null && q195.isCompleted())
			{
				event = "iason_haine_q0196_05.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_3"))
		{
			if(st.isCreated() && player.getLevel() >= 79 && q195 != null && q195.isCompleted())
			{
				event = "iason_haine_q0196_06.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_4"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				int q0;
				if(npc != null)
				{
					q0 = npc.getAIParams().getInteger("q0", 0);
				}
				else
					return "iason_haine_q0196_11.htm";

				if(q0 == 0)
				{
					event = "iason_haine_q0196_09.htm";
					npc.getAIParams().set("q0", 1);
					npc.getAIParams().set("q1", player.getObjectId());
					addSpawn(SSQ_MAMMON, ssqMammonLoc);
				}
				else
				{
					event = "iason_haine_q0196_11.htm";
				}
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_5"))
		{
			if(st.isStarted() && st.getCond() == 5)
			{
				event = "iason_haine_q0196_15.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("iason196_6"))
		{
			if(st.isStarted() && st.getCond() == 5)
			{
				event = "iason_haine_q0196_16.htm";
				st.setCond(6);
				st.setState(STARTED);

			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("mammon196_1"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				event = "npchtm:ssq_mammon_q0196_02.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("mammon196_2"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				event = "npchtm:ssq_mammon_q0196_03.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("mammon196_3"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				event = "npchtm:ssq_mammon_q0196_04.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("mammon196_4"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				st.setCond(2);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				if(npc != null)
					npc.getAI().addTimer(19602, 3000);
				event = "npchtm:ssq_mammon_q0196_05.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("prom196_1"))
		{
			if(st.isStarted() && st.getCond() >= 3 && st.getCond() < 5)
			{
				// Start instance 112
				InstanceManager.enterInstance(112, player, player.getLastNpc(), 196);
				return null;
			}
			else
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_QUEST_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				event = "npchtm:noquest";
			}
		}
		else if(event.equalsIgnoreCase("emp196_1"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				event = "npchtm:emperor_shumeimman_q0196_02.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_2"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				event = "npchtm:emperor_shumeimman_q0196_03.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_3"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				event = "npchtm:emperor_shumeimman_q0196_04.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_4"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				event = "npchtm:emperor_shumeimman_q0196_05.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_10"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				event = "npchtm:emperor_shumeimman_q0196_05a.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_5"))
		{
			if(st.isStarted() && st.getCond() == 3)
			{
				st.giveItems(HOLY_WATER, 1);
				st.giveItems(SACRED_SWORD, 1);
				st.setCond(4);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				player.sendPacket(new SystemMessage(3031));
				player.sendPacket(new SystemMessage(3039));
				event = "emperor_shumeimman_q0196_06.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_6"))
		{
			if(st.isStarted() && st.getCond() == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				event = "npchtm:emperor_shumeimman_q0196_10.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_7"))
		{
			if(st.isStarted() && st.getCond() == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				event = "npchtm:emperor_shumeimman_q0196_11.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_8"))
		{
			if(st.isStarted() && st.getCond() == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				event = "npchtm:emperor_shumeimman_q0196_12.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("emp196_9"))
		{
			if(st.isStarted() && st.getCond() == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				st.takeItems(HOLY_WATER, -1);
				st.takeItems(SACRED_SWORD, -1);
				st.takeItems(MAGIC_STAFF, -1);
				st.takeItems(SEAL_OF_BINDING, -1);
				st.setCond(5);
				event = "emperor_shumeimman_q0196_13.htm";
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				Instance inst = npc.getInstanceZone();
				if(inst != null)
					inst.setNoUserTimeout(0);
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("leon196_1"))
		{
			if(st.isStarted() && st.getCond() >= 3)
			{
				st.takeItems(SACRED_SWORD, -1);
				event = "npchtm:guard_leon_q0196_02.htm";
				player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("priest196_1"))
		{
			if(st.isStarted() && st.getCond() == 6 && player.getLevel() >= 79)
			{
				st.addExpAndSp(52518015, 5817677);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				event = "npchtm:priest_wood_q0196_02.htm";
			}
			else if(st.isStarted() && st.getCond() == 6 && player.getLevel() < 79)
			{
				event = "npchtm:level_check_q0192_01.htm";
			}
			else
				event = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("wizard196_1"))
		{
			if(st.isStarted() && st.getCond() >= 3 && st.getCond() < 5)
			{
				if(st.getQuestItemsCount(MAGIC_STAFF) == 0)
				{
					st.giveItems(MAGIC_STAFF, 1);
					event = "npchtm:ssq_wizard_q0196_02.htm";
					player.sendPacket(new SystemMessage(3040));
				}
			}
		}
		else if(event.equalsIgnoreCase("gate196_1"))
		{
			if(st.isStarted() && st.getCond() >= 3 && player.getLastNpc().i_ai0 == 0)
			{
				player.getLastNpc().i_ai0 = 1;
				// open 17240111 door
				Instance inst = player.getLastNpc().getSpawn().getInstance();
				if(inst != null)
				{
					for(L2DoorInstance door : inst.getDoors())
						if(door.getDoorId() == 17240111)
						{
							door.openMe();
							door.onOpen();
						}

					inst.notifyEvent("spawn_iz112_1724_f02", null, null);
					player.showQuestMovie(ExStartScenePlayer.SCENE_SSQ_SEALING_EMPEROR_1ST);
					npc.i_ai0 = player.getObjectId();
					startQuestTimer("7802_" + player.getReflection(), 18000, player.getLastNpc(), player, true);
				}
				return null;
			}
			return null;
		}

		return event;
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.startsWith("7802_"))
		{
			Instance inst = npc.getSpawn().getInstance();
			inst.notifyEvent("spawn_iz112_1724_f01", null, null);
			if(player != null)
				player.sendPacket(Msg.IN_ORDER_TO_HELP_ANAKIM_ACTIVATE_THE_SEALING_DEVICE_OF_THE_EMPEROR_WHO_IS_POSSESED_BY_THE_EVIL_MAGICAL_CURSE);

			startQuestTimer("7810_" + npc.getReflection(), 1000, npc, player, true);
		}
		else if(event.startsWith("7810_"))
			npc.getAI().broadcastScriptEvent(30, npc.i_ai0, null, 5000);

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == IASON_HEINE)
		{
			if(st.isCompleted())
				return "npchtm:completed";
			if(st.isCreated())
			{
				QuestState q195 = player.getQuestState("_195_SevenSignsSecretRitualofthePriests");
				if(player.getLevel() >= 79 && q195 != null && q195.isCompleted())
					return "iason_haine_q0196_01.htm";
				else
					return "iason_haine_q0196_03.htm";
			}
			if(cond == 1)
				return "iason_haine_q0196_08.htm";
			if(cond == 2)
			{
				st.setState(STARTED);
				st.setCond(3);
				st.playSound(SOUND_MIDDLE);
				return "iason_haine_q0196_12.htm";
			}
			if(cond >= 3 && cond < 5)
			{
				return "iason_haine_q0196_13.htm";
			}
			if(cond == 5)
			{
				return "iason_haine_q0196_14.htm";
			}
			if(cond == 6)
			{
				return "iason_haine_q0196_16a.htm";
			}
		}
		else if(npcId == SSQ_MAMMON)
		{
			if(st.isStarted() && cond == 1)
			{
				int dbid = 0;
				L2NpcInstance iason = L2ObjectsStorage.getByNpcId(IASON_HEINE);
				if(iason != null)
				{
					dbid = iason.getAIParams().getInteger("q1", 0);
				}
				if(player.getObjectId() != dbid)
				{
					return "npchtm:ssq_mammon_q0196_01a.htm";
				}
				else
				{
					return "npchtm:ssq_mammon_q0196_01.htm";
				}
			}
		}
		else if(npcId == EMPEROR)
		{
			if(st.isStarted() && cond == 3)
			{
				return "npchtm:emperor_shumeimman_q0196_01.htm";
			}
			if(st.isStarted() && cond == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) < 4)
			{
				if(st.getQuestItemsCount(HOLY_WATER) >= 1 && st.getQuestItemsCount(SACRED_SWORD) >= 1)
				{
					player.sendPacket(new SystemMessage(3031));
					player.sendPacket(new SystemMessage(3039));
					return "npchtm:emperor_shumeimman_q0196_07.htm";
				}
				if(st.getQuestItemsCount(HOLY_WATER) == 0 && st.getQuestItemsCount(SACRED_SWORD) >= 1)
				{
					st.giveItems(HOLY_WATER, 1);
					player.sendPacket(new SystemMessage(3031));
					player.sendPacket(new SystemMessage(3039));
					return "npchtm:emperor_shumeimman_q0196_08.htm";
				}
				if(st.getQuestItemsCount(HOLY_WATER) >= 1 && st.getQuestItemsCount(SACRED_SWORD) == 0)
				{
					st.giveItems(SACRED_SWORD, 1);
					player.sendPacket(new SystemMessage(3031));
					player.sendPacket(new SystemMessage(3039));
					return "npchtm:emperor_shumeimman_q0196_08.htm";
				}
			}
			if(st.isStarted() && cond == 4 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				return "npchtm:emperor_shumeimman_q0196_09.htm";
			}
			if(st.isStarted() && cond == 5 && st.getQuestItemsCount(SEAL_OF_BINDING) >= 4)
			{
				return "npchtm:emperor_shumeimman_q0196_13a.htm";
			}
		}
		else if(npcId == PRIEST_WOOD)
		{
			if(st.isStarted() && cond == 6)
			{
				return "npchtm:priest_wood_q0196_01.htm";
			}
		}
		else if(npcId == SSQ_WIZARD)
		{
			if(st.isStarted() && cond >= 3 && cond < 5)
			{
				player.sendPacket(new SystemMessage(3040));
				if(st.getQuestItemsCount(MAGIC_STAFF) == 0)
				{
					return "npchtm:ssq_wizard_q0196_01.htm";
				}
				else
					return "npchtm:ssq_wizard_q0196_03.htm";

			}
		}

		return "npchtm:noquest";
	}

	@Override
	public void onDecay(L2NpcInstance npc)
	{
		Instance inst = npc.getSpawn().getInstance();
		if(inst != null)
			inst.notifyDecayd(npc);
	}
}
