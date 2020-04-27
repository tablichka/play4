package ai;

import ai.base.PartyPrivate;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 21.09.11 20:25
 */
public class FreyaBear extends PartyPrivate
{
	public L2Skill MagicHeal = SkillTable.getInstance().getInfo(458752001);

	public FreyaBear(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(_thisActor.isDead())
		{
			return;
		}

		if(eventId == 10002)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.getLeader().l_ai5);
			if(c0 != null && c0 == _thisActor.getLeader())
			{
				if(_thisActor.getMostHated() != null)
				{
					if(c1 != null && _thisActor.getMostHated() == c1)
					{
						return;
					}
				}
				switch(Rnd.get(4))
				{
					case 0:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000292);
						break;
					case 1:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000400);
						break;
					case 2:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000401);
						break;
					case 3:
						Functions.npcSay(_thisActor, Say2C.ALL, 1000402);
						break;
				}
				if(c1 != null)
				{
					removeAllAttackDesire();
					addAttackDesire(L2ObjectsStorage.getAsCharacter(_thisActor.getLeader().l_ai5), 1, 1000000);
				}
			}
		}
		else if(eventId == 10034)
		{
			if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
			{
				addUseSkillDesire(_thisActor.getLeader(), MagicHeal, 1, 1, 1000000);
			}
		}
		else if(eventId == 11039)
		{
			_thisActor.onDecay();
		}
	}
}