package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author admin
 * @date 27.11.2010 16:47:37
 */
public class SSQWizard extends Mystic
{
	private int detect_pc, die_message;

	public SSQWizard(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		detect_pc = getInt("detect_pc", -1);
		die_message = getInt("die_message", -1);
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_thisActor.i_ai0 == 0 && detect_pc > 0 && _thisActor.getAroundPlayers(300).size() > 0)
		{
			_thisActor.i_ai0 = 1;
			Functions.npcSay(_thisActor, Say2C.ALL, detect_pc);
		}

		return super.thinkActive();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(die_message > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, die_message);
	}
}
