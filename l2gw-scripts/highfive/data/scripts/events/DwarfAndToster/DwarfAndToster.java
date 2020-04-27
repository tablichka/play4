package events.DwarfAndToster;

import javolution.util.FastMap;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Files;

import java.util.StringTokenizer;

public class DwarfAndToster extends Functions implements ScriptFile
{

	private static final int npcData[][] = {
			{ 9000, 30930, 87480, 149627, -3431, 0, 0 },
			{ 9001, 30932, 146344, 27880, -2264, 0, 1 },
			{ 9010, 25544, 143514, -168655, -1385, 49152, 2 },
			{ 9024, 31596, -38399, 115046, -2708, 40960, 3 },
			{ 9013, 30656, 106654, 93998, -2096, 0, 4 },
			{ 9014, 30988, 125474, 55889, -3605, 0, 5 },
			{ 9015, 30948, 17740, 62929, -2321, 16384, 6 },
			{ 9017, 30646, 47725, 244533, -6589, 0, 7 },
			{ 9018, 30881, -47077, 47198, -5942, 32768, 8 },
			{ 9019, 30928, -78967, 51236, -4700, 0, 9 },
			{ 9020, 30931, 90472, -7208, -3040, 32768, 10 },
			{ 9021, 30933, -53794, 185087, -4643, 0, 11 },
			{ 9011, 30947, -34610, -101335, -3075, 16384, 12 },
			{ 9022, 30946, 9930, 156268, -2507, 32768, 13 },
			{ 9023, 30975, 102008, -90840, -2000, 16384, 14 },
			{ 9012, 30647, 55014, 211671, -2456, 16384, 15 },
			{ 9016, 31021, 82504, 251976, -7712, 0, 16 }, };

	private static FastMap<Integer, L2NpcInstance> eventnpcs;

	/**
	 * Читает статус эвента из базы.
	 * 
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("DwarfAndToster", "off").equalsIgnoreCase("on");
	}

	public void onLoad()
	{
		if(isActive())
		{
			initEvent();
			_log.info("Loaded Event: DwarfAndToster [state: activated]");
		}
		else
			_log.info("Loaded Event: DwarfAndToster [state: deactivated]");
	}

	public void onReload()
	{

	}

	public void onShutdown()
	{

	}

	/**
	 * Запускает эвент
	 */
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("DwarfAndToster", "on");
			initEvent();
			player.sendMessage("Event 'DwarfAndToster' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.DwarfAndToster.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'DwarfAndToster' already started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Останавливает эвент
	 */
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("DwarfAndToster");
			initEvent();
			player.sendMessage("Event 'DwarfAndToster' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.DwarfAndToster.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'DwarfAndToster' not started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	private void initEvent()
	{

		eventnpcs = new FastMap<Integer, L2NpcInstance>();
		for(int[] element : npcData)
		{
			if(isActive())
			{
				if(NpcTable.getTemplate(element[0]) == null)
				{
					StatsSet npcDat = new StatsSet();
					npcDat.set("npcId", element[0]);
					npcDat.set("displayId", element[1]);
					npcDat.set("level", 1);
					npcDat.set("jClass", "LineageMonster4.Tollese");

					npcDat.set("baseShldDef", 0);
					npcDat.set("baseShldRate", 0);
					npcDat.set("baseCritRate", Math.max(1, 4));

					npcDat.set("name", "");
					npcDat.set("title", "");
					npcDat.set("collision_radius", Double.valueOf("20.000"));
					npcDat.set("collision_height", Double.valueOf("20.000"));
					npcDat.set("sex", "male");
					npcDat.set("type", "L2Npc");
					npcDat.set("ai_type", "npc");
					npcDat.set("baseAtkRange", 40);
					npcDat.set("revardExp", 0);
					npcDat.set("revardSp", 0);
					npcDat.set("basePAtkSpd", 250);
					npcDat.set("baseMAtkSpd", 250);
					npcDat.set("aggroRange", Short.parseShort("0"));
					npcDat.set("rhand", 0);
					npcDat.set("lhand", 0);
					npcDat.set("armor", 0);
					npcDat.set("baseWalkSpd", 100);
					npcDat.set("baseRunSpd", 200);

					npcDat.set("baseHpReg", Double.valueOf("1.000"));
					npcDat.set("baseCpReg", 0);
					npcDat.set("baseMpReg", Double.valueOf("1.000"));

					npcDat.set("baseSTR", 1);
					npcDat.set("baseCON", 1);
					npcDat.set("baseDEX", 1);
					npcDat.set("baseINT", 1);
					npcDat.set("baseWIT", 1);
					npcDat.set("baseMEN", 1);

					npcDat.set("baseHpMax", 1000);
					npcDat.set("baseCpMax", 0);
					npcDat.set("baseMpMax", 1000);
					npcDat.set("basePAtk", 1000);
					npcDat.set("basePDef", 1000);
					npcDat.set("baseMAtk", 1000);
					npcDat.set("baseMDef", 1000);
					npcDat.set("factionId", "");
					npcDat.set("factionRange", Short.parseShort("0"));
					npcDat.set("isDropHerbs", Boolean.parseBoolean("false"));
					npcDat.set("shots", "NONE");
					npcDat.set("corpse_time", 7);

					L2NpcTemplate template = new L2NpcTemplate(npcDat, null);
					NpcTable.replaceTemplate(template);
				}
				L2NpcTemplate template = NpcTable.getTemplate(element[0]);
				try
				{
					L2Spawn sp = new L2Spawn(template);
					sp = new L2Spawn(template);
					sp.setLocx(element[2]);
					sp.setLocy(element[3]);
					sp.setLocz(element[4]);
					sp.setHeading(element[5]);
					sp.setRespawnDelay(0);
					eventnpcs.put(element[6], sp.doSpawn(false));
				}
				catch(SecurityException e)
				{
					e.printStackTrace();
				}
				catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				L2NpcInstance isNpc = L2ObjectsStorage.getByNpcId(element[0]);
				if(isNpc != null)
					isNpc.deleteMe();
			}
		}
	}

	public static void quest()
	{
		L2Player player = (L2Player) self;
		if(npc.getNpcId() == 9016)
		{
			if(player.getVar("GorlumQuest") == null)
			{
				player.setVar("GorlumQuest", "true");
				show(Files.read("data/scripts/events/DwarfAndToster/9016-3.htm", player), player);
			}
			else if(player.getVar("GorlumQuest") != null && player.getVar("Chest") != null)
			{
				String[] s = new String[1];
				s[0] = "16";
				accept(s);
				show(Files.read("data/scripts/events/DwarfAndToster/9016-5.htm", player), player);
			}
		}
	}

	public static void speak(String[] var)
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player, npc))
			show(Files.read("data/html/npcdefault.htm", player), player);
		else
		{
			int val = Integer.valueOf(var[0]);
			if(val > 0)
				show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-" + val + ".htm", player), player);
			else
			{
				// Первый нпц
				if(npc.getNpcId() == 9000)
				{
					if(player.getVar("DwarfAndToster") == null)
						show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + ".htm", player), player);
					else
					{
						if(player.getVar("comlateGorlum") == null)
							show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-5" + ".htm", player), player);
						else
						{
							StringTokenizer st = new StringTokenizer(player.getVar("DwarfAndToster"), ";");
							int count = 0;

							while(st.hasMoreTokens())
							{
								Integer state = Integer.valueOf(st.nextToken());
								for(int[] element : npcData)
									if(element[6] == state)
										count++;
							}
							if(count == 17 || count == 16 && !player.getVar("DwarfAndToster").contains(";1;"))
								show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-4" + ".htm", player), player);
							else
								show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-5" + ".htm", player), player);
						}
					}
				}
				else
				{
					// проводник после первого подхода
					if(npc.getNpcId() == 9001 && player.getVar("DwarfAndToster") != null && !player.getVar("DwarfAndToster").equals("0"))
						show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-letter" + ".htm", player), player);
					else
					{
						if(npc.getNpcId() == 9012 && player.getVar("GorlumQuest") == null)// сундук
							show(Files.read("data/html/npcdefault.htm", player), player);
						else
						{
							if(npc.getNpcId() == 9016 && player.getVar("GorlumQuest") != null && player.getVar("Chest") == null)// горгум
								show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + "-7" + ".htm", player), player);
							else
							{
								if(npc.getNpcId() == 9016 && player.getVar("GorlumQuest") != null && player.getVar("Chest") != null)
									show(Files.read("data/scripts/events/DwarfAndToster/9016-4.htm", player), player);
								else
									show(Files.read("data/scripts/events/DwarfAndToster/" + npc.getNpcId() + ".htm", player), player);
							}
						}
					}
				}
			}
		}
	}

	public static void accept(String[] var)
	{
		L2Player player = (L2Player) self;
		Integer val = Integer.valueOf(var[0]);
		if(val.intValue() == 0 && npc.getNpcId() == 9000)
			player.setVar("DwarfAndToster", val.toString());
		else
		{
			if(npc.getNpcId() != 9000 && npc.getNpcId() != 9016)
			{
				String[] s = new String[1];
				s[0] = "1";
				speak(s);
			}
			if(npc.getNpcId() == 9012)
				player.setVar("Chest", "true");
			if(npc.getNpcId() == 9016)
				player.setVar("comlateGorlum", "1");
			player.setVar("DwarfAndToster", val.toString() + ";" + player.getVar("DwarfAndToster"));
		}

	}

	public static void exit()
	{
		L2Player player = (L2Player) self;
		// player.setVar("DwarfAndToster", null);
		if(npc.getNpcId() == 9000)
			show(Files.read("data/scripts/events/DwarfAndToster/9000-3.htm", player), player);
		else
			show(Files.read("data/scripts/events/DwarfAndToster/exit.htm", player), player);
	}

	public static boolean checkCondition(L2Player player, L2NpcInstance npc)
	{
		boolean canSpeak = true;
		if(player.getVar("DwarfAndTosterComplate") != null)
			return canSpeak = false;
		else
		{
			if(npc.getNpcId() != 9000)
			{
				if(player.getVar("DwarfAndToster") == null)
					canSpeak = false;
				else
				{
					if(npc.getNpcId() != 9001)
					{
						StringTokenizer st = new StringTokenizer(player.getVar("DwarfAndToster"), ";");
						while(st.hasMoreElements())
						{
							int state = Integer.valueOf(st.nextToken());
							for(int[] element : npcData)
								if(element[0] == npc.getNpcId())
									if(element[6] == state)
									{
										canSpeak = false;
										break;
									}
						}
					}
				}
			}
		}
		return canSpeak;
	}

	public static void finish()
	{
		L2Player player = (L2Player) self;
		player.unsetVar("DwarfAndToster");
		player.unsetVar("GorlumQuest");
		player.unsetVar("Chest");
		player.unsetVar("comlateGorlum");
		player.setVar("DwarfAndTosterComplate", String.valueOf(System.currentTimeMillis()));
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(isActive())
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.DwarfAndToster.AnnounceEventStarted", null);
	}
}