package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.PlaySound;

/**
 * @author: rage
 * @date: 23.09.11 20:17
 */
public class AiIcequeenEntranceDefeated extends DefaultAI
{
	public int TIMER_next_voice = 23147001;
	public int TIMER_pc_leave = 23147002;
	public int scene_sec_21 = 24000;

	public AiIcequeenEntranceDefeated(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.l_ai1 = 0;
		_thisActor.setAggroRange(300);
		_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 23140022, _thisActor.getStoredId(), null);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai0 == 0 && creature.isPlayer())
		{
			_thisActor.i_ai0 = 1;
			creature.sendPacket(new PlaySound("Freya.freya_voice_01"));
			//myself.VoiceNPCEffect(creature, "Freya.freya_voice_01", 0);
			_thisActor.l_ai1 = creature.getStoredId();
			addTimer(TIMER_next_voice, 20000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_next_voice)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
			if(c0 != null)
			{
				if(c0.getReflection() == _thisActor.getReflection())
				{
					c0.sendPacket(new PlaySound("Freya.freya_voice_02"));
				}
			}
		}
		else if(timerId == TIMER_pc_leave)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai1);
			if(c0 != null)
			{
				QuestState st = c0.getQuestState(10285);
				st.setMemoState(3);
				st.setCond(10);
				st.getQuest().showQuestMark(st.getPlayer());
				st.playSound(Quest.SOUND_MIDDLE);
				c0.teleToClosestTown();
			}
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.stopInstance();
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 231400001)
		{
			addTimer(TIMER_pc_leave, scene_sec_21);
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}
}
