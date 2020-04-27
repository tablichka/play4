package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.gameserver.templates.L2Weapon;
import ru.l2gw.gameserver.templates.L2Weapon.WeaponType;

public class FishShots implements IItemHandler, ScriptFile
{
	// All the item IDs that this handler knows.
	private static int[] _itemIds = { 6535, 6536, 6537, 6538, 6539, 6540 };
	private static int[] _skillIds = { 2181, 2182, 2183, 2184, 2185, 2186 };

	static final SystemMessage THIS_FISHING_SHOT_IS_NOT_FIT_FOR_THE_FISHING_POLE_CRYSTAL = new SystemMessage(SystemMessage.THIS_FISHING_SHOT_IS_NOT_FIT_FOR_THE_FISHING_POLE_CRYSTAL);
	static final SystemMessage POWER_OF_MANA_ENABLED = new SystemMessage(SystemMessage.POWER_OF_MANA_ENABLED);

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		L2Player player = (L2Player) playable;
		int FishshotId = item.getItemId();

		boolean isAutoSoulShot = false;
		if(player.getAutoSoulShot().contains(FishshotId))
			isAutoSoulShot = true;

		L2ItemInstance weaponInst = player.getActiveWeaponInstance();
		L2Weapon weaponItem = player.getActiveWeaponItem();

		if(weaponInst == null || weaponItem.getItemType() != WeaponType.ROD)
		{
			if(isAutoSoulShot)
				player.removeAutoSoulShot(FishshotId);
			return true;
		}
		if(item.getCount() < 1)
		{
			if(isAutoSoulShot)
				player.removeAutoSoulShot(FishshotId);
			return true;
		}

		// spiritshot is already active
		if(weaponInst.getChargedFishshot())
			return true;

		int cry = weaponItem.getCrystalType().cry;

		if(cry == L2Item.CRYSTAL_NONE && FishshotId != 6535 || cry == L2Item.CRYSTAL_D && FishshotId != 6536 || cry == L2Item.CRYSTAL_C && FishshotId != 6537 || cry == L2Item.CRYSTAL_B && FishshotId != 6538 || cry == L2Item.CRYSTAL_A && FishshotId != 6539 || cry >= L2Item.CRYSTAL_S && FishshotId != 6540)
		{
			if(isAutoSoulShot)
				return true;
			player.sendPacket(THIS_FISHING_SHOT_IS_NOT_FIT_FOR_THE_FISHING_POLE_CRYSTAL);
			return true;
		}

		weaponInst.setChargedFishshot(true);
		player.destroyItem("Consume", item.getObjectId(), 1, null, false);
		player.sendPacket(POWER_OF_MANA_ENABLED);
		player.broadcastPacket(new MagicSkillUse(player, player, _skillIds[weaponItem.getCrystalType().externalOrdinal], 1, 0, 0));
		return true;
	}

	public int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
