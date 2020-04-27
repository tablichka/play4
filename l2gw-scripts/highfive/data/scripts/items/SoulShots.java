package items;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.gameserver.templates.L2Weapon;
import ru.l2gw.gameserver.templates.L2Weapon.WeaponType;

import java.util.HashMap;
import java.util.Map;

public class SoulShots implements IItemHandler, ScriptFile
{
	private static final int[] _itemIds;
	private static final short[] _skillIds = { 2039, 2150, 2151, 2152, 2153, 2154, 2154, 2154 };

	private static final Map<Integer, L2Item.Grade> soulShotGrade = new HashMap<>();

	static
	{
		soulShotGrade.put(5789, L2Item.Grade.NONE);
		soulShotGrade.put(1835, L2Item.Grade.NONE);
		soulShotGrade.put(21845, L2Item.Grade.NONE);
		soulShotGrade.put(1463, L2Item.Grade.D);
		soulShotGrade.put(22082, L2Item.Grade.D);
		soulShotGrade.put(13037, L2Item.Grade.D);
		soulShotGrade.put(21846, L2Item.Grade.D);
		soulShotGrade.put(1464, L2Item.Grade.C);
		soulShotGrade.put(22083, L2Item.Grade.C);
		soulShotGrade.put(13045, L2Item.Grade.C);
		soulShotGrade.put(21847, L2Item.Grade.C);
		soulShotGrade.put(22244, L2Item.Grade.C);
		soulShotGrade.put(1465, L2Item.Grade.B);
		soulShotGrade.put(22084, L2Item.Grade.B);
		soulShotGrade.put(21848, L2Item.Grade.B);
		soulShotGrade.put(22245, L2Item.Grade.B);
		soulShotGrade.put(1466, L2Item.Grade.A);
		soulShotGrade.put(22085, L2Item.Grade.A);
		soulShotGrade.put(13055, L2Item.Grade.A);
		soulShotGrade.put(21849, L2Item.Grade.A);
		soulShotGrade.put(22246, L2Item.Grade.A);
		soulShotGrade.put(1467, L2Item.Grade.S);
		soulShotGrade.put(22086, L2Item.Grade.S);
		soulShotGrade.put(21850, L2Item.Grade.S);
		soulShotGrade.put(22247, L2Item.Grade.S);

		_itemIds = new int[soulShotGrade.size()];
		int i = 0;
		for(Integer itemId : soulShotGrade.keySet())
			_itemIds[i++] = itemId;
	}

	static final SystemMessage POWER_OF_THE_SPIRITS_ENABLED = new SystemMessage(SystemMessage.POWER_OF_THE_SPIRITS_ENABLED);
	static final SystemMessage NOT_ENOUGH_SOULSHOTS = new SystemMessage(SystemMessage.NOT_ENOUGH_SOULSHOTS);
	static final SystemMessage SOULSHOT_DOES_NOT_MATCH_WEAPON_GRADE = new SystemMessage(SystemMessage.SOULSHOT_DOES_NOT_MATCH_WEAPON_GRADE);
	static final SystemMessage CANNOT_USE_SOULSHOTS = new SystemMessage(SystemMessage.CANNOT_USE_SOULSHOTS);

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return true;
		L2Player player = (L2Player) playable;

		L2Weapon weaponItem = player.getActiveWeaponItem();

		L2ItemInstance weaponInst = player.getActiveWeaponInstance();
		int SoulshotId = item.getItemId();
		boolean isAutoSoulShot = false;

		if(player.getAutoSoulShot().contains(SoulshotId))
			isAutoSoulShot = true;

		if(weaponInst == null)
		{
			player.sendPacket(CANNOT_USE_SOULSHOTS);
			return true;
		}

		// soulshot is already active
		if(weaponInst.getChargedSoulshot() != L2ItemInstance.CHARGED_NONE)
			return true;

		L2Item.Grade grade = weaponItem.getCrystalType();
		int soulShotConsumption = weaponItem.getSoulShotCount();
		long count = item.getCount();

		if(soulShotConsumption == 0)
		{
			player.sendPacket(CANNOT_USE_SOULSHOTS);
			return true;
		}

		if(grade.externalOrdinal != soulShotGrade.get(item.getItemId()).externalOrdinal)
		{
			// wrong cry for weapon
			if(isAutoSoulShot)
				return true;
			player.sendPacket(SOULSHOT_DOES_NOT_MATCH_WEAPON_GRADE);
			return true;
		}

		if(weaponItem.getItemType() == WeaponType.BOW || weaponItem.getItemType() == WeaponType.CROSSBOW)
		{
			int newSS = (int) player.calcStat(Stats.SS_USE_BOW, soulShotConsumption, null, null);
			if(newSS < soulShotConsumption && .30 > Rnd.get())
				soulShotConsumption = newSS;
		}

		if(count < soulShotConsumption)
		{
			player.sendPacket(NOT_ENOUGH_SOULSHOTS);
			return false;
		}

		weaponInst.setChargedSoulshot(L2ItemInstance.CHARGED_SOULSHOT);
		player.destroyItem("Consume", item.getObjectId(), soulShotConsumption, null, false);
		player.sendPacket(new SystemMessage(SystemMessage.USE_S1).addItemName(SoulshotId));
		player.sendPacket(POWER_OF_THE_SPIRITS_ENABLED);
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
