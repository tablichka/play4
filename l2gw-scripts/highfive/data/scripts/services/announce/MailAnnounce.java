package services.announce;

import org.apache.commons.lang3.StringUtils;
import ru.l2gw.commons.arrays.ArrayUtils;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.mail.Letter;
import ru.l2gw.gameserver.model.mail.LetterAttach;
import ru.l2gw.gameserver.model.mail.MailController;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author: rage
 * @date: 02.11.12 13:36
 */
public class MailAnnounce extends Functions implements ScriptFile
{
	private static final String SQL_SELECT_ANNOUNCE = "SELECT * FROM mail_announce WHERE (hwid = ? or char_name like ?) and status = 0";
	private static final String SQL_UPDATE_ANNOUNCE = "UPDATE mail_announce SET status = 1 WHERE id = ?";

	@Override
	public void onLoad()
	{
		_log.info("Load Service: Mail Announce state: " + (Config.MAIL_ANNOUNCE_ENABLED ? "enabled" : "disabled"));
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(!Config.MAIL_ANNOUNCE_ENABLED)
			return;

		Map<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/services/announce/html/mail.htm", player, false));

		if(tpls == null || tpls.size() < 1)
		{
			_log.warn("Mail Announce: has no template file: data/scripts/services/announce/html/mail.htm");
			return;
		}

		Connection conn = null;
		PreparedStatement stmt = null, stmt2;
		ResultSet rs = null;
		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_ANNOUNCE);
			stmt.setString(1, player.getLastHWID());
			stmt.setString(2, player.getName());
			rs = stmt.executeQuery();
			String tpl = tpls.get(0);

			while(rs.next())
			{
				tpl = tpl.replace("<?char_name?>", player.getName());
				tpl = tpl.replace("<?field1?>", rs.getString("field1"));
				tpl = tpl.replace("<?field2?>", rs.getString("field2"));
				tpl = tpl.replace("<?field3?>", rs.getString("field3"));
				tpl = tpl.replace("<?field4?>", rs.getString("field4"));
				tpl = tpl.replace("<?field5?>", rs.getString("field5"));
				int id = rs.getInt("id");

				String item_attach = rs.getString("item_attach");

				stmt2 = conn.prepareStatement(SQL_UPDATE_ANNOUNCE);
				stmt2.setInt(1, id);
				stmt2.executeUpdate();

				DbUtils.closeQuietly(stmt2);

				Letter letter = new Letter();
				letter.receiverId = player.getObjectId();
				letter.receiverName = player.getName();
				letter.senderId = 1;
				letter.senderName = "";
				letter.subject = tpls.get(1);
				letter.message = tpl;
				letter.price = 0;
				letter.unread = 1;
				letter.system = 1;
				letter.expire = (int) (System.currentTimeMillis() / 1000) + 14 * 24 * 60 * 60;

				LetterAttach attach = null;

				if(StringUtils.isNotBlank(item_attach))
				{
					int items[] = ArrayUtils.toIntArray(item_attach);
					if(items.length > 1)
					{
						attach = new LetterAttach();

						for(int i = 0; i < items.length; i +=2)
						{
							L2ItemInstance item = ItemTable.getInstance().createItem("MailAnnounce", items[i], items[i + 1], player);
							item.setOwnerId(player.getObjectId());
							item.setLocation(L2ItemInstance.ItemLocation.MAILBOX);
							item.updateDatabase(true);
							attach.addItem(item);
						}
					}
				}

				MailController.getInstance().sendMail(letter, attach);
			}
		}
		catch (Exception e)
		{
			_log.error("Mail Announce: error while loading announce data: " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(conn, stmt, rs);
		}
	}
}