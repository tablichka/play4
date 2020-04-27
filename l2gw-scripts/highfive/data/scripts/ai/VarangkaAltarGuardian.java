package ai;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 17.08.2010 15:30:35
 */
public class VarangkaAltarGuardian extends DefaultAI
{
	private static final int PROTECTION_SOULS = 14848;
	private static final int BOSS_ID = 18808;
	private L2Skill _debuff;
	private long _nextDebuffTime;
	private long _spawnTime;
	private long _lastOkCheck;
	private L2RaidBossInstance _boss;
	private int _spawnerObjectId;

	public VarangkaAltarGuardian(L2Character actor)
	{
		super(actor);
		_debuff = _debuff_skills.length > 0 ? _debuff_skills[0] : null;
		_thisActor.setImobilised(true);
		_thisActor.setIsInvul(true);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_nextDebuffTime < System.currentTimeMillis())
		{
			_nextDebuffTime = System.currentTimeMillis() + 3000;
			for(L2Character cha : _thisActor.getKnownCharacters(2500))
				if(cha.getPlayer() != null && cha.getObjectId() != _spawnerObjectId && !cha.getPlayer().isInvisible())
				{
					if(cha.getPlayer().getItemCountByItemId(PROTECTION_SOULS) < 1)
					{
						if(GeoEngine.canSeeTarget(_thisActor, cha))
							_thisActor.altUseSkill(_debuff, cha);
						else
							_debuff.applyEffects(cha, cha, false);
					}
					else if(_spawnTime == 0 && _thisActor.isInRange(cha, 500))
						_spawnTime = System.currentTimeMillis() + 30000;
				}

			if(_boss == null && _spawnTime != 0 && _spawnTime < System.currentTimeMillis())
			{
				for(L2Player player : _thisActor.getAroundPlayers(500))
					if(player.getItemCountByItemId(PROTECTION_SOULS) > 0 && player.destroyItemByItemId("RaidSpawn", PROTECTION_SOULS, 1, _thisActor, true))
					{
						_spawnerObjectId = player.getObjectId();
						_lastOkCheck = System.currentTimeMillis();
						spawnRaidBoss(player);
						break;
					}

				if(_boss == null)
					_spawnTime = 0;
			}
			else if(_boss != null && _lastOkCheck + 5 * 60000 < System.currentTimeMillis())
			{
				_log.info(_thisActor + " check for despawn.");
				L2Player player = L2ObjectsStorage.getPlayer(_spawnerObjectId);
				if(player == null || !_thisActor.isInRange(player, 2000) || player.isDead())
				{
					_log.info(_thisActor + " despawn boss.");
					_boss.deleteMe();
					_boss = null;
					_spawnerObjectId = 0;
					_spawnTime = 0;
				}
				else
					_lastOkCheck = System.currentTimeMillis();
			}
		}

		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		ThreadPoolManager.getInstance().scheduleAi(new Runnable()
		{
			public void run()
			{
				if(_boss != null)
					_boss.deleteMe();
				_boss = null;
				_spawnTime = 0;
				_spawnerObjectId = 0;
			}
		}, 5000, true);
	}

	private void spawnRaidBoss(L2Player player)
	{
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(BOSS_ID));
			spawn.setLoc(new Location(74618, -101914, -960));
			spawn.setAmount(1);
			spawn.stopRespawn();
			_boss = (L2RaidBossInstance) spawn.spawnOne();
			((DarkShamanVarangka) _boss.getAI()).setGuard(_thisActor);
			_boss.addDamageHate(player, 0, 999);
			_boss.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
		catch(Exception e)
		{
			_log.warn(_thisActor + " can't spawn Raid Boss " + e);
		}
	}
}
