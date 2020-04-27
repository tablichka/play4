package npc.model;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 04.09.2009 17:15:03
 */
public class KamaWardenInstance extends L2RaidBossInstance
{
	private int _debuffTarget;

	public KamaWardenInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		if(getAIParams() != null)
			_debuffTarget = getAIParams().getInteger("kama_debuff_target", 0);

		if(_debuffTarget == 0)
			_log.warn(this + ": kama_debuff_target ai_param not defined!");
	}

	@Override
	public void doDie(L2Character killer)
	{
		super.doDie(killer);

		if(_debuffTarget > 0)
		{
			L2NpcInstance target = L2ObjectsStorage.getByNpcId(_debuffTarget, getReflection());
			if(target != null && !target.isDead())
			{
				L2Skill skill = SkillTable.getInstance().getInfo(4070, 1);
				if(skill != null)
					skill.applyEffects(target, target, false, 3600000);
			}
		}
	}
}
