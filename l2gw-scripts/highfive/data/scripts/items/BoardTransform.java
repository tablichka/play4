package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 30.07.13 13:09
 */
public class BoardTransform implements IItemHandler, ScriptFile
{
	private static final int[] itemIds = { 23269, 23270, 23271 };

	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!(playable instanceof L2Player))
			return false;

		L2Player player = (L2Player) playable;

		L2Skill skill = null;
		switch(player.getRace())
		{
			case human:
				skill = SkillTable.getInstance().getInfo(player.isMageClass() ? 20011 : 20010, 1);
				break;
			case darkelf:
				skill = SkillTable.getInstance().getInfo(20012, 1);
				break;
			case elf:
				skill = SkillTable.getInstance().getInfo(20013, 1);
				break;
			case dwarf:
				skill = SkillTable.getInstance().getInfo(20014, 1);
				break;
			case orc:
				skill = SkillTable.getInstance().getInfo(player.isMageClass() ? 20016 : 20015, 1);
				break;
			case kamael:
				skill = SkillTable.getInstance().getInfo(20017, 1);
				break;
		}

		if(skill != null)
		{
			player.doCast(skill, skill.getAimingTarget(player), item, false);
			return true;
		}

		return false;
	}

	@Override
	public int[] getItemIds()
	{
		return itemIds;
	}

	@Override
	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	@Override
	public void onReload()
	{}

	@Override
	public void onShutdown()
	{}
}