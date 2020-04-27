package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 15:25:06
 */
public class PoenyInstance extends CarlInstance
{
	public PoenyInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(!player.isQuestContinuationPossible(true))
			return;

		if(val == 0)
		{
			QuestState qs = player.getQuestState("_255_Tutorial");
			if(qs == null)
			{
				showPage(player, "carl005.htm");
				player.sendActionFailed();
				return;
			}

			if(qs.getInt("t1") < 0)
			{
				if(player.getRace() == Race.darkelf)
				{
					qs.deleteRadar(28384, 11056, -4233, 2);
					ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 30000);
					qs.set("t1", 0);
					int i1 = qs.getInt("t");
					int i0 = (i1 & 2147483392);
					qs.onTutorialClientEvent(i0 | 1048576);
					showPage(player, player.isMageClass() ? "doff001.htm" : "carl001.htm");
				}
				else
					showPage(player, "carl006.htm");
			}
			else if(qs.getInt("t1") >= 0 && qs.getInt("t1") <= 2 && !qs.haveQuestItems(6353))
				showPage(player, player.isMageClass() ? "doff002.htm" : "carl002.htm");
			else if(qs.getInt("t1") >= 0 && qs.getInt("t1") <= 2 && qs.haveQuestItems(6353))
			{
				qs.takeItems(6353, -1);
				qs.set("t1", 3);
				qs.giveItems(1070, 1);
				ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 30000);
				int i0 = qs.getInt("t") & 2147483392;
				qs.set("t", i0 | 4);
				if(!player.isMageClass() && !qs.haveQuestItems(5789))
				{
					qs.giveItems(5789, 200);
					qs.playTutorialVoice("tutorial_voice_026", 1000);
				}
				else if(player.isMageClass() && !qs.haveQuestItems(5789) && !qs.haveQuestItems(5790))
				{
					if(player.getClassId() == ClassId.orcMage)
					{
						qs.playTutorialVoice("tutorial_voice_026", 1000);
						qs.giveItems(5789, 200);
					}
					else
					{
						qs.playTutorialVoice("tutorial_voice_027", 1000);
						qs.giveItems(5790, 100);
					}
				}
				showPage(player, player.isMageClass() ? "poeny003m.htm" : "poeny003f.htm");
			}
			else if(qs.getInt("t1") == 3)
				showPage(player, "poeny004.htm");
			else
				showPage(player, "carl005.htm");
		}
	}

	@Override
	protected void onTimer(int objectId)
	{
		L2Player player = L2ObjectsStorage.getPlayer(objectId);
		if(player != null && !player.isDead())
		{
			QuestState qs = player.getQuestState("_255_Tutorial");
			if(qs != null)
			{
				if(qs.getInt("t1") == 0)
				{
					qs.playTutorialVoice(player.isMageClass() ? "tutorial_voice_009b" : "tutorial_voice_009a", 0);
					qs.set("t1", 1);
				}
				else if(qs.getInt("t1") == 3)
					qs.playTutorialVoice("tutorial_voice_010d", 0);
			}
		}
	}
}
