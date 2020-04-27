package quests._552_OlympiadVeteran;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.OlympiadGame;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 13.05.11 15:09
 */
public class _552_OlympiadVeteran extends Quest
{
	// NPCs
	private static final int OLYMPIAD_MANAGER = 31688;

	// Items
	private static final int MEDAL_OF_GLORY = 21874;
	private static final int OLYMPIAD_CHEST = 17169;
	private static final int TEAM_CERTIFICATE = 17241;
	private static final int CLASS_FREE_CERTIFICATE = 17242;
	private static final int CLASS_CERTIFICATE = 17243;

	public _552_OlympiadVeteran()
	{
		super(552, "_552_OlympiadVeteran", "Olympiad Veteran");

		addStartNpc(OLYMPIAD_MANAGER);
		addTalkId(OLYMPIAD_MANAGER);
		addQuestItem(TEAM_CERTIFICATE, CLASS_FREE_CERTIFICATE, CLASS_CERTIFICATE);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:olympiad_operator_q0552_06.htm";

		L2Player player = st.getPlayer();
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
			return "npchtm:olympiad_operator_q0552_08.htm";

		if(st.isCreated())
			return "npchtm:olympiad_operator_q0552_01.htm";
		else if(st.isStarted())
		{
			if(!st.haveQuestItems(TEAM_CERTIFICATE) && !st.haveQuestItems(CLASS_FREE_CERTIFICATE) && !st.haveQuestItems(CLASS_CERTIFICATE))
				return "npchtm:olympiad_operator_q0552_04.htm";
			else if(st.haveQuestItems(TEAM_CERTIFICATE) && st.haveQuestItems(CLASS_FREE_CERTIFICATE) && st.haveQuestItems(CLASS_CERTIFICATE))
			{
				st.giveItems(OLYMPIAD_CHEST, 3);
				st.takeItems(TEAM_CERTIFICATE, -1);
				st.takeItems(CLASS_FREE_CERTIFICATE, -1);
				st.takeItems(CLASS_CERTIFICATE, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				return "npchtm:olympiad_operator_q0552_07.htm";
			}
			return "npchtm:olympiad_operator_q0552_05.htm";
		}

		return null;
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("olympiad_operator_q0552_06.htm", player);
			return;
		}
		if(!player.isNoble() || player.getLevel() < 75 || player.getClassId().getLevel() < 4)
		{
			showPage("olympiad_operator_q0552_08.htm", player);
			return;
		}

		if(reply == 1)
			showPage("olympiad_operator_q0552_02.htm", player);
		else if(reply == 552)
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			showPage("olympiad_operator_q0552_03.htm", player);
		}
		else if(reply == 2)
		{
			int count = 0;

			if(st.haveQuestItems(TEAM_CERTIFICATE))
				count++;
			if(st.haveQuestItems(CLASS_FREE_CERTIFICATE))
				count++;
			if(st.haveQuestItems(CLASS_CERTIFICATE))
				count++;
			if(count > 0)
			{
				st.giveItems(OLYMPIAD_CHEST, count);
				st.takeItems(TEAM_CERTIFICATE, -1);
				st.takeItems(CLASS_FREE_CERTIFICATE, -1);
				st.takeItems(CLASS_CERTIFICATE, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				showPage("olympiad_operator_q0552_07.htm", player);
			}
		}
	}

	@Override
	public void onOlympiadEnd(OlympiadGame og, QuestState qs)
	{
		if(qs.getCond() == 1)
		{
			if(og.getGameType() == 0)
			{
				int count = qs.getInt("count1");
				count++;
				qs.set("count1", count);
				if(count == 5)
				{
					qs.giveItems(TEAM_CERTIFICATE, 1);
					if(qs.getInt("count2") >= 5 && qs.getInt("count3") >= 5)
					{
						qs.setCond(2);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
			else if(og.getGameType() == 1)
			{
				int count = qs.getInt("count2");
				count++;
				qs.set("count2", count);
				if(count == 5)
				{
					qs.giveItems(CLASS_FREE_CERTIFICATE, 1);
					if(qs.getInt("count1") >= 5 && qs.getInt("count3") >= 5)
					{
						qs.setCond(2);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
			else
			{
				int count = qs.getInt("count3");
				count++;
				qs.set("count3", count);
				if(count == 5)
				{
					qs.giveItems(CLASS_CERTIFICATE, 1);
					if(qs.getInt("count1") >= 5 && qs.getInt("count2") >= 5)
					{
						qs.setCond(2);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}
