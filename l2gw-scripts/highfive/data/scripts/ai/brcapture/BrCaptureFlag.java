package ai.brcapture;

import ai.base.DefaultNpc;
import events.Capture.Capture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 05.06.12 22:08
 */
public class BrCaptureFlag extends DefaultNpc
{
	private static final Log eventLog = LogFactory.getLog("capture");
	private GArray<L2Player> players = new GArray<>();

	public BrCaptureFlag(L2Character actor)
	{
		super(actor);
		_thisActor.i_ai0 = 0; // team
		_thisActor.i_ai1 = 50; // capture timer
		_thisActor.i_ai2 = 0; // state 0 - uncaptured, 1 - capturing, 2 - stopped, 3 - captured, 4 - recapturing
		_thisActor.i_ai3 = 0; // current capturing team
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(1000, 1000);
		Capture.addFlag(_thisActor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1000)
		{
			_thisActor.lookNeighbor(500);
			int team = 0;

			synchronized(players)
			{
				for(Long storedId : _thisActor.getNeighbors())
				{
					L2Player player = L2ObjectsStorage.getAsPlayer(storedId);
					if(player != null && !player.isDead() && player.getTeam() > 0 && player.isVisible() && !player.isHide())
					{
						team |= player.getTeam();
						if(!players.contains(player))
							players.add(player);
					}
				}
			}

			if(team == 3) // Обе комманды в радиусе захвата
			{
				if(_thisActor.i_ai2 == 1 || _thisActor.i_ai2 == 4) // Флаг в статусе захвата
				{
					_thisActor.setTeam(_thisActor.i_ai2 == 1 ? 0 : _thisActor.i_ai3);
					_thisActor.i_ai4 = _thisActor.i_ai2;
					_thisActor.i_ai2 = 2;
				}
				if(_thisActor.i_ai2 == 2)
				{
					for(L2Player player : players)
					{
						Functions.sendUIEventFStr(player, 1, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
						Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 1000, 0, 17);
					}
				}
			}
			else if(team > 0) // Одна комманда в радиусе захвата
			{
				if(_thisActor.i_ai2 == 0) // Флаг не захвачен, начать захват
				{
					_thisActor.i_ai1 = 50;
					_thisActor.i_ai2 = 1;
					_thisActor.i_ai3 = team;
					for(L2Player player : players)
					{
						if(player.getTeam() == team)
							Functions.sendUIEventFStr(player, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
					}
				}
				else if(_thisActor.i_ai2 == 1) // Флаг в процессе захвата
				{
					if(team != _thisActor.i_ai3) // Захват другой коммандой, начинаем сначала
					{
						_thisActor.i_ai1 = 50;
						_thisActor.i_ai2 = 1;
						_thisActor.i_ai3 = team;
						for(L2Player player : players)
						{
							Functions.sendUIEventFStr(player, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
						}
					}
					else
						_thisActor.i_ai1--;

					_thisActor.setTeam(_thisActor.getTeam() > 0 ? 0 : _thisActor.i_ai3);

					if(_thisActor.i_ai1 == 10)
						for(L2Player player : players)
							Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 10000, 0, 12);
					else if(_thisActor.i_ai1 == 0)
					{
						_thisActor.i_ai2 = 3;
						_thisActor.setTeam(_thisActor.i_ai3);
						for(L2Player player : players)
						{
							Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 5000, 0, 14, _thisActor.getName());
							Functions.showOnScreentMsg(player, 8, 0, 0, 0, 0, 0, 5000, 0, 28, _thisActor.getName(), String.valueOf(Config.CAPTURE_POINTS_FLAG_CAPTURE));
							Capture.addPoints(player, Config.CAPTURE_POINTS_FLAG_CAPTURE, "flag_capture");
							eventLog.info("flag_capture: " + player + "(" + player.getTeam() + ") at " + player.getLoc() + " flag: " + _thisActor + " at " + _thisActor.getLoc() + " add points: " + Config.CAPTURE_POINTS_FLAG_CAPTURE);
						}
						for(L2Player player : Capture.getAllPlayers())
						{
							if(!players.contains(player))
								Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 5000, 0, 21 + _thisActor.i_ai3, _thisActor.getName());
						}
					}
				}
				else if(_thisActor.i_ai2 == 2) // Захват был приостановлен, возобновляем, если таже комманда
				{
					if(_thisActor.i_ai3 != team)
					{
						_thisActor.i_ai1--;
						_thisActor.i_ai2 = _thisActor.i_ai4;
						if(_thisActor.i_ai2 == 1)
							_thisActor.i_ai3 = team;
					}
					else
					{
						_thisActor.i_ai2 = _thisActor.i_ai4;
						_thisActor.i_ai1--;
					}

					for(L2Player player : players)
					{
						if(player.getTeam() != _thisActor.i_ai3)
							Functions.sendUIEventFStr(player, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", _thisActor.i_ai4 == 1 ? 11 : 13);
					}
				}
				else if(_thisActor.i_ai2 == 3) // Флаг захвачен
				{
					if(_thisActor.i_ai3 != team) // Перезахват флага
					{
						_thisActor.i_ai1 = 50;
						_thisActor.i_ai2 = 4;

						for(L2Player player : players)
						{
							Functions.sendUIEventFStr(player, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 13);
						}
					}
				}
				else if(_thisActor.i_ai2 == 4) // Перезахват флага
				{
					if(_thisActor.i_ai3 != team)
					{
						_thisActor.i_ai1--;
						_thisActor.setTeam(_thisActor.getTeam() > 0 ? 0 : _thisActor.i_ai3);

						if(_thisActor.i_ai1 == 10)
							for(L2Player player : players)
								Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 10000, 0, 12);
						else if(_thisActor.i_ai1 == 0)
						{
							_thisActor.i_ai1 = 50;
							_thisActor.i_ai2 = 0;
							_thisActor.i_ai3 = team;
							_thisActor.setTeam(0);

							for(L2Player player : players)
							{
								Functions.sendUIEventFStr(player, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
								Functions.showOnScreentMsg(player, 8, 0, 0, 0, 0, 0, 5000, 0, 29, _thisActor.getName(), String.valueOf(Config.CAPTURE_POINTS_FLAG_ATTACK));
								Capture.addPoints(player, Config.CAPTURE_POINTS_FLAG_ATTACK, "flag_attack");
								eventLog.info("flag_attack: " + player + "(" + player.getTeam() + ") at " + player.getLoc() + " flag: " + _thisActor + " at " + _thisActor.getLoc() + " add points: " + Config.CAPTURE_POINTS_FLAG_ATTACK);
							}

							for(L2Player player : Capture.getAllPlayers())
							{
								if(!players.contains(player))
									Functions.showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 5000, 0, 23 + _thisActor.i_ai3, _thisActor.getName());
							}
						}
					}
					else
					{
						_thisActor.i_ai2 = 3;
						_thisActor.setTeam(_thisActor.i_ai3);
					}
				}
			}
			else
			{
				if(_thisActor.i_ai2 == 4)
				{
					_thisActor.i_ai2 = 3;
					_thisActor.setTeam(_thisActor.i_ai3);
				}
				else if(_thisActor.i_ai2 == 1)
				{
					_thisActor.i_ai2 = 0;
					_thisActor.setTeam(0);
				}

				_thisActor.i_ai1 = 50;
			}

			players.clear();
			addTimer(1000, 1000);
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character character)
	{
		if(character instanceof L2Player && character.getTeam() > 0)
		{
			if(_thisActor.i_ai2 <= 1)
			{
				if(_thisActor.i_ai1 < 10)
					Functions.showOnScreentMsg((L2Player) character, 2, 0, 0, 0, 0, 0, _thisActor.i_ai1 * 1000, 0, 12);
				else
					Functions.sendUIEventFStr(character, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
			}
			else if(_thisActor.i_ai2 == 2)
				Functions.showOnScreentMsg((L2Player) character, 2, 0, 0, 0, 0, 0, 1000, 0, 17);
			else if(_thisActor.i_ai2 == 4)
			{
				if(_thisActor.i_ai1 < 10)
					Functions.showOnScreentMsg((L2Player) character, 2, 0, 0, 0, 0, 0, _thisActor.i_ai1 * 1000, 0, 12);
				else
					Functions.sendUIEventFStr(character, 0, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 13);
			}
		}
	}

	@Override
	protected void onEvtCreatureLost(L2Character character, int objectId)
	{
		if(character instanceof L2Player && character.getTeam() > 0)
		{
			if(_thisActor.i_ai1 > 10)
				Functions.sendUIEventFStr(character, 1, 0, 0, "0", "0", String.valueOf(_thisActor.i_ai1 - 2), "0", "0", 11);
		}
	}
}
