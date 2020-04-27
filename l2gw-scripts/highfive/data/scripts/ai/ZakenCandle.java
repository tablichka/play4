package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.serverpackets.ExSendUIEvent;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 09.12.2010 14:51:17
 */
public class ZakenCandle extends DefaultAI
{
	protected int sroom_id = -1;
	protected String large_room_maker_A = "t21_24_slave1f_001m";
	protected String large_room_maker_B = "t21_24_slave1f_002m";
	protected String large_room_maker_C = "t21_24_slave1f_003m";
	protected String large_room_maker_D = "t21_24_slave1f_004m";
	protected String large_room_maker_E = "t21_24_slave1f_005m";
	protected String large_room_maker_F = "t21_24_slave1f_006m";
	protected String large_room_maker_G = "t21_24_slave1f_007m";
	protected String large_room_maker_H = "t21_24_slave1f_008m";
	protected String large_room_maker_I = "t21_24_slave1f_009m";
	protected String large_room_maker_J = "t21_24_slave1f_010m";
	protected String large_room_maker_K = "t21_24_slave1f_011m";
	protected String large_room_maker_L = "t21_24_slave1f_012m";
	protected String large_room_maker_M = "t21_24_slave1f_013m";
	protected String large_room_maker_N = "t21_24_slave1f_014m";
	protected String large_room_maker_O = "t21_24_slave1f_015m";

	protected int OHS_Weapon = 15280;
	protected int THS_Weapon = 15281;
	protected int BOW_Weapon = 15302;
	protected int i_ai3;
	protected long spawnTime;

	protected GArray<Integer> sendUI;
	protected boolean sendUIFlag = true;

	public ZakenCandle(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		sroom_id = getInt("sroom_id", -1);
		spawnTime = System.currentTimeMillis();
		sendUI = new GArray<Integer>();
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return false;

		if(sendUIFlag)
			for(L2Player player : _thisActor.getAroundPlayers(1500))
				if(_thisActor.isInRange(player, 1000) && !sendUI.contains(player.getObjectId()))
				{
					sendUI.add(player.getObjectId());
					player.sendPacket(new ExSendUIEvent(player.getObjectId(), true, true, (int) ((System.currentTimeMillis() - spawnTime) / 1000), 3600));
				}
				else if(!_thisActor.isInRange(player, 1000) && sendUI.contains(player.getObjectId()))
					sendUI.remove((Integer) player.getObjectId());

		return true;
	}

	@Override
	public boolean onTalk(L2Player player)
	{
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.c_ai1 = player.getStoredId();
			addTimer(2124008, 8000);
			_thisActor.setRHandId(OHS_Weapon);
			_thisActor.setLHandId(OHS_Weapon);
			_thisActor.updateAbnormalEffect();
		}
		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2124008)
		{
			int i1 = 0;
			int i3 = -1;
			int i4, i5;
			i4 = i5 = 0;

			int pos = ServerVariables.getInt("zaken_pos_" + _thisActor.getReflection(), 0);
			switch(pos)
			{
				case 0:
					if(sroom_id == 1 || sroom_id == 3 || sroom_id == 4 || sroom_id == 6)
						i1 = 1;
					i_ai3 = 0;
					i4 = 1;
					break;
				case 1:
					if(sroom_id == 2 || sroom_id == 4 || sroom_id == 5 || sroom_id == 7)
						i1 = 1;
					i_ai3 = 1;
					i4 = 1;
					break;
				case 2:
					if(sroom_id == 4 || sroom_id == 6 || sroom_id == 7 || sroom_id == 9)
						i1 = 1;
					i_ai3 = 2;
					i4 = 1;
					break;
				case 3:
					if(sroom_id == 6 || sroom_id == 8 || sroom_id == 9 || sroom_id == 11)
						i1 = 1;
					i_ai3 = 3;
					i4 = 1;
					break;
				case 4:
					if(sroom_id == 7 || sroom_id == 9 || sroom_id == 10 || sroom_id == 12)
						i1 = 1;
					i_ai3 = 4;
					i4 = 1;
					break;
				case 5:
					if(sroom_id == 13 || sroom_id == 15 || sroom_id == 16 || sroom_id == 18)
						i1 = 1;
					i_ai3 = 0;
					i4 = 2;
					break;
				case 6:
					if(sroom_id == 14 || sroom_id == 16 || sroom_id == 17 || sroom_id == 19)
						i1 = 1;
					i_ai3 = 1;
					i4 = 2;
					break;
				case 7:
					if(sroom_id == 16 || sroom_id == 18 || sroom_id == 19 || sroom_id == 21)
						i1 = 1;
					i_ai3 = 2;
					i4 = 2;
					break;
				case 8:
					if(sroom_id == 18 || sroom_id == 20 || sroom_id == 21 || sroom_id == 23)
						i1 = 1;
					i_ai3 = 3;
					i4 = 2;
					break;
				case 9:
					if(sroom_id == 19 || sroom_id == 21 || sroom_id == 22 || sroom_id == 24)
						i1 = 1;
					i_ai3 = 4;
					i4 = 2;
					break;
				case 10:
					if(sroom_id == 25 || sroom_id == 27 || sroom_id == 28 || sroom_id == 30)
						i1 = 1;
					i_ai3 = 0;
					i4 = 3;
					break;
				case 11:
					if(sroom_id == 26 || sroom_id == 28 || sroom_id == 29 || sroom_id == 31)
						i1 = 1;
					i_ai3 = 1;
					i4 = 3;
					break;
				case 12:
					if(sroom_id == 28 || sroom_id == 30 || sroom_id == 31 || sroom_id == 33)
						i1 = 1;
					i_ai3 = 2;
					i4 = 3;
					break;
				case 13:
					if(sroom_id == 30 || sroom_id == 32 || sroom_id == 33 || sroom_id == 35)
						i1 = 1;
					i_ai3 = 3;
					i4 = 3;
					break;
				case 14:
					if(sroom_id == 31 || sroom_id == 33 || sroom_id == 34 || sroom_id == 36)
						i1 = 1;
					i_ai3 = 4;
					i4 = 3;
					break;
			}

			String groupName = null;

			if(i1 == 0)
			{
				switch(sroom_id)
				{
					case 1:
						groupName = large_room_maker_A + "1";
						i3 = 1;
						i5 = 0;
						break;
					case 2:
						groupName = large_room_maker_B + "1";
						i3 = 1;
						i5 = 1;
						break;
					case 3:
						groupName = large_room_maker_A + "2";
						i3 = 1;
						i5 = 0;
						break;
					case 4:
						groupName = large_room_maker_C + "1";
						i3 = 1;
						i5 = 2;
						break;
					case 5:
						groupName = large_room_maker_B + "2";
						i3 = 1;
						i5 = 1;
						break;
					case 6:
						groupName = large_room_maker_C + "2";
						i3 = 1;
						i5 = 2;
						break;
					case 7:
						groupName = large_room_maker_C + "3";
						i3 = 1;
						i5 = 2;
						break;
					case 8:
						groupName = large_room_maker_D + "1";
						i3 = 1;
						i5 = 3;
						break;
					case 9:
						groupName = large_room_maker_C + "4";
						i3 = 1;
						i5 = 2;
						break;
					case 10:
						groupName = large_room_maker_E + "1";
						i3 = 1;
						i5 = 4;
						break;
					case 11:
						groupName = large_room_maker_D + "2";
						i3 = 1;
						i5 = 3;
						break;
					case 12:
						groupName = large_room_maker_E + "2";
						i3 = 1;
						i5 = 4;
						break;
					case 13:
						groupName = large_room_maker_F + "1";
						i3 = 2;
						i5 = 0;
						break;
					case 14:
						groupName = large_room_maker_G + "1";
						i3 = 2;
						i5 = 1;
						break;
					case 15:
						groupName = large_room_maker_F + "2";
						i3 = 2;
						i5 = 0;
						break;
					case 16:
						groupName = large_room_maker_H + "1";
						i3 = 2;
						i5 = 2;
						break;
					case 17:
						groupName = large_room_maker_G + "2";
						i3 = 2;
						i5 = 1;
						break;
					case 18:
						groupName = large_room_maker_H + "2";
						i3 = 2;
						i5 = 2;
						break;
					case 19:
						groupName = large_room_maker_H + "3";
						i3 = 2;
						i5 = 2;
						break;
					case 20:
						groupName = large_room_maker_I + "1";
						i3 = 2;
						i5 = 3;
						break;
					case 21:
						groupName = large_room_maker_H + "4";
						i3 = 2;
						i5 = 2;
						break;
					case 22:
						groupName = large_room_maker_J + "1";
						i3 = 2;
						i5 = 4;
						break;
					case 23:
						groupName = large_room_maker_I + "2";
						i3 = 2;
						i5 = 3;
						break;
					case 24:
						groupName = large_room_maker_J + "2";
						i3 = 2;
						i5 = 4;
						break;
					case 25:
						groupName = large_room_maker_K + "1";
						i3 = 3;
						i5 = 0;
						break;
					case 26:
						groupName = large_room_maker_L + "1";
						i3 = 3;
						i5 = 1;
						break;
					case 27:
						groupName = large_room_maker_K + "2";
						i3 = 3;
						i5 = 0;
						break;
					case 28:
						groupName = large_room_maker_M + "1";
						i3 = 3;
						i5 = 2;
						break;
					case 29:
						groupName = large_room_maker_L + "2";
						i3 = 3;
						i5 = 1;
						break;
					case 30:
						groupName = large_room_maker_M + "2";
						i3 = 3;
						i5 = 2;
						break;
					case 31:
						groupName = large_room_maker_M + "3";
						i3 = 3;
						i5 = 2;
						break;
					case 32:
						groupName = large_room_maker_N + "1";
						i3 = 3;
						i5 = 3;
						break;
					case 33:
						groupName = large_room_maker_M + "4";
						i3 = 3;
						i5 = 2;
						break;
					case 34:
						groupName = large_room_maker_O + "1";
						i3 = 3;
						i5 = 4;
						break;
					case 35:
						groupName = large_room_maker_N + "2";
						i3 = 3;
						i5 = 3;
						break;
					case 36:
						groupName = large_room_maker_O + "2";
						i3 = 3;
						i5 = 4;
						break;
				}

				_thisActor.setRHandId(THS_Weapon);
				_thisActor.setLHandId(THS_Weapon);
				_thisActor.updateAbnormalEffect();

				if(i_ai3 == i5 && i3 != i4)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 0, 10000, 0, 1800866);

					if(groupName != null)
					{
						Instance inst = _thisActor.getSpawn().getInstance();
						inst.spawnEvent(groupName);
						addTimer(2124009, 2000);
						return;
					}
				}
				if(groupName != null)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 0, 10000, 0, 1800867);
					Instance inst = _thisActor.getSpawn().getInstance();
					inst.spawnEvent(groupName);
					addTimer(2124009, 2000);
				}

				if(i3 != i4)
				{
					Instance inst = _thisActor.getSpawn().getInstance();
					if(inst != null)
						switch(i3)
						{
							case 1:
								inst.sendScriptEvent(2124006, null, null);
								break;
							case 2:
								inst.sendScriptEvent(2124007, null, null);
								break;
							case 3:
								inst.sendScriptEvent(2124008, null, null);
								break;
						}
				}
			}
			else
			{
				_thisActor.setRHandId(BOW_Weapon);
				_thisActor.setLHandId(BOW_Weapon);
				_thisActor.updateAbnormalEffect();
				Instance inst = _thisActor.getSpawn().getInstance();
				if(inst != null)
					inst.sendScriptEvent(2124002, null, null);
			}
		}
		else if(timerId == 2124009)
			broadcastScriptEvent(10016, _thisActor.c_ai1, null, 2500);
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2124010)
			sendUIFlag = false;
	}
}
