package npc.maker;

import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 15.12.11 12:35
 */
public class ImmoBasicMaker extends InzoneMaker
{
	public int tide = 0;
	public int zone = 0;
	public int room = 0;
	public int seq = 0;
	public String z2_a_dispatcher_maker = "rumwarsha14_1424_a_dispm1";
	public String z2_d_dispatcher_maker = "rumwarsha14_1424_d_dispm1";
	public String z3_a_dispatcher_maker = "rumwarsha15_1424_a_dispm1";
	public String z3_d_dispatcher_maker = "rumwarsha15_1424_d_dispm1";
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;

	public ImmoBasicMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		inzone_type_param = 0;
		on_start_spawn = 1;
		script_event_enable = 1;
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
	}

	@Override
	public void onAllNpcDeleted()
	{
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
	}
}