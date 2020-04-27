package ai;

import ai.base.RaidBossType2;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.10.11 20:19
 */
public class LabyrinthD extends RaidBossType2
{
	public L2Skill Debuff1 = SkillTable.getInstance().getInfo(268042241);
	public L2Skill Debuff2 = SkillTable.getInstance().getInfo(268107777);
	public L2Skill Debuff3 = SkillTable.getInstance().getInfo(268173313);
	public int PCCafe_Reward = 13002;
	public int PCCafe_Number = -1;

	public LabyrinthD(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai4 = 0;
		addTimer(8255, 3000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1624002)
		{
			if(Debuff1.getMpConsume() < _thisActor.getCurrentMp() && Debuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Debuff1.getId()))
			{
				addUseSkillDesire(_thisActor, Debuff1, 1, 1, 1000000);
			}
		}
		else if(eventId == 1624003)
		{
			if(Debuff2.getMpConsume() < _thisActor.getCurrentMp() && Debuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Debuff2.getId()))
			{
				addUseSkillDesire(_thisActor, Debuff2, 1, 1, 1000000);
			}
		}
		else if(eventId == 1624004)
		{
			if(Debuff3.getMpConsume() < _thisActor.getCurrentMp() && Debuff3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Debuff3.getId()))
			{
				addUseSkillDesire(_thisActor, Debuff3, 1, 1, 1000000);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		/*
		L2Character c0 = killer;
		if( c0 != null )
		{
			if( c0.master )
			{
				c0 = c0.master;
			}
			if( c0.isPlayer() )
			{
				party0 = gg.GetParty(c0);
				if( myself.IsNullParty(party0) == 0 )
				{
					int i0 = party0.member_count;
					for(int i1 = 0; i1 < i0; i1 = ( i1 + 1 ))
					{
						L2Character c1 = myself.GetMemberOfParty(party0, i1);
						if( c1 != null )
						{
							if( myself.IsPCCafeUser(c1) == 1 )
							{
								if( _thisActor.getLoc().distance3D(c1.getLoc()) < 2500 )
								{
									st.giveItems(PCCafe_Reward, PCCafe_Number);
								}
							}
						}
					}
				}
			}
		}
		*/

		Instance inst = _thisActor.getInstanceZone();
		if(inst != null)
		{
			inst.markRestriction();
			inst.rescheduleEndTask(600);
		}
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate != _thisActor)
		{
			_thisActor.respawnPrivate(partyPrivate, partyPrivate.weight_point, partyPrivate.getMinionData().minionRespawn);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 8255)
		{
			if(_thisActor.getSpawnedLoc().getZ() - _thisActor.getZ() > 15 || _thisActor.getSpawnedLoc().getZ() - _thisActor.getZ() < -500)
			{
				removeAllAttackDesire();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			}
			addTimer(8255, 3000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	public boolean checkBossPosition()
	{
		return false;
	}
}