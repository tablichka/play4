package quests._334_TheWishingPotion;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _334_TheWishingPotion extends Quest
{
	//NPC
	private static final int GRIMA = 27135;
	private static final int SUCCUBUS_OF_SEDUCTION = 27136;
	private static final int GREAT_DEMON_KING = 27138;
	private static final int SANCHES = 27153;
	private static final int ABYSSKING = 27154;
	private static final int EVILOVERLORD = 27155;
	private static final int TORAI = 30557;
	private static final int ALCHEMIST_MATILD = 30738;
	private static final int RUPINA = 30742;
	private static final int WISDOM_CHEST = 30743;
	//Mobs
	private static final int WHISPERING_WIND = 20078;
	private static final int ANT_SOLDIER = 20087;
	private static final int ANT_WARRIOR_CAPTAIN = 20088;
	private static final int SILENOS = 20168;
	private static final int TYRANT = 20192;
	private static final int TYRANT_KINGPIN = 20193;
	private static final int AMBER_BASILISK = 20199;
	private static final int HORROR_MIST_RIPPER = 20227;
	private static final int TURAK_BUGBEAR = 20248;
	private static final int TURAK_BUGBEAR_WARRIOR = 20249;
	private static final int GLASS_JAGUAR = 20250;
	private static final int SECRET_KEEPER_TREE = 27139;
	//Item
	private static final int ADENA_ID = 57;
	private static final int NECKLACE_OF_GRACE_ID = 931;
	private static final int DEMONS_BOOTS_ID = 2435;
	private static final int DEMONS_GLOVES_ID = 2459;
	private static final int WISH_POTION_ID = 3467;
	private static final int ANCIENT_CROWN_ID = 3468;
	private static final int CERTIFICATE_OF_ROYALTY_ID = 3469;
	private static final int ALCHEMY_TEXT_ID = 3678;
	private static final int SECRET_BOOK_ID = 3679;
	private static final int POTION_RECIPE_1_ID = 3680;
	private static final int POTION_RECIPE_2_ID = 3681;
	private static final int MATILDS_ORB_ID = 3682;
	private static final int FORBIDDEN_LOVE_SCROLL_ID = 3683;
	//Quest Item
	private static final int AMBER_SCALE_ID = 3684;
	private static final int WIND_SOULSTONE_ID = 3685;
	private static final int GLASS_EYE_ID = 3686;
	private static final int HORROR_ECTOPLASM_ID = 3687;
	private static final int SILENOS_HORN_ID = 3688;
	private static final int ANT_SOLDIER_APHID_ID = 3689;
	private static final int TYRANTS_CHITIN_ID = 3690;
	private static final int BUGBEAR_BLOOD_ID = 3691;

	public _334_TheWishingPotion()
	{
		super(334, "_334_TheWishingPotion", "The Wishing Potion");

		addStartNpc(ALCHEMIST_MATILD);
		addTalkId(TORAI, WISDOM_CHEST, RUPINA);
		addKillId(GRIMA, SUCCUBUS_OF_SEDUCTION, SANCHES, ABYSSKING, EVILOVERLORD, GREAT_DEMON_KING);
		addKillId(AMBER_BASILISK, ANT_SOLDIER, ANT_WARRIOR_CAPTAIN, GLASS_JAGUAR, HORROR_MIST_RIPPER, SECRET_KEEPER_TREE, SILENOS, TURAK_BUGBEAR, TURAK_BUGBEAR_WARRIOR, TYRANT, TYRANT_KINGPIN, WHISPERING_WIND);
		addQuestItem(ALCHEMY_TEXT_ID, SECRET_BOOK_ID, AMBER_SCALE_ID, WIND_SOULSTONE_ID, GLASS_EYE_ID,
				HORROR_ECTOPLASM_ID, SILENOS_HORN_ID, ANT_SOLDIER_APHID_ID, TYRANTS_CHITIN_ID, BUGBEAR_BLOOD_ID);
	}

	public boolean checkIngr(QuestState st)
	{
		return st.getQuestItemsCount(AMBER_SCALE_ID) == 1 && st.getQuestItemsCount(WIND_SOULSTONE_ID) == 1 && st.getQuestItemsCount(GLASS_EYE_ID) == 1 &&
				st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 1 && st.getQuestItemsCount(SILENOS_HORN_ID) == 1 && st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 1 &&
				st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 1 && st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 1;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = "noquest";

		if(event.equalsIgnoreCase("accept"))
		{
			st.playSound(SOUND_ACCEPT);
			if(st.getQuestItemsCount(ALCHEMY_TEXT_ID) == 0)
				st.giveItems(ALCHEMY_TEXT_ID, 1);
			htmltext = "alchemist_matild_q0334_04.htm";
			st.setMemoState(1);
			st.setCond(1);
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("matild_1"))
		{
			htmltext = "alchemist_matild_q0334_03.htm";
		}
		else if(event.equalsIgnoreCase("matild_2"))
		{
			htmltext = "npchtm:alchemist_matild_q0334_07.htm";
			st.takeItems(SECRET_BOOK_ID, -1);
			st.takeItems(ALCHEMY_TEXT_ID, -1);
			st.giveItems(POTION_RECIPE_1_ID, 1);
			st.giveItems(POTION_RECIPE_2_ID, 1);
			st.setMemoState(2);
			st.setCond(3);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("matild_3"))
		{
			htmltext = "npchtm:alchemist_matild_q0334_10.htm";
		}
		else if(event.equalsIgnoreCase("matild_4"))
		{
			if(st.getQuestItemsCount(AMBER_SCALE_ID) == 1 && st.getQuestItemsCount(GLASS_EYE_ID) == 1 && st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 1 &&
					st.getQuestItemsCount(SILENOS_HORN_ID) == 1 && st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 1 && st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 1 &&
					st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 1 && st.getQuestItemsCount(WIND_SOULSTONE_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 &&
					st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1)
			{
				htmltext = "npchtm:alchemist_matild_q0334_11.htm";
				st.giveItems(WISH_POTION_ID, 1);
				if(st.getQuestItemsCount(MATILDS_ORB_ID) == 0)
					st.giveItems(MATILDS_ORB_ID, 1);
				st.takeItems(AMBER_SCALE_ID, 1);
				st.takeItems(GLASS_EYE_ID, 1);
				st.takeItems(HORROR_ECTOPLASM_ID, 1);
				st.takeItems(SILENOS_HORN_ID, 1);
				st.takeItems(ANT_SOLDIER_APHID_ID, 1);
				st.takeItems(TYRANTS_CHITIN_ID, 1);
				st.takeItems(BUGBEAR_BLOOD_ID, 1);
				st.takeItems(WIND_SOULSTONE_ID, 1);
				st.takeItems(POTION_RECIPE_1_ID, -1);
				st.takeItems(POTION_RECIPE_2_ID, -1);
				st.setMemoState(2);
				st.playSound(SOUND_ITEMGET);
				st.setCond(5);
				st.setState(STARTED);
			}
		}
		else if(event.equalsIgnoreCase("matild_5"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				L2NpcInstance matild = st.getPlayer().getLastNpc();
				if(matild != null && matild.i_ai0 != 1)
					matild.i_ai1 = 0;
				htmltext = "npchtm:alchemist_matild_q0334_13.htm";
			}
			else
				htmltext = "npchtm:alchemist_matild_q0334_14.htm";
		}
		else if(event.equalsIgnoreCase("matild_6"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				htmltext = "npchtm:alchemist_matild_q0334_15a.htm";
			}
			else
			{
				st.giveItems(POTION_RECIPE_1_ID, 1);
				st.giveItems(POTION_RECIPE_2_ID, 1);
				htmltext = "npchtm:alchemist_matild_q0334_15.htm";
			}
		}
		else if(event.equalsIgnoreCase("matild_7"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				L2NpcInstance matild = st.getPlayer().getLastNpc();
				if(matild != null && matild.i_ai0 == 0)
				{
					matild.i_ai0 = 1;
					htmltext = "npchtm:alchemist_matild_q0334_16.htm";
					st.takeItems(WISH_POTION_ID, 1);
					matild.i_ai1 = 1;
					matild.i_ai2 = st.getPlayer().getObjectId();
					st.set("flag", 1);
					startQuestTimer("2336008", 3000, matild, st.getPlayer(), true);
				}
				else
				{
					htmltext = "npchtm:alchemist_matild_q0334_20.htm";

				}
			}
			else
			{
				htmltext = "npchtm:alchemist_matild_q0334_14.htm";
			}
		}
		else if(event.equalsIgnoreCase("matild_8"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				L2NpcInstance matild = st.getPlayer().getLastNpc();
				if(matild != null && matild.i_ai0 == 0)
				{
					matild.i_ai0 = 1;
					htmltext = "npchtm:alchemist_matild_q0334_17.htm";
					st.takeItems(WISH_POTION_ID, 1);
					matild.i_ai1 = 2;
					matild.i_ai2 = st.getPlayer().getObjectId();
					st.set("flag", 2);
					startQuestTimer("2336008", 3000, matild, st.getPlayer(), true);
				}
				else
				{
					htmltext = "npchtm:alchemist_matild_q0334_20.htm";

				}
			}
			else
			{
				htmltext = "npchtm:alchemist_matild_q0334_14.htm";
			}
		}
		else if(event.equalsIgnoreCase("matild_9"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				L2NpcInstance matild = st.getPlayer().getLastNpc();
				if(matild != null && matild.i_ai0 == 0)
				{
					matild.i_ai0 = 1;
					htmltext = "npchtm:alchemist_matild_q0334_18.htm";
					st.takeItems(WISH_POTION_ID, 1);
					matild.i_ai1 = 3;
					matild.i_ai2 = st.getPlayer().getObjectId();
					st.set("flag", 3);
					startQuestTimer("2336008", 3000, matild, st.getPlayer(), true);
				}
				else
				{
					htmltext = "npchtm:alchemist_matild_q0334_20.htm";

				}
			}
			else
			{
				htmltext = "npchtm:alchemist_matild_q0334_14.htm";
			}
		}
		else if(event.equalsIgnoreCase("matild_10"))
		{
			if(st.getQuestItemsCount(WISH_POTION_ID) > 0)
			{
				L2NpcInstance matild = st.getPlayer().getLastNpc();
				if(matild != null && matild.i_ai0 == 0)
				{
					matild.i_ai0 = 1;
					htmltext = "npchtm:alchemist_matild_q0334_19.htm";
					st.takeItems(WISH_POTION_ID, 1);
					matild.i_ai1 = 4;
					matild.i_ai2 = st.getPlayer().getObjectId();
					st.set("flag", 4);
					startQuestTimer("2336008", 3000, matild, st.getPlayer(), true);
				}
				else
				{
					htmltext = "npchtm:alchemist_matild_q0334_20.htm";

				}
			}
			else
			{
				htmltext = "npchtm:alchemist_matild_q0334_14.htm";
			}
		}


		return htmltext;
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.equalsIgnoreCase("2336008") && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 33415);
			startQuestTimer("2336009", 4000, npc, player, true);
		}
		else if(event.equalsIgnoreCase("2336009") && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 33416);
			startQuestTimer("2336010", 4000, npc, player, true);
		}
		else if(event.equalsIgnoreCase("2336010") && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 33417);
			int i0 = 0;
			int i_quest0 = npc.i_ai1;
			if(i_quest0 == 1)
				i0 = Rnd.get(2);
			else if(i_quest0 >= 2 && i_quest0 <= 4)
				i0 = Rnd.get(3);

			switch(i0)
			{
				case 0:
					if(i_quest0 == 1)
					{
						L2NpcInstance fairy = addSpawn(RUPINA, npc.getLoc(), true, 120000);
						Functions.npcSay(fairy, Say2C.ALL, 33420);
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 2)
					{
						L2NpcInstance grima = addSpawn(GRIMA, npc.getLoc(), true, 200000);
						Functions.npcSay(grima, Say2C.ALL, 33422);
						grima = addSpawn(GRIMA, npc.getLoc(), true, 200000);
						Functions.npcSay(grima, Say2C.ALL, 33422);
						grima = addSpawn(GRIMA, npc.getLoc(), true, 200000);
						Functions.npcSay(grima, Say2C.ALL, 33422);
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 3)
					{
						if(player.getObjectId() == npc.i_ai2)
						{
							QuestState st = player.getQuestState(334);
							if(st != null && st.isStarted())
								st.giveItems(CERTIFICATE_OF_ROYALTY_ID, 1);
						}
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 4)
					{
						L2NpcInstance chest = addSpawn(WISDOM_CHEST, npc.getLoc(), true, 120000);
						Functions.npcSay(chest, Say2C.ALL, 33421);
						npc.i_ai0 = 0;
					}
					break;
				case 1:
					if(i_quest0 == 1)
					{
						L2NpcInstance succubus = addSpawn(SUCCUBUS_OF_SEDUCTION, npc.getLoc(), true, 200000);
						Functions.npcSay(succubus, Say2C.ALL, 33423);
						succubus = addSpawn(SUCCUBUS_OF_SEDUCTION, npc.getLoc(), true, 200000);
						Functions.npcSay(succubus, Say2C.ALL, 33423);
						succubus = addSpawn(SUCCUBUS_OF_SEDUCTION, npc.getLoc(), true, 200000);
						Functions.npcSay(succubus, Say2C.ALL, 33423);
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 2)
					{
						if(player.getObjectId() == npc.i_ai2)
						{
							QuestState st = player.getQuestState(334);
							if(st != null && st.isStarted())
								st.rollAndGive(ADENA_ID, 10000, 100);
						}
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 3)
					{
						L2NpcInstance dlord = addSpawn(SANCHES, npc.getLoc(), true);
						Functions.npcSay(dlord, Say2C.ALL, 33424);
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 4)
					{
						L2NpcInstance chest = addSpawn(WISDOM_CHEST, npc.getLoc(), true, 120000);
						Functions.npcSay(chest, Say2C.ALL, 33421);
						npc.i_ai0 = 0;
					}
					break;
				case 2:
					if(i_quest0 == 2)
					{
						if(player.getObjectId() == npc.i_ai2)
						{
							QuestState st = player.getQuestState(334);
							if(st != null && st.isStarted())
								st.rollAndGive(ADENA_ID, 10000, 100);
						}
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 3)
					{
						if(player.getObjectId() == npc.i_ai2)
						{
							QuestState st = player.getQuestState(334);
							if(st != null && st.isStarted())
								st.giveItems(ANCIENT_CROWN_ID, 1);
						}
						npc.i_ai0 = 0;
					}
					else if(i_quest0 == 4)
					{
						L2NpcInstance chest = addSpawn(WISDOM_CHEST, npc.getLoc(), true, 120000);
						Functions.npcSay(chest, Say2C.ALL, 33421);
						npc.i_ai0 = 0;
					}
					break;
			}
		}
		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";

		if(npcId == ALCHEMIST_MATILD)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 30)
				{
					htmltext = "alchemist_matild_q0334_01.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "alchemist_matild_q0334_02.htm";
			}
			else if(st.isStarted() && st.getQuestItemsCount(SECRET_BOOK_ID) == 0 && st.getQuestItemsCount(ALCHEMY_TEXT_ID) == 1)
				htmltext = "npchtm:alchemist_matild_q0334_05.htm";
			else if(st.isStarted() && st.getQuestItemsCount(SECRET_BOOK_ID) == 1 && st.getQuestItemsCount(ALCHEMY_TEXT_ID) == 1)
				htmltext = "npchtm:alchemist_matild_q0334_06.htm";
			else if(st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1
					&& (st.getQuestItemsCount(AMBER_SCALE_ID) == 0 || st.getQuestItemsCount(WIND_SOULSTONE_ID) == 0 ||
					st.getQuestItemsCount(GLASS_EYE_ID) == 0 || st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 0 ||
					st.getQuestItemsCount(SILENOS_HORN_ID) == 0 || st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 0 ||
					st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 0 || st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 0))
				htmltext = "npchtm:alchemist_matild_q0334_08.htm";
			else if(st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(AMBER_SCALE_ID) == 1 && st.getQuestItemsCount(WIND_SOULSTONE_ID) == 1 && st.getQuestItemsCount(GLASS_EYE_ID) == 1 && st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 1 && st.getQuestItemsCount(SILENOS_HORN_ID) == 1 && st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 1 && st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 1 && st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 1)
				htmltext = "npchtm:alchemist_matild_q0334_09.htm";
			else if(st.isStarted() && st.getQuestItemsCount(MATILDS_ORB_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 0 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 0 &&
					(st.getQuestItemsCount(AMBER_SCALE_ID) == 0 || st.getQuestItemsCount(WIND_SOULSTONE_ID) == 0 || st.getQuestItemsCount(GLASS_EYE_ID) == 0 ||
							st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 0 || st.getQuestItemsCount(SILENOS_HORN_ID) == 0 || st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 0 ||
							st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 0 || st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 0))

				htmltext = "npchtm:alchemist_matild_q0334_12.htm";

		}
		else if(npcId == TORAI)
		{
			if(st.isStarted() && st.getQuestItemsCount(FORBIDDEN_LOVE_SCROLL_ID) >= 1)
			{
				st.takeItems(FORBIDDEN_LOVE_SCROLL_ID, 1);
				st.rollAndGive(ADENA_ID, 500000, 100);
				st.playSound(SOUND_MIDDLE);
				htmltext = "npchtm:torai_q0334_01.htm";
			}
		}
		else if(npcId == WISDOM_CHEST)
		{
			if(st.isStarted() && st.getInt("flag") == 4)
			{
				int i0 = Rnd.get(100);
				if(i0 < 10)
				{
					st.giveItems(FORBIDDEN_LOVE_SCROLL_ID, 1);
					htmltext = "npchtm:wisdom_chest_q0334_02.htm";
				}
				else if(i0 >= 10 && i0 < 50)
				{
					htmltext = "npchtm:wisdom_chest_q0334_02.htm";
					int i1 = Rnd.get(4);
					if(i1 == 0)
					{
						st.giveItems(1979, 1);
					}
					else if(i1 == 1)
					{
						st.giveItems(1980, 1);
					}
					else if(i1 == 2)
					{
						st.giveItems(2952, 1);
					}
					else
					{
						st.giveItems(2953, 1);
					}
				}
				else if(i0 >= 50 && i0 < 85)
				{
					htmltext = "npchtm:wisdom_chest_q0334_04.htm";
					int i1 = Rnd.get(2);
					if(i1 == 0)
					{
						st.giveItems(4408, 1);
					}
					else
					{
						st.giveItems(4409, 1);
					}
				}
				else if(i0 >= 85 && i0 < 95)
				{
					htmltext = "npchtm:wisdom_chest_q0334_05.htm";
					int i1 = Rnd.get(4);
					if(i1 == 0)
					{
						st.giveItems(441, 1);
					}
					else if(i1 == 1)
					{
						st.giveItems(472, 1);
					}
					else if(i1 == 2)
					{
						st.giveItems(DEMONS_BOOTS_ID, 1);
					}
					else
					{
						st.giveItems(DEMONS_GLOVES_ID, 1);
					}
				}
				else if(i0 >= 95)
				{
					htmltext = "npchtm:wisdom_chest_q0334_06.htm";
					int i1 = Rnd.get(2);
					if(i1 == 0)
					{
						st.giveItems(12766, 1);
					}
					else
					{
						st.giveItems(12767, 1);
					}
				}
				st.set("flag", 0);
				npc.deleteMe();
			}
		}
		else if(npcId == RUPINA)
		{
			if(st.isStarted() && st.getInt("flag") == 1)
			{
				if(Rnd.chance(4))
				{
					htmltext = "npchtm:fairy_rupina_q0334_01.htm";
					st.giveItems(NECKLACE_OF_GRACE_ID, 1);
					st.set("flag", 0);
				}
				else
				{
					htmltext = "npchtm:fairy_rupina_q0334_02.htm";
					int i0 = Rnd.get(4);
					if(i0 == 0)
					{
						st.giveItems(1979, 1);
					}
					else if(i0 == 1)
					{
						st.giveItems(1980, 1);
					}
					else if(i0 == 2)
					{
						st.giveItems(2952, 1);
					}
					else if(i0 == 3)
					{
						st.giveItems(2953, 1);
					}
					st.set("flag", 0);
				}
				npc.deleteMe();
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();

		if(npcId == GRIMA && st.getMemoState() == 2 && st.getInt("flag") == 2)
		{
			if(Rnd.get(1000) < 33)
			{
				int i0 = Rnd.get(1000);
				if(i0 == 0)
					st.giveItems(ADENA_ID, 100000000);
				else
					st.giveItems(ADENA_ID, 900000);

				st.playSound(SOUND_ITEMGET);
				st.set("flag", 0);
			}
		}
		else if(npcId == SUCCUBUS_OF_SEDUCTION && st.getMemoState() == 2 && st.getInt("flag") == 1 && st.getQuestItemsCount(FORBIDDEN_LOVE_SCROLL_ID) == 0)
		{
			if(Rnd.get(1000) < 28)
			{
				st.giveItems(FORBIDDEN_LOVE_SCROLL_ID, 1);
				st.playSound(SOUND_ITEMGET);
				st.set("flag", 0);
			}
		}
		else if(npcId == SANCHES && st.getMemoState() == 2 && st.getInt("flag") == 3)
		{
			Functions.npcSay(npc, Say2C.ALL, 33414);
			if(Rnd.get(2) == 0)
			{
				L2NpcInstance abyssking = addSpawn(ABYSSKING, npc.getLoc(), true);
				Functions.npcSay(abyssking, Say2C.ALL, 33425);
			}
			else
			{
				int i1 = Rnd.get(4);
				if(i1 == 0)
				{
					st.giveItems(1979, 1);
				}
				else if(i1 == 1)
				{
					st.giveItems(1980, 1);
				}
				else if(i1 == 2)
				{
					st.giveItems(2952, 1);
				}
				else if(i1 == 3)
				{
					st.giveItems(2953, 1);
				}
			}
		}
		else if(npcId == ABYSSKING && st.getMemoState() == 2 && st.getInt("flag") == 3)
		{
			Functions.npcSay(npc, Say2C.ALL, 33413);
			if(Rnd.get(2) == 0)
			{
				L2NpcInstance eviloverlord = addSpawn(EVILOVERLORD, npc.getLoc(), true);
				Functions.npcSay(eviloverlord, Say2C.ALL, 33426);
			}
			else
			{
				int i1 = Rnd.get(4);
				if(i1 == 0)
				{
					st.giveItems(1979, 1);
				}
				else if(i1 == 1)
				{
					st.giveItems(1980, 1);
				}
				else if(i1 == 2)
				{
					st.giveItems(2952, 1);
				}
				else if(i1 == 3)
				{
					st.giveItems(2953, 1);
				}
			}
		}
		else if(npcId == EVILOVERLORD && st.getMemoState() == 2 && st.getInt("flag") == 3)
		{
			Functions.npcSay(npc, Say2C.ALL, 33412);
			if(Rnd.get(2) == 0)
			{
				L2NpcInstance greatdemon = addSpawn(GREAT_DEMON_KING, npc.getLoc(), true);
				Functions.npcSay(greatdemon, Say2C.ALL, 33418);
				greatdemon.addDamageHate(st.getPlayer(), 0, 200000);
				greatdemon.setRunning();
				greatdemon.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());

			}
			else
			{
				int i1 = Rnd.get(4);
				if(i1 == 0)
				{
					st.giveItems(1979, 1);
				}
				else if(i1 == 1)
				{
					st.giveItems(1980, 1);
				}
				else if(i1 == 2)
				{
					st.giveItems(2952, 1);
				}
				else if(i1 == 3)
				{
					st.giveItems(2953, 1);
				}
			}
		}
		else if(npcId == GREAT_DEMON_KING && st.getMemoState() == 2 && st.getInt("flag") == 3)
		{
			st.rollAndGive(ADENA_ID, 1406956, 100);
			st.playSound(SOUND_ITEMGET);
			st.set("flag", 0);
		}
		else if(npcId == AMBER_BASILISK && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(AMBER_SCALE_ID) == 0)
		{
			if(st.rollAndGiveLimited(AMBER_SCALE_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if((npcId == ANT_SOLDIER || npcId == ANT_WARRIOR_CAPTAIN) && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(ANT_SOLDIER_APHID_ID) == 0)
		{
			if(st.rollAndGiveLimited(ANT_SOLDIER_APHID_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if(npcId == GLASS_JAGUAR && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(GLASS_EYE_ID) == 0)
		{
			if(st.rollAndGiveLimited(GLASS_EYE_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if(npcId == HORROR_MIST_RIPPER && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(HORROR_ECTOPLASM_ID) == 0)
		{
			if(st.rollAndGiveLimited(HORROR_ECTOPLASM_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if(npcId == SECRET_KEEPER_TREE && st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(SECRET_BOOK_ID) == 0)
		{
			st.giveItems(SECRET_BOOK_ID, 1);
			st.setCond(2);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(npcId == SILENOS && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(SILENOS_HORN_ID) == 0)
		{
			if(st.rollAndGiveLimited(SILENOS_HORN_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if(npcId == WHISPERING_WIND && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(WIND_SOULSTONE_ID) == 0)
		{
			if(st.rollAndGiveLimited(WIND_SOULSTONE_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if((npcId == TURAK_BUGBEAR || npcId == TURAK_BUGBEAR_WARRIOR) && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(BUGBEAR_BLOOD_ID) == 0)
		{
			if(st.rollAndGiveLimited(BUGBEAR_BLOOD_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
		else if((npcId == TYRANT || npcId == TYRANT_KINGPIN) && st.isStarted() && st.getQuestItemsCount(POTION_RECIPE_1_ID) == 1 && st.getQuestItemsCount(POTION_RECIPE_2_ID) == 1 && st.getQuestItemsCount(TYRANTS_CHITIN_ID) == 0)
		{
			if(st.rollAndGiveLimited(TYRANTS_CHITIN_ID, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				if(checkIngr(st))
				{
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
	}
}