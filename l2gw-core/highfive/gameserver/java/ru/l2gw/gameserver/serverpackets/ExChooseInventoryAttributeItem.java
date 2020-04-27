package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.clientpackets.RequestExEnchantItemAttribute;
import ru.l2gw.gameserver.clientpackets.RequestExEnchantItemAttribute.StoneInfo;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

public class ExChooseInventoryAttributeItem extends L2GameServerPacket
{
	private int _itemId, _attribute, _maxLevel;

	public ExChooseInventoryAttributeItem(L2ItemInstance item)
	{
		_itemId = item.getItemId();
		StoneInfo stoneInfo = RequestExEnchantItemAttribute._stoneLevels.get(item.getItemId());
		if(stoneInfo  != null)
		{
			_maxLevel = stoneInfo.level;
			_attribute = stoneInfo.element;
		}
	}

	@Override
	protected final void writeImpl()
	{
		writeC(EXTENDED_PACKET);
		writeH(0x62);
		writeD(_itemId);
		for(int i = 0; i < 6; i++)
			writeD(i == _attribute ? 0x01 : 0x00);
		writeD(_maxLevel);
	}
}