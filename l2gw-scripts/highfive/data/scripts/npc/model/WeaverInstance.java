package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

import java.util.StringTokenizer;

/**
 * User: ic
 * Date: 03.07.2010
 */
public class WeaverInstance extends L2NpcInstance
{
	private static String _path = "data/html/default/";

	private static int[] MAGIC_PINS = {13898, 13899, 13900, 13901}; // C,B,A,S grades sealed pins
	private static int[] MAGIC_POUCHES = {13918, 13919, 13920, 13921}; // C,B,A,S grades sealed pouches
	private static int[] MAGIC_RUNES = {14902, 14903}; // A,S grades sealed runes
	private static int[] MAGIC_ORNAMENTS = {14904, 14905}; // A,S grades sealed runes

	private static int priceC = 3200;
	private static int priceB = 11800;
	private static int priceA = 26500;
	private static int priceS = 136600;

	public WeaverInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(player.isCursedWeaponEquipped())
			return;

		if(actualCommand.equalsIgnoreCase("unseal"))
		{
			int option = Integer.parseInt(st.nextToken());
			int item = checkForRequiredItems(player, option);
			int price = checkForMoney(player, option);
			if(item == 0)
			{
				showChatWindow(player, _path + "weaver_no.htm");
				return;
			}
			if(price == 0)
			{
				showChatWindow(player, _path + "weaver_nomoney.htm");
				return;
			}
			if(player.getInventory().slotsLeft() < 10)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOUR_INVENTORY_IS_FULL));
				player.sendActionFailed();
				return;
			}

			// All okay, player has item, money and enough slots for exchange.
			int dstItem = selectDstItem(player, option);
			if(dstItem > 0)
			{
				player.destroyItemByItemId("WeaverExchange", item, 1, this, true);
				player.destroyItemByItemId("WeaverExchange", 57, price, this, true);
				player.addItem("WeaverExchange", dstItem, 1, this, true);
				showChatWindow(player, _path + "weaver_ok.htm");
			}
		}
		else
			super.onBypassFeedback(player, command);
	}

	private int checkForRequiredItems(L2Player player, int option)
	{
		int rItem = 0;

		switch(option)
		{
			case 11:
			case 12:
			case 13:
			case 14:
				rItem = MAGIC_PINS[option - 11];
				break;
			case 21:
			case 22:
			case 23:
			case 24:
				rItem = MAGIC_POUCHES[option - 21];
				break;
			case 31:
			case 32:
				rItem = MAGIC_RUNES[option - 31];
				break;
			case 41:
			case 42:
				rItem = MAGIC_ORNAMENTS[option - 41];
				break;
		}

		if(player.getItemCountByItemId(rItem) > 0)
			return rItem;

		return 0;
	}

	private int selectDstItem(L2Player player, int option)
	{
		int dItem = 0;

		switch(option)
		{
			case 11:
				dItem = Rnd.get(13902,13905);
				break;
			case 12:
				dItem = Rnd.get(13906,13909);
				break;
			case 13:
				dItem = Rnd.get(13910,13913);
				break;
			case 14:
				dItem = Rnd.get(13914,13917);
				break;
			case 21:
				dItem = Rnd.get(13922,13925);
				break;
			case 22:
				dItem = Rnd.get(13926,13929);
				break;
			case 23:
				dItem = Rnd.get(13930,13933);
				break;
			case 24:
				dItem = Rnd.get(13934,13937);
				break;
			case 31:
				dItem = Rnd.get(14906,14909);
				break;
			case 32:
				dItem = Rnd.get(14910,14913);
				break;
			case 41:
				dItem = Rnd.get(14914,14917);
				break;
			case 42:
				dItem = Rnd.get(14918,14921);
				break;
		}

		return dItem;
	}

	private int checkForMoney(L2Player player, int option)
	{
		int rMoney = 0;
		long playerMoney = player.getItemCountByItemId(57);

		switch(option)
		{
			case 11:
			case 21:
				rMoney = priceC;
				break;
			case 12:
			case 22:
				rMoney = priceB;
				break;

			case 13:
			case 23:
			case 31:
			case 41:
				rMoney = priceA;
				break;
			case 14:
			case 24:
			case 32:
			case 42:
				rMoney = priceS;
				break;
		}

		if(playerMoney >= rMoney)
			return rMoney;

		return 0;
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return true;
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

}
