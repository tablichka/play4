package npc.model;

import instances.FrintezzaBattleInstance;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.L2GameServerPacket;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.concurrent.ScheduledFuture;

/**
 * @author: rage
 * @date: 12.09.11 0:01
 */
public class L2FrintezzaInstance extends L2MonsterInstance
{
	private ScheduledFuture<?> _scheduledCastTask = null;
	private boolean _disabled;
	private long _lastMusic;
	private L2Skill[] _aggroSkills = new L2Skill[5];
	private CastTask _castTask;
	private String[] _melodyName = { "Fugue of Jubilation", "Requiem of Hatred", "Testimony of Darkness", "Rondo of Solitude", "Frenetic Toccata" };
	private L2RaidBossInstance _demon;
	private int _minDelay, _maxDelay;

	public L2FrintezzaInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		_disabled = true;
		_castTask = new CastTask();
		_scheduledCastTask = ThreadPoolManager.getInstance().scheduleGeneral(_castTask, 10000);
		_minDelay = 60000;
		_maxDelay = 120000;
		_lastMusic = System.currentTimeMillis() + _maxDelay;
		_aggroSkills[0] = SkillTable.getInstance().getInfo(5007, 1);
		_aggroSkills[1] = SkillTable.getInstance().getInfo(5007, 2);
		_aggroSkills[2] = SkillTable.getInstance().getInfo(5007, 3);
		_aggroSkills[3] = SkillTable.getInstance().getInfo(5007, 4);
		_aggroSkills[4] = SkillTable.getInstance().getInfo(5007, 5);
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(_scheduledCastTask != null)
		{
			_scheduledCastTask.cancel(true);
			_scheduledCastTask = null;
		}
		super.doDie(killer);
	}

	@Override
	public void deleteMe()
	{
		if(_scheduledCastTask != null)
		{
			_scheduledCastTask.cancel(true);
			_scheduledCastTask = null;
		}
		super.deleteMe();
	}

	public void setDisabled(boolean disabled)
	{
		if(!disabled && _scheduledCastTask == null)
			_scheduledCastTask = ThreadPoolManager.getInstance().scheduleGeneral(new CastTask(), 10000);

		_disabled = disabled;
	}

	public void setDemon(L2RaidBossInstance demon)
	{
		_demon = demon;
	}

	public L2RaidBossInstance getDemon()
	{
		return _demon;
	}

	public void abortMelody()
	{
		if(isCastingNow())
		{
			_lastMusic = System.currentTimeMillis() + Rnd.get(_minDelay, _maxDelay);
			if(_scheduledCastTask != null)
				_scheduledCastTask.cancel(true);
			abortCast();
			_castTask.run();
		}
	}

	private class CastTask implements Runnable
	{
		public void run()
		{
			if(_disabled)
				_scheduledCastTask = ThreadPoolManager.getInstance().scheduleGeneral(_castTask, 5000);
			else if(_lastMusic > System.currentTimeMillis())
			{
				broadcastPacket(new MagicSkillUse(L2FrintezzaInstance.this, L2FrintezzaInstance.this, 5006, 1, 34000, 0, false));
				_scheduledCastTask = ThreadPoolManager.getInstance().scheduleGeneral(_castTask, 32000);
			}
			else
			{
				int skillLvl = getSkillLevel();
				broadcastPacket(new ExShowScreenMessage(_melodyName[skillLvl], 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, true));
				doCast(_aggroSkills[skillLvl], L2FrintezzaInstance.this, null, false);
				_scheduledCastTask = ThreadPoolManager.getInstance().scheduleGeneral(_castTask, _aggroSkills[skillLvl].getHitTime() - 500);
				_lastMusic = _aggroSkills[skillLvl].getHitTime() + System.currentTimeMillis() + Rnd.get(_minDelay, _maxDelay);
			}
		}
	}

	private int getSkillLevel()
	{
		if(_demon == null || _demon.getNpcId() == 29046 && _demon.getCurrentHp() > _demon.getMaxMp() * 0.5)
		{
			if(Rnd.chance(20))
				return 0;
			else if(Rnd.chance(20))
				return 1;
			else
				return 3;
		}
		else if(_demon.getNpcId() == 29046)
		{
			_maxDelay = 90000;
			if(Rnd.chance(20))
				return 0;
			else if(Rnd.chance(20))
				return 1;
			else if(Rnd.chance(20))
				return 2;
			else
				return 3;
		}
		else
		{
			_minDelay = 30000;
			return Rnd.get(5);
		}
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isAttackingDisabled()
	{
		return true;
	}

	@Override
	public void broadcastPacket(L2GameServerPacket mov)
	{
		for(L2Character player : ((FrintezzaBattleInstance) getSpawn().getInstance()).getHallZone().getCharacters())
			if(player instanceof L2Player && player.getReflection() == getReflection())
				player.sendPacket(mov);
	}
}
