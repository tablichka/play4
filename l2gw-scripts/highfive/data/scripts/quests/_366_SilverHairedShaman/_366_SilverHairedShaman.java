package quests._366_SilverHairedShaman;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _366_SilverHairedShaman extends Quest
{
	//NPC
	private static final int DIETER = 30111;

	//MOBS
	private static final int SAIRON = 20986;
	private static final int SAIRONS_DOLL = 20987;
	private static final int SAIRONS_PUPPET = 20988;
	//VARIABLES
	private static final int ADENA_PER_ONE = 500;
	private static final int ADENA = 57;
	private static final int START_ADENA = 12070;

	//QUEST ITEMS
	private static final int SAIRONS_SILVER_HAIR = 5874;

	public _366_SilverHairedShaman()
	{
		super(366, "_366_SilverHairedShaman", "Silver Haired Shaman");
		addStartNpc(DIETER);

		addKillId(SAIRON);
		addKillId(SAIRONS_DOLL);
		addKillId(SAIRONS_PUPPET);

		addQuestItem(SAIRONS_SILVER_HAIR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30111-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30111-quit.htm"))
		{
			st.takeItems(SAIRONS_SILVER_HAIR, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated())
			st.set("cond", "0");
		else
			cond = st.getInt("cond");
		if(npcId == 30111)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 48)
					htmltext = "30111-01.htm";
				else
				{
					htmltext = "30111-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(SAIRONS_SILVER_HAIR) == 0)
				htmltext = "30111-03.htm";
			else if(cond == 1 && st.getQuestItemsCount(SAIRONS_SILVER_HAIR) >= 1)
			{
				st.rollAndGive(ADENA, (st.getQuestItemsCount(SAIRONS_SILVER_HAIR) * ADENA_PER_ONE + START_ADENA), 100);
				st.takeItems(SAIRONS_SILVER_HAIR, -1);
				htmltext = "30111-have.htm";
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		if(cond == 1 && st.rollAndGive(SAIRONS_SILVER_HAIR, 1, 66))
			st.playSound(SOUND_MIDDLE);
	}
}