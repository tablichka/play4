package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.clientpackets.AbstractEnchantPacket;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.Warehouse.WarehouseType;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.templates.L2Item;

import java.util.ArrayList;

public class WareHouseDepositList extends AbstractItemPacket
{
	private int _whtype;
	private long char_adena;
	private ArrayList<L2ItemInstance> _itemslist = new ArrayList<L2ItemInstance>();

	public WareHouseDepositList(L2Player player, WarehouseType whtype)
	{
		_whtype = whtype.getPacketValue();
		char_adena = player.getAdena();
		AbstractEnchantPacket.checkAndCancelEnchant(player);
		for(L2ItemInstance item : player.getInventory().getItems())
			if(!item.isEquipped() && item.getItem().getType2() != L2Item.TYPE2_QUEST && !item.isActivePetControlItem(player) && item.canBeStored(player, _whtype == 1))
				_itemslist.add(item);
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x41);
		writeH(_whtype);
		writeQ(char_adena);
		writeH(_itemslist.size());
		for(L2ItemInstance temp : _itemslist)
		{
			writeItemInfo(temp);
			writeD(temp.getObjectId()); // return value for define item (object_id)
		}
	}
}