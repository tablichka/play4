package ai;

import ai.base.WarriorFlee;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 07.09.11 15:10
 */
public class AiTantaarFrog extends WarriorFlee
{
	public long Max_Desire = 1000000000000000000L;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(458752001);

	public AiTantaarFrog(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null)
		{
			_thisActor.c_ai0 = attacker.getStoredId();
		}
		if(_thisActor.i_ai1 == 0)
		{
			if(attacker != null && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(35, attacker.getNpcId())))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "buffer out");
				}
				_thisActor.createOnePrivate(18918, "AiTantaarVegetationBuffer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 3, 0);
			}
			_thisActor.i_ai1 = 1;
			_thisActor.changeNpcState(2);
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		super.onEvtSpelled(skill, caster);
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "SPELLED:" + skill.getId());
		}
		if(skill.getId() == 6427)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "s_lizard_grasslands_fungus1 hit");
			}
			addUseSkillDesire(_thisActor, 433979393, 0, 1, Max_Desire);
		}
	}
}
