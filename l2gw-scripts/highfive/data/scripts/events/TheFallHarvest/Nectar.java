package events.TheFallHarvest;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.SkillTable;

public class Nectar implements IItemHandler, ScriptFile
{
	private static int[] _itemIds = { 6391 };

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		L2Player player = (L2Player) playable;
		L2Character target = (L2Character) player.getTarget();

		if(!(target instanceof SquashInstance))
		{
			player.sendPacket(Msg.INVALID_TARGET);
			return false;
		}

		L2Skill skill = SkillTable.getInstance().getInfo(2005, 1);
		if(skill != null) // && skill.checkCondition(player, target, true, false, true))
			player.getAI().Cast(skill, target);
		return true;
	}

	public int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
