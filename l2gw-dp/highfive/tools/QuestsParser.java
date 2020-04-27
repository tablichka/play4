package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class QuestsParser
{
	public static void parse() throws Exception
	{
		File file = new File("questname-e.txt");
		File out1 = new File("questname.sql");
		out1.delete();
		out1.createNewFile();
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o1 = new RandomAccessFile(out1, "rw");
		f.readLine(); // skip header
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o1.writeChars("INSERT INTO `quests` VALUES ('"// 
					+ st[1].replace("\'", "\'\'") + "','" // id
					+ st[2].replace("\'", "\'\'") + "','" // cond
					+ st[3].replace("\'", "\'\'") + "','" // q name
					+ st[25].replace("\'", "\'\'") + "','" // short_description
					+ st[4].replace("\'", "\'\'") + "','" // cond name
					+ st[5].replace("\'", "\'\'") + "','" // cond desc
					+ st[6].replace("\'", "\'\'") + "','" // items_cntr
					+ st[7].replace("\'", "\'\'") + "','" // items_text
					+ st[8].replace("\'", "\'\'") + "','" // num_items_cntr
					+ st[9].replace("\'", "\'\'") + "','" // num_items_text
					+ st[10].replace("\'", "\'\'") + "','" // x
					+ st[11].replace("\'", "\'\'") + "','" // y
					+ st[12].replace("\'", "\'\'") + "','" // z
					+ st[20].replace("\'", "\'\'") + "','" // contact_npc_id
					+ st[21].replace("\'", "\'\'") + "','" // contact_npc_x
					+ st[22].replace("\'", "\'\'") + "','" // contact_npc_y
					+ st[23].replace("\'", "\'\'") + "','" // contact_npc_z
					+ st[13].replace("\'", "\'\'") + "','" // lvl min
					+ st[14].replace("\'", "\'\'") + "','" // lvl max
					+ st[31].replace("\'", "\'\'") // area_id
					/*
					+ "','" 
					+ st[15].replace("\'", "\'\'") + "','" // quest_type
					+ st[16].replace("\'", "\'\'") + "','" // entity_name
					+ st[17].replace("\'", "\'\'") + "','" // get_item_in_quest
					+ st[22].replace("\'", "\'\'") + "','" // restricions
					+ st[24].replace("\'", "\'\'") + "','" // req_class_cntr
					+ st[25].replace("\'", "\'\'") + "','" // req_class_text
					+ st[26].replace("\'", "\'\'") + "','" // req_item_cntr
					+ st[27].replace("\'", "\'\'") + "','" // req_item_text
					+ st[28].replace("\'", "\'\'") + "','" // clan_pet_quest
					+ st[29].replace("\'", "\'\'") + "','" // req_quest_complete
					*/
					+ "');\n");
		}

		f.close();
		o1.close();
	}
}