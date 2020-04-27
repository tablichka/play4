package ai;

import ai.base.RaidBossAgType2;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.09.11 16:39
 */
public class IceFairySirr extends RaidBossAgType2
{
	public L2Skill RangeDDMagic_a = SkillTable.getInstance().getInfo(458752001);
	public L2Skill DeBuff1 = SkillTable.getInstance().getInfo(293601281);
	public L2Skill DeBuff2 = SkillTable.getInstance().getInfo(293666817);
	public L2Skill DeBuff3 = SkillTable.getInstance().getInfo(293732353);

	public IceFairySirr(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1005, 2070000);
		_thisActor.c_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1005)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_1m1");
			if(maker0 != null)
			{
				maker0.onScriptEvent(10005, 0, 0);
			}
			_thisActor.onDecay();
		}
		else if(timerId == 2003)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				L2Party party0 = c0.getParty();
				if(party0 != null)
				{
					for(L2Player member : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(member, 2, 0, 0, 0, 1, 0, 10000, 0, 1010643, "30");
					}
				}
			}
			addTimer(2004, 600000);
		}
		else if(timerId == 2004)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				L2Party party0 = c0.getParty();
				if(party0 != null)
				{
					for(L2Player member : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(member, 2, 0, 0, 0, 1, 0, 10000, 0, 1010643, "20");
					}
				}
			}
			addTimer(1012, 900000);
		}
		else if(timerId == 1012)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(c0 != null)
			{
				L2Party party0 = c0.getParty();
				if(party0 != null)
				{
					for(L2Player member : party0.getPartyMembers())
					{
						Functions.showOnScreentMsg(member, 2, 0, 0, 0, 1, 0, 10000, 0, 1121002);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(Rnd.get((10 * 5)) < 1)
		{
			if(RangeDDMagic_a.getMpConsume() < _thisActor.getCurrentMp() && RangeDDMagic_a.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(RangeDDMagic_a.getId()))
			{
				addUseSkillDesire(attacker, RangeDDMagic_a, 0, 1, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(Rnd.get((10 * 5)) < 1)
		{
			if(RangeDDMagic_a.getMpConsume() < _thisActor.getCurrentMp() && RangeDDMagic_a.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(RangeDDMagic_a.getId()))
			{
				addUseSkillDesire(caster, RangeDDMagic_a, 0, 1, 1000000);
			}
		}
		super.onEvtSeeSpell(skill, caster);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 11036)
		{
			if((Integer) arg1 == 1)
			{
				_thisActor.i_ai0 = 1;
				if(DeBuff1.getMpConsume() < _thisActor.getCurrentMp() && DeBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff1.getId()))
				{
					addUseSkillDesire(_thisActor, DeBuff1, 1, 1, 1000000);
				}
			}
			else if((Integer) arg1 == 2)
			{
				_thisActor.i_ai0 = 2;
				if(DeBuff2.getMpConsume() < _thisActor.getCurrentMp() && DeBuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff2.getId()))
				{
					addUseSkillDesire(_thisActor, DeBuff2, 1, 1, 1000000);
				}
			}
			else if((Integer) arg1 == 3)
			{
				_thisActor.i_ai0 = 3;
				_thisActor.lookNeighbor(600);
			}
			else if((Integer) arg1 == 0)
			{
				_thisActor.i_ai0 = 0;
			}
		}
		if(eventId == 11040)
		{
			_thisActor.c_ai0 =(Long) arg1;
			addTimer(2003, 300000);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_1m1");
		if(maker0 != null)
		{
			maker0.onScriptEvent(10025, 0, 0);
		}
		maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_3m1");
		if(maker0 != null)
		{
			maker0.onScriptEvent(10025, 0, 0);
		}
	}
}
