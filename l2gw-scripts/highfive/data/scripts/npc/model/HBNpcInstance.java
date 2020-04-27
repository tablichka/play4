package npc.model;

import ai.QuarrySlave;
import quests.global.Hellbound;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2DungeonGatekeeperInstance;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.gameserver.taskmanager.DecayTaskManager;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

/**
 * User: ic
 * Date: 09.01.2010
 */
public class HBNpcInstance extends L2DungeonGatekeeperInstance
{
	private static String _path = "data/html/hellbound/";

	private static final int WARPGATE1 = 32315;
	private static final int WARPGATE5 = 32319;
	private static final int BUDENKA = 32294;
	private static final int BURON = 32345;
	private static final int KIEF = 32354;
	private static final int BERNARDE = 32300;
	private static final int MING = 32308;
	private static final int HUDE = 32298;
	private static final int JUDE = 32356;
	private static final int TRAITOR = 32364;
	private static final int FALK = 32297;
	private static final int QUARRY_SLAVE = 32299;
	private static final int NATIVE_SLAVE = 32357;

	private static final int BASIC_CARAVAN_CERTIFICATE = 9850;
	private static final int STANDARD_CARAVAN_CERTIFICATE = 9851;
	private static final int PREMIUM_CARAVAN_CERTIFICATE = 9852;
	private static final int DARION_BADGE = 9674;
	private static final int NATIVE_HELMET = 9669;
	private static final int NATIVE_TUNIC = 9670;
	private static final int NATIVE_PANTS = 9671;

	private static final int MARK_OF_BETRAYAL = 9676;
	private static final int POISON_STINGER = 10012;
	private static final int LIFE_FORCE = 9681;
	private static final int DIM_LIFE_FORCE = 9680;
	private static final int CONTAINED_LIFE_FORCE = 9682;
	private static final int NATIVE_TREASURE = 9684;
	private static final int HOLY_WATER = 9673;
	private static final int MAGIC_BOTTLE = 9672;

	private static final Location HB_START_LOC = new Location(-11272, 236464, -3248);
	private static final Location INFINITUM_LOC = new Location(-22204, 277056, -15045);
	private static final Location DORIAN_LOC = new Location(18046, 283669, -9704);
	private static final int NATIVE_TRANSFORM_SKILL = 3359;

	public HBNpcInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(command.startsWith("instance"))
		{
			if(player.getParty() == null)
			{
				showChatWindow(player, 1);
				return;
			}
			super.onBypassFeedback(player, command);
		}
		else if(command.startsWith("TeleToCitadel"))
		{
			QuestState qs = player.getQuestState("_132_MatrasCuriosity");
			if(qs != null && qs.isCompleted())
				player.teleToLocation(DORIAN_LOC);
			else
				showChatWindow(player, 1);
		}
		else if(command.equalsIgnoreCase("TeleToInfinitum"))
		{
			if(player.getParty() == null || !player.getParty().isLeader(player))
			{
				showChatWindow(player, 2);
				return;
			}
			for(L2Player member : player.getParty().getPartyMembers())
				if(!isInRange(member, 300) || member.getEffectBySkillId(2357) == null)
				{
					showChatWindow(player, 1);
					return;
				}

			if(ServerVariables.getInt("hb_stage", 0) < 11)
			{
				showChatWindow(player, 3);
				return;
			}

			for(L2Player member : player.getParty().getPartyMembers())
				if(isInRange(member, 300))
					member.teleToLocation(INFINITUM_LOC);
		}
		else if(command.startsWith("TeleToTully1"))
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null && inst.getTemplate().getId() == 5)
			{
				player.setStablePoint(player.getLoc());
				player.teleToLocation(inst.getStartLoc(), inst.getReflection());
				return;
			}

			if(player.getParty() == null || !player.getParty().isLeader(player))
			{
				showChatWindow(player, 1);
				return;
			}

			for(L2Player member : player.getParty().getPartyMembers())
				if(!isInRange(member, 300))
				{
					showChatWindow(player, 2);
					return;
				}

			super.onBypassFeedback(player, "instance 5");
		}
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		int hellboundStage = ServerVariables.getInt("hb_stage", 0);
		long hellboundRep = Hellbound.getPoints();
		int npcId = getNpcId();
		String filename = _path + hellboundStage + "/" + npcId + "-" + val + ".htm";

		if(hellboundStage >= 1 && npcId >= WARPGATE1 && npcId <= WARPGATE5 && val == 1)
		{
			QuestState qs_130 = player.getQuestState("_130_PathToHellbound");
			QuestState qs_133 = player.getQuestState("_133_ThatsBloodyHot");
			if((qs_130 != null && qs_130.isCompleted()) || (qs_133 != null && qs_133.isCompleted()))
			{
				player.teleToLocation(HB_START_LOC);
				return;
			}
			else
				filename = _path + hellboundStage + "/" + npcId + "-no.htm";
		}
		else if(hellboundStage >= 1 && npcId == BUDENKA)
		{
			if(player.getItemCountByItemId(PREMIUM_CARAVAN_CERTIFICATE) > 0)
				filename = _path + hellboundStage + "/" + npcId + "-premium.htm";
			else if(player.getItemCountByItemId(STANDARD_CARAVAN_CERTIFICATE) > 0)
				filename = _path + hellboundStage + "/" + npcId + "-standard.htm";
			else
				filename = _path + hellboundStage + "/" + npcId + "-" + val + ".htm";
		}
		else if(npcId == FALK)
		{
			if(val == 2) // Giving Falk 20 Darion badges in exchange for Basic Certificate
			{
				if(player.getInventory().slotsLeft() < 10)
				{
					player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
					player.sendActionFailed();
					return;
				}
				else
				{
					if(player.getItemCountByItemId(BASIC_CARAVAN_CERTIFICATE) > 0 || player.getItemCountByItemId(STANDARD_CARAVAN_CERTIFICATE) > 0
							|| player.getItemCountByItemId(PREMIUM_CARAVAN_CERTIFICATE) > 0)
						filename = _path + hellboundStage + "/" + npcId + "-basic.htm";
					else if(player.getItemCountByItemId(DARION_BADGE) < 20)
						filename = _path + hellboundStage + "/" + npcId + "-no.htm";
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
						player.destroyItemByItemId("FalkExchange", DARION_BADGE, 20, this, true);
						player.addItem("FalkExchange", BASIC_CARAVAN_CERTIFICATE, 1, this, true);
					}
				}
			}
		}
		else if(npcId == BURON)
		{
			if(val >= 2 && val <= 4) // Exchange items
			{
				if(hellboundStage >= 2 && player.getItemCountByItemId(DARION_BADGE) >= 10 && hellboundRep >= Hellbound.LIMIT[1])
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-5.htm";
						switch(val)
						{
							case 2: // Helmet
								player.destroyItemByItemId("BuronExchange", DARION_BADGE, 10, this, true);
								player.addItem("BuronExchange", NATIVE_HELMET, 1, this, true);
								break;
							case 3: // Tunic
								player.destroyItemByItemId("BuronExchange", DARION_BADGE, 10, this, true);
								player.addItem("BuronExchange", NATIVE_TUNIC, 1, this, true);
								break;
							case 4: // Pants
								player.destroyItemByItemId("BuronExchange", DARION_BADGE, 10, this, true);
								player.addItem("BuronExchange", NATIVE_PANTS, 1, this, true);
								break;
							default:
								filename = _path + hellboundStage + "/" + npcId + "-no.htm";
								break;
						}
					}
				}
				else if(hellboundStage >= 2 && hellboundRep >= Hellbound.LIMIT[1])
				{
					filename = _path + hellboundStage + "/" + npcId + "-nobadges.htm";
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
				}
			}
		}
		else if(hellboundStage >= 2 && npcId == KIEF)
		{
			if(val == 1 && hellboundStage < 7) // Accepting Darion Bagdes. 1 Badge = +10 points
			{
				long badges = player.getItemCountByItemId(DARION_BADGE);
				if(badges > 0)
				{
					filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
					long points = badges * 10;
					player.destroyItemByItemId("BuronExchange", DARION_BADGE, badges, this, true);
					if(hellboundStage == 2 || hellboundStage == 3)
					{
						Hellbound.addPoints(points);
					}
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
				}
			}
			else if(val == 2 && hellboundStage >= 7) // Accepting 20 Poison Stingers for Magic Bottle
			{
				if(player.getItemCountByItemId(POISON_STINGER) >= 20)
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
						player.destroyItemByItemId("KiefExchange", POISON_STINGER, 20, this, true);
						player.addItem("KiefExchange", MAGIC_BOTTLE, 1, this, true);
					}
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
				}
			}
			else if(val >= 3 && val <= 5 && hellboundStage == 7) // Accepting  Life Forces for trust points
			{
				long items = 0;
				switch(val)
				{
					case 3:
						// Dim Life Force
						items = player.getItemCountByItemId(DIM_LIFE_FORCE);
						if(items > 0)
						{
							filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
							long points = items * 10;
							player.destroyItemByItemId("KiefExchange", DIM_LIFE_FORCE, items, this, true);
							Hellbound.addPoints(points);
						}
						else
						{
							filename = _path + hellboundStage + "/" + npcId + "-no.htm";
						}
						break;
					case 4:
						// Life Force
						items = player.getItemCountByItemId(LIFE_FORCE);
						if(items > 0)
						{
							filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
							long points = items * 10;
							player.destroyItemByItemId("KiefExchange", LIFE_FORCE, items, this, true);
							Hellbound.addPoints(points);
						}
						else
						{
							filename = _path + hellboundStage + "/" + npcId + "-no.htm";
						}
						break;
					case 5:
						// Contained Life Force
						items = player.getItemCountByItemId(CONTAINED_LIFE_FORCE);
						if(items > 0)
						{
							filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
							long points = items * 100;
							player.destroyItemByItemId("KiefExchange", CONTAINED_LIFE_FORCE, items, this, true);
							Hellbound.addPoints(points);
						}
						else
						{
							filename = _path + hellboundStage + "/" + npcId + "-no.htm";
						}
						break;
				}
			}
		}
		else if(hellboundStage >= 2 && npcId == BERNARDE && player.getEffectBySkillId(NATIVE_TRANSFORM_SKILL) != null)
		{
			if(hellboundStage == 2 && val == 2) // Exchange 5 Darion's Badges for Holy Water
			{
				if(player.getItemCountByItemId(DARION_BADGE) >= 5)
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
						player.destroyItemByItemId("BernardeExchange", DARION_BADGE, 5, this, true);
						player.addItem("BernardeExchange", HOLY_WATER, 1, this, true);
					}
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
				}

			}
			else if(hellboundStage == 3 && val == 3) // Accept Native Treasures
			{
				if(player.getItemCountByItemId(NATIVE_TREASURE) > 0)
				{
					filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
					player.destroyItemByItemId("BernardeExchange", NATIVE_TREASURE, 1, this, true);
					int nativeTreasureCount = ServerVariables.getInt("hb_ber_nt", 0);
					ServerVariables.set("hb_ber_nt", nativeTreasureCount + 1);
					Hellbound.addPoints(1);
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
				}

			}
			else
			{
				filename = _path + hellboundStage + "/" + npcId + "-" + val + ".htm";
			}

			if(val == 0)
				filename = _path + hellboundStage + "/" + npcId + "-native.htm";

		}
		else if(npcId == MING && hellboundRep >= Hellbound.LIMIT[hellboundStage - 1])
		{
			filename = _path + hellboundStage + "/" + npcId + "-1.htm";
		}
		else if(npcId == HUDE)
		{
			if(val == 1) // Saying that player brought Marks of Betrayal and Poison Stingers
			{
				if(player.getItemCountByItemId(MARK_OF_BETRAYAL) >= 30 && player.getItemCountByItemId(POISON_STINGER) >= 60
						&& player.getItemCountByItemId(STANDARD_CARAVAN_CERTIFICATE) == 0
						&& player.getItemCountByItemId(BASIC_CARAVAN_CERTIFICATE) == 1)
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-ok1.htm";
						player.destroyItemByItemId("HudeExchange", MARK_OF_BETRAYAL, 30, this, true);
						player.destroyItemByItemId("HudeExchange", POISON_STINGER, 60, this, true);
						player.destroyItemByItemId("HudeExchange", BASIC_CARAVAN_CERTIFICATE, 1, this, true);
						player.addItem("HudeExchange", STANDARD_CARAVAN_CERTIFICATE, 1, this, true);
					}
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no1.htm";
				}
			}
			else if(val == 2) // Saying that player brought Dim Life Forces, etc...
			{
				if(player.getItemCountByItemId(LIFE_FORCE) >= 80 && player.getItemCountByItemId(CONTAINED_LIFE_FORCE) >= 20
						&& player.getItemCountByItemId(PREMIUM_CARAVAN_CERTIFICATE) == 0
						&& player.getItemCountByItemId(STANDARD_CARAVAN_CERTIFICATE) == 1)
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}
					else
					{
						filename = _path + hellboundStage + "/" + npcId + "-ok2.htm";
						player.destroyItemByItemId("HudeExchange", LIFE_FORCE, 80, this, true);
						player.destroyItemByItemId("HudeExchange", CONTAINED_LIFE_FORCE, 20, this, true);
						player.destroyItemByItemId("HudeExchange", STANDARD_CARAVAN_CERTIFICATE, 1, this, true);
						player.addItem("HudeExchange", PREMIUM_CARAVAN_CERTIFICATE, 1, this, true);
					}
				}
				else
				{
					filename = _path + hellboundStage + "/" + npcId + "-no2.htm";
				}
			}
			else
			{
				if(player.getItemCountByItemId(BASIC_CARAVAN_CERTIFICATE) > 0)
					filename = _path + hellboundStage + "/" + npcId + "-basic.htm";
				else if(player.getItemCountByItemId(STANDARD_CARAVAN_CERTIFICATE) > 0)
					filename = _path + hellboundStage + "/" + npcId + "-standard.htm";
				else if(player.getItemCountByItemId(PREMIUM_CARAVAN_CERTIFICATE) > 0)
					filename = _path + hellboundStage + "/" + npcId + "-premium.htm";
			}
		}
		else if(npcId == JUDE)
		{
			if(val == 1) // Accepting Native Treasures
			{
				if(player.getItemCountByItemId(NATIVE_TREASURE) >= 1)
				{
					filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
					if(hellboundStage == 3)
					{
						int nativeTreasureCount = ServerVariables.getInt("hb_jude_nt", 0);
						ServerVariables.set("hb_jude_nt", nativeTreasureCount + player.getItemCountByItemId(NATIVE_TREASURE));
					}
					player.destroyItemByItemId("JudeExchange", NATIVE_TREASURE, player.getItemCountByItemId(NATIVE_TREASURE), this, true);
					Hellbound.addPoints(player.getItemCountByItemId(NATIVE_TREASURE));
				}
				else
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
			}
		}
		else if(npcId == NATIVE_SLAVE)
		{
			if(val == 1 && hellboundStage == 9) // Accepting Darion Badges
			{
				if(player.getItemCountByItemId(DARION_BADGE) >= 5)
				{
					int acceptCount = 5;
					filename = _path + hellboundStage + "/" + npcId + "-ok.htm";
					player.destroyItemByItemId("NativeSlaves", DARION_BADGE, acceptCount, this, true);
					Hellbound.addPoints(acceptCount * 20);
					DecayTaskManager.getInstance().addDecayTask(this, 0);
				}
				else
					filename = _path + hellboundStage + "/" + npcId + "-no.htm";
			}
		}
		else if(npcId == TRAITOR)
		{
			if(val == 5) // Asking to open the doors 19250003, 19250004
			{
				if(player.getItemCountByItemId(MARK_OF_BETRAYAL) == 0)
				{
					filename = _path + hellboundStage + "/" + npcId + "-nomarks.htm";
				}
				else if(player.getItemCountByItemId(MARK_OF_BETRAYAL) > 0 && player.getItemCountByItemId(MARK_OF_BETRAYAL) < 10)
				{
					filename = _path + hellboundStage + "/" + npcId + "-notenough.htm";
				}
				else
				{
					player.destroyItemByItemId("TraitorExchange", MARK_OF_BETRAYAL, 10, this, true);
					L2DoorInstance door1 = DoorTable.getInstance().getDoor(19250003);
					L2DoorInstance door2 = DoorTable.getInstance().getDoor(19250004);
					if(door1 != null && door2 != null)
					{
						door1.openMe();
						door2.openMe();
					}
					return;
				}
			}
		}
		else if(npcId == QUARRY_SLAVE && hellboundStage == 5 && val == 1)
		{
			((QuarrySlave) getAI()).setFollowTarget(player);
		}
		NpcHtmlMessage html;
		html = new NpcHtmlMessage(player, this, filename, val);
		player.sendPacket(html);

	}

	@Override
	public boolean isInvul()
	{
		return getNpcId() != QUARRY_SLAVE;
	}
}
