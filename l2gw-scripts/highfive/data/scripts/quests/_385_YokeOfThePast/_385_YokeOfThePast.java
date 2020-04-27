package quests._385_YokeOfThePast;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.Experience;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _385_YokeOfThePast extends Quest
{
	final short ANCIENT_SCROLL = 5902;
	final short BLANK_SCROLL = 5965;

	public _385_YokeOfThePast()
	{
		super(385, "_385_YokeOfThePast", "Yoke of the Past"); // Party true

		for(int npcId = 31095; npcId <= 31126; npcId++)
			if(npcId != 31111 && npcId != 31112 && npcId != 31113)
				addStartNpc(npcId);

		for(int mobs = 21208; mobs <= 21256; mobs++)
			addKillId(mobs);

		addQuestItem(ANCIENT_SCROLL);
	}

	public boolean checkNPC(int npc)
	{
		if(npc >= 31095 && npc <= 31126)
			if(npc != 31100 && npc != 31111 && npc != 31112 && npc != 31113)
				return true;
		return false;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("14.htm"))
		{
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.set("cond", "1");
		}
		else if(event.equalsIgnoreCase("16.htm"))
		{
			htmltext = "quit.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			int diff = calculateLevelDiffForDrop(npc.getLevel(), st.getPlayer().getLevel());
			double rand = 60 * Experience.penaltyModifier(diff - 1, 9);

			st.rollAndGive(ANCIENT_SCROLL, 1, rand);
		}
	}

	private int calculateLevelDiffForDrop(int mobLevel, int player)
	{
		if(Config.DEEPBLUE_DROP_RULES && player - mobLevel >= 9)
			return player - mobLevel - 8;
		return 0;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(checkNPC(npcId) && st.getInt("cond") == 0)
			if(st.getPlayer().getLevel() < 20)
			{
				htmltext = "00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "10.htm";
		else if(st.getInt("cond") == 1 && st.getQuestItemsCount(ANCIENT_SCROLL) == 0)
			htmltext = "17.htm";
		else if(st.getInt("cond") == 1 && st.getQuestItemsCount(ANCIENT_SCROLL) > 0)
		{
			htmltext = "16.htm";
			st.giveItems(BLANK_SCROLL, st.getQuestItemsCount(ANCIENT_SCROLL));
			st.takeItems(ANCIENT_SCROLL, -1);
		}
		else
			st.exitCurrentQuest(true);
		return htmltext;
	}
}