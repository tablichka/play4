package quests.Individual;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.boss.AntharasManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.BossState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * Created by IntelliJ IDEA.
 * User: rage
 * Date: 16.01.2009
 * Time: 22:34:49
 */
public class Antharas extends Quest
{
	private static int PORTAL_STONE_ID = 3865;
	private static int DRAGON_HEART_ID = 13001;
	private static String prefix = "Antharas-";

	public Antharas()
	{
		super(21000, "Antharas", "Antharas Individual", true);
		addStartNpc(DRAGON_HEART_ID);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st == null)
			return "noquest";

		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		String text = null;
		if(npcId == DRAGON_HEART_ID)
		{
			if(player.isFlying())
				text = prefix + "wyvern.htm";
			else if(st.getQuestItemsCount(PORTAL_STONE_ID) < 1)
			{
				st.exitCurrentQuest(true);
				text = prefix + "noquest.htm";
			}
			else
			{
				BossState.State state = AntharasManager.getInstance().getState();
				switch(state)
				{
					case NOTSPAWN:
						AntharasManager.getInstance().setAntharasSpawnTask();
						AntharasManager.getInstance().addPlayerToLair(player);
						Location pos = Location.coordsRandomize(new Location(178990, 116520, -7709), 250);
						player.teleToLocation(pos);
						text = null;
						break;
					case ALIVE:
						st.exitCurrentQuest(true);
						text = prefix + "started.htm";
						break;
					case DEAD:
						st.exitCurrentQuest(true);
						text = prefix + "notavailable.htm";
						break;
				}
			}
		}
		return text;
	}
}
