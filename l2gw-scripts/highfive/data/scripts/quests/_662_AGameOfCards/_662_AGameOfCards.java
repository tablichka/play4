package quests._662_AGameOfCards;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Files;
import ru.l2gw.commons.math.Rnd;

import java.util.Arrays;

public class _662_AGameOfCards extends Quest
{
	//NPC
	private static final int KLUMP = 30845;
	//ITEMs
	private static final short RED_GEM = 8765;
	private static final short ZIGGOS_GEMSTONE = 8868;
	private static final short EWS = 959; // Scroll: Enchant Weapon S
	private static final short EWA = 729; // Scroll: Enchant Weapon A
	private static final short EWB = 947; // Scroll: Enchant Weapon B
	private static final short EWC = 951; // Scroll: Enchant Weapon C
	private static final short EWD = 955; // Scroll: Enchant Weapon D
	private static final short EAD = 956; // Scroll: Enchant Armor D

	private static final String CARD_VALUES[] = {
			"<font color=\"LEVEL\">?</font>",
			"<font color=\"FF4500\">A</font>",
			"<font color=\"FF4500\">1</font>",
			"<font color=\"FF4500\">2</font>",
			"<font color=\"FF4500\">3</font>",
			"<font color=\"FF4500\">4</font>",
			"<font color=\"FF4500\">5</font>",
			"<font color=\"FF4500\">6</font>",
			"<font color=\"FF4500\">7</font>",
			"<font color=\"FF4500\">8</font>",
			"<font color=\"FF4500\">9</font>",
			"<font color=\"FF4500\">10</font>",
			"<font color=\"FF4500\">J</font>",
			"<font color=\"FF4500\">Q</font>",
			"<font color=\"FF4500\">K</font>"};

	private static final String REWARDS_TEXT[] = {
			"Hmmm...? This is... No pair? Tough luck, my friend! Want to try again? Perhaps your luck will take a turn for the better...",
			"Hmmm...? This is... One pair? You got lucky this time, but I wonder if it'll last. Here's your prize.",
			"Hmmm...? This is... Three of a kind? Very good, you are very lucky. Here's your prize.",
			"Hmmm...? This is... Four of a kind! Well done, my young friend! That sort of hand doesn't come up very often, that's for sure. Here's your prize.",
			"Hmmm...? This is... Five of a kind!!!! What luck! The goddess of victory must be with you! Here is your prize! Well earned, well played!",
			"Hmmm...? This is... Two pairs? You got lucky this time, but I wonder if it'll last. Here's your prize.",
			"Hmmm...? This is... A full house? Excellent! you're better than I thought. Here's your prize."};

	private static final int[] Mobs = {
			20677,
			21109,
			21112,
			21116,
			21114,
			21004,
			21002,
			21006,
			21008,
			21010,
			18001,
			20672,
			20673,
			20674,
			20955,
			20962,
			21515,
			21513,
			21510,
			21508,
			21535,
			21530,
			21526,
			21520,
			21289,
			21288,
			21287,
			21286,
			21281,
			21280,
			21279,
			21278,
			20972,
			20973,
			20968,
			20965,
			20966,
			20958,
			20959,
			20961};

	public _662_AGameOfCards()
	{
		super(662, "_662_AGameOfCards", "A Game Of Cards"); // Party true
		addStartNpc(KLUMP);
		addKillId(Mobs);
		addQuestItem(RED_GEM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("Klump_AcceptQuest.htm"))
		{
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			st.set("cond", "1");
			st.set("playing", "0");
			st.set("opened_cards", "0");
		}
		else if(event.equalsIgnoreCase("Klump_ExitQuest.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("Klump_QuestInProgress_Have50Gems.htm"))
		{
			if(!st.haveQuestItems(RED_GEM, 50))
				htmltext = "Klump_QuestInProgress.htm";
		}
		else if(event.equalsIgnoreCase("Klump_PlayBegin.htm"))
		{
			//Начинаем играть
			if(!st.haveQuestItems(RED_GEM, 50))
				return "Klump_NoGems.htm";
			//Отобрали 50 камней супостаты
			st.takeItems(RED_GEM, 50);
			/**
			 * Вернули картами в количестве 5 штук. Произвольными надо заметить
			 * А мы взяли и записали их все в состояние квеста, чтобы ничего не забыть после следующего действия.
			 * в принципе логично
			 */
			for(byte n = 1; n < 6; n++)
				st.set("card" + String.valueOf(n), String.valueOf(1 + Rnd.get(CARD_VALUES.length - 1)));
			st.set("playing", "1");
		}
		else if(event.equalsIgnoreCase("Klump_PlayField.htm"))
		{
			//Идет игра
			//Массив признаков открытости карт
			boolean opened[] = {false, false, false, false, false};
			//Размер приза
			int prize = 0;
			//Массив карт, которые мы показываем как открытые. Нулевые значения это вопросики.
			String cards[] = {CARD_VALUES[0], CARD_VALUES[0], CARD_VALUES[0], CARD_VALUES[0], CARD_VALUES[0]};
			//Ссылочки на действия пользователя
			String links[] = {
					"Put the first card face up.",
					"Put the second card face up.",
					"Put the third card face up.",
					"Put the fourth card face up.",
					"Put the fifth card face up."};
			/*
						//Пипец конструкция!!! Она конечно верная, но сцуко медленная. Уберу-ка я ее подальше
						String s = Long.toString(st.getInt("opened_cards"), 2);
						for(int i = 0; i < s.length(); i++)
						{
							if(s.regionMatches(s.length() - 1 - i, "1", 0, 1))
								opened[i] = true;
							else
								opened[i] = false;
						}
			*/
			int openedCards = st.getInt("opened_cards");
			for(int i = 0; i < 5; i++)
			{
				int val = (int) Math.pow(2, i);
				opened[i] = (openedCards & val) == val;
			}
			//Ежели все открыто - вычисляем размер приза
			if(opened[0] && opened[1] && opened[2] && opened[3] && opened[4])
			{
				for(byte n = 0; n < 5; n++)
				{
					//заполняем массив с отображаемыми картами
					cards[n] = CARD_VALUES[st.getInt("card" + String.valueOf(n + 1))];
					//зануляем сцылки, а на первую вешаем игру сначала
					if(n == 0)
						links[n] = "<a action=\"bypass -h Quest _662_AGameOfCards Klump_QuestInProgress_Have50Gems.htm\">Play again.</a>";
					else
						links[n] = "";
				}
				int ca[];
				ca = new int[5];
				for(byte n = 1; n < 6; n++)
					ca[n - 1] = st.getInt("card" + String.valueOf(n));
				Arrays.sort(ca);
				// prize = 4 : 5 карт (XXXXX). 1 вариант [XXXXX]
				if(ca[0] != 0 && ca[0] == ca[1] && ca[1] == ca[2] && ca[2] == ca[3] && ca[3] == ca[4])
					prize = 4;
					//  prize = 3 : 4 карт (XXXX). 2 варианта [XXXX-] [-XXXX]
				else if((ca[0] == ca[1] && ca[1] == ca[2] && ca[2] == ca[3] && ca[4] != ca[0]) || (ca[1] == ca[2] && ca[2] == ca[3] && ca[3] == ca[4] && ca[4] != ca[0]))
					prize = 3;
					// prize = 2 : 3 карт (XXX). 3 варианта [XXX--] [-XXX-] [--XXX]
				else if((ca[0] == ca[1] && ca[1] == ca[2] && ca[3] != ca[0] && ca[4] != ca[0] && ca[4] != ca[3]) || (ca[1] == ca[2] && ca[2] == ca[3] && ca[4] != ca[1] && ca[0] != ca[4] && ca[4] != ca[0]) || (ca[2] == ca[3] && ca[3] == ca[4] && ca[0] != ca[2] && ca[1] != ca[2] && ca[0] != ca[1]))
					prize = 2;
					// prize = 6 : фулхаус (XXXYY). 2 варианта [XXXYY] [YYXXX]
				else if((ca[0] == ca[1] && ca[1] == ca[2] && ca[3] == ca[4] && ca[4] != ca[0]) || (ca[2] == ca[3] && ca[3] == ca[4] && ca[0] == ca[1] && ca[4] != ca[0]))
					prize = 6;
					// prize = 5 : 2 пары (XXYY). 3 варианта [XXYY-] [XX-YY] [-XXYY]
				else if((ca[0] == ca[1] && ca[2] == ca[3] && ca[2] != ca[1] && ca[4] != ca[0] && ca[4] != ca[3]) || (ca[0] == ca[1] && ca[3] == ca[4] && ca[4] != ca[0] && ca[2] != ca[4] && ca[2] != ca[0]) || (ca[1] == ca[2] && ca[3] == ca[4] && ca[1] != ca[4] && ca[0] != ca[1] && ca[0] != ca[4]))
					prize = 5;
					// prize = 1 : 1 пара (XX). 4 варианта [XX---] [-XX--] [--XX-] [---XX]
				else if((ca[0] == ca[1] && ca[2] != ca[3] && ca[3] != ca[4] && ca[4] != ca[2] && ca[0] != ca[2] && ca[0] != ca[3] && ca[0] != ca[4]) || (ca[1] == ca[2] && ca[3] != ca[4] && ca[4] != ca[0] && ca[0] != ca[3] && ca[1] != ca[0] && ca[1] != ca[3] && ca[1] != ca[4]) || (ca[2] == ca[3] && ca[4] != ca[0] && ca[0] != ca[1] && ca[4] != ca[1] && ca[2] != ca[4] && ca[2] != ca[0] && ca[2] != ca[1]) || (ca[3] == ca[4] && ca[0] != ca[1] && ca[1] != ca[2] && ca[2] != ca[0] && ca[3] != ca[0] && ca[3] != ca[1] && ca[3] != ca[2]))
					prize = 1;
				if(prize == 1)
					st.giveItems(EAD, 2);
				else if(prize == 2)
					st.giveItems(EWC, 2);
				else if(prize == 3)
				{
					st.giveItems(EWC, 2);
					st.giveItems(EWS, 2);
				}
				else if(prize == 4)
				{
					st.giveItems(ZIGGOS_GEMSTONE, 43);
					st.giveItems(EWS, 3);
					st.giveItems(EWA, 1);
				}
				else if(prize == 5)
					st.giveItems(EWC, 1);
				else if(prize == 6)
				{
					st.giveItems(EWA, 1);
					st.giveItems(EWB, 2);
					st.giveItems(EWD, 1);
				}
				st.set("playing", "0");
				st.set("opened_cards", "0");
				for(byte n = 1; n < 6; n++)
					st.unset("card" + String.valueOf(n));
			}
			else
			{
				//Если открыто не все - для нас самое интересное
				for(byte n = 0; n < 5; n++)
				{
					//если карта открыта
					if(opened[n])
					{
						//берем значение карты и показываем его
						cards[n] = CARD_VALUES[st.getInt("card" + String.valueOf(n + 1))];
					}
					else
					{
						//делаем активной ссцылку на открытие неоткрытой карты
						links[n] = "<a action=\"bypass -h Quest _662_AGameOfCards open_card" + String.valueOf(n) + "\">" + links[n] + "</a>";
					}
				}
			}
			htmltext = Files.read("data/scripts/quests/_662_AGameOfCards/" + event, st.getPlayer().getVar("lang@"));
			htmltext = htmltext.replace("%card1%", cards[0]).replace("%card2%", cards[1]).replace("%card3%", cards[2]).replace("%card4%", cards[3]).replace("%card5%", cards[4]);
			htmltext = htmltext.replace("%link1%", links[0]).replace("%link2%", links[1]).replace("%link3%", links[2]).replace("%link4%", links[3]).replace("%link5%", links[4]);
			htmltext = htmltext.replace("%prize%", (opened[0] && opened[1] && opened[2] && opened[3] && opened[4]) ? REWARDS_TEXT[prize] : "");
		}
		else
		{
			//Здесь идет анализ открытых карт
			for(byte n = 0; n < 5; n++)
			{
				if(event.equalsIgnoreCase("open_card" + String.valueOf(n)))
				{
					//карта под номером n открыта

					//Берем число в котором записано количество открытых карт
					int openedCards = st.getInt("opened_cards");
					//добавляем в него указание на открытую карту и сохраняем в состояние
					st.set("opened_cards", String.valueOf(openedCards + (int) Math.pow(2, n)));
					//Если вдруг произошла ошибка и указано что открыто больше чем пять карт - выставляем что открыто только 5
					if(openedCards > 31)
						st.set("opened_cards", "31");
					//переходим на отображение поля
					htmltext = onEvent("Klump_PlayField.htm", st);
				}
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 61)
			{
				htmltext = "Klump_NoQuest.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "Klump_FirstTalk.htm";
		}
		else if(st.isStarted())
		{
			if(st.getInt("playing") == 1)
				htmltext = onEvent("Klump_PlayField.htm", st);
			else if(st.haveQuestItems(RED_GEM, 50))
				htmltext = "Klump_QuestInProgress_Have50Gems.htm";
			else
				htmltext = "Klump_QuestInProgress.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGive(RED_GEM, 1, 30 * npc.getTemplate().hp_mod))
			st.playSound(SOUND_ITEMGET);
	}
}