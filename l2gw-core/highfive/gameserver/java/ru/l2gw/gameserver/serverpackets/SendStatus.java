package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.GameServer;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.tables.FakePlayersTable;

public class SendStatus extends L2GameServerPacket
{
	private int _opcode;

	public SendStatus(int opcode)
	{
		_opcode = opcode;
	}

	@Override
	protected final void writeImpl()
	{
		if(_opcode == -3)
		{
			_log.info("STATUS: Status request recieved from " + getClient().toString());

			writeC(0x2E); // Packet ID
			writeD(GameServer.getServerId()); // World ID
			writeD(Config.MAXIMUM_ONLINE_USERS); // Max Online
			writeD(L2ObjectsStorage.getAllPlayersCount() + FakePlayersTable.getFakePlayersCount() + (Rnd.chance(50) ? 2 : 0)); // Current Online
			writeD(L2ObjectsStorage.getAllPlayersCount() + FakePlayersTable.getFakePlayersCount()); // Current Online
			writeD(Config.SEND_STATUS_REAL_STORE ? L2ObjectsStorage.getAllOfflineCount() : (int) (L2ObjectsStorage.getAllPlayersCount() * 0.12)); // Priv.Sotre Chars

			writeH(0x30);
			writeH(0x2C);

			writeH(0x35);
			writeH(0x31);

			writeH(0x30);
			writeH(0x2c);
			writeH(0x37);
			writeH(0x37);
			writeH(0x37);
			writeH(0x35);
			writeH(0x38);
			writeH(0x2c);

			writeH(0x36);
			writeH(0x35);
			writeH(0x30);
			writeH(0x36); 
			writeH(0x00);
			writeH(0x77);
			writeH(0x00);
			writeH(0xb7);
			writeH(0x00);
			writeH(0x9f);
			
			writeD(0x00);
			writeD(0x00);

			writeH(0x00);
			writeH(0x41);
			writeH(0x75);
			writeH(0x67);
			writeH(0x20);
			writeH(0x32);
			writeH(0x39);
			writeH(0x20);
			writeH(0x32);
			writeH(0x30);
			writeH(0x30);
			writeH(0x39);
			writeH(0x00);
			writeH(0x30);
			writeH(0x32);
			writeH(0x3a);
			writeH(0x34);
			writeH(0x30);
			writeH(0x3a);
			writeH(0x34);
			writeH(0x33);
			writeH(0x00);
			writeH(0x57);
			writeH(0x00);
			writeH(0x5d11);
			writeH(0x601f);
		}
		else if(_opcode == -2)
		{
			if(Config.DEBUG)
				_log.info("STATUS: Ping request recieved from " + getClient().toString());
			writeC(0x00);
			writeD(0x01);
		}
	}
}
