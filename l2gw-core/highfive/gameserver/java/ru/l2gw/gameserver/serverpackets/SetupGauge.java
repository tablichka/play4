package ru.l2gw.gameserver.serverpackets;

/**
 *	sample
 *	0000: 85 00 00 00 00 f0 1a 00 00
 */
public class SetupGauge extends L2GameServerPacket
{
	public static final int BLUE = 0;
	public static final int RED = 1;
	public static final int CYAN = 2;
	public static final int GREEN = 3;

	private int _color;
	private int _timeTotal;
	private int _timeLeft;
	private int _objectId;

	public SetupGauge(int color, int time)
	{
		_color = color;// color  0-blue   1-red  2-cyan  3-green
		_timeTotal = time;
		_timeLeft = time;
	}

	public SetupGauge(int color, int time, int timeLeft)
	{
		_color = color;// color  0-blue   1-red  2-cyan  3-green
		_timeTotal = time;
		_timeLeft = timeLeft;
	}

	@Override
	public void runImpl()
	{
		_objectId = getClient().getPlayer().getObjectId();
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x6b);
		writeD(_objectId);
		writeD(_color);
		writeD(_timeLeft);
		writeD(_timeTotal); //c2
	}
}