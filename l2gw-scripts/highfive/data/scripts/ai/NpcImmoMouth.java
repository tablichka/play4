package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 13.12.11 18:59
 */
public class NpcImmoMouth extends DefaultNpc
{
	public int type = 0;
	public int inzone_id1 = 115;
	public int inzone_id2 = 116;
	public int inzone_id3 = 119;
	public int inzone_id4 = 120;
	public int FieldCycle = 3;
	public String fnHi = "mouth_immortality001.htm";
	public String fnHiEnter1 = "mouth_immortality002a.htm";
	public String fnHiEnter2 = "mouth_immortality002b.htm";
	public String fnHiEnterFail = "vigil_immortality003.htm";

	public NpcImmoMouth(L2Character actor)
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
		if(ask == -1000 && reply == 1001)
		{
			if(type == 0)
			{
				_thisActor.showPage(talker, fnHiEnter1);
			}
			else if(type == 1)
			{
				_thisActor.showPage(talker, fnHiEnter2);
			}
		}
		else if(ask == -1100 && reply == 1101)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			if(i0 == 1 || i0 == 2)
			{
				InstanceManager.enterInstance(inzone_id1, talker, _thisActor, 0);
			}
			else if(i0 == 4 || i0 == 5)
			{
				InstanceManager.enterInstance(inzone_id2, talker, _thisActor, 0);
			}
			else
			{
				_thisActor.showPage(talker, fnHiEnterFail);
			}
		}
		else if(ask == -1200 && reply == 1201)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			if(i0 == 1)
			{
				InstanceManager.enterInstance(inzone_id3, talker, _thisActor, 0);
			}
			else if(i0 == 4)
			{
				InstanceManager.enterInstance(inzone_id4, talker, _thisActor, 0);
			}
			else
			{
				_thisActor.showPage(talker, fnHiEnterFail);
			}
		}
	}
}