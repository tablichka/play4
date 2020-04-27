package quests._250_WatchWhatYouEat;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 04.02.11 18:08
 */
public class _250_WatchWhatYouEat extends Quest
{
	// NPCs
	private static final int cute_harry = 32743;

	// Items
	private static final int q_smash_fungus_spore = 15493;
	private static final int q_rug_fungus_spore = 15494;
	private static final int q_rosehip_fragrant_leaf = 15495;

	public _250_WatchWhatYouEat()
	{
		super(250, "_250_WatchWhatYouEat", "Watch What You Eat");

		addStartNpc(cute_harry);
		addTalkId(cute_harry);
		addKillId(18864, 18865, 18868);
		addQuestItem(q_smash_fungus_spore, q_rug_fungus_spore, q_rosehip_fragrant_leaf);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("cute_harry_q0250_12.htm", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == cute_harry)
		{
			if(st.isCreated() && player.getLevel() >= 82)
			{
				if(reply == 250)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("cute_harry_q0250_03.htm", player);
				}
				else if(reply == 1)
					showQuestPage("cute_harry_q0250_02.htm", player);
			}
			else if(reply == 2)
			{
				st.rollAndGive(57, 135661, 100);
				st.addExpAndSp(698334, 76369);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("cute_harry_q0250_10.htm", player);
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:cute_harry_q0250_12.htm";

		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:cute_harry_q0250_12.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == cute_harry)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "cute_harry_q0250_01.htm";

				st.exitCurrentQuest(true);
				return "npchtm:cute_harry_q0250_11.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(!st.haveQuestItems(q_smash_fungus_spore) || !st.haveQuestItems(q_rug_fungus_spore) || !st.haveQuestItems(q_rosehip_fragrant_leaf))
						return "npchtm:cute_harry_q0250_04.htm";

					st.takeItems(q_smash_fungus_spore, -1);
					st.takeItems(q_rug_fungus_spore, -1);
					st.takeItems(q_rosehip_fragrant_leaf, -1);
					st.setMemoState(2);
					return "npchtm:cute_harry_q0250_05.htm";
				}
				if(cond == 2)
					return "npchtm:cute_harry_q0250_06.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getMemoState() == 1)
			if(npc.getNpcId() == 18864)
			{
				if(st.getQuestItemsCount(15493) < 1)
				{
					st.giveItems(15493, 1);
					st.playSound(SOUND_ITEMGET);
					if(st.getQuestItemsCount(15494) >= 1 && st.getQuestItemsCount(15495) >= 1)
					{
						st.setCond(2);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
			else if(npc.getNpcId() == 18865)
			{
				if(st.getQuestItemsCount(15494) < 1)
				{
					st.giveItems(15494, 1);
					st.playSound(SOUND_ITEMGET);
					if(st.getQuestItemsCount(15493) >= 1 && st.getQuestItemsCount(15495) >= 1)
					{
						st.setCond(2);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
			else if(npc.getNpcId() == 18868)
			{
				if(st.getQuestItemsCount(15495) < 1)
				{
					st.giveItems(15495, 1);
					st.playSound(SOUND_ITEMGET);
					if(st.getQuestItemsCount(15493) >= 1 && st.getQuestItemsCount(15494) >= 1)
					{
						st.setCond(2);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}

	}
}
