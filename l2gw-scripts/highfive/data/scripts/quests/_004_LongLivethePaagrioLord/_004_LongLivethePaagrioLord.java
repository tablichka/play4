package quests._004_LongLivethePaagrioLord;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * One-time
 * Solo
 */
public class _004_LongLivethePaagrioLord extends Quest
{
	private final static int HONEY_KHANDAR = 1541;
	private final static int BEAR_FUR_CLOAK = 1542;
	private final static int BLOODY_AXE = 1543;
	private final static int ANCESTOR_SKULL = 1544;
	private final static int SPIDER_DUST = 1545;
	private final static int DEEP_SEA_ORB = 1546;
	private final static int ADENA_ID = 57;

	private final static int[][] NPC_GIFTS = {
			{30585, BEAR_FUR_CLOAK},
			{30566, HONEY_KHANDAR},
			{30562, BLOODY_AXE},
			{30560, ANCESTOR_SKULL},
			{30559, SPIDER_DUST},
			{30587, DEEP_SEA_ORB}};
	//NPCs
	private static final int NAKUSIN = 30578;

	public _004_LongLivethePaagrioLord()
	{
		super(4, "_004_LongLivethePaagrioLord", "Long Live the Paagrio Lord");
		addStartNpc(NAKUSIN);

		addTalkId(30559, 30560, 30562, 30566, 30578, 30585, 30587);

		addQuestItem(SPIDER_DUST, ANCESTOR_SKULL, BLOODY_AXE, HONEY_KHANDAR, BEAR_FUR_CLOAK, DEEP_SEA_ORB);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("30578-03.htm"))
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
		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated() && npcId == NAKUSIN)
		{
			if(st.getPlayer().getRace() != Race.orc)
			{
				htmltext = "30578-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 2)
			{
				htmltext = "30578-01.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30578-02.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == NAKUSIN)
			{
				if(cond == 1)
					htmltext = "30578-04.htm";
				else if(cond == 2)
				{
					htmltext = "30578-06.htm";
					st.giveItems(4, 1);
					st.getPlayer().addExpAndSp(4254, 335);
					st.rollAndGive(ADENA_ID, 1850, 100);
					for(int[] item : NPC_GIFTS)
						st.takeItems(item[1], -1);

					if(st.getPlayer().getVarInt("NR41") % 10 == 0)
					{
						st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
						st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4151", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
					}
					
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
				}
			}
			else if(cond == 1)
				for(int Id[] : NPC_GIFTS)
					if(Id[0] == npcId)
					{
						int item = Id[1];
						if(st.getQuestItemsCount(item) > 0)
							htmltext = npc + "-02.htm";
						else
						{
							st.giveItems(item, 1);
							htmltext = npc + "-01.htm";
							int count = 0;
							for(int[] item1 : NPC_GIFTS)
								count += st.getQuestItemsCount(item1[1]);
							if(count == 6)
							{
								st.set("cond", "2");
								st.playSound(SOUND_MIDDLE);
								st.setState(STARTED);
							}
							else
								st.playSound(SOUND_ITEMGET);
						}
						return htmltext;
					}
		}
		return htmltext;
	}
}