package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 18:20:31
 */
public class NerupaInstance extends RoienInstance
{
	public NerupaInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
			showPage(player, "nerupa003.htm");
			return;
		}

		if(val == 0)
		{
			if(player.getItemCountByItemId(1069) > 0)
				showPage(player, "nerupa001.htm");
			else if(player.getItemCountByItemId(1069) == 0 && qs.getInt("t1") > 3)
				showPage(player, "nerupa004.htm");
			else
				showPage(player, "nerupa003.htm");
		}
		else if(val == 31 && player.getItemCountByItemId(1069) > 0)
		{
			if(!player.isMageClass() && player.getItemCountByItemId(5789) <= 200)
			{
				qs.giveItems(5789, 200);
				qs.playTutorialVoice("tutorial_voice_026", 1000);
				qs.addExpAndSp(0, 50);
			}
			else if(player.isMageClass() && player.getItemCountByItemId(5789) <= 200 && player.getItemCountByItemId(5790) <= 100)
			{
				qs.giveItems(5790, 100);
				qs.playTutorialVoice("tutorial_voice_027", 1000);
				qs.addExpAndSp(0, 50);
			}
			showPage(player, "nerupa002.htm");
			qs.takeItems(1069, 1);
			ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 60000);
			if(qs.getInt("t1") <= 3)
				qs.set("t1", 4);
		}
		else if(val == 41)
		{
			showPage(player, "nerupa005.htm");
			player.teleToLocation(-120050, 44500, 360);
			qs.showRadar(-119692, 44504, 380, 1);
		}
		else if(val == 42)
		{
			showPage(player, "nerupa006.htm");
			qs.showRadar(45475, 48359, -3060, 1);
		}
	}
}
