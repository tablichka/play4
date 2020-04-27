package ai.base;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.commons.math.Rnd;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * @author: rage
 * @date: 02.09.11 20:53
 */
public class MonsterAI extends DefaultAI
{
	public int Party_Type = 0;
	public int Party_Loyalty = 0;
	public String Privates = "";
	public int Party_OneShot = 0;
	public int ShoutMsg1 = 0;
	public int ShoutMsg1_FString = 0;
	public int ShoutMsg1_Probablity = 0;
	public int ShoutMsg2 = 0;
	public int ShoutMsg2_FString = 0;
	public int ShoutMsg2_Probablity = 0;
	public int ShoutMsg3 = 0;
	public int ShoutMsg3_FString = 0;
	public int ShoutMsg3_Probablity = 0;
	public int ShoutMsg4 = 0;
	public int ShoutMsg4_FString = 0;
	public int ShoutMsg4_Probablity = 0;
	public int Social0 = 0;
	public int MoveAroundSocial = 0;
	public int Social0_Probablity = 2000;
	public int Social0_Timer = 10000;
	public int Social1 = 1;
	public int MoveAroundSocial1 = 0;
	public int Social1_Probablity = 2000;
	public int Social1_Timer = 10000;
	public int Social2 = 2;
	public int MoveAroundSocial2 = 0;
	public int Social2_Probablity = 2000;
	public int Social2_Timer = 10000;
	public int OutOfTerritory = 0;
	public float Attack_DecayRatio = 1f;
	public float UseSkill_DecayRatio = 1f;
	public float Attack_BoostValue = 1f;
	public float UseSkill_BoostValue = 1f;

	public MonsterAI(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onIntentionAttack(L2Character target)
	{
		setAttackTarget(target);
		changeIntention(AI_INTENTION_ATTACK, target, null);
		clientStopMoving();
		onEvtThink();
	}

	@Override
	protected void onEvtSpawn()
	{
		_useUDChance = getInt("ud_chance", 0);
		_inMyTerritory = true;
		if(_thisActor.isChampion() > 0)
		{
			_lastChampionTalk = System.currentTimeMillis() + Rnd.get(30, 120) * 1000;
			_useUDChance = 0;
		}
	}
}
