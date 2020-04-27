package npc.model;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.StringTokenizer;

/**
 * User: ic
 * Date: 05.07.2010
 */
public class FortuneTellerInstance extends L2NpcInstance
{
	private static String _path = "data/html/default/";
	private static int price = 1000;

	private static String[] fortunes = {
			"A matter related to a close friend can isolate you, keep staying on the right path.",
			"Luck enters into your work, hobby, family and love.",
			"Help each other among close friends.",
			"Pay special attention when meeting or talking to people as relationships may go amiss.",
			"There will be a shocking incident.",
			"Refrain from getting involved in others' business, try to be loose as a goose.",
			"Be firm and carefully scrutinize circumstances even when things are difficult.",
			"Don't be evasive to accept new findings or experiences.",
			"Be responsible with your tasks but do not hesitate to ask for colleagues' help.",
			"Greed by wanting to take wealth may bring unfortunate disaster."
	};
	public FortuneTellerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command
		if(actualCommand.equalsIgnoreCase("fortune"))
		{

			if(player.getItemCountByItemId(57) < price)
			{
				NpcHtmlMessage html;
				html = new NpcHtmlMessage(player, this, _path + getNpcId()+"-noadena.htm", 0);
				player.sendPacket(html);
				return;
			}

			player.destroyItemByItemId("FortuneTeller", 57, price, this, true);
			NpcHtmlMessage html;
			html = new NpcHtmlMessage(player, this, _path + getNpcId()+"-fortune.htm", 0);
			html.replace("%fortune%", fortunes[Rnd.get(fortunes.length)]);
			player.sendPacket(html);
		}
		else
			super.onBypassFeedback(player, command);
	}

}

