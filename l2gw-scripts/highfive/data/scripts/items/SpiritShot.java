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

public class SpiritShot implements IItemHandler, ScriptFile
{
	// all the items ids that this handler knowns
	private static final int[] _itemIds;
	private static final short[] _skillIds = { 2061, 2155, 2156, 2157, 2158, 2159, 2159, 2159 };
	private static final Map<Integer, L2Item.Grade> spiritShotGrade = new HashMap<>();

	static
	{
		spiritShotGrade.put(5790, L2Item.Grade.NONE);
		spiritShotGrade.put(2509, L2Item.Grade.NONE);
		spiritShotGrade.put(21851, L2Item.Grade.NONE);
		spiritShotGrade.put(2510, L2Item.Grade.D);
		spiritShotGrade.put(22077, L2Item.Grade.D);
		spiritShotGrade.put(21852, L2Item.Grade.D);
		spiritShotGrade.put(2511, L2Item.Grade.C);
		spiritShotGrade.put(22078, L2Item.Grade.C);
		spiritShotGrade.put(21853, L2Item.Grade.C);
		spiritShotGrade.put(2512, L2Item.Grade.B);
		spiritShotGrade.put(22079, L2Item.Grade.B);
		spiritShotGrade.put(21854, L2Item.Grade.B);
		spiritShotGrade.put(2513, L2Item.Grade.A);
		spiritShotGrade.put(22080, L2Item.Grade.A);
		spiritShotGrade.put(21855, L2Item.Grade.A);
		spiritShotGrade.put(2514, L2Item.Grade.S);
		spiritShotGrade.put(22081, L2Item.Grade.S);
		spiritShotGrade.put(21856, L2Item.Grade.S);
		_itemIds = new int[spiritShotGrade.size()];
		int i = 0;
		for(Integer itemId : spiritShotGrade.keySet())
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

		// spiritshot is already active
		if(weaponInst.getChargedSpiritshot() != L2ItemInstance.CHARGED_NONE)
			return true;

		L2Item.Grade grade = weaponItem.getCrystalType();
		int soulSpiritConsumption = weaponItem.getSpiritShotCount();
		long count = item.getCount();

		if(soulSpiritConsumption == 0)
		{
			player.sendPacket(CANNOT_USE_SPIRITSHOTS);
			return true;
		}

		if(grade.externalOrdinal != spiritShotGrade.get(item.getItemId()).externalOrdinal)
		{
			// wrong cry for weapon
			if(isAutoSoulShot)
				return true;
			player.sendPacket(SPIRITSHOT_DOES_NOT_MATCH_WEAPON_GRADE);
			return true;
		}

		if(count < soulSpiritConsumption)
		{
			player.sendPacket(NOT_ENOUGH_SPIRITSHOTS);
			return false;
		}

		weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_SPIRITSHOT);
		player.destroyItem("Consume", item.getObjectId(), soulSpiritConsumption, null, false);
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
