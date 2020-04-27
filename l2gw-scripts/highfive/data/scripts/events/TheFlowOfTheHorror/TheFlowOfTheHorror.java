package events.TheFlowOfTheHorror;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;

import java.util.ArrayList;

public class TheFlowOfTheHorror extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private static int Gilmore = 30754;
	private static int Shackle = 20235;

	private static L2NpcInstance oldGilmore;

	private static int _stage = 1;

	private static Log _log = LogFactory.getLog(L2Spawn.class.getName());

	private static ArrayList<L2MonsterInstance> _spawns = new ArrayList<L2MonsterInstance>();

	private static Location[] points11 = new Location[8];
	private static Location[] points12 = new Location[5];
	private static Location[] points13 = new Location[6];
	private static Location[] points21 = new Location[7];
	private static Location[] points22 = new Location[8];
	private static Location[] points23 = new Location[8];
	private static Location[] points31 = new Location[7];
	private static Location[] points32 = new Location[7];
	private static Location[] points33 = new Location[7];

	public void onLoad()
	{
		//Рукав 1, линия 1
		points11[0] = new Location(84211, 117965, -3020);
		points11[1] = new Location(83389, 117590, -3036);
		points11[2] = new Location(82226, 117051, -3150);
		points11[3] = new Location(80902, 116155, -3533);
		points11[4] = new Location(79832, 115784, -3733);
		points11[5] = new Location(78442, 116510, -3823);
		points11[6] = new Location(76299, 117355, -3786);
		points11[7] = new Location(74244, 117674, -3785);

		//Рукав 1, линия 2
		points12[0] = new Location(84231, 117597, -3020);
		points12[1] = new Location(82536, 116986, -3093);
		points12[2] = new Location(79428, 116341, -3749);
		points12[3] = new Location(76970, 117362, -3771);
		points12[4] = new Location(74322, 117845, -3767);

		//Рукав 1, линия 3
		points13[0] = new Location(83962, 118387, -3022);
		points13[1] = new Location(81960, 116925, -3216);
		points13[2] = new Location(80223, 116059, -3665);
		points13[3] = new Location(78214, 116783, -3854);
		points13[4] = new Location(76208, 117462, -3791);
		points13[5] = new Location(74278, 117454, -3804);

		//Рукав 2, линия 1
		points21[0] = new Location(79192, 111481, -3011);
		points21[1] = new Location(79014, 112396, -3090);
		points21[2] = new Location(79309, 113692, -3437);
		points21[3] = new Location(79350, 115337, -3758);
		points21[4] = new Location(78390, 116309, -3772);
		points21[5] = new Location(76794, 117092, -3821);
		points21[6] = new Location(74451, 117623, -3797);

		//Рукав 2, линия 2
		points22[0] = new Location(79297, 111456, -3017);
		points22[1] = new Location(79020, 112217, -3087);
		points22[2] = new Location(79167, 113236, -3289);
		points22[3] = new Location(79513, 115408, -3752);
		points22[4] = new Location(78555, 116816, -3812);
		points22[5] = new Location(76932, 117277, -3781);
		points22[6] = new Location(75422, 117788, -3755);
		points22[7] = new Location(74223, 117898, -3753);

		//Рукав 2, линия 3
		points23[0] = new Location(79635, 110741, -3003);
		points23[1] = new Location(78994, 111858, -3061);
		points23[2] = new Location(79088, 112949, -3226);
		points23[3] = new Location(79424, 114499, -3674);
		points23[4] = new Location(78913, 116266, -3779);
		points23[5] = new Location(76930, 117137, -3819);
		points23[6] = new Location(75533, 117569, -3781);
		points23[7] = new Location(74255, 117398, -3804);

		//Рукав 3, линия 1
		points31[0] = new Location(83128, 111358, -3663);
		points31[1] = new Location(81538, 111896, -3631);
		points31[2] = new Location(80312, 113837, -3752);
		points31[3] = new Location(79012, 115998, -3772);
		points31[4] = new Location(77377, 117052, -3812);
		points31[5] = new Location(75394, 117608, -3772);
		points31[6] = new Location(73998, 117647, -3784);

		//Рукав 3, линия 2
		points32[0] = new Location(83245, 110790, -3772);
		points32[1] = new Location(81832, 111379, -3641);
		points32[2] = new Location(81405, 112403, -3648);
		points32[3] = new Location(79827, 114496, -3752);
		points32[4] = new Location(78174, 116968, -3821);
		points32[5] = new Location(75944, 117653, -3777);
		points32[6] = new Location(74379, 117939, -3755);

		//Рукав 3, линия 3
		points33[0] = new Location(82584, 111930, -3568);
		points33[1] = new Location(81389, 111989, -3647);
		points33[2] = new Location(80129, 114044, -3748);
		points33[3] = new Location(79190, 115579, -3743);
		points33[4] = new Location(77989, 116811, -3849);
		points33[5] = new Location(76009, 117405, -3800);
		points33[6] = new Location(74113, 117441, -3797);

		if(isActive())
		{
			activateAI();
			_log.info("Loaded Event: The Flow Of The Horror [state: activated]");
		}
		else
			_log.info("Loaded Event: The Flow Of The Horror [state: deactivated]");
	}

	public static void spawnNewWave()
	{
		spawn(Shackle, points11);
		spawn(Shackle, points12);
		spawn(Shackle, points13);
		spawn(Shackle, points21);
		spawn(Shackle, points22);
		spawn(Shackle, points23);
		spawn(Shackle, points31);
		spawn(Shackle, points32);
		spawn(Shackle, points33);

		_stage = 2;
	}

	private static void spawn(int id, Location[] points)
	{
		L2NpcTemplate template = NpcTable.getTemplate(id);
		if(template == null)
		{
			_log.warn("Incorrect monster template. need DP check");
			return;
		}

		try
		{
			L2Spawn spawn = new L2Spawn(template);
			spawn.setLoc(points[0]);
			spawn.setAmount(1);
			spawn.setRespawnDelay(30);
			spawn.doSpawn(false);
			L2MonsterInstance monster = (L2MonsterInstance) spawn.getLastSpawn();

			MonstersAI ai = new MonstersAI(monster);
			monster.setAI(ai);
			ai.setPoints(points);
			ai.startAITask();
			monster.getSpawn().setRespawnDelay(0);
			_spawns.add(monster);
		}
		catch(Exception e)
		{
			_log.warn("Cannot spawn mob or Set AI for him");
		}
	}

	private void activateAI()
	{
		L2NpcInstance target = L2ObjectsStorage.getByNpcId(Gilmore);
		if(target != null)
		{
			oldGilmore = target;
			target.decayMe();

			L2NpcTemplate template = NpcTable.getTemplate(Gilmore);
			L2MonsterInstance monster = new L2MonsterInstance(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
			monster.setCurrentHpMp(monster.getMaxHp(), monster.getMaxMp());
			monster.setXYZ(73329, 117705, -3741, false);
			GilmoreAI ai = new GilmoreAI(monster);
			monster.setAI(ai);
			monster.spawnMe();
			ai.startAITask();
			_spawns.add(monster);
		}
	}

	private void deactivateAI()
	{
		for(L2MonsterInstance monster : _spawns)
			if(monster != null)
			{
				monster.getAI().stopAITask();
				monster.deleteMe();
			}

		if(oldGilmore != null)
			oldGilmore.spawnMe();
	}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("TheFlowOfTheHorror", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("TheFlowOfTheHorror", "on");
			activateAI();
			_log.info("Event 'The Flow Of The Horror' started.");
			//Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'The Flow Of The Horror' already started.");

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
			ServerVariables.unset("TheFlowOfTheHorror");
			deactivateAI();
			_log.info("Event 'The Flow Of The Horror' stopped.");
			//Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'The Flow Of The Horror' not started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public void onReload()
	{
		deactivateAI();
	}

	public void onShutdown()
	{
		deactivateAI();
	}

	public static int getStage()
	{
		return _stage;
	}

	public static void setStage(int stage)
	{
		_stage = stage;
	}
}