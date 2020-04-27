package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 19:15:37
 */
public class LaferonInstance extends RoienInstance
{
	public LaferonInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
			showPage(player, "foreman_laferon003.htm");
			return;
		}

		if(val == 0)
		{
			if(player.getItemCountByItemId(1498) > 0)
				showPage(player, "foreman_laferon001.htm");
			else if(player.getItemCountByItemId(1498) == 0 && qs.getInt("t1") > 3)
				showPage(player, "foreman_laferon004.htm");
			else
				showPage(player, "foreman_laferon003.htm");
		}
		else if(val == 31 && player.getItemCountByItemId(1498) > 0)
		{
			if(!player.isMageClass() && player.getItemCountByItemId(5789) <= 200)
			{
				qs.playTutorialVoice("tutorial_voice_026", 1000);
				qs.giveItems(5789, 200);
				qs.addExpAndSp(0, 50);
			}
			else if(player.isMageClass() && player.getItemCountByItemId(5789) <= 200 && player.getItemCountByItemId(5790) <= 100)
			{
				qs.playTutorialVoice("tutorial_voice_027", 1000);
				qs.giveItems(5790, 100);
				qs.addExpAndSp(0, 50);
			}
			qs.takeItems(1498, 1);
			showPage(player, "foreman_laferon002.htm");
			ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 60000);
			qs.set("t1", 4);
		}
		else if(val == 41)
		{
			showPage(player, "foreman_laferon005.htm");
			player.teleToLocation(-120050, 44500, 360);
			qs.showRadar(-119692, 44504, 380, 1);
		}
		else if(val == 42)
		{
			showPage(player, "foreman_laferon006.htm");
			qs.showRadar(115632, -177996, -905, 1);
		}
	}
}
