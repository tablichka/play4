package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 21.12.2009 17:04:00
 */
public class BaylorAlarmInstance extends L2MonsterInstance
{
	private static L2Skill[] _skills = {
			SkillTable.getInstance().getInfo(5221, 1),
			SkillTable.getInstance().getInfo(5222, 1),
			SkillTable.getInstance().getInfo(5223, 1)};

	public BaylorAlarmInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		Functions.npcSayCustom(this, Say2C.SHOUT, "BaylorAlarm", null);
	}

	public void doDie(L2Character killer)
	{
		super.doDie(killer);
		L2NpcInstance baylor = L2ObjectsStorage.getByNpcId(29099, getReflection());
		if(baylor != null)
			_skills[Rnd.get(_skills.length)].applyEffects(this, baylor, false);
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isAttackingDisabled()
	{
		return true;
	}
}
