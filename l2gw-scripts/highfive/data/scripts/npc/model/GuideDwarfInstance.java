package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:39:10
 */
public class GuideDwarfInstance extends NewbieHelperInstance
{
	public GuideDwarfInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_dwarf_gullin";
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
					showPage(player, "guide_dwarf_gullin_q0255_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_dwarf_gullin_q0255_04h.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115072, -178176, -906, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(117847, -182339, -1537, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116617, -184308, -1569, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(117826, -182576, -1537, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116378, -184308, -1571, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115183, -176728, -791, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114969, -176752, -790, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(117366, -178725, -1118, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(117378, -178914, -1120, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116226, -178529, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116190, -178441, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116016, -178615, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116190, -178615, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116103, -178407, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116103, -178653, -948, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115468, -182446, -1434, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115315, -182155, -1444, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115271, -182692, -1445, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115900, -177316, -915, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116268, -177524, -914, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115741, -181645, -1344, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116192, -181072, -1344, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115205, -180024, -870, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114716, -180018, -871, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114832, -179520, -871, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115717, -183488, -1483, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 57)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115618, -183265, -1483, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 58)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114348, -178537, -813, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 59)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114990, -177294, -854, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 60)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114426, -178672, -812, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 61)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(114409, -178415, -812, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 62)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(117061, -181867, -1413, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 63)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116164, -184029, -1507, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 64)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(115563, -182923, -1448, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 65)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(112656, -174864, -611, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				else if(reply == 66)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(116852, -183595, -1566, 2);
					showPage(player, "guide_dwarf_gullin_q0255_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
