package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 04.09.11 19:13
 */
public class WizardBehavior extends MonsterBehavior
{
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	public int AttackRange = 2;
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

	public WizardBehavior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(AttackRange == 2)
			_thisActor.i_ai4 = 0;
		if(_thisActor.param1 == 1000)
		{
			L2Character target = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(target != null)
			{
				_thisActor.addDamageHate(target, 0, 500);
				addAttackDesire(_thisActor, 0, 500);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayable())
		{
			L2Player player = attacker.getPlayer();
			if(player != null)
			{
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

				_thisActor.addDamageHate(attacker, 0, (int) (f0 * ATTACKED_Weight_Point + Attack_BoostValue));
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayable())
		{
			L2Player player = attacker.getPlayer();
			if(player != null)
			{
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

				_thisActor.addDamageHate(attacker, 0, (int) (f0 * HATE_SKILL_Weight_Point + Attack_BoostValue));
			}
		}

		super.onEvtAggression(attacker, aggro, skill);
	}
}
