package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.NpcTrap;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 09.01.11 17:35
 */
public class SoDTrap extends NpcTrap
{
	private final GArray<PrivateInfo> _privates;
	private Instance _currentInstance;

	public SoDTrap(L2Character actor)
	{
		super(actor);
		_privates = new GArray<PrivateInfo>();

		String privates = getString("privates", "");
		if(!privates.isEmpty())
		{
			for(String privat : privates.split(";"))
				if(!privat.isEmpty())
				{
					try
					{
						String[] priv = privat.split(",");
						_privates.add(new PrivateInfo(Integer.parseInt(priv[0]), Integer.parseInt(priv[1]), Integer.parseInt(priv[2])));
					}
					catch(Exception e)
					{
						_log.warn(_thisActor + " can't parse private: " + privat + " " + e);
					}
				}
		}
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_currentInstance = _thisActor.getSpawn().getInstance();
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisTrap.isDead() || castSkill)
			return true;

		if(_thisTrap.getLifeTime() > 0 && _thisTrap.getLifeTime() < System.currentTimeMillis())
		{
			_thisTrap.doDie(null);
			return true;
		}

		if(isDetected != _thisTrap.isDetected())
		{
			if(isDetected)
				for(L2Player player : L2World.getAroundPlayers(_thisTrap))
					if(_thisTrap.getPlayer() == null || player != _thisTrap.getPlayer())
						player.removeVisibleObject(_thisTrap);

			isDetected = _thisTrap.isDetected();
		}

		if((trapSkill != null && !_thisTrap.isSkillDisabled(trapSkill.getId()) || _privates != null) && _thisTrap.isActive())
			for(L2Player cha : _thisTrap.getAroundPlayers(trapRange))
				if(trapSkill != null && trapSkill.checkTarget(_thisTrap, trapSkill.getAimingTarget(_thisActor, cha), false, true) == null)
				{
					_thisTrap.setDetected(36000);
					_thisTrap.setActive(false);
					castSkill = true;

					ThreadPoolManager.getInstance().scheduleEffect(new CastSkill(_thisTrap, trapSkill, cha), 2000);
					break;
				}
				else if(_privates != null)
				{
					_thisTrap.setDetected(36000);
					_thisTrap.setActive(false);
					for(PrivateInfo pi : _privates)
						for(int i = 0; i < pi.amount; i++)
							_currentInstance.addSpawn(pi.npcId, GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 50, 100, _thisActor.getReflection()), 0);
					break;
				}

		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_thisTrap.setDetected(0);
		castSkill = false;
		if(_skills.size() > 0)
			trapSkill = _skills.get(Rnd.get(_skills.size()));
		super.onEvtDead(killer);
	}

	private class PrivateInfo
	{
		public long spawnDelay;
		public int npcId, amount;

		public PrivateInfo(int id, int c, int sd)
		{
			spawnDelay = sd * 1000L;
			npcId = id;
			amount = c;
		}
	}
}
