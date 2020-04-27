package quests._193_SevenSignDyingMessage;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 13.08.2010 20:46:47
 */
public class _193_SevenSignDyingMessage extends Quest
{
	// NPCs
	private static final int HOLLINT = 30191;
	private static final int CAIN = 32569;
	private static final int ERIC = 32570;
	private static final int ATHEBALDT = 30760;
	private static final int SHILENSEVIL = 27343;

	// ITEMS
	private static final int JACOB_NECK = 13814;
	private static final int DEADMANS_HERB = 13816;
	private static final int SCULPTURE = 14353;

	public _193_SevenSignDyingMessage()
	{
		super(193, "_193_SevenSignDyingMessage", "Seven Sign Dying Message");

		addStartNpc(HOLLINT);
		addTalkId(HOLLINT);
		addTalkId(CAIN);
		addTalkId(ERIC);
		addTalkId(ATHEBALDT);
		addKillId(SHILENSEVIL);
		addQuestItem(JACOB_NECK, DEADMANS_HERB, SCULPTURE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("30191-02.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.giveItems(JACOB_NECK, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32569-05.htm"))
		{
			st.set("cond", 2);
			st.takeItems(JACOB_NECK, 1);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("32570-02.htm"))
		{
			st.set("cond", "3");
			st.giveItems(DEADMANS_HERB, 1);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("9"))
		{
			st.takeItems(DEADMANS_HERB, 1);
			st.set("cond", 4);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
			st.getPlayer().showQuestMovie(ExStartScenePlayer.SCENE_SSQ_DYING_MASSAGE);
			return null;
		}
		else if(event.equals("32569-09.htm"))
		{
			Functions.npcSay(st.getPlayer().getLastNpc(), Say2C.ALL, st.getPlayer().getName() + "! That stranger must be defeated!");
			L2NpcInstance monster = addSpawn(SHILENSEVIL, new Location(82624, 47422, -3220), false, 60000);
			Functions.npcSay(monster, Say2C.ALL, "You are not the owner of that item!");
			monster.setRunning();
			monster.addDamageHate(st.getPlayer(), 0, 999);
			monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
		}
		else if(event.equals("32569-13.htm"))
		{
			st.set("cond", 6);
			st.takeItems(SCULPTURE, 1);
			st.playSound(SOUND_MIDDLE);
			showQuestMark(st.getPlayer());
		}
		else if(event.equals("30760-02.htm"))
		{
			if(st.getPlayer().getLevel() < 79)
				return "<html><body>Only characters who are <font color=\"LEVEL\">level 79</font> or higher may complete this quest.</body></html>";
			st.addExpAndSp(52518015, 0);
			st.addExpAndSp(0, 5817677);
			st.unset("cond");
			st.setState(COMPLETED);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == HOLLINT)
		{
			QuestState first = st.getPlayer().getQuestState("_192_SevenSignSeriesOfDoubt");
			if(first != null && first.isCompleted() && st.isCreated() && st.getPlayer().getLevel() >= 79)
				htmltext = "30191-01.htm";
			else if(cond == 1)
				htmltext = "30191-03.htm";
			else
			{
				htmltext = "30191-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == CAIN)
		{
			if(cond == 1)
				htmltext = "32569-01.htm";
			else if(cond == 2)
				htmltext = "32569-06.htm";
			else if(cond == 3)
				htmltext = "32569-07.htm";
			else if(cond == 4)
				htmltext = "32569-08.htm";
			else if(cond == 5)
				htmltext = "32569-10.htm";
		}
		else if(npcId == ERIC)
		{
			if(cond == 2)
				htmltext = "32570-01.htm";
			else if(cond == 3)
				htmltext = "32570-03.htm";
		}
		else if(npcId == ATHEBALDT && cond == 6)
			htmltext = "30760-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == SHILENSEVIL && st.getInt("cond") == 4)
		{
			Functions.npcSay(npc, Say2C.ALL, st.getPlayer().getName() + "... You may have won this time... But next time, I will surely capture you!");
			st.giveItems(SCULPTURE, 1);
			st.set("cond", 5);
			st.setState(STARTED);
		}
	}
}
