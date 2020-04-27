package quests._050_LanoscosSpecialBait;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _050_LanoscosSpecialBait extends Quest
{
	// NPC
	int Lanosco = 31570;
	int SingingWind = 21026;
	// Items
	int EssenceofWind = 7621;
	int WindFishingLure = 7610;
	// Skill
	Integer FishSkill = 1315;

	public _050_LanoscosSpecialBait()
	{
		super(50, "_050_LanoscosSpecialBait", "Lanoscos Special Bait");

		addStartNpc(Lanosco);

		addTalkId(Lanosco);

		addKillId(SingingWind);

		addQuestItem(EssenceofWind);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31570-04.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31570-07.htm"))
			if(st.getQuestItemsCount(EssenceofWind) < 100)
				htmltext = "31570-08.htm";
			else
			{
				st.takeItems(EssenceofWind, -1);
				st.giveItems(WindFishingLure, 4);
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
		if(npcId == Lanosco)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 27)
				{
					htmltext = "31570-03.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getSkillLevel(FishSkill) >= 8)
					htmltext = "31570-01.htm";
				else
				{
					htmltext = "31570-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 || cond == 2)
				if(st.getQuestItemsCount(EssenceofWind) < 100)
				{
					htmltext = "31570-06.htm";
					st.set("cond", "1");
				}
				else
					htmltext = "31570-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == SingingWind && st.getInt("cond") == 1 && st.rollAndGiveLimited(EssenceofWind, 1, 30, 100))
		{
			if(st.getQuestItemsCount(EssenceofWind) == 100)
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