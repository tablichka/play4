package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class RecipeParser
{
	public static void parse() throws Exception
	{
		File file = new File("recipes.csv");
		File out1 = new File("recipes.sql");
		File out2 = new File("recitems.sql");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o1 = new RandomAccessFile(out1, "rw");
		RandomAccessFile o2 = new RandomAccessFile(out2, "rw");
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			//o1.writeChars("INSERT IGNORE INTO `recipes` VALUES (" + st[1] + ",'" + st[0].replace("\'", "\'\'") + "'," + st[4] + "," + st[5] + "," + st[3] + "," + st[7] + "," + st[2] + "," + st[6] + ",0,0,1);\n");
			o1.writeChars("UPDATE `recipes` SET item=" + st[4] + ",q=" + st[5] + ",lvl=" + st[3] + ",success=" + st[7] + ",recid=" + st[2] + ",mp=" + st[6] + " WHERE `id`=" + st[1] + ";\n");
			/*int x = 9;
			for(int i = 0; i < Integer.parseInt(st[8]); i++)
				o2.writeChars("INSERT INTO `recitems` VALUES (" + st[1] + "," + st[i + x] + "," + st[i + ++x] + ",0);\n");
			*/
		}

		f.close();
		o1.close();
		o2.close();
	}
}