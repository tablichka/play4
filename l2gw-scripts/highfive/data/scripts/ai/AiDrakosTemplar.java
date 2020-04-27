package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GCSArray;

/**
 * @author: rage
 * @date: 03.09.11 13:47
 */
public class AiDrakosTemplar extends DetectPartyWarrior
{
	public L2Skill detectSkill = SkillTable.getInstance().getInfo(449576961);
	public L2Skill speedUpSkill = SkillTable.getInstance().getInfo(449642497);

	private GCSArray<Long> neighbors;
	private long cleanTime;

	public AiDrakosTemplar(L2Character actor)
	{
		super(actor);
		neighbors = new GCSArray<>();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		neighbors.clear();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.6)
		{
			if(cleanTime < System.currentTimeMillis())
			{
				cleanTime = System.currentTimeMillis() + 10000;
				neighbors.clear();
			}

			for(L2NpcInstance npc : _thisActor.getKnownNpc(600))
				if(npc != null && npc.getNpcId() == 22824 && npc.getCurrentHp() < npc.getMaxHp() * 0.6 && !neighbors.contains(npc.getStoredId()))
				{
					neighbors.add(npc.getStoredId());
					addUseSkillDesire(npc, speedUpSkill, 1, 1, 99999999900000000L);
				}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill == detectSkill && _thisActor.isInRange(caster, 600))
		{
			L2Character target = caster.getCastingTarget();
			if(target != null)
				addUseSkillDesire(target, detectSkill, 0, 1, 99999999900000000L);
		}
	}
}
