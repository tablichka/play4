package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 27.09.11 23:21
 */
public class IcequeenStone extends DefaultNpc
{
	public L2Skill Buff = null;
	public String MAKER_summoner = "schuttgart29_2314_05m1";
	public String MAKER_summoner_hard = "schuttgart29_2314_hd_05m1";
	public int TIMER_suicide = 2314004;
	public int TIMER_state_chg = 2314008;
	public int debug_mode = 0;
	public int Dispel_Debuff = 1;
	public L2Skill skill1 = SkillTable.getInstance().getInfo(412942338);
	public L2Skill skill2 = SkillTable.getInstance().getInfo(412942337);

	public IcequeenStone(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.changeNpcState(1);
		addTimer(TIMER_state_chg, 1400);
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_ai0 == 0)
		{
			if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() / 10.000000)
			{
				if(_thisActor.param1 == 1)
				{
					if(skill1.getMpConsume() < _thisActor.getCurrentMp() && skill1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill1.getId()))
					{
						addUseSkillDesire(_thisActor, skill1, 1, 1, 1000000);
					}
				}
				else if(skill2.getMpConsume() < _thisActor.getCurrentMp() && skill2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill2.getId()))
				{
					addUseSkillDesire(_thisActor, skill2, 1, 1, 1000000);
				}
				_thisActor.i_ai0 = 1;
			}
		}
		_thisActor.c_ai0 = attacker.getStoredId();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(_thisActor.i_ai0 == 1)
		{
			if(skill == skill2 || skill == skill1)
			{
				DefaultMaker maker0 = null;
				if(_thisActor.param1 == 1)
				{
					maker0 = _thisActor.getInstanceZone().getMaker(MAKER_summoner_hard);
				}
				else if(_thisActor.param1 == 0)
				{
					maker0 = _thisActor.getInstanceZone().getMaker(MAKER_summoner);
				}
				if(maker0 != null)
				{
					if(Rnd.get(100) < 75)
					{
						L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
						if(c0 != null)
						{
							maker0.onScriptEvent(23140012, getStoredIdFromCreature(_thisActor), _thisActor.c_ai0);
						}
						else
						{
							maker0.onScriptEvent(23140012, getStoredIdFromCreature(_thisActor), 0);
						}
					}
				}
				addTimer(TIMER_suicide, 1000);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_suicide)
		{
			_thisActor.changeNpcState(2);
			_thisActor.changeNpcState(3);
			_thisActor.doDie(null);
		}
		else if(timerId == TIMER_state_chg)
		{
			_thisActor.changeNpcState(2);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			_thisActor.changeNpcState(3);
			addTimer(TIMER_suicide, 1000);
		}
		else if(eventId == 23140051)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140052, getStoredIdFromCreature(_thisActor), 0);
			}
		}
		else if(eventId == 23140048)
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		DefaultMaker maker0 = _thisActor.getMyMaker();
		if(maker0 != null)
		{
			maker0.onScriptEvent(23140040, 0, 0);
		}
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
			{
				effect.exit();
			}
		}
	}
}
