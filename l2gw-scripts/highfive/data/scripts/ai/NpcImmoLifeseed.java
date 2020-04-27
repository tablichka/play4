package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 16.12.11 16:57
 */
public class NpcImmoLifeseed extends DefaultNpc
{
	public int tide = 0;
	public int zone = 0;
	public String type = "decoy";
	public int room = 0;
	public String dispatcher_maker = "";
	public L2Skill Skill_Branding = SkillTable.getInstance().getInfo(542375937);
	public L2Skill Skill_dying_display = SkillTable.getInstance().getInfo(395640833);
	public L2Skill NPC_Attack_Skill_C = SkillTable.getInstance().getInfo(387252225);
	public L2Skill NPC_Attack_Skill_L = SkillTable.getInstance().getInfo(387317761);
	public String fnHi = "";
	public String fnHi_working = "lifeseed_decoy002.htm";
	public String fnHi_notworking = "lifeseed_decoy099.htm";
	public String fnHi_keepworking = "lifeseed_decoy003.htm";
	public int reward_siege = 13797;
	public int reward_rate = 30;
	public int TM_RESPAWN_BLANK = 78001;
	public int TM_SEND_TAUNT = 78003;
	public int TM_REWARD_DROP = 78004;
	public int TM_TUMOR_CHECK = 78005;
	public int TM_HEALTH_REPORT = 78006;
	public int TIME_respawn_blank = 10;
	public int TIME_reward_drop = 60;
	public int TM_DECOY_TIME = 78009;
	public int TIME_DECOY_TIME = 300;
	public int TM_DECOY_RECHARGE = 78010;
	public int TIME_DECOY_RECHARGE = 60;
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;

	public NpcImmoLifeseed(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		if( type.equals("def") )
		{
			_thisActor.i_ai0 = 0;
			if( debug )
			{
				_thisActor.i_ai2 = 3000000;
			}
			else
			{
				_thisActor.i_ai2 = 300;
			}
			addTimer(TM_RESPAWN_BLANK, TIME_respawn_blank * 1000);
		}
		else if( type.equals("decoy") )
		{
			_thisActor.i_ai0 = 1;
			_thisActor.i_ai2 = 100;
			_thisActor.i_ai3 = Rnd.get(3) + 1;
			addTimer(TM_DECOY_TIME, TIME_DECOY_TIME * 1000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == TM_RESPAWN_BLANK )
		{
			_thisActor.i_ai0 = 1;
			addTimer(TM_SEND_TAUNT, ( Rnd.get(15) + 1 ) * 1000);
			addTimer(TM_REWARD_DROP, TIME_reward_drop * 1000);
		}
		else if( timerId == TM_SEND_TAUNT )
		{
			if( type.equals("decoy") && _thisActor.i_ai0 == 1 )
			{
				broadcastScriptEvent(78010052, _thisActor.getStoredId(), 911, 500 + Rnd.get(200) - Rnd.get(200));
				addTimer(TM_SEND_TAUNT, ( Rnd.get(10) + 1 ) * 1000);
			}
			else
			{
				broadcastScriptEvent(78010052, _thisActor.getStoredId(), room, 1300 + Rnd.get(200) - Rnd.get(200));
				addTimer(TM_SEND_TAUNT, ( Rnd.get(15) + 1 ) * 1000);
			}
		}
		else if( timerId == TM_REWARD_DROP )
		{
			if( Rnd.get(100) <= reward_rate )
			{
				_thisActor.dropItem(reward_siege, 1);
			}
			addTimer(TM_REWARD_DROP, TIME_reward_drop * 1000);
		}
		else if( timerId == TM_DECOY_TIME )
		{
			_thisActor.onDecay();
		}
		else if( timerId == TM_DECOY_RECHARGE )
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 1800272);
			_thisActor.i_ai0 = 1;
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		if(skill == null)
			return;

		if( _thisActor.i_ai1 == _thisActor.i_ai2 )
		{
			addUseSkillDesire(_thisActor, Skill_dying_display, 1, 1, 10000000000L);
		}
		else if( skill == NPC_Attack_Skill_C || skill == NPC_Attack_Skill_L )
		{
			_thisActor.i_ai1++;
		}
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if( _thisActor.i_ai0 == 2 )
		{
			_thisActor.showPage(talker, fnHi_keepworking);
		}
		else
		{
			_thisActor.showPage(talker, fnHi);
		}
		return true;
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == null)
			return;

		if( skill == Skill_dying_display)
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -7801 )
		{
			if( reply == 1 || reply == 2 || reply == 3 )
			{
				if( ( _thisActor.i_ai0 == 1 && CategoryManager.isInCategory(11, talker.getActiveClass()) ) || reply == _thisActor.i_ai3 )
				{
					_thisActor.showPage(talker, fnHi_working);
					Functions.npcSay(_thisActor, Say2C.ALL, 1800271);
					_thisActor.i_ai1 = ( _thisActor.i_ai1 + 10 );
					_thisActor.i_ai0 = 2;
					addTimer(TM_SEND_TAUNT, 1000);
					addTimer(TM_DECOY_RECHARGE, TIME_DECOY_RECHARGE * 1000);
				}
				else
				{
					_thisActor.i_ai1 = ( _thisActor.i_ai1 + 5 );
					_thisActor.showPage(talker, fnHi_notworking);
				}
			}
		}
	}
}