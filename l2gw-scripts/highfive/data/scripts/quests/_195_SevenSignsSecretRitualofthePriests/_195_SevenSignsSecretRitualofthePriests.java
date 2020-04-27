package quests._195_SevenSignsSecretRitualofthePriests;

import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 21.11.2010 11:45:29
 */
public class _195_SevenSignsSecretRitualofthePriests extends Quest
{
	// NPCs
	private static final int CLAUDIA = 31001;
	private static final int JOHN = 32576;
	private static final int RAYMOND = 30289;
	private static final int IASON = 30969;
	private static final int LIGHT = 32575;
	private static final int DARKNES = 32579;
	private static final int CODE_INPUT = 32577;
	private static final int ID_DEVICE = 32578;
	private static final int BOOKSHELF = 32581;
	// Items
	private static final int IDENTITY_CARD = 13822;
	private static final int SHUNAIMANS_CONTRACT = 13823;
	// Transform
	private static final L2Skill TRANSFORM_SKILL = SkillTable.getInstance().getInfo(6204, 1);
	private static final L2Skill TRANSFORM_DISPEL = SkillTable.getInstance().getInfo(6200, 1);
	// Doors
	private static final int doorId1 = 17240005;
	private static final int doorId2 = 17240006;

	public _195_SevenSignsSecretRitualofthePriests()
	{
		super(195, "_195_SevenSignsSecretRitualofthePriests", "Seven Signs, Secret Ritual of the Priests");

		addStartNpc(CLAUDIA);
		addTalkId(CLAUDIA, JOHN, RAYMOND, IASON, LIGHT, DARKNES, CODE_INPUT, ID_DEVICE, BOOKSHELF);
		addFirstTalkId(BOOKSHELF);
		addQuestItem(IDENTITY_CARD, SHUNAIMANS_CONTRACT);
	}

	public String onFirstTalk(L2NpcInstance npc, L2Player player)
	{
		if(npc.i_ai0 == 1 && player.getItemCountByItemId(SHUNAIMANS_CONTRACT) > 0)
			return "npchtm:bookshelf2_dawn_q0195_02c.htm";

		return "npchtm:bookshelf2_dawn001.htm";
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();

		if(event.equals("claudia_a_q0195_07.htm"))
		{
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			return event;
		}
		else if(event.startsWith("claudia_a_q0195"))
		{
			if(player.getLevel() >= 79 && player.isQuestComplete(194))
				return event;
			else
				return "claudia_a_q0195_03.htm"; 
		}
		else if(event.equals("master_guard_john_q0195_02.htm"))
		{
			if(st.getCond() == 1)
			{
				st.giveItems(IDENTITY_CARD, 1);
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
			else
				return "master_guard_john_q0195_03.htm";
		}
		else if(event.startsWith("bishop_raimund_q0195"))
		{
			if(event.equals("bishop_raimund_q0195_14.htm") && player.getTransformation() == 113)
			{
				player.getLastNpc().altUseSkill(TRANSFORM_DISPEL, player);
				return "npchtm:" + event;
			}
			else if(st.getCond() == 2 && st.haveQuestItems(IDENTITY_CARD) && player.getTransformation() != 113)
			{
				if(event.equals("bishop_raimund_q0195_04.htm"))
				{
					player.getLastNpc().altUseSkill(TRANSFORM_SKILL, player);
					st.setCond(3);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				return "npchtm:" + event;
			}
			else if(st.getCond() == 3 && st.haveQuestItems(IDENTITY_CARD) && !st.haveQuestItems(SHUNAIMANS_CONTRACT))
			{
				if(event.equals("bishop_raimund_q0195_07.htm"))
					player.getLastNpc().altUseSkill(TRANSFORM_SKILL, player);
				else if(event.equals("bishop_raimund_q0195_08.htm"))
					player.getLastNpc().altUseSkill(TRANSFORM_DISPEL, player);
				return "npchtm:" + event;
			}
			else if(st.getCond() == 3 && st.haveQuestItems(IDENTITY_CARD) && st.haveQuestItems(SHUNAIMANS_CONTRACT))
			{
				if(event.equals("bishop_raimund_q0195_11.htm"))
				{
					st.takeItems(IDENTITY_CARD, -1);
					st.setCond(4);
					st.setState(STARTED);
					if(player.getTransformation() == 113)
						player.getLastNpc().altUseSkill(TRANSFORM_DISPEL, player);
				}
				return "npchtm:" + event;
			}
			else
				return "npchtm:noquest";
		}
		else if(event.equals("iason_haine_q0195_03.htm"))
		{
			if(st.haveQuestItems(SHUNAIMANS_CONTRACT) && player.getLevel() >= 79)
			{
				st.addExpAndSp(52518015, 5817677);
				st.takeItems(SHUNAIMANS_CONTRACT, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
				return "npchtm:iason_haine_q0195_01.htm";
		}
		else if(event.equalsIgnoreCase("enter"))
		{
			if(st.getCond() == 3 && st.haveQuestItems(IDENTITY_CARD) && player.getTransformation() == 113)
			{
				if(InstanceManager.enterInstance(111, player, player.getLastNpc(), 195))
					return "npchtm:light_of_dawn_q0195_03.htm";
			}
			else
				return "npchtm:light_of_dawn_q0195_04.htm";
		}
		else if(event.equals("darkness_of_dawn_q0195_02.htm"))
		{
			if(st.getCond() == 3 && player.getTransformation() == 113 && st.haveQuestItems(IDENTITY_CARD))
			{
				player.teleToClosestTown();
				return "npchtm:" + event;
			}

			return "npchtm:darkness_of_dawn_q0195_03.htm";
		}
		else if(event.equals("darkness_of_dawn_q0195_03.htm"))
			return "npchtm:" + event;
		else if(event.equals("code_input_device_q0195_02.htm"))
		{
			if(st.getCond() == 3 && player.getTransformation() == 113 && st.haveQuestItems(IDENTITY_CARD))
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					for(L2DoorInstance door : inst.getDoors())
						if(door.getDoorId() == doorId1 || door.getDoorId() == doorId2)
						{
							door.openMe();
							door.onOpen();
						}

					startQuestTimer("ssq_msg1_" + player.getReflection(), 1000, player.getLastNpc(), player, true);
				}
				return "npchtm:" + event;
			}
			return "npchtm:code_input_device_q0195_03.htm";
		}
		else if(event.equals("code_input_device_q0195_03.htm"))
		{
			player.teleToLocation(-78240, 205858, -7856);
			return "npchtm:" + event;
		}
		else if(event.equals("identification_device_q0195_01.htm"))
		{
			if(st.getCond() == 3 && player.getTransformation() == 113 && st.haveQuestItems(IDENTITY_CARD))
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				L2NpcInstance npc = player.getLastNpc();
				if(inst != null && npc != null)
				{
					if(npc.getAIParams() != null)
					{
						String[] ds = npc.getAIParams().getString("door_id", "").split(",");
						if(ds.length > 0)
						{
							int[] doors = new int[ds.length];
							for(int i = 0; i < ds.length; i++)
							{
								String door = ds[i];
								if(door != null && !door.isEmpty())
									doors[i] = Integer.parseInt(door);
							}

							for(L2DoorInstance door : inst.getDoors())
								if(contains(doors, door.getDoorId()))
								{
									door.openMe();
									door.onOpen();
								}
						}
					}

					startQuestTimer("ssq_msg1_" + player.getReflection(), 1000, npc, player, true);
				}
				return "npchtm:" + event;
			}
			return "npchtm:identification_device_q0195_02.htm";
		}
		else if(event.equals("bookshelf2_dawn_q0195_02.htm"))
		{
			if(st.getCond() == 3)
			{
				if(player.getLastNpc().i_ai0 == 1)
				{
					st.giveItems(SHUNAIMANS_CONTRACT, 1);
					Instance inst = player.getLastNpc().getInstanceZone();
					if(inst != null)
						inst.setNoUserTimeout(0);
					return "npchtm:" + event;
				}
				return "npchtm:bookshelf2_dawn_q0195_03.htm";
			}
		}
		else if(event.equals("bookshelf2_dawn_q0195_02a.htm"))
			return "npchtm:bookshelf2_dawn_q0195_02a.htm";
		else if(event.equals("bookshelf2_dawn_q0195_02b.htm"))
		{
			player.teleToClosestTown();
			return "npchtm:bookshelf2_dawn_q0195_02b.htm";
		}

		return "npchtm:" + event;
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.startsWith("ssq_msg1_"))
		{
			if(player != null)
			{
				if(npc.getAIParams() != null && npc.getAIParams().getInteger("scene_num", 0) > 0)
				{
					startQuestTimer("ssq_camera2_" + player.getReflection(), npc.getAIParams().getInteger("scene_sec", 30000), npc, player, true);
					player.showQuestMovie(npc.getAIParams().getInteger("scene_num"));
				}
				else
				{
					player.sendPacket(Msg.BY_USING_THE_INVISIBLE_SKILL_SNEAK_INTO_THE_DAWN_S_DOCUMENT_STORAGE);
					startQuestTimer("ssq_msg2_" + player.getReflection(), 100, npc, player, true);
				}
			}
		}
		else if(event.startsWith("ssq_msg2_"))
		{
			if(player != null)
			{
				player.sendPacket(Msg.MALE_GUARDS_CAN_DETECT_THE_CONCEALMENT_BUT_THE_FEMALE_GUARDS_CANNOT);
				startQuestTimer("ssq_msg3_" + player.getReflection(), 100, npc, player, true);
			}
		}
		else if(event.startsWith("ssq_msg3_"))
		{
			if(player != null)
				player.sendPacket(Msg.FEMALE_GUARDS_NOTICE_THE_DISGUISES_FROM_FAR_AWAY_BETTER_THAN_THE_MALE_GUARDS_DO_SO_BEWARE);
		}
		else if(event.startsWith("ssq_camera2_"))
		{
			if(npc != null)
			{
				Instance inst = npc.getSpawn().getInstance();
				if(inst != null)
					inst.spawnEvent("iz111evt02");
			}
			if(player != null)
				player.sendPacket(Msg.THE_DOOR_IN_FRONT_OF_US_IS_THE_ENTRANCE_TO_THE_DAWN_S_DOCUMENT_STORAGE_APPROACH_TO_THE_CODE_INPUT_DEVICE);
		}

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == CLAUDIA)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 79 && player.isQuestComplete(194))
					return "claudia_a_q0195_01.htm";
				else
				{
					st.exitCurrentQuest(true);
					return "claudia_a_q0195_03.htm";
				}
			}
			else if(st.isCompleted())
				return "npchtm:completed";
			else if(cond == 1)
				return "npchtm:claudia_a_q0195_08.htm";
		}
		else if(npcId == JOHN)
		{
			if(cond == 1)
				return "master_guard_john_q0195_01.htm";
			if(cond == 2 && st.haveQuestItems(IDENTITY_CARD))
				return "master_guard_john_q0195_03.htm";
		}
		else if(npcId == RAYMOND)
		{
			if(player.getTransformation() == 113)
				return "npchtm:bishop_raimund_q0195_13.htm";
			if(cond == 2 && st.haveQuestItems(IDENTITY_CARD))
				return "npchtm:bishop_raimund_q0195_01.htm";
			if(cond == 3 && st.haveQuestItems(IDENTITY_CARD))
				return st.haveQuestItems(SHUNAIMANS_CONTRACT) ? "npchtm:bishop_raimund_q0195_09.htm" : "npchtm:bishop_raimund_q0195_06.htm";
			if(cond == 4)
				return "npchtm:bishop_raimund_q0195_12.htm";
		}
		else if(npcId == IASON)
		{
			if(cond == 4 && st.haveQuestItems(SHUNAIMANS_CONTRACT))
				return "npchtm:iason_haine_q0195_01.htm";
			
		}

		return "npchtm:noquest";
	}
}
