package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 19:09:35
 */
public class VullkusInstance extends RoienInstance
{
	public VullkusInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
			showPage(player, "guardian_vullkus003.htm");
			return;
		}

		if(val == 0)
		{
			if(player.getItemCountByItemId(1496) > 0)
				showPage(player, "guardian_vullkus001.htm");
			else if(player.getItemCountByItemId(1496) == 0 && qs.getInt("t1") > 3)
				showPage(player, "guardian_vullkus004.htm");
			else
				showPage(player, "guardian_vullkus003.htm");
		}
		else if(val == 31 && player.getItemCountByItemId(1496) > 0)
		{
			qs.takeItems(1496, 1);
			if(player.getItemCountByItemId(5789) <= 200)
			{
				qs.giveItems(5789, 200);
				qs.addExpAndSp(0, 50);
			}
			showPage(player, "guardian_vullkus002.htm");
			ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 60000);
			qs.set("t1", 4);
			qs.playTutorialVoice("tutorial_voice_026", 1000);
		}
		else if(val == 41)
		{
			showPage(player, "guardian_vullkus005.htm");
			player.teleToLocation(-120050, 44500, 360);
			qs.showRadar(-119692, 44504, 380, 1);
		}
		else if(val == 42)
		{
			showPage(player, "guardian_vullkus006.htm");
			qs.showRadar(-45032, -113598, -192, 1);
		}
	}
}
