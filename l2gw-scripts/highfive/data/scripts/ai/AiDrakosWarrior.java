package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 03.09.11 13:41
 */
public class AiDrakosWarrior extends DetectPartyWarrior
{
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(449445889);

	public AiDrakosWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(Rnd.chance(1))
		{
			addUseSkillDesire(_thisActor, summonSkill, 1, 0, 99999999900000000L);
			int i1 = 2 + Rnd.get(3);
			for(int i = 0; i < i1; i++)
			{
				Location pos = Location.coordsRandomize(_thisActor, 200);
				_thisActor.createOnePrivate(22823, "AiDrakosAssasin", 0, 0, pos.getX(), pos.getY(), pos.getZ(), 0, attacker.getStoredId(), 0, 0);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
