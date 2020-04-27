package quests._650_ABrokenDream;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _650_ABrokenDream extends Quest
{
	// NPC
	private static final int RailroadEngineer = 32054;
	// mobs
	private static final int ForgottenCrewman = 22027;
	private static final int VagabondOfTheRuins = 22028;
	// QuestItem
	private static final int RemnantsOfOldDwarvesDreams = 8514;

	public _650_ABrokenDream()
	{
		super(650, "_650_ABrokenDream", "A Broken Dream");
		addStartNpc(RailroadEngineer);
		addKillId(ForgottenCrewman);
		addKillId(VagabondOfTheRuins);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("2a.htm"))
		{
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.set("cond", "1");
		}
		else if(event.equalsIgnoreCase("500.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			st.unset("cond");
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		String htmltext = "noquest";
		if(st.isCreated())
		{
			QuestState OceanOfDistantStar = st.getPlayer().getQuestState("_117_OceanOfDistantStar");
			if(OceanOfDistantStar != null)
			{
				if(OceanOfDistantStar.isCompleted())
				{
					if(st.getPlayer().getLevel() < 39)
					{
						st.exitCurrentQuest(true);
						htmltext = "100.htm";
					}
					else
						htmltext = "200.htm";
				}
				else
				{
					htmltext = "600.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "600.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
			htmltext = "400.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGive(RemnantsOfOldDwarvesDreams, 1, 68))
			st.playSound(SOUND_ITEMGET);
	}
}