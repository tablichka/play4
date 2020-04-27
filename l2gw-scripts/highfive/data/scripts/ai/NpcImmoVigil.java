package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 13.12.11 18:46
 */
public class NpcImmoVigil extends DefaultNpc
{
	public int type = 0;
	public int inzone_id1 = 121;
	public int inzone_id2 = 122;
	public int FieldCycle = 3;
	public int return_x = -212836;
	public int return_y = 209824;
	public int return_z = 4288;
	public String fnHi = "vigil_immortality001.htm";
	public String fnHiEnter1 = "vigil_immortality002a.htm";
	public String fnHiEnter2 = "vigil_immortality002b.htm";
	public String fnHiEnterFail = "vigil_immortality003.htm";
	public int loc_x01 = -179537;
	public int loc_y01 = 209551;
	public int loc_z01 = -15504;
	public int loc_x02 = -179779;
	public int loc_y02 = 212540;
	public int loc_z02 = -15520;
	public int loc_x03 = -177028;
	public int loc_y03 = 211135;
	public int loc_z03 = -15520;
	public int loc_x04 = -176355;
	public int loc_y04 = 208043;
	public int loc_z04 = -15520;
	public int loc_x05 = -179284;
	public int loc_y05 = 205990;
	public int loc_z05 = -15520;
	public int loc_x06 = -182268;
	public int loc_y06 = 208218;
	public int loc_z06 = -15520;
	public int loc_x07 = -182069;
	public int loc_y07 = 211140;
	public int loc_z07 = -15520;
	public int loc_x08 = -176036;
	public int loc_y08 = 210002;
	public int loc_z08 = -11948;
	public int loc_x09 = -176039;
	public int loc_y09 = 208203;
	public int loc_z09 = -11949;
	public int loc_x10 = -183288;
	public int loc_y10 = 208205;
	public int loc_z10 = -11939;
	public int loc_x11 = -183290;
	public int loc_y11 = 210004;
	public int loc_z11 = -11939;
	public int loc_x12 = -187776;
	public int loc_y12 = 205696;
	public int loc_z12 = -9536;
	public int loc_x13 = -186327;
	public int loc_y13 = 208286;
	public int loc_z13 = -9536;
	public int loc_x14 = -184429;
	public int loc_y14 = 211155;
	public int loc_z14 = -9536;
	public int loc_x15 = -182811;
	public int loc_y15 = 213871;
	public int loc_z15 = -9504;
	public int loc_x16 = -180921;
	public int loc_y16 = 216789;
	public int loc_z16 = -9536;
	public int loc_x17 = -177264;
	public int loc_y17 = 217760;
	public int loc_z17 = -9536;
	public int loc_x18 = -173727;
	public int loc_y18 = 218169;
	public int loc_z18 = -9536;

	public NpcImmoVigil(L2Character actor)
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
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			Functions.sendUIEventFStr(creature, 1, 0, 0, "1", "1", "1", "60", "0", 1911119);
		}
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -1002 && reply == 1003)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			if(i0 == 1)
			{
				_thisActor.showPage(talker, fnHiEnterFail);
			}
			else if(i0 == 2 || i0 == 5)
			{
				_thisActor.showPage(talker, fnHiEnter2);
			}
			else
			{
				_thisActor.showPage(talker, fnHiEnter1);
			}
		}
		else if(ask == -1004 && reply == 1005)
		{
			talker.teleToLocation(return_x, return_y, return_z);
		}
		else if(ask == -1006 && reply == 1007)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			if(i0 == 3)
			{
				int i1 = Rnd.get(18) + 1;
				int i5 = 0, i6 = 0, i7 = 0;
				switch(i1)
				{
					case 1:
						i5 = loc_x01;
						i6 = loc_y01;
						i7 = loc_z01;
						break;
					case 2:
						i5 = loc_x02;
						i6 = loc_y02;
						i7 = loc_z02;
						break;
					case 3:
						i5 = loc_x03;
						i6 = loc_y03;
						i7 = loc_z03;
						break;
					case 4:
						i5 = loc_x04;
						i6 = loc_y04;
						i7 = loc_z04;
						break;
					case 5:
						i5 = loc_x05;
						i6 = loc_y05;
						i7 = loc_z05;
						break;
					case 6:
						i5 = loc_x06;
						i6 = loc_y06;
						i7 = loc_z06;
						break;
					case 7:
						i5 = loc_x07;
						i6 = loc_y07;
						i7 = loc_z07;
						break;
					case 8:
						i5 = loc_x08;
						i6 = loc_y08;
						i7 = loc_z08;
						break;
					case 9:
						i5 = loc_x09;
						i6 = loc_y09;
						i7 = loc_z09;
						break;
					case 10:
						i5 = loc_x10;
						i6 = loc_y10;
						i7 = loc_z10;
						break;
					case 11:
						i5 = loc_x11;
						i6 = loc_y11;
						i7 = loc_z11;
						break;
					case 12:
						i5 = loc_x12;
						i6 = loc_y12;
						i7 = loc_z12;
						break;
					case 13:
						i5 = loc_x13;
						i6 = loc_y13;
						i7 = loc_z13;
						break;
					case 14:
						i5 = loc_x14;
						i6 = loc_y14;
						i7 = loc_z14;
						break;
					case 15:
						i5 = loc_x15;
						i6 = loc_y15;
						i7 = loc_z15;
						break;
					case 16:
						i5 = loc_x16;
						i6 = loc_y16;
						i7 = loc_z16;
						break;
					case 17:
						i5 = loc_x17;
						i6 = loc_y17;
						i7 = loc_z17;
						break;
					case 18:
						i5 = loc_x18;
						i6 = loc_y18;
						i7 = loc_z18;
						break;
				}
				talker.teleToLocation(i5, i6, i7);
			}
			else if(i0 == 4)
			{
				int i1 = (Rnd.get(7) + 1);
				int i5 = 0, i6 = 0, i7 = 0;
				switch(i1)
				{
					case 1:
						i5 = loc_x01;
						i6 = loc_y01;
						i7 = loc_z01;
						break;
					case 2:
						i5 = loc_x02;
						i6 = loc_y02;
						i7 = loc_z02;
						break;
					case 3:
						i5 = loc_x03;
						i6 = loc_y03;
						i7 = loc_z03;
						break;
					case 4:
						i5 = loc_x04;
						i6 = loc_y04;
						i7 = loc_z04;
						break;
					case 5:
						i5 = loc_x05;
						i6 = loc_y05;
						i7 = loc_z05;
						break;
					case 6:
						i5 = loc_x06;
						i6 = loc_y06;
						i7 = loc_z06;
						break;
					case 7:
						i5 = loc_x07;
						i6 = loc_y07;
						i7 = loc_z07;
						break;
				}
				talker.teleToLocation(i5, i6, i7);
			}
			else
			{
				_thisActor.showPage(talker, fnHiEnterFail);
			}
		}
		else if(ask == -1008 && reply == 1009)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			if(i0 == 2)
			{
				InstanceManager.enterInstance(inzone_id1, talker, _thisActor, 0);
			}
			else if(i0 == 5)
			{
				InstanceManager.enterInstance(inzone_id2, talker, _thisActor, 0);
			}
			else
			{
				_thisActor.showPage(talker, fnHiEnterFail);
			}
		}
	}
}