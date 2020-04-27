package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 26.01.2010 13:25:52
 */
public class Chimera extends Fighter
{
	private boolean giveItem;
	private static int skillId = 2359;

	public Chimera(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		giveItem = true;
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		super.onEvtSeeSpell(skill, caster);

		if(giveItem && caster.getPlayer() != null && skill.getId() == skillId && caster.getTarget() == _thisActor && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.10)
		{
			giveItem = false;
			L2Player member = null;
			if(caster.getPlayer() != null && caster.getPlayer().getParty() != null)
				member = caster.getPlayer().getParty().getRandomMember();
			else if(caster.getPlayer() != null)
				member = caster.getPlayer();

			if(member != null)
				member.addItem("QuestLoot", Rnd.chance(80) ? 9680 : 9681, 1, _thisActor, true);

			_thisActor.onDecay();
		}
	}
}
