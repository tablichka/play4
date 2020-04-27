package quests._113_StatusOfTheBeaconTower;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Last editor - LEXX
 */
public class _113_StatusOfTheBeaconTower extends Quest
{
	// NPC
	private static final int MOIRA = 31979;
	private static final int TORRANT = 32016;

	// QUEST ITEM
	private static final int BOX = 8086;

	public _113_StatusOfTheBeaconTower()
	{
		super(113, "_113_StatusOfTheBeaconTower", "Status Of The Beacon Tower");
		addStartNpc(MOIRA);
		addTalkId(TORRANT);

		addQuestItem(BOX);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("seer_moirase_q0113_0104.htm"))
		{
			st.set("cond", "1");
			st.giveItems(BOX, 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("torant_q0113_0201.htm"))
		{
			st.rollAndGive(57, 154800, 100);
			st.addExpAndSp(619300, 44200);
			st.takeItems(BOX, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCompleted())
			htmltext = "completed";
		else if(npcId == MOIRA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 40)
					htmltext = "seer_moirase_q0113_0101.htm";
				else
				{
					htmltext = "seer_moirase_q0113_0103.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "seer_moirase_q0113_0105.htm";
		}
		else if(npcId == TORRANT && st.getQuestItemsCount(BOX) == 1)
			htmltext = "torant_q0113_0101.htm";
		return htmltext;
	}
}