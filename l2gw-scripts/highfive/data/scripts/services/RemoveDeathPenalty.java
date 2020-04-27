package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Files;

/**
 * Используется NPC Black Judge (id: 30981) для сниятия с игрока Death Penalty
 *
 * @Author: SYS
 * @Date: 13/9/2007
 */
public class RemoveDeathPenalty extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public static void showdialog()
	{
		L2Player player = (L2Player) self;
		String htmltext;
		if(player.getDeathPenalty().getLevel() > 0)
		{
			htmltext = Files.read("data/scripts/services/RemoveDeathPenalty-1.htm", player);
			htmltext += "<a action=\"bypass -h scripts_services.RemoveDeathPenalty:remove\">Remove 1 level of Death Penalty (" + getPrice() + " adena).</a>";
		}
		else
			htmltext = Files.read("data/scripts/services/RemoveDeathPenalty-0.htm", player);

		show(htmltext, (L2Player) self);
	}

	public static void remove()
	{
		if(npc == null)
			return;
		L2Player player = (L2Player) self;
		if(player.getDeathPenalty().getLevel() > 0 && player.reduceAdena("RemoveDP", getPrice(), null, true))
			((L2NpcInstance) npc).doCast(SkillTable.getInstance().getInfo(5077, 1), player, false);
		else
			show(Files.read("data/scripts/services/RemoveDeathPenalty-0.htm", player), player);
	}

	public static int getPrice()
	{
		byte playerLvl = ((L2Player) self).getLevel();
		if(playerLvl <= 19)
			return 3600; // Non-grade (confirmed)
		else if(playerLvl >= 20 && playerLvl <= 39)
			return 16400; // D-grade
		else if(playerLvl >= 40 && playerLvl <= 51)
			return 36200; // C-grade
		else if(playerLvl >= 52 && playerLvl <= 60)
			return 50400; // B-grade (confirmed)
		else if(playerLvl >= 61 && playerLvl <= 75)
			return 78200; // A-grade
		else
			return 102800; // S-grade
	}

	public void onLoad()
	{
		_log.info("Loaded Service: NPC RemoveDeathPenalty");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}