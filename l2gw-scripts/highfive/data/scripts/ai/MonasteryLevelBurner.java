package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 14.10.11 21:55
 */
public class MonasteryLevelBurner extends DefaultNpc
{
	public int BURNER_NUMBER = 1000;
	public int CHECK_TIME = 1001;
	public int CHECK_TIME_ANNOUNCE = 1002;
	public int DSPAWN_TIME = 1003;
	public int CHECK_TIME_ANNOUNCE2 = 1004;
	public int SPAWN_TIME = 1005;
	public String my_maker0 = "";
	public String my_maker1 = "";
	public String B_maker = "";
	public String BURNER_NAME = "";

	public MonasteryLevelBurner(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(my_maker0);

		switch(BURNER_NUMBER)
		{
			case 1:
				_thisActor.changeFStrNickName(60008, "");
				break;
			case 2:
				_thisActor.changeFStrNickName(60009, "");
				break;
			case 3:
				_thisActor.changeFStrNickName(60010, "");
				break;
			case 4:
				_thisActor.changeFStrNickName(60011, "");
				break;
		}

		if(maker0 != null)
		{
			maker0.onScriptEvent(21140013, BURNER_NUMBER, 0);
		}

		DefaultMaker maker1 = SpawnTable.getInstance().getNpcMaker(my_maker1);
		if(maker1 != null)
		{
			maker1.onScriptEvent(21140013, BURNER_NUMBER, 0);
		}

		addTimer(CHECK_TIME_ANNOUNCE, 3 * 60000);
		if(BURNER_NUMBER == 1)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.changeNpcState(1);
		}
		else
		{
			_thisActor.i_ai0 = 0;
			_thisActor.changeNpcState(2);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(skill == null)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.changeNpcState(1);
			broadcastScriptEvent(21140011, BURNER_NUMBER, null, 600);
			if(_thisActor.i_ai2 == 0)
			{
				blockTimer(CHECK_TIME_ANNOUNCE);
				_thisActor.i_ai2 = 1;
				addTimer(CHECK_TIME_ANNOUNCE2, 100);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140011 && BURNER_NUMBER != (Integer) arg1)
		{
			_thisActor.i_ai0 = 0;
			_thisActor.changeNpcState(2);
			if(_thisActor.i_ai2 == 0)
			{
				blockTimer(CHECK_TIME_ANNOUNCE);
				_thisActor.i_ai2 = 1;
				addTimer(CHECK_TIME_ANNOUNCE2, 100);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == CHECK_TIME_ANNOUNCE)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60012);
			addTimer(CHECK_TIME, 15000);
		}
		else if(timerId == CHECK_TIME_ANNOUNCE2)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60012);
			addTimer(CHECK_TIME, 15000);
		}
		else if(timerId == CHECK_TIME)
		{
			if(_thisActor.i_ai0 == 1)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(my_maker0);
				if(maker0 != null)
				{
					maker0.onScriptEvent(21140012, BURNER_NUMBER, 0);
					maker0.onScriptEvent(1001, BURNER_NUMBER, 0);
				}
				DefaultMaker maker1 = SpawnTable.getInstance().getNpcMaker(my_maker1);
				if(maker1 != null)
				{
					maker1.onScriptEvent(21140012, BURNER_NUMBER, 0);
					maker1.onScriptEvent(1001, BURNER_NUMBER, 0);
				}
				addTimer(DSPAWN_TIME, 1000);
			}
			else if(_thisActor.i_ai0 == 0)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(my_maker0);
				if(maker0 != null)
				{
					maker0.onScriptEvent(21140013, BURNER_NUMBER, 0);
					maker0.onScriptEvent(1000, BURNER_NUMBER, 0);
				}
				DefaultMaker maker1 = SpawnTable.getInstance().getNpcMaker(my_maker1);
				if(maker1 != null)
				{
					maker1.onScriptEvent(21140013, BURNER_NUMBER, 0);
					maker1.onScriptEvent(1000, BURNER_NUMBER, 0);
				}
				addTimer(DSPAWN_TIME, 1000);
			}
		}
		else if(timerId == DSPAWN_TIME)
		{
			_thisActor.doDie(null);
			addTimer(SPAWN_TIME, 60 * 60000);
		}
		else if(timerId == SPAWN_TIME)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(B_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, BURNER_NUMBER, 0);
			}
		}
	}
}