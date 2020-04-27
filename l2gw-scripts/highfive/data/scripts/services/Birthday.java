package services;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.mail.Letter;
import ru.l2gw.gameserver.model.mail.LetterAttach;
import ru.l2gw.gameserver.model.mail.MailController;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.ItemTable;

import java.util.Calendar;

/**
 * @author rage
 * @date 07.10.2010 16:51:38
 */
public class Birthday extends Functions implements ScriptFile
{
	public void onLoad()
	{
		_log.info("Loaded Service: Birthday");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public static void OnPlayerEnter(L2Player player)
	{
		Calendar create = Calendar.getInstance();
		create.setTimeInMillis(player.getBirthday());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());

		int daysDiff = create.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR);
		if(daysDiff <= 7 && daysDiff > 1)
			player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_S1_DAYS_UNTIL_YOUR_CHARACTERS_BIRTHDAY_ON_THAT_DAY_YOU_CAN_OBTAIN_A_SPECIAL_GIFT_FROM_THE_GATEKEEPER_IN_ANY_VILLAGE).addNumber(daysDiff));

		if(isBirthdayTime(player) && !isGiftReceivedToday(player))
		{
			Letter letter = new Letter();
			letter.receiverId = player.getObjectId();
			letter.receiverName = player.getName();
			letter.senderId = 1;
			letter.senderName = new CustomMessage("birthday.npc", player).toString();
			letter.subject = new CustomMessage("birthday.title", player).toString();
			letter.message = new CustomMessage("birthday.text", player).toString();
			letter.price = 0;
			letter.unread = 1;
			letter.system = 3;
			create.set(Calendar.YEAR, now.get(Calendar.YEAR));
			letter.expire = (int) (create.getTimeInMillis() / 1000) + 14 * 24 * 60 * 60;

			LetterAttach attach = new LetterAttach();
			L2ItemInstance item = ItemTable.getInstance().createItem("Birthday", 21594, 1, player);
			item.setOwnerId(player.getObjectId());
			item.setLocation(L2ItemInstance.ItemLocation.MAILBOX);
			item.updateDatabase(true);
			attach.addItem(item);
			item = ItemTable.getInstance().createItem("Birthday", 22188, 3, player);
			item.setOwnerId(player.getObjectId());
			item.setLocation(L2ItemInstance.ItemLocation.MAILBOX);
			item.updateDatabase(true);
			attach.addItem(item);
			item = ItemTable.getInstance().createItem("Birthday", 21595, 1, player);
			item.setOwnerId(player.getObjectId());
			item.setLocation(L2ItemInstance.ItemLocation.MAILBOX);
			item.updateDatabase(true);
			attach.addItem(item);

			MailController.getInstance().sendMail(letter, attach);
			player.setVar("bd_gift", now.get(Calendar.YEAR));
			player.sendPacket(Msg.ExNotifyBirthDay);
			player.sendPacket(Msg.YOUR_BIRTHDAY_GIFT_HAS_ARRIVED_YOU_CAN_OBTAIN_IT_FROM_THE_GATEKEEPER_IN_ANY_VILLAGE);
		}
	}

	private static boolean isBirthdayTime(L2Player player)
	{
		if(player.getBirthday() == 0)
			return false;

		Calendar create = Calendar.getInstance();
		create.setTimeInMillis(player.getBirthday());
		int cYear = create.get(Calendar.YEAR);
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		create.set(Calendar.YEAR, now.get(Calendar.YEAR));
		long bday = create.getTimeInMillis();
		return cYear != now.get(Calendar.YEAR) && bday <= now.getTimeInMillis() && bday + 14 * 24 * 60 * 60000L > now.getTimeInMillis();
	}

	private static boolean isGiftReceivedToday(L2Player player)
	{
		int lastYear = player.getVarInt("bd_gift");
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		return lastYear == now.get(Calendar.YEAR);
	}
}
