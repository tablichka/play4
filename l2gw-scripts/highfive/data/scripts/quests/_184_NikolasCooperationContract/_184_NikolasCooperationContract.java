package quests._184_NikolasCooperationContract;

import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

public class _184_NikolasCooperationContract extends Quest
{
	// NPCs
	private static final int Nikola = 30621;
	private static final int Lorain = 30673;
	private static final int BrokenController = 32366;
	private static final int Alarm = 32367;

	// Items
	private static final int Certificate = 10362;
	private static final int Metal = 10359;
	private static final int BrokenMetal = 10360;
	private static final int NicolasMap = 10361;

	public _184_NikolasCooperationContract()
	{
		super(184, "_184_NikolasCooperationContract", "Nikolas Cooperation Contract");

		addStartNpc(Nikola);
		addTalkId(Lorain);
		addTalkId(BrokenController);
		addTalkId(Alarm);
		addQuestItem(NicolasMap, BrokenMetal, Metal);
	}


	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		if(npcId == Nikola)
		{
			QuestState qs185 = st.getPlayer().getQuestState(185);
			boolean qs185started = (qs185 != null && qs185.isStarted());

			if(reply == 184 && st.isCreated() && !qs185started && player.getLevel() >= 40 && !player.isQuestComplete(185))
			{
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				st.setState(STARTED);
				showQuestPage("maestro_nikola_q0184_06.htm", player);
				st.giveItems(NicolasMap, 1);
				st.setCond(1);
				showQuestMark(player);
				return;
			}
			else if(reply == 1 && !st.isStarted() && !qs185started && player.isQuestComplete(183) && player.getLevel() >= 40 && !player.isQuestComplete(185))
			{
				if(player.getLevel() >= 40)
					showQuestPage("maestro_nikola_q0184_03.htm", player);
				else
					showQuestPage("maestro_nikola_q0184_03a.htm", player);

				return;
			}
			else if(reply == 2 && !st.isStarted() && !qs185started && player.isQuestComplete(183) && player.getLevel() >= 40 && !player.isQuestComplete(185))
			{
				showQuestPage("maestro_nikola_q0184_04.htm", player);
				return;
			}
			else if(reply == 3 && !st.isStarted() && !qs185started && player.isQuestComplete(183) && player.getLevel() >= 40 && !player.isQuestComplete(185))
			{
				showQuestPage("maestro_nikola_q0184_05.htm", player);
				return;
			}

		}
		else if(npcId == Lorain)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 1)
			{
				showPage("researcher_lorain_q0184_02.htm", player);
				return;
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 1)
			{
				st.takeItems(NicolasMap, -1);
				st.setMemoState(2);
				showPage("researcher_lorain_q0184_03.htm", player);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 2)
			{
				st.setMemoState(3);
				showPage("researcher_lorain_q0184_05.htm", player);
				st.setCond(3);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
			else if(reply == 4 && st.isStarted() && st.getMemoState() == 6)
			{
				showPage("researcher_lorain_q0184_08.htm", player);
				return;
			}
			else if(reply == 5 && st.isStarted() && st.getMemoState() == 6)
			{
				if(st.getQuestItemsCount(Metal) >= 1)
				{
					st.giveItems(Certificate, 1);
					st.takeItems(Metal, -1);
					st.setState(COMPLETED);
					st.playSound(SOUND_FINISH);
					showPage("researcher_lorain_q0184_09.htm", player);
					st.exitCurrentQuest(false);
				}
				else
				{
					st.takeItems(BrokenMetal, -1);
					st.setState(COMPLETED);
					st.playSound(SOUND_FINISH);
					showPage("researcher_lorain_q0184_10.htm", player);
					st.exitCurrentQuest(false);
				}

				if(player.getLevel() < 46)
					st.addExpAndSp(203717, 14032);

				st.rollAndGive(57, 72527, 100);
				return;
			}
		}
		else if(npcId == BrokenController)
		{
			L2NpcInstance controller = player.getLastNpc();
			if(reply == 1 && st.isStarted() && st.getMemoState() == 3)
			{
				if(controller.i_quest0 == 0)
				{
					controller.i_quest0 = 1;
					controller.i_quest1 = player.getObjectId();
					showPage("broken_controller_q0184_03.htm", player);
					L2NpcInstance alarm = addSpawn(Alarm, new Location(controller.getX() + 80, controller.getY() + 65, controller.getZ(), 16384), false);
					alarm.i_quest0 = controller.getObjectId();
					alarm.i_quest1 = player.getObjectId();
					return;
				}
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 4)
			{
				st.giveItems(Metal, 1);
				st.setMemoState(6);
				showPage("broken_controller_q0184_06.htm", player);
				st.setCond(4);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 5)
			{
				st.giveItems(BrokenMetal, 1);
				st.setMemoState(6);
				showPage("broken_controller_q0184_08.htm", player);
				st.setCond(5);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
		}
		else if(npcId == Alarm)
		{
			L2NpcInstance alarm = player.getLastNpc();
			int c0 = alarm.i_quest1;
			if(player.getObjectId() == c0)
			{
				if(reply == 2 && st.isStarted() && st.getMemoState() == 3)
				{
					showPage("alarm_of_giant_q0184_q0184_02.htm", player);
					return;
				}
				else if(reply == 3 && st.isStarted() && st.getMemoState() == 3)
				{
					st.set("MemoStateEx1", 1);
					showPage("alarm_of_giant_q0184_q0184_04.htm", player);
					return;
				}
				else if(reply == 4 && st.isStarted() && st.getMemoState() == 3)
				{
					int i0 = st.getInt("MemoStateEx1");
					st.set("MemoStateEx1", i0 + 1);
					showPage("alarm_of_giant_q0184_q0184_06.htm", player);
					return;
				}
				else if(reply == 5 && st.isStarted() && st.getMemoState() == 3)
				{
					int i0 = st.getInt("MemoStateEx1");
					st.set("MemoStateEx1", i0 + 1);
					showPage("alarm_of_giant_q0184_q0184_08.htm", player);
					return;
				}
				else if(reply == 6 && st.isStarted() && st.getMemoState() == 3)
				{
					int i0 = st.getInt("MemoStateEx1");
					if(i0 >= 3)
					{
						L2Object npc0 = L2ObjectsStorage.findObject(alarm.i_quest0);
						if(npc0 instanceof L2NpcInstance)
						{
							if(((L2NpcInstance) npc0).i_quest0 == 1)
								((L2NpcInstance) npc0).i_quest0 = 0;
						}
						alarm.deleteMe();
						showPage("alarm_of_giant_q0184_q0184_09.htm", player);
						st.setMemoState(4);
					}
					else
					{
						showPage("alarm_of_giant_q0184_q0184_10.htm", player);
						st.set("MemoStateEx1", 0);
					}
					return;
				}
			}
		}
		showPage("noquest", player);
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

		if(npcId == Nikola)
		{
			QuestState qs185 = st.getPlayer().getQuestState(185);
			boolean qs185started = (qs185 != null && qs185.isStarted());
			if(st.isCreated() && !qs185started && st.getPlayer().isQuestComplete(183) && !st.getPlayer().isQuestComplete(185))
			{
				if(st.getPlayer().getLevel() >= 40)
					htmltext = "maestro_nikola_q0184_01.htm";
				else
					htmltext = "maestro_nikola_q0184_02.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				htmltext = "npchtm:maestro_nikola_q0184_07.htm";
			}
		}
		else if(npcId == Lorain)
		{
			if(st.isStarted() && st.getMemoState() == 1)
			{
				htmltext = "npchtm:researcher_lorain_q0184_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:researcher_lorain_q0184_04.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 3 && st.getMemoState() <= 5)
			{
				htmltext = "npchtm:researcher_lorain_q0184_06.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 6)
			{
				htmltext = "npchtm:researcher_lorain_q0184_07.htm";
			}
		}
		else if(npcId == BrokenController)
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				if(npc.i_quest0 == 0)
				{
					htmltext = "npchtm:broken_controller_q0184_01.htm";
				}
				else if(npc.i_quest1 == st.getPlayer().getObjectId())
				{
					htmltext = "npchtm:broken_controller_q0184_03.htm";
				}
				else
				{
					htmltext = "npchtm:broken_controller_q0184_04.htm";
				}
			}
			else if(st.isStarted() && st.getMemoState() == 4)
			{
				htmltext = "npchtm:broken_controller_q0184_05.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5)
			{
				htmltext = "npchtm:broken_controller_q0184_07.htm";
			}

		}
		else if(npcId == Alarm)
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				int c0 = npc.i_quest1;
				if(st.getPlayer().getObjectId() == c0)
				{
					htmltext = "npchtm:alarm_of_giant_q0184001.htm";
				}
				else
				{
					htmltext = "npchtm:alarm_of_giant_q0184002.htm";
				}
			}
		}

		return htmltext;
	}
}
