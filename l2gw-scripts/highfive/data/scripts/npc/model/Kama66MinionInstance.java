package npc.model;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 01.09.2009 9:37:28
 */
public class Kama66MinionInstance extends L2MonsterInstance
{
	private long _lastSpeach;

	public Kama66MinionInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		if(_lastSpeach < System.currentTimeMillis() && Rnd.chance(60))
		{
			_lastSpeach = System.currentTimeMillis() + 60000;
			Functions.npcSay(this, Say2C.ALL, "Arg! The pain is more than I can stand!");
		}
		super.decreaseHp(damage, attacker, directHp, reflect);
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(Rnd.chance(75))
			Functions.npcSay(this, Say2C.ALL, "Ahh! How did he find my weakness?");

		super.doDie(killer);
	}
}
