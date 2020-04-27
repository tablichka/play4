package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;

/**
 * AI моба-мага для Isle of Prayer.<br>
 * - Если атакован членом группы, состоящей более чем из 2х чаров, то спаунятся штрафные мобы Witch Warder ID: 18364, 18365, 18366 (случайным образом 2 штуки).
 * @author SYS
 */
public class IsleOfPrayerFighter extends Fighter
{
	private boolean _penaltyMobsNotSpawned = true;
	private static final int PENALTY_MOBS[] = { 18364, 18365, 18366 };

	public IsleOfPrayerFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_penaltyMobsNotSpawned && attacker != null)
		{
			L2Player player = attacker.getPlayer();

			if(player != null)
			{
				L2Party party = player.getParty();
				if(party != null && party.getMemberCount() > 2)
				{
					_penaltyMobsNotSpawned = false;
					for(int i = 0; i < 2; i++)
						try
						{
							Location pos = GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 100, 120, _thisActor.getReflection());
							L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(PENALTY_MOBS[Rnd.get(PENALTY_MOBS.length)]));
							spawn.setLoc(pos);
							L2NpcInstance npc = spawn.doSpawn(true);
							npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, player, Rnd.get(1, 100));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_penaltyMobsNotSpawned = true;
		super.onEvtDead(killer);
	}
}