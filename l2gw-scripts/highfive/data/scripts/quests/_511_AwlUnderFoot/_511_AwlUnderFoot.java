package quests._511_AwlUnderFoot;

import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2CampkeeperInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.commons.math.Rnd;


public class _511_AwlUnderFoot extends Quest
{
	private static final String prefix = "gludio_fort_a_campkeeper_q0511_";
	private static final int TOTAL_FRAGMENTS = 150;
	private static final int DUNGEON_LEADER_MARK = 9797;
	private static final int KNIGHTS_EP = 9912;
	private static final int LEADERS[] = {
			25589, // Brand the Exile
			25592, // Commander Koenig
			25593, // Gerg the Hunter
	};
	private static final int DETENTION_CAMP_KEEPERS[] = {
			35666, // Shanty Fortress
			35698, // Southern Gludio Fortress
			35735, // Hive Fortress
			35767, // Valley Fortress
			35804, // Ivory Tower Fortress
			35835, // Narsell Fortress
			35867, // Bayou Fortress
			35904, // White Sands Fortress
			35936, // Borderland Fortress
			35974, // Swamp Fortress
			36011, // Archaic Fortress
			36043, // Floran Fortress
			36081, // Cloud Mountain
			36118, // Tanor Fortress
			36149, // Dragonspine Fortress
			36181, // Land Dragon Fortress
			36219, // Western Fortress
			36257, // Hunter's Fortress
			36294, // Aaru Fortress
			36326, // Demon Fortress
			36364, // Monastic Fortress
	};

	public _511_AwlUnderFoot()
	{
		super(511, "_511_AwlUnderFoot", "Awl Under Foot"); // Party true

		addStartNpc(DETENTION_CAMP_KEEPERS);
		addTalkId(DETENTION_CAMP_KEEPERS);
		addQuestItem(DUNGEON_LEADER_MARK);
		addKillId(LEADERS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		L2NpcInstance warden = st.getPlayer().getLastNpc();
		if(!(warden instanceof L2CampkeeperInstance))
		{
			System.out.println("Awl Under Foot (511): Detention Camp Keeper is null!!!");
			return null;
		}
		if(event.equalsIgnoreCase("accept"))
		{
			if(st.getPlayer() != null && (st.getPlayer().getClanId() == 0 || st.getPlayer().getClanId() != warden.getBuilding(1).getOwnerId()))
				htmltext = prefix + "01a.htm";
			else if(st.getPlayer().getLevel() < 60)
				htmltext = prefix + "02.htm";
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				htmltext = prefix + "03.htm";
			}
		}
		else if(event.equalsIgnoreCase("gludio_fort_a_campkeeper_q0511_11.htm"))
		{
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(cond != 1)
			htmltext = prefix + "01.htm";
		else if(st.getQuestItemsCount(DUNGEON_LEADER_MARK) > 0)
		{
			long frags = st.getQuestItemsCount(DUNGEON_LEADER_MARK);
			st.takeItems(DUNGEON_LEADER_MARK, frags);
			st.giveItems(KNIGHTS_EP, frags * 5);
			htmltext = prefix + "09.htm";
		}
		else
			htmltext = prefix + "08.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{

		int partysize = 0;
		if(killer.getParty() != null)
			partysize = killer.getParty().getMemberCount();
		if(partysize == 0)
			partysize = 1;

		int reward = (int) (TOTAL_FRAGMENTS * 0.01f * (95 + Rnd.get(0, 10)) / partysize);

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
			st.giveItems(DUNGEON_LEADER_MARK, reward);

	}
}
