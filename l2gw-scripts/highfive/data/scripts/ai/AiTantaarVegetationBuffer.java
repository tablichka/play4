package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 06.09.11 22:44
 */
public class AiTantaarVegetationBuffer extends DefaultAI
{
	public int Normal_Desire = 1000000;
	public long Max_Desire = 1000000000000000000L;
	public int TID_DESPAWN = 78001;
	public int TIME_DESPAWN = 5;

	public AiTantaarVegetationBuffer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 != 0)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
			if(c0 != null)
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, c0.getName() + ":" + _thisActor.param2);
				}
				switch((int) _thisActor.param2)
				{
					case 0:
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "skill01 out");
						}
						addUseSkillDesire(c0, 421265409, 0, 0, Max_Desire);
						break;
					case 1:
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "skill01 out");
						}
						addUseSkillDesire(c0, 421396481, 0, 0, Max_Desire);
						break;
					case 2:
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "skill01 out");
						}
						addUseSkillDesire(c0, 435683329, 0, 0, Max_Desire);
						break;
					case 3:
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "skill01 out");
						}
						addUseSkillDesire(c0, 421330945, 0, 0, Max_Desire);
						break;
				}
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "target unavailable");
			}
		}
		addTimer(TID_DESPAWN, (TIME_DESPAWN * 1000));
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "USE_SKILL_FINISHED:" + skill.getId());
		}
		_thisActor.doDie(null);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_DESPAWN)
		{
			_thisActor.doDie(null);
		}
	}
}
