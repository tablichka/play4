import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.ShowXMasSeal;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SkillTable;

public class ItemHandlers extends Functions
{
	public static L2Object self;

	// Newspaper
	public static void ItemHandler_19999()
	{
		show("data/html/newspaper/00000000.htm", (L2Player) self);
	}

	public static void ItemHandler_5555()
	{
		((L2Player) self).sendPacket(new ShowXMasSeal(5555));
	}

	public static void ItemHandler_8060()
	{
		if(!canBeExtracted(8060))
			return;

		if(Functions.getItemCount((L2Player) self, 8058) > 0)
		{
			removeItem((L2Player) self, 8058, 1);
			addItem((L2Player) self, 8059, 1);
		}
	}

	private final int[] sweet_list = {
			// Sweet Fruit Cocktail
			2404, // Might
			2405, // Shield
			2406, // Wind Walk
			2407, // Focus
			2408, // Death Whisper
			2409, // Guidance
			2410, // Bless Shield
			2411, // Bless Body
			2412, // Haste
			2413, // Vampiric Rage
	};

	// Sweet Fruit Cocktail
	public void ItemHandler_10178()
	{
		L2Player p = (L2Player) self;
		if(p.isInZoneOlympiad())
		{
			p.sendPacket(new SystemMessage(SystemMessage.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		removeItem(p, 10178, 1);
		for(int skill : sweet_list)
		{
			p.broadcastPacket(new MagicSkillUse(p, p, skill, 1, 0, 0));
			p.altOnMagicUseTimer(p, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	private final int[] fresh_list = {
			// Fresh Fruit Cocktail
			2414, // Berserker Spirit
			2411, // Bless Body
			2415, // Magic Barrier
			2405, // Shield
			2406, // Wind Walk
			2416, // Bless Soul
			2417, // Empower
			2418, // Acumen
			2419, // Clarity
	};

	// Fresh Fruit Cocktail
	public void ItemHandler_10179()
	{
		L2Player p = (L2Player) self;
		if(p.isInZoneOlympiad())
		{
			p.sendPacket(new SystemMessage(SystemMessage.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		removeItem(p, 10179, 1);
		for(int skill : fresh_list)
		{
			p.broadcastPacket(new MagicSkillUse(p, p, skill, 1, 0, 0));
			p.altOnMagicUseTimer(p, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Shield Master
	public void ItemHandler_10143()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10143));
			return;
		}
		removeItem((L2Player) self, 10143, 1);
		for(int skill : new int[]{2379, 2380, 2381, 2382, 2383})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Wizard
	public void ItemHandler_10144()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10144));
			return;
		}
		removeItem((L2Player) self, 10144, 1);
		for(int skill : new int[]{2379, 2380, 2381, 2384, 2385})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Healer
	public void ItemHandler_10145()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10145));
			return;
		}

		removeItem((L2Player) self, 10145, 1);
		for(int skill : new int[]{2379, 2380, 2381, 2384, 2386})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Dagger Master
	public void ItemHandler_10146()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10146));
			return;
		}

		removeItem((L2Player) self, 10146, 1);
		for(int skill : new int[]{2379, 2380, 2381, 2388, 2383})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Bow Master
	public void ItemHandler_10147()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10147));
			return;
		}

		removeItem((L2Player) self, 10147, 1);
		for(int skill : new int[]{2379, 2380, 2381, 2389, 2383})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	// Battleground Spell - Berserker
	public void ItemHandler_10148()
	{
		if(!((L2Player) self).isInSiege())
		{
			((L2Player) self).sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(10148));
			return;
		}

		removeItem((L2Player) self, 10148, 1);
		for(int skill : new int[]{2390, 2391})
		{
			((L2Player) self).broadcastPacket(new MagicSkillUse(((L2Player) self), ((L2Player) self), skill, 1, 0, 0));
			((L2Player) self).altOnMagicUseTimer((L2Player) self, SkillTable.getInstance().getInfo(skill, 1), null);
		}
	}

	private static boolean canBeExtracted(int itemId)
	{
		L2Player player = (L2Player) self;
		if(!player.isQuestContinuationPossible())
		{
			player.sendPacket(Msg.YOUR_INVENTORY_IS_FULL);
			player.sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(itemId));
			return false;
		}
		return true;
	}
}