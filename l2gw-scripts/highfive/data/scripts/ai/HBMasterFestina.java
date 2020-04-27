package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 28.10.2010 18:08:08
 */
public class HBMasterFestina extends Fighter
{
	private long _nextFactionCall;

	public HBMasterFestina(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!_thisActor.isDead() && _nextFactionCall < System.currentTimeMillis())
		{
			_nextFactionCall = System.currentTimeMillis() + 30000;
			for(L2NpcInstance npc : _thisActor.getKnownNpc(3000))
				if(npc.isMonster() && !npc.isDead())
					npc.addDamageHate(attacker, 0, 100);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(_thisActor.getSpawn() != null)
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				inst.addSpawn(18429, new Location(-11171, 272705, -11936), 60);
		}
	}
}
