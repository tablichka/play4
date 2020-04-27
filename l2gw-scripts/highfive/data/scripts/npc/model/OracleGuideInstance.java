package npc.model;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * User: ic
 * Date: 11.11.2009
 */
public class OracleGuideInstance extends L2NpcInstance
{
	// Npcs
	private static final int OG_START = 32273;
	private static final int OG_TEARS = 32274;
	private static final int OG_DARNEL = 32275;
	private static final int OG_SC_1 = 32276;
	private static final int OG_SC_2 = 32277;
	private static final int OG_SC_3 = 32278;
	private static final int OG_KECHI = 32279;
	private static final int OG_BAYLOR = 32280;

	// Items
	private static final int CLEAR_CRYSTAL = 9697;
	private static final int RED_CRYSTAL = 9696;
	private static final int BLUE_CRYSTAL = 9695;

	public OracleGuideInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(getNpcId() == OG_START)
		{
			if(player.getParty() != null && player.getParty().getPartyLeader() == player)
			{
				super.showChatWindow(player, val);
			}
			else
			{
				super.showChatWindow(player, "data/html/default/" + getNpcId() + "-notpl.htm");
			}
		}
		else if(getNpcId() == OG_TEARS || getNpcId() == OG_DARNEL || getNpcId() == OG_KECHI)
		{
			if(player.getParty() != null && player.getParty().getPartyLeader() == player)
			{
				super.showChatWindow(player, val);
			}
			else
			{
				super.showChatWindow(player, "data/html/default/" + getNpcId() + "-notpl.htm");
			}
		}
		else if(getNpcId() == OG_SC_1 || getNpcId() == OG_SC_2 || getNpcId() == OG_SC_3)
		{
			if(player.getParty() == null)
			{
				super.showChatWindow(player, "data/html/default/" + getNpcId() + "-noparty.htm");
			}
			else
			{
				super.showChatWindow(player, val);
			}
		}
		else if(getNpcId() == OG_BAYLOR)
		{
			QuestState qs = player.getQuestState("_131_BirdInACage");
			if(qs != null && qs.getInt("cond") == 2)
			{
				super.showChatWindow(player, "data/html/default/" + getNpcId() + "-quest.htm");
			}
			else
			{
				super.showChatWindow(player, val);
			}
		}
	}
}
