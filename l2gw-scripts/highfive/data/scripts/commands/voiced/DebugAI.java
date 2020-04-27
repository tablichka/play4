package commands.voiced;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.ai.L2PlayableAI;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 06.06.2010 15:57:28
 */
public class DebugAI  extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private String[] _commandList = new String[] { "debugai" };

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public boolean useVoicedCommand(String command, L2Player activeChar, String args)
	{
		L2PlayableAI ai = activeChar.getTarget() instanceof L2Playable ? (L2PlayableAI) ((L2Playable) activeChar.getTarget()).getAI() : activeChar.getAI();
		boolean debug = ai.getDebug();
		ai.setDebug(!debug);
		activeChar.sendMessage("AI Debug is " + (debug ? "off." : "on."));
		return true;
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}
