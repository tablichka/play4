package quests._635_InTheDimensionalRift;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

public class _635_InTheDimensionalRift extends Quest
{
	private static final int DIMENSION_FRAGMENT = 7079;
	private static final int[] ANAKAZEL = {
			25333,
			25334,
			25335,
			25336,
			25337,
			25338
	};

	private long lastUpdate = 0;

	private static final Location RIFT_COORD = new Location(-114790, -180576, -6781);

	public _635_InTheDimensionalRift()
	{
		super(635, "_635_InTheDimensionalRift", "In The Dimensional Rift");

		for(int npcId = 31494; npcId < 31508; npcId++)
		{
			addTalkId(npcId);
			addStartNpc(npcId);
		}

		for(int npcId = 31127; npcId < 31132; npcId++)
		{
			addTalkId(npcId);
			addStartNpc(npcId);
		}

		for(int npcId = 31137; npcId < 31142; npcId++)
		{
			addTalkId(npcId);
			addStartNpc(npcId);
		}

		for(int npcId = 31488; npcId < 31494; npcId++)
			addTalkId(npcId);

		for(int i : ANAKAZEL)
		{
			addAttackId(i);
			addKillId(i);
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int count = st.getInt("count");
		if(event.equals("5.htm"))
		{
			if(count > 0)
				htmltext = "5a.htm";
			st.set("count", String.valueOf(count + 1));
			st.setState(STARTED);
			st.set("cond", "1");
			st.getPlayer().setVar("RiftBackCoords", st.getPlayer().getX() + "," + st.getPlayer().getY() + "," + st.getPlayer().getZ());
			st.getPlayer().teleToLocation(RIFT_COORD);
		}
		else if(event.equalsIgnoreCase("6.htm"))
			st.exitCurrentQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		String back = st.getPlayer().getVar("RiftBackCoords");
		int npcId = npc.getNpcId();
		if(npcId >= 31494 && npcId <= 31508 || npcId >= 31127 && npcId <= 31131 || npcId >= 31137 && npcId <= 31141)
		{
			if(st.getPlayer().getLevel() < 20)
			{
				st.exitCurrentQuest(true);
				htmltext = "1.htm";
			}
			else if(st.getQuestItemsCount(DIMENSION_FRAGMENT) == 0)
				htmltext = "3.htm";
			else
				htmltext = "4.htm";
		}
		else if(back != null)
		{
			String[] coords = back.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			int z = Integer.parseInt(coords[2]);
			st.getPlayer().teleToLocation(x, y, z);
			st.getPlayer().unsetVar("RiftBackCoords");
			st.unset("cond");
			htmltext = "7.htm";
			st.exitCurrentQuest(true);
		}
		else
		{
			htmltext = "Where?";
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		if(lastUpdate < System.currentTimeMillis())
		{
			lastUpdate = System.currentTimeMillis() + 5 * 60000;
			if(st.getPlayer().getParty().getDimensionalRift() != null)
				st.getPlayer().getParty().getDimensionalRift().rescheduleTeleportTask(600);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(killer.getParty().getDimensionalRift() != null)
		{
			killer.getParty().getDimensionalRift().setBossKilled(true);
			killer.getParty().getDimensionalRift().rescheduleTeleportTask(120);
		}

		super.onKill(npc, killer);
	}
}