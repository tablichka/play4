package ai.rr;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 15:13
 */
public class RoyalRushRoomboss4 extends RoyalRushRoombossBasic
{
	public L2Skill ClanBuff1 = SkillTable.getInstance().getInfo(266403841);
	public L2Skill RangeDDMagic1 = SkillTable.getInstance().getInfo(264241153);

	public RoyalRushRoomboss4(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(ClanBuff1.getMpConsume() < _thisActor.getCurrentMp() && ClanBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(ClanBuff1.getId()))
		{
			addUseSkillDesire(_thisActor, ClanBuff1, 1, 1, 1000000);
		}
		super.onEvtSpawn();
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
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(Rnd.get(100) < 33 && _thisActor.getMostHated() != attacker)
		{
			if(RangeDDMagic1.getMpConsume() < _thisActor.getCurrentMp() && RangeDDMagic1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(RangeDDMagic1.getId()))
			{
				addUseSkillDesire(attacker, RangeDDMagic1, 0, 1, 1000000);
			}
		}
		if(Rnd.get(100) < 10)
		{
			if(ClanBuff1.getMpConsume() < _thisActor.getCurrentMp() && ClanBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(ClanBuff1.getId()))
			{
				addUseSkillDesire(attacked_member, ClanBuff1, 0, 1, 1000000);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}