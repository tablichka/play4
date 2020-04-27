package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 02.09.11 21:48
 */
public class WarriorBehavior extends MonsterBehavior
{
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	public float DefaultHate = 0.000000f;
	public int HateRace1 = -1;
	public float HateRace1Boost = 0.000000f;
	public int HateRace2 = -1;
	public float HateRace2Boost = 0.000000f;
	public int HateClass1 = -1;
	public float HateClass1Boost = 0.000000f;
	public int HateClass2 = -1;
	public float HateClass2Boost = 0.000000f;
	public int HateClass3 = -1;
	public float HateClass3Boost = 0.000000f;
	public int HateClassGroup1 = -1;
	public float HateClassGroup1Boost = 0.000000f;
	public int HateClassGroup2 = -1;
	public float HateClassGroup2Boost = 0.000000f;
	public float ATTACKED_Weight_Point = 10.000000f;
	public float CLAN_ATTACKED_Weight_Point = 1.000000f;
	public float PARTY_ATTACKED_Weight_Point = 1.000000f;
	public float SEE_SPELL_Weight_Point = 10.000000f;
	public float HATE_SKILL_Weight_Point = 10.000000f;

	public WarriorBehavior(L2Character actor)
	{
		super(actor);
	}

	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		if(_thisActor.param1 == 1000)
		{
			L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(cha != null && MovingAttack == 1)
				addAttackDesire(cha, 1, 500);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(IsHealer != 1)
		{
			L2Character top_desire_target = _thisActor.getMostHated();

			if(Party_Type == 2 && Rnd.get(10000) < Party_OneShot && top_desire_target != null)
			{
				if(attacker == top_desire_target)
					broadcastScriptEvent(15000001, _thisActor.getStoredId(), top_desire_target.getStoredId(), 500);
			}
			if(attacker.getPlayer() != null)
			{
				L2Player player = attacker.getPlayer();
				float f0 = DefaultHate;
				if(HateRace1 == player.getRace().ordinal())
					f0 += HateRace1Boost;
				if(HateRace2 == player.getRace().ordinal())
					f0 += HateRace2Boost;
				if(player.getActiveClass() == HateClass1)
					f0 += HateClass1Boost;
				if(player.getActiveClass() == HateClass2)
					f0 += HateClass2Boost;
				if(player.getActiveClass() == HateClass3)
					f0 += HateClass3Boost;
				if(HateClassGroup1 > -1 && CategoryManager.isInCategory(HateClassGroup1, player.getActiveClass()))
					f0 += HateClassGroup1Boost;
				if(HateClassGroup2 > -1 && CategoryManager.isInCategory(HateClassGroup2, player.getActiveClass()))
					f0 += HateClassGroup2Boost;
				if(f0 + 1 < 0)
					f0 = 0;
				else
					f0 = damage * (f0 + 1) - damage;

				_thisActor.addDamageHate(attacker, 0, (int) f0);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(IsHealer != 1)
		{
			L2Character top_desire_target = _thisActor.getMostHated();

			if(Party_Type == 2 && Rnd.get(10000) < Party_OneShot && top_desire_target != null)
			{
				if(attacker == top_desire_target)
					broadcastScriptEvent(15000001, _thisActor.getStoredId(), top_desire_target.getStoredId(), 500);
			}
			if(attacker.getPlayer() != null)
			{
				L2Player player = attacker.getPlayer();
				float f0 = DefaultHate;
				if(HateRace1 == player.getRace().ordinal())
					f0 += HateRace1Boost;
				if(HateRace2 == player.getRace().ordinal())
					f0 += HateRace2Boost;
				if(player.getActiveClass() == HateClass1)
					f0 += HateClass1Boost;
				if(player.getActiveClass() == HateClass2)
					f0 += HateClass2Boost;
				if(player.getActiveClass() == HateClass3)
					f0 += HateClass3Boost;
				if(HateClassGroup1 > -1 && CategoryManager.isInCategory(HateClassGroup1, player.getActiveClass()))
					f0 += HateClassGroup1Boost;
				if(HateClassGroup2 > -1 && CategoryManager.isInCategory(HateClassGroup2, player.getActiveClass()))
					f0 += HateClassGroup2Boost;
				if(f0 + 1 < 0)
					f0 = 0;
				else
					f0 = aggro * (f0 + 1) - aggro;

				_thisActor.addDamageHate(attacker, 0, (int) f0);
			}
		}

		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15000001)
		{
			L2Character cha = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(cha != null && _thisActor.getLeader() == cha)
			{
				L2Character target = L2ObjectsStorage.getAsCharacter((Long) arg2);
				if(target != null)
				{
					removeAllAttackDesire();
					addAttackDesire(target, 0, 100);
				}
			}
		}
	}
}
