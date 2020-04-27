package quests.global;

import javolution.util.FastList;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Location;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 18.10.2010 16:18:16
 */
public class SeedOfDestruction extends Quest
{
	private static int _currentSeedSatge;
	private static int _tiatKillCount;
	private static long _seedStageChangeTime;
	private ScheduledFuture<?> _seedChangeTask;
	private static final Location _sodEnter = new Location(-245843, 220547, -12104);
	private static final boolean GMTEST = false;
	private static final L2Zone _zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.instance, 110);

	// MOBS
	private static int TIAT1 = 29163;
	private static int TIAT2 = 29175;
	private static int GRATE_DEVICE = 18777;
	private static int DESTRUCTION_DEVICE = 18778;

	// NPC
	private static int ALLENOS = 32526;

	public SeedOfDestruction()
	{
		super(26001, "SeedOfDestruction", "Seed of Destruction", true);
		addStartNpc(ALLENOS);
		addTalkId(ALLENOS);
		addKillId(TIAT1, TIAT2, GRATE_DEVICE, DESTRUCTION_DEVICE);
	}

	@Override
	public void onLoad()
	{
		_currentSeedSatge = ServerVariables.getInt("sod_stage", 0);
		if(_currentSeedSatge == 0)
		{
			_currentSeedSatge = 1;
			ServerVariables.set("sod_stage", _currentSeedSatge);
		}
		_seedStageChangeTime = ServerVariables.getLong("sod_change_time", 0);
		_tiatKillCount = ServerVariables.getInt("sod_kills", 0);

		_log.info(this + " Current stage: " + _currentSeedSatge);
		if(_currentSeedSatge == 2)
		{
			long currentTime = System.currentTimeMillis();
			if(_seedStageChangeTime > currentTime)
			{
				_log.info(this + " Next seed stage: " + new Date(_seedStageChangeTime));
				_seedChangeTask = ThreadPoolManager.getInstance().scheduleGeneral(new SeedChangeTask(), _seedStageChangeTime - currentTime);
				SpawnTable.getInstance().startEventSpawn("sod_stage2");
			}
		}
	}

	@Override
	public void onShutdown()
	{
		if(_seedChangeTask != null)
			_seedChangeTask.cancel(true);
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		L2Player player = qs.getPlayer();
		L2NpcInstance npc = player.getLastNpc();

		if(event.equalsIgnoreCase("enter"))
		{
			if(_currentSeedSatge == 1)
			{
				int instId = 110;
				InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);

				if(it == null)
				{
					_log.warn(player + " try to enter instance id: " + instId + " but no instance template!");
					return null;
				}

				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				List<L2Player> party = new FastList<L2Player>();

				if(inst != null)
				{
					if(!GMTEST)
					{
						if(inst.getTemplate().getId() != instId)
						{
							player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON));
							return null;
						}
						if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
						{
							player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
							return null;
						}
					}

					if(it.isDispelBuff())
						for(L2Effect e : player.getAllEffects())
						{
							if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
								e.getNext().exit();

							if(e.getSkill().getBuffProtectLevel() < 1)
								e.exit();
						}

					player.setStablePoint(player.getLoc());
					player.teleToLocation(inst.getStartLoc(), inst.getReflection());
					return null;
				}

				if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(instId) >= it.getMaxCount())
				{
					player.sendPacket(new SystemMessage(SystemMessage.THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED_YOU_CANNOT_ENTER));
					return null;
				}

				if(!GMTEST)
				{
					if(player.getParty() == null)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER));
						return null;
					}

					L2CommandChannel channel = player.getParty().getCommandChannel();
					if(channel == null)
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOU_CANNOT_ENTER_BECAUSE_YOU_ARE_NOT_ASSOCIATED_WITH_THE_CURRENT_COMMAND_CHANNEL));
						return null;
					}
					else if(channel.getChannelLeader() != player)
					{
						player.sendPacket(new SystemMessage(SystemMessage.ONLY_A_PARTY_LEADER_CAN_TRY_TO_ENTER));
						return null;
					}
					else if(channel.getMemberCount() > it.getMaxParty() || channel.getMemberCount() < it.getMinParty())
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT));
						return null;
					}

					boolean ok = true;
					for(L2Player member : channel.getMembers())
						if(member.getLevel() < it.getMinLevel() || member.getLevel() > it.getMaxLevel())
						{
							channel.broadcastToChannelMembers(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(member));
							ok = false;
						}
						else if(member.getVar("instance-" + it.getType()) != null || InstanceManager.getInstance().getInstanceByPlayer(member) != null)
						{
							channel.broadcastToChannelMembers(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(member));
							ok = false;
						}
						else if(!npc.isInRange(member, 300))
						{
							channel.broadcastToChannelMembers(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(member));
							ok = false;
						}

					if(!ok)
						return null;

					party.addAll(channel.getMembers());
				}
				else
				{
					if(player.getParty() != null)
						party.addAll(player.getParty().getPartyMembers());
					else
						party.add(player);
				}

				inst = InstanceManager.getInstance().createNewInstance(instId, party);
				if(inst != null)
					for(L2Player member : party)
						if(member != null)
						{
							if(it.isDispelBuff())
								for(L2Effect e : member.getAllEffects())
								{
									if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
										e.getNext().exit();

									if(e.getSkill().getBuffProtectLevel() < 1)
										e.exit();
								}

							member.setStablePoint(member.getLoc());
							member.teleToLocation(inst.getStartLoc(), inst.getReflection());
						}
			}
			else if(_currentSeedSatge == 2)
				player.teleToLocation(_sodEnter);
		}

		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_currentSeedSatge == 1 && npc.getNpcId() == TIAT1)
		{
			_tiatKillCount++;
			if(_tiatKillCount >= 10)
			{
				_tiatKillCount = 0;
				_currentSeedSatge = 2;
				ServerVariables.set("sod_stage", _currentSeedSatge);
				_seedStageChangeTime = System.currentTimeMillis() + 12 * 60 * 60000L;
				ServerVariables.set("sod_change_time", _seedStageChangeTime);
				SpawnTable.getInstance().startEventSpawn("sod_stage2");
				_log.info(this + " stage changed to 2, next change: " + new Date(_seedStageChangeTime));
				_seedChangeTask = ThreadPoolManager.getInstance().scheduleGeneral(new SeedChangeTask(), 12 * 60 * 60000L);
			}
			ServerVariables.set("sod_kills", _tiatKillCount);
			if(npc.getSpawn() != null && npc.getSpawn().getInstance() != null)
				npc.getSpawn().getInstance().successEnd();
		}
		else if(npc.getNpcId() == GRATE_DEVICE || npc.getNpcId() == DESTRUCTION_DEVICE)
		{
			Instance inst = npc.getSpawn().getInstance();
			if(inst != null)
				inst.notifyKill(npc, killer);
		}
	}

	private class SeedChangeTask implements Runnable
	{
		public void run()
		{
			if(_currentSeedSatge == 2)
			{
				_currentSeedSatge = 1;
				ServerVariables.set("sod_stage", _currentSeedSatge);
				SpawnTable.getInstance().stopEventSpawn("sod_stage2", true);
				for(L2Player player : _zone.getPlayers())
					player.teleToClosestTown();
			}
		}
	}
}
