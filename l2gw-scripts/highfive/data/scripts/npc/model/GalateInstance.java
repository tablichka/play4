package npc.model;

import quests.global.Hellbound;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * User: ic
 * Date: 03.07.2010
 */
public class GalateInstance extends L2NpcInstance
{
	private static String _path = "data/html/default/";
	private static final int CRYSTAL_FRAGMENT = 9693;
	private static final int BLUE_CRYSTAL = 9695;
	private static final int RED_CRYSTAL = 9696;
	private static final int CLEAR_CRYSTAL = 9697;

	public GalateInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(player.isCursedWeaponEquipped())
			return;
		String filename;
		int HBStage = ServerVariables.getInt("hb_stage", 0);


		QuestState q133 = player.getQuestState("_133_ThatsBloodyHot");
		QuestState q131 = player.getQuestState("_131_BirdInACage");

		if(HBStage > 0)
			filename = _path + getNpcId() + "-001a.htm"; // default htm
		else // Hellbound stage zero
		{
			filename = _path + getNpcId() + "-001.htm"; // default htm

			if(ServerVariables.getInt("hb_stage0_accept", 0) == 1 && ((q133 != null && (q133.isStarted() || q133.isCompleted())) || (q131 != null && (q131.isStarted() || q131.isCompleted()))))
			{
				filename = _path + getNpcId() + "-002.htm"; // default htm
			}

			if(val == 1) // give her crystal fragment
			{
				long itemCount = player.getItemCountByItemId(CRYSTAL_FRAGMENT);
				if(itemCount == 0)
					filename = _path + getNpcId() + "-no.htm"; // no items
				else
				{
					player.destroyItemByItemId("GalateAcceptItems", CRYSTAL_FRAGMENT, itemCount, this, true);
					ServerVariables.set("hb_stage0_progress", (ServerVariables.getInt("hb_stage0_progress", 0) + itemCount * 2));
					filename = _path + getNpcId() + "-003-" + getProgress() + ".htm"; // progress htm
				}
			}
			else if(val == 2) // give her blue crystal
			{
				long itemCount = player.getItemCountByItemId(BLUE_CRYSTAL);
				if(itemCount == 0)
					filename = _path + getNpcId() + "-no.htm"; // no items
				else
				{
					player.destroyItemByItemId("GalateAcceptItems", BLUE_CRYSTAL, itemCount, this, true);
					ServerVariables.set("hb_stage0_progress", (ServerVariables.getInt("hb_stage0_progress", 0) + itemCount * 100));
					filename = _path + getNpcId() + "-003-" + getProgress() + ".htm"; // progress htm
				}
			}
			else if(val == 3) // give her red crystal
			{
				long itemCount = player.getItemCountByItemId(RED_CRYSTAL);
				if(itemCount == 0)
					filename = _path + getNpcId() + "-no.htm"; // no items
				else
				{
					player.destroyItemByItemId("GalateAcceptItems", RED_CRYSTAL, itemCount, this, true);
					ServerVariables.set("hb_stage0_progress", (ServerVariables.getInt("hb_stage0_progress", 0) + itemCount * 100));
					filename = _path + getNpcId() + "-003-" + getProgress() + ".htm"; // progress htm
				}
			}
			else if(val == 4) // give her red crystal
			{
				long itemCount = player.getItemCountByItemId(CLEAR_CRYSTAL);
				if(itemCount == 0)
					filename = _path + getNpcId() + "-no.htm"; // no items
				else
				{
					player.destroyItemByItemId("GalateAcceptItems", CLEAR_CRYSTAL, itemCount, this, true);
					ServerVariables.set("hb_stage0_progress", (ServerVariables.getInt("hb_stage0_progress", 0) + itemCount * 100));
					filename = _path + getNpcId() + "-003-" + getProgress() + ".htm"; // progress htm
				}
			}

			if(getProgress() >= 10 && HBStage < 1) // the stage of Hellbound goes up to first.
			{
				filename = _path + getNpcId() + "-003-10.htm"; // progress htm
				Hellbound.setStage(1);
				Hellbound.spawnStage(1);
			}
		}

		NpcHtmlMessage html;
		html = new NpcHtmlMessage(player, this, filename, val);
		player.sendPacket(html);
	}

	private int getProgress()
	{
		return (int) (ServerVariables.getInt("hb_stage0_progress", 0) / 10000f);
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return true;
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

}
