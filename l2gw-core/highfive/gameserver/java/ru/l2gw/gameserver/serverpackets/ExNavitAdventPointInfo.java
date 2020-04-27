package ru.l2gw.gameserver.serverpackets;

public class ExNavitAdventPointInfo extends L2GameServerPacket
{
	private final int _points;

	public ExNavitAdventPointInfo(int points)
	{
		_points = points;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(EXTENDED_PACKET);
		writeH(0xDF);
		writeD(_points); // 1% = 72 Max 100% (7200)
	}
}