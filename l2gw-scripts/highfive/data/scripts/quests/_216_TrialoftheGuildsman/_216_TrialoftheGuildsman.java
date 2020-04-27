package quests._216_TrialoftheGuildsman;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * Квест на вторую профессию Trial Of The Guildsman
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _216_TrialoftheGuildsman extends Quest
{
	//NPC
	private static final int VALKON = 30103;
	private static final int NORMAN = 30210;
	private static final int ALTRAN = 30283;
	private static final int PINTER = 30298;
	private static final int DUNING = 30688;
	//Quest Item
	private static final int MARK_OF_GUILDSMAN = 3119;
	private static final int VALKONS_RECOMMEND = 3120;
	private static final int MANDRAGORA_BERRY = 3121;
	private static final int ALLTRANS_INSTRUCTIONS = 3122;
	private static final int ALLTRANS_RECOMMEND1 = 3123;
	private static final int ALLTRANS_RECOMMEND2 = 3124;
	private static final int NORMANS_INSTRUCTIONS = 3125;
	private static final int NORMANS_RECEIPT = 3126;
	private static final int DUNINGS_INSTRUCTIONS = 3127;
	private static final int DUNINGS_KEY = 3128;
	private static final int NORMANS_LIST = 3129;
	private static final int GRAY_BONE_POWDER = 3130;
	private static final int GRANITE_WHETSTONE = 3131;
	private static final int RED_PIGMENT = 3132;
	private static final int BRAIDED_YARN = 3133;
	private static final int JOURNEYMAN_GEM = 3134;
	private static final int PINTERS_INSTRUCTIONS = 3135;
	private static final int AMBER_BEAD = 3136;
	private static final int AMBER_LUMP = 3137;
	private static final int JOURNEYMAN_DECO_BEADS = 3138;
	private static final int JOURNEYMAN_RING = 3139;
	private static final int RP_JOURNEYMAN_RING = 3024;
	private static final int DIMENSION_DIAMOND = 7562;
	private static final int RP_AMBER_BEAD = 3025;
	//Item
	private static final int ADENA = 57;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{3, 4, 20223, VALKONS_RECOMMEND, MANDRAGORA_BERRY, 1, 20, 1},
			{3, 4, 20154, VALKONS_RECOMMEND, MANDRAGORA_BERRY, 1, 50, 1},
			{3, 4, 20155, VALKONS_RECOMMEND, MANDRAGORA_BERRY, 1, 50, 1},
			{3, 4, 20156, VALKONS_RECOMMEND, MANDRAGORA_BERRY, 1, 50, 1},
			{5, 0, 20267, DUNINGS_INSTRUCTIONS, DUNINGS_KEY, 30, 30, 1},
			{5, 0, 20268, DUNINGS_INSTRUCTIONS, DUNINGS_KEY, 30, 30, 1},
			{5, 0, 20269, DUNINGS_INSTRUCTIONS, DUNINGS_KEY, 30, 30, 1},
			{5, 0, 20270, DUNINGS_INSTRUCTIONS, DUNINGS_KEY, 30, 30, 1},
			{5, 0, 20271, DUNINGS_INSTRUCTIONS, DUNINGS_KEY, 30, 30, 1},
			{5, 0, 20200, NORMANS_LIST, GRAY_BONE_POWDER, 70, 100, 2},
			{5, 0, 20201, NORMANS_LIST, GRAY_BONE_POWDER, 70, 100, 2},
			{5, 0, 20202, NORMANS_LIST, RED_PIGMENT, 70, 100, 2},
			{5, 0, 20083, NORMANS_LIST, GRANITE_WHETSTONE, 70, 100, 2},
			{5, 0, 20168, NORMANS_LIST, BRAIDED_YARN, 70, 100, 2}};

	private static boolean QuestProf = true;

	public _216_TrialoftheGuildsman()
	{
		super(216, "_216_TrialoftheGuildsman", "Trial of the Guildsman");

		addStartNpc(VALKON);
		addTalkId(VALKON);
		addTalkId(NORMAN);
		addTalkId(ALTRAN);
		addTalkId(PINTER);
		addTalkId(DUNING);

		addKillId(20079);
		addKillId(20080);
		addKillId(20081);

		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);

		addQuestItem(ALLTRANS_INSTRUCTIONS,
				VALKONS_RECOMMEND,
				ALLTRANS_RECOMMEND1,
				NORMANS_INSTRUCTIONS,
				NORMANS_LIST,
				NORMANS_RECEIPT,
				ALLTRANS_RECOMMEND2,
				PINTERS_INSTRUCTIONS,
				DUNINGS_INSTRUCTIONS,
				JOURNEYMAN_GEM,
				JOURNEYMAN_DECO_BEADS,
				JOURNEYMAN_RING,
				AMBER_BEAD,
				AMBER_LUMP,
				MANDRAGORA_BERRY,
				DUNINGS_KEY,
				GRAY_BONE_POWDER,
				RED_PIGMENT,
				GRANITE_WHETSTONE,
				BRAIDED_YARN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30103-06.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(VALKONS_RECOMMEND, 1);
			st.takeItems(ADENA, 2000);
			if(!st.getPlayer().getVarB("dd1"))
			{
				st.giveItems(DIMENSION_DIAMOND, 64);
				st.getPlayer().setVar("dd1", "1");
			}
		}
		else if(event.equalsIgnoreCase("30103-07c.htm"))
			st.set("cond", "3");
		else if(event.equalsIgnoreCase("30103-05.htm") && st.getQuestItemsCount(ADENA) < 2000)
			htmltext = "30103-05a.htm";
		else if(event.equalsIgnoreCase("30103_3") || event.equalsIgnoreCase("30103_4"))
		{
			if(event.equalsIgnoreCase("30103_3"))
				htmltext = "30103-09a.htm";
			else
				htmltext = "30103-09b.htm";
			st.takeItems(JOURNEYMAN_RING, -1);
			st.takeItems(ALLTRANS_INSTRUCTIONS, -1);
			st.takeItems(RP_JOURNEYMAN_RING, -1);
			st.giveItems(MARK_OF_GUILDSMAN, 1);
			if(!st.getPlayer().getVarB("q216"))
			{
				st.addExpAndSp(514739, 33384);
				st.rollAndGive(57, 93803, 100);
				st.getPlayer().setVar("q216", "1");
			}
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		else if(event.equalsIgnoreCase("30283-03.htm"))
		{
			st.takeItems(VALKONS_RECOMMEND, -1);
			st.takeItems(MANDRAGORA_BERRY, -1);
			st.giveItems(ALLTRANS_INSTRUCTIONS, 1);
			st.giveItems(RP_JOURNEYMAN_RING, 1);
			st.giveItems(ALLTRANS_RECOMMEND1, 1);
			st.giveItems(ALLTRANS_RECOMMEND2, 1);
			st.set("cond", "5");
		}
		else if(event.equalsIgnoreCase("30210-04.htm"))
		{
			st.takeItems(ALLTRANS_RECOMMEND1, -1);
			st.giveItems(NORMANS_INSTRUCTIONS, 1);
			st.giveItems(NORMANS_RECEIPT, 1);
		}
		else if(event.equalsIgnoreCase("30210-10.htm"))
		{
			st.takeItems(DUNINGS_KEY, -1);
			st.takeItems(NORMANS_INSTRUCTIONS, -1);
			st.giveItems(NORMANS_LIST, 1);
		}
		else if(event.equalsIgnoreCase("30688-02.htm"))
		{
			st.takeItems(NORMANS_RECEIPT, -1);
			st.giveItems(DUNINGS_INSTRUCTIONS, 1);
		}
		else if(event.equalsIgnoreCase("30298-04.htm"))
		{
			st.takeItems(ALLTRANS_RECOMMEND2, -1);
			st.giveItems(PINTERS_INSTRUCTIONS, 1);

			if(st.getPlayer().getClassId().getId() == 0x38)
			{
				htmltext = "30298-05.htm";
				st.giveItems(RP_AMBER_BEAD, 1);
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == VALKON)
		{
			if(st.getQuestItemsCount(MARK_OF_GUILDSMAN) > 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() == 0x36 || st.getPlayer().getClassId().getId() == 0x38)
				{
					if(st.getPlayer().getLevel() >= 35)
						htmltext = "30103-03.htm";
					else
					{
						htmltext = "30103-02.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "30103-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 2 && st.getQuestItemsCount(VALKONS_RECOMMEND) > 0)
				htmltext = "30103-07.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0)
				if(st.getQuestItemsCount(JOURNEYMAN_RING) < 7)
					htmltext = "30103-08.htm";
				else
					htmltext = "30103-09.htm";
		}
		else if(npcId == ALTRAN)
		{
			if(cond == 1 && st.getQuestItemsCount(VALKONS_RECOMMEND) > 0)
			{
				htmltext = "30283-01.htm";
				st.set("cond", "2");
			}
			else if(cond == 4 && st.getQuestItemsCount(VALKONS_RECOMMEND) > 0 && st.getQuestItemsCount(MANDRAGORA_BERRY) == 1)
				htmltext = "30283-02.htm";
			else if(cond < 6 && st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) == 1 && st.getQuestItemsCount(JOURNEYMAN_RING) < 7)
				htmltext = "30283-04.htm";
			else if(cond == 6 && st.getQuestItemsCount(JOURNEYMAN_RING) == 7)
				htmltext = "30283-05.htm";
		}
		else if(npcId == NORMAN && cond >= 5)
		{
			if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) == 1 && st.getQuestItemsCount(ALLTRANS_RECOMMEND1) == 1)
				htmltext = "30210-01.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_RECEIPT) > 0)
				htmltext = "30210-05.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(DUNINGS_INSTRUCTIONS) > 0)
				htmltext = "30210-06.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(DUNINGS_KEY) >= 30)
				htmltext = "30210-07.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_LIST) > 0)
			{
				if(st.getQuestItemsCount(GRAY_BONE_POWDER) >= 70 && st.getQuestItemsCount(GRANITE_WHETSTONE) >= 70 && st.getQuestItemsCount(RED_PIGMENT) >= 70 && st.getQuestItemsCount(BRAIDED_YARN) >= 70)
				{
					htmltext = "30210-12.htm";
					st.takeItems(NORMANS_LIST, -1);
					st.takeItems(GRAY_BONE_POWDER, -1);
					st.takeItems(GRANITE_WHETSTONE, -1);
					st.takeItems(RED_PIGMENT, -1);
					st.takeItems(BRAIDED_YARN, -1);
					st.giveItems(JOURNEYMAN_GEM, 7);
					if(st.getQuestItemsCount(JOURNEYMAN_DECO_BEADS) == 7 && st.getQuestItemsCount(JOURNEYMAN_GEM) == 7)
						st.set("cond", "6");
				}
				else
					htmltext = "30210-11.htm";
			}
			else if(st.getQuestItemsCount(NORMANS_INSTRUCTIONS) == 0 && st.getQuestItemsCount(NORMANS_LIST) == 0 && st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) == 1 && (st.getQuestItemsCount(JOURNEYMAN_GEM) > 0 || st.getQuestItemsCount(JOURNEYMAN_RING) > 0))
				htmltext = "30210-13.htm";
		}
		else if(npcId == DUNING && cond >= 5)
		{
			if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_RECEIPT) > 0)
				htmltext = "30688-01.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(DUNINGS_INSTRUCTIONS) > 0)
				htmltext = "30688-03.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(NORMANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(DUNINGS_KEY) == 30)
				htmltext = "30688-04.htm";
			else if(st.getQuestItemsCount(NORMANS_RECEIPT) == 0 && st.getQuestItemsCount(DUNINGS_INSTRUCTIONS) == 0 && st.getQuestItemsCount(DUNINGS_KEY) == 0 && st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) == 1)
				htmltext = "30688-01.htm";
		}
		else if(npcId == PINTER && cond >= 5)
			if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(ALLTRANS_RECOMMEND2) > 0)
				if(st.getPlayer().getLevel() < 36)
					htmltext = "30298-01.htm";
				else
					htmltext = "30298-02.htm";
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(PINTERS_INSTRUCTIONS) > 0)
				if(st.getQuestItemsCount(AMBER_BEAD) < 70)
					htmltext = "30298-06.htm";
				else
				{
					htmltext = "30298-07.htm";
					st.takeItems(PINTERS_INSTRUCTIONS, -1);
					st.takeItems(AMBER_BEAD, -1);
					st.takeItems(RP_AMBER_BEAD, -1);
					st.takeItems(AMBER_LUMP, -1);
					st.giveItems(JOURNEYMAN_DECO_BEADS, 7);
					if(st.getQuestItemsCount(JOURNEYMAN_DECO_BEADS) == 7 && st.getQuestItemsCount(JOURNEYMAN_GEM) == 7)
						st.set("cond", "6");
				}
			else if(st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) == 1 && st.getQuestItemsCount(PINTERS_INSTRUCTIONS) == 0 && (st.getQuestItemsCount(JOURNEYMAN_DECO_BEADS) > 0 || st.getQuestItemsCount(JOURNEYMAN_RING) > 0))
				htmltext = "30298-08.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		for(int[] aDROPLIST_COND : DROPLIST_COND)
			if(cond == aDROPLIST_COND[0] && npcId == aDROPLIST_COND[2])
				if(aDROPLIST_COND[3] == 0 || st.getQuestItemsCount(aDROPLIST_COND[3]) > 0)
				{
					if(aDROPLIST_COND[5] == 0)
						st.rollAndGive(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6]);
					else if(st.rollAndGiveLimited(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6], aDROPLIST_COND[5]))
					{
						if(aDROPLIST_COND[4] == DUNINGS_KEY && st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5])
							st.takeItems(DUNINGS_INSTRUCTIONS, -1);
						if(st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5] && aDROPLIST_COND[1] != cond && aDROPLIST_COND[1] != 0)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(aDROPLIST_COND[1]);
							st.setState(STARTED);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
		if(cond == 5 && (npcId == 20079 || npcId == 20080 || npcId == 20081))
			if(Rnd.chance(50) && st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS) > 0 && st.getQuestItemsCount(PINTERS_INSTRUCTIONS) > 0)
			{
				long count = st.getQuestItemsCount(AMBER_BEAD) + st.getQuestItemsCount(AMBER_LUMP) * 5;
				if(count < 70 && st.getPlayer().getClassId().getId() == 0x36)
				{
					st.giveItems(AMBER_BEAD, 1);
					if(st.getQuestItemsCount(AMBER_BEAD) == 70)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
				if(count < 70 && st.getPlayer().getClassId().getId() == 0x38)
				{
					st.giveItems(AMBER_LUMP, 1);
					count = st.getQuestItemsCount(AMBER_BEAD) + st.getQuestItemsCount(AMBER_LUMP) * 5;
					if(count == 70)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
	}
}
