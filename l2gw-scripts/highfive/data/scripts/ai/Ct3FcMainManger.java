package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 12.12.11 14:03
 */
public class Ct3FcMainManger extends Ct3FcManager
{
	public int DB_CHECK_COMPLETE = 4405;
	public int GM_ID = 111114;
	public int WARNING_TIMER = 4406;
	public int AREA_SETTING = 4407;
	public int AREA_CHECK = 4408;
	public String AreaDataName1 = "14_23_beastacon_for_melee";
	public String AreaDataName2 = "14_23_beastacon_for_archer";
	public String AreaDataName3 = "14_23_beastacon_for_mage";
	public String AreaDataName11 = "14_23_beastacon_for_melee_for_pc";
	public String AreaDataName12 = "14_23_beastacon_for_archer_for_pc";
	public String AreaDataName13 = "14_23_beastacon_for_mage_for_pc";
	public String AreaDataName4 = "13_23_cocracon_for_melee";
	public String AreaDataName5 = "13_23_cocracon_for_archer";
	public String AreaDataName6 = "13_23_cocracon_for_mage";
	public String AreaDataName14 = "13_23_cocracon_for_melee_for_pc";
	public String AreaDataName15 = "13_23_cocracon_for_archer_for_pc";
	public String AreaDataName16 = "13_23_cocracon_for_mage_for_pc";
	public String AreaDataName7 = "14_23_raptilicon_for_melee";
	public String AreaDataName8 = "14_23_raptilicon_for_archer";
	public String AreaDataName9 = "14_23_raptilicon_for_mage";
	public String AreaDataName17 = "14_23_raptilicon_for_melee_for_pc";
	public String AreaDataName18 = "14_23_raptilicon_for_archer_for_pc";
	public String AreaDataName19 = "14_23_raptilicon_for_mage_for_pc";

	public Ct3FcMainManger(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		ZoneManager.getInstance().areaSetOnOff(AreaDataName1, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName2, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName3, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName4, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName5, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName6, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName7, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName8, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName9, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName11, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName12, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName13, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName14, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName15, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName16, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName17, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName18, 0);
		ZoneManager.getInstance().areaSetOnOff(AreaDataName19, 0);
		addTimer(DB_CHECK_COMPLETE, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DB_CHECK_COMPLETE)
		{
			if(ServerVariables.getInt("GM_" + 33, -1) <= 0 || ServerVariables.getInt("GM_" + 33, -1) >= 7)
			{
				int i0 = Rnd.get(6) + 1;
				ServerVariables.set("GM_" + 33, i0);
			}
			addTimer(AREA_SETTING, 5000);
		}
		else if(timerId == AREA_SETTING)
		{
			if(_thisActor.i_ai1 == 0)
			{
				switch(ServerVariables.getInt("GM_" + 33, -1))
				{
					case 1:
						ServerVariables.set("GM_" + 34, 1);
						ServerVariables.set("GM_" + 35, 2);
						ServerVariables.set("GM_" + 36, 3);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName1, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName5, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName9, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName11, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName15, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName19, 1);
						_thisActor.i_ai1 = 1;
						break;
					case 2:
						ServerVariables.set("GM_" + 34, 1);
						ServerVariables.set("GM_" + 35, 3);
						ServerVariables.set("GM_" + 36, 2);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName1, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName6, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName8, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName11, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName16, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName18, 1);
						_thisActor.i_ai1 = 1;
						break;
					case 3:
						ServerVariables.set("GM_" + 34, 2);
						ServerVariables.set("GM_" + 35, 1);
						ServerVariables.set("GM_" + 36, 3);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName2, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName4, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName9, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName12, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName14, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName19, 1);
						_thisActor.i_ai1 = 1;
						break;
					case 4:
						ServerVariables.set("GM_" + 34, 2);
						ServerVariables.set("GM_" + 35, 3);
						ServerVariables.set("GM_" + 36, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName2, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName6, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName7, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName12, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName16, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName17, 1);
						_thisActor.i_ai1 = 1;
						break;
					case 5:
						ServerVariables.set("GM_" + 34, 3);
						ServerVariables.set("GM_" + 35, 1);
						ServerVariables.set("GM_" + 36, 2);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName3, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName4, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName8, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName13, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName14, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName18, 1);
						_thisActor.i_ai1 = 1;
						break;
					case 6:
						ServerVariables.set("GM_" + 34, 3);
						ServerVariables.set("GM_" + 35, 2);
						ServerVariables.set("GM_" + 36, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName3, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName5, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName7, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName13, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName15, 1);
						ZoneManager.getInstance().areaSetOnOff(AreaDataName17, 1);
						_thisActor.i_ai1 = 1;
						break;
				}
			}
		}
	}

	@Override
	public void onFieldCycleExpired(int fieldId, int oldStep, int i1)
	{
		if(fieldId == RaceCycleID)
		{
			if(oldStep == 2)
			{
				if(i1 == 0)
				{
					_thisActor.i_ai1 = 0;
					int i0 = Rnd.get(6) + 1;
					ServerVariables.set("GM_" + 33, i0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName1, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName2, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName3, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName4, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName5, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName6, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName7, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName8, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName9, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName11, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName12, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName13, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName14, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName15, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName16, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName17, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName18, 0);
					ZoneManager.getInstance().areaSetOnOff(AreaDataName19, 0);
					addTimer(AREA_SETTING, 30000);
				}
			}
		}
		super.onFieldCycleExpired(fieldId, oldStep, i1);
	}
}