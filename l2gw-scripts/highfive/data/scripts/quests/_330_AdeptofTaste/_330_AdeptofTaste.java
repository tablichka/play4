package quests._330_AdeptofTaste;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

//written by spellsinger [14.08.08]
public class _330_AdeptofTaste extends Quest
{
	public final short INGREDIENT_LIST_ID = 1420;
	public final short SONIAS_BOTANYBOOK_ID = 1421;
	public final short RED_MANDRAGORA_ROOT_ID = 1422;
	public final short WHITE_MANDRAGORA_ROOT_ID = 1423;
	public final short RED_MANDRAGORA_SAP_ID = 1424;
	public final short WHITE_MANDRAGORA_SAP_ID = 1425;
	public final short JAYCUBS_INSECTBOOK_ID = 1426;
	public final short NECTAR_ID = 1427;
	public final short ROYAL_JELLY_ID = 1428;
	public final short HONEY_ID = 1429;
	public final short GOLDEN_HONEY_ID = 1430;
	public final short PANOS_CONTRACT_ID = 1431;
	public final short HOBGOBLIN_AMULET_ID = 1432;
	public final short DIONIAN_POTATO_ID = 1433;
	public final short GLYVKAS_BOTANYBOOK_ID = 1434;
	public final short GREEN_MARSH_MOSS_ID = 1435;
	public final short BROWN_MARSH_MOSS_ID = 1436;
	public final short GREEN_MOSS_BUNDLE_ID = 1437;
	public final short BROWN_MOSS_BUNDLE_ID = 1438;
	public final short ROLANTS_CREATUREBOOK_ID = 1439;
	public final short MONSTER_EYE_BODY_ID = 1440;
	public final short MONSTER_EYE_MEAT_ID = 1441;
	public final short JONAS_STEAK_DISH1_ID = 1442;
	public final short JONAS_STEAK_DISH2_ID = 1443;
	public final short JONAS_STEAK_DISH3_ID = 1444;
	public final short JONAS_STEAK_DISH4_ID = 1445;
	public final short JONAS_STEAK_DISH5_ID = 1446;
	public final short MIRIENS_REVIEW1_ID = 1447;
	public final short MIRIENS_REVIEW2_ID = 1448;
	public final short MIRIENS_REVIEW3_ID = 1449;
	public final short MIRIENS_REVIEW4_ID = 1450;
	public final short MIRIENS_REVIEW5_ID = 1451;
	public final short ADENA_ID = 57;
	public final short JONAS_SALAD_RECIPE_ID = 1455;
	public final short JONAS_SAUCE_RECIPE_ID = 1456;
	public final short JONAS_STEAK_RECIPE_ID = 1457;

	public final int JONAS = 30469;
	public final int SONIA = 30062;
	public final int GLYVKA = 30067;
	public final int ROLLANT = 30069;

	public final int JACOB = 30073;
	public final int PANO = 30078;
	public final int MIRIEN = 30461;

	public _330_AdeptofTaste()
	{
		super(330, "_330_AdeptofTaste", "Adept of Taste");

		addStartNpc(JONAS);
		addTalkId(SONIA);
		addTalkId(GLYVKA);
		addTalkId(ROLLANT);
		addTalkId(JACOB);
		addTalkId(PANO);
		addTalkId(MIRIEN);

		addQuestItem(INGREDIENT_LIST_ID,
				SONIAS_BOTANYBOOK_ID,
				RED_MANDRAGORA_ROOT_ID,
				WHITE_MANDRAGORA_ROOT_ID,
				RED_MANDRAGORA_SAP_ID,
				WHITE_MANDRAGORA_SAP_ID,
				JAYCUBS_INSECTBOOK_ID,
				NECTAR_ID,
				ROYAL_JELLY_ID,
				HONEY_ID,
				GOLDEN_HONEY_ID,
				PANOS_CONTRACT_ID,
				HOBGOBLIN_AMULET_ID,
				DIONIAN_POTATO_ID,
				GLYVKAS_BOTANYBOOK_ID,
				GREEN_MARSH_MOSS_ID,
				BROWN_MARSH_MOSS_ID,
				GREEN_MOSS_BUNDLE_ID,
				BROWN_MOSS_BUNDLE_ID,
				ROLANTS_CREATUREBOOK_ID,
				MONSTER_EYE_BODY_ID,
				MONSTER_EYE_MEAT_ID,
				JONAS_STEAK_DISH1_ID,
				JONAS_STEAK_DISH2_ID,
				JONAS_STEAK_DISH3_ID,
				JONAS_STEAK_DISH4_ID,
				JONAS_STEAK_DISH5_ID,
				MIRIENS_REVIEW1_ID,
				MIRIENS_REVIEW2_ID,
				MIRIENS_REVIEW3_ID,
				MIRIENS_REVIEW4_ID,
				MIRIENS_REVIEW5_ID,
				JONAS_SALAD_RECIPE_ID,
				JONAS_SAUCE_RECIPE_ID,
				JONAS_STEAK_RECIPE_ID);

		addKillId(20147);
		addKillId(20154);
		addKillId(20155);
		addKillId(20156);
		addKillId(20204);
		addKillId(20223);
		addKillId(20226);
		addKillId(20228);
		addKillId(20229);
		addKillId(20265);
		addKillId(20266);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getInt("cond") == 0)
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.giveItems(INGREDIENT_LIST_ID, 1);
			htmltext = "30469-03.htm";
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30062-01.htm"))
		{

			htmltext = "30062-05.htm";
			st.takeItems(SONIAS_BOTANYBOOK_ID, 1);
			st.takeItems(RED_MANDRAGORA_ROOT_ID, -1);
			st.takeItems(WHITE_MANDRAGORA_ROOT_ID, -1);
			st.giveItems(RED_MANDRAGORA_SAP_ID, 1);

		}
		else if(event.equalsIgnoreCase("30073-01.htm"))
		{
			htmltext = "30073-05.htm";
			st.takeItems(JAYCUBS_INSECTBOOK_ID, 1);
			st.takeItems(NECTAR_ID, -1);
			st.takeItems(ROYAL_JELLY_ID, -1);
			st.giveItems(HONEY_ID, 1);
		}
		else if(event.equalsIgnoreCase("30067-01.htm"))
		{
			htmltext = "30067-05.htm";
			st.takeItems(GLYVKAS_BOTANYBOOK_ID, 1);
			st.takeItems(GREEN_MARSH_MOSS_ID, -1);
			st.takeItems(BROWN_MARSH_MOSS_ID, -1);
			st.giveItems(GREEN_MOSS_BUNDLE_ID, 1);
		}
		return htmltext;
	}

	private long getIngs(QuestState st)
	{
		return st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(HONEY_ID) + st.getQuestItemsCount(DIONIAN_POTATO_ID) + st.getQuestItemsCount(GREEN_MOSS_BUNDLE_ID) + st.getQuestItemsCount(MONSTER_EYE_MEAT_ID) + getspecIngs(st);
	}

	private long getspecIngs(QuestState st)
	{
		return st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(GOLDEN_HONEY_ID) + st.getQuestItemsCount(BROWN_MOSS_BUNDLE_ID);
	}

	private long getdish(QuestState st)
	{
		return st.getQuestItemsCount(JONAS_STEAK_DISH1_ID) + st.getQuestItemsCount(JONAS_STEAK_DISH2_ID) + st.getQuestItemsCount(JONAS_STEAK_DISH3_ID) + st.getQuestItemsCount(JONAS_STEAK_DISH4_ID) + st.getQuestItemsCount(JONAS_STEAK_DISH5_ID);
	}

	private long getreview(QuestState st)
	{
		return st.getQuestItemsCount(MIRIENS_REVIEW1_ID) + st.getQuestItemsCount(MIRIENS_REVIEW2_ID) + st.getQuestItemsCount(MIRIENS_REVIEW3_ID) + st.getQuestItemsCount(MIRIENS_REVIEW4_ID) + st.getQuestItemsCount(MIRIENS_REVIEW5_ID);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "<html><body>I have't any quests for you now. See you later, stranger...</body></html>";
		int npcId = npc.getNpcId();
		int sc = (int) getspecIngs(st);
		if(npcId == JONAS && st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 24)
				htmltext = "30469-02.htm";
			else
			{
				htmltext = "30469-01.htm";
				st.exitCurrentQuest(true);
			}
		}

		//st.getPlayer().sendMessage( ""+Rnd.get(10) );

		if(npcId == JONAS && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) == 0 && getIngs(st) == 0 && getdish(st) > 0 && getreview(st) < 1)
			htmltext = "30469-06.htm";
		else if(npcId == JONAS && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) == 0 && getIngs(st) == 0 && getdish(st) < 1 && getreview(st) > 0)
		{
			st.playSound(SOUND_FINISH);
			if(st.getQuestItemsCount(MIRIENS_REVIEW1_ID) > 0)
			{
				htmltext = "30469-06t1.htm";
				st.takeItems(MIRIENS_REVIEW1_ID, 1);
				st.rollAndGive(ADENA_ID, 7500, 100);
				st.addExpAndSp(6000, 0);
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(MIRIENS_REVIEW2_ID) > 0)
			{
				htmltext = "30469-06t2.htm";
				st.takeItems(MIRIENS_REVIEW2_ID, 1);
				st.rollAndGive(ADENA_ID, 9000, 100);
				st.addExpAndSp(7000, 0);
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(MIRIENS_REVIEW3_ID) > 0)
			{
				htmltext = "30469-06t3.htm";
				st.takeItems(MIRIENS_REVIEW3_ID, 1);
				st.rollAndGive(ADENA_ID, 5800, 100);
				st.exitCurrentQuest(true);
				st.giveItems(JONAS_SALAD_RECIPE_ID, 1);
				st.addExpAndSp(9000, 0);
			}
			else if(st.getQuestItemsCount(MIRIENS_REVIEW4_ID) > 0)
			{
				htmltext = "30469-06t4.htm";
				st.takeItems(MIRIENS_REVIEW4_ID, 1);
				st.rollAndGive(ADENA_ID, 6800, 100);
				st.exitCurrentQuest(true);
				st.giveItems(JONAS_SAUCE_RECIPE_ID, 1);
				st.addExpAndSp(10500, 0);
			}
			else if(st.getQuestItemsCount(MIRIENS_REVIEW5_ID) > 0)
			{
				htmltext = "30469-06t5.htm";
				st.takeItems(MIRIENS_REVIEW5_ID, 1);
				st.rollAndGive(ADENA_ID, 7800, 100);
				st.exitCurrentQuest(true);
				st.giveItems(JONAS_STEAK_RECIPE_ID, 1);
				st.addExpAndSp(12000, 0);
			}
			//st.giveItems(INGREDIENT_LIST_ID, 1, false);
		}

		if(npcId == MIRIEN && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) > 0)
			htmltext = "30461-01.htm";
		else if(npcId == MIRIEN && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) == 0 && getIngs(st) == 0 && getdish(st) > 0 && getreview(st) < 1)
		{
			if(st.getQuestItemsCount(JONAS_STEAK_DISH1_ID) > 0)
			{
				htmltext = "30461-02t1.htm";
				st.takeItems(JONAS_STEAK_DISH1_ID, 1);
				st.giveItems(MIRIENS_REVIEW1_ID, 1);
			}
			else if(st.getQuestItemsCount(JONAS_STEAK_DISH2_ID) > 0)
			{
				htmltext = "30461-02t2.htm";
				st.takeItems(JONAS_STEAK_DISH2_ID, 1);
				st.giveItems(MIRIENS_REVIEW2_ID, 1);
			}
			else if(st.getQuestItemsCount(JONAS_STEAK_DISH3_ID) > 0)
			{
				htmltext = "30461-02t3.htm";
				st.takeItems(JONAS_STEAK_DISH3_ID, 1);
				st.giveItems(MIRIENS_REVIEW3_ID, 1);
			}
			else if(st.getQuestItemsCount(JONAS_STEAK_DISH4_ID) > 0)
			{
				htmltext = "30461-02t4.htm";
				st.takeItems(JONAS_STEAK_DISH4_ID, 1);
				st.giveItems(MIRIENS_REVIEW4_ID, 1);
			}
			else if(st.getQuestItemsCount(JONAS_STEAK_DISH5_ID) > 0)
			{
				htmltext = "30461-02t5.htm";
				st.takeItems(JONAS_STEAK_DISH5_ID, 1);
				st.giveItems(MIRIENS_REVIEW5_ID, 1);
			}
		}
		else if(npcId == JONAS && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) == 0 && getIngs(st) == 0 && getdish(st) < 1 && getreview(st) > 0)
			htmltext = "30461-04.htm";

		if(npcId == JONAS && st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) > 0 && getIngs(st) >= 5)
		{
			switch(sc)
			{
				case 0:
					if(Rnd.get(10) < 1)
					{
						htmltext = "30469-05t2.htm";
						st.giveItems(JONAS_STEAK_DISH2_ID, 1);
					}
					else
					{
						htmltext = "30469-05t1.htm";
						st.giveItems(JONAS_STEAK_DISH1_ID, 1);
					}
					break;
				case 1:
					if(Rnd.get(10) < 1)
					{
						htmltext = "30469-05t3.htm";
						st.giveItems(JONAS_STEAK_DISH3_ID, 1);
					}
					else
					{
						htmltext = "30469-05t2.htm";
						st.giveItems(JONAS_STEAK_DISH2_ID, 1);
					}
					break;
				case 2:
					if(Rnd.get(10) < 1)
					{
						htmltext = "30469-05t4.htm";
						st.giveItems(JONAS_STEAK_DISH4_ID, 1);
					}
					else
					{
						htmltext = "30469-05t3.htm";
						st.giveItems(JONAS_STEAK_DISH3_ID, 1);
					}
					break;
				case 3:
					if(Rnd.get(10) < 1)
					{
						htmltext = "30469-05t5.htm";
						st.giveItems(JONAS_STEAK_DISH5_ID, 1);
						st.playSound("SOUND_JACKPOT");
					}
					else
					{
						htmltext = "30469-05t4.htm";
						st.giveItems(JONAS_STEAK_DISH4_ID, 1);
					}
					break;
			}
			st.takeItems(INGREDIENT_LIST_ID, 1);
			st.takeItems(RED_MANDRAGORA_SAP_ID, 1);
			st.takeItems(WHITE_MANDRAGORA_SAP_ID, 1);
			st.takeItems(HONEY_ID, 1);
			st.takeItems(GOLDEN_HONEY_ID, 1);
			st.takeItems(DIONIAN_POTATO_ID, 1);
			st.takeItems(GREEN_MOSS_BUNDLE_ID, 1);
			st.takeItems(BROWN_MOSS_BUNDLE_ID, 1);
			st.takeItems(MONSTER_EYE_MEAT_ID, 1);
		}

		if(st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) > 0 && getIngs(st) < 5)
			switch(npcId)
			{
				case JONAS:
					htmltext = "30469-04.htm";
					break;
				case SONIA:
					if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) == 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						htmltext = "30062-01.htm";
						st.giveItems(SONIAS_BOTANYBOOK_ID, 1);
					}
					else if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) < 40 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
						htmltext = "30062-02.htm";
					else if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) >= 40 && st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) < 40 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
						htmltext = "30062-03.htm";
					else if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) >= 40 && st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) >= 40 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						htmltext = "30062-06.htm";
						st.takeItems(SONIAS_BOTANYBOOK_ID, 1);
						st.takeItems(RED_MANDRAGORA_ROOT_ID, -1);
						st.takeItems(WHITE_MANDRAGORA_ROOT_ID, -1);
						st.giveItems(WHITE_MANDRAGORA_SAP_ID, 1);
					}
					else if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) == 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
						htmltext = "30062-07.htm";
					break;
				case JACOB:
					if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) == 0 && st.getQuestItemsCount(HONEY_ID) + st.getQuestItemsCount(GOLDEN_HONEY_ID) == 0)
					{
						htmltext = "30073-01.htm";
						st.giveItems(JAYCUBS_INSECTBOOK_ID, 1);
					}
					else if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) > 0 && st.getQuestItemsCount(NECTAR_ID) < 20)
						htmltext = "30073-02.htm";
					else if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) > 0 && st.getQuestItemsCount(NECTAR_ID) >= 20 && st.getQuestItemsCount(ROYAL_JELLY_ID) < 10)
						htmltext = "30073-03.htm";
					else if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) > 0 && st.getQuestItemsCount(NECTAR_ID) >= 20 && st.getQuestItemsCount(ROYAL_JELLY_ID) >= 10)
					{
						htmltext = "30073-06.htm";
						st.takeItems(JAYCUBS_INSECTBOOK_ID, 1);
						st.takeItems(NECTAR_ID, -1);
						st.takeItems(ROYAL_JELLY_ID, -1);
						st.giveItems(GOLDEN_HONEY_ID, 1);
					}
					else if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) == 0 && st.getQuestItemsCount(HONEY_ID) + st.getQuestItemsCount(GOLDEN_HONEY_ID) == 1)
						htmltext = "30073-06.htm";
					break;
				case PANO:
					if(st.getQuestItemsCount(PANOS_CONTRACT_ID) == 0 && st.getQuestItemsCount(DIONIAN_POTATO_ID) == 0)
					{
						htmltext = "30078-01.htm";
						st.giveItems(PANOS_CONTRACT_ID, 1);
					}
					else if(st.getQuestItemsCount(PANOS_CONTRACT_ID) > 0 && st.getQuestItemsCount(HOBGOBLIN_AMULET_ID) < 30)
						htmltext = "30078-02.htm";
					else if(st.getQuestItemsCount(PANOS_CONTRACT_ID) > 0 && st.getQuestItemsCount(HOBGOBLIN_AMULET_ID) >= 30)
					{

						htmltext = "30078-03.htm";
						st.takeItems(PANOS_CONTRACT_ID, 1);
						st.takeItems(HOBGOBLIN_AMULET_ID, -1);
						st.giveItems(DIONIAN_POTATO_ID, 1);
					}
					else if(st.getQuestItemsCount(PANOS_CONTRACT_ID) == 0 && st.getQuestItemsCount(DIONIAN_POTATO_ID) > 0)
						htmltext = "30078-04.htm";
					break;
				case GLYVKA:
					if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) == 0 && st.getQuestItemsCount(GREEN_MOSS_BUNDLE_ID) + st.getQuestItemsCount(BROWN_MOSS_BUNDLE_ID) == 0)
					{
						htmltext = "30067-01.htm";
						st.giveItems(GLYVKAS_BOTANYBOOK_ID, 1);
					}
					else if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(GREEN_MARSH_MOSS_ID) + st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) < 20)
						htmltext = "30067-02.htm";
					else if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(GREEN_MARSH_MOSS_ID) + st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) >= 20 && st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) < 20)
						htmltext = "30067-03.htm";
					else if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(GREEN_MARSH_MOSS_ID) + st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) >= 20 && st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) >= 20)
					{
						htmltext = "30067-06.htm";
						st.takeItems(GLYVKAS_BOTANYBOOK_ID, 1);
						st.takeItems(GREEN_MARSH_MOSS_ID, -1);
						st.takeItems(BROWN_MARSH_MOSS_ID, -1);
						st.giveItems(BROWN_MOSS_BUNDLE_ID, 1);
					}
					else if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) == 0 && st.getQuestItemsCount(GREEN_MOSS_BUNDLE_ID) + st.getQuestItemsCount(BROWN_MOSS_BUNDLE_ID) == 1)
						htmltext = "30067-07.htm";
					break;
				case ROLLANT:
					if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) == 0 && st.getQuestItemsCount(MONSTER_EYE_MEAT_ID) == 0)
					{
						htmltext = "30069-01.htm";
						st.giveItems(ROLANTS_CREATUREBOOK_ID, 1);
					}
					else if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) > 0 && st.getQuestItemsCount(MONSTER_EYE_BODY_ID) < 30)
						htmltext = "30069-02.htm";
					else if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) > 0 && st.getQuestItemsCount(MONSTER_EYE_BODY_ID) >= 30)
					{
						htmltext = "30069-03.htm";
						st.takeItems(ROLANTS_CREATUREBOOK_ID, 1);
						st.takeItems(MONSTER_EYE_BODY_ID, -1);
						st.giveItems(MONSTER_EYE_MEAT_ID, 1);
					}
					else if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) == 0 && st.getQuestItemsCount(MONSTER_EYE_MEAT_ID) == 1)
						htmltext = "30069-04.htm";
					break;
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int n = Rnd.get(100);

		if(st.getInt("cond") > 0 && st.getQuestItemsCount(INGREDIENT_LIST_ID) > 0 && getIngs(st) < 5)
			switch(npcId)
			{
				case 20147:
					if(st.getQuestItemsCount(PANOS_CONTRACT_ID) > 0 && st.rollAndGiveLimited(HOBGOBLIN_AMULET_ID, 1, 100, 30))
					{
						st.playSound(st.getQuestItemsCount(HOBGOBLIN_AMULET_ID) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
					}
					break;
				case 20154:
					if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						if(n < 74)
						{
							if(st.rollAndGiveLimited(RED_MANDRAGORA_ROOT_ID, 1, 100, 40))
							{
								st.playSound(st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
						else if(n > 92)
						{
							if(st.rollAndGiveLimited(WHITE_MANDRAGORA_ROOT_ID, 1, 100, 40))
							{
								st.playSound(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
					}
					break;
				case 20155:
					if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						if(n < 80)
						{
							if(st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) < 40)
							{
								if(st.rollAndGiveLimited(RED_MANDRAGORA_ROOT_ID, 1, 100, 40))
								{
									st.playSound(st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
								}
							}
						}
						else if(n > 91)
						{
							if(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) < 40)
							{
								if(st.rollAndGiveLimited(WHITE_MANDRAGORA_ROOT_ID, 1, 100, 40))
								{
									st.playSound(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
								}
							}
						}
					}
					break;
				case 20156:
					if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						if(n < 51)
						{
							if(st.rollAndGiveLimited(RED_MANDRAGORA_ROOT_ID, 1, 100, 40))
							{
								st.playSound(st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
						else
						{
							if(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) < 40)
							{
								if(st.rollAndGiveLimited(WHITE_MANDRAGORA_ROOT_ID, 1, 100, 40))
								{
									st.playSound(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
								}
							}
						}
					}
					break;
				case 20223:
					if(st.getQuestItemsCount(SONIAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(RED_MANDRAGORA_SAP_ID) + st.getQuestItemsCount(WHITE_MANDRAGORA_SAP_ID) == 0)
					{
						if(n < 67)
						{
							if(st.rollAndGiveLimited(RED_MANDRAGORA_ROOT_ID, 1, 100, 40))
							{
								st.playSound(st.getQuestItemsCount(RED_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
						else if(n > 93)
						{
							if(st.rollAndGiveLimited(WHITE_MANDRAGORA_ROOT_ID, 1, 100, 40))
							{
								st.playSound(st.getQuestItemsCount(WHITE_MANDRAGORA_ROOT_ID) == 40 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
					}
					break;
				case 20204:
					if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) > 0 && st.getQuestItemsCount(HONEY_ID) + st.getQuestItemsCount(GOLDEN_HONEY_ID) == 0)
					{
						if(n < 80)
						{
							if(st.rollAndGiveLimited(NECTAR_ID, 1, 100, 20))
								st.playSound(st.getQuestItemsCount(NECTAR_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
						else if(n > 95)
						{
							if(st.rollAndGiveLimited(ROYAL_JELLY_ID, 1, 100, 10))
								st.playSound(st.getQuestItemsCount(ROYAL_JELLY_ID) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
					}
					break;
				case 20229:
					if(st.getQuestItemsCount(JAYCUBS_INSECTBOOK_ID) > 0 && st.getQuestItemsCount(HONEY_ID) + st.getQuestItemsCount(GOLDEN_HONEY_ID) == 0)
					{
						if(n < 72)
						{
							if(st.rollAndGiveLimited(NECTAR_ID, 1, 100, 20))
								st.playSound(st.getQuestItemsCount(NECTAR_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
						else
						{
							if(st.rollAndGiveLimited(ROYAL_JELLY_ID, 1, 100, 10))
								st.playSound(st.getQuestItemsCount(ROYAL_JELLY_ID) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
					}
					break;
				case 20226:
					if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(GREEN_MOSS_BUNDLE_ID) + st.getQuestItemsCount(BROWN_MOSS_BUNDLE_ID) == 0)
					{
						n = Rnd.get(10);
						if(n < 9)
						{
							if(st.rollAndGiveLimited(GREEN_MARSH_MOSS_ID, 1, 100, 20))
								st.playSound(st.getQuestItemsCount(GREEN_MARSH_MOSS_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
						else
						{
							if(st.rollAndGiveLimited(BROWN_MARSH_MOSS_ID, 1, 100, 20))
								st.playSound(st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
					}
					break;
				case 20228:
					if(st.getQuestItemsCount(GLYVKAS_BOTANYBOOK_ID) > 0 && st.getQuestItemsCount(GREEN_MOSS_BUNDLE_ID) + st.getQuestItemsCount(BROWN_MOSS_BUNDLE_ID) == 0)
					{
						if(n < 88)
						{
							if(st.rollAndGiveLimited(GREEN_MARSH_MOSS_ID, 1, 100, 20))
								st.playSound(st.getQuestItemsCount(GREEN_MARSH_MOSS_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
						else
						{
							if(st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) < 20)
							{
								if(st.rollAndGiveLimited(BROWN_MARSH_MOSS_ID, 1, 100, 20))
									st.playSound(st.getQuestItemsCount(BROWN_MARSH_MOSS_ID) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
							}
						}
					}
					break;
				case 20265:
					if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) > 0 && st.getQuestItemsCount(MONSTER_EYE_BODY_ID) < 30)
					{
						if(n < 75)
						{
							if(st.rollAndGiveLimited(MONSTER_EYE_BODY_ID, 1, 100, 30))
								st.playSound(st.getQuestItemsCount(MONSTER_EYE_BODY_ID) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
					}
					break;
				case 20266:
					if(st.getQuestItemsCount(ROLANTS_CREATUREBOOK_ID) > 0 && st.getQuestItemsCount(MONSTER_EYE_BODY_ID) < 30)
					{
						n = Rnd.get(10);
						if(n < 7)
						{
							if(st.rollAndGiveLimited(MONSTER_EYE_BODY_ID, 1, 100, 30))
								st.playSound(st.getQuestItemsCount(MONSTER_EYE_BODY_ID) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
						}
					}
					break;
			}
	}
}