package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 10:28
 */
public class AiDragonGuard extends WarriorUseSkill
{
	public AiDragonGuard(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(443547649);
		Skill01_Probablity = 1000;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if( Rnd.get(10) < 1 )
		{
			if( Rnd.get(2) < 1 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1911111);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1911112);
			}
		}
		super.onEvtDead(killer);
	}
}
