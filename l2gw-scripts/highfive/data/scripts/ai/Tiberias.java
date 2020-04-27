package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * <hr>AI рейдбосса <strong>Tiberias</strong> npc_id=25528 <hr>
 * <li>любит поговорить после смерти
 * <br><br>
 * @author HellSinger
 * @version 0.1b
 * @since 2009-01-18
 */

public class Tiberias extends Fighter
{

	public Tiberias(L2Character actor)
	{
		super(actor);
	}

	/**
	 * Реплика перед гибелью
	 */
	@Override
	protected void onEvtDead(L2Character killer)
	{
		Functions.npcSay(_thisActor, Say2C.ALL, 1800071);
		super.onEvtDead(killer);
	}
}