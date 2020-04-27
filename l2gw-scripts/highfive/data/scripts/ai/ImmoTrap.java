package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 15.12.11 12:53
 */
public class ImmoTrap extends DefaultNpc
{
	public int FieldCycle = 3;
	public int tide = 0;
	public int zone = 0;
	public int room = 2;
	public String type = "trap";
	public String my_trap_spawner = "";
	public String dispatcher_maker = "";
	public L2Skill trap_skill01 = SkillTable.getInstance().getInfo(355401737);
	public L2Skill trap_skill02 = SkillTable.getInstance().getInfo(355467273);
	public L2Skill trap_skill_display = SkillTable.getInstance().getInfo(341966849);
	//protected L2TrapInstance _thisTrap;

	public ImmoTrap(L2Character actor)
	{
		super(actor);
		//_thisTrap = (L2TrapInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && (type.equalsIgnoreCase("spawn") || type.equalsIgnoreCase("skill")))
		{
			//_thisTrap.setDetected(15);
			addUseSkillDesire(creature, trap_skill_display, 0, 1, 1000000);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == null)
			return;

		if(skill == trap_skill01 || skill == trap_skill02)
		{
			_thisActor.onDecay();
		}
		else if(skill == trap_skill_display)
		{
			if(type.equalsIgnoreCase("spawn"))
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), my_trap_spawner);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010059, 0, 0);
				}
				_thisActor.onDecay();
			}
			else if(type.equalsIgnoreCase("skill"))
			{
				switch(Rnd.get(2))
				{
					case 0:
						addUseSkillDesire(_thisActor, trap_skill01, 0, 0, 1000000);
						break;
					case 1:
						addUseSkillDesire(_thisActor, trap_skill02, 0, 0, 1000000);
						break;
				}
			}
		}
	}
}