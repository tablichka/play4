package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.model.TradeItem;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.ItemTable;

/**
 * @author: rage
 * @date: 28.06.2010 19:06:46
 */
public class AbstractItemPacket extends L2GameServerPacket
{

	protected void writeItemInfo(L2ItemInstance item)
	{
		writeItemInfo(item, item.getCount());
	}

	protected void writeItemInfo(L2ItemInstance item, long count)
	{
		writeD(item.getObjectId());
		writeD(item.getItemId());
		writeD(item.getEquipSlot());
		writeQ(count);
		writeH(item.getItem().getType2());
		writeH(item.getCustomType1());
		writeH(item.isEquipped() ? 1 : 0);
		writeD(item.getBodyPart());
		writeH(item.getEnchantLevel());
		writeH(item.getCustomType2());
		writeD(item.getAugmentationId());
		writeD(item.getMana());
		writeD(item.getExpireTime());
		writeH(item.getAttackElement()[0]);
		writeH(item.getAttackElement()[1]);
		writeH(item.getDefenceFire());
		writeH(item.getDefenceWater());
		writeH(item.getDefenceWind());
		writeH(item.getDefenceEarth());
		writeH(item.getDefenceHoly());
		writeH(item.getDefenceDark());
		writeH(item.getEnchantOptionId(0));
		writeH(item.getEnchantOptionId(1));
		writeH(item.getEnchantOptionId(2));
	}
	
	protected void writeItemInfo(TradeItem item)
	{
		writeD(item.getObjectId());
		writeD(item.getItemId());
		writeD(0);
		writeQ(item.getCount());
		writeH(ItemTable.getInstance().getTemplate(item.getItemId()).getType2());
		writeH(item.getCustomType1());
		writeH(0);
		writeD(item.getBodyPart());
		writeH(item.getEnchantLevel());
		writeH(item.getCustomType2());
		writeD(0);
		writeD(item.getMana());
		writeD(item.getExpireTime());
		writeH(item.getAttackElement());
		writeH(item.getAttackValue());
		writeH(item.getDefenceFire());
		writeH(item.getDefenceWater());
		writeH(item.getDefenceWind());
		writeH(item.getDefenceEarth());
		writeH(item.getDefenceHoly());
		writeH(item.getDefenceDark());
		writeH(item.getEnchantOptionId(0));
		writeH(item.getEnchantOptionId(1));
		writeH(item.getEnchantOptionId(2));
	}

	@Override
	protected void writeImpl()
	{
	}
}
