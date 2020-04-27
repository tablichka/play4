package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 19.08.2010 16:19:30
 */
public class BizarreCocoon extends DefaultAI
{
	private static final int _growthId = 2905;
	private L2Spawn _spawn;

	public BizarreCocoon(L2Character actor)
	{
		super(actor);
		_thisActor.setIsInvul(true);
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_spawn != null)
			_spawn.despawnAll();

		try
		{
			_spawn = new L2Spawn(NpcTable.getTemplate(Rnd.get(25667, 25670)));
			_spawn.setAmount(1);
			_spawn.stopRespawn();
		}
		catch(Exception e)
		{
			_log.warn(_thisActor + " can't create spawn for boss: " + e);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill.getId() == _growthId && caster.getTarget() == _thisActor)
		{
			_spawn.setReflection(_thisActor.getReflection());
			_spawn.setLoc(_thisActor.getLoc());
			L2NpcInstance boss = _spawn.spawnOne();
			boss.addDamageHate(caster, 0, 999);
			boss.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, caster);
			_thisActor.doDie(caster);
		}
	}
}
