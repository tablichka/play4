package ai;

import ai.base.WarriorUseBow;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.09.11 1:31
 */
public class AiTantaarLizardArcher extends WarriorUseBow
{
	public long Max_Desire = 1000000000000000000L;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(458752001);
	public L2Skill Skill02_ID = SkillTable.getInstance().getInfo(458752001);
	public int TID_SKILL_COOLTIME = 780001;
	public int TIME_SKILL_COOLTIME = 2;

	public AiTantaarLizardArcher(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(TID_SKILL_COOLTIME, ( TIME_SKILL_COOLTIME * 1000 ));
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		super.onEvtAttacked(attacker, damage, skill);

		if( attacker != null )
		{
			_thisActor.c_ai0 = attacker.getStoredId();
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		super.onEvtSpelled(skill, caster);
		if( skill.getId() == 6427 )
		{
			if( debug )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "s_lizard_grasslands_fungus1 hit");
			}
			addUseSkillDesire(_thisActor, 433979393, 0, 1, Max_Desire);
		}
		if( debug )
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "SPELLED:" + skill.getId());
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == TID_SKILL_COOLTIME )
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if( c0 != null )
			{
				if( c0.getAbnormalLevelByType(101) == 0 && Rnd.get(100) >= 85 )
				{
					addUseSkillDesire(c0, Skill01_ID, 0, 1, Max_Desire);
				}
				else
				{
					addUseSkillDesire(c0, Skill02_ID, 0, 1, Max_Desire);
				}
			}
			addTimer(TID_SKILL_COOLTIME, TIME_SKILL_COOLTIME * 1000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if( c0 != null )
		{
			_thisActor.createOnePrivate(18919, "AiAuragrafter", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
		if( Rnd.get(1000) == 0 && _thisActor.getNpcId() != 18862 )
		{
			_thisActor.createOnePrivate(18862, "AiTantaarLizardWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
	}

}
