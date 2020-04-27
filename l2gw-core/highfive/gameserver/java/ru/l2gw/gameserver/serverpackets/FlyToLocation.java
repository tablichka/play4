package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.util.Location;

public class FlyToLocation extends L2GameServerPacket
{
	private int _chaObjId;
	private final FlyType _type;
	private Location _loc;
	private Location _destLoc;

	public enum FlyType
	{
		THROW_UP,
		THROW_HORIZONTAL,
		CHARGE,
		DUMMY,
		NONE
	}

	public FlyToLocation(L2Character cha, Location destLoc, FlyType type)
	{
		_destLoc = destLoc;
		_type = type;
		_chaObjId = cha.getObjectId();
		_loc = cha.getLoc();
	}

	@Override
	protected void writeImpl()
	{
		writeC(0xd4);
		writeD(_chaObjId);
		writeD(_destLoc.getX());
		writeD(_destLoc.getY());
		writeD(_destLoc.getZ());
		writeD(_loc.getX());
		writeD(_loc.getY());
		writeD(_loc.getZ());
		writeD(_type.ordinal());
	}
}