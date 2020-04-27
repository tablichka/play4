package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Files;
import ru.l2gw.commons.math.Rnd;

/**
 * @author Bonux
 */

public class BuyBracelets extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private static int Angel = 10320;
	private static int AFirecracker = 10316;
	private static int ABigHead = 10317;
	private static int AEscape = 10318;
	private static int AResurrection = 10319;
	private static int Devil = 10326;
	private static int DFirecracker = 10322;
	private static int DBigHead = 10323;
	private static int DEscape = 10324;
	private static int DResurrection = 10325;
	private static int Item1 = 6471;
	private static int Item2 = 5094;
	private static int Item3 = 9814;
	private static int Item4 = 9816;
	private static int Item5 = 9817;
	private static int Item6 = 9815;
	private static int Adena = 57;
	private static int PVcirclet = 10315;
	private static int GVCirclet = 10321;
	private static int OldAgat = 10408;

	public void onLoad()
	{
		_log.info("Loaded Service: Buy Bracelets");
	}

	public void buyAngel()
	{
		L2Player player = (L2Player) self;
		int Random = Rnd.get(14);

		if(getItemCount(player, Item1) >= 25 && getItemCount(player, Item2) >= 50 && getItemCount(player, Item3) >= 4 && getItemCount(player, Item4) >= 5 && getItemCount(player, Item5) >= 5 && getItemCount(player, Item6) >= 3 && getItemCount(player, Adena) >= 7500000)
		{
			if(Random == 13)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, AResurrection, 1);
			}
			else if(Random == 12)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, AEscape, 1);
			}
			if(Random == 11)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, ABigHead, 1);
			}
			if(Random == 10)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, AFirecracker, 1);
			}
			if(Random == 9)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, Angel, 1);
			}
			if(Random == 8)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, Angel, 1);
			}
			if(Random == 7)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 6)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 5)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 4)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 3)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 2)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 1)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 0)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
		}

		else
			show(Files.read("data/html/merchant/30098-2.htm", player), player);
		return;
	}

	public void buyDevil()
	{
		L2Player player = (L2Player) self;
		int Random = Rnd.get(14);

		if(getItemCount(player, Item1) >= 25 && getItemCount(player, Item2) >= 50 && getItemCount(player, Item3) >= 4 && getItemCount(player, Item4) >= 5 && getItemCount(player, Item5) >= 5 && getItemCount(player, Item6) >= 3 && getItemCount(player, Adena) >= 7500000)
		{
			if(Random == 13)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, DResurrection, 1);
			}
			else if(Random == 12)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, DEscape, 1);
			}
			if(Random == 11)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, DBigHead, 1);
			}
			if(Random == 10)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, DFirecracker, 1);
			}
			if(Random == 9)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, Devil, 1);
			}
			if(Random == 8)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, Devil, 1);
			}
			if(Random == 7)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 6)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 5)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, GVCirclet, 4);
			}
			if(Random == 4)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 3)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 2)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 1)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
			if(Random == 0)
			{
				removeItem(player, Item1, 25);
				removeItem(player, Item2, 50);
				removeItem(player, Item3, 4);
				removeItem(player, Item4, 5);
				removeItem(player, Item5, 5);
				removeItem(player, Item6, 3);
				removeItem(player, Adena, 7500000);
				addItem(player, OldAgat, 1);
				addItem(player, PVcirclet, 4);
			}
		}

		else
			show(Files.read("data/html/merchant/30098-2.htm", player), player);
		return;
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
