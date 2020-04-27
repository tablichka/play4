package ai;

import ai.base.PartyLeaderAggressive;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 21.09.11 20:18
 */
public class FreyaPet extends PartyLeaderAggressive
{
	public L2Skill selfbuff = SkillTable.getInstance().getInfo(458752001);

	public FreyaPet(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai1 = 0;
		_thisActor.c_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && CategoryManager.isInCategory(3, creature.getActiveClass()))
		{
			if(_thisActor.c_ai0 == 0)
			{
				_thisActor.c_ai0 = creature.getStoredId();
			}
			else if(_thisActor.c_ai1 == 0)
			{
				_thisActor.c_ai1 = creature.getStoredId();
			}
			else if(_thisActor.c_ai2 == 0)
			{
				_thisActor.c_ai2 = creature.getStoredId();
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(Rnd.get(100) < 5)
		{
			if(selfbuff.getMpConsume() < _thisActor.getCurrentMp() && selfbuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(selfbuff.getId()))
			{
				addUseSkillDesire(_thisActor, selfbuff, 1, 1, 1000000);
			}
		}
		if(Rnd.get(100) < 3)
		{
			if(_thisActor.getMostHated() == attacker)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000399, attacker.getName());
			}
			removeAllAttackDesire();
			addAttackDesire(attacker, 1, 1000000);
			_thisActor.l_ai5 = getStoredIdFromCreature(attacker);
			broadcastScriptEvent(10002, getStoredIdFromCreature(_thisActor), null, 2000);
		}
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.800000))
		{
			broadcastScriptEvent(10034, 0, null, 2000);
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(_thisActor != partyPrivate)
		{
			_thisActor.lookNeighbor(1000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				if(c0.getCurrentHp() > 1 && c0 != _thisActor)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1000399, c0.getName());
					removeAllAttackDesire();
					addAttackDesire(c0, 1, 1000000);
					_thisActor.l_ai5 = _thisActor.c_ai0;
					broadcastScriptEvent(10002, getStoredIdFromCreature(_thisActor), null, 500);
				}
			}
			L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1);
			if(c1 != null)
			{
				if(c1.getCurrentHp() > 1 && c1 != _thisActor)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1000399, c1.getName());
					removeAllAttackDesire();
					addAttackDesire(c1, 1, 1000000);
					_thisActor.l_ai5 = _thisActor.c_ai1;
					broadcastScriptEvent(10002, getStoredIdFromCreature(_thisActor), null, 500);
				}
			}
			L2Character c2 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai2);
			if(c2 != null)
			{
				if(c2.getCurrentHp() > 1 && c2 != _thisActor)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1000399, c2.getName());
					removeAllAttackDesire();
					addAttackDesire(c2, 1, 1000000);
					_thisActor.l_ai5 = _thisActor.c_ai2;
					broadcastScriptEvent(10002, getStoredIdFromCreature(_thisActor), null, 500);
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_mb2314_05m1");
		if(maker0 != null)
		{
			maker0.onScriptEvent(10005, 0, 0);
		}
		broadcastScriptEvent(11036, 2, null, 7000);
		super.onEvtDead(killer);
	}
}
