package quests._331_ArrowForVengeance;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Рейты применены путем увеличения шанса/количества квестовго дропа
 */
public class _331_ArrowForVengeance extends Quest
{
	private static final int HARPY_FEATHER = 1452;
	private static final int MEDUSA_VENOM = 1453;
	private static final int WYRMS_TOOTH = 1454;
	private static final int ADENA = 57;

	public _331_ArrowForVengeance()
	{
		super(331, "_331_ArrowForVengeance", "Arrow For Vengeance");
		addStartNpc(30125);

		addKillId(20145, 20158, 20176);

		addQuestItem(HARPY_FEATHER, MEDUSA_VENOM, WYRMS_TOOTH);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30125-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30125-06.htm"))
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
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 32)
			{
				htmltext = "30125-02.htm";
				return htmltext;
			}
			htmltext = "30125-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1)
			if(st.getQuestItemsCount(HARPY_FEATHER) + st.getQuestItemsCount(MEDUSA_VENOM) + st.getQuestItemsCount(WYRMS_TOOTH) > 0)
			{
				st.rollAndGive(ADENA, 80 * st.getQuestItemsCount(HARPY_FEATHER) + 90 * st.getQuestItemsCount(MEDUSA_VENOM) + 100 * st.getQuestItemsCount(WYRMS_TOOTH), 100);
				st.takeItems(HARPY_FEATHER, -1);
				st.takeItems(MEDUSA_VENOM, -1);
				st.takeItems(WYRMS_TOOTH, -1);
				htmltext = "30125-05.htm";
			}
			else
				htmltext = "30125-04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") > 0)
			switch(npc.getNpcId())
			{
				case 20145:
					st.rollAndGive(HARPY_FEATHER, 1, 33);
					break;
				case 20158:
					st.rollAndGive(MEDUSA_VENOM, 1, 33);
					break;
				case 20176:
					st.rollAndGive(WYRMS_TOOTH, 1, 33);
					break;
			}
	}
}