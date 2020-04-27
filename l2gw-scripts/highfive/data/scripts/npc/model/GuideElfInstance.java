package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:43:04
 */
public class GuideElfInstance extends NewbieHelperInstance
{
	public GuideElfInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		myname = "guide_elf_roios";
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
					showPage(player, "guide_elf_roios_q0255_04.htm");
				}
				else if(reply == 11)
				{
					showPage(player, "guide_elf_roios_q0255_04a.htm");
				}
				else if(reply == 12)
				{
					showPage(player, "guide_elf_roios_q0255_04b.htm");
				}
				else if(reply == 13)
				{
					showPage(player, "guide_elf_roios_q0255_04c.htm");
				}
				else if(reply == 14)
				{
					showPage(player, "guide_elf_roios_q0255_04d.htm");
				}
				else if(reply == 15)
				{
					showPage(player, "guide_elf_roios_q0255_04e.htm");
				}
				else if(reply == 16)
				{
					showPage(player, "guide_elf_roios_q0255_04f.htm");
				}
				else if(reply == 17)
				{
					showPage(player, "guide_elf_roios_q0255_04g.htm");
				}
				else if(reply == 18)
				{
					showPage(player, "guide_elf_roios_q0255_04h.htm");
				}
				else if(reply == 31)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(46926, 51511, -2977, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 32)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44995, 51706, -2803, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 33)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45727, 51721, -2803, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 34)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(42812, 51138, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 35)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45487, 46511, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 36)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(47401, 51764, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 37)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(42971, 51372, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 38)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(47595, 51569, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 39)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45778, 46534, -2996, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 40)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44476, 47153, -2984, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 41)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(42700, 50057, -2984, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 42)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(42766, 50037, -2984, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 43)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44683, 46952, -2981, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 44)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44667, 46896, -2982, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 45)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45725, 52105, -2795, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 46)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44823, 52414, -2795, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 47)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45000, 52101, -2795, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 48)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45919, 52414, -2795, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 49)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(44692, 52261, -2795, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 50)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(47780, 49568, -2983, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 51)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(47912, 50170, -2983, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 52)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(47868, 50167, -2983, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 53)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(28928, 74248, -3773, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 54)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(43673, 49683, -3046, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 55)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45610, 49008, -3059, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 56)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(50592, 54986, -3376, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 57)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(42978, 49115, -2994, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 58)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(46475, 50495, -3058, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 59)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(45859, 50827, -3058, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 60)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(51210, 82474, -3283, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				else if(reply == 61)
				{
					player.radar.deleteAll(2);
					player.radar.showRadar(49262, 53607, -3216, 2);
					showPage(player, "guide_elf_roios_q0255_05.htm");
				}
				return;
			}
		}
		super.onBypassFeedback(player, command);
	}
}
