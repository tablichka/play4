package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 15.09.11 17:04
 */
public class WarriorCasting3skillMagical extends Warrior
{
	public L2Skill SleepMagic = SkillTable.getInstance().getInfo(265158657);
	public L2Skill DDMagic = SkillTable.getInstance().getInfo(262209537);
	public L2Skill CancelMagic = SkillTable.getInstance().getInfo(268304385);
	public L2Skill CheckMagic = SkillTable.getInstance().getInfo(458752001);
	public L2Skill CheckMagic1 = SkillTable.getInstance().getInfo(458752001);
	public L2Skill CheckMagic2 = SkillTable.getInstance().getInfo(458752001);

	public WarriorCasting3skillMagical(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() == null)
			{
			}
			else if(_thisActor.getMostHated() != attacker)
			{
				int i6 = Rnd.get(100);
				if(_thisActor.i_ai0 == 0)
				{
					_thisActor.i_ai0 = 1;
				}
				else if(_thisActor.i_ai0 == 1 && i6 < 30 && _thisActor.getCurrentHp() > (_thisActor.getMaxHp() / 10.000000))
				{
					if(SleepMagic.getMpConsume() < _thisActor.getCurrentMp() && SleepMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SleepMagic.getId()))
					{
						addUseSkillDesire(attacker, SleepMagic, 0, 1, 1000000);
					}
				}
			}
			else if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
			{
				int i6 = Rnd.get(100);
				if(_thisActor.getMostHated() == attacker && i6 < 33)
				{
					if(DDMagic.getMpConsume() < _thisActor.getCurrentMp() && DDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic.getId()))
					{
						addUseSkillDesire(attacker, DDMagic, 0, 1, 1000000);
					}
				}
			}
			if(Rnd.get(100) < 1 && _thisActor.i_ai1 == 0 && _thisActor.getCurrentHp() > (_thisActor.getMaxHp() * 0.400000))
			{
				_thisActor.i_ai1 = 1;
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && !_thisActor.isMoving)
		{
			int i6 = Rnd.get(100);
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100 && i6 < 33)
			{
				if(DDMagic.getMpConsume() < _thisActor.getCurrentMp() && DDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic.getId()))
				{
					addUseSkillDesire(attacker, DDMagic, 0, 1, 1000000);
				}
			}
			if(Rnd.get(100) < 1 && _thisActor.i_ai1 == 0 && _thisActor.getCurrentHp() > (_thisActor.getMaxHp() * 0.400000))
			{
				_thisActor.i_ai1 = 1;
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}
