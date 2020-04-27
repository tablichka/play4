package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author admin
 * @date 17.11.2010 18:12:12
 */
public class GuideDelfInstance extends NewbieHelperInstance
{
	public GuideDelfInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_delf_frankia";
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(command.startsWith("menu_select "))
		{
			String[] var = command.replace("menu_select ", "").split(" ");
			int ask = Integer.parseInt(var[0]);
			int reply = Integer.parseInt(var[1]);

			if(ask == 255)
			{
				if(reply == 10)
				{
					showPage(player, "guide_delf_frankia_q0255_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_delf_frankia_q0255_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_delf_frankia_q0255_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_delf_frankia_q0255_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_delf_frankia_q0255_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_delf_frankia_q0255_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_delf_frankia_q0255_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_delf_frankia_q0255_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_delf_frankia_q0255_04h.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(9670, 15537, -4574, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(15120, 15656, -4376, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(17306, 13592, -3724, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(15272, 16310, -4377, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(6449, 19619, -3694, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-15404, 71131, -3445, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(7496, 17388, -4377, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(17102, 13002, -3743, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(6532, 19903, -3693, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-15648, 71405, -3451, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(7644, 18048, -4377, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-1301, 75883, -3566, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-1152, 76125, -3566, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10580, 17574, -4554, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(12009, 15704, -4554, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(11951, 15661, -4554, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10761, 17970, -4554, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10823, 18013, -4554, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(11283, 14226, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10447, 14620, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(11258, 14431, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10344, 14445, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10315, 14293, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(10775, 14190, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(11235, 14078, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(11012, 14128, -4242, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 57)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(13380, 17430, -4542, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 58)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(13464, 17751, -4541, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 59)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(13763, 17501, -4542, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 60)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-44225, 79721, -3652, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 61)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-44015, 79683, -3652, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 62)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(25856, 10832, -3724, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 63)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(12328, 14947, -4574, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 64)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(13081, 18444, -4573, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				else if(reply == 65)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(12311, 17470, -4574, 2);
					showPage(player, "guide_delf_frankia_q0255_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
