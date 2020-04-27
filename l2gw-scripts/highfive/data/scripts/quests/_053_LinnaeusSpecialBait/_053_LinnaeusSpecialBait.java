package quests._053_LinnaeusSpecialBait;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _053_LinnaeusSpecialBait extends Quest
{
	int Linnaeu = 31577;
	int CrimsonDrake = 20670;
	int HeartOfCrimsonDrake = 7624;
	int FlameFishingLure = 7613;
	Integer FishSkill = 1315;

	public _053_LinnaeusSpecialBait()
	{
		super(53, "_053_LinnaeusSpecialBait", "Linnaeus Special Bait");

		addStartNpc(Linnaeu);

		addTalkId(Linnaeu);

		addKillId(CrimsonDrake);

		addQuestItem(HeartOfCrimsonDrake);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31577-04.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31577-07.htm"))
			if(st.getQuestItemsCount(HeartOfCrimsonDrake) < 100)
				htmltext = "31577-08.htm";
			else
			{
				st.unset("cond");
				st.takeItems(HeartOfCrimsonDrake, -1);
				st.giveItems(FlameFishingLure, 4);
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
		if(npcId == Linnaeu)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 60)
				{
					htmltext = "31577-03.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getSkillLevel(FishSkill) >= 21)
					htmltext = "31577-01.htm";
				else
				{
					htmltext = "31577-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 || cond == 2)
				if(st.getQuestItemsCount(HeartOfCrimsonDrake) < 100)
				{
					htmltext = "31577-06.htm";
					st.set("cond", "1");
				}
				else
					htmltext = "31577-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == CrimsonDrake && st.getInt("cond") == 1 && st.rollAndGiveLimited(HeartOfCrimsonDrake, 1, 30, 100))
		{
			if(st.getQuestItemsCount(HeartOfCrimsonDrake) == 100)
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