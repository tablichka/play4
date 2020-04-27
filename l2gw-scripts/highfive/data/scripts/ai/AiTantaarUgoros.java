package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 07.09.11 17:33
 */
public class AiTantaarUgoros extends WizardUseSkill
{
	public int Normal_Desire = 1000000;
	public long Max_Desire = 1000000000000000000L;
	public int TID_HERB_CHECK = 780001;
	public int TIME_HERB_CHECK = 2;
	public int TID_VACANCY_CHECK = 780002;
	public int TIME_VACANCY_CHECK = 5;
	public int TID_EXILE_DELAY = 780003;
	public int TIME_EXILE_DELAY = 3;
	public int GM_UGOROS = 37;
	public int SID_DEFAULT = 0;
	public int SID_ENGAGING = 1;
	public int SID_NO_NEED_KOMODO = 0;
	public int SID_SEARCHING_KOMODO = 1;
	public int SID_GOING_KOMODO = 2;
	public int clearer_mode = 79;

	public AiTantaarUgoros(L2Character actor)
	{
		super(actor);
		AttackRange = 2;
		Skill01_ID = SkillTable.getInstance().getInfo(421134337);
		Skill01_Check_Dist = 0;
		Skill02_Check_Dist = 0;
		MoveArounding = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		ServerVariables.set("GM_" + GM_UGOROS, _thisActor.getStoredId());
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1801077);
		_thisActor.c_ai1 = 0;
		_thisActor.c_ai2 = 0;
		_thisActor.i_ai3 = SID_DEFAULT;
		_thisActor.i_ai1 = SID_NO_NEED_KOMODO;
		_thisActor.i_ai2 = 0;
		_thisActor.i_quest4 = 0;
		addTimer(TID_VACANCY_CHECK, TIME_VACANCY_CHECK * 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null)
		{
			_thisActor.c_ai0 = attacker.getStoredId();
		}
		super.onEvtAttacked(attacker, damage, skill);

		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "state:" + _thisActor.i_ai1);
		}
		if(_thisActor.i_ai3 == SID_ENGAGING && _thisActor.getCurrentHp() <= (_thisActor.getMaxHp() * 0.800000) && _thisActor.i_ai1 == SID_NO_NEED_KOMODO)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "finding komodo");
			}
			broadcastScriptEvent(78010080, _thisActor.getStoredId(), null, 5000);
			_thisActor.i_ai1 = SID_SEARCHING_KOMODO;
			_thisActor.i_ai2 = 0;
			_thisActor.c_ai2 = 0;
			addTimer(TID_HERB_CHECK, TIME_HERB_CHECK * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onEvtScriptEvent(eventId, arg1, arg2);
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "state:" + _thisActor.i_ai1);
		}
		if(eventId == 78010080 && (Long) arg1 != 0)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(_thisActor.i_ai1 == SID_SEARCHING_KOMODO && c0 != null && c0 != _thisActor)
			{
				if(_thisActor.i_ai2 == 0 || _thisActor.i_ai2 >= _thisActor.getLoc().distance3D(c0.getLoc()))
				{
					_thisActor.c_ai2 = (Long) arg1;
					_thisActor.i_ai2 = (int) _thisActor.getLoc().distance3D(c0.getLoc());
				}
			}
		}
		else if(eventId == 78010084 && L2ObjectsStorage.getAsCharacter((Long) arg1) != null)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "ugoros engaged");
			}
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(_thisActor.i_ai3 == SID_DEFAULT)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1801078, c0.getName());
				ServerVariables.set("GM_" + GM_UGOROS, -2);
				_thisActor.i_ai3 = SID_ENGAGING;
				_thisActor.addDamageHate(c0, 0, Normal_Desire);
				addAttackDesire(c0, Normal_Desire, 0);
			}
		}
		else if(eventId == 78010085)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(_thisActor.i_ai1 == SID_GOING_KOMODO)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1801081);
				L2Character c2 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai2);
				if(c2 != null)
				{
					removeAttackDesire(c2);
					_thisActor.i_ai1 = SID_NO_NEED_KOMODO;
					_thisActor.i_ai2 = 0;
					_thisActor.c_ai2 = 0;
					if(c0 != null)
						addAttackDesire(c0, Normal_Desire, 0);
				}
			}
			else if(c0 != null)
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "komodo attacked");
				}
				_thisActor.addDamageHate(c0, 0, Normal_Desire);
				addAttackDesire(c0, Normal_Desire, 0);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "state:" + _thisActor.i_ai1);
		}
		if(timerId == TID_HERB_CHECK)
		{
			L2Character c2 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai2);
			if(c2 != null && _thisActor.i_ai2 > 0)
			{
				_thisActor.i_ai1 = SID_GOING_KOMODO;
				addAttackDesire(c2, 1, Max_Desire);
				Functions.npcSay(_thisActor, Say2C.ALL, 1801079);
			}
			else
			{
				_thisActor.i_ai1 = SID_NO_NEED_KOMODO;
			}
		}
		else if(timerId == TID_VACANCY_CHECK)
		{
			addTimer(TID_VACANCY_CHECK, TIME_VACANCY_CHECK * 60 * 1000);
			if(ServerVariables.getLong("GM_" + GM_UGOROS) == -2 && _intention != CtrlIntention.AI_INTENTION_ATTACK && _thisActor.i_ai3 == SID_ENGAGING)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1801082);
				_thisActor.createOnePrivate(18919, "AiUgorosKeeper", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, clearer_mode, 0, 0);
				addTimer(TID_EXILE_DELAY, TIME_EXILE_DELAY * 60 * 1000);
			}
			else if(_thisActor.i_ai3 == SID_DEFAULT)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1801083);
			}
		}
		else if(timerId == TID_EXILE_DELAY)
		{
			clearTasks();
			ServerVariables.set("GM_" + GM_UGOROS, _thisActor.getObjectId());
			_thisActor.c_ai1 = 0;
			_thisActor.i_ai3 = SID_DEFAULT;
			_thisActor.i_ai1 = SID_NO_NEED_KOMODO;
			_thisActor.i_ai2 = 0;
			_thisActor.c_ai2 = 0;
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		super.onEvtSpelled(skill, caster);
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "state:" + _thisActor.i_ai1);
		}
		if(skill.getId() == 6648 && _thisActor.i_ai1 == SID_GOING_KOMODO)
		{
			removeAllAttackDesire();
			Functions.npcSay(_thisActor, Say2C.ALL, 1801080);
			_thisActor.i_ai1 = SID_NO_NEED_KOMODO;
			_thisActor.i_ai2 = 0;
			_thisActor.c_ai2 = 0;
			if(_thisActor.i_quest4 == 0)
			{
				_thisActor.i_quest4 = 1;
			}
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				addAttackDesire(c0, Normal_Desire, 0);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1801084);
		ServerVariables.set("GM_" + GM_UGOROS, -1);
		_thisActor.createOnePrivate(32740, "AiUgorosKeeper", 0, 0, _thisActor.getX() + Rnd.get(300) - Rnd.get(300), _thisActor.getY() + Rnd.get(300) - Rnd.get(300), _thisActor.getZ(), 0, clearer_mode, 0, 0);
	}
}
