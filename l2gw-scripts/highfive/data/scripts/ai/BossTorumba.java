package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 12.12.11 18:47
 */
public class BossTorumba extends DefaultNpc
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(419627009);
	public L2Skill SpecialSkill02_ID = SkillTable.getInstance().getInfo(419692545);
	public L2Skill SpecialSkill03_ID = SkillTable.getInstance().getInfo(419758081);
	public L2Skill SpecialSkill04_ID = SkillTable.getInstance().getInfo(458752001);
	public int max_desire = 10000000;
	public int TARGET_CHECK_TIMER = 1111;
	public int TIME_EXPIRED_TIMER = 1112;
	public int HURRY_UP_TIMER = 1113;
	public int SWING_SKILL_TIMER = 1114;
	public int BULLET_SKILL_TIMER = 1115;
	public int TRR_CHECK_TIMER = 1116;
	public int bomona_x = -174654;
	public int bomona_y = 184277;
	public int bomona_z = -15408;
	public String my_maker = "";

	public BossTorumba(L2Character actor)
	{
		super(actor);
		Dispel_Debuff = 1;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai1 = 0;
		_thisActor.createOnePrivate(18845, "TorumbaHelper", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, getStoredIdFromCreature(_thisActor));
		_thisActor.createOnePrivate(32739, "Bomona", 0, 0, bomona_x, bomona_y, bomona_z, 0, 0, 0, 0);
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		addTimer(TRR_CHECK_TIMER, 5000);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null && caster.isPlayer() && caster.getPlayer().getTransformation() != 126)
		{
			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091020, getStoredIdFromCreature(caster), null);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() && attacker.getPlayer().getTransformation() != 126)
		{
			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091020, getStoredIdFromCreature(attacker), null);
				return;
			}
		}

		if(_thisActor.param1 == 0)
		{
			L2Party party0 = Util.getParty(attacker);
			if(party0 != null)
			{
				addTimer(HURRY_UP_TIMER, 9 * 60000);
				addTimer(SWING_SKILL_TIMER, 10000);
				addTimer(TARGET_CHECK_TIMER, 7000);
				_thisActor.param1 = party0.getPartyId();
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801131);
				_thisActor.i_ai3 = (int) (System.currentTimeMillis() / 1000);
				_thisActor.c_ai1 = attacker.getStoredId();
			}
			else
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091020, getStoredIdFromCreature(attacker), null);
				return;
			}
		}
		else if(_thisActor.param1 != 0)
		{
			L2Party party0 = Util.getParty(attacker);
			if(party0 != null)
			{
				if(_thisActor.param1 != party0.getPartyId())
				{
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091020, getStoredIdFromCreature(attacker), null);
					return;
				}
			}
			else
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091020, getStoredIdFromCreature(attacker), null);
				return;
			}
		}

		if(skill != null && skill.getId() == 968)
		{
			removeAllAttackDesire();
			addAttackDesire(attacker, 1, DEFAULT_DESIRE);

			if((int) (System.currentTimeMillis() / 1000) - _thisActor.i_ai3 < 8)
			{
				_thisActor.i_ai2++;

				if(_thisActor.i_ai2 == 10)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801132);
					_thisActor.changeNpcState(1);
				}
				else if(_thisActor.i_ai2 == 20)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801133);
					_thisActor.changeNpcState(2);
				}
				else if(_thisActor.i_ai2 == 30)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801134);
					_thisActor.changeNpcState(3);
				}
				else if(_thisActor.i_ai2 == 40)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801135);
					_thisActor.changeNpcState(4);
				}
				else if(_thisActor.i_ai2 == 50)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801136);
					_thisActor.changeNpcState(5);
				}
				else if(_thisActor.i_ai2 == 60)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801137);
					_thisActor.changeNpcState(6);
				}
				else if(_thisActor.i_ai2 == 70)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801138);
					_thisActor.changeNpcState(7);
				}
				else if(_thisActor.i_ai2 == 80)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801139);
					_thisActor.changeNpcState(8);
				}
				else if(_thisActor.i_ai2 == 90)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801140);
					_thisActor.changeNpcState(9);
				}
				else if(_thisActor.i_ai2 == 100)
				{
					_thisActor.doDie(null);
					_thisActor.changeNpcState(10);
				}
			}
			else
			{
				_thisActor.i_ai2 = 1;
				_thisActor.changeNpcState(0);
			}
			_thisActor.i_ai3 = (int) (System.currentTimeMillis() / 1000);
		}
		else
		{
			addAttackDesire(attacker, 1, 1);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character caster = _thisActor.getCastingTarget();
		if(skill == SpecialSkill02_ID || skill == SpecialSkill01_ID)
		{
			_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091018, getStoredIdFromCreature(caster), null);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20091017)
		{
			_thisActor.c_ai0 = L2ObjectsStorage.getAsCharacter((Long) arg1).getStoredId();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == HURRY_UP_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801141);
			addTimer(TIME_EXPIRED_TIMER, 60 * 1000);
		}
		else if(timerId == TIME_EXPIRED_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801142);
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(20091019, getStoredIdFromCreature(_thisActor), 0);
			}
		}
		else if(timerId == SWING_SKILL_TIMER)
		{
			addUseSkillDesire(_thisActor, SpecialSkill02_ID, 0, 1, max_desire);
			addTimer(SWING_SKILL_TIMER, 7 * 1000);
		}
		else if(timerId == TARGET_CHECK_TIMER)
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null)
			{
				if(_thisActor.c_ai1 == c0.getStoredId())
				{
					addUseSkillDesire(c0, SpecialSkill01_ID, 0, 1, max_desire);
				}
				_thisActor.c_ai1 = c0.getStoredId();
			}
			addTimer(TARGET_CHECK_TIMER, 6 * 1000);
		}
		else if(timerId == TRR_CHECK_TIMER)
		{
			if(_thisActor.param1 != 0)
			{
				if(!_thisActor.inMyTerritory(_thisActor))
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801143);
					addTimer(TIME_EXPIRED_TIMER, 4 * 1000);
				}
			}
			addTimer(TRR_CHECK_TIMER, 5 * 1000);
		}
	}
}