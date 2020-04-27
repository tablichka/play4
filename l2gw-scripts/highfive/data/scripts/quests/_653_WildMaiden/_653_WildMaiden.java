package quests._653_WildMaiden;

import javolution.util.FastList;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;

import java.util.List;

public class _653_WildMaiden extends Quest
{
	// Npc
	public final int SUKI = 32013;
	public final int GALIBREDO = 30181;

	// Items
	public final int SOE = 736;

	public _653_WildMaiden()
	{
		super(653, "_653_WildMaiden", "Wild Maiden");

		addStartNpc(SUKI);
		addTalkId(SUKI, GALIBREDO);
	}

	private L2NpcInstance findNpc(int npcId, L2Player player)
	{
		L2NpcInstance instance = null;
		List<L2NpcInstance> npclist = new FastList<L2NpcInstance>();
		npclist.addAll(L2ObjectsStorage.getAllByNpcId(npcId, false));

		for(L2NpcInstance npc : npclist)
			if(player.isInRange(npc, 1600))
				return npc;

		return instance;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		L2Player player = st.getPlayer();
		if(event.equalsIgnoreCase("32013-04.htm"))
		{
			if(st.getQuestItemsCount(SOE) > 0)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.takeItems(SOE, 1);
				htmltext = "32013-03.htm";
				L2NpcInstance npc = findNpc(SUKI, player);
				npc.broadcastPacket(new MagicSkillUse(npc, npc, 2013, 1, 20000, 0));
				st.startQuestTimer("suki_timer", 20000);
			}
		}
		else if(event.equalsIgnoreCase("32013-04a.htm"))
		{
			st.exitCurrentQuest(false);
			st.playSound(SOUND_GIVEUP);
		}
		else if(event.equalsIgnoreCase("suki_timer"))
		{
			L2NpcInstance npc = findNpc(SUKI, player);
			npc.deleteMe();
			htmltext = null;
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
		if(npcId == SUKI && st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 36)
				htmltext = "32013-02.htm";
			else
			{
				htmltext = "32013-01.htm";
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == GALIBREDO && st.getInt("cond") == 1)
		{
			htmltext = "30181-01.htm";
			st.rollAndGive(57, 2883, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}
}