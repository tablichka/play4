package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.gameserver.model.L2TradeList;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

/**
 * Format: c ddh[hdddhhd]
 * c - id (0xE8)
 *
 * d - money
 * d - manor id
 * h - size
 * [
 * h - item type 1
 * d - object id
 * d - item id
 * d - count
 * h - item type 2
 * h
 * d - price
 * ]
 *
 */
public final class BuyListSeed extends L2GameServerPacket
{
	private int _manorId;
	private GArray<L2ItemInstance> _list = new GArray<L2ItemInstance>();
	private long _money;

	public BuyListSeed(L2TradeList list, int manorId, long currentMoney)
	{
		_money = currentMoney;
		_manorId = manorId;
		_list = list.getItems();
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0xe9);

		writeQ(_money); // current money
		writeD(_manorId); // manor id

		writeH(_list.size()); // list length

		for(L2ItemInstance item : _list)
		{
			writeD(item.getItemId()); // item id
			writeD(item.getItemId()); // item id
			writeD(0x00); // objectId
			writeQ(item.getCount()); // item count
			writeH(0x05); // item->type2
			writeH(0x00); // item->type1
			writeH(0x00); // Equipped
			writeD(0x00); // Body Part
			writeH(0x00); // Enchant
			writeH(0x00); // Custom Type
			writeD(0x00); // Augment
			writeD(-1); // Mana
			writeD(-9999); // Time
			writeH(0x00); // Element Type
			writeH(0x00); // Element Power
			for (byte i = 0; i < 6; i++)
				writeH(0x00);
			// Enchant Effects
			writeH(0x00);
			writeH(0x00);
			writeH(0x00);
			writeQ(item.getPriceToSell()); // price
		}
	}
}