package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;

/**
 * @author Diamond
 */
public class AllianceOfSplendor extends Fighter
{
	public AllianceOfSplendor(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		try
		{
			if(Rnd.chance(25))
			{
				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(21534));
				spawn.setLoc(_thisActor.getLoc());
				L2NpcInstance npc = spawn.doSpawn(true);
				npc.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, attacker, 100, skill);
				_thisActor.decayMe();
				_thisActor.doDie(_thisActor);
				return;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		super.onEvtAttacked(attacker, damage, skill);
	}
}