package ai;

import javolution.util.FastList;
import ru.l2gw.gameserver.ai.NpcTrap;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.util.Location;

import java.util.List;

/**
 * @author rage
 * @date 28.10.2010 16:20:16
 */
public class HBTrapDoor extends NpcTrap
{
	private Location _teleLoc;
	private int _instId;

	public HBTrapDoor(L2Character actor)
	{
		super(actor);
		String tele = getString("tele_to", null);
		if(tele != null && !tele.isEmpty())
			_teleLoc = Location.parseLoc(tele);
		else
			_instId = getInt("tele_instance", 0);
	}

	@Override
	protected boolean thinkActive()
	{
		super.thinkActive();

		for(L2Player player : _thisTrap.getAroundPlayers(trapRange))
		{
			if(!_thisTrap.isDetected())
			{
				_thisTrap.setDetected(5);
				break;
			}	
			if(_teleLoc != null)
			{
				player.setStablePoint(null);
				_log.info(_thisTrap + " tele to: " + player + " " + _teleLoc);
				player.teleToLocation(_teleLoc, 0);
				break;
			}
			else if(_instId > 0)
			{
				Instance inst = getInstanceByPlayer(player, _instId);
				if(inst != null)
				{
					player.setStablePoint(inst.getTemplate().getZone().getSpawn());
					_log.info(_thisTrap + " tele to instance: " + player + " " + inst);
					player.teleToLocation(inst.getStartLoc(), inst.getReflection());
					break;
				}
			}
		}

		return true;
	}

	public static Instance getInstanceByPlayer(L2Player player, int instId)
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
			party.addAll(player.getParty().getPartyMembers());

		return InstanceManager.getInstance().createNewInstance(instId, party);
	}
}
