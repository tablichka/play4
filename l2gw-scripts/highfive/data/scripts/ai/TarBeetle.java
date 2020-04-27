package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 13.01.11 17:20
 */
public class TarBeetle extends L2CharacterAI
{
	public static final L2Skill s_forge_tar_spite_1 = SkillTable.getInstance().getInfo(6142, 1);
	public static final L2Skill s_forge_tar_spite_2 = SkillTable.getInstance().getInfo(6142, 2);
	public static final L2Skill s_forge_tar_spite_3 = SkillTable.getInstance().getInfo(6142, 3);

	private static final int Shot_num_til_dsp = 5;
	private static final int TID_LONELY_TOO_LONG = 78001;
	private static final int TIME_LONELY_TOO_LONG = 300;
	private static final int TID_LOOK_NEIGHBOR = 78002;
	private static final int TIME_LOOK_NEIGHBOR = 10;

	private L2NpcInstance _thisActor;

	public TarBeetle(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = Shot_num_til_dsp;
		addTimer(TID_LOOK_NEIGHBOR, 1000);
		addTimer(TID_LONELY_TOO_LONG, TIME_LONELY_TOO_LONG * 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_LOOK_NEIGHBOR)
		{
			for(L2Player player : _thisActor.getAroundLivePlayers(300))
			{
				L2Effect effect = player.getEffectBySkillId(6142);
				L2Skill debuff;
				if(effect == null)
					debuff = s_forge_tar_spite_1;
				else if(effect.getAbnormalLevel() < 2)
					debuff = s_forge_tar_spite_2;
				else
					debuff = s_forge_tar_spite_3;

				if(!_thisActor.isSkillDisabled(debuff.getId()) && _thisActor.getCurrentMp() > debuff.getMpConsume() && _thisActor.i_ai1 > 0)
					_thisActor.doCast(debuff, player, false);
			}

			addTimer(TID_LOOK_NEIGHBOR, TIME_LOOK_NEIGHBOR * 1000);
		}
		else if(timerId == TID_LONELY_TOO_LONG)
		{
			if(_thisActor.i_ai1 >= Shot_num_til_dsp && _thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.onDecay();
			}
			else if(_thisActor.i_ai1 < Shot_num_til_dsp)
			{
				_thisActor.i_ai1++;
				addTimer(TID_LONELY_TOO_LONG, TIME_LONELY_TOO_LONG * 1000);
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill != null && skill.getId() == 6142)
		{
			_thisActor.i_ai1--;
			if((_thisActor.i_ai1 <= 0 || _thisActor.getCurrentMp() < s_forge_tar_spite_1.getMpConsume()) && _thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.onDecay();
			}
		}
	}
}
