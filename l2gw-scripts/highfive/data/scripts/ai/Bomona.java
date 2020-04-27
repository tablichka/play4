package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 19:08
 */
public class Bomona extends Citizen
{
	public L2Skill CHECK_SKILL = SkillTable.getInstance().getInfo(419954689);
	public L2Skill TRANSFORM_SKILL = SkillTable.getInstance().getInfo(435748865);

	public Bomona(L2Character actor)
	{
		super(actor);
		fnHi = "bomona001.htm";
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "bomona001.htm");
		return super.onTalk(talker);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -415 && reply == 1 )
		{
			if( talker.getAbnormalLevelBySkill(CHECK_SKILL) == 1 )
			{
				_thisActor.showPage(talker, "bomona003a.htm");
			}
			else
			{
				_thisActor.altUseSkill(TRANSFORM_SKILL, talker);
				_thisActor.altUseSkill(CHECK_SKILL, talker);
				_thisActor.showPage(talker, "bomona003b.htm");
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}
}