package quests._420_LittleWings;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * <hr><em>Квест</em> <strong>Little Wings</strong><hr>
 *
 * @author
 * @version CT2
 * @lastfix HellSinger
 */
public class _420_LittleWings extends Quest
{
	// NPCs
	private static final int Cooper = 30829;
	private static final int Cronos = 30610;
	private static final int Byron = 30711;
	private static final int Maria = 30608;
	private static final int Mimyu = 30747;
	private static final int Exarion = 30748;
	private static final int Zwov = 30749;
	private static final int Kalibran = 30750;
	private static final int Suzet = 30751;
	private static final int Shamhai = 30752;
	// MOBs
	private static final int[] mobs_Enchanted_Valey = {20589, 20590, 20591, 20592, 20593, 20594, 20595, 20596, 20597, 20598, 20599};
	private static final int Toad_Lord = 20231;
	private static final int Marsh_Spider = 20233;
	private static final int Leto_Lizardman_Warrior = 20580;
	private static final int Road_Scavenger = 20551;
	private static final int Breka_Orc_Overlord = 20270;
	private static final int Dead_Seeker = 20202;
	// ITEMs
	private static final short Coal = 1870;
	private static final short Charcoal = 1871;
	private static final short Silver_Nugget = 1873;
	private static final short Stone_of_Purity = 1875;
	private static final short GemstoneD = 2130;
	private static final short GemstoneC = 2131;
	private static final short Dragonflute_of_Wind = 3500;
	private static final short Dragonflute_of_Twilight = 3502;
	private static final short Hatchlings_Soft_Leather = 3912;
	private static final short Food_For_Hatchling = 4038;
	// Quest Items
	private static final short Fairy_Dust = 3499;
	private static final short Fairy_Stone = 3816;
	private static final short Deluxe_Fairy_Stone = 3817;
	private static final short Fairy_Stone_List = 3818;
	private static final short Deluxe_Fairy_Stone_List = 3819;
	private static final short Toad_Lord_Back_Skin = 3820;
	private static final short Juice_of_Monkshood = 3821;

	private static final short Scale_of_Drake_Exarion = 3822;
	private static final short Scale_of_Drake_Zwov = 3824;
	private static final short Scale_of_Drake_Kalibran = 3826;
	private static final short Scale_of_Wyvern_Suzet = 3828;
	private static final short Scale_of_Wyvern_Shamhai = 3830;

	private static final short Egg_of_Drake_Exarion = 3823;
	private static final short Egg_of_Drake_Zwov = 3825;
	private static final short Egg_of_Drake_Kalibran = 3827;
	private static final short Egg_of_Wyvern_Suzet = 3829;
	private static final short Egg_of_Wyvern_Shamhai = 3831;

	// Chances
	private static final int Toad_Lord_Back_Skin_Chance = 30;
	private static final int Egg_Chance = 50;
	private static final int Pet_Armor_Chance = 35;

	private static final short[][] Fairy_Stone_Items = {
			{Coal, 10},
			{Charcoal, 10},
			{GemstoneD, 1},
			{Silver_Nugget, 3},
			{Toad_Lord_Back_Skin, 10}};

	private static final short[][] Delux_Fairy_Stone_Items = {
			{Coal, 10},
			{Charcoal, 10},
			{GemstoneC, 1},
			{Stone_of_Purity, 1},
			{Silver_Nugget, 5},
			{Toad_Lord_Back_Skin, 20}};

	private static final int[][] wyrms = {
			{Leto_Lizardman_Warrior, Exarion, Scale_of_Drake_Exarion, Egg_of_Drake_Exarion},
			{Marsh_Spider, Zwov, Scale_of_Drake_Zwov, Egg_of_Drake_Zwov},
			{Road_Scavenger, Kalibran, Scale_of_Drake_Kalibran, Egg_of_Drake_Kalibran},
			{Breka_Orc_Overlord, Suzet, Scale_of_Wyvern_Suzet, Egg_of_Wyvern_Suzet},
			{Dead_Seeker, Shamhai, Scale_of_Wyvern_Shamhai, Egg_of_Wyvern_Shamhai}};

	public _420_LittleWings()
	{
		super(420, "_420_LittleWings", "Little Wings");

		addStartNpc(Cooper);
		addTalkId(Cronos, Mimyu, Byron, Maria);
		addKillId(Toad_Lord);
		for(int id : mobs_Enchanted_Valey)
			addKillId(id);
		for(int[] id : wyrms)
		{
			addTalkId(id[1]);
			addKillId(id[0]);
			addQuestItem(id[2], id[3]);
		}
		addQuestItem(Fairy_Stone, Deluxe_Fairy_Stone, Fairy_Stone_List, Deluxe_Fairy_Stone_List, Toad_Lord_Back_Skin, Juice_of_Monkshood);
	}

	private static int getWyrmScale(int npc_id)
	{
		for(int[] wyrm : wyrms)
			if(npc_id == wyrm[1])
				return wyrm[2];
		return 0;
	}

	private static int getWyrmEgg(int npc_id)
	{
		for(int[] wyrm : wyrms)
			if(npc_id == wyrm[1])
				return wyrm[3];
		return 0;
	}

	private static int isWyrmStoler(int npc_id)
	{
		for(int[] wyrm : wyrms)
			if(npc_id == wyrm[0])
				return wyrm[1];
		return 0;
	}

	private static int getNeededSkins(QuestState st)
	{
		if(st.haveQuestItems(Deluxe_Fairy_Stone_List))
			return 20;
		else if(st.haveQuestItems(Fairy_Stone_List))
			return 10;
		return 0;
	}

	private static boolean checkFairyStoneItems(QuestState st, short[][] item_list)
	{
		for(short[] _item : item_list)
			if(st.haveQuestItems(_item[0], _item[1]))
				return true;
		return false;
	}

	private static void takeFairyStoneItems(QuestState st, short[][] item_list)
	{
		for(short[] _item : item_list)
			st.takeItems(_item[0], _item[1]);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30829-02.htm"))
		{
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			st.set("cond", "1");
		}
		else if(event.equalsIgnoreCase("30610-05.htm") || event.equalsIgnoreCase("30610-06.htm") || event.equalsIgnoreCase("30610-12.htm") || event.equalsIgnoreCase("30610-13.htm"))// cond == 1
		{
			st.set("cond", "2");
			st.takeItems(Fairy_Stone, -1);
			st.takeItems(Deluxe_Fairy_Stone, -1);
			st.takeItems(Fairy_Stone_List, -1);
			st.takeItems(Deluxe_Fairy_Stone_List, -1);
			if(event.equalsIgnoreCase("30610-05.htm") || event.equalsIgnoreCase("30610-12.htm"))
				st.giveItems(Fairy_Stone_List, 1);
			else if(event.equalsIgnoreCase("30610-06.htm") || event.equalsIgnoreCase("30610-13.htm"))
				st.giveItems(Deluxe_Fairy_Stone_List, 1);
			st.playSound(SOUND_MIDDLE);
			st.unset("broken");
		}
		else if(event.equalsIgnoreCase("30608-03.htm"))// cond == 2
		{
			if(st.haveQuestItems(Fairy_Stone_List))
			{
				if(checkFairyStoneItems(st, Fairy_Stone_Items))
				{
					st.set("cond", "3");
					takeFairyStoneItems(st, Fairy_Stone_Items);
					st.giveItems(Fairy_Stone, 1);
					st.playSound(SOUND_MIDDLE);
				}
				else
					return "30608-01.htm";
			}
		}
		else if(event.equalsIgnoreCase("30608-03a.htm"))// cond == 2
		{
			if(st.haveQuestItems(Deluxe_Fairy_Stone_List))
			{
				if(checkFairyStoneItems(st, Delux_Fairy_Stone_Items))
				{
					st.set("cond", "3");
					takeFairyStoneItems(st, Delux_Fairy_Stone_Items);
					st.giveItems(Deluxe_Fairy_Stone, 1);
					st.playSound(SOUND_MIDDLE);
				}
				else
					return "30608-01a.htm";
			}
		}
		else if(event.equalsIgnoreCase("30711-03.htm"))// cond == 3
		{
			if(st.haveQuestItems(Fairy_Stone) || st.haveQuestItems(Deluxe_Fairy_Stone))
			{
				st.set("cond", "4");
				st.playSound(SOUND_MIDDLE);
				if(st.haveQuestItems(Deluxe_Fairy_Stone))
					return st.getInt("broken") == 1 ? "30711-04a.htm" : "30711-03a.htm";
				else if(st.getInt("broken") == 1)
					return "30711-04.htm";
			}
		}
		else if(event.equalsIgnoreCase("30747-02.htm"))// cond == 4
		{
			if(st.haveQuestItems(Fairy_Stone))
			{
				st.takeItems(Fairy_Stone, -1);
				st.set("takedStone", "1");
			}
		}
		else if(event.equalsIgnoreCase("30747-02a.htm"))// cond == 4
		{
			if(st.haveQuestItems(Deluxe_Fairy_Stone))
			{
				st.takeItems(Deluxe_Fairy_Stone, -1);
				st.set("takedStone", "2");
				st.giveItems(Fairy_Dust, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30747-04.htm"))// cond == 4
		{
			if(st.getInt("takedStone") > 0)
			{
				st.set("cond", "5");
				st.unset("takedStone");
				st.giveItems(Juice_of_Monkshood, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30748-02.htm"))// cond == 5
		{
			if(st.haveQuestItems(Juice_of_Monkshood))
			{
				st.set("cond", "6");
				st.takeItems(Juice_of_Monkshood, -1);
				st.giveItems(Scale_of_Drake_Exarion, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30749-02.htm"))// cond == 5
		{
			if(st.haveQuestItems(Juice_of_Monkshood))
			{
				st.set("cond", "6");
				st.takeItems(Juice_of_Monkshood, -1);
				st.giveItems(Scale_of_Drake_Zwov, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30750-02.htm"))// cond == 5
		{
			if(st.haveQuestItems(Juice_of_Monkshood))
			{
				st.set("cond", "6");
				st.takeItems(Juice_of_Monkshood, -1);
				st.giveItems(Scale_of_Drake_Kalibran, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30751-02.htm"))// cond == 5
		{
			if(st.haveQuestItems(Juice_of_Monkshood))
			{
				st.set("cond", "6");
				st.takeItems(Juice_of_Monkshood, -1);
				st.giveItems(Scale_of_Wyvern_Suzet, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30752-02.htm"))// cond == 5
		{
			if(st.haveQuestItems(Juice_of_Monkshood))
			{
				st.set("cond", "6");
				st.takeItems(Juice_of_Monkshood, -1);
				st.giveItems(Scale_of_Wyvern_Shamhai, 1);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(event.equalsIgnoreCase("30747-09.htm"))// cond == 7
		{ //TODO
			int egg_id = 0;
			for(int[] wyrm : wyrms)
			{
				if(!st.haveQuestItems(wyrm[2]) && st.haveQuestItems(wyrm[3]))
				{
					egg_id = wyrm[3];
					break;
				}
			}
			if(egg_id == 0)
				return "noquest";
			st.takeItems(egg_id, -1);
			st.giveItems(Rnd.get(Dragonflute_of_Wind, Dragonflute_of_Twilight), 1);
			if(st.haveQuestItems(Fairy_Dust))
			{
				st.playSound(SOUND_MIDDLE);
				return "30747-09a.htm";
			}
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30747-10.htm"))// cond == 7
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30747-11.htm"))// cond == 7
		{
			if(!st.haveQuestItems(Fairy_Dust))
				return "30747-10.htm";
			st.takeItems(Fairy_Dust, -1);
			if(Rnd.chance(Pet_Armor_Chance))
			{
				int rnd = Rnd.get(100);
				if(rnd < 32)
					st.giveItems(Hatchlings_Soft_Leather, 1);
				else if(31 < rnd && rnd < 54)
					st.giveItems(Hatchlings_Soft_Leather + 1, 1);
				else if(53 < rnd && rnd < 69)
					st.giveItems(Hatchlings_Soft_Leather + 2, 1);
				else if(68 < rnd && rnd < 81)
					st.giveItems(Hatchlings_Soft_Leather + 3, 1);
				else if(80 < rnd && rnd < 90)
					st.giveItems(Hatchlings_Soft_Leather + 4, 1);
				else if(89 < rnd && rnd < 96)
					st.giveItems(Hatchlings_Soft_Leather + 5, 1);
				else if(95 < rnd && rnd < 100)
					st.giveItems(Hatchlings_Soft_Leather + 6, 1);
			}
			else
				st.rollAndGive(Food_For_Hatchling, 20, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		int broken = st.getInt("broken");
		if(st.isCreated() && npcId == Cooper)
		{
			if(st.getPlayer().getLevel() < 35)
			{
				htmltext = "00";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "01";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case Cooper:
				{
					if(cond == 1)
						htmltext = "02";
					else
						htmltext = "03";
					break;
				}
				case Cronos:
				{
					if(cond == 1)
						htmltext = broken == 1 ? "10" : "01";
					else if(cond == 2)
						htmltext = "07";
					else if(cond == 3)
						htmltext = broken == 1 ? "14" : "08";
					else if(cond == 4)
						htmltext = "09";
					else if(cond > 4)
						htmltext = "11";
					break;
				}
				case Maria:
				{
					if(cond == 2)
					{
						if(st.haveQuestItems(Deluxe_Fairy_Stone_List))
							htmltext = checkFairyStoneItems(st, Delux_Fairy_Stone_Items) ? "30608-02a.htm" : "30608-01a.htm";
						else if(st.haveQuestItems(Fairy_Stone_List))
							htmltext = checkFairyStoneItems(st, Fairy_Stone_Items) ? "02" : "01";
					}
					else if(cond > 2)
						htmltext = "04";
					break;
				}
				case Byron:
				{
					if(cond == 1 && broken == 1)
						htmltext = "06";
					else if(cond == 2 && broken == 1)
						htmltext = "07";
					else if(cond == 3 && (st.haveQuestItems(Fairy_Stone) || st.haveQuestItems(Deluxe_Fairy_Stone)))
						htmltext = "01";
					else if(cond >= 4 && st.haveQuestItems(Deluxe_Fairy_Stone))
						htmltext = "30711-05a.htm";
					else if(cond >= 4 && st.haveQuestItems(Fairy_Stone))
						htmltext = "05";
					break;
				}
				case Mimyu:
				{
					if(cond == 4)
					{
						if(st.haveQuestItems(Deluxe_Fairy_Stone))
							htmltext = "30747-01a.htm";
						else if(st.haveQuestItems(Fairy_Stone))
							htmltext = "01";
						else if(st.getInt("takedStone") > 1)
							htmltext = "30747-02a.htm";
						else if(st.getInt("takedStone") == 1)
							htmltext = "02";
					}
					else if(cond == 5)
						htmltext = "05";
					else if(cond == 6)
					{
						htmltext = "06";
						for(int[] wyrm : wyrms)
							if(!st.haveQuestItems(wyrm[2]) && st.haveQuestItems(wyrm[3], 20))
								htmltext = "07";
					}
					else if(cond == 7)
					{ //TODO { mob, npc, scale, egg }
						for(int[] wyrm : wyrms)
						{
							if(st.haveQuestItems(wyrm[3]))
							{
								htmltext = "08";
								break;
							}
							else if(!st.haveQuestItems(wyrm[3]) && st.haveQuestItems(Fairy_Dust))
								htmltext = "30747-09a.htm";
						}
					}
					break;
				}
				default: //Exarion, Zwov, Kalibran, Suzet, Shamhai (added by addTalkId()) 
				{
					if(cond == 5 && st.haveQuestItems(Juice_of_Monkshood))
						htmltext = "01";
					else if(cond == 6 && st.haveQuestItems(getWyrmScale(npcId)))
					{
						int egg_id = getWyrmEgg(npcId);
						if(st.haveQuestItems(egg_id, 20))
						{
							htmltext = "04";
							st.takeItems(getWyrmScale(npcId), -1);
							st.takeItems(egg_id, -1);
							st.giveItems(egg_id, 1);
							st.set("cond", "7");
						}
						else
							htmltext = "03";
					}
					else if(cond == 7 && st.haveQuestItems(getWyrmEgg(npcId)))
						htmltext = "05";
				}
			}
		}
		if(isdigit(htmltext))
			htmltext = str(npcId) + "-" + htmltext + ".htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 2 && npcId == Toad_Lord && getNeededSkins(st) > 0 &&
				st.rollAndGiveLimited(Toad_Lord_Back_Skin, 1, Toad_Lord_Back_Skin_Chance, getNeededSkins(st)))
			st.playSound(st.getQuestItemsCount(Toad_Lord_Back_Skin) == getNeededSkins(st) ? SOUND_MIDDLE : SOUND_ITEMGET);
		else if(cond == 6)
		{
			int wyrm_id = isWyrmStoler(npcId);
			if(wyrm_id > 0 && st.haveQuestItems(getWyrmScale(wyrm_id)) && st.rollAndGiveLimited(getWyrmEgg(wyrm_id), 1, Egg_Chance, 20))
				st.playSound(st.getQuestItemsCount(getWyrmScale(wyrm_id)) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if(st.haveQuestItems(Deluxe_Fairy_Stone))
		{
			for(int id : mobs_Enchanted_Valey)
			{
				if(npcId == id)
				{
					String text = "You lost fairy stone deluxe!";
					if(st.getPlayer().getVar("lang@").equalsIgnoreCase("ru"))
						text = "Вы потеряли Вашу прелесть!";
					st.getPlayer().sendMessage(text);
					st.takeItems(Deluxe_Fairy_Stone, -1);
					st.set("broken", "1");
					st.set("cond", "1");
					st.setState(STARTED);
					break;
				}
			}
		}
	}
}