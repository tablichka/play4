package ru.l2gw.gameserver.skills.conditions;

import ru.l2gw.gameserver.skills.Env;

/**
 * @author rage
 * @date 25.11.2009 11:40:25
 */
public class ConditionIsClanLeader extends Condition
{
	@Override
	public boolean testImpl(Env env)
	{
		return env.character.isPlayer() && env.character.getPlayer().isClanLeader();
	}
}
