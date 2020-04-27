package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 29.11.2010 19:32:47
 * АИ для новогоднего эвента Saving Santa
 * http://www.lineage2.com/archive/2008/12/saving_santa_ev.html
 */
public class BRTurkey extends Citizen
{
	private static final int PHASE_START_ID = 3301;
	private static final int PHASE_RESULT_ID = 3302;
	private static final int PHASE_WAIT_ID1 = 3303;
	private static final int PHASE_WAIT_ID2 = 3304;
	private static final int PHASE_DESPAWN = 3305;
	private static final int PHASE_COUNT = 3307;
	private static final int PHASE_ANI = 3308;
	private static final int PHASE_READY_END = 3309;
	private static final int PHASE_DEFEAT = 3310;

	private static final L2Skill s_br_turkey_attack = SkillTable.getInstance().getInfo(23020, 1);
	private static final L2Skill s_equip_ultra_bomb = SkillTable.getInstance().getInfo(3405, 1);
	private static final L2Skill s_br_turkey_buff = SkillTable.getInstance().getInfo(23018, 1);
	private static final L2Skill s_br_turkey_gawi = SkillTable.getInstance().getInfo(23021, 1);
	private static final L2Skill s_br_turkey_bawi = SkillTable.getInstance().getInfo(23021, 2);
	private static final L2Skill s_br_turkey_bo = SkillTable.getInstance().getInfo(23021, 3);
	private static final L2Skill s_br_xmas_gawibawibo_cap_gawi = SkillTable.getInstance().getInfo(21014, 1);
	private static final L2Skill s_br_xmas_gawibawibo_cap_bawi = SkillTable.getInstance().getInfo(21015, 1);
	private static final L2Skill s_br_xmas_gawibawibo_cap_bo = SkillTable.getInstance().getInfo(21016, 1);
	private static final L2Skill s_br_turkey_gawi_confirmed = SkillTable.getInstance().getInfo(23019, 1);
	private static final L2Skill s_br_turkey_bawi_confirmed = SkillTable.getInstance().getInfo(23019, 2);
	private static final L2Skill s_br_turkey_bo_confirmed = SkillTable.getInstance().getInfo(23019, 3);
	private static final L2Skill s_br_turkey_pcwins_reset = SkillTable.getInstance().getInfo(23023, 1);
	private static final L2Skill s_b_k_stone = SkillTable.getInstance().getInfo(4578, 1);
	private static final L2Skill[] s_br_turkey_pcwins =
			{
					SkillTable.getInstance().getInfo(23022, 1),
					SkillTable.getInstance().getInfo(23022, 2),
					SkillTable.getInstance().getInfo(23022, 3),
					SkillTable.getInstance().getInfo(23022, 4),
					SkillTable.getInstance().getInfo(23022, 5),
					SkillTable.getInstance().getInfo(23022, 6),
					SkillTable.getInstance().getInfo(23022, 7),
					SkillTable.getInstance().getInfo(23022, 8),
					SkillTable.getInstance().getInfo(23022, 9),
					SkillTable.getInstance().getInfo(23022, 10),
			};

	private int _counter = 0;
	private int _castCount;
	private int i_ai0;
	private int i_ai1;
	private int i_ai2;

	public BRTurkey(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
	
	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1800720);
		Announcements.getInstance().announceToAll(new SystemMessage(6503));
		addTimer(PHASE_WAIT_ID1, 10000);
		addTimer(PHASE_COUNT, 60000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == PHASE_DESPAWN)
		{
			Announcements.getInstance().announceToAll(new SystemMessage(6505));
			_thisActor.deleteMe();
		}
		else if(timerId == PHASE_COUNT && _counter >= 0)
		{
			_counter++;
			if(_counter < _thisActor.i_ai1)
				addTimer(PHASE_COUNT, 60000);
		}
		else if(timerId == PHASE_DEFEAT)
		{
			L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0).getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 1);
			Announcements.getInstance().announceToAll(new SystemMessage(6504));
			_thisActor.doDie(null);
		}

		if(_counter == -1)
			return;

		if(ServerVariables.getInt("br_xmas_event") == 0)
			_counter = _thisActor.i_ai1;

		if(timerId == PHASE_START_ID)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800721 : 1800722);

			_thisActor.doCast(s_br_turkey_attack, _thisActor, false);
			addTimer(PHASE_ANI, 6000);
			addTimer(PHASE_READY_END, 8500);
			i_ai1 = 1;
			_castCount = 0;
		}
		else if(timerId == PHASE_RESULT_ID)
		{
			if(i_ai0 == 2)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800731 : 1800733);
				_thisActor.doCast(s_equip_ultra_bomb, _thisActor, false);
				addTimer(PHASE_DEFEAT, 3000);
			}
			else if(i_ai0 == 1)
			{
				if(Rnd.get(100) < 50)
				{
					if(Rnd.get(100) < 30)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1800727);
						_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 0));
					}
					else if(Rnd.get(100) < 50)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1800730);
						_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 2));
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1800728);
						_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 2));
					}
				}
				else
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1800729);
					_thisActor.doCast(s_br_turkey_buff, _thisActor, false);
				}
			}
			else
				Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800725 : 1800726);

			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 0));
			i_ai1 = 0;
			addTimer(PHASE_WAIT_ID1, 8000);
		}
		else if(timerId == PHASE_WAIT_ID1)
		{
			int i0 = Rnd.get(100);
			if(_counter >= 30)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800745);
				_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 1));
				addTimer(PHASE_DESPAWN, 5000);
				_counter = -1;
				return;
			}
			else if(_counter >= (30 - 2))
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800745);
				_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 3));
			}
			else if(i0 < 40)
				Functions.npcSay(_thisActor, Say2C.ALL, 1800723);
			else if(i0 < 70)
				Functions.npcSay(_thisActor, Say2C.ALL, 1800724);
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800735);
				_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 3));
			}

			addTimer(PHASE_WAIT_ID2, 6000);
		}
		else if(timerId == PHASE_WAIT_ID2)
		{
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 0));
			addTimer(PHASE_START_ID, 5000);
		}
		else if(timerId == PHASE_ANI)
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 4));
		else if(timerId == PHASE_READY_END)
		{
			if(i_ai1 == 1)
			{
				i_ai1 = 2;
				i_ai2 = Rnd.get(1, 3);
				if(i_ai2 == 1)
					_thisActor.doCast(s_br_turkey_gawi, _thisActor, false);
				else if(i_ai2 == 2)
					_thisActor.doCast(s_br_turkey_bawi, _thisActor, false);
				else if(i_ai2 == 3)
					_thisActor.doCast(s_br_turkey_bo, _thisActor, false);

				Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800723 : 1800724);

				i_ai0 = 0;
				checkPlayers(600);
				addTimer(PHASE_RESULT_ID, 10000);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster == null || skill == null)
			return;

		if(i_ai1 == 1 && _castCount < 30)
		{
			if(skill == s_br_xmas_gawibawibo_cap_gawi && skill.getLevel() == s_br_xmas_gawibawibo_cap_gawi.getLevel())
				_thisActor.altUseSkill(s_br_turkey_gawi_confirmed, caster);
			else if(skill == s_br_xmas_gawibawibo_cap_bawi && skill.getLevel() == s_br_xmas_gawibawibo_cap_bawi.getLevel())
				_thisActor.altUseSkill(s_br_turkey_bawi_confirmed, caster);
			else if(skill == s_br_xmas_gawibawibo_cap_bo && skill.getLevel() == s_br_xmas_gawibawibo_cap_bo.getLevel())
				_thisActor.altUseSkill(s_br_turkey_bo_confirmed, caster);

			_castCount++;
		}
	}

	private void checkPlayers(int radius)
	{
		for(L2Player player : _thisActor.getAroundLivePlayers(radius))
		{
			int i2 = 0;
			L2Effect effect = player.getEffectBySkillId(23019);
			int i0 = effect != null ? effect.getSkillLevel() : 0;
			if(i0 > 0)
			{
				player.stopEffect(23019);
				if(i0 == 1)
				{
					if(i_ai2 == 1)
						i2 = 0;
					else if(i_ai2 == 2)
						i2 = 1;
					else if(i_ai2 == 3)
						i2 = -1;
				}
				else if(i0 == 2)
				{
					if(i_ai2 == 1)
						i2 = -1;
					else if(i_ai2 == 2)
						i2 = 0;
					else if(i_ai2 == 3)
						i2 = 1;
				}
				else if(i0 == 3)
				{
					if(i_ai2 == 1)
						i2 = 1;
					else if(i_ai2 == 2)
						i2 = -1;
					else if(i_ai2 == 3)
						i2 = 0;
				}
			}

			effect = player.getEffectBySkillId(23022);
			int i1 = effect != null ? effect.getSkillLevel() : 0;

			if(i2 == 1)
			{
				i1 = 0;
				_thisActor.altUseSkill(s_br_turkey_pcwins_reset, player);
				if(Rnd.chance(30))
					_thisActor.altUseSkill(s_b_k_stone, player);
			}
			else if(i2 == -1)
			{
				if(i1 <= 0)
				{
					_thisActor.altUseSkill(s_br_turkey_pcwins[0], player);
					i1 = 1;
				}
				else
				{
					_thisActor.altUseSkill(s_br_turkey_pcwins[i1], player);
					i1++;
				}
			}

			if(i_ai0 < 1 && i1 >= 3)
				i_ai0 = 1;

			if(i_ai0 < 2 && i1 >= 4)
			{
				i_ai0 = 2;
				ServerVariables.set("br_xmas_event_pc", player.getObjectId());
			}
		}
	}
}
