package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTreeTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * format   d (dddc)
 */
public class SkillList extends L2GameServerPacket
{
	private GArray<L2Skill> _skills;
	private boolean allowEnchant;

	public SkillList(L2Player player)
	{
		_skills = new GArray<>();
		allowEnchant = player.getLevel() >= 76 && player.getClassId().getLevel() >= 4;
		for(L2Skill sk : player.getAllSkills())
			if(sk != null && !sk.isHidden())
				_skills.add(sk);
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x5f);
		writeD(_skills.size());

		for(L2Skill temp : _skills)
		{
			writeD(temp.isActive() || temp.isToggle() ? 0 : 1);
			writeD(temp.getDisplayLevel());
			writeD(temp.getDisplayId());
			writeC(0x00); // c5
			writeC(allowEnchant ? SkillTreeTable.isEnchantable(temp) : 0);
		}
	}
}