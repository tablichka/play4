package events.CofferofShadows;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Drop;
import ru.l2gw.gameserver.model.L2DropData;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.ItemTable;

public class Coffer implements IItemHandler, ScriptFile
{
	// Дроп для эвентого сундука Coffer of Shadows
	private static final int[] _itemIds = { 8659 };

	protected static final L2DropData[] _dropmats = new L2DropData[] {
	//                                     Item                      Chance
			// Материалы
			new L2DropData(ItemTable.getInstance().getTemplate(4041), 1, 1, 250, 1), // Mold Hardener         0.025%
			new L2DropData(ItemTable.getInstance().getTemplate(4042), 1, 1, 450, 1), // Enria                 0.045%
			new L2DropData(ItemTable.getInstance().getTemplate(4040), 1, 1, 500, 1), // Mold Lubricant        0.05%
			new L2DropData(ItemTable.getInstance().getTemplate(1890), 1, 3, 833, 1), // Mithril Alloy         0.0833%
			new L2DropData(ItemTable.getInstance().getTemplate(5550), 1, 3, 833, 1), // Durable Metal Plate   0.0833%
			new L2DropData(ItemTable.getInstance().getTemplate(4039), 1, 1, 833, 1), // Mold Glue             0.0833%
			new L2DropData(ItemTable.getInstance().getTemplate(4043), 1, 1, 833, 1), // Asofe                 0.0833%
			new L2DropData(ItemTable.getInstance().getTemplate(4044), 1, 1, 833, 1), // Thons                 0.0833%
			new L2DropData(ItemTable.getInstance().getTemplate(1888), 1, 3, 1000, 1), // Synthetic Cokes      0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(1877), 1, 3, 1000, 1), // Adamantite Nugget    0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(1894), 1, 3, 3000, 1), // Crafted Leather      0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(1874), 1, 5, 3000, 1), // Oriharukon Ore       0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(1875), 1, 5, 3000, 1), // Stone of Purity      0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(1887), 1, 3, 3000, 1), // Varnish of Purity    0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(1866), 1, 10, 16666, 1), // Suede              1.6666%
			new L2DropData(ItemTable.getInstance().getTemplate(1882), 1, 10, 16666, 1), // Leather            1.6666%
			new L2DropData(ItemTable.getInstance().getTemplate(1881), 1, 10, 10000, 1), // Coarse Bone Powder 1%
			new L2DropData(ItemTable.getInstance().getTemplate(1873), 1, 10, 10000, 1), // Silver Nugget      1%
			new L2DropData(ItemTable.getInstance().getTemplate(1879), 1, 5, 10000, 1), // Cokes               1%
			new L2DropData(ItemTable.getInstance().getTemplate(1880), 1, 5, 10000, 1), // Steel               1%
			new L2DropData(ItemTable.getInstance().getTemplate(1876), 1, 5, 10000, 1), // Mithril Ore         1%
			new L2DropData(ItemTable.getInstance().getTemplate(1864), 1, 20, 25000, 1), // Stem               2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1865), 1, 20, 25000, 1), // Varnish            2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1868), 1, 15, 25000, 1), // Thread             2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1869), 1, 15, 25000, 1), // Iron Ore           2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1870), 1, 15, 25000, 1), // Coal               2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1871), 1, 15, 25000, 1), // Charcoal           2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(1872), 1, 20, 30000, 1), // Animal Bone        3%
			new L2DropData(ItemTable.getInstance().getTemplate(1867), 1, 20, 33333, 1), // Animal Skin        3.3333%
	};

	protected static final L2DropData[] _dropacc = new L2DropData[] {
	// Аксессуары и сувениры
			new L2DropData(ItemTable.getInstance().getTemplate(8660), 1, 1, 1000, 1), // Demon Horns        0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(8661), 1, 1, 1000, 1), // Mask of Spirits    0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(4393), 1, 1, 300, 1), // Calculator          0.03%
			new L2DropData(ItemTable.getInstance().getTemplate(5590), 1, 1, 200, 1), // Squeaking Shoes     0.02%
			new L2DropData(ItemTable.getInstance().getTemplate(7058), 1, 1, 50, 1), // Chrono Darbuka       0.005%
			new L2DropData(ItemTable.getInstance().getTemplate(8350), 1, 1, 50, 1), // Chrono Maracas       0.005%
			new L2DropData(ItemTable.getInstance().getTemplate(5133), 1, 1, 50, 1), // Chrono Unitus        0.005%
			new L2DropData(ItemTable.getInstance().getTemplate(5817), 1, 1, 50, 1), // Chrono Campana       0.005%
			new L2DropData(ItemTable.getInstance().getTemplate(9140), 1, 1, 30, 1), // Salvation Bow        0.003%
			// Призрачные аксессуары - шанс 0.01%
			new L2DropData(ItemTable.getInstance().getTemplate(9177), 1, 1, 100, 1), // Teddy Bear Hat - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9178), 1, 1, 100, 1), // Piggy Hat - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9179), 1, 1, 100, 1), // Jester Hat - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9180), 1, 1, 100, 1), // Wizard's Hat - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9181), 1, 1, 100, 1), // Dapper Cap - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9182), 1, 1, 100, 1), // Romantic Chapeau - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9183), 1, 1, 100, 1), // Iron Circlet - Blessed Resurrection Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9184), 1, 1, 100, 1), // Teddy Bear Hat - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9185), 1, 1, 100, 1), // Piggy Hat - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9186), 1, 1, 100, 1), // Jester Hat - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9187), 1, 1, 100, 1), // Wizard's Hat - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9188), 1, 1, 100, 1), // Dapper Cap - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9189), 1, 1, 100, 1), // Romantic Chapeau - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9190), 1, 1, 100, 1), // Iron Circlet - Blessed Escape Effect
			new L2DropData(ItemTable.getInstance().getTemplate(9191), 1, 1, 100, 1), // Teddy Bear Hat - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9192), 1, 1, 100, 1), // Piggy Hat - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9193), 1, 1, 100, 1), // Jester Hat - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9194), 1, 1, 100, 1), // Wizard Hat - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9195), 1, 1, 100, 1), // Dapper Hat - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9196), 1, 1, 100, 1), // Romantic Chapeau - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9197), 1, 1, 100, 1), // Iron Circlet - Big Head
			new L2DropData(ItemTable.getInstance().getTemplate(9198), 1, 1, 100, 1), // Teddy Bear Hat - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9199), 1, 1, 100, 1), // Piggy Hat - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9200), 1, 1, 100, 1), // Jester Hat - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9201), 1, 1, 100, 1), // Wizard's Hat - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9202), 1, 1, 100, 1), // Dapper Hat - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9203), 1, 1, 100, 1), // Romantic Chapeau - Firework
			new L2DropData(ItemTable.getInstance().getTemplate(9204), 1, 1, 100, 1) // Iron Circlet - Firework
	};

	protected static final L2DropData[] _dropevents = new L2DropData[] {
	// Эвентовые скролы
			new L2DropData(ItemTable.getInstance().getTemplate(9146), 1, 1, 3000, 1), // Scroll of Guidance        0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9147), 1, 1, 3000, 1), // Scroll of Death Whisper   0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9148), 1, 1, 3000, 1), // Scroll of Focus           0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9149), 1, 1, 3000, 1), // Scroll of Acumen          0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9150), 1, 1, 3000, 1), // Scroll of Haste           0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9151), 1, 1, 3000, 1), // Scroll of Agility         0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9152), 1, 1, 3000, 1), // Scroll of Empower         0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9153), 1, 1, 3000, 1), // Scroll of Might           0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9154), 1, 1, 3000, 1), // Scroll of Wind Walk       0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9155), 1, 1, 3000, 1), // Scroll of Shield          0.3%
			new L2DropData(ItemTable.getInstance().getTemplate(9156), 1, 1, 2000, 1), // BSoE                      0.2%
			new L2DropData(ItemTable.getInstance().getTemplate(9157), 1, 1, 1000, 1), // BRES                      0.1%

			// Хлам
			new L2DropData(ItemTable.getInstance().getTemplate(5234), 1, 5, 25000, 1), // Mystery Potion           2.5%
			new L2DropData(ItemTable.getInstance().getTemplate(7609), 50, 100, 12000, 1), // Proof of Catching a Fish 1.2%
			new L2DropData(ItemTable.getInstance().getTemplate(7562), 2, 4, 1000, 1), // Dimensional Diamond       0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(6415), 1, 3, 1000, 1), // Ugly Green Fish :)        0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(1461), 1, 3, 5000, 1), // Crystal: A-Grade          0.5%
			new L2DropData(ItemTable.getInstance().getTemplate(6406), 1, 3, 10000, 1), // Firework                 1%
			new L2DropData(ItemTable.getInstance().getTemplate(6407), 1, 1, 10000, 1), // Large Firework           1%
			new L2DropData(ItemTable.getInstance().getTemplate(6403), 1, 5, 10000, 1), // Star Shard               1%
			new L2DropData(ItemTable.getInstance().getTemplate(6036), 1, 5, 10000, 1), // GMHP                     1%
			new L2DropData(ItemTable.getInstance().getTemplate(5595), 1, 1, 10000, 1), // SP Scroll: High Grade    1%
			new L2DropData(ItemTable.getInstance().getTemplate(1374), 1, 5, 10000, 1), // GHP                      1%
			new L2DropData(ItemTable.getInstance().getTemplate(1375), 1, 5, 10000, 1), // GSAP                     1%
			new L2DropData(ItemTable.getInstance().getTemplate(1540), 1, 3, 10000, 1), // Quick Healing Potion     1%
			new L2DropData(ItemTable.getInstance().getTemplate(5126), 1, 1, 1000, 1) // Dualsword Craft Stamp      0.1%
	};

	protected static final L2DropData[] _dropench = new L2DropData[] {
	// Заточки
			new L2DropData(ItemTable.getInstance().getTemplate(955), 1, 1, 400, 1), // EWD          0.04%
			new L2DropData(ItemTable.getInstance().getTemplate(956), 1, 1, 2000, 1), // EAD         0.2%
			new L2DropData(ItemTable.getInstance().getTemplate(951), 1, 1, 300, 1), // EWC          0.03%
			new L2DropData(ItemTable.getInstance().getTemplate(952), 1, 1, 1500, 1), // EAC         0.15%
			new L2DropData(ItemTable.getInstance().getTemplate(947), 1, 1, 200, 1), // EWB          0.02%
			new L2DropData(ItemTable.getInstance().getTemplate(948), 1, 1, 1000, 1), // EAB         0.1%
			new L2DropData(ItemTable.getInstance().getTemplate(729), 1, 1, 100, 1), // EWA          0.01%
			new L2DropData(ItemTable.getInstance().getTemplate(730), 1, 1, 500, 1), // EAA          0.05%
			new L2DropData(ItemTable.getInstance().getTemplate(959), 1, 1, 50, 1), // EWS           0.005%
			new L2DropData(ItemTable.getInstance().getTemplate(960), 1, 1, 300, 1), // EAS          0.03%

			// Soul Cry 11, 12 lvl
			new L2DropData(ItemTable.getInstance().getTemplate(5577), 1, 1, 30, 1), // Red 11         0.003%
			new L2DropData(ItemTable.getInstance().getTemplate(5578), 1, 1, 30, 1), // Green 11       0.003%
			new L2DropData(ItemTable.getInstance().getTemplate(5579), 1, 1, 30, 1), // Blue 11        0.003%
			new L2DropData(ItemTable.getInstance().getTemplate(5580), 1, 1, 20, 1), // Red 12         0.002%
			new L2DropData(ItemTable.getInstance().getTemplate(5581), 1, 1, 20, 1), // Green 12       0.002%
			new L2DropData(ItemTable.getInstance().getTemplate(5582), 1, 1, 20, 1) // Blue 12         0.002%
	};

	public synchronized boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!playable.isPlayer())
			return false;
		L2Player activeChar = playable.getPlayer();

		if(!activeChar.isQuestContinuationPossible())
			return false;

		activeChar.destroyItem("Consume", item.getObjectId(), 1, null, true);
		getGroupItem(activeChar, _dropmats);
		getGroupItem(activeChar, _dropacc);
		getGroupItem(activeChar, _dropevents);
		getGroupItem(activeChar, _dropench);
		return true;
	}

	/*
	* Выбирает 1 предмет из группы
	*/
	public void getGroupItem(L2Player activeChar, L2DropData[] dropData)
	{
		int count = 0;
		for(L2DropData d : dropData)
			if(Rnd.get(1, L2Drop.MAX_CHANCE) <= d.getChance() * Config.EVENT_CofferOfShadowsRewardRate)
			{
				count = Rnd.get(d.getMinDrop(), d.getMaxDrop());
				activeChar.addItem("CofferofShadows", d.getItemId(), count, activeChar, true);
				return;
			}
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