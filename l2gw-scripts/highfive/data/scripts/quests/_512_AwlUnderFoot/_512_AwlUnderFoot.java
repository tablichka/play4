package quests._512_AwlUnderFoot;

import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2CampkeeperInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.commons.math.Rnd;


public class _512_AwlUnderFoot extends Quest
{
	private static final String prefix = "gludio_prison_keeper_q0512_";
	private static final int TOTAL_FRAGMENTS = 150;
	private static final int FRAGMENT_OF_THE_DUNGEON_LEADER_MARK = 9798;
	private static final int KNIGHTS_EP = 9912;
	private static final int LEADERS[] = {
			25563, // Beautiful Atrielle
			25566, // Nagen the Tomboy
			25569, // Jax the Destroyer
	};
	private static final int WARDENS[] = {
			36403, // Gludio
			36404, // Dion
			36405, // Giran
			36406, // Oren
			36407, // Aden
			36408, // Innadril
			36409, // Goddard
			36410, // Rune
			36411, // Schuttgart
	};

	public _512_AwlUnderFoot()
	{
		super(512, "_512_AwlUnderFoot", "Awl Under Foot"); // Party true

		addStartNpc(WARDENS);
		addTalkId(WARDENS);
		addQuestItem(FRAGMENT_OF_THE_DUNGEON_LEADER_MARK);
		addKillId(LEADERS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		L2NpcInstance warden = st.getPlayer().getLastNpc();
		if(!(warden instanceof L2CampkeeperInstance))
		{
			System.out.println("Awl Under Foot (512): Warden is null!!!");
			return null;
		}
		if(event.equalsIgnoreCase("accept"))
		{
			if(st.getPlayer() != null && (st.getPlayer().getClanId() == 0 || st.getPlayer().getClanId() != warden.getCastle().getOwnerId()))
				htmltext = prefix + "01a.htm";
			else if(st.getPlayer().getLevel() < 70)
				htmltext = prefix + "02.htm";
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				htmltext = prefix + "03.htm";
			}
		}
		else if(event.equalsIgnoreCase("gludio_prison_keeper_q0512_10.htm"))
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
		else if(st.getQuestItemsCount(FRAGMENT_OF_THE_DUNGEON_LEADER_MARK) > 0)
		{
			long frags = st.getQuestItemsCount(FRAGMENT_OF_THE_DUNGEON_LEADER_MARK);
			st.takeItems(FRAGMENT_OF_THE_DUNGEON_LEADER_MARK, frags);
			st.giveItems(KNIGHTS_EP, frags * 10);
			htmltext = prefix + "08.htm";
		}
		else
			htmltext = prefix + "07.htm";
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
			st.giveItems(FRAGMENT_OF_THE_DUNGEON_LEADER_MARK, reward);

	}
}
