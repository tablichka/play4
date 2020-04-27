package services;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;

public class TeleToCatacomb extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public static String DialogAppend_31212(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31213(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31214(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31215(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31216(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31217(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31218(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31219(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31220(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31221(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31222(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31223(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31224(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31767(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31768(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_32048(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String getHtmlAppends(Integer val)
	{
		if(val != 0 || !Config.ALT_SIMPLE_SIGNS)
			return "";

		L2Player player = (L2Player) self;

		String lang = player.getVar("lang@");
		String append = "";

		append += "<br>";

		if(lang.equalsIgnoreCase("en"))
		{
			append += "Teleport to catacomb or necropolis.<br1> ";
			append += "You may teleport to any of the following hunting locations. Each teleport requires 100000 adena.<br>";
		}
		else
		{
			append += "За 100000 аден вы можете переместиться в катакомбы или некрополисы.<br1> ";
			append += "Список доступных локаций:<br>";
		}

		append += "[scripts_Util:Gatekeeper -41567 209463 -5080 100000|Necropolis of Sacrifice (20-30)]<br1>";
		append += "[scripts_Util:Gatekeeper 45248 124223 -5408 100000|The Pilgrim's Necropolis (30-40)]<br1>";
		append += "[scripts_Util:Gatekeeper 110911 174013 -5439 100000|Necropolis of Worship (40-50)]<br1>";
		append += "[scripts_Util:Gatekeeper -22101 77383 -5173 100000|The Patriot's Necropolis (50-60)]<br1>";
		append += "[scripts_Util:Gatekeeper -52654 79149 -4741 100000|Necropolis of Devotion (60-70)]<br1>";
		append += "[scripts_Util:Gatekeeper 117884 132796 -4831 100000|Necropolis of Martyrdom (60-70)]<br1>";
		append += "[scripts_Util:Gatekeeper 82750 209250 -5401 100000|The Saint's Necropolis (70-80)]<br1>";
		append += "[scripts_Util:Gatekeeper 171897 -17606 -4901 100000|Disciples Necropolis(70-80)]<br>";

		append += "[scripts_Util:Gatekeeper 42322 143927 -5381 100000|Catacomb of the Heretic (30-40)]<br1>";
		append += "[scripts_Util:Gatekeeper 45841 170307 -4981 100000|Catacomb of the Branded (40-50)]<br1>";
		append += "[scripts_Util:Gatekeeper 77348 78445 -5125 100000|Catacomb of the Apostate (50-60)]<br1>";
		append += "[scripts_Util:Gatekeeper 139955 79693 -5429 100000|Catacomb of the Witch (60-70)]<br1>";
		append += "[scripts_Util:Gatekeeper -19827 13509 -4901 100000|Catacomb of Dark Omens (70-80)]<br1>";
		append += "[scripts_Util:Gatekeeper 113573 84513 -6541 100000|Catacomb of the Forbidden Path (70-80)]";

		return append;
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Teleport to catacombs");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}