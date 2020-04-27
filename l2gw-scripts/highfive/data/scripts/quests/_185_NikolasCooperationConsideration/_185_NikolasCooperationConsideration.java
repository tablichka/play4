package quests._185_NikolasCooperationConsideration;

import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 24.12.10 11:16
 */
public class _185_NikolasCooperationConsideration extends Quest
{
	private static final int Lorain = 30673;
	private static final int Nikola = 30621;
	private static final int Device = 32366;
	private static final int Alarm = 32367;

	private static final int Certificate = 10362;
	private static final int Metallograph = 10363;
	private static final int BrokenMetal = 10364;
	private static final int NicolasMap = 10365;

	public _185_NikolasCooperationConsideration()
	{
		super(185, "_185_NikolasCooperationConsideration", "Nikolas Cooperation Consideration");

		addTalkId(Lorain, Nikola, Device, Alarm);
		addQuestItem(NicolasMap, BrokenMetal, Metallograph);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();

		if(npcId == Nikola)
		{
			if(st.isCreated() && !player.isQuestStarted(184) && player.getLevel() >= 40 && player.isQuestComplete(183) && !player.isQuestComplete(184))
			{
				if(reply == 185)
				{
					st.giveItems(NicolasMap, 1);
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("maestro_nikola_q0185_06.htm", player);
					st.setCond(1);
					st.setState(STARTED);
				}
				else if(reply == 1)
				{
					if(player.getLevel() >= 40)
						showQuestPage("maestro_nikola_q0185_03.htm", player);
					else
						showQuestPage("maestro_nikola_q0185_03a.htm", player);
				}
				else if(reply == 2)
					showQuestPage("maestro_nikola_q0185_04.htm", player);
				else if(reply == 3)
					showQuestPage("maestro_nikola_q0185_05.htm", player);
			}
		}
		else if(npcId == Lorain)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(memoState == 1)
				{
					if(reply == 1)
						showPage("researcher_lorain_q0185_02.htm", player);
					else if(reply == 2)
					{
						st.takeItems(NicolasMap, -1);
						st.setMemoState(2);
						showPage("researcher_lorain_q0185_03.htm", player);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(memoState == 2 && reply == 3)
				{
					st.setMemoState(3);
					showPage("researcher_lorain_q0185_05.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(memoState == 6)
				{
					if(reply == 4)
						showPage("researcher_lorain_q0185_08.htm", player);
					else if(reply == 5)
					{
						if(st.haveQuestItems(Metallograph))
						{
							st.giveItems(Certificate, 1);
							st.takeItems(Metallograph, -1);
							st.playSound(SOUND_FINISH);
							showPage("researcher_lorain_q0185_09.htm", player);
							st.exitCurrentQuest(false);
						}
						else
						{
							st.takeItems(BrokenMetal, -1);
							st.playSound(SOUND_FINISH);
							showPage("researcher_lorain_q0185_10.htm", player);
							st.exitCurrentQuest(false);
						}
						st.rollAndGive(57, 72527, 100);
						if(player.getLevel() < 46)
							st.addExpAndSp(203717, 14032);
					}
				}
			}
		}
		else if(npcId == Device)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				L2NpcInstance npc = player.getLastNpc();
				if(reply == 1 && memoState == 3)
				{
					if(npc.i_quest0 == 0)
					{
						npc.i_quest0 = 1;
						npc.i_quest1 = player.getObjectId();
						showPage("broken_controller_q0185_03.htm", player);
						L2NpcInstance alarm = addSpawn(Alarm, new Location(npc.getX() + 80, npc.getY() + 65, npc.getZ(), 16384), false);
						alarm.i_quest0 = npc.getObjectId();
						alarm.i_quest1 = player.getObjectId();
					}
				}
				else if(reply == 2 && memoState == 4)
				{
					st.giveItems(Metallograph, 1);
					st.setMemoState(6);
					showPage("broken_controller_q0185_06.htm", player);
					st.setCond(4);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 3 && memoState == 5)
				{
					st.giveItems(BrokenMetal, 1);
					st.setMemoState(6);
					showPage("broken_controller_q0185_08.htm", player);
					st.setCond(5);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == Alarm)
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				if(reply == 2)
					showPage("alarm_of_giant_q0184_q0185_02.htm", player);
				else if(reply == 3)
				{
					st.set("ex", 1);
					showPage("alarm_of_giant_q0184_q0185_04.htm", player);
				}
				else if(reply == 4)
				{
					st.set("ex", st.getInt("ex") + 1);
					showPage("alarm_of_giant_q0184_q0185_06.htm", player);
				}
				else if(reply == 5)
				{
					st.set("ex", st.getInt("ex") + 1);
					showPage("alarm_of_giant_q0184_q0185_08.htm", player);
				}
				else if(reply == 6)
				{
					if(st.getInt("ex") >= 3)
					{
						L2Object npc = L2ObjectsStorage.findObject(player.getLastNpc().i_quest0);
						if(npc instanceof L2NpcInstance && ((L2NpcInstance) npc).i_quest0 == 1)
							((L2NpcInstance) npc).i_quest0 = 0;

						showPage("alarm_of_giant_q0184_q0185_09.htm", player);
						st.setMemoState(4);
						player.getLastNpc().deleteMe();
					}
					else
					{
						showPage("alarm_of_giant_q0184_q0185_10.htm", player);
						st.set("ex", 0);
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(st.isStarted())
			if(npcId == Nikola)
			{
				if(cond == 1)
					return "npchtm:maestro_nikola_q0185_07.htm";
			}
			else if(npcId == Lorain)
			{
				if(cond == 1)
					return "npchtm:researcher_lorain_q0185_01.htm";
				if(cond == 2)
					return "npchtm:researcher_lorain_q0185_04.htm";
				if(cond >= 3 && cond <= 5)
					return "npchtm:researcher_lorain_q0185_06.htm";
				if(cond == 6)
					return "npchtm:researcher_lorain_q0185_07.htm";
			}
			else if(npcId == Device)
			{
				if(cond == 3)
				{
					if(npc.i_quest0 == 0)
						return "npchtm:broken_controller_q0185_01.htm";
					else if(npc.i_quest1 == st.getPlayer().getObjectId())
						return "npchtm:broken_controller_q0185_03.htm";
					else
						return "npchtm:broken_controller_q0185_04.htm";
				}
				if(cond == 4)
					return "npchtm:broken_controller_q0185_05.htm";
				if(cond == 5)
					return "npchtm:broken_controller_q0185_07.htm";
			}

		return htmltext;
	}
}