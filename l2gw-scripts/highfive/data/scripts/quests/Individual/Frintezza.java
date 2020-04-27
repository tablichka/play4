package quests.Individual;

import javolution.util.FastList;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.List;

/**
 * @author rage
 * @date 09.09.2009 15:45:45
 */
public class Frintezza extends Quest
{
	private static int DUNGEON_GK = 32011;
	private static boolean GMTEST = false; // switch it on when you have no need to test CC and etc

	public Frintezza()
	{
		super(21004, "Frintezza", "Frintezza Individual", true);
		addStartNpc(DUNGEON_GK);
	}

	@Override
	public void onLoad()
	{
		_log.info("Loaded: " + this + " Individual.");
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		L2Player player = qs.getPlayer();
		L2NpcInstance npc = player.getLastNpc();
		int npcId = npc.getNpcId();
		if(npcId == DUNGEON_GK)
		{
			if(GMTEST)
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				_log.info(this + " onTalk: " + player + " inst: " + inst);
				if(inst != null)
				{
					player.setStablePoint(player.getLoc());
					player.teleToLocation(inst.getStartLoc(), inst.getReflection());
				}
				else
				{
					List<L2Player> list = new FastList<>();
					if(player.getParty() != null)
						list.addAll(player.getParty().getPartyMembers());
					else
						list.add(player);

					inst = InstanceManager.getInstance().createNewInstance(136, list);
					_log.info(this + " onTalk: " + player + " create inst: " + inst);
					if(inst != null)
						for(L2Player member : list)
						{
							member.setStablePoint(member.getLoc());
							member.teleToLocation(inst.getStartLoc(), inst.getReflection());
							_log.info(this + " onTalk: " + player + " tele to: " + inst.getStartLoc() + " ref: " + inst.getReflection());
						}
				}
			}
			else
			{
				if(player.getParty() == null || player.getParty().getCommandChannel() == null || player.getParty().getCommandChannel().getChannelLeader() != player)
					InstanceManager.enterInstance(136, player, npc, 0);
				else
				{
					Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
					if(inst != null)
					{
						player.teleToLocation(inst.getStartLoc(), inst.getReflection());
					}
					else if(player.getItemCountByItemId(8073) < 1)
					{
						return "npchtm:frintessa_teleporter004.htm";
					}
					else if(InstanceManager.enterInstance(136, player, npc, 0))
					{
						player.destroyItemByItemId("FrintezzaEnter", 8073, 1, npc, true);
						L2Party party = player.getParty();
						if(party != null)
						{
							L2CommandChannel cc = party.getCommandChannel();
							if(cc != null)
							{
								for(L2Party pp : cc.getParties())
									for(L2Player member : pp.getPartyMembers())
										if(member != null)
										{
											long c = member.getItemCountByItemId(8192);
											if(c > 0)
												member.destroyItemByItemId("FrintezzaEnter", 8192, c, npc, true);
											c = member.getItemCountByItemId(8556);
											if(c > 0)
												member.destroyItemByItemId("FrintezzaEnter", 8556, c, npc, true);
										}
							}
							else
								for(L2Player member : party.getPartyMembers())
									if(member != null)
									{
										long c = member.getItemCountByItemId(8192);
										if(c > 0)
											member.destroyItemByItemId("FrintezzaEnter", 8192, c, npc, true);
										c = member.getItemCountByItemId(8556);
										if(c > 0)
											member.destroyItemByItemId("FrintezzaEnter", 8556, c, npc, true);
									}
						}
					}
					else
						return "npchtm:frintessa_teleporter008.htm";
				}
			}
		}
		return null;
	}
}
