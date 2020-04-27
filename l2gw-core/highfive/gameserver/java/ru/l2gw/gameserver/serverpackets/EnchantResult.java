package ru.l2gw.gameserver.serverpackets;

public class EnchantResult extends L2GameServerPacket
{
	private int _result;
	private int _crystal;
	private int _count;

	public EnchantResult(int result)
	{
		this(result, 0, 0);
	}

	public EnchantResult(int result, int crystal, int count)
	{
		_result = result;
		_crystal = crystal;
		_count = count;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x87);
		writeD(_result);
		writeD(_crystal);
		writeQ(_count);
	}
}