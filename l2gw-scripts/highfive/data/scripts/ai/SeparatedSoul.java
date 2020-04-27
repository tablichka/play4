package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 25.09.11 3:42
 */
public class SeparatedSoul extends Citizen
{
	public int TelPosX_01 = 117046;
	public int TelPosY_01 = 76798;
	public int TelPosZ_01 = -2696;
	public int TelPosX_02 = 99218;
	public int TelPosY_02 = 110283;
	public int TelPosZ_02 = -3688;
	public int TelPosX_03 = 116992;
	public int TelPosY_03 = 113716;
	public int TelPosZ_03 = -3056;
	public int TelPosX_04 = 113203;
	public int TelPosY_04 = 121063;
	public int TelPosZ_04 = -3712;
	public int TelPosX_05 = 146129;
	public int TelPosY_05 = 111232;
	public int TelPosZ_05 = -3568;
	public int TelPosX_06 = 148447;
	public int TelPosY_06 = 110582;
	public int TelPosZ_06 = -3944;
	public int TelPosX_07 = 73122;
	public int TelPosY_07 = 118351;
	public int TelPosZ_07 = -3784;
	public int TelPosX_08 = 131116;
	public int TelPosY_08 = 114333;
	public int TelPosZ_08 = -3704;
	public String antaras_items = "separated_soul_01002.htm";
	public String fnNo_items = "separated_soul_01003.htm";
	public String fnNotEnoughLevel = "separated_soul_09001.htm";

	public SeparatedSoul(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -1)
		{
			if(talker.getLevel() < 80)
			{
				_thisActor.showPage(talker, fnNotEnoughLevel);
			}
			else
			{
				switch(reply)
				{
					case 1:
						talker.teleToLocation(TelPosX_01, TelPosY_01, TelPosZ_01);
						break;
					case 2:
						talker.teleToLocation(TelPosX_02, TelPosY_02, TelPosZ_02);
						break;
					case 3:
						talker.teleToLocation(TelPosX_03, TelPosY_03, TelPosZ_03);
						break;
					case 4:
						talker.teleToLocation(TelPosX_04, TelPosY_04, TelPosZ_04);
						break;
					case 5:
						talker.teleToLocation(TelPosX_05, TelPosY_05, TelPosZ_05);
						break;
					case 6:
						talker.teleToLocation(TelPosX_06, TelPosY_06, TelPosZ_06);
						break;
					case 7:
						talker.teleToLocation(TelPosX_07, TelPosY_07, TelPosZ_07);
						break;
					case 8:
						talker.teleToLocation(TelPosX_08, TelPosY_08, TelPosZ_08);
						break;
				}
			}
		}
		else if(ask == -2324)
		{
			if(reply == 1)
			{
				if(talker.getItemCountByItemId(17266) > 0 && talker.getItemCountByItemId(17267) > 0)
				{
					talker.destroyItemByItemId("Quest", 17266, 1, _thisActor, true);
					talker.destroyItemByItemId("Quest", 17267, 1, _thisActor, true);
					talker.addItem("Quest", 17268, 1, _thisActor, true);
				}
				else
				{
					_thisActor.showPage(talker, fnNo_items);
				}
			}
			else if(reply == 2)
			{
				_thisActor.showPage(talker, antaras_items);
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}
}
