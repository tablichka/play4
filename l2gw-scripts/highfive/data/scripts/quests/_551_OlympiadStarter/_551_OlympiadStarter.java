package quests._551_OlympiadStarter;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.OlympiadGame;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 13.05.11 14:30
 */
public class _551_OlympiadStarter extends Quest
{
	// NPCs
	private static final int OLYMPIAD_MANAGER = 31688;

	// Items
	private static final int MEDAL_OF_GLORY = 21874;
	private static final int OLYMPIAD_CHEST = 17169;
	private static final int OLYMPIAD_CERT1 = 17238;
	private static final int OLYMPIAD_CERT2 = 17239;
	private static final int OLYMPIAD_CERT3 = 17240;

	public _551_OlympiadStarter()
	{
		super(551, "_551_OlympiadStarter", "Olympiad Starter");

		addStartNpc(OLYMPIAD_MANAGER);
		addTalkId(OLYMPIAD_MANAGER);
		addQuestItem(OLYMPIAD_CERT1, OLYMPIAD_CERT2, OLYMPIAD_CERT3);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:olympiad_operator_q0551_06.htm";

		L2Player player = st.getPlayer();
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
			return "npchtm:olympiad_operator_q0551_08.htm";

		if(st.isCreated())
			return "npchtm:olympiad_operator_q0551_01.htm";
		else if(st.isStarted())
		{
			if(!st.haveQuestItems(OLYMPIAD_CERT1) && !st.haveQuestItems(OLYMPIAD_CERT2) && !st.haveQuestItems(OLYMPIAD_CERT3))
				return "npchtm:olympiad_operator_q0551_04.htm";

			if(!st.haveQuestItems(OLYMPIAD_CERT3))
				return "npchtm:olympiad_operator_q0551_05.htm";
			else
			{
				st.giveItems(OLYMPIAD_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.takeItems(OLYMPIAD_CERT1, -1);
				st.takeItems(OLYMPIAD_CERT2, -1);
				st.takeItems(OLYMPIAD_CERT3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				return "npchtm:olympiad_operator_q0551_07.htm";
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
			showPage("olympiad_operator_q0551_06.htm", player);
			return;
		}
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
		{
			showPage("olympiad_operator_q0551_08.htm", player);
			return;
		}

		if(reply == 1)
			showPage("olympiad_operator_q0551_02.htm", player);
		else if(reply == 551)
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			showPage("olympiad_operator_q0551_03.htm", player);
		}
		else if(reply == 2)
		{
			if(st.haveQuestItems(OLYMPIAD_CERT3))
			{
				st.giveItems(OLYMPIAD_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.takeItems(OLYMPIAD_CERT1, -1);
				st.takeItems(OLYMPIAD_CERT2, -1);
				st.takeItems(OLYMPIAD_CERT3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0551_07.htm", player);
			}
			else if(st.haveQuestItems(OLYMPIAD_CERT2))
			{
				st.giveItems(OLYMPIAD_CHEST, 2);
				st.giveItems(MEDAL_OF_GLORY, 3); // от балды
				st.takeItems(OLYMPIAD_CERT1, -1);
				st.takeItems(OLYMPIAD_CERT2, -1);
				st.takeItems(OLYMPIAD_CERT3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0551_07.htm", player);
			}
			else if(st.haveQuestItems(OLYMPIAD_CERT1))
			{
				st.giveItems(OLYMPIAD_CHEST, 1);
				//st.giveItems(MEDAL_OF_GLORY, 5); ??
				st.takeItems(OLYMPIAD_CERT1, -1);
				st.takeItems(OLYMPIAD_CERT2, -1);
				st.takeItems(OLYMPIAD_CERT3, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0551_07.htm", player);
			}
		}
	}

	@Override
	public void onOlympiadEnd(OlympiadGame og, QuestState qs)
	{
		if(qs.getCond() == 1)
		{
			int count = qs.getInt("count");
			count++;
			qs.set("count", count);
			if(count == 3)
			{
				qs.giveItems(OLYMPIAD_CERT1, 1);
				qs.playSound(SOUND_ITEMGET);
			}
			else if(count == 5)
			{
				qs.giveItems(OLYMPIAD_CERT2, 1);
				qs.playSound(SOUND_ITEMGET);
			}
			else if(count == 10)
			{
				qs.giveItems(OLYMPIAD_CERT2, 2);
				qs.setCond(2);
				showQuestMark(qs.getPlayer());
				qs.playSound(SOUND_MIDDLE);
			}
		}
	}
}
