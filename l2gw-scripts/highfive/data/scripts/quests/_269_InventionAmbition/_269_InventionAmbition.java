package quests._269_InventionAmbition;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Invention Ambition
 */

public class _269_InventionAmbition extends Quest
{
	//NPC
	public final int INVENTOR_MARU = 32486;
	//MOBS
	public final int RED_EYE_BARBED_BAT = 21124;
	public final int UNDERGROUND_KOBOLD = 21132;
	//ITEMS
	public final int ENERGY_ORES = 10866;
	public final int ADENA = 57;

	public _269_InventionAmbition()
	{
		super(269, "_269_InventionAmbition", "Invention Ambition");
		addStartNpc(INVENTOR_MARU);
		addTalkId(INVENTOR_MARU);
		addKillId(RED_EYE_BARBED_BAT);
		addKillId(UNDERGROUND_KOBOLD);
		addQuestItem(ENERGY_ORES);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("32486-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32486-05.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
			if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "32486-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "32486-01.htm";
		else if(st.getQuestItemsCount(ENERGY_ORES) > 0)
		{
			htmltext = "32486-07.htm";
		}
		if(st.getQuestItemsCount(ENERGY_ORES) >= 20)
		{
			int bonus = 2044;
			st.rollAndGive(ADENA, ENERGY_ORES * 50 + bonus, 100);
			st.takeItems(ENERGY_ORES, -1);
		}
		else
			htmltext = "32486-04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGive(ENERGY_ORES, 1, 60))
			st.playSound(SOUND_ITEMGET);
	}
}