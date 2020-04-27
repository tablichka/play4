package services;

import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.commons.utils.StringUtil;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Rename extends Functions implements ScriptFile
{
	private static final org.apache.commons.logging.Log log = LogFactory.getLog("service");
	public static L2Object self;
	public static L2Object npc;
	private static int RENAME_ITEM = Config.SERVICES_CHANGE_NICK_ITEM;
	private static int RENAME_PRICE = Config.SERVICES_CHANGE_NICK_PRICE;

	public static void rename_page()
	{
		L2Player player = (L2Player) self;
		String append = "!Rename";
		append += "<br>";
		append += "<font color=\"LEVEL\">" + new CustomMessage("scripts.services.Rename.RenameFor", self).addString(Util.formatAdena(RENAME_PRICE)).addItemName(RENAME_ITEM) + "</font>";
		append += "<table>";
		append += "<tr><td>" + new CustomMessage("scripts.services.Rename.NewName", self) + ": <edit var=\"new_name\" width=80></td></tr>";
		append += "<tr><td><button value=\"" + new CustomMessage("scripts.services.Rename.RenameButton", self) + "\" action=\"bypass -h scripts_services.Rename:rename $new_name\" width=80 height=15></td></tr>";
		append += "</table>";
		show(append, player);
	}

	public static void rename(String[] args)
	{
		L2Player player = (L2Player) self;
		if(args.length != 1)
		{
			show(new CustomMessage("scripts.services.Rename.incorrectinput", player), player);
			return;
		}

		String name = args[0];
		if(!StringUtil.isMatchingRegexp(name, Config.EnTemplate))
			if(!Config.Lang.equalsIgnoreCase("ru") || !StringUtil.isMatchingRegexp(name, Config.RusTemplate))
			{
				show(new CustomMessage("scripts.services.Rename.incorrectinput", player), player);
				return;
			}

		if(getItemCount(player, RENAME_ITEM) < RENAME_PRICE)
		{
			if(RENAME_ITEM == 57)
				player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			else
				player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
			return;
		}

		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;

		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("SELECT char_name FROM characters WHERE char_name LIKE ?");
			offline.setString(1, name);
			rs = offline.executeQuery();
			if(rs.next())
			{
				show(new CustomMessage("scripts.services.Rename.Thisnamealreadyexists", player), player);
				return;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			show(new CustomMessage("common.Error", player), player);
			return;
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}

		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("UPDATE characters SET char_name = ? WHERE obj_Id = ?");
			offline.setString(1, name);
			offline.setInt(2, player.getObjectId());
			offline.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			show(new CustomMessage("common.Error", player), player);
			return;
		}
		finally
		{
			DbUtils.closeQuietly(con, offline);
		}

		removeItem(player, RENAME_ITEM, RENAME_PRICE);

		String oldName = player.getName();
		L2World.removeObject(player);
		player.decayMe();
		player.setName(name);
		player.spawnMe();
		player.broadcastUserInfo(true);
		log.info("Player " + oldName + " renamed to " + name);
		show(new CustomMessage("scripts.services.Rename.changedname", player).addString(oldName).addString(name), player);
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Nick change");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}