package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 03.09.11 15:08
 */
public class AiShadowSummoner extends DetectPartyWarrior
{
	public int SummonTimer = 2010506;
	public int FeedTimer = 2010507;
	public int limitTimer = 2010508;
	public int delayTimer = 2010509;
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(447938561);
	public int isChasePC = 2500;
	public int test_id = 1022814;

	public AiShadowSummoner(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.c_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.isInRange(_thisActor.getSpawnedLoc(), isChasePC))
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc());

		if(attacker.isPlayer())
			_thisActor.c_ai0 = attacker.getStoredId();

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.25 && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addTimer(SummonTimer, 1000);
			addTimer(FeedTimer, 30000);
			addTimer(limitTimer, 600000);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == limitTimer)
			_thisActor.i_ai3 = 1;
		else if(timerId == SummonTimer && _thisActor.i_ai3 == 0)
		{
			addTimer(delayTimer, 5000);
			addTimer(SummonTimer, 30000);
		}
		else if(timerId == delayTimer)
		{
			Location pos = Location.coordsRandomize(_thisActor, 150);
			L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			long target = cha != null ? cha.getStoredId() : 0;
			if(Rnd.chance(50))
				_thisActor.createOnePrivate(25730, "AiFeastBoomer", 0, 0, pos.getX(), pos.getY(), pos.getZ(), 0, target, 0, 0);
			else
				_thisActor.createOnePrivate(25731, "AiFeastFeeder", 0, 0, pos.getX(), pos.getY(), pos.getZ(), 0, target, 0, 0);
		}
		else if(timerId == FeedTimer)
		{
			_thisActor.lookNeighbor(450);
			addTimer(FeedTimer, 30000);
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isNpc() && creature.getNpcId() == 25731)
		{
			removeAllAttackDesire();
			addAttackDesire(creature, 1, 9999999999999999L);
		}
	}
}
