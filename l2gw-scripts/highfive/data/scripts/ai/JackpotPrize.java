package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 01.09.11 9:32
 */
public class JackpotPrize extends L2CharacterAI
{
	public int ItemName_52_A = 14678;
	public int ItemName_52_B = 8755;
	public int ItemName_70_A = 14679;
	public int ItemName_70_B_1 = 5577;
	public int ItemName_70_B_2 = 5578;
	public int ItemName_70_B_3 = 5579;
	public int ItemName_80_A = 14680;
	public int ItemName_80_B_1 = 9552;
	public int ItemName_80_B_2 = 9553;
	public int ItemName_80_B_3 = 9554;
	public int ItemName_80_B_4 = 9555;
	public int ItemName_80_B_5 = 9556;
	public int ItemName_80_B_6 = 9557;

	private L2NpcInstance _thisActor;

	public JackpotPrize(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		if(Rnd.chance(50))
			Functions.npcSay(_thisActor, Say2C.ALL, 1900148);
		else
			Functions.npcSay(_thisActor, Say2C.ALL, 1900149);

		addTimer(1001, 600000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 10001)
			_thisActor.deleteMe();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.doDie(attacker);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		L2Player player;

		if(killer == null || (player = killer.getPlayer()) == null)
			return;

		switch((int) _thisActor.param1)
		{
			case 52:
				if(_thisActor.param2 >= 5)
					_thisActor.dropItem(player, ItemName_52_A, 1);
				else if(_thisActor.param2 >= 2 && _thisActor.param2 < 5)
					_thisActor.dropItem(player, ItemName_52_B, 2);
				else
					_thisActor.dropItem(player, ItemName_52_B, 1);
				break;
			case 70:
				if(_thisActor.param2 >= 5)
					_thisActor.dropItem(player, ItemName_70_A, 1);
				else if(_thisActor.param2 >= 2 && _thisActor.param2 < 5)
				{
					int i0 = Rnd.get(3);
					if(i0 == 2)
						_thisActor.dropItem(player, ItemName_70_B_1, 2);
					else if(i0 == 1)
						_thisActor.dropItem(player, ItemName_70_B_2, 2);
					else
						_thisActor.dropItem(player, ItemName_70_B_3, 2);
				}
				else
				{
					int i0 = Rnd.get(3);
					if(i0 == 2)
						_thisActor.dropItem(player, ItemName_70_B_1, 1);
					else if(i0 == 1)
						_thisActor.dropItem(player, ItemName_70_B_2, 1);
					else
						_thisActor.dropItem(player, ItemName_70_B_3, 1);
				}
				break;
			case 80:
				if(_thisActor.param2 >= 5)
					_thisActor.dropItem(player, ItemName_80_A, 1);
				else if(_thisActor.param2 >= 2 && _thisActor.param2 < 5)
				{
					int i0 = Rnd.get(6);
					if(i0 == 5)
						_thisActor.dropItem(player, ItemName_80_B_1, 2);
					else if(i0 == 4)
						_thisActor.dropItem(player, ItemName_80_B_2, 2);
					else if(i0 == 3)
						_thisActor.dropItem(player, ItemName_80_B_3, 2);
					else if(i0 == 2)
						_thisActor.dropItem(player, ItemName_80_B_4, 2);
					else if(i0 == 1)
						_thisActor.dropItem(player, ItemName_80_B_5, 2);
					else
						_thisActor.dropItem(player, ItemName_80_B_6, 2);
				}
				else
				{
					int i0 = Rnd.get(6);
					if(i0 == 5)
						_thisActor.dropItem(player, ItemName_80_B_1, 1);
					else if(i0 == 4)
						_thisActor.dropItem(player, ItemName_80_B_2, 1);
					else if(i0 == 3)
						_thisActor.dropItem(player, ItemName_80_B_3, 1);
					else if(i0 == 2)
						_thisActor.dropItem(player, ItemName_80_B_4, 1);
					else if(i0 == 1)
						_thisActor.dropItem(player, ItemName_80_B_5, 1);
					else
						_thisActor.dropItem(player, ItemName_80_B_6, 1);
				}
				break;
		}
	}
}
