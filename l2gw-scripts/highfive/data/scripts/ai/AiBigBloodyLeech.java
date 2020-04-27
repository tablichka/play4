package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 13:21
 */
public class AiBigBloodyLeech extends DetectPartyWarrior
{
	public L2Skill selfBlasting = SkillTable.getInstance().getInfo(450363393);

	public AiBigBloodyLeech(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10023)
		{
			L2Character cha = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(cha != null)
				addUseSkillDesire(cha, selfBlasting, 1, 0, 99999999900000000L);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == selfBlasting)
			_thisActor.doDie(null);
	}
}
