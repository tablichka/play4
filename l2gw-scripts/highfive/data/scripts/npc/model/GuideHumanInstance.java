package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:45:49
 */
public class GuideHumanInstance extends NewbieHelperInstance
{
	public GuideHumanInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_human_cnacelot";
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
					showPage(player, "guide_human_cnacelot_q0255_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_human_cnacelot_q0255_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_human_cnacelot_q0255_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_human_cnacelot_q0255_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_human_cnacelot_q0255_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_human_cnacelot_q0255_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_human_cnacelot_q0255_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_human_cnacelot_q0255_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_human_cnacelot_q0255_04h.htm");
				}
				else if(reply == 19)
				{
					showPage(player, "guide_human_cnacelot_q0255_04i.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84108, 244604, -3729, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82236, 241573, -3728, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82515, 241221, -3728, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82319, 244709, -3727, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82659, 244992, -3717, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-86114, 244682, -3727, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-86328, 244448, -3724, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-86322, 241215, -3727, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-85964, 240947, -3727, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-85026, 242689, -3729, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-83789, 240799, -3717, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84204, 240403, -3717, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-86385, 243267, -3717, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-86733, 242918, -3717, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84516, 245449, -3714, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84729, 245001, -3726, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84965, 245222, -3726, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84981, 244764, -3726, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-85186, 245001, -3726, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-83326, 242964, -3718, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-83020, 242553, -3718, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-83175, 243065, -3718, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82809, 242751, -3718, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-81895, 243917, -3721, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-81840, 243534, -3721, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-81512, 243424, -3720, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 57)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84436, 242793, -3729, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 58)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-78939, 240305, -3443, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 59)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-85301, 244587, -3725, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 60)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-83163, 243560, -3728, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 61)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-97131, 258946, -3622, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 62)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-114685, 222291, -2925, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 63)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-84057, 242832, -3729, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 64)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-100332, 238019, -3573, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				else if(reply == 65)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(-82041, 242718, -3725, 2);
					showPage(player, "guide_human_cnacelot_q0255_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
