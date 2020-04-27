package ai;

import ai.base.IcequeenHelpPc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 28.09.11 0:25
 */
public class Kegor extends IcequeenHelpPc
{
	public int TIMER_SCENE_20 = 2314506;
	public int TIMER_SCENE_20_END = 2314516;
	public int scene_num_20 = 20;
	public int scene_sec_20 = 58000;
	public int TIMER_despawn = 2314040;
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public String fnHi = "kegor001.htm";
	public String fnYouAreNotLeader = "kegor002.htm";

	public Kegor(L2Character actor)
	{
		super(actor);
		position = -1;
		debug_mode = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(position == 2)
		{
			_thisActor.createOnePrivate(29179, "FreyaDefeated", 0, 0, 114767, -114795, -11200, 0, 0, 0, 0);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140058, 0, 0);
			}
		}
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(position == 2)
		{
			_thisActor.showPage(talker, fnHi);
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -2318)
		{
			if(reply == 1)
			{
				if(talker.getParty() != null && talker.getParty().getCommandChannel() != null && talker.getParty().getCommandChannel().getChannelLeader() == talker)
				{
					if(debug_mode > 0)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "start ending");
					}
					addTimer(TIMER_SCENE_20, 1000);
				}
				else
				{
					_thisActor.showPage(talker, fnYouAreNotLeader);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == TIMER_SCENE_20)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_20");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_20, 4000, 1000);
			addTimer(TIMER_SCENE_20_END, scene_sec_20);
		}
		else if(timerId == TIMER_SCENE_20_END)
		{
			_thisActor.getInstanceZone().rescheduleEndTask(600);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140059, 0, 0);
			}
			addTimer(TIMER_despawn, 2000);
		}
		else if(timerId == TIMER_despawn)
		{
			broadcastScriptEvent(23140020, 0, null, 4000);
			_thisActor.onDecay();
		}
	}
}