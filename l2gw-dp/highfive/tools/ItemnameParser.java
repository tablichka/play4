package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class ItemnameParser
{
	public static void parse() throws Exception
	{
		File file = new File("itemname-e.csv");
		File out = new File("itemname-e.sql");
		out.delete();
		File sets = new File("sets.sql");
		sets.delete();
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		RandomAccessFile t = new RandomAccessFile(sets, "rw");
		f.readLine();
		String s;
		int i = 0;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			for(int k = 0; k < st.length; k++)
				st[k] = st[k].replace("'", "''");
			o.writeChars("INSERT INTO `itemname` VALUES ('" //
					+ st[0] // id
					+ "','" + st[1] // name 
					+ "','" + st[2] // add_name
					+ "','" + st[3] // desc 
					+ "','" + st[4] // popup (?)
					+ "','" + st[5] // set_ids
					+ "','" + st[6] // set_bonus_desc
					+ "','" + st[7] // set_extra_id
					+ "','" + st[8] // set_extra_desc
					+ "','" + st[9] // UNK0_1
					+ "','" + st[10] // UNK0_2
					+ "','" + st[11] // special_enchant_amount
					+ "','" + (st.length > 12 ? st[12] : "") // special_enchant_desc
					+ "');\n");

			if(st[5].length() > 0)
			{
				String[] set = st[5].split(",");
				t.writeChars("INSERT IGNORE INTO `l2jz_sets` VALUES (" + ++i + ",'" + st[1] + " Set','" + st[6] + "',0,0);\n");
				t.writeChars("INSERT IGNORE INTO `l2jz_setitems` VALUES (" + i + "," + st[0] + ");\n");
				for(String si : set)
					t.writeChars("INSERT IGNORE INTO `l2jz_setitems` VALUES (" + i + "," + si.replace("\"", "") + ");\n");
				if(st[7].length() > 0)
					t.writeChars("INSERT IGNORE INTO `l2jz_setitems` VALUES (" + i + "," + st[7] + ");\n");
			}
		}

		t.close();
		f.close();
		o.close();
	}

	public static void parseWeaponGrp() throws Exception
	{
		File file = new File("weapongrp.csv");
		File out = new File("weapongrp.sql");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		f.readLine();
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o.writeChars(//
			"UPDATE `weapon` SET icon='" + st[13].replace("\'", "\'\'").replace("icon.", "") // T1
					+ "',durability='" + st[18] // T1
					+ "',weight='" + st[19] // T1 
					+ "',crystallizable='" + st[21].replace("1", "true").replace("0", "false") // T1
					//+ "',rnd_dam='" + st[38] // random damage
					//+ "',soulshots='" + st[41] //
					//+ "',crystal_type='" + st[42].replace("0", "none").replace("1", "d").replace("2", "c").replace("3", "b").replace("4", "a").replace("5", "s") // grade
					//+ "',critical='" + st[43] // critical
					//+ "',atk_speed='" + st[51] // T1
					//+ "',mp_consume='" + st[49] // T1
					//+ "',shield_def='" + st[50] // T1
					+ "' WHERE item_id='" + st[1] + "';\n");
		}

		f.close();
		o.close();
	}
}