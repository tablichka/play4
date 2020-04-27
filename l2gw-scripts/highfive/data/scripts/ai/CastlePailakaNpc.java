package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 02.12.2010 14:43:50
 */
public class CastlePailakaNpc extends DefaultAI
{
	protected L2Skill Buff, Skill01, Skill02;
	protected int String_Num1;
	protected int String_Num2;
	protected int String_Num3;
	protected int String_Num4;
	protected int String_Num5;
	protected int String_Num6;
	protected int Skill01_Prob, Skill02_Prob;

	protected int i_ai2, i_ai3, i_ai4;

	protected static final int NPC_ANNOUNCE = 2117001;
	protected static final int KNIGHT_ATTACKED = 2117004;
	protected static final int RANGER_ATTACKED = 2117005;
	protected static final int INVADER = 2117006;
	protected static final int TIMEOUT = 2117007;
	protected static final int FINAL_BOSS_KILLED = 2117009;
	protected static final int CLAN_DIED = 2117020;

	public CastlePailakaNpc(L2Character actor)
	{
		super(actor);

		Buff = _buff_skills.length > 0 ? _buff_skills[0] : null;
		Skill01 = _mdam_skills.length > 0 ? _mdam_skills[0] : null;
		Skill02 = _mdam_skills.length > 1 ? _mdam_skills[1] : null;
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		String_Num1 = getInt("String_Num1", -1);
		String_Num2 = getInt("String_Num2", -1);
		String_Num3 = getInt("String_Num3", -1);
		String_Num4 = getInt("String_Num4", -1);
		String_Num5 = getInt("String_Num5", -1);
		String_Num6 = getInt("String_Num6", -1);
		Skill01_Prob = getInt("Skill01_Prob", 20);
		Skill02_Prob = getInt("Skill02_Prob", 20);

		i_ai3 = _thisActor.getMaxHp();
		addTimer(5617, 120000);
		addTimer(5620, 5000);

		if(String_Num1 > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, String_Num1);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 5617)
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				for(L2Player player : inst.getPlayersInside())
					if(_thisActor.isInRange(player, 75))
						_thisActor.altUseSkill(Buff, player);

			addTimer(5617, 120000);
		}
		else if(timerId == 5620)
		{
			broadcastScriptEvent(NPC_ANNOUNCE, _thisActor, null, 1500);
			addTimer(5620, 10000);
		}
		else if(timerId == 5634)
			_thisActor.doDie(null);
		else
			super.onEvtTimer(timerId, arg1 ,arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == FINAL_BOSS_KILLED || eventId == TIMEOUT)
		{
			_thisActor.i_ai0 = 2;
			if(String_Num6 > 0)
				Functions.npcSay(_thisActor, Say2C.ALL, String_Num6);

			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				for(L2Player player : inst.getPlayersInside())
					if(inst.getTemplate().getId() >= 80 && inst.getTemplate().getId() <= 88)
					{
						QuestState qs = player.getQuestState(727);
						if(qs != null && qs.getInt("ex_cond") == 2)
							qs.set("ex_cond", 3);
					}
					else if(inst.getTemplate().getId() >= 89 && inst.getTemplate().getId() <= 109)
					{
						QuestState qs = player.getQuestState(726);
						if(qs != null && qs.getInt("ex_cond") == 2)
							qs.set("ex_cond", 3);
					}
		}
		else if(eventId == CLAN_DIED)
		{
			if(i_ai4 == 0)
			{
				addTimer(5634, 1500);
				i_ai4 = 1;
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(String_Num5 > 0)
			Functions.npcSay(_thisActor, Say2C.ALL, String_Num5);

		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
			inst.notifyEvent("npc_killed", _thisActor, null);

		broadcastScriptEvent(CLAN_DIED, null, null, 10000);			
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker == _thisActor)
			return;

		_thisActor.callFriends(attacker, damage > 0 ? damage : 100);

		if(i_ai2 == 0)
		{
			i_ai2 = 1;
			_thisActor.i_ai0 = 1;
		}

		if(i_ai4 != 1)
		{
			if(i_ai3 == _thisActor.getMaxHp() && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.40)
			{
				i_ai3 = (int)_thisActor.getCurrentHp();
				if(String_Num3 > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, String_Num3);
			}
			else if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.10 && i_ai3 > _thisActor.getMaxHp() * 0.10)
			{
				i_ai3 = (int) _thisActor.getCurrentHp();
				if(String_Num4 > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, String_Num4);
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(i_ai2 == 0)
		{
			i_ai2 = 1;
			_thisActor.i_ai0 = 1;
		}
		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
	}
}
