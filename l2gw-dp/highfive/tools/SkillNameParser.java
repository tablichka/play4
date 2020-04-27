package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class SkillNameParser
{
	public static void parse() throws Exception
	{
		File file = new File("skillname-e.txt");
		File out = new File("skillname.sql");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		f.readLine();
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o.writeChars("INSERT INTO `skillname` VALUES ("// 
					+ st[0] // id
					+ "," + st[1] // level
					+ ",'" + st[2].replace("\'", "\'\'") // name
					+ (!st[4].equals("none") ? " (" + st[4].replace("none", "").replace("\'", "\'\'") + ")" : "") // enchant
					+ "','" + st[3].replace("\'", "\'\'") // description 
					+ "');\n");
		}

		f.close();
		o.close();
	}
}