package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 13:15
 */
public class AiPartyVitalityHerb extends L2CharacterAI
{
	public L2Skill herb_skill = SkillTable.getInstance().getInfo(451084289);
	public int TID_LIFETIME = 787878;
	public int TIME_LIFETIME = 3;

	public AiPartyVitalityHerb(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		L2Character cha = L2ObjectsStorage.getAsCharacter(((L2NpcInstance) _actor).param1);
		if(cha != null)
			_actor.altUseSkill(herb_skill, cha);

		addTimer(TID_LIFETIME, TIME_LIFETIME * 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_LIFETIME)
			_actor.deleteMe();
	}
}
