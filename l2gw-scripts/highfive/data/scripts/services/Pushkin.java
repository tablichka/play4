package services;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;

public class Pushkin extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;

	public static String DialogAppend_30300(Integer val)
	{
		if(val != 0 || !Config.ALT_SIMPLE_SIGNS || self == null)
			return "";

		StringBuilder append = new StringBuilder();

		if(((L2Player) self).getVar("lang@").equalsIgnoreCase("ru"))
		{
			append.append("<br><center>Опции семи печатей:</center><br>");
			append.append("<center>[npc_%objectId%_Multisell 10061|Сделать S-грейд меч]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 40011|Вставить SA в оружие S-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1008|Распечатать броню S-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 10091|Распечатать бижутерию S-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1006|Сделать A-грейд меч]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 4001|Вставить SA в оружие A-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1005|Распечатать броню A-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1009|Распечатать бижутерию A-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1007|Запечатать броню A-грейда]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 4002|Удалить SA из оружия]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 311262511|Обменять оружие с доплатой]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 311262512|Обменять оружие на равноценное]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 501|Купить что-нибудь]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 400|Обменять камни]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 500|Приобрести расходные материалы]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1010|Обработать Базовую вещь]</center><br1>");
			//append.append("<center>[npc_%objectId%_Multisell 9997|Кристаллизация]</center>");
		}
		else
		{
			append.append("<br><center>Seven Signs options:</center><br>");
			append.append("<center>[npc_%objectId%_Multisell 10061|Manufacture an S-grade sword]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 40011|Bestow the special S-grade weapon some abilities]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1008|Release the S-grade armor seal]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 10091|Release the S-grade accessory seal]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1006|Manufacture an A-grade sword]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 4001|Bestow the special A-grade weapon some abilities]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1005|Release the A-grade armor seal]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1009|Release the A-grade accessory seal]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1007|Seal the A-grade armor again]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 4002|Remove the special abilities from a weapon]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 311262511|Upgrade weapon]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 311262512|Make an even exchange of weapons]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 501|Buy Something]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 400|Exchange Seal Stones]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 500|Purchase consumable items]</center><br1>");
			append.append("<center>[npc_%objectId%_Multisell 1010|Complete a Foundation Item]</center><br1>");
			//append.append("<center>[npc_%objectId%_Multisell 9997|Crystallize]</center>");
		}
		return append.toString();
	}

	public static String DialogAppend_30086(Integer val)
	{
		return DialogAppend_30300(val);
	}

	public static String DialogAppend_30098(Integer val)
	{
		if(val != 0 || !Config.ALT_ALLOW_TATTOO)
			return "";

		if(((L2Player) self).getVar("lang@").equalsIgnoreCase("ru"))
			return "<br>[npc_%objectId%_Multisell 6500|Купить тату]";
		return "<br>[npc_%objectId%_Multisell 6500|Buy tattoo]";
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Pushkin");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}
}