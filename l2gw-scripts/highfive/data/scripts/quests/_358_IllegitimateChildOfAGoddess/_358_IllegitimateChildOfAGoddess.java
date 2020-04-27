package quests._358_IllegitimateChildOfAGoddess;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _358_IllegitimateChildOfAGoddess extends Quest
{
	//Variables
	private static final int DROP_RATE = 70; //in %
	private static final int REQUIRED = 108; //how many items will be paid for a reward (affects onkill sounds too)

	//Quest items
	private static final int SN_SCALE = 5868;

	//Rewards
	//private static final int REWARDS=range(6329,6340,2)+range(5364,5367,2)
	private static final int SPhoenixNecl70 = 6329; //16%
	private static final int SPhoenixEarr70 = 6331; //17%
	private static final int SPhoenixRing70 = 6333; //17%

	private static final int SMajestNecl70 = 6335; //8%
	private static final int SMajestEarr70 = 6337; //9%
	private static final int SMajestRing70 = 6339; //9%

	private static final int SDarkCryShield60 = 5364; //8%
	private static final int SNightMareShield60 = 5366; //16%
	//Messages
	private static final String defaulttext = "noquest";

	//NPCs
	private static final int OLTLIN = 30862;

	//Mobs
	private static final int MOB1 = 20672;
	private static final int MOB2 = 20673;

	public _358_IllegitimateChildOfAGoddess()
	{
		super(358, "_358_IllegitimateChildOfAGoddess", "Illegitimate Child Of A Goddess");
		addStartNpc(OLTLIN);

		addKillId(MOB1);
		addKillId(MOB2);

		addQuestItem(SN_SCALE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30862-5.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30862-6.htm"))
			st.exitCurrentQuest(true);
		else if(event.equalsIgnoreCase("30862-7.htm"))
			if(st.getQuestItemsCount(SN_SCALE) >= REQUIRED)
			{
				st.takeItems(SN_SCALE, REQUIRED);
				int item;
				int chance = Rnd.get(100);
				if(chance <= 16)
					item = SPhoenixNecl70;
				else if(chance <= 33)
					item = SPhoenixEarr70;
				else if(chance <= 50)
					item = SPhoenixRing70;
				else if(chance <= 58)
					item = SMajestNecl70;
				else if(chance <= 67)
					item = SMajestEarr70;
				else if(chance <= 76)
					item = SMajestRing70;
				else if(chance <= 84)
					item = SDarkCryShield60;
				else
					item = SNightMareShield60;
				st.giveItems(item, 1);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
			else
				htmltext = "30862-4.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = defaulttext;
		if(st.isCreated())
		{
			st.set("cond", "0");
			if(st.getPlayer().getLevel() < 63)
			{
				st.exitCurrentQuest(true);
				htmltext = "30862-1.htm";
			}
			else
				htmltext = "30862-2.htm";
		}
		else if(st.isStarted())
			if(st.getQuestItemsCount(SN_SCALE) >= REQUIRED)
				htmltext = "30862-3.htm";
			else
				htmltext = "30862-4.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(SN_SCALE, 1, DROP_RATE, REQUIRED))
		{
			if(st.getQuestItemsCount(SN_SCALE) == REQUIRED)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}