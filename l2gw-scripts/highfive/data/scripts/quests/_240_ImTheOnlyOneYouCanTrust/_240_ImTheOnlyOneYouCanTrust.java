package quests._240_ImTheOnlyOneYouCanTrust;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.util.Location;

/**
 * User: ic
 * Date: 19.08.2010
 */
public class _240_ImTheOnlyOneYouCanTrust extends Quest
{
	private static final int KINTAIJIN = 32640; // NPC

	private static final int[] MOBS = {22617, 22618, 22619, 22620, 22621, 22622, 22623, 22624, 22625, 22626, 22627, 22628, 22629, 22630, 22631, 22632, 22633};

	private static final int STAKATO_FANGS = 14879;

	public _240_ImTheOnlyOneYouCanTrust()
	{
		super(240, "_240_ImTheOnlyOneYouCanTrust", "I'm The Only One You Can Trust");

		addStartNpc(KINTAIJIN);
		addTalkId(KINTAIJIN);
		addQuestItem(STAKATO_FANGS);
		addKillId(MOBS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;


		if(event.equalsIgnoreCase("32640-3.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("teleport"))
		{
			String fullpath = "data/scripts/quests/" + getName() + "/";
			if(!st.isCompleted())
			{
				htmltext = "32640-11.htm";

				NpcHtmlMessage htm = new NpcHtmlMessage(st.getPlayer(), st.getPlayer().getLastNpc(), fullpath + htmltext, 0);
				st.getPlayer().sendPacket(htm);
				return null;
			}
			else
			{
				htmltext = "32640-tp.htm";
				NpcHtmlMessage htm = new NpcHtmlMessage(st.getPlayer(), st.getPlayer().getLastNpc(), fullpath + htmltext, 0);
				st.getPlayer().sendPacket(htm);
				return null;
			}
		}
		else if(event.equalsIgnoreCase("teleport1"))
		{
			L2Player player = st.getPlayer();
			Location loc = new Location(80456, -52322, -5640);
			L2NpcInstance npc = player.getLastNpc();
			teleportPlayers(player, loc, npc);
			return null;
		}
		else if(event.equalsIgnoreCase("teleport2"))
		{
			L2Player player = st.getPlayer();
			Location loc = new Location(88718, -46214, -4640);
			L2NpcInstance npc = player.getLastNpc();
			teleportPlayers(player, loc, npc);
			return null;
		}
		else if(event.equalsIgnoreCase("teleport3"))
		{
			L2Player player = st.getPlayer();
			Location loc = new Location(87464, -54221, -5120);
			L2NpcInstance npc = player.getLastNpc();
			teleportPlayers(player, loc, npc);
			return null;
		}
		else if(event.equalsIgnoreCase("teleport4"))
		{
			L2Player player = st.getPlayer();
			Location loc = new Location(80848, -49426, -5128);
			L2NpcInstance npc = player.getLastNpc();
			teleportPlayers(player, loc, npc);
			return null;
		}
		else if(event.equalsIgnoreCase("teleport5"))
		{
			L2Player player = st.getPlayer();
			Location loc = new Location(87682, -43291, -4128);
			L2NpcInstance npc = player.getLastNpc();
			teleportPlayers(player, loc, npc);
			return null;
		}

		return htmltext;
	}

	public void teleportPlayers(L2Player player, Location loc, L2NpcInstance npc)
	{
		if(player.getParty() != null)
		{
			for(L2Player member : player.getParty().getPartyMembers())
				if(npc.isInRange(member, 300))
					member.teleToLocation(loc);
		}
		else
		{
			if(npc.isInRange(player, 300))
				player.teleToLocation(loc);
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCompleted())
			htmltext = "32640-10.htm";
		else if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 81)
				htmltext = "32640-1.htm";
			else
			{
				htmltext = "32640-0.htm";
				st.exitCurrentQuest(true);
			}
		}
		else
		{
			if(cond == 1)
				htmltext = "32640-8.htm";
			else if(cond == 2)
			{
				st.addExpAndSp(589542, 36800);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				htmltext = "32640-9.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(STAKATO_FANGS, 1, 80, 25))
		{
			if(st.getQuestItemsCount(STAKATO_FANGS) >= 25)
			{
				st.set("cond", 2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}
