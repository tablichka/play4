package ai.rr;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 17:29
 */
public class RoyalRushAreaController extends RoyalRushDefaultNpc
{
	public String AreaName = "royal_rush_area_controller_default";
	public L2Skill StatusEffect = SkillTable.getInstance().getInfo(303300609);
	public int type = 0;

	public RoyalRushAreaController(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
		switch(type)
		{
			case 0:
				addTimer(3001, 120000);
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010474);
				break;
			case 1:
				addTimer(3001, 60000);
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010473);
				break;
			case 2:
				ZoneManager.getInstance().areaSetOnOff(AreaName, 1);
				addUseSkillDesire(_thisActor, StatusEffect, 1, 0, 1000000);
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010472);
				addTimer(3002, 30000);
				break;
			case 3:
				addTimer(3001, 180000);
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010475);
				break;
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 3001 )
		{
			ZoneManager.getInstance().areaSetOnOff(AreaName, 1);
			addUseSkillDesire(_thisActor, StatusEffect, 1, 0, 1000000);
			switch(type)
			{
				case 0:
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1010477);
					addTimer(3002, 30000);
					break;
				case 1:
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1010476);
					addTimer(3002, 30000);
					break;
				case 2:
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1010472);
					break;
				case 3:
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1010478);
					addTimer(3002, 30000);
					break;
			}
		}
		if( timerId == 3002 )
		{
			addUseSkillDesire(_thisActor, StatusEffect, 1, 0, 1000000);
			addTimer(3002, 30000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
		switch(type)
		{
			case 0:
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010481);
				break;
			case 1:
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010480);
				break;
			case 2:
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010479);
				break;
			case 3:
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1010482);
				break;
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		addUseSkillDesire(_thisActor, StatusEffect, 1, 0, 1000000);
	}
}