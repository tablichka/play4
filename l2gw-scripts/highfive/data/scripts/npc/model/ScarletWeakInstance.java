package npc.model;

import instances.FrintezzaBattleInstance;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: rage
 * @date: 14.09.2009 13:41:41
 */
public class ScarletWeakInstance extends L2RaidBossInstance
{
	private boolean morphed = false;
	private boolean killed = false;

	public ScarletWeakInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		if(damage >= getCurrentHp())
		{
			if(!killed)
			{
				killed = true;
				((FrintezzaBattleInstance) getSpawn().getInstance()).demonKilled();
			}
		}
		else
		{
			super.decreaseHp(damage, attacker, directHp, reflect);
			if(getCurrentHp() < getMaxHp() * 0.5 && !morphed)
			{
				morphed = true;
				((FrintezzaBattleInstance) getSpawn().getInstance()).showFirstMorph();
			}
		}
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();

		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable(){
			public void run()
			{
				List<L2Character> targets = new ArrayList<>();
				targets.addAll(getKnownCharacters(600));
				SkillTable.getInstance().getInfo(5434, 1).useSkill(ScarletWeakInstance.this, targets);
			}
		}, 1000);
	}

	@Override
	public boolean hasRandomAnimation()
	{
		return false;
	}
}
