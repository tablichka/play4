package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 09.09.11 5:41
 */
public class AiBeastfarmBeastEv extends WarriorUseSkill
{
	public int ITEM_feed_item = 15474;
	public int ITEM_feed_adena = 15475;
	public int ITEM_feed_item_s = 15478;
	public int ITEM_feed_adena_s = 15479;
	public int ITEM_feed_item_bress = 15476;
	public int ITEM_feed_adena_bress = 15477;
	public L2Skill SKILL_feed_item = SkillTable.getInstance().getInfo(593035265);
	public L2Skill SKILL_feed_adena = SkillTable.getInstance().getInfo(593100801);
	public L2Skill SKILL_feed_item_s = SkillTable.getInstance().getInfo(593297409);
	public L2Skill SKILL_feed_adena_s = SkillTable.getInstance().getInfo(593362945);
	public L2Skill SKILL_feed_item_bress = SkillTable.getInstance().getInfo(593166337);
	public L2Skill SKILL_feed_adena_bress = SkillTable.getInstance().getInfo(593231873);
	public L2Skill Buff = SkillTable.getInstance().getInfo(458752001);
	public L2Skill Skill_Display = SkillTable.getInstance().getInfo(458752001);
	public int reward_adena = 100000;

	public AiBeastfarmBeastEv(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(_thisActor.param1 != 0)
		{
			_thisActor.l_ai0 = _thisActor.param1;
			_thisActor.c_ai0 = _thisActor.param1;
		}

		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(c0 != null)
		{
			addAttackDesire(c0, 1, 1000);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		super.onEvtAttacked(attacker, damage, skill);

		if(skill == SKILL_feed_item || skill == SKILL_feed_adena || skill == SKILL_feed_item_s || skill == SKILL_feed_adena_s || skill == SKILL_feed_item_bress || skill == SKILL_feed_adena_bress)
		{
			Functions.showSystemMessageFStr(attacker, 1801092);
			if(skill == SKILL_feed_item)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item, 1);
			}
			else if(skill == SKILL_feed_adena)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena, 1);
			}
			else if(skill == SKILL_feed_item_s)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_s, 1);
			}
			else if(skill == SKILL_feed_adena_s)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_s, 1);
			}
			else if(skill == SKILL_feed_item_bress)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_bress, 1);
			}
			else if(skill == SKILL_feed_adena_bress)
			{
				_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_bress, 1);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(killer != null && killer.getPlayer() != null)
		{
			for(int i0 = 0; i0 < 15; i0++)
			{
				addUseSkillDesire(_thisActor, Skill_Display, 1, 0, 100000000);
				_thisActor.dropItem(killer.getPlayer(), 57, Rnd.get(reward_adena) + reward_adena / 2);
			}
		}
		super.onEvtDead(killer);
	}
}
