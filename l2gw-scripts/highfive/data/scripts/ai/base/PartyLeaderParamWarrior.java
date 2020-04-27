package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 21.09.11 18:49
 */
public class PartyLeaderParamWarrior extends Warrior
{
	public int AttackLowHP = 0;
	public String Privates = "";
	public int ShoutTarget = 0;
	public int SummonPrivateRate = 0;

	public PartyLeaderParamWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(SummonPrivateRate == 0)
		{
			if(_thisActor.getMinionsData() != null && !_thisActor.getMinionsData().isEmpty())
			{
				_thisActor.spawnMinions();
			}
			_thisActor.i_ai2 = 1;
		}
		else
		{
			_thisActor.i_ai2 = 0;
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_ai2 == 0)
		{
			if(Rnd.get(100) < SummonPrivateRate)
			{
				_thisActor.spawnMinions();
				switch(Rnd.get(4))
				{
					case 0:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000294);
						break;
					case 1:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000403);
						break;
					case 2:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000404);
						break;
					case 3:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000405);
						break;
				}
				_thisActor.i_ai2 = 1;
			}
		}
		if(ShoutTarget == 1)
		{
			if(Rnd.get(100) < 50 && ((attacker.getCurrentHp() / attacker.getMaxHp()) * 100) < 40)
			{
				if(_thisActor.getMostHated() != null && attacker.isPlayer())
				{
					if(_thisActor.getMostHated() == attacker)
					{
						switch(Rnd.get(3))
						{
							case 0:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000291, attacker.getName());
								break;
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000398, attacker.getName());
								break;
							case 2:
								Functions.npcSay(_thisActor, Say2C.ALL, 1000399, attacker.getName());
								break;
						}
						removeAllAttackDesire();
						addAttackDesire(attacker, 1, 1000);
						_thisActor.l_ai5 = getStoredIdFromCreature(attacker);
						broadcastScriptEvent(10002, getStoredIdFromCreature(_thisActor), null, 300);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}
}
