package services;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.SkillList;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2Item;

public class NoblessSell extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public void get()
	{
		L2Player player = (L2Player) self;

		if(player.isNoble())
			return;

		if(player.getSubLevel() < 75)
		{
			player.sendMessage("You must make sub class level 75 first.");
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_NOBLESS_SELL_ITEM);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= Config.SERVICES_NOBLESS_SELL_PRICE)
		{
			player.destroyItem("NoblessPay", pay.getObjectId(), Config.SERVICES_NOBLESS_SELL_PRICE, npc, true);
			player.setNoble(true);
			player.addSkill(SkillTable.getInstance().getInfo(1323, 1));
			player.addSkill(SkillTable.getInstance().getInfo(325, 1));
			player.addSkill(SkillTable.getInstance().getInfo(326, 1));
			player.addSkill(SkillTable.getInstance().getInfo(327, 1));
			player.addSkill(SkillTable.getInstance().getInfo(1324, 1));
			player.addSkill(SkillTable.getInstance().getInfo(1325, 1));
			player.addSkill(SkillTable.getInstance().getInfo(1326, 1));
			player.addSkill(SkillTable.getInstance().getInfo(1327, 1));
			player.sendPacket(new SkillList(player));
			player.broadcastPacket(new SocialAction(player.getObjectId(), 16));
			player.broadcastUserInfo(true);
		}
		else if(Config.SERVICES_NOBLESS_SELL_ITEM == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Nobless sell");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}