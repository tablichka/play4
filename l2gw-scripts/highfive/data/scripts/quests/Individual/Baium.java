package quests.Individual;

import ru.l2gw.gameserver.instancemanager.boss.BaiumManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.BossState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 10.02.2009
 * Time: 10:35:23
 */
public class Baium extends Quest
{
	public static int STONE_BAIUM = 29025;
	public static int LIVE_BAIUM = 29020;
	public static int ANGELIC_VORTEX = 31862;
	private static final int BloodedFabric = 4295;
	private static final Location TELEPORT_POSITION = new Location(114000, 16000, 10080);
	private static String prefix = "Baium-";


	public Baium()
	{
		super(21002, "Baium", "Baium Individual", true);

		addStartNpc(STONE_BAIUM);
		addStartNpc(ANGELIC_VORTEX);
		addTalkId(STONE_BAIUM);
		addTalkId(ANGELIC_VORTEX);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getPlayer().isAlikeDead())
			return null;
		L2Player player = st.getPlayer();
		BossState.State state = BaiumManager.getInstance().getState();
		String text = null;

		if(npcId == STONE_BAIUM)
		{
			if(st.getInt("ok") == 1 && state.equals(BossState.State.NOTSPAWN))
			{
				if(!npc.isBusy())
				{
					npc.setBusy(true);
					npc.setBusyMessage("Attending another player's request");
					BaiumManager.getInstance().wakeUp();
					if(Rnd.chance(50))
						st.getPlayer().doDie(npc);
					npc.deleteMe();
				}
				else
					text = prefix + "busy.htm";
			}
			else
			{
				st.exitCurrentQuest(true);
				text = prefix + "cond.htm";
			}
		}
		else if(npcId == ANGELIC_VORTEX)
		{
			if(player.isFlying())
				text = prefix + "wyvern.htm";
			else if(st.getQuestItemsCount(BloodedFabric) < 1)
			{
				text = prefix + "noquest.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				switch(state)
				{
					case NOTSPAWN:
						st.takeItems(BloodedFabric, 1);
						st.set("ok", "1");
						BaiumManager.getInstance().addPlayer(player);
						st.getPlayer().teleToLocation(TELEPORT_POSITION);
						text = null;
						break;
					case ALIVE:
						text = prefix + "started.htm";
						break;
					case DEAD:
						text = prefix + "notavailable.htm";
						break;
				}
			}
		}
		return text;
	}
}
