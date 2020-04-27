package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 12:59
 */
public class AiBatwingDrake extends DetectPartyWizard
{
	public L2Skill vampiricSkill = SkillTable.getInstance().getInfo(449970177);
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(450101249);
	public L2Skill blastingSkill = SkillTable.getInstance().getInfo(450166785);
	public int max_private = 10;

	public AiBatwingDrake(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(target == null)
			return;

		if(skill == vampiricSkill)
		{
			if(_thisActor.i_ai2 > 4)
			{
				if(_thisActor.i_ai4 < max_private)
				{
					_thisActor.createOnePrivate(22828, "AiBigBloodyLeech", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1, 0, 0);
					_thisActor.i_ai4++;
				}
				_thisActor.i_ai2 = 0;
				_thisActor.i_ai3++;
			}
			_thisActor.i_ai2++;
		}
		if(_thisActor.i_ai3 > 4)
		{
			broadcastScriptEvent(10023, getStoredIdFromCreature(target), null, 1000);
			_thisActor.c_ai0 = target.getStoredId();
			if(_thisActor.c_ai0 != 0)
			{
				addUseSkillDesire(target, blastingSkill, 0, 1, 99999999900000000L);
			}
			_thisActor.i_ai3 = 0;
		}
		super.onEvtFinishCasting(skill);
	}
}
