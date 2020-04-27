package quests.Individual;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.boss.ValakasManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.util.Location;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 23.01.2009
 * Time: 10:44:17
 */
public class Valakas extends Quest
{
	private static final int KLEIN_ID = 31540;
	private static final int VOLCANO_HEART_ID = 31385;
	private static final int GATEKEEPER1 = 31384;
	private static final int GATEKEEPER2 = 31686;
	private static final int GATEKEEPER3 = 31687;
	private static final int FLOATING_STONE_ID = 7267;
	private static String prefix = "Valakas-";

	public Valakas()
	{
		super(21001, "Valakas", "Valakas Individual", true);

		addStartNpc(KLEIN_ID);
		addStartNpc(VOLCANO_HEART_ID);
		addTalkId(KLEIN_ID, VOLCANO_HEART_ID, GATEKEEPER1, GATEKEEPER2, GATEKEEPER3);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st == null)
			return "noquest";

		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		String text = null;
		switch(npcId)
		{
			case VOLCANO_HEART_ID: // TODO: count people and show htms if there are too many
				if(st.getQuestItemsCount(FLOATING_STONE_ID) < 1)
				{
					st.exitCurrentQuest(true);
					text = prefix + "noquest.htm";
				}
				else
				{
					switch(ValakasManager.getInstance().getState())
					{
						case NOTSPAWN:
							ValakasManager.getInstance().setValakasSpawnTask();
							ValakasManager.getInstance().addPlayerToLair(player);
							player.teleToLocation(Location.coordsRandomize(new Location(204210, -112200, 45), 250));
							text = null;
							break;
						case DEAD:
							st.exitCurrentQuest(true);
							text = prefix + "notavailable.htm";
							break;
						case ALIVE:
							st.exitCurrentQuest(true);
							text = prefix + "started.htm";
							break;
					}
				}
				break;

			case KLEIN_ID:   // TODO: count people entered and show htm about it
				if(st.getQuestItemsCount(FLOATING_STONE_ID) > 0)
				{
					player.teleToLocation(183831, -115457, -3296);
				}
				else
				{
					st.exitCurrentQuest(true);
					text = prefix + "klein.htm";
				}
				break;

			case GATEKEEPER1:
				if(!DoorTable.getInstance().getDoor(24210004).isOpen())
				{
					DoorTable.getInstance().getDoor(24210004).openMe();
					DoorTable.getInstance().getDoor(24210004).onOpen();
				}
				break;

			case GATEKEEPER2:
				if(!DoorTable.getInstance().getDoor(24210005).isOpen())
				{
					DoorTable.getInstance().getDoor(24210005).openMe();
					DoorTable.getInstance().getDoor(24210005).onOpen();
				}
				break;

			case GATEKEEPER3:
				if(!DoorTable.getInstance().getDoor(24210006).isOpen())
				{
					DoorTable.getInstance().getDoor(24210006).openMe();
					DoorTable.getInstance().getDoor(24210006).onOpen();
				}
				break;
		}
		return text;
	}
}
