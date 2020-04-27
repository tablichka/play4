package quests._553_OlympiadUndefeated;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.OlympiadGame;
import ru.l2gw.gameserver.model.entity.olympiad.OlympiadTeam;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 13.05.11 15:30
 */
public class _553_OlympiadUndefeated extends Quest
{
	// NPCs
	private static final int OLYMPIAD_MANAGER = 31688;

	// Items
	private static final int MEDAL_OF_GLORY = 21874;
	private static final int OLYMPIAD_CHEST = 17169;
	private static final int WINS_CONFIRMATION1 = 17244;
	private static final int WINS_CONFIRMATION2 = 17245;
	private static final int WINS_CONFIRMATION3 = 17246;

	public _553_OlympiadUndefeated()
	{
		super(553, "_553_OlympiadUndefeated", "Olympiad Undefeated");

		addStartNpc(OLYMPIAD_MANAGER);
		addTalkId(OLYMPIAD_MANAGER);
		addQuestItem(WINS_CONFIRMATION1, WINS_CONFIRMATION2, WINS_CONFIRMATION3);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:olympiad_operator_q0553_06.htm";

		L2Player player = st.getPlayer();
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
			return "npchtm:olympiad_operator_q0553_08.htm";

		if(st.isCreated())
			return "npchtm:olympiad_operator_q0553_01.htm";
		else if(st.isStarted())
		{
			if(!st.haveQuestItems(WINS_CONFIRMATION1) && !st.haveQuestItems(WINS_CONFIRMATION2) && !st.haveQuestItems(WINS_CONFIRMATION3))
				return "npchtm:olympiad_operator_q0553_04.htm";

			if(!st.haveQuestItems(WINS_CONFIRMATION3))
				return "npchtm:olympiad_operator_q0553_05.htm";
			else
			{
				st.giveItems(OLYMPIAD_CHEST, 6);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.takeItems(WINS_CONFIRMATION1, -1);
				st.takeItems(WINS_CONFIRMATION2, -1);
				st.takeItems(WINS_CONFIRMATION3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				return "npchtm:olympiad_operator_q0553_07.htm";
			}
		}

		return null;
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("olympiad_operator_q0553_06.htm", player);
			return;
		}
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
		{
			showPage("olympiad_operator_q0553_08.htm", player);
			return;
		}

		if(reply == 1)
			showPage("olympiad_operator_q0553_02.htm", player);
		else if(reply == 553)
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			showPage("olympiad_operator_q0553_03.htm", player);
		}
		else if(reply == 2)
		{
			if(st.haveQuestItems(WINS_CONFIRMATION3))
			{
				st.giveItems(OLYMPIAD_CHEST, 6);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.takeItems(WINS_CONFIRMATION1, -1);
				st.takeItems(WINS_CONFIRMATION2, -1);
				st.takeItems(WINS_CONFIRMATION3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0553_07.htm", player);
			}
			else if(st.haveQuestItems(WINS_CONFIRMATION2))
			{
				st.giveItems(OLYMPIAD_CHEST, 3);
				st.giveItems(MEDAL_OF_GLORY, 3); // от балды
				st.takeItems(WINS_CONFIRMATION1, -1);
				st.takeItems(WINS_CONFIRMATION2, -1);
				st.takeItems(WINS_CONFIRMATION3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0553_07.htm", player);
			}
			else if(st.haveQuestItems(WINS_CONFIRMATION1))
			{
				st.giveItems(OLYMPIAD_CHEST, 1);
				st.takeItems(WINS_CONFIRMATION1, -1);
				st.takeItems(WINS_CONFIRMATION2, -1);
				st.takeItems(WINS_CONFIRMATION3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0553_07.htm", player);
			}
		}
	}

	@Override
	public void onOlympiadEnd(OlympiadGame og, QuestState qs)
	{
		if(qs.getCond() == 1)
		{
			int count = qs.getInt("count");
			OlympiadTeam winner = og.getWinnerTeam();
			if(winner != null && winner.contains(qs.getPlayer().getObjectId()))
				count++;
			else
				count = 0;

			qs.set("count", count);
			if(count == 2 && !qs.haveQuestItems(WINS_CONFIRMATION1))
			{
				qs.giveItems(WINS_CONFIRMATION1, 1);
				qs.playSound(SOUND_ITEMGET);
			}
			else if(count == 5 && !qs.haveQuestItems(WINS_CONFIRMATION2))
			{
				qs.giveItems(WINS_CONFIRMATION2, 1);
				qs.playSound(SOUND_ITEMGET);
			}
			else if(count == 10 && !qs.haveQuestItems(WINS_CONFIRMATION3))
			{
				qs.giveItems(WINS_CONFIRMATION3, 2);
				qs.setCond(2);
				showQuestMark(qs.getPlayer());
				qs.playSound(SOUND_MIDDLE);
			}
			if(count < 10 && qs.haveQuestItems(WINS_CONFIRMATION3))
				qs.takeItems(WINS_CONFIRMATION3, -1);
			if(count < 5 && qs.haveQuestItems(WINS_CONFIRMATION2))
				qs.takeItems(WINS_CONFIRMATION2, -1);
			if(count < 2 && qs.haveQuestItems(WINS_CONFIRMATION1))
				qs.takeItems(WINS_CONFIRMATION1, -1);
		}
	}
}
