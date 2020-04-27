package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * @author: rage
 * @date: 06.09.11 16:46
 */
public class MonsterParameter extends DefaultAI
{
	public int AttackLowLevel = 0;
	public int RunAway = 1;
	public L2Skill SetCurse = null;
	public int AttackLowHP = 0;
	public int HelpHeroSilhouette = 0;
	public String HelpHeroAI = "warrior_hero";
	public int SetAggressiveTime = -1;
	public int HalfAggressive = 0;
	public int RandomAggressive = 0;
	public int SetHateGroup = -1;
	public int SetHateGroupRatio = 0;
	public int SetHateOccupation = -1;
	public int SetHateOccupationRatio = 0;
	public int SetHateRace = -1;
	public int SetHateRaceRatio = 0;
	public int IsTransform = 0;
	public int step1 = 1020130;
	public int step2 = 1020006;
	public int step3 = 1020853;
	public int DaggerBackAttack = 0;
	public int IsVs = 0;
	public L2Skill SpecialSkill = null;
	public int MoveAroundSocial = 0;
	public int MoveAroundSocial1 = 0;
	public int MoveAroundSocial2 = 0;
	public int IsSay = 0;
	public int ShoutMsg1 = 0;
	public int ShoutMsg2 = 0;
	public int ShoutMsg3 = 0;
	public int ShoutMsg4 = 0;
	public int SSQLoserTeleport = 0;
	public int SSQTelPosX = 0;
	public int SSQTelPosY = 0;
	public int SSQTelPosZ = 0;
	public int SwapPosition = 0;
	public int FriendShip = 0;
	public int DungeonType = 0;
	public int DungeonTypeAI = 0;
	public String DungeonTypePrivate = "";
	public int ShoutTarget = 0;
	public int AcceptShoutTarget = 0;
	public L2Skill SelfExplosion = null;
	public int FriendShip1 = 0;
	public int FriendShip2 = 0;
	public int FriendShip3 = 0;
	public int FriendShip4 = 0;
	public int FriendShip5 = 0;
	public int SoulShot = 0;
	public int SoulShotRate = 0;
	public int SpiritShot = 0;
	public int SpiritShotRate = 0;
	public int SpeedBonus = 0;
	public float HealBonus = 0;
	public int CreviceOfDiminsion = 0;
	public int LongRangeGuardRate = -1;
	public int SeeCreatureAttackerTime = -1;

	public MonsterParameter(L2Character actor)
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

	@Override
	protected boolean randomWalk()
	{
		return MoveAroundSocial > 0 && !_actor.isMoving && _actor.hasRandomWalk() && maybeMoveToHome();
	}
}
