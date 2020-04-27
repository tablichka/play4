package events.TheFlowOfTheHorror;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.StopMove;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.gameserver.skills.funcs.FuncMul;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;

public class GilmoreAI extends Fighter
{
	private Location[] points_stage1 = new Location[7];
	private Location[] points_stage2 = new Location[1];

	private String[] text_stage1 = new String[7];
	private String[] text_stage2 = new String[2];

	private long wait_timeout = 0;
	private boolean wait = false;
	private int index;

	private int step_stage2 = 1;

	public GilmoreAI(L2Character actor)
	{
		super(actor);

		AI_TASK_DELAY = 200;

		points_stage1[0] = new Location(73195, 118483, -3722);
		points_stage1[1] = new Location(73535, 117945, -3754);
		points_stage1[2] = new Location(73446, 117334, -3752);
		points_stage1[3] = new Location(72847, 117311, -3711);
		points_stage1[4] = new Location(72296, 117720, -3694);
		points_stage1[5] = new Location(72463, 118401, -3694);
		points_stage1[6] = new Location(72912, 117895, -3723);

		points_stage2[0] = new Location(73615, 117629, -3765);

		text_stage1[0] = "Text1";
		text_stage1[1] = "Text2";
		text_stage1[2] = "Text3";
		text_stage1[3] = "Text4";
		text_stage1[4] = "Text5";
		text_stage1[5] = "Text6";
		text_stage1[6] = "Text7";

		text_stage2[0] = "Готовы?";
		text_stage2[1] = "Начнем, нельзя терять ни минуты!";
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(System.currentTimeMillis() > wait_timeout)
		{
			if(!wait)
				switch(TheFlowOfTheHorror.getStage())
				{
					case 1:
						if(Rnd.chance(30))
						{
							index = Rnd.get(text_stage1.length);
							Functions.npcSay(_thisActor, Say2C.ALL, text_stage1[index]);
							wait_timeout = System.currentTimeMillis() + 10000;
							wait = true;
							return true;
						}
						break;
					case 2:
						switch(step_stage2)
						{
							case 1:
								Functions.npcSay(_thisActor, Say2C.ALL, text_stage2[0]);
								wait_timeout = System.currentTimeMillis() + 10000;
								wait = true;
								return true;
							case 2:
								break;
						}
						break;
				}

			wait_timeout = 0;
			wait = false;

			_thisActor.setRunning();

			Task task;

			switch(TheFlowOfTheHorror.getStage())
			{
				case 1:
					index = Rnd.get(points_stage1.length);

					// Добавить новое задание
					task = new Task();
					task.type = TaskType.MOVE;
					task.loc = points_stage1[index];
					_task_list.add(task);
					_def_think = true;
					return true;
				case 2:
					switch(step_stage2)
					{
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, text_stage2[1]);

							// Добавить новое задание
							task = new Task();
							task.type = TaskType.MOVE;
							task.loc = points_stage2[0];
							_task_list.add(task);
							_def_think = true;

							step_stage2 = 2;
							return true;
						case 2:
							_thisActor.setHeading(0);
							_thisActor.broadcastPacket(new StopMove(_thisActor));
							_thisActor.broadcastPacketToOthers(new MagicSkillUse(_thisActor, _thisActor, 454, 1, 3000, 0));
							step_stage2 = 3;
							return true;
						case 3:
							_thisActor.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK_SPEED, 0x40, this, 5));
							_thisActor.addStatFunc(new FuncMul(Stats.MAGIC_DAMAGE, 0x40, this, 10));
							_thisActor.addStatFunc(new FuncMul(Stats.PHYSICAL_DAMAGE, 0x40, this, 10));
							_thisActor.addStatFunc(new FuncMul(Stats.RUN_SPEED, 0x40, this, 3));
							_thisActor.addSkill(SkillTable.getInstance().getInfo(1467, 1));
							_thisActor.broadcastUserInfo();
							step_stage2 = 4;
							return true;
						case 4:
							setIntention(CtrlIntention.AI_INTENTION_ATTACK, null);
							return true;
						case 10:
							_thisActor.removeStatsOwner(this);
							step_stage2 = 11;
							return true;
					}
			}
		}

		return false;
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		for(L2NpcInstance npc : L2World.getAroundNpc(_thisActor, 1000, 400))
			if(Rnd.chance(10) && npc != null && npc.getNpcId() == 20235)
			{
				L2MonsterInstance monster = (L2MonsterInstance) npc;
				if(Rnd.chance(20))
				{
					addUseSkillDesire(monster, _thisActor.getKnownSkill(1467), 1, 1, DEFAULT_DESIRE * 2);
					return true;
				}

				addAttackDesire(monster, 1, DEFAULT_DESIRE);
				return true;
			}
		return true;
	}
}