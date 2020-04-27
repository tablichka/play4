package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 04.10.11 15:47
 */
public class Ssq2Elcardia1Silence extends DefaultNpc
{
	public int p_SCEN_ID_SSQ2_HOLY_BURIAL_GROUND_OPENING = 24;
	public int p_SCEN_ID_SSQ2_HOLY_BURIAL_GROUND_CLOSING = 25;
	public int p_SCEN_ID_SSQ2_SOLINA_TOMB_OPENING = 26;
	public int p_SCEN_ID_SSQ2_SOLINA_TOMB_CLOSING = 27;
	public int p_SCEN_ID_SSQ2_ELYSS_NARRATION = 28;
	public int p_SCEN_ID_SSQ2_BOSS_OPENING = 29;
	public int p_SCEN_ID_SSQ2_BOSS_CLOSING = 30;
	public int p_SCEN_TIME_SSQ2_HOLY_BURIAL_GROUND_OPENING = 26000;
	public int p_SCEN_TIME_SSQ2_HOLY_BURIAL_GROUND_CLOSING = 25000;
	public int p_SCEN_TIME_SSQ2_SOLINA_TOMB_OPENING = 28000;
	public int p_SCEN_TIME_SSQ2_SOLINA_TOMB_CLOSING = 18000;
	public int p_SCEN_TIME_SSQ2_ELYSS_NARRATION = 59000;
	public int p_SCEN_TIME_SSQ2_BOSS_OPENING = 63000;
	public int p_SCEN_TIME_SSQ2_BOSS_CLOSING = 63000;
	public int p_TIMER_SSQ2_HOLY_BURIAL_GROUND_OPENING = 1000;
	public int p_TIMER_SSQ2_HOLY_BURIAL_GROUND_OPENING_GAP = 2000;
	public int p_TIMER_SSQ2_SOLINA_TOMB_OPENING = 1001;
	public int p_TIMER_SSQ2_SOLINA_TOMB_OPENING_GAP = 2000;
	public int p_TIMER_USE_SKILL_TRUE = 1002;
	public int p_TIMER_TEL_OUTER_SPACE = 1003;
	public int p_TIMER_ID_TALK = 1007;
	public int p_TIMER_GAP_TALK = 10000;
	public int p_ASK_BUFF = -6;
	public int p_REP_BUFF = 1;
	public L2Skill skill1 = SkillTable.getInstance().getInfo(441057281);
	public L2Skill skill2 = SkillTable.getInstance().getInfo(440664065);
	public L2Skill skill3 = SkillTable.getInstance().getInfo(440729601);
	//public L2Skill skill4 = SkillTable.getInstance().getInfo(440795137);
	public L2Skill skill5 = SkillTable.getInstance().getInfo(440860673);
	public L2Skill skill6 = SkillTable.getInstance().getInfo(440926209);
	public L2Skill skill7 = SkillTable.getInstance().getInfo(440991745);
	public L2Skill buff1 = SkillTable.getInstance().getInfo(440008705);
	public L2Skill buff2 = SkillTable.getInstance().getInfo(440074241);
	public L2Skill buff3 = SkillTable.getInstance().getInfo(440139777);
	public L2Skill buff4 = SkillTable.getInstance().getInfo(440205313);
	public L2Skill buff5 = SkillTable.getInstance().getInfo(440336385);
	public L2Skill buff6 = SkillTable.getInstance().getInfo(440401921);
	public L2Skill buff7 = SkillTable.getInstance().getInfo(440467457);
	public L2Skill buff8 = SkillTable.getInstance().getInfo(440532993);
	public L2Skill buff9 = SkillTable.getInstance().getInfo(440598529);


	public Ssq2Elcardia1Silence(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80008, _thisActor.id);
		addTimer(2100, 1000);
		addTimer(p_TIMER_ID_TALK, p_TIMER_GAP_TALK);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 1;
		_thisActor.lookNeighbor(300);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			_thisActor.c_ai0 = creature.getStoredId();
			_thisActor.setRunning();
			addFollowDesire2(creature, 9000, 40, 80);
		}
	}

	@Override
	protected void onEvtNoDesire()
	{
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(cha != null)
			addFollowDesire2(cha, 9000, 40, 80);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2100)
		{
			_thisActor.lookNeighbor(300);
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				if(_thisActor.i_ai4 == 1)
				{
					if(c0.getCurrentHp() / c0.getMaxHp() < 0.400000)
					{
						if(skill1.getMpConsume() < _thisActor.getCurrentMp() && skill1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill1.getId()))
						{
							addUseSkillDesire(c0, skill1, 1, 1, 1000000);
						}
					}
					else if(c0.getCurrentHp() / c0.getMaxHp() < 0.800000)
					{
						if(skill2.getMpConsume() < _thisActor.getCurrentMp() && skill2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill2.getId()))
						{
							addUseSkillDesire(c0, skill2, 1, 1, 1000000);
						}
					}

					if(_thisActor.i_ai0 == 1 || c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() >= 10 || c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() >= 1 || c0.getQuestState(10296) != null && c0.getQuestState(10296).getMemoState() >= 1)
					{
						if(c0.getAbnormalLevelByType(skill3.getId()) <= 0)
						{
							if(skill3.getMpConsume() < _thisActor.getCurrentMp() && skill3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill3.getId()))
							{
								addUseSkillDesire(c0, skill3, 1, 1, 1000000);
							}
						}
						/*
						if(c0.getAbnormalLevelByType(skill4.getId()) <= 0)
						{
							if(skill4.getMpConsume() < _thisActor.getCurrentMp() && skill4.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill4.getId()))
							{
								addUseSkillDesire(c0, skill4, 1, 1, 1000000);
							}
						}
						*/
					}

					if(_thisActor.i_ai1 == 1 || c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() >= 10 || c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() >= 1 || c0.getQuestState(10296) != null && c0.getQuestState(10296).getMemoState() >= 1)
					{
						if(c0.getAbnormalLevelByType(skill5.getId()) <= 0)
						{
							if(skill5.getMpConsume() < _thisActor.getCurrentMp() && skill5.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill5.getId()))
							{
								addUseSkillDesire(c0, skill5, 1, 1, 1000000);
							}
						}
					}

					if(_thisActor.i_ai2 == 1 || c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() >= 10 || c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() >= 1 || c0.getQuestState(10296) != null && c0.getQuestState(10296).getMemoState() >= 1)
					{
						if(c0.getCurrentMp() / c0.getMaxMp() < 0.8)
						{
							if(skill6.getMpConsume() < _thisActor.getCurrentMp() && skill6.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill6.getId()))
							{
								addUseSkillDesire(c0, skill6, 1, 1, 1000000);
							}
						}
					}

					if(_thisActor.i_ai3 == 1 || c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() >= 10 || c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() >= 1 || c0.getQuestState(10296) != null && c0.getQuestState(10296).getMemoState() >= 1)
					{
						if(c0.getAbnormalLevelByType(skill7.getId()) <= 0)
						{
							if(skill7.getMpConsume() < _thisActor.getCurrentMp() && skill7.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill7.getId()))
							{
								addUseSkillDesire(c0, skill7, 1, 1, 1000000);
							}
						}
					}
				}
				addTimer(2100, 5000);
			}
			else
				addTimer(2100, 1000);
		}
		else if(timerId == p_TIMER_SSQ2_HOLY_BURIAL_GROUND_OPENING)
		{
			Functions.startScenePlayer(L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0), p_SCEN_ID_SSQ2_HOLY_BURIAL_GROUND_OPENING);
		}
		else if(timerId == p_TIMER_SSQ2_SOLINA_TOMB_OPENING)
		{
			Functions.startScenePlayer(L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0), p_SCEN_ID_SSQ2_SOLINA_TOMB_OPENING);
		}
		else if(timerId == p_TIMER_TEL_OUTER_SPACE)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
				c0.teleToLocation(76736, -241021, -10780);

			_thisActor.teleToLocation(76736, -241021, -10780);
			L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32792);
			//int i0 = ServerVariables.getInt("GM_" + 80009);
			if(c1 != null)
			{
				c1.teleToLocation(120881, -86496, -3399);
			}
		}
		else if(timerId == p_TIMER_USE_SKILL_TRUE)
		{
			_thisActor.i_ai4 = 1;
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
				_thisActor.teleToLocation(c0.getLoc());
		}
		else if(timerId == p_TIMER_ID_TALK)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				if(c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() == 9)
				{
					switch(Rnd.get(3))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029450);
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029451);
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029452);
							break;
					}
				}
				else if(c0.getQuestState(10294) != null && c0.getQuestState(10294).getMemoState() == 10)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1029453);
				}
				else if(c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() == 1)
				{
					switch(Rnd.get(3))
					{
						case 0:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029550);
							break;
						case 1:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029551);
							break;
						case 2:
							Functions.npcSay(_thisActor, Say2C.ALL, 1029552);
							break;
					}
				}
				else if(c0.getQuestState(10295) != null && c0.getQuestState(10295).getMemoState() == 2)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1029253);
				}
			}
			addTimer(p_TIMER_ID_TALK, p_TIMER_GAP_TALK);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90112)
		{
			_thisActor.c_ai0 = 0;
			clearTasks();
			_thisActor.stopMove();
		}
		else if(eventId == 90106)
		{
			_thisActor.i_ai0 = 1;
		}
		else if(eventId == 90107)
		{
			_thisActor.i_ai1 = 1;
		}
		else if(eventId == 90108)
		{
			_thisActor.i_ai2 = 1;
		}
		else if(eventId == 90109)
		{
			_thisActor.i_ai3 = 1;
		}
		else if(eventId == 90103 && (Integer) arg1 == 4)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null && c0.getQuestState(10294) != null)
			{
				c0.getQuestState(10294).setMemoState(10);
			}
		}
		else if(eventId == 90105)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null && c0.getQuestState(10295) != null)
			{
				c0.getQuestState(10295).setMemoState(2);
			}
		}
		else if(eventId == 90207)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null && c0.getQuestState(10295) != null)
			{
				c0.getQuestState(10295).setMemoState(3);
			}
		}
		else if(eventId == 90209)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null && c0.getQuestState(10296) != null)
			{
				c0.getQuestState(10296).setMemoState(3);
			}
		}
		else if(eventId == 90310)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				addTimer(p_TIMER_SSQ2_HOLY_BURIAL_GROUND_OPENING, p_TIMER_SSQ2_HOLY_BURIAL_GROUND_OPENING_GAP);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_HOLY_BURIAL_GROUND_OPENING);
			}
		}
		else if(eventId == 90311)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				Functions.startScenePlayer(c0, p_SCEN_ID_SSQ2_HOLY_BURIAL_GROUND_CLOSING);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_HOLY_BURIAL_GROUND_CLOSING);
			}
		}
		else if(eventId == 90312)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				addTimer(p_TIMER_SSQ2_SOLINA_TOMB_OPENING, p_TIMER_SSQ2_SOLINA_TOMB_OPENING_GAP);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_SOLINA_TOMB_OPENING);
			}
		}
		else if(eventId == 90313)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				Functions.startScenePlayer(c0, p_SCEN_ID_SSQ2_SOLINA_TOMB_CLOSING);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_SOLINA_TOMB_CLOSING);
			}
		}
		else if(eventId == 90314)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				Functions.startScenePlayer(c0, p_SCEN_ID_SSQ2_ELYSS_NARRATION);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_ELYSS_NARRATION);
			}
		}
		else if(eventId == 90315)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				Functions.startScenePlayer(c0, p_SCEN_ID_SSQ2_BOSS_OPENING);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_BOSS_OPENING);
				addTimer(p_TIMER_TEL_OUTER_SPACE, (p_SCEN_TIME_SSQ2_BOSS_OPENING - 3000));
				_thisActor.teleToLocation(115927, -87005, -3392);
				L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32792);
				if(c1 != null)
				{
					c1.teleToLocation(115927, -87005, -3392);
				}
			}
		}
		else if(eventId == 90316)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				Functions.startScenePlayer(c0, p_SCEN_ID_SSQ2_BOSS_CLOSING);
				_thisActor.i_ai4 = 0;
				addTimer(p_TIMER_USE_SKILL_TRUE, p_SCEN_TIME_SSQ2_BOSS_CLOSING);
				_thisActor.teleToLocation(0, 0, 0);
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == p_ASK_BUFF && reply == p_REP_BUFF)
		{
			if(CategoryManager.isInCategory(0, talker.getActiveClass()))
			{
				if(buff1.getMpConsume() < _thisActor.getCurrentMp() && buff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff1.getId()))
				{
					addUseSkillDesire(talker, buff1, 1, 1, 1000009);
				}
				if(buff2.getMpConsume() < _thisActor.getCurrentMp() && buff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff2.getId()))
				{
					addUseSkillDesire(talker, buff2, 1, 1, 1000008);
				}
				if(buff3.getMpConsume() < _thisActor.getCurrentMp() && buff3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff3.getId()))
				{
					addUseSkillDesire(talker, buff3, 1, 1, 1000007);
				}
				if(buff4.getMpConsume() < _thisActor.getCurrentMp() && buff4.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff4.getId()))
				{
					addUseSkillDesire(talker, buff4, 1, 1, 1000006);
				}
			}
			else if(buff1.getMpConsume() < _thisActor.getCurrentMp() && buff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff1.getId()))
			{
				addUseSkillDesire(talker, buff1, 1, 1, 1000009);
			}
			if(buff5.getMpConsume() < _thisActor.getCurrentMp() && buff5.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff5.getId()))
			{
				addUseSkillDesire(talker, buff5, 1, 1, 1000008);
			}
			if(buff6.getMpConsume() < _thisActor.getCurrentMp() && buff6.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff6.getId()))
			{
				addUseSkillDesire(talker, buff6, 1, 1, 1000007);
			}
			if(buff7.getMpConsume() < _thisActor.getCurrentMp() && buff7.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff7.getId()))
			{
				addUseSkillDesire(talker, buff7, 1, 1, 1000006);
			}
			if(buff8.getMpConsume() < _thisActor.getCurrentMp() && buff8.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff8.getId()))
			{
				addUseSkillDesire(talker, buff8, 1, 1, 1000005);
			}
			if(buff9.getMpConsume() < _thisActor.getCurrentMp() && buff9.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(buff9.getId()))
			{
				addUseSkillDesire(talker, buff9, 1, 1, 1000004);
			}
		}
		/*
		else if( ask == 10295 && reply == 1 )
		{
			talker.teleToLocation( 55955, -250394, -6792);
			int i0 = ServerVariables.getInt("GM_" + 80008);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(i0);
			if( c0 != null )
			{
				myself.DebugSay("cElcardia н:"л _н_┐н_ё");
				c0.teleToLocation( 55955, -250394, -6792);
			}
			else
			{
				myself.DebugSay("cElcardia NULL");
			}
		}
		else if( ask == 10296 && reply == 1 )
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.setNoUserTimeout(60000);
			talker.teleToClosestTown();
		}
		*/
		else
			super.onMenuSelected(talker, ask, reply);
	}
}