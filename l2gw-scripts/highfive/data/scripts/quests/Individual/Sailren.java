package quests.Individual;

import bosses.SailrenManager;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 14.10.2009 11:17:14
 */
public class Sailren extends Quest
{
	private static int STATUE = 32109;
	private static String prefix = "Sailren-";
	private final static boolean GMTEST = false;
	private final static Location START_LOCATION = new Location(27222, -6783, -2000);
	private final static int GAZKH = 8784;
	private final static int SAILREN = 29065;

	public Sailren()
	{
		super(21005, "Sailren", "Sailren Individual", true);
		addStartNpc(STATUE);
		addTalkId(STATUE);
		addKillId(SAILREN);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st == null)
			return "You are either not carrying out your quest or don't meet the criteria.";

		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		String text = null;
		if(npcId == STATUE)
		{
			if(!GMTEST)
			{
				if(player.getParty() != null && player.getParty().getPartyLeader() != player)
					text = prefix + "partyleader.htm";
				else if(player.getParty() == null)
					text = prefix + "noparty.htm";
				else if(st.getQuestItemsCount(GAZKH) < 1)
					text = prefix + "noquest.htm";
				else
				{
					switch(SailrenManager.getInstance().getState())
					{
						case NOTSPAWN:
							if(SailrenManager.getInstance().isStarted())
								text = prefix + "started.htm";
							else
							{
								SailrenManager.getInstance().start();
								for(L2Player member : player.getParty().getPartyMembers())
									if(npc.isInRange(member, 900))
										member.teleToLocation(Location.coordsRandomize(START_LOCATION, 250));
								st.takeItems(GAZKH, 1);
								st.exitCurrentQuest(true);
								text = null;
							}
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
			}
			else
			{
				switch(SailrenManager.getInstance().getState())
				{
					case NOTSPAWN:
						if(SailrenManager.getInstance().isStarted())
							text = prefix + "started.htm";
						else
						{
							SailrenManager.getInstance().start();
							if(player.getParty() != null)
								for(L2Player member : player.getParty().getPartyMembers())
									member.teleToLocation(Location.coordsRandomize(START_LOCATION, 250));
							else
								player.teleToLocation(Location.coordsRandomize(START_LOCATION, 250));
							return null;
						}
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
		}
		return text;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == SAILREN)
			SailrenManager.getInstance().sailrenKilled();
	}
}