package commands.admin;

import javolution.util.FastList;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.instancemanager.superpoint.Superpoint;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;
import ru.l2gw.gameserver.serverpackets.ExServerPrimitive;
import ru.l2gw.gameserver.serverpackets.ExShowTrace;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.tables.TerritoryTable;
import ru.l2gw.util.Location;

import java.io.File;
import java.io.FileWriter;

public class AdminZone extends AdminBase
{
	public static final AdminCommandDescription[] ADMIN_ZONE_COMMANDS = {
			new AdminCommandDescription("admin_zone_check", null),
			new AdminCommandDescription("admin_region", null),
			new AdminCommandDescription("admin_active_region", null),
			new AdminCommandDescription("admin_loc", null),
			new AdminCommandDescription("admin_showloc", null),
			new AdminCommandDescription("admin_location", null),
			new AdminCommandDescription("admin_loc_begin", null),
			new AdminCommandDescription("admin_loc_add", null),
			new AdminCommandDescription("admin_loc_reset", null),
			new AdminCommandDescription("admin_loc_end", null),
			new AdminCommandDescription("admin_setref", null),
			new AdminCommandDescription("admin_zone_start", null),
			new AdminCommandDescription("admin_zone_end", null),
			new AdminCommandDescription("admin_zone_point", null),
			new AdminCommandDescription("admin_show_zone", null),
			new AdminCommandDescription("admin_show_terr", null),
			new AdminCommandDescription("admin_show_superpoint", null),
	};

	private static FastList<int[]> create_loc;
	private static int create_loc_id;
	private static String zone_id = "";
	private static FastList<Location> points;

	private static void locationMenu(L2Player activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		StringBuffer replyMSG = new StringBuffer("<html><body><title>Location Create</title>");

		replyMSG.append("<center><table width=260><tr>");
		replyMSG.append("<td width=70>Location:</td>");
		replyMSG.append("<td width=50><edit var=\"loc\" width=50 height=12></td>");
		replyMSG.append("<td width=50><button value=\"Show\" action=\"bypass -h admin_showloc $loc\" width=50 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=90><button value=\"New Location\" action=\"bypass -h admin_loc_begin $loc\" width=90 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table><br><br></center>");

		if(create_loc != null)
		{
			replyMSG.append("<center><table width=260><tr>");
			replyMSG.append("<td width=80><button value=\"Add Point\" action=\"bypass -h admin_loc_add menu\" width=80 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
			replyMSG.append("<td width=90><button value=\"Reset Points\" action=\"bypass -h admin_loc_reset menu\" width=90 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
			replyMSG.append("<td width=90><button value=\"End Location\" action=\"bypass -h admin_loc_end menu\" width=90 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
			replyMSG.append("</tr></table></center>");
		}

		replyMSG.append("</body></html>");
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private static ExShowTrace Points2Trace(FastList<int[]> _points, int _step, boolean auto_compleate)
	{
		ExShowTrace result = new ExShowTrace();

		int[] _prev = null;
		int[] _first = null;
		for(int[] p : _points)
		{
			if(_first == null)
				_first = p;

			if(_prev != null)
				result.addLine(_prev[0], _prev[1], _prev[2], p[0], p[1], p[2], _step, 60000);

			_prev = p;
		}

		if(_prev == null || _first == null)
			return result;

		if(auto_compleate)
			result.addLine(_prev[0], _prev[1], _prev[2], _first[0], _first[1], _first[2], _step, 60000);

		return result;
	}

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_zone_check"))
		{
			activeChar.sendMessage("===== Active Zones =====");
			L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : activeChar;

			if(target.getZones() != null)
			{
				for(L2Zone zone : target.getZones())
				{
					activeChar.sendMessage(zone.toString());
					activeChar.sendMessage("id[" + zone.getZoneId() + "]: state [" + (zone.isActive(target.getReflection()) ? "A" : "N") + (zone.isInsideZone(target) ? "I" : "") + "]");
					for(ZoneType zt : zone.getTypes())
						activeChar.sendMessage("id[" + zone.getZoneId() + "]: type " + zt);
				}
			}
			activeChar.sendMessage("===== Inside Zones =====");
			target.sendInsideZones(activeChar);
			activeChar.sendMessage("===== Region Zones =====");
			if(ZoneManager.getInstance().getAllZones(target.getX(), target.getY()) != null)
			{
				for(L2Zone zone : ZoneManager.getInstance().getAllZones(target.getX(), target.getY()))
				{
					activeChar.sendMessage("Zone: " + zone.getZoneName());
					activeChar.sendMessage("id[" + zone.getZoneId() + "]: state " + (zone.isActive(target.getReflection()) ? "active" : "not active"));
					for(ZoneType zt : zone.getTypes())
						activeChar.sendMessage("id[" + zone.getZoneId() + "]: type " + zt);
				}
			}

			activeChar.sendMessage("======= Mob Spawns =======");
			for(L2Spawn spawn : SpawnTable.getInstance().getSpawnTable().values())
			{
				int location = spawn.getLocation();
				if(location == 0)
					continue;
				L2Territory terr = TerritoryTable.getInstance().getLocation(location);

				if(terr == null)
					continue;

				if(terr.isInside(activeChar.getX(), activeChar.getY()))
					activeChar.sendMessage("Territory: " + terr.getName());
			}
		}
		else if(command.equals("admin_region"))
		{
			activeChar.sendMessage("Current region: " + activeChar.getCurrentRegion().getName());
			activeChar.sendMessage("Objects list:");
			for(L2Object o : activeChar.getCurrentRegion().getObjectsList(activeChar.getReflection()))
			{
				if(o != null)
					activeChar.sendMessage(o.toString());
			}
			int gx = ((activeChar.getX() - L2World.MAP_MIN_X) >> 15) + Config.GEO_X_FIRST;
			int gy = ((activeChar.getY() - L2World.MAP_MIN_Y) >> 15) + Config.GEO_Y_FIRST;
			activeChar.sendMessage("World region: " + gx + "_" + gy);
		}
		else if(command.equals("admin_active_region"))
			activeChar.sendMessage("Active regions size: " + L2World.getActiveRegionsCount());
			/*
					 * Пишет в консоль текущую точку для локации, оформляем в виде SQL запроса
					 * пример: (8699,'loc_8699',111104,-112528,-1400,-1200),
					 * Удобно для рисования локаций под спавн, разброс z +100/-10
					 * необязательные параметры: id локации и название локации
					 * Бросает бутылку, чтобы не запутаццо :)
					 */
		else if(command.equals("admin_loc"))
		{
			String loc_id = "0";
			String loc_name;
			if(args.length > 0)
				loc_id = args[0];
			if(args.length > 1)
				loc_name = args[1];
			else
				loc_name = "loc_" + loc_id;
			System.out.println("(" + loc_id + ",'" + loc_name + "'," + activeChar.getX() + "," + activeChar.getY() + "," + activeChar.getZ() + "," + (activeChar.getZ() + 100) + "),");
			activeChar.sendMessage("Point saved.");
			L2ItemInstance temp = new L2ItemInstance(IdFactory.getInstance().getNextId(), 1060);
			temp.dropMe(activeChar, activeChar.getLoc());
		}
		else if(command.equals("admin_location"))
			locationMenu(activeChar);
		else if(command.equals("admin_loc_begin"))
		{
			if(args.length < 1)
			{
				activeChar.sendMessage("Usage: //loc_begin <location_id>");
				locationMenu(activeChar);
				return false;
			}
			try
			{
				create_loc_id = Integer.valueOf(args[0]);
			}
			catch(Exception E)
			{
				activeChar.sendMessage("location_id should be integer");
				create_loc = null;
				locationMenu(activeChar);
				return false;
			}

			create_loc = new FastList<int[]>();
			create_loc.add(new int[]{activeChar.getX(), activeChar.getY(), activeChar.getZ(), activeChar.getZ() + 100});
			activeChar.sendMessage("Now you can add points...");
			activeChar.sendPacket(new ExShowTrace());
			locationMenu(activeChar);
		}
		else if(command.equals("admin_loc_add"))
		{
			if(create_loc == null)
			{
				activeChar.sendMessage("Location not started");
				locationMenu(activeChar);
				return false;
			}

			create_loc.add(new int[]{activeChar.getX(), activeChar.getY(), activeChar.getZ(), activeChar.getZ() + 100});

			if(create_loc.size() > 1)
				activeChar.sendPacket(Points2Trace(create_loc, 50, false));
			if(args.length > 0 && args[0].equals("menu"))
				locationMenu(activeChar);
		}
		else if(command.equals("admin_loc_reset"))
		{
			if(create_loc == null)
			{
				activeChar.sendMessage("Location not started");
				locationMenu(activeChar);
				return false;
			}

			create_loc.clear();
			activeChar.sendPacket(new ExShowTrace());
			locationMenu(activeChar);
		}
		else if(command.equals("admin_loc_end"))
		{
			if(create_loc == null)
			{
				activeChar.sendMessage("Location not started");
				locationMenu(activeChar);
				return false;
			}
			if(create_loc.size() < 3)
			{
				activeChar.sendMessage("Minimum location size 3 points");
				locationMenu(activeChar);
				return false;
			}

			String prefix = "(" + create_loc_id + ",'loc_" + create_loc_id + "',";
			for(int[] _p : create_loc)
				System.out.println(prefix + _p[0] + "," + _p[1] + "," + _p[2] + "," + _p[3] + "),");
			System.out.println("");

			activeChar.sendPacket(Points2Trace(create_loc, 50, true));
			create_loc = null;
			create_loc_id = 0;
			activeChar.sendMessage("Location Created, check stdout");
			if(args.length > 0 && args[0].equals("menu"))
				locationMenu(activeChar);
		}
		else if(command.equals("admin_showloc"))
		{
			if(args.length < 1)
			{
				activeChar.sendMessage("Usage: //showloc <location>");
				return false;
			}

			String loc_id = args[0];
			L2Territory terr = TerritoryTable.getInstance().getLocations().get(loc_id);

			if(terr == null)
				terr = TerritoryTable.getInstance().getLocations().get("sql_terr_" + loc_id);

			if(terr == null)
			{
				activeChar.sendMessage("Territory <" + loc_id + "> undefined.");
				return false;
			}

			if(!terr.isInside(activeChar.getX(), activeChar.getY()))
			{
				int[] _loc = terr.getRandomPoint(activeChar.isFlying());
				activeChar.teleToLocation(_loc[0], _loc[1], _loc[2]);
			}
			activeChar.sendPacket(Points2Trace(terr.getCoords(), 50, true));
		}
		else if(command.equals("admin_setref"))
		{
			if(args.length < 1)
			{
				activeChar.sendMessage("Usage: //set_ref <reflection>");
				return false;
			}

			int ref_id = Integer.parseInt(args[0]);
			if(ref_id != 0 && ReflectionTable.getInstance().getById(ref_id) == null)
			{
				activeChar.sendMessage("Reflection <" + ref_id + "> not found.");
				return false;
			}

			L2Object target = activeChar;
			L2Object obj = activeChar.getTarget();
			if(obj != null)
				target = obj;

			target.setReflection(ref_id);
			target.decayMe();
			target.spawnMe();
		}
		else if(command.equals("admin_zone_start"))
		{
			if(zone_id != null && zone_id.length() > 0)
			{
				activeChar.sendMessage("Zone alrady started " + zone_id);
				activeChar.sendMessage("Use //zone_end ");
				return true;
			}
			try
			{
				zone_id = args[0];
				activeChar.sendMessage("Start zone record: " + zone_id);
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Usage //zone_start id");
			}
		}
		else if(command.equals("admin_zone_point"))
		{
			if(zone_id == null || zone_id.length() < 1)
			{
				activeChar.sendMessage("No zone record started!");
			}
			else
			{
				if(points == null) points = new FastList<Location>();
				points.add(new Location(activeChar.getX(), activeChar.getY(), activeChar.getZ()));
				activeChar.sendMessage("Add point to zone(" + zone_id + ") " + activeChar.getX() + " " + activeChar.getY() + " " + activeChar.getZ());
				ExShowTrace est = new ExShowTrace();
				for(Location loc : points)
					est.addTrace(loc.getX(), loc.getY(), loc.getZ(), 30000);
				activeChar.sendPacket(est);
			}
		}
		else if(command.equals("admin_zone_end"))
		{
			if(zone_id == null || zone_id.length() < 1 || points == null || points.size() < 1)
			{
				activeChar.sendMessage("No zone started or points defined!");
			}
			else
			{
				File file = new File(Config.DATAPACK_ROOT, "data/zones/zone_" + zone_id + ".txt");
				try
				{
					FileWriter fw = new FileWriter(file);
					fw.flush();

					for(Location point : points)
					{
						fw.write(point.getX() + "," + point.getY() + "," + point.getZ() + "\n");
					}
					fw.close();
					activeChar.sendMessage("Zone id " + zone_id + " saved to zones/zone_" + zone_id + ".txt");
					zone_id = "";
					points.clear();
				}
				catch(Exception e)
				{
				}
			}
		}
		else if(command.equals("admin_show_zone"))
		{
			String zoneName;
			if(args.length > 0)
			{
				zoneName = args[0];
				L2Zone zone = ZoneManager.getInstance().getZoneByName(zoneName);
				if(zone == null)
				{
					Functions.sendSysMessage(activeChar, "Zone: " + zoneName + " not found.");
					return true;
				}
				activeChar.sendPacket(new ExServerPrimitive(zone, 0x00, 0xFF, 0x00));
				return true;
			}

			GArray<L2Zone> zones = activeChar.getZones();
			if(zones == null || zones.size() < 1)
			{
				Functions.sendSysMessage(activeChar, "You are currently outside any zones.");
				return true;
			}

			for(L2Zone zone : zones)
				if(zone != null)
					activeChar.sendPacket(new ExServerPrimitive(zone, 0x00, 0xFF, 0x00));
		}
		else if(command.equals("admin_show_terr"))
		{
			String terrName;
			if(args.length > 0)
			{
				terrName = args[0];
				if("all".equalsIgnoreCase(terrName))
				{
					GArray<String> terrs = new GArray<>();
					for(L2NpcInstance npc : L2World.getAroundNpc(activeChar))
					{
						if(npc.getSpawnDefine() != null)
						{
							for(L2Territory terr : npc.getSpawnDefine().getMaker().getTerritories())
							{
								if(!terrs.contains(terr.getName()))
								{
									terrs.add(terr.getName());
									activeChar.sendPacket(new ExServerPrimitive(terr, 0xFF, 0xFF, 0x00));
								}
							}
						}
						else if(npc.getSpawn() != null && npc.getSpawn().getLocation() > 0)
						{
							L2Territory terr = TerritoryTable.getInstance().getLocation(npc.getSpawn().getLocation());
							if(terr != null && !terrs.contains(terr.getName()))
							{
								terrs.add(terr.getName());
								activeChar.sendPacket(new ExServerPrimitive(terr, 0xFF, 0xFF, 0x00));
							}
						}
					}

				}
				else if("npc".equalsIgnoreCase(terrName))
				{
					L2NpcInstance npc = activeChar.getTarget() instanceof L2NpcInstance ? (L2NpcInstance) activeChar.getTarget() : null;
					if(npc == null)
					{
						Functions.sendSysMessage(activeChar, "NPC must be in target.");
						return true;
					}

					if(npc.getSpawnDefine() != null)
					{
						for(L2Territory terr : npc.getSpawnDefine().getMaker().getTerritories())
						{
							activeChar.sendPacket(new ExServerPrimitive(terr, 0xFF, 0xFF, 0x00));
						}
					}
					else if(npc.getSpawn() != null && npc.getSpawn().getLocation() > 0)
					{
						L2Territory terr = TerritoryTable.getInstance().getLocation(npc.getSpawn().getLocation());
						if(terr != null)
							activeChar.sendPacket(new ExServerPrimitive(terr, 0xFF, 0xFF, 0x00));
					}
				}
				else
				{
					L2Territory terr = TerritoryTable.getInstance().getLocations().get(terrName);
					if(terr == null)
					{
						Functions.sendSysMessage(activeChar, "Territory: " + terrName + " not found.");
						return true;
					}
					activeChar.sendPacket(new ExServerPrimitive(terr, 0xFF, 0xFF, 0x00));
				}
			}
			else
				Functions.sendSysMessage(activeChar, "//show_terr (territory_name | all | npc)");
		}
		else if(command.equals("admin_show_superpoint"))
		{
			String superpointName;
			if(args.length > 0)
			{
				superpointName = args[0];
				Superpoint sp = SuperpointManager.getInstance().getSuperpointByName(superpointName);
				if(sp == null)
				{
					Functions.sendSysMessage(activeChar, "Superpoint: " + superpointName + " not found.");
					return true;
				}

				activeChar.sendPacket(new ExServerPrimitive(sp, 0x00, 0x00, 0xFF));
			}
			else
				Functions.sendSysMessage(activeChar, "//show_superpoint <name>");
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_ZONE_COMMANDS;
	}
}