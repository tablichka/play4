package ai;

import instances.CrystalCavernsInstance;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;

/**
 * @author: rage
 * @date: 01.12.2009 15:28:33
 */
public class SCMystic extends Mystic
{
	public SCMystic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!attacker.isDead() && attacker.getPlayer() != null && attacker.getPlayer().getEffectBySkillId(CrystalCavernsInstance.TIMER_ID) == null)
		{
			if(Config.DEBUG_INSTANCES)
				Instance._log.info(_thisActor.getSpawn().getInstance() + " " + _thisActor + " attacked by " + attacker + " with no timer.");
			if(attacker.getPlayer().getParty() != null)
				for(L2Player member : attacker.getPlayer().getParty().getPartyMembers())
					member.teleToClosestTown();
			else
				attacker.getPlayer().teleToClosestTown();
			return;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
