package ru.l2gw.gameserver.model;

public class L2ShortCut
{
	public final static int TYPE_ITEM = 1;
	public final static int TYPE_SKILL = 2;
	public final static int TYPE_ACTION = 3;
	public final static int TYPE_MACRO = 4;
	public final static int TYPE_RECIPE = 5;
	public final static int TYPE_MYTELEPORT = 6;

	public final int slot;
	public final int page;
	public final int type;
	public final int id;
	public final int level;
	public int delay_group;
	public int reuse_left;
	public int reuse_delay;

	public L2ShortCut(int slot, int page, int type, int id, int level)
	{
		this(slot, page, type, id, level, -1, -1, 0);

	}
	public L2ShortCut(int slot, int page, int type, int id, int level, int delay_group, int reuse_left, int reuse_delay)
	{
		this.slot = slot;
		this.page = page;
		this.type = type;
		this.id = id;
		this.level = level;
		this.delay_group = delay_group;
		this.reuse_left = reuse_left;
		this.reuse_delay = reuse_delay;
	}

	@Override
	public String toString()
	{
		return "ShortCut: " + slot + "/" + page + " ( " + type + "," + id + "," + level + ")";
	}
}