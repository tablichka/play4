package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 17.11.2010 18:54:38
 */
public class GuideGludinInstance extends NewbieHelperInstance
{
	public GuideGludinInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	public void showChatWindow(L2Player player, int val)
	{
		if(val == 0)
		{
			QuestState qs = player.getQuestState("_255_Tutorial");
			if(qs == null)
			{
				super.showChatWindow(player, val);
				return;
			}

			if(!player.isQuestContinuationPossible(true))
				return;

			if(player.getLevel() < 18)
				showPage(player, "newbie_guide_q0041_16.htm");
			else if(player.getLevel() < 20)
				showPage(player, "newbie_guide_q0041_17.htm");
			else
			{
				showPage(player, "newbie_guide_q0041_17.htm");
				player.setVar("NR41", -1);
			}
		}
		else
			super.showChatWindow(player, val);
	}
}
