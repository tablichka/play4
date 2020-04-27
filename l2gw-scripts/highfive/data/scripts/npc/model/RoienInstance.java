package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 16:33:11
 */
public class RoienInstance extends CarlInstance
{
	public RoienInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
			showPage(player, "roien003.htm");
			return;
		}

		if(val == 0)
		{
			if(player.getItemCountByItemId(1067) > 0)
				showPage(player, "roien001.htm");
			else if(player.getItemCountByItemId(1067) == 0 && qs.getInt("t1") > 3)
				showPage(player, "roien004.htm");
			else
				showPage(player, "roien003.htm");
		}
		else if(val == 31 && player.getItemCountByItemId(1067) > 0)
		{
			if(!player.isMageClass() && qs.getInt("t1") <= 3)
			{
				qs.giveItems(5789, 200);
				qs.playTutorialVoice("tutorial_voice_026", 1000);
				qs.addExpAndSp(0, 50);
				qs.set("t1", 4);
			}
			else if(player.isMageClass() && qs.getInt("t1") <= 3)
			{
				if(player.getClassId() == ClassId.orcMage)
				{
					qs.giveItems(5789, 200);
					qs.playTutorialVoice("tutorial_voice_026", 1000);
				}
				else
				{
					qs.giveItems(5790, 100);
					qs.playTutorialVoice("tutorial_voice_027", 1000);
				}
				qs.addExpAndSp(0, 50);
				qs.set("t1", 4);
			}
			showPage(player, "roien002.htm");
			qs.takeItems(1067, 1);
			ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 60000);
		}
		else if(val == 41)
		{
			showPage(player, "roien005.htm");
			player.teleToLocation(-120050, 44500, 360);
			qs.showRadar(-119692, 44504, 380, 1);
		}
		else if(val == 42)
		{
			showPage(player, "roien006.htm");
			qs.showRadar( -84081, 243277, -3723, 1);
		}
	}

	@Override
	protected void onTimer(int objectId)
	{
		L2Player player = L2ObjectsStorage.getPlayer(objectId);
		if(player != null && !player.isDead())
		{
			QuestState qs = player.getQuestState("_255_Tutorial");
			if(qs != null && qs.getInt("t1") >= 4)
			{
				qs.showQuestionMark(7);
				qs.playSound(Quest.SOUND_TUTORIAL);
				qs.playTutorialVoice("tutorial_voice_025", 1000);
			}
		}
	}
}
