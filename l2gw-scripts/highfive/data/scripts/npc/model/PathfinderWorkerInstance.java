package npc.model;

import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.TownManager;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.Reflection;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import quests.Instances.*;

import java.util.List;
import java.util.StringTokenizer;

/**
 * User: ic
 * Date: 09.01.2010
 */
public class PathfinderWorkerInstance extends L2NpcInstance
{
	private static String _path = "data/html/instances/";
	private static final int MAKER = 32484;
	private static final int GIFT_GIVER = 32485;

	// Gifts
	private static final int ESSENCE_OF_KAMALOKA = 13002;
	private static final int REWARD_D = 13003;
	private static final int REWARD_C = 13004;
	private static final int REWARD_B = 13005;
	private static final int REWARD_A = 13006;
	private static final int REWARD_S = 13007;

	private int num;

	private static final int[] PATHFINDER_SUPPLIES = {
			12824, //	Pathfinder Supplies (level 1)
			10836, //	Pathfinder Supplies (level 2)
			12825, //	Pathfinder Supplies (level 3)
			10837, //	Pathfinder Supplies (level 4)
			10838, //	Pathfinder Supplies (level 5)
			10839, //	Pathfinder Supplies (level 6)
			10840, //	Pathfinder Supplies (level 7)
			10841, //	Pathfinder Supplies (level 8)
			12826, //	Pathfinder Supplies (level 9)
			12827, //	Pathfinder Supplies (level 10)
			10842, //	Pathfinder Supplies (level 11)
			10843, //	Pathfinder Supplies (level 12)
			10844, //	Pathfinder Supplies (level 13)
			10845, //	Pathfinder Supplies (level 14)
			10846, //	Pathfinder Supplies (level 15)
			12828, //	Pathfinder Supplies (level 16)
			12829, //	Pathfinder Supplies (level 17)
			10847, //	Pathfinder Supplies (level 18)
			10848, //	Pathfinder Supplies (level 19)
			10849, //	Pathfinder Supplies (level 20)
			10850, //	Pathfinder Supplies (level 21)
			10851, //	Pathfinder Supplies (level 22)
			12830, //	Pathfinder Supplies (level 23)
			12831, //	Pathfinder Supplies (level 24)
			10852, //	Pathfinder Supplies (level 25)
			10853, //	Pathfinder Supplies (level 26)
			10854, //	Pathfinder Supplies (level 27)
			10855, //	Pathfinder Supplies (level 28)
			10856, //	Pathfinder Supplies (level 29)
			12832, //	Pathfinder Supplies (level 30)
			12833, //	Pathfinder Supplies (level 31)
			10857, //	Pathfinder Supplies (level 32)
			10858, //	Pathfinder Supplies (level 33)
			10859, //	Pathfinder Supplies (level 34)
			10860, //	Pathfinder Supplies (level 35)
			10861, //	Pathfinder Supplies (level 36)
			12834, //	Pathfinder Supplies (level 37)
			10862, //	Pathfinder Supplies (level 38)
			10863, //	Pathfinder Supplies (level 39)
			10864, //	Pathfinder Supplies (level 40)
			10865, //	Pathfinder Supplies (level 41)
	};

	public PathfinderWorkerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		int npcId = getNpcId();
		String filename;
		NpcHtmlMessage html;

		if(npcId == MAKER)
		{
			if(Config.PREMIUM_RIM_ONLY && !player.isGM() && !player.isPremiumEnabled()) // Only accessible for GMs and Premium Enabled accounts
			{
				filename = _path + npcId + (val == 0 ? "" : "-" + val) + ".htm";
				html = new NpcHtmlMessage(player, this, filename, val);
				player.sendPacket(html);
				return;
			}

			if(val == 1) // Enter Rim Kamaloka
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null && inst.getTemplate().getType() == 6)
				{
					filename = _path + npcId + "-inprogress.htm";
					html = new NpcHtmlMessage(player, this, filename, val);
					html.replace("%id%", String.valueOf(inst.getTemplate().getId()));
					player.sendPacket(html);
				}
				else
				{

					int nearestTown = TownManager.getInstance().getClosestTownNumber(this);
					String town;
					switch(nearestTown)
					{
						case 1:
							town = "talkingisland";
							break;
						case 2:
							town = "elvenvillage";
							break;
						case 3:
							town = "darkelfvillage";
							break;
						case 4:
							town = "orcvillage";
							break;
						case 5:
							town = "dwarvenvillage";
							break;
						case 6:
							town = "gludio";
							break;
						case 7:
							town = "gludin";
							break;
						case 8:
							town = "dion";
							break;
						case 9:
							town = "giran";
							break;
						case 10:
							town = "oren";
							break;
						case 11:
							town = "aden";
							break;
						case 12:
							town = "hunters";
							break;
						case 13:
							town = "heine";
							break;
						case 14:
							town = "rune";
							break;
						case 15:
							town = "goddard";
							break;
						case 16:
							town = "schuttgart";
							break;
						case 17:
							town = "kamael";
							break;
						case 18:
							town = "primeval";
							break;
						default:
							town = "aden";
					}

					filename = _path + npcId + "-" + town + ".htm";
					html = new NpcHtmlMessage(player, this, filename, val);
					player.sendPacket(html);
				}
			}
			else if(val == 5) // Exchange D-Grade
				exchange(player, npcId, val, ESSENCE_OF_KAMALOKA, 10, REWARD_D, 1);
			else if(val == 6) // Exchange C-Grade
				exchange(player, npcId, val, ESSENCE_OF_KAMALOKA, 20, REWARD_C, 1);
			else if(val == 7) // Exchange B-Grade
				exchange(player, npcId, val, ESSENCE_OF_KAMALOKA, 50, REWARD_B, 1);
			else if(val == 8) // Exchange B-Grade
				exchange(player, npcId, val, ESSENCE_OF_KAMALOKA, 100, REWARD_A, 1);
			else if(val == 9) // Exchange S-Grade
				exchange(player, npcId, val, ESSENCE_OF_KAMALOKA, 200, REWARD_S, 1);
			else
			{
				filename = _path + npcId + "-" + val + ".htm";
				html = new NpcHtmlMessage(player, this, filename, val);
				player.sendPacket(html);
			}
		}
		else if(npcId == GIFT_GIVER)
		{
			if(val == 1) // View the results
			{
				int results = getResults(player);
				String rank = getRank(player);
				filename = _path + npcId + "-calc.htm";
				if(results > 0)
					filename = _path + npcId + "-results.htm";
				html = new NpcHtmlMessage(player, this, filename, val);
				String[] hints = {
						"Some of the Kanabions haven't get used to their new bodies so if you are quick enough you can strike them from the very beginning of their life to give them a great damage!",
						"Be aware of Kanabion's frenzy, since their power increases rapidly so not many adventurers are able to stay alive!",
				};
				html.replace("%hint%", hints[Rnd.get(hints.length)]);
				html.replace("%grade%", rank);

				if(rank.equals("D"))
					html.replace("%desc%", "Not very well, but you always can try once again later to improve your results or try different level.");
				else if(rank.equals("C"))
					html.replace("%desc%", "Not so bad, your experience is raising each time you success Rim Kamaloka.");
				else if(rank.equals("B"))
					html.replace("%desc%", "Very well, your experience will help other adventurers!");
				else if(rank.equals("A"))
					html.replace("%desc%", "Excellent, adventurers like you will certainly defeat the Darkness in the Rim Kamaloka!");
				else if(rank.equals("S"))
					html.replace("%desc%", "You are the best adventurer we have ever seen!");

				player.sendPacket(html);
				return;
			}
			else if(val == 2) // Receive the supplies
			{
				int results = getResults(player);
				boolean received = player.getVarB("RimKamalokaSupplyReceived");
				String essences = "We are sorry, but we could not collect any valuable amount of Kanabion's Essences.";
				if(results > PATHFINDER_SUPPLIES.length)
					results = PATHFINDER_SUPPLIES.length;

				if(received)
					filename = _path + npcId + "-alreadygave.htm";
				else if(results == 0)
					filename = _path + npcId + "-calc.htm";
				else
				{
					if(player.getInventory().slotsLeft() < 10)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
						player.sendActionFailed();
						return;
					}

					int rewardCount = 0;
					int kamalevel = getKamaLevel(player);
					String rank = getRank(player);
					filename = _path + npcId + "-" + val + ".htm";
					player.setVar("RimKamalokaSupplyReceived", "true");
					essences = "We were able to collect some Kamaloka's Essences from the Kanabions you have killed during this battle.";
					int supplies = getSuppliesLevel(kamalevel, rank) - 1;
					if(supplies < 0)
						_log.info(this + ": getSuppliesLevel returned 0 and therefore ArrayIndexOutOfBound error is possible.");
					else
						player.addItem("PathfinderGift", PATHFINDER_SUPPLIES[supplies], 1, this, true);

					switch(kamalevel)
					{
						case 25:
						case 30:
							rewardCount = 1;
							break;
						case 35:
							rewardCount = 2;
							break;
						case 40:
							rewardCount = 3;
							break;
						case 45:
							rewardCount = 4;
							break;
						case 50:
							rewardCount = 5;
							break;
						case 55:
							rewardCount = 6;
							break;
						case 60:
							rewardCount = 7;
							break;
						case 65:
							rewardCount = 8;
							break;
						case 70:
							rewardCount = 9;
							break;
						case 75:
							rewardCount = 10;
							break;
						default:
							break;
					}
					if(rewardCount > 0)
						player.addItem("PathfinderGift", ESSENCE_OF_KAMALOKA, rewardCount, this, true);

				}

				html = new NpcHtmlMessage(player, this, filename, val);
				html.replace("%ticket%", essences);

				player.sendPacket(html);
				return;
			}

			filename = _path + npcId + "-" + val + ".htm";
			html = new NpcHtmlMessage(player, this, filename, val);
			player.sendPacket(html);
		}
		else
			super.showChatWindow(player, val);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(actualCommand.equals("enterDC"))
		{
			player.setVar("dc", num);
			if(Rnd.chance(50))
				player.teleToLocation(-114597, -152501, -6750);
			else
				player.teleToLocation(-114589, -154162, -6750);
		}
		else if(getNpcId() == MAKER)
		{
			if(actualCommand.equalsIgnoreCase("instance"))
			{
				int npcId = getNpcId();
				String filename;
				NpcHtmlMessage html;

				if(Config.PREMIUM_RIM_ONLY && !player.isGM() && !player.isPremiumEnabled()) // Only accessible for GMs and Premium Enabled accounts
				{
					filename = _path + npcId + ".htm";
					html = new NpcHtmlMessage(player, this, filename, 0);
					player.sendPacket(html);
					return;
				}

				int instId = Integer.parseInt(st.nextToken());
				InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);

				if(it == null)
				{
					_log.warn(this + " try to enter instance id: " + instId + " but no instance template!");
					return;
				}

				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				List<L2Player> party = new FastList<L2Player>();

				if(inst != null)
				{
					if(inst.getTemplate().getId() != instId)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON));
						filename = _path + getNpcId() + "-" + "wrongtime.htm";
						html = new NpcHtmlMessage(player, this, filename, 0);
						player.sendPacket(html);
						return;
					}
					if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
					{
						player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
						filename = _path + getNpcId() + "-" + "wrongtime.htm";
						html = new NpcHtmlMessage(player, this, filename, 0);
						player.sendPacket(html);
						return;
					}
					if(player.getParty() != null)
					{
						filename = _path + getNpcId() + "-" + "party.htm";
						html = new NpcHtmlMessage(player, this, filename, 0);
						player.sendPacket(html);
						return;
					}
					//player.setVar("InstanceRP", player.getX() + "," + player.getY() + "," + player.getZ());
					player.setStablePoint(player.getLoc());
					player.teleToLocation(inst.getTemplate().getStartLoc(), inst.getReflection());
					return;
				}

				if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(instId) >= it.getMaxCount())
				{
					player.sendPacket(new SystemMessage(SystemMessage.THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED_YOU_CANNOT_ENTER));
					return;
				}

				if(player.getParty() != null)
				{
					filename = _path + getNpcId() + "-" + "party.htm";

					html = new NpcHtmlMessage(player, this, filename, 0);
					player.sendPacket(html);
					return;
				}

				if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
				{
					player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
					filename = _path + getNpcId() + "-" + "wrongtime.htm";
					html = new NpcHtmlMessage(player, this, filename, 0);
					player.sendPacket(html);
					return;
				}
				else if(player.getVar("instance-" + it.getType()) != null)
				{
					player.sendPacket(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(player));
					filename = _path + getNpcId() + "-" + "wrongtime.htm";
					html = new NpcHtmlMessage(player, this, filename, 0);
					player.sendPacket(html);
					return;
				}

				party.add(player);

				inst = InstanceManager.getInstance().createNewInstance(instId, party);
				if(inst != null)
					for(L2Player member : party)
						if(member != null)
						{
							//member.setVar("InstanceRP", member.getX() + "," + member.getY() + "," + member.getZ());
							member.setStablePoint(member.getLoc());
							clearAllRimPoints(member);
							member.teleToLocation(it.getStartLoc(), inst.getReflection());
						}
			}
			else
				super.onBypassFeedback(player, command);
		}
		else if(getNpcId() == GIFT_GIVER)
		{
			if(actualCommand.equalsIgnoreCase("exitInstance"))
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst == null)
				{
					_log.warn(this + " try to exit from instance but no instance! " + player + " reflection: " + player.getReflection());
					return;
				}
				if(isInRange(player, 300))
					player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);
			}
			else
				super.onBypassFeedback(player, command);
		}
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void onSpawn()
	{
		if(getNpcId() == GIFT_GIVER)
		{
			Instance inst = this.getSpawn().getInstance();
			Reflection ref = ReflectionTable.getInstance().getById(getReflection());
			for(L2Object object : ref.getAllObjects())
				if(object instanceof L2MonsterInstance)
				{
					((L2MonsterInstance) object).getSpawn().stopRespawn();
					((L2MonsterInstance) object).getSpawn().despawnAll();
				}

			inst.successEnd();
			Instance._log.info("Rim Kamaloka " + inst + " success. Despawned all mobs, gotta calc scores.");
		}
		super.onSpawn();
		num = getAIParams() != null ? getAIParams().getInteger("Num", 0) : 0;
	}

	public void clearAllRimPoints(L2Player player)
	{
		player.setVar("RimKamalokaSupplyReceived", "false");
		RimKamaloka_25.clearPoints(player);
		RimKamaloka_30.clearPoints(player);
		RimKamaloka_35.clearPoints(player);
		RimKamaloka_40.clearPoints(player);
		RimKamaloka_45.clearPoints(player);
		RimKamaloka_50.clearPoints(player);
		RimKamaloka_55.clearPoints(player);
		RimKamaloka_60.clearPoints(player);
		RimKamaloka_65.clearPoints(player);
		RimKamaloka_70.clearPoints(player);
		RimKamaloka_75.clearPoints(player);
	}

	public void exchange(L2Player player, int npcId, int val, int srcItemId, int srcItemCount, int dstItemId, int dstItemCount)
	{
		String filename;
		NpcHtmlMessage html;

		if(player.getItemCountByItemId(srcItemId) >= srcItemCount)
		{
			if(player.getInventory().slotsLeft() < 10)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
				player.sendActionFailed();
				return;
			}
			else
			{
				filename = _path + npcId + "-ok.htm";
				player.destroyItemByItemId("PathfinderExchange", srcItemId, srcItemCount, this, true);
				player.addItem("PathfinderExchange", dstItemId, dstItemCount, this, true);
			}
		}
		else
			filename = _path + npcId + "-notok.htm";

		html = new NpcHtmlMessage(player, this, filename, val);
		player.sendPacket(html);

	}

	public int getResults(L2Player player)
	{
		int points = 0;
		if(RimKamaloka_25.getPoints(player) > points)
			points = RimKamaloka_25.getPoints(player);
		else if(RimKamaloka_30.getPoints(player) > points)
			points = RimKamaloka_30.getPoints(player);
		else if(RimKamaloka_35.getPoints(player) > points)
			points = RimKamaloka_35.getPoints(player);
		else if(RimKamaloka_40.getPoints(player) > points)
			points = RimKamaloka_40.getPoints(player);
		else if(RimKamaloka_45.getPoints(player) > points)
			points = RimKamaloka_45.getPoints(player);
		else if(RimKamaloka_50.getPoints(player) > points)
			points = RimKamaloka_50.getPoints(player);
		else if(RimKamaloka_55.getPoints(player) > points)
			points = RimKamaloka_55.getPoints(player);
		else if(RimKamaloka_60.getPoints(player) > points)
			points = RimKamaloka_60.getPoints(player);
		else if(RimKamaloka_65.getPoints(player) > points)
			points = RimKamaloka_65.getPoints(player);
		else if(RimKamaloka_70.getPoints(player) > points)
			points = RimKamaloka_70.getPoints(player);
		else if(RimKamaloka_75.getPoints(player) > points)
			points = RimKamaloka_75.getPoints(player);

		return points;
	}

	public int getKamaLevel(L2Player player)
	{

		if(RimKamaloka_25.getPoints(player) > 0)
			return 25;
		else if(RimKamaloka_30.getPoints(player) > 0)
			return 30;
		else if(RimKamaloka_35.getPoints(player) > 0)
			return 35;
		else if(RimKamaloka_40.getPoints(player) > 0)
			return 40;
		else if(RimKamaloka_45.getPoints(player) > 0)
			return 45;
		else if(RimKamaloka_50.getPoints(player) > 0)
			return 50;
		else if(RimKamaloka_55.getPoints(player) > 0)
			return 55;
		else if(RimKamaloka_60.getPoints(player) > 0)
			return 60;
		else if(RimKamaloka_65.getPoints(player) > 0)
			return 65;
		else if(RimKamaloka_70.getPoints(player) > 0)
			return 70;
		else if(RimKamaloka_75.getPoints(player) > 0)
			return 75;

		return 0;
	}

	public String getRank(L2Player player)
	{
		int results = getResults(player);

		if(results > 0 && results < 10)
			return "D";
		else if(results >= 10 && results < 20)
			return "C";
		else if(results >= 20 && results < 30)
			return "B";
		else if(results >= 30 && results < 40)
			return "A";
		else if(results >= 40)
			return "S";

		return "NG";
	}

	public int getSuppliesLevel(int kLevel, String playerRank)
	{
		int suppliesLevel = 0;
		int offset = 0;
		if(playerRank.equals("D"))
			offset = 1;
		else if(playerRank.equals("C"))
			offset = 2;
		else if(playerRank.equals("B"))
			offset = 3;
		else if(playerRank.equals("A"))
			offset = 4;
		else if(playerRank.equals("S"))
			offset = 5;

		switch(kLevel)
		{
			case 25:
				suppliesLevel += offset;
				break;
			case 30:
				suppliesLevel += 1 + offset;
				break;
			case 35:
				suppliesLevel += 3 + offset;
				break;
			case 40:
				suppliesLevel += 5 + offset;
				break;
			case 45:
				suppliesLevel += 10 + offset;
				break;
			case 50:
				suppliesLevel += 12 + offset;
				break;
			case 55:
				suppliesLevel += 17 + offset;
				break;
			case 60:
				suppliesLevel += 21 + offset;
				break;
			case 65:
				suppliesLevel += 26 + offset;
				break;
			case 70:
				suppliesLevel += 31 + offset;
				break;
			case 75:
				suppliesLevel += 36 + offset;
				break;

			default:
				break;
		}

		return suppliesLevel;
	}
}
