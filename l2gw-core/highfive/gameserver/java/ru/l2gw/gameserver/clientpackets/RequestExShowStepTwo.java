package ru.l2gw.gameserver.clientpackets;

public class RequestExShowStepTwo extends L2GameClientPacket
{
	private int unk;

	@Override
	public void runImpl()
	{
		System.out.println(getType() + " :: " + unk);
	}

	/**
	 * format: c
	 */
	@Override
	public void readImpl()
	{
		unk = readC();
	}
}