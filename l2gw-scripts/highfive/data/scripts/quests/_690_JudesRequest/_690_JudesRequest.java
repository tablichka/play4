package quests._690_JudesRequest;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _690_JudesRequest extends Quest
{
	// NPC's
	private static int JUDE = 32356;
	// ITEM's
	private static int EVIL_WEAPON = 10327;
	// MOB's
	private static int[] mobs = {22398, 22399};
	// Chance
	private static int EVIL_WEAPON_CHANCE = 50;
	// Reward Recipe's
	private static int ISawsword = 10373;
	private static int IDisperser = 10374;
	private static int ISpirit = 10375;
	private static int IHeavyArms = 10376;
	private static int ITrident = 10377;
	private static int IHammer = 10378;
	private static int IHand = 10379;
	private static int IHall = 10380;
	private static int ISpitter = 10381;
	// private static int IStinger = 10382;
	// private static int IWingblade = 10383;
	// private static int IShooter = 10384; - piece's ?
	// Reward Piece's
	private static int ISawswordP = 10397;
	private static int IDisperserP = 10398;
	private static int ISpiritP = 10399;
	private static int IHeavyArmsP = 10400;
	private static int ITridentP = 10401;
	private static int IHammerP = 10402;
	private static int IHandP = 10403;
	private static int IHallP = 10404;
	private static int ISpitterP = 10405;
	// private static int IStingerP = 10406;
	// private static int IWingbladeP = 10407;

	public _690_JudesRequest()
	{
		super(690, "_690_JudesRequest", "Jude's Request"); // Party true

		addStartNpc(JUDE);
		addTalkId(JUDE);
		addKillId(mobs);
		addQuestItem(EVIL_WEAPON);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("jude_q0690_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	private void giveReward(QuestState st, int item_id, long count)
	{
		st.giveItems(item_id, count);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(cond == 0)
		{
			if(st.getPlayer().getLevel() >= 78)
				htmltext = "jude_q0690_01.htm";
			else
				htmltext = "jude_q0690_02.htm";
			st.exitCurrentQuest(true);
		}

		if(cond == 1 && st.getQuestItemsCount(EVIL_WEAPON) >= 5)
		{
			int reward = Rnd.get(0, 8);
			if(st.getQuestItemsCount(EVIL_WEAPON) >= 100)
			{
				if(reward == 0)
					giveReward(st, ISawsword, 1);
				else if(reward == 1)
					giveReward(st, IDisperser, 1);
				else if(reward == 2)
					giveReward(st, ISpirit, 1);
				else if(reward == 3)
					giveReward(st, IHeavyArms, 1);
				else if(reward == 4)
					giveReward(st, ITrident, 1);
				else if(reward == 5)
					giveReward(st, IHammer, 1);
				else if(reward == 6)
					giveReward(st, IHand, 1);
				else if(reward == 7)
					giveReward(st, IHall, 1);
				else if(reward == 8)
					giveReward(st, ISpitter, 1);

				st.playSound(SOUND_FINISH);
				st.takeItems(EVIL_WEAPON, 100);
				//st.exitCurrentQuest(true);
				//st.giveItems( );
				htmltext = "jude_q0690_07.htm";

			}

			if(st.getQuestItemsCount(EVIL_WEAPON) > 0 && st.getQuestItemsCount(EVIL_WEAPON) < 100)
			{
				if(reward == 0)
					st.giveItems(ISawswordP, 1);
				else if(reward == 1)
					st.giveItems(IDisperserP, 1);
				else if(reward == 2)
					st.giveItems(ISpiritP, 1);
				else if(reward == 3)
					st.giveItems(IHeavyArmsP, 1);
				else if(reward == 4)
					st.giveItems(ITridentP, 1);
				else if(reward == 5)
					st.giveItems(IHammerP, 1);
				else if(reward == 6)
					st.giveItems(IHandP, 1);
				else if(reward == 7)
					st.giveItems(IHallP, 1);
				else if(reward == 8)
					st.giveItems(ISpitterP, 1);

				st.playSound(SOUND_FINISH);
				st.takeItems(EVIL_WEAPON, 5);
				//st.exitCurrentQuest(true);
				//st.giveItems();
				htmltext = "jude_q0690_09.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null && st.rollAndGive(EVIL_WEAPON, 1, EVIL_WEAPON_CHANCE))
			st.playSound(SOUND_ITEMGET);
	}
}