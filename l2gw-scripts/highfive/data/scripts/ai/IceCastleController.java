package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Territory;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.TerritoryTable;

/**
 * @author: rage
 * @date: 27.09.11 21:11
 */
public class IceCastleController extends DefaultNpc
{
	public String TRR_FREYA_1F = "schuttgart29_2314_06";
	public String AREA_stone_lv1 = "23_14_icequeen_lv1";
	public String AREA_stone_lv2 = "23_14_icequeen_lv2";
	public String AREA_stone_lv3 = "23_14_icequeen_lv3";
	public String AREA_stone_lv4 = "23_14_icequeen_lv4";
	public String AREA_stone_lv5 = "23_14_icequeen_lv5";
	public String AREA_stone_lv6 = "23_14_icequeen_lv6";
	public String AREA_stone_lv7 = "23_14_icequeen_lv7";
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public String MAKER_ice_castle = "schuttgart29_2314_06m1";
	public int is_hard_mode = 0;
	public int debug_mode = 0;

	public IceCastleController(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected synchronized void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140015)
		{
			if(_thisActor.i_ai0 >= 7)
			{
				ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv6, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv7, 1, _thisActor.getReflection());
				DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140041, 0, 0);
				}
			}
			else
			{
				_thisActor.i_ai0++;
				if(_thisActor.i_ai0 < 7)
				{
					_thisActor.changeNpcState(_thisActor.i_ai0);
				}
				else if(_thisActor.i_ai0 >= 7)
				{
					_thisActor.changeNpcState(7);
				}
				switch(_thisActor.i_ai0)
				{
					case 1:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv1, 1, _thisActor.getReflection());
						break;
					case 2:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv1, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv2, 1, _thisActor.getReflection());
						break;
					case 3:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv2, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv3, 1, _thisActor.getReflection());
						break;
					case 4:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv3, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv4, 1, _thisActor.getReflection());
						break;
					case 5:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv4, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv5, 1, _thisActor.getReflection());
						break;
					case 6:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv5, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv6, 1, _thisActor.getReflection());
						break;
				}
			}


			L2Territory terr = TerritoryTable.getInstance().getLocations().get(TRR_FREYA_1F);
			int[] p = terr.getRandomPoint(false);
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0.maximum_npc - maker0.npc_count >= 1)
			{
				_thisActor.createOnePrivate(18853, "IcequeenStone", 0, 0, p[0], p[1], p[2], 0, is_hard_mode, 0, 0);
			}
		}
		else if(eventId == 23140040)
		{
			if(_thisActor.i_ai0 > 0)
			{
				switch(_thisActor.i_ai0)
				{
					case 1:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv1, 0, _thisActor.getReflection());
						break;
					case 2:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv2, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv1, 1, _thisActor.getReflection());
						break;
					case 3:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv3, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv2, 1, _thisActor.getReflection());
						break;
					case 4:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv4, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv3, 1, _thisActor.getReflection());
						break;
					case 5:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv5, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv4, 1, _thisActor.getReflection());
						break;
					case 6:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv6, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv5, 1, _thisActor.getReflection());
						break;
					case 7:
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv7, 0, _thisActor.getReflection());
						ZoneManager.getInstance().areaSetOnOff(AREA_stone_lv6, 1, _thisActor.getReflection());
						break;
				}
				_thisActor.i_ai0--;
				if(_thisActor.i_ai0 == 0)
				{
					_thisActor.changeNpcState(8);
					DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
					if(maker0 != null)
					{
						maker0.onScriptEvent(23140057, 0, 0);
					}
				}
				else
				{
					_thisActor.changeNpcState(_thisActor.i_ai0);
				}
			}
		}
	}
}