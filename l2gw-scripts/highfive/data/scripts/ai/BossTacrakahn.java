package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 12.12.11 18:26
 */
public class BossTacrakahn extends CombatMonster
{
	public int TOTEM_TIMER = 3334;
	public int CHECK_TIMER = 3112;
	public int TIME_EXPIRED_TIMER = 3113;
	public int HURRY_UP_TIMER = 3114;
	public int ROAR_TIMER = 3115;
	public int BUFF_TIMER = 3116;
	public int PRIVATE_SPAWN_TIMER = 3117;
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(418119681);
	public L2Skill SpecialSkill02_ID = SkillTable.getInstance().getInfo(418185218);
	public L2Skill CurseOfTacrakhan = SkillTable.getInstance().getInfo(435814401);
	public L2Skill PowerUpSkill01_ID = SkillTable.getInstance().getInfo(417595393);
	public L2Skill PowerUpSkill02_ID = SkillTable.getInstance().getInfo(417595394);
	public L2Skill PowerUpSkill03_ID = SkillTable.getInstance().getInfo(417595395);
	public L2Skill PowerUpSkill04_ID = SkillTable.getInstance().getInfo(417595396);
	public L2Skill PowerUpSkill05_ID = SkillTable.getInstance().getInfo(417595397);
	public L2Skill PowerUpSkill06_ID = SkillTable.getInstance().getInfo(417595398);
	public L2Skill PowerUpSkill07_ID = SkillTable.getInstance().getInfo(417595399);
	public L2Skill PowerUpSkill08_ID = SkillTable.getInstance().getInfo(417595400);
	public L2Skill PowerUpSkill09_ID = SkillTable.getInstance().getInfo(417595401);
	public L2Skill PowerUpSkill10_ID = SkillTable.getInstance().getInfo(417595402);
	public String victim_spawn_maker = "kadif02_1423_raidm2";
	public int max_desire = 10000000;
	public int Totem_Count = 5;
	public L2Skill TotemSkill = SkillTable.getInstance().getInfo(417464321);

	public BossTacrakahn(L2Character actor)
	{
		super(actor);
		Dispel_Debuff = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(418054145);
		Skill01_Probability = 20;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(417988609);
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
		addTimer(CHECK_TIMER, 5000);
		_thisActor.createOnePrivate(18845, "ASeedBossHelper", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, getStoredIdFromCreature(_thisActor));
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_thisActor.param1 == 0)
		{
			L2Party party0 = Util.getParty(caster);
			if(party0 == null)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(caster), null);
			}
		}
		else if(_thisActor.param1 != 0)
		{
			L2Party party0 = Util.getParty(caster);
			if(party0 != null)
			{
				if(_thisActor.param1 != party0.getPartyId())
				{
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(caster), null);
				}
			}
			else
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(caster), null);
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
				addTimer(BUFF_TIMER, 100000);
				addTimer(PRIVATE_SPAWN_TIMER, 30000);
				_thisActor.i_ai0 = 1;
				_thisActor.param1 = party0.getPartyId();
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801126);
				_thisActor.addDamage(attacker, damage);
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
			else
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(attacker), null);
			}
		}
		else if(_thisActor.param1 != 0)
		{
			L2Party party0 = Util.getParty(attacker);
			if(party0 != null)
			{
				if(_thisActor.param1 != party0.getPartyId())
				{
					_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(attacker), null);
				}
			}
			else
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 20091021, getStoredIdFromCreature(attacker), null);
			}

			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
			}

			if(attacker.isPlayer())
			{
				_thisActor.addDamage(attacker, damage);
				addAttackDesire(attacker, 0, DEFAULT_DESIRE);
			}
			else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
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
		if(skill == TotemSkill)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801127);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TOTEM_TIMER)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.isDead() && _thisActor.param1 != 0)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(victim_spawn_maker);
				if(maker0 != null)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801128);
					maker0.onScriptEvent(5, getStoredIdFromCreature(_thisActor), Totem_Count);
				}
			}
			addTimer(TOTEM_TIMER, 60000);
		}
		else if(timerId == CHECK_TIMER)
		{
			if(_thisActor.param1 != 0)
			{
				if(!_thisActor.inMyTerritory(_thisActor))
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801129, "", "", "", "", "");
					addTimer(TIME_EXPIRED_TIMER, 5 * 1000);
				}
			}
			addTimer(CHECK_TIMER, 5000);
		}
		else if(timerId == HURRY_UP_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801129);
			addTimer(TIME_EXPIRED_TIMER, 60000);
		}
		else if(timerId == TIME_EXPIRED_TIMER)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 7, 0, 0, 0, 0, 1, 4000, 0, 1801130);
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(20091019, 0, 0);
			}
		}
		else if(timerId == ROAR_TIMER)
		{
			addUseSkillDesire(_thisActor, SpecialSkill01_ID, 0, 1, max_desire);
			addTimer(ROAR_TIMER, 40 * 1000);
		}
		else if(timerId == BUFF_TIMER)
		{
			if(_thisActor.i_ai1 == 1)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill01_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 2)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill02_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 3)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill03_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 4)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill04_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 5)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill05_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 6)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill06_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 7)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill07_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 8)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill08_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 9)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill09_ID, 0, 1, max_desire * max_desire);
			}
			else if(_thisActor.i_ai1 == 10)
			{
				addUseSkillDesire(_thisActor, PowerUpSkill10_ID, 0, 1, max_desire * max_desire);
			}
			if(_thisActor.i_ai1 < 10)
			{
				_thisActor.i_ai1++;
			}
			addTimer(BUFF_TIMER, 100 * 1000);
		}
		else if(timerId == PRIVATE_SPAWN_TIMER)
		{
			if(_thisActor.isDead())
			{
				int i1 = Rnd.get(3);
				switch(i1)
				{
					case 0:
						_thisActor.createOnePrivate(22747, "Brakian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(22748, "Groykhan", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(22749, "Traikhan", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
				}
			}
			addTimer(PRIVATE_SPAWN_TIMER, 30000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20091017)
		{
			_thisActor.c_ai0 = L2ObjectsStorage.getAsCharacter((Long) arg1).getStoredId();
		}
	}
}