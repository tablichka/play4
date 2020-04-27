package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author: rage
 * @date: 03.09.11 0:07
 */
public class WarriorUseSkill extends WarriorBehavior
{
	public L2Skill Skill01_ID = null;
	public int Skill01_Probablity = 3333;
	public int Skill01_Target = 0;
	public int Skill01_Type = 0;
	public int Skill01_AttackSplash = 0;
	public float Skill01_Desire = 1000000000.000000f;
	public int Skill01_Check_Dist = 0;
	public int Skill01_Dist_Min = 0;
	public int Skill01_Dist_Max = 2000;
	public int Skill01_HPTarget = 0;
	public int Skill01_HighHP = 100;
	public int Skill01_LowHP = 0;
	public int Skill01_FString = 0;
	public int Skill01_FStringRate = 0;

	public L2Skill Skill02_ID = null;
	public int Skill02_Probablity = 3333;
	public int Skill02_Target = 0;
	public int Skill02_Type = 0;
	public int Skill02_AttackSplash = 0;
	public float Skill02_Desire = 1000000000.000000f;
	public int Skill02_Check_Dist = 0;
	public int Skill02_Dist_Min = 0;
	public int Skill02_Dist_Max = 2000;
	public int Skill02_HPTarget = 0;
	public int Skill02_HighHP = 100;
	public int Skill02_LowHP = 0;
	public int Skill02_FString = 0;
	public int Skill02_FStringRate = 0;

	public L2Skill Skill03_ID = null;
	public int Skill03_Probablity = 3333;
	public int Skill03_Target = 0;
	public int Skill03_Type = 0;
	public int Skill03_AttackSplash = 0;
	public float Skill03_Desire = 1000000000.000000f;
	public int Skill03_Check_Dist = 0;
	public int Skill03_Dist_Min = 0;
	public int Skill03_Dist_Max = 2000;
	public int Skill03_HPTarget = 0;
	public int Skill03_HighHP = 100;
	public int Skill03_LowHP = 0;
	public int Skill03_FString = 0;
	public int Skill03_FStringRate = 0;

	public L2Skill Skill04_ID = null;
	public int Skill04_Probablity = 3333;
	public int Skill04_Target = 0;
	public int Skill04_Type = 0;
	public int Skill04_AttackSplash = 0;
	public float Skill04_Desire = 1000000000.000000f;
	public int Skill04_Check_Dist = 0;
	public int Skill04_Dist_Min = 0;
	public int Skill04_Dist_Max = 2000;
	public int Skill04_HPTarget = 0;
	public int Skill04_HighHP = 100;
	public int Skill04_LowHP = 0;
	public int Skill04_FString = 0;
	public int Skill04_FStringRate = 0;

	public L2Skill Skill05_ID = null;
	public int Skill05_Probablity = 3333;
	public int Skill05_Target = 0;
	public int Skill05_Type = 0;
	public int Skill05_AttackSplash = 0;
	public float Skill05_Desire = 1000000000.000000f;
	public int Skill05_Check_Dist = 0;
	public int Skill05_Dist_Min = 0;
	public int Skill05_Dist_Max = 2000;
	public int Skill05_HPTarget = 0;
	public int Skill05_HighHP = 100;
	public int Skill05_LowHP = 0;
	public int Skill05_FString = 0;
	public int Skill05_FStringRate = 0;

	public L2Skill Skill06_ID = null;
	public int Skill06_Probablity = 3333;
	public int Skill06_Target = 0;
	public int Skill06_Type = 0;
	public int Skill06_AttackSplash = 0;
	public float Skill06_Desire = 1000000000.000000f;
	public int Skill06_Check_Dist = 0;
	public int Skill06_Dist_Min = 0;
	public int Skill06_Dist_Max = 2000;
	public int Skill06_HPTarget = 0;
	public int Skill06_HighHP = 100;
	public int Skill06_LowHP = 0;
	public int Skill06_FString = 0;
	public int Skill06_FStringRate = 0;

	public WarriorUseSkill(L2Character actor)
	{
		super(actor);
	}

	protected boolean createNewTask()
	{
		L2Character top_desire_target = getAttackTarget();

		// Новая цель исходя из агрессивности
		L2Character hated = _thisActor.isConfused() ? top_desire_target : _thisActor.getMostHated();

		if(hated != null && hated != _thisActor)
			top_desire_target = hated;
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			top_desire_target = null;
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		if(Skill01_ID != null && Skill01_Probablity > 0)
		{
			if(Skill01_Target == 0 || Skill01_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill01_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill01_Probablity && Skill01_Desire > 0
						&& ((Skill01_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill01_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill01_LowHP)
						|| (Skill01_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill01_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill01_LowHP))
						&& (Skill01_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill01_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill01_Dist_Max))
						&& Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
				{
					if(Skill01_Type == 0)
						addUseSkillDesire(top_desire_target, Skill01_ID, 0, MovingAttack, (long) Skill01_Desire);
					else if(Skill01_Type == 1)
						addUseSkillDesire(top_desire_target, Skill01_ID, 1, MovingAttack, (long) Skill01_Desire);
					else if(Skill01_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill01_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill01_ID, 0, MovingAttack, (long) Skill01_Desire);
					else if(Skill01_Type == 3)
						addUseSkillDesire(top_desire_target, Skill01_ID, 1, MovingAttack, (long) Skill01_Desire);
				}
			}
			else if(Skill01_Target == 2 || Skill01_Target == 3)
			{
				if(!Skill01_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill01_Probablity && Skill01_Desire > 0
						&& (Skill01_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill01_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill01_LowHP)
						&& (Skill01_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill01_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill01_Dist_Max))
						&& Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
				{
					if(Skill01_Type == 0 || Skill01_Type == 3)
						addUseSkillDesire(_thisActor, Skill01_ID, 1, MovingAttack, (long) Skill01_Desire);
					else if((Skill01_Type == 1 || Skill01_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill01_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill01_ID, 1, MovingAttack, (long) Skill01_Desire);
				}
			}
		}
		if(Skill02_ID != null && Skill02_Probablity > 0)
		{
			if(Skill02_Target == 0 || Skill02_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill02_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill02_Probablity && Skill02_Desire > 0
						&& ((Skill02_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill02_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill02_LowHP)
						|| (Skill02_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill02_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill02_LowHP))
						&& (Skill02_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill02_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill02_Dist_Max))
						&& Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
				{
					if(Skill02_Type == 0)
						addUseSkillDesire(top_desire_target, Skill02_ID, 0, MovingAttack, (long) Skill02_Desire);
					else if(Skill02_Type == 1)
						addUseSkillDesire(top_desire_target, Skill02_ID, 1, MovingAttack, (long) Skill02_Desire);
					else if(Skill02_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill02_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill02_ID, 0, MovingAttack, (long) Skill02_Desire);
					else if(Skill02_Type == 3)
						addUseSkillDesire(top_desire_target, Skill02_ID, 1, MovingAttack, (long) Skill02_Desire);
				}
			}
			else if(Skill02_Target == 2 || Skill02_Target == 3)
			{
				if(!Skill02_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill02_Probablity && Skill02_Desire > 0
						&& (Skill02_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill02_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill02_LowHP)
						&& (Skill02_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill02_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill02_Dist_Max))
						&& Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
				{
					if(Skill02_Type == 0 || Skill02_Type == 3)
						addUseSkillDesire(_thisActor, Skill02_ID, 1, MovingAttack, (long) Skill02_Desire);
					else if((Skill02_Type == 1 || Skill02_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill02_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill02_ID, 1, MovingAttack, (long) Skill02_Desire);
				}
			}
		}
		if(Skill03_ID != null && Skill03_Probablity > 0)
		{
			if(Skill03_Target == 0 || Skill03_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill03_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill03_Probablity && Skill03_Desire > 0
						&& ((Skill03_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill03_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill03_LowHP)
						|| (Skill03_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill03_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill03_LowHP))
						&& (Skill03_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill03_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill03_Dist_Max))
						&& Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
				{
					if(Skill03_Type == 0)
						addUseSkillDesire(top_desire_target, Skill03_ID, 0, MovingAttack, (long) Skill03_Desire);
					else if(Skill03_Type == 1)
						addUseSkillDesire(top_desire_target, Skill03_ID, 1, MovingAttack, (long) Skill03_Desire);
					else if(Skill03_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill03_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill03_ID, 0, MovingAttack, (long) Skill03_Desire);
					else if(Skill03_Type == 3)
						addUseSkillDesire(top_desire_target, Skill03_ID, 1, MovingAttack, (long) Skill03_Desire);
				}
			}
			else if(Skill03_Target == 2 || Skill03_Target == 3)
			{
				if(!Skill03_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill03_Probablity && Skill03_Desire > 0
						&& (Skill03_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill03_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill03_LowHP)
						&& (Skill03_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill03_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill03_Dist_Max))
						&& Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
				{
					if(Skill03_Type == 0 || Skill03_Type == 3)
						addUseSkillDesire(_thisActor, Skill03_ID, 1, MovingAttack, (long) Skill03_Desire);
					else if((Skill03_Type == 1 || Skill03_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill03_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill03_ID, 1, MovingAttack, (long) Skill03_Desire);
				}
			}
		}
		if(Skill04_ID != null && Skill04_Probablity > 0)
		{
			if(Skill04_Target == 0 || Skill04_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill04_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill04_Probablity && Skill04_Desire > 0
						&& ((Skill04_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill04_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill04_LowHP)
						|| (Skill04_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill04_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill04_LowHP))
						&& (Skill04_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill04_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill04_Dist_Max))
						&& Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID.getId()))
				{
					if(Skill04_Type == 0)
						addUseSkillDesire(top_desire_target, Skill04_ID, 0, MovingAttack, (long) Skill04_Desire);
					else if(Skill04_Type == 1)
						addUseSkillDesire(top_desire_target, Skill04_ID, 1, MovingAttack, (long) Skill04_Desire);
					else if(Skill04_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill04_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill04_ID, 0, MovingAttack, (long) Skill04_Desire);
					else if(Skill04_Type == 3)
						addUseSkillDesire(top_desire_target, Skill04_ID, 1, MovingAttack, (long) Skill04_Desire);
				}
			}
			else if(Skill04_Target == 2 || Skill04_Target == 3)
			{
				if(!Skill04_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill04_Probablity && Skill04_Desire > 0
						&& (Skill04_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill04_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill04_LowHP)
						&& (Skill04_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill04_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill04_Dist_Max))
						&& Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID.getId()))
				{
					if(Skill04_Type == 0 || Skill04_Type == 3)
						addUseSkillDesire(_thisActor, Skill04_ID, 1, MovingAttack, (long) Skill04_Desire);
					else if((Skill04_Type == 1 || Skill04_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill04_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill04_ID, 1, MovingAttack, (long) Skill04_Desire);
				}
			}
		}
		if(Skill05_ID != null && Skill05_Probablity > 0)
		{
			if(Skill05_Target == 0 || Skill05_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill05_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill05_Probablity && Skill05_Desire > 0
						&& ((Skill05_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill05_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill05_LowHP)
						|| (Skill05_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill05_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill05_LowHP))
						&& (Skill05_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill05_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill05_Dist_Max))
						&& Skill05_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill05_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill05_ID.getId()))
				{
					if(Skill05_Type == 0)
						addUseSkillDesire(top_desire_target, Skill05_ID, 0, MovingAttack, (long) Skill05_Desire);
					else if(Skill05_Type == 1)
						addUseSkillDesire(top_desire_target, Skill05_ID, 1, MovingAttack, (long) Skill05_Desire);
					else if(Skill05_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill05_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill05_ID, 0, MovingAttack, (long) Skill05_Desire);
					else if(Skill05_Type == 3)
						addUseSkillDesire(top_desire_target, Skill05_ID, 1, MovingAttack, (long) Skill05_Desire);
				}
			}
			else if(Skill05_Target == 2 || Skill05_Target == 3)
			{
				if(!Skill05_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill05_Probablity && Skill05_Desire > 0
						&& (Skill05_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill05_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill05_LowHP)
						&& (Skill05_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill05_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill05_Dist_Max))
						&& Skill05_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill05_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill05_ID.getId()))
				{
					if(Skill05_Type == 0 || Skill05_Type == 3)
						addUseSkillDesire(_thisActor, Skill05_ID, 1, MovingAttack, (long) Skill05_Desire);
					else if((Skill05_Type == 1 || Skill05_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill05_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill05_ID, 1, MovingAttack, (long) Skill05_Desire);
				}
			}
		}
		if(Skill06_ID != null && Skill06_Probablity > 0)
		{
			if(Skill06_Target == 0 || Skill06_Target == 1)
			{
				if(top_desire_target != null && top_desire_target.isPlayable()
						&& !Skill06_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill06_Probablity && Skill06_Desire > 0
						&& ((Skill06_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill06_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill06_LowHP)
						|| (Skill06_HPTarget == 1 && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 <= Skill06_HighHP && top_desire_target.getCurrentHp() / top_desire_target.getMaxHp() * 100 > Skill06_LowHP))
						&& (Skill06_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill06_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill06_Dist_Max))
						&& Skill06_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill06_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill06_ID.getId()))
				{
					if(Skill06_Type == 0)
						addUseSkillDesire(top_desire_target, Skill06_ID, 0, MovingAttack, (long) Skill06_Desire);
					else if(Skill06_Type == 1)
						addUseSkillDesire(top_desire_target, Skill06_ID, 1, MovingAttack, (long) Skill06_Desire);
					else if(Skill06_Type == 2 && _thisActor.getAbnormalLevelBySkill(Skill06_ID) <= 0)
						addUseSkillDesire(top_desire_target, Skill06_ID, 0, MovingAttack, (long) Skill06_Desire);
					else if(Skill06_Type == 3)
						addUseSkillDesire(top_desire_target, Skill06_ID, 1, MovingAttack, (long) Skill06_Desire);
				}
			}
			else if(Skill06_Target == 2 || Skill06_Target == 3)
			{
				if(!Skill06_ID.isMuted(_thisActor) && Rnd.get(10000) < Skill06_Probablity && Skill06_Desire > 0
						&& (Skill06_HPTarget == 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 <= Skill06_HighHP && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > Skill06_LowHP)
						&& (Skill06_Check_Dist == 0 || !_thisActor.isInRange(top_desire_target, Skill06_Dist_Min) && _thisActor.isInRange(top_desire_target, Skill06_Dist_Max))
						&& Skill06_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill06_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill06_ID.getId()))
				{
					if(Skill06_Type == 0 || Skill06_Type == 3)
						addUseSkillDesire(_thisActor, Skill06_ID, 1, MovingAttack, (long) Skill06_Desire);
					else if((Skill06_Type == 1 || Skill06_Type == 2) && _thisActor.getAbnormalLevelBySkill(Skill06_ID) <= 0)
						addUseSkillDesire(_thisActor, Skill06_ID, 1, MovingAttack, (long) Skill06_Desire);
				}
			}
		}

		addAttackDesire(top_desire_target, 0, DEFAULT_DESIRE);
		return true;
	}
}
