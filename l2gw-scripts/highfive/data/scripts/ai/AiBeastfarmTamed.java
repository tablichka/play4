package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 09.09.11 6:23
 */
public class AiBeastfarmTamed extends DefaultAI
{
	public L2Skill SKILL_control_attack = SkillTable.getInstance().getInfo(548012033);
	public L2Skill SKILL_control_follow = SkillTable.getInstance().getInfo(548077569);
	public L2Skill SKILL_control_buff = SkillTable.getInstance().getInfo(548143105);
	public L2Skill SKILL_control_byebye = SkillTable.getInstance().getInfo(549060609);
	public int ITEM_feed_item = 15474;
	public int ITEM_feed_adena = 15475;
	public int TIMER_eat_feed = 2115001;
	public int TIMER_confirm_pc = 2115002;
	public int TIMER_byebye = 2115004;
	public int TIMER_byebye2 = 2115011;
	public int TIMER_time_over = 2115005;
	public int TIMER_end_flee = 2115010;
	public L2Skill Buff1 = SkillTable.getInstance().getInfo(421462017);
	public L2Skill Buff2 = SkillTable.getInstance().getInfo(436862977);
	public L2Skill Buff3 = SkillTable.getInstance().getInfo(421658625);
	public L2Skill Buff4 = SkillTable.getInstance().getInfo(436928513);
	public L2Skill Buff5 = SkillTable.getInstance().getInfo(437059585);
	public L2Skill Buff6 = SkillTable.getInstance().getInfo(437256193);
	public L2Skill Buff7 = SkillTable.getInstance().getInfo(421527553);
	public L2Skill Buff8 = SkillTable.getInstance().getInfo(436994049);
	public L2Skill Buff9 = SkillTable.getInstance().getInfo(421593089);
	public L2Skill Buff10 = SkillTable.getInstance().getInfo(437125121);
	public L2Skill Buff11 = SkillTable.getInstance().getInfo(437190657);
	public int my_type = -1;

	public L2Skill skill01 = null;
	public L2Skill skill02 = null;
	private long lastEffectActionTime;

	public AiBeastfarmTamed(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addEffectActionDesire(1, 110, 1000000);
		int i1 = 0;
		if(_thisActor.param1 != 0)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
			if(c0 != null && c0.getPlayer() != null)
			{
				L2Player player = c0.getPlayer();
				_thisActor.l_ai0 = _thisActor.param1;
				_thisActor.setTitle(player.getName());

				QuestState st = player.getQuestState(20);
				if(st != null && st.getCond() == 1 && !st.haveQuestItems(7185) && !st.haveQuestItems(15533) && st.rollAndGiveLimited(15533, 1, 5, 1))
				{
					st.setCond(2);
					st.getQuest().showQuestMark(player);
					st.playSound(Quest.SOUND_MIDDLE);
				}

				L2Clan clan = player.getClanId() > 0 ? player.getClan() : null;
				L2Player c1 = clan != null ? clan.getLeader().getPlayer() : null;
				if(c1 != null && clan.getLevel() >= 4)
				{
					QuestState qs = c1.getQuestState(655);
					if(qs != null && c1.getItemCountByItemId(8084) < 10 && _thisActor.getLoc().distance3D(c1.getLoc()) < 2000)
					{
						if(qs.rollAndGiveLimited(8084, 1, 100, 9))
							if(c1.getItemCountByItemId(8084) >= 9)
							{
								qs.setCond(2);
								qs.getQuest().showQuestMark(c1);
								qs.playSound(Quest.SOUND_MIDDLE);
							}
							else
								qs.playSound(Quest.SOUND_ITEMGET);
					}
				}
			}
		}

		//addTimer(TIMER_byebye, 1500);
		if(_thisActor.param2 == 0)
		{
			_thisActor.i_ai1 = (Rnd.get(2) + 1);
		}
		else
		{
			_thisActor.i_ai1 = (int) _thisActor.param2;
		}

		if(my_type == 1)
		{
			i1 = 1801106;
		}
		else if(my_type == 2)
		{
			i1 = 1801107;
		}
		else if(my_type == 3)
		{
			i1 = 1801108;
		}
		else if(my_type == 4)
		{
			i1 = 1801109;
		}

		int i0 = Rnd.get(6);
		switch(i0)
		{
			case 0:
				skill01 = Buff1;
				skill02 = Buff2;
				_thisActor.changeFStrNickName(1801100, Util.intToFStr(i1));
				break;
			case 1:
				skill01 = Buff3;
				skill02 = Buff4;
				_thisActor.changeFStrNickName(1801101, Util.intToFStr(i1));
				break;
			case 2:
				skill01 = Buff5;
				skill02 = Buff6;
				_thisActor.changeFStrNickName(1801101, Util.intToFStr(i1));
				break;
			case 3:
				skill01 = Buff7;
				skill02 = Buff8;
				_thisActor.changeFStrNickName(1801103, Util.intToFStr(i1));
				break;
			case 4:
				skill01 = Buff9;
				skill02 = Buff10;
				_thisActor.changeFStrNickName(1801104, Util.intToFStr(i1));
				break;
			case 5:
				skill01 = Buff11;
				skill02 = null;
				_thisActor.changeFStrNickName(1801105, Util.intToFStr(i1));
				break;
		}

		addTimer(TIMER_eat_feed, 60000);
		addTimer(TIMER_confirm_pc, 60000);
		addTimer(TIMER_time_over, 30 * 60000);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead() || !_thisActor.isVisible())
			return true;

		L2Player owner = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
		if(owner == null)
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(!_thisActor.isMoving && !_thisActor.isCastingNow() && !_thisActor.isInRange(owner, 100))
		{
			_thisActor.setRunning();
			_thisActor.followToCharacter(owner, Rnd.get(50, 200));
		}
		else if(lastEffectActionTime < System.currentTimeMillis())
			if(Rnd.get(2) == 1)
			{
				lastEffectActionTime = System.currentTimeMillis() + 110000;
				addEffectActionDesire(1, 110, 1000000);
			}
			else
			{
				lastEffectActionTime = System.currentTimeMillis() + 80000;
				addEffectActionDesire(3, 80, 1000000);
			}

		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!attacker.isPlayer())
		{
			addAttackDesire(attacker, 1, (damage / 2));
		}
		else
		{
			addFleeDesire(attacker, 10);
			addTimer(TIMER_end_flee, 5000);
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
		if(c0 != null)
		{
			if(caster == c0 && caster.isPlayer())
			{
				if(skill == SKILL_control_follow)
				{
					clearTasks();
				}
				else if(skill == SKILL_control_buff)
				{
					clearTasks();
					addUseSkillDesire(c0, skill01, 1, 1, 10000000);
					if(skill02 != null)
					{
						addUseSkillDesire(c0, skill02, 1, 1, 10000000);
					}

					L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai0);
					if(c1 != null)
					{
						_thisActor.altUseSkill(skill01, c1);
						if(skill02 != null)
						{
							_thisActor.altUseSkill(skill02, c1);
						}
					}
				}
				else if(skill == SKILL_control_attack)
				{
					addEffectActionDesire(1, 110, 1000000);
					addTimer(TIMER_byebye, 2000);
				}
				else if(skill == SKILL_control_byebye)
				{
					addEffectActionDesire(1, 110, 1000000);
					addTimer(TIMER_byebye, 2000);
				}
			}
		}

		super.onEvtSpelled(skill, caster);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		int i0 = 0;
		if(timerId == TIMER_eat_feed)
		{
			addEffectActionDesire(2, 30, 1000001);
			addEffectActionDesire(3, 80, 1000000);
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				if(_thisActor.i_ai1 == 1 && c0.getItemCountByItemId(ITEM_feed_item) > 0)
				{
					c0.destroyItemByItemId(getClass().getSimpleName(), ITEM_feed_item, 1, _thisActor, true);
				}
				else if(_thisActor.i_ai1 == 2 && c0.getItemCountByItemId(ITEM_feed_adena) > 0)
				{
					c0.destroyItemByItemId(getClass().getSimpleName(), ITEM_feed_adena, 1, _thisActor, true);
				}
				else if(my_type == 1)
				{
					i0 = 1801106;
				}
				else if(my_type == 2)
				{
					i0 = 1801107;
				}
				else if(my_type == 3)
				{
					i0 = 1801108;
				}
				else if(my_type == 4)
				{
					i0 = 1801109;
				}
				if(_thisActor.i_ai1 == 1)
				{
					c0.sendMessage(new CustomMessage("fs1801094", c0).addString(new CustomMessage("fs" + i0, c0).toString()));
				}
				else
				{
					c0.sendMessage(new CustomMessage("fs1801095", c0).addString(new CustomMessage("fs" + i0, c0).toString()));
				}
				addEffectActionDesire(1, 110, 1000000);
				addTimer(TIMER_byebye, 2000);
			}
			addTimer(TIMER_eat_feed, (150 * 1000));
		}
		else if(timerId == TIMER_confirm_pc)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai0);
			if(c0 == null || _thisActor.getLoc().distance3D(c0.getLoc()) >= 2000)
			{
				addEffectActionDesire(1, 110, 1000000);
				addTimer(TIMER_byebye, 2000);
			}

			addTimer(TIMER_confirm_pc, (60 * 1000));
		}
		else if(timerId == TIMER_byebye)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai0);
			if(c0 != null)
			{
				addFleeDesire(c0, 500000);
			}
			addTimer(TIMER_byebye2, 2000);
		}
		else if(timerId == TIMER_byebye2)
		{
			_thisActor.onDecay();
		}
		else if(timerId == TIMER_time_over)
		{
			addEffectActionDesire(1, 110, 1000000);
			addTimer(TIMER_byebye, 2000);
		}
		else if(timerId == TIMER_end_flee)
		{
			clearTasks();
		}
	}
}
