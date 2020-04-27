package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 02.12.2010 17:20:14
 */
public class CastlePailakaInvaderBasic extends DefaultAI
{
	protected int String_Num1;
	protected int String_Num2;
	protected int String_Num3;
	protected final L2Skill SelfBuff;
	protected final L2Skill DDSkill_01;
	protected final L2Skill DDSkill_02;
	protected int DDSkill_01_Prob = 20;
	protected int DDSkill_02_Prob = 20;
	protected Location pos;
	protected final L2Skill different_level_9_attacked;
	protected int i_ai0 = 0;
	protected int i_ai1 = 0;

	protected static final int NPC_ANNOUNCE = 2117001;
	protected static final int INVADER = 2117006;
	protected static final int FINAL_BOSS_KILLED = 2117009;

	public CastlePailakaInvaderBasic(L2Character actor)
	{
		super(actor);
		SelfBuff = _selfbuff_skills.length > 0 ? _selfbuff_skills[0] : null;
		DDSkill_01 = _mdam_skills.length > 0 ? _mdam_skills[0] : null;
		DDSkill_02 = _mdam_skills.length > 1 ? _mdam_skills[1] : null;
		different_level_9_attacked = _debuff_skills.length > 0 ? _debuff_skills[0] : null;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		String_Num1 = getInt("String_Num1", -1);
		String_Num2 = getInt("String_Num2", -1);
		String_Num3 = getInt("String_Num3", -1);
		DDSkill_01_Prob = getInt("DDSkill_01_Prob", 20);
		DDSkill_02_Prob = getInt("DDSkill_02_Prob", 20);
		pos = getInt("Pos_X", 0) != 0 && getInt("Pos_Y", 0) != 0 && getInt("Pos_Z", 0) != 0 ? new Location(getInt("Pos_X"), getInt("Pos_Y"), getInt("Pos_Z")) : null;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.getLevel() > _thisActor.getLevel() + 8)
		{
			if(different_level_9_attacked != null && attacker.getEffectBySkill(different_level_9_attacked) == null)
				_thisActor.altUseSkill(different_level_9_attacked, attacker);
		}
		else
		{
			i_ai0 = 1;
			_thisActor.addDamage(attacker, damage);
			_thisActor.callFriends(attacker, damage > 0 ? damage : 100);
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		i_ai0 = 1;
		if(different_level_9_attacked != null && caster.getLevel() > _thisActor.getLevel() + 8)
		{
			if(caster.getEffectBySkill(different_level_9_attacked) == null)
				_thisActor.altUseSkill(different_level_9_attacked, caster);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == NPC_ANNOUNCE)
		{
			L2Character cha = (L2Character) arg1;
			if(_thisActor.isInRange(cha, 700))
				_thisActor.addDamageHate(cha, 0, Rnd.get(1000));
			else
				_thisActor.addDamageHate(cha, 0, Rnd.get(500));
			i_ai1++;
			if(i_ai1 % 4 == 0)
			{
				cha = _thisActor.getMostHated();
				_thisActor.setRunning();
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, cha);
			}

		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
