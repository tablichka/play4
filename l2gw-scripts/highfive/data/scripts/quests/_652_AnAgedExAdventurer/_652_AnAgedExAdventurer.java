package quests._652_AnAgedExAdventurer;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _652_AnAgedExAdventurer extends Quest
{
	//NPC
	private static final int Tantan = 32012;
	private static final int Sara = 30180;
	//Item
	private static final int SoulshotCgrade = 1464;
	private static final int Adena = 57;
	private static final int ScrollEnchantArmorD = 956;

	public _652_AnAgedExAdventurer()
	{
		super(652, "_652_AnAgedExAdventurer", "An Aged Ex-Adventurer");

		addStartNpc(Tantan);
		addTalkId(Tantan, Sara);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32012-02.htm") && st.getQuestItemsCount(SoulshotCgrade) >= 100)
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.takeItems(SoulshotCgrade, 100);
			st.playSound(SOUND_ACCEPT);
		}
		else
		{
			htmltext = "32012-02a.htm";
			st.exitCurrentQuest(true);
			st.playSound(SOUND_GIVEUP);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Tantan)
		{
			if(st.isCreated())
				if(st.getPlayer().getLevel() < 46)
				{
					htmltext = "32012-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "32012-01.htm";
		}
		else if(npcId == Sara && cond == 1)
		{
			htmltext = "30180-01.htm";
			st.rollAndGive(Adena, 5026, 100);
			if(Rnd.chance(50))
				st.rollAndGive(ScrollEnchantArmorD, 1, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}
}