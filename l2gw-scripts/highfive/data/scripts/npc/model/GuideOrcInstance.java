package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:52:08
 */
public class GuideOrcInstance extends NewbieHelperInstance
{
	public GuideOrcInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_orc_tanai";
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
					showPage(player, "guide_orc_tanai_q0255_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_orc_tanai_q0255_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_orc_tanai_q0255_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_orc_tanai_q0255_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_orc_tanai_q0255_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_orc_tanai_q0255_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_orc_tanai_q0255_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_orc_tanai_q0255_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_orc_tanai_q0255_04h.htm");
				}
				else if(reply == 19)
				{
					showPage(player, "guide_orc_tanai_q0255_04i.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45264, -112512, -235, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46576, -117311, -242, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-47360, -113791, -237, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-47360, -113424, -235, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45744, -117165, -236, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46528, -109968, -250, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45808, -110055, -255, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45731, -113844, -237, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45728, -113360, -237, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45952, -114784, -199, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45952, -114496, -199, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45863, -112621, -200, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45864, -112540, -199, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43264, -112532, -220, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43910, -115518, -194, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43950, -115457, -194, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-44416, -111486, -222, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43926, -111794, -222, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43109, -113770, -221, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-43114, -113404, -221, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46768, -113610, -3, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46802, -114011, -112, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46247, -113866, -21, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-46808, -113184, -112, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-45328, -114736, -237, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-44624, -111873, -238, 2);
					showPage(player, "guide_orc_tanai_q0255_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
