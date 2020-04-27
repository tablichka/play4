package ai.base;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 02.09.11 20:55
 */
public class MonsterBehavior extends MonsterAI
{
	public int IsAggressive = 0;
	public float Aggressive_Time = 7.000000f;
	public int AttackRange = 0;
	public int IsHealer = 0;
	public int MovingAttack = 1;
	public int MoveArounding = 1;
	public int SoulShot = 0;
	public int SoulShotRate = 0;
	public int SpiritShot = 0;
	public int SpiritShotRate = 0;
	public int SpiritShotSpeedBonus = 0;
	public int SpiritShotHealBonus = 0;
	public int LongRangeGuardRate = -1;
	public String SuperPointName = "-1";
	public int SuperPointMethod = 0;
	public int SuperPointDesire = 2000;
	public int FreewayID = -1;
	public int FreewayMethod = 0;
	public int FreewayDesire = 2000;
	public int FieldCycle = -1;
	public int FieldCycle_Condition = 0;
	public int FieldCycle_Quantity = 0;
	public int Threshold_Level_Min = -1;
	public int Threshold_Level_Max = 100;
	public int Threshold_Point_Min = -1;
	public int Threshold_Point_Max = 1000000000;
	public int InzoneRestriction = 0;
	public int InzoneFinish = 0;
	public int PrivateFollowBoss = 1;
	public int SeeCreatureAttackerTime = -1;

	public MonsterBehavior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtOutOfMyTerritory()
	{
		if(OutOfTerritory == 1)
		{
			removeAllAttackDesire();
			addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 10000000);
		}
		else if(OutOfTerritory == 2)
			_thisActor.onDecay();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(ShoutMsg1 == 1 && ShoutMsg1_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg1_FString);
		if(ShoutMsg2 == 1 && ShoutMsg2_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg2_FString);
		if(ShoutMsg3 == 1 && ShoutMsg3_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg3_FString);
		if(ShoutMsg4 == 1 && ShoutMsg4_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg4_FString);
		if(!"-1".equals(SuperPointName) && SuperPointDesire > 0)
			addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
		if(Party_Type == 2)
			_thisActor.spawnMinions();
		/*
		if( FreewayID > -1 && FreewayDesire > 0 )
		{
			_thisActor.AddMoveFreewayDesire(FreewayID, FreewayMethod, FreewayDesire);
		}
		if(MoveAroundSocial > 0 && _thisActor.getCurrentHp() > 0)
			addTimer(1101, Social0_Timer);
		if(MoveAroundSocial1 > 0 && _thisActor.getCurrentHp() > 0)
			addTimer(1102, Social1_Timer);
		if(MoveAroundSocial2 > 0 && _thisActor.getCurrentHp() > 0)
			addTimer(1103, Social2_Timer);
		else if(Party_Type == 1 && PrivateFollowBoss == 1)
		{
			if(_thisActor.isMyBossAlive())
			{
				addTimer(1005, 120000);
				addTimer(1006, 20000);
			}
		}
		*/

		if(AttackRange == 1)
			_thisActor.i_ai4 = 0;
	}

	@Override
	protected void onEvtNoDesire()
	{
		if(!_thisActor.isDead() && !_thisActor.isMoving && SuperPointDesire > 0 && SuperPointName != null && !SuperPointName.isEmpty() && !SuperPointName.equals("-1"))
			addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
	}

	@Override
	protected boolean randomAnimation()
	{
		return (MoveAroundSocial > 0 || MoveAroundSocial1 > 0 || MoveAroundSocial2 > 0) && super.randomAnimation();
	}

	@Override
	protected boolean randomWalk()
	{
		return MoveArounding > 0 && super.randomWalk();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(ShoutMsg1 == 2 && ShoutMsg1_FString > 0 && ShoutMsg1_Probablity > Rnd.get(10000))
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg1_FString);
		if(ShoutMsg2 == 2 && ShoutMsg2_FString > 0 && ShoutMsg2_Probablity > Rnd.get(10000))
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg2_FString);
		if(ShoutMsg3 == 2 && ShoutMsg3_FString > 0 && ShoutMsg3_Probablity > Rnd.get(10000))
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg3_FString);
		if(ShoutMsg4 == 2 && ShoutMsg4_FString > 0 && ShoutMsg4_Probablity > Rnd.get(10000))
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg4_FString);
		if(AttackRange == 1)
		{
			if(_thisActor.isInRangeZ(attacker, 100) && _thisActor.i_ai4 == 0)
			{
				_thisActor.i_ai4 = 1;
				_thisActor.c_ai0 = attacker.getStoredId();
				addTimer(5001, 10000);
			}
		}

		addAttackDesire(attacker, 0, DEFAULT_DESIRE);
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1005)
		{
			if(Party_Type == 1 && PrivateFollowBoss == 1)
			{
				if(!_thisActor.isMyBossAlive())
				{
					if(!_thisActor.isInCombat() && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
					{
						_thisActor.onDecay();
						return;
					}
				}
				if(!inMyTerritory() && _thisActor.isMyBossAlive() && _intention != CtrlIntention.AI_INTENTION_ATTACK)
				{
					_thisActor.teleToLocation(_thisActor.getLeader().getLoc());
					removeAllAttackDesire();
				}
			}
			addTimer(1005, 120000);
		}
		else if(timerId == 1006)
		{
			if(Party_Type == 1 && PrivateFollowBoss == 1 && !_thisActor.isMyBossAlive() && !_thisActor.isInCombat() && _intention != CtrlIntention.AI_INTENTION_ATTACK)
			{
				_thisActor.onDecay();
			}
		}
		else if(timerId == 1101)
		{
			if(MoveAroundSocial > 0)
			{
				if(_intention == CtrlIntention.AI_INTENTION_ACTIVE && !_thisActor.isMoving && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.4 && !_thisActor.isDead() && Social0_Probablity > Rnd.get(10000))
					addEffectActionDesire(Social0, MoveAroundSocial, 10000000);

				addTimer(1101, Social0_Timer);
			}
		}
		else if(timerId == 1102)
		{
			if(MoveAroundSocial1 > 0)
			{
				if(_intention == CtrlIntention.AI_INTENTION_ACTIVE && !_thisActor.isMoving && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.4 && !_thisActor.isDead() && Social1_Probablity > Rnd.get(10000))
					addEffectActionDesire(Social1, MoveAroundSocial, 10000000);

				addTimer(1102, Social1_Timer);
			}
		}
		else if(timerId == 1103)
		{
			if(MoveAroundSocial2 > 0)
			{
				if(_intention == CtrlIntention.AI_INTENTION_ACTIVE && !_thisActor.isMoving && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.4 && !_thisActor.isDead() && Social2_Probablity > Rnd.get(10000))
					addEffectActionDesire(Social2, MoveAroundSocial, 10000000);

				addTimer(1103, Social2_Timer);
			}
		}
		else if(timerId == 5001)
		{
			if(_thisActor.i_ai4 == 1)
			{
				L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
				if(cha != null)
				{
					addFleeDesire(cha, 10000000);
					_thisActor.i_ai4 = 0;
					_thisActor.c_ai0 = 0;
				}
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance minion)
	{
		if(Party_Type == 1)
		{
			if(PrivateFollowBoss == 1)
			{
				if(!_thisActor.isMyBossAlive())
				{
					if(minion == _thisActor.getLeader())
					{
						addTimer(1006, 20000);
					}
				}
			}
		}
		else if(Party_Type == 2)
		{
			if(minion.getMinionData().minionRespawn > 0)
			{
				_thisActor.respawnPrivate(minion, 0, minion.getMinionData().minionRespawn);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if( FieldCycle != -1 && FieldCycle_Condition == 0 )
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if( i0 >= Threshold_Level_Min && i0 <= Threshold_Level_Max )
			{
				if( i1 >= Threshold_Point_Min && i1 <= Threshold_Point_Max )
				{
					FieldCycleManager.addPoint("KILL_1", FieldCycle, FieldCycle_Quantity, killer);
				}
				if( _thisActor.getNpcId() == 18465 )
				{
					if( i1 < 1010000 || i0 < 5 )
					{
						FieldCycleManager.addPoint("KILL_1", FieldCycle, 5, killer);
					}
				}
			}
		}

		if(ShoutMsg1 == 3 && ShoutMsg1_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg1_FString);
		if(ShoutMsg2 == 3 && ShoutMsg2_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg2_FString);
		if(ShoutMsg3 == 3 && ShoutMsg3_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg3_FString);
		if(ShoutMsg4 == 3 && ShoutMsg4_FString > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg4_FString);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return IsAggressive > 0 && super.checkAggression(target);
	}
}
