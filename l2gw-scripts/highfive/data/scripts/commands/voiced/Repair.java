package commands.voiced;

import ru.l2gw.database.mysql;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;

import java.util.Map.Entry;

public class Repair extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	private final String[] _commandList = new String[] { "repair" };

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}

	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		show("data/scripts/commands/voiced/repair.htm", activeChar);
		return true;
	}

	public void repair(String[] var)
	{
		if(var.length == 1)
		{
			String name = var[0];
			L2Player activeChar = (L2Player) self;
			if(!activeChar.getAccountChars().containsValue(name))
			{
				show("You can't repair character not on same account!", activeChar);
				return;
			}
			if(activeChar.getName().equalsIgnoreCase(name))
			{
				show("You can't repair yourself!", activeChar);
				return;
			}
			for(Entry<Integer, String> entry : activeChar.getAccountChars().entrySet())
			{
				int obj_id = entry.getKey();
				String char_name = entry.getValue();
				if(!name.equalsIgnoreCase(char_name))
					continue;
				long jail = mysql.simple_get_long("value", "character_variables", "`obj_id`=" + obj_id + " AND name='jailed'");
				if(jail > 0)
				{
					show("You can't repair jailed character", activeChar);
					return;
				}
				int item_id = mysql.simple_get_int("item_id", "cursed_weapons", "`player_id`=" + obj_id);
				if(item_id > 0)
				{
					show("You can't repair character with cursed weapon!", activeChar);
					return;
				}
				int karma = mysql.simple_get_int("karma", "characters", "`obj_Id`=" + obj_id);
				if(karma > 0)
					mysql.set("UPDATE `characters` SET `x`='17144', `y`='170156', `z`='-3502', `heading`='0' WHERE `obj_Id`='" + obj_id + "' LIMIT 1");
				else
				{
					mysql.set("UPDATE `characters` SET `x`='0', `y`='0', `z`='0', `heading`='0' WHERE `obj_Id`='" + obj_id + "' LIMIT 1");
					mysql.set("UPDATE `items` SET `loc`='WAREHOUSE', loc_data=0 WHERE `loc`='PAPERDOLL' AND `owner_id`=" + obj_id);
				}
				mysql.set("DELETE FROM `character_effects_save` WHERE `char_obj_id`=" + obj_id);
				show("Sucessfully repaired. All inventory moved to warehouse.", activeChar);
				break;
			}
		}
	}

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}