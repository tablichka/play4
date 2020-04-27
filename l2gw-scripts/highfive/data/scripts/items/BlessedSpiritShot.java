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

import java.util.HashMap;
import java.util.Map;

public class BlessedSpiritShot implements IItemHandler, ScriptFile
{
	// all the items ids that this handler knowns
	private static final int[] _itemIds;
	private static final short[] _skillIds = { 2061, 2160, 2161, 2162, 2163, 2164, 2164, 2164 };
	private static final Map<Integer, L2Item.Grade> blessedSpiritShotGrade = new HashMap<>();

	static
	{
		blessedSpiritShotGrade.put(3947, L2Item.Grade.NONE);
		blessedSpiritShotGrade.put(3948, L2Item.Grade.D);
		blessedSpiritShotGrade.put(22072, L2Item.Grade.D);
		blessedSpiritShotGrade.put(3949, L2Item.Grade.C);
		blessedSpiritShotGrade.put(22073, L2Item.Grade.C);
		blessedSpiritShotGrade.put(22249, L2Item.Grade.C);
		blessedSpiritShotGrade.put(3950, L2Item.Grade.B);
		blessedSpiritShotGrade.put(22074, L2Item.Grade.B);
		blessedSpiritShotGrade.put(22250, L2Item.Grade.B);
		blessedSpiritShotGrade.put(3951, L2Item.Grade.A);
		blessedSpiritShotGrade.put(22075, L2Item.Grade.A);
		blessedSpiritShotGrade.put(22251, L2Item.Grade.A);
		blessedSpiritShotGrade.put(3952, L2Item.Grade.S);
		blessedSpiritShotGrade.put(22076, L2Item.Grade.S);
		blessedSpiritShotGrade.put(22231, L2Item.Grade.S);
		blessedSpiritShotGrade.put(22252, L2Item.Grade.S);
		_itemIds = new int[blessedSpiritShotGrade.size()];
		int i = 0;
		for(Integer itemId : blessedSpiritShotGrade.keySet())
			_itemIds[i++] = itemId;
	}

	static final SystemMessage POWER_OF_MANA_ENABLED = new SystemMessage(SystemMessage.POWER_OF_MANA_ENABLED);
	static final SystemMessage NOT_ENOUGH_SPIRITSHOTS = new SystemMessage(SystemMessage.NOT_ENOUGH_SPIRITSHOTS);
	static final SystemMessage SPIRITSHOT_DOES_NOT_MATCH_WEAPON_GRADE = new SystemMessage(SystemMessage.SPIRITSHOT_DOES_NOT_MATCH_WEAPON_GRADE);
	static final SystemMessage CANNOT_USE_SPIRITSHOTS = new SystemMessage(SystemMessage.CANNOT_USE_SPIRITSHOTS);

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return true;
		L2Player player = (L2Player) playable;

		L2ItemInstance weaponInst = player.getActiveWeaponInstance();
		L2Weapon weaponItem = player.getActiveWeaponItem();
		int SoulshotId = item.getItemId();
		boolean isAutoSoulShot = false;

		if(player.getAutoSoulShot().contains(SoulshotId))
			isAutoSoulShot = true;

		if(weaponInst == null)
		{
			player.sendPacket(CANNOT_USE_SPIRITSHOTS);
			return true;
		}

		if(weaponInst.getChargedSpiritshot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
			return true;

		L2Item.Grade grade = weaponItem.getCrystalType();
		int blessedsoulSpiritConsumption = weaponItem.getSpiritShotCount();
		long count = item.getCount();

		if(blessedsoulSpiritConsumption == 0)
		{
			player.sendPacket(CANNOT_USE_SPIRITSHOTS);
			return true;
		}

		if(grade.externalOrdinal != blessedSpiritShotGrade.get(item.getItemId()).externalOrdinal)
		{
			if(isAutoSoulShot)
				return true;
			player.sendPacket(SPIRITSHOT_DOES_NOT_MATCH_WEAPON_GRADE);
			return true;
		}

		if(count < blessedsoulSpiritConsumption)
		{
			player.sendPacket(NOT_ENOUGH_SPIRITSHOTS);
			return false;
		}

		weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT);
		player.destroyItem("Consume", item.getObjectId(), blessedsoulSpiritConsumption, null, false);
		player.sendPacket(new SystemMessage(SystemMessage.USE_S1).addItemName(SoulshotId));
		player.sendPacket(POWER_OF_MANA_ENABLED);
		player.broadcastPacket(new MagicSkillUse(player, player, _skillIds[weaponItem.getCrystalType().externalOrdinal], 1, 0, 0));
		return true;
	}

	public final int[] getItemIds()
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