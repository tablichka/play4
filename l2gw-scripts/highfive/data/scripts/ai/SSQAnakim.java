package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.Say2;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 27.11.2010 15:28:29
 */
public class SSQAnakim extends DefaultAI
{
	private static final L2Skill _skill1 = SkillTable.getInstance().getInfo(6191, 1);

	public SSQAnakim(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.c_ai0 = 0;
		_thisActor.i_ai0 = 0;
		broadcastScriptEvent(10, _thisActor, null, 3000);
		addTimer(1001, 1000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker.isPlayer())
			return;

		_thisActor.addDamageHate(attacker, damage, skill != null ? skill.getEffectPoint() : damage);
		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
	}

	@Override
	protected boolean createNewTask()
	{
		L2Character _temp_attack_target = getAttackTarget();
		if(_temp_attack_target == null)
			_temp_attack_target = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);

		if(_temp_attack_target != null)
			addUseSkillDesire(_temp_attack_target, _skill1, 1, 1, DEFAULT_DESIRE * 2);

		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(_thisActor.i_ai0 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 19606);
				L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_ai1);
				if(player != null && _thisActor.isInRange(player, 2000))
					player.sendPacket(new Say2(_thisActor.getObjectId(), Say2C.TELL, "->" + player.getName(), 19611));
				_thisActor.i_ai0 = 1;
			}
			else if(Rnd.chance(10))
			{
				int i0 = Rnd.get(3);
				L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_ai1);
				if(i0 < 1)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, 19607);
					if(player != null && _thisActor.isInRange(player, 2000))
						player.sendPacket(new Say2(_thisActor.getObjectId(), Say2C.TELL, "->" + _thisActor.getName(), 19612));
				}
				else if(i0 < 2)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, 19608);
					if(player != null && _thisActor.isInRange(player, 2000))
						player.sendPacket(new Say2(_thisActor.getObjectId(), Say2C.TELL, "->" + _thisActor.getName(), 19613));
				}
				else
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, 19609);
					if(player != null && _thisActor.isInRange(player, 2000))
						player.sendPacket(new Say2(_thisActor.getObjectId(), Say2C.TELL, "->" + _thisActor.getName(), 19614, player.getName()));
				}
			}
			broadcastScriptEvent(10, _thisActor, null, 3000);
			addTimer(1001, 1000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20)
		{
			_thisActor.c_ai0 = ((L2Character) arg1).getStoredId();
			_thisActor.addDamageHate((L2Character) arg1, 0, 100);
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, arg1);
		}
		else if(eventId == 30)
			_thisActor.i_ai1 = (Integer) arg1;
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0));
	}
}
