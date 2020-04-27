package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 02.06.11 14:24
 */
public class GatheredNpc extends L2CharacterAI
{
	protected static final int COLLECTED_EVENT = 20110602;
	public int seed_type;
	protected L2NpcInstance _thisActor;

	public GatheredNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor = (L2NpcInstance) _actor;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == COLLECTED_EVENT)
		{
			L2Player player = (L2Player) arg1;
			QuestState qs = player.getQuestState(692);
			if(qs != null && qs.isStarted() && qs.getMemoState() == 3 && Rnd.chance(80))
			{
				switch(seed_type)
				{
					case 1:
						qs.rollAndGive(13867, 1, 100);
						qs.playSound(Quest.SOUND_ITEMGET);
						break;
					case 2:
						qs.rollAndGive(13866, 1, 100);
						qs.playSound(Quest.SOUND_ITEMGET);
						break;
					case 3:
						qs.rollAndGive(15535, 1, 100);
						qs.playSound(Quest.SOUND_ITEMGET);
						break;
				}
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
