package quests._450_GraveRobberMemberRescue;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.taskmanager.DecayTaskManager;
import ru.l2gw.util.Util;

public class _450_GraveRobberMemberRescue extends Quest
{
	private static int KANEMIKA = 32650;
	private static int WARRIOR_NPC = 32651;

	private static int WARRIOR_MON = 22741;

	private static int EVIDENCE_OF_MIGRATION = 14876;

	public _450_GraveRobberMemberRescue()
	{
		super(450, "_450_GraveRobberMemberRescue", "Grave Robber Member Rescue");

		addStartNpc(KANEMIKA);
		addTalkId(WARRIOR_NPC);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32650-05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		L2Player player = st.getPlayer();

		if(npcId == KANEMIKA)
		{
			if(st.isCreated())
			{
				if(player.getLevel() < 80)
				{
					htmltext = "32650-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(!canEnter(player))
				{
					htmltext = "32650-09.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "32650-01.htm";
			}
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) >= 1)
					htmltext = "32650-07.htm";
				else
					htmltext = "32650-06.htm";
			}
			else if(cond == 2 && st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) == 10)
			{
				htmltext = "32650-08.htm";
				st.takeItems(EVIDENCE_OF_MIGRATION, -1);
				st.rollAndGive(57, 65000, 100);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				st.getPlayer().setVar(getName(), String.valueOf(System.currentTimeMillis()));
			}
		}
		else if(cond == 1 && npcId == WARRIOR_NPC)
		{
			if(Rnd.chance(50))
			{
				htmltext = "32651-01.htm";
				st.giveItems(EVIDENCE_OF_MIGRATION, 1);
				st.playSound(SOUND_ITEMGET);
				npc.moveToLocation(Util.getPointInRadius(npc.getLoc(), 200, (int) (Util.calculateAngleFrom(npc, player))), 0, false);
				DecayTaskManager.getInstance().addDecayTask(npc, 2500);
				if(st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) == 10)
				{
					st.set("cond", "2");
					st.playSound(SOUND_MIDDLE);
				}
				String fullpath = "data/scripts/quests/" + getName() + "/";
				NpcHtmlMessage htm = new NpcHtmlMessage(st.getPlayer(), st.getPlayer().getLastNpc(), fullpath + htmltext, 0);
				st.getPlayer().sendPacket(htm);
				return null;
			}
			else
			{
				player.sendPacket(new ExShowScreenMessage("The grave robber warrior has been filled with dark energy and is attacking you!", 4000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false));
				L2NpcInstance warrior = addSpawn(WARRIOR_MON, npc.getLoc(), false, 120000);
				warrior.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, player, Rnd.get(1, 100));
				Functions.npcSay(warrior, Say2C.ALL, Rnd.chance(50) ? "...Grunt... oh..." : "Grunt... What's... wrong with me...");

				npc.onDecay();
				return null;
			}
		}

		return htmltext;
	}

	private boolean canEnter(L2Player player)
	{
		if(player.isGM())
			return true;
		String var = player.getVar(getName());
		if(var == null)
			return true;
		return Long.parseLong(var) - System.currentTimeMillis() > 24 * 60 * 60 * 1000;
	}
}