package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 23.09.11 19:29
 */
public class FreyaDefeatdun extends DefaultAI
{
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(411435009);
	public int Skill01_Prob = 1500;
	public L2Skill Eternal_Blizzard = SkillTable.getInstance().getInfo(411303937);
	public int TIMER_SCENE_21 = 2314507;
	public int TIMER_SCENE_21_END = 2314517;
	public int scene_num_21 = 21;
	public int TIMER_moving = 2314303;
	public int TIMER_Blizzard = 2314304;
	public int debug_mode = 0;

	public FreyaDefeatdun(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.createOnePrivate(18919, "AiIcequeenEntranceDefeated", 0, 0, 114394, -112383, -11200, 0, 0, 0, 0);
		addTimer(TIMER_moving, 60000);
		addTimer(TIMER_Blizzard, 3 * 60000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_ai0 == 0)
		{
			addAttackDesire(attacker, 1, damage);
			if(attacker.isPlayer())
			{
				broadcastScriptEvent(23140043, getStoredIdFromCreature(_thisActor), null, 1500);
			}
			if(Skill01_ID != null)
			{
				if(Rnd.get(10000) < Skill01_Prob)
				{
					if(Rnd.get(2) == 1)
					{
						if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
						}
					}
					else if(_thisActor.getMostHated() != null)
					{
						if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							addUseSkillDesire(_thisActor.getMostHated(), Skill01_ID, 0, 1, 1000000);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai0 == 0)
		{
			if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()) || CategoryManager.isInCategory(123, creature.getNpcId()))
			{
				addAttackDesire(creature, 200, 0);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_SCENE_21)
		{

			Functions.startScenePlayerAround(_thisActor, 21, 4000, 1000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 231400001, 0, 0);
			_thisActor.onDecay();
		}
		else if(timerId == TIMER_moving)
		{
			addMoveToDesire(114730, -114805, -11200, 50);
		}
		else if(timerId == TIMER_Blizzard)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1801125);
			_thisActor.stopMove();
			clearTasks();
			_thisActor.setIsInvul(true);
			_thisActor.i_ai0 = 1;
			addUseSkillDesire(_thisActor, Eternal_Blizzard, 0, 1, 1000000000);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == Eternal_Blizzard)
		{
			broadcastScriptEvent(23140020, 0, null, 4000);
			addTimer(TIMER_SCENE_21, 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140101)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.l_ai5 = (Long) arg1;
			}
		}
		else if(eventId == 23140043)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 50);
			}
		}
		else if(eventId == 23140022)
		{
			if((Long) arg1 != 0)
			{
				_thisActor.c_ai0 = (Long) arg1;
			}
		}
	}
}
