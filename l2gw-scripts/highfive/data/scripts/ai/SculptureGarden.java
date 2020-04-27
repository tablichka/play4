package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 23.09.11 17:03
 */
public class SculptureGarden extends DefaultAI
{
	public L2Skill buff = SkillTable.getInstance().getInfo(293535745);
	public int GM_ID = 7;

	public SculptureGarden(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if( GM_ID != 0 )
		{
			long i0 = ServerVariables.getLong("GM_" + GM_ID, -1);
			if( i0 == -1 )
			{
				ServerVariables.set("GM_" + GM_ID, getStoredIdFromCreature(_thisActor));
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		_thisActor.c_ai0 = creature.getStoredId();
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 10027 )
		{
			_thisActor.teleToLocation(115792, -125760, -3373);
			//myself.InstantTeleportInMyTerritory(115792, -125760, -3373, 200);
		}
		else if( eventId == 11038 )
		{
			_thisActor.lookNeighbor(1000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if( c0 != null && c0.isPlayer())
			{
				L2Party party0 = c0.getPlayer().getParty();
				if( party0 != null )
				{
					for(L2Player member : party0.getPartyMembers())
					{
						_thisActor.altUseSkill(buff, member);
					}
				}
			}
		}
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}
