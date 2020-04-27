package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 21:56
 */
public class AiTantaarVegetation extends DefaultAI
{
	public long Max_Desire = 1000000000000000000L;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(458752001);
	public L2Skill Skill02_ID = SkillTable.getInstance().getInfo(458752001);
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(458752001);
	public String Privates = "22773;base.AiTantaarLizardmanWizard;1;0sec:22773;base.AiTantaarLizardmanWizard;1;0sec:22773;base.AiTantaarLizardmanWizard;1;0sec:";

	public AiTantaarVegetation(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		if(_thisActor.getNpcId() == 18864)
		{
			_thisActor.createPrivates(Privates);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai1 == 0 && attacker != null)
		{
			if(attacker.getPlayer() != null)
			{
				_thisActor.c_ai0 = attacker.getPlayer().getStoredId();
				_thisActor.changeNpcState(2);
				_thisActor.i_ai1 = 1;
				if(_thisActor.getNpcId() == 18864)
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "skill01 out");
					}
					broadcastScriptEvent(78010087, _thisActor.getObjectId(), _thisActor.c_ai0, 800);
					addTimer(78001, 4000);
				}
				else if(_thisActor.getNpcId() == 18865)
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "buffer out");
					}
					_thisActor.createOnePrivate(18918, "AiTantaarVegetationBuffer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 0, 0);
					_thisActor.doDie(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0));
				}
				else if(_thisActor.getNpcId() == 18868)
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "buffer out");
					}
					_thisActor.createOnePrivate(18918, "AiTantaarVegetationBuffer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 1, 0);
					_thisActor.doDie(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0));
				}
				else if(_thisActor.getNpcId() == 18867)
				{
					broadcastScriptEvent(78010085, _thisActor.getObjectId(), null, 5000);
					_thisActor.doDie(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0));
				}
			}
			else if(_thisActor.getNpcId() == 18867 && attacker.getNpcId() == 18863)
			{
				_thisActor.changeNpcState(2);
				_thisActor.i_ai1 = 1;
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "buffer out");
				}
				_thisActor.createOnePrivate(18918, "AiTantaarVegetationBuffer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, getStoredIdFromCreature(attacker), 2, 0);
				_thisActor.doDie(null);
			}
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		super.onEvtSpelled(skill, caster);
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "SPELLED:" + skill.getId());
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "USE_SKILL_FINISHED:" + skill.getId());
		}
		_thisActor.doDie(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0));
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(!_thisActor.isDead() && eventId == 78010080 && (Long) arg1 != 0 && _thisActor.getNpcId() == 18867)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null && c0 != _thisActor)
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "luring ugoros");
				}
				sendScriptEvent(c0, 78010080, getStoredIdFromCreature(_thisActor), null);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 78001)
		{
			addUseSkillDesire(_thisActor, Skill01_ID, 1, 0, Max_Desire);
			_thisActor.createOnePrivate(18918, "AiTantaarVegetationBuffer", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 4, 0);
		}
	}
}
