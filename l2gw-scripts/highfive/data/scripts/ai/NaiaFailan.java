package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 25.01.2010 19:03:24
 */
public class NaiaFailan extends DefaultAI
{
	private static L2Skill[] debuffs = {SkillTable.getInstance().getInfo(5529, 10), SkillTable.getInstance().getInfo(4486, 10)};
	private L2GroupSpawn guards = null;
	private static final L2Skill beam_of_fix = SkillTable.getInstance().getInfo(5493, 1);

	public NaiaFailan(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(guards == null)
		{
			guards = SpawnTable.getInstance().getEventGroupSpawn("naia_guards", null);
			guards.setRespawnDelay(0);
			guards.doSpawn();
		}
		else if(guards.isAllDecayed())
			guards.doSpawn();
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}
		else
			createNewTask();

		return false;
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		L2Character _temp_attack_target = null;

		L2Skill r_skill = debuffs[Rnd.get(debuffs.length)];

		if(!_thisActor.isSkillDisabled(r_skill.getId()))
		{
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			else
			{
				GArray<L2Player> players = _thisActor.getAroundPlayers(r_skill.getCastRange());

				if(players.size() > 0)
				{
					int c = 0;
					while(c < 15)
					{
						c++;
						_temp_attack_target = players.get(Rnd.get(players.size()));
						if(_temp_attack_target != null && !((L2Player) _temp_attack_target).isInvisible() && !_temp_attack_target.isInvul())
							break;
						else
							_temp_attack_target = null;
					}
				}
			}

			if(_temp_attack_target != null)
			{
				addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
				_thisActor.addDamageHate(_temp_attack_target, 0, 1);
			}
		}

		return true;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2245587)
		{
			if(arg1 instanceof L2Character)
			{
				L2Character cha = (L2Character) arg1;
				_thisActor.doCast(beam_of_fix, cha, true);
			}
		}
	}
}
