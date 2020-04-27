package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.09.11 15:53
 */
public class RaidBossStandard extends DefaultAI
{
	public String RaidSpawnMusic = "Rm01_A";
	public int doSummonNPC = 0;
	public int RootingManage = 1;
	public int SSQLoserTeleport = 0;
	public int SSQTelPosX = 0;
	public int SSQTelPosY = 0;
	public int SSQTelPosZ = 0;
	public String AdvMakerGludio = "gludio08_npc1921_101m1";
	public String AdvMakerDion = "dion09_npc2022_101m1";
	public String AdvMakerGiran = "giran11_npc2222_101m1";
	public String AdvMakerOren = "oren17_npc2219_101m1";
	public String AdvMakerAden = "aden13_npc2418_101m1";
	public String AdvMakerInnadril = "innadril09_npc2324_101m1";
	public String AdvMakerGodard = "godard02_npc2416_101m1";
	public String AdvMakerRune = "rune02_npc2116_101m1";
	public String AdvMakerSchuttgart = "schuttgart20_npc2213_101m1";
	public int InzoneRestriction = 0;
	public int InzoneFinish = 0;
	public int CouldDespawn = 0;
	public int DespawnTime = 15;
	public int attack_weight_value = 20000;
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;
	public int SeeCreatureAttackerTime = -1;
	public int DBPosCheck = 0;
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	public L2Skill s_anti_strider_slow = SkillTable.getInstance().getInfo(279052289);

	public RaidBossStandard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.broadcastPacket(new PlaySound(0, RaidSpawnMusic, 0, 0, _thisActor.getLoc()));
		_thisActor.i_quest3 = 0;
		addTimer(1001, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(!_thisActor.inMyTerritory(_thisActor) && !_thisActor.isMoving)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				removeAllAttackDesire();
			}
			if(Rnd.get(5) < 1)
			{
				randomizeTargets();
			}
			/*
			if( CouldDespawn == 1 )
			{
				int i0 = ( myself.GetCurrentTick() - _thisActor.i_quest4 );
				if( i0 > ( DespawnTime * 60 ) )
				{
					if( InzoneRestriction == 1 || InzoneFinish == 1 )
					{
						myself.InstantZone_Finish(5);
					}
					_thisActor.onDecay();
				}
			}
			*/
			addTimer(1001, 60000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() && attacker.getPlayer().getMountEngine().isMounted() && attacker.getAbnormalLevelByType(s_anti_strider_slow.getId()) <= 0)
		{
			if(s_anti_strider_slow.getMpConsume() < _thisActor.getCurrentMp() && s_anti_strider_slow.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(s_anti_strider_slow.getId()))
			{
				addUseSkillDesire(attacker, s_anti_strider_slow, 0, 1, 1000000);
			}
		}
		if(_thisActor.isInZonePeace())
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			removeAllAttackDesire();
		}
		addAttackDesire(attacker, 1, DEFAULT_DESIRE);
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill.getEffectPoint() > 0)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(!_thisActor.isMoving && _thisActor.getMostHated() != caster)
				{
					addAttackDesire(caster, 1, skill.getEffectPoint() / _thisActor.getMaxHp() * 4000 * 150);
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(InzoneRestriction == 1)
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.successEnd();
		}
		if(InzoneFinish == 1)
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.rescheduleEndTask(300);
			//myself.InstantZone_Finish(5);
		}

		L2Player player = killer.getPlayer();
		if(player == null)
			return;

		if(doSummonNPC == 1)
		{
			L2Clan pledge0 = player.getClan();
			if(pledge0 == null)
			{
				return;
			}

			int i0 = pledge0.getHasCastle();
			if(i0 == 1)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerGludio);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 2)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerDion);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 3)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerGiran);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 4)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerOren);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 5)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerAden);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 6)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerInnadril);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 7)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerGodard);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 8)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerRune);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			else if(i0 == 9)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(AdvMakerSchuttgart);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
		}
	}
}
