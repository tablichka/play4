package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 09.09.11 2:08
 */
public class AiEmeraldHorn extends DetectPartyWizard
{
	public L2Skill reflectiveSkill = SkillTable.getInstance().getInfo(447152129);
	public L2Skill reflectiveAttackLv1 = SkillTable.getInstance().getInfo(447217665);
	public L2Skill reflectiveAttackLv2 = SkillTable.getInstance().getInfo(447283201);
	public L2Skill reflectiveAttackLv3 = SkillTable.getInstance().getInfo(447283202);
	public int damageTimer_15 = 20100504;
	public int damageTimer_25 = 20100505;
	public int isChasePC = 2500;

	public AiEmeraldHorn(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		int i0 = (_thisActor.getSpawnedLoc().getX());
		int i1 = (_thisActor.getSpawnedLoc().getY());
		int i2 = (_thisActor.getX());
		int i3 = (_thisActor.getY());
		int i4 = (i0 - i2);
		int i5 = (i1 - i3);
		if(((i4 * i4) + (i5 * i5)) > (isChasePC * isChasePC))
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
		}
		if(_thisActor.getAbnormalLevelByType(reflectiveSkill.getId()) > 0)
		{
			if(_thisActor.i_ai3 == 1)
			{
				_thisActor.i_ai2 = (_thisActor.i_ai2 + damage);
			}
		}
		if(_thisActor.i_ai2 > 5000)
		{
			addUseSkillDesire(attacker, reflectiveAttackLv3, 0, 1, 9999000000000000L);
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai3 = 0;
			_thisActor.i_ai5 = 1;
		}
		if(_thisActor.i_ai2 > 10000)
		{
			addUseSkillDesire(attacker, reflectiveAttackLv2, 1, 0, 9999000000000000L);
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai3 = 0;
			_thisActor.i_ai5 = 1;
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(Rnd.get(5) < 1)
		{
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai3 = 1;
			addUseSkillDesire(_thisActor, reflectiveSkill, 1, 0, 99999000000000000L);
			addTimer(damageTimer_15, (15 * 1000));
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == damageTimer_15)
		{
			if(_thisActor.i_ai5 == 0)
			{
				if(_thisActor.getMostHated() != null)
				{
					addUseSkillDesire(_thisActor.getMostHated(), reflectiveAttackLv1, 1, 0, 9999000000000000L);
				}
			}
			_thisActor.i_ai3 = 0;
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}
