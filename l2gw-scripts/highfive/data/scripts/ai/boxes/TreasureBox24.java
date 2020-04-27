package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 20:57
 */
public class TreasureBox24 extends GTreasureBox
{
	public TreasureBox24(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 3159 )
		{
			_thisActor.dropItem(killer, 736, 7);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2764 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4422 )
		{
			_thisActor.dropItem(killer, 737, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2764 )
		{
			_thisActor.dropItem(killer, 5593, 6);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 5594, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1327 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5749 )
		{
			_thisActor.dropItem(killer, 10131, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5749 )
		{
			_thisActor.dropItem(killer, 10132, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5749 )
		{
			_thisActor.dropItem(killer, 10133, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3833 )
		{
			_thisActor.dropItem(killer, 1538, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1438 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3058 )
		{
			_thisActor.dropItem(killer, 68, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 374 )
		{
			_thisActor.dropItem(killer, 21747, 1);
		}
	}
}