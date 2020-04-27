package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2HennaInstance;

/**
 * @author rage
 * @date 16.12.10 18:18
 */
public class HennaItemRemoveInfo extends L2GameServerPacket
{
	private L2Player _activeChar;
	private L2HennaInstance _henna;
	
	public HennaItemRemoveInfo(L2HennaInstance henna, L2Player player)
	{
		_henna = henna;
		_activeChar = player;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xe7);
		writeD(_henna.getSymbolId()); //symbol Id
		writeD(_henna.getItemIdDye()); //item id of dye
		writeQ(0x00); // total amount of dye require
		writeQ(_henna.getPrice() / 5); //total amount of aden require to remove symbol
		writeD(1); //able to remove or not 0 is false and 1 is true
		writeQ(_activeChar.getAdena());
		writeD(_activeChar.getINT()); //current INT
		writeC(_activeChar.getINT() - _henna.getStatINT()); //equip INT
		writeD(_activeChar.getSTR()); //current STR
		writeC(_activeChar.getSTR() - _henna.getStatSTR()); //equip STR
		writeD(_activeChar.getCON()); //current CON
		writeC(_activeChar.getCON() - _henna.getStatCON()); //equip CON
		writeD(_activeChar.getMEN()); //current MEM
		writeC(_activeChar.getMEN() - _henna.getStatMEM());	//equip MEM
		writeD(_activeChar.getDEX()); //current DEX
		writeC(_activeChar.getDEX() - _henna.getStatDEX());	//equip DEX
		writeD(_activeChar.getWIT()); //current WIT
		writeC(_activeChar.getWIT() - _henna.getStatWIT());	//equip WIT
	}
}
