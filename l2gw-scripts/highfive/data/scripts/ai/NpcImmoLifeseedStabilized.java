package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 19.12.11 13:36
 */
public class NpcImmoLifeseedStabilized extends Citizen
{
	public String fnHi = "";
	public String type = "";
	public int zone = 0;
	public int room = 0;
	public int return_x = -212836;
	public int return_y = 209824;
	public int return_z = 4288;
	public int TM_cooltime = 78001;
	public int TIME_cooltime1 = 120;
	public int TIME_cooltime2 = 360;
	public int TIME_cooltime3 = 720;

	public NpcImmoLifeseedStabilized(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -7801 )
		{
			if( reply == 1 )
			{
				talker.teleToLocation( return_x, return_y, return_z);
			}
		}
	}
}