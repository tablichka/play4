package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 13.12.11 19:33
 */
public class Is1TumorActive extends DefaultNpc
{
	public int type = 0;
	public int start_hp_rate = 59;
	public int regen_value = 5;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 750;
	public int room_number = 0;
	public String AreaName01 = "is1_attack_room1_mob_buff1";
	public String AreaName02 = "is1_attack_room1_mob_buff2";
	public String AreaName03 = "is1_attack_room1_mob_buff3";
	public String AreaName04 = "is1_attack_room1_mob_buff4";
	public String AreaName05 = "is1_attack_room1_mob_buff5";
	public String AreaName11 = "is1_defence_room1_mob_buff1";
	public String AreaName12 = "is1_defence_room1_mob_buff2";
	public String AreaName13 = "is1_defence_room1_mob_buff3";
	public String AreaName14 = "is1_defence_room1_mob_buff4";
	public String AreaName15 = "is1_defence_room1_mob_buff5";
	public String unactive_tumor_maker = "room1_mobm2";

	public Is1TumorActive(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		if(type == 1)
		{
			_thisActor.setCurrentHp(_thisActor.getMaxHp() / 100 * 39);
			broadcastScriptEvent(998917, 0, null, 6000);
		}
		else if(type == 0)
		{
			_thisActor.setCurrentHp(_thisActor.getMaxHp() / 100 * start_hp_rate);
		}
		addTimer(1001, 10000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if((_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) > 80)
			{
				ZoneManager.getInstance().areaSetOnOff(AreaName01, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName02, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName03, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName04, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName05, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName11, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName12, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName13, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName14, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName15, 1, _thisActor.getReflection());
			}
			else if((_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) > 60)
			{
				ZoneManager.getInstance().areaSetOnOff(AreaName01, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName02, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName03, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName04, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName05, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName11, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName12, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName13, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName14, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName15, 0, _thisActor.getReflection());
			}
			else if((_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) > 40)
			{
				ZoneManager.getInstance().areaSetOnOff(AreaName01, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName02, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName03, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName04, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName05, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName11, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName12, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName13, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName14, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName15, 0, _thisActor.getReflection());
			}
			else if((_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) > 20)
			{
				ZoneManager.getInstance().areaSetOnOff(AreaName01, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName02, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName03, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName04, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName05, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName11, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName12, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName13, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName14, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName15, 0, _thisActor.getReflection());
			}
			else if((_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) > 0)
			{
				ZoneManager.getInstance().areaSetOnOff(AreaName01, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName02, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName03, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName04, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName05, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName11, 1, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName12, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName13, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName14, 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff(AreaName15, 0, _thisActor.getReflection());
			}
			addTimer(1001, 10000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 98914)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg2);
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg + regen_value);
			if(c0 != null)
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 998915, 0, null);
			}
		}
		else if(eventId == 9898903)
		{
			int i0 = (Integer) arg1;
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg - i0 * regen_value);
		}
		else if(eventId == 998916 && type == 1)
		{
			broadcastScriptEvent(998916, 0, null, 6000);
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), unactive_tumor_maker);
		if(maker0 != null)
		{
			maker0.onScriptEvent(1001, 0, 0);
		}
		broadcastScriptEvent(989812, 0, null, 600);
		ZoneManager.getInstance().areaSetOnOff(AreaName01, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName02, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName03, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName04, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName05, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName11, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName12, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName13, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName14, 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(AreaName15, 0, _thisActor.getReflection());
	}
}