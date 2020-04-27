package npc.model;

import quests.global.Hellbound;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.taskmanager.DecayTaskManager;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author: rage
 * @date: 24.10.2010 19:19:17
 */
public class HBNativePrisonerInstance extends L2NpcInstance
{
	private boolean _released = false;

	public HBNativePrisonerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(_released)
			return;

		if(command.startsWith("release"))
		{
			_released = true;
			showChatWindow(player, 1);
			Hellbound.addPoints(20);
			Functions.npcSayCustom(this, Say2C.ALL, "scripts.npc.model.HBNativePrisoner", null);
			DecayTaskManager.getInstance().addDecayTask(this, 3000);
		}
	}
}
