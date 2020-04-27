package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author: rage
 * @date: 24.10.2010 19:36:26
 */
public class HBMoonlightInstance extends L2NpcInstance
{
	private static final int KEY_ID = 9714;
	private boolean _opened = false;

	public HBMoonlightInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(command.startsWith("insertKey"))
		{
			if(!player.isInParty())
			{
				showChatWindow(player, 1);
				return;
			}

			if(player.getItemCountByItemId(KEY_ID) < 1)
			{
				showChatWindow(player, 2);
				return;
			}

			Instance inst = getSpawn().getInstance();
			if(inst == null)
			{
				_log.warn(this + " has no instance!!");
				return;
			}

			for(L2Player member : player.getParty().getPartyMembers())
				if(!isInRange(member, 300) || member.getReflection() != getReflection())
				{
					showChatWindow(player, 3);
					return;
				}

			if(_opened)
			{
				showChatWindow(player, 5);
				return;
			}

			if(player.destroyItemByItemId("Consume", KEY_ID, 1, this, true))
			{
				_opened = true;
				inst.successEnd();
				showChatWindow(player, 4);
			}
			else
				showChatWindow(player, 2);
		}
	}
}
