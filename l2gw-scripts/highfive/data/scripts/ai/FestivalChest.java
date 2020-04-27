package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

public class FestivalChest extends DefaultAI
{

	public FestivalChest(L2Character actor)
	{
		super(actor);
		_globalAggro = 0;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		return true;
	}

	@Override
	protected boolean createNewTask()
	{
		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.addDamage(attacker, damage);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		_thisActor.addDamageHate(attacker, 0, aggro);
	}

}