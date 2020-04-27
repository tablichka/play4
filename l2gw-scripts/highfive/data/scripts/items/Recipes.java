package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.RecipeController;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.recipe.RecipeList;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

import java.util.HashMap;

public class Recipes implements IItemHandler, ScriptFile
{
	private static int[] _itemIds = null;

	public Recipes()
	{
		HashMap<Integer, RecipeList> recipeLists = RecipeController.getRecipesList();
		_itemIds = new int[recipeLists.size()];
		int i = 0;
		for(RecipeList rl : recipeLists.values())
		{
			_itemIds[i] = rl.getRecipeItemId();
			i++;
		}
	}

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		L2Player player = (L2Player) playable;

		if(item == null || item.getCount() < 1)
		{
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
			return false;
		}

		RecipeList rp = RecipeController.getRecipeByItemId(item.getItemId());
		if(rp.isCommon() == 0)
		{
			if(player.getDwarvenRecipeLimit() > 0)
			{
				if(player.getDwarvenRecipeBook().size() >= player.getDwarvenRecipeLimit())
				{
					player.sendPacket(Msg.NO_FURTHER_RECIPES_MAY_BE_REGISTERED);
					return false;
				}

				if(rp.getLevel() > player.getSkillLevel(L2Skill.SKILL_CRAFTING))
				{
					player.sendPacket(Msg.CREATE_ITEM_LEVEL_IS_TOO_LOW_TO_REGISTER_THIS_RECIPE);
					return false;
				}
				if(player.findRecipe(rp))
				{
					player.sendPacket(Msg.THAT_RECIPE_IS_ALREADY_REGISTERED);
					return false;
				}
				// add recipe to recipebook
				player.registerRecipe(rp, true);
				player.destroyItem("Consume", item.getObjectId(), 1, null, true);
				player.sendPacket(new SystemMessage(SystemMessage.S1_HAS_BEEN_ADDED).addString(item.getItem().getName()));
			}
			else
				player.sendPacket(Msg.YOU_ARE_NOT_AUTHORIZED_TO_REGISTER_A_RECIPE);
			return false;
		}

		if(player.getCommonRecipeLimit() > 0)
		{
			if(player.getCommonRecipeBook().size() >= player.getCommonRecipeLimit())
			{
				player.sendPacket(Msg.NO_FURTHER_RECIPES_MAY_BE_REGISTERED);
				return false;
			}
			if(player.findRecipe(rp))
			{
				player.sendPacket(Msg.THAT_RECIPE_IS_ALREADY_REGISTERED);
				return false;
			}
			player.registerRecipe(rp, true);
			player.destroyItem("Consume", item.getObjectId(), 1, null, true);
			player.sendPacket(new SystemMessage(SystemMessage.S1_HAS_BEEN_ADDED).addString(item.getItem().getName()));
		}
		else
			player.sendPacket(Msg.YOU_ARE_NOT_AUTHORIZED_TO_REGISTER_A_RECIPE);
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