package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 27.09.11 20:46
 */
public class DefaultNpc extends DefaultAI
{
	public String fnHi = "chi.htm";
	public String fnFlagMan = "flagman.htm";
	public int FriendShip1 = 0;
	public int FriendShip2 = 0;
	public int FriendShip3 = 0;
	public int FriendShip4 = 0;
	public int FriendShip5 = 0;
	public String fnNoFriend = "citizen_html";
	public int NoFnHi = 0;
	public int Dispel_Debuff = 0;
	public int Dispel_Debuff_Prob = 0;

	public DefaultNpc(L2Character actor)
	{
		super(actor);
		check_territory_time = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(NoFnHi == 1)
		{
			return true;
		}
		if(talker.getItemCountByItemId(13560) > 0 || talker.getItemCountByItemId(13561) > 0 || talker.getItemCountByItemId(13562) > 0 || talker.getItemCountByItemId(13563) > 0 || talker.getItemCountByItemId(13564) > 0 || talker.getItemCountByItemId(13565) > 0 || talker.getItemCountByItemId(13566) > 0 || talker.getItemCountByItemId(13567) > 0 || talker.getItemCountByItemId(13568) > 0)
		{
			_thisActor.showPage(talker, fnFlagMan);
			return true;
		}
		if(FriendShip1 == 0)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else if(talker.getItemCountByItemId(FriendShip1) > 0 || talker.getItemCountByItemId(FriendShip2) > 0 || talker.getItemCountByItemId(FriendShip3) > 0 || talker.getItemCountByItemId(FriendShip4) > 0 || talker.getItemCountByItemId(FriendShip5) > 0)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else
		{
			_thisActor.showPage(talker, fnNoFriend);
		}

		return true;
	}

	@Override
	public void onTalkSelected(L2Player talker, int choice, boolean fromChoice)
	{
		_thisActor.showQuestWindow(talker);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
	}

	@Override
	public boolean checkBossPosition()
	{
		return false;
	}

	@Override
	public void returnHome()
	{
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if( Dispel_Debuff == 1 )
			{
				if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 6029313))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 91357185))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 18284545))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 24051713))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 76611585))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 78708737))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 26411009))
				{
						effect.exit();
				}
			}
			else if( Dispel_Debuff == 2 )
			{

				if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 6029313))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 91357185))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 18284545))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 24051713))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 76611585))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 78708737))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 26411009))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
			}
		}
	}
}