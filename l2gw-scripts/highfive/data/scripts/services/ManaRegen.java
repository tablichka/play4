package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

public class ManaRegen extends Functions implements ScriptFile
{
	public static L2Object self;

	public void DoManaRegen()
	{
		L2Player player = (L2Player) self;
		//5 аден за 1 МП
		int cost = 5;
		int tax = (player.getMaxMp() - (int) player.getCurrentMp()) * cost;
		L2ItemInstance pay = player.getInventory().getItemByItemId(57);
		if(pay != null && pay.getCount() >= tax)
		{
			player.destroyItem("ManaRegen", pay.getObjectId(), tax, npc, true);
			player.setCurrentMp(player.getMaxMp());
		}
		else
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Mana Regen");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}