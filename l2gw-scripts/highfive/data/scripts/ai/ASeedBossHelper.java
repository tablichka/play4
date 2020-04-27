package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 18:18
 */
public class ASeedBossHelper extends DefaultNpc
{
	public int max_desire = 10000000;
	public L2Skill CurseOfTacrakhan = SkillTable.getInstance().getInfo(435814401);
	public L2Skill CurseOfDopagen = SkillTable.getInstance().getInfo(435879937);
	public L2Skill HPMPSKILL01_ID = SkillTable.getInstance().getInfo(417857537);
	public L2Skill HPMPSKILL02_ID = SkillTable.getInstance().getInfo(417857538);
	public L2Skill HPMPSKILL03_ID = SkillTable.getInstance().getInfo(417857539);
	public L2Skill HPMPSKILL04_ID = SkillTable.getInstance().getInfo(417857540);
	public L2Skill HPMPSKILL05_ID = SkillTable.getInstance().getInfo(417857541);
	public L2Skill HPMPSKILL06_ID = SkillTable.getInstance().getInfo(417857542);
	public L2Skill HPMPSKILL07_ID = SkillTable.getInstance().getInfo(417857543);
	public L2Skill HPMPSKILL08_ID = SkillTable.getInstance().getInfo(417857544);
	public L2Skill HPMPSKILL09_ID = SkillTable.getInstance().getInfo(417857545);

	public ASeedBossHelper(L2Character actor)
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
		if( c0 != null )
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 20091017, getStoredIdFromCreature(_thisActor), null);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 20091021 )
		{
			addUseSkillDesire(L2ObjectsStorage.getAsCharacter((Long) arg1), CurseOfTacrakhan, 1, 1, max_desire);
		}
		else if( eventId == 20091022 )
		{
			addUseSkillDesire(L2ObjectsStorage.getAsCharacter((Long) arg1), CurseOfDopagen, 1, 1, max_desire);
		}
		else if( eventId == 20091025 )
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 == null)
				return;

			int i0 = c0.getAbnormalLevelBySkill(HPMPSKILL01_ID);
			switch(i0)
			{
				case -1:
					addUseSkillDesire(c0, HPMPSKILL01_ID, 1, 1, max_desire);
					break;
				case 1:
					addUseSkillDesire(c0, HPMPSKILL02_ID, 1, 1, max_desire);
					break;
				case 2:
					addUseSkillDesire(c0, HPMPSKILL03_ID, 1, 1, max_desire);
					break;
				case 3:
					addUseSkillDesire(c0, HPMPSKILL04_ID, 1, 1, max_desire);
					break;
				case 4:
					addUseSkillDesire(c0, HPMPSKILL05_ID, 1, 1, max_desire);
					break;
				case 5:
					addUseSkillDesire(c0, HPMPSKILL06_ID, 1, 1, max_desire * 10);
					break;
				case 6:
					addUseSkillDesire(c0, HPMPSKILL07_ID, 1, 1, max_desire * 100);
					break;
				case 7:
					addUseSkillDesire(c0, HPMPSKILL08_ID, 1, 1, max_desire * 1000);
					break;
				case 8:
					addUseSkillDesire(c0, HPMPSKILL09_ID, 1, 1, max_desire * 10000);
					break;
			}
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