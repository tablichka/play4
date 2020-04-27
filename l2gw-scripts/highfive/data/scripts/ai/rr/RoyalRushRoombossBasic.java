package ai.rr;

import ai.base.Warrior;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 15:06
 */
public class RoyalRushRoombossBasic extends Warrior
{
	public L2Skill PhysicalSpecial1 = SkillTable.getInstance().getInfo(264241153);
	public int KeyBox = 31842;
	public int KeyBox_X = 0;
	public int KeyBox_Y = 0;
	public int KeyBox_Z = 0;

	public RoyalRushRoombossBasic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial1, 0, 1, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(KeyBox_X != 0 && KeyBox_Y != 0 && KeyBox_Z != 0)
		{
			_thisActor.createOnePrivate(KeyBox, "rr.RoyalRushKeybox", 0, 0, KeyBox_X, KeyBox_Y, KeyBox_Z, 0, 0, 0, 0);
		}
		else
		{
			_thisActor.createOnePrivate(KeyBox, "rr.RoyalRushKeybox", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
		}
	}
}