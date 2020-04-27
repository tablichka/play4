package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:48:46
 */
public class GuideKreniskInstance extends NewbieHelperInstance
{
	public GuideKreniskInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_krenisk";
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
					showPage(player, "guide_krenisk_q0209_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_krenisk_q0209_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_krenisk_q0209_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_krenisk_q0209_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_krenisk_q0209_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_krenisk_q0209_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_krenisk_q0209_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_krenisk_q0209_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_krenisk_q0209_04h.htm");
				}
				else if(reply == 19)
				{
					showPage(player, "guide_krenisk_q0209_04i.htm");
				}
				else if(reply == 20)
				{
					showPage(player, "guide_krenisk_q0209_04j.htm");
				}
				else if(reply == 21)
				{
					showPage(player, "guide_krenisk_q0209_04k.htm");
				}
				else if(reply == 22)
				{
					showPage(player, "guide_krenisk_q0209_04l.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-116879, 46591, 380, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119378, 49242, 22, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119774, 49245, 22, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119830, 51860, -787, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119362, 51862, -780, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-112872, 46850, 68, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-112352, 47392, 68, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-110544, 49040, -1124, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-110536, 45162, -1132, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-115888, 43568, 524, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-115486, 43567, 525, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-116920, 47792, 464, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-116749, 48077, 462, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117153, 48075, 463, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119104, 43280, 559, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119104, 43152, 559, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117056, 43168, 559, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117060, 43296, 559, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118192, 42384, 838, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117968, 42384, 838, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118132, 42788, 723, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118028, 42788, 720, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-114802, 44821, 524, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-114975, 44658, 524, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-114801, 45031, 525, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120432, 45296, 416, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 57)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120706, 45079, 419, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 58)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120356, 45293, 416, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 59)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120604, 44960, 423, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 60)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120294, 46013, 384, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 61)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120157, 45813, 355, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 62)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120158, 46221, 354, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 63)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120400, 46921, 415, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 64)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120407, 46755, 423, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 65)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-120442, 47125, 422, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 66)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118720, 48062, 473, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 67)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118918, 47956, 474, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 68)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118527, 47955, 473, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 69)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117605, 48079, 472, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 70)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-117824, 48080, 476, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 71)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118030, 47930, 465, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 72)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-119221, 46981, 380, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				else if(reply == 73)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-118080, 42835, 720, 2);
					showPage(player, "guide_krenisk_q0209_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
