package quests._262_TradewiththeIvoryTower;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _262_TradewiththeIvoryTower extends Quest
{
	//NPC
	public final int VOLODOS = 30137;

	//MOB
	public final int GREEN_FUNGUS = 20007;
	public final int BLOOD_FUNGUS = 20400;

	public final int FUNGUS_SAC = 707;
	public final int ADENA = 57;

	public _262_TradewiththeIvoryTower()
	{
		super(262, "_262_TradewiththeIvoryTower", "Tradewiththe Ivory Tower");

		addStartNpc(VOLODOS);
		addKillId(new int[]{BLOOD_FUNGUS, GREEN_FUNGUS});
		addQuestItem(new int[]{FUNGUS_SAC});
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30137-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 8)
			{
				htmltext = "30137-02.htm";
				return htmltext;
			}
			htmltext = "30137-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1 && st.getQuestItemsCount(FUNGUS_SAC) < 10)
			htmltext = "30137-04.htm";
		else if(cond == 2 && st.getQuestItemsCount(FUNGUS_SAC) >= 10)
		{
			st.giveItems(ADENA, 3000);
			st.takeItems(FUNGUS_SAC, -1);
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			htmltext = "30137-05.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getInt("cond") == 1)
			if((npcId == GREEN_FUNGUS || npcId == BLOOD_FUNGUS) && st.rollAndGiveLimited(FUNGUS_SAC, 1, npcId == GREEN_FUNGUS ? 30 : 40, 10))
			{
				if(st.getQuestItemsCount(FUNGUS_SAC) == 10)
				{
					st.set("cond", "2");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}