package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 11.09.11 17:57
 */
public class AiMarguene extends DefaultAI
{
	public int FIRST_TIMER = 1111;
	public int SECOND_TIMER = 1112;
	public int THIRD_TIMER = 1113;
	public int FORTH_TIMER = 1114;
	public int END_TIMER = 1115;
	public int DIST_CHECK_TIMER = 1116;
	public L2Skill B_PLASMA1 = SkillTable.getInstance().getInfo(417267713);
	public L2Skill B_PLASMA2 = SkillTable.getInstance().getInfo(417267714);
	public L2Skill B_PLASMA3 = SkillTable.getInstance().getInfo(417267715);
	public L2Skill C_PLASMA1 = SkillTable.getInstance().getInfo(417333249);
	public L2Skill C_PLASMA2 = SkillTable.getInstance().getInfo(417333250);
	public L2Skill C_PLASMA3 = SkillTable.getInstance().getInfo(417333251);
	public L2Skill R_PLASMA1 = SkillTable.getInstance().getInfo(417398785);
	public L2Skill R_PLASMA2 = SkillTable.getInstance().getInfo(417398786);
	public L2Skill R_PLASMA3 = SkillTable.getInstance().getInfo(417398787);
	public L2Skill B_BUFF_1 = SkillTable.getInstance().getInfo(415694849);
	public L2Skill B_BUFF_2 = SkillTable.getInstance().getInfo(415694850);
	public L2Skill C_BUFF_1 = SkillTable.getInstance().getInfo(417136641);
	public L2Skill C_BUFF_2 = SkillTable.getInstance().getInfo(417136642);
	public L2Skill R_BUFF_1 = SkillTable.getInstance().getInfo(417202177);
	public L2Skill R_BUFF_2 = SkillTable.getInstance().getInfo(417202178);

	public AiMarguene(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		talker.sendActionFailed();
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return false;

		if(_def_think)
		{
			doTask();
			return true;
		}

		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
		if(c0 != null && !_thisActor.isInRange(c0, 100))
		{
			_thisActor.setRunning();
			addMoveToDesire(Location.coordsRandomize(c0, 10, 40), 100);
		}

		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
		if(c0 != null)
		{
			_thisActor.setTitle(c0.getName());
			_thisActor.setDisplayId(_thisActor.getNpcId());
			_thisActor.updateAbnormalEffect();
			if(_thisActor.param2 == 0)
			{
				Functions.showOnScreentMsg(c0, 2, 0, 0, 0, 0, 1, 4000, 0, 1801149);
			}
		}

		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		addTimer(DIST_CHECK_TIMER, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DIST_CHECK_TIMER)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
			if(c0 == null || _thisActor.getLoc().distance3D(c0.getLoc()) < 150 && _thisActor.i_ai2 == 0)
			{
				_thisActor.i_ai2 = 1;
				addTimer(FIRST_TIMER, 4000);
			}
			else
			{
				addTimer(DIST_CHECK_TIMER, 1000);
			}
		}
		else if(timerId == FIRST_TIMER)
		{
			int i0 = (Rnd.get(3) + 1);

			_thisActor.changeNpcState(i0);
			_thisActor.i_ai1 = i0;
			addTimer(SECOND_TIMER, 5000 + Rnd.get(300));
			i0 = Rnd.get(3) + 1;
			switch(i0)
			{
				case 1:
					addEffectActionDesire(1, 1, 100);
					break;
				case 2:
					addEffectActionDesire(2, 1, 100);
					break;
				case 3:
					addEffectActionDesire(3, 1, 100);
					break;
			}
		}
		else if(timerId == SECOND_TIMER)
		{
			_thisActor.changeNpcState(4);
			int i0 = Rnd.get(3) + 1;

			_thisActor.changeNpcState(i0);
			_thisActor.i_ai1 = i0;
			addTimer(THIRD_TIMER, 4600 + Rnd.get(600));
			i0 = (Rnd.get(3) + 1);
			switch(i0)
			{
				case 1:
					addEffectActionDesire(1, 1, 100);
					break;
				case 2:
					addEffectActionDesire(2, 1, 100);
					break;
				case 3:
					addEffectActionDesire(3, 1, 100);
					break;
			}
		}
		else if(timerId == THIRD_TIMER)
		{
			_thisActor.changeNpcState(4);
			int i0 = Rnd.get(3) + 1;

			_thisActor.changeNpcState(i0);
			_thisActor.i_ai1 = i0;
			addTimer(FORTH_TIMER, 4200 + Rnd.get(900));

			i0 = Rnd.get(3) + 1;
			switch(i0)
			{
				case 1:
					addEffectActionDesire(1, 1, 100);
					break;
				case 2:
					addEffectActionDesire(2, 1, 100);
					break;
				case 3:
					addEffectActionDesire(3, 1, 100);
					break;
			}
		}
		else if(timerId == FORTH_TIMER)
		{
			_thisActor.i_ai1 = 0;
			_thisActor.changeNpcState(4);
			addTimer(END_TIMER, 500);
			int i0 = Rnd.get(3) + 1;
			switch(i0)
			{
				case 1:
					addEffectActionDesire(1, 1, 100);
					break;
				case 2:
					addEffectActionDesire(2, 1, 100);
					break;
				case 3:
					addEffectActionDesire(3, 1, 100);
					break;
			}
		}
		else if(timerId == END_TIMER)
		{
			_thisActor.changeNpcState(4);
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
			if(c0 != null)
			{
				c0.setSessionVar("maguen", null);
				if(_thisActor.param2 == 1)
				{
					c0.stopEffect(B_PLASMA1.getId());
					c0.stopEffect(C_PLASMA1.getId());
					c0.stopEffect(R_PLASMA1.getId());
				}
			}
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character c0 = _thisActor.getCastingTarget();
		if(c0 == null || !c0.isPlayer())
			return;

		L2Player target = (L2Player) c0;

		int i1 = target.getAbnormalLevelByType(B_PLASMA1.getId());
		if(i1 == -1)
		{
			i1 = 0;
		}
		int i2 = target.getAbnormalLevelByType(C_PLASMA1.getId());
		if(i2 == -1)
		{
			i2 = 0;
		}
		int i3 = target.getAbnormalLevelByType(R_PLASMA1.getId());
		if(i3 == -1)
		{
			i3 = 0;
		}
		if(i1 == 3 && i2 == 0 && i3 == 0)
		{
			Functions.showSystemMessageFStr(target, 1801150);
			target.stopEffect(B_PLASMA1.getId());

			if(Rnd.get(100) < 70)
			{
				addUseSkillDesire(target, B_BUFF_1, 0, 1, 100000000);
			}
			else
			{
				addUseSkillDesire(target, B_BUFF_2, 0, 1, 100000000);
			}
			int i4 = Rnd.get(10000);
			int i5 = Rnd.get(20);
			if(i4 == 0 && i5 != 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15488, 1, _thisActor, true);
				}
			}
			else if(i4 == 0 && i5 == 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15489, 1, _thisActor, true);
				}
			}
			addTimer(END_TIMER, (3 * 1000));
		}
		else if(i1 == 0 && i2 == 3 && i3 == 0)
		{
			Functions.showSystemMessageFStr(target, 1801151);
			target.stopEffect(C_PLASMA1.getId());
			if(Rnd.get(100) < 70)
			{
				addUseSkillDesire(target, C_BUFF_1, 0, 1, 100000000);
			}
			else
			{
				addUseSkillDesire(target, C_BUFF_2, 0, 1, 100000000);
			}
			int i4 = Rnd.get(10000);
			int i5 = Rnd.get(20);
			if(i4 == 0 && i5 != 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15488, 1, _thisActor, true);
				}
			}
			else if(i4 == 0 && i5 == 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15489, 1, _thisActor, true);
				}
			}
			addTimer(END_TIMER, (3 * 1000));
		}
		else if(i1 == 0 && i2 == 0 && i3 == 3)
		{
			Functions.showSystemMessageFStr(target, 1801152);
			target.stopEffect(R_PLASMA1.getId());
			if(Rnd.get(100) < 70)
			{
				addUseSkillDesire(target, R_BUFF_1, 0, 1, 100000000);
			}
			else
			{
				addUseSkillDesire(target, R_BUFF_2, 0, 1, 100000000);
			}
			int i4 = Rnd.get(10000);
			int i5 = Rnd.get(20);
			if(i4 == 0 && i5 != 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15488, 1, _thisActor, true);
				}
			}
			else if(i4 == 0 && i5 == 0)
			{
				if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
				{
					target.addItem("MaguenPet", 15489, 1, _thisActor, true);
				}
			}
			addTimer(END_TIMER, (3 * 1000));
		}
		else if(i1 + i2 + i3 == 3)
		{
			if(i1 == 1 && i2 == 1 && i3 == 1)
			{
				target.stopEffect(B_PLASMA1.getId());
				target.stopEffect(C_PLASMA1.getId());
				target.stopEffect(R_PLASMA1.getId());
				Functions.showSystemMessageFStr(target, 1801153);
				switch(Rnd.get(3))
				{
					case 0:
						if(Rnd.get(100) < 70)
						{
							addUseSkillDesire(target, B_BUFF_1, 0, 1, 100000000);
						}
						else
						{
							addUseSkillDesire(target, B_BUFF_2, 0, 1, 100000000);
						}
						break;
					case 1:
						if(Rnd.get(100) < 70)
						{
							addUseSkillDesire(target, C_BUFF_1, 0, 1, 100000000);
						}
						else
						{
							addUseSkillDesire(target, C_BUFF_2, 0, 1, 100000000);
						}
						break;
					case 2:
						if(Rnd.get(100) < 70)
						{
							addUseSkillDesire(target, R_BUFF_1, 0, 1, 100000000);
						}
						else
						{
							addUseSkillDesire(target, R_BUFF_2, 0, 1, 100000000);
						}
						break;
				}
				int i4 = Rnd.get(10000);
				int i5 = Rnd.get(20);
				if(i4 == 0 && i5 != 0)
				{
					if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
					{
						target.addItem("MaguenPet", 15488, 1, _thisActor, true);
					}
				}
				else if(i4 == 0 && i5 == 0)
				{
					if(target.getInventoryLimit() - target.getInventory().getSize() >= 1)
					{
						target.addItem("MaguenPet", 15489, 1, _thisActor, true);
					}
				}
				addTimer(END_TIMER, (3 * 1000));
			}
			else
			{
				Functions.showSystemMessageFStr(target, 1801154);
				target.stopEffect(B_PLASMA1.getId());
				target.stopEffect(C_PLASMA1.getId());
				target.stopEffect(R_PLASMA1.getId());
				addTimer(END_TIMER, 1000);
			}
		}
		else
		{
			addTimer(END_TIMER, 1000);
		}
		_thisActor.changeNpcState(4);

		target.setSessionVar("maguen", null);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character speller)
	{
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
		if(skill.getId() == 9060 && c0 == speller)
		{
			if(_thisActor.i_ai1 != 0 && _thisActor.i_ai0 == 0)
			{
				int i1 = speller.getAbnormalLevelByType(B_PLASMA1.getId());
				int i2 = speller.getAbnormalLevelByType(C_PLASMA1.getId());
				int i3 = speller.getAbnormalLevelByType(R_PLASMA1.getId());

				blockTimer(FIRST_TIMER);
				blockTimer(SECOND_TIMER);
				blockTimer(THIRD_TIMER);
				blockTimer(FORTH_TIMER);
				addTimer(END_TIMER, 30 * 1000);
				_thisActor.i_ai0 = 1;
				if(_thisActor.i_ai1 == 1)
				{
					if(i1 == -1)
					{
						addUseSkillDesire(speller, B_PLASMA1, 0, 1, 100000000);
					}
					else if(i1 == 1)
					{
						addUseSkillDesire(speller, B_PLASMA2, 0, 1, 100000000);
					}
					else if(i1 == 2)
					{
						addUseSkillDesire(speller, B_PLASMA3, 0, 1, 100000000);
					}
				}
				else if(_thisActor.i_ai1 == 2)
				{
					if(i2 == -1)
					{
						addUseSkillDesire(speller, C_PLASMA1, 0, 1, 100000000);
					}
					else if(i2 == 1)
					{
						addUseSkillDesire(speller, C_PLASMA2, 0, 1, 100000000);
					}
					else if(i2 == 2)
					{
						addUseSkillDesire(speller, C_PLASMA3, 0, 1, 100000000);
					}
				}
				else if(_thisActor.i_ai1 == 3)
				{
					if(i3 == -1)
					{
						addUseSkillDesire(speller, R_PLASMA1, 0, 1, 100000000);
					}
					else if(i3 == 1)
					{
						addUseSkillDesire(speller, R_PLASMA2, 0, 1, 100000000);
					}
					else if(i3 == 2)
					{
						addUseSkillDesire(speller, R_PLASMA3, 0, 1, 100000000);
					}
				}
			}
		}
	}
}
