package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 19:21:48
 */
public class PerwanInstance extends RoienInstance
{
	public PerwanInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(!player.isQuestContinuationPossible(true))
		{
			player.sendActionFailed();
			return;
		}

		QuestState qs = player.getQuestState("_255_Tutorial");
		if(qs == null)
		{
			showPage(player, "subelder_perwan003.htm");
			return;
		}

		if(val == 0)
		{
			if(player.getItemCountByItemId(9881) > 0)
				showPage(player, "subelder_perwan001.htm");
			else if(player.getItemCountByItemId(9881) == 0 && qs.getInt("t1") > 3)
				showPage(player, "subelder_perwan004.htm");
			else
				showPage(player, "subelder_perwan003.htm");
		}
		else if(val == 31 && player.getItemCountByItemId(9881) > 0)
		{
			if(player.getClassId().getLevel() == 1 && qs.getInt("t1") <= 3)
			{
				qs.playTutorialVoice("tutorial_voice_026", 1000);
				qs.giveItems(5789, 200);
				qs.addExpAndSp(0, 50);
				qs.set("t1", 4);
			}
			qs.takeItems(9881, 1);
			showPage(player, "subelder_perwan002.htm");
			ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 60000);
			qs.showRadar(-119692, 44504, 380, 1);
		}
	}
}
