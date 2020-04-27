package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * @author rage
 * @date 02.12.2010 18:11:45
 */
public class CastlePailakaInvaderBoss extends CastlePailakaInvaderWarrior
{
	private int is_last_invader;
	private int Spawn_String_Num;

	public CastlePailakaInvaderBoss(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		is_last_invader = getInt("is_last_invader", 0);
		Spawn_String_Num = getInt("Spawn_String_Num", -1);

		if(String_Num1 > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, String_Num1);
		if(Spawn_String_Num > 0)
			_thisActor.broadcastPacket(new ExShowScreenMessage(10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, true, Spawn_String_Num));

		addTimer(1514, 10000);
		addTimer(1212, 21000);
		addTimer(1401, 25000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1212)
		{
			if(i_ai0 == 0)
			{
				if(String_Num2 > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, String_Num2);
				_thisActor.moveToLocation(pos, 0, true);
				i_ai0 = 1;
			}
		}
		else if(timerId == 1401)
		{
			if(SelfBuff != null)
				_thisActor.altUseSkill(SelfBuff, _thisActor);

			addTimer(1401, 30000);
		}
		else if(timerId == 1601)
		{
			broadcastScriptEvent(INVADER, _thisActor, null, 2000);
			addTimer(1601, 10000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(is_last_invader == 1)
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				inst.notifyEvent("boss_killed", _thisActor, null);
		}
	}
}
