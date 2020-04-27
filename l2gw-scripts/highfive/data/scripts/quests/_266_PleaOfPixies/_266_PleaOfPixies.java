package quests._266_PleaOfPixies;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _266_PleaOfPixies extends Quest
{
	private static final int PREDATORS_FANG = 1334;
	private static final int EMERALD = 1337;
	private static final int BLUE_ONYX = 1338;
	private static final int ONYX = 1339;
	private static final int GLASS_SHARD = 1336;
	private static final int REC_LEATHER_BOOT = 2176;
	private static final int REC_SPIRITSHOT = 3032;

	public _266_PleaOfPixies()
	{
		super(266, "_266_PleaOfPixies", "Plea Of Pixies");
		addStartNpc(31852);
		addKillId(20525, 20530, 20534, 20537);
		addQuestItem(PREDATORS_FANG);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31852-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getInt("cond") == 0)
		{
			if(st.getPlayer().getRace() != Race.elf)
			{
				htmltext = "31852-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 3)
			{
				htmltext = "31852-01.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31852-02.htm";
		}
		else if(st.getQuestItemsCount(PREDATORS_FANG) < 100)
			htmltext = "31852-04.htm";
		else
		{
			st.takeItems(PREDATORS_FANG, -1);
			int n = Rnd.get(100);
			if(n < 2)
			{
				st.giveItems(EMERALD, 1);
				st.giveItems(REC_SPIRITSHOT, 1);
				st.playSound(SOUND_JACKPOT);
			}
			else if(n < 20)
			{
				st.giveItems(BLUE_ONYX, 1);
				st.giveItems(REC_LEATHER_BOOT, 1);
			}
			else if(n < 45)
				st.giveItems(ONYX, 1);
			else
				st.giveItems(GLASS_SHARD, 1);
			htmltext = "31852-05.htm";
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(PREDATORS_FANG, 1, 60 + npc.getLevel() * 5, 100))
			st.playSound(st.getQuestItemsCount(PREDATORS_FANG) == 100 ? SOUND_MIDDLE : SOUND_ITEMGET);
	}
}