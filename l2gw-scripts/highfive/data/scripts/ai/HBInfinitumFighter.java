package ai;

import javolution.util.FastList;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.List;

/**
 * @author rage
 * @date 27.10.2010 15:23:44
 */
public class HBInfinitumFighter extends Fighter
{
	private int _currentFloor;
	public static final Location[] _teleLoc =
			{
					new Location(-22204, 277056, -15045),
					new Location(-22204, 277056, -13381),
					new Location(-22204, 277056, -11566),
					new Location(-22204, 277056, -9925),
					new Location(-22204, 277056, -8215),
					new Location(-19017, 277056, -8256),
					new Location(-19017, 277056, -9924),
					new Location(-19017, 277056, -11648),
					new Location(-19017, 277056, -13376),
					new Location(-19017, 277056, -15040)
			};

	public static final L2Zone[] _floors = new L2Zone[10];
	static
	{
		for(int i = 0; i < 10; i++)
			_floors[i] = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 705001 + i);
	}

	public HBInfinitumFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_currentFloor = getCurrentFloor(_thisActor);
		if(_currentFloor < 0)
			_log.warn(_thisActor + " " + _thisActor.getLoc() + " can't find floor!");
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || _thisActor.isDead())
			return;

		if(attacker.getPlayer() != null && teleToNextFloor(attacker.getPlayer(), _currentFloor))
			return;

		super.onEvtAttacked(attacker, damage, skill);
	}

	public static int getCurrentFloor(L2NpcInstance npc)
	{
		for(int i = 0; i < _floors.length; i++)
			if(_floors[i].isInsideZone(npc))
				return i;

		return -1;
	}

	public static Instance getInstanceByPlayer(L2Player player, int instId, int currentFloor)
	{
		Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
		if(inst != null)
			return inst;

		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);
		if(it == null)
		{
			_log.warn("No instance template: " + instId);
			return null;
		}

		List<L2Player> party = new FastList<L2Player>();
		if(player.getParty() == null)
			party.add(player);
		else
			for(L2Player member : player.getParty().getPartyMembers())
				if(_floors[currentFloor].isInsideZone(member))
					party.add(member);

		return InstanceManager.getInstance().createNewInstance(instId, party);
	}

	public static boolean teleToNextFloor(L2Player player, int currentFloor)
	{
		int chance = Rnd.get(40000);
		int nextFloor = -1;

		// Портает вниз (c 1 и 6 вниз не портает)
		if(chance <= 5 && currentFloor != 0 && currentFloor != 5)
			nextFloor = currentFloor - 1;
		// Портает вверх
		else if(chance < 20 && chance > 5)
			nextFloor = currentFloor + 1;

		if(nextFloor >= 0)
		{
			Location loc = _teleLoc[nextFloor];
			if(nextFloor == 4 || nextFloor == 9) // Instances: 3 Tower of Infinitum Demon Prince, 4 Tower of Infinitum Ranku
			{
				Instance inst = getInstanceByPlayer(player, nextFloor == 4 ? 3 : 4, currentFloor);
				if(player.getParty() == null)
				{
					player.setStablePoint(_teleLoc[currentFloor]);
					player.teleToLocation(inst.getStartLoc(), inst.getReflection());
				}
				else
					for(L2Player member : player.getParty().getPartyMembers())
						if(_floors[currentFloor].isInsideZone(member) && inst.isInside(member.getObjectId()))
						{
							member.setStablePoint(_teleLoc[currentFloor]);
							member.teleToLocation(inst.getStartLoc(), inst.getReflection());
						}
				return true;
			}

			if(player.getParty() == null)
				player.teleToLocation(loc);
			else
				for(L2Player member : player.getParty().getPartyMembers())
					if(_floors[currentFloor].isInsideZone(member))
						member.teleToLocation(loc);

			return true;
		}
		return false;
	}
}
