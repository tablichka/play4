package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;

import java.util.HashMap;

/**
 * AI полиморфных ангелов. После смерти заспавненного респится его двойник, за которого дают и экспу и дроп.
 *
 * @author SYS
 */
public class PolimorphingAngel extends Fighter
{
	// ID для полиморфизма
	private static final HashMap<Integer, Integer> polymorphing = new HashMap<Integer, Integer>();

	static
	{
		polymorphing.put(20830, 20859);
		polymorphing.put(21067, 21068);
		polymorphing.put(21062, 21063);
		polymorphing.put(20831, 20860);
		polymorphing.put(21070, 21071);
	}

	public PolimorphingAngel(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(polymorphing.get(_thisActor.getNpcId())));
			spawn.setLoc(_thisActor.getLoc());
			L2NpcInstance npc = spawn.doSpawn(true);

			npc.addDamageHate(killer, 0, 10000);
			if(npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE)
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		super.onEvtDead(killer);
	}
}