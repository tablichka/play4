package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 12.01.11 13:03
 */
public class FighterDoppel extends Fighter
{
	private int silhouette;

	public FighterDoppel(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		silhouette = getInt("silhouette", 0);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.isDecayed() && silhouette > 0)
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
			{
				L2NpcInstance mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				mob = inst.addSpawn(silhouette, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 10, 20, _thisActor.getReflection()), 0);
				mob.addDamageHate(attacker, 0, 1000);
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				_thisActor.onDecay();
				return;
			}

			super.onEvtAttacked(attacker, damage, skill);
		}
	}
}
