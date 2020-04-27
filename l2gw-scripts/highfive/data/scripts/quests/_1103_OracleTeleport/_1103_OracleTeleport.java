package quests._1103_OracleTeleport;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * Используется для телепорта игроков на фестиваль тьмы.
 */
public class _1103_OracleTeleport extends Quest
{
	private static final int BLOOD_OFFERING = 5901;
	private static final Location ORACLE_OF_DAWN = new Location(-80157, 111344, -4901);
	private static final Location ORACLE_OF_DUSK = new Location(-81261, 86531, -5157);

	public _1103_OracleTeleport()
	{
		super(1103, "_1103_OracleTeleport", "Oracle Teleport", true);

		for(int i = 31078; i < 31092; i++)
			addStartNpc(i);

		for(int i = 31168; i <= 31170; i++)
			addStartNpc(i);

		for(int i = 31692; i <= 31696; i++)
			addStartNpc(i);

		for(int i = 31997; i <= 31999; i++)
			addStartNpc(i);

		for(int j = 31127; j <= 31142; j++)
			addStartNpc(j);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		L2Player player = st.getPlayer();

		switch(npcId)
		{
			// Dawn Locations
			case 31078:
			case 31079:
			case 31080:
			case 31081:
			case 31083:
			case 31084:
			case 31082:
			case 31168:
			case 31692:
			case 31694:
			case 31997:
				player.setVar("FestivalBackCoords", player.getX() + "," + player.getY() + "," + player.getZ());
				player.teleToLocation(ORACLE_OF_DAWN);
				break;
			// Dusk Locations
			case 31085:
			case 31086:
			case 31087:
			case 31088:
			case 31090:
			case 31091:
			case 31089:
			case 31169:
			case 31693:
			case 31695:
			case 31998:
				player.setVar("FestivalBackCoords", player.getX() + "," + player.getY() + "," + player.getZ());
				player.teleToLocation(ORACLE_OF_DUSK);
				break;
			default:
				String back = player.getVar("FestivalBackCoords");

				if(back == null)
					return "Error.htm";

				// Take all Blood Offerings when teleporting character back to the village
				if(st.getQuestItemsCount(BLOOD_OFFERING) > 0)
					st.takeItems(BLOOD_OFFERING, st.getQuestItemsCount(BLOOD_OFFERING));

				player.unsetVar("FestivalBackCoords");

				String[] coords = back.split(",");
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				int z = Integer.parseInt(coords[2]);
				player.teleToLocation(x, y, z);

				break;
		}

		return null;
	}
}