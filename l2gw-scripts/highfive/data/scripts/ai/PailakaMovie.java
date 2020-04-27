package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.SpecialCamera;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 15.10.2010 10:58:52
 */
public class PailakaMovie extends DefaultAI
{
	private L2NpcInstance _latana = null;
	private L2NpcInstance _dummy = null;
	private boolean _showWakeup = true;
	private L2Player _player;
	
	public PailakaMovie(L2Character actor)
	{
		super(actor);
		_actor.setImobilised(true);
		_actor.setIsInvul(true);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_latana == null || _dummy == null)
			for(L2NpcInstance npc : _thisActor.getKnownNpc(1000))
				if(_latana != null && _dummy != null)
					break;
				else if(npc.getNpcId() == 18660)
					_latana = npc;
				else if(npc.getNpcId() == 18605)
					_dummy = npc;

		if(_showWakeup)
		{
			L2Playable playable = null;
			for(L2Character cha : _thisActor.getKnownCharacters(900))
				if(cha instanceof L2Playable)
				{
					playable = (L2Playable) cha;
					break;
				}

			if(playable != null && playable.getPlayer() != null)
			{
				_showWakeup = false;
				_player = playable.getPlayer();
				_player.stopMove();
				if(_player.getPet() != null)
					_player.getPet().stopMove();

				_player.abortAttack();
				_player.abortCast();
				_player.block();
				_thisActor.broadcastPacket(new PlaySound(1, "BS08_A", 0, 0, new Location(0, 0, 0)));
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 600, 200, 5, 0, 10000, -10, 8, 1, 1, 1));
				addTimer(1, 2000);
			}
		}

		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		switch(timerId)
		{
			case 1:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 400, 200, 5, 4000, 10000, -10, 8, 1, 1, 0));
				addTimer(2, 2200);
				break;
			case 2:
				if(_latana != null)
					_latana.broadcastPacket(new SocialAction(_latana.getObjectId(), 0));
				addTimer(3, 1900);
				break;
			case 3:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 300, 195, 4, 1500, 10000, -5, 10, 1, 1, 0));
				addTimer(4, 2500);
				break;
			case 4:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 130, 2, 5, 0, 10000, 0, 0, 1, 0, 1));
				addTimer(41, 100);
				addTimer(5, 1500);
				addTimer(8, 12000);
				break;
			case 41:
				if(_latana != null)
				{
					_latana.doCast(SkillTable.getInstance().getInfo(5759, 1), _latana, false);
					_latana.startAttackStanceTask();
				}
				break;
			case 5:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 220, 0, 4, 800, 10000, 5, 10, 1, 0, 0));
				addTimer(6, 800);
				break;
			case 6:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 250, 185, 5, 4000, 10000, -5, 10, 1, 1, 0));
				addTimer(7, 4000);
				break;
			case 7:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 200, 0, 5, 2000, 10000, 0, 25, 1, 0 ,0));
				addTimer(9, 4500);
				break;
			case 8:
				if(_latana != null)
				{
					if(_dummy != null)
						_latana.doCast(SkillTable.getInstance().getInfo(5716, 1), _dummy, true);
					else
						_log.info(_thisActor + " dummy npc is null!");
				}
				break;
			case 9:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 300, -3, 5, 3500, 6000, 0, 6, 1, 0, 0));
				addTimer(10, 2000);
				break;
			case 10:
				if(_player != null)
				{
					_latana.addDamageHate(_player, 0, 100);
					_latana.startAttackStanceTask();
					_player.unblock();
					_player = null;
				}
				break;
			case 21:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 350, 200, 5, 5600, 10000, 15, 10, 1, 1, 0));
				addTimer(22, 4900);
				break;
			case 22:
				_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 360, 200, 5, 1000, 2000, -15, 10, 1, 1, 0));
				addTimer(23, 1000);
				break;
			case 23:
				if(_player != null)
				{
					_player.unblock();
					_player = null;
				}
				break;
			default:
				super.onEvtTimer(timerId, arg1, arg2);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20 && arg1 instanceof L2Player)
		{
			_player = (L2Player) arg1;
			_player.stopMove();
			_player.block();
			_thisActor.broadcastPacket(new SpecialCamera(_thisActor.getObjectId(), 450, 200, 3, 0, 10000, -15, 20, 1, 1, 1));
			addTimer(21, 15);
		}
	}
}
