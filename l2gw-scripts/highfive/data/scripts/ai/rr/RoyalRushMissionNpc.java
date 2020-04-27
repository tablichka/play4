package ai.rr;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 17:19
 */
public class RoyalRushMissionNpc extends DefaultNpc
{
	public int KeyBox = 20130;
	public int KeyBox_X = 0;
	public int KeyBox_Y = 0;
	public int KeyBox_Z = 0;
	public int StrongNPC = 1020130;
	public String StrongNPC_AI = "rr.RoyalRushStrongMan1";
	public L2Skill MobHate = SkillTable.getInstance().getInfo(262209537);
	public L2Skill skill01 = SkillTable.getInstance().getInfo(287309825);
	public L2Skill skill02 = SkillTable.getInstance().getInfo(287375361);
	public L2Skill skill03 = SkillTable.getInstance().getInfo(287440897);
	public L2Skill skill04 = SkillTable.getInstance().getInfo(287506433);

	public RoyalRushMissionNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(3000, 300000);
		addTimer(3001, 5000);
		_thisActor.setRunning();
		_thisActor.lookNeighbor(300);
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtNoDesire()
	{
		addMoveAroundDesire2(100, 500);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer())
		{
			if(Rnd.get(100) < 80)
			{
				_thisActor.notifyAiEvent(creature, CtrlEvent.EVT_SCRIPT_EVENT, 1234, getStoredIdFromCreature(_thisActor), null);
			}
		}
		else
		{
			addFollowDesire2(creature, 100, 40, 100);
			if(_thisActor.i_ai0 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1010483, creature.getName());
			}
			else
			{
				int i0 = Rnd.get(4);
				switch(i0)
				{
					case 0:
						if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
						{
							addUseSkillDesire(creature, skill01, 1, 1, creature.getObjectId());
						}
						break;
					case 1:
						if(skill02.getMpConsume() < _thisActor.getCurrentMp() && skill02.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill02.getId()))
						{
							addUseSkillDesire(creature, skill02, 1, 1, creature.getObjectId());
						}
						break;
					case 2:
						if(skill03.getMpConsume() < _thisActor.getCurrentMp() && skill03.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill03.getId()))
						{
							addUseSkillDesire(creature, skill03, 1, 1, creature.getObjectId());
						}
						break;
					case 3:
						if(skill04.getMpConsume() < _thisActor.getCurrentMp() && skill04.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill04.getId()))
						{
							addUseSkillDesire(creature, skill04, 1, 1, creature.getObjectId());
						}
						break;
				}
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addFleeDesire(attacker, 1000);
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3000)
		{
			if(!_thisActor.isDead())
			{
				if(KeyBox_X != 0 && KeyBox_Y != 0 && KeyBox_Z != 0)
				{
					_thisActor.createOnePrivate(KeyBox, "rr.RoyalRushKeybox", 0, 0, KeyBox_X, KeyBox_Y, KeyBox_Z, 0, 0, 0, 0);
					_thisActor.i_ai0 = 1;
				}
				else
				{
					_thisActor.createOnePrivate(KeyBox, "rr.RoyalRushKeybox", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					_thisActor.i_ai0 = 1;
				}
				Functions.npcSay(_thisActor, Say2C.ALL, 1000503);
				_thisActor.lookNeighbor(300);
			}
		}
		else if(timerId == 3001)
		{
			_thisActor.lookNeighbor(300);
			addTimer(3001, 5000);
			if(_thisActor.i_ai0 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1010484);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.createOnePrivate(StrongNPC, StrongNPC_AI, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
		}
	}
}