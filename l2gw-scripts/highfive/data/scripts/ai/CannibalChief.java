package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 19.08.2010 16:33:36
 */
public class CannibalChief extends Fighter
{
	private L2Skill _summon, _debuff;
	private int _useSummonSkill;

	public CannibalChief(L2Character actor)
	{
		super(actor);
		_summon = _thisActor.getTemplate().getSkillsByType("SPECIAL1")[0];
		_debuff = _thisActor.getTemplate().getSkillsByType("SPECIAL2")[0];
	}

	@Override
	protected void onEvtSpawn()
	{
		_useSummonSkill = 0;
	}

	@Override
	protected boolean createNewTask()
	{
		if(_useSummonSkill == 1 || (_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5 && _useSummonSkill == 0))
		{
			_useSummonSkill = 1;
			addUseSkillDesire(_thisActor, _summon, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}
		if(_useSummonSkill == 2)
		{
			addUseSkillDesire(_thisActor, _debuff, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}

		return super.createNewTask();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		super.onEvtFinishCasting(skill);
		if(skill == null)
			return;
		if(skill.getId() == _summon.getId())
		{
			L2Character mh = _thisActor.getMostHated();
			if(mh != null && mh.getPlayer() != null)
			{
				L2Player player = mh.getPlayer();
				GArray<L2Player> list = new GArray<L2Player>(9);
				if(player.getParty() != null)
				{
					for(L2Player member : player.getParty().getPartyMembers())
						if(!member.isDead() && member.isInRange(player, 900))
							list.add(member);
				}
				else
					list.add(player);

				for(L2Player member : list)
				{
					member.teleToLocation(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 40, 50, _thisActor.getReflection()));
					L2NpcInstance.AggroInfo ai = _thisActor.getAggroList().get(member.getObjectId());
					if(ai != null)
						ai.hate = ai.damage;
				}
			}
			_useSummonSkill = 2;
		}
		else if(skill.getId() == _debuff.getId())
			_useSummonSkill = 3;
	}
}
