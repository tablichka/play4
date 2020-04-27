package npc.model;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 30.09.2009 14:31:36
 */
public class DungeonMinionInstance extends L2MonsterInstance
{
	public DungeonMinionInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void addDamageHate(L2Character attacker, long damage, long aggro)
	{
		if(damage > 0 && aggro == 0)
			aggro = damage;

		if(attacker == null)
			return;

		AggroInfo ai = getAggroList().get(attacker.getObjectId());

		if(ai != null)
		{
			ai.damage += damage;
			ai.hate += aggro;
			ai.level = attacker.getLevel();
			if(ai.getAttacker() != attacker)
				ai.setAttacker(attacker);
			if(ai.hate < 0)
				ai.hate = 0;
		}
		else if(aggro > 0)
		{
			ai = new AggroInfo(attacker);
			ai.damage = damage;
			ai.hate = aggro;
			getAggroList().put(attacker.getObjectId(), ai);
		}
	}
}
