package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class NpcParser
{
	public static void parse() throws Exception
	{
		File file = new File("npcname-e.csv");
		File out = new File("npcupd.sql");
		File add = new File("npcadd.sql");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		RandomAccessFile a = new RandomAccessFile(add, "rw");
		f.readLine();
		String s;
		int i = 0;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o.writeChars("UPDATE `npc` SET `ordinal`='" + ++i + "',`name`='" + st[1].replace("\'", "\'\'").trim() + "',`title`='" + st[2].replace("\'", "\'\'").trim() + "' WHERE `id`='" + st[0] + "';\n");
			a.writeChars("INSERT INTO `npcn` VALUES ('" + ++i + "','" + st[0] + "','" + st[1].replace("\'", "\'\'").trim() + "','" + st[2].replace("\'", "\'\'").trim() + "');\n");
		}

		f.close();
		o.close();
	}
}