package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.10.11 14:57
 */
public class DivineAnais extends WizardUseSkill
{
	public int TIME_FOR_ANAIS_INFO = 901;

	public DivineAnais(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(414449665);
		Skill02_ID = SkillTable.getInstance().getInfo(414384129);
		Skill03_ID = SkillTable.getInstance().getInfo(414515201);
		Skill03_AttackSplash = 1;
		Skill03_Target = 1;
		MoveArounding = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		_thisActor.i_quest3 = 0;
		addTimer(TIME_FOR_ANAIS_INFO, 3000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIME_FOR_ANAIS_INFO)
		{
		}
		broadcastScriptEvent(2114008, _thisActor.getStoredId(), null, 2000);
		addTimer(TIME_FOR_ANAIS_INFO, (30 * 1000));
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai0 == 0)
		{
			broadcastScriptEvent(2114006, 1, null, 2000);
			_thisActor.i_ai0 = 1;
		}
		else if(_thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.750000 && _thisActor.i_ai0 == 1)
		{
			broadcastScriptEvent(2114006, 2, null, 2000);
			_thisActor.i_ai0 = 2;
		}
		else if(_thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.500000 && _thisActor.i_ai0 == 2)
		{
			broadcastScriptEvent(2114006, 3, null, 2000);
			_thisActor.i_ai0 = 3;
		}
		else if(_thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.250000 && _thisActor.i_ai0 == 3)
		{
			broadcastScriptEvent(2114006, 4, null, 2000);
			_thisActor.i_ai0 = 4;
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140010)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();

			if(h0 != null)
			{
				L2Character c1 = h0.getAttacker();
				if(c1 != null)
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 2114009, c1.getStoredId(), null);
				}
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		broadcastScriptEvent(2114007, 1, null, 3000);
		super.onEvtDead(killer);
	}
}