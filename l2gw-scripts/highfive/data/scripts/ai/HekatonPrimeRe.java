package ai;

import ai.base.RaidBossType4;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.PlaySound;

/**
 * @author: rage
 * @date: 29.12.11 11:47
 */
public class HekatonPrimeRe extends RaidBossType4
{
	public int GlobalMap_ID = 39;

	public HekatonPrimeRe(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_quest3 = 0;
		_thisActor.i_quest4 = (int) (System.currentTimeMillis() / 1000);
		addTimer(1001, 1000);
		_thisActor.weight_point = 10;
		_thisActor.setHide(true);
		_thisActor.updateAbnormalEffect();
		_thisActor.i_ai5 = 0;
		_inMyTerritory = true;
		ServerVariables.set("GM_" + GlobalMap_ID, 1);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai5 == 0)
		{
			return;
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_thisActor.i_ai5 == 0)
		{
			return;
		}
		super.onEvtSeeSpell(skill, caster);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2519001)
		{
			ServerVariables.set("GM_" + GlobalMap_ID, 0);
			_thisActor.setHide(false);
			_thisActor.i_ai5 = 1;
			_thisActor.broadcastPacket(new PlaySound(RaidSpawnMusic), 8000);
			_thisActor.spawnMinions();
			_thisActor.updateAbnormalEffect();
		}
	}
}