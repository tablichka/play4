package ai.rr;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 15:09
 */
public class RoyalRushRoomboss1 extends RoyalRushRoombossBasic
{
	public L2Skill RangeDDMagic1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill SelfDeBuff1 = SkillTable.getInstance().getInfo(266403841);
	public L2Skill SelfDeBuff2 = SkillTable.getInstance().getInfo(266403841);
	public L2Skill SelfDeBuff3 = SkillTable.getInstance().getInfo(266403841);

	public RoyalRushRoomboss1(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		if(SelfDeBuff1.getMpConsume() < _thisActor.getCurrentMp() && SelfDeBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfDeBuff1.getId()))
		{
			addUseSkillDesire(_thisActor, SelfDeBuff1, 1, 1, 1000000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(RangeDDMagic1.getMpConsume() < _thisActor.getCurrentMp() && RangeDDMagic1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(RangeDDMagic1.getId()))
					{
						addUseSkillDesire(attacker, RangeDDMagic1, 0, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanDead(L2NpcInstance victim)
	{
		if(victim != _thisActor)
		{
			switch(_thisActor.i_ai0)
			{
				case 2:
					if(SelfDeBuff1.getMpConsume() < _thisActor.getCurrentMp() && SelfDeBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfDeBuff1.getId()))
					{
						addUseSkillDesire(_thisActor, SelfDeBuff1, 0, 1, 1000000);
					}
					break;
				case 4:
					if(SelfDeBuff2.getMpConsume() < _thisActor.getCurrentMp() && SelfDeBuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfDeBuff2.getId()))
					{
						addUseSkillDesire(_thisActor, SelfDeBuff2, 0, 1, 1000000);
					}
					break;
				case 6:
					if(SelfDeBuff3.getMpConsume() < _thisActor.getCurrentMp() && SelfDeBuff3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfDeBuff3.getId()))
					{
						addUseSkillDesire(_thisActor, SelfDeBuff3, 0, 1, 1000000);
					}
					break;
			}
			_thisActor.i_ai0++;
		}
		super.onEvtClanDead(victim);
	}
}