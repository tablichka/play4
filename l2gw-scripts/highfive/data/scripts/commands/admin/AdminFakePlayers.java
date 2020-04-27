package commands.admin;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.listeners.PlayerActionListener;
import ru.l2gw.extensions.listeners.events.MethodEvent;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.FakePlayer;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.tables.FakePlayersTable;
import ru.l2gw.util.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: rage
 * @date: 18.09.13 21:09
 */
public class AdminFakePlayers extends AdminBase
{
	private static AdminCommandDescription[] commands = new AdminCommandDescription[]{
			new AdminCommandDescription("admin_spawn_bot", "usage: //spawn_bot classId sex timeMin"),
			new AdminCommandDescription("admin_set_spawn_bot", "usage: //set_spawn_bot classId timeMin timeMax"),
			new AdminCommandDescription("admin_unset_spawn_bot", "usage: //unset_spawn_bot"),
	};

	private static final PlayerMoveListener moveListener = new PlayerMoveListener();

	private static final Map<ClassId, Map<Byte, Integer[]>> style = new HashMap<>();
	static
	{
		style.put(ClassId.fighter, new HashMap<Byte, Integer[]>());
		style.get(ClassId.fighter).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.fighter).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.mage, new HashMap<Byte, Integer[]>());
		style.get(ClassId.mage).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.mage).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.elvenFighter, new HashMap<Byte, Integer[]>());
		style.get(ClassId.elvenFighter).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.elvenFighter).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.elvenMage, new HashMap<Byte, Integer[]>());
		style.get(ClassId.elvenMage).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.elvenMage).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.darkFighter, new HashMap<Byte, Integer[]>());
		style.get(ClassId.darkFighter).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.darkFighter).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.darkMage, new HashMap<Byte, Integer[]>());
		style.get(ClassId.darkMage).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.darkMage).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.orcFighter, new HashMap<Byte, Integer[]>());
		style.get(ClassId.orcFighter).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.orcFighter).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.orcMage, new HashMap<Byte, Integer[]>());
		style.get(ClassId.orcMage).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.orcMage).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.dwarvenFighter, new HashMap<Byte, Integer[]>());
		style.get(ClassId.dwarvenFighter).put((byte) 0, new Integer[]{5, 4, 3});
		style.get(ClassId.dwarvenFighter).put((byte) 1, new Integer[]{7, 4, 3});
		style.put(ClassId.maleSoldier, new HashMap<Byte, Integer[]>());
		style.get(ClassId.maleSoldier).put((byte) 0, new Integer[]{5, 3, 3});
		style.put(ClassId.femaleSoldier, new HashMap<Byte, Integer[]>());
		style.get(ClassId.femaleSoldier).put((byte) 1, new Integer[]{7, 3, 3});
	}

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player player)
	{
		if("admin_spawn_bot".equals(command))
		{
			if(args.length < 3)
			{
				Functions.sendSysMessage(player, commands[0].usage);
				return false;
			}

			try
			{
				ClassId classId = ClassId.valueOf(args[0]);

				Map<Byte, Integer[]> params = style.get(classId);
				byte maxHs, maxHc, maxFace, sex = Byte.parseByte(args[1]);
				if(params == null)
				{
					Functions.sendSysMessage(player, "No class params, use default");
					maxHs = 5;
					maxHc = 3;
					maxFace = 3;
				}
				else
				{
					Integer[] p;
					if(params.containsKey(sex))
						p = params.get(sex);
					else
						p = params.get((byte) (sex == 1 ? 0 : 1));

					maxHs = p[0].byteValue();
					maxHc = p[1].byteValue();
					maxFace = p[2].byteValue();
				}
				int time = Integer.parseInt(args[2]);

				byte hs = (byte) Rnd.get(maxHs);
				byte hc = (byte) Rnd.get(maxHc);
				byte f = (byte) Rnd.get(maxFace);

				FakePlayer fakePlayer = FakePlayersTable.createFakePlayer(classId, sex, hs, hc, f, time, time);
				fakePlayer.setXYZInvisible(player.getX(), player.getY(), player.getZ());
				fakePlayer.setHeading(player.getHeading());
				fakePlayer.spawnMe();
				FakePlayersTable.addFakePlayer(fakePlayer);
				logGM.info("Spawn fake player: " + fakePlayer + " at " + fakePlayer.getLoc() + " despawn after: " + time + " min " + fakePlayer.getHairStyle() + " " + fakePlayer.getHairColor() + " " + fakePlayer.getFace());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Functions.sendSysMessage(player, "Error: " + e);
			}
		}
		else if("admin_set_spawn_bot".equals(command))
		{
			if(args.length < 3)
			{
				Functions.sendSysMessage(player, commands[1].usage);
				return false;
			}

			try
			{
				ClassId.valueOf(args[0]);
				Integer.parseInt(args[1]);
				Integer.parseInt(args[2]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(player, "can't set params: " + e);
				return false;
			}

			player.setSessionVar("bot_classId", args[0]);
			player.setSessionVar("bot_timeMin", args[1]);
			player.setSessionVar("bot_timeMax", args[2]);

			player.removeMethodInvokeListener(moveListener);
			player.addMethodInvokeListener(moveListener);
			Functions.sendSysMessage(player, "Spawn bot param set: " + args[0] + " " + args[1] + " " + args[2]);
		}
		else if("admin_unset_spawn_bot".equals(command))
		{
			player.setSessionVar("bot_classId", null);
			player.setSessionVar("bot_timeMin", null);
			player.setSessionVar("bot_timeMax", null);

			player.removeMethodInvokeListener(moveListener);
			Functions.sendSysMessage(player, "unset spawn bot params");
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return commands;
	}

	private static class PlayerMoveListener extends PlayerActionListener
	{
		@Override
		public boolean accept(MethodEvent event)
		{
			return event.getMethodName().equals(onMoveRequest);
		}

		@Override
		public void methodInvoked(MethodEvent e)
		{
			if(e.getArgs()[0] instanceof Location)
			{
				try
				{
					L2Player player = (L2Player) e.getOwner();
					ClassId classId = ClassId.valueOf(player.getSessionVar("bot_classId"));
					int timeMin = Integer.parseInt(player.getSessionVar("bot_timeMin"));
					int timeMax = Integer.parseInt(player.getSessionVar("bot_timeMax"));
					Location loc = (Location) e.getArgs()[0];

					Map<Byte, Integer[]> params = style.get(classId);
					byte maxHs, maxHc, maxFace, sex;
					if(params == null)
					{
						Functions.sendSysMessage(player, "No class params, use default");
						maxHs = 5;
						maxHc = 3;
						maxFace = 3;
						sex = (byte) Rnd.get(0, 1);
					}
					else
					{
						sex = (byte) Rnd.get(0, 1);
						Integer[] p;
						if(params.containsKey(sex))
							p = params.get(sex);
						else
							p = params.get((byte) (sex == 1 ? 0 : 1));

						maxHs = p[0].byteValue();
						maxHc = p[1].byteValue();
						maxFace = p[2].byteValue();
					}

					byte hs = (byte) Rnd.get(maxHs);
					byte hc = (byte) Rnd.get(maxHc);
					byte f = (byte) Rnd.get(maxFace);

					FakePlayer fakePlayer = FakePlayersTable.createFakePlayer(classId, sex, hs, hc, f, timeMin, timeMax);
					if(fakePlayer == null)
					{
						Functions.sendSysMessage(player, "Can't create fake player!");
						return;
					}

					fakePlayer.setXYZInvisible(loc.getX(), loc.getY(), loc.getZ());
					fakePlayer.setHeading(Rnd.get(65535));
					fakePlayer.spawnMe();
					FakePlayersTable.addFakePlayer(fakePlayer);
					logGM.info("Spawn fake player: " + fakePlayer + " at " + fakePlayer.getLoc() + " despawn after: " + ((fakePlayer.getDespawnTime() - System.currentTimeMillis()) / 60000) + " min");
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}
