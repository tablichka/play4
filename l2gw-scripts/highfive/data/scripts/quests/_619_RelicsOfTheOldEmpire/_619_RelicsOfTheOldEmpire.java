package quests._619_RelicsOfTheOldEmpire;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _619_RelicsOfTheOldEmpire extends Quest
{
	private static final int RELICS = 7254;
	private static final int ENTRANCE = 7075;
	private static final int GHOST = 31538;

	private static final int[] monsters = {
			21396,
			21397,
			21398,
			21399,
			21400,
			21401,
			21402,
			21403,
			21404,
			21405,
			21406,
			21407,
			21408,
			21409,
			21410,
			21411,
			21412,
			21413,
			21414,
			21415,
			21416,
			21417,
			21418,
			21419,
			21420,
			21421,
			21422,
			21423,
			21424,
			21425,
			21426,
			21427,
			21428,
			21429,
			21430,
			21431,
			21432,
			21433,
			21434,
			21798,
			21799,
			21800,
			18120,
			18121,
			18122,
			18123,
			18124,
			18125,
			18126,
			18127,
			18128,
			18129,
			18130,
			18131,
			18132,
			18133,
			18134,
			18135,
			18136,
			18137,
			18138,
			18139,
			18140,
			18141,
			18142,
			18143,
			18144,
			18145,
			18146,
			18147,
			18148,
			18149,
			18150,
			18151,
			18152,
			18153,
			18154,
			18155,
			18156,
			18157,
			18158,
			18159,
			18160,
			18161,
			18162,
			18163,
			18164,
			18165,
			18166,
			18167,
			18168,
			18169,
			18170,
			18171,
			18172,
			18173,
			18174,
			18175,
			18176,
			18177,
			18178,
			18179,
			18180,
			18181,
			18182,
			18183,
			18184,
			18185,
			18186,
			18187,
			18188,
			18189,
			18190,
			18191,
			18192,
			18193,
			18194,
			18195,
			18196,
			18197,
			18198,
			18199,
			18200,
			18201,
			18202,
			18203,
			18204,
			18205,
			18206,
			18207,
			18208,
			18209,
			18210,
			18211,
			18212,
			18213,
			18214,
			18215,
			18216,
			18217,
			18218,
			18219,
			18220,
			18221,
			18222,
			18223,
			18224,
			18225,
			18226,
			18227,
			18228,
			18229,
			18230,
			18231,
			18232,
			18233,
			18234,
			18235,
			18236,
			18237,
			18238,
			18239,
			18240,
			18241,
			18242,
			18243,
			18244,
			18245,
			18246,
			18247,
			18248,
			18249,
			18250,
			18251,
			18252,
			18253,
			18254,
			18255,
			18256,
			13008,
			13009,
			13010,
			13011,
			13012,
			13013,
			13016,
			13017};

	private static final int[] REWARDS = {6881, 6883, 6885, 6887, 6891, 6893, 6895, 6897, 6899, 7580};

	public _619_RelicsOfTheOldEmpire()
	{
		super(619, "_619_RelicsOfTheOldEmpire", "Relics Of The Old Empire"); // Party true
		addStartNpc(GHOST);
		addKillId(monsters);
		addQuestItem(RELICS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("31538-03.htm"))
		{
			if(st.getPlayer().getLevel() >= 74)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "31538-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(event.equals("31538-07.htm"))
		{
			if(st.getQuestItemsCount(RELICS) >= 1000)
			{
				htmltext = "31538-07.htm";
				st.takeItems(RELICS, 1000);
				st.giveItems(REWARDS[Rnd.get(REWARDS.length)], 1);
			}
			else
				htmltext = "31538-05.htm";
		}
		else if(event.equals("31538-08.htm"))
			st.exitCurrentQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 74)
				htmltext = "31538-01.htm";
			else
			{
				htmltext = "31538-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(RELICS) >= 1000)
			htmltext = "31538-04.htm";
		else if(st.getQuestItemsCount(ENTRANCE) > 0)
			htmltext = "31538-05.htm";
		else
			htmltext = "31538-05a.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			int mult = 0;
			if(npc.getLevel() - 76 > 0)
				mult = npc.getLevel() - 76;
			st.rollAndGive(RELICS, 1, 15 + mult * 5);
			st.rollAndGive(ENTRANCE, 1, 3);
		}
	}
}