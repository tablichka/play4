package quests._381_LetsBecomeARoyalMember;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _381_LetsBecomeARoyalMember extends Quest
{
	//Quest items
	private static int KAILS_COIN = 5899;
	private static int COIN_ALBUM = 5900;
	private static int MEMBERSHIP_1 = 3813;
	private static int CLOVER_COIN = 7569;
	private static int ROYAL_MEMBERSHIP = 5898;
	//NPCs
	private static int SORINT = 30232;
	private static int SANDRA = 30090;
	//MOBs
	private static int ANCIENT_GARGOYLE = 21018;
	private static int VEGUS = 27316;
	//CHANCES (custom values, feel free to change them)
	private static int GARGOYLE_CHANCE = 5;
	private static int VEGUS_CHANCE = 100;

	public _381_LetsBecomeARoyalMember()
	{
		super(381, "_381_LetsBecomeARoyalMember", "Let's become a Royal Member");

		addStartNpc(SORINT);
		addTalkId(SANDRA);

		addKillId(ANCIENT_GARGOYLE);
		addKillId(VEGUS);

		addQuestItem(KAILS_COIN, COIN_ALBUM, CLOVER_COIN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("30232-02.htm"))
			if(st.getPlayer().getLevel() >= 55 && st.getQuestItemsCount(MEMBERSHIP_1) > 0)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				htmltext = "30232-03.htm";
			}
			else
			{
				htmltext = "30232-02.htm";
				st.exitCurrentQuest(true);
			}
		else if(event.equalsIgnoreCase("30090-02.htm"))
			if(st.getInt("cond") == 1)
			{
				st.set("id", "1");
				st.playSound(SOUND_ACCEPT);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		long album = st.getQuestItemsCount(COIN_ALBUM);

		if(npcId == SORINT)
		{
			if(st.isCreated())
				htmltext = "30232-01.htm";
			else if(cond == 1)
			{
				long coin = st.getQuestItemsCount(KAILS_COIN);
				if(coin > 0 && album > 0)
				{
					st.takeItems(KAILS_COIN, -1);
					st.takeItems(COIN_ALBUM, -1);
					st.giveItems(ROYAL_MEMBERSHIP, 1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
					htmltext = "30232-06.htm";
				}
				else if(album == 0)
					htmltext = "30232-05.htm";
				else if(coin == 0)
					htmltext = "30232-04.htm";
			}
		}
		else
		{
			long clover = st.getQuestItemsCount(CLOVER_COIN);
			if(album > 0)
				htmltext = "30090-05.htm";
			else if(clover > 0)
			{
				st.takeItems(CLOVER_COIN, -1);
				st.giveItems(COIN_ALBUM, 1);
				st.playSound(SOUND_ITEMGET);
				htmltext = "30090-04.htm";
			}
			else if(st.getInt("id") == 0)
				htmltext = "30090-01.htm";
			else
				htmltext = "30090-03.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();


		if(npcId == ANCIENT_GARGOYLE && st.rollAndGiveLimited(KAILS_COIN, 1, GARGOYLE_CHANCE, 1))
		{
			if(st.getQuestItemsCount(COIN_ALBUM) > 0 || st.getQuestItemsCount(CLOVER_COIN) > 0)
				st.playSound(SOUND_MIDDLE);
			else
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == VEGUS && st.getQuestItemsCount(CLOVER_COIN) + st.getQuestItemsCount(COIN_ALBUM) == 0 && st.getInt("id") != 0 && st.rollAndGiveLimited(CLOVER_COIN, 1, VEGUS_CHANCE, 1))
			if(st.getQuestItemsCount(KAILS_COIN) > 0)
				st.playSound(SOUND_MIDDLE);
			else
				st.playSound(SOUND_ITEMGET);
	}
}