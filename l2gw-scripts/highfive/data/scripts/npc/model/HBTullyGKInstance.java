package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 29.10.2010 17:38:51
 */
public class HBTullyGKInstance extends L2NpcInstance
{
	private static final String _path = "data/html/default/";
	private long _nextActiveTime;
	private GArray<L2DoorInstance> _doors;

	public HBTullyGKInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		if(getSpawn() == null)
			return;
		Instance inst = getSpawn().getInstance();

		if(inst == null)
			_log.warn(this + " has no instance!!");
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(_doors == null)
		{
			String[] doors = getAIParams() != null ? getAIParams().getString("doors", "").split(",") : new String[0];
			_doors = new GArray<L2DoorInstance>(doors.length);

			Instance inst = getSpawn().getInstance();
			for(L2DoorInstance door : inst.getDoors())
				for(String doorId : doors)
					if(doorId != null && !doorId.isEmpty() && door.getDoorId() == Integer.parseInt(doorId))
					{
						_doors.add(door);
						if(_doors.size() == doors.length)
							break;
					}

			if(_doors.size() < 1)
				_log.warn(this + " doors not found!");
		}

		if(command.startsWith("openDoor"))
		{
			if(_nextActiveTime > System.currentTimeMillis())
			{
				showChatWindow(player, 1);
				return;
			}
			if(player.getClassId() == ClassId.maestro || player.getClassId() == ClassId.warsmith || Rnd.chance(60))
			{
				for(L2DoorInstance door : _doors)
					if(!door.isOpen())
					{
						door.openMe();
						door.onOpen();
					}
			}
			else
			{
				_nextActiveTime = System.currentTimeMillis() + Rnd.get(120000, 300000);
				showChatWindow(player, 1);
			}
		}
	}

	public void showChatWindow(L2Player player, int val)
	{
		String filename = _path;
		if(val == 0)
		{
			if(_nextActiveTime < System.currentTimeMillis())
				filename += "tully_gk_001.htm";
			else
				filename += "tully_gk_002.htm";
		}
		else
			filename += "tully_gk_003.htm";

		player.sendPacket(new NpcHtmlMessage(player, this, filename, 0));
	}
}
