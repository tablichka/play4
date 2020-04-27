package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 13.01.11 19:26
 */
public class FighterForge extends Fighter
{
	private static final int TID_MOB_COUNT_REFRESH = 78001;
	private static final int TIME_MOB_COUNT_REFRESH = 15;
	private static final int MobCount_bonus_min = 3;
	private static final int MobCount_bonus_upper_lv01 = 5;
	private static final int MobCount_bonus_upper_lv02 = 10;
	private static final int MobCount_bonus_upper_lv03 = 15;
	private static final int MobCount_bonus_upper_lv04 = 20;
	private static final int MobCount_bonus_upper_lv05 = 35;
	private static final int MobCount_bonus_lower_lv01 = 5;
	private static final int MobCount_bonus_lower_lv02 = 10;
	private static final int MobCount_bonus_lower_lv03 = 15;
	private static final int Prob_forge_bonus01 = 20;
	private static final int Prob_forge_bonus02 = 40;
	private static final int[] noClanNpc = {18799, 18800, 18801, 18802, 18803, 22642, 22643};
	public int mode = 0;

	public FighterForge(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai1 = 0;
		addTimer(TID_MOB_COUNT_REFRESH, TIME_MOB_COUNT_REFRESH * 1000);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1)
		{
			if(arg1 instanceof L2NpcInstance)
			{
				L2NpcInstance victim = (L2NpcInstance) arg1;
				if(!Quest.contains(noClanNpc, victim.getNpcId()) && victim.getFactionId() != null && victim.getFactionId().equalsIgnoreCase(_thisActor.getFactionId()))
					_thisActor.i_ai1++;
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_MOB_COUNT_REFRESH)
		{
			if(_thisActor.i_ai0 > 0 && (int) _thisActor.getCurrentHp() == _thisActor.getMaxMp())
				_thisActor.i_ai0 = 0;

			addTimer(TID_MOB_COUNT_REFRESH, TIME_MOB_COUNT_REFRESH * 1000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		broadcastScriptEvent(1, _thisActor, null, 300);
		int i0 = Rnd.get(100);

		if(mode == 1 && _thisActor.getSpawnDefine() != null && _thisActor.getSpawnDefine().getMaker().npc_count < 48)
		{
			if(_thisActor.i_ai1 > MobCount_bonus_lower_lv03 && i0 <= Prob_forge_bonus02)
				createOnePrivate(18803);
			else if(_thisActor.i_ai1 > MobCount_bonus_lower_lv02 && _thisActor.i_ai1 <= MobCount_bonus_lower_lv03)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18803);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18802);
			}
			else if(_thisActor.i_ai1 > MobCount_bonus_lower_lv01 && _thisActor.i_ai1 <= MobCount_bonus_lower_lv02)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18802);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18801);
			}
			if(_thisActor.i_ai1 >= MobCount_bonus_min && _thisActor.i_ai1 <= MobCount_bonus_lower_lv01)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18801);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18800);
			}
		}
		else if(_thisActor.getSpawnDefine() != null && _thisActor.getSpawnDefine().getMaker().npc_count < 32)
		{
			if(_thisActor.i_ai1 > MobCount_bonus_upper_lv05 && i0 <= Prob_forge_bonus02)
				createOnePrivate(18803);
			else if(_thisActor.i_ai1 > MobCount_bonus_upper_lv04 && _thisActor.i_ai1 <= MobCount_bonus_upper_lv05)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18803);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18802);
			}
			else if(((_thisActor.i_ai1 > MobCount_bonus_upper_lv03) && (_thisActor.i_ai1 <= MobCount_bonus_upper_lv04)))
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18802);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18801);
			}
			else if(_thisActor.i_ai1 > MobCount_bonus_upper_lv02 && _thisActor.i_ai1 <= MobCount_bonus_upper_lv03)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18801);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18800);
			}
			else if(_thisActor.i_ai1 > MobCount_bonus_upper_lv01 && _thisActor.i_ai1 <= MobCount_bonus_upper_lv02)
			{
				if(i0 <= Prob_forge_bonus01)
					createOnePrivate(18800);
				else if(i0 <= Prob_forge_bonus02)
					createOnePrivate(18799);
			}
			if(_thisActor.i_ai1 >= MobCount_bonus_min && _thisActor.i_ai1 <= MobCount_bonus_upper_lv01 && i0 <= Prob_forge_bonus01)
				createOnePrivate(18799);
		}
	}

	private void createOnePrivate(int npcId)
	{
		try
		{
			_thisActor.getMinionList().spawnSingleMinion(npcId, null, 0, _thisActor.getLoc());
		}
		catch(Exception e)
		{
			_log.info(_thisActor + " createOnePrivate error: " + e);
			e.printStackTrace();
		}
	}
}
