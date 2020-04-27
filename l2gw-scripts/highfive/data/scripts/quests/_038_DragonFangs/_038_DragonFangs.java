package quests._038_DragonFangs;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _038_DragonFangs extends Quest
{
	//NPC
	public final int ROHMER = 30344;
	public final int LUIS = 30386;
	public final int IRIS = 30034;

	//QUEST ITEM
	public final int FEATHER_ORNAMENT = 7173;
	public final int TOOTH_OF_TOTEM = 7174;
	public final int LETTER_OF_IRIS = 7176;
	public final int LETTER_OF_ROHMER = 7177;
	public final int TOOTH_OF_DRAGON = 7175;

	//MOBS
	public final int LANGK_LIZARDMAN_LIEUTENANT = 20357;
	public final int LANGK_LIZARDMAN_SENTINEL = 21100;
	public final int LANGK_LIZARDMAN_LEADER = 20356;
	public final int LANGK_LIZARDMAN_SHAMAN = 21101;

	//CHANCE FOR DROP
	public final int CHANCE_FOR_QUEST_ITEMS = 100; // 100%???

	//REWARD
	public final int ADENA = 57;
	public final int BONE_HELMET = 45;
	public final int ASSAULT_BOOTS = 1125;
	public final int BLUE_BUCKSKIN_BOOTS = 1123;

	public _038_DragonFangs()
	{
		super(38, "_038_DragonFangs", "Dragon Fangs");

		addStartNpc(LUIS);

		addTalkId(IRIS);
		addTalkId(ROHMER);

		addKillId(LANGK_LIZARDMAN_LEADER);
		addKillId(LANGK_LIZARDMAN_SHAMAN);
		addKillId(LANGK_LIZARDMAN_SENTINEL);
		addKillId(LANGK_LIZARDMAN_LIEUTENANT);

		addQuestItem(TOOTH_OF_TOTEM, LETTER_OF_IRIS, LETTER_OF_ROHMER, TOOTH_OF_DRAGON, FEATHER_ORNAMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equals("30386-3.htm"))
			if(st.isCreated())
			{
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
		if(event.equals("30386-5.htm"))
			if(cond == 2)
			{
				st.set("cond", "3");
				st.takeItems(FEATHER_ORNAMENT, 100);
				st.giveItems(TOOTH_OF_TOTEM, 1);
				st.playSound(SOUND_MIDDLE);
			}
		if(event.equals("30034-2.htm"))
			if(cond == 3)
			{
				st.set("cond", "4");
				st.takeItems(TOOTH_OF_TOTEM, 1);
				st.giveItems(LETTER_OF_IRIS, 1);
				st.playSound(SOUND_MIDDLE);
			}
		if(event.equals("30344-2.htm"))
			if(cond == 4)
			{
				st.set("cond", "5");
				st.takeItems(LETTER_OF_IRIS, 1);
				st.giveItems(LETTER_OF_ROHMER, 1);
				st.playSound(SOUND_MIDDLE);
			}
		if(event.equals("30034-6.htm"))
			if(cond == 5)
			{
				st.set("cond", "6");
				st.takeItems(LETTER_OF_ROHMER, 1);
				st.playSound(SOUND_MIDDLE);
			}
		if(event.equals("30034-9.htm"))
			if(cond == 7)
			{
				st.takeItems(TOOTH_OF_DRAGON, 50);
				int luck = Rnd.get(3);
				if(luck == 0)
				{
					st.giveItems(BLUE_BUCKSKIN_BOOTS, 1);
					st.rollAndGive(ADENA, 1500, 100);
				}
				if(luck == 1)
				{
					st.giveItems(BONE_HELMET, 1);
					st.rollAndGive(ADENA, 5200, 100);
				}
				if(luck == 2)
				{
					st.giveItems(ASSAULT_BOOTS, 1);
					st.rollAndGive(ADENA, 1500, 100);
				}
				st.addExpAndSp(435117, 23977);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == LUIS && st.isCreated())
			if(st.getPlayer().getLevel() < 19)
			{
				htmltext = "30386-2.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() >= 19)
				htmltext = "30386-1.htm";
		if(npcId == LUIS && cond == 1)
			htmltext = "30386-6.htm";
		if(npcId == LUIS && cond == 2 && st.getQuestItemsCount(FEATHER_ORNAMENT) == 100)
			htmltext = "30386-4.htm";
		if(npcId == LUIS && cond == 3)
			htmltext = "30386-7.htm";
		if(npcId == IRIS && cond == 3 && st.getQuestItemsCount(TOOTH_OF_TOTEM) == 1)
			htmltext = "30034-1.htm";
		if(npcId == IRIS && cond == 4)
			htmltext = "30034-3.htm";
		if(npcId == IRIS && cond == 5 && st.getQuestItemsCount(LETTER_OF_ROHMER) == 1)
			htmltext = "30034-5.htm";
		if(npcId == IRIS && cond == 6)
			htmltext = "30034-7.htm";
		if(npcId == IRIS && cond == 7 && st.getQuestItemsCount(TOOTH_OF_DRAGON) == 50)
			htmltext = "30034-8.htm";
		if(npcId == ROHMER && cond == 4 && st.getQuestItemsCount(LETTER_OF_IRIS) == 1)
			htmltext = "30344-1.htm";
		if(npcId == ROHMER && cond == 5)
			htmltext = "30344-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		if(npcId == 20357 || npcId == 21100)
			if(cond == 1 && st.rollAndGiveLimited(FEATHER_ORNAMENT, 1, CHANCE_FOR_QUEST_ITEMS, 100))
			{
				if(st.getQuestItemsCount(FEATHER_ORNAMENT) == 100)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "2");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		if(npcId == 20356 || npcId == 21101)
			if(cond == 6 && st.rollAndGiveLimited(TOOTH_OF_DRAGON, 1, CHANCE_FOR_QUEST_ITEMS, 50))
			{
				if(st.getQuestItemsCount(TOOTH_OF_DRAGON) == 50)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "7");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}