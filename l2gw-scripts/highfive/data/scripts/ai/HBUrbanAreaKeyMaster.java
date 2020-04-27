package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 24.10.2010 18:23:01
 */
public class HBUrbanAreaKeyMaster extends Fighter
{
	private static final Location[] _spawns =
			{
					new Location(14044, 254892, -2035),
					new Location(15084, 253788, -2038),
					new Location(14140, 253132, -2039),
					new Location(13964, 251860, -1962),
					new Location(14220, 250428, -1966),
					new Location(16564, 253932, -2060),
					new Location(15596, 255580, -2041),
					new Location(16260, 256284, -2043),
					new Location(20140, 256220, -2116),
					new Location(19852, 255132, -2034),
					new Location(17660, 255060, -2045),
					new Location(17228, 253020, -2041),
					new Location(17548, 250476, -1948),
					new Location(18924, 251268, -2035),
					new Location(21276, 250468, -2006),
					new Location(21324, 251148, -2034),
					new Location(18868, 251820, -2037),
					new Location(20948, 251756, -2033),
					new Location(21996, 253388, -2040),
					new Location(22028, 254028, -2036)
			};
	private static final int AMASKARI_ID = 22449;
	private boolean _firstTimeAttacked;

	public HBUrbanAreaKeyMaster(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_firstTimeAttacked = true;
		Location loc = _spawns[Rnd.get(_spawns.length)];
		_thisActor.teleToLocation(loc);
		_thisActor.setSpawnedLoc(loc);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_firstTimeAttacked)
		{
			_firstTimeAttacked = false;
			Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBUrbanAreaKeyMaster", null);//TODO: Найти fString и заменить.
			for(L2NpcInstance npc : _thisActor.getKnownNpc(5000))
				if(npc.getNpcId() == AMASKARI_ID && !npc.isDead())
				{
					npc.teleToLocation(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 150, 180, _thisActor.getReflection()));
					npc.addDamageHate(attacker, 0, 10000);
					npc.setSpawnedLoc(_thisActor.getLoc());
					break;
				}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}
