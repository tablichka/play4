package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.11.2010 11:59:19
 */
public class CarlInstance extends L2NpcInstance
{
	protected static final String _path = "data/html/guide/";

	public CarlInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
				if(player.getClassId() == ClassId.fighter && player.getRace() == Race.human)
				{
					qs.deleteRadar(-71424, 258336, -3109, 2);
					ThreadPoolManager.getInstance().scheduleGeneral(new Timer(player.getObjectId()), 30000);
					qs.set("t1", 0);
					int i1 = qs.getInt("t");
					int i0 = (i1 & 2147483392);
					qs.onTutorialClientEvent(i0 | 1048576);
					showPage(player, "carl001.htm");
				}
				else
					showPage(player, "carl006.htm");
			}
			else if(qs.getInt("t1") >= 0 && qs.getInt("t1") <= 2 && !qs.haveQuestItems(6353))
				showPage(player, "carl002.htm");
			else if(qs.getInt("t1") >= 0 && qs.getInt("t1") <= 2 && qs.haveQuestItems(6353))
			{
				qs.takeItems(6353, -1);
				qs.set("t1", 3);
				qs.giveItems(1067, 1);
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
				showPage(player, "carl003.htm");
			}
			else if(qs.getInt("t1") == 3)
				showPage(player, "carl004.htm");
			else
				showPage(player, "carl005.htm");
		}
	}

	@Override
	public void showPage(L2Player player, String page)
	{
		player.sendPacket(new NpcHtmlMessage(player, this, _path + page, 0));
	}

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
					qs.playTutorialVoice("tutorial_voice_009a", 0);
					qs.set("t1", 1);
				}
				else if(qs.getInt("t1") == 3)
					qs.playTutorialVoice("tutorial_voice_010a", 0);
			}
		}
	}

	protected class Timer implements Runnable
	{
		private final int _objectId;
		public Timer(int objectId)
		{
			_objectId = objectId;
		}

		public void run()
		{
			onTimer(_objectId);
		}
	}
}
