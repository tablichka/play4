package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PriestOfBlessingInstance extends L2NpcInstance
{
	private static class Hourglass
	{
		public int minLevel;
		public int maxLevel;
		public int itemPrice;
		public int[] itemId;

		public Hourglass(int min, int max, int price, int[] id)
		{
			minLevel = min;
			maxLevel = max;
			itemPrice = price;
			itemId = id;
		}
	}

	private static List<Hourglass> hourglassList = new ArrayList<Hourglass>();
	static
	{
		hourglassList.add(new Hourglass(1, 19, 4000, new int[] {17095,17096,17097,17098,17099})); // 1-19
		hourglassList.add(new Hourglass(20, 39, 30000, new int[] {17100,17101,17102,17103,17104})); // 20-39
		hourglassList.add(new Hourglass(40, 51, 110000, new int[] {17105,17106,17107,17108,17109})); // 40-51
		hourglassList.add(new Hourglass(52, 60, 310000, new int[] {17110,17111,17112,17113,17114})); // 52-60
		hourglassList.add(new Hourglass(61, 75, 970000, new int[] {17115,17116,17117,17118,17119})); // 61-75
		hourglassList.add(new Hourglass(76, 79, 2160000, new int[] {17120,17121,17122,17123,17124})); // 76-79
		hourglassList.add(new Hourglass(80, 85, 5000000, new int[] {17125,17126,17127,17128,17129})); // 80-85
	}

	public PriestOfBlessingInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(actualCommand.equals("BuyHourglass"))
		{
			int val = Integer.parseInt(st.nextToken());
			Hourglass hg = getHourglass(player);
			int itemId = getHourglassId(hg);
			buyLimitedItem(player, "hourglass" + hg.minLevel + hg.maxLevel, itemId, val);
		}
		else if(actualCommand.equals("BuyVoice"))
		{
			buyLimitedItem(player, "nevitsVoice" + player.getAccountName(), 17094, 100000);
		}
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(val == 0)
		{
			Hourglass hg = getHourglass(player);
			NpcHtmlMessage html = new NpcHtmlMessage(player, this);
			html.setFile(getHtmlPath(getNpcId(), val, player.getKarma()));
			html.replace("%price%", String.valueOf(hg.itemPrice));
			html.replace("%priceBreak%", Util.formatAdena(hg.itemPrice));
			html.replace("%minLvl%", String.valueOf(hg.minLevel));
			html.replace("%maxLvl%", String.valueOf(hg.maxLevel));
			player.sendPacket(html);
			return;
		}
		super.showChatWindow(player, val);
	}

	private static Hourglass getHourglass(L2Player player)
	{
		for(Hourglass hg : hourglassList)
			if(player.getLevel() >= hg.minLevel && player.getLevel() <= hg.maxLevel)
				return hg;

		return null;
	}

	private static int getHourglassId(Hourglass hg)
	{
		int id = hg.itemId[Rnd.get(hg.itemId.length)];
		return id;
	}

	private void buyLimitedItem(L2Player player, String var, int itemId, int price)
	{
		long _remaining_time;
		long _reuse_time = 20 * 60 * 60 * 1000;
		long _curr_time = System.currentTimeMillis();
		String _last_use_time = player.getVar(var);

		if(_last_use_time != null)
			_remaining_time = _curr_time - Long.parseLong(_last_use_time);
		else
			_remaining_time = _reuse_time;

		if(_remaining_time >= _reuse_time)
		{
			if(player.reduceAdena("PriestOfBlessing", price, this, true))
			{
				Functions.addItem(player, itemId, 1);
				player.setVar(var, String.valueOf(_curr_time));
			}
			else
				player.sendPacket(new SystemMessage(SystemMessage._2_UNITS_OF_THE_ITEM_S1_IS_REQUIRED).addNumber(price));
		}
		else
		{
			int hours = (int) (_reuse_time - _remaining_time) / 3600000;
			int minutes = (int) (_reuse_time - _remaining_time) % 3600000 / 60000;
			if(hours > 0)
				player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_S1_HOURSS_AND_S2_MINUTES_REMAINING_UNTIL_THE_TIME_WHEN_THE_ITEM_CAN_BE_PURCHASED).addNumber(hours).addNumber(minutes));
			else if(minutes > 0)
				player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_S1_MINUTES_REMAINING_UNTIL_THE_TIME_WHEN_THE_ITEM_CAN_BE_PURCHASED).addNumber(minutes));
			else if(player.reduceAdena("PriestOfBlessing", price, this, true))
			{
				Functions.addItem(player, itemId, 1);
				player.setVar(var, String.valueOf(_curr_time));
			}
			else
				player.sendPacket(new SystemMessage(SystemMessage._2_UNITS_OF_THE_ITEM_S1_IS_REQUIRED).addNumber(price));
		}
	}
}