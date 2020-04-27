package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 17.09.11 15:26
 */
public class KegorSavedun extends DefaultAI
{
	public L2Skill Buff = SkillTable.getInstance().getInfo(411959297);
	public int TIMER_buff = 2314003;
	public int TIMER_grima = 2314013;
	public int ITEM_quest_potion = 15514;
	public String MAKER_grimas = "schuttgart29_2512_204m1";
	public String fnHi = "kegor_savedun001.htm";

	public KegorSavedun(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.equipItem(15280);
		_thisActor.i_ai0 = 0;
		_thisActor.l_ai1 = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(!talker.isQuestStarted(10284))
		{
			_thisActor.showPage(talker, "kegor_savedun_q10284_04.htm", 10284);
		}
		else if(talker.isQuestStarted(10284) && talker.getQuestState(10284).getMemoState() == 3)
		{
			QuestState st = talker.getQuestState(10284);
			st.rollAndGive(57, 296425, 100);
			st.addExpAndSp(921805, 82230);
			st.exitCurrentQuest(false);
			st.playSound(Quest.SOUND_FINISH);
			_thisActor.showPage(talker, "kegor_savedun_q10284_03.htm", 10284);
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.setNoUserTimeout(0);
		}
		else if(_thisActor.i_ai0 == 0)
		{
			_thisActor.showPage(talker, "kegor_savedun001.htm");
		}
		else if(_thisActor.i_ai0 == 1)
		{
			if(talker.isQuestStarted(10284) && talker.getQuestState(10284).getMemoState() == 2)
			{
				_thisActor.showPage(talker, "kegor_savedun_q10284_02.htm", 10284);
			}
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -2315)
		{
			if(reply == 1)
			{
				QuestState st = talker.getQuestState(10284);
				if(st != null && st.isStarted() && st.getMemoState() == 2)
				{
					if(st.getQuestItemsCount(ITEM_quest_potion) > 0 && _thisActor.i_ai0 == 0)
					{
						st.takeItems(15514, -1);
						_thisActor.showPage(talker, "kegor_savedun_q10284_01.htm", 10284);
						st.setCond(5);
						st.getQuest().showQuestMark(st.getPlayer());
						st.playSound(Quest.SOUND_MIDDLE);
						addTimer(TIMER_grima, 3000);
						addTimer(TIMER_buff, 3500);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai0 >= 1)
		{
			if(!attacker.isPlayer() && !CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				addAttackDesire(attacker, 1, (damage * 5));
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			_thisActor.l_ai1 = getStoredIdFromCreature(creature);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_buff)
		{
			broadcastScriptEvent(2117001, getStoredIdFromCreature(_thisActor), null, 1000);

			if(_thisActor.i_ai0 == 1)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
				if(c0 != null)
				{
					if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
					{
						addUseSkillDesire(c0, Buff, 1, 1, 10000000);
					}
				}
				addTimer(TIMER_buff, 30000);
			}
		}
		else if(timerId == TIMER_grima)
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst == null)
				return;

			DefaultMaker maker0 = inst.getMaker(MAKER_grimas);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			_thisActor.i_ai0 = 1;
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2117001)
		{
			if(_thisActor.i_ai0 == 1)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					if(c0 != _thisActor)
					{
						addAttackDesire(c0, 1, 1000);
					}
				}
			}
		}
		else if(eventId == 23141002)
		{
			clearTasks();
			_thisActor.stopMove();

			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai1);
			if(c0 != null)
			{
				addFollowDesire(c0, 5);

				Functions.npcSay(_thisActor, Say2C.ALL, 1801099);
				QuestState st = c0.getQuestState(10284);

				if(st != null && st.isStarted() && st.getMemoState() == 2)
				{
					st.setMemoState(3);
					st.setCond(6);
					st.getQuest().showQuestMark(c0);
					st.playSound(Quest.SOUND_MIDDLE);
					_thisActor.i_ai0 = 3;
				}
			}
			_thisActor.equipItem(0);
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.successEnd();
		}
		else if(eventId == 23140100)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 1000);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		Functions.npcSay(_thisActor, Say2C.ALL, 1801098);
		Instance inst = _thisActor.getInstanceZone();
		if(inst != null)
			inst.successEnd();
		super.onEvtDead(killer);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}