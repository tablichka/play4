package services.multisell;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.MultiSellEntry;
import ru.l2gw.gameserver.model.base.MultiSellHandler;
import ru.l2gw.gameserver.model.base.MultiSellIngredient;
import ru.l2gw.gameserver.model.base.MultiSellListContainer;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.commons.arrays.GArray;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author: rage
 * @date: 22.11.11 0:06
 */
public class BestEquipment extends Functions implements ScriptFile, MultiSellHandler
{
	private static int[] lists;
	private static final String equipment_file = "equipment.xml";
	private static final HashMap<Integer, HashMap<L2Item.Grade, GArray<Integer>>> armor_equipment = new HashMap<>();
	private static final HashMap<Integer, HashMap<L2Item.Grade, GArray<Integer>>> weapon_equipment = new HashMap<>();
	private static final HashMap<L2Item.Grade, long[]> armor_prices = new HashMap<>(4);
	private static final HashMap<L2Item.Grade, long[]> weapon_prices = new HashMap<>(4);

	public void onLoad()
	{
		_log.info("BestEquipment: service loaded.");
		load();
		L2Multisell.getInstance().registerMultiSellHandler(this);
	}

	public void onReload()
	{
		L2Multisell.getInstance().unregisterMultiSellHandler(this);
	}

	public void onShutdown()
	{
	}

	@Override
	public int[] getMultiSellId()
	{
		return lists;
	}

	@Override
	public MultiSellListContainer generateMultiSellList(int listId, L2Player player, double taxRate)
	{
		MultiSellListContainer list = new MultiSellListContainer();
		list.setListId(listId);
		list.setShowAll(true);
		list.setKeepEnchant(false);
		list.setNoTax(true);
		list.community = true;

		try
		{
			if(armor_equipment.containsKey((int) player.getActiveClass()))
			{
				L2Item.Grade grade = L2Item.Grade.values()[listId - 999900];
				GArray<Integer> armorSet = armor_equipment.get((int) player.getActiveClass()).get(grade);
				GArray<Integer> weaponSet = null;
				if(weapon_equipment.containsKey((int) player.getActiveClass()))
					weaponSet = weapon_equipment.get((int) player.getActiveClass()).get(grade);

				long[] armorPrice = armor_prices.get(grade);
				long[] weaponPrice = weapon_prices.get(grade);

				if(armorSet == null && weaponSet == null || (armorSet != null && armorPrice == null || weaponSet != null && weaponPrice == null))
					return null;

				if(armorSet != null)
				{
					for(Integer itemId : armorSet)
					{
						final int entry = new int[]{(int) armorPrice[0], itemId, 0}.hashCode();
						MultiSellEntry possibleEntry = new MultiSellEntry(entry, itemId, 1, null);
						possibleEntry.addIngredient(new MultiSellIngredient((int) armorPrice[0], armorPrice[1], null));
						list.entries.add(possibleEntry);
					}
				}
				if(weaponSet != null)
				{
					for(Integer itemId : weaponSet)
					{
						final int entry = new int[]{(int) weaponPrice[0], itemId, 0}.hashCode();
						MultiSellEntry possibleEntry = new MultiSellEntry(entry, itemId, 1, null);
						possibleEntry.addIngredient(new MultiSellIngredient((int) weaponPrice[0], weaponPrice[1], null));
						list.entries.add(possibleEntry);
					}
				}
			}
		}
		catch(Exception e)
		{
		}
		return list;
	}

	private static void load()
	{
		armor_equipment.clear();
		weapon_equipment.clear();
		armor_prices.clear();
		weapon_prices.clear();
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);

			File file = new File("data/scripts/services/multisell/" + equipment_file);

			if(!file.exists())
			{
				_log.info("BestEquipment: file " + equipment_file + " not found.");
				return;
			}

			Document doc = factory.newDocumentBuilder().parse(file);
			for(Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
				if("list".equalsIgnoreCase(n.getNodeName()))
				{
					for(Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						if("category".equalsIgnoreCase(d.getNodeName()))
						{
							HashMap<L2Item.Grade, GArray<Integer>> armors = new HashMap<>();
							HashMap<L2Item.Grade, GArray<Integer>> weapons = new HashMap<>();
							for(Node c = d.getFirstChild(); c != null; c = c.getNextSibling())
							{
								if("armor".equalsIgnoreCase(c.getNodeName()))
								{
									L2Item.Grade grade = L2Item.Grade.valueOf(c.getAttributes().getNamedItem("grade").getNodeValue());
									StringTokenizer st = new StringTokenizer(c.getFirstChild().getNodeValue());
									GArray<Integer> set = new GArray<>(st.countTokens());
									while(st.hasMoreTokens())
									{
										set.add(Integer.parseInt(st.nextToken()));
									}
									armors.put(grade, set);
								}
								else if("weapon".equalsIgnoreCase(c.getNodeName()))
								{
									L2Item.Grade grade = L2Item.Grade.valueOf(c.getAttributes().getNamedItem("grade").getNodeValue());
									StringTokenizer st = new StringTokenizer(c.getFirstChild().getNodeValue());
									GArray<Integer> set = new GArray<>(st.countTokens());
									while(st.hasMoreTokens())
									{
										set.add(Integer.parseInt(st.nextToken()));
									}
									weapons.put(grade, set);
								}
							}

							String[] classes = d.getAttributes().getNamedItem("id").getNodeValue().split(",");
							for(String cl : classes)
								if(cl != null && !cl.isEmpty())
								{
									armor_equipment.put(Integer.parseInt(cl), armors);
									weapon_equipment.put(Integer.parseInt(cl), weapons);
								}
						}
						else if("armorPrice".equalsIgnoreCase(d.getNodeName()))
						{
							L2Item.Grade grade = L2Item.Grade.valueOf(d.getAttributes().getNamedItem("grade").getNodeValue());
							armor_prices.put(grade, new long[]{Integer.parseInt(d.getAttributes().getNamedItem("itemId").getNodeValue()), Long.parseLong(d.getAttributes().getNamedItem("price").getNodeValue())});
						}
						else if("weaponPrice".equalsIgnoreCase(d.getNodeName()))
						{
							L2Item.Grade grade = L2Item.Grade.valueOf(d.getAttributes().getNamedItem("grade").getNodeValue());
							weapon_prices.put(grade, new long[]{Integer.parseInt(d.getAttributes().getNamedItem("itemId").getNodeValue()), Long.parseLong(d.getAttributes().getNamedItem("price").getNodeValue())});
						}
				}

			lists = new int[armor_prices.size()];
			int i = 0;
			for(L2Item.Grade grade : armor_prices.keySet())
			{
				lists[i] = 999900 + grade.ordinal();
				i++;
			}
		}
		catch(Exception e)
		{
			_log.warn("BestEquipment: Error parsing file: " + e);
			e.printStackTrace();
		}
	}
}
