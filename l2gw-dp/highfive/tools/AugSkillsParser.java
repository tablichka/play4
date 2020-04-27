package main;

import java.io.File;
import java.io.RandomAccessFile;

public abstract class AugSkillsParser
{
	public static void parse() throws Exception
	{
		File file = new File("skills_client.txt");
		File out = new File("augmentation_skillmap.xml");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o.writeChars("	<augmentation id=\"" + st[0] + "\">\n");
			o.writeChars("		<!--" + st[3].substring(2, st[3].length() - 2) + "-->\n");
			o.writeChars("		<skillId val=\"-1\" />\n");
			if(st[3].startsWith("a,Active:"))
				o.writeChars("		<type val=\"active\" />\n");
			else if(st[3].startsWith("a,Chance:"))
				o.writeChars("		<type val=\"chance\" />\n");
			else if(st[3].startsWith("a,Passive:"))
				o.writeChars("		<type val=\"passive\" />\n");
			o.writeChars("	</augmentation>\n");
		}

		f.close();
		o.close();
	}
}