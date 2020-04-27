package npc.model;

import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.List;

/**
 * User: ic
 * Date: 21.10.2009
 */
public class DCMSymbolInstance extends L2NpcInstance
{
	public final static int SYMBOL_OF_FAITH = 32288;
	public final static int SYMBOL_OF_ADVERSITY = 32289;
	public final static int SYMBOL_OF_ADVENTURE = 32290;
	public final static int SYMBOL_OF_TRUTH = 32291;
	private List<Integer> gotCrystals;
	public final static int CONTAMINATED_CRYSTAL = 9690;

	public DCMSymbolInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}


	@Override
	public void onSpawn()
	{
		if(getNpcId() == SYMBOL_OF_TRUTH)
			gotCrystals = new FastList<Integer>();
		super.onSpawn();
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		String eventName = null;
		if(Config.DEBUG_INSTANCES)
			Instance._log.info("DCMSymbolInstance[refId=" + this.getReflection() + ";]: showChatWindow of " + this + " clicked by " + player);
		if(getNpcId() == SYMBOL_OF_FAITH)
			eventName = "faith";
		else if(getNpcId() == SYMBOL_OF_ADVERSITY)
			eventName = "adversity";
		else if(getNpcId() == SYMBOL_OF_ADVENTURE)
			eventName = "adventury";
		else if(getNpcId() == SYMBOL_OF_TRUTH)
			eventName = "truth";

		if(getSpawn() != null && getSpawn().getInstance() != null && eventName != null)
			getSpawn().getInstance().notifyEvent(eventName,this,player);

		if(getNpcId() != SYMBOL_OF_TRUTH)
			super.showChatWindow(player, val);
		else
		{
			Quest q = QuestManager.getQuest("DarkCloudMansion");
			QuestState qs = player.getQuestState("DarkCloudMansion");
			if(qs == null)
				qs = q.newQuestState(player);

			if(!gotCrystals.contains(player.getObjectId()) && player.getItemCountByItemId(CONTAMINATED_CRYSTAL) == 0)
			{
				gotCrystals.add(player.getObjectId());
				qs.giveItems(CONTAMINATED_CRYSTAL, 1);
				super.showChatWindow(player, val);
			}
			else
				super.showChatWindow(player, "data/html/default/32291a.htm");
		}
	}

}
