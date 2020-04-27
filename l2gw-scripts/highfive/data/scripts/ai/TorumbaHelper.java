package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 19:04
 */
public class TorumbaHelper extends DefaultNpc
{
	public L2Skill POISON_SLASH1 = SkillTable.getInstance().getInfo(419561473);
	public L2Skill POISON_SLASH2 = SkillTable.getInstance().getInfo(419561474);
	public L2Skill POISON_SLASH3 = SkillTable.getInstance().getInfo(419561475);
	public L2Skill POISON_SLASH4 = SkillTable.getInstance().getInfo(419561476);
	public L2Skill POISON_SLASH5 = SkillTable.getInstance().getInfo(419561477);
	public L2Skill TORUMBA_CURSE = SkillTable.getInstance().getInfo(419823617);
	public int max_desire = 10000000;

	public TorumbaHelper(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param3);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 20091017, getStoredIdFromCreature(_thisActor), null);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20091018)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				int i0 = c0.getAbnormalLevelBySkill(POISON_SLASH1);
				switch(i0)
				{
					case -1:
						addUseSkillDesire(c0, POISON_SLASH1, 0, 1, max_desire);
						break;
					case 11:
						addUseSkillDesire(c0, POISON_SLASH2, 0, 1, max_desire);
						break;
					case 12:
						addUseSkillDesire(c0, POISON_SLASH3, 0, 1, max_desire);
						break;
					case 13:
						addUseSkillDesire(c0, POISON_SLASH4, 0, 1, max_desire);
						break;
					case 14:
						addUseSkillDesire(c0, POISON_SLASH5, 0, 1, max_desire);
						break;
					case 15:
						addUseSkillDesire(c0, POISON_SLASH5, 0, 1, max_desire);
						break;
				}
			}
		}
		else if(eventId == 20091020)
		{
			addUseSkillDesire(L2ObjectsStorage.getAsCharacter((Long) arg1), TORUMBA_CURSE, 1, 1, max_desire);
		}
	}

	@Override
	protected void onEvtNoDesire()
	{
		L2Character leader = _thisActor.getLeader();
		if(leader != null)
		{
			if(!_thisActor.isInRange(leader, 500))
				_thisActor.teleToLocation(leader.getLoc());
			if(!_thisActor.isMyBossAlive())
				_thisActor.onDecay();
		}
		else
		{
			_thisActor.onDecay();
		}
	}
}