package quests._218_TestimonyOfLife;

import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _218_TestimonyOfLife extends Quest
{
	private static final int MARK_OF_LIFE_ID = 3140;
	private static final int CARDIENS_LETTER_ID = 3141;
	private static final int CAMOMILE_CHARM_ID = 3142;
	private static final int HIERARCHS_LETTER_ID = 3143;
	private static final int MOONFLOWER_CHARM_ID = 3144;
	private static final int GRAIL_DIAGRAM_ID = 3145;
	private static final int THALIAS_LETTER1_ID = 3146;
	private static final int THALIAS_LETTER2_ID = 3147;
	private static final int THALIAS_INSTRUCTIONS_ID = 3148;
	private static final int PUSHKINS_LIST_ID = 3149;
	private static final int PURE_MITHRIL_CUP_ID = 3150;
	private static final int ARKENIAS_CONTRACT_ID = 3151;
	private static final int ARKENIAS_INSTRUCTIONS_ID = 3152;
	private static final int ADONIUS_LIST_ID = 3153;
	private static final int ANDARIEL_SCRIPTURE_COPY_ID = 3154;
	private static final int STARDUST_ID = 3155;
	private static final int ISAELS_INSTRUCTIONS_ID = 3156;
	private static final int ISAELS_LETTER_ID = 3157;
	private static final int GRAIL_OF_PURITY_ID = 3158;
	private static final int TEARS_OF_UNICORN_ID = 3159;
	private static final int WATER_OF_LIFE_ID = 3160;
	private static final int PURE_MITHRIL_ORE_ID = 3161;
	private static final int ANT_SOLDIER_ACID_ID = 3162;
	private static final int WYRMS_TALON1_ID = 3163;
	private static final int SPIDER_ICHOR_ID = 3164;
	private static final int HARPYS_DOWN_ID = 3165;
	private static final int TALINS_SPEAR_BLADE_ID = 3166;
	private static final int TALINS_SPEAR_SHAFT_ID = 3167;
	private static final int TALINS_RUBY_ID = 3168;
	private static final int TALINS_AQUAMARINE_ID = 3169;
	private static final int TALINS_AMETHYST_ID = 3170;
	private static final int TALINS_PERIDOT_ID = 3171;
	private static final int TALINS_SPEAR_ID = 3026;
	private static final int RewardExp = 943416;
	private static final int RewardSP = 62959;
	private static final int RewardAdena = 171144;

	public _218_TestimonyOfLife()
	{
		super(218, "_218_TestimonyOfLife", "Testimony Of Life");

		addStartNpc(30460);

		addTalkId(30460, 30154, 30300, 30371, 30375, 30419, 30655);

		addKillId(20145, 20176, 20233, 27077, 20550, 20581, 20582, 20082, 20084, 20086, 20087, 20088);

		addQuestItem(CAMOMILE_CHARM_ID,
				CARDIENS_LETTER_ID,
				WATER_OF_LIFE_ID,
				MOONFLOWER_CHARM_ID,
				HIERARCHS_LETTER_ID,
				STARDUST_ID,
				PURE_MITHRIL_CUP_ID,
				THALIAS_INSTRUCTIONS_ID,
				ISAELS_LETTER_ID,
				TEARS_OF_UNICORN_ID,
				GRAIL_DIAGRAM_ID,
				PUSHKINS_LIST_ID,
				THALIAS_LETTER1_ID,
				ARKENIAS_CONTRACT_ID,
				ANDARIEL_SCRIPTURE_COPY_ID,
				ARKENIAS_INSTRUCTIONS_ID,
				ADONIUS_LIST_ID,
				THALIAS_LETTER2_ID,
				TALINS_SPEAR_BLADE_ID,
				TALINS_SPEAR_SHAFT_ID,
				TALINS_RUBY_ID,
				TALINS_AQUAMARINE_ID,
				TALINS_AMETHYST_ID,
				TALINS_PERIDOT_ID,
				ISAELS_INSTRUCTIONS_ID,
				GRAIL_OF_PURITY_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;

		if(event.equalsIgnoreCase("1"))
		{
			htmltext = "30460-04.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(CARDIENS_LETTER_ID, 1);
		}
		else if(event.equalsIgnoreCase("30154_1"))
			htmltext = "30154-02.htm";
		else if(event.equalsIgnoreCase("30154_2"))
			htmltext = "30154-03.htm";
		else if(event.equalsIgnoreCase("30154_3"))
			htmltext = "30154-04.htm";
		else if(event.equalsIgnoreCase("30154_4"))
			htmltext = "30154-05.htm";
		else if(event.equalsIgnoreCase("30154_5"))
			htmltext = "30154-06.htm";
		else if(event.equalsIgnoreCase("30154_6"))
		{
			htmltext = "30154-07.htm";
			st.set("cond", "2");
			st.setState(STARTED);
			st.takeItems(CARDIENS_LETTER_ID, 1);
			st.giveItems(MOONFLOWER_CHARM_ID, 1);
			st.giveItems(HIERARCHS_LETTER_ID, 1);
		}
		else if(event.equalsIgnoreCase("30371_1"))
			htmltext = "30371-02.htm";
		else if(event.equalsIgnoreCase("30371_2"))
		{
			htmltext = "30371-03.htm";
			st.set("cond", "3");
			st.setState(STARTED);
			st.takeItems(HIERARCHS_LETTER_ID, 1);
			st.giveItems(GRAIL_DIAGRAM_ID, 1);
		}
		else if(event.equalsIgnoreCase("30371_3"))
			if(st.getPlayer().getLevel() < 37)
			{
				htmltext = "30371-10.htm";
				st.set("cond", "13");
				st.setState(STARTED);
				st.takeItems(STARDUST_ID, 1);
				st.giveItems(THALIAS_INSTRUCTIONS_ID, 1);
			}
			else
			{
				htmltext = "30371-11.htm";
				st.set("cond", "14");
				st.setState(STARTED);
				st.takeItems(STARDUST_ID, 1);
				st.giveItems(THALIAS_LETTER2_ID, 1);
			}
		else if(event.equalsIgnoreCase("30300_1"))
			htmltext = "30300-02.htm";
		else if(event.equalsIgnoreCase("30300_2"))
			htmltext = "30300-03.htm";
		else if(event.equalsIgnoreCase("30300_3"))
			htmltext = "30300-04.htm";
		else if(event.equalsIgnoreCase("30300_4"))
			htmltext = "30300-05.htm";
		else if(event.equalsIgnoreCase("30300_5"))
		{
			htmltext = "30300-06.htm";
			st.set("cond", "4");
			st.setState(STARTED);
			st.takeItems(GRAIL_DIAGRAM_ID, 1);
			st.giveItems(PUSHKINS_LIST_ID, 1);
		}
		else if(event.equalsIgnoreCase("30300_6"))
			htmltext = "30300-09.htm";
		else if(event.equalsIgnoreCase("30300_7"))
		{
			htmltext = "30300-10.htm";
			st.set("cond", "6");
			st.setState(STARTED);
			st.takeItems(PURE_MITHRIL_ORE_ID, st.getQuestItemsCount(PURE_MITHRIL_ORE_ID));
			st.takeItems(ANT_SOLDIER_ACID_ID, st.getQuestItemsCount(ANT_SOLDIER_ACID_ID));
			st.takeItems(WYRMS_TALON1_ID, st.getQuestItemsCount(WYRMS_TALON1_ID));
			st.takeItems(PUSHKINS_LIST_ID, 1);
			st.giveItems(PURE_MITHRIL_CUP_ID, 1);
		}
		else if(event.equalsIgnoreCase("30419_1"))
			htmltext = "30419-02.htm";
		else if(event.equalsIgnoreCase("30419_2"))
			htmltext = "30419-03.htm";
		else if(event.equalsIgnoreCase("30419_3"))
		{
			htmltext = "30419-04.htm";
			st.set("cond", "8");
			st.setState(STARTED);
			st.takeItems(THALIAS_LETTER1_ID, 1);
			st.giveItems(ARKENIAS_CONTRACT_ID, 1);
			st.giveItems(ARKENIAS_INSTRUCTIONS_ID, 1);
		}
		else if(event.equalsIgnoreCase("30375_1"))
		{
			htmltext = "30375-02.htm";
			st.set("cond", "9");
			st.setState(STARTED);
			st.takeItems(ARKENIAS_INSTRUCTIONS_ID, 1);
			st.giveItems(ADONIUS_LIST_ID, 1);
		}
		else if(event.equalsIgnoreCase("30655_1"))
		{
			htmltext = "30655-02.htm";
			st.set("cond", "15");
			st.setState(STARTED);
			st.takeItems(THALIAS_LETTER2_ID, 1);
			st.giveItems(ISAELS_INSTRUCTIONS_ID, 1);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.getQuestItemsCount(MARK_OF_LIFE_ID) > 0 || st.isCompleted())
		{
			st.exitCurrentQuest(true);
			return "completed";
		}
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == 30460)
		{
			if(st.isCreated())
				if(st.getPlayer().getRace().ordinal() == 1)
				{
					if(st.getPlayer().getLevel() < 37)
					{
						htmltext = "30460-02.htm";
						st.exitCurrentQuest(true);
					}
					else
					{
						htmltext = "30460-03.htm";
					}
				}
				else
				{
					htmltext = "30460-01.htm";
				}
			else if(st.getQuestItemsCount(CARDIENS_LETTER_ID) > 0)
				htmltext = "30460-05.htm";
			else if(st.getQuestItemsCount(MOONFLOWER_CHARM_ID) > 0)
				htmltext = "30460-06.htm";
			else if(st.getQuestItemsCount(CAMOMILE_CHARM_ID) > 0)
			{
				if(!st.getPlayer().getVarB("q218"))
				{
					st.getPlayer().setVar("q218", "true");
					st.addExpAndSp(RewardExp, RewardSP);
					st.giveItems(7562, 16);
					st.giveItems(57, RewardAdena);
					st.giveItems(MARK_OF_LIFE_ID, 1);
					st.takeItems(CAMOMILE_CHARM_ID, 1);
				}
				htmltext = "30460-07.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 30154)
		{
			if(st.getQuestItemsCount(CARDIENS_LETTER_ID) > 0)
				htmltext = "30154-01.htm";
			else if(st.getQuestItemsCount(WATER_OF_LIFE_ID) > 0)
			{
				htmltext = "30154-09.htm";
				st.set("cond", "21");
				st.takeItems(WATER_OF_LIFE_ID, 1);
				st.takeItems(MOONFLOWER_CHARM_ID, 1);
				st.giveItems(CAMOMILE_CHARM_ID, 1);
			}
			else if(st.getQuestItemsCount(MOONFLOWER_CHARM_ID) > 0)
				htmltext = "30154-08.htm";
			else if(st.getQuestItemsCount(CAMOMILE_CHARM_ID) > 0)
				htmltext = "30154-10.htm";
		}
		else if(npcId == 30371)
		{
			if(st.getQuestItemsCount(HIERARCHS_LETTER_ID) > 0)
				htmltext = "30371-01.htm";
			else if(st.getQuestItemsCount(GRAIL_DIAGRAM_ID) > 0)
				htmltext = "30371-04.htm";
			else if(st.getQuestItemsCount(PUSHKINS_LIST_ID) > 0)
				htmltext = "30371-05.htm";
			else if(st.getQuestItemsCount(PURE_MITHRIL_CUP_ID) > 0)
			{
				htmltext = "30371-06.htm";
				st.set("cond", "7");
				st.takeItems(PURE_MITHRIL_CUP_ID, 1);
				st.giveItems(THALIAS_LETTER1_ID, 1);
			}
			else if(st.getQuestItemsCount(THALIAS_LETTER1_ID) > 0)
				htmltext = "30371-07.htm";
			else if(st.getQuestItemsCount(ARKENIAS_CONTRACT_ID) > 0)
				htmltext = "30371-08.htm";
			else if(st.getQuestItemsCount(STARDUST_ID) > 0)
				htmltext = "30371-09.htm";
			else if(st.getQuestItemsCount(THALIAS_INSTRUCTIONS_ID) > 0)
			{
				if(st.getPlayer().getLevel() < 37)
				{
					htmltext = "30371-12.htm";
					st.set("cond", "13");
				}
				else
				{
					st.set("cond", "14");
					st.takeItems(THALIAS_INSTRUCTIONS_ID, 1);
					st.giveItems(THALIAS_LETTER2_ID, 1);
				}
			}
			else if(st.getQuestItemsCount(THALIAS_LETTER2_ID) > 0)
				htmltext = "30371-14.htm";
			else if(st.getQuestItemsCount(ISAELS_INSTRUCTIONS_ID) > 0)
				htmltext = "30371-15.htm";
			else if(st.getQuestItemsCount(ISAELS_LETTER_ID) > 0)
			{
				htmltext = "30371-16.htm";
				st.set("cond", "18");
				st.takeItems(ISAELS_LETTER_ID, 1);
				st.giveItems(GRAIL_OF_PURITY_ID, 1);
			}
			else if(st.getQuestItemsCount(GRAIL_OF_PURITY_ID) > 0)
				htmltext = "30371-17.htm";
			else if(st.getQuestItemsCount(TEARS_OF_UNICORN_ID) > 0)
			{
				htmltext = "30371-18.htm";
				st.set("cond", "20");
				st.takeItems(TEARS_OF_UNICORN_ID, 1);
				st.giveItems(WATER_OF_LIFE_ID, 1);
			}
			else if(st.getQuestItemsCount(WATER_OF_LIFE_ID) > 0)
				htmltext = "30371-19.htm";
		}
		else if(npcId == 30300)
		{
			if(st.getQuestItemsCount(GRAIL_DIAGRAM_ID) > 0)
				htmltext = "30300-01.htm";
			else if(st.getQuestItemsCount(PUSHKINS_LIST_ID) > 0)
			{
				if(st.getCond() == 5)
					htmltext = "30300-08.htm";
				else
					htmltext = "30300-07.htm";
			}
			else if(st.getQuestItemsCount(PURE_MITHRIL_CUP_ID) > 0)
				htmltext = "30300-11.htm";
			else if(st.getCond() > 5)
				htmltext = "30300-12.htm";
		}
		else if(npcId == 30419)
		{
			if(st.getQuestItemsCount(THALIAS_LETTER1_ID) > 0)
				htmltext = "30419-01.htm";
			else if(st.getQuestItemsCount(ARKENIAS_INSTRUCTIONS_ID) > 0 || st.getQuestItemsCount(ADONIUS_LIST_ID) > 0)
				htmltext = "30419-05.htm";
			else if(st.getQuestItemsCount(ANDARIEL_SCRIPTURE_COPY_ID) > 0)
			{
				htmltext = "30419-06.htm";
				st.set("cond", "12");
				st.takeItems(ARKENIAS_CONTRACT_ID, 1);
				st.takeItems(ANDARIEL_SCRIPTURE_COPY_ID, 1);
				st.giveItems(STARDUST_ID, 1);
			}
			else if(st.getQuestItemsCount(STARDUST_ID) > 0)
				htmltext = "30419-07.htm";
			else
				htmltext = "30419-08.htm";
		}
		else if(npcId == 30375)
		{
			if(st.getQuestItemsCount(ARKENIAS_INSTRUCTIONS_ID) > 0)
				htmltext = "30375-01.htm";
			else if(st.getQuestItemsCount(ADONIUS_LIST_ID) > 0)
			{
				if(st.getCond() == 10)
				{
					htmltext = "30375-04.htm";
					st.set("cond", "11");
					st.takeItems(SPIDER_ICHOR_ID, st.getQuestItemsCount(SPIDER_ICHOR_ID));
					st.takeItems(HARPYS_DOWN_ID, st.getQuestItemsCount(HARPYS_DOWN_ID));
					st.takeItems(ADONIUS_LIST_ID, 1);
					st.giveItems(ANDARIEL_SCRIPTURE_COPY_ID, 1);
				}
				else
				{
					htmltext = "30375-03.htm";
				}
			}
			else if(st.getQuestItemsCount(ANDARIEL_SCRIPTURE_COPY_ID) > 0)
				htmltext = "30375-05.htm";
			else
				htmltext = "30375-06.htm";
		}
		else if(npcId == 30655)
		{
			if(st.getQuestItemsCount(THALIAS_LETTER2_ID) > 0)
				htmltext = "30655-01.htm";
			else if(st.getQuestItemsCount(ISAELS_INSTRUCTIONS_ID) > 0)
			{
				if(st.getQuestItemsCount(TALINS_SPEAR_BLADE_ID) > 0 && st.getQuestItemsCount(TALINS_SPEAR_SHAFT_ID) > 0 &&
						st.getQuestItemsCount(TALINS_RUBY_ID) > 0 && st.getQuestItemsCount(TALINS_AQUAMARINE_ID) > 0 &&
						st.getQuestItemsCount(TALINS_AMETHYST_ID) > 0 && st.getQuestItemsCount(TALINS_PERIDOT_ID) > 0)
				{
					htmltext = "30655-04.htm";
					st.set("cond", "17");
					st.takeItems(TALINS_SPEAR_BLADE_ID, 1);
					st.takeItems(TALINS_SPEAR_SHAFT_ID, 1);
					st.takeItems(TALINS_RUBY_ID, 1);
					st.takeItems(TALINS_AQUAMARINE_ID, 1);
					st.takeItems(TALINS_AMETHYST_ID, 1);
					st.takeItems(TALINS_PERIDOT_ID, 1);
					st.takeItems(ISAELS_INSTRUCTIONS_ID, 1);
					st.giveItems(ISAELS_LETTER_ID, 1);
					st.giveItems(TALINS_SPEAR_ID, 1);
				}
				else
					htmltext = "30655-03.htm";
			}
			else if(st.getQuestItemsCount(TALINS_SPEAR_ID) > 0 && st.getQuestItemsCount(ISAELS_LETTER_ID) > 0)
				htmltext = "30655-05.htm";
			else if(st.getQuestItemsCount(GRAIL_OF_PURITY_ID) > 0 || st.getQuestItemsCount(CAMOMILE_CHARM_ID) > 0)
				htmltext = "30655-06.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 20550)
		{
			if(cond == 4 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(PUSHKINS_LIST_ID) == 1 && st.rollAndGiveLimited(PURE_MITHRIL_ORE_ID, 1, 50, 10))
			{
				if(st.getQuestItemsCount(PURE_MITHRIL_ORE_ID) < 10)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == 20176)
		{
			if(cond == 4 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(PUSHKINS_LIST_ID) == 1 && st.rollAndGiveLimited(WYRMS_TALON1_ID, 1, 50, 20))
			{
				if(st.getQuestItemsCount(WYRMS_TALON1_ID) < 20)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == 20082 || npcId == 20084 || npcId == 20086)
		{
			if(cond == 4 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(PUSHKINS_LIST_ID) == 1 && st.rollAndGiveLimited(ANT_SOLDIER_ACID_ID, 1, 80, 20))
			{
				if(st.getQuestItemsCount(ANT_SOLDIER_ACID_ID) < 20)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == 20087 || npcId == 20088)
		{
			if(cond == 4 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(PUSHKINS_LIST_ID) == 1 && st.rollAndGiveLimited(ANT_SOLDIER_ACID_ID, 1, 50, 20))
			{
				if(st.getQuestItemsCount(ANT_SOLDIER_ACID_ID) < 20)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}

		if(cond == 4 && st.getQuestItemsCount(PURE_MITHRIL_ORE_ID) >= 10 && st.getQuestItemsCount(WYRMS_TALON1_ID) >= 20
				&& st.getQuestItemsCount(ANT_SOLDIER_ACID_ID) >= 20)
		{
			st.setCond(5);
			st.setState(STARTED);
		}

		if(npcId == 20233)
		{
			if(cond == 9 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(ADONIUS_LIST_ID) == 1 && st.rollAndGiveLimited(SPIDER_ICHOR_ID, 1, 50, 20))
			{
				if(st.getQuestItemsCount(SPIDER_ICHOR_ID) < 20)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == 20145)
		{
			if(cond == 9 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getQuestItemsCount(ADONIUS_LIST_ID) == 1 && st.rollAndGiveLimited(HARPYS_DOWN_ID, 1, 50, 20))
			{
				if(st.getQuestItemsCount(HARPYS_DOWN_ID) < 20)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}

		if(cond == 9 && st.getQuestItemsCount(SPIDER_ICHOR_ID) >= 20 && st.getQuestItemsCount(HARPYS_DOWN_ID) >= 20)
		{
			st.setCond(10);
			st.setState(STARTED);
		}


		if(npcId == 27077)
		{
			if(cond == 18 && st.getQuestItemsCount(MOONFLOWER_CHARM_ID) == 1 && st.getItemEquipped(Inventory.PAPERDOLL_LRHAND) == TALINS_SPEAR_ID && st.getQuestItemsCount(GRAIL_OF_PURITY_ID) == 1 && st.rollAndGiveLimited(TEARS_OF_UNICORN_ID, 1, 100, 1))
				if(st.getQuestItemsCount(TALINS_SPEAR_ID) > 0)
				{
					st.takeItems(GRAIL_OF_PURITY_ID, 1);
					st.takeItems(TALINS_SPEAR_ID, 1);
					st.setCond(19);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
		}
		else if(npcId == 20581 || npcId == 20582)
		{
			if(cond == 15 && st.getQuestItemsCount(ISAELS_INSTRUCTIONS_ID) == 1 && Rnd.chance(50))
				if(st.getQuestItemsCount(TALINS_SPEAR_BLADE_ID) == 0)
				{
					st.giveItems(TALINS_SPEAR_BLADE_ID, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(TALINS_SPEAR_SHAFT_ID) == 0)
				{
					st.giveItems(TALINS_SPEAR_SHAFT_ID, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(TALINS_RUBY_ID) == 0)
				{
					st.giveItems(TALINS_RUBY_ID, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(TALINS_AQUAMARINE_ID) == 0)
				{
					st.giveItems(TALINS_AQUAMARINE_ID, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(TALINS_AMETHYST_ID) == 0)
				{
					st.giveItems(TALINS_AMETHYST_ID, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(st.getQuestItemsCount(TALINS_PERIDOT_ID) == 0)
				{
					st.giveItems(TALINS_PERIDOT_ID, 1);
					st.playSound(SOUND_MIDDLE);
					st.setCond(16);
					st.setState(STARTED);
				}

		}
	}
}
