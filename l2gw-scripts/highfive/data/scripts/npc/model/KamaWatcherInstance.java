package npc.model;

import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Util;

/**
 * @author rage
 * @date 04.09.2009 16:01:59
 */
public class KamaWatcherInstance extends L2MonsterInstance
{
	private int _debuffTarget;

	public KamaWatcherInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		if(getAIParams() != null)
			_debuffTarget = getAIParams().getInteger("kama_debuff_target", 0);

		if(_debuffTarget == 0)
			_log.warn(this + ": kama_debuff_target ai_param not defined!");
	}

	@Override
	public void spawnMinions()
	{
		if(getTemplate().getMinionData() != null)
		{
			GArray<L2MinionData> minions = getMinionsData();

			for(L2MinionData minion : minions)
			{
				int a = 360 / minion.minionAmount;
				for(int i = 0; i < minion.minionAmount; i++)
					minionList.spawnSingleMinion(minion, Util.getPointInRadius(getLoc(), 150, a * i));
			}
		}
	}

	@Override
	public void doDie(L2Character killer)
	{
		super.doDie(killer);

		if(_debuffTarget > 0)
		{
			for(L2NpcInstance cha : getKnownNpc(2500))
				if(cha != null && cha.getNpcId() == getNpcId() && cha.getReflection() == getReflection() && !cha.isDead())
					return;

			L2NpcInstance target = L2ObjectsStorage.getByNpcId(_debuffTarget, getReflection());
			if(target != null && !target.isDead())
			{
				L2Skill skill = SkillTable.getInstance().getInfo(4321, 1);
				if(skill != null)
					skill.applyEffects(target, target, false, 3600000);
			}
		}
	}

	@Override
	public void callMinionsToAssist(L2Character attacker, L2Character victim, int damage)
	{
	}
}
