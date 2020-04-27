package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2ObjectTasks;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 06.09.11 10:39
 */
public class AiGuardianOfAntaras extends WarriorUseSkill
{
	public String SuperPointName1 = "24_21_course1";
	public String SuperPointName2 = "24_21_course2";
	public String SuperPointName3 = "24_21_course3";
	public String SuperPointName4 = "24_21_course4";
	public int SuperPointMethod1 = 0;
	public int SuperPointMethod2 = 1;
	public int SuperPointMethod3 = 2;
	public int SuperPointMethod4 = 3;
	public int ACTIVATION_TIMER = 2010;
	public int ACTIVATION_INTERVAL_1 = 60;
	public int ACTIVATION_INTERVAL_2 = 60;
	public int ACTIVATION_INTERVAL_3 = 60;
	public int ACTIVATION_INTERVAL_4 = 60;
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(441909249);
	public int max_desire = 10000000;

	public AiGuardianOfAntaras(L2Character actor)
	{
		super(actor);
		SuperPointDesire = 10;
		Skill01_ID = SkillTable.getInstance().getInfo(442171393);
		Skill01_Target = 0;
		Skill01_Probablity = 333;
		Skill02_ID = SkillTable.getInstance().getInfo(441974785);
		Skill02_Target = 0;
		Skill01_Probablity = 333;
		Skill03_ID = SkillTable.getInstance().getInfo(458752001);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.setHide(true);
		addTimer(1001, 60000);
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai0 = (int) _thisActor.param1;
		_thisActor.i_ai4 = _thisActor.getX();
		_thisActor.i_ai5 = _thisActor.getY();
		_thisActor.i_ai6 = _thisActor.getZ();
		if(_thisActor.i_ai0 == 1)
		{
			if(SuperPointName1 != null && !SuperPointName1.isEmpty())
			{
				_thisActor.i_ai3 = Rnd.get(28) + 1;
				_thisActor.setRunning();
				addMoveSuperPointDesire(SuperPointName1, SuperPointMethod1, SuperPointDesire);
			}
		}
		else if(_thisActor.i_ai0 == 2)
		{
			if(SuperPointName2 != null && !SuperPointName2.isEmpty())
			{
				_thisActor.i_ai3 = (Rnd.get(26) + 1);
				_thisActor.setRunning();
				addMoveSuperPointDesire(SuperPointName2, SuperPointMethod1, SuperPointDesire);
			}
		}
		else if(_thisActor.i_ai0 == 3)
		{
			if(SuperPointName3 != null && !SuperPointName3.isEmpty())
			{
				_thisActor.i_ai3 = (Rnd.get(26) + 1);
				_thisActor.setRunning();
				addMoveSuperPointDesire(SuperPointName3, SuperPointMethod1, SuperPointDesire);
			}
		}
		else if(_thisActor.i_ai0 == 4)
		{
			if(SuperPointName4 != null && !SuperPointName4.isEmpty())
			{
				_thisActor.i_ai3 = (Rnd.get(16) + 1);
				_thisActor.setRunning();
				addMoveSuperPointDesire(SuperPointName4, SuperPointMethod1, SuperPointDesire);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(_thisActor.getMostHated() == null && _thisActor.i_ai1 == 1)
			{
				if(_thisActor.isInRange(new Location(_thisActor.i_ai4, _thisActor.i_ai5, _thisActor.i_ai6), 50))
				{
					ThreadPoolManager.getInstance().executeAi(new L2ObjectTasks.NotifyAITask(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 20100501, _thisActor.i_ai0), false);
					_thisActor.onDecay();
				}
			}
			addTimer(1001, 60000);
			_thisActor.i_ai4 = (_thisActor.getX());
			_thisActor.i_ai5 = (_thisActor.getY());
			_thisActor.i_ai6 = (_thisActor.getZ());
		}
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
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

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 250)
		{
			if(CategoryManager.isInCategory(70, attacker) || CategoryManager.isInCategory(2, attacker))
			{
				if(Rnd.get(100) < 10)
				{
					addUseSkillDesire(attacker, SpecialSkill01_ID, 0, 1, (max_desire * max_desire));
				}
				if(Rnd.get(100) < 5)
				{
					_thisActor.createOnePrivate(18967, "AiGuardianHelper", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1, 0, 0);
					_thisActor.createOnePrivate(18967, "AiGuardianHelper", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 2, 0, 0);
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai2 != 0)
		{
			_thisActor.i_ai1 = 1;
			addAttackDesire(creature, 1, 100);
		}
		super.onEvtSeeCreature(creature);
	}

	protected void onEvtNodeArrived(SuperpointNode node)
	{
		int eventId = node.getNodeId();
		if(_thisActor.i_ai0 == 1)
		{
			if(SuperPointName1 != null && !SuperPointName1.isEmpty())
			{
				if(eventId == _thisActor.i_ai3 && _thisActor.i_ai2 < 1)
				{
					_thisActor.setHide(false);
					_thisActor.i_ai2 = 1;
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1811137);
				}
			}
		}
		else if(_thisActor.i_ai0 == 2)
		{
			if(SuperPointName2 != null && !SuperPointName2.isEmpty())
			{
				if(eventId == _thisActor.i_ai3 && _thisActor.i_ai2 < 1)
				{
					_thisActor.setHide(false);
					_thisActor.i_ai2 = 1;
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1811137);
				}
			}
		}
		else if(_thisActor.i_ai0 == 3)
		{
			if(SuperPointName3 != null && !SuperPointName3.isEmpty())
			{
				if(eventId == _thisActor.i_ai3 && _thisActor.i_ai2 < 1)
				{
					_thisActor.setHide(false);
					_thisActor.i_ai2 = 1;
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1811137);
				}
			}
		}
		else if(_thisActor.i_ai0 == 4)
		{
			if(SuperPointName4 != null && !SuperPointName4.isEmpty())
			{
				if(eventId == _thisActor.i_ai3 && _thisActor.i_ai2 < 1)
				{
					_thisActor.setHide(false);
					_thisActor.i_ai2 = 1;
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1811137);
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 20100501, _thisActor.i_ai0, null);
		super.onEvtDead(killer);
	}
}
