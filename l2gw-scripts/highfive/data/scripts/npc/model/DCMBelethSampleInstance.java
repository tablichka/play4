package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

import java.util.concurrent.ScheduledFuture;

/**
 * User: ic
 * Date: 06.11.2009
 */
public class DCMBelethSampleInstance extends L2MonsterInstance
{
	private Instance _inst;
	private ScheduledFuture _talker1, _talker2;
	private String shouts1[] = {
			"Pick me!",
			"I'm the real one!",
			"Don't be fooled! Don't be fooled! I'm the real one!",
			"Trust me!",
			"Find me!",
	};

	private String shouts2[] = {
			"Can't you even find out?",
			"I'm the real one!",
			"I'm the real one! Phew!!",
			"Not that, dude, I'm the real one!",
			"Find me!",
	};

	public DCMBelethSampleInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		_inst = getSpawn().getInstance();
		if(_inst == null)
			_log.warn(this + " has no instance WTF??");

		_talker1 = ThreadPoolManager.getInstance().scheduleGeneral(new MobTalk(this, shouts1), 1000);
		_talker2 = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new MobTalk(this, shouts2), 7000, 7000);
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(_talker1 != null)
			_talker1.cancel(true);
		if(_talker2 != null)
			_talker2.cancel(true);
		super.doDie(killer);
	}

	@Override
	public void deleteMe()
	{
		if(_talker1 != null)
			_talker1.cancel(true);
		if(_talker2 != null)
			_talker2.cancel(true);
		super.deleteMe();
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean canMoveToHome()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return false;
	}

	public static class MobTalk implements Runnable
	{
		L2NpcInstance _npc;
		String _shouts[];


		public MobTalk(L2NpcInstance npc, String shouts[])
		{
			_npc = npc;
			_shouts = shouts;
		}

		public void run()
		{
			if(Rnd.chance(66))
				Functions.npcSay(_npc, Say2C.ALL, _shouts[Rnd.get(_shouts.length)]);
		}
	}

}
