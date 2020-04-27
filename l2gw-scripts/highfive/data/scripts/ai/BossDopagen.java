package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Util;

import java.lang.ref.WeakReference;

/**
 * @author: rage
 * @date: 12.12.11 17:26
 */
public class BossDopagen extends CombatMonster
{
	public int CHECK_TIMER = 1111;
	public int TOTEM_TIMER = 1112;
	public int TIME_EXPIRED_TIMER = 1113;
	public int HURRY_UP_TIMER = 1114;
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(418906113);
	public L2Skill SpecialSkill02_ID = SkillTable.getInstance().getInfo(417857537);
	public L2Skill SpecialBuff01_ID = SkillTable.getInstance().getInfo(417923073);
	public L2Skill SpecialBuff02_ID = SkillTable.getInstance().getInfo(417923074);
	public L2Skill SpecialBuff03_ID = SkillTable.getInstance().getInfo(417923075);
	public L2Skill SpecialBuff04_ID = SkillTable.getInstance().getInfo(417923076);
	public L2Skill SpecialBuff05_ID = SkillTable.getInstance().getInfo(417923077);
	public L2Skill SpecialBuff06_ID = SkillTable.getInstance().getInfo(417923078);
	public L2Skill SpecialBuff07_ID = SkillTable.getInstance().getInfo(417923079);
	public L2Skill SpecialBuff08_ID = SkillTable.getInstance().getInfo(417923080);
	public L2Skill SpecialBuff09_ID = SkillTable.getInstance().getInfo(417923081);
	public L2Skill SpecialBuff10_ID = SkillTable.getInstance().getInfo(417923082);
	public L2Skill HPMPSKILL01_ID = SkillTable.getInstance().getInfo(417857537);
	public L2Skill HPMPSKILL02_ID = SkillTable.getInstance().getInfo(417857538);
	public L2Skill HPMPSKILL03_ID = SkillTable.getInstance().getInfo(417857539);
	public L2Skill HPMPSKILL04_ID = SkillTable.getInstance().getInfo(417857540);
	public L2Skill HPMPSKILL05_ID = SkillTable.getInstance().getInfo(417857541);
	public L2Skill HPMPSKILL06_ID = SkillTable.getInstance().getInfo(417857542);
	public L2Skill HPMPSKILL07_ID = SkillTable.getInstance().getInfo(417857543);
	public L2Skill HPMPSKILL08_ID = SkillTable.getInstance().getInfo(417857544);
	public L2Skill HPMPSKILL09_ID = SkillTable.getInstance().getInfo(417857545);
	public int max_desire = 10000000;
	public int CurseOfDopagen = 435879937;
	public String totem_spawn_maker = "kadif02_1323_raid2m1";
	public int TotemSkill = 417660929;
	private WeakReference<L2Party> party;

	public BossDopagen(L2Character actor)
	{
		super(actor);
		Dispel_Debuff = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(418775041);
		Skill01_Probability = 15;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(418840577);
		Skill02_Probability = 20;
		Skill02_Target_Type = 1;
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
		_thisActor.i_ai1 = 0;
		party = null;
		addTimer(CHECK_TIMER, 5000);
		_thisActor.createOnePrivate(18845, "ASeedBossHelper", 0, 0, _thisActor.getX(), _thisActor.getY(), (_thisActor.getZ() + 10), 0, 0, 0, getStoredIdFromCreature(_thisActor));
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_thisActor.param1 == 0)
		{
			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(caster), null);
			}
		}
		else if(_thisActor.param1 != 0)
		{
			if(caster != null && caster.getPlayer() != null && caster.getPlayer().getParty() != null)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(caster), null);
			}

			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(caster), null);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.param1 == 0)
		{
			L2Party party0 = Util.getParty(attacker);
			if(party0 != null)
			{
				addTimer(TOTEM_TIMER, 10000);
				addTimer(HURRY_UP_TIMER, 9 * 60000);
				_thisActor.param1 = party0.getPartyId();
				party = new WeakReference<L2Party>(party0);
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801144);
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(totem_spawn_maker);
				_thisActor.i_ai0 = 1;
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else
			{
				if(_thisActor.c_ai0 != 0)
				{
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(attacker), null);
				}
			}
		}
		else if(_thisActor.param1 != 0)
		{
			L2Party party0 = Util.getParty(attacker);
			if(party0 != null)
			{
				if(_thisActor.param1 != party0.getPartyId())
				{
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(attacker), null);
					return;
				}
			}
			else
			{
				if(_thisActor.c_ai0 != 0)
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091022, getStoredIdFromCreature(attacker), null);
				return;
			}

			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
			}

			_thisActor.callFriends(attacker, damage);

			if(attacker.isPlayer())
			{
				_thisActor.addDamage(attacker, damage);
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
			else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker))
			{
				if(!attacker.getPlayer().isDead())
				{
					_thisActor.addDamageHate(attacker.getPlayer(), 0, damage / 2);
					_thisActor.addDamage(attacker, damage);
					addAttackDesire(attacker, 1, DEFAULT_DESIRE);
				}
				else
				{
					_thisActor.addDamage(attacker, damage);
					addAttackDesire(attacker, 1, DEFAULT_DESIRE);
				}
			}
			if(_thisActor.isInRange(attacker, 250))
			{
				if(CategoryManager.isInCategory(70, attacker) || CategoryManager.isInCategory(2, attacker))
				{
					if(Rnd.get(100) < 5)
					{
						addUseSkillDesire(attacker, SpecialSkill02_ID, 0, 1, max_desire * 100);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TOTEM_TIMER)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(totem_spawn_maker);
			if(maker0 != null)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801145);
				maker0.onScriptEvent(1001, getStoredIdFromCreature(_thisActor), 0);
			}
		}
		else if(timerId == CHECK_TIMER)
		{
			if(_thisActor.param1 != 0)
			{
				if(!_thisActor.inMyTerritory(_thisActor))
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801146);
					addTimer(TIME_EXPIRED_TIMER, 60 * 1000);
				}
			}
			broadcastScriptEvent(8, getStoredIdFromCreature(_thisActor), null, 2000);
			addTimer(CHECK_TIMER, 1000);
		}
		else if(timerId == HURRY_UP_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801147);
			addTimer(TIME_EXPIRED_TIMER, 60000);
		}
		else if(timerId == TIME_EXPIRED_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801148);
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(20091019, 0, 0);
			}
			maker0 = SpawnTable.getInstance().getNpcMaker(totem_spawn_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1000, 0, 0);
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(totem_spawn_maker);
		if(maker0 != null)
		{
			maker0.onScriptEvent(1000, 0, 0);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20091017)
		{
			_thisActor.c_ai0 = (Long) arg1;
		}
		else if(eventId == 20091024)
		{
			switch(_thisActor.i_ai1)
			{
				case 0:
					addUseSkillDesire(_thisActor, SpecialBuff01_ID, 0, 1, max_desire * max_desire);
					break;
				case 1:
					addUseSkillDesire(_thisActor, SpecialBuff02_ID, 0, 1, max_desire * max_desire);
					break;
				case 2:
					addUseSkillDesire(_thisActor, SpecialBuff03_ID, 0, 1, max_desire * max_desire);
					break;
				case 3:
					addUseSkillDesire(_thisActor, SpecialBuff04_ID, 0, 1, max_desire * max_desire);
					break;
				case 4:
					addUseSkillDesire(_thisActor, SpecialBuff05_ID, 0, 1, max_desire * max_desire);
					break;
				case 5:
					addUseSkillDesire(_thisActor, SpecialBuff06_ID, 0, 1, max_desire * max_desire);
					break;
				case 6:
					addUseSkillDesire(_thisActor, SpecialBuff07_ID, 0, 1, max_desire * max_desire);
					break;
				case 7:
					addUseSkillDesire(_thisActor, SpecialBuff08_ID, 0, 1, max_desire * max_desire);
					break;
				case 8:
					addUseSkillDesire(_thisActor, SpecialBuff09_ID, 0, 1, max_desire * max_desire);
					break;
				case 9:
					addUseSkillDesire(_thisActor, SpecialBuff10_ID, 0, 1, max_desire * max_desire);
					break;
			}
			if(_thisActor.i_ai1 < 9)
			{
				_thisActor.i_ai1++;
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == null)
			return;

		if(skill == SpecialBuff01_ID || skill == SpecialBuff02_ID || skill == SpecialBuff03_ID || skill == SpecialBuff04_ID || skill == SpecialBuff05_ID || skill == SpecialBuff06_ID || skill == SpecialBuff07_ID || skill == SpecialBuff08_ID || skill == SpecialBuff09_ID || skill == SpecialBuff10_ID)
		{
			L2Party party0 = party != null ? party.get() : null;
			if(party0 != null)

				for(L2Player c0 : party0.getPartyMembers())
				{
					if(c0 != null)
					{
						_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091025, getStoredIdFromCreature(c0), null);
					}
				}
		}
		super.onEvtFinishCasting(skill);
	}
}