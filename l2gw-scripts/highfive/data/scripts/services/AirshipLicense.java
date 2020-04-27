package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author rage
 * @date 08.09.2010 16:08:43
 */
public class AirshipLicense extends Functions implements ScriptFile
{
	private static final int ENERGY_STAR_STONE = 13277;
	private static final int AIRSHIP_SUMMON_LICENSE = 13559;

	public void sell()
	{
		L2Player player = (L2Player) self;

		if(player == null)
			return;

		if(player.getClan() == null || !player.isClanLeader() || player.getClan().getLevel() < 5)
		{
			show("data/html/default/32557-2.htm", player);
			return;
		}

		if(player.getClan().isAirshipEnabled() || Functions.getItemCount(player, AIRSHIP_SUMMON_LICENSE) > 0)
		{
			show("data/html/default/32557-4.htm", player);
			return;
		}

		if(player.getItemCountByItemId(ENERGY_STAR_STONE) < 10)
		{
			show("data/html/default/32557-3.htm", player);
			return;
		}

		if(player.destroyItemByItemId("AirshipLicense", ENERGY_STAR_STONE, 10, npc, true))
			player.addItem("AirshipLicense", AIRSHIP_SUMMON_LICENSE, 1, npc, true);
	}

	public void onLoad()
	{}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
