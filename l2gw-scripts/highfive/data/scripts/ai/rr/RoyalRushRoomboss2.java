package ai.rr;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 15:10
 */
public class RoyalRushRoomboss2 extends RoyalRushRoombossBasic
{
	public L2Skill RangeDDMagic1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill SelfBuff1 = SkillTable.getInstance().getInfo(266403841);
	public L2Skill SelfBuff2 = SkillTable.getInstance().getInfo(266403841);
	public L2Skill SelfBuff3 = SkillTable.getInstance().getInfo(266403841);

	public RoyalRushRoomboss2(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
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
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() != attacker)
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
					if(SelfBuff1.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff1.getId()))
					{
						addUseSkillDesire(_thisActor, SelfBuff1, 0, 1, 1000000);
					}
					break;
				case 4:
					if(SelfBuff2.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff2.getId()))
					{
						addUseSkillDesire(_thisActor, SelfBuff2, 0, 1, 1000000);
					}
					break;
				case 6:
					if(SelfBuff3.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff3.getId()))
					{
						addUseSkillDesire(_thisActor, SelfBuff3, 0, 1, 1000000);
					}
					break;
			}
			_thisActor.i_ai0++;
		}
		super.onEvtClanDead(victim);
	}
}