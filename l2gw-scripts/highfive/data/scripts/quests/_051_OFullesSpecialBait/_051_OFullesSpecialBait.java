package quests._051_OFullesSpecialBait;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _051_OFullesSpecialBait extends Quest
{
	int OFulle = 31572;
	int FetteredSoul = 20552;

	int LostBaitIngredient = 7622;
	int IcyAirFishingLure = 7611;

	Integer FishSkill = 1315;

	public _051_OFullesSpecialBait()
	{
		super(51, "_051_OFullesSpecialBait", "O Fulles Special Bait");

		addStartNpc(OFulle);

		addTalkId(OFulle);

		addKillId(FetteredSoul);

		addQuestItem(LostBaitIngredient);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31572-04.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31572-07.htm"))
			if(st.getQuestItemsCount(LostBaitIngredient) < 100)
				htmltext = "31572-08.htm";
			else
			{
				st.unset("cond");
				st.takeItems(LostBaitIngredient, -1);
				st.giveItems(IcyAirFishingLure, 4);
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
		if(npcId == OFulle)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 36)
				{
					htmltext = "31572-03.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getSkillLevel(FishSkill) >= 11)
					htmltext = "31572-01.htm";
				else
				{
					htmltext = "31572-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 || cond == 2)
				if(st.getQuestItemsCount(LostBaitIngredient) < 100)
				{
					htmltext = "31572-06.htm";
					st.set("cond", "1");
				}
				else
					htmltext = "31572-05.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == FetteredSoul && st.getInt("cond") == 1 && st.rollAndGiveLimited(LostBaitIngredient, 1, 30, 100))
		{
			if(st.getQuestItemsCount(LostBaitIngredient) == 100)
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
