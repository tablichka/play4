package npc.model;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 09.12.2010 17:40:46
 */
public class ZakenEnterInstance extends L2NpcInstance
{
	public ZakenEnterInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	public void onBypassFeedback(L2Player player, String command)
	{
		if(!isInRange(player, getInteractDistance(player)))
		{
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, this);
			player.sendActionFailed();
		}
		else
		{
			if(command.equals("zaken_day"))
			{
				int instId = 133;
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					InstanceManager.enterInstance(instId, player, this, 0);
					return;
				}

				L2Party party = player.getParty();
				if(party != null)
				{
					L2CommandChannel cc = party.getCommandChannel();
					if(cc == null)
					{
						if(party.isLeader(player))
							InstanceManager.enterInstance(instId, player, this, 0);
						else
							showChatWindow(player, "/data/html/default/zaken_enter001a.htm");
					}
					else
					{
						if(cc.getChannelLeader() == player)
							InstanceManager.enterInstance(instId, player, this, 0);
						else
							showChatWindow(player, "/data/html/default/zaken_enter001c.htm");
					}
				}
				else
					showChatWindow(player, "/data/html/default/zaken_enter001b.htm");
			}
			else if(command.equals("zaken_night"))
			{
				if(!InstanceManager.enterInstance(114, player, this, 0))
				{
					if(player.getParty() == null || player.getParty().getCommandChannel() == null || player.getParty().getCommandChannel().getChannelLeader() != player)
						showChatWindow(player, "/data/html/default/zaken_enter001c.htm");
					else if(player.getParty() != null && player.getParty().getCommandChannel() != null && player.getParty().getCommandChannel().getParties().size() < 7)
						showChatWindow(player, "/data/html/default/zaken_enter001d.htm");
				}
			}
			else if(command.equals("zaken_day83"))
			{
				int instId = 135;
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					InstanceManager.enterInstance(instId, player, this, 0);
					return;
				}

				L2Party party = player.getParty();
				if(party != null)
				{
					L2CommandChannel cc = party.getCommandChannel();
					if(cc == null)
					{
						if(party.isLeader(player))
							InstanceManager.enterInstance(instId, player, this, 0);
						else
							showChatWindow(player, "/data/html/default/zaken_enter001a.htm");
					}
					else
					{
						if(cc.getChannelLeader() == player)
							InstanceManager.enterInstance(instId, player, this, 0);
						else
							showChatWindow(player, "/data/html/default/zaken_enter001c.htm");
					}
				}
				else
					showChatWindow(player, "/data/html/default/zaken_enter001b.htm");
			}
			else
				super.onBypassFeedback(player, command);
		}
	}
}
