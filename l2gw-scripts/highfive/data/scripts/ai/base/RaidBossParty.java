package ai.base;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 23.09.11 16:29
 */
public class RaidBossParty extends RaidBossStandard
{
	public String Privates = "20130;PartyPrivate;1;20sec:";

	public RaidBossParty(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.weight_point = 10;
		_thisActor.spawnMinions();
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(!attacker.isDead() && attacker.getLevel() > _thisActor.getLevel() + Config.RAID_MAX_LEVEL_DIFF)
		{
			L2Skill revengeSkill = SkillTable.getInstance().getInfo(L2Skill.SKILL_RAID_CURSE, 1);
			if(!_thisActor.isSkillDisabled(revengeSkill.getId()))
				_thisActor.altUseSkill(revengeSkill, attacker);

			return;
		}
		if(victim != _thisActor)
		{
			if(attacker.getAbnormalLevelByType(4515) == -1)
			{
				if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
				{
					if(damage == 0)
					{
						damage = 1;
					}
					addAttackDesire(attacker, 1, (long) (1.000000 * damage / (_thisActor.getLevel() + 7) * 20000));
				}
			}
		}
		if(_thisActor.isInZonePeace())
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			removeAllAttackDesire();
		}
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate != _thisActor)
		{
			_thisActor.respawnPrivate(partyPrivate, partyPrivate.getMinionData().weight_point, 100 + Rnd.get(50));
		}
	}
}