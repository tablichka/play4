package quests._052_WilliesSpecialBait;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _052_WilliesSpecialBait extends Quest
{
	private final static int Willie = 31574;
	private final static int[] TarlkBasilisks = {20573, 20574};
	private final static int EyeOfTarlkBasilisk = 7623;
	private final static int EarthFishingLure = 7612;
	private final static Integer FishSkill = 1315;

	public _052_WilliesSpecialBait()
	{
		super(52, "_052_WilliesSpecialBait", "Willies Special Bait");

		addStartNpc(Willie);

		addKillId(TarlkBasilisks);

		addQuestItem(EyeOfTarlkBasilisk);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31574-04.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31574-07.htm"))
			if(st.getQuestItemsCount(EyeOfTarlkBasilisk) < 100)
				htmltext = "31574-08.htm";
			else
			{
				st.unset("cond");
				st.takeItems(EyeOfTarlkBasilisk, -1);
				st.giveItems(EarthFishingLure, 4);
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
		if(npcId == Willie)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 48)
				{
					htmltext = "31574-03.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getSkillLevel(FishSkill) >= 16)
					htmltext = "31574-01.htm";
				else
				{
					htmltext = "31574-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 || cond == 2)
				if(st.getQuestItemsCount(EyeOfTarlkBasilisk) < 100)
				{
					htmltext = "31574-06.htm";
					st.set("cond", "1");
				}
				else
					htmltext = "31574-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == TarlkBasilisks[0] || npcId == TarlkBasilisks[1] && st.getInt("cond") == 1 && st.rollAndGiveLimited(EyeOfTarlkBasilisk, 1, 30, 100))
		{
			if(st.getQuestItemsCount(EyeOfTarlkBasilisk) == 100)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}