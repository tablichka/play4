package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 16:57:16
 */
public class NewbieHelperInstance extends NewbieGuideInstance
{
	public NewbieHelperInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(val == 0)
		{
			QuestState qs = player.getQuestState("_255_Tutorial");
			if(qs == null)
			{
				super.showChatWindow(player, val);
				return;
			}

			if(!player.isQuestContinuationPossible(true))
				return;

			if(qs.getInt("t1") < 5 && player.getVarInt("NR41") >= 0)
			{
				if(!player.isMageClass())
				{
					qs.playTutorialVoice("tutorial_voice_026", 1000);
					qs.giveItems(5789, 200);
					qs.giveItems(8594, 2);
					qs.set("t1", 5);
					if(player.getLevel() <= 1)
						qs.addExpAndSp(68, 50);
					else
						qs.addExpAndSp(0, 50);
				}
				else if(player.isMageClass())
				{
					if(player.getActiveClass() == 49)
					{
						qs.playTutorialVoice("tutorial_voice_026", 1000);
						qs.giveItems(5789, 200);
					}
					else
					{
						qs.playTutorialVoice("tutorial_voice_027", 1000);
						qs.giveItems(5790, 100);
					}
					qs.giveItems(8594, 2);
					qs.set("t1", 5);
					if(player.getLevel() <= 1)
						qs.addExpAndSp(68, 50);
					else
						qs.addExpAndSp(0, 50);
				}

				if(player.getLevel() < 6)
				{
					if(player.getVarInt("NR41") % 10 == 1)
					{
						showPage(player, "newbie_guide_q0041_02.htm");

						if(player.getVarInt("NR41") % 100 / 10 == 0)
						{
							if(player.getLevel() >= 5)
							{
								qs.giveItems(57, 695);
								qs.addExpAndSp(3154, 127);
							}
							else if(player.getLevel() >= 4)
							{
								qs.giveItems(57, 1041);
								qs.addExpAndSp(4870, 195);
							}
							else if(player.getLevel() >= 3)
							{
								qs.giveItems(57, 1186);
								qs.addExpAndSp(5675, 227);
							}
							else
							{
								qs.giveItems(57, 1240);
								qs.addExpAndSp(5970, 239);
							}

							player.setVar("NR41", player.getVarInt("NR41") + 10);
						}
					}
					else if(getRace() == Race.human)
					{
						showPage(player, "newbie_guide_q0041_01a.htm");
						qs.showRadar(-84436, 242793, -3729, 2);
					}
					else if(getRace() == Race.elf)
					{
						showPage(player, "newbie_guide_q0041_01b.htm");
						qs.showRadar(42978, 49115, 2994, 2);
					}
					else if(getRace() == Race.darkelf)
					{
						showPage(player, "newbie_guide_q0041_01c.htm");
						qs.showRadar(25790, 10844, -3727, 2);
					}
					else if(getRace() == Race.orc)
					{
						showPage(player, "newbie_guide_q0041_01d.htm");
						qs.showRadar(-47360, -113791, -237, 2);
					}
					else if(getRace() == Race.dwarf)
					{
						showPage(player, "newbie_guide_q0041_01e.htm");
						qs.showRadar(112656, -174864, -611, 2);
					}
					else if(getRace() == Race.kamael)
					{
						showPage(player, "newbie_guide_q0041_01f.htm");
						qs.showRadar(-119378, 49242, 22, 2);
					}
				}
				else if(player.getLevel() < 10)
				{
					if(player.getVarInt("NR41") % 1000 / 100 == 1 && player.getVarInt("NR41") % 10000 / 1000 == 1)
					{
						if(player.getRace() == Race.human)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_05a.htm");
								qs.showRadar(-71384, 258304, -3109, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_05b.htm");
								qs.showRadar(-91008, 248016, -3568, 2);
							}
						}
						else if(player.getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_05c.htm");
							qs.showRadar(47595, 51569, -2996, 2);
						}
						else if(player.getRace() == Race.darkelf)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_05d.htm");
								qs.showRadar(10580, 17574, -4554, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_05e.htm");
								qs.showRadar(10775, 14190, -4242, 2);
							}
						}
						else if(player.getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_05f.htm");
							qs.showRadar(-46808, -113184, -112, 2);
						}
						else if(player.getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_05g.htm");
							qs.showRadar(115717, -183488, -1483, 2);
						}
						else if(player.getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_05h.htm");
							qs.showRadar(-118080, 42835, 720, 2);
						}

						if(player.getVarInt("NR41") % 100000 / 10000 == 0)
						{
							if(player.getLevel() >= 9)
							{
								qs.giveItems(57, 5563);
								qs.addExpAndSp(16851, 711);
							}
							else if(player.getLevel() >= 8)
							{
								qs.giveItems(57, 9290);
								qs.addExpAndSp(28806, 1207);
							}
							else if(player.getLevel() >= 7)
							{
								qs.giveItems(57, 11567);
								qs.addExpAndSp(36942, 1541);
							}
							else
							{
								qs.giveItems(57, 12928);
								qs.addExpAndSp(42191, 1753);
							}

							player.setVar("NR41", player.getVar("NR41") + 10000);
						}
					}
					else if(player.getVarInt("NR41") % 1000 / 100 == 1 && player.getVarInt("NR41") % 10000 / 1000 != 1)
					{
						if(getRace() == Race.human)
						{
							showPage(player, "newbie_guide_q0041_04a.htm");
							qs.showRadar(-82236, 241573, -3728, 2);
						}
						else if(getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_04b.htm");
							qs.showRadar(42812, 51138, -2996, 2);
						}
						else if(getRace() == Race.darkelf)
						{
							showPage(player, "newbie_guide_q0041_04c.htm");
							qs.showRadar(7644, 18048, -4377, 2);
						}
						else if(getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_04d.htm");
							qs.showRadar(-46802, -114011, -112, 2);
						}
						else if(getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_04e.htm");
							qs.showRadar(116103, -178407, -948, 2);
						}
						else if(getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_04f.htm");
							qs.showRadar(-119378, 49242, 22, 2);
						}
					}
					else
						showPage(player, "newbie_guide_q0041_03.htm");
				}
				else
				{
					showPage(player, "newbie_guide_q0041_06.htm");
					player.setVar("NR41", -1);
				}
			}
			else if(qs.getInt("t1") >= 5 && player.getVarInt("NR41") >= 0)
			{
				if(player.getLevel() < 6)
				{
					if((player.getVarInt("NR41") % 10) == 1)
					{
						showPage(player, "newbie_guide_q0041_08.htm");
						if(player.getVarInt("NR41") % 100 / 10 == 0)
						{
							if(player.getLevel() >= 5)
							{
								qs.giveItems(57, 695);
								qs.addExpAndSp(3154, 127);
							}
							else if(player.getLevel() >= 4)
							{
								qs.giveItems(57, 1041);
								qs.addExpAndSp(4870, 195);
							}
							else if(player.getLevel() >= 3)
							{
								qs.giveItems(57, 1186);
								qs.addExpAndSp(5675, 227);
							}
							else
							{
								qs.giveItems(57, 1240);
								qs.addExpAndSp(5970, 239);
							}

							player.setVar("NR41", player.getVarInt("NR41") + 10);
						}
					}
					else if(getRace() == Race.human)
					{
						showPage(player, "newbie_guide_q0041_07a.htm");
						qs.showRadar(-84436, 242793, -3729, 2);
					}
					else if(getRace() == Race.elf)
					{
						showPage(player, "newbie_guide_q0041_07b.htm");
						qs.showRadar(42978, 49115, 2994, 2);
					}
					else if(getRace() == Race.darkelf)
					{
						showPage(player, "newbie_guide_q0041_07c.htm");
						qs.showRadar(25790, 10844, -3727, 2);
					}
					else if(getRace() == Race.orc)
					{
						showPage(player, "newbie_guide_q0041_07d.htm");
						qs.showRadar(-47360, -113791, -237, 2);
					}
					else if(getRace() == Race.dwarf)
					{
						showPage(player, "newbie_guide_q0041_07e.htm");
						qs.showRadar(112656, -174864, -611, 2);
					}
					else if(getRace() == Race.kamael)
					{
						showPage(player, "newbie_guide_q0041_07f.htm");
						qs.showRadar(-119378, 49242, 22, 2);
					}
				}
				else if(player.getLevel() < 10)
				{
					if(((player.getVarInt("NR41") % 100000) / 10000) == 1)
					{
						showPage(player, "newbie_guide_q0041_09g.htm");
					}
					else if(player.getVarInt("NR41") % 1000 / 100 == 1 && player.getVarInt("NR41") % 10000 / 1000 == 1 && player.getVarInt("NR41") % 100000 / 10000 != 1)
					{
						if(player.getRace() == Race.human)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_10a.htm");
								qs.showRadar(-71384, 258304, -3109, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_10b.htm");
								qs.showRadar(-91008, 248016, -3568, 2);
							}
						}
						else if(player.getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_10c.htm");
							qs.showRadar(47595, 51569, -2996, 2);
						}
						else if(player.getRace() == Race.darkelf)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_10d.htm");
								qs.showRadar(10580, 17574, -4554, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_10e.htm");
								qs.showRadar(10775, 14190, -4242, 2);
							}
						}
						else if(player.getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_10f.htm");
							qs.showRadar(-46808, -113184, -112, 2);
						}
						else if(player.getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_10g.htm");
							qs.showRadar(115717, -183488, -1483, 2);
						}
						else if(player.getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_10h.htm");
							qs.showRadar(-118080, 42835, 720, 2);
						}

						if(player.getVarInt("NR41") % 100000 / 10000 == 0)
						{
							if(player.getLevel() >= 9)
							{
								qs.giveItems(57, 5563);
								qs.addExpAndSp(16851, 711);
							}
							else if(player.getLevel() >= 8)
							{
								qs.giveItems(57, 9290);
								qs.addExpAndSp(28806, 1207);
							}
							else if(player.getLevel() >= 7)
							{
								qs.giveItems(57, 11567);
								qs.addExpAndSp(36942, 1541);
							}
							else
							{
								qs.giveItems(57, 12928);
								qs.addExpAndSp(42191, 1753);
							}

							player.setVar("NR41", player.getVarInt("NR41") + 10000);
						}
					}
					else if(player.getVarInt("NR41") % 1000 / 100 == 1 && player.getVarInt("NR41") % 10000 / 1000 != 1)
					{
						if(getRace() == Race.human)
						{
							showPage(player, "newbie_guide_q0041_09a.htm");
							qs.showRadar(-82236, 241573, -3728, 2);
						}
						else if(getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_09b.htm");
							qs.showRadar(42812, 51138, -2996, 2);
						}
						else if(getRace() == Race.darkelf)
						{
							showPage(player, "newbie_guide_q0041_09c.htm");
							qs.showRadar(7644, 18048, -4377, 2);
						}
						else if(getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_09d.htm");
							qs.showRadar(-46802, -114011, -112, 2);
						}
						else if(getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_09e.htm");
							qs.showRadar(116103, -178407, -948, 2);
						}
						else if(getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_09f.htm");
							qs.showRadar(-119378, 49242, 22, 2);
						}
					}
					else
					{
						showPage(player, "newbie_guide_q0041_08.htm");
					}
				}
				else if(player.getLevel() < 15)
				{
					if(player.getVarInt("NR41") % 1000000 / 100000 == 1 && player.getVarInt("NR41") % 10000000 / 1000000 == 1)
					{
						showPage(player, "newbie_guide_q0041_15.htm");
					}
					else if(player.getVarInt("NR41") % 1000000 / 100000 == 1 && player.getVarInt("NR41") % 10000000 / 1000000 != 1)
					{
						if(getRace() == Race.human)
						{
							showPage(player, "newbie_guide_q0041_11a.htm");
							qs.showRadar(-84057, 242832, -3729, 2);
						}
						else if(getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_11b.htm");
							qs.showRadar(45859, 50827, -3058, 2);
						}
						else if(getRace() == Race.darkelf)
						{
							showPage(player, "newbie_guide_q0041_11c.htm");
							qs.showRadar(11258, 14431, -4242, 2);
						}
						else if(getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_11d.htm");
							qs.showRadar(-45863, -112621, -200, 2);
						}
						else if(getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_11e.htm");
							qs.showRadar(116268, -177524, -914, 2);
						}
						else if(getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_11f.htm");
							qs.showRadar(-125872, 38208, 1251, 2);
						}

						if(player.getVarInt("NR41") % 10000000 / 1000000 == 0)
						{
							if(player.getLevel() >= 14)
							{
								qs.giveItems(57, 13002);
								qs.addExpAndSp(62876, 2891);
							}
							else if(player.getLevel() >= 13)
							{
								qs.giveItems(57, 23468);
								qs.addExpAndSp(113137, 5161);
							}
							else if(player.getLevel() >= 12)
							{
								qs.giveItems(57, 31752);
								qs.addExpAndSp(152653, 6914);
							}
							else if(player.getLevel() >= 11)
							{
								qs.giveItems(57, 38180);
								qs.addExpAndSp(183128, 8242);
							}
							else
							{
								qs.giveItems(57, 43054);
								qs.addExpAndSp(206101, 9227);
							}

							player.setVar("NR41", player.getVarInt("NR41") + 1000000);
						}
					}
					else if(player.getVarInt("NR41") % 1000000 / 100000 != 1)
					{
						if(player.getRace() == Race.human)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_10a.htm");
								qs.showRadar(-71384, 258304, -3109, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_10b.htm");
								qs.showRadar(-91008, 248016, -3568, 2);
							}
						}
						else if(player.getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_10c.htm");
							qs.showRadar(47595, 51569, -2996, 2);
						}
						else if(player.getRace() == Race.darkelf)
						{
							if(!player.isMageClass())
							{
								showPage(player, "newbie_guide_q0041_10d.htm");
								qs.showRadar(10580, 17574, -4554, 2);
							}
							else
							{
								showPage(player, "newbie_guide_q0041_10e.htm");
								qs.showRadar(10775, 14190, -4242, 2);
							}
						}
						else if(player.getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_10f.htm");
							qs.showRadar(-46808, -113184, -112, 2);
						}
						else if(player.getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_10g.htm");
							qs.showRadar(115717, -183488, -1483, 2);
						}
						else if(player.getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_10h.htm");
							qs.showRadar(-118080, 42835, 720, 2);
						}
					}
				}
				else if(player.getLevel() < 18)
				{
					if(player.getVarInt("NR41") % 100000000 / 10000000 == 1 && player.getVarInt("NR41") % 1000000000 / 100000000 == 1)
					{
						showPage(player, "newbie_guide_q0041_13.htm");
						player.setVar("NR41", -1);
					}
					else if(player.getVarInt("NR41") % 100000000 / 10000000 == 1 && player.getVarInt("NR41") % 1000000000 / 100000000 != 1)
					{
						if(player.getLevel() >= 17)
						{
							qs.giveItems(57, 22996);
							qs.addExpAndSp(113712, 5518);
						}
						else if(player.getLevel() >= 16)
						{
							qs.giveItems(57, 10018);
							qs.addExpAndSp(208133, 42237);
						}
						else
						{
							qs.giveItems(57, 13648);
							qs.addExpAndSp(285670, 58155);
						}

						showPage(player, "newbie_guide_q0041_12.htm");
						player.setVar("NR41", -1);
					}
					else if(player.getVarInt("NR41") % 100000000 / 10000000 != 1)
					{
						if(getRace() == Race.human)
						{
							showPage(player, "newbie_guide_q0041_11a.htm");
							qs.showRadar(-84057, 242832, -3729, 2);
						}
						else if(getRace() == Race.elf)
						{
							showPage(player, "newbie_guide_q0041_11b.htm");
							qs.showRadar(45859, 50827, -3058, 2);
						}
						else if(getRace() == Race.darkelf)
						{
							showPage(player, "newbie_guide_q0041_11c.htm");
							qs.showRadar(11258, 14431, -4242, 2);
						}
						else if(getRace() == Race.orc)
						{
							showPage(player, "newbie_guide_q0041_11d.htm");
							qs.showRadar(-45863, -112621, -200, 2);
						}
						else if(getRace() == Race.dwarf)
						{
							showPage(player, "newbie_guide_q0041_11e.htm");
							qs.showRadar(116268, -177524, -914, 2);
						}
						else if(getRace() == Race.kamael)
						{
							showPage(player, "newbie_guide_q0041_11f.htm");
							qs.showRadar(-125872, 38208, 1251, 2);
						}
					}
				}
				else if(player.getClassId().getLevel() == 2)
				{
					showPage(player, "newbie_guide_q0041_13.htm");
					player.setVar("NR41", -1);
				}
				else
				{
					showPage(player, "newbie_guide_q0041_14.htm");
					player.setVar("NR41", -1);
				}
			}
			else
				super.showChatWindow(player, val);
		}
		else
			super.showChatWindow(player, val);
	}
}
