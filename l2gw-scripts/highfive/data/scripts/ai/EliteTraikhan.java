package ai;

import ai.base.AiASeedEliteMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 17:02
 */
public class EliteTraikhan extends AiASeedEliteMonster
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(418185217);

	public EliteTraikhan(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(418447361);
		Skill01_Probability = 15;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(418643969);
		Skill02_Probability = 15;
		Skill02_Target_Type = 1;
		FieldCycle_ID = 4;
		FieldCycle_point = 10;
	}

	@Override
	protected void onEvtSpawn()
	{
		int i1 = Rnd.get(3);
		for(int i0 = 0; i0 < 3; i0++)
		{
			switch(i1)
			{
				case 0:
					_thisActor.createOnePrivate(22746, "Bgurent", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
				case 1:
					_thisActor.createOnePrivate(22747, "Brakian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
				case 2:
					_thisActor.createOnePrivate(22748, "Groykhan", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					break;
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 300)
		{
			if(Rnd.get(100) < 5)
			{
				addUseSkillDesire(attacker, SpecialSkill01_ID, 0, 1, 1000000);
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}
}