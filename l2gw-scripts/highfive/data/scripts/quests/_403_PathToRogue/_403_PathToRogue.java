package quests._403_PathToRogue;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _403_PathToRogue extends Quest
{
	//npc
	public final int BEZIQUE = 30379;
	public final int NETI = 30425;
	//mobs
	public final int TRACKER_SKELETON = 20035;
	public final int TRACKER_SKELETON_LEADER = 20042;
	public final int SKELETON_SCOUT = 20045;
	public final int SKELETON_BOWMAN = 20051;
	public final int RUIN_SPARTOI = 20054;
	public final int RAGING_SPARTOI = 20060;
	public final int CATS_EYE_BANDIT = 27038;
	//items
	public final int BEZIQUES_LETTER_ID = 1180;
	public final int SPATOIS_BONES_ID = 1183;
	public final int HORSESHOE_OF_LIGHT_ID = 1184;
	public final int WANTED_BILL_ID = 1185;
	public final int STOLEN_JEWELRY_ID = 1186;
	public final int STOLEN_TOMES_ID = 1187;
	public final int STOLEN_RING_ID = 1188;
	public final int STOLEN_NECKLACE_ID = 1189;
	public final int BEZIQUES_RECOMMENDATION_ID = 1190;
	public final int NETIS_BOW_ID = 1181;
	public final int NETIS_DAGGER_ID = 1182;
	//MobsTable {MOB_ID,CHANCE}
	public final int[][] MobsTable = {
			{TRACKER_SKELETON, 2},
			{TRACKER_SKELETON_LEADER, 3},
			{SKELETON_SCOUT, 2},
			{SKELETON_BOWMAN, 2},
			{RUIN_SPARTOI, 8},
			{RAGING_SPARTOI, 8}};

	public final int[] STOLEN_ITEM = {STOLEN_JEWELRY_ID, STOLEN_TOMES_ID, STOLEN_RING_ID, STOLEN_NECKLACE_ID};

	public _403_PathToRogue()
	{
		super(403, "_403_PathToRogue", "Path to Rogue");

		addStartNpc(BEZIQUE);

		addTalkId(BEZIQUE);
		addTalkId(NETI);

		addKillId(CATS_EYE_BANDIT);
		addAttackId(CATS_EYE_BANDIT);

		for(int[] element : MobsTable)
		{
			addKillId(element[0]);
			addAttackId(element[0]);
		}

		addQuestItem(STOLEN_ITEM);
		addQuestItem(NETIS_BOW_ID, NETIS_DAGGER_ID, WANTED_BILL_ID, HORSESHOE_OF_LIGHT_ID, BEZIQUES_LETTER_ID, SPATOIS_BONES_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30379_2"))
		{
			if(st.getPlayer().getClassId().getId() != 0x00)
			{
				if(st.getPlayer().getClassId().getId() == 0x07)
					htmltext = "30379-02a.htm";
				else
					htmltext = "30379-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID) > 0)
			{
				htmltext = "30379-04.htm";
				st.exitCurrentQuest(true);
			}
			if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30379-03.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30379-05.htm";
		}
		else if(event.equalsIgnoreCase("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.giveItems(BEZIQUES_LETTER_ID, 1);
			htmltext = "30379-06.htm";
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30425_1"))
		{
			st.takeItems(BEZIQUES_LETTER_ID, 1);
			if(st.getQuestItemsCount(NETIS_BOW_ID) < 1)
				st.giveItems(NETIS_BOW_ID, 1);
			if(st.getQuestItemsCount(NETIS_DAGGER_ID) < 1)
				st.giveItems(NETIS_DAGGER_ID, 1);
			st.set("cond", "2");
			htmltext = "30425-05.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == BEZIQUE)
		{
			if(cond == 6 && st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) < 1 && st.getQuestItemsCount(STOLEN_JEWELRY_ID) + st.getQuestItemsCount(STOLEN_TOMES_ID) + st.getQuestItemsCount(STOLEN_RING_ID) + st.getQuestItemsCount(STOLEN_NECKLACE_ID) == 4)
			{
				htmltext = "30379-09.htm";
				st.takeItems(NETIS_BOW_ID, 1);
				st.takeItems(NETIS_DAGGER_ID, 1);
				st.takeItems(WANTED_BILL_ID, 1);
				for(int i : STOLEN_ITEM)
					st.takeItems(i, -1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(BEZIQUES_RECOMMENDATION_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 20232);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 26930);
						else
							st.addExpAndSp(160267, 33628);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
			else if(cond == 1 && st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) < 1 && st.getQuestItemsCount(BEZIQUES_LETTER_ID) > 0)
				htmltext = "30379-07.htm";
			else if(cond == 4 && st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) > 0)
			{
				htmltext = "30379-08.htm";
				st.takeItems(HORSESHOE_OF_LIGHT_ID, 1);
				st.giveItems(WANTED_BILL_ID, 1);
				st.set("cond", "5");
			}
			else if(cond > 1 && st.getQuestItemsCount(NETIS_BOW_ID) > 0 && st.getQuestItemsCount(NETIS_DAGGER_ID) > 0 && st.getQuestItemsCount(WANTED_BILL_ID) < 1)
				htmltext = "30379-10.htm";
			else if(cond == 5 && st.getQuestItemsCount(WANTED_BILL_ID) > 0)
				htmltext = "30379-11.htm";
			else
				htmltext = "30379-01.htm";
		}
		else if(npcId == NETI)
			if(cond == 1 && st.getQuestItemsCount(BEZIQUES_LETTER_ID) > 0)
				htmltext = "30425-01.htm";
			else if(cond == 2 | cond == 3 && st.getQuestItemsCount(SPATOIS_BONES_ID) < 10)
			{
				htmltext = "30425-06.htm";
				st.set("cond", "2");
			}
			else if(cond == 3 && st.getQuestItemsCount(SPATOIS_BONES_ID) > 9)
			{
				htmltext = "30425-07.htm";
				st.takeItems(SPATOIS_BONES_ID, -1);
				st.giveItems(HORSESHOE_OF_LIGHT_ID, 1);
				st.set("cond", "4");
			}
			else if(cond == 4 && st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) > 0)
				htmltext = "30425-08.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		int netis_cond = st.getInt("netis_cond");
		if(netis_cond == 1 && st.getItemEquipped(Inventory.PAPERDOLL_LRHAND) == NETIS_BOW_ID || st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == NETIS_DAGGER_ID)
		{
			Functions.npcSay(npc, Say2C.ALL, "I must do something about this shameful incident...");
			switch(cond)
			{
				case 2:
					for(int[] element : MobsTable)
						if(npcId == element[0] && st.rollAndGiveLimited(SPATOIS_BONES_ID, 1, 10 * element[1], 10))
						{
							if(st.getQuestItemsCount(SPATOIS_BONES_ID) == 10)
							{
								st.playSound(SOUND_MIDDLE);
								st.set("cond", "3");
								st.setState(STARTED);
							}
							else
								st.playSound(SOUND_ITEMGET);
						}
					break;
				case 5:
					if(npcId == CATS_EYE_BANDIT)
						if(st.getQuestItemsCount(WANTED_BILL_ID) > 0)
						{
							int n = Rnd.get(4);
							if(st.getQuestItemsCount(STOLEN_ITEM[n]) == 0)
							{
								st.giveItems(STOLEN_ITEM[n], 1);
								if(st.getQuestItemsCount(STOLEN_JEWELRY_ID) + st.getQuestItemsCount(STOLEN_TOMES_ID) + st.getQuestItemsCount(STOLEN_RING_ID) + st.getQuestItemsCount(STOLEN_NECKLACE_ID) < 4)
									st.playSound(SOUND_ITEMGET);
								else
								{
									st.playSound(SOUND_MIDDLE);
									st.set("cond", "6");
								}
							}
						}
					break;
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int netis_cond = st.getInt("netis_cond");
		if(st.getItemEquipped(Inventory.PAPERDOLL_LRHAND) != NETIS_BOW_ID && st.getItemEquipped(Inventory.PAPERDOLL_RHAND) != NETIS_DAGGER_ID)
			st.set("netis_cond", "0");
		else if(st.isCreated())
		{
			st.set("netis_cond", "1");
			Functions.npcSay(npc, Say2C.ALL, "You childish fool, do you think you can catch me?");
		}
		return null;
	}
}