package quests._402_PathToKnight;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _402_PathToKnight extends Quest
{
	//npc
	public final int SIR_KLAUS_VASPER = 30417;
	public final int BIOTIN = 30031;
	public final int LEVIAN = 30037;
	public final int GILBERT = 30039;
	public final int RAYMOND = 30289;
	public final int SIR_COLLIN_WINDAWOOD = 30311;
	public final int BATHIS = 30332;
	public final int BEZIQUE = 30379;
	public final int SIR_ARON_TANFORD = 30653;
	//mobs
	public final int BUGBEAR_RAIDER = 20775;
	public final int UNDEAD_PRIEST = 27024;
	public final int POISON_SPIDER = 20038;
	public final int ARACHNID_TRACKER = 20043;
	public final int ARACHNID_PREDATOR = 20050;
	public final int LANGK_LIZARDMAN = 20030;
	public final int LANGK_LIZARDMAN_SCOUT = 20027;
	public final int LANGK_LIZARDMAN_WARRIOR = 20024;
	public final int GIANT_SPIDER = 20103;
	public final int TALON_SPIDER = 20106;
	public final int BLADE_SPIDER = 20108;
	public final int SILENT_HORROR = 20404;
	//items
	public final int SWORD_OF_RITUAL = 1161;
	public final int COIN_OF_LORDS1 = 1162;
	public final int COIN_OF_LORDS2 = 1163;
	public final int COIN_OF_LORDS3 = 1164;
	public final int COIN_OF_LORDS4 = 1165;
	public final int COIN_OF_LORDS5 = 1166;
	public final int COIN_OF_LORDS6 = 1167;
	public final int GLUDIO_GUARDS_MARK1 = 1168;
	public final int BUGBEAR_NECKLACE = 1169;
	public final int EINHASAD_CHURCH_MARK1 = 1170;
	public final int EINHASAD_CRUCIFIX = 1171;
	public final int GLUDIO_GUARDS_MARK2 = 1172;
	public final int POISON_SPIDER_LEG1 = 1173;
	public final int EINHASAD_CHURCH_MARK2 = 1174;
	public final int LIZARDMAN_TOTEM = 1175;
	public final int GLUDIO_GUARDS_MARK3 = 1176;
	public final int GIANT_SPIDER_HUSK = 1177;
	public final int EINHASAD_CHURCH_MARK3 = 1178;
	public final int HORRIBLE_SKULL = 1179;
	public final int MARK_OF_ESQUIRE = 1271;
	//	# [MOB_ID, REQUIRED, ITEM, NEED_COUNT, CHANCE]
	public final int[][] DROPLIST = {
			{BUGBEAR_RAIDER, GLUDIO_GUARDS_MARK1, BUGBEAR_NECKLACE, 10, 100},
			{UNDEAD_PRIEST, EINHASAD_CHURCH_MARK1, EINHASAD_CRUCIFIX, 12, 100},
			{POISON_SPIDER, GLUDIO_GUARDS_MARK2, POISON_SPIDER_LEG1, 20, 100},
			{ARACHNID_TRACKER, GLUDIO_GUARDS_MARK2, POISON_SPIDER_LEG1, 20, 100},
			{ARACHNID_PREDATOR, GLUDIO_GUARDS_MARK2, POISON_SPIDER_LEG1, 20, 100},
			{LANGK_LIZARDMAN, EINHASAD_CHURCH_MARK2, LIZARDMAN_TOTEM, 20, 50},
			{LANGK_LIZARDMAN_SCOUT, EINHASAD_CHURCH_MARK2, LIZARDMAN_TOTEM, 20, 100},
			{LANGK_LIZARDMAN_WARRIOR, EINHASAD_CHURCH_MARK2, LIZARDMAN_TOTEM, 20, 100},
			{GIANT_SPIDER, GLUDIO_GUARDS_MARK3, GIANT_SPIDER_HUSK, 20, 40},
			{TALON_SPIDER, GLUDIO_GUARDS_MARK3, GIANT_SPIDER_HUSK, 20, 40},
			{BLADE_SPIDER, GLUDIO_GUARDS_MARK3, GIANT_SPIDER_HUSK, 20, 40},
			{SILENT_HORROR, EINHASAD_CHURCH_MARK3, HORRIBLE_SKULL, 10, 100}};

	public _402_PathToKnight()
	{
		super(402, "_402_PathToKnight", "Path to Knight");

		addStartNpc(SIR_KLAUS_VASPER);

		addTalkId(SIR_KLAUS_VASPER);
		addTalkId(BIOTIN);
		addTalkId(LEVIAN);
		addTalkId(GILBERT);
		addTalkId(RAYMOND);
		addTalkId(SIR_COLLIN_WINDAWOOD);
		addTalkId(BATHIS);
		addTalkId(BEZIQUE);
		addTalkId(SIR_ARON_TANFORD);

		for(int[] element : DROPLIST)
			addKillId(element[0]);

		addQuestItem(BUGBEAR_NECKLACE, EINHASAD_CRUCIFIX, POISON_SPIDER_LEG1, LIZARDMAN_TOTEM, GIANT_SPIDER_HUSK, HORRIBLE_SKULL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		Integer classid = st.getPlayer().getClassId().getId();
		Byte level = st.getPlayer().getLevel();
		long squire = st.getQuestItemsCount(MARK_OF_ESQUIRE);
		long coin1 = st.getQuestItemsCount(COIN_OF_LORDS1);
		long coin2 = st.getQuestItemsCount(COIN_OF_LORDS2);
		long coin3 = st.getQuestItemsCount(COIN_OF_LORDS3);
		long coin4 = st.getQuestItemsCount(COIN_OF_LORDS4);
		long coin5 = st.getQuestItemsCount(COIN_OF_LORDS5);
		long coin6 = st.getQuestItemsCount(COIN_OF_LORDS6);
		long guards_mark1 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK1);
		long guards_mark2 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK2);
		long guards_mark3 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK3);
		long church_mark1 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK1);
		long church_mark2 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK2);
		long church_mark3 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK3);
		if(event.equalsIgnoreCase("30417-02a.htm"))
		{
			if(classid != 0x00)
			{
				if(classid == 0x04)
					htmltext = "30417-02a.htm";
				else
					htmltext = "30417-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(SWORD_OF_RITUAL) > 0)
			{
				htmltext = "30417-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(level < 18)
			{
				htmltext = "30417-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30417-05.htm";
		}
		else if(event.equalsIgnoreCase("30417-08.htm"))
		{
			if(st.getInt("cond") == 0 && classid == 0x00 && level >= 18)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.giveItems(MARK_OF_ESQUIRE, 1);
			}
		}
		else if(event.equalsIgnoreCase("30332-02.htm"))
		{
			if(squire > 0 && guards_mark1 < 1 && coin1 < 1)
				st.giveItems(GLUDIO_GUARDS_MARK1, 1);
		}
		else if(event.equalsIgnoreCase("30289-03.htm"))
		{
			if(squire > 0 && church_mark1 < 1 && coin2 < 1)
				st.giveItems(EINHASAD_CHURCH_MARK1, 1);
		}
		else if(event.equalsIgnoreCase("30379-02.htm"))
		{
			if(squire > 0 && guards_mark2 < 1 && coin3 < 1)
				st.giveItems(GLUDIO_GUARDS_MARK2, 1);
		}
		else if(event.equalsIgnoreCase("30037-02.htm"))
		{
			if(squire > 0 && church_mark2 < 1 && coin4 < 1)
				st.giveItems(EINHASAD_CHURCH_MARK2, 1);
		}
		else if(event.equalsIgnoreCase("30039-02.htm"))
		{
			if(squire > 0 && guards_mark3 < 1 && coin5 < 1)
				st.giveItems(GLUDIO_GUARDS_MARK3, 1);
		}
		else if(event.equalsIgnoreCase("30031-02.htm"))
		{
			if(squire > 0 && church_mark3 < 1 && coin6 < 1)
				st.giveItems(EINHASAD_CHURCH_MARK3, 1);
		}
		else if(event.equalsIgnoreCase("30417-13.htm") | event.equalsIgnoreCase("30417-14.htm"))
			if(squire > 0 && coin1 + coin2 + coin3 + coin4 + coin5 + coin6 >= 3)
			{
				for(int i = 1162; i < 1179; i++)
					st.takeItems(i, -1);
				st.takeItems(MARK_OF_ESQUIRE, -1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(SWORD_OF_RITUAL, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 23152);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 29850);
						else
							st.addExpAndSp(160267, 36542);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		long squire = st.getQuestItemsCount(MARK_OF_ESQUIRE);
		long coin1 = st.getQuestItemsCount(COIN_OF_LORDS1);
		long coin2 = st.getQuestItemsCount(COIN_OF_LORDS2);
		long coin3 = st.getQuestItemsCount(COIN_OF_LORDS3);
		long coin4 = st.getQuestItemsCount(COIN_OF_LORDS4);
		long coin5 = st.getQuestItemsCount(COIN_OF_LORDS5);
		long coin6 = st.getQuestItemsCount(COIN_OF_LORDS6);
		long guards_mark1 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK1);
		long guards_mark2 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK2);
		long guards_mark3 = st.getQuestItemsCount(GLUDIO_GUARDS_MARK3);
		long church_mark1 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK1);
		long church_mark2 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK2);
		long church_mark3 = st.getQuestItemsCount(EINHASAD_CHURCH_MARK3);

		if(npcId == SIR_KLAUS_VASPER)
		{
			if(st.isCreated())
				htmltext = "30417-01.htm";
			else if(cond == 1 && squire > 0)
				if(coin1 + coin2 + coin3 + coin4 + coin5 + coin6 < 3)
					htmltext = "30417-09.htm";
				else if(coin1 + coin2 + coin3 + coin4 + coin5 + coin6 == 3)
					htmltext = "30417-10.htm";
				else if(coin1 + coin2 + coin3 + coin4 + coin5 + coin6 > 3 && coin1 + coin2 + coin3 + coin4 + coin5 + coin6 < 6)
					htmltext = "30417-11.htm";
				else if(coin1 + coin2 + coin3 + coin4 + coin5 + coin6 == 6)
				{
					htmltext = "30417-12.htm";
					for(int i = 1162; i < 1179; i++)
						st.takeItems(i, -1);
					st.takeItems(MARK_OF_ESQUIRE, -1);
					st.giveItems(SWORD_OF_RITUAL, 1);
					st.unset("cond");
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
				}
		}
		else if(npcId == BATHIS && cond == 1 && squire > 0)
		{
			if(guards_mark1 < 1 && coin1 < 1)
				htmltext = "30332-01.htm";
			else if(guards_mark1 > 0)
			{
				if(st.getQuestItemsCount(BUGBEAR_NECKLACE) < 10)
					htmltext = "30332-03.htm";
				else
				{
					htmltext = "30332-04.htm";
					st.takeItems(BUGBEAR_NECKLACE, -1);
					st.takeItems(GLUDIO_GUARDS_MARK1, 1);
					st.giveItems(COIN_OF_LORDS1, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin1 > 0)
				htmltext = "30332-05.htm";
		}
		else if(npcId == RAYMOND && cond == 1 && squire > 0)
		{
			if(church_mark1 < 1 && coin2 < 1)
				htmltext = "30289-01.htm";
			else if(church_mark1 > 0)
			{
				if(st.getQuestItemsCount(EINHASAD_CRUCIFIX) < 12)
					htmltext = "30289-04.htm";
				else
				{
					htmltext = "30289-05.htm";
					st.takeItems(EINHASAD_CRUCIFIX, -1);
					st.takeItems(EINHASAD_CHURCH_MARK1, 1);
					st.giveItems(COIN_OF_LORDS2, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin2 > 0)
				htmltext = "30289-06.htm";
		}
		else if(npcId == BEZIQUE && cond == 1 && squire > 0)
		{
			if(coin3 < 1 && guards_mark2 < 1)
				htmltext = "30379-01.htm";
			else if(guards_mark2 > 0)
			{
				if(st.getQuestItemsCount(POISON_SPIDER_LEG1) < 20)
					htmltext = "30379-03.htm";
				else
				{
					htmltext = "30379-04.htm";
					st.takeItems(POISON_SPIDER_LEG1, -1);
					st.takeItems(GLUDIO_GUARDS_MARK2, 1);
					st.giveItems(COIN_OF_LORDS3, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin3 > 0)
				htmltext = "30379-05.htm";
		}
		else if(npcId == LEVIAN && cond == 1 && squire > 0)
		{
			if(coin4 < 1 && church_mark2 < 1)
				htmltext = "30037-01.htm";
			else if(church_mark2 > 0)
			{
				if(st.getQuestItemsCount(LIZARDMAN_TOTEM) < 20)
					htmltext = "30037-03.htm";
				else
				{
					htmltext = "30037-04.htm";
					st.takeItems(LIZARDMAN_TOTEM, -1);
					st.takeItems(EINHASAD_CHURCH_MARK2, 1);
					st.giveItems(COIN_OF_LORDS4, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin4 > 0)
				htmltext = "30037-05.htm";
		}
		else if(npcId == GILBERT && cond == 1 && squire > 0)
		{
			if(guards_mark3 < 1 && coin5 < 1)
				htmltext = "30039-01.htm";
			else if(guards_mark3 > 0)
			{
				if(st.getQuestItemsCount(GIANT_SPIDER_HUSK) < 20)
					htmltext = "30039-03.htm";
				else
				{
					htmltext = "30039-04.htm";
					st.takeItems(GIANT_SPIDER_HUSK, -1);
					st.takeItems(GLUDIO_GUARDS_MARK3, 1);
					st.giveItems(COIN_OF_LORDS5, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin5 > 0)
				htmltext = "30039-05.htm";
		}
		else if(npcId == BIOTIN && cond == 1 && squire > 0)
		{
			if(church_mark3 < 1 && coin6 < 1)
				htmltext = "30031-01.htm";
			else if(church_mark3 > 0)
			{
				if(st.getQuestItemsCount(HORRIBLE_SKULL) < 10)
					htmltext = "30031-03.htm";
				else
				{
					htmltext = "30031-04.htm";
					st.takeItems(HORRIBLE_SKULL, -1);
					st.takeItems(EINHASAD_CHURCH_MARK3, 1);
					st.giveItems(COIN_OF_LORDS6, 1);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(coin6 > 0)
				htmltext = "30031-05.htm";
		}
		else if(npcId == SIR_COLLIN_WINDAWOOD && cond == 1 && squire > 0)
			htmltext = "30311-01.htm";
		else if(npcId == SIR_ARON_TANFORD && cond == 1 && squire > 0)
			htmltext = "30653-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		for(int[] element : DROPLIST)
			if(st.getInt("cond") > 0 && npcId == element[0] && st.rollAndGiveLimited(element[2], 1, element[4], element[3]) && st.getQuestItemsCount(element[1]) > 0)
			{
				if(st.getQuestItemsCount(element[2]) == element[3])
					st.playSound(SOUND_MIDDLE);
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}