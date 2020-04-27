package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;

/**
 * AI для Timak Orc Troop Leader ID: 20767, кричащего и призывающего братьев по клану при ударе.
 *
 * @author SYS
 */
public class TimakOrcTroopLeader extends Fighter
{
	private boolean _firstTimeAttacked = true;
	private static final int BROTHERS[] = { 20768, // Timak Orc Troop Shaman
			20769, // Timak Orc Troop Warrior
			20770 // Timak Orc Troop Archer
	};

	public TimakOrcTroopLeader(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_firstTimeAttacked)
		{
			_firstTimeAttacked = false;
			Functions.npcSay(_thisActor, Say2C.ALL, "Соратники, на помощь! На нас напали!");//TODO: Найти fString и заменить.
			for(int bro : BROTHERS)
				try
				{
					Location pos = GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 100, 120, _thisActor.getReflection());
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(bro));
					spawn.setLoc(pos);
					L2NpcInstance npc = spawn.doSpawn(true);
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, Rnd.get(1, 100), skill);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}