package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.10.11 15:08
 */
public class GrailProtection extends DefaultNpc
{
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(414580737);
	public int TIME_TO_DIE = 50001;
	public int TIME_TO_FOLLOW = 50002;
	public int TIME_EXPLODE = 5003;
	public int DIST_CHECK = 5004;

	public GrailProtection(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.changeNpcState(2);
		addTimer(TIME_TO_FOLLOW, 100);
		addTimer(TIME_TO_DIE, 2 * 60000);
		addTimer(DIST_CHECK, 100);
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(c0 != null)
		{
			_thisActor.changeMasterName("****" + c0.getName() + "****");
			if(CategoryManager.isInCategory(112, c0.getActiveClass()))
			{
				_thisActor.setWalking();
				//myself.FixMoveType(1);
			}
			else if(CategoryManager.isInCategory(3, c0.getActiveClass()))
			{
				_thisActor.setWalking();
				//myself.FixMoveType(1);
			}
			else
			{
				_thisActor.setRunning();
				//myself.FixMoveType(1);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2114007)
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		addTimer(TIME_EXPLODE, 100);
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIME_TO_FOLLOW)
		{
			addTimer(TIME_TO_FOLLOW, 100);

			if(_thisActor.param1 != 0)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
				if(c0 != null)
				{
					if(CategoryManager.isInCategory(112, c0.getActiveClass()))
					{
						_thisActor.setWalking();
						addAttackDesire(c0, 1, 10);
					}
					else if(CategoryManager.isInCategory(3, c0.getActiveClass()))
					{
						_thisActor.setWalking();
						addAttackDesire(c0, 1, 10);
					}
					else
					{
						_thisActor.setRunning();
						addAttackDesire(c0, 1, 10);
					}
				}
			}
		}
		else if(timerId == TIME_TO_DIE)
		{
			_thisActor.doDie(null);
		}
		else if(timerId == TIME_EXPLODE)
		{
			_thisActor.doDie(null);
		}
		else if(timerId == DIST_CHECK)
		{
			addTimer(DIST_CHECK, 100);
			if(_thisActor.param1 != 0)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
				if(c0 != null)
					if(_thisActor.getLoc().distance3D(c0.getLoc()) < 100)
					{
						clearTasks();
						_thisActor.changeMasterName(c0.getName());
						addUseSkillDesire(c0, Skill01_ID, 0, 1, 1000000);
					}
					else if(_thisActor.getLoc().distance3D(c0.getLoc()) > 200000)
					{
						_thisActor.doDie(null);
					}
					else if(_thisActor.getLoc().distance3D(c0.getLoc()) < 150 && _thisActor.i_ai1 == 2)
					{
						_thisActor.i_ai1 = 3;
						_thisActor.changeMasterName("*" + c0.getName() + "*");
					}
					else if(_thisActor.getLoc().distance3D(c0.getLoc()) < 200 && _thisActor.i_ai1 == 1)
					{
						_thisActor.i_ai1 = 2;
						_thisActor.changeMasterName("**" + c0.getName() + "**");
					}
					else if(_thisActor.getLoc().distance3D(c0.getLoc()) < 250 && _thisActor.i_ai1 == 0)
					{
						_thisActor.i_ai1 = 1;
						_thisActor.changeMasterName("***" + c0.getName() + "***");
					}
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}