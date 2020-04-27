package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.util.Location;
import instances.CrystalCavernsInstance;

/**
 * @author: rage
 * @date: 03.12.2009 14:24:07
 */
public class KechiMinion extends Balanced
{
	private Location[] path;
	private boolean moveOnPosition;
	private int currenPoint;
	private long active;

	public KechiMinion(L2Character actor)
	{
		super(actor);
		currenPoint = 0;
		moveOnPosition = true;
		active = System.currentTimeMillis() + 5000;
	}

	@Override
	public boolean thinkActive()
	{
		if(active > System.currentTimeMillis())
			return true;

		if(moveOnPosition && path != null)
		{
			if(!_thisActor.isMoving && currenPoint < path.length)
			{
				_thisActor.setRunning();
				_thisActor.moveToLocation(path[currenPoint], 0, false);
				currenPoint++;
			}
			return true;
		}

		return super.thinkActive();
	}

	@Override
	public void onEvtArrived()
	{
		if(moveOnPosition)
		{
			if(currenPoint < path.length)
			{
				_thisActor.moveToLocation(path[currenPoint], 0, false);
				currenPoint++;
			}
			else
			{
				moveOnPosition = false;
				currenPoint = 0;
				_globalAggro = -1;
			}
		}
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(moveOnPosition)
			return;

		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(moveOnPosition)
			return;

		if(!attacker.isDead() && attacker.getPlayer() != null && attacker.getPlayer().getEffectBySkillId(CrystalCavernsInstance.TIMER_ID) == null)
		{
			if(Config.DEBUG_INSTANCES)
				Instance._log.info(_thisActor.getSpawn().getInstance() + " " + _thisActor + " attacked by " + attacker + " with no timer.");
			if(attacker.getPlayer().getParty() != null)
				for(L2Player member : attacker.getPlayer().getParty().getPartyMembers())
					member.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(member, MapRegionTable.TeleportWhereType.ClosestTown));
			else
				attacker.getPlayer().teleToLocation(MapRegionTable.getInstance().getTeleToLocation(attacker.getPlayer(), MapRegionTable.TeleportWhereType.ClosestTown));
			return;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	public void setActive(long act)
	{
		active = act;
	}

	public void setPath(Location[] p)
	{
		path = p;
	}
}
