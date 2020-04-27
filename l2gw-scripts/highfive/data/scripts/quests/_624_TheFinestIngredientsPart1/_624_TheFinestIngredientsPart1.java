package quests._624_TheFinestIngredientsPart1;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * <hr><em>Квест</em> <strong>The Finest Ingredients Part1</strong><hr>
 *
 * @author
 * @version CT2
 * @lastfix HellSinger
 */
public class _624_TheFinestIngredientsPart1 extends Quest
{
	//NPCs
	private static final int Jeremy = 31521;
	//MOBs
	private static final int HotSpringsNepenthes = 21319;
	private static final int HotSpringsAtroxspawn = 21317;
	private static final int HotSpringsAtrox = 21321;
	private static final int HotSpringsBandersnatchling = 21314;
	//ITEMs
	private static final short SecretSpice = 7204;
	private static final short TrunkOfNepenthes = 7202;
	private static final short FootOfBandersnatchling = 7203;
	private static final short IceCrystal = 7080;
	private static final short SoySauceJar = 7205;

	public _624_TheFinestIngredientsPart1()
	{
		super(624, "_624_TheFinestIngredientsPart1", "The Finest Ingredients Part1"); // Party true

		addStartNpc(Jeremy);
		addKillId(HotSpringsAtrox, HotSpringsNepenthes, HotSpringsAtroxspawn, HotSpringsBandersnatchling);
		addQuestItem(TrunkOfNepenthes, FootOfBandersnatchling, SecretSpice);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31521-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31521-4.htm"))
		{
			if(st.haveQuestItems(TrunkOfNepenthes, 50) && st.haveQuestItems(FootOfBandersnatchling, 50) && st.haveQuestItems(SecretSpice, 50))
			{
				htmltext = "31521-4.htm";
				st.playSound(SOUND_FINISH);
				st.giveItems(SoySauceJar, 1);
				if(!st.haveQuestItems(IceCrystal))
					st.giveItems(IceCrystal, 1);
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "31521-5.htm";
				st.set("cond", "1");
			}
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 73)
			{
				htmltext = "31521-0a.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31521-0.htm";
		}
		else if(st.isStarted())
		{
			if(st.getInt("cond") == 1)
				htmltext = "31521-2.htm";
			else if(st.getCond() == 3 && st.haveQuestItems(TrunkOfNepenthes, 50) && st.haveQuestItems(FootOfBandersnatchling, 50) && st.haveQuestItems(SecretSpice, 50))
				htmltext = "31521-3.htm";
			else
			{
				htmltext = "31521-5.htm";
				st.set("cond", "1");
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{

		GArray<QuestState> pm = new GArray<QuestState>();
		int npcId = npc.getNpcId();
		int item = -1;

		if(npcId == HotSpringsNepenthes)
			item = TrunkOfNepenthes;
		else if(npcId == HotSpringsBandersnatchling)
			item = FootOfBandersnatchling;
		else if(npcId == HotSpringsAtrox || npcId == HotSpringsAtroxspawn)
			item = SecretSpice;

		if(item == -1)
			return;

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
			if(st.getQuestItemsCount(item) < 100)
				pm.add(st);

		if(pm.isEmpty())
			return;

		QuestState st = pm.get(Rnd.get(pm.size()));

		if(st.rollAndGiveLimited(item, 1, 100, 50))
			st.playSound(st.getQuestItemsCount(item) == 50 ? SOUND_MIDDLE : SOUND_ITEMGET);

		if(st.haveQuestItems(TrunkOfNepenthes, 50) && st.haveQuestItems(FootOfBandersnatchling, 50) && st.haveQuestItems(SecretSpice, 50))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}
}