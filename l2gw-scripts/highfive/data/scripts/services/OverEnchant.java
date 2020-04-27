package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.clientpackets.AbstractEnchantPacket;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.PcInventory;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.ExPutEnchantTargetItemResult;

/**
 * @author: rage
 * @date: 08.10.12 20:04
 */
public class OverEnchant extends Functions implements ScriptFile
{
	public L2Object self;
	public L2NpcInstance npc;

	@Override
	public void onLoad()
	{
		if(Config.OVER_ENCHANT_ENABLED)
			_log.info("Loaded Service: Over Enchant enabled.");
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}

	public void onAnswer(Integer objectId)
	{
		if(Config.OVER_ENCHANT_ENABLED && self instanceof L2Player && objectId != null)
		{
			L2Player player = (L2Player) self;

			if(player.isOutOfControl() || player.isActionsDisabled())
			{
				player.sendActionFailed();
				return;
			}

			PcInventory inventory = player.getInventory();
			L2ItemInstance itemToEnchant = inventory.getItemByObjectId(objectId);
			L2ItemInstance scroll = player.getEnchantScroll();

			if(itemToEnchant == null || scroll == null || player.getEnchantStartTime() > 0)
			{
				player.cancelActiveEnchant();
				return;
			}

			if(player.isInStoreMode())
			{
				player.cancelActiveEnchant();
				player.sendPacket(new ExPutEnchantTargetItemResult(0));
				player.sendPacket(Msg.YOU_CANNOT_PRACTICE_ENCHANTING_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_MANUFACTURING_WORKSHOP);
				return;
			}

			// template for scroll
			AbstractEnchantPacket.EnchantScroll scrollTemplate = AbstractEnchantPacket.getEnchantScroll(scroll);

			if(!scrollTemplate.isValid(itemToEnchant) || !AbstractEnchantPacket.isEnchantable(itemToEnchant))
			{
				player.sendPacket(Msg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
				player.cancelActiveEnchant();
				player.sendPacket(new ExPutEnchantTargetItemResult(0));
				return;
			}

			player.setEnchantStartTime(System.currentTimeMillis());
			player.sendPacket(new ExPutEnchantTargetItemResult(objectId));
		}
	}

	public void onAnswerFail(Integer objectId)
	{
		if(Config.OVER_ENCHANT_ENABLED && self instanceof L2Player)
		{
			L2Player player = (L2Player) self;
			player.sendPacket(Msg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
			player.cancelActiveEnchant();
			player.sendPacket(new ExPutEnchantTargetItemResult(0));
		}
	}
}
