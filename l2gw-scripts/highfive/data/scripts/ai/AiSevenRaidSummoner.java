package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 09.09.11 1:56
 */
public class AiSevenRaidSummoner extends Citizen
{
	public int raid_monster_1 = 25718;
	public int raid_monster_2 = 25719;
	public int raid_monster_3 = 25720;
	public int raid_monster_4 = 25721;
	public int raid_monster_5 = 25723;
	public int raid_monster_6 = 25722;
	public int raid_monster_7 = 25724;
	public int spot_1_x = 92744;
	public int spot_1_y = 114045;
	public int spot_1_z = -3072;
	public int spot_2_x = 110112;
	public int spot_2_y = 124976;
	public int spot_2_z = -3624;
	public int spot_3_x = 121637;
	public int spot_3_y = 113657;
	public int spot_3_z = -3792;
	public int spot_4_x = 109346;
	public int spot_4_y = 111849;
	public int spot_4_z = -3040;
	public int my_zone = 0;

	public AiSevenRaidSummoner(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "seven_raid_summoner001.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -1 && reply == 1 )
		{
			if( _thisActor.i_ai2 == 1 )
			{
				_thisActor.showPage(talker, "seven_raid_summoner003.htm");
				return;
			}
			if( talker.getItemCountByItemId(17248) > 0 )
			{
				talker.destroyItemByItemId(getClass().getSimpleName(), 17248, 1, _thisActor, true);
			}
			else
			{
				_thisActor.showPage(talker, "seven_raid_summoner002.htm");
				return;
			}
			if( _thisActor.i_ai2 == 0 )
			{
				int i0 = Rnd.get(100);
				int i1;
				String s1;
				if( i0 < 3 )
				{
					i1 = raid_monster_7;
					s1 = "AiMuscleBomber";
				}
				else if( i0 < 8 )
				{
					i1 = raid_monster_6;
					s1 = "AiShadowSummoner";
				}
				else if( i0 < 15 )
				{
					i1 = raid_monster_5;
					s1 = "AiSpikeSlasher";
				}
				else if( i0 < 25 )
				{
					i1 = raid_monster_4;
					s1 = "AiBlackdaggerWing";
				}
				else if( i0 < 45 )
				{
					i1 = raid_monster_3;
					s1 = "AiBleedingFly";
				}
				else if( i0 < 67 )
				{
					i1 = raid_monster_2;
					s1 = "AiDustRider";
				}
				else
				{
					i1 = raid_monster_1;
					s1 = "AiEmeraldHorn";
				}
				switch(my_zone)
				{
					case 1:
						_thisActor.createOnePrivate(i1, s1, 0, 0, spot_1_x, spot_1_y, spot_1_z, 0, 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(i1, s1, 0, 0, spot_2_x, spot_2_y, spot_2_z, 0, 0, 0, 0);
						break;
					case 3:
						_thisActor.createOnePrivate(i1, s1, 0, 0, spot_3_x, spot_3_y, spot_3_z, 0, 0, 0, 0);
						break;
					case 4:
						_thisActor.createOnePrivate(i1, s1, 0, 0, spot_4_x, spot_4_y, spot_4_z, 0, 0, 0, 0);
						break;
				}
				_thisActor.i_ai2 = 1;
				addTimer(20100506, 60000);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 20100506 )
		{
			_thisActor.i_ai2 = 0;
		}
	}
}
