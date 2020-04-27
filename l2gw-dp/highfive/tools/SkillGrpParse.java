package main;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

public class SkillGrpParse
{
	static SkillGrpParse _this = new SkillGrpParse();

	public static void parse() throws Exception
	{
		File file = new File("skillgrp.txt");
		File out = new File("skillgrp.sql");
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		f.readLine(); // пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
		String s;
		while((s = f.readLine()) != null)
		{
			String[] st = s.split("	");
			o.writeChars("INSERT INTO `skillgrp` VALUES ("//
					+ st[0]// id
					+ "," + st[1] // level 
					+ "," + st[2] // optype 
					+ ",'" + st[3] // mp
					+ "','" + st[4] // range
					+ "','" + st[7] // is magic
					+ "','" + st[10].substring(5) // icon
					+ "');\n");
		}

		f.close();
		o.close();
	}

	public static void parseXML() throws Exception
	{
		long time = System.currentTimeMillis();
		File file = new File("skillgrp.txt");
		File file2 = new File("skillname-e.txt");
		File out = new File("templates.xml");
		out.delete();
		out.createNewFile();
		RandomAccessFile f = new RandomAccessFile(file, "r");
		RandomAccessFile f2 = new RandomAccessFile(file2, "r");
		RandomAccessFile o = new RandomAccessFile(out, "rw");
		f.readLine();
		f2.readLine();
		String s;
		ArrayList<String[]> skillgrp = new ArrayList<String[]>();
		ArrayList<String[]> skillname = new ArrayList<String[]>();
		while((s = f.readLine()) != null)
			skillgrp.add(s.split("	"));
		while((s = f2.readLine()) != null)
			skillname.add(s.split("	"));

		HashMap<Integer, Skill> skills = new HashMap<Integer, Skill>();
		for(String[] sa : skillname)
		{
			if(sa[2].equalsIgnoreCase("NoSkillName"))
				continue;
			int id = Integer.parseInt(sa[0]);
			//			if(!(id >= 470 && id <= 498 || id >= 500 && id <= 537 || id >= 1418 && id <= 1486))
			//				continue;
			Skill sk = skills.get(Integer.parseInt(sa[0]));
			if(sk == null)
			{
				sk = _this.new Skill();
				skills.put(id, sk);
				sk.id = id;
				sk.name = sa[2];
			}
			sk.levels++;
			try
			{
				sk.desc[Integer.parseInt(sa[1])] = sa[3];
			}
			catch(Exception e)
			{
				System.out.println(sa[0] + " " + sa[1] + " " + sa[2]);
				Runtime.getRuntime().halt(0);
			}
			if(sa[3].contains(". Power "))
			{
				String[] t1 = sa[3].replace(".", ",").split(", Power ");
				if(t1.length > 1)
				{
					if(t1[1].startsWith("is"))
						if(t1.length > 2)
							t1[1] = t1[2];
						else
							continue;

					if(t1[1].contains(","))
						try
						{
							sk.power[Integer.parseInt(sa[1])] = Integer.parseInt(t1[1].replace(".", ",").split(",")[0].trim());
						}
						catch(Exception e)
						{}
					else
						try
						{
							sk.power[Integer.parseInt(sa[1])] = Integer.parseInt(t1[1].replace(".", ",").split(",")[0].trim());
						}
						catch(Exception e)
						{}
				}
			}
			else if(sa[3].contains(".  Power "))
			{
				String[] t1 = sa[3].replace(".", ",").split(",  Power ");
				if(t1.length > 1)
				{
					if(t1[1].startsWith("is"))
						if(t1.length > 2)
							t1[1] = t1[2];
						else
							continue;

					if(t1[1].contains(","))
						try
						{
							sk.power[Integer.parseInt(sa[1])] = Integer.parseInt(t1[1].replace(".", ",").split(",")[0].trim());
						}
						catch(Exception e)
						{}
					else
						try
						{
							sk.power[Integer.parseInt(sa[1])] = Integer.parseInt(t1[1].replace(".", ",").split(",")[0].trim());
						}
						catch(Exception e)
						{}
				}
			}
		}

		for(String[] sa : skillgrp)
		{
			Skill sk = skills.get(Integer.parseInt(sa[0]));
			if(sk == null)
				continue;

			//			if(sk == null || !(sk.id >= 470 && sk.id <= 498 || sk.id >= 500 && sk.id <= 537 || sk.id >= 1418 && sk.id <= 1486))
			//				continue;

			int level = Integer.parseInt(sa[1]);

			sk.optype = Integer.parseInt(sa[2]);
			sk.magic = sa[7].equals("1") || sa[7].equals("3");

			sk.range[level] = Integer.parseInt(sa[4]);
			sk.time = (int) Double.parseDouble(sa[6].replace("\"", "").replace(",", ".")) * 1000;
			sk.mp[level] = Integer.parseInt(sa[3]);
		}

		System.out.println("parsed " + skills.size());

		for(Skill sk : skills.values())
		{
			o.writeChars("	<skill id=\"" + sk.id + "\" levels=\"" + sk.levels + "\" name=\"" + sk.name + "\">\n");
			o.writeChars("		<!-- Auto parser\n");
			for(int i = 1; i < 1000; i++)
			{
				if(sk.desc[i] == null)
					continue;
				o.writeChars("			Level " + i + ": " + sk.desc[i] + "\n");
			}
			o.writeChars("		-->\n");

			if(sk.mp[1] != null && sk.mp[1] != 0)
			{
				o.writeChars("		<table name=\"#mpConsume2\">");
				for(int i = 1; i < 1000; i++)
				{
					if(sk.mp[i] == null)
						continue;
					if(i > 1)
						o.writeChars(" " + sk.mp[i]);
					else
						o.writeChars(String.valueOf(sk.mp[i]));
				}
				o.writeChars("</table>\n");
				o.writeChars("		<set name=\"mpConsume2\" val=\"#mpConsume2\" />\n");
			}

			if(sk.power[1] != null && sk.power[1] != 0)
			{
				o.writeChars("		<table name=\"#power\">");
				for(int i = 1; i < 1000; i++)
				{
					if(sk.power[i] == null)
						continue;
					if(i > 1)
						o.writeChars(" " + sk.power[i]);
					else
						o.writeChars(String.valueOf(sk.power[i]));
				}
				o.writeChars("</table>\n");
				o.writeChars("		<set name=\"power\" val=\"#power\" />\n");
			}

			if(sk.range[1] != null && sk.range[1] > 0)
			{
				boolean razbros = false;
				for(int i = 1; i < 1000; i++)
				{
					if(sk.range[i] != null && sk.range[i] > 0 && !sk.range[i].equals(sk.range[1]))
					{
						razbros = true;
						break;
					}
				}
				if(razbros)
				{
					o.writeChars("		<table name=\"#castRange\">");
					for(int i = 1; i < 1000; i++)
					{
						if(sk.range[i] == null)
							continue;
						if(i > 1)
							o.writeChars(" " + sk.range[i]);
						else
							o.writeChars(String.valueOf(sk.range[i]));
					}
					o.writeChars("</table>\n");
					o.writeChars("		<set name=\"castRange\" val=\"#castRange\" />\n");
				}
				else
					o.writeChars("		<set name=\"castRange\" val=\"" + sk.range[1] + "\" />\n");
			}

			if(sk.magic)
				o.writeChars("		<set name=\"isMagic\" val=\"true\" />\n");
			if(sk.time > 0)
				o.writeChars("		<set name=\"hitTime\" val=\"" + sk.time + "\" />\n");
			o.writeChars("		<set name=\"target\" val=\"TARGET_NONE\" />\n");

			if(sk.optype <= 1)
				o.writeChars("		<set name=\"operateType\" val=\"OP_ACTIVE\" />\n");
			else if(sk.optype == 2)
				o.writeChars("		<set name=\"operateType\" val=\"OP_PASSIVE\" />\n");
			else if(sk.optype == 3)
				o.writeChars("		<set name=\"operateType\" val=\"OP_TOGGLE\" />\n");
			else
				o.writeChars("		<set name=\"operateType\" val=\"OP_TOGGLE\" />\n");

			o.writeChars("		<set name=\"skillType\" val=\"NOTDONE\" />\n");
			o.writeChars("	</skill>\n");
		}

		f.close();
		o.close();
		System.out.println("time: " + ((System.currentTimeMillis() - time) / 1000));
	}

	private class Skill
	{
		public int id;
		public String name;
		public int levels = 0;
		public int time;
		public int optype;
		public boolean magic;
		public String[] desc = new String[1000];
		public Integer[] mp = new Integer[1000];
		public Integer[] power = new Integer[1000];
		public Integer[] range = new Integer[1000];

		// id, name, power, level, castRange, mpConsume2, operateType, isMagic

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			final Skill other = (Skill) obj;
			if(id != other.id)
				return false;
			return true;
		}
	}
}