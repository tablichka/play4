package npc.model;

import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author admin
 * @date 27.11.2010 21:12:42
 */
public class UndieingMonsterInstance extends L2MonsterInstance
{
	public UndieingMonsterInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void setCurrentHp(double newHp)
	{
		if(getCurrentHp() > 1)
		{
			if(newHp < 1)
			{
				newHp = 1;
				super.setCurrentHp(newHp);
				stopHpMpRegeneration();
			}
			else
				super.setCurrentHp(newHp);
		}
	}
}
