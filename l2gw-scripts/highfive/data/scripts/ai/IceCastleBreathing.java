package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 27.09.11 20:56
 */
public class IceCastleBreathing extends DefaultNpc
{
	public L2Skill Skill_Blizzard = null;
	public L2Skill Skill_Suicide = null;
	public int TIMER_blizzard = 2314005;
	public int TIMER_elemental_suicide = 2314006;
	public int TIMER_elemental_killed = 2314007;
	public int TIMER_suicide = 2314050;
	public int debug_mode = 0;
	public int Dispel_Debuff_Prob = 7000;

	public IceCastleBreathing(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.setWalking();
		if(_thisActor.getInstanceZoneId() == 144)
		{
			addTimer(TIMER_blizzard, 10000);
		}
		else
		{
			addTimer(TIMER_blizzard, 20000);
		}
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		if(_thisActor.param1 != 0)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 1000);
			}
		}
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if(_thisActor.getInstanceZoneId() == 139)
			{
				if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
			}
			else if(_thisActor.getInstanceZoneId() == 144)
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

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
		{
			if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() / 20.000000))
			{
				if(_thisActor.i_ai0 == 0)
				{
					_thisActor.i_ai0 = 1;
					addTimer(TIMER_elemental_killed, 1000);
				}
			}
			else
			{
				_thisActor.addDamage(attacker, damage);
				addAttackDesire(attacker, 1, damage * 2);
				if(skill != null)
				{
					if(skill.getAbnormalTypes().contains("aura_of_hate"))
					{
						if(debug_mode > 0)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "HATE");
						}
						addAttackDesire(attacker, 1, damage * 5);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			addAttackDesire(creature, 1, 100);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_blizzard)
		{
			_thisActor.i_ai1++;
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "timer_blizzard " + _thisActor.i_ai1);
			}
			if(_thisActor.i_ai0 == 0)
			{
				if(_thisActor.getInstanceZoneId() == 144)
				{
					if(_thisActor.i_ai1 == 5)
					{
						addTimer(TIMER_elemental_suicide, (10 * 1000));
					}
					else if(Skill_Blizzard.getMpConsume() < _thisActor.getCurrentMp() && Skill_Blizzard.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Blizzard.getId()))
					{
						addUseSkillDesire(_thisActor, Skill_Blizzard, 0, 1, 1000000);
					}
					addTimer(TIMER_blizzard, (10 * 1000));
				}
				else if(_thisActor.i_ai1 == 2)
				{
					addTimer(TIMER_elemental_suicide, (20 * 1000));
				}
				else if(Skill_Blizzard.getMpConsume() < _thisActor.getCurrentMp() && Skill_Blizzard.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Blizzard.getId()))
				{
					addUseSkillDesire(_thisActor, Skill_Blizzard, 0, 1, 1000000);
				}
				addTimer(TIMER_blizzard, (20 * 1000));
			}
		}
		else if(timerId == TIMER_elemental_suicide)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "timer_suicide");
			}
			_thisActor.stopMove();
			clearTasks();
			addUseSkillDesire(_thisActor, Skill_Suicide, 0, 0, 10000000000L);
		}
		else if(timerId == TIMER_elemental_killed)
		{
			if(_thisActor.i_ai0 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "i killed. use suicide");
				}
				_thisActor.stopMove();
				clearTasks();
				addUseSkillDesire(_thisActor, Skill_Suicide, 0, 0, 10000000000L);
			}
		}
		else if(timerId == TIMER_suicide)
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == Skill_Suicide)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "skill_suicide finished");
			}
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			_thisActor.doDie(null);
		}
		else if(eventId == 23140048)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SCE_HOLD_DESIRE_ON");
			}
			clearTasks();
			_thisActor.stopMove();
			_thisActor.onDecay();
			//_thisActor.absolute_defence = 1;
			//_thisActor.no_attack_damage = 1;
		}
		else if(eventId == 23140049)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SCE_HOLD_DESIRE_OFF");
			}
			//_thisActor.absolute_defence = 0;
			//_thisActor.no_attack_damage = 0;
		}
	}
}
