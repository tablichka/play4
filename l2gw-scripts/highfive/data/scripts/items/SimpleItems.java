package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

public class SimpleItems implements IItemHandler, ScriptFile
{
	private static final int[] _itemIds = {
			5125,
			5235, // Face
			5236,
			5237,
			5238,
			5239,
			5240,
			5241, // Hair Color
			5242,
			5243,
			5244,
			5245,
			5246,
			5247,
			5248 // Hair Style
	};

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		L2Skill[] skills = item.getItem().getAttachedSkills();

		if(skills != null && skills.length > 0)
			for(L2Skill skill : skills)
				playable.getAI().Cast(skill, skill.getAimingTarget(playable), item, false, false);
		return true;
	}

	public final int[] getItemIds()
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