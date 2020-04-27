package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 31.08.2010 16:41:35
 */
public class FortuneBug extends DefaultAI
{
	private static final int ItemName_A = 57;
	private static final int ItemName_B_1 = 1881;
	private static final int ItemName_B_2 = 1890;
	private static final int ItemName_B_3 = 1880;
	private static final int ItemName_B_4 = 729;
	private static final L2Skill s_display_bug_of_fortune1 = SkillTable.getInstance().getInfo(6045, 1);
	private static final L2Skill s_display_jackpot_firework = SkillTable.getInstance().getInfo(5778, 1);

	private long _nextEat;
	private int i_ai0, i_ai1, i_ai2;

	public FortuneBug(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(7778, 1000);
		i_ai0 = i_ai1 = i_ai2 = 0;
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();

		L2ItemInstance closestItem = null;
		int minDist = Integer.MAX_VALUE;

		if(_nextEat < System.currentTimeMillis())
		{
			for(L2Object obj : L2World.getAroundObjects(_thisActor, 20, 100))
				if(obj instanceof L2ItemInstance && !((L2ItemInstance) obj).isEquipable() && _thisActor.getDistance3D(obj) < minDist)
				{
					minDist = (int) _thisActor.getDistance3D(obj);
					closestItem = (L2ItemInstance) obj;
				}

			if(closestItem != null)
			{
				closestItem.deleteMe();
				_thisActor.altUseSkill(s_display_bug_of_fortune1, _thisActor);
				Functions.npcSayInRange(_thisActor, Say2C.ALL, 1800291, 600);

				i_ai0++;
				if(i_ai0 > 1 && i_ai0 <= 10)
					i_ai1 = 1;
				else if(i_ai0 > 10 && i_ai0 <= 100)
					i_ai1 = 2;
				else if(i_ai0 > 100 && i_ai0 <= 500)
					i_ai1 = 3;
				else if(i_ai0 > 500 && i_ai0 <= 1000)
					i_ai1 = 4;
				if(i_ai0 > 1000)
					i_ai1 = 5;

				switch(i_ai1)
				{
					case 0:
						i_ai2 = 0;
						break;
					case 1:
						if(Rnd.get(100) < 10)
							i_ai2 = 2;
						else if(Rnd.get(100) < 15)
							i_ai2 = 3;
						else
							i_ai2 = 1;
						break;
					case 2:
						if(Rnd.get(100) < 10)
							i_ai2 = 3;
						else if(Rnd.get(100) < 15)
							i_ai2 = 4;
						else
							i_ai2 = 2;
						break;
					case 3:
						if(Rnd.get(100) < 10)
							i_ai2 = 4;
						else
							i_ai2 = 3;
						break;
					case 4:
						if(Rnd.get(100) < 10)
							i_ai2 = 3;
						else
							i_ai2 = 4;
						break;
				}

				_nextEat = System.currentTimeMillis() + 10000;
			}
		}
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(!_thisActor.isMoving && _nextEat < System.currentTimeMillis())
		{
			L2ItemInstance closestItem = null;
			int minDist = Integer.MAX_VALUE;

			for(L2Object obj : L2World.getAroundObjects(_thisActor, 500, 100))
				if(obj instanceof L2ItemInstance && ((L2ItemInstance) obj).isStackable() && _thisActor.getDistance3D(obj) < minDist)
				{
					minDist = (int) _thisActor.getDistance3D(obj);
					closestItem = (L2ItemInstance) obj;
				}

			if(closestItem != null)
				_thisActor.moveToLocation(closestItem.getLoc(), 0, true);
		}

		return false;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(killer != null)
		{
			if(i_ai2 == 0)
				Functions.npcSayInRange(_thisActor, Say2C.ALL, 1800290, 600);
			else
				_thisActor.broadcastPacket(new MagicSkillUse(_thisActor, s_display_jackpot_firework.getId(), 1, s_display_jackpot_firework.getHitTime(), 0));

			int i0, i1;
			switch(i_ai2)
			{
				case 1:
					i0 = 695;
					i1 = 2245;
					_thisActor.dropItem(killer.getPlayer(), ItemName_A, i0 + Rnd.get(i1 - i0));
					break;
				case 2:
					i0 = 3200;
					i1 = 8400;
					_thisActor.dropItem(killer.getPlayer(), ItemName_A, i0 + Rnd.get(i1 - i0));
					break;
				case 3:
					i0 = 7;
					i1 = 17;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_1, i0 + Rnd.get(i1 - i0));
					i0 = 1;
					i1 = 1;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_2, i0 + Rnd.get(i1 - i0));
					i0 = 7;
					i1 = 17;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_3, i0 + Rnd.get(i1 - i0));
					break;
				case 4:
					i0 = 15;
					i1 = 45;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_1, i0 + Rnd.get(i1 - i0));
					i0 = 10;
					i1 = 20;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_2, i0 + Rnd.get(i1 - i0));
					i0 = 15;
					i1 = 45;
					_thisActor.dropItem(killer.getPlayer(), ItemName_B_3, i0 + Rnd.get(i1 - i0));
					if(Rnd.get(100) < 10)
						_thisActor.dropItem(killer.getPlayer(), ItemName_B_4, 1);
					break;
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 7778)
		{
			switch(i_ai0)
			{
				case 0:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800279 : 1800280, 600);
					break;
				case 1:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800281 : 1800282, 600);
					break;
				case 2:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800283 : 1800284, 600);
					break;
				case 3:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800285 : 1800286, 600);
					break;
				case 4:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800287 : 1800288, 600);
					break;
				case 5:
					Functions.npcSayInRange(_thisActor, Say2C.ALL, 1800289, 600);
					break;
			}
			addTimer(7778, 10000 + Rnd.get(10) * 1000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}
}
